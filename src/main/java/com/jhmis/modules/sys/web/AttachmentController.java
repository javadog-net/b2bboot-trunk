/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.Attachment;
import com.jhmis.modules.sys.service.AttachmentService;
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
 * 附件表Controller
 * @author tity
 * @version 2018-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/attachment")
public class AttachmentController extends BaseController {

	@Autowired
	private AttachmentService attachmentService;
	
	@ModelAttribute
	public Attachment get(@RequestParam(required=false) String id) {
		Attachment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attachmentService.get(id);
		}
		if (entity == null){
			entity = new Attachment();
		}
		return entity;
	}
	
	/**
	 * 附件表列表页面
	 */
	@RequiresPermissions("sys:attachment:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/attachment/attachmentList";
	}
	
	/**
	 * 附件表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:attachment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Attachment attachment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Attachment> page = attachmentService.findPage(new Page<Attachment>(request, response), attachment); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑附件表表单页面
	 */
	@RequiresPermissions(value={"sys:attachment:view","sys:attachment:add","sys:attachment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Attachment attachment, Model model) {
		model.addAttribute("attachment", attachment);
		return "modules/sys/attachment/attachmentForm";
	}

	/**
	 * 保存附件表
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:attachment:add","sys:attachment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Attachment attachment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, attachment)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		attachmentService.save(attachment);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存附件表成功");
		return j;
	}
	
	/**
	 * 删除附件表
	 */
	@ResponseBody
	@RequiresPermissions("sys:attachment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Attachment attachment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		attachmentService.delete(attachment);
		j.setMsg("删除附件表成功");
		return j;
	}
	
	/**
	 * 批量删除附件表
	 */
	@ResponseBody
	@RequiresPermissions("sys:attachment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			attachmentService.delete(attachmentService.get(id));
		}
		j.setMsg("删除附件表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:attachment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Attachment attachment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "附件表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Attachment> page = attachmentService.findPage(new Page<Attachment>(request, response, -1), attachment);
    		new ExportExcel("附件表", Attachment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出附件表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:attachment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Attachment> list = ei.getDataList(Attachment.class);
			for (Attachment attachment : list){
				try{
					attachmentService.save(attachment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条附件表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条附件表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入附件表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/attachment/?repage";
    }
	
	/**
	 * 下载导入附件表数据模板
	 */
	@RequiresPermissions("sys:attachment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "附件表数据导入模板.xlsx";
    		List<Attachment> list = Lists.newArrayList(); 
    		new ExportExcel("附件表数据", Attachment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/attachment/?repage";
    }

}