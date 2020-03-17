package com.jhmis.common.websocket.config;

import com.jhmis.common.websocket.service.msg.MsgSocketHandler;
import com.jhmis.common.websocket.service.msg.MsgSocketHandshakeInterceptor;
import com.jhmis.common.websocket.service.onchat.LayIMSocketHandler;
import com.jhmis.common.websocket.service.onchat.LayIMSocketHandshakeInterceptor;
import com.jhmis.common.websocket.service.system.SystemInfoSocketHandler;
import com.jhmis.common.websocket.service.system.SystemInfoSocketHandshakeInterceptor;
import com.jhmis.common.websocket.service.wx.HandShake;
import com.jhmis.common.websocket.service.wx.MyWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket 
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    	//注册layIM socket服务
        registry.addHandler(layImSocketHandler(),"/layIMSocketServer").setAllowedOrigins("*");
        registry.addHandler(layImSocketHandler(), "/sockjs/layIMSocketServer").setAllowedOrigins("*")
                .withSockJS();
        
      //注册 系统通知socket服务
        registry.addHandler(systemInfoSocketHandler(),"/systemInfoSocketServer").setAllowedOrigins("*");
        registry.addHandler(systemInfoSocketHandler(), "/sockjs/systemInfoSocketServer").setAllowedOrigins("*")
                .withSockJS();
        //注册 采购商，供应商 通知socket服务
        registry.addHandler(msgSocketHandler(),"/msgServer").setAllowedOrigins("*");
        registry.addHandler(msgSocketHandler(), "/sockjs/msgServer").setAllowedOrigins("*")
                .withSockJS();

        //小程序操作
        registry.addHandler(myHandler(), "/ws").addInterceptors(new HandShake()).setAllowedOrigins("*");
        registry.addHandler(myHandler(), "/ws/sockjs").addInterceptors(new HandShake()).setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public WebSocketHandler layImSocketHandler(){
        return new LayIMSocketHandler();
    }

    @Bean
    public WebSocketHandler systemInfoSocketHandler(){
        return new SystemInfoSocketHandler();
    }

    @Bean
    public WebSocketHandler msgSocketHandler(){
        return new MsgSocketHandler();
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }

}