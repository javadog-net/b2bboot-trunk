/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shophicback;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.utils.Constants;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.mapper.shopmsg.ShopMsgMapper;
import com.jhmis.modules.process.mapper.shopmsgorder.ShopMsgCustcodeOrderMapper;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.shophicback.ShopHicbackpassLog;
import com.jhmis.modules.process.mapper.shophicback.ShopHicbackpassLogMapper;

/**
 * 需求400需求回传日志表Service
 * @author hdx
 * @version 2019-11-26
 */
@Service
@Transactional(readOnly = true)
public class ShopHicbackpassLogService extends CrudService<ShopHicbackpassLogMapper, ShopHicbackpassLog> {

	@Autowired
	ShopMsgMapper shopMsgMapper;

	@Autowired
	ShopMsgCustcodeOrderMapper shopMsgCustcodeOrderMapper;

	@Autowired
	ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;


	public ShopHicbackpassLog get(String id) {
		return super.get(id);
	}

	public List<ShopHicbackpassLog> findList(ShopHicbackpassLog shopHicbackpassLog) {
		return super.findList(shopHicbackpassLog);
	}

	public Page<ShopHicbackpassLog> findPage(Page<ShopHicbackpassLog> page, ShopHicbackpassLog shopHicbackpassLog) {
		return super.findPage(page, shopHicbackpassLog);
	}

	@Transactional(readOnly = false)
	public void save(ShopHicbackpassLog shopHicbackpassLog) {
		super.save(shopHicbackpassLog);
	}

	@Transactional(readOnly = false)
	public void delete(ShopHicbackpassLog shopHicbackpassLog) {
		super.delete(shopHicbackpassLog);
	}

	@Transactional(readOnly = false)
	public void hicBack(ShopHicbackpassLog shopHicbackpassLog) throws ShopMsgException{
		String msgId = shopHicbackpassLog.getBackid();
		ShopMsg shopMsg= shopMsgMapper.get(msgId);
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
		shopMsgCustcodeOrder.setMsgId(msgId);
		//shopMsgCustcodeOrder.setCancelFlag("1");
		List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderMapper.findList(shopMsgCustcodeOrder);
		if(listShopMsgCustcodeOrder!=null && listShopMsgCustcodeOrder.size()>0){
			ShopMsgCustcodeOrder smco = listShopMsgCustcodeOrder.get(0);
			shopHicbackpassLog.setBindtime(smco.getBindTime());
			shopHicbackpassLog.setBindmoney(smco.getTotalCount());
			//证明已经回传400
			smco.setIsBackpass("1");
			//更新订单表，证明已回传
			shopMsgCustcodeOrderMapper.update(smco);
		}
		//回调hic系统，完全复制当时接口
		com.alibaba.fastjson.JSONObject reqJson = new JSONObject();
		reqJson.put("backId",shopHicbackpassLog.getBackid());
		if("中标".equals(shopHicbackpassLog.getState())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			reqJson.put("zbTime", sdf.format(shopHicbackpassLog.getZbtime()));
			reqJson.put("cjMoney",shopHicbackpassLog.getCjmoney());
		}else {
			reqJson.put("zbTime",null);
			reqJson.put("cjMoney",null);
		}
		reqJson.put("state",shopHicbackpassLog.getState());
		String content =reqJson.toString();
		System.out.println("[调用报文]"+reqJson.toString());
		String transFlag =  sendData(Constants.HIC_URL,content);
		boolean a = (boolean) JSONArray.parseObject(transFlag).get("success");
		if(transFlag ==null || "".equals(transFlag) || a ==false){
			logger.error("传输HIC系统失败,请联系管理员");
			shopHicbackpassLog.setMemo(transFlag);
			this.save(shopHicbackpassLog);
			throw new ShopMsgException("接口请求异常");
		}else{
			this.save(shopHicbackpassLog);
		}
	}


	//后台调用ajax操作静态方法
	public static String sendData(String url , String content){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		String returnMessage = null;
		try {
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			StringEntity sendEntity = new StringEntity(new String(content), "UTF-8");
			httpPost.setEntity(sendEntity);
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity returnEntity = response.getEntity();
			returnMessage = EntityUtils.toString(returnEntity);
		} catch (Exception e) {
			System.out.println("接口调用异常："+e);
		}
		return returnMessage;
	}

}