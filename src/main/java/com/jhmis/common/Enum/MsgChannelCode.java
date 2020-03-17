package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-09-02 17:19
 * @Description: 需求来源码表
 */
public enum  MsgChannelCode {
    /**
     需求来源码表
    */

    FOUR("0","400"),
    FOUR_CUSTOMER_SERVICE("1","400客服"),
    APP("2","大客户app"),
    EAPP("3","E企app商空"),
    B2BWEB("4","企业购官网"),
    HOUSE("5","住房租赁频道"),
    COMMERCIAL_PRODUCTS("6","军工"),
    COMMERCIAL_AIR_CONDITIONING("7","商空"),
    COMMERCIAL_AIR_CONDITIONING_DEPART("8","商空专区"),
    TEL("9","固话"),
    GRAND_RECEPTION("10","大接待"),
    WEBSIT_MESSAGE("11","官网留言"),
    CUSTOMER_SERVICE("12","客服"),
    WEIXIN("13","微信"),
    MOBILE("14","手机端"),
    TENDER_NETWORK("15","招标网"),
    HAIER_B2B("16","海尔b2b"),
    HAIER_SHOP("17","海尔商城"),
    HAIER_WEBSIT("18","海尔官网"),
    HAIER_MOBILE("19","海尔手机端"),
    BAIDU("20","百度"),
    COMMUNITY_INTERACTION("21","社群交互"),
    HOTEL("22","酒店频道留言板")

    ;
    private String index;
    private String value;

    MsgChannelCode(String index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MsgChannelCode{" +
                "value='" + value + '\'' +
                '}';
    }

    // 普通方法
    public static String getName(String index) {
        for (MsgChannelCode c : MsgChannelCode.values()) {
            if (c.getIndex() == index) {
                return c.value;
            }
        }
        return null;
    }

    // 普通方法
    public static String getIndex(String value) {
        for (MsgChannelCode c : MsgChannelCode.values()) {
            if (c.getValue().equals(value)) {
                return c.index;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(MsgChannelCode.FOUR.getIndex());
    }
}
