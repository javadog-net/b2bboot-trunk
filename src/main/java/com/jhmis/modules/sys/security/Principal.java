package com.jhmis.modules.sys.security;

import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;

import java.io.Serializable;

/**
 * 授权用户信息
 */
public class Principal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // 编号
    private String loginName; // 登录名
    private String name; // 姓名
    private boolean mobileLogin; // 是否手机登录
    private String userType;    //用户类型
    private String token;

    public Principal() {

    }

    public Principal(User user, boolean mobileLogin) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.name = user.getName();
        this.mobileLogin = mobileLogin;
    }

    public Principal(User user, boolean mobileLogin, String userType) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.name = user.getName();
        this.mobileLogin = mobileLogin;
        this.userType = userType;
    }

    public Principal(User user, boolean mobileLogin, String userType, String token) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.name = user.getName();
        this.mobileLogin = mobileLogin;
        this.token = token;
    }

    public Principal(String id, String loginName, String name, boolean mobileLogin, String userType, String token) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.mobileLogin = mobileLogin;
        this.userType = userType;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

    public void setMobileLogin(boolean mobileLogin) {
        this.mobileLogin = mobileLogin;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取SESSIONID
     */
    public String getSessionid() {
        try{
            return (String) UserUtils.getSession().getId();
        }catch (Exception e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return id;
    }

}
