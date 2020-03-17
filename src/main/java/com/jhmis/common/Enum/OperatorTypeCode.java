package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-09-23 16:55
 * @Description: 操作用户类型
 */
public enum OperatorTypeCode {
    ORDINARY_USER("0","普通用户"),
    CUSTOMER_SERVICE("1","平台"),
    CHIEF_INSPECTOR("2","总监"),
    DISTRIBUTOR("3","经销商"),
    ;
    private  String label;
    private  String value;

    OperatorTypeCode(String label, String value) {
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
