/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

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
import com.jhmis.modules.shop.entity.StoreEvaluate;
import com.jhmis.modules.shop.service.StoreEvaluateService;

/**
 * 店铺评分Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/storeEvaluate")
public class StoreEvaluateController extends BaseController {

	@Autowired
	private StoreEvaluateService storeEvaluateService;
	
	@ModelAttribute
	public StoreEvaluate get(@RequestParam(required=false) String id) {
		StoreEvaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = storeEvaluateService.get(id);
		}
		if (entity == null){
			entity = new StoreEvaluate();
		}
		return entity;
	}
	
	/**
	 * 店铺评分列表页面
	 */
	@RequiresPermissions("shop:storeEvaluate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/storeEvaluateList";
	}
	
	/**
	 * 店铺评分列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeEvaluate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StoreEvaluate storeEvaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StoreEvaluate> page = storeEvaluateService.findPage(new Page<StoreEvaluate>(request, response), storeEvaluate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑店铺评分表单页面
	 */
	@RequiresPermissions(value={"shop:storeEvaluate:view","shop:storeEvaluate:add","shop:storeEvaluate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StoreEvaluate storeEvaluate, Model model) {
		model.addAttribute("storeEvaluate", storeEvaluate);
		return "modules/shop/storeEvaluateForm";
	}

	/**
	 * 保存店铺评分
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:storeEvaluate:add","shop:storeEvaluate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StoreEvaluate storeEvaluate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, storeEvaluate)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		storeEvaluateService.save(storeEvaluate);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存店铺评分成功");
		return j;
	}
	
	/**
	 * 删除店铺评分
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeEvaluate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StoreEvaluate storeEvaluate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		storeEvaluateService.delete(storeEvaluate);
		j.setMsg("删除店铺评分成功");
		return j;
	}
	
	/**
	 * 批量删除店铺评分
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeEvaluate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			storeEvaluateService.delete(storeEvaluateService.get(id));
		}
		j.setMsg("删除店铺评分成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeEvaluate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(StoreEvaluate storeEvaluate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "店铺评分"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StoreEvaluate> page = storeEvaluateService.findPage(new Page<StoreEvaluate>(request, response, -1), storeEvaluate);
    		new ExportExcel("店铺评分", StoreEvaluate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出店铺评分记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:storeEvaluate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StoreEvaluate> list = ei.getDataList(StoreEvaluate.class);
			for (StoreEvaluate storeEvaluate : list){
				try{
					storeEvaluateService.save(storeEvaluate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条店铺评分记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条店铺评分记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入店铺评分失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storeEvaluate/?repage";
    }
	
	/**
	 * 下载导入店铺评分数据模板
	 */
	@RequiresPermissions("shop:storeEvaluate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "店铺评分数据导入模板.xlsx";
    		List<StoreEvaluate> list = Lists.newArrayList(); 
    		new ExportExcel("店铺评分数据", StoreEvaluate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storeEvaluate/?repage";
    }

}