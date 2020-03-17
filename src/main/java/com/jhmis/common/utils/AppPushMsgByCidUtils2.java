package com.jhmis.common.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.DictionaryAlertMsg;
import com.gexin.rp.sdk.dto.GtReq.NotifyInfo.Type;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.jhmis.common.wsClient.test.main;

public class AppPushMsgByCidUtils2 {


	private static String appId = "ImJryenMrn96d7GnKIXkG3";
	private static String appKey = "zf0754jcyk6qpimiAXYna6";
	private static String masterSecret = "awOeZ8RNNn53mCabYeD1x9";
	private static void pushSingleMessage(IGtPush push,String Cid, ITemplate template, boolean offline) {
		
		SingleMessage message = new SingleMessage();
		message.setOffline(offline); // 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(Cid);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
			System.out.println(ret.getResponse().toString());
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
	}

	private static APNPayload getApnPayload(String title, String messageInfo, String badge,String url) {
		APNPayload payload = new APNPayload();
		// +1在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge(badge);
		payload.setContentAvailable(0);
		payload.setSound("123.wav");
		payload.setCategory("$由客户端定义");
		payload.addCustomMsg("url", url);
		 //简单模式APNPayload.SimpleMsg
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg(messageInfo+"这是payload里面的content"));
//		DictionaryAlertMsg dictionaryAlertMsg = new DictionaryAlertMsg();
//		dictionaryAlertMsg.setTitle(title);
//		dictionaryAlertMsg.setBody(messageInfo);
//		payload.setAlertMsg(dictionaryAlertMsg);
//		payload.addCustomMsg("parkinfo","{\"parkid\":\"停车场id\",\"platenum\":\"车位号\",\"statue\":\"状态\"}");
		return payload;
	}
	
	/** 
	  * @Title: iosTransmissionTemplate 
	  * @Description: TODO  IOS离线，android离线时采用该模版
	  * @param title
	  * @param messageInfo
	  * @param badge
	  * @return 
	  * @return TransmissionTemplate
	  * @author tc
	  * @date 2019年7月5日下午3:56:26
	  */
	private static TransmissionTemplate iosTransmissionTemplate(String title, String messageInfo, String badge,String url) {
		TransmissionTemplate template = transmissionTemplate(messageInfo,title);
		APNPayload payload = getApnPayload(title, messageInfo, badge,url);
		// 字典模式使用下者
		// payload.setAlertMsg(getDictionaryAlertMsg());
		template.setAPNInfo(payload);
			return template;
	}
	/** 
	  * @Title: iosOnlineTransmissionTemplate 
	  * @Description: TODO  ios 在线推送通知模版
	  * @param messageInfo
	  * @param url
	  * @return 
	  * @return TransmissionTemplate
	  * @author tc
	  * @date 2019年7月8日下午5:33:46
	  */
	private static TransmissionTemplate iosOnlineTransmissionTemplate(String messageInfo,String url) {
	TransmissionTemplate template = new TransmissionTemplate();
	template.setAppId(appId);
    template.setAppkey(appKey);
    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
    template.setTransmissionType(2);
    template.setTransmissionContent(messageInfo);
    return template;

	}	
	
	/** 
	  * @Title: androidTransmissionTemplate 
	  * @Description: TODO  Android透传模版
	  * @param title
	  * @param messageInfo
	  * @param badge
	  * @param url
	  * @return 
	  * @return TransmissionTemplate
	  * @author tc
	  * @date 2019年7月8日上午11:19:40
	  */
	private static TransmissionTemplate androidTransmissionTemplate(String title, String messageInfo, String badge,String url) {
		TransmissionTemplate template = transmissionTemplate(messageInfo,title);
		
		APNPayload payload = getApnPayload(title, messageInfo, badge,url);
		// 字典模式使用下者
		// payload.setAlertMsg(getDictionaryAlertMsg());
		return template;
	}
	/** 
	  * @Title: transmissionTemplate 
	  * @Description: TODO  IOS离线，android离线时采用该模版
	  * @param messageInfo
	  * @param title
	  * @return 
	  * @return TransmissionTemplate
	  * @author tc
	  * @date 2019年7月5日下午3:34:46
	  */
	private static TransmissionTemplate transmissionTemplate(String messageInfo,String title) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		Notify notify=new Notify();
		 
		notify.setContent(messageInfo+"notifycontent");
		notify.setTitle(title+"notifytitle");
		notify.setIntent("intent:#Intent;launchFlags=0x04000000;action=android.intent.action.oppopush;package=com.haiereqpp.bundle;component=com.haiereqpp.bundle/io.dcloud.PandoraEntry; S.UP-OL-SU=true;S.title=测试标题;S.content=测试内容;S.payload=test;end");
		//notify.setIntent("intent:#Intent;action=android.intent.action.oppopush;launchFlags=0x14000000;component=io.dcloud.HBuilder/io.dcloud.PandoraEntry;S.UP-OL-SU=true;S.title=测试标题;S.content=测试内容;S.payload=test;end");
		notify.setType(Type._intent);
		template.set3rdNotifyInfo(notify);
		
		template.setTransmissionContent(messageInfo+"url");
		template.setTransmissionType(2);
		
		return template;
	}

	/** 
	  * @Title: notificationTemplateDemo 
	  * @Description: TODO   安卓机 app在线（状态是Online）时候 采用该模版
	  * @param title
	  * @param messageInfo
	  * @param badge
	  * @return 
	  * @return NotificationTemplate
	  * @author tc
	  * @date 2019年7月5日下午3:32:06
	  */
	private static NotificationTemplate notificationTemplateDemo(String title, String messageInfo, String badge,String url) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(messageInfo);
		// 配置通知栏图标
		template.setLogo("icon.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		//template.setAPNInfo(getApnPayload(title, messageInfo, badge,url));
		
		return template;
	}
	

	
	/** 
	  * @Title: appPushMsgByCid 
	  * @Description: TODO 通过CID 来推送消息的、 
	  * @param Cid
	  * @param title
	  * @param content
	  * @param url
	  * @param type 
	  * @return void
	  * @author tc
	  * @date 2019年7月8日上午9:23:56
	  */
	public static void appPushMsgByCid(String Cid,String title,String content,String url,String type) {
		//String Cid="b7c57b5a8bee3cc56c656a328b5a3025";
		IGtPush push = new IGtPush(appKey, masterSecret, true);
		IQueryResult mess=push.getClientIdStatus(appId, Cid);
		//System.out.println(JSON.toJSONString(mess));
		String status=mess.getResponse().get("result").toString();
		System.out.println("这是用户的状态status"+status);
		//ios 推送
		if(StringUtils.isNotBlank(type)&&type.equals("iOS")){
		
			if(StringUtils.isNotBlank(status)&&status.equals("Offline")){
				TransmissionTemplate iosTransmissionTemplate = null;
				iosTransmissionTemplate = iosTransmissionTemplate(title,content, "+1",url);// 带APNPayload
				
				pushSingleMessage(push,Cid,iosTransmissionTemplate,true);//ios 离线推送 
				System.out.println("iOS离线推送");
			}
			if(StringUtils.isNotBlank(status)&&status.equals("Online")){
				TransmissionTemplate iosOnlineTransmissionTemplate = null;
				iosOnlineTransmissionTemplate =iosOnlineTransmissionTemplate(content,url);
				
				pushSingleMessage(push,Cid,iosOnlineTransmissionTemplate,true);//ios 在线推送 
				System.out.println("iOS在线推送");
			}
		}
		//android 推送
		if(StringUtils.isNotBlank(type)&&type.equals("Android")){
			if(StringUtils.isNotBlank(status)&&status.equals("Online")){
				NotificationTemplate notificationTemplate=null;
				notificationTemplate=notificationTemplateDemo(title,content, "+1",url);
				pushSingleMessage(push,Cid,notificationTemplate,true);//安卓在线推送
			System.out.println("Android在线推送");
			}
			if(StringUtils.isNotBlank(status)&&status.equals("Offline")){
				TransmissionTemplate androidTransmissionTemplate = null;
				androidTransmissionTemplate = androidTransmissionTemplate(title,content, "+1",url);// 带APNPayload
				
				pushSingleMessage(push,Cid,androidTransmissionTemplate,true);//android离线推送 
				System.out.println("Android离线推送");
			}
		}
		
	}
	
	public static void main(String[] args) {
		
		//   c2d543472486c7dce8ed0897cd0c3c72
		appPushMsgByCid("ce878ac894a6edeeb8408f01bbdbb9b8","title28","content2","url","iOS");
	}
	
	
}
