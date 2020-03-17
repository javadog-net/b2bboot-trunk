/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.ViewBusinessOpportunity;

import java.util.List;

/**
 * 项目履约视图MAPPER接口
 * @author h'd'x
 * @version 2020-02-13
 */
@MyBatisMapper
public interface ViewBusinessOpportunityMapper extends BaseMapper<ViewBusinessOpportunity> {
    public List<ViewBusinessOpportunity> findTaskList(ViewBusinessOpportunity viewBusinessOpportunity);
}