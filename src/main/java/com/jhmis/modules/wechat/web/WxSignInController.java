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
import com.jhmis.modules.wechat.entity.WxSignIn;
import com.jhmis.modules.wechat.service.WxSignInService;

/**
 * 签到表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxSignIn")
public class WxSignInController extends BaseController {

	@Autowired
	private WxSignInService wxSignInService;
	
	@ModelAttribute
	public WxSignIn get(@RequestParam(required=false) String id) {
		WxSignIn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxSignInService.get(id);
		}
		if (entity == null){
			entity = new WxSignIn();
		}
		return entity;
	}
	
	/**
	 * 签到表列表页面
	 */
	@RequiresPermissions("wechat:wxSignIn:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxSignInList";
	}
	
	/**
	 * 签到表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxSignIn:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxSignIn wxSignIn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxSignIn> page = wxSignInService.findPage(new Page<WxSignIn>(request, response), wxSignIn); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑签到表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxSignIn:view","wechat:wxSignIn:add","wechat:wxSignIn:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxSignIn wxSignIn, Model model) {
		model.addAttribute("wxSignIn", wxSignIn);
		if(StringUtils.isBlank(wxSignIn.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxSignInForm";
	}

	/**
	 * 保存签到表
	 */
	@RequiresPermissions(value={"wechat:wxSignIn:add","wechat:wxSignIn:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxSignIn wxSignIn, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxSignIn)){
			return form(wxSignIn, model);
		}
		//新增或编辑表单保存
		wxSignInService.save(wxSignIn);//保存
		addMessage(redirectAttributes, "保存签到表成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxSignIn/?repage";
	}
	
	/**
	 * 删除签到表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxSignIn:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxSignIn wxSignIn, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxSignInService.delete(wxSignIn);
		j.setMsg("删除签到表成功");
		return j;
	}
	
	/**
	 * 批量删除签到表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxSignIn:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxSignInService.delete(wxSignInService.get(id));
		}
		j.setMsg("删除签到表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxSignIn:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxSignIn wxSignIn, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "签到表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxSignIn> page = wxSignInService.findPage(new Page<WxSignIn>(request, response, -1), wxSignIn);
    		new ExportExcel("签到表", WxSignIn.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出签到表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxSignIn:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxSignIn> list = ei.getDataList(WxSignIn.class);
			for (WxSignIn wxSignIn : list){
				try{
					wxSignInService.save(wxSignIn);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条签到表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条签到表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入签到表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxSignIn/?repage";
    }
	
	/**
	 * 下载导入签到表数据模板
	 */
	@RequiresPermissions("wechat:wxSignIn:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "签到表数据导入模板.xlsx";
    		List<WxSignIn> list = Lists.newArrayList(); 
    		new ExportExcel("签到表数据", WxSignIn.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxSignIn/?repage";
    }

}