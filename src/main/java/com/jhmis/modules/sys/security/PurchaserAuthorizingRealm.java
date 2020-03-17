package com.jhmis.modules.sys.security;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMenu;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * 采购商认证
 */
public class PurchaserAuthorizingRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return Global.USER_TYPE_PURCHASER;
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
    /**
     * 认证回调函数, 登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        String token = (String) authcToken.getCredentials();
        String userId = JWTUtils.getUserId(token);
        String userType = JWTUtils.getUserType(token);
        if (userId == null || userType == null) {
            throw new AuthenticationException("token invalid");
        }
        if (! JWTUtils.verify(token, userId, userType)) {
            throw new AuthenticationException("token verify error");
        }
        PurchaserAccount account = PurchaserUtils.get(userId);
        if (account==null) throw new AuthenticationException(Constants.ERROR_DESC_NO_USER);
        //同时下面构造的principal里面放入purchaser的相关数据
        //提交给AuthenticationInfo进行密码校验
        Principal principal = new Principal(userId,account.getLoginName(),account.getRealName(),false,Global.USER_TYPE_PURCHASER,token);
        return new SimpleAuthenticationInfo(principal, token, getName());
    }
    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object availablePrincipal = getAvailablePrincipal(principals);
        if(!(availablePrincipal instanceof Principal)){
            return null;
        }
        Principal principal = (Principal)availablePrincipal;
        //不属于自身用户类型的不处理
        if(!getName().equals(principal.getUserType())){
            return null;
        }

        String userId = principal.getId();
        PurchaserAccount account = PurchaserUtils.get(userId);
        if (account != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<PurchaserMenu> list = PurchaserUtils.getMenuList();
            for (PurchaserMenu menu : list){
                if (StringUtils.isNotBlank(menu.getPermission())){
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(),",")){
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加供应商权限
            info.addRole("purchaser");
            // 添加用户角色信息
            /*for (PurchaserRole role : account.getRoleList()){
                info.addRole(role.getName());
            }*/
            return info;
        } else {
            return null;
        }
    }

}
