/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.mail.MailSendUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.sys.entity.DictType;
import com.jhmis.modules.sys.entity.DictValue;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.service.DictTypeService;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.tools.entity.CmsEmailDetail;
import com.jhmis.modules.tools.entity.CmsEmailInfo;
import com.jhmis.modules.tools.service.CmsEmailDetailService;
import com.jhmis.modules.tools.service.CmsEmailInfoService;

/**
 * 邮件Controller
 * @author tc
 * @version 2019-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/email/cmsEmailInfo")
public class CmsEmailInfoController extends BaseController {
	@Autowired
	private DictTypeService dictTypeService;
	@Autowired
	private CmsEmailInfoService cmsEmailInfoService;
	@Autowired
	private CmsEmailDetailService cmsEmailDetailService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private DealerService dealerService;
	
	@ModelAttribute
	public CmsEmailInfo get(@RequestParam(required=false) String id) {
		CmsEmailInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsEmailInfoService.get(id);
		}
		if (entity == null){
			entity = new CmsEmailInfo();
		}
		return entity;
	}
	
	/**
	 * 邮件列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/tools/email/cmsEmailInfoList";
	}
	
	/**
	 * 邮件列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsEmailInfo cmsEmailInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsEmailInfo> pa=new Page<CmsEmailInfo>(request, response);
		pa.setOrderBy("createtime desc");
		Page<CmsEmailInfo> page = cmsEmailInfoService.findPage(pa, cmsEmailInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑邮件表单页面
	 */
	@RequestMapping(value = "form")
	public String form(CmsEmailInfo cmsEmailInfo, Model model) {
		model.addAttribute("cmsEmailInfo", cmsEmailInfo);
		if(StringUtils.isBlank(cmsEmailInfo.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/tools/email/sendEmail";
	}

	/**
	 * 保存邮件
	 */
	@RequestMapping(value = "save")
	public String save(CmsEmailInfo cmsEmailInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if(cmsEmailInfo==null||StringUtils.isBlank(cmsEmailInfo.getId())){
		cmsEmailInfo.setCreatetime(new Date());
		cmsEmailInfo.setIssendemail("0");
		}
		if (!beanValidator(model, cmsEmailInfo)){
			return form(cmsEmailInfo, model);
		}
		//新增或编辑表单保存
		cmsEmailInfoService.save(cmsEmailInfo);//保存
		addMessage(redirectAttributes, "保存邮件成功");
		return "redirect:"+Global.getAdminPath()+"/tools/email/cmsEmailInfo/?repage";
	}
	
	/**
	 * 删除邮件
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsEmailInfo cmsEmailInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsEmailInfoService.delete(cmsEmailInfo);
		j.setMsg("删除邮件成功");
		return j;
	}
	
	/**
	 * 批量删除邮件
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsEmailInfoService.delete(cmsEmailInfoService.get(id));
		}
		j.setMsg("删除邮件成功");
		return j;
	}
	/** 
	  * @Title: send 
	  * @Description: TODO 按照条件发送邮件 
	  * @param model
	  * @param cmsEmailInfo
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年9月4日下午2:13:49
	  */
	@ResponseBody
	@RequestMapping("send")
	public AjaxJson send(Model model,CmsEmailInfo cmsEmailInfo) {
		AjaxJson j = new AjaxJson();
		String emailAddress="";
		String title="" ;
		String content="";
		if(cmsEmailInfo==null){
			//model.addAttribute("result", "数据有误！请重新");
			//return "modules/tools/email/sendEmailResult";
			j.setMsg("数据有误！请重试一下。");
			j.setSuccess(false);
			return j;
		}
		if(cmsEmailInfo.getSendtype().equals("0")){
			emailAddress=cmsEmailInfo.getEmail();
		}else{
			//全部、经销商、采购商、按照地域、未激活、已激活
			if(cmsEmailInfo.getRectype().equals("全部")){
				List<Purchaser>pList=purchaserService.findListAllOrArea(new Purchaser());
				for(Purchaser p:pList){
					emailAddress+=p.getEmail()+";";
				}
				List<Dealer>dList=dealerService.findListAllOrArea(new Dealer());
				for(Dealer d:dList){
					emailAddress+=d.getEmail()+";";
				}
			}else if(cmsEmailInfo.getRectype().equals("经销商")){
				List<Dealer>dList=dealerService.findListAllOrArea(new Dealer());
				for(Dealer d:dList){
					emailAddress=d.getEmail()+";";
				}
			}else if(cmsEmailInfo.getRectype().equals("采购商")){
				List<Purchaser>pList=purchaserService.findListAllOrArea(new Purchaser());
				for(Purchaser p:pList){
					emailAddress=p.getEmail()+";";
				}
			}else if(cmsEmailInfo.getRectype().equals("按地域")){
				Purchaser purchaser=new Purchaser();
				purchaser.setAreaInfo(cmsEmailInfo.getProvincename());
				List<Purchaser>pList=purchaserService.findListAllOrArea(purchaser);
				for(Purchaser p:pList){
					emailAddress+=p.getEmail()+";";
				}
				Dealer dealer=new Dealer();
				dealer.setAreaInfo(cmsEmailInfo.getProvincename());
				List<Dealer>dList=dealerService.findListAllOrArea(dealer);
				for(Dealer d:dList){
					emailAddress+=d.getEmail()+";";
				}
			}
			//由于迁移字段还未确认暂时激活跟 未激活 还没有写。
		}
		emailAddress=emailAddress.replace(" ", "");//去除emailAddress中的所有空格，减少发送不成功
		title=cmsEmailInfo.getTitle();
		content=cmsEmailInfo.getContent();
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
			User user = UserUtils.getUser();
			if (isSuccess) {
				result += emailAddress + ":<font color='green'>发送成功!</font><br/>";
				j.setSuccess(true);
				//修改 该邮件的发送状态
				cmsEmailInfo.setIssendemail("1");
				cmsEmailInfo.setSendtime(new Date());
				
				if(user!=null){
				  cmsEmailInfo.setSender(user.getLoginName());
				}
				cmsEmailInfoService.updateIsSend(cmsEmailInfo);
			} else {
				result += emailAddress + ":<font color='red'>发送失败!</font><br/>";
				j.setSuccess(false);
			}
			if(isSuccess){
				String body = content.replaceAll("\n\r", "\r").replaceAll("\r\n", "\n").replaceAll("\r", "\n")
						.replaceAll("\n", "\r\n");
				body = HtmlUtils.htmlUnescape(body);
				//保存发送邮件的履历
				CmsEmailDetail cmsEmailDetail=new CmsEmailDetail();
				cmsEmailDetail.setMailinfoid(cmsEmailInfo.getId());
				cmsEmailDetail.setEmail(body);
				cmsEmailDetail.setResult(result);
				if(user!=null){
				   cmsEmailDetail.setSender(user.getLoginName());
				}
				cmsEmailDetail.setSendtime(new Date());
				cmsEmailDetailService.save(cmsEmailDetail);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		j.setMsg(result);
		System.out.println("===" + result + "result");
		//model.addAttribute("result", result);
		//return "modules/tools/email/sendEmailResult";
		
		return j;
	}
}