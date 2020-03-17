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
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.service.WxFriendMessageService;

/**
 * 聊天信息表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxFriendMessage")
public class WxFriendMessageController extends BaseController {

	@Autowired
	private WxFriendMessageService wxFriendMessageService;
	
	@ModelAttribute
	public WxFriendMessage get(@RequestParam(required=false) String id) {
		WxFriendMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxFriendMessageService.get(id);
		}
		if (entity == null){
			entity = new WxFriendMessage();
		}
		return entity;
	}
	
	/**
	 * 聊天信息表列表页面
	 */
	@RequiresPermissions("wechat:wxFriendMessage:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxFriendMessageList";
	}
	
	/**
	 * 聊天信息表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriendMessage:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxFriendMessage wxFriendMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxFriendMessage> page = wxFriendMessageService.findPage(new Page<WxFriendMessage>(request, response), wxFriendMessage); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑聊天信息表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxFriendMessage:view","wechat:wxFriendMessage:add","wechat:wxFriendMessage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxFriendMessage wxFriendMessage, Model model) {
		model.addAttribute("wxFriendMessage", wxFriendMessage);
		if(StringUtils.isBlank(wxFriendMessage.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxFriendMessageForm";
	}

	/**
	 * 保存聊天信息表
	 */
	@RequiresPermissions(value={"wechat:wxFriendMessage:add","wechat:wxFriendMessage:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxFriendMessage wxFriendMessage, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxFriendMessage)){
			return form(wxFriendMessage, model);
		}
		//新增或编辑表单保存
		wxFriendMessageService.save(wxFriendMessage);//保存
		addMessage(redirectAttributes, "保存聊天信息表成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFriendMessage/?repage";
	}
	
	/**
	 * 删除聊天信息表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriendMessage:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxFriendMessage wxFriendMessage, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxFriendMessageService.delete(wxFriendMessage);
		j.setMsg("删除聊天信息表成功");
		return j;
	}
	
	/**
	 * 批量删除聊天信息表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriendMessage:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxFriendMessageService.delete(wxFriendMessageService.get(id));
		}
		j.setMsg("删除聊天信息表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriendMessage:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxFriendMessage wxFriendMessage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "聊天信息表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxFriendMessage> page = wxFriendMessageService.findPage(new Page<WxFriendMessage>(request, response, -1), wxFriendMessage);
    		new ExportExcel("聊天信息表", WxFriendMessage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出聊天信息表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxFriendMessage:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxFriendMessage> list = ei.getDataList(WxFriendMessage.class);
			for (WxFriendMessage wxFriendMessage : list){
				try{
					wxFriendMessageService.save(wxFriendMessage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条聊天信息表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条聊天信息表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入聊天信息表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFriendMessage/?repage";
    }
	
	/**
	 * 下载导入聊天信息表数据模板
	 */
	@RequiresPermissions("wechat:wxFriendMessage:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "聊天信息表数据导入模板.xlsx";
    		List<WxFriendMessage> list = Lists.newArrayList(); 
    		new ExportExcel("聊天信息表数据", WxFriendMessage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFriendMessage/?repage";
    }

}