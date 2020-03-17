/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.TOrderDetail;

import java.util.List;

/**
 * 巨商会订单信息详情推送表MAPPER接口
 * @author hdx
 * @version 2020-01-20
 */
@MyBatisMapper
public interface TOrderDetailMapper extends BaseMapper<TOrderDetail> {
    public List<TOrderDetail> findTaskList(TOrderDetail tOrderDetail);
}