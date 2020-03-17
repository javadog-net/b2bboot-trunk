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
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.service.WxGroupUserService;

/**
 * 群组成员Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxGroupUser")
public class WxGroupUserController extends BaseController {

	@Autowired
	private WxGroupUserService wxGroupUserService;
	
	@ModelAttribute
	public WxGroupUser get(@RequestParam(required=false) String id) {
		WxGroupUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxGroupUserService.get(id);
		}
		if (entity == null){
			entity = new WxGroupUser();
		}
		return entity;
	}
	
	/**
	 * 群组成员列表页面
	 */
	@RequiresPermissions("wechat:wxGroupUser:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxGroupUserList";
	}
	
	/**
	 * 群组成员列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupUser:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxGroupUser wxGroupUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxGroupUser> page = wxGroupUserService.findPage(new Page<WxGroupUser>(request, response), wxGroupUser); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑群组成员表单页面
	 */
	@RequiresPermissions(value={"wechat:wxGroupUser:view","wechat:wxGroupUser:add","wechat:wxGroupUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxGroupUser wxGroupUser, Model model) {
		model.addAttribute("wxGroupUser", wxGroupUser);
		if(StringUtils.isBlank(wxGroupUser.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxGroupUserForm";
	}

	/**
	 * 保存群组成员
	 */
	@RequiresPermissions(value={"wechat:wxGroupUser:add","wechat:wxGroupUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxGroupUser wxGroupUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxGroupUser)){
			return form(wxGroupUser, model);
		}
		//新增或编辑表单保存
		wxGroupUserService.save(wxGroupUser);//保存
		addMessage(redirectAttributes, "保存群组成员成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroupUser/?repage";
	}
	
	/**
	 * 删除群组成员
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupUser:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxGroupUser wxGroupUser, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxGroupUserService.delete(wxGroupUser);
		j.setMsg("删除群组成员成功");
		return j;
	}
	
	/**
	 * 批量删除群组成员
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupUser:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxGroupUserService.delete(wxGroupUserService.get(id));
		}
		j.setMsg("删除群组成员成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupUser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxGroupUser wxGroupUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "群组成员"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxGroupUser> page = wxGroupUserService.findPage(new Page<WxGroupUser>(request, response, -1), wxGroupUser);
    		new ExportExcel("群组成员", WxGroupUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出群组成员记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxGroupUser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxGroupUser> list = ei.getDataList(WxGroupUser.class);
			for (WxGroupUser wxGroupUser : list){
				try{
					wxGroupUserService.save(wxGroupUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条群组成员记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条群组成员记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入群组成员失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroupUser/?repage";
    }
	
	/**
	 * 下载导入群组成员数据模板
	 */
	@RequiresPermissions("wechat:wxGroupUser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "群组成员数据导入模板.xlsx";
    		List<WxGroupUser> list = Lists.newArrayList(); 
    		new ExportExcel("群组成员数据", WxGroupUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroupUser/?repage";
    }

}