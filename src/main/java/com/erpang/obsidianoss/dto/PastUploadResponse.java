package com.erpang.obsidianoss.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class PastUploadResponse {
    private Boolean success;

    private List<String> result;

    public static PastUploadResponse fail() {
        PastUploadResponse fail = new PastUploadResponse();
        fail.setSuccess(false);
        return fail;
    }

    public static PastUploadResponse fail(String msg){
        PastUploadResponse fail = new PastUploadResponse();
        fail.setSuccess(false);
        fail.setResult(Arrays.asList(msg));
        return fail;
    }
}
