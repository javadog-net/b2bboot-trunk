package com.jhmis.common.websocket.service.msg;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.websocket.utils.Constants;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class MsgSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static Logger logger = LoggerFactory.getLogger(MsgSocketHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object
            > attributes) throws Exception {
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = servletRequest.getServletRequest().getParameter("token");
            if (StringUtils.isNotBlank(token)) {
                //用户认证
                String userId = JWTUtils.getUserId(token);
                String userType = JWTUtils.getUserType(token);
                if (JWTUtils.verify(token, userId, userType)) {
                    if(Global.USER_TYPE_DEALER.equals(userType)){
                        DealerAccount account = DealerUtils.get(userId);
                        if(account != null){
                            attributes.put(Constants.WEBSOCKET_USERTYPE, userType);
                            attributes.put(Constants.WEBSOCKET_LOGINID, userId);
                            attributes.put(Constants.WEBSOCKET_LOGINNAME, account.getLoginName());
                            return true;
                        }
                    } else if(Global.USER_TYPE_PURCHASER.equals(userType)){
                        PurchaserAccount account = PurchaserUtils.get(userId);
                        if(account != null){
                            attributes.put(Constants.WEBSOCKET_USERTYPE, userType);
                            attributes.put(Constants.WEBSOCKET_LOGINID, userId);
                            attributes.put(Constants.WEBSOCKET_LOGINNAME, account.getLoginName());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}