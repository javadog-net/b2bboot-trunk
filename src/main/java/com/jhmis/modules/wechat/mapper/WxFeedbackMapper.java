/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxFeedback;

/**
 * 反馈信息MAPPER接口
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@MyBatisMapper
public interface WxFeedbackMapper extends BaseMapper<WxFeedback> {
	/**
	 * 创建新的反馈信息
	 * @param wxFeedback
	 */
	void add(WxFeedback wxFeedback);
}