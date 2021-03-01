package cn.com.quanyou.ioc.file.manage.dao;


import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.PaySuccessRateBean;
import org.springframework.stereotype.Component;

/**
 * @author yhy
 * @title: 最终付款成功率
 * @date 2019/07/19
 */
@Component
public interface ISaleBeforePaySuccessRateImportDao  extends ImportBasicMapper<PaySuccessRateBean> {

}
