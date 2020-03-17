package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-08-14 17:19
 * @Description: Acg异常类对应编码
 */
public enum AcgErrorCode {
    PARAM_COMPANYCODE_ERROR("acg001","companyCode必填参数不能为空"),
    PARAM_COMPANYNAME_ERROR("acg002","companyName必填参数不能为空"),
    PARAM_CONTACTS_ERROR("acg003","contacts必填参数不能为空"),
    PARAM_MOBILE_ERROR("acg004","mobile必填参数不能为空"),
    PARAM_EMAIL_ERROR("acg005","email必填参数不能为空"),
    PARAM_ACGPASSWORD_ERROR("acg006","acgPassword必填参数不能为空"),
    HAVE_ERROR("acg010","此客户已存在"),
    SAVE_ERROR("acg011","客户Dealer数据保存失败"),
    SAVE_ACCOUNT_ERROR("acg012","客户account数据保存失败"),
    UNKNOWN_ERROR("acg999","系统繁忙，请稍后再试....");
    ;

    private String value;
    private String desc;

    public String getValue() {
        return value;
    }

    AcgErrorCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }
}
