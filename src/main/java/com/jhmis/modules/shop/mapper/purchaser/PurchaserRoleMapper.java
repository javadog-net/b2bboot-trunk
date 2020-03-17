/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserRole;

/**
 * 采购商角色MAPPER接口
 * @author tity
 * @version 2018-07-25
 */
@MyBatisMapper
public interface PurchaserRoleMapper extends BaseMapper<PurchaserRole> {
    /**
     * 通过名字获取，用于校验重复
     * @param role
     * @return
     */
    public PurchaserRole getByName(PurchaserRole role);
    /**
     * 删除角色对应的菜单关系
     * @param role
     * @return
     */
    public int deleteRoleMenu(PurchaserRole role);

    /**
     * 插入角色对应的菜单关系
     * 使用了role里面的menuList
     * @param role
     * @return
     */
    public int insertRoleMenu(PurchaserRole role);
}