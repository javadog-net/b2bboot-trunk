package com.haier.webservices.client.mdm.util;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.druid.util.StringUtils;
import com.haier.webservices.client.mdm.createcust2mdm.CustomerConstantModel;
import com.haier.webservices.client.mdm.generalbankinterface.ProcessResponseBankTableBankItem;


/**
 * 类描述：
 */
public class MDMServiceXmlHelper {
	
	/**
	 * 把要推送的订单列表皮拼接xml字符串
	 * @param productList
	 * @return
	 */
	public String getBankXML(ProcessResponseBankTableBankItem bank) {
		StringBuffer bankDoc = new StringBuffer("");
		if(bank!=null){
			// 定义一个StringBuffer
			bankDoc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bankDoc.append("<schema xmlns=\"http://www.w3.org/2001/XMLSchema\" attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\"targetNamespace=\"http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface\">");
			bankDoc.append("<element name=\"process\">");
			// <!—订单基本信息-->
			bankDoc.append("<complexType>");
			bankDoc.append("<sequence>");
			bankDoc.append("<element name=\"bankBranchCode\" type=\"string\">").append(bank.getBankBranchCode()).append("</element>");
			bankDoc.append("<element name=\"bankName\" type=\"string\">").append(bank.getBankName()).append("</element>");
			bankDoc.append("<element name=\"region\" type=\"string\">").append(bank.getRegion()).append("</element>");
			bankDoc.append("<element name=\"cityStreetRoom\" type=\"string\">").append(bank.getCityStreetRoom()).append("</element>");
			bankDoc.append("<element name=\"bankCategory\" type=\"string\">").append(bank.getBankCategory()).append("</element>");
			bankDoc.append("</sequence>");
			bankDoc.append("</complexType>");
			bankDoc.append("</element>");
			bankDoc.append("</schema>");
			}
		return bankDoc.toString();
	}
	
	
	public String saveCustomerXML(CustomerConstantModel cust) {
		
		StringBuffer custDoc = new StringBuffer("");
		if(cust!=null){
			// 定义一个StringBuffer
			custDoc.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
			custDoc.append("<soap:Body xmlns:ns1=\"http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM\">");
			custDoc.append("<ns1:process>");
			custDoc.append("<ns1:IN_CUSTOMERNUMBER>").append(cust.getIN_CUSTOMERNUMBER()).append("</ns1:IN_CUSTOMERNUMBER>");
			custDoc.append("<ns1:IN_PARTNERFLAGSP>").append(cust.getIN_PARTNERFLAGSP()).append("</ns1:IN_PARTNERFLAGSP>");
			custDoc.append("<ns1:IN_PARTNERFLAGSH>").append(cust.getIN_PARTNERFLAGSH()).append("</ns1:IN_PARTNERFLAGSH>");
			custDoc.append("<ns1:IN_PARTNERFLAGBP>").append(cust.getIN_PARTNERFLAGBP()).append("</ns1:IN_PARTNERFLAGBP>");
			custDoc.append("<ns1:IN_PARTNERFLAGPY>").append(cust.getIN_PARTNERFLAGPY()).append("</ns1:IN_PARTNERFLAGPY>");
			custDoc.append("<ns1:IN_CUSTOMERTITLE>").append(cust.getIN_CUSTOMERTITLE()).append("</ns1:IN_CUSTOMERTITLE>");
			custDoc.append("<ns1:IN_ACCOUNTGROUP>").append(cust.getIN_ACCOUNTGROUP()).append("</ns1:IN_ACCOUNTGROUP>");
			custDoc.append("<ns1:IN_CUSTOMERNAME>").append(cust.getIN_CUSTOMERNAME()).append("</ns1:IN_CUSTOMERNAME>");
			custDoc.append("<ns1:IN_SEARCHTERMS>").append(cust.getIN_SEARCHTERMS()).append("</ns1:IN_SEARCHTERMS>");
			custDoc.append("<ns1:IN_CUSTOMERCOUNTRY>").append(cust.getIN_CUSTOMERCOUNTRY()).append("</ns1:IN_CUSTOMERCOUNTRY>");
			custDoc.append("<ns1:IN_LANGUAGE>").append(cust.getIN_LANGUAGE()).append("</ns1:IN_LANGUAGE>");
			custDoc.append("<ns1:IN_REGION>").append(cust.getIN_REGION()).append("</ns1:IN_REGION>");
			custDoc.append("<ns1:IN_CITY>").append(cust.getIN_CITY()).append("</ns1:IN_CITY>");
			custDoc.append("<ns1:IN_STREET>").append(cust.getIN_STREET()).append("</ns1:IN_STREET>");
			custDoc.append("<ns1:IN_CONTACTPERSON>").append(cust.getIN_CONTACTPERSON()).append("</ns1:IN_CONTACTPERSON>");
			custDoc.append("<ns1:IN_PHONENUMBER1>").append(cust.getIN_PHONENUMBER1()).append("</ns1:IN_PHONENUMBER1>");
			custDoc.append("<ns1:IN_VATREGNO>").append(cust.getIN_VATREGNO()).append("</ns1:IN_VATREGNO>");
			custDoc.append("<ns1:IN_INDUSTRYCLASS>").append(cust.getIN_INDUSTRYCLASS()).append("</ns1:IN_INDUSTRYCLASS>");
			custDoc.append("<ns1:IN_TAXDOCTYPE>").append(cust.getIN_TAXDOCTYPE()).append("</ns1:IN_TAXDOCTYPE>");
			custDoc.append("<ns1:IN_LEGAL_PERSON>").append(cust.getIN_LEGAL_PERSON()).append("</ns1:IN_LEGAL_PERSON>");
			custDoc.append("<ns1:IN_TRAINSTATION>").append(cust.getIN_TRAINSTATION()).append("</ns1:IN_TRAINSTATION>");
			custDoc.append("<ns1:IN_TELEPHONE>").append(cust.getIN_TELEPHONE()).append("</ns1:IN_TELEPHONE>");
			custDoc.append("<ns1:IN_BANKCOUNTRY>").append(cust.getIN_BANKCOUNTRY()).append("</ns1:IN_BANKCOUNTRY>");
			custDoc.append("<ns1:IN_BANKKEY>").append(cust.getIN_BANKKEY()).append("</ns1:IN_BANKKEY>");
			custDoc.append("<ns1:IN_ACCOUNTHOLDER>").append(cust.getIN_ACCOUNTHOLDER()).append("</ns1:IN_ACCOUNTHOLDER>");
			custDoc.append("<ns1:IN_COMPANYCODE>").append(cust.getIN_COMPANYCODE()).append("</ns1:IN_COMPANYCODE>");
			custDoc.append("<ns1:IN_RECONACCOUNT>").append(cust.getIN_RECONACCOUNT()).append("</ns1:IN_RECONACCOUNT>");
			custDoc.append("<ns1:IN_SORTKEY>").append(cust.getIN_SORTKEY()).append("</ns1:IN_SORTKEY>");
			custDoc.append("<ns1:IN_CUSTOMER_SUMMARY_GROUP>").append(cust.getIN_CUSTOMER_SUMMARY_GROUP()).append("</ns1:IN_CUSTOMER_SUMMARY_GROUP>");
			custDoc.append("<ns1:IN_CURRENCY>").append(cust.getIN_CURRENCY()).append("</ns1:IN_CURRENCY>");
			custDoc.append("<ns1:IN_CUSTOMER_PRICE_PROCEDURE>").append(cust.getIN_CUSTOMER_PRICE_PROCEDURE()).append("</ns1:IN_CUSTOMER_PRICE_PROCEDURE>");
			custDoc.append("<ns1:IN_ACCT_ASSGMT_GROUP>").append(cust.getIN_ACCT_ASSGMT_GROUP()).append("</ns1:IN_ACCT_ASSGMT_GROUP>");
			custDoc.append("<ns1:IN_TAX_CLASSIFICATION>").append(cust.getIN_TAX_CLASSIFICATION()).append("</ns1:IN_TAX_CLASSIFICATION>");
			custDoc.append("<ns1:IN_SHIP_CONDITION>").append(cust.getIN_SHIP_CONDITION()).append("</ns1:IN_SHIP_CONDITION>");
			custDoc.append("<ns1:IN_POD_RELATION>").append(cust.getIN_POD_RELATION()).append("</ns1:IN_POD_RELATION>");
			custDoc.append("<ns1:IN_SALES_REGION>").append(cust.getIN_SALES_REGION()).append("</ns1:IN_SALES_REGION>");
			custDoc.append("<ns1:IN_FINANACOUNTNAME>").append(cust.getIN_FINANACOUNTNAME()).append("</ns1:IN_FINANACOUNTNAME>");
			custDoc.append("<ns1:IN_FINANORGN>").append(cust.getIN_FINANORGN()).append("</ns1:IN_FINANORGN>");
			custDoc.append("<ns1:IN_FINANORGNSHORT>").append(cust.getIN_FINANORGNSHORT()).append("</ns1:IN_FINANORGNSHORT>");
			custDoc.append("<ns1:IN_FINANACOUNTCODE>").append(cust.getIN_FINANACOUNTCODE()).append("</ns1:IN_FINANACOUNTCODE>");
			custDoc.append("<ns1:IN_FINANCURRENCY>").append(cust.getIN_FINANCURRENCY()).append("</ns1:IN_FINANCURRENCY>");
			custDoc.append("<ns1:IN_FINANTYPE>").append(cust.getIN_FINANTYPE()).append("</ns1:IN_FINANTYPE>");
			custDoc.append("<ns1:IN_FINANSTATE>").append(cust.getIN_FINANSTATE()).append("</ns1:IN_FINANSTATE>");
			custDoc.append("<ns1:IN_SYSNAME>").append(cust.getIN_SYSNAME()).append("</ns1:IN_SYSNAME>");
			custDoc.append("<ns1:IN_CREATED_BY>").append(cust.getIN_CREATED_BY()).append("</ns1:IN_CREATED_BY>");
			custDoc.append("<ns1:OPERATETYPE>").append(cust.getOPERATETYPE()).append("</ns1:OPERATETYPE>");
			custDoc.append("<ns1:UPDATE_BASE>").append(cust.getUPDATE_BASE()).append("</ns1:UPDATE_BASE>");
			custDoc.append("<ns1:UPDATE_BANK>").append(cust.getUPDATE_BANK()).append("</ns1:UPDATE_BANK>");
			custDoc.append("<ns1:UPDATE_EXTEND>").append(cust.getUPDATE_EXTEND()).append("</ns1:UPDATE_EXTEND>");
			custDoc.append("<ns1:UPDATE_SALE>").append(cust.getUPDATE_SALE()).append("</ns1:UPDATE_SALE>");
			custDoc.append("<ns1:UPDATE_COMP>").append(cust.getUPDATE_COMP()).append("</ns1:UPDATE_COMP>");
			custDoc.append("<ns1:UPDATE_FINANCE>").append(cust.getUPDATE_FINANCE()).append("</ns1:UPDATE_FINANCE>");
			custDoc.append("<ns1:IF_AFFIX>").append(cust.getIF_AFFIX()).append("</ns1:IF_AFFIX>");
			custDoc.append("<ns1:IN_AFFIX_IP>").append(cust.getIN_AFFIX_IP()).append("</ns1:IN_AFFIX_IP>");
			custDoc.append("<ns1:IN_AFFIX_USERNAME>").append(cust.getIN_AFFIX_USERNAME()).append("</ns1:IN_AFFIX_USERNAME>");
			custDoc.append("<ns1:IN_AFFIX_PASSWORD>").append(cust.getIN_AFFIX_PASSWORD()).append("</ns1:IN_AFFIX_PASSWORD>");
			custDoc.append("<ns1:IN_AFFIX_URL>").append(cust.getIN_AFFIX_URL()).append("</ns1:IN_AFFIX_URL>");
			custDoc.append("<ns1:IN_AFFIX_FILENAME>").append(cust.getIN_AFFIX_FILENAME()).append("</ns1:IN_AFFIX_FILENAME>");
			custDoc.append("</ns1:process>");
			custDoc.append("</soap:Body>");
			custDoc.append("</soap:Envelope>");
			}
		return custDoc.toString();
		
	}
	
	/**
	 * 验证账号信息是否存在
	 * @param fieldName 条件名
	 * @param fieldValue 条件值
	 * @param queryType 条件类型
	 * @return
	 */
	public String searchCustomerXML(String fieldName,String fieldValue,String queryType) {
		StringBuffer custDoc = new StringBuffer("");
			// 定义一个StringBuffer
		custDoc.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		custDoc.append("<soap:Body xmlns:ns1=\"http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew\">");
		custDoc.append("<ns1:process>");
		custDoc.append("<ns1:IN_SYS_NAME>").append("S00763").append("</ns1:IN_SYS_NAME>");
		custDoc.append("<ns1:IN_MASTER_TYPE>").append("HAIERMDM").append("</ns1:IN_MASTER_TYPE>");
		custDoc.append("<ns1:IN_TABLE_NAME>").append("HRHAIERBTB_HM_CUSTOMERS").append("</ns1:IN_TABLE_NAME>");
		custDoc.append("<ns1:IN_FIELDS_VALUE_TABLE>");
		custDoc.append("<ns1:IN_FIELDS_VALUE_TABLE_ITEM>");
		custDoc.append("<ns1:FIELD_NAME>").append("MATERIAL_CODE").append("</ns1:FIELD_NAME>");
		custDoc.append("<ns1:FIELD_VALUE>").append("8800314198").append("</ns1:FIELD_VALUE>");
		custDoc.append("<ns1:FIELD_QUERY_TYPE>").append("in").append("/ns1:FIELD_QUERY_TYPE");
		custDoc.append("</ns1:IN_FIELDS_VALUE_TABLE_ITEM>");
		custDoc.append("</ns1:IN_FIELDS_VALUE_TABLE>");
		custDoc.append("</ns1:process>");
		custDoc.append("</soap:Body>");
		custDoc.append("</soap:Envelope>");
		return custDoc.toString();
	}
	
	
	
	
	/**
	 * 解析返回结果
	 * <RETURN>
		<RETURN_CODE>返回操作状态码</ RETURN_CODE>
		<RETURN_MESSAGE>返回操作说明</ RETURN_ MESSAGE >
		</ RETURN> 

	 */
	
	public String parseResultCode(String returnXml){
		if(StringUtils.isEmpty(returnXml)){
			returnXml = returnXml.replaceAll(" ", "");
		}
		
		String returnCode = "";
		Document document;
		
			try {
				document = DocumentHelper.parseText(returnXml);
				//根节点
				Element root = document.getRootElement(); 
				returnCode = root.elementTextTrim("RETURN_CODE");
				
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
		
		return returnCode;
	}
	

}
