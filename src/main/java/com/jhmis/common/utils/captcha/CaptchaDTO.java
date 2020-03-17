package com.jhmis.common.utils.captcha;

import java.io.Serializable;

public class CaptchaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String captchaToken;
    private String captchaImage;

    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }

    public String getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(String captchaImage) {
        this.captchaImage = captchaImage;
    }
}
