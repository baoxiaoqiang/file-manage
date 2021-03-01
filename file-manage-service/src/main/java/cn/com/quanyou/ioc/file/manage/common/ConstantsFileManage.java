package cn.com.quanyou.ioc.file.manage.common;

import cn.com.quanyou.ioc.file.manage.common.utils.StringUtil;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: Constants
 * @date 2019/6/12 9:09
 * @projectName file-manage
 * @description:
 */
public class ConstantsFileManage{

    /**
     * @Description excel导入中，每批次导入的行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:14 2019/6/12
     **/
    public static final Integer IMPORT_BATCH_ROW_NUMBER=1000;

    /**
     * @Description 默认页数量
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:24 2019/6/14
     **/
    public static final Integer PAGE_SIZE_DEFAULT=10;

    /**
     * @Description 默认页数
     * @Author heshiyi@quanyou.com.cn
     * @Date 16:26 2019/6/14
     **/
    public static final Integer PAGE_NUM_DEFAULT=1;

    /**
     * @Description 行数的列名
     * @Author heshiyi@quanyou.com.cn
     * @Date 22:31 2019/6/17
     **/
    public static final String ROW_NUMBER_COLUMN="ROW_NUMBER";

    /**
     * @Description 行数的属性名
     * @Author heshiyi@quanyou.com.cn
     * @Date 22:32 2019/6/17
     **/
    public static final String ROW_NUMBER_FIELD= StringUtil.camelCaseName(ROW_NUMBER_COLUMN);

    /**
     * @Description  主键ID名称
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:42 2019/6/18
     **/
    public static final String PRIMARY_KEY="DATA_ID";

    /**
     * @Description 验证状态字段
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:10 2019/6/18
     **/
    public static final String VALID_STATUS_COLUMN="VALID_STATUS";

    /**
     * @Description 登录用户，header中的KEY值
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:49 2019/6/20
     **/
    public static final String LOGIN_USER_HEADER_KEY="gw_userName";


    /**
     * @Description: 店铺名称
     * @Author: yangli
     * @Date: 2019/9/25-10:19
    **/
    public static final String SHOP_NAME_KEY = "SHOP_NAME";

}
