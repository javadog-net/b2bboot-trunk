/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.CustomerComment;
import com.jhmis.modules.customer.service.CustomerCommentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

/**
 * 客单评论Controller
 * @author tc
 * @version 2020-01-21
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerComment")
public class CustomerCommentController extends BaseController {

	@Autowired
	private CustomerCommentService customerCommentService;
	
	@ModelAttribute
	public CustomerComment get(@RequestParam(required=false) String id) {
		CustomerComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerCommentService.get(id);
		}
		if (entity == null){
			entity = new CustomerComment();
		}
		return entity;
	}
	
	/**
	 * 客单评论列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/customerCommentList";
	}
	
	/**
	 * 客单评论列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(CustomerComment customerComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerComment> page = customerCommentService.findPage(new Page<CustomerComment>(request, response), customerComment);
		List<CustomerComment> list=page.getList();

		if(null !=list&&list.size()>0){
			for(CustomerComment cc:list){
				String customerType="";
				String type=cc.getCommentType();
				String [] arr=type.split(",");
				for(int i=0;i<arr.length;i++){
					if("1".equals(arr[i])){
						customerType="系统使用,"+customerType;
					}
					if("2".equals(arr[i])){
						customerType="漏斗项目,"+customerType;
					}
					if("3".equals(arr[i])){
						customerType="产品订单,"+customerType;
					}
					if("4".equals(arr[i])){
						customerType="物流,"+customerType;
					}
					if("5".equals(arr[i])){
						customerType="安装及售后,"+customerType;
					}
					if("6".equals(arr[i])){
						customerType="培训,"+customerType;
					}
					if("7".equals(arr[i])){
						customerType="其他,"+customerType;
					}
				}

                cc.setCommentType(customerType.substring(0,customerType.length()-1));
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客单评论表单页面
	 */
	@RequestMapping(value = "form")
	public String form(CustomerComment customerComment, Model model) {
		model.addAttribute("customerComment", customerComment);
		if(StringUtils.isBlank(customerComment.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/customerCommentForm";
	}

	/**
	 * 保存客单评论
	 */
	@RequestMapping(value = "save")
	public String save(CustomerComment customerComment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, customerComment)){
			return form(customerComment, model);
		}
		//新增或编辑表单保存
		customerCommentService.save(customerComment);//保存
		addMessage(redirectAttributes, "保存客单评论成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerComment/?repage";
	}
	
	/**
	 * 删除客单评论
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerComment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CustomerComment customerComment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		customerCommentService.delete(customerComment);
		j.setMsg("删除客单评论成功");
		return j;
	}
	
	/**
	 * 批量删除客单评论
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			customerCommentService.delete(customerCommentService.get(id));
		}
		j.setMsg("删除客单评论成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:customerComment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CustomerComment customerComment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客单评论"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CustomerComment> page = customerCommentService.findPage(new Page<CustomerComment>(request, response, -1), customerComment);
    		new ExportExcel("客单评论", CustomerComment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客单评论记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CustomerComment> list = ei.getDataList(CustomerComment.class);
			for (CustomerComment customerComment : list){
				try{
					customerCommentService.save(customerComment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客单评论记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条客单评论记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入客单评论失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerComment/?repage";
    }
	
	/**
	 * 下载导入客单评论数据模板
	 */
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客单评论数据导入模板.xlsx";
    		List<CustomerComment> list = Lists.newArrayList(); 
    		new ExportExcel("客单评论数据", CustomerComment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerComment/?repage";
    }



}