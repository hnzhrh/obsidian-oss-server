package com.erpang.obsidianoss.context;

import cn.hutool.core.swing.clipboard.ClipboardUtil;
import com.erpang.obsidianoss.api.IOss;
import com.erpang.obsidianoss.config.OssConfig;
import com.erpang.obsidianoss.dto.PastUploadResponse;
import com.erpang.obsidianoss.enums.OssType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OssContext {
    @Autowired
    private Map<String, IOss> ossMap = new ConcurrentHashMap<>();

    @Autowired
    private OssConfig ossConfig;

    public PastUploadResponse pastUpload() {
        String imageTempPath = null;
        try {
            imageTempPath = this.getFileFromClipboard();
        } catch (Exception e) {
            return PastUploadResponse.fail(e.getMessage());
        }
        PastUploadResponse result = new PastUploadResponse();
        result.setSuccess(true);
        try {
            result.setResult(Arrays.asList(ossMap.get(ossConfig.getType().name()).upload(imageTempPath)));
        } catch (Exception e) {
            return PastUploadResponse.fail(e.getMessage());
        }
        return result;
    }

    public String getFileFromClipboard() throws Exception {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);

        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

            if (!fileList.isEmpty()) {
                File file = fileList.get(0); // 获取剪切板中的第一个文件
                // 将文件存储到临时目录
                String tempDir = System.getProperty("java.io.tmpdir");
                File tempFile = new File(tempDir, file.getName());
                Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return tempFile.getAbsolutePath();
            }
        }

        return null;
    }
}
