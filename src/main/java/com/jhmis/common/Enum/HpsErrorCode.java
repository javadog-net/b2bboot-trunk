package com.jhmis.common.Enum;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-08-14 21:36
 * @Description: hps错误对应码
 */
public enum HpsErrorCode{
    PARAM_OLDCODE_ERROR("hps_error_001","oldCode必填参数不能为空"),
    PARAM_NEWCODE_ERROR("hps_error_002","newCode必填参数不能为空"),
    OLDCODE_NO_EXIST_ERROR("hps_error_003","查无此客户，请核对"),
    OLDCODE_NO_ACOUNT_EXIST_ERROR("hps_error_004","查无此客户账户，请核对"),
    NEWCODE_EXIST_ERROR("hps_error_005","此88码账户已存在"),
    NEWCODE_TRANS_ERROR("hps_error_006","转化码已在企业购dealer存在，请核对"),
    NEWCODE_TRANS_ACCOUNT_ERROR("hps_error_007","转化码已在企业购dealer_account存在，请核对"),
    UPDATE_ERROR("hps_error_008","更新88码失败"),
    DATA_ERROR("hps_error_009","客户数据异常"),
    UNKNOWN_ERROR("hps_error_999","系统繁忙，请稍后再试....");
    private String value;
    private String desc;

    public String getValue() {
        return value;
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
    public String toString()  {
        return "[" + this.value + "]" + this.desc;
    }

    HpsErrorCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
