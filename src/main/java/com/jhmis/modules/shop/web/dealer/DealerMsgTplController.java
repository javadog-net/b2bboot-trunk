/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.dealer;

import java.util.Date;
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
import com.jhmis.modules.shop.entity.dealer.DealerMsgTpl;
import com.jhmis.modules.shop.service.dealer.DealerMsgTplService;

/**
 * 供应商消息模板Controller
 * @author tity
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/dealer/dealerMsgTpl")
public class DealerMsgTplController extends BaseController {

	@Autowired
	private DealerMsgTplService dealerMsgTplService;
	
	@ModelAttribute
	public DealerMsgTpl get(@RequestParam(required=false) String id) {
		DealerMsgTpl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dealerMsgTplService.get(id);
		}
		if (entity == null){
			entity = new DealerMsgTpl();
		}
		return entity;
	}
	
	/**
	 * 消息模板列表页面
	 */
	@RequiresPermissions("shop:dealer:dealerMsgTpl:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/dealer/dealerMsgTplList";
	}
	
	/**
	 * 消息模板列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsgTpl:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DealerMsgTpl dealerMsgTpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DealerMsgTpl> page=new Page<DealerMsgTpl>(request, response);
		page.setOrderBy("creat_time desc");
		Page<DealerMsgTpl> pa = dealerMsgTplService.findPage(new Page<DealerMsgTpl>(request, response), dealerMsgTpl); 
		return getBootstrapData(pa);
	}

	/**
	 * 查看，增加，编辑消息模板表单页面
	 */
	@RequiresPermissions(value={"shop:dealer:dealerMsgTpl:view","shop:dealer:dealerMsgTpl:add","shop:dealer:dealerMsgTpl:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DealerMsgTpl dealerMsgTpl, Model model) {
		model.addAttribute("dealerMsgTpl", dealerMsgTpl);
		if(StringUtils.isBlank(dealerMsgTpl.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/dealer/dealerMsgTplForm";
	}

	/**
	 * 保存消息模板
	 */
	@RequiresPermissions(value={"shop:dealer:dealerMsgTpl:add","shop:dealer:dealerMsgTpl:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DealerMsgTpl dealerMsgTpl, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dealerMsgTpl)){
			return form(dealerMsgTpl, model);
		}
		dealerMsgTpl.setCreatTime(new Date());
		//新增或编辑表单保存
		dealerMsgTplService.save(dealerMsgTpl);//保存
		
		addMessage(redirectAttributes, "保存消息模板成功");
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealerMsgTpl/?repage";
	}
	
	/**
	 * 删除消息模板
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsgTpl:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DealerMsgTpl dealerMsgTpl, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		dealerMsgTplService.delete(dealerMsgTpl);
		j.setMsg("删除消息模板成功");
		return j;
	}
	
	/**
	 * 批量删除消息模板
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsgTpl:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dealerMsgTplService.delete(dealerMsgTplService.get(id));
		}
		j.setMsg("删除消息模板成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsgTpl:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DealerMsgTpl dealerMsgTpl, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "消息模板"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DealerMsgTpl> page = dealerMsgTplService.findPage(new Page<DealerMsgTpl>(request, response, -1), dealerMsgTpl);
    		new ExportExcel("消息模板", DealerMsgTpl.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出消息模板记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:dealer:dealerMsgTpl:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DealerMsgTpl> list = ei.getDataList(DealerMsgTpl.class);
			for (DealerMsgTpl dealerMsgTpl : list){
				try{
					dealerMsgTplService.save(dealerMsgTpl);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条消息模板记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条消息模板记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入消息模板失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealerMsgTpl/?repage";
    }
	
	/**
	 * 下载导入消息模板数据模板
	 */
	@RequiresPermissions("shop:dealer:dealerMsgTpl:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "消息模板数据导入模板.xlsx";
    		List<DealerMsgTpl> list = Lists.newArrayList(); 
    		new ExportExcel("消息模板数据", DealerMsgTpl.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealerMsgTpl/?repage";
    }

}