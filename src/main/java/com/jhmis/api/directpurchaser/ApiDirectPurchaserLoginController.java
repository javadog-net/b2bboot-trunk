package com.jhmis.api.directpurchaser;

import com.alibaba.fastjson.JSON;
import com.haier.http.HttpClientHelper;
import com.haier.mdm.qygcx.CustomerDateModel;
import com.haier.mdm.qygcx.CustomerInfoDateModel;
import com.haier.mdm.yhcx.BankDateModel;
import com.haier.mdm.yhcx.ProcessResponse;
import com.haier.user.api.UserApi;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.util.MDM_BANK;
import com.haier.util.MDM_QYG;
import com.jhmis.common.beanvalidator.RegValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MD5;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.service.UploadService;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Article;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.UploadResult;
import com.jhmis.modules.sys.utils.UserUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import com.haier.mdm.yhcx.ProcessResponse.BankTable.BankItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@Api(value = "ApiDirectPurchaserLoginController", description = "直采-登录注册")
@RequestMapping("/api/directpurchaser")
public class ApiDirectPurchaserLoginController extends BaseController {
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private PurchaserAccountService purchaserAccountService;
    @Autowired
    private PurchaserInvoiceService purchaserInvoiceService;
    @Autowired
    UploadService uploadService;
    /**
     * 企业购在客户中心的应用系统AppID
     */
    @Value("${Jsh.Client_Id}")
    protected String clientId;
    /**
     * 业购在客户中心的应用系统AppID的秘钥
     */
    @Value("${Jsh.Client_Secret}")
    protected String clientSecret;
    /**
     * 通过code获取客户中心access_token接口地址
     */
    @Value("${Jsh.TokenUrl}")
    protected String tokenUrl;
    /**
     * 通过access_token获取客户中心客户信息接口地址
     */
    @Value("${Jsh.AccountUrl}")
    protected String accountUrl;



    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(ApiDirectPurchaserLoginController.class);

    /**
     * 采购商注册
     * 直采类型采购商注册
     */
    @ApiOperation(notes = "/purchaserRegister", httpMethod = "POST", value = "采购商注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
//    		@ApiImplicitParam(name = "apptab", value = "apptab", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyNature", value = "公司性质", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "proviceId", value = "省份id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "城市id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "districtId", value = "区id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "proviceName", value = "省份名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "areaInfo", value = "省市区汉字", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityName", value = "城市", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "detailAddress", value = "详细地址", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyNum", value = "公司人数", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyTel", value = "公司电话", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "bankName", value = "开户银行", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "bankAccount", value = "开户账号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "bankAddress", value = "开户地址", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "licenceUrl", value = "营业执照(三证合一url)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "logoUrl", value = "公司LOGO", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "invitationCode", value = "邀请码", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "此次注册是新增还是修改   1新增   2修改", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "purchaseType", value = "直采类型", required = false, paramType = "form", dataType = "String")

    })
    @PostMapping("/purchaserRegister")
    public AjaxJson purchaserRegister(String mobile, String code, String userName, String email,String passwd,String proviceName,String cityName,String type,
                                      String companyName,String contacts,String companyNature,String areaInfo,String provinceId,String purchaseType,String vatregno,
                                      String detailAddress,String companyNum,String companyTel,String bankAccount,String bankAddress,String bankName,String bankKey,
                                      String licenceUrl,String logoUrl,String invitationCode,String proviceId,String cityId,String districtId,String regPhone,String regAddr){
        if(StringUtils.isBlank(mobile)){
            return AjaxJson.fail("手机号不能为空");
        }
        if(StringUtils.isNotBlank(mobile)){
            if(!RegValidators.isMobile(mobile)){
                return AjaxJson.fail("手机格式不正确");
            }
        }
    	/*if(StringUtils.isBlank(code)){
    		return AjaxJson.fail("短信验证码不能为空");
    	}*/
        if(StringUtils.isBlank(userName)){
            return AjaxJson.fail("用户名不能为空");
        }
        if(StringUtils.isNotBlank(email)){
            if(!RegValidators.isEmail(email)){
                return AjaxJson.fail("邮箱格式不正确");
            }
        }
        if(StringUtils.isBlank(passwd)){
            return AjaxJson.fail("密码不能为空");
        }
        if(StringUtils.isBlank(bankAccount)){
            return AjaxJson.fail("开户账号不能为空");
        }
        if(StringUtils.isBlank(bankAddress)){
            return AjaxJson.fail("开户地址不能为空");
        }
    	/*if(StringUtils.isBlank(licenceUrl)){
    		return AjaxJson.fail("三证合一url不能为空");
    	}*/
        //校验用户在本地数据库是否已经注册
        List<Purchaser> byMobile = purchaserService.findByMobile(mobile);
        if(byMobile != null && byMobile.size() > 0){
            return AjaxJson.fail("该手机号已被注册");
        }
        //校验用户是否已经注册了
        ExecuteResult<String> usernameResult = UserApi.userIsRegistered(companyName);
        if(!usernameResult.isSuccess()){
            return AjaxJson.fail("该账号已经注册了，请直接登录");
        }
        //根据公司名称查询是否已经注册
        boolean name = purchaserService.findByCompanyName(companyName);
        if(name){
            return AjaxJson.fail("该公司名称已被注册");
        }
        //插入采购商
        Purchaser purchaser = new Purchaser();
        PurchaserAccount account = new PurchaserAccount();
        PurchaserInvoice invoice = new PurchaserInvoice();
        purchaser.setMobile(mobile);
        purchaser.setEmail(email);
        purchaser.setCompanyName(companyName);
        purchaser.setContacts(contacts);
        purchaser.setCompanyNature(companyNature);
        purchaser.setDetailAddress(detailAddress);
        purchaser.setAreaInfo(areaInfo);
        purchaser.setBankKey(bankKey);
        purchaser.setBankAccount(bankAccount);
        purchaser.setBankName(bankName);
        purchaser.setProvinceId(provinceId);
        purchaser.setCityId(cityId);
        purchaser.setDistrictId(districtId);
        purchaser.setCompanyNum(companyNum);
        purchaser.setAuditState("0");
        if (StringUtils.isNotEmpty(companyTel)) {
            purchaser.setCompanyTel(companyTel);
        }
        purchaser.setBankAddress(bankAddress);
        if(StringUtils.isNotEmpty(licenceUrl)){
            purchaser.setLicenceUrl(licenceUrl);//没有      营业执照
        }
        purchaser.setLogoUrl(logoUrl);//没有     公司Logo
        purchaser.setInvitationCode(invitationCode);
        if (purchaseType.equals("1")) {
            //直采类型      1
            purchaser.setPurchaseType("1");
        }else{
            //采购商     0
            purchaser.setPurchaseType("0");
        }
        purchaser.setVatregno(vatregno);
        purchaser.setCityName(cityName);
        purchaser.setRegPhone(regPhone);
        purchaser.setRegAddr(regAddr);
        purchaser.setDelFlag(Article.DEL_FLAG_NORMAL);
//        Map map = MDM_ADD.organizationDate(purchaser,type);
//        if(map!=null){
//        	 if("S".equals(map.get("retcode"))){
//                 String loginNum = map.get("customcode").toString();
//                 purchaser.setLoginNum(loginNum);
        account.setLoginName(userName);
        account.setPasswd(MD5.MD5Encode(passwd));
        account.setIsAdmin("1");
        account.setCreateDate(new Date());
        account.setIsClosed("0");
        account.setMobile(mobile);
        account.setEmail(email);
        account.setDelFlag(Article.DEL_FLAG_NORMAL);
//                 account.setLoginNum(loginNum);   // 获取的88码
        invoice.setCompanyName(companyName);
        invoice.setRegAddr(regAddr);
        invoice.setRegPhone(regPhone);
        invoice.setRegBaccount(bankAccount);
        invoice.setRegBname(bankName);
        invoice.setTaxCode(vatregno);
        invoice.setKind("2");
        invoice.setRecName(contacts);
        invoice.setRecMobphone(mobile);
        invoice.setRecProvinceId(provinceId);
        invoice.setRecCityId(cityId);
        invoice.setRecDistrictId(districtId);
        invoice.setRecDetailAddr(detailAddress);
        invoice.setRecAreaInfo(areaInfo);
        try{
            logger.info("保存 purchsser");
            purchaserService.save(purchaser);
            account.setPurchaserId(purchaser.getId());
            invoice.setPurchaserId(purchaser.getId());
            logger.info("保存 account");
            purchaserAccountService.save(account);
            //保存发票信息
            logger.info("保存 发票信息");
            purchaserInvoiceService.save(invoice);

            return AjaxJson.ok("注册成功");
        }catch (Exception e){
            e.printStackTrace();

        }
        return AjaxJson.fail("注册失败");

//        	 }else{
//        		 return AjaxJson.fail(map.get("msg").toString());
//        	 }
//        }else{
//        	return AjaxJson.fail("MDM推送失败，保存失败");
//        }


    }


    /**
     * 获取银行详情
     * @param
     * @return
     */
    @ApiOperation(notes = "getBankInfo",httpMethod = "POST",value = "获取银行信息")
    @RequestMapping(value = "getBankInfo")
    @ApiImplicitParam(name = "code", value = "银行编号", required = false, paramType = "form", dataType = "String")
    public  AjaxJson getBankInfo(String code,HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isNotBlank(code)){
            Map map = MDM_BANK.Query(code, "", "", "", "");
            BankDateModel bm=new BankDateModel();

            List<ProcessResponse.BankTable.BankItem> item=(List)map.get("bank");
            if(item!=null && item.size()>0){
                BankItem bi=item.get(0);
                if(bi!=null){
                    bm.setBankName(bi.getBankName());
                    bm.setBankNum(bi.getBankBranchCode());
                    bm.setCountryType(bi.getBankCountry());
                }
            }
            return AjaxJson.ok(bm);
        }else{
            return AjaxJson.fail("银行编号为空");
        }
    }


    /**
     * 查询采购商是否存在，并且查询客户是否在3200建户
     * @param
     * @return
     */
    @ApiOperation(notes = "queryMDM",httpMethod = "POST",value = "查询采购商是否存在")
    @RequestMapping(value = "queryMDM")
    @ApiImplicitParam(name = "companyName", value = "公司名称", required = false, paramType = "form", dataType = "String")
    public  AjaxJson queryMDM(String companyName,HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isNotBlank(companyName)){
            //通过公司名称查询客户是否在MDM存在
            Map map88 = MDM_QYG.Query("S00763", "HAIERMDM", "HRHAIERBTB_CUST_INFO","CUSTOMER_NAME1",companyName,"=");
            String code = (String)map88.get("return_code");
            if("SUCCESS".equals(code)){
                //判断MDM是否在3200建户
                boolean isHave = false;
                Map map2 = MDM_QYG.readStringXmlOutInfo(map88.get("result").toString());
                List<CustomerInfoDateModel> list2 = (List)map2.get("date");
                CustomerInfoDateModel ci = null;
                if(list2 != null && list2.size()>0){
                    for(int i = 0;i < list2.size(); i++ ){
                        CustomerInfoDateModel ci1 = list2.get(i);
                        if(ci1 != null){
                            ci = ci1;
                        }
                    }
                }
                if(ci != null){
                    //判断客户是否在3200建户
                    Map map = MDM_QYG.Query("S00763", "HAIERMDM", "HRHAIERBTB_HM_CUSTOMERS","CUSTOMER_NUMBER",ci.getCUSTOMER_NUMBER(),"=");
                    String code1 = (String)map.get("return_code");
                    if("SUCCESS".equals(code1)){
                        Map map3 = MDM_QYG.readStringXmlOut(map.get("result").toString());
                        List<CustomerDateModel> list3 = (List)map3.get("date");
                        if(CollectionUtils.isNotEmpty(list3)){
                            isHave = true;
                        }
                    }
                }
                if(isHave){
                    PurchaserAccount purchaserAccount = purchaserAccountService.getByNumber(ci.getCUSTOMER_NUMBER());
                    if(purchaserAccount != null){
                        return AjaxJson.fail("您在本系统已经注册");
                    }

                }

                return AjaxJson.ok("可以注册",ci);
            }else{
                return AjaxJson.ok("可以注册",null);
            }
        }else{
            return AjaxJson.fail("公司名称为空");
        }
    }

    /**
     * 上传图片
     *
     * @param request
     * @param isScale
     * @param width
     * @param height
     * @return
     */
    @ApiOperation(notes = "uploadimage", httpMethod = "POST", value = "上传图片", consumes = "multipart/*")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isScale", value = "是否缩放（true,false)", defaultValue = "false", required = false, paramType = "form", dataType = "boolean"),
            @ApiImplicitParam(name = "width", value = "缩放宽度", defaultValue = "240", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "height", value = "缩放高度", defaultValue = "240", required = false, paramType = "form", dataType = "int")
    })
    @PostMapping(value = "uploadimage")
    public UploadResult uploadImage(HttpServletRequest request, @ApiParam(name = "任意", value = "图片上传") MultipartFile file, @RequestParam(defaultValue = "false") Boolean isScale, @RequestParam(defaultValue = "240") Integer width, @RequestParam(defaultValue = "240") Integer height) {
        UploadResult result = new UploadResult();
        try {
            AjaxJson ret = uploadService.uploadImages(request, null, UserUtils.getPrincipal().getUserType(), UserUtils.getPrincipal().getId(), isScale, width, height);
            if (ret.isSuccess()) {
                List<AttInfo> attInfos = (List<AttInfo>) ret.getResult();
                if (attInfos != null && attInfos.size() > 0) {
                    AttInfo info = attInfos.get(0);
                    result.setSuccess(true);
                    result.setState("SUCCESS");
                    result.setExt(info.getAttExt());
                    result.setTitle(info.getAttName());
                    result.setSize(info.getAttSize());
                    result.setUrl(info.getAttUrl());
                    result.setThumb(info.getAttThumb());
                    result.setOriginal(info.getAttName());
                } else {
                    result.setState("上传失败");
                    result.setSuccess(false);
                    result.setMessage("上传失败");
                }
            } else {
                result.setState(ret.getMsg());
                result.setSuccess(false);
                result.setMessage(ret.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(e.getMessage());
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    /**
     * 采购商登录
     * @param loginNum
     * @param password
     * @return
     */
    @ApiOperation(notes = "loginNumber", httpMethod = "POST", value = "采购商登录", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginNum", value = "88码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "passwd", value = "密码", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/loginNumber")
    public AjaxJson loginNumber( String  loginNum,String password){
        System.out.println("loginNum: "+loginNum);
        System.out.println("passwd: "+password);
        return purchaserAccountService.loginNumber(loginNum,password);
    }


    /**
     * 通过code获取客户中心登录
     * @param code   客户中心登录成功回调返回的code
     * @return
     */
    @ApiOperation(notes = "quickLogin", httpMethod = "POST", value = "通过code获取客户中心access_token", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "客户中心登录成功回调返回的code", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/quickLogin")
    public AjaxJson quickLogin(String  code){

        if(StringUtils.isBlank(code)){
            return AjaxJson.fail("参数为空");
        }
        logger.info("*_*_*_*_*_*_*_*_*_*quickLogin  code=" + code + "*_*_*_*_*_*_*_*_*_*");

//      https://c.jsh.com/open/oauth2/token?grant_type=authorization_code&code=CODE&client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET
//        String paramter = "grant_type=authorization_code&code="+ code +"&client_id="+ clientId +"&client_secret=" + clientSecret;
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("grant_type","authorization_code");
        paramsMap.put("code",code);
        paramsMap.put("client_id",clientId);
        paramsMap.put("client_secret",clientSecret);
        try {
            logger.info("*_*_*_*_*_*_*_*_*_*quickLogin  paramsMap=" + paramsMap + "*_*_*_*_*_*_*_*_*_*");
            //通过code获取access_token
            Object returnObject = HttpClientHelper.sendPost(tokenUrl,paramsMap);
            String returnStr = returnObject.toString();
            logger.info("*_*_*_*_*_*_*_*_*_*quickLogin  returnStr:" + returnStr + "*_*_*_*_*_*_*_*_*_*");
            if(StringUtils.isNotBlank(returnStr)){
                Map<String,Object> tokenMap = JSON.parseObject(returnStr,Map.class);
                if(tokenMap != null){
                    String access_token = tokenMap.get("access_token").toString();
                    logger.info("*_*_*_*_*_*_*_*_*_*quickLogin  access_token:" + access_token + "*_*_*_*_*_*_*_*_*_*");
                    if(StringUtils.isNotBlank(access_token)){
                        //通过access_token获取客户信息
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("access_token",access_token);
                        Object customerInfo = HttpClientHelper.sendGet(accountUrl,map);
                        String customerStr = customerInfo.toString();
                        logger.info("*_*_*_*_*_*_*_*_*_*quickLogin  customerStr:" + customerStr + "*_*_*_*_*_*_*_*_*_*");
//                        {"id":8700221225,"name":"8700014608","nickname":"","phoneNumber":"15892382355","phoneVerified":false,"sex":0,"headImgUrl":null,"address":null,"status":0,"email":"","emailVerified":false,"position":null,"empno":null,"createdAt":1572571253000,"updatedAt":1572571253000,"lastLoginAt":1572571253000,"customers":[{"id":"8700014608","isPrimary":true,"isSocial":false}]}
                        if(StringUtils.isNotBlank(customerStr)){
                            Map<String,Object> customerMap = JSON.parseObject(customerStr,Map.class);
                            if(customerMap != null){
                                String customersStr = customerMap.get("customers").toString();
                                List list = JSON.parseObject(customersStr,List.class);
                                if(CollectionUtils.isNotEmpty(list)){
                                    Map<String,Object> customersMap = JSON.parseObject(list.get(0).toString(),Map.class);
                                    if(customersMap != null){
                                        String id = customersMap.get("id").toString();
                                        if(StringUtils.isNotBlank(id)){
                                            return purchaserAccountService.quickLogin(id);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }else {
                return AjaxJson.fail("获取token接口返回失效");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.fail("接口错误");
    }


}
