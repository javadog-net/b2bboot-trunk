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
import com.jhmis.modules.shop.entity.GoodsClass;
import com.jhmis.modules.shop.service.GoodsClassService;

/**
 * 商品类型管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsClass")
public class GoodsClassController extends BaseController {

	@Autowired
	private GoodsClassService goodsClassService;
	
	@ModelAttribute
	public GoodsClass get(@RequestParam(required=false) String id) {
		GoodsClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsClassService.get(id);
		}
		if (entity == null){
			entity = new GoodsClass();
		}
		return entity;
	}
	
	/**
	 * 商品类型列表页面
	 */
	@RequiresPermissions("shop:goodsClass:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/goodsClassList";
	}
	
	/**
	 * 商品类型列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClass:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GoodsClass goodsClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsClass> page = goodsClassService.findPage(new Page<GoodsClass>(request, response), goodsClass); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品类型表单页面
	 */
	@RequiresPermissions(value={"shop:goodsClass:view","shop:goodsClass:add","shop:goodsClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsClass goodsClass, Model model) {
		model.addAttribute("goodsClass", goodsClass);
		return "modules/shop/goodsClassForm";
	}

	/**
	 * 保存商品类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:goodsClass:add","shop:goodsClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GoodsClass goodsClass, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, goodsClass)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		goodsClassService.save(goodsClass);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品类型成功");
		return j;
	}
	
	/**
	 * 删除商品类型
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClass:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsClass goodsClass, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsClassService.delete(goodsClass);
		j.setMsg("删除商品类型成功");
		return j;
	}
	
	/**
	 * 批量删除商品类型
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClass:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsClassService.delete(goodsClassService.get(id));
		}
		j.setMsg("删除商品类型成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsClass:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GoodsClass goodsClass, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsClass> page = goodsClassService.findPage(new Page<GoodsClass>(request, response, -1), goodsClass);
    		new ExportExcel("商品类型", GoodsClass.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品类型记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:goodsClass:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsClass> list = ei.getDataList(GoodsClass.class);
			for (GoodsClass goodsClass : list){
				try{
					goodsClassService.save(goodsClass);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品类型记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品类型记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品类型失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsClass/?repage";
    }
	
	/**
	 * 下载导入商品类型数据模板
	 */
	@RequiresPermissions("shop:goodsClass:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品类型数据导入模板.xlsx";
    		List<GoodsClass> list = Lists.newArrayList(); 
    		new ExportExcel("商品类型数据", GoodsClass.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsClass/?repage";
    }

}