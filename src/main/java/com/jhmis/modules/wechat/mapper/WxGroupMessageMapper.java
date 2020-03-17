/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxGroupMessage;

/**
 * 群聊信息MAPPER接口
 * @author hdx
 * @version 2018-12-16
 */
@MyBatisMapper
public interface WxGroupMessageMapper extends BaseMapper<WxGroupMessage> {
	int messageNumber(String groupId);
}