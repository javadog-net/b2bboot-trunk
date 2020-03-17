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
import com.jhmis.core.persistence.UseUrl;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.service.AttInfoService;
import com.jhmis.modules.wechat.entity.AppVersion;
import com.jhmis.modules.wechat.service.AppVersionService;

/**
 * app版本Controller
 * @author abc
 * @version 2019-05-10
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/appVersion")
public class AppVersionController extends BaseController {

	@Autowired
	private AppVersionService appVersionService;
	@Autowired
	private AttInfoService attInfoService;
	@Autowired
	private UseUrl userUrl;
	
	
	@ModelAttribute
	public AppVersion get(@RequestParam(required=false) String id) {
		AppVersion entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = appVersionService.get(id);
		}
		if (entity == null){
			entity = new AppVersion();
		}
		return entity;
	}
	
	/**
	 * app版本列表页面
	 */
	@RequiresPermissions("wechat:appVersion:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/appVersionList";
	}
	
	/**
	 * app版本列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:appVersion:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(AppVersion appVersion, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AppVersion> page = appVersionService.findPage(new Page<AppVersion>(request, response), appVersion); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑app版本表单页面
	 */
	@RequiresPermissions(value={"wechat:appVersion:view","wechat:appVersion:add","wechat:appVersion:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AppVersion appVersion, Model model) {
		model.addAttribute("appVersion", appVersion);
		if(StringUtils.isBlank(appVersion.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/appVersionForm";
	}

	/**
	 * 保存app版本
	 */
	@RequiresPermissions(value={"wechat:appVersion:add","wechat:appVersion:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(AppVersion appVersion, Model model, RedirectAttributes redirectAttributes) throws Exception{
		
		if(null==appVersion.getTime()){
			appVersion.setTime(new Date());
		}
		if(null != appVersion.getAddress()){
			AttInfo attInfo = new AttInfo();
			attInfo.setAttUrl(appVersion.getAddress());
			List<AttInfo> attInfos = attInfoService.findList(attInfo);
			if(attInfos != null && attInfos.size()!=0){
				appVersion.setTotalSize(attInfos.get(0).getAttSize());
			}
		}
		if(null==appVersion.getUpdateTime()){
			appVersion.setUpdateTime(new Date());
		}

		//新增或编辑表单保存
		appVersionService.save(appVersion);//保存
		
		addMessage(redirectAttributes, "保存app版本成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/appVersion/?repage";
	}
	
	/**
	 * 删除app版本
	 */
	@ResponseBody
	@RequiresPermissions("wechat:appVersion:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(AppVersion appVersion, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		appVersionService.delete(appVersion);
		j.setMsg("删除app版本成功");
		return j;
	}
	
	/**
	 * 批量删除app版本
	 */
	@ResponseBody
	@RequiresPermissions("wechat:appVersion:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			appVersionService.delete(appVersionService.get(id));
		}
		j.setMsg("删除app版本成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:appVersion:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(AppVersion appVersion, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "app版本"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AppVersion> page = appVersionService.findPage(new Page<AppVersion>(request, response, -1), appVersion);
    		new ExportExcel("app版本", AppVersion.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出app版本记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:appVersion:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AppVersion> list = ei.getDataList(AppVersion.class);
			for (AppVersion appVersion : list){
				try{
					appVersionService.save(appVersion);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条app版本记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条app版本记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入app版本失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/appVersion/?repage";
    }
	
	/**
	 * 下载导入app版本数据模板
	 */
	@RequiresPermissions("wechat:appVersion:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "app版本数据导入模板.xlsx";
    		List<AppVersion> list = Lists.newArrayList(); 
    		new ExportExcel("app版本数据", AppVersion.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/appVersion/?repage";
    }

}