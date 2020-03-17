/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.web;

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
import com.jhmis.modules.old.entity.OldShopProjectProductDetail;
import com.jhmis.modules.old.service.OldShopProjectProductDetailService;

/**
 * 2Controller
 * @author 2
 * @version 2019-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/old/oldShopProjectProductDetail")
public class OldShopProjectProductDetailController extends BaseController {

	@Autowired
	private OldShopProjectProductDetailService oldShopProjectProductDetailService;
	
	@ModelAttribute
	public OldShopProjectProductDetail get(@RequestParam(required=false) String id) {
		OldShopProjectProductDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = oldShopProjectProductDetailService.get(id);
		}
		if (entity == null){
			entity = new OldShopProjectProductDetail();
		}
		return entity;
	}
	
	/**
	 * 2列表页面
	 */
	@RequiresPermissions("old:oldShopProjectProductDetail:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/old/oldShopProjectProductDetailList";
	}
	
	/**
	 * 2列表数据
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProductDetail:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OldShopProjectProductDetail oldShopProjectProductDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OldShopProjectProductDetail> page = oldShopProjectProductDetailService.findPage(new Page<OldShopProjectProductDetail>(request, response), oldShopProjectProductDetail); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑2表单页面
	 */
	@RequiresPermissions(value={"old:oldShopProjectProductDetail:view","old:oldShopProjectProductDetail:add","old:oldShopProjectProductDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OldShopProjectProductDetail oldShopProjectProductDetail, Model model) {
		model.addAttribute("oldShopProjectProductDetail", oldShopProjectProductDetail);
		if(StringUtils.isBlank(oldShopProjectProductDetail.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/old/oldShopProjectProductDetailForm";
	}

	/**
	 * 保存2
	 */
	@RequiresPermissions(value={"old:oldShopProjectProductDetail:add","old:oldShopProjectProductDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OldShopProjectProductDetail oldShopProjectProductDetail, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, oldShopProjectProductDetail)){
			return form(oldShopProjectProductDetail, model);
		}
		//新增或编辑表单保存
		oldShopProjectProductDetailService.save(oldShopProjectProductDetail);//保存
		addMessage(redirectAttributes, "保存2成功");
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectProductDetail/?repage";
	}
	
	/**
	 * 删除2
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProductDetail:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OldShopProjectProductDetail oldShopProjectProductDetail, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		oldShopProjectProductDetailService.delete(oldShopProjectProductDetail);
		j.setMsg("删除2成功");
		return j;
	}
	
	/**
	 * 批量删除2
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProductDetail:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			oldShopProjectProductDetailService.delete(oldShopProjectProductDetailService.get(id));
		}
		j.setMsg("删除2成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("old:oldShopProjectProductDetail:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OldShopProjectProductDetail oldShopProjectProductDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "2"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OldShopProjectProductDetail> page = oldShopProjectProductDetailService.findPage(new Page<OldShopProjectProductDetail>(request, response, -1), oldShopProjectProductDetail);
    		new ExportExcel("2", OldShopProjectProductDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出2记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("old:oldShopProjectProductDetail:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OldShopProjectProductDetail> list = ei.getDataList(OldShopProjectProductDetail.class);
			for (OldShopProjectProductDetail oldShopProjectProductDetail : list){
				try{
					oldShopProjectProductDetailService.save(oldShopProjectProductDetail);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条2记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条2记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入2失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectProductDetail/?repage";
    }
	
	/**
	 * 下载导入2数据模板
	 */
	@RequiresPermissions("old:oldShopProjectProductDetail:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "2数据导入模板.xlsx";
    		List<OldShopProjectProductDetail> list = Lists.newArrayList(); 
    		new ExportExcel("2数据", OldShopProjectProductDetail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/old/oldShopProjectProductDetail/?repage";
    }

}