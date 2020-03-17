package com.haier.order.dto;



import java.util.HashMap;
import java.util.Map;

/**
 * 订单主信息相关状态声明（订单状态，订单类型）
 * @author wzh
 * @data 2017/3/21
 */
public class OrderParamModel {

    /**
     * 订单状态Map
     */
    private static Map<String,String> orderStateMap=new HashMap<String,String>();

    /**
     * 订单完成状态Map
     */
    private static Map<String,String> stateFinishMap=new HashMap<String,String>();
    /**
     * 支付类型Map
     */
    private static Map<String,String> payTypeMap=new HashMap<String,String>();

    /**
     * 配送类型
     */
    private static Map<String,String> shipTypeMap=new HashMap<String,String>();

    /**
     * 订单类型
     */
    private static Map<String,String> orderTypeMap=new HashMap<String,String>();

    static {
        orderStateMap.put(OrderParamModel.ORDER_STATE_WAIT_PAY, "等待买家付款");
        orderStateMap.put(OrderParamModel.ORDER_STATE_WAIT_DELIVER, "等待卖家发货");
        orderStateMap.put(OrderParamModel.ORDER_STATE_PREPARE_DELIVER, "卖家备货中");
        orderStateMap.put(OrderParamModel.ORDER_STATE_WAIT_CONFIRM, "等待买家确认收货");
        orderStateMap.put(OrderParamModel.ORDER_STATE_SUCCESS, "交易成功");
        orderStateMap.put(OrderParamModel.ORDER_STATE_CLOSE, "交易关闭");

        stateFinishMap.put(OrderParamModel.STATE_NOT_START,"未开始");
        stateFinishMap.put(OrderParamModel.STATE_FINISH,"完成");
        stateFinishMap.put(OrderParamModel.STATE_UNFINISH,"未完成");

        payTypeMap.put(OrderParamModel.PAY_TYPE_COD,"在线支付");
        payTypeMap.put(OrderParamModel.PAY_TYPE_NO_COD,"货到付款");

        shipTypeMap.put(OrderParamModel.SHIP_TYPE_EXPRESS,"快递配送");
        shipTypeMap.put(OrderParamModel.SHIP_TYPE_PICKUP,"自提");
        shipTypeMap.put(OrderParamModel.SHIP_TYPE_SURFACEMAIL,"平邮");


        orderTypeMap.put(OrderParamModel.ORDER_TYPE_NOMALTYPE, "实物订单");

        orderTypeMap.put(OrderParamModel.ORDER_TYPE_FACEPAY, "当面付");
    }

    public static String getOrderTypeByKey(String key) {
        return orderTypeMap.get(key);
    }

    public static Map<String, String> getOrderTypeMap() {
        return orderTypeMap;
    }

    public static String getShipTypeByKey(String key) {
        return shipTypeMap.get(key);
    }

    public static Map<String, String> getShipTypeMap() {
        return shipTypeMap;
    }

    public static String getPayTypeByKey(String key) {
        return payTypeMap.get(key);
    }

    public static Map<String, String> getPayTypeMap() {
        return payTypeMap;
    }

    public static String getStateFinishMap(String key) {
        return stateFinishMap.get(key);
    }

    public static String getOrderStateNameByKey(String key) {
        return orderStateMap.get(key);
    }

    public static Map<String, String> getOrderStateMap() {
        return orderStateMap;
    }


    /////////////////// 订单状态
    /**
     * 等待买家付款
     */
    public static final String ORDER_STATE_WAIT_PAY="1";
    /**
     * 等待卖家发货
     */
    public static final String ORDER_STATE_WAIT_DELIVER="2";
    /**
     * 卖家备货中
     */
    public static final String ORDER_STATE_PREPARE_DELIVER="3";
    /**
     * 等待买家确认收货
     */
    public static final String ORDER_STATE_WAIT_CONFIRM="4";
    /**
     * 交易成功
     */
    public static final String ORDER_STATE_SUCCESS="5";
    /**
     * 交易关闭
     */
    public static final String ORDER_STATE_CLOSE="6";

    /////////////////////////////////////
    /**
     * 订单支付类型---在线支付
     */
    public static final String PAY_TYPE_COD="1";

    /**
     * 订单支付类型---货到付款
     */
    public static final String PAY_TYPE_NO_COD="2";
    //////////////////////////////////////////////////////
    /**
     * 配送方式--快递配送
     */
    public static final String SHIP_TYPE_EXPRESS="1";
    /**
     * 配送方式--自提
     */
    public static final String SHIP_TYPE_PICKUP="2";
    /**
     * 配送方式--平邮
     */
    public static final String SHIP_TYPE_SURFACEMAIL="3";

    ////////////////////////////////////////////

    /**
     * 订单类型---普通订单
     */
    public static final String ORDER_TYPE_NOMALTYPE="1";
    
    /**
     * 订单类型---当面付
     */
    public static final String ORDER_TYPE_FACEPAY = "2";

    //////////////////////////////////////////////////////
    /**
     * 状态未开始（评论，晒单，店铺评论,退货，退款）
     */
    public static final String STATE_NOT_START="0";
    /**
     * 状态已完成（评论，晒单，店铺评论,退货，退款）
     */
    public static final String STATE_FINISH = "1";
    /**
     * 状态流转中（评论，晒单，店铺评论,退货，退款）
     */
    public static final String STATE_UNFINISH = "2";

    /**
     * 五分好评（评论，晒单，店铺评论）
     */
    public static final Integer HIGH_PRAISE = 5;


}

