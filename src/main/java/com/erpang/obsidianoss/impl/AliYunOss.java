package com.erpang.obsidianoss.impl;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.erpang.obsidianoss.api.IOss;
import com.erpang.obsidianoss.config.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service("Ali")
public class AliYunOss implements IOss {
    private final OssConfig ossConfig;
    private OSS ossClient;

    @Autowired
    public AliYunOss(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = String.format("%s.aliyuncs.com", ossConfig.getRegion());
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getSecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossConfig.getBucketName();
        // 创建OSSClient实例。
        this.ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public String upload(String filePath) throws Exception {
        // 获取File对象
        File tmpFile = new File(filePath);
        if (!tmpFile.exists()) {
            throw new RuntimeException("无法获取临时文件.");
        }
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = String.format("%s/%s", ossConfig.getFolder(), tmpFile.getName());

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), objectName, tmpFile);
        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        PutObjectResult result = ossClient.putObject(putObjectRequest);
        return this.generateFileUrl(ossConfig, objectName);
    }

    private String generateFileUrl(OssConfig ossConfig, String objectName) {
        StringBuffer stringBuffer = new StringBuffer();
        if (StrUtil.isNotBlank(ossConfig.getCustomUrl())) {
            stringBuffer.append(ossConfig.getCustomUrl());
        } else {
            stringBuffer.append("https://");
            stringBuffer.append(ossConfig.getBucketName())
                    .append(".")
                    .append(String.format("%s.aliyuncs.com", ossConfig.getRegion()));
        }
        stringBuffer.append("/");
        stringBuffer.append(objectName);
        return stringBuffer.toString();
    }
}
