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
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.service.StoreService;

/**
 * 店铺管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/store")
public class StoreController extends BaseController {

	@Autowired
	private StoreService storeService;
	
	@ModelAttribute
	public Store get(@RequestParam(required=false) String id) {
		Store entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = storeService.get(id);
		}
		if (entity == null){
			entity = new Store();
		}
		return entity;
	}
	
	/**
	 * 店铺列表页面
	 */
	@RequiresPermissions("shop:store:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/storeList";
	}
	
	/**
	 * 店铺列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:store:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Store store, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Store> page = storeService.findPage(new Page<Store>(request, response), store); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑店铺表单页面
	 */
	@RequiresPermissions(value={"shop:store:view","shop:store:add","shop:store:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Store store, Model model) {
		model.addAttribute("store", store);
		if(StringUtils.isBlank(store.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/storeForm";
	}

	/**
	 * 保存店铺
	 */
	@RequiresPermissions(value={"shop:store:add","shop:store:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Store store, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, store)){
			return form(store, model);
		}
		//新增或编辑表单保存
		storeService.save(store);//保存
		addMessage(redirectAttributes, "保存店铺成功");
		return "redirect:"+Global.getAdminPath()+"/shop/store/?repage";
	}
	
	/**
	 * 删除店铺
	 */
	@ResponseBody
	@RequiresPermissions("shop:store:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Store store, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		storeService.delete(store);
		j.setMsg("删除店铺成功");
		return j;
	}
	
	/**
	 * 批量删除店铺
	 */
	@ResponseBody
	@RequiresPermissions("shop:store:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			Store store = new Store();
			store.setId(id);
			store.setDelFlag("1");
			storeService.delete(store);
		}
		j.setMsg("删除店铺成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:store:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Store store, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "店铺"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Store> page = storeService.findPage(new Page<Store>(request, response, -1), store);
    		new ExportExcel("店铺", Store.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出店铺记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:store:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Store> list = ei.getDataList(Store.class);
			for (Store store : list){
				try{
					storeService.save(store);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条店铺记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条店铺记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入店铺失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/store/?repage";
    }
	
	/**
	 * 下载导入店铺数据模板
	 */
	@RequiresPermissions("shop:store:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "店铺数据导入模板.xlsx";
    		List<Store> list = Lists.newArrayList(); 
    		new ExportExcel("店铺数据", Store.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/store/?repage";
    }

}