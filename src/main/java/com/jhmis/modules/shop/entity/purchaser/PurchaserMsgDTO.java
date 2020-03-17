package com.jhmis.modules.shop.entity.purchaser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PurchaserMsgDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String purchaserId;
    private String relId;
    private Map<String,String> params = new HashMap<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(String purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void put(String key, String value){
        params.put(key, value);
    }

    public void remove(String key){
        params.remove(key);
    }
}
