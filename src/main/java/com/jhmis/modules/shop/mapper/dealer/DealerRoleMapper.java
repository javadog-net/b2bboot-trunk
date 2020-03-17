/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.dealer;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.dealer.DealerRole;

/**
 * 供应商角色MAPPER接口
 * @author tity
 * @version 2018-07-25
 */
@MyBatisMapper
public interface DealerRoleMapper extends BaseMapper<DealerRole> {
    /**
     * 通过名字获取，用于校验重复
     * @param role
     * @return
     */
    public DealerRole getByName(DealerRole role);
    /**
     * 删除角色对应的菜单关系
     * @param role
     * @return
     */
    public int deleteRoleMenu(DealerRole role);

    /**
     * 插入角色对应的菜单关系
     * 使用了role里面的menuList
     * @param role
     * @return
     */
    public int insertRoleMenu(DealerRole role);
}