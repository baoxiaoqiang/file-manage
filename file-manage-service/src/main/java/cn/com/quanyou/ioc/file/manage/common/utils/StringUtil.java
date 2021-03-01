package cn.com.quanyou.ioc.file.manage.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: StringUtil
 * @date 2019/6/17 21:40
 * @projectName file-manage
 * @description:
 */
public class StringUtil {

    /**
    * @Description 驼峰转下划线
    * @Author heshiyi@quanyou.com.cn
    * @Date 21:41 2019/6/17
    * @param camelCaseName
    * @return
    **/
    public static String underscoreName(String camelCaseName) {
        if(StringUtils.isBlank(camelCaseName)){
            return null;
        }
        camelCaseName = camelCaseName.trim();
        StringBuilder result = new StringBuilder();
        result.append(camelCaseName.substring(0, 1).toLowerCase());
        for (int i = 1; i < camelCaseName.length(); i++) {
            char ch = camelCaseName.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append("_");
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    /**
    * @Description 下划线转驼峰
    * @Author heshiyi@quanyou.com.cn
    * @Date 21:41 2019/6/17
    * @param strUnderLine
    * @return
    **/
    public static String camelCaseName(String strUnderLine) {

        if(StringUtils.isBlank(strUnderLine)){
            return null;
        }
        strUnderLine = strUnderLine.trim().toLowerCase();

        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < strUnderLine.length(); i++) {
            char ch = strUnderLine.charAt(i);
            if ("_".charAt(0) == ch) {
                flag = true;
            } else {
                if (flag) {
                    result.append(Character.toUpperCase(ch));
                    flag = false;
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
}
