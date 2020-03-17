package com.jhmis.common.Exception;


/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Exception
 * @Author: hdx
 * @CreateTime: 2019-09-02 15:09
 * @Description: 发布需求自定义异常类
 */
public class ShopMsgException extends Exception{
    private static final long serialVersionUID = 1L;
    public ShopMsgException(Object Obj) {
        super(Obj.toString());
    }

}
