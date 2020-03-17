/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.TrancheProduct;

/**
 * 一期商品数据MAPPER接口
 * @author hdx
 * @version 2018-07-29
 */
@MyBatisMapper
public interface TrancheProductMapper extends BaseMapper<TrancheProduct> {
	 public int update(TrancheProduct trancheProduct);
}