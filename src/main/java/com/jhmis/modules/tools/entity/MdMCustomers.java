package com.jhmis.modules.tools.entity;
/**
 * @Author：hdx
 * @Description: MdM供应商相关信息
 * @Date: Created in 16:50 2018/7/22
 * @Modified By
 */
public class MdMCustomers {
    private String cuscode;
    private String cusname;
    private String kind;
    private String kindname;
    private String street;
    private String post;
    private String linkman;
    private String tel;
    private String fax;
    private String e_mail;
    private String datasource;

    public String getCuscode() {
        return cuscode;
    }

    public void setCuscode(String cuscode) {
        this.cuscode = cuscode;
    }

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
}
