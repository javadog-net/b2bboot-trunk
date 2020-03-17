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
import com.jhmis.modules.wechat.entity.WxMember;
import com.jhmis.modules.wechat.service.WxMemberService;

/**
 * 微信会员信息表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxMember")
public class WxMemberController extends BaseController {

	@Autowired
	private WxMemberService wxMemberService;
	
	@ModelAttribute
	public WxMember get(@RequestParam(required=false) String id) {
		WxMember entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxMemberService.get(id);
		}
		if (entity == null){
			entity = new WxMember();
		}
		return entity;
	}
	
	/**
	 * 微信会员信息表列表页面
	 */
	@RequiresPermissions("wechat:wxMember:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/wxMemberList";
	}
	
	/**
	 * 微信会员信息表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMember:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMember wxMember, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxMember> page = wxMemberService.findPage(new Page<WxMember>(request, response), wxMember); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑微信会员信息表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxMember:view","wechat:wxMember:add","wechat:wxMember:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMember wxMember, Model model) {
		model.addAttribute("wxMember", wxMember);
		if(StringUtils.isBlank(wxMember.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/wxMemberForm";
	}

	/**
	 * 保存微信会员信息表
	 */
	@RequiresPermissions(value={"wechat:wxMember:add","wechat:wxMember:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxMember wxMember, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxMember)){
			return form(wxMember, model);
		}
		//新增或编辑表单保存
		wxMemberService.save(wxMember);//保存
		addMessage(redirectAttributes, "保存微信会员信息表成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMember/?repage";
	}
	
	/**
	 * 删除微信会员信息表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMember:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMember wxMember, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxMemberService.delete(wxMember);
		j.setMsg("删除微信会员信息表成功");
		return j;
	}
	
	/**
	 * 批量删除微信会员信息表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMember:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxMemberService.delete(wxMemberService.get(id));
		}
		j.setMsg("删除微信会员信息表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMember:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxMember wxMember, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "微信会员信息表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxMember> page = wxMemberService.findPage(new Page<WxMember>(request, response, -1), wxMember);
    		new ExportExcel("微信会员信息表", WxMember.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出微信会员信息表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxMember:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMember> list = ei.getDataList(WxMember.class);
			for (WxMember wxMember : list){
				try{
					wxMemberService.save(wxMember);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微信会员信息表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条微信会员信息表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入微信会员信息表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMember/?repage";
    }
	
	/**
	 * 下载导入微信会员信息表数据模板
	 */
	@RequiresPermissions("wechat:wxMember:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微信会员信息表数据导入模板.xlsx";
    		List<WxMember> list = Lists.newArrayList(); 
    		new ExportExcel("微信会员信息表数据", WxMember.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxMember/?repage";
    }

}