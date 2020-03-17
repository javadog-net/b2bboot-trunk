/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.wechat;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.beanvalidator.RegValidators;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 注册相关Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/wechat/register")
public class ApiRegisterController extends BaseController {
	/**
	 * 获取短信验证码
	 * @param mobile
	 * @return
	 */
	@ApiOperation(notes = "getMobileValidateCodeByWx", httpMethod = "POST", value = "获取短信验证码", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String")
	})
	@PostMapping("/getMobileValidateCodeByWx")
	public AjaxJson getMobileValidateCodeByWx(String mobile){
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

    /**
     * 验证手机验证码是否正确
     * @param mobilephone
     * @param mobileCode
     * @return
     */
    @ApiOperation(notes = "checkMobileCodeByWx", httpMethod = "POST", value = "获取短信验证码", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobilephone", value = "手机", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobileCode", value = "验证码", required = true, paramType = "form", dataType = "String")
    })
    @PostMapping(value="/checkMobileCodeByWx")
    public AjaxJson checkMobileCode(String mobilephone,String mobileCode, HttpServletResponse response){
        ExecuteResult<String> result = UserApi.checkMobileCode(mobilephone,mobileCode);
        if(!result.isSuccess()){
            return AjaxJson.fail("验证码不正确");
        }
        return AjaxJson.ok();
    }
    /**
     * 判断用户是否已经注册
     * @param mobilephone
     * @return
     */

    @ApiOperation(notes = "userIsRegisteredByWx", httpMethod = "POST", value = "判断用户是否已经注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobilephone", value = "手机", required = true, paramType = "form", dataType = "String"),
    })
    @PostMapping(value="/userIsRegisteredByWx")
    public AjaxJson userIsRegistered(String mobilephone, HttpServletResponse response){
        ExecuteResult<String> result = UserApi.userIsRegistered(mobilephone);
        if(!result.isSuccess()){
            return AjaxJson.fail("此账号已注册");
        }
        return AjaxJson.ok();
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
    @Autowired
	private PurchaserAccountService purchaserAccountService;
    @Autowired
    PurchaserService purchaserService;

    @ApiOperation(notes = "registerByWx", httpMethod = "POST", value = "注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    		@ApiImplicitParam(name = "openid", value = "openid", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "code", value = "短信验证码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "proviceId", value = "省份id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "proviceName", value = "省份名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "城市id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityName", value = "城市名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyTel", value = "公司电话", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "depart", value = "所在部门", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "licenceUrl", value = "三证合一url", required = false, paramType = "form", dataType = "String")
    })
    @PostMapping("/registerByWx")
    public AjaxJson register(String userName, String mobile, String email, String password, String code,
    		              String proviceId,String proviceName,String cityId,String cityName,String companyTel,
    		              String companyName,String openid,String depart,String licenceUrl){

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
        HaierUserDTO haierUser = result.getResult();
        //用户中心验证通过了
        //PurchaserAccount account = this.getByName(userName);
            //插入新用户和采购商
            Purchaser purchaser = new Purchaser();
            PurchaserAccount account = new PurchaserAccount();
            //暂时把公司编码设置为用户的登录账号
            purchaser.setCompanyCode(userName);
            purchaser.setCompanyName(companyName);
            purchaser.setMobile(haierUser.getMobile());
            purchaser.setEmail(email);
            purchaser.setAuditState(Global.AUDIT_STATE_UNSUBMIT);//待完善状态
            purchaser.setProvinceId(proviceId);
            purchaser.setCreateDate(new Date());
            purchaser.setCityId(cityId);
            purchaser.setDepart(depart);
            purchaser.setChannel("vx");
            if(StringUtils.isNotEmpty(companyTel)){
            	purchaser.setCompanyTel(companyTel);
            }
            if(StringUtils.isNotEmpty(licenceUrl)){
            	purchaser.setLicenceUrl(licenceUrl);
            }
            String areaInfo=proviceName+"/"+cityName;
            purchaser.setAreaInfo(areaInfo);
            purchaserService.save(purchaser);
            account.setPurchaserId(purchaser.getId());
            account.setLoginName(userName);
            account.setRealName(userName);
            account.setPasswd(password);
            account.setMobile(mobile);
            account.setCreateDate(new Date());
            account.setDepartName(depart);
            account.setIsAdmin("1");
            account.setIsClosed("0");
            account.setEmail(email);
            account.setHaierUserId(String.valueOf(haierUser.getUserId()));
            purchaserAccountService.save(account);
            return AjaxJson.ok("注册成功");
    }


}