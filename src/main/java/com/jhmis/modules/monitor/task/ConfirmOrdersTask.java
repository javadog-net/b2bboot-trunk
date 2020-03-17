package com.jhmis.modules.monitor.task;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.SpringContextHolder;
import com.jhmis.modules.monitor.entity.Task;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class ConfirmOrdersTask extends Task {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(ConfirmOrdersTask.class);
    @Override
    public void run() {
        OrdersService ordersService  = SpringContextHolder.getBean(OrdersService.class);
        if(ordersService != null){
            Orders orders = new Orders();
            //自动确认收货时间
            orders.setInterval(15*24);
            List<Orders> ordersList = ordersService.findConfirmOrders(orders);
            if(ordersList!= null && ordersList.size() > 0){
                for(Orders order:ordersList){
                    order.setOrderState(Global.ORDER_STATE_RECEIVED);
                    orders.setDeliveryTime(new Date());
                    orders.setConfirmReceiver("系统触发");
                    ordersService.updateOrdersState(orders);
                    String responseStr = ordersService.tradeSettle(orders);
                    logger.info(responseStr);
                }
            }

        }
    }
}
