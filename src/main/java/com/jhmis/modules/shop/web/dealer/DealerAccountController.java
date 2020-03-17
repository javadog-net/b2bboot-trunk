/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.dealer;

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
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;

/**
 * 供应商账号管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/dealer/dealerAccount")
public class DealerAccountController extends BaseController {

	@Autowired
	private DealerAccountService dealerAccountService;
	
	@ModelAttribute
	public DealerAccount get(@RequestParam(required=false) String id) {
		DealerAccount entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dealerAccountService.get(id);
		}
		if (entity == null){
			entity = new DealerAccount();
		}
		return entity;
	}
	
	/**
	 * 供应商账号列表页面
	 */
	@RequiresPermissions("shop:dealer:dealerAccount:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/dealer/dealerAccountList";
	}
	
	/**
	 * 供应商账号列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerAccount:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DealerAccount dealerAccount, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DealerAccount> page = dealerAccountService.findPage(new Page<DealerAccount>(request, response), dealerAccount); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑供应商账号表单页面
	 */
	@RequiresPermissions(value={"shop:dealer:dealerAccount:view","shop:dealer:dealerAccount:add","shop:dealer:dealerAccount:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DealerAccount dealerAccount, Model model) {
		model.addAttribute("dealerAccount", dealerAccount);
		return "modules/shop/dealer/dealerAccountForm";
	}

	/**
	 * 保存供应商账号
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:dealer:dealerAccount:add","shop:dealer:dealerAccount:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(DealerAccount dealerAccount, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, dealerAccount)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		dealerAccountService.save(dealerAccount);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存供应商账号成功");
		return j;
	}
	
	/**
	 * 删除供应商账号
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerAccount:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DealerAccount dealerAccount, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		dealerAccountService.delete(dealerAccount);
		j.setMsg("删除供应商账号成功");
		return j;
	}
	
	/**
	 * 批量删除供应商账号
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerAccount:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dealerAccountService.delete(dealerAccountService.get(id));
		}
		j.setMsg("删除供应商账号成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerAccount:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DealerAccount dealerAccount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "供应商账号"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DealerAccount> page = dealerAccountService.findPage(new Page<DealerAccount>(request, response, -1), dealerAccount);
    		new ExportExcel("供应商账号", DealerAccount.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出供应商账号记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:dealer:dealerAccount:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DealerAccount> list = ei.getDataList(DealerAccount.class);
			for (DealerAccount dealerAccount : list){
				try{
					dealerAccountService.save(dealerAccount);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条供应商账号记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条供应商账号记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入供应商账号失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealerAccount/?repage";
    }
	
	/**
	 * 下载导入供应商账号数据模板
	 */
	@RequiresPermissions("shop:dealer:dealerAccount:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "供应商账号数据导入模板.xlsx";
    		List<DealerAccount> list = Lists.newArrayList(); 
    		new ExportExcel("供应商账号数据", DealerAccount.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealerAccount/?repage";
    }

}