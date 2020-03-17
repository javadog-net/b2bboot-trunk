package com.haier.webservices.client.hps.verify;

import com.jhmis.api.customer.ApiCustomerController;
import com.jhmis.common.utils.Constants;
import com.jhmis.modules.customer.entity.ViewQygBrown;
import com.jhmis.modules.customer.entity.ViewQygBrownInfo;
import com.jhmis.modules.customer.entity.ViewQygBrownItem;

import net.sf.json.JSONObject;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HpsVerifyApi {
	
    public static final String RESINFOBROWN_CODE_Y = "Y";

    static Logger logger = LoggerFactory.getLogger(ApiCustomerController.class);
    
    static URL wsdlLocation;

    static {
        try {
            wsdlLocation = new URL(Constants.HPS_URL + "/soap/verify?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       //工程版信息获取接口 测试main方法
        //queryPageForVerifyFromHPS(1,20,"8700000983","");
        //工程版信息获取接口 测试main方法
        getVerifyBillFromBrownFromHPS("1557022806649dc63dc77329947aba9c51fd3319e10b1");
    }

     
    public static ResInfoBrown queryPageForVerifyFromHPS(int page,int rows, String dealerCode,String brownCode){
        VerifyBillWebService_Service ser = new VerifyBillWebService_Service(wsdlLocation);
        QueryPageForVerify queryPageForVerify = new QueryPageForVerify();
        WebserviceBrownQueryPagePara webserviceBrownQueryPagePara = new WebserviceBrownQueryPagePara();
        webserviceBrownQueryPagePara.setDealerCode(dealerCode);
        webserviceBrownQueryPagePara.setPage(page);
        webserviceBrownQueryPagePara.setRows(rows);
        webserviceBrownQueryPagePara.setBrownCode(brownCode);
        queryPageForVerify.setArg0(webserviceBrownQueryPagePara);
        ResInfoBrown res = ser.getVerifyBillWebServiceImplPort().queryPageForVerify(webserviceBrownQueryPagePara);
        return res;
    }


     
    public static ResInfoVerifyInfo getVerifyBillFromBrownFromHPS(String id){
        VerifyBillWebService_Service ser = new VerifyBillWebService_Service(wsdlLocation);
        ResInfoVerifyInfo resInfoVerifyInfo =  ser.getVerifyBillWebServiceImplPort().getVerifyBillFromBrown(id);
        return resInfoVerifyInfo;
    }

     
    public static ResInfo saveOrUpdateFromHPS(VerifyBillSaveVo verifyBillSaveVo, ViewQygBrown v,ViewQygBrownInfo viewQygBrownInfo, List<ViewQygBrownItem> listViewQygBrownItem){
        List<VerifyBillDetailSaveParam> listVerifyBillDetailSaveParam = new ArrayList<VerifyBillDetailSaveParam>();
        String[] argQuantity = verifyBillSaveVo.getQualityStr().split(",");
        for(int i=0; i<listViewQygBrownItem.size(); i++){
            VerifyBillDetailSaveParam verifyBillDetailSaveParam = new VerifyBillDetailSaveParam();
            verifyBillDetailSaveParam.setCbillCode(v.getBrownCode());
            verifyBillDetailSaveParam.setProductGroupCode(v.getProductGroupCode());
            verifyBillDetailSaveParam.setProductGroupName(v.getProductGroupName());
            verifyBillDetailSaveParam.setAddress(v.getCaddress());
            verifyBillDetailSaveParam.setModelCode(listViewQygBrownItem.get(i).getProductCode());
            verifyBillDetailSaveParam.setModelName(listViewQygBrownItem.get(i).getProductName());
            verifyBillDetailSaveParam.setProjectCode(v.getProjectCode());
            if(argQuantity.length>0){
                verifyBillDetailSaveParam.setQuantity(Integer.parseInt(argQuantity[i]));
            }
            listVerifyBillDetailSaveParam.add(verifyBillDetailSaveParam);
        }
        VerifyBillSaveParam verifyBillSaveParam = new VerifyBillSaveParam();
        verifyBillSaveParam.setCbillCode(verifyBillSaveVo.getCbillCode());
        verifyBillSaveParam.setProjectName(v.getProjectName());        
        verifyBillSaveParam.setProjectAddress(viewQygBrownInfo.getProjectAddress());       
        verifyBillSaveParam.setCustCode(v.getDealerCode());
        verifyBillSaveParam.setCustName(v.getDealerName());       
        verifyBillSaveParam.setProductGroupCode(v.getProductGroupCode());
        verifyBillSaveParam.setProductGroupName(v.getProductGroupName());
        verifyBillSaveParam.setCenter(v.getCenter());
        verifyBillSaveParam.setCenterName(v.getCenterName());      
        verifyBillSaveParam.setDomain(v.getDomainCode()); 
        verifyBillSaveParam.setInstaller(verifyBillSaveVo.getInstaller());
        verifyBillSaveParam.setInstallerLinkman(verifyBillSaveVo.getInstallerLinkman());
        verifyBillSaveParam.setInstallerLinkmanPhone(verifyBillSaveVo.getInstallerLinkmanPhone());
        verifyBillSaveParam.setInvoiceCode(verifyBillSaveVo.getInvoiceCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            System.out.println(sdf.parse(verifyBillSaveVo.getInvoiceDateStr()));
            verifyBillSaveParam.setInvoiceDate(dateToXmlDate(sdf.parse(verifyBillSaveVo.getInvoiceDateStr())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        verifyBillSaveParam.setCbillId(verifyBillSaveVo.getCbillId());
        verifyBillSaveParam.setProjectCode(v.getProjectCode());
        verifyBillSaveParam.setProjectId(v.getProjectId());
        verifyBillSaveParam.setSaleMoney(verifyBillSaveVo.getSaleMoney());
        verifyBillSaveParam.setTaxId(verifyBillSaveVo.getTaxId());
        verifyBillSaveParam.setTaxMoney(verifyBillSaveVo.getTaxMoney());
        verifyBillSaveParam.setInvoiceNumber(verifyBillSaveVo.getInvoiceNumber());
        verifyBillSaveParam.setInvoiceFile(verifyBillSaveVo.getInvoiceFile());
        verifyBillSaveParam.setMarketGrade(verifyBillSaveVo.getMarketGrade());
        verifyBillSaveParam.setGvsQuantity(verifyBillSaveVo.getGvsQuantity());
        verifyBillSaveParam.setAddressProvince(verifyBillSaveVo.getAddressProvince());
        verifyBillSaveParam.setAddressCity(verifyBillSaveVo.getAddressCity());
        verifyBillSaveParam.setAddressCounty(verifyBillSaveVo.getAddressCounty());
        verifyBillSaveParam.setBillQuantity(verifyBillSaveVo.getBillQuantity());
        verifyBillSaveParam.setVerifyQuantity(verifyBillSaveVo.getVerifyQuantity());        
        verifyBillSaveParam.setVerifyRate(verifyBillSaveVo.getVerifyRate());
        verifyBillSaveParam.getList().addAll(listVerifyBillDetailSaveParam);
        verifyBillSaveParam.setVerifyStatus("1");
        String verifyJson = JSONObject.fromObject(verifyBillSaveParam).toString();
        logger.info("*_*_*_*_*_*_*_*_*_* 给hps传递的验收或申诉参数="+ verifyJson +" *_*_*_*_*_*_*_*_*_*");
        VerifyBillWebService_Service ser = new VerifyBillWebService_Service(wsdlLocation);
        ResInfo resInfo =  ser.getVerifyBillWebServiceImplPort().saveOrUpdate(verifyBillSaveParam,"1");
        return resInfo;
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
}
