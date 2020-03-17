package com.haier.webservices.client.hps.project;

import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

 
public class Main {
    public static void main(String[] args) throws Exception {
        String s = getOrderIdByTime();
        System.out.println(s);
        ProjectWebServiceServerStub.ProjectSaveParam[]  arr = new ProjectWebServiceServerStub.ProjectSaveParam[3];
        ProjectWebServiceServerStub.ProjectSaveParam param = new ProjectWebServiceServerStub.ProjectSaveParam();
        ProjectWebServiceServerStub.ProjectROneSaveParam rone = new  ProjectWebServiceServerStub.ProjectROneSaveParam();
        //需求ID msgId
        rone.setMsgId(14);
        //项目名称（公司名+时间戳）
        rone.setProjectName("B2B海尔企业购201901090021");
        //项目来源（公司名+时间戳）
        rone.setProjectSource("B2B");
        //中心工贸
        rone.setCenter("12A02");
        //甲方公司名称
        rone.setFirstCompanyName("测试");
        //是否战略（默认false）
        rone.setBeStrategy(false);
        //项目地址（省）
        rone.setAddressProvince("北京");
        //项目地址（市）
        rone.setAddressCity("北京市辖");
        //项目录入人编码
        rone.setProjectCreaterCode("00616366");
        //产业模式（客单和直单 --- 直单:DIRECTIVD_ORDER,客单:CUSTOMER_ORDER）
        rone.setDomainModel("CUSTOMER_ORDER");
        rone.setProjectManagerId("123");
        rone.setProjectManagerCode("00616366");
        rone.setProjectManagerName("糊糊");
        ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam[]  pfArr = new ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam[100];
        ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam pfParam = new ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam();
        pfParam.setProductGroup("BA");
        pfParam.setEstimatedQuantity(2L);
        pfParam.setPurchaseBudget(new BigDecimal(20));
        pfArr[0] = pfParam;
        rone.setPurchaseForecastList(pfArr);
        ProjectWebServiceServerStub.ProjectRTwoSaveParam rtwo = new ProjectWebServiceServerStub.ProjectRTwoSaveParam();
        //甲方联系人姓名
        rtwo.setFirstContactName("张三007");
        //甲方联系人电话
        rtwo.setPhone("15588886666");
        //项目经理编码同上
        param.setProjectROneSaveParam(rone);
        param.setProjectRTwoSaveParam(rtwo);
        arr[1] = param;
        ProjectWebServiceServerStub sub1 = new ProjectWebServiceServerStub("http://10.138.10.68:8090/soap/project");
        ProjectWebServiceServerStub.SaveProjectFromQYGE saveProjectFromQYG0 = new ProjectWebServiceServerStub.SaveProjectFromQYGE();
        ProjectWebServiceServerStub.SaveProjectFromQYG p = new ProjectWebServiceServerStub.SaveProjectFromQYG();
        p.setArg0(arr);
        saveProjectFromQYG0.setSaveProjectFromQYG(p);
        ProjectWebServiceServerStub.SaveProjectFromQYGResponseE result = sub1.saveProjectFromQYG(saveProjectFromQYG0);
        System.out.println(result.localSaveProjectFromQYGResponse.local_return);
        JSONObject jsStr = JSONObject.fromObject(result);
    }
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

}
