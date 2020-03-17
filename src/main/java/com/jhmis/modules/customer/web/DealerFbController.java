/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.shop.entity.dealer.DealerAccount;
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
import com.jhmis.modules.customer.entity.DealerFb;
import com.jhmis.modules.customer.service.DealerFbService;

/**
 * 非标客户Controller
 * @author hdx
 * @version 2019-05-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/dealerFb")
public class DealerFbController extends BaseController {

	@Autowired
	private DealerFbService dealerFbService;
	
	@ModelAttribute
	public DealerFb get(@RequestParam(required=false) String id) {
		DealerFb entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dealerFbService.get(id);
		}
		if (entity == null){
			entity = new DealerFb();
		}
		return entity;
	}
	
	/**
	 * 非标客户列表页面
	 */
	@RequiresPermissions("customer:dealerFb:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/dealerFbList";
	}
	
	/**
	 * 非标客户列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:dealerFb:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DealerFb dealerFb, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DealerFb> page = dealerFbService.findPage(new Page<DealerFb>(request, response), dealerFb); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑非标客户表单页面
	 */
	@RequiresPermissions(value={"customer:dealerFb:view","customer:dealerFb:add","customer:dealerFb:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DealerFb dealerFb, Model model) {
		model.addAttribute("dealerFb", dealerFb);
		if(StringUtils.isBlank(dealerFb.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/dealerFbForm";
	}

	/**
	 * 保存非标客户
	 */
	@RequiresPermissions(value={"customer:dealerFb:add","customer:dealerFb:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DealerFb dealerFb, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dealerFb)){
			return form(dealerFb, model);
		}
		//新增或编辑表单保存
		dealerFbService.save(dealerFb);//保存
		addMessage(redirectAttributes, "保存非标客户成功");
		return "redirect:"+Global.getAdminPath()+"/customer/dealerFb/?repage";
	}
	
	/**
	 * 删除非标客户
	 */
	@ResponseBody
	@RequiresPermissions("customer:dealerFb:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DealerFb dealerFb, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		dealerFbService.delete(dealerFb);
		j.setMsg("删除非标客户成功");
		return j;
	}

	/**
	 * 审核
	 */
	@ResponseBody
	@RequestMapping(value = "isCheck")
	public AjaxJson isCheck(DealerFb dealerFb, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		DealerFb entity = dealerFbService.get(dealerFb.getId());
		String isCheck = dealerFb.getIsCheck();
		entity.setIsCheck(dealerFb.getIsCheck());
		if("1".equals(isCheck)){
			//审核通过,入库供应商表
			try{
				DealerAccount dealerAccount = dealerFbService.perfectInfo(entity);
				//消息模板
				String content = "管理员已审核通过您的注册信息，您的非标用户名为:"+ dealerAccount.getLoginName() + "密码:Haier"+dealerAccount.getMobile();
				//发送成功审核后信息
				String result = SendMsgApi.SendMessage(dealerAccount.getMobile(),content);
				logger.info("短信发送结果"+result);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		dealerFbService.save(entity);
		j.setMsg("审核成功");
		return j;
	}
	
	/**
	 * 批量删除非标客户
	 */
	@ResponseBody
	@RequiresPermissions("customer:dealerFb:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dealerFbService.delete(dealerFbService.get(id));
		}
		j.setMsg("删除非标客户成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:dealerFb:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DealerFb dealerFb, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "非标客户"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DealerFb> page = dealerFbService.findPage(new Page<DealerFb>(request, response, -1), dealerFb);
    		new ExportExcel("非标客户", DealerFb.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出非标客户记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:dealerFb:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DealerFb> list = ei.getDataList(DealerFb.class);
			for (DealerFb dealerFb : list){
				try{
					dealerFbService.save(dealerFb);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条非标客户记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条非标客户记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入非标客户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/dealerFb/?repage";
    }
	
	/**
	 * 下载导入非标客户数据模板
	 */
	@RequiresPermissions("customer:dealerFb:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "非标客户数据导入模板.xlsx";
    		List<DealerFb> list = Lists.newArrayList(); 
    		new ExportExcel("非标客户数据", DealerFb.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/dealerFb/?repage";
    }

}