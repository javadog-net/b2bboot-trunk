/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shopproject;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
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
import com.jhmis.modules.process.entity.shopproject.ShopProject;
import com.jhmis.modules.process.service.shopproject.ShopProjectService;

/**
 * hps需求汇总Controller
 * @author hdx
 * @version 2019-11-04
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shopproject/shopProject")
public class ShopProjectController extends BaseController {

	@Autowired
	private ShopProjectService shopProjectService;
	@Autowired
	private ShopMsgService shopMsgService;
	@Autowired
	private ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;
	@Autowired
	private ShopMsgDispatcherService shopMsgDispatcherService;
	
	@ModelAttribute
	public ShopProject get(@RequestParam(required=false) String id) {
		ShopProject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopProjectService.get(id);
		}
		if (entity == null){
			entity = new ShopProject();
		}
		return entity;
	}
	
	/**
	 * hps需求汇总列表页面
	 */
	@RequiresPermissions("process:shopproject:shopProject:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/shopproject/shopProjectList";
	}
	
	/**
	 * hps需求汇总列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shopproject:shopProject:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopProject shopProject, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopProject.setProjectsSource("GN_PRJ_08");
		Page<ShopProject> page = shopProjectService.findPage(new Page<ShopProject>(request, response), shopProject); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑hps需求汇总表单页面
	 */
	@RequiresPermissions(value={"process:shopproject:shopProject:view","process:shopproject:shopProject:add","process:shopproject:shopProject:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopProject shopProject, Model model) {
		model.addAttribute("shopProject", shopProject);
		if(StringUtils.isBlank(shopProject.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/shopproject/shopProjectForm";
	}

	/**
	 * 保存hps需求汇总
	 */
	@RequiresPermissions(value={"process:shopproject:shopProject:add","process:shopproject:shopProject:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopProject shopProject, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopProject)){
			return form(shopProject, model);
		}
		//新增或编辑表单保存
		shopProjectService.save(shopProject);//保存
		addMessage(redirectAttributes, "保存hps需求汇总成功");
		return "redirect:"+Global.getAdminPath()+"/process/shopproject/shopProject/?repage";
	}
	
	/**
	 * 删除hps需求汇总
	 */
	@ResponseBody
	@RequiresPermissions("process:shopproject:shopProject:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopProject shopProject, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopProjectService.delete(shopProject);
		j.setMsg("删除hps需求汇总成功");
		return j;
	}
	
	/**
	 * 批量删除hps需求汇总
	 */
	@ResponseBody
	@RequiresPermissions("process:shopproject:shopProject:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopProjectService.delete(shopProjectService.get(id));
		}
		j.setMsg("删除hps需求汇总成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:shopproject:shopProject:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopProject shopProject, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "hps需求汇总"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopProject> page = shopProjectService.findPage(new Page<ShopProject>(request, response, -1), shopProject);
    		new ExportExcel("hps需求汇总", ShopProject.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出hps需求汇总记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shopproject:shopProject:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopProject> list = ei.getDataList(ShopProject.class);
			for (ShopProject shopProject : list){
				try{
					shopProjectService.save(shopProject);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条hps需求汇总记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条hps需求汇总记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入hps需求汇总失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopproject/shopProject/?repage";
    }
	
	/**
	 * 下载导入hps需求汇总数据模板
	 */
	@RequiresPermissions("process:shopproject:shopProject:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "hps需求汇总数据导入模板.xlsx";
    		List<ShopProject> list = Lists.newArrayList(); 
    		new ExportExcel("hps需求汇总数据", ShopProject.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopproject/shopProject/?repage";
    }


	/**
	 * 查看经销商订单详情页面
	 */
	@RequiresPermissions(value={"process:shopmsgorder:shopMsgCustcodeOrder:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ShopProject shopProject, Model model, RedirectAttributes redirectAttributes) {
		//查询订单相关信息
		model.addAttribute("shopProject", shopProject);
		//根据id查询相关
		if(StringUtils.isEmpty(shopProject.getMsgId())){
			addMessage(redirectAttributes, "查看经销商订单详情页面失败！失败信息："+ "需求id不能为空");
		}
		ShopMsg shopMsg = shopMsgService.get(shopProject.getMsgId());
		if(null==shopMsg){
			addMessage(redirectAttributes, "查看经销商订单详情页面失败！失败信息："+ "未查到此需求");
		}else{
			//如果是派单相关可查派单相关数据
			if(ProcessCode.IS_DISPATCHER.getLabel().equals(shopMsg.getIsDispatch())){
				//证明进入过派单,查询一下派单相关信息
				ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
				shopMsgDispatcher.setMsgId(shopMsg.getId());
				//设置msgid
				List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
				model.addAttribute("listShopMsgDispatcher", listShopMsgDispatcher);
			}
			model.addAttribute("shopMsg", shopMsg);
		}
		//订单相关
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
		shopMsgCustcodeOrder.setMsgId(shopProject.getMsgId());
		List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(shopMsgCustcodeOrder);
		if(listShopMsgCustcodeOrder!=null && listShopMsgCustcodeOrder.size()>0){
			model.addAttribute("shopMsgCustcodeOrder", listShopMsgCustcodeOrder.get(0));
		}
		return "modules/process/shopproject/shopProjectView";
	}

}