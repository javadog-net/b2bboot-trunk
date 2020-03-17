package com.jhmis.modules.shop.entity.dealer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DealerMsgDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String dealerId;
    private String relId;
    private Map<String,String> params = new HashMap<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
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
