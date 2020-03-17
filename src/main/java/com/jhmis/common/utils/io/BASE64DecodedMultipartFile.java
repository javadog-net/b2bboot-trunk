package com.jhmis.common.utils.io;

import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * base64字符串转MultipartFile的类
 */
public class BASE64DecodedMultipartFile implements MultipartFile {
    private final byte[] content;
    private String name;
    private String originalFilename;
    private String ext;
    private String contentType;
    public BASE64DecodedMultipartFile(String name, String base64Str) {
        Assert.hasLength(name, "Name must not be null");
        this.name = name;
        try {
            String[] baseStrs = base64Str.split(",");
            String preStr = baseStrs[0];
            String contentStr = baseStrs[1];
            this.contentType = preStr.substring(preStr.indexOf("data:") + 5, preStr.indexOf(";base64")).toLowerCase();
            //处理扩展名 TODO 如何通过contentType获取扩展名，需要有对应的mime表,或者简单截取/后面字符
            this.ext = this.contentType.substring(this.contentType.indexOf("/")+1);
            this.originalFilename = UUID.randomUUID().toString() + "." + ext;
            //处理内容字节
            this.content = Base64.getDecoder().decode(contentStr);
            for (int j = 0; j < this.content.length; ++j) {
                if (this.content[j] < 0) {// 调整异常数据
                    this.content[j] += 256;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("base64字符串格式错误");
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return content == null || content.length == 0;
    }

    @Override
    public long getSize() {
        return (long)this.content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }
}
