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
import com.jhmis.modules.wechat.entity.SkMvDetails;
import com.jhmis.modules.wechat.service.SkMvDetailsService;

/**
 * 商空视频详情Controller
 * @author tc
 * @version 2019-05-23
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/skMvDetails")
public class SkMvDetailsController extends BaseController {

	@Autowired
	private SkMvDetailsService skMvDetailsService;
	
	@ModelAttribute
	public SkMvDetails get(@RequestParam(required=false) String id) {
		SkMvDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = skMvDetailsService.get(id);
		}
		if (entity == null){
			entity = new SkMvDetails();
		}
		return entity;
	}
	
	/**
	 * 商空视频详情列表页面
	 */
	@RequiresPermissions("wechat:skMvDetails:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/skMvDetailsList";
	}
	
	/**
	 * 商空视频详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvDetails:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SkMvDetails skMvDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SkMvDetails> page = skMvDetailsService.findPage(new Page<SkMvDetails>(request, response), skMvDetails); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商空视频详情表单页面
	 */
	@RequiresPermissions(value={"wechat:skMvDetails:view","wechat:skMvDetails:add","wechat:skMvDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SkMvDetails skMvDetails, Model model) {
		model.addAttribute("skMvDetails", skMvDetails);
		if(StringUtils.isBlank(skMvDetails.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/skMvDetailsForm";
	}

	/**
	 * 保存商空视频详情
	 */
	@RequiresPermissions(value={"wechat:skMvDetails:add","wechat:skMvDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SkMvDetails skMvDetails, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, skMvDetails)){
			return form(skMvDetails, model);
		}
		//新增或编辑表单保存
		skMvDetailsService.save(skMvDetails);//保存
		addMessage(redirectAttributes, "保存商空视频详情成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvDetails/?repage";
	}
	
	/**
	 * 删除商空视频详情
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvDetails:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SkMvDetails skMvDetails, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		skMvDetailsService.delete(skMvDetails);
		j.setMsg("删除商空视频详情成功");
		return j;
	}
	
	/**
	 * 批量删除商空视频详情
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvDetails:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			skMvDetailsService.delete(skMvDetailsService.get(id));
		}
		j.setMsg("删除商空视频详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMvDetails:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SkMvDetails skMvDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商空视频详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SkMvDetails> page = skMvDetailsService.findPage(new Page<SkMvDetails>(request, response, -1), skMvDetails);
    		new ExportExcel("商空视频详情", SkMvDetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商空视频详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:skMvDetails:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SkMvDetails> list = ei.getDataList(SkMvDetails.class);
			for (SkMvDetails skMvDetails : list){
				try{
					skMvDetailsService.save(skMvDetails);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商空视频详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商空视频详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商空视频详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvDetails/?repage";
    }
	
	/**
	 * 下载导入商空视频详情数据模板
	 */
	@RequiresPermissions("wechat:skMvDetails:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商空视频详情数据导入模板.xlsx";
    		List<SkMvDetails> list = Lists.newArrayList(); 
    		new ExportExcel("商空视频详情数据", SkMvDetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/skMvDetails/?repage";
    }

}