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
import com.jhmis.modules.shop.entity.GoodsRecommend;
import com.jhmis.modules.shop.service.GoodsRecommendService;

/**
 * 商品推荐Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/goodsRecommend")
public class GoodsRecommendController extends BaseController {

	@Autowired
	private GoodsRecommendService goodsRecommendService;
	
	@ModelAttribute
	public GoodsRecommend get(@RequestParam(required=false) String id) {
		GoodsRecommend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsRecommendService.get(id);
		}
		if (entity == null){
			entity = new GoodsRecommend();
		}
		return entity;
	}
	
	/**
	 * 商品推荐列表页面
	 */
	@RequiresPermissions("shop:goodsRecommend:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/goodsRecommendList";
	}
	
	/**
	 * 商品推荐列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsRecommend:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GoodsRecommend goodsRecommend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsRecommend> page = goodsRecommendService.findPage(new Page<GoodsRecommend>(request, response), goodsRecommend); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商品推荐表单页面
	 */
	@RequiresPermissions(value={"shop:goodsRecommend:view","shop:goodsRecommend:add","shop:goodsRecommend:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GoodsRecommend goodsRecommend, Model model) {
		model.addAttribute("goodsRecommend", goodsRecommend);
		return "modules/shop/goodsRecommendForm";
	}

	/**
	 * 保存商品推荐
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:goodsRecommend:add","shop:goodsRecommend:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GoodsRecommend goodsRecommend, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, goodsRecommend)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		goodsRecommendService.save(goodsRecommend);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存商品推荐成功");
		return j;
	}
	
	/**
	 * 删除商品推荐
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsRecommend:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GoodsRecommend goodsRecommend, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		goodsRecommendService.delete(goodsRecommend);
		j.setMsg("删除商品推荐成功");
		return j;
	}
	
	/**
	 * 批量删除商品推荐
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsRecommend:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			goodsRecommendService.delete(goodsRecommendService.get(id));
		}
		j.setMsg("删除商品推荐成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:goodsRecommend:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GoodsRecommend goodsRecommend, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "商品推荐"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GoodsRecommend> page = goodsRecommendService.findPage(new Page<GoodsRecommend>(request, response, -1), goodsRecommend);
    		new ExportExcel("商品推荐", GoodsRecommend.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商品推荐记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:goodsRecommend:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GoodsRecommend> list = ei.getDataList(GoodsRecommend.class);
			for (GoodsRecommend goodsRecommend : list){
				try{
					goodsRecommendService.save(goodsRecommend);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条商品推荐记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条商品推荐记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商品推荐失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsRecommend/?repage";
    }
	
	/**
	 * 下载导入商品推荐数据模板
	 */
	@RequiresPermissions("shop:goodsRecommend:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "商品推荐数据导入模板.xlsx";
    		List<GoodsRecommend> list = Lists.newArrayList(); 
    		new ExportExcel("商品推荐数据", GoodsRecommend.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/goodsRecommend/?repage";
    }

}