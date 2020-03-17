/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.wechat.entity.WxFriendGroup;
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
import com.jhmis.modules.wechat.service.FriendGroupService;

/**
 * 分组表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/friendGroup")
public class FriendGroupController extends BaseController {

	@Autowired
	private FriendGroupService friendGroupService;
	
	@ModelAttribute
	public WxFriendGroup get(@RequestParam(required=false) String id) {
		WxFriendGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = friendGroupService.get(id);
		}
		if (entity == null){
			entity = new WxFriendGroup();
		}
		return entity;
	}
	
	/**
	 * 分组表列表页面
	 */
	@RequiresPermissions("wechat:friendGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/friendGroupList";
	}
	
	/**
	 * 分组表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriendGroup:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxFriendGroup wxFriendGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxFriendGroup> page = friendGroupService.findPage(new Page<WxFriendGroup>(request, response), wxFriendGroup);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑分组表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxFriendGroup:view","wechat:wxFriendGroup:add","wechat:wxFriendGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxFriendGroup wxFriendGroup, Model model) {
		model.addAttribute("friendGroup", wxFriendGroup);
		return "modules/wechat/friendGroupForm";
	}

	/**
	 * 保存分组表
	 */
	@ResponseBody
	@RequiresPermissions(value={"wechat:wxFriendGroup:add","wechat:wxFriendGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WxFriendGroup wxFriendGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, wxFriendGroup)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		friendGroupService.save(wxFriendGroup);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存分组表成功");
		return j;
	}
	
	/**
	 * 删除分组表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriendGroup:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxFriendGroup wxFriendGroup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		friendGroupService.delete(wxFriendGroup);
		j.setMsg("删除分组表成功");
		return j;
	}
	
	/**
	 * 批量删除分组表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:friendGroup:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			friendGroupService.delete(friendGroupService.get(id));
		}
		j.setMsg("删除分组表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxFriendGroup:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxFriendGroup wxFriendGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "分组表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxFriendGroup> page = friendGroupService.findPage(new Page<WxFriendGroup>(request, response, -1), wxFriendGroup);
    		new ExportExcel("分组表", WxFriendGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出分组表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:friendGroup:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxFriendGroup> list = ei.getDataList(WxFriendGroup.class);
			for (WxFriendGroup wxFriendGroup : list){
				try{
					friendGroupService.save(wxFriendGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条分组表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条分组表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入分组表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/friendGroup/?repage";
    }
	
	/**
	 * 下载导入分组表数据模板
	 */
	@RequiresPermissions("wechat:friendGroup:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分组表数据导入模板.xlsx";
    		List<WxFriendGroup> list = Lists.newArrayList();
    		new ExportExcel("分组表数据", WxFriendGroup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/friendGroup/?repage";
    }

}