/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.StoreGoodsPrice;

import java.util.List;

/**
 * 店铺商品价格MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface StoreGoodsPriceMapper extends BaseMapper<StoreGoodsPrice> {
     void deleteBySPGId(String storePriceGroupId);
}