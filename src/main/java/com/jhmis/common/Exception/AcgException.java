package com.jhmis.common.Exception;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Exception
 * @Author: hdx
 * @CreateTime: 2019-08-14 17:16
 * @Description: Acg自定义异常类
 */
public class AcgException extends Exception{
    private static final long serialVersionUID = 1L;

    public AcgException(Object Obj) {
        super(Obj.toString());
    }
}
