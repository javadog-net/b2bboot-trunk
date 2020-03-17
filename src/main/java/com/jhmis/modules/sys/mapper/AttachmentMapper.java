/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.sys.entity.Attachment;

/**
 * 附件表MAPPER接口
 * @author tity
 * @version 2018-07-06
 */
@MyBatisMapper
public interface AttachmentMapper extends BaseMapper<Attachment> {
	
}