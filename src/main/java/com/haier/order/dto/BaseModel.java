package com.haier.order.dto;

import java.util.HashMap;
import java.util.Map;

public class BaseModel {
    private String uuid;
    private String oper;
    private String opeTime;
    private String createOper;
    private String createOpeTime;
    private int version;
    private String sortName = "opeTime";
    private String sortType = "desc";
    private Map<String, Integer> mapCondition = new HashMap();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getOpeTime() {
        return opeTime;
    }

    public void setOpeTime(String opeTime) {
        this.opeTime = opeTime;
    }

    public String getCreateOper() {
        return createOper;
    }

    public void setCreateOper(String createOper) {
        this.createOper = createOper;
    }

    public String getCreateOpeTime() {
        return createOpeTime;
    }

    public void setCreateOpeTime(String createOpeTime) {
        this.createOpeTime = createOpeTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Map<String, Integer> getMapCondition() {
        return mapCondition;
    }

    public void setMapCondition(Map<String, Integer> mapCondition) {
        this.mapCondition = mapCondition;
    }
}
