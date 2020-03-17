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
import com.jhmis.modules.shop.entity.Cart;
import com.jhmis.modules.shop.service.CartService;

/**
 * 购物车Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/cart")
public class CartController extends BaseController {

	@Autowired
	private CartService cartService;
	
	@ModelAttribute
	public Cart get(@RequestParam(required=false) String id) {
		Cart entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cartService.get(id);
		}
		if (entity == null){
			entity = new Cart();
		}
		return entity;
	}
	
	/**
	 * 购物车列表页面
	 */
	@RequiresPermissions("shop:cart:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/cartList";
	}
	
	/**
	 * 购物车列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:cart:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Cart cart, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Cart> page = cartService.findPage(new Page<Cart>(request, response), cart); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑购物车表单页面
	 */
	@RequiresPermissions(value={"shop:cart:view","shop:cart:add","shop:cart:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Cart cart, Model model) {
		model.addAttribute("cart", cart);
		return "modules/shop/cartForm";
	}

	/**
	 * 保存购物车
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:cart:add","shop:cart:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Cart cart, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, cart)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		cartService.save(cart);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存购物车成功");
		return j;
	}
	
	/**
	 * 删除购物车
	 */
	@ResponseBody
	@RequiresPermissions("shop:cart:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Cart cart, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cartService.delete(cart);
		j.setMsg("删除购物车成功");
		return j;
	}
	
	/**
	 * 批量删除购物车
	 */
	@ResponseBody
	@RequiresPermissions("shop:cart:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cartService.delete(cartService.get(id));
		}
		j.setMsg("删除购物车成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:cart:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Cart cart, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "购物车"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Cart> page = cartService.findPage(new Page<Cart>(request, response, -1), cart);
    		new ExportExcel("购物车", Cart.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出购物车记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:cart:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Cart> list = ei.getDataList(Cart.class);
			for (Cart cart : list){
				try{
					cartService.save(cart);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条购物车记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条购物车记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入购物车失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/cart/?repage";
    }
	
	/**
	 * 下载导入购物车数据模板
	 */
	@RequiresPermissions("shop:cart:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "购物车数据导入模板.xlsx";
    		List<Cart> list = Lists.newArrayList(); 
    		new ExportExcel("购物车数据", Cart.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/cart/?repage";
    }

}