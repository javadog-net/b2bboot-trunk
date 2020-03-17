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
import com.jhmis.modules.wechat.entity.WxFeedback;
import com.jhmis.modules.wechat.service.WxFeedbackService;

/**
 * 反馈信息Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxFeedback")
public class WxFeedbackController extends BaseController {

	@Autowired
	private WxFeedbackService wxFeedbackService;
	
	@ModelAttribute
	public WxFeedback get(@RequestParam(required=false) String id) {
		WxFeedback entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxFeedbackService.get(id);
		}
		if (entity == null){
			entity = new WxFeedback();
		}
		return entity;
	}
	
	/**
	 * 反馈信息列表页面
	 */
	@RequiresPermissions("wechat:wxFeedback:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/chat/wxFeedbackList";
	}
	
	/**
	 * 反馈信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFeedback:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxFeedback wxFeedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxFeedback> page = wxFeedbackService.findPage(new Page<WxFeedback>(request, response), wxFeedback); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑反馈信息表单页面
	 */
	@RequiresPermissions(value={"wechat:wxFeedback:view","wechat:wxFeedback:add","wechat:wxFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxFeedback wxFeedback, Model model) {
		model.addAttribute("wxFeedback", wxFeedback);
		if(StringUtils.isBlank(wxFeedback.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/chat/wxFeedbackForm";
	}

	/**
	 * 保存反馈信息
	 */
	@RequiresPermissions(value={"wechat:wxFeedback:add","wechat:wxFeedback:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxFeedback wxFeedback, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxFeedback)){
			return form(wxFeedback, model);
		}
		//新增或编辑表单保存
		wxFeedbackService.save(wxFeedback);//保存
		addMessage(redirectAttributes, "保存反馈信息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFeedback/?repage";
	}
	
	/**
	 * 删除反馈信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFeedback:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxFeedback wxFeedback, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxFeedbackService.delete(wxFeedback);
		j.setMsg("删除反馈信息成功");
		return j;
	}
	
	/**
	 * 批量删除反馈信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFeedback:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxFeedbackService.delete(wxFeedbackService.get(id));
		}
		j.setMsg("删除反馈信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFeedback:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(WxFeedback wxFeedback, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "反馈信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxFeedback> page = wxFeedbackService.findPage(new Page<WxFeedback>(request, response, -1), wxFeedback);
    		new ExportExcel("反馈信息", WxFeedback.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出反馈信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxFeedback:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxFeedback> list = ei.getDataList(WxFeedback.class);
			for (WxFeedback wxFeedback : list){
				try{
					wxFeedbackService.save(wxFeedback);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条反馈信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条反馈信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入反馈信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFeedback/?repage";
    }
	
	/**
	 * 下载导入反馈信息数据模板
	 */
	@RequiresPermissions("wechat:wxFeedback:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "反馈信息数据导入模板.xlsx";
    		List<WxFeedback> list = Lists.newArrayList(); 
    		new ExportExcel("反馈信息数据", WxFeedback.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxFeedback/?repage";
    }

}