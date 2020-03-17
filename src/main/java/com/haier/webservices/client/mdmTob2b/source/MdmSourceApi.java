package com.haier.webservices.client.mdmTob2b.source;

import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.entity.MdmSourceReturn;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.ws.Holder;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MdmSourceApi {

	static URL wsdlLocation;
	
	static {
        try {          
            wsdlLocation = new URL("http://bpel.mdm.haier.com:7778/soa-infra/services/interface/MDMTableConditionDataNew/mdmtableconditiondatanew_client_ep?WSDL");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
	
	
	public static MdmSourceReturn addSourceFromMdm(String code88){ 
		
		MdmtableconditiondatanewClientEp ss = new MdmtableconditiondatanewClientEp(wsdlLocation);
		
		ObjectFactory factory = new ObjectFactory();
		
		HAIERMDMFIELDSVALUETYPE hAIERMDMFIELDSVALUETYPE1 = new HAIERMDMFIELDSVALUETYPE();	
		hAIERMDMFIELDSVALUETYPE1.setFIELDNAME(factory.createHAIERMDMFIELDSVALUETYPEFIELDVALUE(code88));
		hAIERMDMFIELDSVALUETYPE1.setFIELDVALUE(factory.createHAIERMDMFIELDSVALUETYPEFIELDNAME("CUSTOMER_NUMBER"));
		hAIERMDMFIELDSVALUETYPE1.setFIELDQUERYTYPE(factory.createHAIERMDMFIELDSVALUETYPEFIELDQUERYTYPE("="));
		
		HAIERMDMFIELDSVALUETYPE hAIERMDMFIELDSVALUETYPE2 = new HAIERMDMFIELDSVALUETYPE();	
		hAIERMDMFIELDSVALUETYPE2.setFIELDNAME(factory.createHAIERMDMFIELDSVALUETYPEFIELDVALUE("1"));
		hAIERMDMFIELDSVALUETYPE2.setFIELDVALUE(factory.createHAIERMDMFIELDSVALUETYPEFIELDNAME("PARTNER_FLAG_SP"));
		hAIERMDMFIELDSVALUETYPE2.setFIELDQUERYTYPE(factory.createHAIERMDMFIELDSVALUETYPEFIELDQUERYTYPE("="));
		
		
		HAIERMDMFIELDSVALUETABLE hAIERMDMFIELDSVALUETABLE = new HAIERMDMFIELDSVALUETABLE();
		hAIERMDMFIELDSVALUETABLE.getINFIELDSVALUETABLEITEM().add(hAIERMDMFIELDSVALUETYPE1);
		hAIERMDMFIELDSVALUETABLE.getINFIELDSVALUETABLEITEM().add(hAIERMDMFIELDSVALUETYPE2);	
		
		Holder<String> outRESULT = new Holder<>();
        Holder<String> outRETMSG = new Holder<>();
        Holder<String> outRETCODE = new Holder<>();
		
		ss.getMDMTableConditionDataNewPt().process("S00763", "HAIERMDM", "HRHAIERBTB_CUST_INFO", hAIERMDMFIELDSVALUETABLE, outRESULT, outRETMSG, outRETCODE);

		
		
		MdmCustomersSource mdmCustomersSource = new MdmCustomersSource();
		
		if("S".equals(outRETCODE.value)){
			System.out.println(outRESULT.value);
			try {
				Document document = DocumentHelper.parseText(outRESULT.value);
				System.out.println(document);
				Element rowElt = document.getRootElement().element("ROWSET").element("ROW");
				mdmCustomersSource.setCusCode(rowElt.element("CUSTOMER_NUMBER").getText());
				mdmCustomersSource.setAccountGroup(rowElt.element("ACCOUNT_GROUP").getText());
				mdmCustomersSource.setComName(rowElt.element("CUSTOMER_NAME1").getText());
				mdmCustomersSource.setCusName(rowElt.element("CUSTOMER_NAME1").getText());
				mdmCustomersSource.setCusAbbName(rowElt.element("CUSTOMER_NAME1").getText());
				mdmCustomersSource.setAddress(rowElt.element("CITY_STREET_ROOM").getText());
				mdmCustomersSource.setStreet(rowElt.element("STREET_ROOM").getText());
				mdmCustomersSource.setTax(rowElt.element("TAX_CODE").getText());
				mdmCustomersSource.setTel(rowElt.element("PHONE_NUMBER").getText());
				mdmCustomersSource.setKind(rowElt.element("INDUSTRY_CLASS").getText());
				mdmCustomersSource.setDeleteFlag(rowElt.element("DELETE_FLAG").getText());
				mdmCustomersSource.setOrgId(rowElt.element("TRADE_CODE").getText());
				mdmCustomersSource.setDeleteFlag(rowElt.element("DELETE_FLAG").getText());
				//客户大类
				mdmCustomersSource.setCustomerCategory(rowElt.element("CUSTOMER_CATEGORY").getText());
				//客户小类
				mdmCustomersSource.setIndustryClass(rowElt.element("INDUSTRY_CLASS").getText());
				mdmCustomersSource.setMdmProvince(rowElt.element("ADDRESS_PROVINCE").getText());//省
				mdmCustomersSource.setMdmCity(rowElt.element("ADDRESS_CITY").getText());//市
				mdmCustomersSource.setMdmArea(rowElt.element("ADDRESS_AREA").getText());//区
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					mdmCustomersSource.setAlterDate(sdf.parse(rowElt.element("LAST_UPD").getText()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		
		
		
		return new MdmSourceReturn(mdmCustomersSource,outRETMSG.value,outRETCODE.value);
    
	}
	
	
	

	
}
