package com.jhmis.api.app;


import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import net.oschina.j2cache.redis.QueueEnum;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Tuple;

/*@RestController
@EnableScheduling
@RequestMapping("/api/app/getuiinfo")*/
public class ApiAppPushMsg {
	

	@RequestMapping(value = "/infogetuizadd")
	public void zadd(long time, String id,String title,String des ,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println("加入redis队列");
		String info=id+"@"+title+"@"+des;
		getJedis().zadd(QueueEnum.UNPAID_GETUIINFO_QUEUE.type(), time, info);
	}

	// 文章的推送
	private static String appId = "ImJryenMrn96d7GnKIXkG3";
	private static String appKey = "zf0754jcyk6qpimiAXYna6";
	private static String masterSecret = "awOeZ8RNNn53mCabYeD1x9";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	@Scheduled(fixedRate = 1000 * 60 * 5)
	public void geGuiInfo() {
		System.out.println("进入推送 接口");
		String id="";
		String title="";
		String content="";
		Set<Tuple> item = getJedis().zrangeWithScores(QueueEnum.UNPAID_GETUIINFO_QUEUE.type(), 0, 0);
		if (item != null && !item.isEmpty()) {
			Tuple tuple = item.iterator().next();
			System.out.println("当前时间的时间戳"+System.currentTimeMillis());
			System.out.println("规定的时间戳"+tuple.getScore());
			if (System.currentTimeMillis() >= tuple.getScore()) {
				System.out.println("当前时间戳大于规定时间戳，也就是开始执行推送业务");
				String cmsInfo = tuple.getElement();
				String idArray[] = cmsInfo.split("@");
				if(idArray.length==3){
				   id=idArray[0].toString();
				   title=idArray[1].toString();
				   content=idArray[2].toString();
				}else{
					return;
				}
				// 业务处理：推送
				/*
				IGtPush push = new IGtPush(host, appKey, masterSecret);

				LinkTemplate template = linkTemplateDemo(id, title, content);
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
				List<String> provinceList = new ArrayList<String>();
				// 自定义tag
				List<String> tagList = new ArrayList<String>();
				
				AppConditions cdt = new AppConditions();
				List<String> phoneTypeList = new ArrayList<String>();
                phoneTypeList.add("???");
				cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
				cdt.addCondition(AppConditions.REGION, provinceList);
				cdt.addCondition(AppConditions.TAG, tagList);
				message.setConditions(cdt);

				IPushResult ret = push.pushMessageToApp(message, "任务别名_toApp");
				System.out.println(ret.getResponse().toString() + "文章推送完成");*/
				//按照 cid分别个推处理
				String url="/pages/news_info/index?id=" + id;
			ApiAppGeTuiToFriendController p=new ApiAppGeTuiToFriendController();
			p.pushToMsgByCidToInfo(content, title, url);
				
				
				// 删除队列元素
				getJedis().zrem(QueueEnum.UNPAID_GETUIINFO_QUEUE.type(), tuple.getElement());

			}
		}

	}

	public static LinkTemplate linkTemplateDemo(String id, String title, String content) {
		LinkTemplate template = new LinkTemplate();
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
		String url="/pages/news_info/index?id=" + id;
       // template.setAPNInfo(getApnPayload());
		//template.setUrl("eqipp://pages/news_info/index?id=" + id);

		return template;

	}
	
	private static final String ADDR = "127.0.0.1";
	private static final int PORT = 6379;
	private static JedisPool jedisPool = new JedisPool(ADDR, PORT);
	/*public static Jedis getJedis() {
	        return jedisPool.getResource();
	    }*/
	 public static Jedis getJedis() {
	        JedisShardInfo shardInfo = new JedisShardInfo("redis://127.0.0.1:6379");//这里是连接的本地地址和端口
	        shardInfo.setPassword("123456");//这里是密码
	        Jedis jedis = new Jedis(shardInfo);
	        jedis.connect();
	        //Jedis j = jedisPool.getResource();
	        return jedis;
	    }
	
	 

	public static void main(String[] args) {
//		String id="0019f735-f0a5-44d1-a5ed-163d063a0f0f";
//		String title="海尔冰箱双开门新款颠覆传统";
//		String content="海尔冰箱双开门新款应和广大消费者的需求，进行产品的迭代升级，颠覆传统，推出符合时代要求的节能保鲜、风冷无霜冰箱。";
//		String url="/pages/news_info/index?id=" + id;
//		ApiAppGeTuiToFriendController p=new ApiAppGeTuiToFriendController();
//		p.pushToMsgByCidToInfo(content, title, url);
//	
		
		
		
		
	}
	 
	 
}
