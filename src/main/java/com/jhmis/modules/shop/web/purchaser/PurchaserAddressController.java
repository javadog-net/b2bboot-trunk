/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.purchaser;

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
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;

/**
 * 采购商地址管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaserAddress")
public class PurchaserAddressController extends BaseController {

	@Autowired
	private PurchaserAddressService purchaserAddressService;
	
	@ModelAttribute
	public PurchaserAddress get(@RequestParam(required=false) String id) {
		PurchaserAddress entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserAddressService.get(id);
		}
		if (entity == null){
			entity = new PurchaserAddress();
		}
		return entity;
	}
	
	/**
	 * 收货地址列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaserAddress:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/purchaser/purchaserAddressList";
	}
	
	/**
	 * 收货地址列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserAddress:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserAddress purchaserAddress, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserAddress> page = purchaserAddressService.findPage(new Page<PurchaserAddress>(request, response), purchaserAddress); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑收货地址表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserAddress:view","shop:purchaser:purchaserAddress:add","shop:purchaser:purchaserAddress:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserAddress purchaserAddress, Model model) {
		model.addAttribute("purchaserAddress", purchaserAddress);
		return "modules/shop/purchaser/purchaserAddressForm";
	}

	/**
	 * 保存收货地址
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:purchaser:purchaserAddress:add","shop:purchaser:purchaserAddress:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PurchaserAddress purchaserAddress, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, purchaserAddress)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		purchaserAddressService.save(purchaserAddress);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存收货地址成功");
		return j;
	}
	
	/**
	 * 删除收货地址
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserAddress:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserAddress purchaserAddress, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserAddressService.delete(purchaserAddress);
		j.setMsg("删除收货地址成功");
		return j;
	}
	
	/**
	 * 批量删除收货地址
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserAddress:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserAddressService.delete(purchaserAddressService.get(id));
		}
		j.setMsg("删除收货地址成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserAddress:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserAddress purchaserAddress, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "收货地址"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserAddress> page = purchaserAddressService.findPage(new Page<PurchaserAddress>(request, response, -1), purchaserAddress);
    		new ExportExcel("收货地址", PurchaserAddress.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出收货地址记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:purchaser:purchaserAddress:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserAddress> list = ei.getDataList(PurchaserAddress.class);
			for (PurchaserAddress purchaserAddress : list){
				try{
					purchaserAddressService.save(purchaserAddress);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条收货地址记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条收货地址记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入收货地址失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserAddress/?repage";
    }
	
	/**
	 * 下载导入收货地址数据模板
	 */
	@RequiresPermissions("shop:purchaser:purchaserAddress:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "收货地址数据导入模板.xlsx";
    		List<PurchaserAddress> list = Lists.newArrayList(); 
    		new ExportExcel("收货地址数据", PurchaserAddress.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserAddress/?repage";
    }

}