/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserRole;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购商角色Service
 * @author tity
 * @version 2018-07-25
 */
@Service
@Transactional(readOnly = true)
public class PurchaserRoleService extends CrudService<PurchaserRoleMapper, PurchaserRole> {

	public PurchaserRole get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserRole> findList(PurchaserRole purchaserRole) {
		return super.findList(purchaserRole);
	}
    public List<PurchaserRole> findAllList(PurchaserRole purchaserRole) {
        return this.mapper.findAllList(purchaserRole);
    }
	public Page<PurchaserRole> findPage(Page<PurchaserRole> page, PurchaserRole purchaserRole) {
		return super.findPage(page, purchaserRole);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserRole purchaserRole) {
		super.save(purchaserRole);
        // 更新角色与菜单关联
        this.mapper.deleteRoleMenu(purchaserRole);
        if (purchaserRole.getMenuList().size() > 0){
            this.mapper.insertRoleMenu(purchaserRole);
        }
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserRole purchaserRole) {
        this.mapper.deleteRoleMenu(purchaserRole);
        super.delete(purchaserRole);
	}

    /**
     * 通过名称取权限
     * @param purchaserRole
     * @return
     */
    public PurchaserRole getByName(PurchaserRole purchaserRole) {
        return this.mapper.getByName(purchaserRole);
    }
}