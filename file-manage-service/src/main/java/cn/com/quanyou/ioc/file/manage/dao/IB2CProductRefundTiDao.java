package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.B2CProductRefundTi;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.IocemallProductDim;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author bxq
 * @desc
 * @date 2019/11/25 13:35
 */
@Mapper
public interface IB2CProductRefundTiDao extends ImportBasicMapper<B2CProductRefundTi> {

    List<IocemallProductDim> getStoreDimList();

    List<IocemallProductDim> getOrgDimList();
}
