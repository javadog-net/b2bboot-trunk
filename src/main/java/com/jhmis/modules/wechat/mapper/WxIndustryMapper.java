/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxFriendGroup;
import com.jhmis.modules.wechat.entity.WxIndustry;

/**
 * 行业表MAPPER接口
 * @author TC
 * @version 2018-11-22
 */
@MyBatisMapper
public interface WxIndustryMapper extends BaseMapper<WxIndustry> {
	public WxIndustry find(String id);
	public List<WxIndustry> findLists(WxIndustry wxIndustry);
}