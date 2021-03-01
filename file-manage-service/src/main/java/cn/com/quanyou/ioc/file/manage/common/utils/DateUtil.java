package cn.com.quanyou.ioc.file.manage.common.utils;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: DateUtil
 * @date 2019/6/11 16:32
 * @projectName file-manage
 * @description:
 */
public class DateUtil {

    private static final String [] parsePatterns = {"yyyy-MM","yyyy-MM-dd","yyyy-MM-dd HH:mm:ss","yyyyMMdd","yyyy/MM/dd HH:mm:ss","yyyy年MM月dd日 HH:mm:ss","yyyyMM","yyyy"};

    private static final String[] parsePatternsImport = {"yyyyMMdd","yyyyMM","yyyy"};

    /**
    * @Description 导入数据的日期格式转换
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:55 2019/6/22
    * @param dateStr
    * @return
    **/
    public static Date parseDateImport(String dateStr){
        try {
            return DateUtils.parseDate(dateStr,parsePatternsImport);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String dateStr){
        try {
            return DateUtils.parseDate(dateStr,parsePatterns);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

    /**
    * @Description 判断两个日期是否是同一个天
    * @Author heshiyi@quanyou.com.cn
    * @Date 10:35 2019/6/19
    * @param date1
    * @param date2
    * @return
    **/
    public static boolean isSameDayAndNotEmpty(Date date1, Date date2){
        if( date1 == null || date2 == null){
            return false;
        }
        return DateUtils.isSameDay(date1,date2);
    }

    public static boolean isSameMonthAndNotEmpty(Date date1, Date date2){
        if( date1 == null || date2 == null){
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MARCH) == cal2.get(Calendar.MARCH);
    }

    public static String dateFormat(String dateStr){
        Date date = parseDate(dateStr);
        if(date == null){
            return null;
        }
        return dateToString(date);
    }

    public static String dateToString(Date date){
        if( date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }



    public static void main(String[] args){

        System.out.println(dateToString(parseDate("2019")));
        System.out.println(dateToString(parseDate("20193")));
    }

}


