package com.haier.webservices.client.hps.project;

import com.jhmis.modules.customer.entity.CustomerMsg;
import net.sf.json.JSONObject;
import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

 
public class SaveProjectFromQYGService {

    public static Object excute(CustomerMsg customerMsg){
      Map<String,Object> map = new HashMap<>();
        try {
            ProjectWebServiceServerStub.ProjectSaveParam[]  arr = new ProjectWebServiceServerStub.ProjectSaveParam[3];
            ProjectWebServiceServerStub.ProjectSaveParam param = new ProjectWebServiceServerStub.ProjectSaveParam();
            ProjectWebServiceServerStub.ProjectROneSaveParam rone = new  ProjectWebServiceServerStub.ProjectROneSaveParam();
            //??ID msgId
            //rone.setMsgId(customerMsg.getMsgId());
            rone.setMsgId(111);
            //????????+????
            rone.setProjectName(customerMsg.getProjectName());
            //???
            rone.setAddressProvince(customerMsg.getAddressProvince());
            rone.setAddressCity(customerMsg.getAddressCity());
            rone.setAddressCounty(customerMsg.getAddressCounty());
            //??????
            rone.setFirstCompanyName(customerMsg.getFirstCompanyName());
            //???????
            rone.setProjectCreaterCode(customerMsg.getProjectCreaterCode());
            //?????????? --- ??:DIRECTIVD_ORDER,??:CUSTOMER_ORDER?
            rone.setDomainModel(customerMsg.getDomainModel());
            ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam[]  pfArr = new ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam[100];
            //???
            String []args = customerMsg.getProductGroup().split(",");
            for(int i=0; i<args.length; i++){
                ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam pfParam = new ProjectWebServiceServerStub.ProjectPurchaseForecastSaveParam();
                pfParam.setProductGroup(customerMsg.getDomainModel());
                pfArr[i] = pfParam;
            }
            ProjectWebServiceServerStub sub1 = new ProjectWebServiceServerStub();
            ProjectWebServiceServerStub.SaveProjectFromQYGE saveProjectFromQYG0 = new ProjectWebServiceServerStub.SaveProjectFromQYGE();
            ProjectWebServiceServerStub.SaveProjectFromQYG p = new ProjectWebServiceServerStub.SaveProjectFromQYG();
            param.setProjectROneSaveParam(rone);
            arr[1] = param;
            p.setArg0(arr);
            saveProjectFromQYG0.setSaveProjectFromQYG(p);
            ProjectWebServiceServerStub.SaveProjectFromQYGResponseE result = sub1.saveProjectFromQYG(saveProjectFromQYG0);
            System.out.println(result.localSaveProjectFromQYGResponse.local_return);
            JSONObject local_returnStr = JSONObject.fromObject(result.localSaveProjectFromQYGResponse.local_return);
            Boolean local_returnTrackerStr = result.localSaveProjectFromQYGResponse.local_returnTracker;
            map.put("local_returnStr",local_returnStr);
            map.put("local_returnTrackerStr",local_returnTrackerStr);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return map;
    }
}
