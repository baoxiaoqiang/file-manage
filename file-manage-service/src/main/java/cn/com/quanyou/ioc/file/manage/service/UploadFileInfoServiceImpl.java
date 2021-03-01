package cn.com.quanyou.ioc.file.manage.service;

import cn.com.quanyou.ioc.file.manage.common.enums.ValidStatusEnum;
import cn.com.quanyou.ioc.file.manage.dao.IUploadFileInfoDao;
import cn.com.quanyou.ioc.file.manage.facade.IUploadFileInfoService;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: UploadFileInfoServiceImpl
 * @date 2019/6/416:07
 * @projectName file-manage
 * @description:
 */
@Service
@Transactional(readOnly = true)
public class UploadFileInfoServiceImpl implements IUploadFileInfoService {

    @Autowired
    private IUploadFileInfoDao uploadFileInfoDao;

    @Override
    public UploadFileInfoBean add(UploadFileInfoBean entity) {

        UploadFileInfoBean oldFile = uploadFileInfoDao.queryEntityByFileHashCode(entity.getFileHashCode());
        if(oldFile != null){
            //该文件已经上传
            if(ValidStatusEnum.success.getCode().equals(oldFile.getValidStatus())){
                //如果记录状态有效，则直接返回文件信息
                return oldFile;
            }
            //记录状态无效，则更新问有效状态
            oldFile.setValidStatus(ValidStatusEnum.success.getCode());
            uploadFileInfoDao.updateByPrimaryKey(entity);
            return oldFile;
        }

        uploadFileInfoDao.add(entity);
        return entity;
    }

    @Override
    public UploadFileInfoBean getEntityByDataId(String dataId) {
        if(StringUtils.isBlank(dataId)){
            //主键ID为空，则返回空
            return null;
        }
        return uploadFileInfoDao.queryEntityByPrimaryKey(dataId);
    }

    @Override
    public UploadFileInfoBean getEntityByHashCode(String hashCode) {
        if(StringUtils.isBlank(hashCode)){
            return null;
        }
        return uploadFileInfoDao.queryEntityByFileHashCode(hashCode);
    }
}
