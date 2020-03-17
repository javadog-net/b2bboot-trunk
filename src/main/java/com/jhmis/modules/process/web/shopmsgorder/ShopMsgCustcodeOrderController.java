/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shopmsgorder;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.haier.mdm.khzj.Process;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ProductCode;
import com.jhmis.common.utils.Constants;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.sys.entity.Role;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
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
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;

/**
 * 经销商订单表Controller
 * @author hdx
 * @version 2019-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shopmsgorder/shopMsgCustcodeOrder")
public class ShopMsgCustcodeOrderController extends BaseController {

	@Autowired
	private ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;

	@Autowired
	private ShopMsgService shopMsgService;

	@Autowired
	private ShopMsgDispatcherService shopMsgDispatcherService;
	
	@ModelAttribute
	public ShopMsgCustcodeOrder get(@RequestParam(required=false) String id) {
		ShopMsgCustcodeOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopMsgCustcodeOrderService.get(id);
		}
		if (entity == null){
			entity = new ShopMsgCustcodeOrder();
		}
		return entity;
	}
	
	/**
	 * 经销商订单列表页面
	 */
	@RequiresPermissions("process:shopmsgorder:shopMsgCustcodeOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/shopmsgorder/shopMsgCustcodeOrderList";
	}
	
	/**
	 * 经销商订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgorder:shopMsgCustcodeOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopMsgCustcodeOrder shopMsgCustcodeOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopMsgCustcodeOrder> ShopMsgCustcodeOrderPage = new Page<ShopMsgCustcodeOrder>(request, response);
		ShopMsgCustcodeOrderPage.setOrderBy(" a.create_date desc");
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		shopMsgCustcodeOrder = this.productType(currentUser,shopMsgCustcodeOrder);
		Page<ShopMsgCustcodeOrder> page = shopMsgCustcodeOrderService.findPage(ShopMsgCustcodeOrderPage, shopMsgCustcodeOrder);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑经销商订单表单页面
	 */
	@RequiresPermissions(value={"process:shopmsgorder:shopMsgCustcodeOrder:view","process:shopmsgorder:shopMsgCustcodeOrder:add","process:shopmsgorder:shopMsgCustcodeOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopMsgCustcodeOrder shopMsgCustcodeOrder, Model model) {
		model.addAttribute("shopMsgCustcodeOrder", shopMsgCustcodeOrder);
		if(StringUtils.isBlank(shopMsgCustcodeOrder.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/shopmsgorder/shopMsgCustcodeOrderForm";
	}


	/**
	 * 查看经销商订单详情页面
	 */
	@RequiresPermissions(value={"process:shopmsgorder:shopMsgCustcodeOrder:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ShopMsgCustcodeOrder shopMsgCustcodeOrder, Model model, RedirectAttributes redirectAttributes) {
		//查询订单相关信息
		model.addAttribute("shopMsgCustcodeOrder", shopMsgCustcodeOrder);
		//根据id查询相关
		if(StringUtils.isEmpty(shopMsgCustcodeOrder.getMsgId())){
			addMessage(redirectAttributes, "查看经销商订单详情页面失败！失败信息："+ "需求id不能为空");
		}
		ShopMsg shopMsg = shopMsgService.get(shopMsgCustcodeOrder.getMsgId());
		if(null==shopMsg){
			addMessage(redirectAttributes, "查看经销商订单详情页面失败！失败信息："+ "未查到此需求");
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
		return "modules/process/shopmsgorder/shopMsgCustcodeOrderView";
	}

	/**
	 * 保存经销商订单
	 */
	@RequiresPermissions(value={"process:shopmsgorder:shopMsgCustcodeOrder:add","process:shopmsgorder:shopMsgCustcodeOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopMsgCustcodeOrder shopMsgCustcodeOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopMsgCustcodeOrder)){
			return form(shopMsgCustcodeOrder, model);
		}
		//新增或编辑表单保存
		shopMsgCustcodeOrderService.save(shopMsgCustcodeOrder);//保存
		addMessage(redirectAttributes, "保存经销商订单成功");
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgorder/shopMsgCustcodeOrder/?repage";
	}
	
	/**
	 * 删除经销商订单
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgorder:shopMsgCustcodeOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopMsgCustcodeOrder shopMsgCustcodeOrder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopMsgCustcodeOrderService.delete(shopMsgCustcodeOrder);
		j.setMsg("删除经销商订单成功");
		return j;
	}
	
	/**
	 * 批量删除经销商订单
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgorder:shopMsgCustcodeOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopMsgCustcodeOrderService.delete(shopMsgCustcodeOrderService.get(id));
		}
		j.setMsg("删除经销商订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsgorder:shopMsgCustcodeOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopMsgCustcodeOrder shopMsgCustcodeOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "经销商订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopMsgCustcodeOrder> page = shopMsgCustcodeOrderService.findPage(new Page<ShopMsgCustcodeOrder>(request, response, -1), shopMsgCustcodeOrder);
    		new ExportExcel("经销商订单", ShopMsgCustcodeOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出经销商订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shopmsgorder:shopMsgCustcodeOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopMsgCustcodeOrder> list = ei.getDataList(ShopMsgCustcodeOrder.class);
			for (ShopMsgCustcodeOrder shopMsgCustcodeOrder : list){
				try{
					shopMsgCustcodeOrderService.save(shopMsgCustcodeOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条经销商订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条经销商订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入经销商订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgorder/shopMsgCustcodeOrder/?repage";
    }
	
	/**
	 * 下载导入经销商订单数据模板
	 */
	@RequiresPermissions("process:shopmsgorder:shopMsgCustcodeOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "经销商订单数据导入模板.xlsx";
    		List<ShopMsgCustcodeOrder> list = Lists.newArrayList(); 
    		new ExportExcel("经销商订单数据", ShopMsgCustcodeOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsgorder/shopMsgCustcodeOrder/?repage";
    }

	public ShopMsgCustcodeOrder productType(User currentUser,ShopMsgCustcodeOrder shopMsgCustcodeOrder){
		if(null==currentUser){
			logger.error("当前用户不存在");
			return shopMsgCustcodeOrder;
		}
		List<Role> listRole = currentUser.getRoleList();
		if(null==listRole || listRole.size()==0){
			logger.error("当前用户权限不存在");
			return shopMsgCustcodeOrder;
		}
		for(Role r:listRole){
			//判断如果是管理员则全都可以看
			if(r.getId().equals(Constants.SYSTEM_ADMIN)){
				shopMsgCustcodeOrder.setProGroup("");
				return shopMsgCustcodeOrder;
			}
			switch (r.getId()){
				//商空管理员
				case Constants.SK_ADMIN :
					//商空管理员标识
					shopMsgCustcodeOrder.setIsSkAdmin("1");
					break;
				//热水器管理员
				case Constants.GEYSER_ADMIN :
					shopMsgCustcodeOrder.setProGroup(ProductCode.ELECTRIC_GAS_WATER_HEATER.getLabel());
					break;
				//洗衣机管理员
				case Constants.WASHING_MACHINE_ADMIN :
					shopMsgCustcodeOrder.setProGroup(ProductCode.WASHING_MACHINE.getLabel());
					break;
				//彩电管理员
				case Constants.TV_ADMIN :
					shopMsgCustcodeOrder.setProGroup(ProductCode.COLOR_TV.getLabel());
					break;
				//家空管理员
				case Constants.JK_ADMIN :
					shopMsgCustcodeOrder.setProGroup(ProductCode.HOUSEHOLD_AIR_CONDITIONER.getLabel());
					break;
				//厨电管理员
				case Constants.KITCHEN_ADMIN :
					shopMsgCustcodeOrder.setProGroup(ProductCode.KITCHEN_APPLIANCES.getLabel());
					break;
				//冷柜管理员
				case Constants.FREEZER_ADMIN :
					shopMsgCustcodeOrder.setProGroup(ProductCode.COMMERCIAL_REFRIGERATOR.getLabel());
					break;
				//冰箱管理员
				case Constants.ICE_BOX_ADMIN :
					shopMsgCustcodeOrder.setProGroup(ProductCode.REFRIGERATOR.getLabel());
					break;
			}
		}
		return shopMsgCustcodeOrder;
	}


}