package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.common.component.util.StringUtils;
import cn.com.quanyou.ioc.file.manage.common.ConstantsFileManage;
import cn.com.quanyou.ioc.file.manage.common.PageInfo;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.BeanUtils;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelModel;
import cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel.ExcelUtilsMap;
import cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils.FileManager;
import cn.com.quanyou.ioc.file.manage.dao.IAnalysisTaskDao;
import cn.com.quanyou.ioc.file.manage.dao.IServiceSaleBeforeImportDao;
import cn.com.quanyou.ioc.file.manage.dao.IShopImportBasicInfoImportDao;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ServiceSaleBeforeBean;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ShopBasicInfoBean;
import cn.com.quanyou.ioc.file.manage.vo.SearchParamVo;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: OperationalDataImportService
 * @date 2019/6/1010:28
 * @projectName file-manage
 * @description:
 */
@Service
@Slf4j
public class ServiceSaleBeforeService extends ExcelImportService implements IExcelImportService<ServiceSaleBeforeBean> {

    @Autowired
    private IServiceSaleBeforeImportDao importDao;


    @Autowired
    private IShopImportBasicInfoImportDao shopBasicDao;

    @Autowired
    private IAnalysisTaskDao taskDao;

    @Override
    public void importExcel(AnalysisTaskBean task, ExcelModel excelModel) {

        UploadFileInfoBean fileInfo = task.getFileInfo();
        if(fileInfo == null || excelModel == null){
            return ;
        }
        ExcelUtilsMap<ServiceSaleBeforeBean> excelUtils = new ExcelUtilsMap<>(ServiceSaleBeforeBean.class,this);
        excelUtils.importExcelByXlsxStreamer(FileManager.downFile(fileInfo.getGroupName(),fileInfo.getRemoteFileName()),excelModel,task);
    }


    @Override
    public List<AnalysisTaskErrorBean> relationDataCheck(List validList, List unvalidList, List entityList) {
        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();

        List<Map<String,String>> shopNames = dataValidateService.queryShopNames();

        List<ServiceSaleBeforeBean> list = entityList;

        List<ShopBasicInfoBean> basicList = shopBasicDao.queryAllFormal();

        for(ServiceSaleBeforeBean entity : list){
            ResultInfo<AnalysisTaskErrorBean,String > shopCheckResult = super.checkShop(entity.getShopName(),entity.getTaskId(),entity.getRowNumber(),shopNames);
            if(shopCheckResult.isFailed()){
                errorList.add(shopCheckResult.getData());
                unvalidList.add(entity.getDataId());
            }else{
                //店铺正确，检验店铺账号关联
                ResultInfo<AnalysisTaskErrorBean,String> checkShopUserResult = super.isCorrectShopAndUserName(entity.getTaskId(),entity.getRowNumber(),entity.getShopName(),entity.getUserName(),basicList);
                if(checkShopUserResult.isFailed()){
                    errorList.add(checkShopUserResult.getData());
                    unvalidList.add(entity.getDataId());
                }
            }
            ResultInfo<AnalysisTaskErrorBean,String > dateCheckResult = super.checkReportDate(entity.getReportDate(),entity.getSaleDate(),entity.getTaskId(),entity.getRowNumber(),"日期");
            if(dateCheckResult.isFailed()){
                errorList.add(dateCheckResult.getData());
                unvalidList.add(entity.getDataId());
            }
        }

        return errorList;
    }

    @Override
    public void dataValidate(String taskId,String loginUser) {
        List<Map<String,Object>> dataList =  importDao.queryByTaskId(taskId, ValidStatusEnum.unValid.getCode());

        // 20191024 新增过滤出与选择的店铺名称相同的数据来进行校验，忽略其他店铺的数据
        String reqShopName = taskDao.queryShopNameByTaskId(taskId);
        if(StringUtils.isNotBlank(reqShopName)){
            dataList = dataList.stream().filter(map -> StringUtils.isNotBlank((String)map.get(ConstantsFileManage.SHOP_NAME_KEY)) &&
                    map.get(ConstantsFileManage.SHOP_NAME_KEY).equals(reqShopName))
                    .collect(Collectors.toList());
        }

        BeanUtils<ServiceSaleBeforeBean> beanUtils = new BeanUtils<>(ServiceSaleBeforeBean.class);
        List<String> validList = new ArrayList<>();
        List<String> unvalidList = new ArrayList<>();

        super.dataValidate(taskId,beanUtils,dataList,validList,unvalidList,loginUser);

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

        String shopName = taskDao.queryShopNameByTaskId(taskId);
        if(StringUtils.isNotBlank(shopName)){
            importDao.deleteByTaskIdAndShopName(taskId, shopName);
            importDao.cloneFromTempTByShop(taskId, shopName);
        } else {
            importDao.deleteByTaskId(taskId);
            importDao.cloneFromTempT(taskId);
        }

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
    public ResultInfo<PageInfo<ServiceSaleBeforeBean>, String> search(SearchParamVo searchParamVo, PageInfo<ServiceSaleBeforeBean> pageInfo) {
        List<ServiceSaleBeforeBean> dataList = importDao.search(searchParamVo,pageInfo.getStartRow(),pageInfo.getEndRow());
        pageInfo.setDataList(dataList);
        pageInfo.setTotal(importDao.querySearchTotal(searchParamVo,pageInfo.getStartRow(),pageInfo.getEndRow()));

        ResultInfo<PageInfo<ServiceSaleBeforeBean>, String> resultInfo = new ResultInfo<>();
        resultInfo.setData(pageInfo);
        return resultInfo;
    }
}
