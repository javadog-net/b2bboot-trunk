/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.app;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.jhmis.modules.wechat.entity.WxAppCid;
import com.jhmis.modules.wechat.service.PurchaserLogintimeService;
import com.jhmis.modules.wechat.service.WxAppCidService;
import com.jhmis.modules.wechat.service.WxMemberService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 登录相关Controller
 * 
 * @author hdx
 * @version 2018-11-22
 */
@RestController
@RequestMapping("/api/app/login")
public class AppApiLoginController extends BaseController {

	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private PurchaserAccountMapper purchaserAccountMapper;
	@Autowired
	WxMemberService wxMemberService;
	@Autowired
	WxAppCidService wxAppCidService;
	@Autowired
	PurchaserLogintimeService purchaserLogintimeService;

	/**
	 * APP通过用户名和密码登录
	 * 
	 * @param userName
	 * @param password
	 *            cId 用户cid phoneType 用户的手机类型
	 * @return
	 */
	@ApiOperation(notes = "loginByApp", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String") })
	@RequestMapping("/loginByApp")
	public AjaxJson login(String userName, String password, String cId, HttpServletRequest request, String phoneType) {
		logger.info("userName=" + userName + "  password=" + password);
		if (StringUtils.isBlank(userName)) {
			return AjaxJson.fail("用户名不能为空");
		}
		if (StringUtils.isBlank(phoneType)) {
			return AjaxJson.fail("手机类型不能为空");
		}
		if (StringUtils.isBlank(cId)) {
			return AjaxJson.fail("用户的CID不能为空");
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
			// TODO: handle exception
			e.printStackTrace();
		}
		logger.info("后");
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
				logger.info(" super.save(account);");
				purchaserAccountService.save(account);
				logger.info("super.save(account);后");
				WxAppCid cid = new WxAppCid();
				cid.setCId(cId);
				List<WxAppCid> listappcid = wxAppCidService.getByCidAndUserId(cid); // 这是
																					// 值通过cid
																					// 查询的
				if (listappcid != null && listappcid.size() > 0) {
					logger.info("删除不相等的cid前");
					wxAppCidService.deleteAll(listappcid);
					logger.info("删除不相等的cid后");
				}
				WxAppCid wxAppCid = new WxAppCid();
				wxAppCid.setUserId(account.getId());
				wxAppCid.setCId(cId);
				wxAppCid.setUserPhone(userName);
				wxAppCid.setPhoneType(phoneType);
				logger.info("保存 cid前");
				wxAppCidService.save(wxAppCid);
				logger.info("保存 cid后");

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
			WxAppCid cid = new WxAppCid();
			cid.setCId(cId);
			List<WxAppCid> listappcid = wxAppCidService.getByCidAndUserId(cid); // 这是
																				// 值通过cid
																				// 查询的
			if (listappcid != null && listappcid.size() > 0) {
				logger.info("删除不相等的cid前");
				wxAppCidService.deleteAll(listappcid);
				logger.info("删除不相等的cid后");
			}
			WxAppCid wxAppCid = new WxAppCid();
			wxAppCid.setUserId(account.getId());
			wxAppCid.setCId(cId);
			wxAppCid.setUserPhone(userName);
			wxAppCid.setPhoneType(phoneType);
			logger.info("保存 cid前");
			wxAppCidService.save(wxAppCid);
			logger.info("保存 cid后");

		}
		PurchaserAccount account2 = purchaserAccountService.getByName(userName);
		account2.setAvatar(purchaserAccountService.getAvatarById(account2.getId()));
		account2.setNickName(purchaserAccountService.getNicknameById(account2.getId()));
		account2.setRoleList(PurchaserUtils.getRoleListFromDb(account2));
		logger.info("set role");

		// 先清空账户缓存
		PurchaserUtils.clearCache(account2);
		// 再放入缓存
		PurchaserUtils.putCache(account2);
		HttpSession session = request.getSession();
		session.setAttribute("", "");
		AjaxJson ret = new AjaxJson();
		ret.put("account", account2);
		ret.put("token", JWTUtils.sign(account2.getId(), Global.USER_TYPE_PURCHASER));
		return ret;
	}

	/**
	 * APP通过手机号和验证码登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@ApiOperation(notes = "loginByCode", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "form", dataType = "String"), })
	@RequestMapping("/loginByCode")
	public AjaxJson loginByCode(String userName, String code, @RequestParam(name = "cId", required = false) String cId,
			@RequestParam(name = "phoneType", required = false) String phoneType) {
		logger.info("userName=" + userName + "  code=" + code);
		if (StringUtils.isBlank(userName)) {
			return AjaxJson.fail("手机号不能为空");
		}
		if (StringUtils.isBlank(code)) {
			return AjaxJson.fail("验证码不能为空");
		}
		if (StringUtils.isBlank(cId) || StringUtils.isBlank(phoneType)) {
			return AjaxJson.fail("cid或 phoneType为空，请联系客服进行协助解决");
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
				WxAppCid cid = new WxAppCid();
				cid.setCId(cId);
				List<WxAppCid> listappcid = wxAppCidService.getByCidAndUserId(cid); // 这是
																					// 值通过cid
																					// 查询的
				if (listappcid != null && listappcid.size() > 0) {
					logger.info("删除不相等的cid前");
					wxAppCidService.deleteAll(listappcid);
					logger.info("删除不相等的cid后");
				}
				WxAppCid wxAppCid = new WxAppCid();
				wxAppCid.setUserId(account.getId());
				wxAppCid.setCId(cId);
				wxAppCid.setUserPhone(userName);
				wxAppCid.setPhoneType(phoneType);
				logger.info("保存 cid前");
				wxAppCidService.save(wxAppCid);
				logger.info("保存 cid后");
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
			WxAppCid cid = new WxAppCid();
			cid.setCId(cId);
			List<WxAppCid> listappcid = wxAppCidService.getByCidAndUserId(cid); // 这是
																				// 值通过cid
																				// 查询的
			if (listappcid != null && listappcid.size() > 0) {
				logger.info("删除不相等的cid前");
				wxAppCidService.deleteAll(listappcid);
				logger.info("删除不相等的cid后");
			}
			WxAppCid wxAppCid = new WxAppCid();
			wxAppCid.setUserId(account.getId());
			wxAppCid.setCId(cId);
			wxAppCid.setUserPhone(userName);
			wxAppCid.setPhoneType(phoneType);
			logger.info("保存 cid前");
			wxAppCidService.save(wxAppCid);
			logger.info("保存 cid后");
		}
		PurchaserAccount account2 = purchaserAccountService.getByName(userName);
		account2.setAvatar(purchaserAccountService.getAvatarById(account2.getId()));
		account2.setNickName(purchaserAccountService.getNicknameById(account2.getId()));
		account2.setRoleList(PurchaserUtils.getRoleListFromDb(account2));
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
	public AjaxJson loginByPC(String userName, String password,HttpServletRequest request) {
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
		HttpSession session = request.getSession();
		session.setAttribute("", "");
		AjaxJson ret = new AjaxJson();
		ret.put("account", account2);
		ret.put("token", JWTUtils.sign(account2.getId(), Global.USER_TYPE_PURCHASER));
		return ret;
	}
	
	/**
	 * PC端通过手机号和验证码登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@ApiOperation(notes = "loginByCodePC", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "form", dataType = "String"), })
	@RequestMapping("/loginByCodePC")
	public AjaxJson loginByCodePC(String userName, String code) {
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
				purchaserLogintime.setLoginTime(account.getLastLoginDate());
				purchaserLogintime.setNum(0);
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
		purchaserLogintime.setAccountId(account2.getId());
		purchaserLogintime.setPurchaserId(account2.getPurchaserId());
		purchaserLogintimeService.save(purchaserLogintime);
		//account2.setAvatar(purchaserAccountService.getAvatarById(account2.getId()));
		//account2.setNickName(purchaserAccountService.getNicknameById(account2.getId()));
		//account2.setRoleList(PurchaserUtils.getRoleListFromDb(account2));
		//logger.info("set role");

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
	 * 验证登录时token是否过期
	 * 
	 * @return
	 */
	@RequestMapping("/testLogin")
	public AjaxJson testLogin() {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		} else {
			return AjaxJson.ok();
		}
	}

	/**
	 * @Title: updateUserPasswordByPhoneOrEmail
	 * @Description: TODO
	 * @param code
	 * @param mobileOrEmail
	 * @param password
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年3月27日下午2:07:07
	 */
	@ApiOperation(notes = "updatepwdbyphone", httpMethod = "POST", value = "通过手机号找回密码", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mobileOrEmail", value = "用户名", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),

	})
	@RequestMapping("/updatepwdbyphone")
	public AjaxJson updateUserPasswordByPhoneOrEmail(String code, String mobileOrEmail, String password) {
		if (StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(password)) {
			return AjaxJson.fail("参数不完整");
		}
		ExecuteResult<String> result = UserApi.updateUserPasswordByPhoneOrEmail(code, mobileOrEmail, password);
		if (result != null && result.getError().equals("1005")) {
			return AjaxJson.fail("验证码不正确，请重新输入或重新获取输入！");
		}
		if (result != null && result.getError().equals("1004")) {
			return AjaxJson.fail("手机格式不正确，请再确认是否输入正确！");
		}
		if (result != null && result.getSuccessMessage().equals("修改密码成功")) {
			return AjaxJson.ok("找回密码成功，请牢记密码！");
		}
		return AjaxJson.fail("接口异常，找回密码失败。");
	}

	/**
	 * @Title: logout
	 * @Description: TODO 清除cid（删除）
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年5月7日上午11:29:30
	 */
	@RequestMapping("/logout")
	public AjaxJson logout() {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if(purchaserAccount==null){
			return AjaxJson.ok("已退出，请重新登录");
		}
		wxAppCidService.deleteByUserId(purchaserAccount.getId());
		if(StringUtils.isNotBlank(purchaserAccount.getMobile())){
		 wxAppCidService.deleteByUserPhone(purchaserAccount.getMobile());
		}

		purchaserAccountService.logout();
		return AjaxJson.ok("退出成功");
	}

	/**
	 * @Title: checkCid
	 * @Description: TODO 检查用户是否有手机型号 通过cid判断
	 * @param cId
	 * @param type
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年7月10日下午4:25:14
	 */
	@RequestMapping("/checkCid")
	public AjaxJson checkCid(@RequestParam(value = "cId", required = false) String cId,
			@RequestParam(value = "type", required = false) String type) {
		logger.info("cid" + cId);
		List<WxAppCid> list = wxAppCidService.findByCid(cId);
		logger.info("list.size" + list.size());
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				logger.info("多个cid");

				return AjaxJson.fail("请重新登录");
			}
			if (list.size() == 1) {
				WxAppCid cid = list.get(0);
				if (StringUtils.isBlank(cid.getPhoneType())) {
					logger.info("手机类型是空的");
					return AjaxJson.fail("请重新登录");
				}
			}
		}else{
			logger.info("在wx_app_cid中 通过cid没查询到 该用户 需要重新登录");
			return AjaxJson.fail("请重新登录");
		}
		return AjaxJson.ok("成功");

	}
	
	
	
	
}