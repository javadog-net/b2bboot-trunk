package com.jhmis.common.wsClient.entity;

import java.util.Date;

/**
 * @Author：hdx
 * @Description: 销售样表实体类
 * @Date: Created in 16:40 2018/7/22
 * @Modified By
 */
public class BatchsaleTemplateItem {
    //信息ID_通用字段
    private String row_id;
    //物料号
    private String material_code;
    //销售组织
    private String sales_organization;
    //大渠道
    private String sales_channel;
    //小渠道
    private String sales_channel2;
    //客户号
    private String customer_code;
    //有效开始日期
    private Date active_date_begin;
    //有效截止日期
    private Date active_date_end;
    //最后更新时间_通用字段
    private Date last_upd;
    //删除标记:0正常,1删除(可选)_通用字段
    private String delete_flag;
    //产品组
    private String department;
    //组合类型，1:工贸渠道组合；2:市场级别组合；3:客户组合
    private String combine_type;
    //自有渠道投放方式(0：全部1：伞下投放 2：直营客户投放)
    private String put_methods;

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public String getMaterial_code() {
        return material_code;
    }

    public void setMaterial_code(String material_code) {
        this.material_code = material_code;
    }

    public String getSales_organization() {
        return sales_organization;
    }

    public void setSales_organization(String sales_organization) {
        this.sales_organization = sales_organization;
    }

    public String getSales_channel() {
        return sales_channel;
    }

    public void setSales_channel(String sales_channel) {
        this.sales_channel = sales_channel;
    }

    public String getSales_channel2() {
        return sales_channel2;
    }

    public void setSales_channel2(String sales_channel2) {
        this.sales_channel2 = sales_channel2;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public Date getActive_date_begin() {
        return active_date_begin;
    }

    public void setActive_date_begin(Date active_date_begin) {
        this.active_date_begin = active_date_begin;
    }

    public Date getActive_date_end() {
        return active_date_end;
    }

    public void setActive_date_end(Date active_date_end) {
        this.active_date_end = active_date_end;
    }

    public Date getLast_upd() {
        return last_upd;
    }

    public void setLast_upd(Date last_upd) {
        this.last_upd = last_upd;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCombine_type() {
        return combine_type;
    }

    public void setCombine_type(String combine_type) {
        this.combine_type = combine_type;
    }

    public String getPut_methods() {
        return put_methods;
    }

    public void setPut_methods(String put_methods) {
        this.put_methods = put_methods;
    }

    @Override
    public String toString() {
        return "BatchsaleTemplateItem{" +
                "row_id='" + row_id + '\'' +
                ", material_code='" + material_code + '\'' +
                ", sales_organization='" + sales_organization + '\'' +
                ", sales_channel='" + sales_channel + '\'' +
                ", sales_channel2='" + sales_channel2 + '\'' +
                ", customer_code='" + customer_code + '\'' +
                ", active_date_begin=" + active_date_begin +
                ", active_date_end=" + active_date_end +
                ", last_upd=" + last_upd +
                ", delete_flag='" + delete_flag + '\'' +
                ", department='" + department + '\'' +
                ", combine_type='" + combine_type + '\'' +
                ", put_methods='" + put_methods + '\'' +
                '}';
    }
}
