package cn.com.quanyou.ioc.file.manage.controller;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ProcessStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FastDFSFile;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.common.utils.MD5Utils;
import cn.com.quanyou.ioc.file.manage.facade.IAnalysisTaskService;
import cn.com.quanyou.ioc.file.manage.facade.IAsyncTaskService;
import cn.com.quanyou.ioc.file.manage.facade.ISearchService;
import cn.com.quanyou.ioc.file.manage.facade.IUploadFileInfoService;
import cn.com.quanyou.ioc.file.manage.vo.*;
import com.monitorjbl.xlsx.StreamingReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: FileController
 * @date 2019/6/519:53
 * @projectName file-manage
 * @description:
 */

@Controller
@RequestMapping("/file")
@Slf4j
@Api(tags = "文件服务")
public class FileController extends  BaseController{

    @Autowired
    private IUploadFileInfoService uploadFileInfoService;

    @Autowired
    private IAnalysisTaskService taskService;

    @Autowired
    private IAsyncTaskService asyncTaskService;

    @Autowired
    private ISearchService searchService;

    /**
    * @Description 上传附件选择文件页面
    * @Author heshiyi@quanyou.com.cn
    * @Date 21:23 2019/6/5
    * @param
    * @return
    **/
    @ApiOperation("跳转上传附件选择文件页面")
    @RequestMapping(value = "/upload",method = {RequestMethod.GET})
    public String uploadFile(){

        return "views/uploadFileTest";
    }

    /**
    * @Description 文件上传action
    * @Author heshiyi@quanyou.com.cn
    * @Date 21:24 2019/6/5
    * @param request
    * @return
    **/
    @ResponseBody
    @ApiOperation("文件上传action")
    @RequestMapping(value = "/upload",method = {RequestMethod.POST})
    public List<ResultInfo<UploadFileInfoBean,String>> upload(HttpServletRequest request){
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        List<ResultInfo<UploadFileInfoBean,String>> uploadResult = new ArrayList<>();
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext()){
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());

                if(file!=null && file.getSize() != 0){
                    uploadResult.add(this.saveFile(file,super.getUserName(request)));
                }else{
                    ResultInfo<UploadFileInfoBean,String> resultInfo = new ResultInfo<>();
                    resultInfo.setMessage("文件获取失败");
                    resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                    uploadResult.add(resultInfo);
                }
            }
        }else{
            ResultInfo<UploadFileInfoBean,String> resultInfo = new ResultInfo<>();
            resultInfo.setMessage("没有检测到上传文件");
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            uploadResult.add(resultInfo);
        }
        return uploadResult;
    }

    /**
    * @Description 检查文件的后缀名
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:43 2019/6/26
    * @param request
    * @param fileExt
    * @return
    **/
    private ResultInfo<String ,String> checkFileType(HttpServletRequest request,String fileExt){
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        ResultInfo<String,String> checkResult = new ResultInfo<>();
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext()){
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());

                if(file!=null && file.getSize() != 0){
                    String fileName=file.getOriginalFilename();
                    String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
                    if(!fileExt.equals(ext)){
                        checkResult.setMessage("只能导入xlsx格式的excel文件");
                        checkResult.setResultStatusEnum(ResultStatusEnum.failed);
                        return checkResult;
                    }else{
                        break;
                    }
                }else{
                    checkResult.setMessage("文件获取失败");
                    checkResult.setResultStatusEnum(ResultStatusEnum.failed);
                    return checkResult;
                }
            }
        }else{
            checkResult.setMessage("没有检测到上传文件");
            checkResult.setResultStatusEnum(ResultStatusEnum.failed);
        }
        return checkResult;
    }

    /***
    * @Description 获取去Excel解析异常的分页信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:25 2019/6/15
    * @param taskId 任务ID
    * @param pageInfo 分页象信息
    * @return
    **/
    @ResponseBody
    @ApiOperation("获取去Excel解析异常的分页信息")
    @RequestMapping(value = "/{taskId}/analysisExceptions",method = {RequestMethod.GET})
    public ResultInfo<PageInfo<AnalysisTaskErrorBean>,String> analysisErrorDetail(@PathVariable("taskId") String taskId,PageInfo<AnalysisTaskErrorBean> pageInfo) {

        if(StringUtils.isBlank(taskId)){
            ResultInfo<PageInfo<AnalysisTaskErrorBean>,String> resultInfo = new ResultInfo<>();
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("任务ID丢失");
            return resultInfo;
        }
        return taskService.queryAnalysisExceptionByTaskId(taskId,pageInfo);
    }

    /**
    * @Description 数据查询
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:43 2019/6/20
    * @param
    * @return
    **/
    @ResponseBody
    @ApiOperation("数据查询")
    @RequestMapping(value = "/search",method = {RequestMethod.GET})
    public ResultInfo<PageInfo<Object>,String> searchData(SearchParamVo searchParamVo,PageInfo<Object> pageInfo){
        ResultInfo<PageInfo<Object>,String> resultInfo = searchService.search(searchParamVo,pageInfo);

        return resultInfo;
    }

    /**
    * @Description 下载文件
    * @Author heshiyi@quanyou.com.cn
    * @Date 9:59 2019/6/15
    * @param fileId 文件ID
    * @param response
    * @return
    **/
    @ApiOperation("下载文件")
    @RequestMapping(value = "/download/{fileId}",method = RequestMethod.GET)
    public void  downloadFile(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        UploadFileInfoBean fileInfo =  uploadFileInfoService.getEntityByDataId(fileId);
        if(fileInfo == null){
            return ;
        }

        InputStream is=FileManager.downFile(fileInfo.getGroupName(),fileInfo.getRemoteFileName());
        if(is==null){
            return ;
        }

        BufferedInputStream bins=new BufferedInputStream(is);//放到缓冲流里面
        OutputStream outs=response.getOutputStream();//获取文件输出IO流
        BufferedOutputStream bouts=new BufferedOutputStream(outs);
        response.setContentType("application/x-download");//设置response内容的类型 普通下载类型
        response.setContentLength(fileInfo.getFileSize().intValue());//设置文件大小
        response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileInfo.getFileName(), "UTF-8"));//设置头部信息
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        //开始向网络传输文件流
        while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
            bouts.write(buffer, 0, bytesRead);
        }
        bouts.flush();//这里一定要调用flush()方法
        is.close();
        bins.close();
        outs.close();
        bouts.close();
    }

    /**
    * @Description  保存文件
    * @Author heshiyi@quanyou.com.cn
    * @Date 9:11 2019/6/10
    * @param multipartFile
    * @return
    **/
    private ResultInfo<UploadFileInfoBean,String> saveFile(MultipartFile multipartFile,String loginUserName){
        String fileName=multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        try {
            InputStream inputStream=multipartFile.getInputStream();
            if(inputStream!=null){
                int len1 = inputStream.available();
                file_buff = new byte[len1];
                inputStream.read(file_buff);
            }

            Long fileSize = multipartFile.getSize();
            String fileHashCode = MD5Utils.MD5(multipartFile.getBytes());

            inputStream.close();

            UploadFileInfoBean oldFile = uploadFileInfoService.getEntityByHashCode(fileHashCode);

            ResultInfo<UploadFileInfoBean,String> resultInfo = null;
            if( oldFile == null){

                FastDFSFile file = new FastDFSFile(file_buff,fileName, ext);
                resultInfo = FileManager.upload(file);
                resultInfo.getData().setFileName(fileName);
                resultInfo.getData().setFileSize(fileSize);
                resultInfo.getData().setFileHashCode(fileHashCode);
                resultInfo.getData().setCreatedBy(loginUserName);
                resultInfo.getData().setLastUpdatedBy(loginUserName);
                uploadFileInfoService.add(resultInfo.getData());
            } else{
                //如果已经存在该文件，则不重新上传到服务器
                resultInfo = new ResultInfo<>();
                resultInfo.setData(oldFile);
                resultInfo.setResultStatusEnum(ResultStatusEnum.alreadyUpload);
            }
            return resultInfo;
        } catch (Exception e) {
            ResultInfo<UploadFileInfoBean,String> result = new ResultInfo<>();
            result.setResultStatusEnum(ResultStatusEnum.failed);
            result.setMessage(e.getMessage());
            UploadFileInfoBean data = new UploadFileInfoBean();
            data.setFileName(fileName);
            return result;
        }
    }


    /**
    * @Description 导入页面
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:39 2019/6/11
    * @param model
    * @return
    **/
    @ApiOperation("跳转导入页面")
    @RequestMapping(value = "/import",method = {RequestMethod.GET})
    public String importFile(ModelMap model){

        model.addAttribute("importFileTypes",ImportFileTypeEnum.values());
        return "views/importFileTest";
    }

    @ResponseBody
    @ApiOperation("查询导入历史")
    @RequestMapping(value = "/importHistory",method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "importType", value = "导入类型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "shopName", value = "店铺名称", dataType = "String"),
            @ApiImplicitParam(name = "date", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "factoryCode", value = "工厂code", dataType = "String"),
            @ApiImplicitParam(name = "lineCode", value = "生产线code", dataType = "String"),
            @ApiImplicitParam(name = "factoryName", value = "工厂名", dataType = "String"),
            @ApiImplicitParam(name = "lineName", value = "生产线名", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String"),
            @ApiImplicitParam(name = "entrance", value = "入口：用于指定查询方式[目前使用cds表示新类型查询]", dataType = "String")
    })
    public ResultInfo<PageInfo<AnalysisTaskBean>,String> queryImportHistoryByImportType(ImportParamVo paramVo, PageInfo<AnalysisTaskBean> pageInfo,HttpServletRequest request){
        if(!ImportFileTypeEnum.hasCorrentGroup(paramVo.getImportType())){
            ResultInfo<PageInfo<AnalysisTaskBean>,String> resultInfo = new ResultInfo<>();
            resultInfo.setData(pageInfo);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("导入类型错误");
            return resultInfo;
        }
        return taskService.queryImportHistoryByImportType(paramVo, pageInfo);
    }


    /***
    * @Description  文件导入
    * @Author heshiyi@quanyou.com.cn
    * @Date 9:45 2019/6/10
    * @param request 请求体，包含文件信息
    * @param importParam
    * @return 上传后的文件信息，以及导入结果信息
    **/
    @ResponseBody
    @RequestMapping(value = "/import",method = {RequestMethod.POST})
    @ApiOperation("文件导入")
    public ResultInfo<AnalysisTaskBean,String> importData(HttpServletRequest request, ImportParamVo importParam) {

//        List<ResultInfo<AnalysisTaskBean,String>> resultList = new ArrayList<>();
        ResultInfo<AnalysisTaskBean, String> resultInfo = new ResultInfo<>();
        ResultInfo<String ,String> checkResult = this.checkFileType(request,"xlsx");
        if(checkResult.isFailed()){
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage(checkResult.getMessage());
            return resultInfo;
        }

        /**
         * @Description  导入数据的时候，先上传文件到文件服务器
         * @Author heshiyi@quanyou.com.cn
         * @Date 9:14 2019/6/10
         **/
        List<ResultInfo<UploadFileInfoBean,String>> uploadResultList = this.upload(request);

        if(uploadResultList  == null || uploadResultList.isEmpty()){
            //上传失败
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("文件上传失败");
            return resultInfo;
        }

        ResultInfo<UploadFileInfoBean,String> uploadResult = uploadResultList.get(0);

            if(uploadResult.isFailed()){
                //上传失败
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage(uploadResult.getMessage());
                return resultInfo;
            }
            if(uploadResult.getData() == null ){
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage("文件信息丢失");
                return resultInfo;
            }
            //加入异步任务
            AnalysisTaskBean entity = new AnalysisTaskBean();
            entity.setExcelRowAnalysis(0);
            entity.setFileDataId(uploadResult.getData().getDataId());
            entity.setProcessStatus(ProcessStatusEnum.waitAnalysis.name());
            entity.setFileInfo(uploadResult.getData());
            entity.setCreatedBy(super.getUserName(request));
            entity.setLastUpdatedBy(super.getUserName(request));
            entity.setImportUser(super.getUserName(request));

            InputStream is = FileManager.downFile(entity.getFileInfo().getGroupName(),entity.getFileInfo().getRemoteFileName());
            Workbook workbook = null;
            try{
                workbook = StreamingReader.builder()
                        .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                        .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                        .open(is);            // InputStream or File for XLSX file (required)
            }catch (Exception e){
                log.error(e.getMessage());
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage("文件打开失败，只能导入xlsx的excel文件");
                return resultInfo;
            }


            boolean hasSheetNeedImport = false;

            for(int i =0;i<workbook.getNumberOfSheets();i++) {

                String sheetName = workbook.getSheetName(i);
                ImportFileTypeEnum type = ImportFileTypeEnum.getByDescription(sheetName);
                if (type != null) {
                    if(type.getGroup().equals(importParam.getImportType())){
                        resultInfo.setMessage(""+type.getDescription()+"加入导入任务列表");
                        hasSheetNeedImport = true;
                    }
                }
            }
            if(!hasSheetNeedImport){
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage("没有需要导入的sheet，请检查sheet名称及导入类型是否匹配");
                return resultInfo;
            }
            try {
                is.close();
            } catch (IOException e) {
                log.info(e.getMessage());
            }
            asyncTaskService.createImportExcelTask(entity,importParam);
        return resultInfo;
    }

    /**
     * @Description: 文件导入[本地测试]
     * @param file 文件
     * @param importParam 导入参数
     * @Author: yangli
     * @Date: 2019/9/10-14:48
     **/
    @ResponseBody
    @RequestMapping(value = "/import_v2",method = {RequestMethod.POST})
    @ApiOperation("文件导入[本地测试]")
    public ResultInfo<AnalysisTaskBean,String> uploadFileForLocalTest(MultipartFile file,
                                                                      ImportParamVo importParam) {
        String userName = "admin";
        ResultInfo<AnalysisTaskBean, String> resultInfo = new ResultInfo<>();

        List<ResultInfo<UploadFileInfoBean,String>> uploadResultList = new ArrayList<>();
        uploadResultList.add(this.saveFile(file, userName));

        if(uploadResultList  == null || uploadResultList.isEmpty()){
            //上传失败
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("文件上传失败");
            return resultInfo;
        }

        ResultInfo<UploadFileInfoBean,String> uploadResult = uploadResultList.get(0);

            if(uploadResult.isFailed()){
                //上传失败
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage(uploadResult.getMessage());
                return resultInfo;
            }
            if(uploadResult.getData() == null ){
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage("文件信息丢失");
                return resultInfo;
            }
            //加入异步任务
            AnalysisTaskBean entity = new AnalysisTaskBean();
            entity.setExcelRowAnalysis(0);
            entity.setFileDataId(uploadResult.getData().getDataId());
            entity.setProcessStatus(ProcessStatusEnum.waitAnalysis.name());
            entity.setFileInfo(uploadResult.getData());
            entity.setCreatedBy(userName);
            entity.setLastUpdatedBy(userName);
            entity.setImportUser(userName);

            InputStream is = FileManager.downFile(entity.getFileInfo().getGroupName(),entity.getFileInfo().getRemoteFileName());
            Workbook workbook = null;
            try{
                workbook = StreamingReader.builder()
                        .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                        .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                        .open(is);            // InputStream or File for XLSX file (required)
            }catch (Exception e){
                log.error(e.getMessage());
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage("文件打开失败，只能导入xlsx的excel文件");
                return resultInfo;
            }


            boolean hasSheetNeedImport = false;

            for(int i =0;i<workbook.getNumberOfSheets();i++) {

                String sheetName = workbook.getSheetName(i);
                ImportFileTypeEnum type = ImportFileTypeEnum.getByDescription(sheetName);
                if (type != null) {
                    if(type.getGroup().equals(importParam.getImportType())){
                        resultInfo.setMessage(""+type.getDescription()+"加入导入任务列表");
                        hasSheetNeedImport = true;
                    }
                }
            }
            if(!hasSheetNeedImport){
                resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
                resultInfo.setMessage("没有需要导入的sheet，请检查sheet名称及导入类型是否匹配");
                return resultInfo;
            }
            try {
                is.close();
            } catch (IOException e) {
                log.info(e.getMessage());
            }
            asyncTaskService.createImportExcelTask(entity,importParam);
        return resultInfo;
    }

    @ResponseBody
    @ApiOperation("文件上传v2[本地测试]")
    @RequestMapping(value = "/upload_v2",method = {RequestMethod.POST})
    public List<ResultInfo<UploadFileInfoBean, String>> upload_v2(MultipartFile file) {
        //检查form中是否有enctype="multipart/form-data"
        List<ResultInfo<UploadFileInfoBean, String>> uploadResult = new ArrayList<>();

        if (file != null && file.getSize() != 0) {
            uploadResult.add(this.saveFile(file, "admin"));
        } else {
            ResultInfo<UploadFileInfoBean, String> resultInfo = new ResultInfo<>();
            resultInfo.setMessage("文件获取失败");
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            uploadResult.add(resultInfo);
        }

        return uploadResult;
    }
}
