/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.dealer;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.service.TreeService;
import com.jhmis.modules.shop.entity.dealer.DealerMenu;
import com.jhmis.modules.shop.mapper.dealer.DealerMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商菜单管理Service
 * @author tity
 * @version 2018-07-25
 */
@Service
@Transactional(readOnly = true)
public class DealerMenuService extends TreeService<DealerMenuMapper, DealerMenu> {

	public DealerMenu get(String id) {
		return super.get(id);
	}
	
	public List<DealerMenu> findList(DealerMenu dealerMenu) {
		if (StringUtils.isNotBlank(dealerMenu.getParentIds())){
			dealerMenu.setParentIds(","+dealerMenu.getParentIds()+",");
		}
		return super.findList(dealerMenu);
	}

    public List<DealerMenu> findAllList(DealerMenu dealerMenu) {
        return this.mapper.findAllList(dealerMenu);
    }

	@Transactional(readOnly = false)
	public void save(DealerMenu dealerMenu) {
		super.save(dealerMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(DealerMenu dealerMenu) {
		// 解除菜单角色关联
		List<Object> mrlist =  this.mapper.execSelectSql(
				"SELECT distinct a.menu_id as id FROM dealer_role_menu a left join dealer_menu menu on a.menu_id = menu.id WHERE a.menu_id ='"
						+ dealerMenu.getId() + "' OR menu.parent_ids LIKE  '%," + dealerMenu.getId() + ",%'");
		for (Object mr : mrlist) {
			this.mapper.deleteMenuRole(mr.toString());
		}
		super.delete(dealerMenu);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(DealerMenu menu) {
		this.mapper.updateSort(menu);
	}
}