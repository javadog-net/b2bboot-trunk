/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import java.util.Date;
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
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxMeetingFile;
import com.jhmis.modules.wechat.service.WxMeetingFileService;

/**
 * 会议附件上传Controller
 * @author tc
 * @version 2019-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxMeetingFile")
public class WxMeetingFileController extends BaseController {

	@Autowired
	private WxMeetingFileService wxMeetingFileService;
	
	@ModelAttribute
	public WxMeetingFile get(@RequestParam(required=false) String id) {
		WxMeetingFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMeetingFileService.get(id);
		}
		if (entity == null){
			entity = new WxMeetingFile();
		}
		return entity;
	}
	
	/**
	 * 上传会议附件列表页面
	 */
	@RequiresPermissions("wechat:wxMeetingFile:list")
	@RequestMapping(value = {"list", ""})
	public String list(WxMeetingFile wxMeetingFile,Model model) {
		System.out.println(wxMeetingFile+"====");
		
		model.addAttribute("wxMeetingFile", wxMeetingFile);
		
		return "modules/wechat/wxMeetingFileList";
	}
	
	/**
	 * 上传会议附件列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingFile:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMeetingFile wxMeetingFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println(wxMeetingFile+"====");
		
		Page<WxMeetingFile> page = wxMeetingFileService.findPage(new Page<WxMeetingFile>(request, response), wxMeetingFile); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑上传会议附件表单页面
	 */
	@RequiresPermissions(value={"wechat:wxMeetingFile:view","wechat:wxMeetingFile:add","wechat:wxMeetingFile:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMeetingFile wxMeetingFile, Model model) {
		model.addAttribute("wxMeetingFile", wxMeetingFile);
		if(StringUtils.isBlank(wxMeetingFile.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxMeetingFileForm";
	}

	/**
	 * 保存上传会议附件
	 */
	@RequiresPermissions(value={"wechat:wxMeetingFile:add","wechat:wxMeetingFile:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxMeetingFile wxMeetingFile, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxMeetingFile)){
			return form(wxMeetingFile, model);
		}
		if(wxMeetingFile.getTextUrl().isEmpty()){
		redirectAttributes.addFlashAttribute("error", "error");
		addMessage(redirectAttributes, "请选择文件，并点击上传文件，等待上传成功！");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingFile/?repage&&meetingId="+wxMeetingFile.getMeetingId();	
		}
		//新增或编辑表单保存
		wxMeetingFile.setAddTime(new Date());
		User user = UserUtils.getUser();
		if(null!=user){
	     wxMeetingFile.setAddUser(user.getLoginName());
		}
		wxMeetingFileService.save(wxMeetingFile);//保存
		addMessage(redirectAttributes, "保存上传会议附件成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingFile/?repage&&meetingId="+wxMeetingFile.getMeetingId();
	}
	
	/**
	 * 删除上传会议附件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingFile:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMeetingFile wxMeetingFile, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxMeetingFileService.delete(wxMeetingFile);
		j.setMsg("删除上传会议附件成功");
		return j;
	}
	
	/**
	 * 批量删除上传会议附件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingFile:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxMeetingFileService.delete(wxMeetingFileService.get(id));
		}
		j.setMsg("删除上传会议附件成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingFile:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxMeetingFile wxMeetingFile, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "上传会议附件"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxMeetingFile> page = wxMeetingFileService.findPage(new Page<WxMeetingFile>(request, response, -1), wxMeetingFile);
    		new ExportExcel("上传会议附件", WxMeetingFile.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出上传会议附件记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxMeetingFile:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMeetingFile> list = ei.getDataList(WxMeetingFile.class);
			for (WxMeetingFile wxMeetingFile : list){
				try{
					wxMeetingFileService.save(wxMeetingFile);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条上传会议附件记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条上传会议附件记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入上传会议附件失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingFile/?repage";
    }
	
	/**
	 * 下载导入上传会议附件数据模板
	 */
	@RequiresPermissions("wechat:wxMeetingFile:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "上传会议附件数据导入模板.xlsx";
    		List<WxMeetingFile> list = Lists.newArrayList(); 
    		new ExportExcel("上传会议附件数据", WxMeetingFile.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMeetingFile/?repage";
    }

}