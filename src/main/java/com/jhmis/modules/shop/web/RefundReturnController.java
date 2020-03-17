/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jhmis.modules.shop.entity.RefundReturn;
import com.jhmis.modules.shop.service.RefundReturnService;

/**
 * 退款管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/refundReturn")
public class RefundReturnController extends BaseController {

	@Autowired
	private RefundReturnService refundReturnService;
	
	@ModelAttribute
	public RefundReturn get(@RequestParam(required=false) String id) {
		RefundReturn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = refundReturnService.get(id);
		}
		if (entity == null){
			entity = new RefundReturn();
		}
		return entity;
	}
	
	/**
	 * 退款列表页面
	 */
	@RequiresPermissions("shop:refundReturn:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/refundReturnList";
	}
	
	/**
	 * 退款列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReturn:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(RefundReturn refundReturn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RefundReturn> page = refundReturnService.findPage(new Page<RefundReturn>(request, response), refundReturn); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑退款表单页面
	 */
	@RequiresPermissions(value={"shop:refundReturn:view","shop:refundReturn:add","shop:refundReturn:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RefundReturn refundReturn, Model model) {
		model.addAttribute("refundReturn", refundReturn);
		if(StringUtils.isBlank(refundReturn.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/refundReturnForm";
	}

	/**
	 * 保存退款
	 */
	@RequiresPermissions(value={"shop:refundReturn:add","shop:refundReturn:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(RefundReturn refundReturn, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, refundReturn)){
			return form(refundReturn, model);
		}
		//新增或编辑表单保存
		refundReturnService.save(refundReturn);//保存
		addMessage(redirectAttributes, "保存退款成功");
		return "redirect:"+Global.getAdminPath()+"/shop/refundReturn/?repage";
	}
	
	/**
	 * 删除退款
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReturn:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(RefundReturn refundReturn, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		refundReturnService.delete(refundReturn);
		j.setMsg("删除退款成功");
		return j;
	}
	
	/**
	 * 批量删除退款
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReturn:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			refundReturnService.delete(refundReturnService.get(id));
		}
		j.setMsg("删除退款成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReturn:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(RefundReturn refundReturn, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "退款"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RefundReturn> page = refundReturnService.findPage(new Page<RefundReturn>(request, response, -1), refundReturn);
    		new ExportExcel("退款", RefundReturn.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出退款记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:refundReturn:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RefundReturn> list = ei.getDataList(RefundReturn.class);
			for (RefundReturn refundReturn : list){
				try{
					refundReturnService.save(refundReturn);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条退款记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条退款记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入退款失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/refundReturn/?repage";
    }
	
	/**
	 * 下载导入退款数据模板
	 */
	@RequiresPermissions("shop:refundReturn:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "退款数据导入模板.xlsx";
    		List<RefundReturn> list = Lists.newArrayList(); 
    		new ExportExcel("退款数据", RefundReturn.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/refundReturn/?repage";
    }

}