package com.jhmis.modules.tools.entity;

import java.util.List;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 16:50 2018/7/22
 * @Modified By
 */
public class BatchsaleTemplate {
    private String retCode;
    private String retMsg;
    private List<BatchsaleTemplateItem> batchsaleTemplateList;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public List<BatchsaleTemplateItem> getBatchsaleTemplateList() {
        return batchsaleTemplateList;
    }

    public void setBatchsaleTemplateList(List<BatchsaleTemplateItem> batchsaleTemplateList) {
        this.batchsaleTemplateList = batchsaleTemplateList;
    }
    @Override
    public String toString() {
        return "BatchsaleTemplate{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", batchsaleTemplateList=" + batchsaleTemplateList +
                '}';
    }
}
