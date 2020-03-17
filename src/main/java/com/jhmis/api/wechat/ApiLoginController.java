/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.beanvalidator.RegValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录相关Controller
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/login")
public class ApiLoginController extends BaseController {


    @Autowired
    private PurchaserAccountService purchaserAccountService;
    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation(notes = "loginByWx", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "openid", value = "微信openid", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/loginByWx")
    public AjaxJson login(String userName,String password,String openid){
        return purchaserAccountService.loginByWx(userName,password,openid);
    }
}