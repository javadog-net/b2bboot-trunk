package com.haier.webservices.server.acg;

import javax.jws.WebService;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.haier.webservices.server.acg
 * @Author: hdx
 * @CreateTime: 2019-08-14 15:52
 * @Description: demo测试webservice
 */
@WebService
public interface DemoService {
    public String sayHello(String user);
}