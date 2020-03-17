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
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;

/**
 * 发票信息Controller
 * @author tity
 * @version 2018-08-14
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaserInvoice")
public class PurchaserInvoiceController extends BaseController {

	@Autowired
	private PurchaserInvoiceService purchaserInvoiceService;
	
	@ModelAttribute
	public PurchaserInvoice get(@RequestParam(required=false) String id) {
		PurchaserInvoice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserInvoiceService.get(id);
		}
		if (entity == null){
			entity = new PurchaserInvoice();
		}
		return entity;
	}
	
	/**
	 * 发票列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaserInvoice:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/purchaser/purchaserInvoiceList";
	}
	
	/**
	 * 发票列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserInvoice:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserInvoice purchaserInvoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserInvoice> page = purchaserInvoiceService.findPage(new Page<PurchaserInvoice>(request, response), purchaserInvoice); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑发票表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserInvoice:view","shop:purchaser:purchaserInvoice:add","shop:purchaser:purchaserInvoice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserInvoice purchaserInvoice, Model model) {
		model.addAttribute("purchaserInvoice", purchaserInvoice);
		if(StringUtils.isBlank(purchaserInvoice.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/purchaser/purchaserInvoiceForm";
	}

	/**
	 * 保存发票
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserInvoice:add","shop:purchaser:purchaserInvoice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PurchaserInvoice purchaserInvoice, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, purchaserInvoice)){
			return form(purchaserInvoice, model);
		}
		//新增或编辑表单保存
		purchaserInvoiceService.save(purchaserInvoice);//保存
		addMessage(redirectAttributes, "保存发票成功");
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserInvoice/?repage";
	}
	
	/**
	 * 删除发票
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserInvoice:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserInvoice purchaserInvoice, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserInvoiceService.delete(purchaserInvoice);
		j.setMsg("删除发票成功");
		return j;
	}
	
	/**
	 * 批量删除发票
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserInvoice:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserInvoiceService.delete(purchaserInvoiceService.get(id));
		}
		j.setMsg("删除发票成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserInvoice:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserInvoice purchaserInvoice, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "发票"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserInvoice> page = purchaserInvoiceService.findPage(new Page<PurchaserInvoice>(request, response, -1), purchaserInvoice);
    		new ExportExcel("发票", PurchaserInvoice.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出发票记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:purchaser:purchaserInvoice:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserInvoice> list = ei.getDataList(PurchaserInvoice.class);
			for (PurchaserInvoice purchaserInvoice : list){
				try{
					purchaserInvoiceService.save(purchaserInvoice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条发票记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条发票记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入发票失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserInvoice/?repage";
    }
	
	/**
	 * 下载导入发票数据模板
	 */
	@RequiresPermissions("shop:purchaser:purchaserInvoice:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "发票数据导入模板.xlsx";
    		List<PurchaserInvoice> list = Lists.newArrayList(); 
    		new ExportExcel("发票数据", PurchaserInvoice.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserInvoice/?repage";
    }

}