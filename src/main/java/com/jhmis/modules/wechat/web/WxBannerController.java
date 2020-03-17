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
import com.jhmis.modules.wechat.entity.WxBanner;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.service.WxBannerService;
import com.jhmis.modules.wechat.service.WxMeetingService;

/**
 * bannerController
 * @author abc
 * @version 2019-01-25
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxBanner")
public class WxBannerController extends BaseController {

	@Autowired
	private WxBannerService wxBannerService;
	@Autowired
	private WxMeetingService wxMeetingService;
		
	@ModelAttribute
	public WxBanner get(@RequestParam(required=false) String id) {
		WxBanner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxBannerService.get(id);
		}
		if (entity == null){
			entity = new WxBanner();
		}
		System.out.println("entity"+entity);
		return entity;
	}
	
	/**
	 * banner列表页面
	 */
	@RequiresPermissions("wechat:wxBanner:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/chat/wxBannerList";
	}
	
	/**
	 * banner列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBanner:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxBanner wxBanner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxBanner> page = wxBannerService.findPage(new Page<WxBanner>(request, response), wxBanner); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑banner表单页面
	 */
	@RequiresPermissions(value={"wechat:wxBanner:view","wechat:wxBanner:add","wechat:wxBanner:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxBanner wxBanner, Model model) {
		List<WxMeeting> wxMeetingList = wxMeetingService.findAllMeetingList(new WxMeeting());
		model.addAttribute("wxBanner", wxBanner);
		model.addAttribute("meetinglist", wxMeetingList);
		if(StringUtils.isBlank(wxBanner.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/chat/wxBannerForm";
	}

	/**
	 * 保存banner
	 */
	@RequiresPermissions(value={"wechat:wxBanner:add","wechat:wxBanner:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxBanner wxBanner,
			Model model, RedirectAttributes redirectAttributes) throws Exception{
		
		String link="";
		String bannerLink=wxBanner.getLink();
		if(StringUtils.isNotEmpty(bannerLink)){
			if("1".equals(wxBanner.getType())){
				if(bannerLink.contains(",")){
					link=bannerLink.substring(bannerLink.lastIndexOf(",")+1,bannerLink.length());
				}
			}else{
				if(bannerLink.contains(",")){
					link=bannerLink.substring(0,bannerLink.indexOf(","));
				}
				
			}		
		}
		wxBanner.setLink(link);
		if (!beanValidator(model, wxBanner)){
			return form(wxBanner, model);
		}
		/*if("1".equals(wxBanner.getType())){
			WxMeeting wxMeeting = new WxMeeting();
			System.out.println("wxBanner=" + wxBanner);
			wxMeeting.setName(wxBanner.getLink());
			List<WxMeeting> wxMeetingList = wxMeetingService.findList(wxMeeting);
			System.out.println("wxMeetingList="+wxMeetingList);
			if(wxMeetingList!=null && wxMeetingList.size()!=0){
			wxBanner.setLink(wxMeetingList.get(0).getId());
			}
		}	*/
		
	
		//新增或编辑表单保存
		wxBannerService.save(wxBanner);//保存
		addMessage(redirectAttributes, "保存banner成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxBanner/?repage";
	}
	
	/**
	 * 删除banner
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBanner:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxBanner wxBanner, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxBannerService.delete(wxBanner);
		j.setMsg("删除banner成功");
		return j;
	}
	
	/**
	 * 批量删除banner
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBanner:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxBannerService.delete(wxBannerService.get(id));
		}
		j.setMsg("删除banner成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBanner:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxBanner wxBanner, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "banner"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxBanner> page = wxBannerService.findPage(new Page<WxBanner>(request, response, -1), wxBanner);
    		new ExportExcel("banner", WxBanner.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出banner记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxBanner:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxBanner> list = ei.getDataList(WxBanner.class);
			for (WxBanner wxBanner : list){
				try{
					wxBannerService.save(wxBanner);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条banner记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条banner记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入banner失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxBanner/?repage";
    }
	
	/**
	 * 下载导入banner数据模板
	 */
	@RequiresPermissions("wechat:wxBanner:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "banner数据导入模板.xlsx";
    		List<WxBanner> list = Lists.newArrayList(); 
    		new ExportExcel("banner数据", WxBanner.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxBanner/?repage";
    }

}