/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.StoreJoinin;
import com.jhmis.modules.shop.service.StoreJoininService;
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
 * 店铺申请Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/storeJoinin")
public class StoreJoininController extends BaseController {

	@Autowired
	private StoreJoininService storeJoininService;
	
	@ModelAttribute
	public StoreJoinin get(@RequestParam(required=false) String id) {
		StoreJoinin entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = storeJoininService.get(id);
		}
		if (entity == null){
			entity = new StoreJoinin();
		}
		return entity;
	}
	
	/**
	 * 店铺申请列表页面
	 */
	@RequiresPermissions("shop:storeJoinin:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/storeJoininList";
	}
	
	/**
	 * 店铺申请列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeJoinin:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(StoreJoinin storeJoinin, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<StoreJoinin> page = storeJoininService.findPage(new Page<StoreJoinin>(request, response), storeJoinin); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑店铺申请表单页面
	 */
	@RequiresPermissions(value={"shop:storeJoinin:view","shop:storeJoinin:add","shop:storeJoinin:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(StoreJoinin storeJoinin, Model model) {
		model.addAttribute("storeJoinin", storeJoinin);
		if(StringUtils.isBlank(storeJoinin.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/storeJoininForm";
	}

	/**
	 * 保存店铺申请
	 */
	@RequiresPermissions(value={"shop:storeJoinin:add","shop:storeJoinin:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(StoreJoinin storeJoinin, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, storeJoinin)){
			return form(storeJoinin, model);
		}
		//新增或编辑表单保存
		storeJoininService.save(storeJoinin);//保存
		addMessage(redirectAttributes, "保存店铺申请成功");
		return "redirect:"+Global.getAdminPath()+"/shop/storeJoinin/?repage";
	}
	
	/**
	 * 删除店铺申请
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeJoinin:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(StoreJoinin storeJoinin, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		storeJoininService.delete(storeJoinin);
		j.setMsg("删除店铺申请成功");
		return j;
	}
	
	/**
	 * 批量删除店铺申请
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeJoinin:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			storeJoininService.delete(storeJoininService.get(id));
		}
		j.setMsg("删除店铺申请成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:storeJoinin:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(StoreJoinin storeJoinin, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "店铺申请"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<StoreJoinin> page = storeJoininService.findPage(new Page<StoreJoinin>(request, response, -1), storeJoinin);
    		new ExportExcel("店铺申请", StoreJoinin.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出店铺申请记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:storeJoinin:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<StoreJoinin> list = ei.getDataList(StoreJoinin.class);
			for (StoreJoinin storeJoinin : list){
				try{
					storeJoininService.save(storeJoinin);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条店铺申请记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条店铺申请记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入店铺申请失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storeJoinin/?repage";
    }
	
	/**
	 * 下载导入店铺申请数据模板
	 */
	@RequiresPermissions("shop:storeJoinin:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "店铺申请数据导入模板.xlsx";
    		List<StoreJoinin> list = Lists.newArrayList(); 
    		new ExportExcel("店铺申请数据", StoreJoinin.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/storeJoinin/?repage";
    }

    /**
     * 审核
     * @return
     */
    @ResponseBody
    @RequiresPermissions("shop:storeJoinin:audit")
    @RequestMapping(value = "audit", method=RequestMethod.POST)
    public AjaxJson audit(StoreJoinin storeJoinin) {
        if(StringUtils.isBlank(storeJoinin.getId())){
            return AjaxJson.fail("申请记录不存在");
        }
        if(Global.AUDIT_STATE_WAIT.equals(storeJoinin.getAuditState())){
            return AjaxJson.fail("待审核状态才能审核");
        }
        if(Global.AUDIT_STATE_NO.equals(storeJoinin.getAuditState()) && StringUtils.isBlank(storeJoinin.getAuditDesc())){
            return AjaxJson.fail("请填写不同意的原因");
        }
        return storeJoininService.audit(storeJoinin);
    }

}