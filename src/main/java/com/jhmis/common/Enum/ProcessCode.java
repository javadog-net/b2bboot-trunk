package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-09-05 11:02
 * @Description: 采购商需求抢派单相关参数枚举类
 */
public enum ProcessCode {
    /**
     * 枚举要有注释
    */
    NO_REGIST("0","未注册"),
    IS_REGIST("1","已注册"),
    NO_SENDMSG("0","未发送"),
    IS_SENDMSG("1","已发送"),
    SERVICE_ISCHECK_NO_PASS("0","审核不通过"),
    SERVICE_ISCHECK_PASS("1","审核通过"),
    DIRECTOR_ISCHECK_NO_PASS("0","审核不通过"),

    DIRECTOR_ISCHECK_PASS("1","审核通过"),
    IS_DISPATCHER("1","已派单"),
    NO_DISPATCHER("0","未派单"),
    DISORDER_SOURCE_DISPAT("0","派单"),
    DISORDER_SOURCE_TIMEOUT("1","抢单超时"),
    DISORDER_SOURCE_CANCEL("2","废弃订单"),
    DISPA_TYPE_PT("0","平台派单"),
    DISPA_TYPE_ZJ("1","总监派单"),
    MSG_IS_READ("1","已读"),
    MSG_NO_READ("0","未读"),
    MSG_IS_CLOSE("0","未关闭"),
    MSG_NO_CLOSE("1","已关闭"),
    DISPATCHER_MSG_IS_CLOSE("0","未关闭"),
    DISPATCHER_MSG_NO_CLOSE("1","已关闭"),

    ORDER_SOURCE_GRAB("0","抢单"),
    ORDER_SOURCE_PLATFORM_ASSIGNMENT("1","平台派单"),
    ORDER_SOURCE_DIRECTOR_DISPATCH("2","总监派单"),
    MSG_ORDER_CANCELFLAG_NO_OPERATE("0","未处理"),
    MSG_ORDER_BIND_NO_BIND("0","未中标"),
    ORDER_OUT_WAREHOUSE("1","已出库"),

    ORDER_CANCEL_FLAG_WAIT("0","未处理"),

    CANCEL_FLAG_WAIT("0","未处理"),
    CANCEL_FLAG_FOLLOW("1","承接前废弃"),
    ANCEL_FLAG_RETAIL_CLOSURE("2","零售关闭"),
    ANCEL_FLAG_WORKS_CLOSURE("3","工程关闭"),

    ORDER_PROCESS_CLOSE("1","关闭"),
    ORDER_PROCESS_ON_GOINT("2","正在进行中"),
    ORDER_PROCESS_BIND("3","中标"),

    IS_NOT_BIND("0","未中标"),
    IS_BIND("1","已中标"),

    NO_UNDERTAKE("-1","未承接"),
    GC_UNDERTAKE("0","工程"),
    LS_UNDERTAKE("1","零售"),

    MSG_CATEGORY_ENQUIRY("0","询价"),
    MSG_CATEGORY_AGENT("1","代理"),
    MSG_CATEGORY_INVITATION("2","邀标"),
    MSG_CATEGORY_ACQUISITION("3","资料获取"),
    MSG_CATEGORY_COOPERATION("4","品牌合作"),
    MSG_CATEGORY_EXCHANGE("5","文化交流"),

    MOBILE_PROCESS_RESPONSE("0","需求响应"),
    MOBILE_PROCESS_DOCUMENTARY("1","总监响应"),
    MOBILE_PROCESS_DOCUMENTARY_SE("2","供应商承接"),
    MOBILE_PROCESS_ARRIVAL("3","到货"),
    MOBILE_PROCESS_INSTALL("4","安装"),
    MOBILE_PROCESS_DRYING("5","晒单"),

    YES("1","是"),
    NO("0","否"),


    ;
    private  String label;
    private  String value;

    ProcessCode(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "ProcessCode{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    // 普通方法
    public static String getIndex(String value) {
        for (ProcessCode c : ProcessCode.values()) {
            if (c.getValue().equals(value) ) {
                return c.label;
            }
        }
        return null;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
