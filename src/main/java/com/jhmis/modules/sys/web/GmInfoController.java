/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.web;

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
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.service.GmInfoService;

/**
 * 工贸信息Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/gmInfo")
public class GmInfoController extends BaseController {

	@Autowired
	private GmInfoService gmInfoService;
	
	@ModelAttribute
	public GmInfo get(@RequestParam(required=false) String id) {
		GmInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gmInfoService.get(id);
		}
		if (entity == null){
			entity = new GmInfo();
		}
		return entity;
	}
	
	/**
	 * 工贸信息列表页面
	 */
	@RequiresPermissions("sys:gmInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/gmInfoList";
	}
	
	/**
	 * 工贸信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GmInfo gmInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GmInfo> page = gmInfoService.findPage(new Page<GmInfo>(request, response), gmInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工贸信息表单页面
	 */
	@RequiresPermissions(value={"sys:gmInfo:view","sys:gmInfo:add","sys:gmInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GmInfo gmInfo, Model model) {
		model.addAttribute("gmInfo", gmInfo);
		return "modules/sys/gmInfoForm";
	}

	/**
	 * 保存工贸信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:gmInfo:add","sys:gmInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GmInfo gmInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, gmInfo)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		gmInfoService.save(gmInfo);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存工贸信息成功");
		return j;
	}
	
	/**
	 * 删除工贸信息
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GmInfo gmInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		gmInfoService.delete(gmInfo);
		j.setMsg("删除工贸信息成功");
		return j;
	}
	
	/**
	 * 批量删除工贸信息
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			gmInfoService.delete(gmInfoService.get(id));
		}
		j.setMsg("删除工贸信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GmInfo gmInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工贸信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GmInfo> page = gmInfoService.findPage(new Page<GmInfo>(request, response, -1), gmInfo);
    		new ExportExcel("工贸信息", GmInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工贸信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:gmInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GmInfo> list = ei.getDataList(GmInfo.class);
			for (GmInfo gmInfo : list){
				try{
					gmInfoService.save(gmInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工贸信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条工贸信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入工贸信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/gmInfo/?repage";
    }
	
	/**
	 * 下载导入工贸信息数据模板
	 */
	@RequiresPermissions("sys:gmInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工贸信息数据导入模板.xlsx";
    		List<GmInfo> list = Lists.newArrayList(); 
    		new ExportExcel("工贸信息数据", GmInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/gmInfo/?repage";
    }

}