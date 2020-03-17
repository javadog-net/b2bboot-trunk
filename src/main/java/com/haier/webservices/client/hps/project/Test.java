package com.haier.webservices.client.hps.project;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Test {

    public static void main(String[] args) throws Exception {
        ProjectWebServiceServerStub.ProjectSaveParam[]  arr = new ProjectWebServiceServerStub.ProjectSaveParam[3];
        ProjectWebServiceServerStub.ProjectSaveParam param = new ProjectWebServiceServerStub.ProjectSaveParam();
        ProjectWebServiceServerStub.ProjectROneSaveParam rone = new  ProjectWebServiceServerStub.ProjectROneSaveParam();
        //需求ID msgId
        rone.setMsgId(223);
        //项目名称（公司名+时间戳）
        rone.setProjectName("B2B海尔企业购20190109002122");
        //项目来源（公司名+时间戳）
        rone.setProjectSource("B2B");
        //中心工贸
        rone.setCenter("12A02");
        //甲方公司名称
        rone.setFirstCompanyName("测试");
        //是否战略（默认false）
        rone.setBeStrategy(false);
        //项目录入人编码
        rone.setProjectCreaterCode("A0029325");
        rone.setProjectManagerCode("A002932");
        //产业模式（客单和直单 --- 直单:DIRECTIVD_ORDER,客单:CUSTOMER_ORDER）
        rone.setDomainModel("CUSTOMER_ORDER");
        rone.setAddressProvince("370000000000");
        rone.setAddressCity("370200000000");
        rone.setAddressCounty("370213000000");
        ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam[]  pfArr = new ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam[100];
        ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam pfParam = new ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam();
        pfParam.setProductGroup("BA");
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
        ProjectWebServiceServerStub sub1 = new ProjectWebServiceServerStub();
        ProjectWebServiceServerStub.SaveProjectFromQYGE saveProjectFromQYG0 = new ProjectWebServiceServerStub.SaveProjectFromQYGE();
        ProjectWebServiceServerStub.SaveProjectFromQYG p = new ProjectWebServiceServerStub.SaveProjectFromQYG();
        p.setArg0(arr);
        saveProjectFromQYG0.setSaveProjectFromQYG(p);
        ProjectWebServiceServerStub.SaveProjectFromQYGResponseE result = sub1.saveProjectFromQYG(saveProjectFromQYG0);
        JSONObject local_returnStr = JSONObject.fromObject(result.localSaveProjectFromQYGResponse.local_return);
        JSONObject local_returnTrackerStr = JSONObject.fromObject(result.localSaveProjectFromQYGResponse.local_returnTracker);
        Map<String,Object> map = new HashMap<>();
        map.put("local_returnStr",local_returnStr);
        map.put("local_returnTrackerStr",local_returnTrackerStr);
    }
}

