package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.IServiceBellwetherTaobaoImportDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceBellwetherTaobaoBean;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: OperationalDataImportService
 * @date 2019/6/1010:28
 * @projectName file-manage
 * @description:
 */
@Service
@Slf4j
public class ServiceBellwetherTaobaoService extends ExcelImportService implements IExcelImportService<ServiceBellwetherTaobaoBean> {

    @Autowired
    private IServiceBellwetherTaobaoImportDao importDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {

        UploadFileInfoBean fileInfo = task.getFileInfo();
        if(fileInfo == null || excelModel == null){
            return ;
        }
        ExcelUtilsMap<ServiceBellwetherTaobaoBean> excelUtils = new ExcelUtilsMap<>(ServiceBellwetherTaobaoBean.class,this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(),fileInfo.getRemoteFileName()),excelModel,task);
    }

    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<Map<String,String>> shopNames = dataValidateService.queryShopNames();

        List<ServiceBellwetherTaobaoBean> list = entityList;
        for(ServiceBellwetherTaobaoBean entity : list){
            ResultInfo<AnalysisTaskErrorBean,String > shopCheckResult = super.checkShop(entity.getShopName(),entity.getTaskId(),entity.getRowNumber(),shopNames);
            if(shopCheckResult.isFailed()){
                errorList.add(shopCheckResult.getData());
                unvalidList.add(entity.getDataId());
            }
        }

        return errorList;
    }


    @Override
    public void dataValidate(String taskId,String loginUser) {
        List<Map<String,Object>> dataList =  importDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());
        BeanUtils<ServiceBellwetherTaobaoBean> beanUtils = new BeanUtils<>(ServiceBellwetherTaobaoBean.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId,beanUtils,dataList,validList,unvalidList ,loginUser);

        if(!validList.isEmpty()){
            //更新验证结果
            importDao.updateValidStatusByDataIds(validList, ValidStatusEnum.success.getCode());
        }
        if(!unvalidList.isEmpty()){
            //更新验证结果
            importDao.updateValidStatusByDataIds(unvalidList, ValidStatusEnum.fail.getCode());
        }
    }

    @Transactional
    @Override
    public ResultInfo<String,String> copyDataFromTemp(String taskId) {
        ResultInfo<String,String> resultInfo = super.checkAnalysisStatus(taskId);
        if(resultInfo .isFailed()){
            return resultInfo;
        }
        int count = importDao.queryMistakeAndUncheckedCount(taskId);
        if(count>0){
            log.info("还有数据没有验证或验证有误，停止从临时表导入正式表：task="+taskId);
            resultInfo.setResultStatusEnum(ResultStatusEnum.failed);
            resultInfo.setMessage("还有数据没有验证或验证有误");
            return resultInfo;
        }
        importDao.deleteByTaskId(taskId);
        importDao.cloneFromTempT(taskId);
        super.finishSyn(taskId);

        return resultInfo;
    }

    @Override
    public void saveExcelDataMap(List<Map<String, Object>> dataList) {

        if( dataList == null || dataList.isEmpty()){
            return;
        }
        importDao.addBatchMap(dataList);

    }

    @Override
    public ResultInfo<PageInfo<ServiceBellwetherTaobaoBean>, String> search(SearchParamVo searchParamVo, PageInfo<ServiceBellwetherTaobaoBean> pageInfo) {
        List<ServiceBellwetherTaobaoBean> dataList = importDao.search(searchParamVo,pageInfo.getStartRow(),pageInfo.getEndRow());
        pageInfo.setDataList(dataList);
        pageInfo.setTotal(importDao.querySearchTotal(searchParamVo,pageInfo.getStartRow(),pageInfo.getEndRow()));

        ResultInfo<PageInfo<ServiceBellwetherTaobaoBean>, String> resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        return resultInfo;
    }
}
