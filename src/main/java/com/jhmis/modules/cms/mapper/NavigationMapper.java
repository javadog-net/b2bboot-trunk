/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.TreeMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.Navigation;

import java.util.List;

/**
 * 导航管理MAPPER接口
 * @author lydia
 * @version 2019-09-05
 */
@MyBatisMapper
public interface NavigationMapper extends TreeMapper<Navigation> {
     List<Object> testNa();

    /**
     * 导航排序
     * @param navigation
     * @return
     */
    List<Navigation> navigationSort(Navigation navigation);

    /**
     *
     * @param navigation
     * @return
     */
    int deleteByLogic(Navigation navigation);
}