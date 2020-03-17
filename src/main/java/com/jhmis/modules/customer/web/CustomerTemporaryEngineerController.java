/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.customer.entity.DealerFb;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxMessageRecord;
import com.jhmis.modules.wechat.service.WxMessageRecordService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.customer.entity.CustomerTemporaryEngineer;
import com.jhmis.modules.customer.service.CustomerTemporaryEngineerService;

/**
 * 临时工程商Controller
 * @author hdx
 * @version 2019-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerTemporaryEngineer")
public class CustomerTemporaryEngineerController extends BaseController {

	@Autowired
	private CustomerTemporaryEngineerService customerTemporaryEngineerService;
	@Autowired
	private WxMessageRecordService wxMessageRecordService;

	@ModelAttribute
	public CustomerTemporaryEngineer get(@RequestParam(required=false) String id) {
		CustomerTemporaryEngineer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerTemporaryEngineerService.get(id);
		}
		if (entity == null){
			entity = new CustomerTemporaryEngineer();
		}
		return entity;
	}
	
	/**
	 * 临时工程商列表页面
	 */
	@RequiresPermissions("customer:customerTemporaryEngineer:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/customerTemporaryEngineerList";
	}
	
	/**
	 * 临时工程商列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerTemporaryEngineer:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CustomerTemporaryEngineer customerTemporaryEngineer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerTemporaryEngineer> page = customerTemporaryEngineerService.findPage(new Page<CustomerTemporaryEngineer>(request, response), customerTemporaryEngineer); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑临时工程商表单页面
	 */
	@RequiresPermissions(value={"customer:customerTemporaryEngineer:view","customer:customerTemporaryEngineer:add","customer:customerTemporaryEngineer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerTemporaryEngineer customerTemporaryEngineer, Model model) {
		model.addAttribute("customerTemporaryEngineer", customerTemporaryEngineer);
		if(StringUtils.isBlank(customerTemporaryEngineer.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/customerTemporaryEngineerForm";
	}

	/**
	 * 保存临时工程商
	 */
	@RequiresPermissions(value={"customer:customerTemporaryEngineer:add","customer:customerTemporaryEngineer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CustomerTemporaryEngineer customerTemporaryEngineer, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customerTemporaryEngineer)){
			return form(customerTemporaryEngineer, model);
		}
		//新增或编辑表单保存
		customerTemporaryEngineerService.save(customerTemporaryEngineer);//保存
		addMessage(redirectAttributes, "保存临时工程商成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerTemporaryEngineer/?repage";
	}
	
	/**
	 * 删除临时工程商
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerTemporaryEngineer:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CustomerTemporaryEngineer customerTemporaryEngineer, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		customerTemporaryEngineerService.delete(customerTemporaryEngineer);
		j.setMsg("删除临时工程商成功");
		return j;
	}
	
	/**
	 * 批量删除临时工程商
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerTemporaryEngineer:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerTemporaryEngineerService.delete(customerTemporaryEngineerService.get(id));
		}
		j.setMsg("删除临时工程商成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerTemporaryEngineer:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CustomerTemporaryEngineer customerTemporaryEngineer, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "临时工程商"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerTemporaryEngineer> page = customerTemporaryEngineerService.findPage(new Page<CustomerTemporaryEngineer>(request, response, -1), customerTemporaryEngineer);
    		new ExportExcel("临时工程商", CustomerTemporaryEngineer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出临时工程商记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:customerTemporaryEngineer:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerTemporaryEngineer> list = ei.getDataList(CustomerTemporaryEngineer.class);
			for (CustomerTemporaryEngineer customerTemporaryEngineer : list){
				try{
					customerTemporaryEngineerService.save(customerTemporaryEngineer);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条临时工程商记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条临时工程商记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入临时工程商失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerTemporaryEngineer/?repage";
    }
	
	/**
	 * 下载导入临时工程商数据模板
	 */
	@RequiresPermissions("customer:customerTemporaryEngineer:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "临时工程商数据导入模板.xlsx";
    		List<CustomerTemporaryEngineer> list = Lists.newArrayList(); 
    		new ExportExcel("临时工程商数据", CustomerTemporaryEngineer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerTemporaryEngineer/?repage";
    }

	/**
	 * 审核
	 */
	@ResponseBody
	@RequestMapping(value = "isCheck")
	public AjaxJson isCheck(CustomerTemporaryEngineer customerTemporaryEngineer, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		CustomerTemporaryEngineer entity = customerTemporaryEngineerService.get(customerTemporaryEngineer.getId());
		String isCheck = customerTemporaryEngineer.getStatus();
		entity.setStatus(isCheck);
		if("1".equals(isCheck)){
			//审核通过,入库供应商表
			try{
				DealerAccount dealerAccount = customerTemporaryEngineerService.perfectInfo(entity);
				//消息模板
				String mobile = dealerAccount.getMobile();
				String endPass = "";
				if(mobile==null || mobile.length()<4){
					endPass = "123456";
				}else{
					endPass = mobile.substring(5,11);
				}
				String content = "管理员已审核通过您的注册信息，您的非标用户名为:"+ dealerAccount.getLoginName() + "密码:"+endPass;
				//发送成功审核后信息
				String result = SendMsgApi.SendMessage(dealerAccount.getMobile(),content);
				saveMessageRecord(dealerAccount.getMobile(),"管理员已审核通过您的注册信息，您的非标用户名为:"+ dealerAccount.getLoginName() + "密码:"+endPass,result);
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			String content = "管理员已拒绝您的注册信息，请联系管理员或等待客服联系";
			//发送成功审核后信息
			String result="";
			try {
				result = SendMsgApi.SendMessage(customerTemporaryEngineer.getCusMobile(),content);
				logger.info("短信结果===>"+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			saveMessageRecord(customerTemporaryEngineer.getCusMobile(),"非标审核拒绝" +customerTemporaryEngineer.getCusMobile(),result);
		}
		customerTemporaryEngineerService.save(entity);
		j.setMsg("审核成功");
		return j;
	}

	/**
	 * @description 短息履历
	 * @method
	 *
	 * @return
	 * @date:  15:28:06
	 * @author:hdx
	 */
	public void saveMessageRecord(String mobile,String content,String result){
		User user = UserUtils.getUser();
		WxMessageRecord wxMessageRecord = new WxMessageRecord();
		wxMessageRecord.setAddPerson(user.getId());
		wxMessageRecord.setAddTime(new Date());
		wxMessageRecord.setContent(content);
		wxMessageRecord.setResult(result);
		wxMessageRecord.setMobile(mobile);
		wxMessageRecordService.save(wxMessageRecord);
	}

}