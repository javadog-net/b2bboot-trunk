/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

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
import com.jhmis.modules.wechat.entity.Design;
import com.jhmis.modules.wechat.service.DesignService;

/**
 * 免费设计Controller
 * @author abc
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/design")
public class DesignController extends BaseController {

	@Autowired
	private DesignService designService;
	
	@ModelAttribute
	public Design get(@RequestParam(required=false) String id) {
		Design entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = designService.get(id);
		}
		if (entity == null){
			entity = new Design();
		}
		return entity;
	}
	
	/**
	 * 免费设计列表页面
	 */
	@RequiresPermissions("wechat:design:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/chat/designList";
	}
	
	/**
	 * 免费设计列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:design:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Design design, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Design> page = designService.findPage(new Page<Design>(request, response), design); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑免费设计表单页面
	 */
	@RequiresPermissions(value={"wechat:design:view","wechat:design:add","wechat:design:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Design design, Model model) {
		model.addAttribute("design", design);
		if(StringUtils.isBlank(design.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/chat/designForm";
	}

	/**
	 * 保存免费设计
	 */
	@RequiresPermissions(value={"wechat:design:add","wechat:design:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Design design, Model model, RedirectAttributes redirectAttributes) throws Exception{
		//新增或编辑表单保存
		designService.save(design);//保存
		addMessage(redirectAttributes, "保存免费设计成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/design/?repage";
	}
	
	/**
	 * 删除免费设计
	 */
	@ResponseBody
	@RequiresPermissions("wechat:design:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Design design, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		designService.delete(design);
		j.setMsg("删除免费设计成功");
		return j;
	}
	
	/**
	 * 批量删除免费设计
	 */
	@ResponseBody
	@RequiresPermissions("wechat:design:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			designService.delete(designService.get(id));
		}
		j.setMsg("删除免费设计成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:design:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Design design, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "免费设计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Design> page = designService.findPage(new Page<Design>(request, response, -1), design);
    		new ExportExcel("免费设计", Design.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出免费设计记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:design:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Design> list = ei.getDataList(Design.class);
			for (Design design : list){
				try{
					designService.save(design);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条免费设计记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条免费设计记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入免费设计失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/design/?repage";
    }
	
	/**
	 * 下载导入免费设计数据模板
	 */
	@RequiresPermissions("wechat:design:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "免费设计数据导入模板.xlsx";
    		List<Design> list = Lists.newArrayList(); 
    		new ExportExcel("免费设计数据", Design.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/design/?repage";
    }

}