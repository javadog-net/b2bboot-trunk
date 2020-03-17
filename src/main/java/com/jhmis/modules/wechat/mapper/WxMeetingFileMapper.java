/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxMeetingFile;

/**
 * 会议附件上传MAPPER接口
 * @author tc
 * @version 2019-03-20
 */
@MyBatisMapper
public interface WxMeetingFileMapper extends BaseMapper<WxMeetingFile> {
	
}