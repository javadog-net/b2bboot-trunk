package com.haier.webservices.client.hps.exception;

import com.haier.webservices.client.hps.file.FileWebService_Service;
import com.haier.webservices.client.hps.file.FileWrapper;
import com.haier.webservices.client.hps.file.ResInfoFile;
import com.haier.webservices.client.hps.verify.ResInfoVerifyInfo;
import com.haier.webservices.client.hps.verify.VerifyBillWebService_Service;
import com.jhmis.api.customer.ApiCustomerController;
import com.jhmis.common.utils.Constants;
import com.jhmis.modules.customer.entity.BrownException;
import com.jhmis.modules.customer.entity.ViewQygBrownItem;

import net.sf.json.JSONObject;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HpsExceptionApi {

    public static final String RESINFOBROWN_CODE_Y = "Y";
    
    static Logger logger = LoggerFactory.getLogger(ApiCustomerController.class);

    static URL wsdlLocation;

    static {
        try {
            wsdlLocation = new URL(Constants.HPS_URL + "/soap/createBrownException?wsdl ");
            //wsdlLocation = new URL("http://10.138.111.55:8090/soap/createBrownException?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //uploadFromHPS();
    }

     
    public static String createBrownExceptionFromHps(BrownException e, List<ViewQygBrownItem> listViewQygBrownItem){
        BrownExceptionWebserviceImplService ser = new BrownExceptionWebserviceImplService(wsdlLocation);
        BrownExceptionSaveParam param = new BrownExceptionSaveParam();
        List<ExceptionProductSaveParam> list = new ArrayList<ExceptionProductSaveParam>();
        String quantityStr = e.getQuantityStr();
        String[] str = quantityStr.split(",");
        for(int i=0; i<listViewQygBrownItem.size(); i++){     	
            ExceptionProductSaveParam exceptionProductSaveParam = new ExceptionProductSaveParam();
            exceptionProductSaveParam.setProductCode(listViewQygBrownItem.get(i).getProductCode());
            exceptionProductSaveParam.setProductName(listViewQygBrownItem.get(i).getProductName());
            exceptionProductSaveParam.setDrownApplyQuantity(Long.parseLong(listViewQygBrownItem.get(i).getOrderQuantity()));           
            exceptionProductSaveParam.setQuantityUnavailable(Long.parseLong(str[i]));           
            list.add(exceptionProductSaveParam);
            
        }
        param.setBrownId(e.getBrownId());
        param.setBrownCode(e.getBrownCode());
        param.setQuestioner(e.getQuestioner());
        param.setContactPerson(e.getContactPerson());
        param.setPhone(e.getPhone());
        param.setProposeDate(dateToXmlDate(new Date()));
        param.setReasonFlag(e.getReasonFlag());
        param.setReasonInfo(e.getReasonInfo());
        param.setFileId(e.getFileId());
        param.getParam().addAll(list);
        String brownExceptionJson = JSONObject.fromObject(param).toString();
        logger.info("*_*_*_*_*_*_*_*_*_* 给hps传递的异常报备参数="+ brownExceptionJson +" *_*_*_*_*_*_*_*_*_*");
        System.out.println(brownExceptionJson);
        String res = ser.getBrownExceptionWebserviceImplPort().createBrownException(param);
        return res;
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
