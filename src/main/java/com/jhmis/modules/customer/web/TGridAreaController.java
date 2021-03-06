/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.web;

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
import com.jhmis.modules.customer.entity.TGridArea;
import com.jhmis.modules.customer.service.TGridAreaService;

/**
 * 网格码表Controller
 * @author hdx
 * @version 2020-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/tGridArea")
public class TGridAreaController extends BaseController {

	@Autowired
	private TGridAreaService tGridAreaService;
	
	@ModelAttribute
	public TGridArea get(@RequestParam(required=false) String id) {
		TGridArea entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tGridAreaService.get(id);
		}
		if (entity == null){
			entity = new TGridArea();
		}
		return entity;
	}
	
	/**
	 * 网格码表列表页面
	 */
	@RequiresPermissions("customer:tGridArea:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/customer/tGridAreaList";
	}
	
	/**
	 * 网格码表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("customer:tGridArea:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TGridArea tGridArea, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TGridArea> page = tGridAreaService.findPage(new Page<TGridArea>(request, response), tGridArea); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑网格码表表单页面
	 */
	@RequiresPermissions(value={"customer:tGridArea:view","customer:tGridArea:add","customer:tGridArea:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(TGridArea tGridArea, Model model) {
		model.addAttribute("tGridArea", tGridArea);
		if(StringUtils.isBlank(tGridArea.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/customer/tGridAreaForm";
	}

	/**
	 * 保存网格码表
	 */
	@RequiresPermissions(value={"customer:tGridArea:add","customer:tGridArea:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(TGridArea tGridArea, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, tGridArea)){
			return form(tGridArea, model);
		}
		//新增或编辑表单保存
		tGridAreaService.save(tGridArea);//保存
		addMessage(redirectAttributes, "保存网格码表成功");
		return "redirect:"+Global.getAdminPath()+"/customer/tGridArea/?repage";
	}
	
	/**
	 * 删除网格码表
	 */
	@ResponseBody
	@RequiresPermissions("customer:tGridArea:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(TGridArea tGridArea, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		tGridAreaService.delete(tGridArea);
		j.setMsg("删除网格码表成功");
		return j;
	}
	
	/**
	 * 批量删除网格码表
	 */
	@ResponseBody
	@RequiresPermissions("customer:tGridArea:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			tGridAreaService.delete(tGridAreaService.get(id));
		}
		j.setMsg("删除网格码表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("customer:tGridArea:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(TGridArea tGridArea, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "网格码表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TGridArea> page = tGridAreaService.findPage(new Page<TGridArea>(request, response, -1), tGridArea);
    		new ExportExcel("网格码表", TGridArea.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出网格码表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("customer:tGridArea:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TGridArea> list = ei.getDataList(TGridArea.class);
			for (TGridArea tGridArea : list){
				try{
					tGridAreaService.save(tGridArea);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条网格码表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条网格码表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入网格码表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/tGridArea/?repage";
    }
	
	/**
	 * 下载导入网格码表数据模板
	 */
	@RequiresPermissions("customer:tGridArea:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "网格码表数据导入模板.xlsx";
    		List<TGridArea> list = Lists.newArrayList(); 
    		new ExportExcel("网格码表数据", TGridArea.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/tGridArea/?repage";
    }

}