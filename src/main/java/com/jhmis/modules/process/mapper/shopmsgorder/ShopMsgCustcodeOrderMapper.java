/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.mapper.shopmsgorder;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 经销商订单表MAPPER接口
 * @author hdx
 * @version 2019-09-26
 */
@MyBatisMapper
public interface ShopMsgCustcodeOrderMapper extends BaseMapper<ShopMsgCustcodeOrder> {
    List<Integer> dealerOrderAllStatus(@Param("dealerCode") String dealerCode);
    Integer dealerOrderUnderTake(@Param("dealerCode") String dealerCode);
    List<Integer> findbycustcodeAndStatus(String companyCode);
}