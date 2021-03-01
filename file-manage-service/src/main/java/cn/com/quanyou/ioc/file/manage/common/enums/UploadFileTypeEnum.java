package cn.com.quanyou.ioc.file.manage.common.enums;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: UploadFileTypeEnum
 * @date 2019/6/109:38
 * @projectName file-manage
 * @description: 上传文件类型枚举
 */
public enum UploadFileTypeEnum {

    common(1,"通用文件"),

    importExcelTemplate(2,"导入的Excel模板"),

    ;

    /**
     * @Description  文件类型
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:41 2019/6/10
     **/
    private Integer fileType;

    /**
     * @Description 说明
     * @Author heshiyi@quanyou.com.cn
     * @Date 9:41 2019/6/10
     **/
    private String description;


    private UploadFileTypeEnum(Integer fileType,String description){
        this.fileType = fileType;
        this.description = description;
    }

    public Integer getFileType() {
        return fileType;
    }

    public String getDescription() {
        return description;
    }}
