package com.haier.b2b.api.wsClient;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;

import java.rmi.RemoteException;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 18:49 2019/2/14
 * @Modified By
 */
public class Main {
    public static void main(String[] args) {
        SendMessage("18306390693","haha");
    }

    protected static String  SendMessage(String mobilePhone, String content){
        SendMesServiceSingleStub stub = null;
//			String targetEndpoint = "http://10.135.1.198:7001/EAI/RoutingProxyService/EAI_SOAP_ServiceRoot?INT_CODE=EAI_INT_1704";//测试
        String targetEndpoint = "http://10.135.7.152:7001/EAI/RoutingProxyService/EAI_SOAP_ServiceRoot?INT_CODE=EAI_INT_1704";
        String result = "";
        try {
            stub = new SendMesServiceSingleStub(targetEndpoint);
            Options options = stub._getServiceClient().getOptions();
            options.setTimeOutInMilliSeconds(10000);
            options.setProperty(HTTPConstants.SO_TIMEOUT, 10000);
            options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, 10000);
            stub._getServiceClient().setOptions(options);
            SendMesServiceSingleStub.InputType inputType = new SendMesServiceSingleStub.InputType();
            SendMesServiceSingleStub.SendMesServiceSingle sendMess = new SendMesServiceSingleStub.SendMesServiceSingle();
            inputType.setDepartment("B2B");
            inputType.setMsgCode("B2B");
            inputType.setPhoneNum(mobilePhone);
            inputType.setMsgContnet(content);
            sendMess.setIn(inputType);
            SendMesServiceSingleStub.SendMesServiceSingleResponse response = stub.sendMesServiceSingle(sendMess);
            SendMesServiceSingleStub.OutputType outputType = response.getOut();
            result = outputType.getResult();
            String msgCode = outputType.getMsgCode();
        } catch (AxisFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auteo-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
