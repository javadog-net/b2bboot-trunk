package com.haier.order.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单日志类型信息（操作人类型，操作类型）
 * @author wzh
 * @data 2017/3/21
 */
public class OrderLogParamModel {
    /**
     * 操作人类型
     */
    private static Map<String,String> operTypeMap=new HashMap<String,String>();

    /**
     * 操作类型
     */
    private static Map<String,String> opeTypeMap=new HashMap<String,String>();

    static{
        operTypeMap.put(OrderLogParamModel.OPER_TYPE_CUSTOMER,"会员");
        operTypeMap.put(OrderLogParamModel.OPER_TYPE_STORE,"商户");
        operTypeMap.put(OrderLogParamModel.OPER_TYPE_PLATFORM,"平台");


        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_ORDER_STATE,"更新订单状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE,"更新订单");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_SUN_STATE,"更新订单晒单状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_SHOP_STATE,"更新订单店铺评论状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_APPRAISE_STATE,"更新订单评论状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_CREATE,"创建订单");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_ACCOUNT_STATE,"更新订单结算单状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_SPLIT_STATE,"更新订单生成账单状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_RETURN_STATE,"更新订单退货状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_REFUND_STATE,"更新订单退款状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_CHANGE_STATE,"更新订单换货状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_PAY_STATE,"更新订单支付状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_DEL_STATE,"更新订单删除状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_SEND_POINTS_STATE,"更新订单赠送积分状态");
        opeTypeMap.put(OrderLogParamModel.OPE_TYPE_ORDER_UPDATE_SEND_GIFT_COUPON_STATE,"更新订单赠送优惠券状态");

    }

    /**
     * 获取操作人类型说明
     * @param operType 操作人类型
     * @return 操作人类型说明
     */
    public static String getOperType(String operType) {
        return operTypeMap.get(operType);
    }

    /**
     * 获取操作类型说明
     * @param opeType 操作类型
     * @return 操作类型说明
     */
    public static String getOpeType(String opeType) {
        return opeTypeMap.get(opeType);
    }

    ///////////////////////////////////////////////// 操作人类型

    /**
     * 会员
     */
    public static final String OPER_TYPE_CUSTOMER="1";
    /**
     * 商户
     */
    public static final String OPER_TYPE_STORE="2";
    /**
     * 平台
     */
    public static final String OPER_TYPE_PLATFORM="3";

    //////////////////////////////////////// 操作类型

    /**
     * 更新订单状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_ORDER_STATE="1";
    /**
     * 更新订单
     */
    public static final String OPE_TYPE_ORDER_UPDATE ="2";
    /**
     * 更新订单晒单状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_SUN_STATE="3";
    /**
     * 更新订单店铺评论状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_SHOP_STATE="4";
    /**
     * 更新订单评论状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_APPRAISE_STATE="5";
    /**
     * 订单信息
     */
    public static final String OPE_TYPE_ORDER_CREATE="6";

    /**
     * 更新订单结算状态
     */
    public  static final String OPE_TYPE_ORDER_UPDATE_ACCOUNT_STATE="7";
    /**
     * 更新平台生成账单状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_SPLIT_STATE = "8";
    /**
     * 更新订单退货状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_RETURN_STATE = "9";
    /**
     * 更新订单退款状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_REFUND_STATE = "10";
    /**
     * 更新订单换货状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_CHANGE_STATE = "11";
    
    /**
     * 更新订单支付状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_PAY_STATE = "12";
    /**
     * 更新订单删除状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_DEL_STATE = "13";

    /**
     * 更新订单赠送积分状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_SEND_POINTS_STATE = "14";
    /**
     * 更新订单赠送优惠券状态
     */
    public static final String OPE_TYPE_ORDER_UPDATE_SEND_GIFT_COUPON_STATE = "15";

}

