package com.haier.order.dto;

public class KpInvoiceDTO {

    private String orderId;		//订单
    private String kind;		    // 发票类型  发票类型(1普通发票，2增值税发票，3电子发票)
    private String companyName;	// 单位名称
    private String taxCode;		// 纳税人识别号
    private String regAddr;		// 注册地址
    private String regPhone;		// 注册电话
    private String regBname;		// 开户银行
    private String regBaccount;	// 银行账户
    private String recName;		// 收票人姓名
    private String recMobphone;	// 收票人手机号
    private String recProvinceId;	// 省
    private String recCityId;		// 市
    private String recDistrictId;	// 区
    private String recAreaInfo;	// 收票人所在地区
    private String recDetailAddr;	// 送票详细地址

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr;
    }

    public String getRegPhone() {
        return regPhone;
    }

    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone;
    }

    public String getRegBname() {
        return regBname;
    }

    public void setRegBname(String regBname) {
        this.regBname = regBname;
    }

    public String getRegBaccount() {
        return regBaccount;
    }

    public void setRegBaccount(String regBaccount) {
        this.regBaccount = regBaccount;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecMobphone() {
        return recMobphone;
    }

    public void setRecMobphone(String recMobphone) {
        this.recMobphone = recMobphone;
    }

    public String getRecProvinceId() {
        return recProvinceId;
    }

    public void setRecProvinceId(String recProvinceId) {
        this.recProvinceId = recProvinceId;
    }

    public String getRecCityId() {
        return recCityId;
    }

    public void setRecCityId(String recCityId) {
        this.recCityId = recCityId;
    }

    public String getRecDistrictId() {
        return recDistrictId;
    }

    public void setRecDistrictId(String recDistrictId) {
        this.recDistrictId = recDistrictId;
    }

    public String getRecAreaInfo() {
        return recAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    public String getRecDetailAddr() {
        return recDetailAddr;
    }

    public void setRecDetailAddr(String recDetailAddr) {
        this.recDetailAddr = recDetailAddr;
    }

}
