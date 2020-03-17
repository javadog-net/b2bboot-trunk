/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.mail.MailSendUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.sys.entity.DictType;
import com.jhmis.modules.sys.entity.DictValue;
import com.jhmis.modules.sys.entity.SystemConfig;
import com.jhmis.modules.sys.service.DictTypeService;
import com.jhmis.modules.sys.service.SystemConfigService;

/**
 * <p>
 * Title: EmailController
 * </p>
 * <p>
 * Description:
 * </p>
 * 外部邮件发送
 * 
 * @author tc
 * @date 2019年8月29日 下午3:46:59
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/email")
public class EmailController extends BaseController {

	@Autowired
	private DictTypeService dictTypeService;
	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private DealerAccountService dealerAccountService;
	/**
	 * 打开邮件页面
	 */
	@RequestMapping(value = { "index", "" })
	public String index(HttpServletRequest request, HttpServletResponse response,
			Model model,String id) {
		
		
		return "modules/tools/email/sendEmail";
	}

	/**
	 * 发送邮件
	 */
	@RequestMapping("send")
	public String send(String emailAddress, HttpServletResponse response, String title, String content, Model model) {
		System.out.println("刚进入send方法的content" + content);
		String smtp = "";
		String port = "";
		String mailName = "";
		String mailPassword = "";
		DictType dictType = new DictType();
		dictType.setType("email_config");
		List<DictType> listType = dictTypeService.findList(dictType);
		if (listType != null && listType.size() > 0) {
			dictType = listType.get(0);
		}
		List<DictValue> listValue = dictTypeService.get(dictType.getId()).getDictValueList();
		for (DictValue value : listValue) {
			if (value.getSort().equals("1")) {// 邮件服务器
				smtp = value.getValue();
			}
			if (value.getSort().equals("2")) {// 邮件端口
				port = value.getValue();
			}
			if (value.getSort().equals("5")) {// 邮件帐号
				mailName = value.getValue();
			}
			if (value.getSort().equals("6")) {// 邮件密码
				mailPassword = value.getValue();
			}
		}
		String result = "";
		logger.info("smtp:" + smtp + "port:" + port + "mail:" + mailName + "mailPassword:" + mailPassword);
		try {

			logger.info(emailAddress + "发送邮件的地址address");
			boolean isSuccess = MailSendUtils.sendEmail(smtp, port, mailName, mailPassword, emailAddress, title,
					content, "2");
			if (isSuccess) {
				result += emailAddress + ":<font color='green'>发送成功!</font><br/>";
			} else {
				result += emailAddress + ":<font color='red'>发送失败!</font><br/>";
			}
			String body = content.replaceAll("\n\r", "\r").replaceAll("\r\n", "\n").replaceAll("\r", "\n")
					.replaceAll("\n", "\r\n");
			body = HtmlUtils.htmlUnescape(body);
			//保存发送邮件的履历
			SendMsgApi sendMsgApi=new SendMsgApi();
			sendMsgApi.saveMessageRecord(emailAddress, "titile:" + title+ "content:" + content,result);

		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("===" + result + "result");
		model.addAttribute("result", result);
		return "modules/tools/email/sendEmailResult";
	}

	public static void main(String[] args) {
		Model model = null;
		HttpServletResponse response = null;
		String title = "测试";
		String content = "测试";
		String emailAddress = "793929984@qq.com";
		EmailController e = new EmailController();
		try {
			String result = e.send(emailAddress, response, title, content, model);
			System.out.println(result);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}