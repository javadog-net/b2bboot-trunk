package com.haier.webservices.server.service400;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.process.entity.shopmsg.ShopMsgVo;

/**   待测试
 * <p>Title: ShopMsg400Service</p>
 * <p>Description: </p>  //提供给400客服的需求录入
 * @author tc  
 * @date 2019年9月17日 上午11:32:25
 */ 
@WebService
public interface ShopMsg400Service {
    //提供给400客服的需求录入
    @WebMethod
    AjaxJson pushMsgToB2b(@WebParam(name="username") String username,
			    		@WebParam(name="password")String password,
			    		@WebParam(name="shopMsgVo") ShopMsgVo shopMsgVo);
}