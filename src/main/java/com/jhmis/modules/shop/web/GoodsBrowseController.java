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
import com.jhmis.modules.shop.entity.GoodsBrowse;
import com.jhmis.modules.shop.service.GoodsBrowseService;

/**
 * 商品浏览记录Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsBrowse")
public class GoodsBrowseController extends BaseController {

	@Autowired
	private GoodsBrowseService goodsBrowseService;
	
	@ModelAttribute
	public GoodsBrowse get(@RequestParam(required=false) String id) {
		GoodsBrowse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsBrowseService.get(id);
		}
		if (entity == null){
			entity = new GoodsBrowse();
		}
		return entity;
	}
	
	/**
	 * 商品浏览记录列表页面
	 */
	@RequiresPermissions("shop:goodsBrowse:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/goodsBrowseList";
	}
	
	/**
	 * 商品浏览记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsBrowse:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GoodsBrowse goodsBrowse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsBrowse> page = goodsBrowseService.findPage(new Page<GoodsBrowse>(request, response), goodsBrowse); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品浏览记录表单页面
	 */
	@RequiresPermissions(value={"shop:goodsBrowse:view","shop:goodsBrowse:add","shop:goodsBrowse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsBrowse goodsBrowse, Model model) {
		model.addAttribute("goodsBrowse", goodsBrowse);
		return "modules/shop/goodsBrowseForm";
	}

	/**
	 * 保存商品浏览记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:goodsBrowse:add","shop:goodsBrowse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GoodsBrowse goodsBrowse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, goodsBrowse)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		goodsBrowseService.save(goodsBrowse);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品浏览记录成功");
		return j;
	}
	
	/**
	 * 删除商品浏览记录
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsBrowse:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsBrowse goodsBrowse, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsBrowseService.delete(goodsBrowse);
		j.setMsg("删除商品浏览记录成功");
		return j;
	}
	
	/**
	 * 批量删除商品浏览记录
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsBrowse:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsBrowseService.delete(goodsBrowseService.get(id));
		}
		j.setMsg("删除商品浏览记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsBrowse:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GoodsBrowse goodsBrowse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品浏览记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsBrowse> page = goodsBrowseService.findPage(new Page<GoodsBrowse>(request, response, -1), goodsBrowse);
    		new ExportExcel("商品浏览记录", GoodsBrowse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品浏览记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:goodsBrowse:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsBrowse> list = ei.getDataList(GoodsBrowse.class);
			for (GoodsBrowse goodsBrowse : list){
				try{
					goodsBrowseService.save(goodsBrowse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品浏览记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品浏览记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品浏览记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsBrowse/?repage";
    }
	
	/**
	 * 下载导入商品浏览记录数据模板
	 */
	@RequiresPermissions("shop:goodsBrowse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品浏览记录数据导入模板.xlsx";
    		List<GoodsBrowse> list = Lists.newArrayList(); 
    		new ExportExcel("商品浏览记录数据", GoodsBrowse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsBrowse/?repage";
    }

}