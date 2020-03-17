/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import java.util.Date;
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
import com.jhmis.modules.shop.entity.RefundReason;
import com.jhmis.modules.shop.service.RefundReasonService;

/**
 * 退货原因管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/refundReason")
public class RefundReasonController extends BaseController {

	@Autowired
	private RefundReasonService refundReasonService;
	
	@ModelAttribute
	public RefundReason get(@RequestParam(required=false) String id) {
		RefundReason entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = refundReasonService.get(id);
		}
		if (entity == null){
			entity = new RefundReason();
		}
		return entity;
	}
	
	/**
	 * 退货原因列表页面
	 */
	@RequiresPermissions("shop:refundReason:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/refundReasonList";
	}
	
	/**
	 * 退货原因列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReason:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(RefundReason refundReason, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<RefundReason> page = refundReasonService.findPage(new Page<RefundReason>(request, response), refundReason); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑退货原因表单页面
	 */
	@RequiresPermissions(value={"shop:refundReason:view","shop:refundReason:add","shop:refundReason:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(RefundReason refundReason, Model model) {
		model.addAttribute("refundReason", refundReason);
		return "modules/shop/refundReasonForm";
	}

	/**
	 * 保存退货原因
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:refundReason:add","shop:refundReason:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(RefundReason refundReason, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		refundReason.setUpdateTime(new Date());
		refundReasonService.save(refundReason);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存退货原因成功");
		return j;
	}
	
	/**
	 * 删除退货原因
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReason:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(RefundReason refundReason, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		refundReasonService.delete(refundReason);
		j.setMsg("删除退货原因成功");
		return j;
	}
	
	/**
	 * 批量删除退货原因
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReason:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			refundReasonService.delete(refundReasonService.get(id));
		}
		j.setMsg("删除退货原因成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:refundReason:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(RefundReason refundReason, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "退货原因"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<RefundReason> page = refundReasonService.findPage(new Page<RefundReason>(request, response, -1), refundReason);
    		new ExportExcel("退货原因", RefundReason.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出退货原因记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:refundReason:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<RefundReason> list = ei.getDataList(RefundReason.class);
			for (RefundReason refundReason : list){
				try{
					refundReasonService.save(refundReason);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条退货原因记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条退货原因记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入退货原因失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/refundReason/?repage";
    }
	
	/**
	 * 下载导入退货原因数据模板
	 */
	@RequiresPermissions("shop:refundReason:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "退货原因数据导入模板.xlsx";
    		List<RefundReason> list = Lists.newArrayList(); 
    		new ExportExcel("退货原因数据", RefundReason.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/refundReason/?repage";
    }

}