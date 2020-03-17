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
import com.jhmis.modules.shop.entity.GoodsProperties;
import com.jhmis.modules.shop.service.GoodsPropertiesService;

/**
 * 商品属性Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsProperties")
public class GoodsPropertiesController extends BaseController {

	@Autowired
	private GoodsPropertiesService goodsPropertiesService;
	
	@ModelAttribute
	public GoodsProperties get(@RequestParam(required=false) String id) {
		GoodsProperties entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsPropertiesService.get(id);
		}
		if (entity == null){
			entity = new GoodsProperties();
		}
		return entity;
	}
	
	/**
	 * 商品属性列表页面
	 */
	@RequiresPermissions("shop:goodsProperties:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/goodsPropertiesList";
	}
	
	/**
	 * 商品属性列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsProperties:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GoodsProperties goodsProperties, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsProperties> page = goodsPropertiesService.findPage(new Page<GoodsProperties>(request, response), goodsProperties); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品属性表单页面
	 */
	@RequiresPermissions(value={"shop:goodsProperties:view","shop:goodsProperties:add","shop:goodsProperties:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsProperties goodsProperties, Model model) {
		model.addAttribute("goodsProperties", goodsProperties);
		return "modules/shop/goodsPropertiesForm";
	}

	/**
	 * 保存商品属性
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:goodsProperties:add","shop:goodsProperties:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GoodsProperties goodsProperties, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, goodsProperties)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		goodsPropertiesService.save(goodsProperties);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品属性成功");
		return j;
	}
	
	/**
	 * 删除商品属性
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsProperties:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsProperties goodsProperties, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsPropertiesService.delete(goodsProperties);
		j.setMsg("删除商品属性成功");
		return j;
	}
	
	/**
	 * 批量删除商品属性
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsProperties:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsPropertiesService.delete(goodsPropertiesService.get(id));
		}
		j.setMsg("删除商品属性成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsProperties:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GoodsProperties goodsProperties, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品属性"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsProperties> page = goodsPropertiesService.findPage(new Page<GoodsProperties>(request, response, -1), goodsProperties);
    		new ExportExcel("商品属性", GoodsProperties.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品属性记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:goodsProperties:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsProperties> list = ei.getDataList(GoodsProperties.class);
			for (GoodsProperties goodsProperties : list){
				try{
					goodsPropertiesService.save(goodsProperties);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品属性记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品属性记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品属性失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsProperties/?repage";
    }
	
	/**
	 * 下载导入商品属性数据模板
	 */
	@RequiresPermissions("shop:goodsProperties:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品属性数据导入模板.xlsx";
    		List<GoodsProperties> list = Lists.newArrayList(); 
    		new ExportExcel("商品属性数据", GoodsProperties.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsProperties/?repage";
    }

}