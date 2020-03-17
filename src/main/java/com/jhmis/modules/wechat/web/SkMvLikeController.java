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
import com.jhmis.modules.wechat.entity.SkMvLike;
import com.jhmis.modules.wechat.service.SkMvLikeService;

/**
 * 商空点赞Controller
 * @author tc
 * @version 2019-05-28
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/skMvLike")
public class SkMvLikeController extends BaseController {

	@Autowired
	private SkMvLikeService skMvLikeService;
	
	@ModelAttribute
	public SkMvLike get(@RequestParam(required=false) String id) {
		SkMvLike entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = skMvLikeService.get(id);
		}
		if (entity == null){
			entity = new SkMvLike();
		}
		return entity;
	}
	
	/**
	 * 点赞列表页面
	 */
	@RequiresPermissions("wechat:skMvLike:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/skMvLikeList";
	}
	
	/**
	 * 点赞列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvLike:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SkMvLike skMvLike, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SkMvLike> page = skMvLikeService.findPage(new Page<SkMvLike>(request, response), skMvLike); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑点赞表单页面
	 */
	@RequiresPermissions(value={"wechat:skMvLike:view","wechat:skMvLike:add","wechat:skMvLike:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SkMvLike skMvLike, Model model) {
		model.addAttribute("skMvLike", skMvLike);
		if(StringUtils.isBlank(skMvLike.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/skMvLikeForm";
	}

	/**
	 * 保存点赞
	 */
	@RequiresPermissions(value={"wechat:skMvLike:add","wechat:skMvLike:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SkMvLike skMvLike, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, skMvLike)){
			return form(skMvLike, model);
		}
		//新增或编辑表单保存
		skMvLikeService.save(skMvLike);//保存
		addMessage(redirectAttributes, "保存点赞成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvLike/?repage";
	}
	
	/**
	 * 删除点赞
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvLike:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SkMvLike skMvLike, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		skMvLikeService.delete(skMvLike);
		j.setMsg("删除点赞成功");
		return j;
	}
	
	/**
	 * 批量删除点赞
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvLike:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			skMvLikeService.delete(skMvLikeService.get(id));
		}
		j.setMsg("删除点赞成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvLike:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SkMvLike skMvLike, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "点赞"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SkMvLike> page = skMvLikeService.findPage(new Page<SkMvLike>(request, response, -1), skMvLike);
    		new ExportExcel("点赞", SkMvLike.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出点赞记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:skMvLike:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SkMvLike> list = ei.getDataList(SkMvLike.class);
			for (SkMvLike skMvLike : list){
				try{
					skMvLikeService.save(skMvLike);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条点赞记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条点赞记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入点赞失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvLike/?repage";
    }
	
	/**
	 * 下载导入点赞数据模板
	 */
	@RequiresPermissions("wechat:skMvLike:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "点赞数据导入模板.xlsx";
    		List<SkMvLike> list = Lists.newArrayList(); 
    		new ExportExcel("点赞数据", SkMvLike.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvLike/?repage";
    }

}