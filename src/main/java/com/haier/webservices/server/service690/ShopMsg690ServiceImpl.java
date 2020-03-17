package com.haier.webservices.server.service690;




import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import com.jhmis.common.Enum.MsgChannelCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.process.entity.shopmsg.ShopMsgVo;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;

public class ShopMsg690ServiceImpl extends BaseController implements ShopMsg690Service
{
	@Resource
	private ShopMsgService shopMsgService;

	/**
	 * 690大接待 推送需求接口
	 *
	 * @param username
	 * @param password
	 * @param shopMsgVo
	 * @return
	 */
	public AjaxJson pushMsgToB2b(String username, String password, ShopMsgVo shopMsgVo) {
		logger.info("690大接待 推送需求接口");
		ShopMsg shopMsg = null;
		try {
			shopMsgVo.setChannel(MsgChannelCode.GRAND_RECEPTION.getIndex());
			shopMsgVo = shopMsgService.pushMsgToB2b(username,password,shopMsgVo);
		} catch (ShopMsgException | InvocationTargetException | IllegalAccessException e) {
			logger.error("690大接待 推送需求接口异常，原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(shopMsgVo);
	}

}
