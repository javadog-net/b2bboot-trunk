package com.jhmis.api.purchaser;

import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.beanvalidator.RegValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.captcha.CaptchaUtil;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "ApiPurchaserOrdersController", description = "采购商授权管理")
@RestController
@RequestMapping("/api/purchaser/auth")
public class ApiPurchaserAuthController {
    @Autowired
    private PurchaserAccountService purchaserAccountService;

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
        return purchaserAccountService.login(userName,password,false,"","");
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
        return purchaserAccountService.login(userName,password,true,captchaToken,captchaCode);
    }

    /** 
      * @Title: loginByPhoneCode 
      * @Description: TODO  验证码登录
      * @param userName
      * @param code
      * @return 
      * @return AjaxJson
      * @author tc
      * @date 2019年8月9日下午5:13:25
      */
    @ApiOperation(notes = "loginByPhoneCode", httpMethod = "POST", value = "登录带验证码", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "手机验证码", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/loginByPhoneCode")
    public AjaxJson loginByPhoneCode(String userName,String code){
        return purchaserAccountService.loginByPhoneCode(userName,code);
    }
    
    /**
     * 退出
     * @return
     */
    @ApiOperation(notes = "loginout", httpMethod = "POST", value = "退出", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/logout")
    public AjaxJson logout(){
        return purchaserAccountService.logout();
    }

    /**
     * 注册
     * @param userName
     * @param mobile
     * @param email
     * @param password
     * @param code
     * @return
     */
    @ApiOperation(notes = "register", httpMethod = "POST", value = "注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, paramType = "form", dataType = "String")
    })
    @PostMapping("/register")
    public AjaxJson register(String userName, String mobile, String email, String password, String code){
        if(StringUtils.isBlank(userName)){
            return AjaxJson.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(mobile)){
            return AjaxJson.fail("手机不能为空");
        }
        if(StringUtils.isNotBlank(mobile)){
            if(!RegValidators.isMobile(mobile)){
                return AjaxJson.fail("手机格式不正确");
            }
        }
        if(StringUtils.isNotBlank(email)){
            if(!RegValidators.isEmail(email)){
                return AjaxJson.fail("邮箱格式不正确");
            }
        }
        if(StringUtils.isBlank(code)){
            return AjaxJson.fail("验证码不能为空");
        }
        //校验验证码
        ExecuteResult<String> codeResult = UserApi.checkMobileCode(mobile,code);
        if(!codeResult.isSuccess()){
            return AjaxJson.fail("短信验证码错误");
        }
        //校验用户是否已经注册了
        ExecuteResult<String> usernameResult = UserApi.userIsRegistered(userName);
        if(!usernameResult.isSuccess()){
            return AjaxJson.fail("该账号已经注册了，请直接登录");
        }
        ExecuteResult<HaierUserDTO> result = UserApi.userRegister(userName, mobile, email, password, code);
        if(!result.isSuccess()){
            return AjaxJson.fail(result.getError(), UserApiErrorDesc.RegisterError.getDesc(result.getError()));
        }
        return AjaxJson.ok("注册成功");
    }

    /**
     * 获取短信验证码
     * @param mobile
     * @return
     */
    @ApiOperation(notes = "getMobileValidateCode", httpMethod = "POST", value = "获取短信验证码", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "captchaToken", value = "验证码token", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "captchaCode", value = "验证码(前台通过{host}/api/common/captcha?+new Date().getTime() 获取验证码)", required = true, paramType = "form", dataType = "String")
    })
    @PostMapping("/getMobileValidateCode")
    public AjaxJson getMobileValidateCode(String mobile,String captchaToken,String captchaCode){
        if (captchaToken == null) {
            return AjaxJson.fail("验证码token不能为空");
        }
        if (captchaCode == null) {
            return AjaxJson.fail("验证码不能为空");
        }
        if (!CaptchaUtil.valid(captchaToken,captchaCode)) {
            return AjaxJson.fail(Constants.ERROR_CODE_VALIDCODE_ERROR, Constants.ERROR_DESC_VALIDCODE_ERROR);
        }
        if(StringUtils.isBlank(mobile)){
            return AjaxJson.fail("手机不能为空");
        }
        if(!RegValidators.isMobile(mobile)){
            return AjaxJson.fail("手机格式不正确");
        }
        ExecuteResult<String> sendResult = UserApi.sendMobile(mobile);
        if(!sendResult.isSuccess()){
            return AjaxJson.fail("发送验证码失败");
        }
        return AjaxJson.ok();
    }
}
