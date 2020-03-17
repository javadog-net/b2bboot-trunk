package com.haier.user.api;

import java.util.HashMap;
import java.util.Map;

public class QixinApi {
	
	//根据企业名称关键字获取企业列表
	public final static String ENTERPRISE_SEARCH_URL="http://apigw.haier.net/dtsqxbapi/APIService/v2/enterprise/searchListPaging";
	public final static String API_GAREWAY_AUTH_APP_ID="1a0e65d2-e02e-48d1-aa99-fbd3a4472dee";
	public final static String API_GAREWAY_AUTH_APP_CIPHERKEY ="3cdeD5ScdsVc";
	//根据企业名称关键字获取企业列表
	public final static String ENTERPRISE_SEARCH_TEST_URL="http://api.qixin.com/APITestService/v2/enterprise/searchListPaging";	
	public final static String APP_KEY_TEST="ada44bd0070711e6b8a865678b483fde";
	public final static String API_GAREWAY_AUTH_APP_ID_TEST="d051fc2f-d2ad-4e3b-a8ad-35782b3a7503";
	public final static String API_GAREWAY_AUTH_APP_CIPHERKEY_TEST ="Haier,123";
	
	//根据企业全名或注册号精确获取企业工商详细信息及联系方式
	public final static String DETAIL_CONTACT_URL="http://apigw.haier.net/dtsqxbapi/APIService/enterprise/getDetailAndContactByName";
	public final static String DETAIL_API_GAREWAY_AUTH_APP_ID="9f62a3ea-e941-4b4e-ad96-4cb5d41f200e";
	public final static String DETAIL_API_GAREWAY_AUTH_APP_CIPHERKEY ="$tw%Ng63uLQnfzx";

	//根据企业全名或注册号精确获取企业工商详细信息及联系方式(测试接口)
	public final static String DETAIL_CONTACT_TEST_URL="http://10.138.67.1:8083/dtsqxbapitest/APITestService/enterprise/getDetailAndContactByName";	
	public final static String DETAIL_API_GAREWAY_AUTH_APP_ID_TEST="5d59bb64-37ba-41da-abdd-3dc2639cbf50";
	public final static String DETAIL_API_GAREWAY_AUTH_APP_CIPHERKEY_TEST ="TdMosD!WgNBy51v";
	
	
	/**
	 * 根据企业名称关键字获取企业列表(调用生成接口)
	 * @return
	 */
	public static String searchListPaging(String keyword, String skip) {
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("keyword", keyword);
		para.put("skip", skip);
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("api_gateway_auth_app_id", API_GAREWAY_AUTH_APP_ID);
		header.put("api_gateway_auth_app_password", API_GAREWAY_AUTH_APP_CIPHERKEY);
		return HttpWebUtil.QixinhttpRequestHeader(ENTERPRISE_SEARCH_URL, para, header);

	}
	
	/**
	 * 根据企业名称关键字获取企业列表(调用测试接口)
	 * @return
	 */
	public static String searchListPagingTest(String keyword, String skip) {
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("keyword", keyword);
		para.put("appkey", APP_KEY_TEST);
		return HttpWebUtil.QixinhttpRequest(ENTERPRISE_SEARCH_TEST_URL, para);
	}
	
	/**
	 * //根据企业全名或注册号精确获取企业工商详细信息及联系方式
	 * @return
	 */
	public static String searchDetailContact(String keyword) {
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("keyword", keyword);
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("api_gateway_auth_app_id", DETAIL_API_GAREWAY_AUTH_APP_ID);
		header.put("api_gateway_auth_app_password", DETAIL_API_GAREWAY_AUTH_APP_CIPHERKEY);
		return HttpWebUtil.QixinhttpRequestHeader(DETAIL_CONTACT_URL, para, header);

	}
	
	//根据企业全名或注册号精确获取企业工商详细信息及联系方式(测试接口)
	public static String searchDetailContactTest(String keyword) {
		Map<String,Object> para  =  new HashMap<String,Object>();
		para.put("keyword", keyword);
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("api_gateway_auth_app_id", DETAIL_API_GAREWAY_AUTH_APP_ID_TEST);
		header.put("api_gateway_auth_app_password", DETAIL_API_GAREWAY_AUTH_APP_CIPHERKEY_TEST);
		return HttpWebUtil.QixinhttpRequestHeader(DETAIL_CONTACT_TEST_URL, para, header);

	}
	
	
	
	

	
	public static class Error{
        private static Map<String,String> errors = new HashMap<>();
        static {
            errors.put("201","查询无结果");
            errors.put("213","调用次数超过今日额度限制");
            errors.put("205","超过最大查询数量");
            errors.put("206","跳过条数超过最大数");
            errors.put("207","查询错误，请联系技术人员");
            errors.put("208","参数名错误或参数为空");
            errors.put("209","接口查询异常，请联系技术人员");
            errors.put("101","Appkey无效");
            errors.put("102","账户余额不足");
            errors.put("103","appkey被停用");
            errors.put("104","IP未授权");
            errors.put("105","未授权调用该接口");
            errors.put("109","接口被停用");
        }
        /**
         * 返回错误描述
         * @param code
         * @return
         */
        public static String getDesc(String code){
            String desc = errors.get(code);
            if(desc==null){
                desc = "未知错误";
            }
            return desc;
        }
    }
	
}
