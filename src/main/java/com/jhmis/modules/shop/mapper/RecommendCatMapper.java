/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.RecommendCat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类推荐表MAPPER接口
 * @author hdx
 * @version 2018-08-02
 */
@MyBatisMapper
public interface RecommendCatMapper extends BaseMapper<RecommendCat> {
    List<RecommendCat> selectRcForApi(@Param(value="categoryId") String categoryId, @Param(value="cityid") String cityid);
}