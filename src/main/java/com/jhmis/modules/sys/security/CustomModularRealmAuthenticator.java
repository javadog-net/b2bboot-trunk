package com.jhmis.modules.sys.security;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.JWTUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Description:全局shiro拦截分发realm
 */
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {
	@Override
	protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 登录类型
        String userType = Global.USER_TYPE_SYS;
        if(authenticationToken instanceof JWTToken ) {
            userType = JWTUtils.getUserType(((JWTToken)authenticationToken).getToken());
        } else if(authenticationToken instanceof UsernamePasswordToken ){
            userType = ((UsernamePasswordToken)authenticationToken).getUserType();
        }
        // 所有Realm
        Collection<Realm> realms = getRealms();

        // 登录类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            String realmName = realm.getName();
            if (realmName.equals(userType)) {
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1)
            return doSingleRealmAuthentication(typeRealms.iterator().next(), authenticationToken);
        else
            return doMultiRealmAuthentication(typeRealms, authenticationToken);
	}

}
