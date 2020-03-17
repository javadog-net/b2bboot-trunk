/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.mobiles;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>
 * Title: MobilesApiRegisterController
 * </p>
 * <p>
 * Description:
 * </p>
 * 注册相关Controller 提供给 移动端
 * 
 * @author tc
 * @date 2019年10月28日 上午10:51:42
 */
@RestController
@RequestMapping("/api/mobiles/register")
public class MobilesApiRegisterController extends BaseController {
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private PurchaserAccountService purchaserAccountService;

	/**
	 * 获取短信验证码
	 * 
	 * @param mobile
	 * @return
	 */
	@ApiOperation(notes = "getMobileValidateCodeByMobiles", httpMethod = "POST", value = "获取短信验证码", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String") })
	@PostMapping("/getMobileValidateCodeByMobiles")
	public AjaxJson getMobileValidateCode(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return AjaxJson.fail("手机不能为空");
		}
		if (!RegValidators.isMobile(mobile)) {
			return AjaxJson.fail("手机格式不正确");
		}
		ExecuteResult<String> sendResult = UserApi.sendMobile(mobile);
		if (!sendResult.isSuccess()) {
			return AjaxJson.fail("发送验证码失败");
		}
		return AjaxJson.ok();
	}

	/**
	 * 验证手机验证码是否正确
	 * 
	 * @param mobilephone
	 * @param mobileCode
	 * @return
	 */
	@ApiOperation(notes = "checkMobileValidateCodeByMobiles", httpMethod = "POST", value = "获取短信验证码", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mobilephone", value = "手机", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "mobileCode", value = "验证码", required = true, paramType = "form", dataType = "String") })
	@PostMapping(value = "/checkMobileValidateCodeByMobiles")
	public AjaxJson checkMobileValidateCode(String mobilephone, String mobileCode) {
		ExecuteResult<String> result = UserApi.checkMobileCode(mobilephone, mobileCode);
		if (!result.isSuccess()) {
			return AjaxJson.fail("验证码不正确");
		}
		return AjaxJson.ok();
	}

	/**
	 * 判断用户是否已经注册
	 * 
	 * @param mobilephone
	 * @return
	 */

	@ApiOperation(notes = "userIsRegisteredByMobiles", httpMethod = "POST", value = "判断用户是否已经注册", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mobilephone", value = "手机", required = true, paramType = "form", dataType = "String"), })
	@PostMapping(value = "/userIsRegisteredByMobiles")
	public AjaxJson userIsRegistered(String mobilephone) {
		if (StringUtils.isBlank(mobilephone)) {
			return AjaxJson.fail("手机不能为空");
		}
		if (!RegValidators.isMobile(mobilephone)) {
			return AjaxJson.fail("手机格式不正确");
		}
		ExecuteResult<String> result = UserApi.userIsRegistered(mobilephone);
		if (!result.isSuccess()) {
			return AjaxJson.fail("此账号已注册");
		}
		return AjaxJson.ok();
	}

	/**
	 * 
	 * @Title: registerByApp
	 * @Description: TODO 简化注册
	 * @param userName
	 * @param mobile
	 * @param email
	 * @param password
	 * @param code
	 * @param mobilestab 注册来源标识 移动端
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年10月28日上午10:53:22
	 */
	@ApiOperation(notes = "registerByMobiles", httpMethod = "POST", value = "注册", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mobilestab", value = "mobilestab", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "短信验证码", required = true, paramType = "form", dataType = "String"), })
	@PostMapping("/registerByMobiles")
	public AjaxJson registerByMobiles(String userName, String mobile, String email, String password, String code,
			String mobilestab) {

		if (StringUtils.isBlank(userName)) {
			return AjaxJson.fail("用户名不能为空");
		}
		if (StringUtils.isBlank(mobile)) {
			return AjaxJson.fail("手机不能为空");
		}
		if (StringUtils.isNotBlank(mobile)) {
			if (!RegValidators.isMobile(mobile)) {
				return AjaxJson.fail("手机格式不正确");
			}
		}
		if (StringUtils.isNotBlank(email)) {
			if (!RegValidators.isEmail(email)) {
				return AjaxJson.fail("邮箱格式不正确");
			}
		}
		if (StringUtils.isBlank(code)) {
			return AjaxJson.fail("验证码不能为空");
		}
		// 校验验证码
		ExecuteResult<String> codeResult = UserApi.checkMobileCode(mobile, code);
		if (!codeResult.isSuccess()) {
			return AjaxJson.fail("短信验证码错误");
		}
		// 校验用户是否已经注册了
		ExecuteResult<String> usernameResult = UserApi.userIsRegistered(userName);
		if (!usernameResult.isSuccess()) {
			return AjaxJson.fail("该账号已经注册了，请直接登录");
		}
		ExecuteResult<HaierUserDTO> result = UserApi.userRegister(userName, mobile, email, password, code);

		if (!result.isSuccess()) {
			return AjaxJson.fail(result.getError(), UserApiErrorDesc.RegisterError.getDesc(result.getError()));
		}
		HaierUserDTO haierUser = result.getResult();
		// 用户中心验证通过了
		// PurchaserAccount account = this.getByName(userName);
		// 插入新用户和采购商
		Purchaser purchaser = new Purchaser();
		PurchaserAccount account = new PurchaserAccount();
		purchaser.setMobile(haierUser.getMobile());
		purchaser.setEmail(email);
		purchaser.setCreateDate(new Date());
		purchaser.setAuditState(Global.AUDIT_STATE_UNSUBMIT);// 待完善状态
		purchaser.setChannel(mobilestab);
		purchaserService.save(purchaser);
		account.setPurchaserId(purchaser.getId());
		account.setLoginName(userName);
		account.setCreateDate(new Date());
		account.setRealName(userName);
		account.setPasswd(password);
		account.setMobile(mobile);
		account.setIsAdmin("1");
		account.setIsClosed("0");
		account.setEmail(email);
		account.setHaierUserId(String.valueOf(haierUser.getUserId()));
		purchaserAccountService.save(account);
		return AjaxJson.ok("注册成功");
	}

}