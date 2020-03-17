/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxMeetingSchedule;

import java.util.List;

/**
 * 会议日程表MAPPER接口
 * @author lvyangzhuo
 * @version 2018-11-23
 */
@MyBatisMapper
public interface WxMeetingScheduleMapper extends BaseMapper<WxMeetingSchedule> {

    /**
     * 查询会议日程列表
     * @param wxMeetingSchedule
     * @return
     */
    List<WxMeetingSchedule> findMeetingScheduleList(WxMeetingSchedule wxMeetingSchedule);
}