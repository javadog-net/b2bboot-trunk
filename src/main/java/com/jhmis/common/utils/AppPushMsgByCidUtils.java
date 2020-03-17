package com.jhmis.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.DictionaryAlertMsg;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.dto.GtReq.NotifyInfo.Type;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class AppPushMsgByCidUtils implements Runnable{

	private String Cid;
	public void setCid(String cid) {
		Cid = cid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String title;
	private String content;
	private String type;
	private String url;
	
	
	private static String appId = "ImJryenMrn96d7GnKIXkG3";
	private static String appKey = "zf0754jcyk6qpimiAXYna6";
	private static String masterSecret = "awOeZ8RNNn53mCabYeD1x9";

	
	
	@Override
	public void run() {
		appPushMsgByCid(Cid,title,content,url,type);
	}
	
	private static void pushSingleMessage(IGtPush push, String Cid, ITemplate template, boolean offline) {

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

	private static APNPayload getApnPayload(String title, String messageInfo, String badge, String url) {
		APNPayload payload = new APNPayload();
		// +1在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		payload.setAutoBadge(badge);
		payload.setContentAvailable(1);
		payload.setSound("123.wav");
		payload.setCategory("$由客户端定义");

		// 简单模式APNPayload.SimpleMsg
		// payload.setAlertMsg(new APNPayload.SimpleAlertMsg(messageInfo));
		DictionaryAlertMsg dictionaryAlertMsg = new DictionaryAlertMsg();
		dictionaryAlertMsg.setTitle(title);
		dictionaryAlertMsg.setBody(messageInfo);
		payload.setAlertMsg(dictionaryAlertMsg);
		// payload.addCustomMsg("parkinfo","{\"parkid\":\"dd\"}");
		payload.addCustomMsg("url",url);
		return payload;
	}

	/**
	 * @Title: iosTransmissionTemplate
	 * @Description: TODO IOS离线，android离线时采用该模版
	 * @param title
	 * @param messageInfo
	 * @param badge
	 * @return
	 * @return TransmissionTemplate
	 * @author tc
	 * @date 2019年7月5日下午3:56:26
	 */
	private static TransmissionTemplate iosTransmissionTemplate(String title, String messageInfo, String badge,
			String url) {
		TransmissionTemplate template = transmissionTemplate(messageInfo, title);
		APNPayload payload = getApnPayload(title, messageInfo, badge, url);
		// 字典模式使用下者
		// payload.setAlertMsg(getDictionaryAlertMsg());
		template.setAPNInfo(payload);
		return template;
	}

	/**
	 * @Title: iosOnlineTransmissionTemplate
	 * @Description: TODO ios 在线推送通知模版
	 * @param messageInfo
	 * @param url
	 * @return
	 * @return TransmissionTemplate
	 * @author tc
	 * @date 2019年7月8日下午5:33:46
	 */
	private static TransmissionTemplate iosOnlineTransmissionTemplate(String content, String url) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(
				"{\"content\":\"" + content + "\",\"url\":\"" + url + "\",\"title\":\"" + content + "\"}");
		return template;

	}

	/**
	 * @Title: androidTransmissionTemplate
	 * @Description: TODO Android透传模版
	 * @param title
	 * @param messageInfo
	 * @param badge
	 * @param url
	 * @return
	 * @return TransmissionTemplate
	 * @author tc
	 * @date 2019年7月8日上午11:19:40
	 */
/*	private static TransmissionTemplate androidTransmissionTemplate(String title, String messageInfo, String badge,
			String url) {
		TransmissionTemplate template = transmissionTemplate(messageInfo, title);

		APNPayload payload = getApnPayload(title, messageInfo, badge, url);
		// 字典模式使用下者
		// payload.setAlertMsg(getDictionaryAlertMsg());
		return template;
	}*/

	/**
	 * @Title: transmissionTemplate
	 * @Description: TODO IOS离线，时采用该模版
	 * @param messageInfo
	 * @param title
	 * @return
	 * @return TransmissionTemplate
	 * @author tc
	 * @date 2019年7月5日下午3:34:46
	 */
	private static TransmissionTemplate transmissionTemplate( String title,String url) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent(url);
		template.setTransmissionType(2);
		
		return template;
	}

	/**
	 * @Title: transmissionTemplate
	 * @Description: TODO android 离线推送
	 * @param messageInfo
	 * @param title
	 * @return
	 * @return TransmissionTemplate
	 * @author tc
	 * @date 2019年7月8日下午6:39:55
	 */
	private static TransmissionTemplate androidOfflinetransmissionTemplate(String title, String messageInfo,
			String url) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		Notify notify = new Notify();
		notify.setContent(messageInfo );
		notify.setTitle(title );
		//String text="{title:'"+title+"',content:'"+messageInfo+"',payload:'通知去干嘛这里可以自定义'}";
	    template.setTransmissionContent("22");
		template.setTransmissionType(2);
		notify.setIntent(""
			+ "intent:#Intent;"
			+ "launchFlags=0x14000000;"
			+ "component=com.haiereqpp.bundle/io.dcloud.PandoraEntry;"
			+ "S.UP-OL-SU=true;S.title=测试标题;"
			+ "S.content=测试内容;"
			+ "S.payload="+url+";end");
		notify.setType(Type._intent);
		template.set3rdNotifyInfo(notify);
		return template;
	}


	

	/**
	 * @Title: notificationTemplateDemo
	 * @Description: TODO 安卓机 app在线（状态是Online）时候 采用该模版
	 * @param title
	 * @param messageInfo
	 * @param badge
	 * @return
	 * @return NotificationTemplate
	 * @author tc
	 * @date 2019年7月5日下午3:32:06
	 */
	private static NotificationTemplate notificationTemplateDemo(String title, String messageInfo, String url) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		//template.setChannelLevel(1);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(messageInfo);
		template.setTransmissionType(2);
		template.setTransmissionContent(url);
		// 配置通知栏图标
 		
		template.setLogo("icon.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		//template.setAPNInfo(getApnPayload(title, messageInfo, "1",url));
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
	public static void appPushMsgByCid(String Cid, String title, String content, String url, String type) {
		// String Cid="b7c57b5a8bee3cc56c656a328b5a3025";
		IGtPush push = new IGtPush(appKey, masterSecret, true);
		IQueryResult mess = push.getClientIdStatus(appId, Cid);
		// System.out.println(JSON.toJSONString(mess));
		String status = mess.getResponse().get("result").toString();
		System.out.println("这是用户的状态status" + status);
		//手机类型是空的时候 则都 ios 和Android都推送
		if(StringUtils.isBlank(type)){
			TransmissionTemplate iosTransmissionTemplate = null;
			iosTransmissionTemplate = iosTransmissionTemplate(title, content, "0", url);// 带APNPayload

			pushSingleMessage(push, Cid, iosTransmissionTemplate, true);// ios
							// 离线推送
			System.out.println("空类型iOS离线推送");
			TransmissionTemplate androidOfflinetransmissionTemplate = null;
			androidOfflinetransmissionTemplate = androidOfflinetransmissionTemplate(title, content, url);// 带APNPayload

			pushSingleMessage(push, Cid, androidOfflinetransmissionTemplate, true);// android离线推送
			System.out.println("空类型Android离线推送");
		}
		
		// ios 推送
		if (StringUtils.isNotBlank(type) && type.equals("iOS")) {

			if (StringUtils.isNotBlank(status) && status.equals("Offline")) {
				TransmissionTemplate iosTransmissionTemplate = null;
				iosTransmissionTemplate = iosTransmissionTemplate(title, content, "0", url);// 带APNPayload

				pushSingleMessage(push, Cid, iosTransmissionTemplate, true);// ios
								// 离线推送
				System.out.println("iOS离线推送");
			}
			if (StringUtils.isNotBlank(status) && status.equals("Online")) {
				TransmissionTemplate iosOnlineTransmissionTemplate = null;
				iosOnlineTransmissionTemplate = iosOnlineTransmissionTemplate(content, url);

				pushSingleMessage(push, Cid, iosOnlineTransmissionTemplate, true);// ios
																					// 在线推送
				System.out.println("iOS在线推送");
			}
		}
		// android 推送
		if (StringUtils.isNotBlank(type) && type.equals("Android")) {
			if (StringUtils.isNotBlank(status) && status.equals("Online")) {
				NotificationTemplate notificationTemplate = null;
				notificationTemplate = notificationTemplateDemo(title, content, url);
				pushSingleMessage(push, Cid, notificationTemplate, true);// 安卓在线推送
				System.out.println("Android在线推送");
			}
			if (StringUtils.isNotBlank(status) && status.equals("Offline")) {
				/*TransmissionTemplate transmissionTemplate = null;
				transmissionTemplate = transmissionTemplate(title, url);

				pushSingleMessage(push, Cid, transmissionTemplate, true);// Android个推透传推送 静默推送
				System.out.println("Android个推透传推送");*/
				TransmissionTemplate androidOfflinetransmissionTemplate = null;
				androidOfflinetransmissionTemplate = androidOfflinetransmissionTemplate(title, content, url);// 带APNPayload

				pushSingleMessage(push, Cid, androidOfflinetransmissionTemplate, true);// android离线推送
				System.out.println("Android离线推送");
				
			}
		}

	}

	public static void main(String[] args) {

		// ce878ac894a6edeeb8408f01bbdbb9b8 
		appPushMsgByCid("58a591a66df47efc8d49ebe78bc21a17", "title28", "content2",
				"/pages/central/air-conditioning/index", "Android");
		//pushMsgToApp();
	}

	public static void pushMsgToApp(){
		 String hosthost = "http://sdk.open.api.igexin.com/apiex.htm";
		IGtPush push = new IGtPush(hosthost, appKey, masterSecret);

		//LinkTemplate template = linkTemplateDemo(id, title, content);
		String url ="/pages/news_info/index?id=0019f735-f0a5-44d1-a5ed-163d063a0f0f";
		TransmissionTemplate template=transmissionTemplate("海尔冰箱双开门新款颠覆传统",url);
		AppMessage message = new AppMessage();
		message.setData(template);

		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 推送给App的目标用户需要满足的条件
		
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);
		// 手机类型
		// 省份
		//List<String> provinceList = new ArrayList<String>();
		// 自定义tag
		//List<String> tagList = new ArrayList<String>();
		
		AppConditions cdt = new AppConditions();
		List<String> phoneTypeList = new ArrayList<String>();
        phoneTypeList.add("IOS");//IOS  或者 ANDROID
        phoneTypeList.add("ANDROID");
		cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
//		cdt.addCondition(AppConditions.REGION, provinceList);
//		cdt.addCondition(AppConditions.TAG, tagList);
		message.setConditions(cdt);

		IPushResult ret = push.pushMessageToApp(message, "任务别名_toApp");
		System.out.println(ret.getResponse().toString() + "文章推送完成");
		
	}
	
	
}
