/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shopmsgzykc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
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
import com.jhmis.modules.process.entity.shopmsgzykc.ShopMsgZykc;
import com.jhmis.modules.process.service.shopmsgzykc.ShopMsgZykcService;

/**
 * 零售相关Controller
 * @author hdx
 * @version 2019-10-11
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shopmsgzykc/shopMsgZykc")
public class ShopMsgZykcController extends BaseController {

	@Autowired
	private ShopMsgZykcService shopMsgZykcService;

	@Autowired
	private ShopMsgService shopMsgService;

	@Autowired
	private ShopMsgDispatcherService shopMsgDispatcherService;

	@Autowired
	private ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;

	
	@ModelAttribute
	public ShopMsgZykc get(@RequestParam(required=false) String id) {
		ShopMsgZykc entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopMsgZykcService.get(id);
		}
		if (entity == null){
			entity = new ShopMsgZykc();
		}
		return entity;
	}
	
	/**
	 * 零售相关列表页面
	 */
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/shopmsgzykc/shopMsgZykcList";
	}
	
	/**
	 * 零售相关列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopMsgZykc shopMsgZykc, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopMsgZykc> page = shopMsgZykcService.findPage(new Page<ShopMsgZykc>(request, response), shopMsgZykc); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑零售相关表单页面
	 */
	@RequiresPermissions(value={"process:shopmsgzykc:shopMsgZykc:view","process:shopmsgzykc:shopMsgZykc:add","process:shopmsgzykc:shopMsgZykc:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopMsgZykc shopMsgZykc, Model model) {
		model.addAttribute("shopMsgZykc", shopMsgZykc);
		if(StringUtils.isBlank(shopMsgZykc.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/shopmsgzykc/shopMsgZykcForm";
	}

	/**
	 * 保存零售相关
	 */
	@RequiresPermissions(value={"process:shopmsgzykc:shopMsgZykc:add","process:shopmsgzykc:shopMsgZykc:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopMsgZykc shopMsgZykc, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopMsgZykc)){
			return form(shopMsgZykc, model);
		}
		//新增或编辑表单保存
		shopMsgZykcService.save(shopMsgZykc);//保存
		addMessage(redirectAttributes, "保存零售相关成功");
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgzykc/shopMsgZykc/?repage";
	}
	
	/**
	 * 删除零售相关
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopMsgZykc shopMsgZykc, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopMsgZykcService.delete(shopMsgZykc);
		j.setMsg("删除零售相关成功");
		return j;
	}
	
	/**
	 * 批量删除零售相关
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopMsgZykcService.delete(shopMsgZykcService.get(id));
		}
		j.setMsg("删除零售相关成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopMsgZykc shopMsgZykc, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "零售相关"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopMsgZykc> page = shopMsgZykcService.findPage(new Page<ShopMsgZykc>(request, response, -1), shopMsgZykc);
    		new ExportExcel("零售相关", ShopMsgZykc.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出零售相关记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopMsgZykc> list = ei.getDataList(ShopMsgZykc.class);
			for (ShopMsgZykc shopMsgZykc : list){
				try{
					shopMsgZykcService.save(shopMsgZykc);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条零售相关记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条零售相关记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入零售相关失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgzykc/shopMsgZykc/?repage";
    }
	
	/**
	 * 下载导入零售相关数据模板
	 */
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "零售相关数据导入模板.xlsx";
    		List<ShopMsgZykc> list = Lists.newArrayList(); 
    		new ExportExcel("零售相关数据", ShopMsgZykc.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgzykc/shopMsgZykc/?repage";
    }


	/**
	 * 审核零售管理
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgzykc:shopMsgZykc:check")
	@RequestMapping(value = "check")
	public AjaxJson check(@RequestParam String zykcId,@RequestParam String ischeck) {
		try {
			shopMsgZykcService.check(zykcId,ischeck);
		} catch (ShopMsgException e) {
			logger.error("/check-审核零售管理异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}


	/**
	 * 查看经销商订单详情页面
	 */
	@RequiresPermissions(value={"process:shopmsgzykc:shopMsgZykc:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ShopMsgZykc shopMsgZykc, Model model, RedirectAttributes redirectAttributes) {
		//查询订单相关信息
		model.addAttribute("shopMsgZykc", shopMsgZykc);
		//根据id查询相关
		if(StringUtils.isEmpty(shopMsgZykc.getMsgId())){
			addMessage(redirectAttributes, "查看零售订单详情页面失败！失败信息："+ "需求id不能为空");
		}
		ShopMsg shopMsg = shopMsgService.get(shopMsgZykc.getMsgId());
		if(null==shopMsg){
			addMessage(redirectAttributes, "查看零售订单单详情页面失败！失败信息："+ "未查到此需求");
		}
		model.addAttribute("shopMsg", shopMsg);
		//如果是派单相关可查派单相关数据
		if(ProcessCode.IS_DISPATCHER.getLabel().equals(shopMsg.getIsDispatch())){
			//证明进入过派单,查询一下派单相关信息
			ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
			shopMsgDispatcher.setMsgId(shopMsg.getId());
			//设置msgid
			List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
			model.addAttribute("listShopMsgDispatcher", listShopMsgDispatcher);
		}
		//获取经销商订单信息
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = shopMsgCustcodeOrderService.get(shopMsgZykc.getOrderId());
		if(null==shopMsgCustcodeOrder){
			addMessage(redirectAttributes, "查看零售订单详情页面失败！失败信息："+ "未查到此经销商订单信息");
		}
		model.addAttribute("shopMsgCustcodeOrder", shopMsgCustcodeOrder);

		return "modules/process/shopmsgzykc/shopMsgZykcView";
	}

}