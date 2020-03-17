/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.mapper.GoodsMapper;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;
import com.jhmis.modules.shop.mapper.StoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserFavorites;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserFavoritesMapper;

import javax.annotation.Resource;

/**
 * 商品店铺收藏管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class PurchaserFavoritesService extends CrudService<PurchaserFavoritesMapper, PurchaserFavorites> {

	@Resource
	StoreMapper storeMapper;

	@Resource
	GoodsMapper goodsMapper;

	@Resource
	StoreGoodsMapper storeGoodsMapper;


	public PurchaserFavorites get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserFavorites> findList(PurchaserFavorites purchaserFavorites) {
		return super.findList(purchaserFavorites);
	}
	
	public Page<PurchaserFavorites> findPage(Page<PurchaserFavorites> page, PurchaserFavorites purchaserFavorites) {
		return super.findPage(page, purchaserFavorites);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserFavorites purchaserFavorites) {
		super.save(purchaserFavorites);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserFavorites purchaserFavorites) {
		super.delete(purchaserFavorites);
	}

	@Transactional(readOnly = false)
	public void deleteById(String id) {
		String []args = id.split(",");
		if(args.length>0){
			for(int i=0; i<args.length; i++){
				mapper.deleteById(args[i]);
			}
		}
	}

	@Transactional(readOnly = false)
	public boolean checkOver(PurchaserFavorites purchaserFavorites){
		if("goods".equals(purchaserFavorites.getFavType())){
			//商品查重
			PurchaserFavorites pcheck = new PurchaserFavorites();
			pcheck.setPurchaserAccountId(purchaserFavorites.getPurchaserAccountId());
			pcheck.setStoreId(purchaserFavorites.getStoreId());
			pcheck.setGoodsCode(purchaserFavorites.getGoodsCode());
			pcheck.setFavType("goods");
			List<PurchaserFavorites> purchaserFavoritesList = mapper.findList(pcheck);
			if(purchaserFavoritesList!=null && purchaserFavoritesList.size()!=0){
				return false;
			}else{
				return true;
			}
		}else if("store".equals(purchaserFavorites.getFavType())){
			//店铺查重
			PurchaserFavorites pcheck = new PurchaserFavorites();
			pcheck.setPurchaserAccountId(purchaserFavorites.getPurchaserAccountId());
			pcheck.setStoreId(purchaserFavorites.getStoreId());
			pcheck.setFavType("store");
			mapper.findList(pcheck);
			List<PurchaserFavorites> purchaserFavoritesList = mapper.findList(pcheck);
			if(purchaserFavoritesList!=null  && purchaserFavoritesList.size()!=0){
				return false;
			}else{
				return true;
			}
		}
		return true;
	}

	@Transactional(readOnly = false)
	public Map<String,Object> savePurchaserFavorites(PurchaserFavorites purchaserFavorites, Store s){
		Map<String,Object> map = new HashMap<>();
		map.put("flag",true);
		map.put("msg","操作成功");
		purchaserFavorites.setStoreName(s.getStoreName());
		if("goods".equals(purchaserFavorites.getFavType())){
			//此为收藏商品则录入商品信息
			if(StringUtils.isEmpty(purchaserFavorites.getGoodsCode())){
				map.put("flag",false);
				map.put("msg","商品信息异常");
				return map;
			}else{
				Goods g = new Goods();
				g.setCode(purchaserFavorites.getGoodsCode());
				List<Goods> goodsList = goodsMapper.findList(g);
				if(goodsList==null || goodsList.size()==0){
					map.put("flag",false);
					map.put("msg","此商品不存在");
					return map;
				}
				g = goodsList.get(0);
				purchaserFavorites.setGoodsName(g.getName());
				purchaserFavorites.setGoodsMainPic(g.getMainPicUrl());
				purchaserFavorites.setCategoryId(g.getCat().getId());
				//根据其收藏的产品进行数值加一
				StoreGoods storeGoods = new StoreGoods();
				storeGoods.setCode(purchaserFavorites.getGoodsCode());
				storeGoods.setStoreId(purchaserFavorites.getStoreId());
				List<StoreGoods> storeGoodList = storeGoodsMapper.findList(storeGoods);
				if(storeGoodList!=null && storeGoodList.size()==1){
					//查到此信息
					storeGoods = storeGoodList.get(0);
					if(storeGoods.getGoodsCollect()!=null){
						int goodsCollect = storeGoods.getGoodsCollect() + 1;
						storeGoods.setGoodsCollect(goodsCollect);
						storeGoodsMapper.update(storeGoods);
					}
				}
			}
		}
		purchaserFavorites.setFavDate(new Date());
		super.save(purchaserFavorites);
		return map;
	}

}