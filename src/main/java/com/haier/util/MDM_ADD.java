package com.haier.util;

import com.haier.mdm.khzj.Createplcust2MdmClientEp;
import com.haier.mdm.khzj.ParamObject;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import org.apache.commons.lang.StringUtils;

import javax.xml.ws.Holder;
import java.util.HashMap;
import java.util.Map;

public class MDM_ADD {

	/**
	 * 功能：客户创建
	 * @param paramObject   参数实体类
	 * @return              result为SUCCESS成功，msg为成功信息，custom_code客户编码
	 *      *               result为FAILURE失败，msg为失败信息，custom_code客户编码
	 */
	public static Map Add(ParamObject paramObject){
		Map<String,Object> map = new HashMap<>();
		Createplcust2MdmClientEp clientEp = new Createplcust2MdmClientEp();
		Holder<String> outCustomCode = new Holder<>();
		Holder<String> outRetCode = new Holder<>();
		Holder<String> outRetMsg = new Holder<>();

		try {
			clientEp.getCreatePlCust2MDMPt().process(paramObject.getInCUSTOMERNUMBER(), paramObject.getIN_PARTNERFLAGSP(), paramObject.getIN_PARTNERFLAGSH(),
					paramObject.getIN_PARTNERFLAGBP(), paramObject.getIN_PARTNERFLAGPY(), paramObject.getIN_CUSTOMERTITLE(), paramObject.getIN_ACCOUNTGROUP(),
					paramObject.getIN_CUSTOMERNAME(), paramObject.getIN_SEARCHTERMS(), paramObject.getIN_CUSTOMERCOUNTRY(), paramObject.getIN_LANGUAGE(),
					paramObject.getIN_REGION(), paramObject.getIN_CITY(), paramObject.getIN_STREET(), paramObject.getIN_CONTACTPERSON(), paramObject.getIN_PHONENUMBER1(),
					paramObject.getIN_VATREGNO(), paramObject.getIN_INDUSTRYCLASS(), paramObject.getIN_TAXDOCTYPE(), paramObject.getInLEGALPERSON(), paramObject.getIN_LEGAL_TYPE(),
					paramObject.getIN_TRAINSTATION(), paramObject.getIN_TELEPHONE(), paramObject.getIN_BANKCOUNTRY(), paramObject.getIN_BANKKEY(),
					paramObject.getIN_ACCOUNTHOLDER(), paramObject.getIN_COMPANYCODE(), paramObject.getIN_RECONACCOUNT(), paramObject.getIN_SORTKEY(),paramObject.getIN_CUSTOMER_SUMMARY_GROUP(),
					paramObject.getIN_CURRENCY(), paramObject.getIN_CUSTOMER_PRICE_PROCEDURE(), paramObject.getIN_ACCT_ASSGMT_GROUP(),
					paramObject.getIN_TAX_CLASSIFICATION(), paramObject.getIN_SHIP_CONDITION(), paramObject.getIN_POD_RELATION(), paramObject.getIN_REGION(),
					paramObject.getIN_FINANACOUNTNAME(), paramObject.getIN_FINANORGN(), paramObject.getIN_FINANORGNSHORT(), paramObject.getIN_FINANACOUNTCODE(),
					paramObject.getIN_FINANCURRENCY(), paramObject.getIN_FINANTYPE(), paramObject.getIN_FINANSTATE(), paramObject.getIN_SYSNAME(),
					paramObject.getIN_CREATED_BY(), paramObject.getOPERATETYPE(), paramObject.getUPDATE_BASE(), paramObject.getUPDATE_BANK(),
					paramObject.getUPDATE_EXTEND(), paramObject.getUPDATE_SALE(), paramObject.getUPDATE_COMP(), paramObject.getUPDATE_FINANCE(),
					paramObject.getIF_AFFIX(), paramObject.getIN_AFFIX_IP(), paramObject.getIN_AFFIX_USERNAME(), paramObject.getIN_AFFIX_PASSWORD(),
					paramObject.getIN_AFFIX_URL(), paramObject.getIN_AFFIX_FILENAME(), paramObject.getIn_ADDRESS_PROVINCE(), paramObject.getIN_CITY(),
					paramObject.getIn_ADDRESS_AREA(), paramObject.getIn_ADDRESS_TOWN(), paramObject.getIn_ADDRESS_VILLAGE(), paramObject.getIn_ADDRESS_ROAD(),
					paramObject.getIn_ADDRESS_HOUSE_NUMBER(), paramObject.getIn_ADDRESS_LONGITUDE(), paramObject.getIn_ADDRESS_LATITUDE(),
					paramObject.getIn_SPECIFIC_ADDRESS(), paramObject.getIN_COMPANYCODE(), outCustomCode, outRetCode, outRetMsg);
			if("S".equals(outRetCode.value)){
				map.put("result","SUCCESS");
			}else{
				map.put("result","FAILURE");
			}
			map.put("msg",outRetMsg.value);
			map.put("retcode",outRetCode.value);
			map.put("customcode", outCustomCode.value);
		}catch (Exception e){
			map.put("result","FAILUREE");
			map.put("msg","错误："+e.getMessage());
		}
		return map;
	}

	/**
	 *
	 * @param purchaser
	 * @param type            此次注册是新增还是修改   1新增   2扩充
	 * @return
	 */
	public static Map organizationDate(Purchaser purchaser,String type){

		ParamObject paramObject = new ParamObject();

		paramObject.setIN_PARTNERFLAGSP("1");
		paramObject.setIN_PARTNERFLAGSH("1");
		paramObject.setIN_PARTNERFLAGBP("1");
		paramObject.setIN_PARTNERFLAGPY("1");

		paramObject.setIN_CUSTOMERTITLE("公司");
		paramObject.setIN_ACCOUNTGROUP("0280");
		paramObject.setIN_CUSTOMERNAME(purchaser.getCompanyName());
//    	paramObject.setIN_SEARCHTERMS("TEST9011A");
		paramObject.setIN_CUSTOMERCOUNTRY("CN");
		paramObject.setIN_LANGUAGE("ZH");
//    	paramObject.setIN_REGION("110100");

		paramObject.setIN_CITY(purchaser.getCityName());
		paramObject.setIN_CONTACTPERSON(purchaser.getContacts());
		paramObject.setIN_PHONENUMBER1(purchaser.getMobile());
		//开票用 公司注册地址
		paramObject.setIN_STREET(purchaser.getRegAddr());
		//增值税登记号
		paramObject.setIN_VATREGNO(purchaser.getVatregno());
		if(StringUtils.isNotBlank(purchaser.getCompanyNature())){
			paramObject.setIN_INDUSTRYCLASS(purchaser.getCompanyNature());
		}else {
			paramObject.setIN_INDUSTRYCLASS("HY007");
		}
		//开票类型 01 增值税发票  02普通发票；传01或02
		paramObject.setIN_TAXDOCTYPE("01");
		paramObject.setInLEGALPERSON(purchaser.getContacts());
		paramObject.setIN_LEGAL_TYPE("01");
		paramObject.setIN_TRAINSTATION("");  //默认空
		//开票所需电话
		paramObject.setIN_TELEPHONE(purchaser.getRegPhone());

		paramObject.setIN_BANKCOUNTRY("CN");
		paramObject.setIN_BANKKEY(purchaser.getBankKey());
		paramObject.setIN_ACCOUNTHOLDER(purchaser.getBankAccount());

		paramObject.setIN_COMPANYCODE("3200");
		paramObject.setIN_RECONACCOUNT("1122002000");
		paramObject.setIN_SORTKEY("001");

		paramObject.setIN_CUSTOMER_SUMMARY_GROUP("1");
		paramObject.setIN_CURRENCY("CNY");
		paramObject.setIN_CUSTOMER_PRICE_PROCEDURE("1");
		paramObject.setIN_ACCT_ASSGMT_GROUP("20");
		paramObject.setIN_TAX_CLASSIFICATION("1");
		paramObject.setIN_SHIP_CONDITION("01");
//    	paramObject.setIN_POD_RELATION("X");
//    	paramObject.setIN_SALES_REGION("370283001000");
//    	paramObject.setIN_FINANACOUNTNAME("1");
//    	paramObject.setIN_FINANORGN("1");
//    	paramObject.setIN_FINANORGNSHORT("KJT");
//    	paramObject.setIN_FINANACOUNTCODE("1");
//    	paramObject.setIN_FINANCURRENCY("CNY");
//    	paramObject.setIN_FINANTYPE("2");
//    	paramObject.setIN_FINANSTATE("0");
		paramObject.setIN_SYSNAME("S00023");
		paramObject.setIN_CREATED_BY("01285874");
		if("1".equals(type)){
			paramObject.setOPERATETYPE("CREATE");
		}else {
			paramObject.setOPERATETYPE("EXTEND");
			if(StringUtils.isNotBlank(purchaser.getLoginNum())){
				paramObject.setInCUSTOMERNUMBER(purchaser.getLoginNum());
			}
		}
		paramObject.setUPDATE_BASE("");
		paramObject.setUPDATE_BANK("");
		paramObject.setUPDATE_EXTEND("");
		paramObject.setUPDATE_SALE("");
		paramObject.setUPDATE_COMP("");
		paramObject.setUPDATE_FINANCE("");
		paramObject.setIF_AFFIX("");
		paramObject.setIN_AFFIX_IP("");
		paramObject.setIN_AFFIX_USERNAME("");
		paramObject.setIN_AFFIX_PASSWORD("");
		paramObject.setIN_AFFIX_URL("");
		paramObject.setIN_AFFIX_FILENAME("");
//    	paramObject.setIn_ADDRESS_PROVINCE("");
//    	paramObject.setIn_ADDRESS_CITY("");
//    	paramObject.setIn_ADDRESS_AREA("");
//    	paramObject.setIn_ADDRESS_TOWN("");
//    	paramObject.setIn_ADDRESS_VILLAGE("");
//    	paramObject.setIn_ADDRESS_ROAD("");
//    	paramObject.setIn_ADDRESS_HOUSE_NUMBER("");
//    	paramObject.setIn_ADDRESS_LONGITUDE("");
//    	paramObject.setIn_ADDRESS_LATITUDE("");
//    	paramObject.setIn_SPECIFIC_ADDRESS("");
//    	paramObject.setIN_INNER_COMPANYCODE("");
		Map map = Add(paramObject);
		return map;
	}



	public static void main(String[] args) {
		ParamObject paramObject = new ParamObject();

		paramObject.setIN_PARTNERFLAGSP("1");
		paramObject.setIN_PARTNERFLAGSH("1");
		paramObject.setIN_PARTNERFLAGBP("1");
		paramObject.setIN_PARTNERFLAGPY("1");

		paramObject.setIN_CUSTOMERTITLE("公司");
		paramObject.setIN_ACCOUNTGROUP("0280");
		paramObject.setIN_CUSTOMERNAME("济南全网数商科技股份有限公司");
		paramObject.setIN_SEARCHTERMS("TEST9011A");
		paramObject.setIN_CUSTOMERCOUNTRY("CN");
		paramObject.setIN_LANGUAGE("ZH");
//    	paramObject.setIN_REGION("110100");
		paramObject.setIN_CITY("济南");
		paramObject.setIN_STREET("皂君庙");
		paramObject.setIN_CONTACTPERSON("徐");
		paramObject.setIN_PHONENUMBER1("13223232313");
		paramObject.setIN_VATREGNO("23211119U");
		paramObject.setIN_INDUSTRYCLASS("HY007");
		paramObject.setIN_TAXDOCTYPE("02");
		paramObject.setInLEGALPERSON("徐");
		paramObject.setIN_LEGAL_TYPE("01");
		paramObject.setIN_TRAINSTATION("");  //默认空
		paramObject.setIN_TELEPHONE("13223232313");

		paramObject.setIN_BANKCOUNTRY("CN");
		paramObject.setIN_BANKKEY("104582241322");
		paramObject.setIN_ACCOUNTHOLDER("2143332232");

		paramObject.setIN_COMPANYCODE("9010");
		paramObject.setIN_RECONACCOUNT("1122002000");
		paramObject.setIN_SORTKEY("001");
		paramObject.setIN_CUSTOMER_SUMMARY_GROUP("1");
		paramObject.setIN_CURRENCY("CNY");
		paramObject.setIN_CUSTOMER_PRICE_PROCEDURE("1");
		paramObject.setIN_ACCT_ASSGMT_GROUP("20");
		paramObject.setIN_TAX_CLASSIFICATION("1");
		paramObject.setIN_SHIP_CONDITION("01");
		paramObject.setIN_POD_RELATION("X");
		paramObject.setIN_SALES_REGION("370283001000");
		paramObject.setIN_FINANACOUNTNAME("1");
		paramObject.setIN_FINANORGN("1");
		paramObject.setIN_FINANORGNSHORT("KJT");
		paramObject.setIN_FINANACOUNTCODE("1");
		paramObject.setIN_FINANCURRENCY("CNY");
		paramObject.setIN_FINANTYPE("2");
		paramObject.setIN_FINANSTATE("0");
		paramObject.setIN_SYSNAME("S00023");
		paramObject.setIN_CREATED_BY("01285874");
		paramObject.setOPERATETYPE("CREATE");
		paramObject.setUPDATE_BASE("");
		paramObject.setUPDATE_BANK("");
		paramObject.setUPDATE_EXTEND("");
		paramObject.setUPDATE_SALE("");
		paramObject.setUPDATE_COMP("");
		paramObject.setUPDATE_FINANCE("");
		paramObject.setIF_AFFIX("");
		paramObject.setIN_AFFIX_IP("");
		paramObject.setIN_AFFIX_USERNAME("");
		paramObject.setIN_AFFIX_PASSWORD("");
		paramObject.setIN_AFFIX_URL("");
		paramObject.setIN_AFFIX_FILENAME("");
//    	paramObject.setIn_ADDRESS_PROVINCE("");
//    	paramObject.setIn_ADDRESS_CITY("");
//    	paramObject.setIn_ADDRESS_AREA("");
//    	paramObject.setIn_ADDRESS_TOWN("");
//    	paramObject.setIn_ADDRESS_VILLAGE("");
//    	paramObject.setIn_ADDRESS_ROAD("");
//    	paramObject.setIn_ADDRESS_HOUSE_NUMBER("");
//    	paramObject.setIn_ADDRESS_LONGITUDE("");
//    	paramObject.setIn_ADDRESS_LATITUDE("");
//    	paramObject.setIn_SPECIFIC_ADDRESS("");
//    	paramObject.setIN_INNER_COMPANYCODE("");
		Map map = Add(paramObject);
		System.out.println(map);
	}
}
