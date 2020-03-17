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
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.SkMvComment;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.service.SkMvCommentService;


/**
 * 评论Controller
 * @author tc
 * @version 2019-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/skMvComment")
public class SkMvCommentController extends BaseController {

	@Autowired
	private SkMvCommentService skMvCommentService;
	
	@ModelAttribute
	public SkMvComment get(@RequestParam(required=false) String id) {
		SkMvComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = skMvCommentService.get(id);
		}
		if (entity == null){
			entity = new SkMvComment();
		}
		return entity;
	}
	
	/**
	 * 评论列表页面
	 */
	@RequiresPermissions("wechat:skMvComment:list")
	@RequestMapping(value = {"list", ""})
	public String list(SkMvComment skMvComment ,Model model) {
		model.addAttribute("skMvComment", skMvComment);
		System.out.println(skMvComment.toString());
		System.out.println(skMvComment.getSkMvId()+"视频id");
		return "modules/wechat/skMvCommentList";
	}
	
	/**
	 * 评论列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvComment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SkMvComment skMvComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println(skMvComment.toString());
		System.out.println("skMvComment"+skMvComment.getSkMvId());
		Page<SkMvComment> pa = new Page<SkMvComment>(request, response);
		pa.setOrderBy("sk_mv_comment_time desc");
		Page<SkMvComment> page = skMvCommentService.findPage(pa, skMvComment);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑评论表单页面
	 */
	@RequiresPermissions(value={"wechat:skMvComment:view","wechat:skMvComment:add","wechat:skMvComment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SkMvComment skMvComment, Model model) {
		model.addAttribute("skMvComment", skMvComment);
		if(StringUtils.isBlank(skMvComment.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/skMvCommentForm";
	}

	/**
	 * 保存评论
	 */
	@RequiresPermissions(value={"wechat:skMvComment:add","wechat:skMvComment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SkMvComment skMvComment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, skMvComment)){
			return form(skMvComment, model);
		}
		//新增或编辑表单保存
		skMvCommentService.save(skMvComment);//保存
		addMessage(redirectAttributes, "保存评论成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvComment/?repage";
	}
	
	/**
	 * 删除评论
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvComment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SkMvComment skMvComment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		skMvCommentService.delete(skMvComment);
		j.setMsg("删除评论成功");
		return j;
	}
	
	/**
	 * 审核 拒绝 评论
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvComment:edit")
	@RequestMapping(value = "check")
	public AjaxJson check(String id, RedirectAttributes redirectAttributes,String state) {
		AjaxJson j = new AjaxJson();
		
		
		
		User user = UserUtils.getUser();
		if(user==null){
			j.setMsg("请重新登录！");
			return j;
		}
		if(user!=null){
			if(state.equals("0")){
				j.setMsg("该评论已被拒绝");
			}
			if(state.equals("1")){
				j.setMsg("该评论成功通过审核");
			} 
			skMvCommentService.updateState(id,state,user.getName());
		}
		return j;
	}
	
	@ResponseBody
	@RequiresPermissions("wechat:skMvComment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			skMvCommentService.delete(skMvCommentService.get(id));
		}
		j.setMsg("删除评论成功");
		return j;
	}
	
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvComment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SkMvComment skMvComment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "评论"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SkMvComment> page = skMvCommentService.findPage(new Page<SkMvComment>(request, response, -1), skMvComment);
    		new ExportExcel("评论", SkMvComment.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("wechat:skMvComment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SkMvComment> list = ei.getDataList(SkMvComment.class);
			for (SkMvComment skMvComment : list){
				try{
					skMvCommentService.save(skMvComment);
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
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvComment/?repage";
    }
	
	/**
	 * 下载导入评论数据模板
	 */
	@RequiresPermissions("wechat:skMvComment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "评论数据导入模板.xlsx";
    		List<SkMvComment> list = Lists.newArrayList(); 
    		new ExportExcel("评论数据", SkMvComment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvComment/?repage";
    }

}