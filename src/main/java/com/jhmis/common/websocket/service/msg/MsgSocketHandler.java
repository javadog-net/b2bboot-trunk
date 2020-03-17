package com.jhmis.common.websocket.service.msg;

import com.jhmis.common.config.Global;
import com.jhmis.common.websocket.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;

public class MsgSocketHandler implements WebSocketHandler {

    private static final Logger logger;
    private static final ArrayList<WebSocketSession> purchasers;
    private static final ArrayList<WebSocketSession> dealers;

    static {
        purchasers = new ArrayList<>();
        dealers = new ArrayList<>();
        logger = LoggerFactory.getLogger(MsgSocketHandler.class);
    }

    //建立websocket连接时触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("connect to the websocket success......");
        String userType = (String) session.getAttributes().get(Constants.WEBSOCKET_USERTYPE);
        //String userId = (String) session.getAttributes().get(Constants.WEBSOCKET_LOGINID);
        if(Global.USER_TYPE_DEALER.equals(userType)){
            dealers.add(session);
        }else if(Global.USER_TYPE_PURCHASER.equals(userType)){
            purchasers.add(session);
        }
    }

    //接收js侧发送来的用户信息
    @SuppressWarnings("unused")
	@Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> socketMessage) throws Exception {
    	String message = socketMessage.getPayload().toString();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.debug("websocket connection closed......");
        String userType = (String) session.getAttributes().get(Constants.WEBSOCKET_USERTYPE);
        //String userId = (String) session.getAttributes().get(Constants.WEBSOCKET_LOGINID);
        if(session.isOpen()){
            session.close();
        }
        if(Global.USER_TYPE_DEALER.equals(userType)){
            dealers.remove(session);
        }else if(Global.USER_TYPE_PURCHASER.equals(userType)){
            purchasers.remove(session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        String userType = (String) session.getAttributes().get(Constants.WEBSOCKET_USERTYPE);
        //String userId = (String) session.getAttributes().get(Constants.WEBSOCKET_LOGINID);
        if(Global.USER_TYPE_DEALER.equals(userType)){
            dealers.remove(session);
        }else if(Global.USER_TYPE_PURCHASER.equals(userType)){
            purchasers.remove(session);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有采购商发送消息
     *
     * @param message
     */
    public void sendMessageToAllPurchasers( String message) {
        for (WebSocketSession purchaser : purchasers) {
            try {
                if (purchaser.isOpen()) {
                    purchaser.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给所有供应商商发送消息
     *
     * @param message
     */
    public void sendMessageToAlldealers( String message) {
        for (WebSocketSession dealer : dealers) {
            try {
                if (dealer.isOpen()) {
                    dealer.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给某个采购商发送消息(通过用户登录账号)
     *
     * @param loginName
     * @param message
     */
    public boolean sendMessageToPurchaserByName(String loginName, String message) {
    	boolean result = false;
        for (WebSocketSession purchaser : purchasers) {
            if (purchaser.getAttributes().get(Constants.WEBSOCKET_LOGINNAME).equals(loginName)) {//允许用户多个浏览器登录，每个浏览器页面都会收到用户信息
                try {
                    if (purchaser.isOpen()) {
                        purchaser.sendMessage(new TextMessage(message));
                        result = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;//注释掉此处意味着遍历该用户打开的所有页面并发送信息，否则只会向用户登录的第一个网页发送信息。
            }
        }
        return result;
    }

    /**
     * 给某个采购商发送消息(通过用户ID)
     *
     * @param userId
     * @param message
     */
    public boolean sendMessageToPurchaserById(String userId, String message) {
        boolean result = false;
        for (WebSocketSession purchaser : purchasers) {
            if (purchaser.getAttributes().get(Constants.WEBSOCKET_LOGINID).equals(userId)) {//允许用户多个浏览器登录，每个浏览器页面都会收到用户信息
                try {
                    if (purchaser.isOpen()) {
                        purchaser.sendMessage(new TextMessage(message));
                        result = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;//注释掉此处意味着遍历该用户打开的所有页面并发送信息，否则只会向用户登录的第一个网页发送信息。
            }
        }
        return result;
    }

    /**
     * 给某个供应商发送消息(通过用户登录账号)
     *
     * @param loginName
     * @param message
     */
    public boolean sendMessageToDealerByName(String loginName, String message) {
        boolean result = false;
        for (WebSocketSession dealer : dealers) {
            if (dealer.getAttributes().get(Constants.WEBSOCKET_LOGINNAME).equals(loginName)) {//允许用户多个浏览器登录，每个浏览器页面都会收到用户信息
                try {
                    if (dealer.isOpen()) {
                        dealer.sendMessage(new TextMessage(message));
                        result = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;//注释掉此处意味着遍历该用户打开的所有页面并发送信息，否则只会向用户登录的第一个网页发送信息。
            }
        }
        return result;
    }

    /**
     * 给某个供应商发送消息(通过用户ID)
     *
     * @param userId
     * @param message
     */
    public boolean sendMessageToDealerById(String userId, String message) {
        boolean result = false;
        for (WebSocketSession dealer : dealers) {
            if (dealer.getAttributes().get(Constants.WEBSOCKET_LOGINID).equals(userId)) {//允许用户多个浏览器登录，每个浏览器页面都会收到用户信息
                try {
                    if (dealer.isOpen()) {
                        dealer.sendMessage(new TextMessage(message));
                        result = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //break;//注释掉此处意味着遍历该用户打开的所有页面并发送信息，否则只会向用户登录的第一个网页发送信息。
            }
        }
        return result;
    }

}