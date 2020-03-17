/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.euc.entity.EucMsg;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * euc系统相关需求MAPPER接口
 * @author hdx
 * @version 2019-11-08
 */
@MyBatisMapper
public interface EucMsgMapper extends BaseMapper<EucMsg> {
    List<EucMsg> findListOver(EucMsg eucMsg);
    EucMsg getById(String id);

    /**
     * 更新是否在抢单池中可见的标志
     * @param eucMsg
     */
    void updateIsValid(EucMsg eucMsg);


    /**
     * 更新是否在抢单池中可见的标志
     * @param eucMsg
     */
    List<EucMsg> findPuncture(EucMsg eucMsg);


    /**
     * 获取所有未被共享的商机单
     * @param
     */
    List<EucMsg> findAllNoShare();

    /**
     * 获取单一需求单
     * @param
     */
    EucMsg getOwnById(String id);

}