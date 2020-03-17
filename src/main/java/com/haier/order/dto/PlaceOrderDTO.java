package com.haier.order.dto;


import com.jhmis.modules.cart.dto.CartManagerDTO;

import java.util.List;

/**
 * 下单传参辅助model
 * @author wzh
 * @data 2017/5/11
 */
public class PlaceOrderDTO {

    /**
     * 用户uuid
     */
    private String customerUuid;
    private String customerName;
    /**
     * 登录人88码
     */
    private String customerCode;

    /**
     * 购物车信息
     */
    private List<CartManagerDTO> cartManagerModels;
    /**
     * 用户收货地址uuid
     */
    private OrderAddressDTO orderAddressDTO;
    /**
     * 用户收货地址uuid
     */
    private String customerAddressUuid;
    /**
     * 订单来源
     */
    private String fromType;

    private double totalMoney;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getCustomerAddressUuid() {
        return customerAddressUuid;
    }

    public void setCustomerAddressUuid(String customerAddressUuid) {
        this.customerAddressUuid = customerAddressUuid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public List<CartManagerDTO> getCartManagerModels() {
        return cartManagerModels;
    }

    public void setCartManagerModels(List<CartManagerDTO> cartManagerModels) {
        this.cartManagerModels = cartManagerModels;
    }

    public OrderAddressDTO getOrderAddressDTO() {
        return orderAddressDTO;
    }

    public void setOrderAddressDTO(OrderAddressDTO orderAddressDTO) {
        this.orderAddressDTO = orderAddressDTO;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }
}

