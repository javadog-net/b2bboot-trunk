/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserStoreFavorites;

/**
 * 店铺收藏MAPPER接口
 * @author hdx
 * @version 2018-08-16
 */
@MyBatisMapper
public interface PurchaserStoreFavoritesMapper extends BaseMapper<PurchaserStoreFavorites> {
    //重复验证
    PurchaserStoreFavorites checkAgain(String storeId,String purchaserAccountId);
    //取消
     void cancel(String storeId,String purchaserAccountId);
    //根据id删除
     void deleteById(String favoritesStoreId);
}