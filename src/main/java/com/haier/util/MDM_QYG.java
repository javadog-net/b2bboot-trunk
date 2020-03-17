package com.haier.util;


import javax.xml.bind.JAXBElement;
import javax.xml.ws.Holder;

import com.haier.mdm.qygcx.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MDM_QYG {

	   /**
     * 功能：企业信息
     * @param sysName       系统编号
     * @param masterType    模块标识
     * @param tableName     MDM表名
     * @param fieldName     条件字段名
     * @param fieldValue    条件值
     * @param fieldType     条件查询类型可传“=”，“LIKE”，“IN”
     * @return              result为SUCCESS成功，msg为携带企业信息的成功信息
     *                      result为FAILURE失败，msg为失败信息
     */
    public static Map Query(String sysName,String masterType,String tableName,String fieldName,String fieldValue,String fieldType){
        Map<String,String> map = new HashMap<>();
        //webservice返回值
        Holder<String> outResult = new Holder<>();
        Holder<String> outRetMsg = new Holder<>();
        Holder<String> outRetCode = new Holder<>();
        MdmtableconditiondatanewClientEp mdmtableconditiondatanewClientEp = new MdmtableconditiondatanewClientEp();
        try {
        	HAIERMDMFIELDSVALUETABLE inFIELDSVALUETABLE = new HAIERMDMFIELDSVALUETABLE();
        	if(!"".equals(fieldValue)) {
        		HAIERMDMFIELDSVALUETYPE inFIELDSVALUETYPE = new HAIERMDMFIELDSVALUETYPE();
        		ObjectFactory objectFactory = new ObjectFactory();
        		JAXBElement<String> jfieldName = objectFactory.createHAIERMDMFIELDSVALUETYPEFIELDNAME(fieldName);
        		JAXBElement<String> jfieldValue = objectFactory.createHAIERMDMFIELDSVALUETYPEFIELDVALUE(fieldValue);
        		JAXBElement<String> jfieldType = objectFactory.createHAIERMDMFIELDSVALUETYPEFIELDQUERYTYPE(fieldType);
        		inFIELDSVALUETYPE.setFIELDNAME(jfieldName);
        		inFIELDSVALUETYPE.setFIELDVALUE(jfieldValue);
        		inFIELDSVALUETYPE.setFIELDQUERYTYPE(jfieldType);
        		inFIELDSVALUETABLE.getINFIELDSVALUETABLEITEM().add(inFIELDSVALUETYPE);
        	mdmtableconditiondatanewClientEp.getMDMTableConditionDataNewPt().process(sysName, masterType, tableName, inFIELDSVALUETABLE, outResult, outRetMsg, outRetCode);
            if ("S".equals(outRetCode.value)) {
                map.put("return_code", "SUCCESS");
                map.put("result", outResult.value);
                map.put("msg", outRetMsg.value);
            } else {
                map.put("return_code", "FAILURE");
                map.put("result", outResult.value);
                map.put("msg", outRetMsg.value);
            }
          }
        }catch (Exception e){
            map.put("return_code", "FAILURE");
            map.put("result", outResult.value);
            map.put("msg", outRetMsg.value);
            map.put("return_msg", "失败："+e.getMessage());
        }
        return map;
    }


    public static void main(String[] args) {
    	Map map = Query("S00763", "HAIERMDM", "HRHAIERBTB_HM_CUSTOMERS","CUSTOMER_NAME1","青岛海振威模具制品有限公司","IN");
    	System.out.println(map);
    	String ret=map.get("return_code").toString();
    	if("SUCCESS".equals(ret)){
    		Map map1 = readStringXmlOut(map.get("result").toString());
    		List list=(List)map1.get("date");
    		System.out.println(list.size());
    	}
	}
    
    /**
     * @description 将xml字符串转换成map
     * @param xml
     * @return Map
     */
    public static Map readStringXmlOut(String xml) {
        Map map = new HashMap();
        Document doc = null;
        try {
            // 将字符串转为XML
            doc = DocumentHelper.parseText(xml); 
            // 获取根节点
            Element rootElt = doc.getRootElement(); 
            // 拿到根节点的名称
            System.out.println("根节点：" + rootElt.getName()); 

            // 获取根节点下的子节点head
            Iterator iter = rootElt.elementIterator("ROWSET"); 
            // 遍历head节点
            while (iter.hasNext()) {

                Element recordEle = (Element) iter.next();
                // 拿到head节点下的子节点title值
                String NAME = recordEle.elementTextTrim("NAME"); 
                String MODULE=recordEle.elementTextTrim("MODULE"); 
                map.put("NAME", NAME);
                map.put("MODULE", MODULE);
                // 获取子节点head下的子节点script
                Iterator ROW = recordEle.elementIterator("ROW"); 
                // 遍历Header节点下的Response节点
                List<CustomerDateModel> list=new ArrayList<>();
                while (ROW.hasNext()) {
                    Element itemEle = (Element) ROW.next();
                    // 拿到head下的子节点script下的字节点username的值
                    String CUSTOMER_NUMBER = itemEle.elementTextTrim("CUSTOMER_NUMBER"); 
                    String CUSTOMER_NAME1 = itemEle.elementTextTrim("CUSTOMER_NAME1");
                    String CUSTOMER_CATEGORY=itemEle.elementTextTrim("CUSTOMER_CATEGORY");
                    String DELETE_FLAG=itemEle.elementTextTrim("DELETE_FLAG");
                    CustomerDateModel  cm=new CustomerDateModel();
                    cm.setNUMBER(CUSTOMER_NUMBER);
                    cm.setNAME1(CUSTOMER_NAME1);
                    cm.setCATEGORY(CUSTOMER_CATEGORY);
                    cm.setFLAG(DELETE_FLAG);
                    list.add(cm);
                }
                map.put("date", list);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public static Map readStringXmlOutInfo(String xml) {
        Map map = new HashMap();
        Document doc = null;
        try {
            // 将字符串转为XML
            doc = DocumentHelper.parseText(xml);
            // 获取根节点
            Element rootElt = doc.getRootElement();
            // 拿到根节点的名称
            System.out.println("根节点：" + rootElt.getName());

            // 获取根节点下的子节点head
            Iterator iter = rootElt.elementIterator("ROWSET");
            // 遍历head节点
            while (iter.hasNext()) {

                Element recordEle = (Element) iter.next();
                // 拿到head节点下的子节点title值
                String NAME = recordEle.elementTextTrim("NAME");
                String MODULE=recordEle.elementTextTrim("MODULE");
                System.out.println("节点：" + NAME);
                map.put("NAME", NAME);
                map.put("MODULE", MODULE);
                // 获取子节点head下的子节点script
                Iterator ROW = recordEle.elementIterator("ROW");
                // 遍历Header节点下的Response节点
                List<CustomerInfoDateModel> list=new ArrayList<CustomerInfoDateModel>();
                while (ROW.hasNext()) {
                    Element itemEle = (Element) ROW.next();
                    // 拿到head下的子节点script下的字节点username的值
                    String ROW_ID = itemEle.elementTextTrim("ROW_ID");
                    String CUSTOMER_NUMBER = itemEle.elementTextTrim("CUSTOMER_NUMBER");
                    String ACCOUNT_GROUP=itemEle.elementTextTrim("ACCOUNT_GROUP");
                    String CUSTOMER_NAME1=itemEle.elementTextTrim("CUSTOMER_NAME1");
                    String CITY_STREET_ROOM=itemEle.elementTextTrim("CITY_STREET_ROOM");
                    String STREET_ROOM=itemEle.elementTextTrim("STREET_ROOM");
                    String TAX_CODE=itemEle.elementTextTrim("TAX_CODE");
                    String PHONE_NUMBER=itemEle.elementTextTrim("PHONE_NUMBER");
                    String CUSTOMER_CATEGORY=itemEle.elementTextTrim("CUSTOMER_CATEGORY");
                    String INDUSTRY_CLASS=itemEle.elementTextTrim("INDUSTRY_CLASS");
                    String TRADE_CODE=itemEle.elementTextTrim("TRADE_CODE");
                    String DELETE_FLAG=itemEle.elementTextTrim("DELETE_FLAG");
                    String PARTNER_FLAG_SP=itemEle.elementTextTrim("PARTNER_FLAG_SP");
                    String LAST_UPD=itemEle.elementTextTrim("LAST_UPD");
                    String SALES_REGION=itemEle.elementTextTrim("SALES_REGION");
                    String MANAGE_CUSTOMER=itemEle.elementTextTrim("MANAGE_CUSTOMER");


                    CustomerInfoDateModel cidm = new CustomerInfoDateModel();
                    cidm.setROW_ID(ROW_ID);
                    cidm.setCUSTOMER_NUMBER(CUSTOMER_NUMBER);
                    cidm.setACCOUNT_GROUP(ACCOUNT_GROUP);
                    cidm.setCUSTOMER_NAME1(CUSTOMER_NAME1);
                    cidm.setCITY_STREET_ROOM(CITY_STREET_ROOM);
                    cidm.setSTREET_ROOM(STREET_ROOM);
                    cidm.setTAX_CODE(TAX_CODE);
                    cidm.setPHONE_NUMBER(PHONE_NUMBER);
                    cidm.setCUSTOMER_CATEGORY(CUSTOMER_CATEGORY);
                    cidm.setINDUSTRY_CLASS(INDUSTRY_CLASS);
                    cidm.setTRADE_CODE(TRADE_CODE);
                    cidm.setDELETE_FLAG(DELETE_FLAG);
                    cidm.setPARTNER_FLAG_SP(PARTNER_FLAG_SP);
                    cidm.setLAST_UPD(LAST_UPD);
                    cidm.setSALES_REGION(SALES_REGION);
                    cidm.setMANAGE_CUSTOMER(MANAGE_CUSTOMER);

                    list.add(cidm);
                }
                map.put("date", list);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
