package com.haier.webservices.client.hps.project;

import com.alibaba.fastjson.JSONArray;
import com.jhmis.api.customer.ApiCustomerController;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.entity.CustomerMsgProduct;
import com.jhmis.modules.customer.entity.CustomerQuotes;
import com.jhmis.modules.customer.entity.CustomerSearch;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.entity.shopmsgproduct.ShopMsgProduct;
import net.sf.json.JSONObject;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HpsApi {
	static Logger logger = LoggerFactory.getLogger(ApiCustomerController.class);

    static URL wsdlLocation;

    static {
        try {          
            wsdlLocation = new URL(Constants.HPS_URL + "/soap/project?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void testAA(List<ProjectSaveParam> listProjectSaveParam){
    	ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
    	String res = ser.getProjectWebServiceServerImplPort().saveProjectFromQYGNew(listProjectSaveParam);
    }
    
     
    public static List<EnterpriseInfoVO> queryEnterpriseList(String keys){
        ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
        QueryProjectManagerFromHPS queryProjectManagerFromHPS = new QueryProjectManagerFromHPS();
        EnterpriseOtherParam eop = new EnterpriseOtherParam();
        eop.setKeyword(keys);
        List<EnterpriseInfoVO> listEnterpriseInfoVO = ser.getProjectWebServiceServerImplPort().queryEnterpriseList(eop);
        return listEnterpriseInfoVO;
    }
    
     
    public static List<LockUserVO> lockUserList(String key){
        ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
        List<LockUserVO> lockUsers = ser.getProjectWebServiceServerImplPort().lockUserList(key);
        return lockUsers;
    }
    
    public static ProjectDetailInfoQYGVO projectDetailInfoList(CustomerSearch customerSearch) throws ParseException{
        ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
        
        ProjectDetailOtherParam projectDetailOtherParam = new ProjectDetailOtherParam();
        if(StringUtils.isNotBlank(customerSearch.getCusCode())){
        	projectDetailOtherParam.setCusCode(customerSearch.getCusCode());
        }
        if(StringUtils.isNotBlank(customerSearch.getNodename())){
        	projectDetailOtherParam.setNodename(customerSearch.getNodename());
        }
        if(StringUtils.isNotBlank(customerSearch.getOperateStatus())){
        	projectDetailOtherParam.setOperateStatus(customerSearch.getOperateStatus());
        }
        if(StringUtils.isNotBlank(customerSearch.getPageNo())){
        	projectDetailOtherParam.setPageNo(customerSearch.getPageNo());
        }
        if(StringUtils.isNotBlank(customerSearch.getPageSize())){
        	projectDetailOtherParam.setPageSize(customerSearch.getPageSize());
        }
        if(StringUtils.isNotBlank(customerSearch.getProjectName())){
        	projectDetailOtherParam.setProjectName(customerSearch.getProjectName());
        }
        if(StringUtils.isNotBlank(customerSearch.getCreateBeginDate())){
        	projectDetailOtherParam.setCreateBeginDate(dateToXmlDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(customerSearch.getCreateBeginDate())));
        }
        if(StringUtils.isNotBlank(customerSearch.getCreateEndDate())){
        	projectDetailOtherParam.setCreateEndDate(dateToXmlDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(customerSearch.getCreateEndDate())));
        }
        
        String json = JSONObject.fromObject(projectDetailOtherParam).toString();
        logger.info("*_*_*_*_*_*_*_*_*_* 工程需求申请接口给hps传递的参数="+ json +" *_*_*_*_*_*_*_*_*_*");
        
        ProjectDetailInfoQYGVO projectDetailInfoQYGVO = ser.getProjectWebServiceServerImplPort().queryProjectDetailList(projectDetailOtherParam);
        return projectDetailInfoQYGVO;
    }
    

    public static void main(String[] args) {
        //queryEnterpriseList();
    }

     
    public static List<UserDTO> queryProjectManagerFromHPS(String projectManagerCenter,String productGroup,String domainModel,String bigAndSmall){
        ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
        QueryProjectManagerFromHPS queryProjectManagerFromHPS = new QueryProjectManagerFromHPS();
        ProjectROneSaveParam prone = new ProjectROneSaveParam();
        prone.setProjectManagerCenter(projectManagerCenter);
        //直单（DIRECTIVD_ORDER） 客单(CUSTOMER_ORDER)
        prone.setDomainModel(domainModel);
        //处理多个产品组
        String []argsProductGroup = productGroup.split(",");
        List<ProjectPurchaseForecastSaveParam> listprojectPurchaseForecastSaveParam = new ArrayList<>();
        for(int i=0; i<argsProductGroup.length; i++){
            ProjectPurchaseForecastSaveParam projectPurchaseForecastSaveParam = new ProjectPurchaseForecastSaveParam();
            projectPurchaseForecastSaveParam.setProductGroup(argsProductGroup[i]);
            listprojectPurchaseForecastSaveParam.add(projectPurchaseForecastSaveParam);
        }
        prone.getPurchaseForecastList().addAll(listprojectPurchaseForecastSaveParam);
        if(StringUtils.isNotBlank(bigAndSmall)){
        	prone.setBigAndSmall(bigAndSmall);
        }
        queryProjectManagerFromHPS.setArg0(prone);
        List<UserDTO> listUserDTO = ser.getProjectWebServiceServerImplPort().queryProjectManagerFromHPS(prone);
        return listUserDTO;
    }


     
    
   public static Map<String,Object>  SaveProjectFromQYG(CustomerMsg customerMsg){
  	
   	Map<String,Object> map = new HashMap<>();
       try{
	        ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
	        QueryProjectManagerFromHPS queryProjectManagerFromHPS = new QueryProjectManagerFromHPS();
	        ProjectROneSaveParam rone = new ProjectROneSaveParam();
	        //需求ID msgId
	        rone.setMsgId(customerMsg.getMsgId());
	        //中心录入
	        rone.setCenter(customerMsg.getCenter());
	        //项目名称（公司名+时间戳）
	        rone.setProjectName(customerMsg.getProjectName());
	        //甲方公司名称
	        rone.setFirstCompanyName(customerMsg.getFirstCompanyName());
	        //组织机构代码证
	        rone.setFirstCompanyOrgCode(customerMsg.getFirstCompanyOrgCode());
	        //项目录入人编码
	        rone.setProjectCreaterCode(customerMsg.getProjectCreaterCode());
	        rone.setProjectManagerCode(customerMsg.getProjectCreaterCode());
	        //产业模式（客单和直单 --- 直单:DIRECTIVD_ORDER,客单:CUSTOMER_ORDER）
	        rone.setDomainModel("CUSTOMER_ORDER");
	        //省市区对应编码
	        rone.setAddressProvince(customerMsg.getAddressProvince());
	        rone.setAddressCity(customerMsg.getAddressCity());
	        rone.setAddressCounty(customerMsg.getAddressCounty());
	        rone.setAddressDetail(customerMsg.getAddressDetail());
	        //数据来源（工程平台自创建和企业购传入） 默认企业购
	        rone.setDataSource("企业购");
	        //产业类型(大客户、商空、冰箱等) 根据产品组匹配
	        rone.setDomainType("");
	        //预计投标日期
	        if(null!=customerMsg.getEstimatedTimeBid()){
	            rone.setEstimatedTimeBid(dateToXmlDate(customerMsg.getEstimatedTimeBid()));
	        }
	        //B2B对应漏斗编码：GN_PRJ_08(文档中说不必填但是需要必填)
	        //todo GN_PRJ_15 经销商自主报备
	        rone.setProjectSource("GN_PRJ_15");
	        //是否战略(文档中说不必填但是需要必填)
	        rone.setBeStrategy(false);
	        //甲方联系人姓名
	        //产品组
	        List<ProjectPurchaseForecastSaveParam> listprojectPurchaseForecastSaveParam = new ArrayList<>();
	        List<CustomerMsgProduct> listCustomerMsgProduct = customerMsg.getListCustomerMsgProduct();
	        for(int i=0; i<listCustomerMsgProduct.size(); i++){
	            ProjectPurchaseForecastSaveParam projectPurchaseForecastSaveParam = new ProjectPurchaseForecastSaveParam();
	            projectPurchaseForecastSaveParam.setProductGroup(listCustomerMsgProduct.get(i).getProductGroup());
	            projectPurchaseForecastSaveParam.setPurchaseBudget(new BigDecimal(listCustomerMsgProduct.get(i).getPurchaseBudget()));
	            projectPurchaseForecastSaveParam.setEstimatedQuantity(Long.parseLong(listCustomerMsgProduct.get(i).getEstimatedQuantity()));
	            if("0".equals(listCustomerMsgProduct.get(i).getBeWisdom())){
	                projectPurchaseForecastSaveParam.setBeWisdom(true);
	            }else if("1".equals(listCustomerMsgProduct.get(i).getBeWisdom())){
	                projectPurchaseForecastSaveParam.setBeWisdom(false);
	            }
	            listprojectPurchaseForecastSaveParam.add(projectPurchaseForecastSaveParam);
	        }
	        rone.getPurchaseForecastList().addAll(listprojectPurchaseForecastSaveParam);
	        rone.setProjectManagerCenter(customerMsg.getCenter());
	        rone.setProductSeriesOneCode(customerMsg.getProductSeriesOneCode());
	        rone.setProductSeriesOneName(customerMsg.getProductSeriesOneName());
	        rone.setProductSeriesTwoCode(customerMsg.getProductSeriesTwoCode());
	        rone.setProductSeriesTwoName(customerMsg.getProductSeriesTwoName());
	        rone.setBrandInfoCode(customerMsg.getBrandInfoCode());
	        rone.setBrandInfoName(customerMsg.getBrandInfoName());
	        rone.setBigAndSmall(customerMsg.getBigOrSmall());
	        logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectROneSaveParam *_*_*_*_*_*_*_*_*_*");

	        ProjectRTwoSaveParam projectRTwoSaveParam = new ProjectRTwoSaveParam();
	        //甲方联系人姓名
	        projectRTwoSaveParam.setFirstContactName(customerMsg.getFirstContactName());
	        //职位
	        projectRTwoSaveParam.setPosition(customerMsg.getPosition());
	        //电话
	        projectRTwoSaveParam.setPhone(customerMsg.getPhone());
	        //项目所属行业门类
	        projectRTwoSaveParam.setIndustryHomeCategory(customerMsg.getIndustryHomeCategory());
	        //行业大类
	        projectRTwoSaveParam.setIndustryCategory(customerMsg.getIndustryCategory());
	        //机会点
	        projectRTwoSaveParam.setChancePoint(customerMsg.getChancePoint());
	        //用户群
	        projectRTwoSaveParam.setUserGroup(customerMsg.getUserGroup());
	        //锁定用户
	        projectRTwoSaveParam.setLockUser(customerMsg.getLockUser());
	        logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectRTwoSaveParam *_*_*_*_*_*_*_*_*_*");

	        //R3经销商编码
	        ProjectRThreeSaveParam rthree = new ProjectRThreeSaveParam();
	        rthree.setContractorCode(customerMsg.getContractorCode());
	        //承接商名称
	        rthree.setContractorName(customerMsg.getContractorName());
	        //客户需求分析
	        rthree.setCustomerDemandAnalysis(customerMsg.getCustomerDemandAnalysis());
	        logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectRThreeSaveParam *_*_*_*_*_*_*_*_*_*");

	        //R4经销商编码
	        ProjectRFourSaveParam projectRFourSaveParam = new ProjectRFourSaveParam();
	        //甲方招标文件
	        projectRFourSaveParam.setFirstBidDocument(customerMsg.getFirstBidDocument());
	        //是否送装一体
	        projectRFourSaveParam.setBeSendWithOne(customerMsg.getBeSendWithOne());
	        //是否售后招标
	        projectRFourSaveParam.setAfterSalesBidding(customerMsg.getAfterSalesBidding());
	        //是否直发工地
	        projectRFourSaveParam.setNeedDirectDeliverySite(customerMsg.getNeedDirectDeliverySite());
	        logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectRFourSaveParam *_*_*_*_*_*_*_*_*_*");

	        //R5经销商编码
	        ProjectRFiveSaveParam projectRFiveSaveParam = new ProjectRFiveSaveParam();
	        //预计开标时间
	        if(null!=customerMsg.getWinBidTime()){
	        	projectRFiveSaveParam.setWinBidTime(dateToXmlDate(customerMsg.getWinBidTime()));
	        }
	        //预计签约日期
	        if(null!=customerMsg.getEstimatedTimeSigning()){
	        	projectRFiveSaveParam.setEstimatedTimeSigning(dateToXmlDate(customerMsg.getEstimatedTimeSigning()));
	        }
	        //实际首次下单日期
	        if(null!=customerMsg.getEstimatedTimeDelivery()){
	        	projectRFiveSaveParam.setEstimatedTimeDelivery(dateToXmlDate(customerMsg.getEstimatedTimeDelivery()));
	        }
	        //是否中标
	        projectRFiveSaveParam.setBeWinBid(customerMsg.getBeWinBid());
	        logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - projectRFiveSaveParam *_*_*_*_*_*_*_*_*_*");

	        ContractSaveParam contractSaveParam = new ContractSaveParam();
	        //合同扫描件
	        contractSaveParam.setContractAttachement(customerMsg.getContractAttachement());
	        //签约日期
	        if(null!=customerMsg.getEstimatedTimeSigning()){
	        	contractSaveParam.setSignTime(dateToXmlDate(customerMsg.getEstimatedTimeSigning()));
	        }
	        logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ContractSaveParam *_*_*_*_*_*_*_*_*_*");

	        QuoteSaveParam quoteSaveParam = new QuoteSaveParam();
	        List<CustomerQuotes> listCustomerQuotes = customerMsg.getQuotes();
	        if(listCustomerQuotes != null && listCustomerQuotes.size()>0){
	        	int num = listCustomerQuotes.size();
		        List<QuoteProductSaveParam> quoteProductList = new ArrayList<QuoteProductSaveParam>();
		        for(int i=0; i<num; i++){
		        	QuoteProductSaveParam quoteProduct = new QuoteProductSaveParam();	
		        	//产业
		        	//quoteProduct.setDomainCode(listCustomerQuotes.get(i).getDomainType());
		        	//产品组
		        	//quoteProduct.setProductGroupCode (listCustomerQuotes.get(i).getProductGroup());
		        	//品牌
		        	//quoteProduct.setBrandName(listCustomerQuotes.get(i).getBrand());
		        	//报价
		        	quoteProduct.setProductQuote(listCustomerQuotes.get(i).getProductQuote());
		        	//产品编码
		        	quoteProduct.setProductCode(listCustomerQuotes.get(i).getProductCode());
		        	//数量
		        	quoteProduct.setDemandQuantity(listCustomerQuotes.get(i).getDemandQuantity());
		        	
		        	if(StringUtils.isBlank(quoteProduct.getProductCode())){
		        		continue;
		        	}
		        	quoteProductList.add(quoteProduct);
		        	
		        }
		        quoteSaveParam.getQuoteProductList().addAll(quoteProductList);
	        }
	        logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - QuoteSaveParam *_*_*_*_*_*_*_*_*_*");

	        List<ProjectSaveParam> listProjectSaveParam = new ArrayList<>();
	        ProjectSaveParam psp = new ProjectSaveParam();
	        psp.setProjectROneSaveParam(rone);
	        psp.setProjectRTwoSaveParam(projectRTwoSaveParam);
	        psp.setProjectRThreeSaveParam(rthree);
	        psp.setProjectRFourSaveParam(projectRFourSaveParam);
	        psp.setProjectRFiveSaveParam(projectRFiveSaveParam);
	        psp.setContractSaveParam(contractSaveParam);
	        psp.setQuoteSaveParam(quoteSaveParam);
	        listProjectSaveParam.add(psp);

	        String pspJson = JSONObject.fromObject(psp).toString();
	        logger.info("*_*_*_*_*_*_*_*_*_* 给hps传递的参数="+ pspJson +" *_*_*_*_*_*_*_*_*_*");

	        String res = ser.getProjectWebServiceServerImplPort().saveProjectFromQYGNew(listProjectSaveParam);
	        JSONObject resObj = JSONObject.fromObject(res);

	        //判断是否成功进入漏斗
	        String flag = (String) resObj.get("flag");
	        map.put("flag",flag);
	        if("error".equals(flag)){
	            //如果失败
	            map.put("msgId",resObj.get("msgId"));
	            map.put("errorMsg",resObj.get("errorMsg"));
	            logger.info("*_*_*_*_*_*_*_*_*_* hps返回报备错误码："+ resObj.get("errorMsg") +" *_*_*_*_*_*_*_*_*_*");

	        }else{
	            JSONArray successMsgArray = JSONArray.parseArray((String) resObj.get("successMsg"));
	            JSONObject successMsgArrayObj = JSONObject.fromObject(successMsgArray.get(0));
	            String projectCode = (String) successMsgArrayObj.get("projectCode");
	            String msgId = (String) successMsgArrayObj.get("msgId");
	            String loginLevel = (String) successMsgArrayObj.get("loginLevel");
	            String info = (String) successMsgArrayObj.get("info");
	            map.put("projectCode",projectCode);
	            map.put("msgId",msgId);
	            map.put("loginLevel",loginLevel);
	            map.put("info", info);
	        }
       }catch (Exception e){
           map.put("flag","error");
           map.put("errorMsg","请联系业务人员！");
           return map;
       }
       return map;
   }
   
    
   public static Map<String,Object>  updateProjectFromQYG(CustomerMsg customerMsg){
  	
   	Map<String,Object> map = new HashMap<>();
       try{
	        ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
	      
	        ProjectROneSaveParam rone = new ProjectROneSaveParam();
	        //需求ID msgId
	        rone.setMsgId(customerMsg.getMsgId());
	        //预计投标日期
	        if(null!=customerMsg.getEstimatedTimeBid()){
	            rone.setEstimatedTimeBid(dateToXmlDate(customerMsg.getEstimatedTimeBid()));
	        }
	        
	        logger.info("*_*_*_*_*_*_*_*_*_* updateProjectFromQYG - ProjectROneSaveParam*_*_*_*_*_*_*_*_*_*");
	        
	        ProjectRTwoSaveParam projectRTwoSaveParam = new ProjectRTwoSaveParam();	        
	        //项目所属行业门类
	        projectRTwoSaveParam.setIndustryHomeCategory(customerMsg.getIndustryHomeCategory());
	        //行业大类
	        projectRTwoSaveParam.setIndustryCategory(customerMsg.getIndustryCategory());
	        //机会点
	        projectRTwoSaveParam.setChancePoint(customerMsg.getChancePoint());
	        //锁定用户
	        projectRTwoSaveParam.setLockUser(customerMsg.getLockUser());
	        logger.info("*_*_*_*_*_*_*_*_*_* updateProjectFromQYG - ProjectRTwoSaveParam*_*_*_*_*_*_*_*_*_*");
	       
	        //R4经销商编码
	        ProjectRFourSaveParam projectRFourSaveParam = new ProjectRFourSaveParam();	        
	        //是否售后招标
	        projectRFourSaveParam.setAfterSalesBidding(customerMsg.getAfterSalesBidding());
	        //是否直发工地
	        projectRFourSaveParam.setNeedDirectDeliverySite(customerMsg.getNeedDirectDeliverySite());
	        logger.info("*_*_*_*_*_*_*_*_*_* updateProjectFromQYG - ProjectRFourSaveParam*_*_*_*_*_*_*_*_*_*");
	        
	        ContractSaveParam contractSaveParam = new ContractSaveParam();
	        //合同扫描件
	        contractSaveParam.setContractAttachement(customerMsg.getContractAttachement());
	        //签约日期
	        if(null!=customerMsg.getEstimatedTimeSigning()){
	        	contractSaveParam.setSignTime(dateToXmlDate(customerMsg.getEstimatedTimeSigning()));
	        }
	        logger.info("*_*_*_*_*_*_*_*_*_* updateProjectFromQYG - ContractSaveParam*_*_*_*_*_*_*_*_*_*");
	        
	        QuoteSaveParam quoteSaveParam = new QuoteSaveParam();
	        List<CustomerQuotes> listCustomerQuotes = customerMsg.getQuotes();
	        if(listCustomerQuotes != null && listCustomerQuotes.size()>0){
	        	int num = listCustomerQuotes.size();
		        List<QuoteProductSaveParam> quoteProductList = new ArrayList<QuoteProductSaveParam>();
		        for(int i=0; i<num; i++){
		        	QuoteProductSaveParam quoteProduct = new QuoteProductSaveParam();
		        	quoteProduct.setDomainCode(listCustomerQuotes.get(i).getDomainType());
		        	quoteProduct.setProductGroupCode (listCustomerQuotes.get(i).getProductGroup());
		        	quoteProduct.setBrandName(listCustomerQuotes.get(i).getBrand());
		        	//报价
		        	quoteProduct.setProductQuote(listCustomerQuotes.get(i).getProductQuote());
		        	//产品编码
		        	quoteProduct.setProductCode(listCustomerQuotes.get(i).getProductCode());
		        	//数量
		        	quoteProduct.setDemandQuantity(listCustomerQuotes.get(i).getDemandQuantity());
		        	//quoteProduct.setBeWisdom(listCustomerQuotes.get(i).getBeWisdom());		        	
		        	if(StringUtils.isBlank(quoteProduct.getProductCode())){
		        		continue;
		        	}
		        	quoteProductList.add(quoteProduct);
		        }
		        quoteSaveParam.getQuoteProductList().addAll(quoteProductList);
	        }
	        logger.info("*_*_*_*_*_*_*_*_*_* updateProjectFromQYG - QuoteSaveParam*_*_*_*_*_*_*_*_*_*");
	        
	        List<ProjectSaveParam> listProjectSaveParam = new ArrayList<>();
	        ProjectSaveParam psp = new ProjectSaveParam();
	        psp.setProjectROneSaveParam(rone);
	        psp.setProjectRTwoSaveParam(projectRTwoSaveParam);
	        psp.setProjectRFourSaveParam(projectRFourSaveParam);
	        psp.setContractSaveParam(contractSaveParam);
	        psp.setQuoteSaveParam(quoteSaveParam);
	        listProjectSaveParam.add(psp);

	        String pspJson = JSONObject.fromObject(psp).toString();
	        logger.info("*_*_*_*_*_*_*_*_*_* 给hps传递的二次编辑参数="+ pspJson +" *_*_*_*_*_*_*_*_*_*");

	        String res = ser.getProjectWebServiceServerImplPort().updateProjectFromQYGNew(listProjectSaveParam);
	        JSONObject resObj = JSONObject.fromObject(res);

	        //判断是否成功进入漏斗
	        String flag = (String) resObj.get("flag");
	        map.put("flag",flag);
	        if("error".equals(flag)){
	            map.put("errorMsg",resObj.get("errorMsg"));
	            logger.info("*_*_*_*_*_*_*_*_*_* hps返回二次编辑错误码："+ resObj.get("errorMsg") +" *_*_*_*_*_*_*_*_*_*");

	        }else{
	            JSONArray successMsgArray = JSONArray.parseArray((String) resObj.get("successMsg"));
	            JSONObject successMsgArrayObj = JSONObject.fromObject(successMsgArray.get(0));
	            String projectCode = (String) successMsgArrayObj.get("projectCode");
	            //String msgId = (String) successMsgArrayObj.get("msgId");
	            //String loginLevel = (String) successMsgArrayObj.get("loginLevel");
	            //String info = (String) successMsgArrayObj.get("info");
	            map.put("projectCode",projectCode);
	            //map.put("msgId",msgId);
	            //map.put("loginLevel",loginLevel);
	            //map.put("info", info);
	        }
       }catch (Exception e){
           map.put("flag","error");
           map.put("errorMsg","请联系业务人员！");
           return map;
       }
       return map;
   }

    private static ProjectRTwoSaveParam setChancePoint(String chancePoint) {
		// TODO Auto-generated method stub
		return null;
	}

	public static XMLGregorianCalendar dateToXmlDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
        }
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        //由于Calendar.MONTH取值范围为0~11,需要加1
        dateType.setMonth(cal.get(Calendar.MONTH)+1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));
        return dateType;
    }




	 
	public static Map<String,Object>  processSaveProject(ShopMsgCustcodeOrder shopMsgCustcodeOrder, ShopMsg shopMsg, List<ShopMsgProduct> listShopMsgProduct) throws ShopMsgException {

		Map<String,Object> map = new HashMap<>();
			ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
			QueryProjectManagerFromHPS queryProjectManagerFromHPS = new QueryProjectManagerFromHPS();
			ProjectROneSaveParam rone = new ProjectROneSaveParam();
			//需求ID msgId
			rone.setMsgId(shopMsg.getId());
			//中心录入
			rone.setCenter(shopMsg.getOrgId());
			//项目名称（公司名+时间戳）
			rone.setProjectName(shopMsg.getCompanyName());
			//甲方公司名称
			rone.setFirstCompanyName(shopMsg.getCompanyOrgName());
			//组织机构代码证
			rone.setFirstCompanyOrgCode(shopMsg.getCompanyOrgCode());
			//项目录入人编码
			rone.setProjectCreaterCode(shopMsg.getManagerNo());
			rone.setProjectManagerCode(shopMsg.getManagerNo());
			//产业模式（客单和直单 --- 直单:DIRECTIVD_ORDER,客单:CUSTOMER_ORDER）
			rone.setDomainModel("CUSTOMER_ORDER");
			//省市区对应编码
			rone.setAddressProvince(shopMsg.getProvinceId());
			rone.setAddressCity(shopMsg.getCityId());
			rone.setAddressCounty(shopMsg.getDistricId());
			rone.setAddressDetail(shopMsg.getAddressDetail());
			//数据来源（工程平台自创建和企业购传入） 默认企业购
			rone.setDataSource("企业购");
			//产业类型(大客户、商空、冰箱等) 根据产品组匹配
			rone.setDomainType("");
			//B2B对应漏斗编码：GN_PRJ_08(文档中说不必填但是需要必填)
			//客户提报 GN_PRJ_08
			rone.setProjectSource("GN_PRJ_08");
			//是否战略(文档中说不必填但是需要必填)
			rone.setBeStrategy(false);
			//产品组
			List<ProjectPurchaseForecastSaveParam> listprojectPurchaseForecastSaveParam = new ArrayList<>();
			//产品组拆分数据
			for(int i=0; i<listShopMsgProduct.size(); i++){
			    String code[] = listShopMsgProduct.get(i).getProductGroupCode().split(",");
			    for (int j=0; j<code.length; j++){
                    ProjectPurchaseForecastSaveParam projectPurchaseForecastSaveParam = new ProjectPurchaseForecastSaveParam();
                    projectPurchaseForecastSaveParam.setProductGroup(code[j]);
                    projectPurchaseForecastSaveParam.setPurchaseBudget(new BigDecimal(listShopMsgProduct.get(i).getPurchaseBudget()));
                    projectPurchaseForecastSaveParam.setEstimatedQuantity(Long.parseLong(listShopMsgProduct.get(i).getEstimatedQuantity()));
                    projectPurchaseForecastSaveParam.setBeWisdom(false);
                    listprojectPurchaseForecastSaveParam.add(projectPurchaseForecastSaveParam);
                }
			}
			rone.getPurchaseForecastList().addAll(listprojectPurchaseForecastSaveParam);
			//中心
			rone.setProjectManagerCenter(shopMsg.getOrgId());
			ProjectRTwoSaveParam projectRTwoSaveParam = new ProjectRTwoSaveParam();
			//甲方联系人姓名
			projectRTwoSaveParam.setFirstContactName(shopMsg.getConnectName());
			//职位
			projectRTwoSaveParam.setPosition("");
			//电话
			projectRTwoSaveParam.setPhone(shopMsg.getMobile());
			//R3经销商编码
			ProjectRThreeSaveParam rthree = new ProjectRThreeSaveParam();
			//承接商编码
			rthree.setContractorCode(shopMsgCustcodeOrder.getCustCode());
			//承接商名称
			rthree.setContractorName(shopMsgCustcodeOrder.getCustName());
			List<ProjectSaveParam> listProjectSaveParam = new ArrayList<>();
			ProjectSaveParam psp = new ProjectSaveParam();
			psp.setProjectROneSaveParam(rone);
			psp.setProjectRThreeSaveParam(rthree);
			listProjectSaveParam.add(psp);
			//hps返回值
			String res = "";
			try{
				res = ser.getProjectWebServiceServerImplPort().saveProjectFromQYGNew(listProjectSaveParam);
			}catch (Exception e){
				throw new ShopMsgException("提交hps接口异常,原因: " + e.getMessage());
			}
			JSONObject resObj = JSONObject.fromObject(res);
			//判断是否成功进入漏斗
			String flag = (String) resObj.get("flag");
			map.put("flag",flag);
			if("error".equals(flag)){
				//如果失败
				map.put("msgId",resObj.get("msgId"));
				map.put("errorMsg",resObj.get("errorMsg"));
			}else{
				JSONArray successMsgArray = JSONArray.parseArray((String) resObj.get("successMsg"));
				JSONObject successMsgArrayObj = JSONObject.fromObject(successMsgArray.get(0));
				String projectCode = (String) successMsgArrayObj.get("projectCode");
				String msgId = (String) successMsgArrayObj.get("msgId");
				String loginLevel = (String) successMsgArrayObj.get("loginLevel");
				String info = (String) successMsgArrayObj.get("info");
				map.put("projectCode",projectCode);
				map.put("msgId",msgId);
				map.put("loginLevel",loginLevel);
				map.put("info", info);
			}

		return map;
	}

    public static Map<String,Object>  SaveEucProjectFromQYG(CustomerMsg customerMsg){

        Map<String,Object> map = new HashMap<>();
        try{
            ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
            QueryProjectManagerFromHPS queryProjectManagerFromHPS = new QueryProjectManagerFromHPS();
            ProjectROneSaveParam rone = new ProjectROneSaveParam();
            //需求ID msgId
            rone.setMsgId(customerMsg.getMsgId());
            //中心录入
            rone.setCenter(customerMsg.getCenter());
            //项目名称（公司名+时间戳）
            rone.setProjectName(customerMsg.getProjectName());
            //甲方公司名称
            rone.setFirstCompanyName(customerMsg.getFirstCompanyName());
            //组织机构代码证
            rone.setFirstCompanyOrgCode(customerMsg.getFirstCompanyOrgCode());
            //项目录入人编码
            rone.setProjectCreaterCode(customerMsg.getProjectCreaterCode());
            rone.setProjectManagerCode(customerMsg.getProjectCreaterCode());
            //产业模式（客单和直单 --- 直单:DIRECTIVD_ORDER,客单:CUSTOMER_ORDER）
            rone.setDomainModel("CUSTOMER_ORDER");
            //省市区对应编码
            rone.setAddressProvince(customerMsg.getAddressProvince());
            rone.setAddressCity(customerMsg.getAddressCity());
            rone.setAddressCounty(customerMsg.getAddressCounty());
            rone.setAddressDetail(customerMsg.getAddressDetail());
            //数据来源（工程平台自创建和企业购传入） 默认企业购
            rone.setDataSource("企业购");
            //产业类型(大客户、商空、冰箱等) 根据产品组匹配
            rone.setDomainType("");
            //预计投标日期
            if(null!=customerMsg.getEstimatedTimeBid()){
                rone.setEstimatedTimeBid(dateToXmlDate(customerMsg.getEstimatedTimeBid()));
            }
            //B2B对应漏斗编码：GN_PRJ_08(文档中说不必填但是需要必填)
             
            rone.setProjectSource(customerMsg.getProjectSource());
            //设置EUC商机编码
            rone.setEucId(customerMsg.getBusinessCode());
            //是否战略(文档中说不必填但是需要必填)
            rone.setBeStrategy(false);
            //甲方联系人姓名
            //产品组
            List<ProjectPurchaseForecastSaveParam> listprojectPurchaseForecastSaveParam = new ArrayList<>();
            List<CustomerMsgProduct> listCustomerMsgProduct = customerMsg.getListCustomerMsgProduct();
            for(int i=0; i<listCustomerMsgProduct.size(); i++){
                ProjectPurchaseForecastSaveParam projectPurchaseForecastSaveParam = new ProjectPurchaseForecastSaveParam();
                projectPurchaseForecastSaveParam.setProductGroup(listCustomerMsgProduct.get(i).getProductGroup());
                projectPurchaseForecastSaveParam.setPurchaseBudget(new BigDecimal(listCustomerMsgProduct.get(i).getPurchaseBudget()));
                projectPurchaseForecastSaveParam.setEstimatedQuantity(Long.parseLong(listCustomerMsgProduct.get(i).getEstimatedQuantity()));
                if("0".equals(listCustomerMsgProduct.get(i).getBeWisdom())){
                    projectPurchaseForecastSaveParam.setBeWisdom(true);
                }else if("1".equals(listCustomerMsgProduct.get(i).getBeWisdom())){
                    projectPurchaseForecastSaveParam.setBeWisdom(false);
                }
                listprojectPurchaseForecastSaveParam.add(projectPurchaseForecastSaveParam);
            }
            rone.getPurchaseForecastList().addAll(listprojectPurchaseForecastSaveParam);
            rone.setProjectManagerCenter(customerMsg.getCenter());
            rone.setProductSeriesOneCode(customerMsg.getProductSeriesOneCode());
            rone.setProductSeriesOneName(customerMsg.getProductSeriesOneName());
            rone.setProductSeriesTwoCode(customerMsg.getProductSeriesTwoCode());
            rone.setProductSeriesTwoName(customerMsg.getProductSeriesTwoName());
            rone.setBrandInfoCode(customerMsg.getBrandInfoCode());
            rone.setBrandInfoName(customerMsg.getBrandInfoName());
            rone.setBigAndSmall(customerMsg.getBigOrSmall());
            logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectROneSaveParam *_*_*_*_*_*_*_*_*_*");
            ProjectRTwoSaveParam projectRTwoSaveParam = new ProjectRTwoSaveParam();
            //甲方联系人姓名
            projectRTwoSaveParam.setFirstContactName(customerMsg.getFirstContactName());
            //职位
            projectRTwoSaveParam.setPosition(customerMsg.getPosition());
            //电话
            projectRTwoSaveParam.setPhone(customerMsg.getPhone());
            //项目所属行业门类
            projectRTwoSaveParam.setIndustryHomeCategory(customerMsg.getIndustryHomeCategory());
            //行业大类
            projectRTwoSaveParam.setIndustryCategory(customerMsg.getIndustryCategory());
            //机会点
            projectRTwoSaveParam.setChancePoint(customerMsg.getChancePoint());
            //用户群
            projectRTwoSaveParam.setUserGroup(customerMsg.getUserGroup());
            //锁定用户
            projectRTwoSaveParam.setLockUser(customerMsg.getLockUser());
            logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectRTwoSaveParam *_*_*_*_*_*_*_*_*_*");

            //R3经销商编码
            ProjectRThreeSaveParam rthree = new ProjectRThreeSaveParam();
            rthree.setContractorCode(customerMsg.getContractorCode());
            //承接商名称
            rthree.setContractorName(customerMsg.getContractorName());
            //客户需求分析
            rthree.setCustomerDemandAnalysis(customerMsg.getCustomerDemandAnalysis());
            logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectRThreeSaveParam *_*_*_*_*_*_*_*_*_*");

            //R4经销商编码
            ProjectRFourSaveParam projectRFourSaveParam = new ProjectRFourSaveParam();
            //甲方招标文件
            projectRFourSaveParam.setFirstBidDocument(customerMsg.getFirstBidDocument());
            //是否送装一体
            projectRFourSaveParam.setBeSendWithOne(customerMsg.getBeSendWithOne());
            //是否售后招标
            projectRFourSaveParam.setAfterSalesBidding(customerMsg.getAfterSalesBidding());
            //是否直发工地
            projectRFourSaveParam.setNeedDirectDeliverySite(customerMsg.getNeedDirectDeliverySite());
            logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ProjectRFourSaveParam *_*_*_*_*_*_*_*_*_*");

            //R5经销商编码
            ProjectRFiveSaveParam projectRFiveSaveParam = new ProjectRFiveSaveParam();
            //预计开标时间
            if(null!=customerMsg.getWinBidTime()){
                projectRFiveSaveParam.setWinBidTime(dateToXmlDate(customerMsg.getWinBidTime()));
            }
            //预计签约日期
            if(null!=customerMsg.getEstimatedTimeSigning()){
                projectRFiveSaveParam.setEstimatedTimeSigning(dateToXmlDate(customerMsg.getEstimatedTimeSigning()));
            }
            //实际首次下单日期
            if(null!=customerMsg.getEstimatedTimeDelivery()){
                projectRFiveSaveParam.setEstimatedTimeDelivery(dateToXmlDate(customerMsg.getEstimatedTimeDelivery()));
            }
            //是否中标
            projectRFiveSaveParam.setBeWinBid(customerMsg.getBeWinBid());
            logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - projectRFiveSaveParam *_*_*_*_*_*_*_*_*_*");

            ContractSaveParam contractSaveParam = new ContractSaveParam();
            //合同扫描件
            contractSaveParam.setContractAttachement(customerMsg.getContractAttachement());
            //签约日期
            if(null!=customerMsg.getEstimatedTimeSigning()){
                contractSaveParam.setSignTime(dateToXmlDate(customerMsg.getEstimatedTimeSigning()));
            }
            logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - ContractSaveParam *_*_*_*_*_*_*_*_*_*");

            QuoteSaveParam quoteSaveParam = new QuoteSaveParam();
            List<CustomerQuotes> listCustomerQuotes = customerMsg.getQuotes();
            if(listCustomerQuotes != null && listCustomerQuotes.size()>0){
                int num = listCustomerQuotes.size();
                List<QuoteProductSaveParam> quoteProductList = new ArrayList<QuoteProductSaveParam>();
                for(int i=0; i<num; i++){
                    QuoteProductSaveParam quoteProduct = new QuoteProductSaveParam();
                    //产业
                    //quoteProduct.setDomainCode(listCustomerQuotes.get(i).getDomainType());
                    //产品组
                    //quoteProduct.setProductGroupCode (listCustomerQuotes.get(i).getProductGroup());
                    //品牌
                    //quoteProduct.setBrandName(listCustomerQuotes.get(i).getBrand());
                    //报价
                    quoteProduct.setProductQuote(listCustomerQuotes.get(i).getProductQuote());
                    //产品编码
                    quoteProduct.setProductCode(listCustomerQuotes.get(i).getProductCode());
                    //数量
                    quoteProduct.setDemandQuantity(listCustomerQuotes.get(i).getDemandQuantity());

                    if(StringUtils.isBlank(quoteProduct.getProductCode())){
                        continue;
                    }
                    quoteProductList.add(quoteProduct);

                }
                quoteSaveParam.getQuoteProductList().addAll(quoteProductList);
            }
            logger.info("*_*_*_*_*_*_*_*_*_* saveProjectFromQYGNew - QuoteSaveParam *_*_*_*_*_*_*_*_*_*");

            List<ProjectSaveParam> listProjectSaveParam = new ArrayList<>();
            ProjectSaveParam psp = new ProjectSaveParam();
            psp.setProjectROneSaveParam(rone);
            psp.setProjectRTwoSaveParam(projectRTwoSaveParam);
            psp.setProjectRThreeSaveParam(rthree);
            psp.setProjectRFourSaveParam(projectRFourSaveParam);
            psp.setProjectRFiveSaveParam(projectRFiveSaveParam);
            psp.setContractSaveParam(contractSaveParam);
            psp.setQuoteSaveParam(quoteSaveParam);
            listProjectSaveParam.add(psp);

            String pspJson = JSONObject.fromObject(psp).toString();
            logger.info("*_*_*_*_*_*_*_*_*_* 给hps传递的参数="+ pspJson +" *_*_*_*_*_*_*_*_*_*");

            String res = ser.getProjectWebServiceServerImplPort().saveProjectFromQYGNew(listProjectSaveParam);
            JSONObject resObj = JSONObject.fromObject(res);

            //判断是否成功进入漏斗
            String flag = (String) resObj.get("flag");
            map.put("flag",flag);
            if("error".equals(flag)){
                //如果失败
                map.put("msgId",resObj.get("msgId"));
                map.put("errorMsg",resObj.get("errorMsg"));
                logger.info("*_*_*_*_*_*_*_*_*_* hps返回报备错误码："+ resObj.get("errorMsg") +" *_*_*_*_*_*_*_*_*_*");

            }else{
                JSONArray successMsgArray = JSONArray.parseArray((String) resObj.get("successMsg"));
                JSONObject successMsgArrayObj = JSONObject.fromObject(successMsgArray.get(0));
                String projectCode = (String) successMsgArrayObj.get("projectCode");
                String msgId = (String) successMsgArrayObj.get("msgId");
                String loginLevel = (String) successMsgArrayObj.get("loginLevel");
                String info = (String) successMsgArrayObj.get("info");
                map.put("projectCode",projectCode);
                map.put("msgId",msgId);
                map.put("loginLevel",loginLevel);
                map.put("info", info);
            }
        }catch (Exception e){
            map.put("flag","error");
            map.put("errorMsg","请联系业务人员！");
            return map;
        }
        return map;
    }
    

}
