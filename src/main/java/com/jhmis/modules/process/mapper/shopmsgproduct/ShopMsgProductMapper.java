/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.mapper.shopmsgproduct;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.process.entity.shopmsgproduct.ShopMsgProduct;

/**
 * 需求产品子表MAPPER接口
 * @author hdx
 * @version 2019-09-23
 */
@MyBatisMapper
public interface ShopMsgProductMapper extends BaseMapper<ShopMsgProduct> {
    public void deleteByMsgId(String msgId);
	
}