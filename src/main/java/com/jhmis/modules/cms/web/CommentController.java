/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

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
import com.jhmis.modules.cms.entity.Comment;
import com.jhmis.modules.cms.service.CommentService;

/**
 * 评论管理Controller
 * @author lydia
 * @version 2019-09-16
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/comment")
public class CommentController extends BaseController {

	@Autowired
	private CommentService commentService;
	
	@ModelAttribute
	public Comment get(@RequestParam(required=false) String id) {
		Comment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = commentService.get(id);
		}
		if (entity == null){
			entity = new Comment();
		}
		return entity;
	}
	
	/**
	 * 评论列表页面
	 */
	@RequiresPermissions("cms:comment:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/commentList";
	}
	
	/**
	 * 评论列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Comment> page = commentService.findPage(new Page<Comment>(request, response), comment); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑评论表单页面
	 */
	@RequiresPermissions(value={"cms:comment:view","cms:comment:add","cms:comment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Comment comment, Model model) {
		model.addAttribute("comment", comment);
		if(StringUtils.isBlank(comment.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cms/commentForm";
	}

	/**
	 * 保存评论
	 */
	@RequiresPermissions(value={"cms:comment:add","cms:comment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Comment comment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comment)){
			return form(comment, model);
		}
		//新增或编辑表单保存
		commentService.save(comment);//保存
		addMessage(redirectAttributes, "保存评论成功");
		return "redirect:"+Global.getAdminPath()+"/cms/comment/?repage";
	}
	
	/**
	 * 删除评论
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Comment comment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		commentService.delete(comment);
		j.setMsg("删除评论成功");
		return j;
	}
	
	/**
	 * 批量删除评论
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			commentService.delete(commentService.get(id));
		}
		j.setMsg("删除评论成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:comment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Comment comment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "评论"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Comment> page = commentService.findPage(new Page<Comment>(request, response, -1), comment);
    		new ExportExcel("评论", Comment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出评论记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:comment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Comment> list = ei.getDataList(Comment.class);
			for (Comment comment : list){
				try{
					commentService.save(comment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条评论记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条评论记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入评论失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/comment/?repage";
    }
	
	/**
	 * 下载导入评论数据模板
	 */
	@RequiresPermissions("cms:comment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "评论数据导入模板.xlsx";
    		List<Comment> list = Lists.newArrayList(); 
    		new ExportExcel("评论数据", Comment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/comment/?repage";
    }

}