/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasergoodsrel.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.purchasergoodsrel.entity.PurchaserGoodsRel;
import com.jhmis.modules.purchasergoodsrel.service.PurchaserGoodsRelService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

/**
 * 采购商可采商品Controller
 * @author wangbn
 * @version 2019-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/purchasergoodsrel/purchaserGoodsRel")
public class PurchaserGoodsRelController extends BaseController {

	@Autowired
	private PurchaserGoodsRelService purchaserGoodsRelService;
	
	@ModelAttribute
	public PurchaserGoodsRel get(@RequestParam(required=false) String id) {
		PurchaserGoodsRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserGoodsRelService.get(id);
		}
		if (entity == null){
			entity = new PurchaserGoodsRel();
		}
		return entity;
	}
	
	/**
	 * 采购商商品关系列表页面
	 */
	@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/purchasergoodsrel/purchaserGoodsRelList";
	}
	
	/**
	 * 采购商商品关系列表数据
	 */
	@ResponseBody
	@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserGoodsRel purchaserGoodsRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserGoodsRel> page = purchaserGoodsRelService.findPage(new Page<PurchaserGoodsRel>(request, response), purchaserGoodsRel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑采购商商品关系表单页面
	 */
	@RequiresPermissions(value={"purchasergoodsrel:purchaserGoodsRel:view","purchasergoodsrel:purchaserGoodsRel:add","purchasergoodsrel:purchaserGoodsRel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserGoodsRel purchaserGoodsRel, Model model) {
		model.addAttribute("purchaserGoodsRel", purchaserGoodsRel);
		if(StringUtils.isBlank(purchaserGoodsRel.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/purchasergoodsrel/purchaserGoodsRelForm";
	}

	/**
	 * 保存采购商商品关系
	 */
	@RequiresPermissions(value={"purchasergoodsrel:purchaserGoodsRel:add","purchasergoodsrel:purchaserGoodsRel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PurchaserGoodsRel purchaserGoodsRel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, purchaserGoodsRel)){
			return form(purchaserGoodsRel, model);
		}
		//新增或编辑表单保存
		purchaserGoodsRelService.save(purchaserGoodsRel);//保存
		addMessage(redirectAttributes, "保存采购商商品关系成功");
		return "redirect:"+Global.getAdminPath()+"/purchasergoodsrel/purchaserGoodsRel/?repage";
	}
	
	   /**
* 关联可采商品
*/
@ResponseBody
@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:del")
@RequestMapping(value = "savePurchaserGoodsRel")
public AjaxJson savePurchaserGoodsRel(String ids,String purchaserId, RedirectAttributes redirectAttributes) {
	
  AjaxJson j = new AjaxJson();
  if(ids.indexOf(",")>-1){
  	  String idArray[] = ids.split(",");
        for (String id : idArray) {
        	PurchaserGoodsRel rel=new PurchaserGoodsRel();
        	rel.setPurchaserId(purchaserId);
        	rel.setState("0");
        	rel.setGoodsSku(id);
        	purchaserGoodsRelService.save(rel);
        }
  }else{
	 PurchaserGoodsRel rel=new PurchaserGoodsRel();
	 rel.setPurchaserId(purchaserId);
	 rel.setState("0");
 	 rel.setGoodsSku(ids);
 	 purchaserGoodsRelService.save(rel);
  }

  j.setMsg("上架商品成功");
  return j;
}
	
	
	
	@ResponseBody
	@RequiresPermissions(value={"purchasergoodsrel:purchaserGoodsRel:add","purchasergoodsrel:purchaserGoodsRel:edit"},logical=Logical.OR)
	@RequestMapping(value = "updatePrice")
	public AjaxJson updatePrice(PurchaserGoodsRel purchaserGoodsRel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, purchaserGoodsRel)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		if(purchaserGoodsRel.getExclusivePrice() <= 0){
			j.setSuccess(false);
			j.setMsg("专享价必须大于0！");
			return j;
		}

		String pid=purchaserGoodsRel.getPurchaserId();
		model.addAttribute("purchaserId", pid);
		purchaserGoodsRelService.updatePrice(purchaserGoodsRel);//保存
		j.setSuccess(true);
		j.setMsg("设置企业专享价成功");
		return j;
	}
	
	   /**
  * 下架可采商品
  */
 @ResponseBody
 @RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:del")
 @RequestMapping(value = "deletePurchaserGoodsRel")
 public AjaxJson deletePurchaserGoodsRel(String ids,String purchaserId, RedirectAttributes redirectAttributes) {
     AjaxJson j = new AjaxJson();
     if(ids.indexOf(",")>-1){
     	  String idArray[] = ids.split(",");
           for (String id : idArray) {
               purchaserGoodsRelService.delete(purchaserId,id);
           }
     }else{
     	 purchaserGoodsRelService.delete(purchaserId,ids);
     }
   
     j.setMsg("移除商品成功");
     return j;
 }
	
	
	/**
	 * 删除采购商商品关系
	 */
	@ResponseBody
	@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserGoodsRel purchaserGoodsRel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserGoodsRelService.delete(purchaserGoodsRel);
		j.setMsg("删除采购商商品关系成功");
		return j;
	}
	
	/**
	 * 批量删除采购商商品关系
	 */
	@ResponseBody
	@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserGoodsRelService.delete(purchaserGoodsRelService.get(id));
		}
		j.setMsg("删除采购商商品关系成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserGoodsRel purchaserGoodsRel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购商商品关系"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserGoodsRel> page = purchaserGoodsRelService.findPage(new Page<PurchaserGoodsRel>(request, response, -1), purchaserGoodsRel);
    		new ExportExcel("采购商商品关系", PurchaserGoodsRel.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出采购商商品关系记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserGoodsRel> list = ei.getDataList(PurchaserGoodsRel.class);
			for (PurchaserGoodsRel purchaserGoodsRel : list){
				try{
					purchaserGoodsRelService.save(purchaserGoodsRel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条采购商商品关系记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条采购商商品关系记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入采购商商品关系失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/purchasergoodsrel/purchaserGoodsRel/?repage";
    }
	
	/**
	 * 下载导入采购商商品关系数据模板
	 */
	@RequiresPermissions("purchasergoodsrel:purchaserGoodsRel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "采购商商品关系数据导入模板.xlsx";
    		List<PurchaserGoodsRel> list = Lists.newArrayList(); 
    		new ExportExcel("采购商商品关系数据", PurchaserGoodsRel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/purchasergoodsrel/purchaserGoodsRel/?repage";
    }

}