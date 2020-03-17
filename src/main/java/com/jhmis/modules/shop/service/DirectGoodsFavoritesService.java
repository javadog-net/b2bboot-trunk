/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.common.utils.IdGen;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.DirectGoodsFavorites;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.purchaser.PurchaserGoodsFavorites;
import com.jhmis.modules.shop.mapper.DirectGoodsFavoritesMapper;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;

/**
 * 直采Service
 * @author tc
 * @version 2019-03-26
 */
@Service
@Transactional(readOnly = true)
public class DirectGoodsFavoritesService extends CrudService<DirectGoodsFavoritesMapper, DirectGoodsFavorites> {


	@Resource
	StoreGoodsMapper storeGoodsMapper;

	
	public DirectGoodsFavorites get(String id) {
		return super.get(id);
	}
	
	public List<DirectGoodsFavorites> findList(DirectGoodsFavorites directGoodsFavorites) {
		return super.findList(directGoodsFavorites);
	}
	
	public Page<DirectGoodsFavorites> findPage(Page<DirectGoodsFavorites> page, DirectGoodsFavorites directGoodsFavorites) {
		return super.findPage(page, directGoodsFavorites);
	}
	
	@Transactional(readOnly = false)
	public void save(DirectGoodsFavorites directGoodsFavorites) {
		super.save(directGoodsFavorites);
	}
	
	@Transactional(readOnly = false)
	public void delete(DirectGoodsFavorites directGoodsFavorites) {
		super.delete(directGoodsFavorites);
	}
	@Transactional(readOnly = false)
	public boolean check(String storeGoodsId,String purchaserAccountId) {
		DirectGoodsFavorites directGoodsFavorites = mapper.checkAgain(storeGoodsId,purchaserAccountId);
		if(directGoodsFavorites!=null){
			return false;
		}
		return true;
	}
	@Transactional(readOnly = false)
	public boolean saveGoodsFavorites(DirectGoodsFavorites directGoodsFavorites,StoreGoods storeGoods) {
		directGoodsFavorites.setCategoryId(storeGoods.getCategoryId());
		directGoodsFavorites.setCategoryPid(storeGoods.getCategoryPid());
		directGoodsFavorites.setFavDate(new Date());
		directGoodsFavorites.setGoodsCode(storeGoods.getCode());
		directGoodsFavorites.setGoodsName(storeGoods.getGoodsName());
		directGoodsFavorites.setGoodsMainPic(storeGoods.getMainPicUrl());
		directGoodsFavorites.setLogPrice(storeGoods.getMarketPrice().toString());
		directGoodsFavorites.setStoreGoodsId(storeGoods.getId());
		directGoodsFavorites.setStoreId(storeGoods.getStoreId());
		directGoodsFavorites.setStoreName(storeGoods.getStoreName());
		try{
			directGoodsFavorites.setId(IdGen.uuid());
			mapper.insert(directGoodsFavorites);
			
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Transactional(readOnly = false)
	public void cancel(String storeGoodsId,String purchaserAccountId) {
		mapper.cancel(storeGoodsId,purchaserAccountId);
	}

	@Transactional(readOnly = false)
	public void deleteById(String favoritesGoodsId) {
		mapper.deleteById(favoritesGoodsId);
	}
	
}