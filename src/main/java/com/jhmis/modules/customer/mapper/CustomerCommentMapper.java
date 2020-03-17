/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.CustomerComment;

/**
 * 客单评论MAPPER接口
 * @author tc
 * @version 2020-01-21
 */
@MyBatisMapper
public interface CustomerCommentMapper extends BaseMapper<CustomerComment> {
	
}