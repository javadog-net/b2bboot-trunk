package com.jhmis.api.process;

import com.haier.webservices.client.hps.project.EnterpriseInfoVO;
import com.haier.webservices.client.hps.project.HpsApi;
import com.jhmis.common.Enum.*;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MsgUtils;
import com.jhmis.common.utils.lock.LockUtils;
import com.jhmis.modules.process.entity.shopevaluate.ShopMsgEvaluate;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.service.shopevaluate.ShopMsgEvaluateService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.service.GmInfoService;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.entity.WxMeetingFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.process
 * @Author: hdx
 * @CreateTime: 2019-08-30 17:32
 * @Description: 企业购内部使用接口
 */
@Api(value = "ApiProcessB2bController", description = "企业购内部使用接口")
@RestController
@RequestMapping("/api/process/b2b")
public class ApiProcessB2bController {

    Logger log = LoggerFactory.getLogger(ApiProcessB2bController.class);
    @Autowired
    ShopMsgService shopMsgService;

    @Autowired
    MsgUtils msgUtils;

    @Autowired
    AreaCodeService areaCodeService;

    @Autowired
    GmInfoService gmInfoService;

    @Autowired
    ShopMsgEvaluateService shopMsgEvaluateService;

    /**
     * @Author: hdx
     * @CreateTime: 17:35 2019/8/30
     * @param: * @param shopMsg实体
     * @Description: 发布需求接口
     */
    @RequestMapping(value = "postwant")
    @SysLog(desc = "发布需求接口")
    public AjaxJson postwant(ShopMsg shopMsg, HttpServletRequest request, HttpServletResponse response) {
        //发布需求
        try {
            //若设置需求来源为空则(从枚举取值16-海尔b2b,一期官网确定是海尔b2b)
            if(StringUtils.isBlank(shopMsg.getChannel())){
                shopMsg.setChannel(MsgChannelCode.HAIER_B2B.getIndex());
            }
            //默认用户部门-采购
            shopMsg.setDepart(DepartCode.DEPART_PURCHASE.getLabel());
            //派单标识默认为未派-0
            shopMsg.setIsDispatch(ProcessCode.NO.getLabel());
            //分类0需求
            shopMsg.setCategory(ProcessCode.MSG_CATEGORY_ENQUIRY.getLabel());
            shopMsgService.addShopMsg(shopMsg);
        } catch (ShopMsgException e) {
            log.error(e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        //是否自注册
        try {
            shopMsgService.transMember(shopMsg);
        } catch (ShopMsgException e) {
            log.error("/postwant 发布需求接口自注册异常，原因=" + e.getMessage());
        }
        return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_001"));
    }


    /**
     * @param msgId 需求id
     * @Author: hdx
     * @CreateTime: 16:06 2019/9/25
     * @Description: 根据需求id获取需求详情信息
     */
    @ApiOperation(notes = "getShopMsgDetail", httpMethod = "POST", value = "根据需求id获取需求详情信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/getShopMsgDetail", method = RequestMethod.POST)
    @SysLog(desc = "根据需求id获取需求详情信息")
    public AjaxJson getShopMsgDetail(String msgId) {
        ShopMsg shopMsg = null;
        try {
            shopMsg = shopMsgService.getShopMsgDetail(msgId);
        } catch (ShopMsgException e) {
            log.error("/getShopMsgDetail 根据需求id获取需求详情信息异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(shopMsg);
    }


    /**
     * @param mobile 手机号
     * @Author: hdx
     * @CreateTime: 16:06 2019/9/25
     * @Description: 用户获取对应的需求列表
     */
    @ApiOperation(notes = "purchaserGetMsgList", httpMethod = "POST", value = "用户获取对应的需求列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "category", value = "分类", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/purchaserGetMsgList", method = RequestMethod.POST)
    @SysLog(desc = "用户获取对应的需求列表")
    public AjaxJson purchaserGetMsgList(String mobile, String category, String nodetag) {
        List<ShopMsg> listShopMsg = null;
        try {
            listShopMsg = shopMsgService.purchaserGetMsgList(mobile, category, nodetag);
        } catch (ShopMsgException e) {
            log.error("/purchaserGetMsgList 用户获取对应的需求列表异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(listShopMsg);
    }


    /**
     * 获取省市区信息
     *
     * @param areaId
     * @return
     */
    @ApiOperation(notes = "getAreaInfo", httpMethod = "GET", value = "获取省市区信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaId", value = "地区id(如果不传则取省份)", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping(value = "getAreaInfo")
    @SysLog(desc = "获取省市区信息")
    public AjaxJson getAreaInfo(String areaId) {
        //如果
        AreaCode areaCode = new AreaCode();
        if (StringUtils.isEmpty(areaId)) {
            areaCode.setParentId("0");
            List<AreaCode> areaCodeList = areaCodeService.findList(areaCode);
            if (areaCodeList == null || areaCodeList.size() == 0) {
                return AjaxJson.fail("没有查到相关地区");
            }
            return AjaxJson.ok(areaCodeList);
        }
        //如果不为空则查询子集
        areaCode.setParentId(areaId);
        List<AreaCode> areaCodeList = areaCodeService.findList(areaCode);
        if (areaCodeList == null || areaCodeList.size() == 0) {
            return AjaxJson.fail("没有查到相关地区");
        }
        return AjaxJson.ok(areaCodeList);
    }


    /**
     * @return AjaxJson
     * @Title: selectGmInfo
     * @Description: 查询全国工贸信息
     * @author tc
     * @date 2019年9月9日下午5:04:05
     */
    @ApiOperation(notes = "selectGmInfo", httpMethod = "GET", value = "查询全国工贸信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/selectGmInfo")
    @SysLog(desc = "查询全国工贸信息")
    public AjaxJson selectGmInfo() {
        List<GmInfo> gmInfoList = gmInfoService.findList(new GmInfo());
        return AjaxJson.ok(gmInfoList);
    }

    /**
     * @return AjaxJson
     * @Title: selectGmInfo
     * @Description: 获取公司鹰眼接口
     * @author tc
     * @date 2019年9月9日下午5:04:05
     */
    @ApiOperation(notes = "queryEnterpriseList", httpMethod = "GET", value = "获取公司鹰眼接口", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/queryEnterpriseList")
    @SysLog(desc = "获取公司鹰眼接口")
    public AjaxJson queryEnterpriseList(String companyName) {
        log.info("*_*_*_*_*_*_*_*_*_*   获取省市区信息----------接口开始*_*_*_*_*_*_*_*_*_*");
        List<EnterpriseInfoVO> listEnterpriseInfoVO = null;
        try {
            listEnterpriseInfoVO = HpsApi.queryEnterpriseList(companyName);
        } catch (Exception e) {
            return AjaxJson.fail("hps接口异常,请联系相关人员处理");
        }
        return AjaxJson.ok(listEnterpriseInfoVO);
    }


    /**
     * @param shopMsgEvaluate 评价信息
     * @Author: hdx
     * @CreateTime: 2019年11月6日 16:14:32
     * @Description: 用户评价
     */
    @ApiOperation(notes = "evaluateAdd", httpMethod = "POST", value = "用户评价", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "shopMsgEvaluate", value = "ping实体", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/evaluateAdd", method = RequestMethod.POST)
    @SysLog(desc = "用户评价")
    public AjaxJson evaluateAdd(ShopMsgEvaluate shopMsgEvaluate) {
        List<ShopMsg> listShopMsg = null;
        try {
            shopMsgEvaluateService.evaluateAdd(shopMsgEvaluate);
        } catch (ShopMsgException e) {
            log.error("/purchaserGetMsgList 用户评价异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));
    }

    /**
     * @param msgId 需求id
     * @Author: hdx
     * @CreateTime: 2019年11月6日 16:14:32
     * @Description: 获取用户所有评价信息
     */
    @ApiOperation(notes = "evaluateList", httpMethod = "POST", value = "获取用户所有评价信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msgId", value = "需求id", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/evaluateList/{msgId}", method = RequestMethod.GET)
    @SysLog(desc = "用户评价")
    @Validated
    public AjaxJson evaluateList(@PathVariable String msgId) {
        List<ShopMsgEvaluate> listShopMsgEvaluate = null;
        try {
            listShopMsgEvaluate = shopMsgEvaluateService.evaluateList(msgId);
        } catch (ShopMsgException e) {
            log.error("/evaluateList 获取用户所有评价信息异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(listShopMsgEvaluate);
    }


    /**
     * @Author: hdx
     * @CreateTime: 17:56 2019/10/8
     * @Description: 获取企业购自定义产品相关信息
     */
    @ApiOperation(notes = "getMsgProduct", httpMethod = "GET", value = "获取企业购自定义产品相关信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getMsgProduct", method = RequestMethod.GET)
    @SysLog(desc = "获取企业购自定义产品相关信息")
    public AjaxJson getMsgProduct() throws ShopMsgException {
        List<ProductCode.ProductCodeEntity> listProductCodeEntity = null;
        try {
            listProductCodeEntity = ProductCode.getProduct();
        } catch (Exception e) {
            log.error("/getMsgProduct-获取需求对应的履历信息,原因:" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(listProductCodeEntity);
    }


    /**
     * @param
     * @Author: hdx
     * @CreateTime: 16:06 2019/9/25
     * @Description: 根据采购商手机号获取需求数量集合
     */
    @ApiOperation(notes = "getShopMsgListCount", httpMethod = "POST", value = "根据采购商手机号获取需求数量集合", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/getShopMsgListCount", method = RequestMethod.POST)
    @SysLog(desc = "根据需求id获取需求详情信息")
    public AjaxJson getShopMsgListCount(String mobile) {
        List<Integer> msgListCount = new ArrayList<>();
        try {
            msgListCount = shopMsgService.getShopMsgListCount(mobile);
        } catch (ShopMsgException e) {
            log.error("/getShopMsgListCount 根据需求id获取需求详情信息异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(msgListCount);
    }
}
