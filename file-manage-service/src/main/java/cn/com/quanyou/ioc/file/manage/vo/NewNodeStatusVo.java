package cn.com.quanyou.ioc.file.manage.vo;

import lombok.Data;

/**
 * @author bxq
 * @desc
 * @date 2019/12/18 15:30
 */
@Data
public class NewNodeStatusVo {
    /**
     * 节点状态:1未开始，2进行中，3已完成,4异常
     */
    private Integer taskStatus;
    /**
     * 类型
     */
    private String newType;
    /**
     * spu编码
     */
    private String spuCode;
    /**
     * 新品批次号
     */
    private String batchCode;

    /**
     * 拍摄批次号
     */
    private String photoBatchCode;

}
