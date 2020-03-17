/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserFavorites;

/**
 * 商品店铺收藏管理MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface PurchaserFavoritesMapper extends BaseMapper<PurchaserFavorites> {
    void deleteById(String id);
}