/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import java.util.Date;
import java.util.List;

import com.jhmis.common.utils.IdGen;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserGoodsFavorites;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserGoodsFavoritesMapper;

import javax.annotation.Resource;

/**
 * 商品收藏Service
 * @author hdx
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class PurchaserGoodsFavoritesService extends CrudService<PurchaserGoodsFavoritesMapper, PurchaserGoodsFavorites> {

	@Resource
	StoreGoodsMapper storeGoodsMapper;

	public PurchaserGoodsFavorites get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserGoodsFavorites> findList(PurchaserGoodsFavorites purchaserGoodsFavorites) {
		return super.findList(purchaserGoodsFavorites);
	}
	
	public Page<PurchaserGoodsFavorites> findPage(Page<PurchaserGoodsFavorites> page, PurchaserGoodsFavorites purchaserGoodsFavorites) {
		return super.findPage(page, purchaserGoodsFavorites);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserGoodsFavorites purchaserGoodsFavorites) {
		super.save(purchaserGoodsFavorites);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserGoodsFavorites purchaserGoodsFavorites) {
		super.delete(purchaserGoodsFavorites);
	}

	@Transactional(readOnly = false)
	public boolean check(String storeGoodsId,String purchaserAccountId) {
		PurchaserGoodsFavorites purchaserGoodsFavorites = mapper.checkAgain(storeGoodsId,purchaserAccountId);
		if(purchaserGoodsFavorites!=null){
			return false;
		}
		return true;
	}
	@Transactional(readOnly = false)
	public boolean saveGoodsFavorites(PurchaserGoodsFavorites purchaserGoodsFavorites,StoreGoods storeGoods) {
		purchaserGoodsFavorites.setCategoryId(storeGoods.getCategoryId());
		purchaserGoodsFavorites.setCategoryPid(storeGoods.getCategoryPid());
		purchaserGoodsFavorites.setFavDate(new Date());
		purchaserGoodsFavorites.setGoodsCode(storeGoods.getCode());
		purchaserGoodsFavorites.setGoodsName(storeGoods.getGoodsName());
		purchaserGoodsFavorites.setGoodsMainPic(storeGoods.getMainPicUrl());
		purchaserGoodsFavorites.setLogPrice(storeGoods.getMarketPrice().toString());
		purchaserGoodsFavorites.setStoreGoodsId(storeGoods.getId());
		purchaserGoodsFavorites.setStoreId(storeGoods.getStoreId());
		purchaserGoodsFavorites.setStoreName(storeGoods.getStoreName());
		try{
			purchaserGoodsFavorites.setId(IdGen.uuid());
			mapper.insert(purchaserGoodsFavorites);
			//更新店铺商品表
			int num = storeGoods.getGoodsCollect();
			num = num + 1;
			storeGoods.setGoodsCollect(num);
			storeGoodsMapper.update(storeGoods);
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