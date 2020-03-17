package com.haier.webservices.server.acg;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.shop.entity.dealer.DealerForAcg;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.haier.webservices.server.acg
 * @Author: hdx
 * @CreateTime: 2019-08-14 16:11
 * @Description: Acg推送企业购非标客户接口
 */
@WebService
public interface AcgService {
    /**
    *@Author: hdx
    *@CreateTime: 16:13 2019/8/14
    *@param:  * @param null
    *@Description: acg推送非标客户

    */
    @WebMethod
    AjaxJson pushContractor(@WebParam(name = "dealerForAcg") DealerForAcg dealerForAcg);
}
