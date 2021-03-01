package cn.com.quanyou.ioc.file.manage.common.utils.FastdfsUtils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author heshiyi@quanyou.com.cn
 * @title: FastDFSFile
 * @date 2019/6/69:49
 * @projectName file-manage
 * @description:
 */
@Data
public class FastDFSFile implements Serializable {

    private byte[] content;
    private String name;
    private String ext;
    private String length;
    private String author;

    public FastDFSFile(byte[] content, String ext) {
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile(byte[] content, String name, String ext) {
        this.content = content;
        this.name = name;
        this.ext = ext;
    }

    public FastDFSFile(byte[] content, String name, String ext, String length,
                       String author) {
        this.content = content;
        this.name = name;
        this.ext = ext;
        this.length = length;
        this.author = author;
    }
}
