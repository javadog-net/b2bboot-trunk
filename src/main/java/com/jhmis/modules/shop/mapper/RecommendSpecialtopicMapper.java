/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.RecommendCat;
import com.jhmis.modules.shop.entity.RecommendSpecialtopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 特别推荐表MAPPER接口
 * @author hdx
 * @version 2018-08-02
 */
@MyBatisMapper
public interface RecommendSpecialtopicMapper extends BaseMapper<RecommendSpecialtopic> {
    List<RecommendSpecialtopic> selectRcForApi(@Param(value="dictionaryId") String dictionaryId, @Param(value="cityid") String cityid);
}