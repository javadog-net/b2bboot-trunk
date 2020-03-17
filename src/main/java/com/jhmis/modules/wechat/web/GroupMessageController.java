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
import com.jhmis.modules.wechat.entity.GroupMessage;
import com.jhmis.modules.wechat.service.GroupMessageService;

/**
 * 群组聊天信息Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/groupMessage")
public class GroupMessageController extends BaseController {

	@Autowired
	private GroupMessageService groupMessageService;
	
	@ModelAttribute
	public GroupMessage get(@RequestParam(required=false) String id) {
		GroupMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groupMessageService.get(id);
		}
		if (entity == null){
			entity = new GroupMessage();
		}
		return entity;
	}
	
	/**
	 * 群组聊天信息列表页面
	 */
	@RequiresPermissions("wechat:groupMessage:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/groupMessageList";
	}
	
	/**
	 * 群组聊天信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:groupMessage:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GroupMessage groupMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GroupMessage> page = groupMessageService.findPage(new Page<GroupMessage>(request, response), groupMessage); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑群组聊天信息表单页面
	 */
	@RequiresPermissions(value={"wechat:groupMessage:view","wechat:groupMessage:add","wechat:groupMessage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GroupMessage groupMessage, Model model) {
		model.addAttribute("groupMessage", groupMessage);
		if(StringUtils.isBlank(groupMessage.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/groupMessageForm";
	}

	/**
	 * 保存群组聊天信息
	 */
	@RequiresPermissions(value={"wechat:groupMessage:add","wechat:groupMessage:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(GroupMessage groupMessage, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, groupMessage)){
			return form(groupMessage, model);
		}
		//新增或编辑表单保存
		groupMessageService.save(groupMessage);//保存
		addMessage(redirectAttributes, "保存群组聊天信息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/groupMessage/?repage";
	}
	
	/**
	 * 删除群组聊天信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:groupMessage:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GroupMessage groupMessage, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		groupMessageService.delete(groupMessage);
		j.setMsg("删除群组聊天信息成功");
		return j;
	}
	
	/**
	 * 批量删除群组聊天信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:groupMessage:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			groupMessageService.delete(groupMessageService.get(id));
		}
		j.setMsg("删除群组聊天信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:groupMessage:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GroupMessage groupMessage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "群组聊天信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GroupMessage> page = groupMessageService.findPage(new Page<GroupMessage>(request, response, -1), groupMessage);
    		new ExportExcel("群组聊天信息", GroupMessage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出群组聊天信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:groupMessage:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GroupMessage> list = ei.getDataList(GroupMessage.class);
			for (GroupMessage groupMessage : list){
				try{
					groupMessageService.save(groupMessage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条群组聊天信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条群组聊天信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入群组聊天信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/groupMessage/?repage";
    }
	
	/**
	 * 下载导入群组聊天信息数据模板
	 */
	@RequiresPermissions("wechat:groupMessage:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "群组聊天信息数据导入模板.xlsx";
    		List<GroupMessage> list = Lists.newArrayList(); 
    		new ExportExcel("群组聊天信息数据", GroupMessage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/groupMessage/?repage";
    }

}