package cn.com.quanyou.ioc.file.manage.vo;

import cn.com.quanyou.ioc.file.manage.common.enums.ImportFileTypeEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ProcessResultEnum;
import cn.com.quanyou.ioc.file.manage.common.enums.ProcessStatusEnum;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: AnalysisTaskBean
 * @date 2019/6/12 9:45
 * @projectName file-manage
 * @description: 解析任务
 */
@Data
public class AnalysisTaskBean extends BasicBean{

    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * @Description 文件ID
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:50 2019/6/12
     **/
    private String fileDataId;

    /**
     * @Description 任务类型，见枚举 ImortFileTypeEnum.type;
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:26 2019/6/12
     **/
    private String taskType;


    /**
     * @Description 解析状态
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:50 2019/6/12
     **/
    private String processStatus;

    /**
     * @Description 解析状态说明,不存数据库
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:08 2019/6/24
     **/
    private String processStatusDescription;

    /**
     * @Description 已经解析的行数
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:50 2019/6/12
     **/
    private Integer excelRowAnalysis;

    /**
     * @Description 解析结果信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:50 2019/6/12
     **/
    private String processResult;

    /**
     * @Description 解析结果说明
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:10 2019/6/24
     **/
    private String processResultDescription;

    /**
     * @Description 导入用户
     * @Author heshiyi@quanyou.com.cn
     * @Date 17:24 2019/6/13
     **/
    private String importUser;

    /**
     * @Description 文件信息
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:22 2019/6/12
     **/
    private UploadFileInfoBean fileInfo;

    /**
     * @Description  上报日期
     * @Author heshiyi@quanyou.com.cn
     * @Date 15:54 2019/6/15
     **/
    private Date reportDate;

    /**
     * @Description 前台页面上显示的文件名称,包括的sheetName
     * @Author heshiyi@quanyou.com.cn
     * @Date 11:38 2019/6/24
     **/
    private String fileNameShow;

    /**
     * @Description 工厂code
     * @Author bxq
     * @Date 15:45 2019/10/15
     */
    private Integer factoryCode;

    /**
     * @Description 工厂名称
     * @Author bxq
     * @Date 15:45 2019/10/15
     */
    private String factoryName;

    /**
     * @Description 生产线code
     * @Author bxq
     * @Date 15:45 2019/10/15
     */
    private Integer lineCode;

    /**
     * @Description 生产线code
     * @Author bxq
     * @Date 15:45 2019/10/15
     */
    private String lineName;

    public String getFileNameShow() {
        if(this.fileInfo == null){
            return null;
        }
        if(ImportFileTypeEnum.groupHasMoreSub(this.getTaskType())){
            return this.fileInfo.getFileName()+"("+ImportFileTypeEnum.getByType(this.getTaskType()).getDescription()+")";
        }else{
            return this.getFileInfo().getFileName();
        }
    }

    public String getProcessStatusDescription() {
        ProcessStatusEnum statusEnum = ProcessStatusEnum.getByName(this.getProcessStatus());
        return statusEnum == null?null : statusEnum.getDescription();
    }

    public String getProcessResultDescription() {

        ProcessResultEnum resultEnum = ProcessResultEnum.getByName(this.getProcessResult());
        return resultEnum == null?null : resultEnum.getDescription();
    }
}
