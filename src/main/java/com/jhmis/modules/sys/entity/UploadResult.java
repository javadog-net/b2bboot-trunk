package com.jhmis.modules.sys.entity;

import java.io.Serializable;

public class UploadResult implements Serializable {
    private static final long serialVersionUID = 3161961701375524093L;
    //ueditor参数
    private String state;
    private String url;
    private String title;
    private String original;
    //private String type;
    //private Long size;
    //kindeditor必须的三个参数
    private boolean success;
    private String ext;
    private Long size;
    //其他参数
    private String message;
    private String thumb;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getType() {
        if(this.ext!=null&&this.ext.length()>0) {
            return "." + this.ext;
        } else {
            return null;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
