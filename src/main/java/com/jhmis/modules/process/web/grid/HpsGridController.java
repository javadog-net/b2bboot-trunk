/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.grid;

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
import com.jhmis.modules.process.entity.grid.HpsGrid;
import com.jhmis.modules.process.service.grid.HpsGridService;

/**
 * 省市区匹配工贸Controller
 * @author mll
 * @version 2019-09-25
 */
@Controller
@RequestMapping(value = "${adminPath}/process/grid/hpsGrid")
public class HpsGridController extends BaseController {

	@Autowired
	private HpsGridService hpsGridService;
	
	@ModelAttribute
	public HpsGrid get(@RequestParam(required=false) String id) {
		HpsGrid entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = hpsGridService.get(id);
		}
		if (entity == null){
			entity = new HpsGrid();
		}
		return entity;
	}
	
	/**
	 * 省市区匹配工贸列表页面
	 */
	@RequiresPermissions("process:grid:hpsGrid:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/grid/hpsGridList";
	}
	
	/**
	 * 省市区匹配工贸列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:grid:hpsGrid:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(HpsGrid hpsGrid, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<HpsGrid> page = hpsGridService.findPage(new Page<HpsGrid>(request, response), hpsGrid); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑省市区匹配工贸表单页面
	 */
	@RequiresPermissions(value={"process:grid:hpsGrid:view","process:grid:hpsGrid:add","process:grid:hpsGrid:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HpsGrid hpsGrid, Model model) {
		model.addAttribute("hpsGrid", hpsGrid);
		if(StringUtils.isBlank(hpsGrid.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/grid/hpsGridForm";
	}

	/**
	 * 保存省市区匹配工贸
	 */
	@RequiresPermissions(value={"process:grid:hpsGrid:add","process:grid:hpsGrid:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HpsGrid hpsGrid, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, hpsGrid)){
			return form(hpsGrid, model);
		}
		//新增或编辑表单保存
		hpsGridService.save(hpsGrid);//保存
		addMessage(redirectAttributes, "保存省市区匹配工贸成功");
		return "redirect:"+Global.getAdminPath()+"/process/grid/hpsGrid/?repage";
	}
	
	/**
	 * 删除省市区匹配工贸
	 */
	@ResponseBody
	@RequiresPermissions("process:grid:hpsGrid:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(HpsGrid hpsGrid, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		hpsGridService.delete(hpsGrid);
		j.setMsg("删除省市区匹配工贸成功");
		return j;
	}
	
	/**
	 * 批量删除省市区匹配工贸
	 */
	@ResponseBody
	@RequiresPermissions("process:grid:hpsGrid:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			hpsGridService.delete(hpsGridService.get(id));
		}
		j.setMsg("删除省市区匹配工贸成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:grid:hpsGrid:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(HpsGrid hpsGrid, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "省市区匹配工贸"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<HpsGrid> page = hpsGridService.findPage(new Page<HpsGrid>(request, response, -1), hpsGrid);
    		new ExportExcel("省市区匹配工贸", HpsGrid.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出省市区匹配工贸记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:grid:hpsGrid:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<HpsGrid> list = ei.getDataList(HpsGrid.class);
			for (HpsGrid hpsGrid : list){
				try{
					hpsGridService.save(hpsGrid);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条省市区匹配工贸记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条省市区匹配工贸记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入省市区匹配工贸失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/grid/hpsGrid/?repage";
    }
	
	/**
	 * 下载导入省市区匹配工贸数据模板
	 */
	@RequiresPermissions("process:grid:hpsGrid:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "省市区匹配工贸数据导入模板.xlsx";
    		List<HpsGrid> list = Lists.newArrayList(); 
    		new ExportExcel("省市区匹配工贸数据", HpsGrid.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/grid/hpsGrid/?repage";
    }
	

}