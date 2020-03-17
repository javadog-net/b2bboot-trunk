/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.DirectGoodsFavorites;
import com.jhmis.modules.shop.entity.purchaser.PurchaserGoodsFavorites;

/**
 * 直采MAPPER接口
 * @author tc
 * @version 2019-03-26
 */
@MyBatisMapper
public interface DirectGoodsFavoritesMapper extends BaseMapper<DirectGoodsFavorites> {

	 //查重
    public DirectGoodsFavorites checkAgain(String storeGoodsId,String purchaserAccountId);
    //取消
    public void cancel(String storeGoodsId,String purchaserAccountId);
    //根据id删除
    public void deleteById(String favoritesGoodsId);	
}