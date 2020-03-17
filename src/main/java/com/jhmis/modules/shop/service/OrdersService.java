/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.http.HttpUtil;
import com.jhmis.core.mapper.JsonMapper;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.mapper.*;
import com.jhmis.modules.shop.mapper.dealer.DealerMapper;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.kjtpay.gateway.common.util.security.SecurityService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class OrdersService extends CrudService<OrdersMapper, Orders> {
	@Autowired
	private OrderPayMapper orderPayMapper;
	@Autowired
	private  OrdersMapper ordersMapper;
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private PurchaserAccountMapper purchaserAccountMapper;
	@Autowired
	private OrderGoodsMapper orderGoodsMapper;
	@Autowired
	private DealerMapper dealerMapper;
	@Autowired
	private PurchaserAddressService purchaserAddressService;
	@Autowired
	private PurchaserInvoiceService purchaserInvoiceService;
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private StoreGoodsMapper storeGoodsMapper;
	@Resource
	private SecurityService securityServiceRsa;


	public Orders get(String id) {
		return super.get(id);
	}
	
	public List<Orders> findList(Orders orders) {
		return super.findList(orders);
	}
	public Page<Orders> findPageList(Page<Orders> page, Orders orders) {
		return super.findPage(page, orders);
	}

	public Page<Orders> findPage(Page<Orders> page, Orders orders) {
			dataRuleFilter(orders);
			orders.setPage(page);
		    List<Orders> ordersList = mapper.findList(orders);
		    List<Orders> ordersListResult = new ArrayList<>();
			OrderGoods goods = new OrderGoods();
		    for(Orders order:ordersList){
				goods.setOrderId(order.getId());
				order.setOrdersGoodsList(orderGoodsMapper.findList(goods));
				ordersListResult.add(order);
			}
			page.setList(ordersListResult);
			return page;
	}

	/**
	 * 立即购买
	 * @param purchaserAccountId
	 *
	 * @param num
	 * @param isApplyInvoice
	 */
	@Transactional(readOnly = false)
	public List<String> save(String purchaserAccountId, Integer num, List<StoreGoods> storeGoodsList, PurchaserAddress purchaserAddress, PurchaserInvoice purchaserInvoice , String isApplyInvoice) {
		// 保存到订单支付表，产生paysn
		OrderPay orderPay = new OrderPay();
		orderPay.setPaySn(IdGen.makePaySn());
		orderPay.setId(IdGen.uuid());
		orderPay.setPurchaserAccountId(purchaserAccountId);
		orderPay.setIsPayState(Global.YES);
		orderPayMapper.insert(orderPay);
		//查询下单人所在采购商公司信息
		PurchaserAccount purchaserAccount = purchaserAccountMapper.get(purchaserAccountId);
		// 保存到订单商品表
		Store store = null;
		List<String> ordersList = new ArrayList<>();
		Dealer dealer = null;
		Orders orders = null;
		for(StoreGoods goods:storeGoodsList){
			orders = new Orders();
			orders.setId(IdGen.uuid());
			orders.setDealerId(goods.getDealerId());
			orders.setDealerName(goods.getDealerName());
			orders.setKjtAccount(goods.getKjtAccount());
			if(purchaserAddress != null){
				orders.setPurchaseraddressInfo(purchaserAddress);
			}
			if(purchaserInvoice != null){
				orders.setPurchaserInvoice(purchaserInvoice);
			}

			orders.setAddressInfo(JsonMapper.toJsonString(purchaserAddress));
			orders.setInvoiceInfo(JsonMapper.toJsonString(purchaserInvoice));
			orders.setIsApplyInvoice(isApplyInvoice);
			orders.setPurchaserName(purchaserAccount.getPurchaserName());
			orders.setPurchaserLoginName(purchaserAccount.getLoginName());
			orders.setPurchaserId(purchaserAccount.getPurchaserId());
			orders.setPurchaserAccountId(purchaserAccount.getId());
			orders.setPurchaserPhone(purchaserAccount.getMobile());
			OrderGoods orderGoods = new OrderGoods();
			if(goods.getPromotionPrice() != 0){
				orderGoods.setPayPrice(goods.getPromotionPrice());
			}else{
				orderGoods.setPayPrice(goods.getPrice());
			}

			// 保存到订单主表 ，根据店铺，每个供应商保存一条订单信息
			orders.setGoodsAmount(orderGoods.getPayPrice()*num);
			orders.setOrderAmount(orderGoods.getPayPrice()*num);
			orders.setPaySn(orderPay.getPaySn());
			orders.setId(IdGen.uuid());
			orders.setOrderSn(IdGen.makeBillCode());
			orders.setOrderState(Global.ORDER_STATE_NEW);
			orders.setCreateDate(new Date());
			orders.setStoreName(goods.getStoreName());
			orders.setStoreId(goods.getStoreId());
			ordersMapper.insert(orders);
			ordersList.add(orders.getId());

			orderGoods.setOrderId(orders.getId());
			orderGoods.setStoreId(orders.getStoreId());
			orderGoods.setStoreName(orders.getStoreName());
			orderGoods.setGoodsCode(goods.getCode());
			orderGoods.setGoodsName(goods.getGoodsName());
			orderGoods.setPrice(goods.getPrice());
			orderGoods.setGoodsType("1");
			orderGoods.setCategoryId(goods.getCategoryId());
			orderGoods.setMainPicUrl(goods.getMainPicUrl());
			orderGoods.setCreateDate(new Date());
			orderGoods.setStoreGoodsId(goods.getId());
			orderGoods.setPurchaserId(purchaserAccount.getPurchaserId());
			orderGoods.setId(IdGen.uuid());
			orderGoods.setCreateDate(new Date());
			orderGoods.setCreateDate(new Date());
			orderGoods.setNum(num);
			orderGoodsMapper.insert(orderGoods);
			//修改库存
			goods.setStock(goods.getStock()-num);
			storeGoodsMapper.updateStock(goods);
		}
		return ordersList;
	}
	@Transactional(readOnly = false)
	public List<String> saveFromCart(List<String> cartIdList,String purchaserAccountId,PurchaserAddress purchaserAddress,PurchaserInvoice purchaserInvoice,String isApplyInvoice) {
		// 保存到订单支付表，产生paysn
		OrderPay orderPay = new OrderPay();
		orderPay.setPaySn(IdGen.makePaySn());
		orderPay.setIsPayState(Global.YES);
		orderPay.setId(IdGen.uuid());
		orderPay.setPurchaserAccountId(purchaserAccountId);
		orderPayMapper.insert(orderPay);
		//查询下单人所在采购商公司信息
		PurchaserAccount purchaserAccount = purchaserAccountMapper.get(purchaserAccountId);
		// 保存到订单商品表
		Dealer dealer = null;
		Orders orders = null;
		OrderGoods orderGoods = null;
		Cart  searchCart = new Cart();
		searchCart.setIdList(cartIdList);
		List<Store> storeList = storeMapper.findDistinctStore(searchCart);
		List<String> ordersList = new ArrayList<>();
		if(storeList.size()==0){
			return null;
		}
		for(Store store:storeList){
			List<OrderGoods> orderGoodsList = new ArrayList<>();
			orders = new Orders();
			orders.setAddressInfo(JsonMapper.toJsonString(purchaserAddress));
			orders.setInvoiceInfo(JsonMapper.toJsonString(purchaserInvoice));
			orders.setPurchaserName(purchaserAccount.getPurchaserName());
			orders.setPurchaserLoginName(purchaserAccount.getLoginName());
			orders.setPurchaserId(purchaserAccount.getPurchaserId());
			orders.setPurchaserPhone(purchaserAccount.getMobile());
			orders.setPurchaserAccountId(purchaserAccount.getId());
			orders.setStoreId(store.getId());
			orders.setIsApplyInvoice(isApplyInvoice);

			if(store != null && StringUtils.isNotEmpty(store.getDealerId())){
				dealer = dealerMapper.get(store.getDealerId());
				if(dealer != null){
					orders.setDealerName(dealer.getCompanyName());
					orders.setKjtAccount(dealer.getKjtAccount());
					orders.setDealerId(dealer.getId());
				}
				orders.setStoreName(store.getStoreName());
			}
			// 保存到订单主表 ，根据店铺，每个供应商保存一条订单信息
			orders.setPaySn(orderPay.getPaySn());
			orders.setId(IdGen.uuid());
			orders.setOrderSn(IdGen.makeBillCode());
			orders.setOrderState(Global.ORDER_STATE_NEW);
			orders.setCreateDate(new Date());
			StoreGoods storeGoods = new StoreGoods();
			storeGoods.setStoreId(orders.getStoreId());
			storeGoods.setIdList(cartIdList);
			List<StoreGoods> storeGoodsList = storeGoodsMapper.findCartStoreGoods(storeGoods);
			double orderAmount = 0;
			for(StoreGoods goods:storeGoodsList){
				orderGoods = new OrderGoods();
				orderGoods.setOrderId(orders.getId());
				orderGoods.setStoreId(orders.getStoreId());
				orderGoods.setStoreName(orders.getStoreName());
				orderGoods.setGoodsCode(goods.getCode());
				orderGoods.setGoodsName(goods.getGoodsName());
				orderGoods.setPrice(goods.getPrice());
				orderGoods.setGoodsType("1");
				orderGoods.setCategoryId(goods.getCategoryId());
				orderGoods.setMainPicUrl(goods.getMainPicUrl());
				orderGoods.setCreateDate(new Date());
				orderGoods.setStoreGoodsId(goods.getId());
				if(goods.getPromotionPrice() != 0){
					orderGoods.setPayPrice(goods.getPromotionPrice());
				}else{
					orderGoods.setPayPrice(goods.getPrice());
				}
				orderGoods.setNum(goods.getChooseNum());
				orderAmount += orderGoods.getPayPrice()*orderGoods.getNum();
				orderGoods.setPurchaserId(purchaserAccount.getPurchaserId());
				orderGoods.setId(IdGen.uuid());
				orderGoods.setCreateDate(new Date());
				orderGoodsList.add(orderGoods);
			}
			orders.setOrdersGoodsList(orderGoodsList);
			orders.setOrderAmount(orderAmount);
			orders.setGoodsAmount(orderAmount);
			saveFromCart(orders,storeGoodsList);
			ordersList.add(orders.getId());
		}
		//删除购物车中的信息
		for(String cs:cartIdList){
			Cart ca = new Cart();
			ca.setId(cs);
			cartMapper.delete(ca);
		}
		return ordersList;
	}
	@Transactional(readOnly = false)
	public void saveFromCart(Orders orders,List<StoreGoods> storeGoodsList) {
		ordersMapper.insert(orders);
		for(OrderGoods orderGoods:orders.getOrdersGoodsList()){
			orderGoodsMapper.insert(orderGoods);
		}
		//修改库存
		for(StoreGoods storeGoods:storeGoodsList){
			storeGoods.setStock(storeGoods.getStock()-storeGoods.getChooseNum());
			storeGoodsMapper.updateStock(storeGoods);

		}

	}
	@Transactional(readOnly = false)
	public void deleteByOrderId(List<String> idList) {
		Orders orders = null;
		for(String id:idList){
			orders = ordersMapper.findUniqueByProperty("id",id);
			if(orders != null){
				ordersMapper.deleteByLogic(orders);
				orderGoodsMapper.deleteByLogicOrderId(orders.getId());
			}
		}
	}
	@Transactional(readOnly = false)
	public void updateOrdersState(Orders orders){
	    if(Global.ORDER_STATE_PAY_FINISHED ==orders.getOrderState()){
	        if(orders != null && StringUtils.isNotEmpty(orders.getPaySn())){
	            OrderPay orderPay = orderPayMapper.findUniqueByProperty("pay_sn",orders.getPaySn());
	            if(orderPay != null){
	                orderPay.setApiPayState(Global.KJT_API_PAY_STATE_SUCCESS);
	                orderPayMapper.update(orderPay);
                }
            }
        }
		mapper.updateOrdersState(orders);
	}
    @Transactional(readOnly = false)
    public void cancleOrder(Orders orders){
	    if(orders != null){
	        OrderGoods orderGoods = new OrderGoods();
	        orderGoods.setOrderId(orders.getId());
	        List<OrderGoods> orderGoodsList = orderGoodsMapper.findList(orderGoods);
	        if(orderGoodsList.size() > 0){
	            for(OrderGoods orderGood:orderGoodsList){
	                StoreGoods storeGoods = storeGoodsMapper.get(orderGood.getStoreGoodsId());
	                storeGoods.setStock(storeGoods.getStock()+ orderGood.getNum());
	                storeGoodsMapper.updateStock(storeGoods);
                }
            }
            if(StringUtils.isNotEmpty(orders.getPaySn())){
	        	OrderPay orderPay = orderPayMapper.findUniqueByProperty("pay_sn",orders.getPaySn());
	        	if(orderPay != null){
	        		orderPay.setIsPayState(Global.NO);
	        		orderPayMapper.update(orderPay);
				}
			}
        }

        mapper.updateOrdersState(orders);
    }
	public List<Orders> selectUnPaid(Orders orders){
		return mapper.selectUnPaid(orders);
	}

    /**
     * 确认收货
     * @param orders
     */
	@Transactional(readOnly = false)
	public void confirmReceive(Orders orders){
        ordersMapper.updateOrdersState(orders);
    }

	public int findByOrderState(Orders orders){
		return ordersMapper.findByOrderState(orders);
	}


	public String tradeSettle(Orders orders){
		List<Map<String,String>> list = new ArrayList<>();
		Map<String, String> payMap = getCommonParam(null,null,orders,null,Global.TRADE_SETTLE);
		Map<String, String> sPara = buildRequestPara(payMap);
		Map<String,Object> paramMap = new HashMap<>();
		List<String> keys = new ArrayList<String>(sPara.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = "";
			try {
				value = URLEncoder.encode((String) sPara.get(name), "UTF-8");
				paramMap.put(name,value);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String responseStr = HttpUtil.doPost(Global.KJT_GATEWAY_NEW ,paramMap);

		return responseStr;
	}
	/**
	 * 业务参数
	 * @param orderPay
	 * @param request
	 * @return
	 */
	public Map<String,Object> getBusinessMap(OrderPay orderPay,List<Orders> ordersList,HttpServletRequest request,String basePath){
		Map<String,Object> map = new HashMap<>();
		map.put("biz_product_code","20702");
		map.put("trade_info", JsonMapper.getInstance().toJson(getTradeInfoList(ordersList,basePath)));

//        map.put("trade_info", JsonMapper.getInstance().toJson(getTradeInfo(ordersList.get(0),basePath)));
		map.put("timeout_express","");
		map.put("payer_ip","122.224.203.210");
		map.put("return_url",orderPay.getReturnUrl());
		map.put("pay_method","");
		map.put("inexpectant_pay_product_code","");
		map.put("payer_identity_type","1");
		Map<String, String> terminalMap = new HashMap<>();
		terminalMap.put("terminal_type", "01");
//        terminalMap.put("ip", PurchaserUtils.getIpAddr(request));
		terminalMap.put("ip", "122.224.203.210");
		map.put("terminal_info", JsonMapper.getInstance().toJson(terminalMap));// 终端信息域
		map.put("payer_identity","anonymous");
		map.put("cashier_type","WEB");
		map.put("payer_platform_type","1");
		return map;
	}
	/**
	 * 生成要请求给支付宝的参数数组
	 *
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	private Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {

		// 生成签名结果
		String mysign = buildRequestMysign(sParaTemp);

		// 签名结果与签名方式加入请求提交参数组中
		sParaTemp.put("sign", mysign);
		return sParaTemp;
	}

	/**
	 * 生成签名结果
	 *
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public String buildRequestMysign(Map<String, String> sPara) {
		String charset = sPara.get("charset");
		String signType = sPara.get("sign_type");
		String mysign = "";
		if (StringUtils.isNoneBlank(charset) && StringUtils.isNoneBlank(signType)) {
			String service = sPara.get("service");
			if(Global.ENSURE_TRADE.equals(service) || Global.TRADE_SETTLE.equals(service)){
				mysign = securityServiceRsa.sign(sPara, charset);
				System.out.println("sign info： " + mysign);
			}
		}
		return mysign;
	}
	private String encrypt(String oriText, String charset) {
		System.out.println("转换前的json  信息是：" + oriText);
		// 加密前信息转换
		// 因转出的嵌套json有\，使用gson转成请求类会报错，故需要转换一下
		String bizReq = convertParm(oriText).toJSONString();
		System.out.println("转换后的json:"+bizReq);
		return securityServiceRsa.encrypt(bizReq, charset);

	}
	public Map<String,String> getCommonParam(OrderPay orderPay,List<Orders> ordersList,Orders orders,String basePath,String serviceName){
		Map<String,String> map = new HashMap<>();
		//加密
		String req = "";
		 if(Global.TRADE_SETTLE.equals(serviceName)){
			map.put("version","1.0");
			req = JsonMapper.getInstance().toJson(getTradeSettleBusinessMap(orders));
		}
		map.put("service",serviceName);


		logger.info("req---------------------"+req);
		String encrypt = encrypt(req, "UTF-8");
		logger.info("加密后的信息----------"+encrypt);
		map.put("biz_content", encrypt);
		map.put("request_no", IdGen.makeBillCode()+(new Random().nextInt(900)+100));
		map.put("partner_id",Global.KJT_PARTNER);
		map.put("charset","UTF-8");
		map.put("sign_type","RSA");
		map.put("timestamp", DateUtils.getDateTime());//
		logger.info("加密后的信息即 biz_content 中的内容是：" + encrypt(req, "UTF-8"));
		map.put("format", "JSON");
		return map;

	}
	/**
	 * 转换参数
	 *
	 * @param
	 * @param reqParm
	 * @return
	 */
	public JSONObject convertParm(String reqParm) {
		JSONObject tradeReq = JSONObject.parseObject(reqParm);
		// 设置复杂属性
		// 转换支付方式pay_method
		String pay_method = tradeReq.getString("pay_method");
		JSONObject payMethod = JSONObject.parseObject(pay_method);
		tradeReq.put("pay_method", payMethod);

		// 转换终端信息域terminal_info
		String terminal_info = tradeReq.getString("terminal_info");
		JSONObject terminalInfo = JSONObject.parseObject(terminal_info);
		tradeReq.put("terminal_info", terminalInfo);

		// 转换交易信息trade_info
		String trade_info = tradeReq.getString("trade_info");

		if (StringUtils.isNoneBlank(trade_info)) {
			JSONArray tradeArray = JSONArray.parseArray(trade_info);
//            JSONObject tradeInfo = JSONObject.parseObject(trade_info);
			//转换交易扩展参数trade_ext
//            String trade_ext = tradeInfo.getString("trade_ext");
//            if(StringUtils.isNoneBlank(trade_ext)){
//                JSONObject tradeExt = JSONObject.parseObject(trade_ext);
//                tradeInfo.put("trade_ext", tradeExt);
//            }
			tradeReq.put("trade_info", JSONArray.toJSON(tradeArray));
//            tradeReq.put("trade_info", JSONArray.toJSON(tradeInfo));

			return tradeReq;
		}

		// 转换分账列表royalty_info
		String royalty_info = tradeReq.getString("royalty_info");
		if (StringUtils.isNoneBlank(royalty_info)) {
			JSONArray royaltyInfos = JSONArray.parseArray(royalty_info);
			tradeReq.put("royalty_info", JSONArray.toJSON(royaltyInfos));
			return tradeReq;
		}

		return null;
	}
	public JSONObject convertParm(String service, String reqParm){

		if("ensure_trade".equals(service)){
			return convertInstantTradeParm(reqParm);

		}else if("trade_settle".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);

		}else if("trade_refund".equals(service)){
			List<String> fieldNameList = new ArrayList<String>();
			fieldNameList.add("royalty_info");
			return convertWithSpecialParm(reqParm, fieldNameList);

		}
		return null;
	}
	/**
	 * 转换给定参数名的参数，将给定参数的JSON字符串格式转换成JSON对象数组格式
	 * @param reqParm
	 * @param fieldNameList
	 * @return
	 */
	public JSONObject convertWithSpecialParm(String reqParm, List<String> fieldNameList){

		JSONObject reqJson = JSONObject.parseObject(reqParm);

		if(!CollectionUtils.isEmpty(fieldNameList)){

			for(String fieldName : fieldNameList){
				String fieldValue = reqJson.getString(fieldName);
				if(StringUtils.isNoneBlank(fieldValue)){
					JSONArray fieldValueJSONArray = JSONArray.parseArray(fieldValue);
					reqJson.put(fieldName, fieldValueJSONArray);
				}
			}

		}

		return reqJson;
	}
	/**
	 * 转换即时到账参数
	 * @param req
	 * @return
	 */
	public JSONObject convertInstantTradeParm(String req){
		JSONObject tradeReq = JSONObject.parseObject(req);
		//设置复杂属性
		//转换支付方式pay_method
		String pay_method = tradeReq.getString("pay_method");
		JSONObject payMethod = JSONObject.parseObject(pay_method);
		tradeReq.put("pay_method", payMethod);

		//转换终端信息域terminal_info
		String terminal_info = tradeReq.getString("terminal_info");
		JSONObject terminalInfo = JSONObject.parseObject(terminal_info);
		tradeReq.put("terminal_info", terminalInfo);
		//转换商户自定义域merchant_custom
		String merchant_custom = tradeReq.getString("merchant_custom");
		JSONObject merchantCustom = JSONObject.parseObject(merchant_custom);
		tradeReq.put("merchant_custom", merchantCustom);

		//转换交易信息trade_info
		String trade_info = tradeReq.getString("trade_info");

		if(StringUtils.isNoneBlank(trade_info)){
			JSONObject tradeInfo = JSONObject.parseObject(trade_info);
			tradeReq.put("trade_info", tradeInfo);
			return tradeReq;
		}
		return null;
	}
	public List<Map<String,Object> > getTradeInfoList(List<Orders> ordersList,String basePath){
		List<Map<String,Object>>  tradeInfoList = new ArrayList<>();
		for(Orders orders:ordersList){
			if(Global.ORDER_STATE_NEW == orders.getOrderState()){
				tradeInfoList.add(getTradeInfo(orders,basePath));
			}
		}
		return  tradeInfoList;
	}

	/**
	 * 交易信息
	 * 如果一个订单中包含多种或多个商品数量，都合并成一个
	 * @param orders
	 * @return
	 */
	public Map<String,Object> getTradeInfo(Orders orders, String basePath){
		Map<String,Object> map = new HashMap<>();
		map.put("total_amount",orders.getOrderAmount()+Global.KJT_CHARGE+"");
		map.put("ensure_amount",orders.getOrderAmount()+Global.KJT_CHARGE+"");
		map.put("subject","商品");
		map.put("payee_identity_type","1");
        map.put("notify_url",basePath+Global.KJT_NOTIFY_URL);
//		map.put("notify_url","http://b2b.jhmis.net:8090/payment/kjt/notify.do");
		map.put("out_trade_no",orders.getOrderSn());
		map.put("currency","");
		map.put("trade_ext","");
		map.put("deposit_amount","");
		map.put("payee_identity",Global.KJT_PARTNER);
		map.put("price",orders.getOrderAmount()+Global.KJT_CHARGE);
		map.put("quantity","1");
		map.put("biz_no","");
		map.put("deposit_no","");
		map.put("show_url","");
		return map;
	}
	//分润账号
	public  Map<String,String> getRoyalty(String kjtAccount,double amount ){
		Map<String,String> map = new HashMap<>();
		map.put("payee_identity_type","2");
		map.put("payee_member_id",kjtAccount);
//		map.put("amount",String.valueOf(Math.round(amount*0.997*100/100)));
		map.put("amount",(amount-10)+"");
		return map;
	}

	//分润账号集
	public List<Map<String,String>> getRoyaltyList(Orders orders){
		List<Map<String,String>> royaltyList = new ArrayList<>();
		Map<String,String> royaltyMap = getRoyalty(orders.getKjtAccount(),orders.getOrderAmount());
		royaltyList.add(royaltyMap);
		return royaltyList;
	}

	//网关达成业务参数
	public Map<String,String> getTradeSettleBusinessMap(Orders orders){
		Map<String,String> map = new HashMap<>();
		map.put("orig_out_trade_no",orders.getOrderSn());
		map.put("royalty_info",JsonMapper.getInstance().toJson(getRoyaltyList(orders)));
		map.put("out_trade_no",orders.getOrderSn()+(new Random().nextInt(900)+100));
		return map;
	}

	/**
	 * 获取分账失败列表
	 * @param page
	 * @param orders
	 * @return
	 */
	public Page<Orders> findRoyaltyPageList(Page<Orders> page, Orders orders) {
		dataRuleFilter(orders);
		orders.setPage(page);
		page.setList(mapper.findRoyaltyList(orders));
		return page;
	}
	public List<Orders> findConfirmOrders(Orders orders){
		return mapper.findConfirmOrders(orders);
    }

	public List<Orders> getOrdersList(Orders orders) {
		// TODO Auto-generated method stub
		return mapper.getOrdersList(orders);
	}
}