package com.jhmis.modules.euc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 获取授权状态的参数
 */
public class AuthInfoRequest implements Serializable {
    private String businessCode; //商机编码
    private List<AuthInfo> authList; //授权信息

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public List<AuthInfo> getAuthList() {
        return authList;
    }

    public void setAuthList(List<AuthInfo> authList) {
        this.authList = authList;
    }

}
