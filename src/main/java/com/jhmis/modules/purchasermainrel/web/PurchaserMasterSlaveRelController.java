/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasermainrel.web;

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
import com.jhmis.modules.directpurchaser.service.DirectPurchaserService;
import com.jhmis.modules.purchasergoodsrel.entity.PurchaserGoodsRel;
import com.jhmis.modules.purchasermainrel.entity.PurchaserMasterSlaveRel;
import com.jhmis.modules.purchasermainrel.service.PurchaserMasterSlaveRelService;

/**
 * 采购商主副关系Controller
 * @author wbn
 * @version 2019-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/purchasermainrel/purchaserMasterSlaveRel")
public class PurchaserMasterSlaveRelController extends BaseController {

	@Autowired
	private PurchaserMasterSlaveRelService purchaserMasterSlaveRelService;
	
	@Autowired
	private DirectPurchaserService directPurchaserService;
	
	@ModelAttribute
	public PurchaserMasterSlaveRel get(@RequestParam(required=false) String id) {
		PurchaserMasterSlaveRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserMasterSlaveRelService.get(id);
		}
		if (entity == null){
			entity = new PurchaserMasterSlaveRel();
		}
		return entity;
	}
	
	/**
	 * 采购商主副关系列表页面
	 */
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/purchasermainrel/purchaserMasterSlaveRelList";
	}
	
	/**
	 * 采购商主副关系列表数据
	 */
	@ResponseBody
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserMasterSlaveRel purchaserMasterSlaveRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserMasterSlaveRel> page = purchaserMasterSlaveRelService.findPage(new Page<PurchaserMasterSlaveRel>(request, response), purchaserMasterSlaveRel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑采购商主副关系表单页面
	 */
	@RequiresPermissions(value={"purchasermainrel:purchaserMasterSlaveRel:view","purchasermainrel:purchaserMasterSlaveRel:add","purchasermainrel:purchaserMasterSlaveRel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserMasterSlaveRel purchaserMasterSlaveRel, Model model) {
		model.addAttribute("purchaserMasterSlaveRel", purchaserMasterSlaveRel);
		if(StringUtils.isBlank(purchaserMasterSlaveRel.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/purchasermainrel/purchaserMasterSlaveRelForm";
	}
	
	
	
	
	
	   /**
	* 关联子账号
	*/
	@ResponseBody
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:add")
	@RequestMapping(value = "savePurchaserRelAll")
	public AjaxJson savePurchaserRelAll(String ids,String purchaserId, RedirectAttributes redirectAttributes) {
		
	  AjaxJson j = new AjaxJson();
	  if(ids.indexOf(",")>-1){
	  	  String idArray[] = ids.split(",");
	        for (String id : idArray) {
	        	PurchaserMasterSlaveRel rel=new PurchaserMasterSlaveRel();
	        	rel.setPurchaserMasterId(purchaserId);
	        	rel.setPurchaserSlaveId(id);
	        	rel.setOpeDate(DateUtils.getDateTime());
	        	purchaserMasterSlaveRelService.save(rel);
	        	directPurchaserService.updatePurchaserRel(id,"0");
	        }
	  }else{
		  PurchaserMasterSlaveRel rel=new PurchaserMasterSlaveRel();
      	  rel.setPurchaserMasterId(purchaserId);
      	  rel.setPurchaserSlaveId(ids);
      	  rel.setOpeDate(DateUtils.getDateTime());
      	purchaserMasterSlaveRelService.save(rel);
      	directPurchaserService.updatePurchaserRel(ids,"0");
	  }

	  j.setMsg("关联子账号成功");
	  return j;
	}	
	
	
	   /**
* 
* 取消关联子账号
*/
@ResponseBody
@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:add")
@RequestMapping(value = "deletePurchaserRelAll")
public AjaxJson deletePurchaserRelAll(String ids,String purchaserId, RedirectAttributes redirectAttributes) {
  AjaxJson j = new AjaxJson();
  if(ids.indexOf(",")>-1){
  	  String idArray[] = ids.split(",");
        for (String id : idArray) {
        	purchaserMasterSlaveRelService.delete(purchaserId,id);
        	directPurchaserService.updatePurchaserRel(id,"1");
        }
  }else{
	  purchaserMasterSlaveRelService.delete(purchaserId,ids);
	  directPurchaserService.updatePurchaserRel(ids,"1");
  }

  j.setMsg("取消子账号成功");
  return j;
}
	

	/**
	 * 保存采购商主副关系
	 */
	@RequiresPermissions(value={"purchasermainrel:purchaserMasterSlaveRel:add","purchasermainrel:purchaserMasterSlaveRel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PurchaserMasterSlaveRel purchaserMasterSlaveRel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, purchaserMasterSlaveRel)){
			return form(purchaserMasterSlaveRel, model);
		}
		//新增或编辑表单保存
		purchaserMasterSlaveRelService.save(purchaserMasterSlaveRel);//保存
		addMessage(redirectAttributes, "保存采购商主副关系成功");
		return "redirect:"+Global.getAdminPath()+"/purchasermainrel/purchaserMasterSlaveRel/?repage";
	}
	
	/**
	 * 删除采购商主副关系
	 */
	@ResponseBody
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserMasterSlaveRel purchaserMasterSlaveRel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserMasterSlaveRelService.delete(purchaserMasterSlaveRel);
		j.setMsg("删除采购商主副关系成功");
		return j;
	}
	
	/**
	 * 批量删除采购商主副关系
	 */
	@ResponseBody
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserMasterSlaveRelService.delete(purchaserMasterSlaveRelService.get(id));
		}
		j.setMsg("删除采购商主副关系成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserMasterSlaveRel purchaserMasterSlaveRel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购商主副关系"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserMasterSlaveRel> page = purchaserMasterSlaveRelService.findPage(new Page<PurchaserMasterSlaveRel>(request, response, -1), purchaserMasterSlaveRel);
    		new ExportExcel("采购商主副关系", PurchaserMasterSlaveRel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出采购商主副关系记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserMasterSlaveRel> list = ei.getDataList(PurchaserMasterSlaveRel.class);
			for (PurchaserMasterSlaveRel purchaserMasterSlaveRel : list){
				try{
					purchaserMasterSlaveRelService.save(purchaserMasterSlaveRel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条采购商主副关系记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条采购商主副关系记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入采购商主副关系失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/purchasermainrel/purchaserMasterSlaveRel/?repage";
    }
	
	/**
	 * 下载导入采购商主副关系数据模板
	 */
	@RequiresPermissions("purchasermainrel:purchaserMasterSlaveRel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "采购商主副关系数据导入模板.xlsx";
    		List<PurchaserMasterSlaveRel> list = Lists.newArrayList(); 
    		new ExportExcel("采购商主副关系数据", PurchaserMasterSlaveRel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/purchasermainrel/purchaserMasterSlaveRel/?repage";
    }

}