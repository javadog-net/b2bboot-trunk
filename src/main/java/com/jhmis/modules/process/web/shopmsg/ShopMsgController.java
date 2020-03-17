/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shopmsg;

import java.util.List;
import java.util.Map;

import javax.management.relation.RoleList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.haier.mdm.khzj.Process;
import com.haier.user.api.UserApi;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.webservices.client.hps.project.EnterpriseInfoVO;
import com.haier.webservices.client.hps.project.HpsApi;
import com.jhmis.common.Enum.MsgChannelCode;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ProductCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.MsgUtils;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shophicback.ShopHicbackpassLog;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.entity.shopproject.ShopProject;
import com.jhmis.modules.process.mapper.shopmsgorder.ShopMsgCustcodeOrderMapper;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shophicback.ShopHicbackpassLogService;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
import com.jhmis.modules.process.service.shopmsgstatus.ShopMsgStatusService;
import com.jhmis.modules.process.service.shopproject.ShopProjectService;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.sys.entity.Role;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.DictUtils;
import com.jhmis.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;

/**
 * 发布需求相关Controller
 * @author hdx
 * @version 2019-09-03
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shopmsg/shopMsg")
public class ShopMsgController extends BaseController {
	Logger log = LoggerFactory.getLogger(ShopMsgController.class);
	@Autowired
	private ShopMsgService shopMsgService;

	@Autowired
	AreaCodeService areaCodeService;

	@Autowired
	DealerService dealerService;

	@Autowired
	ShopMsgStatusService shopMsgStatusService;

	@Autowired
	ShopMsgDispatcherService shopMsgDispatcherService;

	@Autowired
	private ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;

	@Autowired
	ShopHicbackpassLogService shopHicbackpassLogService;

	@Autowired
	private ShopMsgCustcodeOrderMapper shopMsgCustcodeOrderMapper;

	@Autowired
	private ShopProjectService shopProjectService;

	@ModelAttribute
	public ShopMsg get(@RequestParam(required=false) String id) {
		ShopMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopMsgService.get(id);
		}
		if (entity == null){
			entity = new ShopMsg();
		}
		return entity;
	}
	
	/**
	 * 需求相关列表页面
	 */
	@RequiresPermissions("process:shopmsg:shopMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		if(null==currentUser){
			logger.error("当前用户不存在");
		}
		List<Role> listRole = currentUser.getRoleList();
		if(null==listRole || listRole.size()==0){
			logger.error("当前用户权限不存在");
		}
		for(Role r:listRole) {
			//判断如果是管理员则全都可以看
			if (r.getId().equals(Constants.SK_ADMIN)) {
				model.addAttribute("isSk","1");
			}
		}
		return "modules/process/shopmsg/shopMsgList";
	}
	
	/**
	 * 需求相关列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsg:shopMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopMsg shopMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopMsg> pageShopMsg = new Page<ShopMsg>(request, response);
		if(StringUtils.isEmpty(pageShopMsg.getOrderBy())){
			pageShopMsg.setOrderBy("create_date desc");
		}
        //获取当前操作用户
        User currentUser = UserUtils.getUser();
		shopMsg = this.productType(currentUser,shopMsg);
		Page<ShopMsg> page = shopMsgService.findPage(pageShopMsg, shopMsg);
		return getBootstrapData(page);
	}


	public ShopMsg productType(User currentUser,ShopMsg shopMsg){
        if(null==currentUser){
            logger.error("当前用户不存在");
            return shopMsg;
        }
        List<Role> listRole = currentUser.getRoleList();
        if(null==listRole || listRole.size()==0){
            logger.error("当前用户权限不存在");
            return shopMsg;
        }
        for(Role r:listRole){
        	//判断如果是管理员则全都可以看
				if(r.getId().equals(Constants.SYSTEM_ADMIN)){
					shopMsg.setProGroup("");
					return shopMsg;
				}
            switch (r.getId()){
                //商空管理员
                case Constants.SK_ADMIN :
                	//商空管理员标识
                    shopMsg.setIsSkAdmin("1");
                  break;
                //热水器管理员
                case Constants.GEYSER_ADMIN :
                    shopMsg.setProGroup(ProductCode.ELECTRIC_GAS_WATER_HEATER.getLabel());
					break;
                //洗衣机管理员
                case Constants.WASHING_MACHINE_ADMIN :
					shopMsg.setProGroup(ProductCode.WASHING_MACHINE.getLabel());
					break;
                //彩电管理员
                case Constants.TV_ADMIN :
					shopMsg.setProGroup(ProductCode.COLOR_TV.getLabel());
					break;
                //家空管理员
                case Constants.JK_ADMIN :
					shopMsg.setProGroup(ProductCode.HOUSEHOLD_AIR_CONDITIONER.getLabel());
					break;
                //厨电管理员
                case Constants.KITCHEN_ADMIN :
					shopMsg.setProGroup(ProductCode.KITCHEN_APPLIANCES.getLabel());
					break;
                //冷柜管理员
                case Constants.FREEZER_ADMIN :
					shopMsg.setProGroup(ProductCode.COMMERCIAL_REFRIGERATOR.getLabel());
					break;
                //冰箱管理员
                case Constants.ICE_BOX_ADMIN :
					shopMsg.setProGroup(ProductCode.REFRIGERATOR.getLabel());
					break;
            }
        }
        return shopMsg;
    }

	/**
	 * 查看，增加，编辑需求相关表单页面
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:view","process:shopmsg:shopMsg:add","process:shopmsg:shopMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopMsg shopMsg, Model model) {
		model.addAttribute("shopMsg", shopMsg);
		if(StringUtils.isBlank(shopMsg.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		//省市区初始化
		AreaCode areaCode = new AreaCode();
		areaCode.setParentId("0");
		List<AreaCode> areaCodeList = areaCodeService.findList(areaCode);
		model.addAttribute("areaCodeList", areaCodeList);
		return "modules/process/shopmsg/shopMsgForm";
	}

	/**
	 * 查看详情页面
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ShopMsg shopMsg, Model model) {
		//校验是否已经注册
		ExecuteResult<String> result = null;
		try{
			result = UserApi.userIsRegistered(shopMsg.getMobile());
		}catch (Exception e){
			log.error("校验是否已经注册接口异常" + e.getMessage());
		}
		if(!(result==null?true:result.isSuccess())){
			//已经注册过
			shopMsg.setRegistType(ProcessCode.IS_REGIST.getLabel());
		}else{
			shopMsg.setRegistType(ProcessCode.NO_REGIST.getLabel());
		}
		//查看详情标记为已读
		if(ProcessCode.MSG_NO_READ.getLabel().equals(shopMsg.getIsRead())){
			//如果没有进入详情读过，更新状态
			try{
				shopMsg.setIsRead(ProcessCode.MSG_IS_READ.getLabel());
				shopMsgService.updateMsg(shopMsg);
			} catch (ShopMsgException e) {
				log.error("更新需求发生异常,错误:" + e.getMessage());
			}
		}
		//如果是派单相关可查派单相关数据
		if(ProcessCode.IS_DISPATCHER.getLabel().equals(shopMsg.getIsDispatch())){
			//证明进入过派单,查询一下派单相关信息
			ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
			shopMsgDispatcher.setMsgId(shopMsg.getId());
			//设置msgid
			List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
			model.addAttribute("listShopMsgDispatcher", listShopMsgDispatcher);
		}
		//如果经销商订单列表存在则查询相关数据
		if(!"".equals(shopMsg.getCustCode()) && null !=shopMsg.getCustCode()){
			ShopMsgCustcodeOrder smco = new ShopMsgCustcodeOrder();
			smco.setMsgId(shopMsg.getId());
			List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(smco);
			model.addAttribute("listShopMsgCustcodeOrder", listShopMsgCustcodeOrder);
		}
		//如果hps项目有数据
		ShopProject shopProject = new ShopProject();
		shopProject.setMsgId(shopMsg.getId());
		List<ShopProject> spList = shopProjectService.findList(shopProject);
		if(spList!=null && spList.size()>0){
			model.addAttribute("shopProject", spList.get(0));
		}
		model.addAttribute("shopMsg", shopMsg);
		return "modules/process/shopmsg/shopMsgView";
	}

	/**
	 * 客服派单页面
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:dispatcher"},logical=Logical.OR)
	@RequestMapping(value = "dispatcher")
	public  String dispatcher(ShopMsg shopMsg, Model model) {
		//从字典中获取渠道来源
		String channel = DictUtils.getDictLabel(shopMsg.getChannel()==null?"":shopMsg.getChannel().toString(),"msg_channel","海尔b2b");
		//从字典中获取部门
		String deaprt = DictUtils.getDictLabel(shopMsg.getDepart()==null?"":shopMsg.getDepart().toString(),"depart_type","采购");
		//校验是否已经注册
		ExecuteResult<String> result = UserApi.userIsRegistered(shopMsg.getMobile());
		if(!(result==null?true:result.isSuccess())){
			//已经注册过
			shopMsg.setRegistType(ProcessCode.IS_REGIST.getLabel());
		}else{
			shopMsg.setRegistType(ProcessCode.NO_REGIST.getLabel());
		}
		//重置此对应值
		shopMsg.setChannel(channel);
		shopMsg.setDepart(deaprt);
		model.addAttribute("shopMsg", shopMsg);
		return "modules/process/shopmsg/shopMsgDispatcher";
	}


	/**
	 * 发送短信
	 */
	@RequestMapping(value = "sendShortMsg",method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson sendShortMsg(ShopMsg shopMsg,String content, Model model) {
		try {
			shopMsgService.sendShortMsg(shopMsg,content);
		}catch (ShopMsgException e){
			log.error("sendShortMsg,错误:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.SEND_SHORTMSG_SUCCESS.getDesc());
	}

	/**
	 * 关闭需求
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:closeMsg"},logical=Logical.OR)
	@RequestMapping(value = "closeMsg")
	@ResponseBody
	public AjaxJson closeMsg(ShopMsg shopMsg, Model model) {
		try {
			shopMsgService.closeMsg(shopMsg);
		}catch (ShopMsgException e){
			log.error(shopMsg.getCompanyName()+ ","+e.getMessage());
			return AjaxJson.fail(shopMsg.getCompanyName()+ ","+e.getMessage());
		}
		return AjaxJson.ok(shopMsg.getCompanyName()+ ","+ShopMsgCode.CLOSE_SHOPMSG_SUCCESS.getDesc());
	}


	/**
	 * 进行审核
	 */
    @RequiresPermissions(value={"process:shopmsg:shopMsg:ptCheck"},logical=Logical.OR)
	@RequestMapping(value = "ptCheck")
	@ResponseBody
	public AjaxJson ptCheck(ShopMsg shopMsg, Model model) {
		try {
			shopMsgService.ptCheck(shopMsg);
		}catch (ShopMsgException e){
			log.error(shopMsg.getCompanyName()+ "项目,"+e.getMessage());
			return AjaxJson.fail(shopMsg.getCompanyName()+ ","+e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.OPERATION_SUCCESS.getDesc());
	}


	/**
	 * 供应商列表页面
	 */
	@RequestMapping(value = {"choose"})
    @RequiresPermissions(value={"process:shopmsg:shopMsg:choose"},logical=Logical.OR)
	public String choose(Model model,String msgId) {
		ShopMsg shopMsg = null;
		if(StringUtils.isNotEmpty(msgId)){
			shopMsg = shopMsgService.get(msgId);
		}
		Dealer d = new Dealer();
		//工贸编码
		d.setGmId(shopMsg.getOrgId());
		//承接地区
		d.setUndertakeArea(shopMsg.getCityName());
		//承接品类
		d.setUnderProduct(shopMsg.getProGroup());
		//是否允许抢单
		d.setAllowDispatch(ProcessCode.YES.getLabel());
		model.addAttribute("dealer", d);
		return "modules/process/dispatcher/dealerDispatcherList";
	}

	/**
	 * 供应商列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "dataDispatcher")
	public Map<String, Object> dataDispatcher(Dealer dealer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Dealer> pageDealer = new Page<Dealer>(request, response);
		if(StringUtils.isEmpty(pageDealer.getOrderBy())){
			pageDealer.setOrderBy("create_date desc");
		}
		Page<Dealer> page = dealerService.findPage(pageDealer, dealer);
		return getBootstrapData(page);
	}

	/**
	 * 客服进行直接派单
	 */
	@RequestMapping(value = "dispatcherOrder")
	@ResponseBody
	public AjaxJson dispatcherOrder(ShopMsg shopMsg,String dealerId, Model model) {
		try {
			shopMsgService.dispatcherOrder(shopMsg,dealerId);
		}catch (ShopMsgException e){
			log.error("客服进行直接派单,"+e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.DISPATCHER_SUCCESS.getDesc());
	}


	/**
	 * 开启关闭的需求
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:restart"},logical=Logical.OR)
	@RequestMapping(value = "restart")
	@ResponseBody
	public AjaxJson restart(ShopMsg shopMsg, Model model) {
		try {
			shopMsgService.restart(shopMsg);
		}catch (ShopMsgException e){
			log.error(shopMsg.getCompanyName()+ ","+e.getMessage());
			return AjaxJson.fail(shopMsg.getCompanyName()+ ","+e.getMessage());
		}
		return AjaxJson.ok(shopMsg.getCompanyName()+ ","+ShopMsgCode.OPEN_SHOPMSG_SUCCESS.getDesc());
	}

	/**
	 * 获取省市区地址
	 */
	@RequestMapping(value="getAreaInfo")
	@ResponseBody
	public AjaxJson getAreaInfo(String areaId){
		logger.info("*_*_*_*_*_*_*_*_*_*   获取省市区信息----------接口开始*_*_*_*_*_*_*_*_*_*");
		//如果
		AreaCode areaCode = new AreaCode();
		if(org.apache.commons.lang3.StringUtils.isEmpty(areaId)){
			areaCode.setParentId("0");
			List<AreaCode> areaCodeList = areaCodeService.findList(areaCode);
			if(areaCodeList==null||areaCodeList.size()==0){
				return AjaxJson.fail("没有查到相关地区");
			}
			return AjaxJson.ok(areaCodeList);
		}
		//如果不为空则查询子集
		areaCode.setParentId(areaId);
		List<AreaCode> areaCodeList = areaCodeService.findList(areaCode);
		if(areaCodeList==null||areaCodeList.size()==0){
			return AjaxJson.fail("没有查到相关地区");
		}
		return AjaxJson.ok(areaCodeList);
	}

	/**
	 * 获取公司鹰眼接口
	 */
	@RequestMapping(value="queryEnterpriseList")
	@ResponseBody
	public AjaxJson queryEnterpriseList(String companyName){
		logger.info("*_*_*_*_*_*_*_*_*_*   获取省市区信息----------接口开始*_*_*_*_*_*_*_*_*_*");
		List<EnterpriseInfoVO> listEnterpriseInfoVO = null;
		try{
			listEnterpriseInfoVO =  HpsApi.queryEnterpriseList(companyName);
		}catch (Exception e){
			return AjaxJson.fail("hps接口异常,请联系相关人员处理");
		}
		return AjaxJson.ok(listEnterpriseInfoVO);
	}


	/**
	 * 保存需求相关
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:add","process:shopmsg:shopMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopMsg shopMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopMsg)){
			return form(shopMsg, model);
		}
		//新增或编辑表单保存
        try{
            shopMsgService.saveByManager(shopMsg);//保存
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
		addMessage(redirectAttributes, "保存需求相关成功");
		return "redirect:"+Global.getAdminPath()+"/process/shopmsg/shopMsg/?repage";
	}
	
	/**
	 * 删除需求相关
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsg:shopMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopMsg shopMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopMsgService.delete(shopMsg);
		j.setMsg("删除需求相关成功");
		return j;
	}
	
	/**
	 * 批量删除需求相关
	 */
	@ResponseBody
	@RequiresPermissions("process:shopmsg:shopMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopMsgService.delete(shopMsgService.get(id));
		}
		j.setMsg("删除需求相关成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
    @RequestMapping(value = "export", method=RequestMethod.GET)
    public AjaxJson exportFile(ShopMsg shopMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "需求相关"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			Page<ShopMsg> pageShopMsg = new Page<ShopMsg>(request, response,-1);
			if(StringUtils.isEmpty(pageShopMsg.getOrderBy())){
				pageShopMsg.setOrderBy("create_date desc");
			}
			//获取当前操作用户
			User currentUser = UserUtils.getUser();
			//获取权限
			shopMsg = this.productType(currentUser,shopMsg);
            Page<ShopMsg> page = shopMsgService.findPage(pageShopMsg, shopMsg);
    		new ExportExcel("需求相关", ShopMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出需求相关记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shopmsg:shopMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopMsg> list = ei.getDataList(ShopMsg.class);
			for (ShopMsg shopMsg : list){
				try{
					shopMsgService.save(shopMsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条需求相关记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条需求相关记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入需求相关失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsg/shopMsg/?repage";
    }
	
	/**
	 * 下载导入需求相关数据模板
	 */
	@RequiresPermissions("process:shopmsg:shopMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "需求相关数据导入模板.xlsx";
    		List<ShopMsg> list = Lists.newArrayList(); 
    		new ExportExcel("需求相关数据", ShopMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopmsg/shopMsg/?repage";
    }


    @RequestMapping(value = {"returnList"})
    public String returnList() {
        return "modules/process/shopmsgreturn/shopMsgListReturn";
    }


    /**
     * 400需求相关列表数据
     */
    @ResponseBody
    @RequiresPermissions("process:shopmsg:shopMsg:return")
    @RequestMapping(value = "returnListData")
    public Map<String, Object> returnListData(ShopMsg shopMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ShopMsg> pageShopMsg = new Page<ShopMsg>(request, response);
        if(StringUtils.isEmpty(pageShopMsg.getOrderBy())){
            pageShopMsg.setOrderBy("a.create_date desc");
        }
        //获取当前操作用户
        User currentUser = UserUtils.getUser();
        shopMsg = this.productType(currentUser,shopMsg);
        shopMsg.setChannel(MsgChannelCode.FOUR.getIndex());
        Page<ShopMsg> page = shopMsgService.findReturnPage(pageShopMsg, shopMsg);
        return getBootstrapData(page);
    }


	/**
	 * 查看，增加，编辑需求相关表单页面
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:return"},logical=Logical.OR)
	@RequestMapping(value = "returnFor400")
	public String returnFor400(ShopMsg shopMsg, Model model) {
		//如果是派单相关可查派单相关数据
		if(ProcessCode.IS_DISPATCHER.getLabel().equals(shopMsg.getIsDispatch())){
			//证明进入过派单,查询一下派单相关信息
			ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
			shopMsgDispatcher.setMsgId(shopMsg.getId());
			//设置msgid
			List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
			model.addAttribute("listShopMsgDispatcher", listShopMsgDispatcher);
		}
		//如果经销商订单列表存在则查询相关数据
		if(!"".equals(shopMsg.getCustCode()) && null !=shopMsg.getCustCode()){
			ShopMsgCustcodeOrder smco = new ShopMsgCustcodeOrder();
			smco.setMsgId(shopMsg.getId());
			List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(smco);
			model.addAttribute("listShopMsgCustcodeOrder", listShopMsgCustcodeOrder);
		}
		model.addAttribute("shopMsg", shopMsg);
		return "modules/process/shopmsg/shopMsgReturn400";
	}


	/**
	 * 查看详情页面
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:view"},logical=Logical.OR)
	@RequestMapping(value = "returnView")
	public String returnView(ShopMsg shopMsg, Model model) {
		/*//校验是否已经注册
		ExecuteResult<String> result = null;
		try{
			result = UserApi.userIsRegistered(shopMsg.getMobile());
		}catch (Exception e){
			log.error("校验是否已经注册接口异常" + e.getMessage());
		}
		if(!(result==null?true:result.isSuccess())){
			//已经注册过
			shopMsg.setRegistType(ProcessCode.IS_REGIST.getLabel());
		}else{
			shopMsg.setRegistType(ProcessCode.NO_REGIST.getLabel());
		}*/
		//查看详情标记为已读
		if(ProcessCode.MSG_NO_READ.getLabel().equals(shopMsg.getIsRead())){
			//如果没有进入详情读过，更新状态
			try{
				shopMsg.setIsRead(ProcessCode.MSG_IS_READ.getLabel());
				shopMsgService.updateMsg(shopMsg);
			} catch (ShopMsgException e) {
				log.error("更新需求发生异常,错误:" + e.getMessage());
			}
		}
		//如果是派单相关可查派单相关数据
		if(ProcessCode.IS_DISPATCHER.getLabel().equals(shopMsg.getIsDispatch())){
			//证明进入过派单,查询一下派单相关信息
			ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
			shopMsgDispatcher.setMsgId(shopMsg.getId());
			//设置msgid
			List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
			model.addAttribute("listShopMsgDispatcher", listShopMsgDispatcher);
		}
		//如果经销商订单列表存在则查询相关数据
		if(!"".equals(shopMsg.getCustCode()) && null !=shopMsg.getCustCode()){
			ShopMsgCustcodeOrder smco = new ShopMsgCustcodeOrder();
			smco.setMsgId(shopMsg.getId());
			List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(smco);
			model.addAttribute("listShopMsgCustcodeOrder", listShopMsgCustcodeOrder);
		}

		model.addAttribute("shopMsg", shopMsg);
		return "modules/process/shopmsg/shopMsgViewReturn";
	}

    /**
     * 400系统回传
     */
    @RequestMapping(value="hicBack")
    @ResponseBody
	@RequiresPermissions("process:shopmsg:shopMsg:hicBack")
    public AjaxJson hicBack(ShopHicbackpassLog shopHicbackpassLog){
        logger.info("*_*_*_*_*_*_*_*_*_*   400系统回传----------400系统回传接口开始*_*_*_*_*_*_*_*_*_*");
        try{
			shopHicbackpassLogService.hicBack(shopHicbackpassLog);
		}catch (ShopMsgException shopMsgException){
        	logger.error(shopMsgException.getMessage());
			return AjaxJson.fail(shopMsgException.getMessage());
		}
		//回传hic系统
        return AjaxJson.ok("回传成功");
    }

}