package com.jhmis.modules.monitor.task;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.SpringContextHolder;
import com.jhmis.modules.monitor.entity.Task;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.service.OrdersService;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;
import java.util.List;

@DisallowConcurrentExecution
public class OrderUnPaidCancelTask extends Task {

    @Override
    public void run() {
         OrdersService ordersService  = SpringContextHolder.getBean(OrdersService.class);
         if(ordersService != null){
             Orders orders = new Orders();
             orders.setInterval(2);
             List<Orders> ordersList = ordersService.selectUnPaid(orders);
             if(ordersList!= null && ordersList.size() > 0){
                 for(Orders order:ordersList){
                     order.setOrderState(Global.ORDER_STATE_CANCEL);
                     orders.setCancelTime(new Date());
                     order.setCanceler("定时任务取消");
                     ordersService.cancleOrder(order);
                 }
             }

         }
    }
}
