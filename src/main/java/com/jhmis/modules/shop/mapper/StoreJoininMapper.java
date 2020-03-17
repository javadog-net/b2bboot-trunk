/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.StoreJoinin;

/**
 * 店铺申请MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface StoreJoininMapper extends BaseMapper<StoreJoinin> {
    public void updateAuditState(StoreJoinin entiry);

    /**
     * 通过经销商id获取申请记录
     * @param dealerId
     * @return
     */
    public StoreJoinin getByDealerId(String dealerId);
}