package cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils;

import cn.com.quanyou.ioc.file.manage.common.ResultInfo;
import cn.com.quanyou.ioc.file.manage.common.enums.ResultStatusEnum;
import cn.com.quanyou.ioc.file.manage.vo.UploadFileInfoBean;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: FileManager
 * @date 2019/6/69:21
 * @projectName file-manage
 * @description:
 */
public class FileManager implements Serializable {

    private static final long serialVersionUID = 1L;
    private static String domainName;

    static {
        try {
           /* Properties properties = new Properties();
            properties.load(FileManager.class.getResourceAsStream("/application.yml"));
            properties.put("fastdfs.tracker_servers",properties.getProperty("tracker-server"));*/

            YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean(); //可以加载多个yml文件
            yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
            Properties properties = yamlMapFactoryBean.getObject(); //获取yml里的参数
            properties.put("fastdfs.tracker_servers",properties.getProperty("fdfs.tracker-server"));
            ClientGlobal.initByProperties(properties);
            domainName = properties.getProperty("fdfs.domain-name");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultInfo<UploadFileInfoBean,String> upload(FastDFSFile file) throws IOException {

        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());

        ResultInfo<UploadFileInfoBean,String> result = new ResultInfo<>();

        String[] uploadResults = null;
        StorageClient storageClient=null;
        try {
            storageClient = getTrackerClient();
            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
        } catch (IOException e) {
            result.setMessage(e.getMessage());
            result.setResultStatusEnum(ResultStatusEnum.failed);
            return result;
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setResultStatusEnum(ResultStatusEnum.failed);
            return result;
        }

        if (uploadResults == null && storageClient!=null) {
            result.setMessage("文件上传失败");
            result.setResultStatusEnum(ResultStatusEnum.failed);
            return result;
        }
        UploadFileInfoBean fileInfo =new UploadFileInfoBean();
        fileInfo.setGroupName(uploadResults[0]);
        fileInfo.setRemoteFileName(uploadResults[1]);
        fileInfo.setFilePath(FileManager.getTrackerUrl()+uploadResults[0]+ "/"+uploadResults[1]);
        result.setData(fileInfo);
        return result;
    }

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileByte);
            return ins;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFile(String groupName, String remoteFileName)
            throws Exception {
        StorageClient storageClient = getTrackerClient();
        int i = storageClient.delete_file(groupName, remoteFileName);
    }

    public static StorageServer[] getStoreStorages(String groupName)
            throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getStoreStorages(trackerServer, groupName);
    }

    public static ServerInfo[] getFetchStorages(String groupName,
                                                String remoteFileName) throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    public static String getTrackerUrl() throws IOException {
        return domainName;
    }

    private static StorageClient getTrackerClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return  storageClient;
    }

    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return  trackerServer;
    }

}
