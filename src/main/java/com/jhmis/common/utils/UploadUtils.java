package com.jhmis.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

public class UploadUtils
{
    private static Logger logger = LoggerFactory.getLogger(UploadUtils.class);

    public static void main(String[] args) {
        MultipartResolver multipartResolver = SpringContextHolder.getBean(DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME);

    }
}
