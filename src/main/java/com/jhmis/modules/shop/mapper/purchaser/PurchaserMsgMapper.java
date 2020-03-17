/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购商消息列表MAPPER接口
 * @author tity
 * @version 2018-08-20
 */
@MyBatisMapper
public interface PurchaserMsgMapper extends BaseMapper<PurchaserMsg> {
    public int isRead(@Param("msgId") String msgId, @Param("accountId") String accountId);

    public int unReadCount(@Param("type") String type, @Param("accountId") String accountId);

    public int readMsg(@Param("msgId") String msgId, @Param("accountId") String accountId);

    public int deleteMsgRead(@Param("msgId") String msgId);

    /**
     * 查找系统消息
     * @param entity
     * @return
     */
    public List<PurchaserMsg> findSysMsgList(PurchaserMsg entity);
}