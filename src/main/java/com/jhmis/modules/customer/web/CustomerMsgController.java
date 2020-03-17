/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.modules.customer.entity.CustomerProjectProduct;
import com.jhmis.modules.customer.entity.CustomerProjectProductDetail;
import com.jhmis.modules.customer.service.CustomerProjectInfoService;
import com.jhmis.modules.customer.service.CustomerProjectProductDetailService;
import com.jhmis.modules.customer.service.CustomerProjectProductService;
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
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.service.CustomerMsgService;

/**
 * 客单需求Controller
 * @author hdx
 * @version 2019-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerMsg")
public class CustomerMsgController extends BaseController {

	@Autowired
	private CustomerMsgService customerMsgService;

	@Autowired
	private CustomerProjectInfoService customerProjectInfoService;

	@Autowired
	private CustomerProjectProductService customerProjectProductService;

	@Autowired
	private CustomerProjectProductDetailService customerProjectProductDetailService;


	@ModelAttribute
	public CustomerMsg get(@RequestParam(required=false) String id) {
		CustomerMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerMsgService.get(id);
		}
		if (entity == null){
			entity = new CustomerMsg();
		}
		return entity;
	}
	
	/**
	 * 客单需求列表页面
	 */
	@RequiresPermissions("customer:customerMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/customerMsgList";
	}
	
	/**
	 * 客单需求列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CustomerMsg customerMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerMsg> page = customerMsgService.findPage(new Page<CustomerMsg>(request, response), customerMsg); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客单需求表单页面
	 */
	@RequiresPermissions(value={"customer:customerMsg:view","customer:customerMsg:add","customer:customerMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CustomerMsg customerMsg, Model model) {
		model.addAttribute("customerMsg", customerMsg);
		if(StringUtils.isBlank(customerMsg.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}else{
			//如果存在.查询是否有效
			if("0".equals(customerMsg.getStatus())){
				return "modules/customer/customerMsgForm";
			}else{
				//有效则查询漏斗项目
				CustomerProjectInfo customerProjectInfo = customerProjectInfoService.get(customerMsg.getMsgId());
				//漏斗产品信息
				if(customerProjectInfo!=null){
					customerMsg.setCustomerProjectInfo(customerProjectInfo);
					CustomerProjectProduct customerProjectProduct = new CustomerProjectProduct();
					customerProjectProduct.setProjectcode(customerProjectInfo.getProjectcode());
					//漏斗产品产品组
					List<CustomerProjectProduct> listCustomerProjectProduct = customerProjectProductService.findList(customerProjectProduct);
					customerMsg.setListCustomerProjectProduct(listCustomerProjectProduct);
					//漏斗产品详情
					if(listCustomerProjectProduct!=null && listCustomerProjectProduct.size()>0){
						CustomerProjectProductDetail customerProjectProductDetail = new CustomerProjectProductDetail();
						customerProjectProductDetail.setProjectcode(customerMsg.getMsgId());
						List<CustomerProjectProductDetail> listCustomerProjectProductDetail =  customerProjectProductDetailService.findList(customerProjectProductDetail);
						customerMsg.setListCustomerProjectProductDetail(listCustomerProjectProductDetail);
			 		}
				}
				model.addAttribute("customerMsg", customerMsg);
			}
		}
		return "modules/customer/customerMsgForm";
	}

	/**
	 * 保存客单需求
	 */
	@RequiresPermissions(value={"customer:customerMsg:add","customer:customerMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CustomerMsg customerMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customerMsg)){
			return form(customerMsg, model);
		}
		//新增或编辑表单保存
		customerMsgService.save(customerMsg);//保存
		addMessage(redirectAttributes, "保存客单需求成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerMsg/?repage";
	}
	
	/**
	 * 删除客单需求
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CustomerMsg customerMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		customerMsgService.delete(customerMsg);
		j.setMsg("删除客单需求成功");
		return j;
	}
	
	/**
	 * 批量删除客单需求
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerMsgService.delete(customerMsgService.get(id));
		}
		j.setMsg("删除客单需求成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerMsg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CustomerMsg customerMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客单需求"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerMsg> page = customerMsgService.findPage(new Page<CustomerMsg>(request, response, -1), customerMsg);
    		new ExportExcel("客单需求", CustomerMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客单需求记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:customerMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerMsg> list = ei.getDataList(CustomerMsg.class);
			for (CustomerMsg customerMsg : list){
				try{
					customerMsgService.save(customerMsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客单需求记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客单需求记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客单需求失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerMsg/?repage";
    }
	
	/**
	 * 下载导入客单需求数据模板
	 */
	@RequiresPermissions("customer:customerMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客单需求数据导入模板.xlsx";
    		List<CustomerMsg> list = Lists.newArrayList(); 
    		new ExportExcel("客单需求数据", CustomerMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerMsg/?repage";
    }

}