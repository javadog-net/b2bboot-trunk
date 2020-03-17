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
import com.jhmis.modules.wechat.entity.WxGroup;
import com.jhmis.modules.wechat.service.WxGroupService;

/**
 * 群组表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxGroup")
public class WxGroupController extends BaseController {

	@Autowired
	private WxGroupService wxGroupService;
	
	@ModelAttribute
	public WxGroup get(@RequestParam(required=false) String id) {
		WxGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxGroupService.get(id);
		}
		if (entity == null){
			entity = new WxGroup();
		}
		return entity;
	}
	
	/**
	 * 群组表列表页面
	 */
	@RequiresPermissions("wechat:wxGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxGroupList";
	}
	
	/**
	 * 群组表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroup:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxGroup wxGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxGroup> page = wxGroupService.findPage(new Page<WxGroup>(request, response), wxGroup); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑群组表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxGroup:view","wechat:wxGroup:add","wechat:wxGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxGroup wxGroup, Model model) {
		model.addAttribute("wxGroup", wxGroup);
		if(StringUtils.isBlank(wxGroup.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxGroupForm";
	}

	/**
	 * 保存群组表
	 */
	@RequiresPermissions(value={"wechat:wxGroup:add","wechat:wxGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxGroup wxGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxGroup)){
			return form(wxGroup, model);
		}
		//新增或编辑表单保存
		wxGroupService.save(wxGroup);//保存
		addMessage(redirectAttributes, "保存群组表成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroup/?repage";
	}
	
	/**
	 * 删除群组表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroup:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxGroup wxGroup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxGroupService.delete(wxGroup);
		j.setMsg("删除群组表成功");
		return j;
	}
	
	/**
	 * 批量删除群组表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroup:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxGroupService.delete(wxGroupService.get(id));
		}
		j.setMsg("删除群组表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroup:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxGroup wxGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "群组表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxGroup> page = wxGroupService.findPage(new Page<WxGroup>(request, response, -1), wxGroup);
    		new ExportExcel("群组表", WxGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出群组表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxGroup:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxGroup> list = ei.getDataList(WxGroup.class);
			for (WxGroup wxGroup : list){
				try{
					wxGroupService.save(wxGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条群组表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条群组表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入群组表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroup/?repage";
    }
	
	/**
	 * 下载导入群组表数据模板
	 */
	@RequiresPermissions("wechat:wxGroup:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "群组表数据导入模板.xlsx";
    		List<WxGroup> list = Lists.newArrayList(); 
    		new ExportExcel("群组表数据", WxGroup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroup/?repage";
    }

}