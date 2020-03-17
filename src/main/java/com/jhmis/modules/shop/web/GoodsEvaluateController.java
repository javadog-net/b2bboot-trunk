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
import com.jhmis.modules.shop.entity.GoodsEvaluate;
import com.jhmis.modules.shop.service.GoodsEvaluateService;

/**
 * 商品评价Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsEvaluate")
public class GoodsEvaluateController extends BaseController {

	@Autowired
	private GoodsEvaluateService goodsEvaluateService;
	
	@ModelAttribute
	public GoodsEvaluate get(@RequestParam(required=false) String id) {
		GoodsEvaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsEvaluateService.get(id);
		}
		if (entity == null){
			entity = new GoodsEvaluate();
		}
		return entity;
	}
	
	/**
	 * 商品评价列表页面
	 */
	@RequiresPermissions("shop:goodsEvaluate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/goodsEvaluateList";
	}
	
	/**
	 * 商品评价列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsEvaluate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GoodsEvaluate goodsEvaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsEvaluate> page = goodsEvaluateService.findPage(new Page<GoodsEvaluate>(request, response), goodsEvaluate); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品评价表单页面
	 */
	@RequiresPermissions(value={"shop:goodsEvaluate:view","shop:goodsEvaluate:add","shop:goodsEvaluate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsEvaluate goodsEvaluate, Model model) {
		model.addAttribute("goodsEvaluate", goodsEvaluate);
		return "modules/shop/goodsEvaluateForm";
	}

	/**
	 * 保存商品评价
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:goodsEvaluate:add","shop:goodsEvaluate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GoodsEvaluate goodsEvaluate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, goodsEvaluate)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		goodsEvaluateService.save(goodsEvaluate);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品评价成功");
		return j;
	}
	
	/**
	 * 删除商品评价
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsEvaluate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsEvaluate goodsEvaluate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsEvaluateService.delete(goodsEvaluate);
		j.setMsg("删除商品评价成功");
		return j;
	}
	
	/**
	 * 批量删除商品评价
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsEvaluate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsEvaluateService.delete(goodsEvaluateService.get(id));
		}
		j.setMsg("删除商品评价成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsEvaluate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GoodsEvaluate goodsEvaluate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品评价"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsEvaluate> page = goodsEvaluateService.findPage(new Page<GoodsEvaluate>(request, response, -1), goodsEvaluate);
    		new ExportExcel("商品评价", GoodsEvaluate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品评价记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:goodsEvaluate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsEvaluate> list = ei.getDataList(GoodsEvaluate.class);
			for (GoodsEvaluate goodsEvaluate : list){
				try{
					goodsEvaluateService.save(goodsEvaluate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品评价记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品评价记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品评价失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsEvaluate/?repage";
    }
	
	/**
	 * 下载导入商品评价数据模板
	 */
	@RequiresPermissions("shop:goodsEvaluate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品评价数据导入模板.xlsx";
    		List<GoodsEvaluate> list = Lists.newArrayList(); 
    		new ExportExcel("商品评价数据", GoodsEvaluate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsEvaluate/?repage";
    }

}