package com.haier.webservices.client.acg;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.haier.webservices.client.acg
 * @Author: hdx
 * @CreateTime: 2019-08-14 16:47
 * @Description: 测试acg Webservice方法
 */
public class Main {
    public static void main(String[] args) {
        AcgPushServiceImplService service = new AcgPushServiceImplService();
        com.haier.webservices.client.acg.Dealer d = new com.haier.webservices.client.acg.Dealer();
        d.setCompanyCode("80000");
        d.setAcgPassword("123456");
        d.setCompanyName("测试");
        d.setContacts("胡东旭");
        d.setEmail("862422627@qq.com");
        d.setMobile("18306390693");
        AjaxJson a = service.getAcgPushServiceImplPort().pushContractor(d);
        System.out.println(a);
    }
}
