/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.Goods;

import java.util.List;

/**
 * 商品表MAPPER接口
 * @author tity
 * @version 2018-07-23
 */
@MyBatisMapper
public interface GoodsMapper extends BaseMapper<Goods> {
     Goods findByCode(String code);
     List<Goods> findNoPublishList(Goods goods);
     
     /**
      * 通过产品编码集合获取产品集合
      * @param goods
      * @return
      */
     List<Goods> findByCodeList(Goods goods);

}