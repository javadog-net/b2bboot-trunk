/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.modules.shop.entity.GoodsClass;
import com.jhmis.modules.shop.service.GoodsClassService;
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
import com.jhmis.modules.shop.entity.GoodsClassProperties;
import com.jhmis.modules.shop.service.GoodsClassPropertiesService;

/**
 * 商品属性Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsClassProperties")
public class GoodsClassPropertiesController extends BaseController {

	@Autowired
	private GoodsClassPropertiesService goodsClassPropertiesService;

	@Autowired
	private GoodsClassService goodsClassService;
	
	@ModelAttribute
	public GoodsClassProperties get(@RequestParam(required=false) String id) {
		GoodsClassProperties entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsClassPropertiesService.get(id);
		}
		if (entity == null){
			entity = new GoodsClassProperties();
		}
		return entity;
	}
	
	/**
	 * 商品属性列表页面
	 */
	@RequiresPermissions("shop:goodsClassProperties:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/goodsClassPropertiesList";
	}
	
	/**
	 * 商品属性列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClassProperties:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GoodsClassProperties goodsClassProperties, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsClassProperties> page = goodsClassPropertiesService.findPage(new Page<GoodsClassProperties>(request, response), goodsClassProperties); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品属性表单页面
	 */
	@RequiresPermissions(value={"shop:goodsClassProperties:view","shop:goodsClassProperties:add","shop:goodsClassProperties:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsClassProperties goodsClassProperties, Model model) {
		model.addAttribute("goodsClassProperties", goodsClassProperties);
		return "modules/shop/goodsClassPropertiesForm";
	}

	/**
	 * 保存商品属性
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:goodsClassProperties:add","shop:goodsClassProperties:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GoodsClassProperties goodsClassProperties, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, goodsClassProperties)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		if(StringUtils.isEmpty(goodsClassProperties.getId())){
			//新增操作进行查重
			GoodsClassProperties gcp = new GoodsClassProperties();
			gcp.setName(goodsClassProperties.getName());
			gcp.setClassId(goodsClassProperties.getClassId());
			List<GoodsClassProperties> goodsClassPropertiesList = goodsClassPropertiesService.findList(gcp);
			if(goodsClassPropertiesList!=null && goodsClassPropertiesList.size()>0){
				//名称重复
				j.setSuccess(false);
				j.setMsg("属性名称不能重复！");
				return j;
			}
		}
		goodsClassPropertiesService.save(goodsClassProperties);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品属性成功");
		return j;
	}
	
	/**
	 * 删除商品属性
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClassProperties:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsClassProperties goodsClassProperties, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsClassPropertiesService.delete(goodsClassProperties);
		j.setMsg("删除商品属性成功");
		return j;
	}
	
	/**
	 * 批量删除商品属性
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClassProperties:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsClassPropertiesService.delete(goodsClassPropertiesService.get(id));
		}
		j.setMsg("删除商品属性成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClassProperties:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GoodsClassProperties goodsClassProperties, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品属性"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsClassProperties> page = goodsClassPropertiesService.findPage(new Page<GoodsClassProperties>(request, response, -1), goodsClassProperties);
    		new ExportExcel("商品属性", GoodsClassProperties.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("shop:goodsClassProperties:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsClassProperties> list = ei.getDataList(GoodsClassProperties.class);
			for (GoodsClassProperties goodsClassProperties : list){
				try{
					goodsClassPropertiesService.save(goodsClassProperties);
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
		return "redirect:"+Global.getAdminPath()+"/shop/goodsClassProperties/?repage";
    }
	
	/**
	 * 下载导入商品属性数据模板
	 */
	@RequiresPermissions("shop:goodsClassProperties:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品属性数据导入模板.xlsx";
    		List<GoodsClassProperties> list = Lists.newArrayList(); 
    		new ExportExcel("商品属性数据", GoodsClassProperties.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsClassProperties/?repage";
    }

}