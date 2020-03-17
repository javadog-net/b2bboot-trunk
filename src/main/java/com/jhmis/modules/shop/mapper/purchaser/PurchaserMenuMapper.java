/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.TreeMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购商菜单MAPPER接口
 * @author tity
 * @version 2018-07-25
 */
@MyBatisMapper
public interface PurchaserMenuMapper extends TreeMapper<PurchaserMenu> {
    /**
     * 是否有子菜单
     * @param parentId
     * @return
     */
    public Boolean hasChildren(String parentId);

    /**
     * 查询所有
     * @param entity
     * @return
     */
    public List<PurchaserMenu> findAllList(PurchaserMenu entity);

    /**
     * 根据采购商账号id查询数据列表
     * @param entity
     * @return
     */
    public List<PurchaserMenu> findByAccountId(PurchaserMenu entity);

    /**
     * 删除权限中的菜单
     * @param menu_id
     */
    public void deleteMenuRole(@Param(value = "menu_id") String menu_id);
}