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
import com.jhmis.modules.wechat.entity.WxMessageRecord;
import com.jhmis.modules.wechat.service.WxMessageRecordService;

/**
 * 短信履历Controller
 * @author hdx
 * @version 2019-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxMessageRecord")
public class WxMessageRecordController extends BaseController {

	@Autowired
	private WxMessageRecordService wxMessageRecordService;
	
	@ModelAttribute
	public WxMessageRecord get(@RequestParam(required=false) String id) {
		WxMessageRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMessageRecordService.get(id);
		}
		if (entity == null){
			entity = new WxMessageRecord();
		}
		return entity;
	}
	
	/**
	 * 短信履历列表页面
	 */
	@RequiresPermissions("wechat:wxMessageRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxMessageRecordList";
	}
	
	/**
	 * 短信履历列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMessageRecord:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMessageRecord wxMessageRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMessageRecord> page = wxMessageRecordService.findPage(new Page<WxMessageRecord>(request, response), wxMessageRecord); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑短信履历表单页面
	 */
	@RequiresPermissions(value={"wechat:wxMessageRecord:view","wechat:wxMessageRecord:add","wechat:wxMessageRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMessageRecord wxMessageRecord, Model model) {
		model.addAttribute("wxMessageRecord", wxMessageRecord);
		if(StringUtils.isBlank(wxMessageRecord.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxMessageRecordForm";
	}

	/**
	 * 保存短信履历
	 */
	@RequiresPermissions(value={"wechat:wxMessageRecord:add","wechat:wxMessageRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxMessageRecord wxMessageRecord, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxMessageRecord)){
			return form(wxMessageRecord, model);
		}
		//新增或编辑表单保存
		wxMessageRecordService.save(wxMessageRecord);//保存
		addMessage(redirectAttributes, "保存短信履历成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMessageRecord/?repage";
	}
	
	/**
	 * 删除短信履历
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMessageRecord:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMessageRecord wxMessageRecord, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxMessageRecordService.delete(wxMessageRecord);
		j.setMsg("删除短信履历成功");
		return j;
	}
	
	/**
	 * 批量删除短信履历
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMessageRecord:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxMessageRecordService.delete(wxMessageRecordService.get(id));
		}
		j.setMsg("删除短信履历成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMessageRecord:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxMessageRecord wxMessageRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "短信履历"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxMessageRecord> page = wxMessageRecordService.findPage(new Page<WxMessageRecord>(request, response, -1), wxMessageRecord);
    		new ExportExcel("短信履历", WxMessageRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出短信履历记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxMessageRecord:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMessageRecord> list = ei.getDataList(WxMessageRecord.class);
			for (WxMessageRecord wxMessageRecord : list){
				try{
					wxMessageRecordService.save(wxMessageRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条短信履历记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条短信履历记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入短信履历失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMessageRecord/?repage";
    }
	
	/**
	 * 下载导入短信履历数据模板
	 */
	@RequiresPermissions("wechat:wxMessageRecord:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "短信履历数据导入模板.xlsx";
    		List<WxMessageRecord> list = Lists.newArrayList(); 
    		new ExportExcel("短信履历数据", WxMessageRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMessageRecord/?repage";
    }

}