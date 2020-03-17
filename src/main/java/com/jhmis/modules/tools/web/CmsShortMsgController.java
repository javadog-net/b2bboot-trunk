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

import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.tools.entity.CmsEmailInfo;
import com.jhmis.modules.tools.entity.CmsShortMsg;
import com.jhmis.modules.tools.entity.CmsShortMsgDetail;
import com.jhmis.modules.tools.service.CmsShortMsgDetailService;
import com.jhmis.modules.tools.service.CmsShortMsgService;

/**
 * 发送短信Controller
 * @author tc
 * @version 2019-09-04
 */
@Controller
@RequestMapping(value = "${adminPath}/tools/sms/cmsShortMsg")
public class CmsShortMsgController extends BaseController {

	@Autowired
	private CmsShortMsgService cmsShortMsgService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private CmsShortMsgDetailService cmsShortMsgDetailService;
	
	@ModelAttribute
	public CmsShortMsg get(@RequestParam(required=false) String id) {
		CmsShortMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsShortMsgService.get(id);
		}
		if (entity == null){
			entity = new CmsShortMsg();
		}
		return entity;
	}
	
	/**
	 * 发送短信列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/tools/sms/cmsShortMsgList";
	}
	
	/**
	 * 发送短信列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsShortMsg cmsShortMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsShortMsg> pa=new Page<CmsShortMsg>(request, response);
		pa.setOrderBy("createtime desc");
		Page<CmsShortMsg> page = cmsShortMsgService.findPage(pa, cmsShortMsg); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑发送短信表单页面
	 */
	@RequestMapping(value = "form")
	public String form(CmsShortMsg cmsShortMsg, Model model) {
		model.addAttribute("cmsShortMsg", cmsShortMsg);
		if(StringUtils.isBlank(cmsShortMsg.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/tools/sms/sendSMS";
	}

	/**
	 * 保存发送短信
	 */
	@RequestMapping(value = "save")
	public String save(CmsShortMsg cmsShortMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if(cmsShortMsg==null||StringUtils.isBlank(cmsShortMsg.getId())){
			User u=UserUtils.getUser();
			if (u!=null) {//放入创建人
				cmsShortMsg.setCreater(u.getLoginName());
			}
			cmsShortMsg.setCreatetime(new Date());
			cmsShortMsg.setIsshortmsg("0");
		}
		if (!beanValidator(model, cmsShortMsg)){
			return form(cmsShortMsg, model);
		}
		//新增或编辑表单保存
		cmsShortMsgService.save(cmsShortMsg);//保存
		addMessage(redirectAttributes, "保存发送短信成功");
		return "redirect:"+Global.getAdminPath()+"/tools/sms/cmsShortMsg/?repage";
	}
	
	/**
	 * 删除发送短信
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsShortMsg cmsShortMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsShortMsgService.delete(cmsShortMsg);
		j.setMsg("删除发送短信成功");
		return j;
	}
	
	/**
	 * 批量删除发送短信
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsShortMsgService.delete(cmsShortMsgService.get(id));
		}
		j.setMsg("删除发送短信成功");
		return j;
	}
	
	
	/** 
	  * @Title: send 
	  * @Description: TODO  按条件 短信发送
	  * @param cmsShortMsg
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年9月5日上午9:11:27
	  */
	@ResponseBody
	@RequestMapping("send")
	public AjaxJson send(CmsShortMsg cmsShortMsg) {
        String phone="";
        String content="";
		AjaxJson j=new AjaxJson();
		if(cmsShortMsg==null){
			//model.addAttribute("result", "数据有误！请重新");
			j.setMsg("数据有误！请重试一下。");
			j.setSuccess(false);
			return j;
		}
		if(cmsShortMsg.getSendtype().equals("0")){
			phone=cmsShortMsg.getMobilephone();
		}else{
			//全部、经销商、采购商、按地域
			if(cmsShortMsg.getMembertype().equals("全部")){
				List<Purchaser>pList=purchaserService.findListAllOrArea(new Purchaser());
				for(Purchaser p:pList){
					
					phone+=p.getMobile()+",";
				}
				List<Dealer>dList=dealerService.findListAllOrArea(new Dealer());
				for(Dealer d:dList){
					phone+=d.getMobile()+",";
				}
			}else if(cmsShortMsg.getMembertype().equals("经销商")){
				List<Dealer>dList=dealerService.findListAllOrArea(new Dealer());
				for(Dealer d:dList){
					phone=d.getMobile()+",";
				}
			}else if(cmsShortMsg.getMembertype().equals("采购商")){
				List<Purchaser>pList=purchaserService.findListAllOrArea(new Purchaser());
				for(Purchaser p:pList){
					phone=p.getMobile()+",";
				}
			}else if(cmsShortMsg.getMembertype().equals("按地域")){
				Purchaser purchaser=new Purchaser();
				purchaser.setAreaInfo(cmsShortMsg.getAreaname());
				List<Purchaser>pList=purchaserService.findListAllOrArea(purchaser);
				for(Purchaser p:pList){
					phone+=p.getMobile()+",";
				}
				Dealer dealer=new Dealer();
				dealer.setAreaInfo(cmsShortMsg.getAreaname());
				List<Dealer>dList=dealerService.findListAllOrArea(dealer);	
				for(Dealer d:dList){
					phone+=d.getMobile()+",";
				}
			}
			
		}
		if(phone.equals("")){
			j.setMsg("数据有误，请重试。");
			return  j;
		}
		String[] tel= phone.replace(" ", "").split(",");
		System.out.println("phone===>"+phone);
		content=cmsShortMsg.getContent().replace(" ", "");
		String res="";
		for (String t : tel) {
		       boolean b=SendMsgApi.isPhone(t);
					if(!b){
						continue;
					}
			try {
				String result = SendMsgApi.SendMessage(t, content);
				logger.info("发送短信结果是：tools/sms/cmsShortMsg/send==="+tel+"--->结果:"+ result);
				res+=tel+"发送结果>:"+ result+"/ ";
				j.setMsg(res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(res)){
			//修改发送状态
			User u=UserUtils.getUser();
			if (u!=null) {//放入发送人
				cmsShortMsg.setSender(u.getLoginName());
			}
			cmsShortMsg.setSendtime(new Date());
			cmsShortMsg.setIsshortmsg("1");
			cmsShortMsgService.save(cmsShortMsg);
			//保存发送短信履历
			CmsShortMsgDetail cmsShortMsgDetail=new CmsShortMsgDetail();
			cmsShortMsgDetail.setMobilephone(phone);
			cmsShortMsgDetail.setResult(res);
			cmsShortMsgDetail.setSendtime(new Date());
			if (u!=null) {//放入发送人
				cmsShortMsgDetail.setSender(u.getLoginName());
			}
			cmsShortMsgDetail.setShortmsgid(cmsShortMsg.getId());
			cmsShortMsgDetailService.save(cmsShortMsgDetail);
		}
		//return "modules/tools/sms/sendSMSResult";
		return j;
	}


	

}