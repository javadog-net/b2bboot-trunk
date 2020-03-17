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
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.service.WxFriendService;

/**
 * 聊天好友表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxFriend")
public class WxFriendController extends BaseController {

	@Autowired
	private WxFriendService wxFriendService;
	
	@ModelAttribute
	public WxFriend get(@RequestParam(required=false) String id) {
		WxFriend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxFriendService.get(id);
		}
		if (entity == null){
			entity = new WxFriend();
		}
		return entity;
	}
	
	/**
	 * 聊天好友表列表页面
	 */
	@RequiresPermissions("wechat:wxFriend:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxFriendList";
	}
	
	/**
	 * 聊天好友表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriend:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxFriend wxFriend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxFriend> page = wxFriendService.findPage(new Page<WxFriend>(request, response), wxFriend); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑聊天好友表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxFriend:view","wechat:wxFriend:add","wechat:wxFriend:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxFriend wxFriend, Model model) {
		model.addAttribute("wxFriend", wxFriend);
		return "modules/wechat/wxFriendForm";
	}

	/**
	 * 保存聊天好友表
	 */
	@ResponseBody
	@RequiresPermissions(value={"wechat:wxFriend:add","wechat:wxFriend:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WxFriend wxFriend, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, wxFriend)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		wxFriendService.save(wxFriend);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存聊天好友表成功");
		return j;
	}
	
	/**
	 * 删除聊天好友表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriend:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxFriend wxFriend, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxFriendService.delete(wxFriend);
		j.setMsg("删除聊天好友表成功");
		return j;
	}
	
	/**
	 * 批量删除聊天好友表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriend:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxFriendService.delete(wxFriendService.get(id));
		}
		j.setMsg("删除聊天好友表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriend:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxFriend wxFriend, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "聊天好友表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxFriend> page = wxFriendService.findPage(new Page<WxFriend>(request, response, -1), wxFriend);
    		new ExportExcel("聊天好友表", WxFriend.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出聊天好友表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxFriend:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxFriend> list = ei.getDataList(WxFriend.class);
			for (WxFriend wxFriend : list){
				try{
					wxFriendService.save(wxFriend);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条聊天好友表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条聊天好友表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入聊天好友表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFriend/?repage";
    }
	
	/**
	 * 下载导入聊天好友表数据模板
	 */
	@RequiresPermissions("wechat:wxFriend:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "聊天好友表数据导入模板.xlsx";
    		List<WxFriend> list = Lists.newArrayList(); 
    		new ExportExcel("聊天好友表数据", WxFriend.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFriend/?repage";
    }

}