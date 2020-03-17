/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.Cart;

import java.util.List;

/**
 * 购物车MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface CartMapper extends BaseMapper<Cart> {

    /**
     * 获取购物车中的产品数量（件数）
     * @param cart
     * @return
     */
    public int getCartCount(Cart cart);


}