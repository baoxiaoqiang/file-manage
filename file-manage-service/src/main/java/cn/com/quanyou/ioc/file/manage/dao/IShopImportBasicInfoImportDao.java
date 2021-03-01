package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.ShopBasicInfoBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IOperationalDataImportDao
 * @date 2019/6/14 10:37
 * @projectName file-manage
 * @description: 总评数据
 */
@Component
public interface IShopImportBasicInfoImportDao extends ImportBasicMapper<ShopBasicInfoBean> {

    List<ShopBasicInfoBean> queryAllFormal();
}
