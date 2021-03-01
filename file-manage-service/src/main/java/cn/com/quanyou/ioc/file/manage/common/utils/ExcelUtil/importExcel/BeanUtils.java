package cn.com.quanyou.ioc.file.manage.common.utils.ExcelUtil.importExcel;

import cn.com.quanyou.ioc.file.manage.common.ConstantsFileManage;
import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.annotation.ExcelModelAnnotation;
import cn.com.quanyou.ioc.file.manage.common.utils.DateUtil;
import cn.com.quanyou.ioc.file.manage.common.utils.StringUtil;
import cn.com.quanyou.ioc.file.manage.vo.AnalysisTaskErrorBean;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: BeanUtils
 * @date 2019/6/17 21:54
 * @projectName file-manage
 * @description:
 */
@Slf4j
public class BeanUtils<T> {


    private Class<T> clazz;

    public BeanUtils(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * @param map map数据
     * @return
     * @Description 通过MAP，获取实体信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:58 2019/6/19
     **/
    public T getFromMap(Map<String, Object> map) {
        T t = this.get();
        Field[] fields = this.getAllFields();
        for (Field field : fields) {
//            String fieldName = field.getName();
//            String camelCaseFieldName= StringUtil.camelCaseName(fieldName);
//            if(ConstantsFileManage.PRIMARY_KEY.equals(camelCaseFieldName)){
//                //主键Key
//                continue;
//            }

            String celValueStr = (String) map.get(StringUtil.underscoreName(field.getName()).toUpperCase());
            if (celValueStr == null) {
                continue;
            }
            field.setAccessible(true);
            try {
                if (field.getType() == String.class) {
                    field.set(t, celValueStr);
                } else if (field.getType() == Integer.class) {
                    celValueStr = celValueStr.replaceAll(",", "");//去掉都好，避免excel中，数字格式以逗号分隔
                    Integer intValue = Integer.valueOf(celValueStr);
                    field.set(t, intValue);
                } else if (field.getType() == Date.class) {
                    Date dateValue = DateUtil.parseDate(celValueStr);
                    field.set(t, dateValue);
                } else if (field.getType() == Long.class) {
                    celValueStr = celValueStr.replaceAll(",", "");//去掉都好，避免excel中，数字格式以逗号分隔
                    Long longValue = Long.valueOf(celValueStr);
                    field.set(t, longValue);
                } else if (field.getType() == BigDecimal.class) {
                    celValueStr = celValueStr.replaceAll(",", "");//去掉都好，避免excel中，数字格式以逗号分隔
                    BigDecimal value = new BigDecimal(celValueStr);
                    field.set(t, value);
                } else if (field.getType() == Boolean.class) {
                    Boolean value = Boolean.valueOf(celValueStr);
                    field.set(t, value);
                } else {
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }

        return t;
    }

    /**
     * @param map
     * @param taskId 任务ID
     * @return
     * @Description 验证数据
     * @Author heshiyi@quanyou.com.cn
     * @Date 23:33 2019/6/17
     **/
    public ResultInfo<List<AnalysisTaskErrorBean>, Boolean> checkData(Map<String, Object> map, String taskId) {

        T t = this.get();

        ResultInfo<List<AnalysisTaskErrorBean>, Boolean> resultInfo = new ResultInfo<>();

        List<AnalysisTaskErrorBean> errorList = new ArrayList<>();
        resultInfo.setData(errorList);

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            ExcelModelAnnotation.FieldAnnotation fieldAnnotation = field.getAnnotation(ExcelModelAnnotation.FieldAnnotation.class);
            if (fieldAnnotation == null) {
                continue;
            }
            String celValueStr = (String) map.get(StringUtil.underscoreName(field.getName()).toUpperCase());
            Integer rowNumber = Integer.valueOf((String) map.get(ConstantsFileManage.ROW_NUMBER_COLUMN));
            String headerName = fieldAnnotation.headerName();
            if (StringUtils.isBlank(headerName)) {
                continue;
            }
            if (StringUtils.isBlank(celValueStr)) {
                //数据为空
                if (!fieldAnnotation.nullAble()) {
                    //不允许为空
                    this.setEmptyError(rowNumber, fieldAnnotation.headerName(), errorList, taskId);
                }
                continue;
            }
            //不为空，

            if (field.getType() == String.class) {
                this.setStringValue(fieldAnnotation, celValueStr, errorList, taskId, fieldAnnotation.headerName(), rowNumber);
            } else if (field.getType() == Integer.class) {
                celValueStr = celValueStr.replaceAll(",", "");//去掉都好，避免excel中，数字格式以逗号分隔
                this.setIntegerValue(fieldAnnotation, celValueStr, errorList, taskId, headerName, rowNumber);
            } else if (field.getType() == Date.class) {
                this.setDateValue(celValueStr, errorList, taskId, headerName, rowNumber);
            } else if (field.getType() == Long.class) {
                celValueStr = celValueStr.replaceAll(",", "");//去掉都好，避免excel中，数字格式以逗号分隔
                this.setLongValue(fieldAnnotation, celValueStr, errorList, taskId, headerName, rowNumber);
            } else if (field.getType() == BigDecimal.class) {
                celValueStr = celValueStr.replaceAll(",", "");//去掉都好，避免excel中，数字格式以逗号分隔
                this.setBigDecimalValue(fieldAnnotation, celValueStr, errorList, taskId, headerName, rowNumber);
            } else if (field.getType() == Boolean.class) {

            } else {
                this.setErrorInfo(taskId, rowNumber, headerName, "内部错误：属性类型不支持", errorList);
            }
        }

        boolean result;
        if (errorList.size() == 0) {
            result = true;
        } else {
            result = false;
        }
        resultInfo.setMessage(result);

        return resultInfo;
    }

    /**
     * @param rowNumber  行数
     * @param headerName 表头名称
     * @param errorList  错误列表
     * @param taskId     任务ID
     * @return
     * @Description 设置不能为空的错误信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 22:37 2019/6/17
     **/
    private void setEmptyError(Integer rowNumber, String headerName, List<AnalysisTaskErrorBean> errorList, String taskId) {
        AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
        error.setTaskId(taskId);
        error.setRow(rowNumber);
        error.setCellName(headerName);
        error.setErrorInfo("不能为空");
        errorList.add(error);
    }


    /**
     * @param
     * @return
     * @Description 创建一个新的对象
     * @Author heshiyi@quanyou.com.cn
     * @Date 22:09 2019/6/17
     **/
    private T get() {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param
     * @return
     * @Description 获取所有的属性
     * @Author heshiyi@quanyou.com.cn
     * @Date 22:10 2019/6/17
     **/
    private Field[] getFields() {
        T t = this.get();
        return t.getClass().getDeclaredFields();
    }

    private Field[] getAllFields() {
        List<Field> fieldList = new ArrayList<>();
        Class clazzA = clazz;
        while (clazzA != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazzA.getDeclaredFields())));
            clazzA = clazzA.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }


    private void setBigDecimalValue(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, String celValue, List<AnalysisTaskErrorBean> errorList, String taskId, String headerName, Integer rowNumber) {
        BigDecimal value = this.getBigDecimal(celValue, errorList, taskId, headerName, rowNumber);
        this.checkBigDecimal(fieldAnnotation, value, errorList, taskId, headerName, rowNumber);
    }

    private void setLongValue(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, String celValue, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        Long value = this.getLong(celValue, errorList, taskId, healderName, rowNumber);
    }

    private void setDateValue(String celValueStr, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        Date date = DateUtil.parseDateImport(celValueStr);
        if (date != null) {

        } else {
            this.setErrorInfo(taskId, rowNumber, healderName, "数据类型有误，非日期类型", errorList);
        }
    }

    private void setIntegerValue(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, String value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        Integer intValue = this.getInteger(value, errorList, taskId, healderName, rowNumber);
        this.checkInteger(fieldAnnotation, intValue, errorList, taskId, healderName, rowNumber);
    }

    private void checkBigDecimal(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, BigDecimal value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        /* 20191004 新增value非空判断，解决该字段为空时的空指针异常*/
        if (value == null) {
            return;
        }

        this.checkMinBigDecimal(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);
        this.checkMaxBigDecimal(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);
    }

    private void checkMaxBigDecimal(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, BigDecimal value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        long maxBig = fieldAnnotation.maxBig();
        if (maxBig != Long.MAX_VALUE) {
            BigDecimal max = new BigDecimal(maxBig);
            if (max.compareTo(value) == -1) {
                this.setErrorInfo(taskId, rowNumber, healderName, "不能大于" + maxBig, errorList);
            }
        }
    }

    private void checkMinBigDecimal(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, BigDecimal value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        long minBig = fieldAnnotation.minBig();
        if (minBig != Long.MIN_VALUE) {
            BigDecimal min = new BigDecimal(minBig);
            if ( min.compareTo(value) == 1) {
                this.setErrorInfo(taskId, rowNumber, healderName, "不能小于" + minBig, errorList);
            }
        }
    }

    private void checkInteger(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, Integer value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        if (value == null) {
            return;
        }
        this.checkIntegerRang(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);

        this.checkIntegerMin(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);
        this.checkIntegerMax(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);
    }

    private void checkIntegerMin(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, Integer value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {

        int minInt = fieldAnnotation.intMin();
        if (minInt == Integer.MIN_VALUE) {
            return;
        }
        if (value < minInt) {
            this.setErrorInfo(taskId, rowNumber, healderName, "不能小于：" + minInt, errorList);
        }

    }

    private void checkIntegerMax(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, Integer value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {

        int maxInt = fieldAnnotation.intMax();
        if (maxInt == Integer.MAX_VALUE) {
            return;
        }
        if (value > maxInt) {
            this.setErrorInfo(taskId, rowNumber, healderName, "不能大于：" + maxInt, errorList);
        }
    }

    private void checkStringRang(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, String value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        String[] strValue = fieldAnnotation.strRang();
        if (strValue.length == 0) {
            return;
        }
        boolean isCorrectValue = false;

        for (String str : strValue) {
            if (str.equals(value)) {
                isCorrectValue = true;
            }
        }
        if (!isCorrectValue) {
            this.setErrorInfo(taskId, rowNumber, healderName, "只能使用：" + JSON.toJSONString(strValue), errorList);
        }
    }

    private void checkIntegerRang(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, Integer value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {

        int[] ints = fieldAnnotation.ints();
        if (ints.length == 0) {
            return;
        }
        boolean isCorrectValue = false;

        for (int num : ints) {
            if (num == value) {
                isCorrectValue = true;
            }
        }
        if (!isCorrectValue) {
            this.setErrorInfo(taskId, rowNumber, healderName, "只能使用：" + ints.toString(), errorList);
        }
    }

    private void setErrorInfo(String taskId, Integer rowIndex, String headerName, String errorMessage, List<AnalysisTaskErrorBean> errorList) {
        AnalysisTaskErrorBean error = new AnalysisTaskErrorBean();
        error.setTaskId(taskId);
        this.setErrorNormalInfo(rowIndex, headerName, error);
        error.setErrorInfo(errorMessage);
        errorList.add(error);
    }

    private void setErrorNormalInfo(Integer rowNum, String header, AnalysisTaskErrorBean error) {
        error.setRow(rowNum);
        error.setCellName(header);
    }

    private Long getLong(String value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        try {
            if (value.contains("%")) {
                this.setErrorInfo(taskId, rowNumber, healderName, "非数字类型错误,请去除%", errorList);
                return null;
            }

            return Long.valueOf(value);
        } catch (Exception e) {
            this.setErrorInfo(taskId, rowNumber, healderName, "数据类型错误", errorList);
            return null;
        }
    }

    private Integer getInteger(String value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        try {
            if (value.contains("%")) {
                this.setErrorInfo(taskId, rowNumber, healderName, "非数字类型错误,请去除%", errorList);
                return null;
            }

            return Integer.valueOf(value);
        } catch (Exception e) {
            this.setErrorInfo(taskId, rowNumber, healderName, "非整数类型错误", errorList);
            return null;
        }
    }

    private BigDecimal getBigDecimal(String celValue, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        try {
            if (celValue.contains("%")) {
                this.setErrorInfo(taskId, rowNumber, healderName, "非数字类型错误,请去除%", errorList);
                return null;
            }
            return new BigDecimal(celValue);
        } catch (Exception e) {
            this.setErrorInfo(taskId, rowNumber, healderName, "非数字类型错误", errorList);
            return null;
        }
    }

    private void setStringValue(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, String value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        this.strCheck(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);
    }

    private void strCheck(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, String value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        if (!fieldAnnotation.nullAble() && value == null || StringUtils.isBlank(value)) {
            this.setEmptyError(rowNumber, healderName, errorList, taskId);
        } else {
            //字符串长度限制
            this.strLengthCheck(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);
            this.checkStringRang(fieldAnnotation, value, errorList, taskId, healderName, rowNumber);
        }
    }

    private void strLengthCheck(ExcelModelAnnotation.FieldAnnotation fieldAnnotation, String value, List<AnalysisTaskErrorBean> errorList, String taskId, String healderName, Integer rowNumber) {
        if (fieldAnnotation.maxLength() != 0 && value.length() > fieldAnnotation.maxLength()) {
            this.setErrorInfo(taskId, rowNumber, healderName, "字符数量超过限制:" + fieldAnnotation.maxLength(), errorList);
        }
    }

}
