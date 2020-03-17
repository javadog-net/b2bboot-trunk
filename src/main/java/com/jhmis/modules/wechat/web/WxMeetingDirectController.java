/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxMeetingDirect;
import com.jhmis.modules.wechat.service.WxMeetingDirectService;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会议直播表Controller
 * @author lvyangzhuo
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxMeetingDirect")
public class WxMeetingDirectController extends BaseController {

	@Autowired
	private WxMeetingDirectService wxMeetingDirectService;
	
	@ModelAttribute
	public WxMeetingDirect get(@RequestParam(required=false) String id) {
		WxMeetingDirect entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMeetingDirectService.get(id);
		}
		if (entity == null){
			entity = new WxMeetingDirect();
		}
		return entity;
	}
	
	/**
	 * 会议直播表列表页面
	 */
	@RequiresPermissions("wechat:wxMeetingDirect:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/meeting/wxMeetingDirectList";
	}
	
	/**
	 * 会议直播表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingDirect:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMeetingDirect wxMeetingDirect, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMeetingDirect> page = wxMeetingDirectService.findPage(new Page<WxMeetingDirect>(request, response), wxMeetingDirect); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会议直播表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxMeetingDirect:view","wechat:wxMeetingDirect:add","wechat:wxMeetingDirect:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMeetingDirect wxMeetingDirect, Model model) {
		model.addAttribute("wxMeetingDirect", wxMeetingDirect);
		return "modules/wechat/meeting/wxMeetingDirectForm";
	}

	/**
	 * 保存会议直播表
	 */
	@ResponseBody
	@RequiresPermissions(value={"wechat:wxMeetingDirect:add","wechat:wxMeetingDirect:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WxMeetingDirect wxMeetingDirect, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		wxMeetingDirect.setSendTime(new Date());
		if (!beanValidator(model, wxMeetingDirect)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		//放入创建人id
		User user = UserUtils.getUser();
		if (user != null) {
			wxMeetingDirect.setUserId(user.getId());
		}
		wxMeetingDirectService.save(wxMeetingDirect);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存会议直播表成功");
		return j;
	}
	
	/**
	 * 删除会议直播表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingDirect:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMeetingDirect wxMeetingDirect, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxMeetingDirectService.delete(wxMeetingDirect);
		j.setMsg("删除会议直播表成功");
		return j;
	}
	
	/**
	 * 批量删除会议直播表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingDirect:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxMeetingDirectService.delete(wxMeetingDirectService.get(id));
		}
		j.setMsg("删除会议直播表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingDirect:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxMeetingDirect wxMeetingDirect, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议直播表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxMeetingDirect> page = wxMeetingDirectService.findPage(new Page<WxMeetingDirect>(request, response, -1), wxMeetingDirect);
    		new ExportExcel("会议直播表", WxMeetingDirect.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议直播表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxMeetingDirect:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMeetingDirect> list = ei.getDataList(WxMeetingDirect.class);
			for (WxMeetingDirect wxMeetingDirect : list){
				try{
					wxMeetingDirectService.save(wxMeetingDirect);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会议直播表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会议直播表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会议直播表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingDirect/?repage";
    }
	
	/**
	 * 下载导入会议直播表数据模板
	 */
	@RequiresPermissions("wechat:wxMeetingDirect:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会议直播表数据导入模板.xlsx";
    		List<WxMeetingDirect> list = Lists.newArrayList(); 
    		new ExportExcel("会议直播表数据", WxMeetingDirect.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingDirect/?repage";
    }

}