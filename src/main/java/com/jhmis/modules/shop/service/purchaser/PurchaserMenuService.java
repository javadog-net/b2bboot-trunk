/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.service.TreeService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMenu;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购商菜单Service
 * @author tity
 * @version 2018-07-25
 */
@Service
@Transactional(readOnly = true)
public class PurchaserMenuService extends TreeService<PurchaserMenuMapper, PurchaserMenu> {

	public PurchaserMenu get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserMenu> findList(PurchaserMenu purchaserMenu) {
		if (StringUtils.isNotBlank(purchaserMenu.getParentIds())){
			purchaserMenu.setParentIds(","+purchaserMenu.getParentIds()+",");
		}
		return super.findList(purchaserMenu);
	}

    public List<PurchaserMenu> findAllList(PurchaserMenu purchaserMenu) {
        return this.mapper.findAllList(purchaserMenu);
    }

	@Transactional(readOnly = false)
	public void save(PurchaserMenu purchaserMenu) {
		super.save(purchaserMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserMenu purchaserMenu) {
        // 解除菜单角色关联
        List<Object> mrlist =  this.mapper.execSelectSql(
                "SELECT distinct a.menu_id as id FROM purchaser_role_menu a left join purchaser_menu menu on a.menu_id = menu.id WHERE a.menu_id ='"
                        + purchaserMenu.getId() + "' OR menu.parent_ids LIKE  '%," + purchaserMenu.getId() + ",%'");
        for (Object mr : mrlist) {
            this.mapper.deleteMenuRole(mr.toString());
        }
        super.delete(purchaserMenu);
	}

    @Transactional(readOnly = false)
    public void updateMenuSort(PurchaserMenu menu) {
        this.mapper.updateSort(menu);
    }
	
}