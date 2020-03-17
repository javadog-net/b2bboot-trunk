/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxMessageRecord;

/**
 * 短信履历MAPPER接口
 * @author hdx
 * @version 2019-02-15
 */
@MyBatisMapper
public interface WxMessageRecordMapper extends BaseMapper<WxMessageRecord> {
	
}