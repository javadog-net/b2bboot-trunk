/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.mapper.shopevaluate;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.process.entity.shopevaluate.ShopMsgEvaluate;

import java.util.List;

/**
 * 评价相关MAPPER接口
 * @author hdx
 * @version 2019-11-06
 */
@MyBatisMapper
public interface ShopMsgEvaluateMapper extends BaseMapper<ShopMsgEvaluate> {
    List<ShopMsgEvaluate> findListGroup(ShopMsgEvaluate shopMsgEvaluate);

}