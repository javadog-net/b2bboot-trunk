package com.haier.webservices.server.service690;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.process.entity.shopmsg.ShopMsgVo;


/**    待测试
 * <p>Title: ShopMsg690Service</p>
 * <p>Description: </p>  提供给690大接待的需求录入
 * @author tc  
 * @date 2019年9月17日 上午11:44:25
 */ 
@WebService
public interface ShopMsg690Service {
    //提供给690大接待的需求录入
    @WebMethod
    AjaxJson pushMsgToB2b(@WebParam(name="username") String username,
			    		@WebParam(name="password")String password,
                        @WebParam(name="shopMsgVo") ShopMsgVo shopMsgVo);
}