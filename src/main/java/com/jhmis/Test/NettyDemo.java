package com.jhmis.Test;

import java.util.Properties;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-14 17:12
 * @Description: Netty
 */
public class NettyDemo {
    public static void main(String[] args) {
        System.setProperty("haha","你妹妹");
        Properties properties = System.getProperties();
        //遍历所有的属性
        for (String key : properties.stringPropertyNames()) {
            //输出对应的键和值
            System.out.println(key + "=" + properties.getProperty(key));
        }
    }

}
