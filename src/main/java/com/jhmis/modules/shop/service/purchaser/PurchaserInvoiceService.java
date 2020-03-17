/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserInvoiceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 发票信息Service
 * @author tity
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
public class PurchaserInvoiceService extends CrudService<PurchaserInvoiceMapper, PurchaserInvoice> {

	public PurchaserInvoice get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserInvoice> findList(PurchaserInvoice purchaserInvoice) {
		return super.findList(purchaserInvoice);
	}
	
	public Page<PurchaserInvoice> findPage(Page<PurchaserInvoice> page, PurchaserInvoice purchaserInvoice) {
		return super.findPage(page, purchaserInvoice);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserInvoice purchaserInvoice) {
        if("1".equals(purchaserInvoice.getIsDefault())){
            this.mapper.clearDefault(purchaserInvoice.getPurchaserId());
        }
		super.save(purchaserInvoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserInvoice purchaserInvoice) {
		super.delete(purchaserInvoice);
	}

    /**
     * 设为默认
     * @param purchaserInvoice
     */
    @Transactional(readOnly = false)
    public void setDefault(PurchaserInvoice purchaserInvoice) {
        this.mapper.clearDefault(purchaserInvoice.getPurchaserId());
        this.mapper.setDefault(purchaserInvoice.getId());
    }

    /**
     * 获取默认发票
     * @param purchaserId
     * @return
     */
    public PurchaserInvoice getDefault(String purchaserId) {
        PurchaserInvoice entity = new PurchaserInvoice();
        entity.setPurchaserId(purchaserId);
        entity.setIsDefault("1");
        List<PurchaserInvoice> list = this.mapper.findList(entity);
        if(list.size()>0){
            return list.get(0);
        } else {
            return null;
        }
    }
}