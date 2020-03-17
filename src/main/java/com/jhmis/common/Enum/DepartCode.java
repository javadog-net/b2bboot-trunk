package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-10-21 11:08
 * @Description: 部门枚举类
 */
public enum DepartCode {

    /**
     部门枚举类
    */
    DEPART_PURCHASE("0","采购"),
    DEPART_MARKET("1","营销"),
    DEPART_DEVISE("2","设计"),
    DEPART_FINANCE("3","财务"),
    DEPART_ADMINISTRATION("4","行政"),
    DEPART_SERVICE("5","业务"),
    DEPART_OTHERS("6","其他"),
    ;
    private  String label;
    private  String value;

    DepartCode(String label, String value) {
        this.label = label;
        this.value = value;
    }

    // 普通方法
    public static String getIndex(String value) {
        for (DepartCode c : DepartCode.values()) {
            if (c.getValue() == value) {
                return c.label;
            }
        }
        return null;
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
