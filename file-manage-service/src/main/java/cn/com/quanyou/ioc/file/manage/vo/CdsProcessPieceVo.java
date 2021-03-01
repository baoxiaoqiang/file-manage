package cn.com.quanyou.ioc.file.manage.vo;

import lombok.Data;

/**
 * @Description //cds工序信息
 * @Author yangli
 * @Date 2019/10/15-15:34
 **/
@Data
public class CdsProcessPieceVo {
    /** 工序名*/
    private String processName;

    /** 工厂名*/
    private String factoryName;

    /** 生产线名*/
    private String lineName;
}
