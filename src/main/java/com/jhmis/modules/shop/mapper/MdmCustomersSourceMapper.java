/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.MdmCustomersSource;

/**
 * 直采专区mdm主数据源MAPPER接口
 * @author hdx
 * @version 2019-03-26
 */
@MyBatisMapper
public interface MdmCustomersSourceMapper extends BaseMapper<MdmCustomersSource> {
	/**
     * 根据送达方88码获取销售工厂和销售组织
     * @param loginName
     * @return
     */
    public MdmCustomersSource getByCusCode(@Param("cusCode") String cusCode);
}