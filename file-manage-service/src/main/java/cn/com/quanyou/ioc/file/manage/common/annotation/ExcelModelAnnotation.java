package cn.com.quanyou.ioc.file.manage.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ExcelModelAnnotation
 * @date 2019/6/1014:03
 * @projectName file-manage
 * @description: excelModel的注解
 */
public class ExcelModelAnnotation {

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FieldAnnotation {

        /**
         * @Description excel中。列的索引，默认为-1，不解析此属性，不在excel中
         * @Author heshiyi@quanyou.com.cn
         * @Date 14:07 2019/6/10
         **/
        int excelCellIndex() default -1;

        /**
         * @Description 是否允许为空
         * @Author heshiyi@quanyou.com.cn
         * @Date 10:58 2019/6/13
         **/
        boolean nullAble() default false;

        /**
         * @Description 表头名称
         * @Author heshiyi@quanyou.com.cn
         * @Date 13:30 2019/6/13
         **/
        String headerName() default "";

        /**
         * @Description 最大长度
         * @Author heshiyi@quanyou.com.cn
         * @Date 16:20 2019/6/13
         **/
        int maxLength() default 0;

        /**
         * @Description 只能包含的字符串
         * @Author heshiyi@quanyou.com.cn
         * @Date 16:19 2019/6/13
         **/
        String[] strRang() default {};

        /**
         * @Description  只能包含的整数数值
         * @Author heshiyi@quanyou.com.cn
         * @Date 16:24 2019/6/13
         **/
        int[] ints() default {};

        /**
         * @Description int最小值
         * @Author heshiyi@quanyou.com.cn
         * @Date 21:50 2019/6/13
         **/
        int intMin() default 0;

        /**
         * @Description max最大值
         * @Author heshiyi@quanyou.com.cn
         * @Date 21:51 2019/6/13
         **/
        int intMax() default Integer.MAX_VALUE;

        /**
         * @Description 大数据的最小值
         * @Author heshiyi@quanyou.com.cn
         * @Date 22:10 2019/6/13
         **/
        long minBig() default Long.MIN_VALUE;

        /**
         * @Description 大数据的最大值
         * @Author heshiyi@quanyou.com.cn
         * @Date 22:29 2019/6/13
         **/
        long maxBig() default Long.MAX_VALUE;
    }

}
