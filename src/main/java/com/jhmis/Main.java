package com.jhmis;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.ServiceInfo;

import com.alibaba.fastjson.JSONObject;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 18:15 2018/12/11
 * @Modified By
 */
public class Main {
	//private static String masterSecret = "nefyw2MZRc6JPljLNawir8";
	//static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	public static NotificationTemplate notificationTemplateDemo(String appId, String appkey) {
		NotificationTemplate template = new NotificationTemplate();

		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
	//	template.setChannel("1646d700a1d05012ad1871b66f45ce48");

		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent("请输入您要透传的内容");
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		return template;
	}

	public static TransmissionTemplate getTemplate(String appId,String appKey) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);  
		template.setAppkey(appKey); 
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		/* Map<String, Object> contentMap = new HashMap<String, Object>();
	        contentMap.put( "pushType", 11 );
	        contentMap.put( "content", "我是消息" );
	        String jsonString = JSON.toJSONString(contentMap);
	        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
*/	        template.setTransmissionType(2);
	        //template.setTransmissionContent(jsonString);
		//如果客户端是ios的话，需要需要设置setAPNInfo参数 
		APNPayload payload = new APNPayload(); 
		//在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setBadge(1);
		payload.setAutoBadge("1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("$由客户端定义"); 
		//推送消息模板 ios 
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello")); 
		//payload.addCustomMsg("type",3);
		template.setAPNInfo(payload); 
		return template;
	
		
		
	}

	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg() {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody("body");
		alertMsg.setActionLocKey("ActionLockey");
		alertMsg.setLocKey("LocKey");
		alertMsg.addLocArg("loc-args");
		alertMsg.setLaunchImage("launch-image");
		// iOS8.2以上版本支持
		alertMsg.setTitle("Title");
		alertMsg.setTitleLocKey("TitleLocKey");
		alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
	}

	
	
	
	
	
	public static void dd(String CID) {
		
		String host = "http://sdk.open.api.igexin.com/apiex.htm";
		 String appId = "Je31M4l6KhAwD7q0F970V8";
		String appKey ="HS9chM48so5XYYFSmGW2K1";
		 String masterSecret = "nefyw2MZRc6JPljLNawir8";
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		//LinkTemplate template = linkTemplateDemo(meetingid, content, title);
		//NotificationTemplate template=notificationTemplateDemo(appId,appKey);
		TransmissionTemplate template=getTemplate(appId, appKey);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(CID);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
			System.out.println(ret.getResponse().get("result"));
			// return ret.getResponse().get("result").toString();
		} else {
			System.out.println("服务器响应异常");
			// return "error";
		}
	}
	
	public static void maindd(String[] args) {
		dd("854F325F92DDD6D12918A13F3CF9958306C7872ED495F257BE6116B115D1F04E");
		
	}

	public static void mainss(String[] args) throws Exception {
		/*List<Object> listParam=new ArrayList<>();
		//String params="{\"newCode\":\"1111\",\"oldCode\":23}";
		//listParam.add(params);
		Map<String, String> map=new HashMap<String,String>();
		map.put("oldCode", "33");
		map.put("newCode", "111");
		String  ss="newCode";
		java.lang.Object[] pa = new Object[2] ;
		Object[] parameters = new Object[]{"11","22"};
	    JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	    Client client = dcf.createClient("http://b2b.haier.com/shop/soap/hps?wsdl");
	   
	    Object[] objects = null;
		try {
			objects = client.invoke("transCode",parameters);
			System.out.println(objects.toString());
		String str=	JSONObject.toJSONString(objects);
		System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //输出调用结果
	   */
		
		//com.jhmis.modules.shop.entity.dealer.DealerForAcg
		//com.haier.webservices.server.acg.DealerForAcg
		//loadClass 这个值 是service接口命名空间路径
		
		 JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		    Client client = dcf.createClient("http://b2b.haier.com/shop/soap/acg?wsdl");
		   
		ServiceInfo serviceInfo=client.getEndpoint().getService().getServiceInfos().get(0);
		//System.out.println(JSONObject.toJSONString(serviceInfo)+"serviceInfo");
		Set qSet=serviceInfo.getMessages().keySet();
		Iterator qIter=qSet.iterator();
		while(qIter.hasNext()){
		QName q=(QName) qIter.next();
		String packageName=JAXBUtils.namespaceURIToPackage(serviceInfo.getName().getNamespaceURI());
		//类名
		String className=JAXBUtils.nameToIdentifier(q.getLocalPart(),JAXBUtils.IdentifierType.INTERFACE);
		//获取生成代理类的全部完整路径
		System.out.println("类="+packageName+"."+className);
		}
		Object person = Thread.currentThread().getContextClassLoader().
				loadClass("com.haier.webservices.server.acg.DealerForAcg").newInstance();   
		Method m = person.getClass().getMethod("setMobile", String.class);  
		m.invoke(person, "110110");  
		
		    Object[] objects = null;
			try {
				objects = client.invoke("pushContractor",person);
				System.out.println(objects.toString());
				String str=	JSONObject.toJSONString(objects);
				System.out.println(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	
	
		
}
