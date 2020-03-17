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
import com.jhmis.modules.wechat.entity.WxGroupMessage;
import com.jhmis.modules.wechat.service.WxGroupMessageService;

/**
 * 群聊信息Controller
 * @author hdx
 * @version 2018-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxGroupMessage")
public class WxGroupMessageController extends BaseController {

	@Autowired
	private WxGroupMessageService wxGroupMessageService;
	
	@ModelAttribute
	public WxGroupMessage get(@RequestParam(required=false) String id) {
		WxGroupMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxGroupMessageService.get(id);
		}
		if (entity == null){
			entity = new WxGroupMessage();
		}
		return entity;
	}
	
	/**
	 * 群聊信息列表页面
	 */
	@RequiresPermissions("wechat:wxGroupMessage:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxGroupMessageList";
	}
	
	/**
	 * 群聊信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupMessage:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxGroupMessage wxGroupMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxGroupMessage> page = wxGroupMessageService.findPage(new Page<WxGroupMessage>(request, response), wxGroupMessage); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑群聊信息表单页面
	 */
	@RequiresPermissions(value={"wechat:wxGroupMessage:view","wechat:wxGroupMessage:add","wechat:wxGroupMessage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxGroupMessage wxGroupMessage, Model model) {
		model.addAttribute("wxGroupMessage", wxGroupMessage);
		if(StringUtils.isBlank(wxGroupMessage.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxGroupMessageForm";
	}

	/**
	 * 保存群聊信息
	 */
	@RequiresPermissions(value={"wechat:wxGroupMessage:add","wechat:wxGroupMessage:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxGroupMessage wxGroupMessage, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxGroupMessage)){
			return form(wxGroupMessage, model);
		}
		//新增或编辑表单保存
		wxGroupMessageService.save(wxGroupMessage);//保存
		addMessage(redirectAttributes, "保存群聊信息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroupMessage/?repage";
	}
	
	/**
	 * 删除群聊信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupMessage:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxGroupMessage wxGroupMessage, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxGroupMessageService.delete(wxGroupMessage);
		j.setMsg("删除群聊信息成功");
		return j;
	}
	
	/**
	 * 批量删除群聊信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupMessage:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxGroupMessageService.delete(wxGroupMessageService.get(id));
		}
		j.setMsg("删除群聊信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxGroupMessage:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxGroupMessage wxGroupMessage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "群聊信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxGroupMessage> page = wxGroupMessageService.findPage(new Page<WxGroupMessage>(request, response, -1), wxGroupMessage);
    		new ExportExcel("群聊信息", WxGroupMessage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出群聊信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxGroupMessage:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxGroupMessage> list = ei.getDataList(WxGroupMessage.class);
			for (WxGroupMessage wxGroupMessage : list){
				try{
					wxGroupMessageService.save(wxGroupMessage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条群聊信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条群聊信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入群聊信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroupMessage/?repage";
    }
	
	/**
	 * 下载导入群聊信息数据模板
	 */
	@RequiresPermissions("wechat:wxGroupMessage:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "群聊信息数据导入模板.xlsx";
    		List<WxGroupMessage> list = Lists.newArrayList(); 
    		new ExportExcel("群聊信息数据", WxGroupMessage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxGroupMessage/?repage";
    }

}