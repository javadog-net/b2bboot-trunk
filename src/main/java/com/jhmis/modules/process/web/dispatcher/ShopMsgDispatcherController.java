/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.dispatcher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.haier.user.api.UserApi;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.webservices.client.hps.project.HpsApi;
import com.haier.webservices.client.hps.project.UserDTO;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ProductCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.modules.process.entity.shopmsgproduct.ShopMsgProduct;
import com.jhmis.modules.process.service.shopmsgproduct.ShopMsgProductService;
import com.jhmis.modules.process.web.shopmsg.ShopMsgController;
import com.jhmis.modules.sys.entity.Role;
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
import com.haier.util.ProductCodeRelateUtil;
import com.haier.webservices.client.hps.project.ProjectWebServiceServerStub;
import com.haier.webservices.client.hps.project.ProjectWebServiceServer_Service;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.process.entity.dispatcher.CmsOperlogs;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.service.dispatcher.CmsOperlogsService;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.sys.entity.Area;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.mapper.UserMapper;
import com.jhmis.modules.sys.service.AreaService;

/**
 * 派单相关Controller
 * @author hdx
 * @version 2019-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/process/dispatcher/shopMsgDispatcher")
public class ShopMsgDispatcherController extends BaseController {

	Logger log = LoggerFactory.getLogger(ShopMsgDispatcherController.class);

	@Autowired
	private ShopMsgDispatcherService shopMsgDispatcherService;
	@Autowired
	private ShopMsgService shopMsgService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private DealerService dealerService;
	@Autowired
	private CmsOperlogsService cmsOperlogsService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	ShopMsgProductService shopMsgProductService;
	@ModelAttribute
	public ShopMsgDispatcher get(@RequestParam(required=false) String id) {
		ShopMsgDispatcher entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopMsgDispatcherService.get(id);
		}
		if (entity == null){
			entity = new ShopMsgDispatcher();
		}
		return entity;
	}
	
	/**
	 * 派单功能列表页面
	 */
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/dispatcher/shopMsgDispatcherList";
	}
	
	/**
	 * 派单功能列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopMsgDispatcher shopMsgDispatcher, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopMsgDispatcher> pageShopMsgDispatcher = new Page<ShopMsgDispatcher>(request, response);
		if(StringUtils.isEmpty(pageShopMsgDispatcher.getOrderBy())){
			pageShopMsgDispatcher.setOrderBy("a.create_date desc");
		}
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		shopMsgDispatcher = this.productType(currentUser,shopMsgDispatcher);
		Page<ShopMsgDispatcher> page = shopMsgDispatcherService.findPage(pageShopMsgDispatcher, shopMsgDispatcher);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑派单功能表单页面
	 */
	@RequiresPermissions(value={"process:dispatcher:shopMsgDispatcher:view","process:dispatcher:shopMsgDispatcher:add","process:dispatcher:shopMsgDispatcher:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopMsgDispatcher shopMsgDispatcher, Model model) {
		model.addAttribute("shopMsgDispatcher", shopMsgDispatcher);
		if(StringUtils.isBlank(shopMsgDispatcher.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/dispatcher/shopMsgDispatcherForm";
	}

	/**
	 * 保存派单功能
	 */
	@RequiresPermissions(value={"process:dispatcher:shopMsgDispatcher:add","process:dispatcher:shopMsgDispatcher:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopMsgDispatcher shopMsgDispatcher, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopMsgDispatcher)){
			return form(shopMsgDispatcher, model);
		}
		//新增或编辑表单保存
		shopMsgDispatcherService.save(shopMsgDispatcher);//保存
		addMessage(redirectAttributes, "保存派单功能成功");
		return "redirect:"+Global.getAdminPath()+"/process/dispatcher/shopMsgDispatcher/?repage";
	}
	
	/**
	 * 删除派单功能
	 */
	@ResponseBody
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopMsgDispatcher shopMsgDispatcher, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopMsgDispatcherService.delete(shopMsgDispatcher);
		j.setMsg("删除派单功能成功");
		return j;
	}
	
	/**
	 * 批量删除派单功能
	 */
	@ResponseBody
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopMsgDispatcherService.delete(shopMsgDispatcherService.get(id));
		}
		j.setMsg("删除派单功能成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopMsgDispatcher shopMsgDispatcher, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "派单功能"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopMsgDispatcher> page = shopMsgDispatcherService.findPage(new Page<ShopMsgDispatcher>(request, response, -1), shopMsgDispatcher);
    		new ExportExcel("派单功能", ShopMsgDispatcher.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出派单功能记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopMsgDispatcher> list = ei.getDataList(ShopMsgDispatcher.class);
			for (ShopMsgDispatcher shopMsgDispatcher : list){
				try{
					shopMsgDispatcherService.save(shopMsgDispatcher);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条派单功能记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条派单功能记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入派单功能失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/dispatcher/shopMsgDispatcher/?repage";
    }
	
	/**
	 * 下载导入派单功能数据模板
	 */
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "派单功能数据导入模板.xlsx";
    		List<ShopMsgDispatcher> list = Lists.newArrayList(); 
    		new ExportExcel("派单功能数据", ShopMsgDispatcher.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/dispatcher/shopMsgDispatcher/?repage";
    }

	
	
	/** 
	  * @Title: directorFranchiser 
	  * @Description: TODO  总监派单拉取经销商列表   xml 未完善，待完善 
	  * @param msgid
	  * @param orgid
	  * @param currPage
	  * @param pageSize
	  * @param request
	  * @param response
	  * @return 
	  * @return Map<String,Object>
	  * @author tc
	  * @date 2019年9月19日下午5:15:33
	  */
	@ResponseBody
	@RequestMapping(value = "/directorFranchiser", method = RequestMethod.POST)
	public Map<String, Object> directorFranchiser(String msgid, String orgid, int currPage, int pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Dealer> franchisers = null;
		if (StringUtils.isBlank(msgid)|| StringUtils.isBlank(orgid)) {
			String content="异常信息，获取经销商列表失败-----" + msgid + "------" + orgid;
			logger.info(content);
			CmsOperlogs	cmsOperlogs=new CmsOperlogs();
			cmsOperlogs.setContent(replaceHtml(content));
			cmsOperlogs.setIp(request.getRemoteAddr());
			cmsOperlogs.setLoginname("总监拉取经销商信息列表接口异常:msgid/orgid为空");
			cmsOperlogs.setOpertime(new Date());
			cmsOperlogsService.save(cmsOperlogs);
			return ret;
		}

		try {
			ShopMsg msgInfo = shopMsgService.get(msgid);
			/*
			 * if (msgInfo != null &&
			 * StringUtils.isNotEmpty(msgInfo.getProduct())) { product =
			 * msgInfo.getProduct(); // 如果产品组全部是商空产品,则只能指派给商空的经销商 if
			 * (StringUtils.isNotEmpty(msgInfo.getSkmodel())) {
			 * franchiser.setSkmodel("1"); } }
			 */
			// 产品组
			String underproduct = "";
			// 承接区域
			String underarea = "";
			String product = msgInfo.getProGroup();
			if (StringUtils.isNotEmpty(product)) {
				if (product.indexOf(";") > 0) {
					String[] productArray = product.split(";");
					for (int i = 0; i < productArray.length; i++) {
						underproduct = underproduct + ("and underproduct like '%" + productArray[i] + "%'");
					}
				} else {
					underproduct = ("and underproduct like '%" + product + "%'");
				}
			}
			// 如果所在城市不为空
			if (StringUtils.isNotEmpty(msgInfo.getCityName())) {
				underarea = msgInfo.getCityName();
			} else {
				// 城市名称为空，根据城市编码查询所在城市名称
				if (StringUtils.isNotEmpty(msgInfo.getCityId())) {
					Area area = areaService.get(msgInfo.getCityId());
					if (area != null && StringUtils.isNotEmpty(area.getName())) {
						underarea = area.getName();
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("orgid", orgid);
			map.put("msgid", msgid);
			map.put("underproduct", underproduct);
			map.put("underarea", underarea);
			// 分页起始值
			int skip = (currPage - 1) * pageSize;
			map.put("skip", skip);
			map.put("pageSize", pageSize);
			logger.info("总监拉取经销商信息列表，参数" + map);
			// 经销商选择列表
			franchisers = dealerService.findListdealer(map);
			ret.put("franchisers", franchisers);
			int total = 0;
			int totalPage = 0;
			if (franchisers != null) {
				total = franchisers.size();
			}
			if (total % pageSize == 0) {
				totalPage = total / pageSize;
			} else {
				totalPage = total / pageSize + 1;
			}
			ret.put("total", total);
			ret.put("totalPage", totalPage);
			ret.put("pageSize", pageSize);
			ret.put("currPage", currPage);
			logger.info("成功拉取总监拉取经销商信息列表");
			return ret;
		} catch (Exception e) {
			String content="总监拉取经销商信息列表接口异常,参数 msgid= " + msgid + "orgid= " + orgid;
			logger.info(content);
			CmsOperlogs	cmsOperlogs=new CmsOperlogs();
			cmsOperlogs.setContent(replaceHtml(content));
			cmsOperlogs.setIp(request.getRemoteAddr());
			cmsOperlogs.setLoginname("总监拉取经销商信息列表接口异常");
			cmsOperlogs.setOpertime(new Date());
			cmsOperlogsService.save(cmsOperlogs);
			return ret;
		}
	}
	
	public static String replaceHtml(String html){ 
        String regEx="<.+?>"; //表示标签 
        Pattern p=Pattern.compile(regEx); 
        Matcher m=p.matcher(html); 
        String s=m.replaceAll(""); 
        return s; 
    }


	/**
	 * 查看详情页面
	 */
	@RequestMapping(value = "view")
	public String view(ShopMsg shopMsg, Model model, RedirectAttributes redirectAttributes) {
		//非空判断
		if(StringUtils.isEmpty(shopMsg.getId())){
			addMessage(redirectAttributes, "派单管理查看详情页面异常：原因-需求id为空");
		}
		//查询需求id
		ShopMsg s = shopMsgService.get(shopMsg.getId());
		if(null==s){
			addMessage(redirectAttributes, "派单管理查看详情页面异常：原因-id=" + shopMsg.getId() + "此需求不存在");
		}
		//查询产品组编码
		List<ShopMsgProduct> listShopMsgProduct = shopMsgProductService.findList(new ShopMsgProduct().setMsgId(shopMsg.getId()));
		//循环获取产品组
		String proCode = "";
		for(ShopMsgProduct sp:listShopMsgProduct){
			proCode = proCode.concat(sp.getProductGroupCode()+",");
		}
		//去除多余逗号
		if(listShopMsgProduct.size()>1){
			proCode = proCode.substring(0,proCode.length()-1);
		}
		//通过工贸及产品组查询海尔接口人
		List<UserDTO> listUserDTO = HpsApi.queryProjectManagerFromHPS(s.getOrgId(),proCode,"CUSTOMER_ORDER","");
		if(null==listUserDTO || listUserDTO.size()==0){
			addMessage(redirectAttributes, "派单管理查询海尔接口人异常：原因-海尔接口人不存在");
		}
		//根据需求id查询派单表相关数据
		ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
		//设置需求id
		shopMsgDispatcher.setMsgId(shopMsg.getId());
		//是否关闭
		shopMsgDispatcher.setIsClosed(ProcessCode.NO.getLabel());
		List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
		if(null==listShopMsgDispatcher||listShopMsgDispatcher.size()==0){
		    logger.error("派单管理查询海尔接口人异常：原因-此需求不在流程节点内");
			//addMessage(redirectAttributes, "派单管理查询海尔接口人异常：原因-此需求不在流程节点内");
			model.addAttribute("message", "派单管理查询海尔接口人异常：原因-此需求不在流程节点内");
            return "modules/process/dispatcher/shopMsgDispatcherView";
		}
		shopMsgDispatcher = listShopMsgDispatcher.get(0);
		model.addAttribute("shopMsgDispatcher", shopMsgDispatcher);
		model.addAttribute("shopMsg", s);
		model.addAttribute("listUserDTO", listUserDTO);
        return "modules/process/dispatcher/shopMsgDispatcherView";
	}

	/**
	 * 确认海尔接口人
	 */
	@ResponseBody
	@RequestMapping(value = "/sureHaierPerson")
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:sureHaierPerson")
	public AjaxJson sureHaierPerson(ShopMsg shopMsg, RedirectAttributes redirectAttributes) {

		try {
			shopMsgService.sureHaierPerson(shopMsg);
		} catch (ShopMsgException e) {
			log.error("/sureHaierPerson-确认海尔接口人 接口异常,原因：" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.OPERATION_SUCCESS.getDesc());
	}

	/**
	 * 派单关闭
	 */
	@ResponseBody
	@RequestMapping(value = "/dispatclose")
	@RequiresPermissions("process:dispatcher:shopMsgDispatcher:dispatclose")
	public AjaxJson dispatclose(ShopMsgDispatcher shopMsgDispatcher, RedirectAttributes redirectAttributes) {
		try {
			shopMsgService.dispatclose(shopMsgDispatcher);
		} catch (ShopMsgException e) {
			log.error("/dispatclose-派单关闭,接口异常,原因：" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.OPERATION_SUCCESS.getDesc());
	}


	public ShopMsgDispatcher productType(User currentUser,ShopMsgDispatcher shopMsgDispatcher){
		if(null==currentUser){
			logger.error("当前用户不存在");
			return shopMsgDispatcher;
		}
		List<Role> listRole = currentUser.getRoleList();
		if(null==listRole || listRole.size()==0){
			logger.error("当前用户权限不存在");
			return shopMsgDispatcher;
		}
		for(Role r:listRole){
			//判断如果是管理员则全都可以看
			if(r.getId().equals(Constants.SYSTEM_ADMIN)){
				shopMsgDispatcher.setProGroup("");
				return shopMsgDispatcher;
			}
			switch (r.getId()){
				//商空管理员
				case Constants.SK_ADMIN :
					//商空管理员标识
					shopMsgDispatcher.setIsSkAdmin("1");
					break;
				//热水器管理员
				case Constants.GEYSER_ADMIN :
					shopMsgDispatcher.setProGroup(ProductCode.ELECTRIC_GAS_WATER_HEATER.getLabel());
					break;
				//洗衣机管理员
				case Constants.WASHING_MACHINE_ADMIN :
					shopMsgDispatcher.setProGroup(ProductCode.WASHING_MACHINE.getLabel());
					break;
				//彩电管理员
				case Constants.TV_ADMIN :
					shopMsgDispatcher.setProGroup(ProductCode.COLOR_TV.getLabel());
					break;
				//家空管理员
				case Constants.JK_ADMIN :
					shopMsgDispatcher.setProGroup(ProductCode.HOUSEHOLD_AIR_CONDITIONER.getLabel());
					break;
				//厨电管理员
				case Constants.KITCHEN_ADMIN :
					shopMsgDispatcher.setProGroup(ProductCode.KITCHEN_APPLIANCES.getLabel());
					break;
				//冷柜管理员
				case Constants.FREEZER_ADMIN :
					shopMsgDispatcher.setProGroup(ProductCode.COMMERCIAL_REFRIGERATOR.getLabel());
					break;
				//冰箱管理员
				case Constants.ICE_BOX_ADMIN :
					shopMsgDispatcher.setProGroup(ProductCode.REFRIGERATOR.getLabel());
					break;
			}
		}
		return shopMsgDispatcher;
	}
	
}