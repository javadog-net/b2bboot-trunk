/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.ibatis.annotations.Param;
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
import com.jhmis.modules.wechat.entity.CmsPrize;
import com.jhmis.modules.wechat.service.CmsPrizeService;

/**
 * 奖项表操作Controller
 * @author tc
 * @version 2019-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/cmsPrize")
public class CmsPrizeController extends BaseController {

	@Autowired
	private CmsPrizeService cmsPrizeService;
	
	@ModelAttribute
	public CmsPrize get(@RequestParam(required=false) String id) {
		CmsPrize entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsPrizeService.get(id);
		}
		if (entity == null){
			entity = new CmsPrize();
		}
		return entity;
	}
	
	/**
	 * 奖项表列表页面
	 */
	@RequiresPermissions("wechat:cmsPrize:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/cmsPrizeList";
	}
	
	/**
	 * 奖项表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsPrize:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsPrize cmsPrize, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsPrize> page = cmsPrizeService.findPage(new Page<CmsPrize>(request, response), cmsPrize); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑奖项表表单页面
	 */
	@RequiresPermissions(value={"wechat:cmsPrize:view","wechat:cmsPrize:add","wechat:cmsPrize:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsPrize cmsPrize, Model model) {
		model.addAttribute("cmsPrize", cmsPrize);
		if(StringUtils.isBlank(cmsPrize.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/cmsPrizeForm";
	}

	/**
	 * 保存奖项表
	 */
	@RequiresPermissions(value={"wechat:cmsPrize:add","wechat:cmsPrize:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsPrize cmsPrize, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsPrize)){
			return form(cmsPrize, model);
		}
		//新增或编辑表单保存
		cmsPrizeService.save(cmsPrize);//保存
		addMessage(redirectAttributes, "保存奖项表成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/cmsPrize/?repage";
	}
	
	/**
	 * 删除奖项表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsPrize:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsPrize cmsPrize, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsPrizeService.delete(cmsPrize);
		j.setMsg("删除奖项表成功");
		return j;
	}
	
	/**
	 * 批量删除奖项表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsPrize:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsPrizeService.delete(cmsPrizeService.get(id));
		}
		j.setMsg("删除奖项表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsPrize:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CmsPrize cmsPrize, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "奖项表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsPrize> page = cmsPrizeService.findPage(new Page<CmsPrize>(request, response, -1), cmsPrize);
    		new ExportExcel("奖项表", CmsPrize.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出奖项表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:cmsPrize:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsPrize> list = ei.getDataList(CmsPrize.class);
			for (CmsPrize cmsPrize : list){
				try{
					cmsPrizeService.save(cmsPrize);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条奖项表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条奖项表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入奖项表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/cmsPrize/?repage";
    }
	
	/**
	 * 下载导入奖项表数据模板
	 */
	@RequiresPermissions("wechat:cmsPrize:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "奖项表数据导入模板.xlsx";
    		List<CmsPrize> list = Lists.newArrayList(); 
    		new ExportExcel("奖项表数据", CmsPrize.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/cmsPrize/?repage";
    }

	
	
}