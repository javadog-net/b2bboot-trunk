package com.jhmis.api.dealer;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "ApiPurchaserOrdersController", description = "供应商授权管理")
@RestController
@RequestMapping("/api/dealer/auth")
public class ApiDealerAuthController {
    @Autowired
    private DealerAccountService dealerAccountService;

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation(notes = "login", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/login")
    public AjaxJson login(String userName,String password){
        return dealerAccountService.login(userName,password,false,"","");
    }

    /**
     * 登录带验证码
     * @param userName
     * @param password
     * @param captchaToken
     * @param captchaCode
     * 验证码(前台通过{host}/api/common/captcha?+new Date().getTime() 获取验证码
     * @return
     */
    @ApiOperation(notes = "loginCaptcha", httpMethod = "POST", value = "登录带验证码", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "captchaToken", value = "验证码token", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "captchaCode", value = "验证码(前台通过{host}/api/common/captcha?+new Date().getTime() 获取验证码)", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/loginCaptcha")
    public AjaxJson login(String userName,String password,String captchaToken,String captchaCode){
        return dealerAccountService.login(userName,password,true,captchaToken,captchaCode);
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation(notes = "loginout", httpMethod = "POST", value = "退出", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/logout")
    public AjaxJson logout(){
        return dealerAccountService.logout();
    }

}
