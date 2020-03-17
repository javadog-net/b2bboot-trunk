package com.haier.webservices.client.hps.project;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

 
public class MainQuery {
    public static void main(String[] args) {
        URL wsdlLocation = null;
        try {
            wsdlLocation = new URL("http://10.138.10.68:8090/soap/project");
            ProjectWebServiceServer_Service ser = new ProjectWebServiceServer_Service(wsdlLocation);
            QueryProjectManagerFromHPS queryProjectManagerFromHPS = new QueryProjectManagerFromHPS();
            ProjectROneSaveParam prone = new ProjectROneSaveParam();
            ProjectPurchaseForecastSaveParam projectPurchaseForecastSaveParam = new ProjectPurchaseForecastSaveParam();
            projectPurchaseForecastSaveParam.setProductGroup("BA");
            prone.setAddressProvince("370000000000");
            prone.setAddressCity("370200000000");
            prone.setAddressCounty("370213000000");
            prone.setDomainModel("DIRECTIVD_ORDER");
            List<ProjectPurchaseForecastSaveParam> listprojectPurchaseForecastSaveParam = new ArrayList<ProjectPurchaseForecastSaveParam>();
            listprojectPurchaseForecastSaveParam.add(projectPurchaseForecastSaveParam);
            prone.getPurchaseForecastList().addAll(listprojectPurchaseForecastSaveParam);
            queryProjectManagerFromHPS.setArg0(prone);
           List<UserDTO>  Listres = ser.getProjectWebServiceServerImplPort().queryProjectManagerFromHPS(prone);
           System.out.println(Listres);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


// 

    }
}
