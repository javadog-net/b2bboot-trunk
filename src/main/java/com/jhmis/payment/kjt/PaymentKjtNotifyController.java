package com.jhmis.payment.kjt;

import com.alibaba.fastjson.JSON;
import com.jhmis.common.config.Global;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.lock.LockUtils;
import com.jhmis.modules.shop.entity.OrderPay;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.service.OrderPayService;
import com.jhmis.modules.shop.service.OrdersService;
import com.jhmis.modules.shop.service.dealer.DealerMsgService;
import com.jhmis.modules.shop.service.purchaser.PurchaserMsgService;
import com.kjtpay.gateway.common.domain.VerifyResult;
import com.kjtpay.gateway.common.util.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@RequestMapping("/payment/kjt")
@RestController
public class PaymentKjtNotifyController {
    protected Logger logger = LoggerFactory.getLogger(PaymentKjtNotifyController.class);
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private DealerMsgService dealerMsgService;
    @Autowired
    private PurchaserMsgService purchaserMsgService;
    @Autowired
    private SecurityService securityServiceRsa;

    @RequestMapping(value = "notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.info("notify in---------------");
            Map<String, String> params = getParamMap(request);
            // 商户支付单号
            String out_trade_no = params.get("outer_trade_no");  //支付单号
            String inner_trade_no = params.get("inner_trade_no");  //快捷通订单号
            String tradeStatus = params.get("trade_status");
            if(StringUtils.isEmpty(out_trade_no) || StringUtils.isEmpty(inner_trade_no) || StringUtils.isEmpty(tradeStatus)){
                response.getWriter().write("fail");
                return;
            }
            boolean locked =  LockUtils.lock(out_trade_no);
            if (locked) {
                try {
                    logger.info("验证in---------------");
                    // 验签成功！
                    if (verify(params)) {
                        //买家付款的回调
                        if (StringUtils.isNotEmpty(tradeStatus) && (tradeStatus.equals(Global.KJT_PAY_STATUS_FINISHED))) {
                            //判断订单支付状态
                            Orders orders = ordersService.findUniqueByProperty("order_sn",out_trade_no);
                          if(orders != null){
                              if(Global.KJT_API_PAY_STATE_SUCCESS == orders.getApiPayState()) {
                                  response.getWriter().write("success");
                                  return;
                              }
                          } else {
                            response.getWriter().write("fail");
                            return;
                        }
                          //更新支付状态，更新订单状态
                            orders.setApiPayState(Global.KJT_API_PAY_STATE_SUCCESS);
                            orders.setApiPayDate(new Date());
                            orders.setOrderState(Global.ORDER_STATE_PAY_FINISHED);
                            ordersService.updateOrdersState(orders);
                            //通知经销商消息
                            Map<String,String> map = new HashMap<>();
                            map.put("order_sn",orders.getOrderSn());
                            dealerMsgService.putMsg("new_order",orders.getDealerId(),orders.getId(),map);
                            purchaserMsgService.putMsg("new_order",orders.getPurchaserId(),orders.getId(),map);
                            response.getWriter().write("success");
                        }
                        //网关达成（分账回调）
                        else if(StringUtils.isNotEmpty(tradeStatus) && (tradeStatus.equals(Global.KJT_TRADE_STATUS_SUCCESS)||tradeStatus.equals(Global.KJT_TRADE_STATUS_FINISHED))){
                            // 判断分账状态
                            Orders orders = ordersService.findUniqueByProperty("order_sn",out_trade_no);
                            if(orders != null){
                                if(Global.KJT_API_ROYALTY_STATE_SUCCESS == orders.getRoyaltyState()) {
                                    response.getWriter().write("success");
                                    return;
                                }
                            } else {
                                response.getWriter().write("fail");
                                return;
                            }
                            //更新支付状态，更新订单状态
                            orders.setRoyaltyState(Global.KJT_API_ROYALTY_STATE_SUCCESS);
                            orders.setRoyaltyDate(new Date());
                            orders.setOrderState(Global.ORDER_STATE_RECEIVED);
                            orders.setTradeNo(inner_trade_no);
                            ordersService.updateOrdersState(orders);
                            response.getWriter().write("success");
                        }
                    } else {
                        // 验签失败
                        logger.info("verify-------------------------------");
                        response.getWriter().write("fail");
                    }
                } finally {
                    LockUtils.unLock(out_trade_no);
                }
            }
        } catch (Exception e) {
            logger.error("failed----", e);
            response.getWriter().write("fail");
        }
    }

    /**
     * 商户验签
     *
     * @param
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/verify.do", method = RequestMethod.POST)
    public boolean verify(Map<String, String> responseParameter) throws UnsupportedEncodingException {
        VerifyResult result = null;
        logger.info("verify______________________________________");
        if (responseParameter != null) {

            String bizContent = responseParameter.get("biz_content") == null ? null
                    : JSON.toJSONString(responseParameter.get("biz_content"));
            logger.info("bizContent-----------------" + bizContent);
            logger.info(responseParameter.keySet().size() + "");

              for (Iterator<String> iter =  responseParameter.keySet().iterator(); iter.hasNext();) {
                  String name = iter.next();
                  String values = responseParameter.get(name);
                  String  valueStr = new String(values.getBytes("ISO-8859-1"), "utf-8");
                  responseParameter.put(name,valueStr);
              }

            String charset = responseParameter.get("_input_charset");
            String sign = responseParameter.get("sign").toString();
            result = securityServiceRsa.verify(responseParameter, sign, charset);
            if (result != null) {
                if (result.isSuccess()) {
                    return true;
                } else {
                    logger.info("result is:" + result + "   sign--------------" + sign);
                    return false;
                }

            } else {
                return false;
            }
        }
        // 参数错误
        return false;
    }
    public Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            logger.info("name:" + name + "&&&& value=" + valueStr);
            params.put(name, valueStr);
        }
        return params;
    }
}
