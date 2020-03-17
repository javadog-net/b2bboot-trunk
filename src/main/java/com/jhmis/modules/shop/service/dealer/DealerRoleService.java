/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.dealer;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.dealer.DealerRole;
import com.jhmis.modules.shop.mapper.dealer.DealerRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 供应商角色Service
 * @author tity
 * @version 2018-07-25
 */
@Service
@Transactional(readOnly = true)
public class DealerRoleService extends CrudService<DealerRoleMapper, DealerRole> {

	public DealerRole get(String id) {
		return super.get(id);
	}
	
	public List<DealerRole> findList(DealerRole dealerRole) {
		return super.findList(dealerRole);
	}
	
	public Page<DealerRole> findPage(Page<DealerRole> page, DealerRole dealerRole) {
		return super.findPage(page, dealerRole);
	}

    public List<DealerRole> findAllList(DealerRole dealerRole) {
        return this.mapper.findAllList(dealerRole);
    }

	@Transactional(readOnly = false)
	public void save(DealerRole dealerRole) {
		super.save(dealerRole);
		// 更新角色与菜单关联
		this.mapper.deleteRoleMenu(dealerRole);
		if (dealerRole.getMenuList().size() > 0){
			this.mapper.insertRoleMenu(dealerRole);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(DealerRole dealerRole) {
		this.mapper.deleteRoleMenu(dealerRole);
		super.delete(dealerRole);
	}

    /**
     * 通过名称取权限
     * @param dealerRole
     * @return
     */
    public DealerRole getByName(DealerRole dealerRole) {
        return this.mapper.getByName(dealerRole);
    }
}