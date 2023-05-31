package com.erpang.obsidianoss.config;

import com.erpang.obsidianoss.enums.OssType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {
    private OssType type;
    private String region;
    private String accessKeyId;
    private String secret;
    private String bucketName;
    private String folder;
    private String customUrl;
}
