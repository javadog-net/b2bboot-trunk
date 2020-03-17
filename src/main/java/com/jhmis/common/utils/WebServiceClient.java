package com.jhmis.common.utils;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import javax.xml.soap.SOAPHeader;

/**
 * 如何从wsdl中获取需要的命名空间等参数
 * 打开wsdl文件找到前面的targetNamespace这个就是nameSpace参数
 * wsdlUrl就是指webservice的服务地址
 * serviceName 查找service name = 可以获取到服务名，或者soapUi建立soap项目，根目录就是项目名字
 * portName 查找port name= APPQD_WebServiceSoap，APPQD_WebServiceSoap12，一般有这两个，或者soapUi，根目录下面的节点就是portName
 * operationName 就是需要调用的接口名称
 * responseName 根据soapUi执行看返回的结果类型
 */
public class WebServiceClient {
    public static final String SOAP_1_1_PROTOCOL = "SOAP 1.1 Protocol";

    /**
     * Used to create <code>MessageFactory</code> instances that create
     * <code>SOAPMessages</code> whose behavior supports the SOAP 1.2
     * specification
     *
     * @since  SAAJ 1.3
     */
    public static final String SOAP_1_2_PROTOCOL = "SOAP 1.2 Protocol";

    String nameSpace = "";
    String wsdlUrl = "";
    String serviceName = "";
    String portName = "";
    String responseName = "";
    String operationName = "";
    String prefix = "ser";
    private String protocol = SOAP_1_2_PROTOCOL;
    //引用自JAXWSProperties,避免使用这些包后，要依赖jdk
    private static String REQUEST_TIMEOUT = "com.sun.xml.internal.ws.request.timeout";
    private static String CONNECT_TIMEOUT = "com.sun.xml.internal.ws.connect.timeout";
    int timeout = 20000;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @param nameSpace
     * @param wsdlUrl
     * @param serviceName
     * @param portName
     * @param operationName
     * @param responseName
     */
    public WebServiceClient(String nameSpace, String wsdlUrl,
                            String serviceName, String portName, String operationName,
                            String responseName) {
        this.nameSpace = nameSpace;
        this.wsdlUrl = wsdlUrl;
        this.serviceName = serviceName;
        this.portName = portName;
        this.operationName = operationName;
        this.responseName = responseName;
    }

    /**
     * @param nameSpace
     * @param wsdlUrl
     * @param serviceName
     * @param portName
     * @param operationName
     * @param responseName
     * @param timeOut
     */
    public WebServiceClient(String nameSpace, String wsdlUrl,
                            String serviceName, String portName, String operationName,
                            String responseName, int timeOut) {
        this.nameSpace = nameSpace;
        this.wsdlUrl = wsdlUrl;
        this.serviceName = serviceName;
        this.portName = portName;
        this.operationName = operationName;
        this.responseName = responseName;
        this.timeout = timeOut;
    }

    public org.dom4j.Document sendMessage(HashMap<String, String> inMsg) throws Exception {
        // 创建URL对象
        URL url = null;
        try {
            url = new URL(wsdlUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建URL对象异常");
            //return "创建URL对象异常";
        }
        // 创建服务(Service)
        QName sname = new QName(nameSpace, serviceName);
        Service service = Service.create(url, sname);

        // 创建Dispatch对象
        Dispatch<SOAPMessage> dispatch = null;
        try {
            dispatch = service.createDispatch(new QName(nameSpace, portName),
                    SOAPMessage.class, Service.Mode.MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("创建Dispatch对象异常");
            //return "创建Dispatch对象异常";
        }

        // 创建SOAPMessage
        try {
            SOAPMessage msg = MessageFactory.newInstance(
                    protocol).createMessage();
            msg.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");

            SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();

            // 创建SOAPHeader(不是必需)
            // SOAPHeader header = envelope.getHeader();
            // if (header == null)
            // header = envelope.addHeader();
            // QName hname = new QName(nameSpace, "username", "nn");
            // header.addHeaderElement(hname).setValue("huoyangege");

            // 创建SOAPBody
            SOAPBody body = envelope.getBody();
            QName ename = new QName(nameSpace, operationName, prefix);
            SOAPBodyElement ele = body.addBodyElement(ename);
            // 增加Body元素和值
            for (Map.Entry<String, String> entry : inMsg.entrySet()) {
                ele.addChildElement(new QName(nameSpace, entry.getKey()))
                        .setValue(entry.getValue());
            }

            // 超时设置
            dispatch.getRequestContext().put(CONNECT_TIMEOUT, timeout);
            dispatch.getRequestContext().put(REQUEST_TIMEOUT,
                    timeout);

            // 通过Dispatch传递消息,会返回响应消息
            SOAPMessage response = dispatch.invoke(msg);

            // 响应消息处理,将响应的消息转换为doc对象
            Document doc = response.getSOAPPart().getEnvelope().getBody()
                    .extractContentAsDocument();
            org.dom4j.io.DOMReader   xmlReader   =   new   org.dom4j.io.DOMReader();
        /*    String ret = doc.getElementsByTagName(responseName).item(0)
                    .getTextContent();*/
            return xmlReader.read(doc);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
