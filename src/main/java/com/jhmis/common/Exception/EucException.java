package com.jhmis.common.Exception;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Exception
 * @Author: hdx
 * @CreateTime: 2019-11-08 14:53
 * @Description: Euc异常类
 */
public class EucException extends  Exception{
    private static final long serialVersionUID = 1L;
    public EucException(Object obj) {
        super(obj.toString());
    }
}
