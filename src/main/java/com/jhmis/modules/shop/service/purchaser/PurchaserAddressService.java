/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购商地址管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class PurchaserAddressService extends CrudService<PurchaserAddressMapper, PurchaserAddress> {

	public PurchaserAddress get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserAddress> findList(PurchaserAddress purchaserAddress) {
		return super.findList(purchaserAddress);
	}
	
	public Page<PurchaserAddress> findPage(Page<PurchaserAddress> page, PurchaserAddress purchaserAddress) {
		return super.findPage(page, purchaserAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserAddress purchaserAddress) {
	    if("1".equals(purchaserAddress.getIsDefault())){
	        this.mapper.clearDefault(purchaserAddress.getPurchaserId());
        }
		super.save(purchaserAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserAddress purchaserAddress) {
		super.delete(purchaserAddress);
	}

    /**
     * 设为默认
     * @param purchaserAddress
     */
    @Transactional(readOnly = false)
    public void setDefault(PurchaserAddress purchaserAddress) {
        this.mapper.clearDefault(purchaserAddress.getPurchaserId());
        this.mapper.setDefault(purchaserAddress.getId());
    }

    /**
     * 获取默认地址
     * @param purchaserId
     * @return
     */
    public PurchaserAddress getDefault(String purchaserId) {
        PurchaserAddress entity = new PurchaserAddress();
        entity.setPurchaserId(purchaserId);
        entity.setIsDefault("1");
        List<PurchaserAddress> list = this.mapper.findList(entity);
        if(list.size()>0){
            return list.get(0);
        } else {
            return null;
        }
    }
}