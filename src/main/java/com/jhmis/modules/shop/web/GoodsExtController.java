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
import com.jhmis.modules.shop.entity.GoodsExt;
import com.jhmis.modules.shop.service.GoodsExtService;

/**
 * 商品扩展资料Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsExt")
public class GoodsExtController extends BaseController {

	@Autowired
	private GoodsExtService goodsExtService;
	
	@ModelAttribute
	public GoodsExt get(@RequestParam(required=false) String id) {
		GoodsExt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsExtService.get(id);
		}
		if (entity == null){
			entity = new GoodsExt();
		}
		return entity;
	}
	
	/**
	 * 商品扩展资料列表页面
	 */
	@RequiresPermissions("shop:goodsExt:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/goodsExtList";
	}
	
	/**
	 * 商品扩展资料列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsExt:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GoodsExt goodsExt, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsExt> page = goodsExtService.findPage(new Page<GoodsExt>(request, response), goodsExt); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品扩展资料表单页面
	 */
	@RequiresPermissions(value={"shop:goodsExt:view","shop:goodsExt:add","shop:goodsExt:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsExt goodsExt, Model model) {
		model.addAttribute("goodsExt", goodsExt);
		if(StringUtils.isBlank(goodsExt.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/goodsExtForm";
	}

	/**
	 * 保存商品扩展资料
	 */
	@RequiresPermissions(value={"shop:goodsExt:add","shop:goodsExt:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(GoodsExt goodsExt, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, goodsExt)){
			return form(goodsExt, model);
		}
		//新增或编辑表单保存
		goodsExtService.save(goodsExt);//保存
		addMessage(redirectAttributes, "保存商品扩展资料成功");
		return "redirect:"+Global.getAdminPath()+"/shop/goodsExt/?repage";
	}
	
	/**
	 * 删除商品扩展资料
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsExt:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsExt goodsExt, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsExtService.delete(goodsExt);
		j.setMsg("删除商品扩展资料成功");
		return j;
	}
	
	/**
	 * 批量删除商品扩展资料
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsExt:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsExtService.delete(goodsExtService.get(id));
		}
		j.setMsg("删除商品扩展资料成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsExt:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GoodsExt goodsExt, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品扩展资料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsExt> page = goodsExtService.findPage(new Page<GoodsExt>(request, response, -1), goodsExt);
    		new ExportExcel("商品扩展资料", GoodsExt.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品扩展资料记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:goodsExt:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsExt> list = ei.getDataList(GoodsExt.class);
			for (GoodsExt goodsExt : list){
				try{
					goodsExtService.save(goodsExt);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品扩展资料记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品扩展资料记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品扩展资料失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsExt/?repage";
    }
	
	/**
	 * 下载导入商品扩展资料数据模板
	 */
	@RequiresPermissions("shop:goodsExt:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品扩展资料数据导入模板.xlsx";
    		List<GoodsExt> list = Lists.newArrayList(); 
    		new ExportExcel("商品扩展资料数据", GoodsExt.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsExt/?repage";
    }

}