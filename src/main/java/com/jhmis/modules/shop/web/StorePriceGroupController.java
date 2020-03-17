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
import com.jhmis.modules.shop.entity.StorePriceGroup;
import com.jhmis.modules.shop.service.StorePriceGroupService;

/**
 * 商品价格分组Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/storePriceGroup")
public class StorePriceGroupController extends BaseController {

	@Autowired
	private StorePriceGroupService storePriceGroupService;
	
	@ModelAttribute
	public StorePriceGroup get(@RequestParam(required=false) String id) {
		StorePriceGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = storePriceGroupService.get(id);
		}
		if (entity == null){
			entity = new StorePriceGroup();
		}
		return entity;
	}
	
	/**
	 * 商品价格分组列表页面
	 */
	@RequiresPermissions("shop:storePriceGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/storePriceGroupList";
	}
	
	/**
	 * 商品价格分组列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:storePriceGroup:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StorePriceGroup storePriceGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StorePriceGroup> page = storePriceGroupService.findPage(new Page<StorePriceGroup>(request, response), storePriceGroup); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品价格分组表单页面
	 */
	@RequiresPermissions(value={"shop:storePriceGroup:view","shop:storePriceGroup:add","shop:storePriceGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StorePriceGroup storePriceGroup, Model model) {
		model.addAttribute("storePriceGroup", storePriceGroup);
		return "modules/shop/storePriceGroupForm";
	}

	/**
	 * 保存商品价格分组
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:storePriceGroup:add","shop:storePriceGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StorePriceGroup storePriceGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, storePriceGroup)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		storePriceGroupService.save(storePriceGroup);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品价格分组成功");
		return j;
	}
	
	/**
	 * 删除商品价格分组
	 */
	@ResponseBody
	@RequiresPermissions("shop:storePriceGroup:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StorePriceGroup storePriceGroup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		storePriceGroupService.delete(storePriceGroup);
		j.setMsg("删除商品价格分组成功");
		return j;
	}
	
	/**
	 * 批量删除商品价格分组
	 */
	@ResponseBody
	@RequiresPermissions("shop:storePriceGroup:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			storePriceGroupService.delete(storePriceGroupService.get(id));
		}
		j.setMsg("删除商品价格分组成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:storePriceGroup:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(StorePriceGroup storePriceGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品价格分组"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StorePriceGroup> page = storePriceGroupService.findPage(new Page<StorePriceGroup>(request, response, -1), storePriceGroup);
    		new ExportExcel("商品价格分组", StorePriceGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品价格分组记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:storePriceGroup:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StorePriceGroup> list = ei.getDataList(StorePriceGroup.class);
			for (StorePriceGroup storePriceGroup : list){
				try{
					storePriceGroupService.save(storePriceGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品价格分组记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品价格分组记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品价格分组失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storePriceGroup/?repage";
    }
	
	/**
	 * 下载导入商品价格分组数据模板
	 */
	@RequiresPermissions("shop:storePriceGroup:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品价格分组数据导入模板.xlsx";
    		List<StorePriceGroup> list = Lists.newArrayList(); 
    		new ExportExcel("商品价格分组数据", StorePriceGroup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storePriceGroup/?repage";
    }

}