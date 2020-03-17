/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.BaseEntity;
import com.jhmis.modules.sys.entity.DataRule;
import com.jhmis.modules.sys.utils.UserUtils;

/**
 * Service基类
 * @author jhmis
 * @version 2017-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	
	/**
	 * 数据范围过滤
	 * @param entity 当前过滤的实体类
	 */
	public static void dataRuleFilter(BaseEntity<?> entity) {

		entity.setCurrentUser(UserUtils.getUser());
		List<DataRule> dataRuleList = UserUtils.getDataRuleList();
		
		// 如果是超级管理员，则不过滤数据
		if (dataRuleList.size() == 0) {
			return;
		}

		// 数据范围
		StringBuilder sqlString = new StringBuilder();

		
			for(DataRule dataRule : dataRuleList){
				if(entity.getClass().getSimpleName().equals(dataRule.getClassName())){
					sqlString.append(dataRule.getDataScopeSql());
				}
				
			}

		entity.setDataScope(sqlString.toString());
		
	}

}
