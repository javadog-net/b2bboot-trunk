package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-09-02 17:19
 * @Description: 需求状态枚举类
 */
public enum MsgFlagCode {
    /**
     需求状态枚举类
    */
    PLATFORM_TO_BE_AUDITED("0","平台待审核"),
    PLATFORM_AUDIT_PASS("10","平台审核通过"),
    PLATFORM_AUDIT_NOT_PASS("11","平台审核不通过"),
    PLATFORM_DIRECT_DELIVERY("12","平台直接派单"),
    SHANGHANG_PROJECT_PLATFORM_APPROVED("13","商空项目平台审核通过"),
    DIRECTOR_APPROVED("20","总监通过"),
    DIRECTOR_FAILED_TO_APPROVE("21","总监审核不通过"),
    DIRECTOR_SENT_ORDER("22","总监派单"),
    WAITING_LIST("23","待派单(超过两小时未抢需求)"),
    PAYMENT_MANAGEMENT_PAYMENT("24","派单管理派单"),
    GRAB_SINGLE_SUCCESS("30","抢单成功"),
    CHOOSE_UNDERTAKE_BEFORE_CLOSING("31","经销商选择承接方式前关闭"),
    CHOOSES_UNDERTAKE_PROJECT("40","经销商选择承接方式-工程单"),
    CLOSE_AFTER_SELECTING_ACCEPTANCE("41","经销商选择承接方式后关闭"),
    CHOOSE_UNDERTAKE_RETAIL("42","经销商选择承接方式-零售"),
    UPLOAD_WITNESS_MATERIAL("50","零售上传见证性材料"),
    CUSTOMER_CLOSING("60","客服关闭"),
    CUSTOMER_DELIVERY_CLOSED("61","客服派单关闭"),
    MOBILE_PROCESS_ARRIVAL("70","到货"),
    MOBILE_PROCESS_INSTALL("71","安装"),
    MOBILE_PROCESS_DRYING("72","晒单")
    ;

    private  String label;
    private  String value;

    MsgFlagCode(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
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
