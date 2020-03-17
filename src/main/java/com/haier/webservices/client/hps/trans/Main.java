package com.haier.webservices.client.hps.trans;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.haier.webservices.client.hps.trans
 * @Author: hdx
 * @CreateTime: 2019-08-15 09:29
 * @Description: 测试提供给hps webservice接口
 */
public class Main {
    public static void main(String[] args) {
        com.haier.webservices.client.hps.trans.HpsServiceImplService service = new com.haier.webservices.client.hps.trans.HpsServiceImplService();
        AjaxJson a = service.getHpsServiceImplPort().transCode("8800298591","80000");
        System.out.println(a);
    }
}
