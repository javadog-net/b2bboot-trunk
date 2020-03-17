/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserGoodsFavorites;

/**
 * 商品收藏MAPPER接口
 * @author hdx
 * @version 2018-08-16
 */
@MyBatisMapper
public interface PurchaserGoodsFavoritesMapper extends BaseMapper<PurchaserGoodsFavorites> {
    //查重
    public PurchaserGoodsFavorites checkAgain(String storeGoodsId,String purchaserAccountId);
    //取消
    public void cancel(String storeGoodsId,String purchaserAccountId);
    //根据id删除
    public void deleteById(String favoritesGoodsId);
}