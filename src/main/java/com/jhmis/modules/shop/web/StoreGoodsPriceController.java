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
import com.jhmis.modules.shop.entity.StoreGoodsPrice;
import com.jhmis.modules.shop.service.StoreGoodsPriceService;

/**
 * 店铺商品价格Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/storeGoodsPrice")
public class StoreGoodsPriceController extends BaseController {

	@Autowired
	private StoreGoodsPriceService storeGoodsPriceService;
	
	@ModelAttribute
	public StoreGoodsPrice get(@RequestParam(required=false) String id) {
		StoreGoodsPrice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = storeGoodsPriceService.get(id);
		}
		if (entity == null){
			entity = new StoreGoodsPrice();
		}
		return entity;
	}
	
	/**
	 * 商品价格列表页面
	 */
	@RequiresPermissions("shop:storeGoodsPrice:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/storeGoodsPriceList";
	}
	
	/**
	 * 商品价格列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeGoodsPrice:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StoreGoodsPrice storeGoodsPrice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StoreGoodsPrice> page = storeGoodsPriceService.findPage(new Page<StoreGoodsPrice>(request, response), storeGoodsPrice); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品价格表单页面
	 */
	@RequiresPermissions(value={"shop:storeGoodsPrice:view","shop:storeGoodsPrice:add","shop:storeGoodsPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StoreGoodsPrice storeGoodsPrice, Model model) {
		model.addAttribute("storeGoodsPrice", storeGoodsPrice);
		return "modules/shop/storeGoodsPriceForm";
	}

	/**
	 * 保存商品价格
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:storeGoodsPrice:add","shop:storeGoodsPrice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(StoreGoodsPrice storeGoodsPrice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, storeGoodsPrice)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		storeGoodsPriceService.save(storeGoodsPrice);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品价格成功");
		return j;
	}
	
	/**
	 * 删除商品价格
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeGoodsPrice:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StoreGoodsPrice storeGoodsPrice, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		storeGoodsPriceService.delete(storeGoodsPrice);
		j.setMsg("删除商品价格成功");
		return j;
	}
	
	/**
	 * 批量删除商品价格
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeGoodsPrice:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			storeGoodsPriceService.delete(storeGoodsPriceService.get(id));
		}
		j.setMsg("删除商品价格成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeGoodsPrice:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(StoreGoodsPrice storeGoodsPrice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品价格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StoreGoodsPrice> page = storeGoodsPriceService.findPage(new Page<StoreGoodsPrice>(request, response, -1), storeGoodsPrice);
    		new ExportExcel("商品价格", StoreGoodsPrice.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品价格记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:storeGoodsPrice:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StoreGoodsPrice> list = ei.getDataList(StoreGoodsPrice.class);
			for (StoreGoodsPrice storeGoodsPrice : list){
				try{
					storeGoodsPriceService.save(storeGoodsPrice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品价格记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品价格记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品价格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storeGoodsPrice/?repage";
    }
	
	/**
	 * 下载导入商品价格数据模板
	 */
	@RequiresPermissions("shop:storeGoodsPrice:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品价格数据导入模板.xlsx";
    		List<StoreGoodsPrice> list = Lists.newArrayList(); 
    		new ExportExcel("商品价格数据", StoreGoodsPrice.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storeGoodsPrice/?repage";
    }

}