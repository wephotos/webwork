package com.github.wephotos.webwork.file.stor;

import com.github.wephotos.webwork.file.config.FileProperties;
import com.github.wephotos.webwork.file.entity.WebworkFile;
import com.github.wephotos.webwork.logging.LoggerFactory;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Bucket.DeleteObjectOutput;
import com.qingstor.sdk.service.Bucket.GetObjectOutput;
import com.qingstor.sdk.service.Bucket.PutObjectInput;
import com.qingstor.sdk.service.Bucket.PutObjectOutput;
import com.qingstor.sdk.service.QingStor;
import lombok.Getter;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

/**
 * 青云对象存储
 *
 * @author TQ
 */
@Getter
public class FileStorQing implements FileStor {

    // 日志
    private static final Logger log = LoggerFactory.getLogger(FileStorQing.class);

    public FileStorQing(FileProperties props) {
        this.props = props;
    }

    private FileProperties props;
    /**
     * 存储空间
     */
    private Bucket bucket;

    /**
     * 初始化方法
     */
    @PostConstruct
    public void initConfig() {
        EnvContext evn = new EnvContext(props.getCloudKey(), props.getCloudSecret());
        QingStor stor = new QingStor(evn, props.getCloudZone());
        bucket = stor.getBucket(props.getCloudBucket(), props.getCloudZone());
    }

    @Override
    public StorDialect getStorDialect() {
        return StorDialect.QING_CLOUD;
    }

    @Override
    public String getNewObjectName(String filename) {
        filename = filename.replaceAll("[^a-zA-Z0-9_\u4E00-\u9FA5&&[^.*-]]+", "");
        LocalDate now = LocalDate.now();
        return String.format("bughub/%s/%s/%s/%s-%s", now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(),
                System.currentTimeMillis(), filename);
    }

    @Override
    public void storage(WebworkFile file) throws IOException {
        PutObjectInput input = new PutObjectInput();
        input.setContentLength(file.getSize());
        input.setBodyInputStream(file.getInputStream());
        PutObjectOutput output;
        try {
            output = bucket.putObject(file.getObjectName(), input);
        } catch (QSException e) {
            throw new FileStorException("上传文件流到青云失败", e);
        }
        if (output.getStatueCode() != 201) {
            throw new FileStorException(String.format("上传附件到青云失败:%s", output.getMessage()));
        }
        log.info("附件上传青云成功:{}", file.getObjectName());
    }

    @Override
    public void delete(String objectName) throws IOException {
        DeleteObjectOutput output;
        try {
            output = bucket.deleteObject(objectName);
        } catch (QSException e) {
            throw new FileStorException("删除青云附件失败", e);
        }
        if (output.getStatueCode() != 204) {
            throw new FileStorException(String.format("删除青云附件失败:%s", output.getMessage()));
        }
        log.info("删除青云附件成功:{}", objectName);
    }

    @Override
    public InputStream get(String objectName) throws IOException {
        GetObjectOutput output;
        try {
            output = bucket.getObject(objectName, null);
        } catch (QSException e) {
            throw new FileStorException("获取青云附件失败", e);
        }
        log.info("获取青云附件成功:{}", objectName);
        return output.getBodyInputStream();
    }

}
