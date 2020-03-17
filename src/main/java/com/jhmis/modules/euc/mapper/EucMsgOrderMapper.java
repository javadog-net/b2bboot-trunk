/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.euc.entity.EucMsgOrder;

import java.util.List;

/**
 * euc订单MAPPER接口
 * @author hdx
 * @version 2019-12-25
 */
@MyBatisMapper
public interface EucMsgOrderMapper extends BaseMapper<EucMsgOrder> {
    List<Integer>  dealerOrderAllStatus(String dealerCode);
    List<EucMsgOrder> findListOver(EucMsgOrder eucMsgOrder);

    /**
     * 保存或编辑申诉信息
     * @param eucMsgOrder
     */
    void updateAppealInfo(EucMsgOrder eucMsgOrder);
}