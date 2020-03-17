/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.web;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.service.SystemConfigService;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxMessageRecord;
import com.jhmis.modules.wechat.service.WxMessageRecordService;

/**
 * 发送短信
 * 
 * @author lgf
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/sms")
public class SMSController extends BaseController {

	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private DealerAccountService dealerAccountService;
	@Autowired
	private WxMessageRecordService wxMessageRecordService;
	

	/**
	 * 打开短信页面
	 */
	@RequestMapping(value = { "index", "" })
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/tools/sms/sendSMS";
	}

	/**
	 * 发送短信
	 */
	@RequestMapping("send")
	public String send(String tels, HttpServletResponse response, String content, Model model) {
		String[] tel = tels.split(",");
		String res="";
		for (String t : tel) {
			try {
				String result = SendMsgApi.SendMessage(t, content);
				logger.info("发送短信结果是：tools/sms/send==="+tel+"--->结果:"+ result);
				res=tel+"发送结果>:"+ result+"/ ";
				//保存发送短信履历
				SendMsgApi sendMsgApi=new SendMsgApi();
				sendMsgApi.saveMessageRecord(t, content, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("result", res);
		return "modules/tools/sms/sendSMSResult";
	}


	
}