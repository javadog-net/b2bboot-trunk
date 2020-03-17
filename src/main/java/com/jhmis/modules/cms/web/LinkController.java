/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Link;
import com.jhmis.modules.cms.service.LinkService;
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
 * 链接管理Controller
 * @author lydia
 * @version 2019-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/link")
public class LinkController extends BaseController {

	@Autowired
	private LinkService linkService;
	
	@ModelAttribute
	public Link get(@RequestParam(required=false) String id) {
		Link entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = linkService.get(id);
		}
		if (entity == null){
			entity = new Link();
		}
		return entity;
	}
	
	/**
	 * 链接管理列表页面
	 */
	@RequiresPermissions("cms:link:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/linkList";
	}
	
	/**
	 * 链接管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:link:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Link link, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Link> page = linkService.findPage(new Page<Link>(request, response), link); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑链接管理表单页面
	 */
	@RequiresPermissions(value={"cms:link:view","cms:link:add","cms:link:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Link link, Model model) {
		model.addAttribute("link", link);
		return "modules/cms/linkForm";
	}

	/**
	 * 保存链接管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"cms:link:add","cms:link:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Link link, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, link)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		linkService.save(link);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存链接管理成功");
		return j;
	}
	
	/**
	 * 删除链接管理
	 */
	@ResponseBody
	@RequiresPermissions("cms:link:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Link link, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		linkService.delete(link);
		j.setMsg("删除链接管理成功");
		return j;
	}
	
	/**
	 * 批量删除链接管理
	 */
	@ResponseBody
	@RequiresPermissions("cms:link:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			linkService.delete(linkService.get(id));
		}
		j.setMsg("删除链接管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:link:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Link link, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "链接管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Link> page = linkService.findPage(new Page<Link>(request, response, -1), link);
    		new ExportExcel("链接管理", Link.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出链接管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:link:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Link> list = ei.getDataList(Link.class);
			for (Link link : list){
				try{
					linkService.save(link);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条链接管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条链接管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入链接管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/link/?repage";
    }
	
	/**
	 * 下载导入链接管理数据模板
	 */
	@RequiresPermissions("cms:link:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "链接管理数据导入模板.xlsx";
    		List<Link> list = Lists.newArrayList(); 
    		new ExportExcel("链接管理数据", Link.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/link/?repage";
    }

}