/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.mapper.shopmsg;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发布需求相关MAPPER接口
 * @author hdx
 * @version 2019-09-03
 */
@MyBatisMapper
public interface ShopMsgMapper extends BaseMapper<ShopMsg> {
    List<ShopMsg> findListOver(ShopMsg shopMsg);
    //经销商查看可抢单信息
    List<ShopMsg> dealerPreempInfo(ShopMsg shopMsg);
    //400hic回传信息
    List<ShopMsg> findReturnList(ShopMsg shopMsg);
}