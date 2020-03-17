/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.cms.entity.CmsModel;
import com.jhmis.modules.cms.service.CmsModelService;
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
import com.jhmis.modules.cms.entity.Position;
import com.jhmis.modules.cms.service.PositionService;

/**
 * 位置管理Controller
 * @author lydia
 * @version 2019-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/position")
public class PositionController extends BaseController {

	@Autowired
	private PositionService positionService;
	@Autowired
	private CmsModelService modelService;
	
	@ModelAttribute
	public Position get(@RequestParam(required=false) String id) {
		Position entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = positionService.get(id);
		}
		if (entity == null){
			entity = new Position();
		}
		return entity;
	}
	
	/**
	 * 位置管理列表页面
	 */
	@RequiresPermissions("cms:position:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/positionList";
	}
	
	/**
	 * 位置管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:position:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Position position, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Position> page = positionService.findPage(new Page<Position>(request, response), position); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑位置管理表单页面
	 */
	@RequiresPermissions(value={"cms:position:view","cms:position:add","cms:position:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Position position, Model model) {
		//查询模型信息
		List<CmsModel> modelList = modelService.findCmsModelList();
		model.addAttribute("list",modelList);
		model.addAttribute("position", position);
		return "modules/cms/positionForm";
	}

	/**
	 * 保存位置管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"cms:position:add","cms:position:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Position position, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, position)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		positionService.save(position);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存位置管理成功");
		return j;
	}
	
	/**
	 * 删除位置管理
	 */
	@ResponseBody
	@RequiresPermissions("cms:position:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Position position, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		positionService.delete(position);
		j.setMsg("删除位置管理成功");
		return j;
	}
	
	/**
	 * 批量删除位置管理
	 */
	@ResponseBody
	@RequiresPermissions("cms:position:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			positionService.delete(positionService.get(id));
		}
		j.setMsg("删除位置管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:position:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Position position, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "位置管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Position> page = positionService.findPage(new Page<Position>(request, response, -1), position);
    		new ExportExcel("位置管理", Position.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出位置管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:position:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Position> list = ei.getDataList(Position.class);
			for (Position position : list){
				try{
					positionService.save(position);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条位置管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条位置管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入位置管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/position/?repage";
    }
	
	/**
	 * 下载导入位置管理数据模板
	 */
	@RequiresPermissions("cms:position:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "位置管理数据导入模板.xlsx";
    		List<Position> list = Lists.newArrayList(); 
    		new ExportExcel("位置管理数据", Position.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/position/?repage";
    }

}