package com.jhmis.api.directpurchaser;


import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserAccountService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserService;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(value = "ApiDirectPurchaserSubAccountController", description = "采购商-账号管理")
@RestController
@RequestMapping("/api/directpurchaser/subaccount")
public class ApiDirectPurchaserSubAccountController {

    @Autowired
    private DirectPurchaserService directPurchaserService;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private DirectPurchaserAccountService directPurchaserAccountService;


    @ApiOperation(notes = "getSubAccountList",httpMethod = "POST",value = "子账号表页")
    @RequestMapping(value = "getSubAccountList")
    public AjaxJson getSubAccountList(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response) {
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }
        if(!"1".equals(account.getIsAdmin())){
            return AjaxJson.fail("该账号不是主账号");
        }

        purchaser.setId(account.getPurchaserId());
        Page<Purchaser> page = directPurchaserService.getSubRelPurchaser(new Page<Purchaser>(request, response), purchaser);

        return AjaxJson.layuiTable(page);
    }




    @ApiOperation(notes = "getAccount",httpMethod = "POST",value = "当前登录信息")
    @RequestMapping(value = "getAccount")
    public AjaxJson getAccount(HttpServletRequest request, HttpServletResponse response) {
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }

        Map<String,Object> map = new HashMap<String, Object>();
        Purchaser purchaser = purchaserService.get(account.getPurchaserId());
        if(purchaser != null){
            map.put("companyName",purchaser.getCompanyName());
            map.put("isAdminName",purchaser.getIsAdminName());
            map.put("logoUrl",purchaser.getLogoUrl());
            map.put("purchaser",purchaser);
        }
        map.put("account",account);

        return AjaxJson.ok(map);
    }
    
    @ApiOperation(notes = "getCreditBalance",httpMethod = "POST",value = "获取授信余额")
    @RequestMapping(value = "getCreditBalance")
    public AjaxJson getCreditBalance( HttpServletRequest request, HttpServletResponse response) {
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }
        if(!"1".equals(account.getIsAdmin())){
            return AjaxJson.fail("该账号不是主账号");
        }
        //获取公司信息
        Purchaser purchaser = purchaserService.get(account.getPurchaserId());
        double creditBalance = 0.00;
        try{
            creditBalance = directPurchaserAccountService.getCreditBalance(purchaser.getLoginNum());
            return AjaxJson.ok(creditBalance);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxJson.ok(creditBalance);
    }
    
    
    
    
}
