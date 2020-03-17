package com.jhmis.common.wsClient.test;

import com.jhmis.common.wsClient.entity.MDMCustomer;
import com.jhmis.common.wsClient.service.WsClientList;

import java.util.List;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 14:40 2018/7/29
 * @Modified By
 */
public class main {
    public static void main(String[] args) {
        List<MDMCustomer> list = WsClientList.getMDMCustomersList("8700000235","青岛日日顺物流有限公司");
    }
}
