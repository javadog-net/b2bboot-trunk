/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.CmsModelField;

import java.util.List;

/**
 * 模块字段管理MAPPER接口
 * @author lydia
 * @version 2019-08-30
 */
@MyBatisMapper
public interface CmsModelFieldMapper extends BaseMapper<CmsModelField> {
    /**
     * 根据
     * @param modelId
     * @return
     */
    public List<CmsModelField> findByModelId(String modelId);
	
}