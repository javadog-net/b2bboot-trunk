package com.jhmis.common.Exception;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Exception
 * @Author: hdx
 * @CreateTime: 2019-08-14 21:34
 * @Description: hps异常类
 */
public class HpsException  extends  Exception{
    private static final long serialVersionUID = 1L;
    public HpsException(Object Obj) {
        super(Obj.toString());
    }
}
