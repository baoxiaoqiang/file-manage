package cn.com.quanyou.ioc.file.manage.facade;

import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IUploadFileInfoService
 * @date 2019/6/416:04
 * @projectName file-manage
 * @description: 附件上传接口
 */


public interface IUploadFileInfoService {

    /**
     * @Description  添加附件
     * @Date 16:05 2019/6/4
     * @Param [entity] 附件信息
     * @return cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    UploadFileInfoBean add(UploadFileInfoBean entity);

    /**
     * @Description 更具附件信息的主键ID，获取附件的信息
     * @Date 10:36 2019/6/5
     * @Param [dataId] 如果为空，则返回null
     * @return 附件信息
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    UploadFileInfoBean getEntityByDataId(String dataId);

    /**
    * @Description 根据hashCode，获取文件信息
    * @Author heshiyi@quanyou.com.cn
    * @Date 14:31 2019/6/11
    * @param hashCode 文件的hashCode
    * @return
    **/
    UploadFileInfoBean getEntityByHashCode(String hashCode);
}
