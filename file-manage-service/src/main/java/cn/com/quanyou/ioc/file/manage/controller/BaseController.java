package cn.com.quanyou.ioc.file.manage.controller;

import cn.com.quanyou.ioc.file.manage.common.ConstantsFileManage;
import cn.com.quanyou.ioc.file.manage.common.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: BaseController
 * @date 2019/6/14 17:11
 * @projectName file-manage
 * @description:
 */
@Controller
public class BaseController{
    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                setValue(DateUtil.parseDate(value));
            }
        });
    }

    /***
    * @Description 获取当前登录用户名
    * @Author heshiyi@quanyou.com.cn
    * @Date 9:52 2019/6/20
    * @param request
    * @return
    **/
    protected String getUserName(HttpServletRequest request){
        return  request.getHeader(ConstantsFileManage.LOGIN_USER_HEADER_KEY);// == null? "sys":request.getHeader(ConstantsFileManage.LOGIN_USER_HEADER_KEY);
    }
}
