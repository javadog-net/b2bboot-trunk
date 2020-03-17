/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.directpurchaser.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品表MAPPER接口
 * @author tity
 * @version 2018-07-23
 */
@MyBatisMapper
public interface DirectGoodsMapper extends BaseMapper<Goods> {
     Goods findByCode(@Param("code") String code);
     List<Goods> findNoPublishList(Goods goods);
     
     List<Goods> findPurchaserRelList(@Param("purchaserId") String purchaserId);
     
     List<Goods> findPurchaserRelListByGoods(Goods goods);

     Goods findPurchaserRelByGoods(Goods goods);
}