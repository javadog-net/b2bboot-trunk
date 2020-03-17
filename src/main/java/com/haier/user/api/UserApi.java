package com.haier.user.api;

import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.utils.CacheUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * 海尔用户中心接口调用
 * @author jianghl
 */
public class UserApi {
    // 海尔用户中心token缓存key
    private static final String HAIER_UCENTER_REGION = "haierUcenterCache";
    private static final String KEY_HAIER_UCENTER_TOKEN = "KEY_HAIER_UCENTER_TOKEN";
    private static final Integer HAIER_UCENTER_TOKEN_EXPIRETIME = 5400;
	public final static String GET_TOKEN_URL="https://user.haier.net/services/getToken";
    public final static String CHECK_TOKEN_URL="https://user.haier.net/services/checkToken";
	public final static String SEND_MOBILE_URL="https://user.haier.net/services/sendMobile";
	public final static String CHECK_MOBILE_CODE_URL="https://user.haier.net/services/checkMobileCode";
	public final static String USER_IS_REGISTORED_URL="https://user.haier.net/services/userIsRegistored";
	public final static String USER_REGISTER_TREEKEY_URL="https://user.haier.net/services/userRegisterThreeKey";
	public final static String GET_COOKIE_URL="https://user.haier.net/services/get_cookie";
	public final static String GET_USERINFO_URL="https://user.haier.net/services/getUserInfo";
	public final static String GET_USERBASIC_BY_USERTOKEN_URL="https://user.haier.net/services/getUserBasicByUserToken";
	public final static String LOGIN_URL="https://user.haier.net/services/login";
	public final static String VERIFICODE_LOGIN_URL="https://user.haier.net/services/verificodelogin";
	public final static String SET_COOKIE_URL="https://user.haier.net/services/set_cookie";
	public final static String CLEAR_COOKIE_URL="https://user.haier.net/services/clear_cookie";
	public final static String UPDATE_USER_INFO_URL="https://user.haier.net/services/updateUserInfo";
	public final static String UPDATE_USER_CIPHERKEY_URL ="https://user.haier.net/services/updateUserPass";
	public final static String UPDATE_USER_CIPHERKEYByPhoneOrEmail_URL ="https://user.haier.net/services/updatePassword";
	public final static String SEND_MAIL_URL="https://user.haier.net/services/sendEmail";
	
	public final static String APP_KEY="BTB";
	public final static String SECRET="1f2bf59825a38a81c2a66a645f36a422";
//	public final static String APP_KEY="HUCO";
//	public final static String SECRET="e41437fee3435677fdc41ccb1098203a";

    /**
     * 同步获取token
     * @return
     */
    public static synchronized ExecuteResult<String> syncGetToken() {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("appKey", APP_KEY);
        para.put("secret", SECRET);
        ExecuteResult<String> result = HttpWebUtil.httpRequest(GET_TOKEN_URL, para);
        if(result.isSuccess()){
            CacheUtils.put(HAIER_UCENTER_REGION, KEY_HAIER_UCENTER_TOKEN, result.getResult(), HAIER_UCENTER_TOKEN_EXPIRETIME);
        }
        return result;
    }

    /**
     * 检查token是否有效
     * @param token
     * @return
     */
    public static ExecuteResult<String> checkToken(String token){
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("token", token);
        ExecuteResult<String> result = HttpWebUtil.httpRequest(CHECK_TOKEN_URL, para);
        return result;
    }
	/**
	 * 获取token
	 * @return
	 */
	public static ExecuteResult<String> getToken() {
        String token = (String) CacheUtils.get(HAIER_UCENTER_REGION, KEY_HAIER_UCENTER_TOKEN);
        if(StringUtils.isBlank(token)){
            ExecuteResult<String> result = syncGetToken();
            return result;
        } else {
            ExecuteResult<String> checkResult = checkToken(token);
            if(!checkResult.isSuccess()){
                ExecuteResult<String> result = syncGetToken();
                return result;
            } else {
                ExecuteResult<String> result = new ExecuteResult<>();
                result.setResult(token);
                return result;
            }
        }
	}
	
	/**
	 * 发送手机验证码
	 * @return
	 */
	public static ExecuteResult<String> sendMobile(String mobile) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            return result;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("mobile", mobile);
		return HttpWebUtil.httpRequest(SEND_MOBILE_URL, para);

	}
	/**
	 * 验证手机验证码
	 * @param mobile
	 * @return
	 */
	public static ExecuteResult<String> checkMobileCode(String mobile, String code) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            return result;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("mobile", mobile);
		para.put("code", code);
		return HttpWebUtil.httpRequest(CHECK_MOBILE_CODE_URL, para);
	}

    /**
     * 发送邮件验证码
     * @param email
     * @return
     * TODO 返回500
     */
    public static ExecuteResult<String> sendMail(String email) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            return result;
        }
        Map<String,Object> para  =  new HashMap<String,Object>();
        para.put("token", result.getResult());
        return HttpWebUtil.httpRequest(SEND_MAIL_URL, para);
    }

	/**
	 *判断用户是否注册
	 * @param userName
	 * @return
     * 已经注册返回1005
	 */
	public static ExecuteResult<String> userIsRegistered(String userName) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            return result;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("user", userName);
		return HttpWebUtil.httpRequest(USER_IS_REGISTORED_URL, para);

	}

	/**
	 * 获取cookie
	 * @return
     * TODO 有问题
	 */
	public static ExecuteResult<String> getCookie() {
		Map<String,Object> para  =  new HashMap<String,Object>();
		return HttpWebUtil.httpRequest(GET_COOKIE_URL, para);
	}

    /**
     * 设置token
     * @param user_token
     * @return
     * TODO 有问题(返回null(success),json没法解析)
     */
    public static ExecuteResult<String> setCookie(String user_token) {
        Map<String,Object> para  =  new HashMap<String,Object>();
        para.put("user_token", user_token);
        return HttpWebUtil.httpRequest(SET_COOKIE_URL, para);
    }

    /**
     * 清除cookie
     * @return
     * TODO 有问题
     */
    public static ExecuteResult<String> clearCookie() {
        Map<String,Object> para  =  new HashMap<String,Object>();
        return HttpWebUtil.httpRequest(CLEAR_COOKIE_URL, para);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     * 2000，没有此用户信息
     */
	public static ExecuteResult<HaierUserDTO> getUserInfo(String userId) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            ExecuteResult<HaierUserDTO> ret = new ExecuteResult<HaierUserDTO>();
            ret.setErrorMessages(result.getErrorMessages());
            ret.setFieldErrors(result.getFieldErrors());
            return ret;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("userId", userId);
		return HttpWebUtil.httpRequest(GET_USERINFO_URL, para ,HaierUserDTO.class);

	}

    /**
     * 通过token获取用户基础信息
     * @param userToken
     * @return
     */
	public static ExecuteResult<String> getUserBasicByUserToken(String userToken) {
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("userToken", userToken);
		return HttpWebUtil.httpRequest(GET_USERBASIC_BY_USERTOKEN_URL, para);
	}

    /**
     * 用户注册
     * @param userName
     * @param password
     * @param code
     * @return
     */
    public static ExecuteResult<HaierUserDTO> userRegister(String userName, String mobile, String email, String password, String code) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            ExecuteResult<HaierUserDTO> ret = new ExecuteResult<HaierUserDTO>();
            ret.setErrorMessages(result.getErrorMessages());
            ret.setFieldErrors(result.getFieldErrors());
            return ret;
        }
        Map<String,Object> para  =  new HashMap<String,Object>();
        
        if(StringUtils.isBlank(email)){
        	para.put("email", "");
        }else{
        	para.put("email", email);
        }    
        para.put("token", result.getResult());
        para.put("userName", userName);
        para.put("password", password);
        para.put("mobile", mobile);
        para.put("code", code);
        return HttpWebUtil.httpRequest(USER_REGISTER_TREEKEY_URL, para, HaierUserDTO.class);
    }

    /**
     * 采购商注册查询公司名称
     */
    public static ExecuteResult<String> purchaserRegister(String companyName) {
//    	Purchaser purchaser = new Purchaser();
    	//根据companyName查询本地数据库
    	
    	//本地库中没有，去调海尔库
    	
    	//本地库中有直接返回结果
    	
    	
		return null;
    }
    
    
    /**
     * 用户通过账号和密码登录
     * @param loginname
     * @param pwd
     * @return
     */
	public static ExecuteResult<HaierUserDTO> login(String loginname, String pwd) {
		System.out.println("hh");
        ExecuteResult<String> result = getToken();
        System.out.println("jj");
        if(!result.isSuccess()){
            ExecuteResult<HaierUserDTO> ret = new ExecuteResult<HaierUserDTO>();
            ret.setErrorMessages(result.getErrorMessages());
            ret.setFieldErrors(result.getFieldErrors());
            return ret;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("loginName", loginname);
		para.put("pwd", pwd);
		return HttpWebUtil.httpRequest(LOGIN_URL, para, HaierUserDTO.class);

	}


	/**
	 * 根据88码 密码 推荐码登录
	 * @param number
	 * @param password
	 * @param code
	 * @return*/

	public static ExecuteResult<PurchaserAccount> loginNumber(String loginNum, String password,String code){
			ExecuteResult<String> result = getToken();
			if (!result.isSuccess()){
				ExecuteResult<PurchaserAccount> ret = new ExecuteResult<>();
				ret.setErrorMessages(result.getErrorMessages());
				ret.setFieldErrors(result.getFieldErrors());
				return ret;
			}
			HashMap<String, Object> para = new HashMap<>();
			para.put("token", result.getResult());
			para.put("loginNum",loginNum);
			para.put("password",password);
			para.put("code",code);
		    return HttpWebUtil.httpRequest(LOGIN_URL, para, PurchaserAccount.class);
	}


    /**
     * 用户通过手机号和验证码登录
     * @param loginname
     * @param code
     * @return
     */
	public static ExecuteResult<HaierUserDTO> verificodelogin(String loginname, String code) {
		System.out.println("hh");
        ExecuteResult<String> result = getToken();
        System.out.println("jj");
        if(!result.isSuccess()){
            ExecuteResult<HaierUserDTO> ret = new ExecuteResult<HaierUserDTO>();
            ret.setErrorMessages(result.getErrorMessages());
            ret.setFieldErrors(result.getFieldErrors());
            return ret;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("loginName", loginname);
		para.put("code", code);
		return HttpWebUtil.httpRequest(VERIFICODE_LOGIN_URL, para, HaierUserDTO.class);
	}

    /**
     * 更新用户信息
     * @param dto
     * @return
     */
	public static ExecuteResult<String> updateUserInfo(HaierUserDTO dto) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            return result;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("userDetailedDTO", dto);
		return HttpWebUtil.httpRequest(UPDATE_USER_INFO_URL, para);
	}

    /**
     * 更新用户密码
     * @param userId
     * @param oldPassword
     * @param pwd
     * @return
     */
	public static ExecuteResult<String> updateUserPassword(String userId,String oldPassword, String pwd) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            return result;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("userId", userId);
		para.put("oldPassword", oldPassword);
		para.put("pwd", pwd);
		return HttpWebUtil.httpRequest(UPDATE_USER_CIPHERKEY_URL, para);
	}

	
	
	/** 
	  * @Title: updateUserPasswordByPhoneOrEmail 
	  * @param code
	  * @param password
      * @param mobileOrEmail
	  * @return 
	  * @return ExecuteResult<String>
	  * @author tc
	  * @date 2019年3月20日上午11:23:06
	  */
	public static ExecuteResult<String> updateUserPasswordByPhoneOrEmail(String code,String mobileOrEmail, String password) {
        ExecuteResult<String> result = getToken();
        if(!result.isSuccess()){
            return result;
        }
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("token", result.getResult());
		para.put("mobileOrEmail", mobileOrEmail);
		para.put("password", password);
		para.put("code", code);
		return HttpWebUtil.httpRequest(UPDATE_USER_CIPHERKEYByPhoneOrEmail_URL, para);
	}

	
	
	/**
	 * 测试接口
	 * @param args
     * 总结
     * 用户注册需要
     * 1.判断用户是否注册
     * 如果已注册，提示已经注册，直接去登录
     * 2.发送短信验证码
     * 3.验证短信验证码
     * 4.用户注册
     * 用户登录
     * 先调用用户中心接口，登录失败则失败，
     * 登录成功，检查本地是否有用户，如果没有添加到本地。并产生采购商数据。
     * 本地是否添加userid等用户中心相关字段。最好添加上。
     * 修改密码
     * 老密码，新密码，调用用户中心接口处理修改密码，本地可以不处理
	 */
	public static void main(String[] args) {
		
		//ExecuteResult<HaierUserDTO> result1 =verificodelogin("13210006056","392824");
		
		
		//ExecuteResult<HaierUserDTO> result1 =login("13021661569","381022");
	//System.out.println(result1.getSuccessMessage());
		//392824
	 ExecuteResult<String> result = sendMobile("18669434707");
	 System.out.println(result.getSuccessMessage());
	/*System.out.println(result1.getResult());
	System.out.println(result1.getSuccessMessage());
	System.out.println(result1.getError());
	*/
	
//		String code="253480";
//		String mobileOrEmail="15863792159";
//		String password="tiancong";
//		ExecuteResult<String> s=updateUserPasswordByPhoneOrEmail(code, mobileOrEmail, password);
//		System.out.println(s.getError()+"1");
//		System.out.println(s.getResult()+"2");
//		System.out.println(s.getSuccessMessage()+"3");
//		System.out.println(s.getUserToken()+"4");
//		System.out.println(s.getErrorMessages()+"5");
//		System.out.println(s.getWarningMessages()+"6");
//		
		
		//541503
//		ExecuteResult<String> token = getToken();
//        System.out.println(result.getSuccessMessage());
        //ExecuteResult<String> result = userRegister("13969696605","13969696605", "", "123456", "391504");
        //ExecuteResult<HaierUserDTO> result1 = login("15863792159","dingrui");
        //ExecuteResult<HaierUserDTO> result = getUserInfo("2951459986539520");
        //ExecuteResult<String> result2 = setCookie(result.getUserToken());
        //System.out.println(result2.getError());
        //ExecuteResult<String> result1 = getCookie();
        //System.out.println(result1.getError());
        //String s = "{\"errorMessages\":[\"1\",\"2\"],\"fieldErrors\":{\"aa\":\"sss\",\"bb\":\"eee\"},\"result\":\"APP_BTB_52029cbe49813fd437c4f14c24fbd014\",\"success\":true,\"warningMessages\":[]}";
       /* ExecuteResult<String> a = JSONObject.parseObject(s, new TypeReference<ExecuteResult<String>>(){});
        if(a.isSuccess()){
            System.out.println("success");
        } else {
            System.out.println("error");
        }
        System.out.println("sdf");*/
//		HttpResponseVO vo1 = getToken();
//		System.out.println(vo1.getResult());
//		System.out.println(vo1.getSuccess());
//		System.out.println(vo1.getErrorMessage());
//		
//		HttpResponseVO vo = getCookie();
//		System.out.println(vo.getResult());
//		System.out.println(vo.getSuccess());
//		System.out.println(vo.getErrorMessage());
//		sendMobile("18669775356");
//		userIsRegistored("18669775356");
//		userRegister("jianghl","18660253448","249574168@qq.com","abc123456","");()
//		HttpResponseVO vo = getUserInfo();
//		UserCallbackDTO dto = JSONObject.parseObject(vo.getResult().toString(),UserCallbackDTO.class);
//		System.out.println(dto.getUserName());
		
//		ExecuteResult<HaierUserDTO>ss=login("18669775356","HaiLiang@2046");
//		ExecuteResult<HaierUserDTO> tt=getUserInfo("3172256670885888");
//		ExecuteResult<String> result3=updateUserPassword("3172256670885888","a11111","HaiLiang@2046");
//		System.out.println(result3);
//		System.out.println(ss);
//		System.out.println(tt);
		
//		HaierUserDTO dto = new HaierUserDTO();
//	dto.setUserId(String.valueOf(((Member)session.getAttribute("loginMember")).getUserId()));
//		dto.setUserId("3172256670885888");
//		dto.setProvince("山东省");
//		dto.setCity("青岛市");
//		dto.setCorpName("海尔");
//		ExecuteResult<String> vo = updateUserInfo(dto)
//      sendMail("jianghailiang84@163.com");
		
	}
}


