package com.jhmis.common.utils;

/**
 * @Author：hdx
 * @Description: 静态常量类
 * @Date: Created in 22:47 2018/7/24
 * @Modified By
 */
public class Constants {
    //公共错误提示定义
    public static final String ERROR_CODE_NO_USER = "8010";
    public static final String ERROR_DESC_NO_USER = "无此用户";
    public static final String ERROR_CODE_CIPHERKEY_ERROR = "8011";
    public static final String ERROR_DESC_CIPHERKEY_ERROR = "密码错误";
    public static final String ERROR_CODE_STOP_ERROR = "8012";
    public static final String ERROR_DESC_STOP_ERROR = "账号已停用";
    public static final String ERROR_CODE_OLDCIPHERKEY_ERROR = "8013";
    public static final String ERROR_DESC_OLDCIPHERKEY_ERROR = "老密码不正确";
    public static final String ERROR_CODE_VALIDCODE_ERROR = "8014";
    public static final String ERROR_DESC_VALIDCODE_ERROR = "验证码不正确";
    public static final String ERROR_CODE_EXCEPTION_ERROR = "666";
    public static final String ERROR_DESC_EXCEPTION_ERROR = "逻辑异常";
    public static final String ERROR_CODE_400_ERROR = "400";
    public static final String ERROR_DESC_400_ERROR = "参数错误";
    public static final String ERROR_CODE_404_ERROR = "404";
    public static final String ERROR_DESC_404_ERROR = "无此资源";
    public static final String ERROR_CODE_500_ERROR = "500";
    public static final String ERROR_DESC_500_ERROR = "内部错误";
    public static final String ERROR_CODE_AUTH_ERROR = "401";
    public static final String ERROR_DESC_AUTH_ERROR = "授权异常";
    public static final String ERROR_CODE_ROLE_ERROR = "403";
    public static final String ERROR_DESC_ROLE_ERROR = "没有权限";
    //==================================================================================================================
    //企业购取销售样表信息****测试地址
    public static final String T_BATCHSALETEMPLATEQUERYMDM2BTB_CLIENT_EP = "http://10.135.16.46:10201/soa-infra/services/interface/BatchSaleTemplateQueryMDM2BTB/batchsaletemplatequerymdm2btb_client_ep";

    //从MDM获取供应商信息接口地址****生产地址
    public static final String D_GETMDM_CUSTOMERSLIST = "http://hpm.haier.net/haiergc/Service/APPQD_WebService.asmx";

    //从MDM获取供应商信息接口地址****测试地址
    public static final String T_GETMDM_CUSTOMERSLIST = "http://10.135.12.95/haiergc_test/Service/APPQD_WebService.asmx";

    //MDM接口相关参数
    public static final String MDM_NAMESPACE = "http://IP/haiergc/Service/";
    public static final String MDM_WSDLURL = "http://hpm.haier.net/haiergc/Service/APPQD_WebService.asmx?wsdl";
    public static final String MDM_SERVICENAME = "APPQD_WebService";
    public static final String MDM_PORTNAME = "APPQD_WebServiceSoap12";
    public static final String MDM_OPERATIONNAME = "getMDM_CustomersList";
    public static final String MDM_RESPONSENAME = "getMDM_CustomersListResponse";

    //企业销售杨图标相关参数
    public static final String BST_NAMESPACE = "http://xmlns.oracle.com/Interface/BatchSaleTemplateQueryMDM2BTB";
    public static final String BST_WSDLURL = "http://10.135.16.46:10201/soa-infra/services/interface/BatchSaleTemplateQueryMDM2BTB/batchsaletemplatequerymdm2btb_client_ep?wsdl";
    public static final String BST_SERVICENAME = "batchsaletemplatequerymdm2btb_client_ep";
    public static final String BST_PORTNAME = "BatchSaleTemplateQueryMDM2BTB_pt";
    public static final String BST_OPERATIONNAME = "process";
    public static final String BST_RESPONSENAME = "processResponse";

    //产品中心相关参数(生产)
    public static final long PRODUCTS_CENTER_KEY = Long.parseLong("1000154");
    public static final String PRODUCTS_CENTER_SECRET = "aa3db7f4014d2b158d252305e21bcc4d";
    //产品中心相关参数(测试)
   /* public static final long PRODUCTS_CENTER_KEY = Long.parseLong("1000075");
    public static final String PRODUCTS_CENTER_SECRET = "6d72a1171f1527ccbd47c9bd45e1376f";*/

   //app相关静态变量
   //待处理事项  0-会议审核    1-会议提醒    2-好友提醒     3-出票状态
   public static final String PENDING_CHECK = "0";
   public static final String PENDING_MEETING = "1";
   public static final String PENDING_FRIEND = "2";
   public static final String PENDING_TICKET = "3";
   //行程相关
   //证件类型(0是身份证 1是护照)
   public static final String TRAVEL_CERTIFICATE_TYPE_IDCARD = "0";
   public static final String TRAVEL_CERTIFICATE_TYPE_PASS = "1";

   //(交通工具)0是火车 1是飞机
   public static final String TRAVEL_VEHICLE_TRAIN = "0";
    public static final String TRAVEL_VEHICLE_PLANE = "1";

    //是否接站 (0是 1否)
    public static final String TRAVEL_START_RECEIVE_YES = "0";
    public static final String TRAVEL_START_RECEIVE_NO = "1";

    //出票状态 (0出票中 1已出票)
    public static final String TRAVEL_TICKET_IN = "0";
    public static final String TRAVEL_TICKET_END = "1";
    
    //招投标审核状态
    public static final String BID_CHECK_PASS = "1";
    public static final String BID_CHECK_FALSE = "2";
    //项目有效状态
    public static final String BID_VALID_YES = "1";
    public static final String BID_VALID_NO = "0";
    //hps接口地址
    public static final String HPS_URL = "http://10.138.10.68:8090";
    //public static final String HPS_URL = "http://10.138.111.55:8090";
    //发布需求400客服
    public static final String B2B_400_USERNAME = "b2binsertmsg";
    public static final String B2B_400_CIPHERKEY = "3fb-729e10fdff66%1cgkf0";
    //发布需求690大接待
    public static final String B2B_RECEPTION_USERNAME = "reception";
    public static final String B2B_RECEPTION_CIPHERKEY = "e10adc3949ba59abbe56e057f20f883e";

    public static final String SHOP_MSG_CHANNEL_NAME = "400客服";
	public static final String RECEPTION_MSG_CHANNEL_NAME = "大接待";
	public static final String IS_NOT_HAVE_UNDER_TAKE= "0"; //该需求没有可承接的经销商
	public static final String IS_HAVE_UNDER_TAKE= "1";//该需求有可承接的经销商
	public static final int MSG_FLAG_APP_STRAIGHT= 22;  //app 审核通过直单
	//直单标识
	public static final String GC_DOMAIN_MODEL_DIRECTIVD = "DIRECTIVD_ORDER";
	//客单标识
	public static final String GC_DOMAIN_MODEL_CUSTOMER = "CUSTOMER_ORDER";
	//总监角色id
    public static final String DIRECTOR_ROLE_ID = "7334b6b2-526c-46d7-a419-37746fa461d5";
    //商空管理员
    public static final String SK_ADMIN = "02d0a087acfb48b78eb9f73a7e5c6e6e";
    //热水器管理员
    public static final String GEYSER_ADMIN = "b800c4ee-898f-4b98-b053-002309366279";
    //洗衣机管理员
    public static final String WASHING_MACHINE_ADMIN = "05382746-9c4c-4aee-9f1f-76a577ee3130";
    //彩电管理员
    public static final String TV_ADMIN = "cd2bf7b4-688d-4899-87b7-d9fe8f992e13";
    //家空管理员
    public static final String JK_ADMIN = "bc9c2d5f-d4a0-4ae0-a7cf-f9f4663b71ed";
    //厨电管理员
    public static final String KITCHEN_ADMIN = "96c95ba1-0a50-4ffa-a7af-69a36c6d85d8";
    //冷柜管理员
    public static final String FREEZER_ADMIN = "063991d1-fbe7-4c5a-a87a-a61689831e27";
    //冰箱管理员
    public static final String ICE_BOX_ADMIN = "fd833deb-2239-4c5c-af25-a33da24be56b";
    //系统管理员
    public static final String SYSTEM_ADMIN = "c54e003c1fc4dcd9b087ef8d48abac3";

    //app推送接口url地址
    public static final String APP_PUSH_URL = "http://58.56.174.23:8081/gcapp/pushCust";


    //EUC 生产接口
    //public static final String EUC_URL = "https://euc.haier.net";
    //EUC 测试接口
    public static final String EUC_URL = "http://euc-test.qd-ctcc.haier.net";


    //HIC 生产接口
    //public static final String HIC_URL = "http://10.155.3.3/expose/businessBuyBack.go";
    //HIC 测试接口
    public static final String HIC_URL = "http://10.155.7.4:7010/expose/businessBuyBack.go";
}
