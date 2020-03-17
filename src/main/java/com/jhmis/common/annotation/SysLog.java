package com.jhmis.common.annotation;

import java.lang.annotation.*;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.annotation
 * @Author: hdx
 * @CreateTime: 2019-08-28 15:01
 * @Description: 日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    //接口描述
    String desc() default "";

}

