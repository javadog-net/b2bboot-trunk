/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Navigation;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.NavigationService;
import com.jhmis.modules.cms.utils.CmsEnum;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导航管理Controller
 * @author lydia
 * @version 2019-09-05
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/navigation")
public class NavigationController extends BaseController {

	@Autowired
	private NavigationService navigationService;
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public Navigation get(@RequestParam(required=false) String id) {
		Navigation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = navigationService.get(id);
		}
		if (entity == null){
			entity = new Navigation();
		}
		return entity;
	}
	
	/**
	 * 导航列表页面
	 */
	@RequiresPermissions("cms:navigation:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Navigation> list = Lists.newArrayList();
		List<Navigation> navigationList = navigationService.findList(new Navigation());
		Navigation.sortList(list,navigationList,"0");
		model.addAttribute("list",list);
		return "modules/cms/navigationList";
	}
	
	/**
	 * 查看，增加，编辑导航表单页面
	 */
	@RequiresPermissions(value={"cms:navigation:view","cms:navigation:add","cms:navigation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Navigation navigation, Model model) {
		model.addAttribute("navigation", navigation);
		if(StringUtils.isBlank(navigation.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		Navigation parent = null;
		if(navigation.getParent() == null || navigation.getParent().getId() == null || "0".equals(navigation.getParent().getId())||StringUtils.isEmpty(navigation.getParent().getId())){
			parent = new Navigation("0");
		}else{
			parent = navigationService.get(navigation.getParent().getId());
		}
		navigation.setParent(parent);
		model.addAttribute("navigation",navigation);
		return "modules/cms/navigationForm";
	}

	/**
	 * 保存导航
	 */
	@RequiresPermissions(value={"cms:navigation:add","cms:navigation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Navigation navigation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, navigation)){
			return form(navigation, model);
		}
		if(StringUtils.isNotEmpty(navigation.getLinkId())){

		}
		// 新增导航时，把导航需要设置为 最大序号
		//TODO 是否有必要设置为同一级导航下的最大序号？？
		if(navigation.getSort() == null){
			navigation.setSort(navigationService.maxNum(navigation));
		}
		//新增或编辑表单保存
		navigationService.save(navigation);//保存
		addMessage(redirectAttributes, "保存导航成功");
		return "redirect:"+Global.getAdminPath()+"/cms/navigation/?repage";
	}
	
	/**
	 * 删除导航
	 */
	@ResponseBody
	@RequiresPermissions("cms:navigation:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Navigation navigation, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		navigationService.delete(navigation);
		j.setMsg("删除导航成功");
		return j;
	}
	
	/**
	 * 批量删除导航
	 */
	@ResponseBody
	@RequiresPermissions("cms:navigation:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			navigationService.delete(navigationService.get(id));
		}
		j.setMsg("删除导航成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:navigation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Navigation navigation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导航"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Navigation> page = navigationService.findPage(new Page<Navigation>(request, response, -1), navigation);
    		new ExportExcel("导航", Navigation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导航记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:navigation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Navigation> list = ei.getDataList(Navigation.class);
			for (Navigation navigation : list){
				try{
					navigationService.save(navigation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导航记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条导航记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入导航失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/navigation/?repage";
    }
	
	/**
	 * 下载导入导航数据模板
	 */
	@RequiresPermissions("cms:navigation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导航数据导入模板.xlsx";
    		List<Navigation> list = Lists.newArrayList(); 
    		new ExportExcel("导航数据", Navigation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/navigation/?repage";
    }
    @ResponseBody
	@RequestMapping("/treeData")
	public List<Map<String,Object>> treeData(String extId){
		List<Map<String,Object>> mapList = Lists.newArrayList();
		List<Navigation> navigationList = navigationService.findList(null);
		for(Navigation n:navigationList){
			Map<String,Object> map = new HashMap<>();
			map.put("id",n.getId());
			if("0".equals(n.getParent().getId())){
				map.put("parent","#");
				Map<String, Object> state = Maps.newHashMap();
				state.put("opened", true);
				map.put("state", state);
			}else{
				map.put("parent", n.getParent().getId());
			}
			map.put("name", n.getName());
			map.put("text", n.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 导航排序
	 * @param navigation
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("/navigationSort")
	public String navigationSort(Navigation navigation, Model model, HttpServletResponse response){
		if(navigation != null && StringUtils.isNotEmpty(navigation.getId())){
				String msg="";
				try {
					if(CmsEnum.UP.getCode().equals(navigation.getSortType())){
						msg+="上升";
					}else if (CmsEnum.DOWN.getCode().equals(navigation.getSortType())) {
						msg+="下降";
					}
					navigationService.navigationSort(navigation);
				} catch (Exception e) {
					msg+="导航 "+navigation.getName()+" 失败:"+e.toString();
				}
				msg+="导航 "+navigation.getName()+" 成功";
		}
		return list(model);
	}
}