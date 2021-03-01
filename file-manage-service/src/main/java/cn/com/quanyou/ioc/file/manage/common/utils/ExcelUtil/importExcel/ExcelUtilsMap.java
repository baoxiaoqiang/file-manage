package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel;

import cn.com.quanyou.ioc.file.manage.common.ConstantsFileManage;
import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.utils.DateUtil;
import cn.com.quanyou.ioc.file.manage.facade.IExcelImportService;
import cn.com.quanyou.ioc.file.manage.service.CdsProcessPieceService;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskBean;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelUtils
 * @date 2019/6/510:53
 * @projectName file-manage
 * @description:
 */
public class ExcelUtilsMap<T> {

    private Class<T> clazzT;

    /**
     * @Description 数据持久化回调服务
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:40 2019/6/13
     **/
    private IExcelImportService<T> excelImportService;


    public ExcelUtilsMap(Class<T> clazzT, IExcelImportService<T> excelImportService){
        this.clazzT = clazzT;
        this.excelImportService = excelImportService;
    }


    /**
    * @Description 设置excel解析模板的解析方案信息，excel列和实体属性的对应关系
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:19 2019/6/10
    * @param excelModel excel解析模板
    * @return
    **/
    private void getExcelModelCellAndDataAttributeMap(ExcelModel excelModel){
        // 获得字段注解
        Field[] fields = clazzT.getDeclaredFields();
        for(Field field : fields){
            ExcelModelAnnotation.FieldAnnotation fieldAnnotation =  field.getAnnotation(ExcelModelAnnotation.FieldAnnotation.class);
            if(fieldAnnotation == null){
                continue;
            }
            if(fieldAnnotation.excelCellIndex() != -1){
                excelModel.getCellAndDataAttributeMap().put(fieldAnnotation.excelCellIndex(),field.getName());
                excelModel.getHeaderMap().put(fieldAnnotation.excelCellIndex(),fieldAnnotation.headerName());
            }
        }
    }



    /**
    * @Description 获取行内容
    * @Author heshiyi@quanyou.com.cn
    * @Date 13:24 2019/6/12
    * @param row 行数据
    * @param dataList 结果列表
    * @param excelModel 解析模板
    * @return true：有错误信息；false：无错误信息
    **/
    private void getContentFromRow(Row row ,List<Map<String,Object>> dataList,ExcelModel excelModel,AnalysisTaskBean task){
        if(row == null){
            return ;
        }
        //循环sheet的行
        Map<String,Object> objMap = new HashMap<>();
        for (Cell cell : row) {
            //循环row的列
            this.setTAttribute(objMap,cell,excelModel);
        }
        Iterator<Map.Entry<String,Object>> iterator = objMap.entrySet().iterator();
        boolean isEmpty = true;
        while(iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            if(!StringUtils.isBlank((String) entry.getValue())){
                isEmpty = false;
                break;
            }
        }
        if(isEmpty){
            //该行为空
            return;
        }

        //特殊处理 导入指定店铺的数据
//        if (!StringUtils.isEmpty(task.getShopName())) {//导入店铺条件不为空时
//            if (objMap.get("shopName").equals(task.getShopName())) {
//                dataList.add(objMap);
//            }
//        }else {
//            dataList.add(objMap);
//        }
        dataList.add(objMap);


        objMap.put(ConstantsFileManage.ROW_NUMBER_FIELD,row.getRowNum());
        objMap.put("createdBy",task.getCreatedBy());
        objMap.put("lastUpdatedBy",task.getLastUpdatedBy());
        objMap.put("taskId",task.getDataId());
        if(ImportFileTypeEnum.logistics.getType().equals(task.getTaskType())){
            objMap.put("shipmentDate",DateUtil.dateToString(task.getReportDate()));
        }else{
            objMap.put("reportDate",DateUtil.dateToString(task.getReportDate()));
        }
    }


    /**
    * @Description 采用xlsx-streamer，分步加载数据。避免内存溢出
    * @Author heshiyi@quanyou.com.cn
    * @Date 9:45 2019/6/13
    * @param is 文件输入流
    * @return
    **/
    public void importExcelByXlsxStreamer(InputStream is, ExcelModel excelModel, AnalysisTaskBean task){

        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);            // InputStream or File for XLSX file (required)
        this.getExcelModelCellAndDataAttributeMap(excelModel);
        int rowNum = 0;
        List<Map<String,Object>> dataList = new ArrayList<>();
        for(Row row : workbook.getSheetAt(excelModel.getSheetNum())){
            if(row.getRowNum()<excelModel.getDataRowStart()){
                continue;
            }
            rowNum ++;
            if(rowNum> ConstantsFileManage.IMPORT_BATCH_ROW_NUMBER){
                excelImportService.saveExcelDataMap(dataList);//持久化数据
                excelImportService.dataValidate(task.getDataId(),task.getCreatedBy());//数据验证，分步执行验证
                dataList.clear();//清空结果列表
                rowNum = 0;
            }
            this.getContentFromRow(row,dataList,excelModel,task);
            if(task.getFactoryCode()!=null){
                for (Map<String, Object> map : dataList) {
                    map.put("factoryCode",task.getFactoryCode());
                    map.put("lineCode",task.getLineCode());
                    map.put("pieceDate",task.getReportDate());
                }
            }
        }
        excelImportService.saveExcelDataMap(dataList);//持久化数据
        excelImportService.dataValidate(task.getDataId(),task.getCreatedBy());//数据验证
        excelImportService.copyDataFromTemp(task.getDataId());//同步数据从临时表到正式表
    }

    private Field getField(String objAttributeName){
        Field field = null;
        try {
            field = clazzT.getDeclaredField(objAttributeName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return field;
    }


    /**
    * @Description 设置导出的数据信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 15:02 2019/6/5
    * @param cell 列数据
    * @param excelModel 导出配置相关配置信息
    * @return
    **/
    private void setTAttribute(Map<String,Object> objMap,Cell cell,ExcelModel excelModel) {

        String objAttributeName = excelModel.getCellAndDataAttributeMap().get(cell.getColumnIndex());
        if(StringUtils.isBlank(objAttributeName)){
            return ;
        }

        String celValueStr = cell.getStringCellValue();
        if(celValueStr != null ) {
            //日期，获取的数据有问题。
            Field field = this.getField(objAttributeName);

            if(celValueStr.startsWith("reserved-") && celValueStr.endsWith("1F")) {
                if (field.getType() == Date.class) {
                    Date date = DateUtil.parseDate(celValueStr);
                    if (date != null) {
                        celValueStr = DateUtil.dateToString(date);
                    } else {
                        Date value = cell.getDateCellValue();
                        if (value != null) {
                            celValueStr = DateUtil.dateToString(value);
                        }
                    }
                }
            }
            if(field.getType() == Integer.class ||field.getType() == Long.class ||field.getType() == BigDecimal.class){
                //去掉数字中的逗号
                celValueStr = celValueStr.replaceAll(",","");
            }
        }
        objMap.put(objAttributeName,celValueStr);
    }

}
