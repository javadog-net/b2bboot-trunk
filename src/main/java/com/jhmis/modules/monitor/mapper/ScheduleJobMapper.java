/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.monitor.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.monitor.entity.ScheduleJob;

/**
 * 定时任务MAPPER接口
 * @author lgf
 * @version 2017-02-04
 */
@MyBatisMapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {

	
}