/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.web;

import java.text.SimpleDateFormat;
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
import com.jhmis.modules.bid.entity.BidInfo;
import com.jhmis.modules.bid.entity.BidSignup;
import com.jhmis.modules.bid.service.BidSignupService;
import com.jhmis.modules.shop.entity.Industry;

/**
 * 招投标Controller
 * @author hdx
 * @version 2019-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/bid/bidSignup")
public class BidSignupController extends BaseController {

	@Autowired
	private BidSignupService bidSignupService;
	
	@ModelAttribute
	public BidSignup get(@RequestParam(required=false) String id) {
		BidSignup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bidSignupService.get(id);
		}
		if (entity == null){
			entity = new BidSignup();
		}
		return entity;
	}
	
	/**
	 * 保存报名信息列表页面
	 */
	//@RequiresPermissions("bid:bidSignup:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/bid/bidSignupList";
	}
	
	
	// @RequiresPermissions(value={"bid:bidInfo:view","bid:bidInfo:add","bid:bidInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "lists")
	public String lists(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try{
		String bidInfoId = request.getParameter("bidInfoId");
		System.out.println(bidInfoId+"bidInfoId");
		if(bidInfoId!=null){
		model.addAttribute("bidInfoId", bidInfoId);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "modules/bid/bidSignupList";

	}

	/**
	 * 保存报名信息列表数据
	 */
	@ResponseBody
	//@RequiresPermissions("bid:bidSignup:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BidSignup bidSignup, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("bidsignup"+bidSignup.toString());
		Page<BidSignup> page = bidSignupService.findPage(new Page<BidSignup>(request, response), bidSignup); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑保存报名信息表单页面
	 */
	//@RequiresPermissions(value={"bid:bidSignup:view","bid:bidSignup:add","bid:bidSignup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BidSignup bidSignup, Model model) {
		model.addAttribute("bidSignup", bidSignup);
		if(StringUtils.isBlank(bidSignup.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/bid/bidSignupForm";
	}

	
	
	@RequestMapping(value = "select")
	public String select(BidSignup bidSignup, Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(bidSignup.getAddTime()!=null){
        String time = sdf.format(bidSignup.getAddTime());
        bidSignup.setAddDate(time);
        }
		model.addAttribute("bidSignup", bidSignup);
		if(StringUtils.isBlank(bidSignup.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/bid/bidSignupParticular";
	}
	/**
	 * 保存保存报名信息
	 */
	@RequiresPermissions(value={"bid:bidSignup:add","bid:bidSignup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BidSignup bidSignup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bidSignup)){
			return form(bidSignup, model);
		}
		//新增或编辑表单保存
		bidSignupService.save(bidSignup);//保存
		addMessage(redirectAttributes, "保存保存报名信息成功");
		return "redirect:"+Global.getAdminPath()+"/bid/bidSignup/?repage";
	}
	
	/**
	 * 删除保存报名信息
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidSignup:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BidSignup bidSignup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		bidSignupService.delete(bidSignup);
		j.setMsg("删除保存报名信息成功");
		return j;
	}
	
	/**
	 * 批量删除保存报名信息
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidSignup:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bidSignupService.delete(bidSignupService.get(id));
		}
		j.setMsg("删除保存报名信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidSignup:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(BidSignup bidSignup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "保存报名信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BidSignup> page = bidSignupService.findPage(new Page<BidSignup>(request, response, -1), bidSignup);
    		new ExportExcel("保存报名信息", BidSignup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出保存报名信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bid:bidSignup:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BidSignup> list = ei.getDataList(BidSignup.class);
			for (BidSignup bidSignup : list){
				try{
					bidSignupService.save(bidSignup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条保存报名信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条保存报名信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入保存报名信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bid/bidSignup/?repage";
    }
	
	/**
	 * 下载导入保存报名信息数据模板
	 */
	@RequiresPermissions("bid:bidSignup:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "保存报名信息数据导入模板.xlsx";
    		List<BidSignup> list = Lists.newArrayList(); 
    		new ExportExcel("保存报名信息数据", BidSignup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bid/bidSignup/?repage";
    }

}