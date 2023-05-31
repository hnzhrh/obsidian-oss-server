package com.erpang.obsidianoss.rest;

import com.erpang.obsidianoss.context.OssContext;
import com.erpang.obsidianoss.dto.PastUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController("/")
public class ObsidianOssPluginRest {
    @Autowired
    private OssContext ossContext;

    @PostMapping("/upload")
    public PastUploadResponse pastUpload() {
        return ossContext.pastUpload();
    }
}
