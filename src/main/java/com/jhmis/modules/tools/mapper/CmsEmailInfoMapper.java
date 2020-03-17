/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.tools.entity.CmsEmailInfo;

/**
 * 邮件MAPPER接口
 * @author tc
 * @version 2019-09-03
 */
@MyBatisMapper
public interface CmsEmailInfoMapper extends BaseMapper<CmsEmailInfo> {

	void updateIsSend(CmsEmailInfo cmsEmailInfo);
	
}