package cn.com.quanyou.ioc.file.manage.dao;

import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: IUploadFileInfoDao
 * @date 2019/6/414:33
 * @projectName file-manage
 * @description: 附件上传持久化接口
 */
@Component(value = "uploadFileInfoDao")
public interface IUploadFileInfoDao extends ImportBasicMapper<UploadFileInfoBean> {


    /**
     * @Description 添加上传附件
     * @Date 14:37 2019/6/4
     * @Param [entity] 附件信息
     * @return void
     *
     * @Author heshiyi@quanyou.com.cn
     **/
//    public int add(UploadFileInfoBean entity);

    /**
     * @Description  根据文件haseCode,获取文件的数量
     * @Date 17:39 2019/6/4
     * @Param [fileHashCode]
     * @return UploadFileInfoBean 附件信息
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    public UploadFileInfoBean queryEntityByFileHashCode(@Param("fileHashCode") String fileHashCode);

    /**
     * @Description 根据主键更新上传附件信息
     * @Date 9:15 2019/6/5
     * @Param [entity] 需要更新的信息，dataId不能为空。
     * @return int 成功更新的记录数量
     *
     * @Author heshiyi@quanyou.com.cn
     **/
//    public int updateByPrimaryKey(UploadFileInfoBean entity);

    /**
     * @Description 根据主键，获取上传附件信息
     * @Date 9:16 2019/6/5
     * @Param [entity]
     * @return cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean
     *
     * @Author heshiyi@quanyou.com.cn
     **/
//    public UploadFileInfoBean queryEntityByPrimaryKey(@Param("dataId") String dataId);

    /**
     * @Description 根据附件信息属性，获取附件列表
     * @Date 10:39 2019/6/5
     * @Param [entity]
     * @return java.util.List<cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean>
     *
     * @Author heshiyi@quanyou.com.cn
     **/
    public List<UploadFileInfoBean> queryListByEntity(UploadFileInfoBean entity);
}
