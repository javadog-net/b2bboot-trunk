package com.jhmis.api.common;

import com.haier.http.client.eagleEye.EagleEyeApi;
import com.haier.webservices.client.hps.project.EnterpriseInfoVO;
import com.haier.webservices.client.hps.project.HpsApi;
import com.haier.webservices.client.hps.project.UserDTO;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.captcha.CaptchaDTO;
import com.jhmis.common.utils.captcha.CaptchaUtil;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.entity.RefundReason;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.IndustryService;
import com.jhmis.modules.shop.service.RefundReasonService;
import com.jhmis.modules.sys.entity.DictType;
import com.jhmis.modules.sys.entity.DictValue;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.service.DictTypeService;
import com.jhmis.modules.sys.service.GmInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(value = "ApiCommonController", description = "公共接口")
@RestController
@RequestMapping("/api/common")
public class ApiCommonController {
    protected Logger logger = LoggerFactory.getLogger(ApiCommonController.class);
    @Autowired
    GmInfoService gmInfoService;
    @Autowired
    IndustryService industryService;
    @Autowired
    DictTypeService dictTypeService;
    @Autowired
    RefundReasonService refundReasonService;
    @Autowired
    AreaCodeService areaCodeService;

    private static final String DOMAINMODEL = "CUSTOMER_ORDER";
    /**
     * 工贸列表
     * @return
     */
    @ApiOperation(notes = "gmlist", httpMethod = "POST", value = "工贸列表", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/gmlist")
    public AjaxJson gmList(){
        GmInfo info = new GmInfo();
        List<GmInfo> gmList = gmInfoService.findList(info);
        return AjaxJson.ok(gmList);
    }

    /**
     * 行业列表
     * @return
     */
    @ApiOperation(notes = "industrylist", httpMethod = "POST", value = "行业列表", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/industrylist")
    public AjaxJson industryList(){
        Industry industry = new Industry();
        List<Industry> industryList = industryService.findList(industry);
        return AjaxJson.ok(industryList);
    }

    /**
     * 获取验证码
     * @return
     */
    @ApiOperation(notes = "captcha", httpMethod = "POST", value = "获取验证码", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "width", value = "宽度", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "height", value = "高度", required = false, paramType = "form", dataType = "int")
    })
    @RequestMapping("/captcha")
    public AjaxJson captcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
        CaptchaDTO dto = CaptchaUtil.createImage(request,response);
        return AjaxJson.ok(dto);
    }

    /**
     * 获取字典值列表
     * @param dictType
     * @return
     */
    @ApiOperation(notes = "getDicValueList", httpMethod = "GET", value = "获取字典值列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "字典类型", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping( value = "/getDicValueList",method = RequestMethod.GET)
    public AjaxJson getDicValueList(DictType dictType){
        if(dictType == null || StringUtils.isEmpty(dictType.getType())){
            return AjaxJson.fail("参数错误");
        }
        dictType= dictTypeService.findUniqueByProperty("type", dictType.getType());
        if(dictType == null || StringUtils.isEmpty(dictType.getId())){
            return AjaxJson.fail("参数错误");
        }
        DictValue dictValue = new DictValue();
        dictValue.setDictType(dictType);
        List<DictValue> dictValueList = dictTypeService.findValueList(dictValue);
        return AjaxJson.layuiTable(dictValueList);
    }

    /**
     * 获取退款退货原因列表
     * @param refundType
     * @return
     */
    @ApiOperation(notes = "getReasonList", httpMethod = "GET", value = "获取退款退货原因列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refundType", value = "原因类型（1，退换；2，退货）", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping(value="getReasonList")
    public AjaxJson getReasonList(String refundType){
        if(StringUtils.isEmpty(refundType)){
            return AjaxJson.fail("原因类型不能为空");
        }
        RefundReason reason = new RefundReason ();
        reason.setRefundType(refundType);
        List<RefundReason> reasonList = refundReasonService.findList(reason);
        return AjaxJson.layuiTable(reasonList);
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
    public AjaxJson getAreaInfo(String areaId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCommonController  /getAreaInfo  获取省市区信息----------接口开始*_*_*_*_*_*_*_*_*_*");
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


//    /**
//     * 获取相关负责人信息(老)
//     * @param
//     * @return
//     */
//    @ApiOperation(notes = "queryProjectManager",  httpMethod = "POST", value = "获取hps负责人信息", consumes = "application/x-www-form-urlencoded")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "addressProvince", value = "省份id", required = true, paramType = "form", dataType = "String"),
//            @ApiImplicitParam(name = "addressCity", value = "地址id", required = true, paramType = "form", dataType = "String"),
//            @ApiImplicitParam(name = "addressCounty", value = "区域id", required = true, paramType = "form", dataType = "String"),
//            @ApiImplicitParam(name = "productGroup", value = "产品组多个按照逗号隔开", required = true, paramType = "form", dataType = "String")
//    })
//    @RequestMapping(value="queryProjectManager")
//    public AjaxJson queryProjectManager(String addressProvince,String addressCity,String addressCounty,String productGroup){
//        logger.info("*_*_*_*_*_*_*_*_*_* ApiCommonController  /queryProjectManager  获取hps负责人信息----------接口开始*_*_*_*_*_*_*_*_*_*");
//    if(StringUtils.isEmpty(addressProvince)||StringUtils.isEmpty(addressCity)||StringUtils.isEmpty(addressCounty)||StringUtils.isEmpty(productGroup)){
//        return AjaxJson.fail("参数错误");
//    }
//    //调取hps的webservices接口获取hps负责人信息queryEagleEye(客单为CUSTOMER_ORDER)
//    List<UserDTO> listUserDTO = HpsApi.queryProjectManagerFromHPS(addressProvince,addressCity,addressCounty,productGroup,DOMAINMODEL);
//    return AjaxJson.ok(listUserDTO);
//    }


    /**
     * 获取相关负责人信息
     * @param
     * @return
     */
    @ApiOperation(notes = "queryProjectManager",  httpMethod = "POST", value = "获取hps负责人信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectManagerCenter", value = "工贸中心编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "productGroup", value = "产品组多个按照逗号隔开", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "bigAndSmall", value = "商空产品大机小机", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping(value="queryProjectManager")
    public AjaxJson queryProjectManager(String projectManagerCenter,String productGroup,@RequestParam(value = "bigAndSmall", required = false)String bigAndSmall){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCommonController  /queryProjectManager  获取hps负责人信息----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(projectManagerCenter)||StringUtils.isEmpty(productGroup)){
            return AjaxJson.fail("参数错误");
        }
        //调取hps的webservices接口获取hps负责人信息queryEagleEye(客单为CUSTOMER_ORDER)
        List<UserDTO> listUserDTO = HpsApi.queryProjectManagerFromHPS(projectManagerCenter,productGroup,DOMAINMODEL,bigAndSmall);
        return AjaxJson.ok(listUserDTO);
    }



    /**
     * 通过鹰眼接口获取企业信息
     * @param
     * @return
     */
    @ApiOperation(notes = "queryEagleEye",  httpMethod = "POST", value = "通过鹰眼接口获取企业信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "skip", value = "分页参数", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping(value="queryEagleEye")
    public AjaxJson queryEagleEye(String companyName,String skip){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCommonController  /queryEagleEye  通过鹰眼接口获取企业信息----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(companyName)){
            return AjaxJson.fail("参数错误");
        }
        Map<String,Object> map =  EagleEyeApi.eagleEyeApi(companyName,skip);
        return AjaxJson.ok(map);
    }


    /**
     * 由鹰眼接口切换为hps接口
     * @param
     * @return
     */
    @ApiOperation(notes = "queryEagleEyeForHPS",  httpMethod = "POST", value = "由鹰眼接口切换为hps接口", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value="queryEagleEyeForHPS")
    public AjaxJson queryEagleEyeForHPS(String companyName){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCommonController  /queryEagleEyeForHPS  由鹰眼接口切换为hps接口----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(companyName)){
            return AjaxJson.fail("参数错误");
        }
        List<EnterpriseInfoVO> listEnterpriseInfoVO = null;
        try{
            listEnterpriseInfoVO =  HpsApi.queryEnterpriseList(companyName);
        }catch (Exception e){
            return AjaxJson.fail("hps接口异常，请联系相关人员");
        }
        return AjaxJson.ok(listEnterpriseInfoVO);
    }


    /**
     * 获取工贸列表
     * @param
     * @return
     */
        @ApiOperation(notes = "getGmList",  httpMethod = "GET", value = "获取工贸列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({

    })
    @RequestMapping(value="getGmList")
    public AjaxJson getGmList(){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCommonController  /getGmList  获取工贸列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        List<GmInfo> gmInfoList= gmInfoService.findList(new GmInfo());
        return AjaxJson.ok(gmInfoList);
    }
}
