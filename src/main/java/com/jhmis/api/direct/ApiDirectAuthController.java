package com.jhmis.api.direct;

import com.alibaba.fastjson.JSON;
import com.haier.http.HttpClientHelper;
import com.haier.webservices.client.mdmTob2b.source.MdmSourceApi;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.entity.MdmSourceReturn;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.MdmCustomersPartnerService;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Api(value = "ApiDirectAuthController", description = "直采账户信息")
@RestController
@RequestMapping("/api/direct/auth")
public class ApiDirectAuthController {
    protected Logger logger = LoggerFactory.getLogger(ApiDirectAuthController.class);
    //校验88码状态（0是无此88码用户，1是有此88码主数据但未注册,2是正常可登陆信息）
    public static final String CHECKFLAG_NONE = "0";
    public static final String CHECKFLAG_HAVE = "1";
    public static final String CHECKFLAG_SUCCESS = "2";
    public static final String CHECKFLAG_SOTHER = "3";
    @Autowired
    private DealerService dealerService;

    @Resource
    private DealerAccountService dealerAccountService;

    @Autowired
    private MdmCustomersSourceService mdmCustomersSourceService;

    @Autowired
    private MdmCustomersPartnerService mdmCustomersPartnerService;

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;
    
    /**
     * 企业购在客户中心的应用系统AppID
     */
    @Value("${Jhmis.Client_Id}")
    protected String clientId;
    /**
     * 业购在客户中心的应用系统AppID的秘钥
     */
    @Value("${Jhmis.Client_Secret}")
    protected String clientSecret;
    /**
     * 通过code获取客户中心access_token接口地址
     */
    @Value("${Jsh.TokenUrl}")
    protected String tokenUrl;
    /**
     * 通过access_token获取客户中心客户信息接口地址
     */
    @Value("${Jsh.AccountUrl}")
    protected String accountUrl;
    
    /*
	 * 随机生成一个8位验证码
     */
    @ApiOperation(notes = "getCode", httpMethod = "GET", value = "获取8位验证码")
    @RequestMapping("/getCode")
    public AjaxJson getCode() {
    	int n = 8;
		String string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//保存数字0-9 和 大小写字母
		char[] ch = new char[n]; //声明一个字符数组对象ch 保存 验证码
		for (int i = 0; i < n; i++) {
			Random random = new Random();//创建一个新的随机数生成器
			int index = random.nextInt(string.length());//返回[0,string.length)范围的int值    作用：保存下标
			ch[i] = string.charAt(index);//charAt() : 返回指定索引处的 char 值   ==》保存到字符数组对象ch里面
		}
		return AjaxJson.ok(String.valueOf(ch));
	}
    
    /**
     * 校验8码是否封户
     */
    @ApiOperation(notes = "check8Code", httpMethod = "GET", value = "检查8码是否有效")
    @ApiImplicitParams({@ApiImplicitParam(name = "cuscode", value = "88码(88码用户)", required = true, paramType = "query", dataType = "String")})
    @RequestMapping("/check8Code")
    public AjaxJson check8Code(String cuscode) {
    	
    	if(cuscode.length() >1){
            if("8".equals(cuscode.substring(0,1))){
                //校验88码状态，如果DELETE_FLAG 是1 提示账号异常
            	MdmSourceReturn mdmSourceReturn = MdmSourceApi.addSourceFromMdm(cuscode);
                if(StringUtils.isNotEmpty(mdmSourceReturn.getMdmCustomersSource().getDeleteFlag()) && "1".equals(mdmSourceReturn.getMdmCustomersSource().getDeleteFlag())){
                    return  AjaxJson.fail("账号异常，请联系工贸业务接口人！");
                }
            }
        }
    	
    	return AjaxJson.ok("账号正常");
    	
    }

    /**
     * 校验88码是否存在
     * @return
     */
    @ApiOperation(notes = "checkCusCode", httpMethod = "GET", value = "直采登录校验88码")
    @ApiImplicitParams({@ApiImplicitParam(name = "cuscode", value = "88码(88码用户)", required = true, paramType = "query", dataType = "String")})
    @RequestMapping("/checkCusCode")
    public AjaxJson checkCusCode(String cuscode) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectAuthController  /check88code  直采登录校验88码----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(cuscode)){
            return AjaxJson.fail("参数错误！");
        }
        Map<String, Object> map = new HashMap<>();
        MdmSourceReturn mdmSourceReturn = null;
        //如果是8码登录，只验证8码 是防止有89开头的
        if(cuscode.length() >1){
            if("8".equals(cuscode.substring(0,1))){
                //校验88码状态，如果DELETE_FLAG 是1 提示账号异常
                 mdmSourceReturn = MdmSourceApi.addSourceFromMdm(cuscode);
                if(StringUtils.isNotEmpty(mdmSourceReturn.getMdmCustomersSource().getDeleteFlag()) && "1".equals(mdmSourceReturn.getMdmCustomersSource().getDeleteFlag())){
                    return  AjaxJson.fail("账号异常，请联系工贸业务接口人！");
                }
            }
        }
        //校验信息
        DealerAccount dealerAccount = dealerAccountService.findUniqueByProperty("login_name",cuscode);
        //如果没有用户名密码
        if(dealerAccount==null){
            // 再去校验是否在MDM主数据中
            MdmCustomersSource mdmCustomersSource = new MdmCustomersSource();
            mdmCustomersSource.setCusCode(cuscode);
            List<MdmCustomersSource> listMdmCustomersSource = mdmCustomersSourceService.findList(mdmCustomersSource);
            //如果主数据为空
            if(listMdmCustomersSource==null || listMdmCustomersSource.size()==0){

                //校验一下视图中数据
                if(mdmSourceReturn == null){
                    mdmSourceReturn = MdmSourceApi.addSourceFromMdm(cuscode);
                }
                if("S".equals(mdmSourceReturn.getOutRetcode())){
                    mdmCustomersSourceService.save(mdmSourceReturn.getMdmCustomersSource());
                }else if("E".equals(mdmSourceReturn.getOutRetcode())){
                    if("取数条件内无数据".equals(mdmSourceReturn.getOutRetmsg())){
                        mdmSourceReturn.setOutRetmsg("请确认8码申请流程结束，检查8码是否有误！");
                    }
                    map.put("msg",mdmSourceReturn.getOutRetmsg());
                    map.put("flag",ApiDirectAuthController.CHECKFLAG_NONE);
                    return AjaxJson.ok(map);
                }else{
                    map.put("msg","请联系管理员");
                    map.put("flag",ApiDirectAuthController.CHECKFLAG_NONE);
                    return AjaxJson.ok(map);
                }

            }
            map.put("msg","请设置密码");
            map.put("flag",ApiDirectAuthController.CHECKFLAG_HAVE);
            return AjaxJson.ok(map);
        }
        map.put("msg","请登录");
        map.put("flag",ApiDirectAuthController.CHECKFLAG_SUCCESS);
        return AjaxJson.ok(map);
    }

    /**
     * 完善供应商信息
     * @param cuscode
     * @param password
     * @param spassword
     * @param mobile
     * @return
     */
    @ApiOperation(notes = "perfect", httpMethod = "POST", value = "完善供应商信息(录入密码及手机号)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cuscode", value = "88码(88码用户)", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "spassword", value = "确认密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/perfect")
    public AjaxJson perfect(String cuscode,String password,String spassword,String mobile){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectAuthController  /perfect  完善供应商信息----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(cuscode)||StringUtils.isEmpty(password)||StringUtils.isEmpty(spassword)||StringUtils.isEmpty(mobile)){
            return AjaxJson.fail("参数错误！");
        }
        //首先检验是否存在
        Dealer dealer = dealerService.findUniqueByProperty("company_code",cuscode);
        if(dealer!=null){
            return AjaxJson.fail("请直接登录");
        }
        // 再去校验是否在MDM主数据中
        MdmCustomersSource mdmCustomersSource = new MdmCustomersSource();
        mdmCustomersSource.setCusCode(cuscode);
        List<MdmCustomersSource> listMdmCustomersSource = mdmCustomersSourceService.findList(mdmCustomersSource);
        if(listMdmCustomersSource==null && listMdmCustomersSource.size()==0){
            MdmSourceReturn mdmSourceReturn = MdmSourceApi.addSourceFromMdm(cuscode);
            if("S".equals(mdmSourceReturn.getOutRetcode())){
                mdmCustomersSource = mdmSourceReturn.getMdmCustomersSource();
                mdmCustomersSourceService.save(mdmCustomersSource);
                mdmCustomersSource.setComName(mdmCustomersSource.getCusAbbName());
                //加入大渠道
                mdmCustomersSource.setCustomerCategory(mdmCustomersSource.getCustomerCategory());
                //加入小渠道
                mdmCustomersSource.setIndustryClass(mdmCustomersSource.getIndustryClass());
                if(StringUtils.isNotBlank(mdmCustomersSource.getMdmArea())){
                	mdmCustomersSource.setMdmProvince(mdmCustomersSource.getMdmProvince());
                    mdmCustomersSource.setMdmCity(mdmCustomersSource.getMdmCity());
                    mdmCustomersSource.setMdmArea(mdmCustomersSource.getMdmArea());
                }             
            }else if("E".equals(mdmSourceReturn.getOutRetcode())){
                if("取数条件内无数据".equals(mdmSourceReturn.getOutRetmsg())){
                    mdmSourceReturn.setOutRetmsg("请确认8码申请流程结束，检查8码是否有误！");
                }
                return AjaxJson.fail(mdmSourceReturn.getOutRetmsg());
            }else{
                return AjaxJson.fail("请联系管理员");
            }

        }else if(listMdmCustomersSource.size()>0){
            mdmCustomersSource = listMdmCustomersSource.get(0);
            mdmCustomersSource.setComName(mdmCustomersSource.getCusAbbName());
        }
		
        //校验前后密码是否一致
        if(!password.trim().equals(spassword.trim())){
            return AjaxJson.fail("两次密码输入不正确");
        }
        //完善去其数据
        Boolean flag = mdmCustomersSourceService.perfectInfo(mdmCustomersSource,password,mobile);
        if(flag){
            return AjaxJson.ok("完善信息成功");
        }else{
            return AjaxJson.fail("完善信息失败");
        }
    }




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
     * 对接客户中心登录
     * @return
     */
    @ApiOperation(notes = "loginTransfer", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/loginTransfer")
    public AjaxJson loginTransfer(String code){
    	if(StringUtils.isBlank(code)){
            return AjaxJson.fail("参数为空");
        }
    	logger.info("*_*_*_*_*_*_*_*_*_*loginTransfer  code=" + code + "*_*_*_*_*_*_*_*_*_*");
        
//      https://c.jsh.com/open/oauth2/token?grant_type=authorization_code&code=CODE&client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET
//        String paramter = "grant_type=authorization_code&code="+ code +"&client_id="+ clientId +"&client_secret=" + clientSecret;
       
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("grant_type","authorization_code");
        paramsMap.put("code",code);
        paramsMap.put("client_id",clientId);
        paramsMap.put("client_secret",clientSecret);

        try {
        	logger.info("*_*_*_*_*_*_*_*_*_*loginTransfer  paramsMap=" + paramsMap + "*_*_*_*_*_*_*_*_*_*");          
            //通过code获取access_token
            Object returnObject = HttpClientHelper.sendPost(tokenUrl,paramsMap);           
            String returnStr = returnObject.toString();
            logger.info("*_*_*_*_*_*_*_*_*_*loginTransfer  returnStr:" + returnStr + "*_*_*_*_*_*_*_*_*_*");
            
            if(StringUtils.isNotBlank(returnStr)){
                Map<String,Object> tokenMap = JSON.parseObject(returnStr,Map.class);
                if(tokenMap != null){
                    String access_token = tokenMap.get("access_token").toString();
                    logger.info("*_*_*_*_*_*_*_*_*_*loginTransfer  access_token:" + access_token + "*_*_*_*_*_*_*_*_*_*");
                    
                    if(StringUtils.isNotBlank(access_token)){
                        //通过access_token获取客户信息
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("access_token",access_token);
                        Object customerInfo = HttpClientHelper.sendGet(accountUrl,map);
                        String customerStr = customerInfo.toString();
                        logger.info("*_*_*_*_*_*_*_*_*_*loginTransfer  customerStr:" + customerStr + "*_*_*_*_*_*_*_*_*_*");
//                        {"id":8700221225,"name":"8700014608","nickname":"","phoneNumber":"15892382355","phoneVerified":false,"sex":0,"headImgUrl":null,"address":null,"status":0,"email":"","emailVerified":false,"position":null,"empno":null,"createdAt":1572571253000,"updatedAt":1572571253000,"lastLoginAt":1572571253000,"customers":[{"id":"8700014608","isPrimary":true,"isSocial":false}]}
                        if(StringUtils.isNotBlank(customerStr)){
                            Map<String,Object> customerMap = JSON.parseObject(customerStr,Map.class);
                            if(customerMap != null){
                                String customersStr = customerMap.get("customers").toString();
                                String phoneNumber = customerMap.get("phoneNumber").toString();
                                List list = JSON.parseObject(customersStr,List.class);
                                if(CollectionUtils.isNotEmpty(list)){
                                    Map<String,Object> customersMap = JSON.parseObject(list.get(0).toString(),Map.class);
                                    if(customersMap != null){
                                    	String name = customersMap.get("id").toString();
                                    	return dealerAccountService.loginTransfer(name,phoneNumber);
                                    }else{
                                    	return AjaxJson.fail("8码无客单报备权限");
                                    }
                                }
                            }
                        }
                    }
                }
            }else {
                return AjaxJson.fail("获取token接口返回失效");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.fail("接口错误");
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


    public boolean mdmSourcePull(String cusCode){
        final int[] count = {0};
        //mdm数据源
        String sql = "select b.SALES_GROUP as ComCode,  a.CUSTOMER_NUMBER as CusCode, a.ACCOUNT_GROUP as ACCOUNT_GROUP, A.market_area, e.Value_Meaning AS COMNAME,  CASE WHEN a.CUSTOMER_NAME IS NULL THEN a.CUSTOMER_NAME1 ELSE a.CUSTOMER_NAME END AS CusName, a.CUSTOMER_NAME1 as  CusAbbName, NULL as  OldCustCode, a.INDUSTRY_CLASS as  Kind, F.VALUE_MEANING as KindName, a.CITY_STREET_ROOM as  Address , a.STREET_ROOM as  Street , a.POSTAL_CODE  as  Post, a.NAME1 as  Linkman, a.PHONE_NUMBER as  Tel, a.FAX_NUMBER as  Fax, NULL as  E_Mail, NULL as  BankL,  NULL as  BankN, NULL as  BankA , NULL as  OrgID, a.TAX_CODE as  Tax, NULL as  EnrBankroll, NULL as  CreditGrade, NULL as  Channel, NULL as  AreaName, NULL as  SysTrade, a.CREATED_BY  as  Create_By, a.CREATED as  ImportDate, sysdate as SysDates, a.LAST_UPD_BY as  Alter_By, a.LAST_UPD as  Alter_Date, a.ACTIVE_FLAG as  ACTIVE_FLAG, a.LOCK_FLAG as  LOCK_FLAG, a.DELETE_FLAG as  DELETE_FLAG, a.OPERATE_STATUS as  OPERATE_STATUS, a.REMARK  as  MEMO from haiermdm.hrgc_hm_customers a  INNER join haiermdm.hrgc_hm_customer_sales_data b on b.CUSTOMER_ID =a.ROW_ID   INNER join haiermdm.hm_value_set e on e.value=b.SALES_GROUP and e.value_set_id='CompanyCode'   left join  haiermdm.hrgc_hm_cust_additional_data d on d.CUSTOMER_ID =a.ROW_ID left join  haiermdm.hm_value_set f on f.value=A.INDUSTRY_CLASS and f.value_set_id='IndustryClass'  where 1=1 and a.FOR_GVS=1  AND A.DELETE_FLAG=0   and a.PARTNER_FLAG_SP=1 and a.CUSTOMER_NUMBER = '"+ cusCode+"'";
        List<Map<String, Object>> resObj = (List<Map<String, Object>>) jdbcTemplate1.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(sql);
            }
        }, new PreparedStatementCallback<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.execute();
                ResultSet rs = null;
                try{
                    rs = ps.getResultSet();
                    while(rs.next()){
                        count[0]++;
                        MdmCustomersSource m = new MdmCustomersSource();
                        m.setComCode(rs.getString(1));
                        m.setCusCode(rs.getString(2));
                        m.setAccountGroup(rs.getString(3));
                        m.setMarketArea(rs.getString(4));
                        m.setComName(rs.getString(5));
                        m.setCusName(rs.getString(6));
                        m.setCusAbbName(rs.getString(7));
                        m.setOldCustCode(rs.getString(8));
                        m.setKind(rs.getString(9));
                        m.setKindName(rs.getString(10));
                        m.setAddress(rs.getString(11));
                        m.setStreet(rs.getString(12));
                        m.setPost(rs.getString(13));
                        m.setLinkman(rs.getString(14));
                        m.setTel(rs.getString(15));
                        m.setFax(rs.getString(16));
                        m.setEMail(rs.getString(17));
                        m.setBankL(rs.getString(18));
                        m.setBankN(rs.getString(19));
                        m.setBankA(rs.getString(20));
                        m.setOrgId(rs.getString(21));
                        m.setTax(rs.getString(22));
                        m.setEnrBankroll(rs.getString(23));
                        m.setCreditGrade(rs.getString(24));
                        m.setChannel(rs.getString(25));
                        m.setAreaName(rs.getString(26));
                        m.setSysTrade(rs.getString(27));
                        m.setImportDate(rs.getDate(29));
                        m.setSysDates(rs.getDate(30));
                        m.setAlterBy(rs.getString(31));
                        m.setAlterDate(rs.getDate(32));
                        m.setActiveFlag(rs.getString(33));
                        m.setLockFlag(rs.getString(34));
                        m.setDelFlag(rs.getString(35));
                        m.setOperateStatus(rs.getString(36));
                        m.setMemo(rs.getString(37));
                        mdmCustomersSourceService.save(m);
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                }finally {
                    rs.close();
                }
                return null;
            }
        } );
        String sql2 = "select pp.CUSTOMER_NUMBER  ,pp.CUSTOMER_NAME1   ,pp.CUST_PARTNER_TYPE   ,pp.CUST_PARTNER_SUBJECT_ID    ,pp.SUB_CUSTOMER_NAME1         ,pp.SALES_GROUP                ,pp.PARTNER_ROW_ID AS PARTNER_ROW_ID  ,PP.LAST_UPD AS LAST_UPD  ,pp.CUSTOMER_FLAG  ,pp.cust_delete_flag  ,pp.sales_delete_flag  ,pp.partner_delete_flag ,pp.sub_customer_delete_flag from haiermdm.view_cust_partner_all pp where 1=1 and ( exists(  select *  from haiermdm.view_cust_partner_all p where p.customer_number = p.cust_partner_subject_id  and p.cust_partner_type = 'SP'  and p.cust_delete_flag= '0'  and p.sales_delete_flag= '0' and p.partner_delete_flag = '0' and p.sub_customer_delete_flag='0' and p.customer_number =pp.customer_number  )  )  and pp.cust_delete_flag= '0' and pp.sales_delete_flag= '0'  and pp.partner_delete_flag = '0'  and pp.sub_customer_delete_flag='0' AND pp.CUSTOMER_NUMBER = '"+cusCode+"'";

        List<Map<String, Object>> resObj2 = (List<Map<String, Object>>) jdbcTemplate1.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(sql);
            }
        }, new PreparedStatementCallback<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.execute();
                ResultSet rs = null;
                try{
                    rs = ps.getResultSet();
                    while(rs.next()){
                        count[0]++;
                        MdmCustomersPartner mdmCustomersPartner = new MdmCustomersPartner();
                        mdmCustomersPartner.setCustomerNumber(rs.getString(1));
                        mdmCustomersPartner.setCustomerName1(rs.getString(2));
                        mdmCustomersPartner.setCustPartnerType(rs.getString(3));
                        mdmCustomersPartner.setCustPartnerSubjectId(rs.getString(4));
                        mdmCustomersPartner.setSubCustomerName1(rs.getString(5));
                        mdmCustomersPartner.setSalesGroup(rs.getString(6));
                        mdmCustomersPartner.setPartnerRowId(rs.getString(7));
                        mdmCustomersPartner.setLastUpd(rs.getDate(8));
                        mdmCustomersPartner.setCustomerFlag(rs.getString(9));
                        mdmCustomersPartner.setCustDeleteFlag(rs.getString(10));
                        mdmCustomersPartner.setSalesDeleteFlag(rs.getString(11));
                        mdmCustomersPartner.setPartnerDeleteFlag(rs.getString(12));
                        mdmCustomersPartner.setSubCustomerDeleteFlag(rs.getString(13));
                        mdmCustomersPartnerService.save(mdmCustomersPartner);
                    }
                }catch (Exception e){
                    logger.error(e.getMessage());
                }finally {
                    rs.close();
                }

                return null;
            }
        } );
        System.out.println(count);
        if(count[0]>1){
            return true;
        }
        return false;
    }
}
