/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.OrderPay;
import com.jhmis.modules.shop.entity.Orders;

import java.util.List;

/**
 * 订单管理MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface OrdersMapper extends BaseMapper<Orders> {
    /**
     * 更新订单的状态
     * @param orders
     */
    public void updateOrdersState(Orders orders);

    /**
     * 已超时未支付的订单信息
     * @param orders
     * @return
     */
    public List<Orders> selectUnPaid(Orders orders);

    /**
     *
     * @param orders
     * @return
     */
    public int findByOrderState(Orders orders);

    public int countDistinctPaySn(Orders orders);

    public  List<Orders> getOrdersList(Orders orders);

    /**
     * 转账失败的列表
     * @param orders
     * @return
     */
    public List<Orders> findRoyaltyList(Orders orders);

    /**
     * 查找需要自动确认收货的订单信息
     * @param orders
     * @return
     */
    public List<Orders> findConfirmOrders(Orders orders);


}