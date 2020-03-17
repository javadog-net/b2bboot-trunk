/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.mapper;

import java.util.List;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.sys.entity.DataRule;
import com.jhmis.modules.sys.entity.User;

/**
 * 数据权限MAPPER接口
 * @author lgf
 * @version 2017-04-02
 */
@MyBatisMapper
public interface DataRuleMapper extends BaseMapper<DataRule> {

	public void deleteRoleDataRule(DataRule dataRule);
	
	public List<DataRule> findByUserId(User user);
}