/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.InfoProduct;

/**
 * 文章关联产品MAPPER接口
 * @author lydia
 * @version 2019-10-14
 */
@MyBatisMapper
public interface InfoProductMapper extends BaseMapper<InfoProduct> {
	
}