package com.jhmis.api.purchaser;

import com.jhmis.common.beanvalidator.BeanValidators;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.UploadService;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.shop.utils.PurchaserUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Api(value = "ApiPurchaserController", description = "采购商相关")
@RestController
@RequestMapping("/api/purchaser")
public class ApiPurchaserController {
    /**
     * 完善采购商信息
     * @return
     */
    @ApiOperation(notes = "updateinfo", httpMethod = "POST", value = "完善采购商信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "provinceId", value = "省编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "市编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "districtId", value = "区编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "areaInfo", value = "省/市/区 格式文本", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "detailAddress", value = "详细地址", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyName", value = "公司名称", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyNum", value = "公司人数", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "电话", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyNature", value = "公司性质", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "logoUrl", value = "企业logo地址", required = false, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/updateinfo")
    public AjaxJson updateInfo(Purchaser purchaser) throws Exception {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Purchaser entity = purchaserService.get(currentAccount.getPurchaserId());
        if(entity == null){
            return AjaxJson.fail("无此采购商信息");
        }
        //校验
        if(Global.AUDIT_STATE_WAIT.equals(entity.getAuditState())){
            return AjaxJson.fail("待审核状态不能修改");
        }
        //移除不允许修改的字段
        purchaser.setAuditState(null);
        //purchaser.setCompanyCode(null);
        //purchaser.setCompanyName(null);
        purchaser.setAuditDesc(null);
      
        //正常还应该有公司的固定信息，不允许修改
        purchaser.setId(currentAccount.getPurchaserId());
        MyBeanUtils.copyBeanNotNull2Bean(purchaser,entity);
        entity.setAuditState(Global.AUDIT_STATE_WAIT);
        //参数验证
        BeanValidators.validator(entity);
        purchaserService.save(entity);
        return AjaxJson.ok();
    }
    @Autowired
    PurchaserService purchaserService;

    @Autowired
    UploadService uploadService;

    /**
     * 查看采购商信息
     * @return
     */
    @ApiOperation(notes = "viewinfo", httpMethod = "GET", value = "查看采购商信息", consumes = "application/x-www-form-urlencoded")
    @RequiresRoles("purchaser")
    @RequestMapping("/viewinfo")
    public AjaxJson vieweInfo(){
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Purchaser purchaser = purchaserService.get(currentAccount.getPurchaserId());
        return AjaxJson.ok(purchaser);
    }

    /**
     * 上传企业logo
     * @return
     */
    @ApiOperation(notes = "uploadLogo", httpMethod = "POST", value = "上传企业logo", consumes = "multipart/*")
    @RequiresRoles("purchaser")
    @PostMapping(value= "/uploadlogo" , consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public AjaxJson uploadLogo(HttpServletRequest request,@ApiParam(name="任意",value="图片上传") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Purchaser entity = purchaserService.get(currentAccount.getPurchaserId());
        //
        AjaxJson ret = uploadService.uploadAvatars(request,null, Global.USER_TYPE_PURCHASER, UserUtils.getPrincipal().getId());
        List<AttInfo> attInfos = (List<AttInfo>) ret.getResult();
        if(attInfos!=null && attInfos.size()>0){
            entity.setLogoUrl(attInfos.get(0).getAttUrl());
            purchaserService.save(entity);
        } else {
            return AjaxJson.fail("上传失败");
        }
        return AjaxJson.ok(entity.getLogoUrl());
    }

    @ApiOperation(notes = "getRelatedPurchaser", httpMethod = "POST", value = "获取经销商订单关联的采购商", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "companyName", value = "公司名称", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "电话", required = false, paramType = "form", dataType = "String")
    })
    @RequiresRoles("dealer")
    @RequestMapping("/getRelatedPurchaser")
    public AjaxJson getRelatedPurchaser(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if (purchaser == null) {
            purchaser = new Purchaser();
        }
        purchaser.setId(currentAccount.getDealerId());
        //分页写法
        Page<Purchaser> page = purchaserService.findPage(new Page<Purchaser>(request, response), purchaser);
        return AjaxJson.layuiTable(page);
    }
    
    @ApiOperation(notes = "test", httpMethod = "POST", value = "获取经销商订单关联的采购商", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "test", value = "页码", required = false, paramType = "form", dataType = "String")

    })

    @RequestMapping("/test")
    public AjaxJson test(String test) {
        System.out.println(test);
        return null;

    }

    
    
}
