package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.exportExcel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelExportUtils
 * @date 2019/6/24 17:45
 * @projectName file-manage
 * @description: excel文件导出工具类
 */
@Slf4j
public class ExcelExportUtils {


    public File test(String exportType, Map<String,List> dataMap){
        ExcelFile fileInfo = getFileInfo(exportType);
        try {
            return this.getSXSSFWorkbook(fileInfo,exportType,dataMap);
        } catch (IOException e) {
            log.error("文件导出失败："+e.getMessage());
            return null;
        }
    }

    private File getSXSSFWorkbook(ExcelFile fileInfo,String exportType, Map<String,List> dataMap) throws IOException {
            if(fileInfo == null){
                return null;
            }

            SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk

            CellStyle cellStyleHeader = wb.createCellStyle();
            cellStyleHeader.setAlignment(HorizontalAlignment.CENTER); //水平布局：居中
            cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);

            for(ExcelSheet excelSheet : fileInfo.getSheets()){
                int rowIndex =0;
                Sheet sheet = wb.createSheet(excelSheet.getSheetName());
                rowIndex = this.setHeader(rowIndex,sheet,excelSheet,cellStyleHeader);
                this.setData(sheet,rowIndex,excelSheet,dataMap.get(excelSheet.getSheetClass()));

            }
            File tmpFile = File.createTempFile(fileInfo.getFileName(),".xlsx");

            FileOutputStream out = new FileOutputStream(tmpFile);
            wb.write(out);
            out.close();

            wb.dispose();
            return tmpFile;
    }


    /**
    * @Description 导出excel文件，
    * @Author heshiyi@quanyou.com.cn
    * @Date 11:21 2019/6/25
    * @param exportType 导出类型，再exportTemplate.xml中定义的file
    * @return 生成文件的文件路径
    **/
    public void export(HttpServletResponse response, String exportType, Map<String,List> dataMap) throws IOException {

        ExcelFile fileInfo = getFileInfo(exportType);
        File excelFile = this.getSXSSFWorkbook(fileInfo,exportType,dataMap);
        if(excelFile == null){
            return ;
        }
        InputStream is = new FileInputStream(excelFile);
        BufferedInputStream bins=new BufferedInputStream(is);//放到缓冲流里面
        OutputStream outs=response.getOutputStream();//获取文件输出IO流
        BufferedOutputStream bouts=new BufferedOutputStream(outs);
        response.setContentType("application/x-download");//设置response内容的类型 普通下载类型
        response.setContentLength((int)excelFile.length());//设置文件大小
        response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileInfo.getFileName()+".xlsx", "UTF-8"));//设置头部信息
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

    private void setData(Sheet sheet, int rowIndex, ExcelSheet excelSheet, List dataList){

        List<Field> fieldList = this.getFields(excelSheet.getSheetClass());
        if(fieldList == null){
            return;
        }
        for(Object t:dataList){
            Row headerRow = sheet.createRow(rowIndex);
            int columnIndex = 0;

            for (ExcelColumnProperty columnProperty : excelSheet.getRow().getColumns()) {
                if (columnProperty.getColumnNum() > 1 || columnProperty.getRowNum() > 1) {
                    //所占用的行和列大于1，则合并单元格
                    CellRangeAddress region = new CellRangeAddress(rowIndex, rowIndex + columnProperty.getRowNum() - 1, columnIndex, columnIndex + columnProperty.getColumnNum() - 1);// 下标从0开始 起始行号，终止行号， 起始列号，终止
                    sheet.addMergedRegion(region);
                }
                Cell cell = headerRow.createCell(columnIndex);
                try {
                    cell.setCellValue(this.getFieldValue(columnProperty.getFieldName(),columnProperty.getFieldTypeFormat(),fieldList,t));
                } catch (IllegalAccessException e) {
                    log.error("导出获取值失败。"+e.getMessage());
                }
                columnIndex = columnIndex + columnProperty.getColumnNum();
            }
            rowIndex++;
        }
    }

    private String getFieldValue(String fieldName,String format,List<Field> fieldList,Object t) throws IllegalAccessException {
        for(Field field : fieldList){
            if(field.getName().equals(fieldName)){
                //打开私有访问
                field.setAccessible(true);

                if(field.getType() == String.class){
                    return (String) field.get(t);
                }else if (field.getType() == Integer.class){
                    return (String) field.get(t);
                }else if (field.getType() == Date.class){
                    Date date = (Date) field.get(t);
                    if(date == null){
                        return null;
                    }
                    if(StringUtils.isBlank(format)){
                        return null;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    return sdf.format(date);
                }else if (field.getType() == Long.class){
                    return (String) field.get(t);
                }else if(field.getType() == BigDecimal.class){
                    return ( field.get(t)).toString();
                }else if(field.getType() == Boolean.class){
                    return (String) field.get(t);
                }else{
                    log.warn("非常见数据类型，获取值失败");
                }

                return null;
            }
        }
        return null;
    }

    /**
    * @Description 获取所有字段信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 16:11 2019/6/25
    * @return
    **/
    private List<Field> getFields(String classFullPath){
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = null;
        try {
            tempClass = Class.forName(classFullPath);
        } catch (ClassNotFoundException e) {
            log.error("导出类路径没有找到。");
            return null;
        }
        while (tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object")) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }

    /**
     * @Description 设置sheet的表头
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:50 2019/6/25
     * @param  rowIndex 这一行的索引
     * @param sheet sheet
     * @param excelSheet 导出的sheet的配置信息，从xml读取
     * @return 下一行的索引
     **/
    private int setHeader(int rowIndex, Sheet sheet, ExcelSheet excelSheet, CellStyle cellStyleHeader) {

        for (ExcelHeader excelHeader : excelSheet.getHeaders()) {
            Row headerRow = sheet.createRow(rowIndex);
            int columnIndex = 0;
            for (ExcelHeaderColumn headerColumn : excelHeader.getColumns()) {
                if(headerColumn.getColumnIndex()!=null){
                    columnIndex = headerColumn.getColumnIndex();
                }
                if (headerColumn.getColumnNum() > 1 || headerColumn.getRowNum() > 1) {
                    //所占用的行和列大于1，则合并单元格
                    int endColumn = columnIndex + headerColumn.getColumnNum() - 1;
                    int endRow = rowIndex + headerColumn.getRowNum() - 1;
                    CellRangeAddress region = new CellRangeAddress(rowIndex, endRow, columnIndex, endColumn);// 下标从0开始 起始行号，终止行号， 起始列号，终止
                    sheet.addMergedRegion(region);
                }
                Cell cell = headerRow.createCell(columnIndex);
                cell.setCellStyle(cellStyleHeader);
                cell.setCellValue(headerColumn.getHeaderName());
                columnIndex = columnIndex + headerColumn.getColumnNum();
            }
            rowIndex++;
        }
        return rowIndex;
    }

    /**
    * @Description 获取导出文件的配置信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:48 2019/6/25
    * @param exportType
    * @return
    **/
    public ExcelFile getFileInfo(String exportType){
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(ExcelExportUtils.class.getResourceAsStream("/exportTemplate.xml"));
        } catch (DocumentException e) {
            log.error(e.getMessage());
            return null;
        }
        Node file = document.selectSingleNode("/files/file[@name='"+exportType+"']");

        if(file == null ){
            return null;
        }

        ExcelFile excelFile = new ExcelFile();
        excelFile.setFileName(file.valueOf("@fileName"));

        List<Node> fileSheets = file.selectNodes("sheet");

        List<ExcelSheet> excelSheets = excelFile.getSheets();

        for(Node fileSheet : fileSheets) {
            String sheetId = fileSheet.valueOf("@id");
            Node sheet = document.selectSingleNode("/files/sheets/sheet[@id='"+sheetId+"']");
            ExcelSheet sheetEntity = new ExcelSheet();
            excelSheets.add(sheetEntity);
            //获取每个sheet的属性
            sheetEntity.setSheetClass(sheet.valueOf("@id"));
            sheetEntity.setSheetName(sheet.valueOf("@name"));
            List<Node> headers = sheet.selectNodes("headers/header");
            if(headers == null || headers.isEmpty()){
                continue;
            }
            sheetEntity.setHeaders(getHeaders(headers));
            sheetEntity.setRow(getColumns(sheet));
        }
        return excelFile;
    }

    /**
    * @Description 获取列的属性信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:40 2019/6/25
    * @param sheet
    * @return
    **/
    private ExcelRow getColumns(Node sheet){

        ExcelRow excelRow = new ExcelRow();
        List<Node> columns = sheet.selectNodes("columns/column");
        for(Node column :columns){
            ExcelColumnProperty columnProperty = new ExcelColumnProperty();
            excelRow.getColumns().add(columnProperty);
            Node classField = column.selectSingleNode("porperty[@name='fieldName']");
            if(classField != null){
                columnProperty.setFieldName(classField.valueOf("@value"));
            }
            Node fieldType = column.selectSingleNode("porperty[@name='fieldType']");
            if(fieldType != null){
                columnProperty.setFieldType(fieldType.valueOf("@value"));
                columnProperty.setFieldTypeFormat(fieldType.valueOf("@formate"));
            }

            Node columnNum = column.selectSingleNode("porperty[@name='columnNum']");
            if(columnNum != null){
                columnProperty.setColumnNum(columnNum.numberValueOf("@value").intValue());
            }
            Node rowNum = column.selectSingleNode("porperty[@name='rowNum']");
            if(rowNum != null){
                columnProperty.setRowNum(rowNum.numberValueOf("@value").intValue());
            }
        }

        return excelRow;
    }


    /**
    * @Description 获取excel的表头信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:20 2019/6/25
    * @param headers
    * @return
    **/
    private List<ExcelHeader> getHeaders(List<Node> headers){
        List<ExcelHeader> headerList = new ArrayList<>();
        for(Node header:headers){
            ExcelHeader headerEntity = new ExcelHeader();
            headerList.add(headerEntity);
            List<Node> columns = header.selectNodes("column");
            for(Node column :columns){
                ExcelHeaderColumn columnEntity = new ExcelHeaderColumn();
                headerEntity.getColumns().add(columnEntity);
                Node headerName = column.selectSingleNode("porperty[@name='headerName']");
                if(headerName != null){
                    columnEntity.setHeaderName(headerName.valueOf("@value"));
                }
                Node columnNum = column.selectSingleNode("porperty[@name='columnNum']");
                if(columnNum != null){
                    columnEntity.setColumnNum(columnNum.numberValueOf("@value").intValue());
                }
                Node rowNum = column.selectSingleNode("porperty[@name='rowNum']");
                if(rowNum != null){
                    columnEntity.setRowNum(rowNum.numberValueOf("@value").intValue());
                }
                Node columnIndex = column.selectSingleNode("porperty[@name='columnIndex']");
                if(columnIndex != null){
                    columnEntity.setColumnIndex(columnIndex.numberValueOf("@value").intValue());
                }
            }
        }
        return headerList;
    }

}
