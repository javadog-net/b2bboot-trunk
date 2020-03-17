/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.dealer;

import com.jhmis.core.persistence.TreeMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.dealer.DealerMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商菜单管理MAPPER接口
 * @author tity
 * @version 2018-07-25
 */
@MyBatisMapper
public interface DealerMenuMapper extends TreeMapper<DealerMenu> {

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
    public List<DealerMenu> findAllList(DealerMenu entity);
    /**
     * 根据供应商账号id查询数据列表
     * @param entity
     * @return
     */
    public List<DealerMenu> findByAccountId(DealerMenu entity);

    /**
     * 删除权限中的菜单
     * @param menu_id
     */
    public void deleteMenuRole(@Param(value = "menu_id") String menu_id);
}