package com.jhmis.modules.cms.utils;

/**
 * @author lydia
 * @date 2019/9/23 16:48
 */
public enum CmsEnum {
    PC("pc","pc导航"),
    MOBILE("mobile","手机导航"),
    TEMPLATE("default","默认模板"),
    UP("up","升序"),
    DOWN("down","升序"),
    PARENTIDSNULL("0,","空"),
    CATEGORY("0","栏目"),
    INFO("1","内容"),
    LINKCATEGORY("1","链接到栏目"),
    LINKCONTENT("2","链接到内容"),
    INDEX_TEMPLATE("index.html","首页模板名称"),
    HTML_SELECTED("selected","静态化选中"),
    HTML_ALL("all","静态化全部"),
    HTML_QUARTZ("allstatic","全站静态化");

    private  String code;
    private String descp;

    private CmsEnum(String code, String descp){
        this.code = code;
        this.descp = descp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
