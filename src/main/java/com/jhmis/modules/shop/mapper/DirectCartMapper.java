/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.Cart;
import com.jhmis.modules.shop.entity.DirectCart;

import java.util.List;

/**
 * 直采购物车MAPPER接口
 * @author hdx
 * @version 2019-03-27
 */
@MyBatisMapper
public interface DirectCartMapper extends BaseMapper<DirectCart> {
    /**
     * 获取购物车中的产品数量（件数）
     * @param cart
     * @return
     */
    public int getCartCount(DirectCart cart);


    public List<DirectCart> getGroupProduct(String dealerId);
}