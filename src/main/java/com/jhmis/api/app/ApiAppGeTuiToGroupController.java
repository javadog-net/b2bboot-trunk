package com.jhmis.api.app;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class ApiAppGeTuiToGroupController {

	
	private static String appId = "ImJryenMrn96d7GnKIXkG3";
	private static String appKey = "zf0754jcyk6qpimiAXYna6";
	private static String masterSecret = "awOeZ8RNNn53mCabYeD1x9";
	public static void geTuiToGroup(String CID,String groupId,String name,String content,String title) {
		System.out.println("Cid"+CID+"--"+"groupId"+groupId+"name"+name+"--"+"content"+content+"--"+"title"+title);
		//String CID = "b8217c2507aa68efee57d3e68ac63b75";
		// 别名推送方式
	    //String Alias = "总群";
		String host = "http://sdk.open.api.igexin.com/apiex.htm";

		IGtPush push = new IGtPush(host, appKey, masterSecret);
		LinkTemplate template = linkTemplateDemo(groupId,name,content,title);
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
		} else {
			System.out.println("服务器响应异常");
		}
	}

	public static LinkTemplate linkTemplateDemo(String groupId,String name,String content,String title) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(title);
		style.setText(content);
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		// 设置打开的网址地址
		template.setUrl("eqipp://pages/group_chat/group_chat?id="+groupId+"&name="+name);
		return template;
	}
	
	
	
	
}
