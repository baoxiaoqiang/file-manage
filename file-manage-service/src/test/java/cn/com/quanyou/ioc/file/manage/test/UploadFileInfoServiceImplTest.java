package cn.com.quanyou.ioc.file.manage.test;

import cn.com.quanyou.ioc.file.manage.facade.IUploadFileInfoService;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


/**
 * @author heshiyi@quanyou.com.cn
 * @title: UploadFileInfoServiceImplTest
 * @date 2019/6/416:13
 * @projectName file-manage
 * @description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadFileInfoServiceImplTest {

    private final static Logger logger = LoggerFactory.getLogger(UploadFileInfoServiceImplTest.class);
    @Autowired
    private IUploadFileInfoService uploadFileInfoService;

    @Test
    public void add() {
        UploadFileInfoBean entity = new UploadFileInfoBean();
        entity.setCreatedBy("heshiyi");
        entity.setFileHashCode((new Date()).toString());
        entity.setFileName("test.docx");
        entity.setFilePath("filePath");
        entity.setFileSize(123123L);
        entity.setLastUpdatedBy("heshiyi");
        entity.setLastUpdateDate(new Date());
        entity = uploadFileInfoService.add(entity);

        UploadFileInfoBean newEntity = uploadFileInfoService.getEntityByDataId(entity.getDataId());
        logger.info(newEntity.getCreationDate().toString());

    }
}