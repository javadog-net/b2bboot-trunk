package com.jhmis.api.process;

import cn.hutool.http.HttpRequest;
import com.haier.util.ProductCodeRelateUtil;
import com.haier.webservices.client.hps.project.HpsApi;
import com.haier.webservices.client.hps.project.UserDTO;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ProductCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.lock.LockUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.UploadService;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.process.entity.shopevaluate.ShopMsgEvaluate;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.entity.shopmsgstatus.ShopMsgStatus;
import com.jhmis.modules.process.entity.shopmsgzykc.ShopMsgZykc;
import com.jhmis.modules.process.service.shopevaluate.ShopMsgEvaluateService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
import com.jhmis.modules.process.service.shopmsgzykc.ShopMsgZykcService;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.entity.Role;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.service.GmInfoService;
import com.jhmis.modules.sys.utils.UserUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.process
 * @Author: hdx
 * @CreateTime: 2019-08-30 17:32
 * @Description: 企业购给大客户APP提供的接口
 */
@Api(value = "ApiProcessAppController", description = "企业购给大客户app提供接口")
@RestController
@RequestMapping("/api/process/app")
public class ApiProcessAppController extends BaseController {

    @Autowired
    ShopMsgService shopMsgService;

    @Autowired
    ShopMsgZykcService shopMsgZykcService;

    @Autowired
    AreaCodeService areaCodeService;

	@Autowired
	private GmInfoService gmInfoService;

    @Autowired
    ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;

	@Autowired
	UploadService uploadService;


	@Autowired
	DealerService dealerService;

	@Autowired
    ShopMsgEvaluateService shopMsgEvaluateService;


	/**
	 *@Author: hdx
	 *@CreateTime: 16:06 2019/9/25
	 *@param msgId 需求id
	 *@Description: 根据需求id获取需求详情信息
	 */
	@ApiOperation(notes = "getShopMsgDetail", httpMethod = "POST", value = "根据需求id获取需求详情信息", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/getShopMsgDetail", method = RequestMethod.POST)
	@SysLog(desc="根据需求id获取需求详情信息")
	public AjaxJson getShopMsgDetail(String msgId) {
		ShopMsg shopMsg = null;
		boolean managerNoFlag = true;
		try {
			shopMsg = shopMsgService.getShopMsgDetail(msgId);
			if(StringUtils.isEmpty(shopMsg.getManagerNo())){
				managerNoFlag = false;
			}
			//设置海尔接口人是否存在标识
			shopMsg.setManagerNoFlag(managerNoFlag);
		} catch (ShopMsgException e) {
			logger.error("/getShopMsgDetail 根据需求id获取需求详情信息异常，原因=" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(shopMsg);
	}



    /**
     *@Author: hdx
     *@CreateTime: 16:06 2019/9/25
     *@param orgId 工贸id
     *@param directorNo 总监编号
     *@Description: 对应App留言板-行业总监查看待审核的留言信息
     */
    @ApiOperation(notes = "directorTradeAll", httpMethod = "POST", value = "行业总监查看待审核的留言信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "orgId", value = "工贸id", required = true, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "directorNo", value = "总监编号", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping(value = "/directorTradeAll", method = RequestMethod.POST)
    @SysLog(desc="行业总监查看待审核的留言信息")
    public AjaxJson directorTradeAll(String orgId, String directorNo) {
        List<?> listMsg = null;
        try {
            listMsg = shopMsgService.directorGetMsgDetail(orgId,directorNo);
        } catch (ShopMsgException e) {
            logger.error("/tradeAll 行业总监查看待审核的留言信息异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(listMsg);
    }

    /**
    *@Author: hdx
    *@CreateTime: 17:35 2019/8/30
    *@param:  * @param shopMsg实体
    *@Description: 总监审核接口
    */
    @RequestMapping(value = "/directorApproval", method = RequestMethod.POST)
    @SysLog(desc="总监审核接口")
    public AjaxJson directorApproval(ShopMsg shopMsg, HttpServletRequest request, HttpServletResponse response) {
        try {
            shopMsgService.directorCheck(shopMsg);
        } catch (ShopMsgException e) {
        	logger.error("/directorApproval-总监审核接口异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
    }

	/**
	 *@Author: hdx
	 *@CreateTime: 17:35 2019/8/30
	 *@param: msgId 需求id
	 *@param: directorNo 总监编号
	 *@param: orgid 工贸编码
	 *@param: currPage 当前页
	 *@param: pageSize 页数
	 *@Description: 总监派单拉取经销商列表
	 */
	@ApiOperation(notes = "directorPullListDealer", httpMethod = "POST", value = "总监派单拉取经销商列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "directorNo", value = "总监账号", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "orgid", value = "工贸编码", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "currPage", value = "当前页", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "form", dataType = "String")
	})
	@RequestMapping(value = "/directorPullListDealer", method = RequestMethod.POST)
	@SysLog(desc="总监派单拉取经销商列表")
	public AjaxJson directorPullListDealer(String msgId, String directorNo, String orgid, @RequestParam(defaultValue="1") int currPage,@RequestParam(defaultValue="10") int pageSize, HttpServletRequest request, HttpServletResponse response) {
		Page<?> pageDealer = null;
		try {
			pageDealer =  shopMsgService.directorPullListDealer(msgId,directorNo,orgid,currPage,pageSize,request,response);
		} catch (ShopMsgException e) {
			logger.error("/directorPullListDealer-总监派单拉取经销商列表,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(pageDealer);
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param:
     * @param directorNo 总监账号
	 * @param msgid 需求id
	 * @param dealerCode 经销商编码
	 * @param dispadesc 派单原因
	 * @param managerNo 海尔接口人编号
	 * @param managerName 海尔接口人名称
	 *@Description:
	 */
	@ApiOperation(notes = "directorDispatch", httpMethod = "POST", value = "总监派单", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "directorNo", value = "总监账号", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "msgid", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "dispadesc", value = "派单原因", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "managerNo", value = "海尔接口人编号", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "managerName", value = "海尔接口人名称", required = true, paramType = "form", dataType = "String")
	})
	@RequestMapping(value = "/directorDispatch", method = RequestMethod.POST)
	@SysLog(desc="总监派单")
	public AjaxJson directorDispatch(String directorNo, String msgid, String dealerCode, String dispadesc, String managerNo, String managerName, HttpServletRequest request, HttpServletResponse response) {

		try {
			shopMsgService.directorDispatch(directorNo,msgid,dealerCode,dispadesc,managerNo,managerName);
		} catch (ShopMsgException e) {
			logger.error("/directorDispatch-总监派单异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		//总监派单成功
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_008"));
	}




	/**
	 *@Author: hdx
	 *@CreateTime: 17:35 2019/10/8
	 *@param: msgId 需求id
	 *@param: directorNo 总监编号
	 *@param: orgid 工贸编码
	 *@param: currPage 当前页
	 *@param: pageSize 页数
	 *@Description: 管理员派单拉取经销商列表
	 */
	@ApiOperation(notes = "adminPullListDealer", httpMethod = "POST", value = "管理员派单拉取经销商列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "currPage", value = "当前页", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "form", dataType = "String")
	})
	@RequestMapping(value = "/adminPullListDealer", method = RequestMethod.POST)
	@SysLog(desc="管理员派单拉取经销商列表")
	public AjaxJson adminPullListDealer(String msgId,@RequestParam(defaultValue="1") int currPage,@RequestParam(defaultValue="10") int pageSize, HttpServletRequest request, HttpServletResponse response) {
		Page<?> pageDealer = null;
		try {
			pageDealer =  shopMsgService.adminPullListDealer(msgId,currPage,pageSize,request,response);
		} catch (ShopMsgException e) {
			logger.error("/adminPullListDealer-管理员派单拉取经销商列表异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(pageDealer);
	}





    /**
     *@Author: hdx
     *@CreateTime: 17:35 2019/8/30
     *@param: dealerCode 经销商编码
     *@Description: 经销商查看可抢单信息
     */
    @ApiOperation(notes = "dealerPreempInfo", httpMethod = "POST", value = "经销商查看可抢单信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/dealerPreempInfo", method = RequestMethod.POST)
    @SysLog(desc="经销商查看可抢单信息")
    public AjaxJson dealerPreempInfo(String dealerCode) {
		List<ShopMsg> listShopMsg = null;
        try {
			listShopMsg = shopMsgService.dealerPreempInfo(dealerCode);
        } catch (ShopMsgException e) {
            logger.error("/dealerPreempInfo-经销商查看可抢单信息,原因:" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(listShopMsg);
    }


	/**
	 *@Author: hdx
	 *@CreateTime: 17:35 2019/8/30
	 *@param: dealerCode 经销商编码
	 *@Description: 经销商查看是否还可继续抢单(当处于跟进中的单子大于10时,则不可再抢单)
	 */
	@ApiOperation(notes = "dealerIsGrab", httpMethod = "POST", value = "经销商查看是否还可继续抢单(当处于跟进中的单子大于10时,则不可再抢单)", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerIsGrab", method = RequestMethod.POST)
	@SysLog(desc="经销商查看是否还可继续抢单(当处于跟进中的单子大于10时,则不可再抢单)")
	public AjaxJson dealerIsGrab(String dealerCode) {
		List<ShopMsg> listShopMsg = null;
		Map<String,Object> map = null;
		try {
			map= shopMsgService.dealerIsGrab(dealerCode);
		} catch (ShopMsgException e) {
			logger.error("/dealerIsGrab-经销商查看是否还可继续抢单异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(map);
	}


    /**
     * 通过msgId 拉取负责人列表
     * @param msgId 需求id
     * @return
     */
    @ApiOperation(notes = "queryProjectManager", httpMethod = "POST", value = "通过msgId 拉取负责人列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping(value = "/queryProjectManager", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(desc="通过msgId 拉取负责人列表")
    public AjaxJson queryProjectManager(String msgId,HttpServletRequest request, HttpServletResponse response) throws IOException {
        ShopMsg shopMsg = shopMsgService.get(msgId);
        //判断有无数据
        if(shopMsg==null){
            return AjaxJson.fail("此需求不存在");
        }
        //存在数据则进行产品组筛选
        String pro = shopMsg.getProGroup();
        if("".equals(pro)){
            return AjaxJson.fail("产品组参数不能为空");
        }
        //拆分产品组
        String[] proArgs = pro.split(";");
        //设置最终产品组值
        String endParsePro = "";
        //获取产品组分类
        Map<String,String> mapCodeCollection = ProductCodeRelateUtil.getProductMap();
        for(int i=0; i<proArgs.length; i++){
            logger.info("*********************拆分匹配产品组与漏斗编码对应mapCodeCollection*********************" + mapCodeCollection);
            endParsePro = endParsePro + mapCodeCollection.get(proArgs[i]) + ",";
        }
        logger.info("*_*_*_*_*_*_*_*_*_* 产品组分类= " +endParsePro+ "*_*_*_*_*_*_*_*_*_*");
        endParsePro = endParsePro.substring(0,endParsePro.length()-1);
        List<UserDTO> listUserDTO = null ;
        try {
            listUserDTO = HpsApi.queryProjectManagerFromHPS(shopMsg.getOrgId(), endParsePro, "CUSTOMER_ORDER","");
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxJson.ok(listUserDTO);
    }

    /**
     * 获取省市区信息
     * @param areaId
     * @return
     */
    @ApiOperation(notes = "getAreaInfo", httpMethod = "GET", value = "获取省市区信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaId", value = "地区id(如果不传则取省份)", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping(value="getAreaInfo")
    @SysLog(desc="获取省市区信息")
    public AjaxJson getAreaInfo(String areaId){
        //如果
        AreaCode areaCode = new AreaCode();
        if(StringUtils.isEmpty(areaId)){
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
	  * @Title: selectGmInfo
	  * @Description: 查询全国工贸信息
	  * @return
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年9月9日下午5:04:05
	  */
	@ApiOperation(notes = "selectGmInfo", httpMethod = "GET", value = "查询全国工贸信息", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
	})
	@RequestMapping(value = "/selectGmInfo")
	@SysLog(desc="查询全国工贸信息")
	public AjaxJson selectGmInfo() {
		List<GmInfo> gmInfoList = gmInfoService.findList(new GmInfo());
		return AjaxJson.ok(gmInfoList);
	}


	/**
	 * @Title: selectIndustryDirector
	 * @Description: TODO 根据工贸branchCode 查询工程总监
	 * @param branchCode
	 *            工贸branchCode
	 * @param response
	 * @param request
	 * @return
	 * @return
	 * @author tc
	 * @date 2019年9月11日下午2:39:24
	 */
	@RequestMapping(value = "/selectIndustryDirector")
	@SysLog(desc = "根据工贸branchCode 查询工程总监")
	public AjaxJson selectIndustryDirector(String branchCode, HttpServletResponse response,
			HttpServletRequest request) {
		User u=null;
		try {
			u=shopMsgService.selectIndustryDirector(branchCode);
		} catch (ShopMsgException e) {
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(u);
	}

	/**
	 * @Title: selectDealerByOrgid
	 * @Description: 总监-根据工贸code 查询经销商
	 * @param orgid
	 * @param request
	 * @param response
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年9月25日下午6:07:15
	 */
	@RequestMapping("/selectDealerByOrgid")
	@SysLog(desc = "总监-根据工贸code 查询经销商")
	public AjaxJson selectDealerByOrgid(String orgid, HttpServletRequest request, HttpServletResponse response) {
		List<Dealer> uLsit = null;
		try {
			uLsit = shopMsgService.selectDealerByOrgid(orgid);
		} catch (ShopMsgException e) {
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(uLsit);
	}

	/**
	 * @Title: sendApp
	 * @Description: TODO app 推送方法 传入参数 形式如下
	 * @param param
	 *            cn.hutool.json.JSONObject param= JSONUtil.createObj();
	 *            param.put("keyWords", keyWords); ......
	 *  params.put("pushUser", "被推送人");
		params.put("content", "派单提醒： 您收到总监派单项目" + msgInfo.getCompanyname() + "，请及时跟进");
		params.put("jumpType", "qd4");
		params.put("id", msgCustcodeOrder.getId().toString());
		params.put("mid", msgCustcodeOrder.getMsgid().toString());
		params.put("customernumber", msgCustcodeOrder.getCustcode());
		params.put("undertake", msgCustcodeOrder.getUndertake());
		params.put("canelflag", String.valueOf(msgCustcodeOrder.getCancelfalg()));
		params.put("depart", msgCustcodeOrder.getOrgid());
		params.put("orderid", msgCustcodeOrder.getId().toString());         
	 *            
	 *            
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author tc
	 * @date 2019年9月26日上午11:15:17
	 */
	public static boolean sendApp(cn.hutool.json.JSONObject param) throws Exception {
		// app推送接口url地址
		String url = "http://58.56.174.23:8081/gcapp/pushCust";
		Boolean flag = false;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Content-Type", "application/json;charset=utf-8");
			String result = HttpRequest.post(url).addHeaders(map).body(param).execute().body();
			if (result.length() > 0) {
				if ("true".equals(result.substring(1, result.length() - 1))) {
					flag = true;
				} else {
					throw new Exception(result);
				}
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return flag;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param:
	 *@param dealerCode 经销商编码
	 *@Description:  经销商-根据销商编码返回每个状态的订单数量
	 */
	@ApiOperation(notes = "dealerOrderAllStatus", httpMethod = "POST", value = "经销商-根据销商编码返回每个状态的订单数量", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String")
	})
	@RequestMapping(value = "/dealerOrderAllStatus", method = RequestMethod.POST)
	@SysLog(desc="经销商-根据销商编码返回每个状态的订单数量")
	public AjaxJson dealerOrderAllStatus(String dealerCode, HttpServletRequest request, HttpServletResponse response) {
		List<Integer> listAllStatus = null;
		try {
			listAllStatus = shopMsgService.dealerOrderAllStatus(dealerCode);
		} catch (ShopMsgException e) {
			logger.error("/dealerOrderAllStatus-经销商-根据销商编码返回每个状态的订单数量异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		//返回每个状态的订单数量成功
		return AjaxJson.ok(listAllStatus);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param:
	 * @param dealerCode 经销商编码
	 *@Description:  经销商-根据经销商编码获得未处理(未选择承接方式)订单数量
	 */
	@ApiOperation(notes = "dealerOrderUnderTake", httpMethod = "POST", value = "经销商- 根据经销商编码获得未处理(未选择承接方式)订单数量", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String")
	})
	@RequestMapping(value = "/dealerOrderUnderTake", method = RequestMethod.POST)
	@SysLog(desc="经销商-根据经销商编码获得未处理(未选择承接方式)订单数量")
	public AjaxJson dealerOrderUnderTake(String dealerCode) throws ShopMsgException{
		//查询是否存在此经销商
		int count = 0;
		try{
			count = shopMsgService.dealerOrderUnderTake(dealerCode);
		}catch (ShopMsgException e){
			logger.error("/dealerOrderUnderTake-经销商- 根据经销商编码获得未处理(未选择承接方式)订单数量异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(count);
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param:
	 *@param dealerCode 经销商编码
	 *@param gcFlag 工程单标记
	 *@param lsFlag 零售单标记
	 *@param cancelFlag 客户弃单标记
     *@param pageNo 页数
     *@param pageSize 每页显示数
	 *@Description:  经销商-查看我的项目
	 */
	@ApiOperation(notes = "dealerGetMineOrder", httpMethod = "POST", value = "经销商-查看我的项目", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "gcFlag", value = "工程单标记", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "lsFlag", value = "零售单标记", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "cancelFlag", value = "客户弃单标记", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, paramType = "form", dataType = "int"),
	})
	@RequestMapping(value = "/dealerGetMineOrder", method = RequestMethod.POST)
	@SysLog(desc="经销商-查看我的项目")
	public AjaxJson dealerGetMineOrder(String dealerCode,String gcFlag,String lsFlag,String cancelFlag,int pageNo,int pageSize,HttpServletRequest request, HttpServletResponse response) throws ShopMsgException{
        Page<ShopMsgCustcodeOrder> pageShopMsgCustcodeOrder = null;
		try{
            pageShopMsgCustcodeOrder = shopMsgService.dealerGetMineOrder(dealerCode,gcFlag,lsFlag,cancelFlag,pageNo,pageSize,request,response);
		}catch (ShopMsgException e){
			logger.error("/dealerGetMineOrder-经销商-查看我的项目异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(pageShopMsgCustcodeOrder);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param orgId 经销商编码
	 *@param gcFlag 工程单标记
	 *@param lsFlag 零售单标记
	 *@param cancelFlag 客户弃单标记
	 *@Description:  总监/管理员-查看我的项目
	 */
	@ApiOperation(notes = "directorOrAdminGetOrder", httpMethod = "POST", value = "经销商-查看我的项目", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "directorNo", value = "总监账号", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "custCode", value = "经销商账号(多个逗号隔开)", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "orgId", value = "工贸编码", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "gcFlag", value = "工程单标记", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "lsFlag", value = "零售单标记", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "cancelFlag", value = "客户弃单标记", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "pageNo", value = "页数", required = true, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示数", required = true, paramType = "form", dataType = "int"),
	})
	@RequestMapping(value = "/directorOrAdminGetOrder", method = RequestMethod.POST)
	@SysLog(desc="总监/管理员-查看我的项目")
	public AjaxJson directorOrAdminGetOrder(String custCode, String directorNo,String orgId,String gcFlag,String lsFlag,String cancelFlag,int pageNo,int pageSize) throws ShopMsgException{
		Page<ShopMsgCustcodeOrder> pageShopMsgCustcodeOrder = null;
		try{
			pageShopMsgCustcodeOrder = shopMsgService.directorOrAdminGetOrder(custCode,directorNo,orgId,gcFlag,lsFlag,cancelFlag,pageNo,pageSize);
		}catch (ShopMsgException e){
			logger.error("/directorOrAdminGetOrder-总监/管理员-查看我的项目异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(pageShopMsgCustcodeOrder);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param msgId 需求id
	 *@param cancelReason 工贸编码
	 *@param memo 工程单标记
	 *@param dealerCode 经销商编码
	 *@Description:  经销商-在选择承接方式之前弃单
	 */
	@ApiOperation(notes = "dealerCancelOrderBefore", httpMethod = "POST", value = "经销商-在选择承接方式之前弃单", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "cancelReason", value = "弃单原因", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "memo", value = "备注", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String")
	})
	@RequestMapping(value = "/dealerCancelOrderBefore", method = RequestMethod.POST)
	@SysLog(desc="经销商-在选择承接方式之前弃单")
	public AjaxJson dealerCancelOrderBefore(String msgId,String cancelReason,String memo,String dealerCode) throws ShopMsgException{
		try{
			shopMsgService.dealerCancelOrderBefore(msgId,cancelReason,memo,dealerCode);
		}catch (ShopMsgException e){
			logger.error("/dealerCancelOrderBefore-经销商-在选择承接方式之前弃单异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_009"));
	}


	/**
	 * @Title: outWarehouse
	 * @Description: TODO 提供给app的物流出库接口
	 * @param orderid
	 * @param delivernum
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年9月29日下午2:08:58
	 */
	@RequestMapping("/outWarehouse")
	@SysLog(desc="提供给app的物流出库接口")
	public AjaxJson outWarehouse(String orderid, String delivernum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = shopMsgCustcodeOrderService.outWarehouse(orderid, delivernum);
		} catch (ShopMsgException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(map);
	}

	/**
	  * @Title: sign
	  * @Description: TODO 提供给app 的物流签收接口。上传凭证
	  * @param orderid
	  * @param file
	  * @param request
	  * @param isScale
	  * @param width
	  * @param height
	  * @return
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年9月29日下午4:20:19
	  */
	@ApiOperation(notes = "sign", httpMethod = "POST", value = "上传图片", consumes = "multipart/*")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
			@ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
			@ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
	})
	@PostMapping(value = "/sign")
	@SysLog(desc="提供给app的物流签收接口")
	public AjaxJson sign(String orderid,@ApiParam(name = "任意", value = "图片上传") MultipartFile file,
			HttpServletRequest request,
			@RequestParam(defaultValue = "false") Boolean isScale,
			@RequestParam(defaultValue = "240") Integer width,
			@RequestParam(defaultValue = "240") Integer height
			){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map=shopMsgCustcodeOrderService.sign(orderid, file, request, isScale, width, height);
		} catch (ShopMsgException e) {
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(map);
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param msgId 需求id
	 *@param dealerCode 经销商编码
	 *@Description:  经销商-抢单
	 */
	@ApiOperation(notes = "dealerRobtrade", httpMethod = "POST", value = "经销商-抢单", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerRobtrade", method = RequestMethod.POST)
	@SysLog(desc="经销商-抢单")
	public AjaxJson dealerRobtrade(String msgId,String dealerCode) throws ShopMsgException{
		//开启锁
		boolean lockMsgId = LockUtils.lock(msgId);
		try{
			if(lockMsgId){
				shopMsgService.dealerRobtrade(msgId,dealerCode);
			}
		}catch (ShopMsgException e){
			logger.error("/dealerRobtrade-经销商-抢单异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}finally {
			//释放锁
			LockUtils.unLock(msgId);
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param msgId 需求id
	 *@param dealerCode 经销商编码
	 *@Description:  经销商-选择承接方式
	 */
	@ApiOperation(notes = "dealerUnderTake", httpMethod = "POST", value = "经销商-选择承接方式", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "orderId", value = "经销商订单id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "undertake", value = "承接方式(0工程单,1零售)", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "managerNo", value = "海尔接口人", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "managerName", value = "海尔接口人名称", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerUnderTake", method = RequestMethod.POST)
	@SysLog(desc="经销商-选择承接方式")
	public AjaxJson dealerUnderTake(String msgId,String orderId,String dealerCode,String undertake,String managerNo,String managerName) throws ShopMsgException{
		try{
			shopMsgService.dealerUnderTake(msgId,orderId,dealerCode,undertake,managerNo,managerName);
		}catch (ShopMsgException e){
			logger.error("/dealerUnderTake-经销商-选择承接方式异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param msgId 需求id
	 *@param remarks 备注
	 *@param remarksperson 备注人
	 *@Description:  总监-提交备注
	 */
	@ApiOperation(notes = "directorRemarks", httpMethod = "POST", value = "总监-提交备注", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "remarks", value = "备注", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "remarksperson", value = "备注人", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/directorRemarks", method = RequestMethod.POST)
	@SysLog(desc="总监-提交备注")
	public AjaxJson directorRemarks(String msgId, String remarks, String remarksperson) throws ShopMsgException{
		try{
			shopMsgService.directorRemarks(msgId,remarks,remarksperson);
		}catch (ShopMsgException e){
			logger.error("/directorRemarks-总监-提交备注异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param msgId 需求id
	 *@param cancelFlag 废弃类型
	 *@param cancelreson 废弃原因
	 *@param canceldesc 废弃描述
	 *@param orderId 经销商订单id
	 *@Description:  经销商-作废零售订单
	 */
	@ApiOperation(notes = "dealerCancelOrder", httpMethod = "POST", value = "经销商-作废零售订单", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "cancelFlag", value = "废弃类型", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "cancelreson", value = "废弃原因", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "canceldesc", value = "废弃描述", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "orderId", value = "经销商订单id", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerCancelOrder", method = RequestMethod.POST)
	@SysLog(desc="经销商-作废零售订单")
	public AjaxJson dealerCancelOrder(String msgId,String cancelFlag,String cancelreson,String canceldesc,String orderId) throws ShopMsgException {
		try {
			shopMsgService.dealerCancelOrder(msgId, cancelFlag, cancelreson, canceldesc, orderId);
		} catch (ShopMsgException e) {
			logger.error("/dealerCancelOrder-经销商-作废订单,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param file 上传文件
	 *@param dealerCode 经销商编码
	 *@Description:  经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)
	 */
	@ApiOperation(notes = "dealerZykcUploadImg", httpMethod = "POST", value = "经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "file", value = "上传文件", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerZykcUploadImg", method = RequestMethod.POST)
	@SysLog(desc=" 经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)")
	public AjaxJson dealerZykcUploadImg(HttpServletRequest request,@ApiParam(name = "任意", value = "文件上传") MultipartFile file,String dealerCode) throws ShopMsgException {
		AjaxJson ret = null;
		AttInfo attInfo = null;
		try {
			 ret = uploadService.uploadFiles(request, null, Global.USER_TYPE_PROCESS, dealerCode);
			 if(ret.isSuccess()){
			 	 //存在
				 attInfo = (AttInfo)((List)ret.getBody().get("result")).get(0);
			 }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return AjaxJson.fail("/dealerZykcUploadImg- 经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)异常,原因:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return AjaxJson.fail("/dealerZykcUploadImg- 经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)异常,原因:" + e.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			return AjaxJson.fail("/dealerZykcUploadImg- 经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)异常,原因:" + e.getMessage());
		}
		return AjaxJson.ok(attInfo);
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param msgId 需求id
	 *@param urls 文件图片(多个以逗号隔开)
     *@param dealerCode 经销商编码
	 *@param orderId 经销商订单id
	 *@Description:  经销商-零售单上传见证性材料(图片url+交易金额)
	 */
	@ApiOperation(notes = "dealerZykcUpload", httpMethod = "POST", value = "零售单上传见证性材料(图片url+交易金额)", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "urls", value = "文件图片(多个以逗号隔开)", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "tradeCount", value = "交易金额", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerZykcUpload", method = RequestMethod.POST)
	@SysLog(desc="零售单上传见证性材料(图片url+交易金额)")
	public AjaxJson dealerZykcUpload(String msgId,String urls,String dealerCode,String orderId,String tradeCount) throws ShopMsgException {
		try{
			shopMsgService.dealerZykcUpload(msgId,urls, dealerCode,orderId, tradeCount);
		}catch (ShopMsgException e){
			logger.error("/dealerZykcUpload-经销商-零售单上传见证性材料(图片url+交易金额)异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));

	}


    /**
     *@Author: hdx
     *@CreateTime: 17:56 2019/10/8
     *@param msgId 需求id
     *@Description:  经销商-零售获取信息及之前上传的见证性材料
     */
    @ApiOperation(notes = "dealerZykcGetInfo", httpMethod = "POST", value = "经销商-零售获取信息及之前上传的见证性材料", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "经销商订单id", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/dealerZykcGetInfo", method = RequestMethod.POST)
    @SysLog(desc="经销商-零售获取信息及之前上传的见证性材料")
    public AjaxJson dealerZykcUpload(String msgId,String orderId) throws ShopMsgException {
        ShopMsgZykc smz = null;
        try{
            smz = shopMsgService.dealerZykcGetInfo(msgId,orderId);
        }catch (ShopMsgException e){
            logger.error("/dealerZykcGetInfo-经销商-零售获取信息及之前上传的见证性材料异常,原因:" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(smz);

    }







	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param msgId 需求id
	 *@Description:  获取需求对应的履历信息
	 */
	@ApiOperation(notes = "getMsgRecord", httpMethod = "POST", value = "获取需求对应的履历信息", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/getMsgRecord", method = RequestMethod.POST)
	@SysLog(desc="获取需求对应的履历信息")
	public AjaxJson getMsgRecord(String msgId) throws ShopMsgException {
		List<ShopMsgStatus> listShopMsgStatus = null;
		try {
			listShopMsgStatus = shopMsgService.getMsgRecord(msgId);
		} catch (ShopMsgException e) {
			logger.error("/getMsgRecord-获取需求对应的履历信息,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(listShopMsgStatus);
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@param dealerCode 需求id
	 *@Description:  根据经销商编码获取经销商信息
	 */
	@ApiOperation(notes = "getDealerDetails", httpMethod = "POST", value = "根据经销商编码获取经销商信息", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/getDealerDetails", method = RequestMethod.POST)
	@SysLog(desc="根据经销商编码获取经销商信息")
	public AjaxJson getDealerDetails(String dealerCode) throws ShopMsgException {
		Dealer dealer = null;
		try {
			dealer = shopMsgService.getDealerDetails(dealerCode);
		} catch (ShopMsgException e) {
			logger.error("/getDealerDetails-根据经销商编码获取经销商信息异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(dealer);
	}


    /**
     *@Author: hdx
     *@CreateTime: 17:56 2019/10/8
     *@Description:  获取企业购自定义产品相关信息
     */
    @ApiOperation(notes = "getMsgProduct", httpMethod = "GET", value = "获取企业购自定义产品相关信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getMsgProduct", method = RequestMethod.GET)
    @SysLog(desc="获取企业购自定义产品相关信息")
    public AjaxJson getMsgProduct() throws ShopMsgException {
        List<ProductCode.ProductCodeEntity> listProductCodeEntity = null;
        try {
            listProductCodeEntity = ProductCode.getProduct();
        } catch (Exception e) {
            logger.error("/getMsgProduct-获取需求对应的履历信息,原因:" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(listProductCodeEntity);
    }



	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@Description:  经销商-app物流签收接口(上传订单及签收凭证)
	 */
	@ApiOperation(notes = "dealerSign", httpMethod = "POST", value = "经销商-app物流签收接口(上传订单及签收凭证)", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "signUrl", value = "签收图片url(多个以逗号隔开)", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerSign", method = RequestMethod.POST)
	@SysLog(desc="经销商-app物流签收接口")
	public AjaxJson dealerSign(String orderId,String signUrl) throws ShopMsgException {
		try {
			shopMsgService.dealerSign(orderId,signUrl);
		} catch (Exception e) {
			logger.error("/dealerSign-经销商-app物流签收接口异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@Description:  经销商-app物流签收接口(上传图片返回上传信息)
	 */
	@ApiOperation(notes = "dealerSignUpload", httpMethod = "POST", value = "经销商-app物流签收接口(上传图片返回上传信息)", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
	})
	@RequestMapping(value = "/dealerSignUpload", method = RequestMethod.POST)
	@SysLog(desc="经销商-app物流签收接口(上传图片返回上传信息)")
	public AjaxJson dealerSignUpload(HttpServletRequest request,@ApiParam(name = "任意", value = "文件上传") MultipartFile file,String dealerCode) throws ShopMsgException {
		AjaxJson ret = null;
		AttInfo attInfo = null;
		try {
			ret = uploadService.uploadFiles(request, null, Global.USER_TYPE_PROCESS, dealerCode);
			if(ret.isSuccess()){
				//存在
				attInfo = (AttInfo)((List)ret.getBody().get("result")).get(0);
			}
		}catch (Exception e){
			e.printStackTrace();
			return AjaxJson.fail("/dealerSignUpload- 经销商-app物流签收接口(上传图片返回上传信息)异常,原因:" + e.getMessage());
		}
		return AjaxJson.ok(attInfo);
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@Description:  经销商-app物流出库接口
	 */
	@ApiOperation(notes = "dealerDeliver", httpMethod = "POST", value = "经销商-app物流签收接口", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "deliverNum", value = "出库单号", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerDeliver", method = RequestMethod.POST)
	@SysLog(desc="经销商-app物流出库接口")
	public AjaxJson dealerDeliver(String orderId,String deliverNum) throws ShopMsgException {
		try {
			shopMsgService.dealerDeliver(orderId,deliverNum);
		} catch (Exception e) {
			logger.error("/dealerDeliver-经销商-app物流出库接口异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:56 2019/10/8
	 *@Description:  经销商-app安装接口
	 */
	@ApiOperation(notes = "dealerInstall", httpMethod = "POST", value = "经销商-app安装接口", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "installman", value = "安装人", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerInstall", method = RequestMethod.POST)
	@SysLog(desc="经销商-app安装接口")
	public AjaxJson dealerInstall(String orderId,String installman) throws ShopMsgException {
		try {
			shopMsgService.dealerInstall(orderId,installman);
		} catch (Exception e) {
			logger.error("/dealerInstall-经销商-app安装接口异常,原因:" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 16:06 2019/9/25
	 *@param orgId 工贸id
	 *@Description: 行业总监根据中心获得中心下的经销商列表
	 */
	@ApiOperation(notes = "directorSelectFranchiserByOrgid", httpMethod = "POST", value = "行业总监根据中心获得中心下的经销商列表", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgId", value = "工贸id", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/directorSelectFranchiserByOrgid", method = RequestMethod.POST)
	@SysLog(desc="行业总监根据中心获得中心下的经销商列表")
	public AjaxJson directorSelectFranchiserByOrgid(String orgId) {
		List<Dealer> listDealer = null ;
		try {
			Dealer d = new Dealer();
			//设置工贸id
			d.setGmId(orgId);
			//设置是否可抢单
			d.setAllowDispatch(ProcessCode.YES.getLabel());
			listDealer = dealerService.findList(d);
		} catch (Exception e) {
			logger.error("/directorSelectFranchiserByOrgid 行业总监根据中心获得中心下的经销商列表异常，原因=" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(listDealer);
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 16:06 2019/9/25
	 *@param orgId 工贸id
	 *@Description: 获取总监信息
	 */
	@ApiOperation(notes = "getDirectorInfo", httpMethod = "POST", value = "获取总监信息", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgId", value = "工贸id", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/getDirectorInfo", method = RequestMethod.POST)
	@SysLog(desc="获取总监信息")
	public AjaxJson getDirectorInfo(String orgId) {
		if(StringUtils.isBlank(orgId)){
			return AjaxJson.fail("中心编码不能为空");
		}
		//声明Director用户
		User director = new User();
		//根据工贸查询总监相关信息
		List<User> listUser = UserUtils.getAboutUser(orgId);
		//找到总监(只可有一个总监)
		if(null==listUser || listUser.size()==0){
			logger.error(ShopMsgCode.DIRECT_NOTEXIST_ERROR.getValue());
		}
		//判断是否是总监权限
		for(User u:listUser){
			User full_user = UserUtils.getByLoginName(u.getLoginName());
			if(full_user!=null){
				List<Role> roleList = full_user.getRoleList();
				for(Role role:roleList){
					if(Constants.DIRECTOR_ROLE_ID.equals(role.getId())){
						//证明此是总监
						director = u;
					}
				}
			}
		}
		return AjaxJson.ok(director);
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 16:06 2019/9/25
	 *@param orderId 订单id
	 *@Description: 经销商-获取订单详情
	 */
	@ApiOperation(notes = "dealerGetOrderDetail", httpMethod = "POST", value = "经销商-获取订单详情", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerGetOrderDetail", method = RequestMethod.POST)
	@SysLog(desc="经销商-获取订单详情")
	public AjaxJson dealerGetOrderDetail(String orderId) {
		if(StringUtils.isBlank(orderId)){
			return AjaxJson.fail(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR.getDesc());
		}
		ShopMsg shopMsg = new ShopMsg();
		try {
			ShopMsgCustcodeOrder shopMsgCustcodeOrder = shopMsgCustcodeOrderService.get(orderId);
			if(shopMsgCustcodeOrder!=null){
				shopMsg = shopMsgService.get(shopMsgCustcodeOrder.getMsgId());
				if(shopMsg!=null){
					if(StringUtils.isBlank(shopMsg.getManagerNo())){
						shopMsg.setManagerNoFlag(false);
					}else{
						shopMsg.setManagerNoFlag(true);
					}
					shopMsg.setShopMsgCustcodeOrder(shopMsgCustcodeOrder);
					//获取零售单
                    ShopMsgZykc shopMsgZykc = new ShopMsgZykc();
                    shopMsgZykc.setOrderId(shopMsgCustcodeOrder.getId());
                    shopMsgZykc.setMsgId(shopMsg.getId());
                    List<ShopMsgZykc> listShopMsgZykc = shopMsgZykcService.findList(shopMsgZykc);
                    if(listShopMsgZykc!=null && listShopMsgZykc.size()>0){
                        shopMsg.setShopMsgZykc(listShopMsgZykc.get(0));
                    }
				}
			}
		} catch (Exception e) {
			logger.error("/dealerGetOrderDetail 经销商-获取订单详情异常，原因=" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok(shopMsg);
	}



    /**
     *@Author: hdx
     *@CreateTime: 16:06 2019/9/25
     *@param dealerCode 经销商编码
     *@Description: 根据88码获取经销商信息
     */
    @ApiOperation(notes = "getDealerDetail", httpMethod = "POST", value = "根据88码获取经销商信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/getDealerDetail", method = RequestMethod.POST)
    @SysLog(desc="根据88码获取经销商信息")
    public AjaxJson getDealerDetail(String dealerCode) {
        if(StringUtils.isBlank(dealerCode)){
            return AjaxJson.fail(ShopMsgCode.PARAM_DEALERNUMBER_ERROR.getDesc());
        }
        Dealer dealer = new Dealer();
        List<Dealer> listDealer = null ;
        try {
            Dealer d = new Dealer();
            d.setCompanyCode(dealerCode);
            listDealer = dealerService.findList(d);
            if(listDealer!=null && listDealer.size()>0){
                dealer = listDealer.get(0);
            }

        } catch (Exception e) {
            logger.error("/getDealerDetail 根据88码获取经销商信息异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(dealer);
    }


    /**
     * @param msgId 需求id
     * @Author: hdx
     * @CreateTime: 2019年11月6日 16:14:32
     * @Description: 获取大客户app评价信息
     */
    @ApiOperation(notes = "evaluateList", httpMethod = "POST", value = "获取大客户app评价信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/evaluateList/{msgId}", method = RequestMethod.GET)
    @SysLog(desc = "用户评价")
    @Validated
    public AjaxJson evaluateList(@PathVariable String msgId) {
        List<ShopMsgEvaluate> listShopMsgEvaluate = null;
        ShopMsgEvaluate shopMsgEvaluate = new ShopMsgEvaluate();
        try {
            listShopMsgEvaluate = shopMsgEvaluateService.getEvaluate(msgId);
            if(listShopMsgEvaluate!=null && listShopMsgEvaluate.size()>0){
                shopMsgEvaluate = listShopMsgEvaluate.get(0);
            }
        } catch (ShopMsgException e) {
            logger.error("/evaluateList 获取大客户app评价信息异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(shopMsgEvaluate);
    }


	/**
	 * @param msgId 需求id
	 * @Author: hdx
	 * @CreateTime: 2019年11月6日 16:14:32
	 * @Description: 经销商填写超期原因
	 */
	@ApiOperation(notes = "dealerUnderTakeTimeOut", httpMethod = "POST", value = "经销商填写超期原因", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单Id", required = true, paramType = "form", dataType = "String"),
	})
	@RequestMapping(value = "/dealerUnderTakeTimeOut", method = RequestMethod.POST)
	@SysLog(desc = "经销商填写超期原因")
	@Validated
	public AjaxJson dealerUnderTakeTimeOut(String orderId, String timeoutReason) {
		if(StringUtils.isBlank(orderId)){
			return AjaxJson.fail(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR.getDesc());
		}
		if(StringUtils.isBlank(timeoutReason)){
			return AjaxJson.fail(ShopMsgCode.PARAM_TIMEOUT_ERROR.getDesc());
		}
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = shopMsgCustcodeOrderService.get(orderId);
		if(shopMsgCustcodeOrder!=null){
			//超时理由
			shopMsgCustcodeOrder.setTimeoutReason(timeoutReason);
			//超时标识
			shopMsgCustcodeOrder.setTimeoutFlag(ProcessCode.YES.getLabel());
		}
		try {
			shopMsgCustcodeOrderService.save(shopMsgCustcodeOrder);
		} catch (Exception e) {
			logger.error("/dealerUnderTakeTimeOut 经销商填写超期原因异常，原因=" + e.getMessage());
			return AjaxJson.fail(e.getMessage());
		}
		return AjaxJson.ok("操作成功");
	}

}
