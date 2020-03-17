package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-11-08 15:17
 * @Description: hps项目来源基础数据对应枚举类
 */
public enum ProjectSourceCode {
    /**
    *@Author: hdx
    *@CreateTime: 14:48 2019/11/11
    *@param:  * @param null
    *@Description:

    */
    GN_PRJ_12("GN_PRJ_12","个人获取"),
    GN_PRJ_09("GN_PRJ_09","A类工程商"),
    GN_PRJ_13("GN_PRJ_13","生态会"),
    GN_PRJ_03("GN_PRJ_03","集团分供方&园区资源供应商客户"),
    GN_PRJ_16("GN_PRJ_16","政府红头文"),
    GN_PRJ_08("GN_PRJ_08","企业购B2B"),
    GN_PRJ_11("GN_PRJ_11","奥维"),
    GN_PRJ_17("GN_PRJ_17","明源云"),
    GN_PRJ_18("GN_PRJ_18","千里马"),
    GN_PRJ_19("GN_PRJ_19","瑞达恒"),
    GN_PRJ_20("GN_PRJ_20","乙方宝"),
    GN_PRJ_14("GN_PRJ_14","其它第三方"),
    GN_PRJ_15("GN_PRJ_15","经销商自主报备"),
    GN_PRJ_10("GN_PRJ_15","其他");



    private  String label;
    private  String value;

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

    ProjectSourceCode(String label, String value) {
        this.label = label;
        this.value = value;
    }
    @Override
    public String toString()  {
        return this.value;
    }
}
