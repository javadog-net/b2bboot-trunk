/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.Cart;
import com.jhmis.modules.shop.entity.Store;

import java.util.List;

/**
 * 店铺管理MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface StoreMapper extends BaseMapper<Store> {
    Store selectByDealerId(String dealerId);
    List<Store> findDistinctStore(Cart cart);
    public List<Store> selectByIds(Cart cart);
}