package com.haier.user.api;

import java.util.HashMap;
import java.util.Map;

public class ProductsApi {
	
	public final static String PRODUCTS_PRODUCTINFO_URL="http://product.haier.net/api/upper/es/productInfo";	
	public final static String PRODUCTS_PRODUCTINFO_TEST_URL="http://hlht.product.haier.net/api/upper/es/productInfo";	
	
	//产品中心相关参数(生产)
    public static final long PRODUCTS_CENTER_KEY = Long.parseLong("1000154");
    public static final String PRODUCTS_CENTER_SECRET = "aa3db7f4014d2b158d252305e21bcc4d";
    //产品中心相关参数(测试)
    public static final long PRODUCTS_CENTER_KEY_TEST = Long.parseLong("1000075");
    public static final String PRODUCTS_CENTER_SECRET_TEST = "6d72a1171f1527ccbd47c9bd45e1376f";

    /**
	 * 分页获取产品信息(调用生成接口)
	 * @return
	 */
	public static String getProductInfo(String keyWords, String pageNo ,String pageSize ) {
		String prurl = PRODUCTS_PRODUCTINFO_URL+"?key="+PRODUCTS_CENTER_KEY+"&secret="+PRODUCTS_CENTER_SECRET+"&pageNo="+pageNo+"&pageSize="+pageSize;
		return HttpWebUtil.ProcuctsHttpRequestHeader(prurl, keyWords);
	}
	
	/**
	 * 根据企业名称关键字获取企业列表(调用测试接口)
	 * @return
	 */
	public static String getProductInfoTest(String keyWords, String pageNo ,String pageSize ) {
		String prurl = PRODUCTS_PRODUCTINFO_TEST_URL+"?key="+PRODUCTS_CENTER_KEY_TEST+"&secret="+PRODUCTS_CENTER_SECRET_TEST+"&pageNo="+pageNo+"&pageSize="+pageSize;
		return HttpWebUtil.ProcuctsHttpRequestHeader(prurl, keyWords);
	}
	

}
