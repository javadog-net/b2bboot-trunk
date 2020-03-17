/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.tools.entity.SysDataSource;

/**
 * 多数据源MAPPER接口
 * @author tity
 * @version 2017-07-27
 */
@MyBatisMapper
public interface SysDataSourceMapper extends BaseMapper<SysDataSource> {

    public SysDataSource getByEnname(String enname);

}