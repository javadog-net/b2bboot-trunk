/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.CmsIslike;

/**
 * info信息  是否喜欢 MAPPER接口
 * @author tc
 * @version 2019-11-07
 */
@MyBatisMapper
public interface CmsIslikeMapper extends BaseMapper<CmsIslike> {

    Integer selectCountById(String id);
}