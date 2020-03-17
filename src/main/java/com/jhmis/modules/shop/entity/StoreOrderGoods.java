package com.jhmis.modules.shop.entity;

import com.jhmis.core.persistence.DataEntity;

import java.util.List;

/**
 * 订单中的店铺商品信息
 */
public class StoreOrderGoods extends DataEntity<StoreOrderGoods> {
    private static final long serialVersionUID = 1L;
    private String storeId;		// 店铺ID
    private Double goodsAmount;		// 商品总价格
    private Double orderAmount;		// 订单总价格
    private List<OrderGoods> orderGoodsList; //订单商品信息

    public String getStoreId() {
        return storeId;
    }
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }


    public List<OrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }
}
