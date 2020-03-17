package com.haier.webservices.server.hps;

import com.jhmis.common.json.AjaxJson;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.haier.webservices.server.hps
 * @Author: hdx
 * @CreateTime: 2019-08-14 21:14
 * @Description: 提供给hps非标客户更新接口
 */
@WebService
public interface HpsService {
    //提供给非标客户编码更新接口
    @WebMethod
    AjaxJson transCode(@WebParam(name="oldCode") String oldCode, @WebParam(name="newCode") String newCode);
}
