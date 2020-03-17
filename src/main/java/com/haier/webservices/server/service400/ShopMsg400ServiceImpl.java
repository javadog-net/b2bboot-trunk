package com.haier.webservices.server.service400;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import com.jhmis.common.Enum.MsgChannelCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.process.entity.shopmsg.ShopMsgVo;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;

public class ShopMsg400ServiceImpl extends BaseController implements ShopMsg400Service {

	@Resource
	private ShopMsgService shopMsgService;

	/**
	 * 400 客服 推送需求接口
	 * 
	 * @param username
	 * @param password
	 * @param shopMsgVo
	 * @return
	 */
	public AjaxJson pushMsgToB2b(String username, String password, ShopMsgVo shopMsgVo) {
		logger.info("400 客服 推送需求接口");
		ShopMsg shopMsg = null;
        try {
			shopMsgVo.setChannel(MsgChannelCode.FOUR_CUSTOMER_SERVICE.getIndex());
			shopMsgVo = shopMsgService.pushMsgToB2b(username,password,shopMsgVo);
		} catch (ShopMsgException | InvocationTargetException | IllegalAccessException e) {
            logger.error("400 客服 推送需求接口异常，原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(shopMsgVo);
	}

}
