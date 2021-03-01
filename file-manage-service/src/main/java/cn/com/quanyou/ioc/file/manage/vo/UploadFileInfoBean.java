package cn.com.quanyou.ioc.file.manage.vo;

import lombok.Data;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: UploadFileInfoBean
 * @date 2019/6/413:57
 * @projectName file-manage
 * @description: 上传附件信息
 */
@Data
public class UploadFileInfoBean extends BasicBean {

    /**
     * @Description 上传的附件名称
     * @Date 14:19 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    private String fileName;

    /**
     * @Description fastdfs文件服务器的groupname
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:55 2019/6/6
     **/
    private String groupName;

    /**
     * @Description fastdfs文件服务器的remoteFileName
     * @Author heshiyi@quanyou.com.cn
     * @Date 10:55 2019/6/6
     **/
    private String remoteFileName;

    /**
 * @Description 文件类型，1，通用附加
 * @Date 14:20 2019/6/4
 *
 * @Author heshiyi@quanyou.com.cn
 **/
    private Integer fileType;

/**
 * @Description 附件上传后的路径。
 * @Date 14:21 2019/6/4
 *
 * @Author heshiyi@quanyou.com.cn
 **/
    private String filePath;

    /**
     * @Description 附件大小，字节
     * @Date 14:22 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    private Long fileSize;

    /**
     * @Description 附件的hashCode编码，上传附件之前检索比较用，如果已经存在，则不上传。直接返回文件信息
     * @Date 14:23 2019/6/4
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    private String fileHashCode;



}
