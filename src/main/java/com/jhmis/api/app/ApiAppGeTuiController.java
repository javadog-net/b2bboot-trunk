package com.jhmis.api.app;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;
public class ApiAppGeTuiController {

	// 采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	private static String appId = "ImJryenMrn96d7GnKIXkG3";
	private static String appKey = "zf0754jcyk6qpimiAXYna6";
	private static String masterSecret = "awOeZ8RNNn53mCabYeD1x9";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	public static void mains(String[] args) throws Exception {

	        IGtPush push = new IGtPush(host, appKey, masterSecret);

	        LinkTemplate template = linkTemplateDemo();
	        AppMessage message = new AppMessage();
	        message.setData(template);

	        message.setOffline(true);
	        //离线有效时间，单位为毫秒，可选
	        message.setOfflineExpireTime(24 * 1000 * 3600);
	        //推送给App的目标用户需要满足的条件
	        AppConditions cdt = new AppConditions();
	        List<String> appIdList = new ArrayList<String>();
	        appIdList.add(appId);
	        message.setAppIdList(appIdList);
	        //手机类型
	        List<String> phoneTypeList = new ArrayList<String>();
	        //省份
	        List<String> provinceList = new ArrayList<String>();
	        //自定义tag
	        List<String> tagList = new ArrayList<String>();

	        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
	        cdt.addCondition(AppConditions.REGION, provinceList);
	        cdt.addCondition(AppConditions.TAG,tagList);
	        message.setConditions(cdt);

	        IPushResult ret = push.pushMessageToApp(message,"任务别名_toApp");
	        System.out.println(ret.getResponse().toString());
	        
	    }

	public static LinkTemplate linkTemplateDemo() throws Exception {
	        LinkTemplate template = new LinkTemplate();
	        template.setAppId(appId);
	        template.setAppkey(appKey);

	        Style0 style = new Style0();
	        // 设置通知栏标题与内容
	        style.setTitle("感谢安装我们App");
	        style.setText("如有疑问请致电海尔");
	        // 配置通知栏图标
	        style.setLogo("https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIJd240KyOj2cfv6Tug2RZdQpXdYoHvGC73O8Uxpib2DYGFg2SpXuhJE33R2QPkOpQZUG3UXRsuJmA/132");
	        // 配置通知栏网络图标
	        style.setLogoUrl("https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIJd240KyOj2cfv6Tug2RZdQpXdYoHvGC73O8Uxpib2DYGFg2SpXuhJE33R2QPkOpQZUG3UXRsuJmA/132");
	        // 设置通知是否响铃，震动，或者可清除
	        style.setRing(true);
	        style.setVibrate(true);
	        style.setClearable(true);
	        template.setStyle(style);

	        template.setUrl("eqipp://pages/meeting/meeting_about?id=9c39dd1ee2fc4f6f86ab0701d171f6e5");

	        return template;

	}

}
