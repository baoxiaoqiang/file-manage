package cn.com.quanyou.ioc.file.manage.vo;

import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: ImportParamVo
 * @date 2019/6/14 13:12
 * @projectName file-manage
 * @description:
 */
@Data
public class ImportParamVo implements Serializable {

    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * @Description 导入类型
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:13 2019/6/14
     **/
    private String importType;

    /**
     * @Description 时间
     * @Author heshiyi@quanyou.com.cn
     * @Date 13:13 2019/6/14
     **/
    private Date date;

    /**
     * @Description 导入类型详情
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:45 2019/6/21
     **/
    private List<String> importTypeSubList;

    /**
     * @Description 工厂code
     * @Author bxq
     * @Date 15:45 2019/10/215
     */
    private Integer factoryCode;

    /**
     * @Description 生产线code
     * @Author bxq
     * @Date 15:45 2019/10/215
     */
    private Integer lineCode;

    /**
     * @Description 工厂名
     * @Author bxq
     * @Date 15:45 2019/10/15
     */
    private String factoryName;

    /**
     * @Description 生产线名
     * @Author bxq
     * @Date 15:45 2019/10/15
     */
    private String lineName;

    public List<String> getImportTypeSubList() {
        List<String> subList = new ArrayList<>();
        for(ImportFileTypeEnum type : ImportFileTypeEnum.values()){
            if(type.getGroup().equals(importType)){
                subList.add(type.getType());
            }
        }
        return subList;
    }

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 入口：用于指定查询方式
     * 当entrance为cds时
     */
    private String entrance;
}
