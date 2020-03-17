package com.haier.webservices.client.hps.file;

import com.haier.webservices.client.hps.verify.*;
import com.jhmis.common.utils.Constants;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class HpsFileApi {

    public static final String RESINFOBROWN_CODE_Y = "Y";

    static URL wsdlLocation;

    static {
        try {
            //wsdlLocation = new URL("http://10.138.10.68:8090/soap/file?wsdl");
            wsdlLocation = new URL(Constants.HPS_URL + "/soap/file?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //uploadFromHPS();
    }

    /**
     * queryPageForVerify 工程版信息获取接口
     * page:当前页
     * rows:每页数
     * dealerCode:dealerCode
     * @return
     */
    public static ResInfoFile uploadFromHPS(File file){
        FileWebService_Service ser = new FileWebService_Service(wsdlLocation);
        FileWrapper fileWrapper = new FileWrapper();
        //File file = new File("F:\\logo1.png");
        DataSource dataSource = new FileDataSource(file);
        DataHandler dataHandler = new DataHandler(dataSource);
        fileWrapper.setFile(dataHandler);
        fileWrapper.setFileName("测试");
        ResInfoFile resFile = ser.getFileWebServiceImplPort().upload(fileWrapper);
        System.out.println(resFile);
        return resFile;
    }


    /**
     * getVerifyBillFromBrown 根据工程版ID获取基础信息
     * id:工程单id
     * @return
     */
    public static ResInfoVerifyInfo getVerifyBillFromBrownFromHPS(String id){
        VerifyBillWebService_Service ser = new VerifyBillWebService_Service(wsdlLocation);
        ResInfoVerifyInfo resInfoVerifyInfo =  ser.getVerifyBillWebServiceImplPort().getVerifyBillFromBrown(id);
        return resInfoVerifyInfo;
    }


}
