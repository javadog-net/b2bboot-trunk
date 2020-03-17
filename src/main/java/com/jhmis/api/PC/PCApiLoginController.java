package com.jhmis.api.PC;

import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.PurchaserLogintime;
import com.jhmis.modules.wechat.service.PurchaserLogintimeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/pc/login")
public class PCApiLoginController extends BaseController {

	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired 
	private PurchaserAccountMapper purchaserAccountMapper;
	@Autowired
	private PurchaserLogintimeService purchaserLogintimeService;

	@ApiOperation(notes = "loginByCode", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "form", dataType = "String"), })
	@RequestMapping("/loginByCode")
	public AjaxJson loginByCode(String userName, String code) {
		logger.info("userName=" + userName + "  code=" + code);
		if (StringUtils.isBlank(userName)) {
			return AjaxJson.fail("手机号不能为空");
		}
		if (StringUtils.isBlank(code)) {
			return AjaxJson.fail("验证码不能为空");
		}
		logger.info("UserApi.login(userName,code);前");
		ExecuteResult<HaierUserDTO> result = UserApi.verificodelogin(userName, code);
		logger.info("UserApi.login(userName,code);后");
		if (!result.isSuccess()) {
			logger.info("!result.isSuccess()");
			return AjaxJson.fail(result.getError(), UserApiErrorDesc.LoginError.getDesc(result.getError()));
		}
		logger.info("result.getResult();");
		HaierUserDTO haierUser = result.getResult();
		logger.info("result.getResult();结束");
		// 用户中心验证通过了
		logger.info("this.getByName(userName);" + userName);
		PurchaserAccount account = new PurchaserAccount();
		try {
			account = purchaserAccountService.getByName(userName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		logger.info("后");
		PurchaserLogintime purchaserLogintime = new PurchaserLogintime();
		if (account == null) {
			logger.info("account == null");
			// 插入新用户和采购商
			Purchaser purchaser = new Purchaser();
			account = new PurchaserAccount();
			// 暂时把公司编码设置为用户的登录账号
			purchaser.setCompanyCode(userName);
			purchaser.setCompanyName(haierUser.getUserName());
			purchaser.setMobile(haierUser.getMobile());
			purchaser.setEmail(haierUser.getEmail());
			purchaser.setAuditState(Global.AUDIT_STATE_UNSUBMIT);// 待完善状态
			try {
				logger.info(" purchaserService.save(purchaser);前");
				purchaserService.save(purchaser);
				logger.info("  purchaserService.save(purchaser);后");
				account.setPurchaserId(purchaser.getId());
				account.setLoginName(userName);
				account.setRealName(haierUser.getUserName());
				account.setMobile(haierUser.getMobile());
				account.setIsAdmin("1");
				account.setIsClosed("0");
				account.setEmail(haierUser.getEmail());
				account.setHaierUserId(String.valueOf(haierUser.getUserId()));
				account.setLastLoginDate(new Date());
				logger.info(" super.save(account);");
				purchaserAccountService.save(account);
				logger.info("super.save(account);后");
				logger.info("super.本地没有account 保存后返回的accountid" + account.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			logger.info("account != null");
			if (Global.YES.equals(account.getIsClosed())) {
				return AjaxJson.fail(Constants.ERROR_CODE_STOP_ERROR, Constants.ERROR_DESC_STOP_ERROR);
			}
			account.setLastLoginDate(new Date());
			purchaserAccountMapper.updateLoginInfo(account);
			purchaserLogintime.setLoginTime(account.getLastLoginDate());
			purchaserLogintime.setNum(1+purchaserLogintimeService.findNumByAccountId(account.getId()));
		}
		PurchaserAccount account2 = purchaserAccountService.getByName(userName);
		account2.setAvatar(purchaserAccountService.getAvatarById(account2.getId()));//头像
		account2.setNickName(purchaserAccountService.getNicknameById(account2.getId()));
		account2.setRoleList(PurchaserUtils.getRoleListFromDb(account2));//获取用户角色
		purchaserLogintime.setAccountId(account2.getId());
		purchaserLogintime.setPurchaserId(account2.getPurchaserId());
		purchaserLogintimeService.save(purchaserLogintime);
		logger.info("set role");
		// 先清空账户缓存
		PurchaserUtils.clearCache(account2);
		// 再放入缓存
		PurchaserUtils.putCache(account2);
		AjaxJson ret = new AjaxJson();
		ret.put("account", account2);
		ret.put("token", JWTUtils.sign(account2.getId(), Global.USER_TYPE_PURCHASER));
		return ret;
	}

	/**
	 * PC端通过用户名和密码登录
	 * @param userName
	 * @param password
	 * @return
	 */
	@ApiOperation(notes = "loginByPC", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String") })
	@RequestMapping("/loginByPC")
	public AjaxJson loginByPC(String userName, String password, HttpServletRequest request) {
		logger.info("userName=" + userName + "  password=" + password);
		if (StringUtils.isBlank(userName)) {
			return AjaxJson.fail("用户名不能为空");
		}
		if (StringUtils.isBlank(password)) {
			return AjaxJson.fail("密码不能为空");
		}
		logger.info("UserApi.verificodelogin(userName,password);前");
		ExecuteResult<HaierUserDTO> result = UserApi.login(userName, password);
		logger.info("UserApi.verificodelogin(userName,password);后");
		if (!result.isSuccess()) {
			logger.info("!result.isSuccess()");
			return AjaxJson.fail(result.getError(), UserApiErrorDesc.LoginError.getDesc(result.getError()));
		}
		logger.info("result.getResult();");
		HaierUserDTO haierUser = result.getResult();
		logger.info("result.getResult();结束");
		// 用户中心验证通过了
		logger.info("this.getByName(userName);" + userName);
		PurchaserAccount account = new PurchaserAccount();
		try {
			account = purchaserAccountService.getByName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("后");
		PurchaserLogintime purchaserLogintime = new PurchaserLogintime();
		if (account == null) {
			logger.info("account == null");
			// 插入新用户和采购商
			Purchaser purchaser = new Purchaser();
			account = new PurchaserAccount();
			purchaser.setCompanyName(haierUser.getUserName());
			purchaser.setMobile(haierUser.getMobile());
			purchaser.setEmail(haierUser.getEmail());
			purchaser.setAuditState(Global.AUDIT_STATE_UNSUBMIT);// 待完善状态
			try {
				logger.info("  purchaserService.save(purchaser);前");
				purchaserService.save(purchaser);
				logger.info("  purchaserService.save(purchaser);后");
				account.setPurchaserId(purchaser.getId());
				account.setLoginName(userName);
				account.setRealName(haierUser.getUserName());
				account.setPasswd(password);
				account.setMobile(haierUser.getMobile());
				account.setIsAdmin("1");
				account.setIsClosed("0");
				account.setEmail(haierUser.getEmail());
				account.setHaierUserId(String.valueOf(haierUser.getUserId()));
				account.setLastLoginDate(new Date());
				purchaserLogintime.setLoginTime(account.getLastLoginDate());
				purchaserLogintime.setNum(0);
				logger.info(" super.save(account);");
				purchaserAccountService.save(account);
				logger.info("super.save(account);后");																					// 查询的
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			logger.info("account != null");
			if (Global.YES.equals(account.getIsClosed())) {
				return AjaxJson.fail(Constants.ERROR_CODE_STOP_ERROR, Constants.ERROR_DESC_STOP_ERROR);
			}
			account.setLastLoginDate(new Date());
			purchaserAccountMapper.updateLoginInfo(account);
			purchaserLogintime.setLoginTime(account.getLastLoginDate());
			purchaserLogintime.setNum(1+purchaserLogintimeService.findNumByAccountId(account.getId()));
		}
		PurchaserAccount account2 = purchaserAccountService.getByName(userName);
		purchaserLogintime.setAccountId(account2.getId());
		purchaserLogintime.setPurchaserId(account2.getPurchaserId());
		purchaserLogintimeService.save(purchaserLogintime);
//		account2.setAvatar(purchaserAccountService.getAvatarById(account2.getId()));
//		account2.setNickName(purchaserAccountService.getNicknameById(account2.getId()));
//		account2.setRoleList(PurchaserUtils.getRoleListFromDb(account2));
//		logger.info("set role");

		// 先清空账户缓存
		PurchaserUtils.clearCache(account2);
		// 再放入缓存
		PurchaserUtils.putCache(account2);

		AjaxJson ret = new AjaxJson();
		ret.put("account", account2);
		ret.put("token", JWTUtils.sign(account2.getId(), Global.USER_TYPE_PURCHASER));
		return ret;
	}

	@RequestMapping("/logout")
	public AjaxJson logout(){
		return purchaserAccountService.logout();
	}



}
