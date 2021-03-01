package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.CdsEmployeeInfoVo;
import cn.com.quanyou.ioc.file.manage.vo.CdsProcessPieceVo;
import cn.com.quanyou.ioc.file.manage.vo.ExcelImportEntities.CdsProcessPieceTi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @Description //新上架拍摄时间上报接口表Mapper
 * @Author bxq
 * @Date 2019/10/10
 **/
@Component
public interface ICdsProcessPieceTiDao extends ImportBasicMapper<CdsProcessPieceTi> {

    /**
     * @param factoryCode
     * @param lineCode
     * @Description: 获取工序名称列表
     * @Return: 工序名称列表
     * @Author: yangli
     * @Date: 2019/10/15-15:21
     **/
    List<CdsProcessPieceVo> listProcessNameList(@Param("factoryCode") String factoryCode, @Param("lineCode") String lineCode);

    /**
     * @Description: 获取员工信息列表
     * @Return: 员工信息列表
     * @Author: yangli
     * @Date: 2019/10/15-15:56
     **/
    List<CdsEmployeeInfoVo> listEmployeeInfo();
}
