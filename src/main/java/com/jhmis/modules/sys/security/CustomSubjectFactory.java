package com.jhmis.modules.sys.security;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 自定义SubjectFactory,JWT模式不需要Session
 */
public class CustomSubjectFactory extends DefaultWebSubjectFactory {
    private final DefaultSessionStorageEvaluator storageEvaluator;

    public CustomSubjectFactory(DefaultSessionStorageEvaluator storageEvaluator) {
        this.storageEvaluator = storageEvaluator;
    }

    @Override
    public Subject createSubject(SubjectContext context) {
        this.storageEvaluator.setSessionStorageEnabled(Boolean.TRUE.booleanValue());
        AuthenticationToken token = context.getAuthenticationToken();
        //判断token isInstanceOf jwtToken
        if(token instanceof JWTToken ){
            context.setSessionCreationEnabled(false);
            this.storageEvaluator.setSessionStorageEnabled(Boolean.FALSE.booleanValue());
        }
        return super.createSubject(context);
    }
}
