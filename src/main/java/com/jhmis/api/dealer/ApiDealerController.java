package com.jhmis.api.dealer;

import com.jhmis.common.beanvalidator.BeanValidators;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.core.service.UploadService;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.StoreJoinin;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.StoreJoininService;
import com.jhmis.modules.shop.service.StoreService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.utils.UserUtils;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Api(value = "ApiDealerController", description = "供应商相关")
@RestController
@RequestMapping("/api/dealer")
public class ApiDealerController {
    @Autowired
    StoreJoininService storeJoininService;
    @Autowired
    StoreService storeService;
    @Autowired
    DealerService dealerService;
    @Autowired
    UploadService uploadService;
    /**
     * 申请开店
     * @return
     */
    @ApiOperation(notes = "joinin", httpMethod = "POST", value = "申请开店", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeName", value = "店铺名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "applyYear", value = "申请年限", required = true, paramType = "form", dataType = "Integer")
    })
    @RequiresRoles("dealer")
    @RequestMapping("/joinin")
    public AjaxJson joinin(StoreJoinin joinin){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        //参数验证
        BeanValidators.validator(joinin);
        //判断当前申请状态
        StoreJoinin entity = storeJoininService.getByDealerId(currentAccount.getDealerId());
        if(entity!=null){
            if(Global.AUDIT_STATE_WAIT.equals(entity.getAuditState())){
                return AjaxJson.fail("您的申请正在等待审批");
            }
            if(Global.AUDIT_STATE_OK.equals(entity.getAuditState())){
                return AjaxJson.fail("您的申请已经通过了，无需重复申请");
            }
            //使用原有的记录来修改
            joinin.setId(entity.getId());
            if(!joinin.getStoreName().equals(entity.getStoreName())){
                //判断店名是否重复
                StoreJoinin checker = storeJoininService.findUniqueByProperty("store_name", joinin.getStoreName());
                if (checker != null) {
                    return AjaxJson.fail("店名重复");
                }
            }
        } else {
            //判断店名是否重复
            StoreJoinin checker = storeJoininService.findUniqueByProperty("store_name", joinin.getStoreName());
            if (checker != null) {
                return AjaxJson.fail("店名重复");
            }
        }
        //检查现有店铺名是否重复
        Store store = storeService.findUniqueByProperty("store_name",joinin.getStoreName());
        if(store!=null){
            return AjaxJson.fail("店名重复");
        }
        joinin.setDealerId(currentAccount.getDealerId());
        joinin.setAuditState(Global.AUDIT_STATE_WAIT);
        joinin.setCreateById(currentAccount.getId());
        joinin.setUpdateById(currentAccount.getId());
        storeJoininService.save(joinin);
        return AjaxJson.ok();
    }

    /**
     * 申请开店状态查询
     * @return
     */
    @ApiOperation(notes = "joininstate", httpMethod = "POST", value = "申请开店状态查询(根据返回的auditState看审批状态 0待审批，1通过，2未通过)", consumes = "application/x-www-form-urlencoded")
    @RequiresRoles("dealer")
    @RequestMapping("/joininstate")
    public AjaxJson joininState(){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        StoreJoinin entity = storeJoininService.getByDealerId(currentAccount.getDealerId());
        if(entity==null){
            return AjaxJson.fail("未申请");
        }
        return AjaxJson.ok(entity);
    }

    /**
     * 查看供应商信息
     * @return
     */
    @ApiOperation(notes = "viewinfo", httpMethod = "GET", value = "查看供应商信息", consumes = "application/x-www-form-urlencoded")
    @RequiresRoles("dealer")
    @RequestMapping("/viewinfo")
    public AjaxJson vieweInfo(){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Dealer dealer = dealerService.get(currentAccount.getDealerId());
        return AjaxJson.ok(dealer);
    }

    /**
     * 完善供应商信息
     * @return
     */
    @ApiOperation(notes = "updateinfo", httpMethod = "POST", value = "完善供应商信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "provinceId", value = "省编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "市编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "districtId", value = "区编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "areaInfo", value = "省/市/区 格式文本", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "detailAddress", value = "详细地址", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "taxCode", value = "税码", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "kjtAccount", value = "快捷通账号", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "electronicUrl", value = "营业执照电子版", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "idCardUrl", value = "身份证电子版", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "legalPersonName", value = "法人姓名", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "legalPersonIdCard", value = "法人身份证书", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyTel", value = "公司电话", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "electronicLicense", value = "开户许可证电子版", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "logoUrl", value = "企业logo地址", required = false, paramType = "form", dataType = "String")
    })
    @RequiresRoles("dealer")
    @RequestMapping("/updateinfo")
    public AjaxJson updateInfo(Dealer dealer) throws Exception {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Dealer entity = dealerService.get(currentAccount.getDealerId());
        if(entity == null){
            return AjaxJson.fail("无此供应商信息");
        }
        //校验
        if(Global.AUDIT_STATE_WAIT.equals(entity.getAuditState())){
            return AjaxJson.fail("待审核状态不能修改");
        }
        //移除不允许修改的字段
        dealer.setAuditState(null);
        dealer.setCompanyCode(null);
        dealer.setCompanyName(null);
        dealer.setAuditDesc(null);
        dealer.setAuditDesc(null);
        dealer.setIsSelf(null);
        dealer.setIsStore(null);
        //正常还应该有公司的固定信息，不允许修改
        dealer.setId(currentAccount.getDealerId());
        MyBeanUtils.copyBeanNotNull2Bean(dealer,entity);
        entity.setAuditState(Global.AUDIT_STATE_WAIT);
        //参数验证
        BeanValidators.validator(entity);
        dealerService.save(entity);
        return AjaxJson.ok();
    }

    /**
     * 上传企业logo
     * @return
     */
    @ApiOperation(notes = "uploadLogo", httpMethod = "POST", value = "上传企业logo", consumes = "multipart/*")
    @RequiresRoles("dealer")
    @PostMapping(value= "/uploadlogo" , consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public AjaxJson uploadLogo(HttpServletRequest request,@ApiParam(name="任意",value="图片上传") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Dealer entity = dealerService.get(currentAccount.getDealerId());
        //
        AjaxJson ret = uploadService.uploadAvatars(request,null, Global.USER_TYPE_DEALER, UserUtils.getPrincipal().getId());
        List<AttInfo> attInfos = (List<AttInfo>) ret.getResult();
        if(attInfos!=null && attInfos.size()>0){
            entity.setLogoUrl(attInfos.get(0).getAttUrl());
            dealerService.save(entity);
        } else {
            return AjaxJson.fail("上传失败");
        }
        return AjaxJson.ok(entity.getLogoUrl());
    }


}
