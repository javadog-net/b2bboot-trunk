/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserStoreFavorites;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserStoreFavoritesMapper;

/**
 * 店铺收藏Service
 * @author hdx
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class PurchaserStoreFavoritesService extends CrudService<PurchaserStoreFavoritesMapper, PurchaserStoreFavorites> {

	public PurchaserStoreFavorites get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserStoreFavorites> findList(PurchaserStoreFavorites purchaserStoreFavorites) {
		return super.findList(purchaserStoreFavorites);
	}
	
	public Page<PurchaserStoreFavorites> findPage(Page<PurchaserStoreFavorites> page, PurchaserStoreFavorites purchaserStoreFavorites) {
		return super.findPage(page, purchaserStoreFavorites);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserStoreFavorites purchaserStoreFavorites) {
		super.save(purchaserStoreFavorites);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserStoreFavorites purchaserStoreFavorites) {
		super.delete(purchaserStoreFavorites);
	}

	@Transactional(readOnly = false)
	public boolean checkAgain(String storeId,String purchaserAccountId){
		PurchaserStoreFavorites purchaserStoreFavorites = mapper.checkAgain(storeId,purchaserAccountId);
		if(purchaserStoreFavorites!=null){
			return false;
		}
		return true;
	}

	@Transactional(readOnly = false)
	public void cancel(String storeId,String purchaserAccountId){
		mapper.cancel(storeId,purchaserAccountId);
	}

	@Transactional(readOnly = false)
	public void deleteById(String favoritesStoreId){
		mapper.deleteById(favoritesStoreId);
	}



}