package com.jhmis.api.shop;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.http.HttpClientUtil;
import com.jhmis.core.mapper.JsonMapper;
import com.jhmis.modules.shop.entity.OrderPay;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.service.OrderPayService;
import com.jhmis.modules.shop.service.OrdersService;
import com.kjtpay.gateway.common.util.security.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller
 * 主要是可能会与后台的控制器重名
 * 支付控制器
 */
@Api(value = "ApiShopPayController", description = "支付接口")
@RestController
@RequestMapping("/api/shop/pay")
public class ApiShopPayController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderPayService orderPayService;
    @Resource
    private SecurityService securityServiceRsa;
    protected Logger logger = LoggerFactory.getLogger(ApiShopPayController.class);

    private static String KJT_GATEWAY_NEW = "https://gateway.kjtpay.com/recv.do";// 正式
//    private static String KJT_GATEWAY_NEW = "https://c1gateway.kjtpay.com/recv.do";// 测试
    /**
     * 支付
     * @param paySn
     * @param return_url
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(notes = "pay", httpMethod = "POST", value = "支付接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paySn", value = "支付单号", required =true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "return_url", value = "支付完成后的跳转页面", required = true, paramType = "form",dataType = "String"),
    })
    @RequestMapping(value = "pay")
    public AjaxJson pay(String paySn, String return_url, HttpServletRequest request, HttpServletResponse response){
        String portStr = "";
        int port = request.getServerPort();
        if (port != 80 && port != 443) {
            portStr = ":".concat(String.valueOf(port));
        }
        String basePath = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath();
        if (StringUtils.isEmpty(paySn)) {
            return AjaxJson.fail("订单的支付单号不能为空");
        }
        //判断订单是否已支付
        OrderPay orderPay = orderPayService.findUniqueByProperty("pay_sn",paySn);
        if(orderPay == null){
            return AjaxJson.fail("该订单的支付信息不存在");
        }
        if(orderPay.getApiPayState() == 1){
            return AjaxJson.fail("该订单已支付");
        }
        //判断买家是否为空
//        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
//        if(purchaserAccount == null){
//            return  AjaxJson.fail("买家不存在");
//        }
        //判断订单是否存在
        Orders orders = new Orders();
        orders.setPaySn(paySn);
        orders.setOrderState(Global.ORDER_STATE_NEW);
        List<Orders> ordersList = ordersService.findList(orders);
        if(ordersList.size() == 0){
            return AjaxJson.fail("订单不存在");
        }
        for(Orders o:ordersList){
            if(o.getOrderAmount()<10){
                return AjaxJson.fail("订单金额不能低于10元");
            }
        }
        List<Map<String,String>> list = new ArrayList<>();
        Map<String, String> payMap = getCommonParam(orderPay,ordersList,null,basePath,request,Global.ENSURE_TRADE);
        String htmlStr = buildRequest(payMap, "post", "确认");
        return AjaxJson.ok(htmlStr);
    }
    @ApiOperation(notes = "tradeSettle", httpMethod = "POST", value = "网关达成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", required =true, paramType = "form",dataType = "String")
    })
    @RequestMapping(value = "tradeSettle")
    public AjaxJson tradeSettle(String orderId, HttpServletRequest request, HttpServletResponse response){
        String portStr = "";
        int port = request.getServerPort();
        if (port != 80 && port != 443) {
            portStr = ":".concat(String.valueOf(port));
        }
        String basePath = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath();
        if (StringUtils.isEmpty(orderId)) {
            return AjaxJson.fail("支付ID不能为空");
        }
        //判断订单是否已支付
        Orders orders = ordersService.get(orderId);
        if(orders == null){
            return AjaxJson.fail("该订单信息不存在");
        }
        if(orders.getApiPayState() != 1){
            return AjaxJson.fail("该订单未支付");
        }
        /*
        判断买家是否为空
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        return  AjaxJson.fail("买家不存在");
        }
        */
        List<Map<String,String>> list = new ArrayList<>();
        Map<String, String> payMap = getCommonParam(null,null,orders,basePath,request,Global.TRADE_SETTLE);
        String htmlStr = buildRequest(payMap, "post", "确认");
        return AjaxJson.ok(htmlStr);
    }
    /**
     * 建立请求，以表单HTML形式构造（默认）
     *
     * @param sParaTemp
     *            请求参数数组
     * @param strMethod
     *            提交方式。两个值可选：post、get
     * @param strButtonName
     *            确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"kjtsubmit\" name=\"kjtsubmit\" action=\"" + KJT_GATEWAY_NEW + "\" method=\""
                + strMethod + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value="";
            try {
                value = URLEncoder.encode((String) sPara.get(name),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['kjtsubmit'].submit();</script>");
        logger.info("form 表单中的内容----------"+sbHtml);

        return sbHtml.toString();
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
    public Map<String,String> getCommonParam(OrderPay orderPay,List<Orders> ordersList,Orders orders,String basePath,HttpServletRequest request,String serviceName){
        Map<String,String> map = new HashMap<>();
        //加密
        String req = "";
        if(Global.ENSURE_TRADE.equals(serviceName)){
            map.put("version","1.1");
            req = JsonMapper.getInstance().toJson(getBusinessMap(orderPay,ordersList,request,basePath));
        }else if(Global.TRADE_SETTLE.equals(serviceName)){
            map.put("version","1.0");
            req = JsonMapper.getInstance().toJson(getTradeSettleBusinessMap(orders,request));
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
        map.put("total_amount",orders.getOrderAmount());
        map.put("ensure_amount",orders.getOrderAmount());
        map.put("subject","商品");
        map.put("payee_identity_type","1");
        map.put("notify_url",basePath+Global.KJT_NOTIFY_URL);
//        map.put("notify_url","http://b2b.jhmis.net:8090/payment/kjt/notify.do");
        map.put("out_trade_no",orders.getOrderSn());
        map.put("currency","");
        map.put("trade_ext","");
        map.put("deposit_amount","");
        map.put("payee_identity",Global.KJT_PARTNER);
        map.put("price",orders.getOrderAmount());
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
        map.put("amount","1");
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
    public Map<String,String> getTradeSettleBusinessMap(Orders orders,HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        map.put("orig_out_trade_no",orders.getOrderSn());
        map.put("royalty_info",JsonMapper.getInstance().toJson(getRoyaltyList(orders)));
        map.put("out_trade_no",orders.getOrderSn()+(new Random().nextInt(900)+100));
        return map;
    }

//    //退款接口业务参数
//    public Map<String,String> getTradeRefundBusinessMap(RefundReturn refundReturn, HttpServletRequest request){
//        Map<String,String> map = new HashMap<>();
//        map.put("out_trade_no",orderPay.getPaySn());
//        map.put("orig_out_trade_no",orderPay.getPaySn());
//        map.put("refund_amount",refundReturn.getRefundAmount()+"");
//        map.put("royalty_info",JsonMapper.getInstance().toJson(getRoyaltyList(ordersList)));
//
//        return map;
//    }
@ApiOperation(notes = "payTest", httpMethod = "POST", value = "支付接口")
@ApiImplicitParams({
        @ApiImplicitParam(name = "paySn", value = "支付单号", required =true, paramType = "form",dataType = "String"),
        @ApiImplicitParam(name = "return_url", value = "支付完成后的跳转页面", required = true, paramType = "form",dataType = "String"),
})
@RequestMapping(value = "payTest")
public AjaxJson payTest(String paySn, String return_url, HttpServletRequest request, HttpServletResponse response){
    if (StringUtils.isEmpty(paySn)) {
        return AjaxJson.fail("订单的支付单号不能为空");
    }
    //判断订单是否已支付
    OrderPay orderPay = orderPayService.findUniqueByProperty("pay_sn",paySn);
    if(orderPay == null){
        return AjaxJson.fail("该订单的支付信息不存在");
    }
    if(orderPay.getApiPayState() == 1){
        return AjaxJson.fail("该订单已支付");
    }
    //判断买家是否为空
//        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
//        if(purchaserAccount == null){
//            return  AjaxJson.fail("买家不存在");
//        }
    //判断订单是否存在
    Orders orders = new Orders();
    orders.setPaySn(paySn);
    orders.setOrderState(Global.ORDER_STATE_NEW);
    List<Orders> ordersList = ordersService.findList(orders);
    if(ordersList.size() == 0){
        return AjaxJson.fail("订单不存在");
    }
    orderPay.setApiPayState(Global.KJT_API_PAY_STATE_SUCCESS);
    orderPayService.updatePayState(orderPay,null);
    return AjaxJson.ok("付款成功！");

}
}
