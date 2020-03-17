/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.TreeMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.shop.entity.GoodsCategory;

import java.util.List;

/**
 * 商品管理MAPPER接口
 * @author tity
 * @version 2018-07-23
 */
@MyBatisMapper
public interface GoodsCategoryMapper extends TreeMapper<GoodsCategory> {
    public List<GoodsCategory> findStoreAllCat(String storeId);

    void updatecategoryurl(GoodsCategory category);
}