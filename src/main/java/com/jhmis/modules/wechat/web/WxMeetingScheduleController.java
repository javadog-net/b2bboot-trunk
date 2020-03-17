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
import com.jhmis.modules.wechat.entity.WxMeetingSchedule;
import com.jhmis.modules.wechat.service.WxMeetingScheduleService;
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
 * 会议日程表Controller
 * @author lvyangzhuo
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxMeetingSchedule")
public class WxMeetingScheduleController extends BaseController {

	@Autowired
	private WxMeetingScheduleService wxMeetingScheduleService;
	
	@ModelAttribute
	public WxMeetingSchedule get(@RequestParam(required=false) String id) {
		WxMeetingSchedule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMeetingScheduleService.get(id);
		}
		if (entity == null){
			entity = new WxMeetingSchedule();
		}
		return entity;
	}
	
	/**
	 * 会议日程表列表页面
	 */
	@RequiresPermissions("wechat:wxMeetingSchedule:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/meeting/wxMeetingScheduleList";
	}
	
	/**
	 * 会议日程表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSchedule:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMeetingSchedule wxMeetingSchedule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMeetingSchedule> page = wxMeetingScheduleService.findPage(new Page<WxMeetingSchedule>(request, response), wxMeetingSchedule); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会议日程表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxMeetingSchedule:view","wechat:wxMeetingSchedule:add","wechat:wxMeetingSchedule:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMeetingSchedule wxMeetingSchedule, Model model) {
		model.addAttribute("wxMeetingSchedule", wxMeetingSchedule);
		return "modules/wechat/meeting/wxMeetingScheduleForm";
	}

	/**
	 * 保存会议日程表
	 */
	@ResponseBody
	@RequiresPermissions(value={"wechat:wxMeetingSchedule:add","wechat:wxMeetingSchedule:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WxMeetingSchedule wxMeetingSchedule, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, wxMeetingSchedule)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		wxMeetingScheduleService.save(wxMeetingSchedule);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存会议日程表成功");
		return j;
	}
	
	/**
	 * 删除会议日程表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSchedule:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMeetingSchedule wxMeetingSchedule, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxMeetingScheduleService.delete(wxMeetingSchedule);
		j.setMsg("删除会议日程表成功");
		return j;
	}
	
	/**
	 * 批量删除会议日程表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSchedule:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxMeetingScheduleService.delete(wxMeetingScheduleService.get(id));
		}
		j.setMsg("删除会议日程表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSchedule:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxMeetingSchedule wxMeetingSchedule, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议日程表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxMeetingSchedule> page = wxMeetingScheduleService.findPage(new Page<WxMeetingSchedule>(request, response, -1), wxMeetingSchedule);
    		new ExportExcel("会议日程表", WxMeetingSchedule.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议日程表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxMeetingSchedule:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMeetingSchedule> list = ei.getDataList(WxMeetingSchedule.class);
			for (WxMeetingSchedule wxMeetingSchedule : list){
				try{
					wxMeetingScheduleService.save(wxMeetingSchedule);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会议日程表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会议日程表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会议日程表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingSchedule/?repage";
    }
	
	/**
	 * 下载导入会议日程表数据模板
	 */
	@RequiresPermissions("wechat:wxMeetingSchedule:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会议日程表数据导入模板.xlsx";
    		List<WxMeetingSchedule> list = Lists.newArrayList(); 
    		new ExportExcel("会议日程表数据", WxMeetingSchedule.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingSchedule/?repage";
    }

}