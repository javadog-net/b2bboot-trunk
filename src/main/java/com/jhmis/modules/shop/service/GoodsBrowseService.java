/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.Date;
import java.util.List;

import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsExt;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.mapper.GoodsExtMapper;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.GoodsBrowse;
import com.jhmis.modules.shop.mapper.GoodsBrowseMapper;

import javax.annotation.Resource;

/**
 * 商品浏览记录Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GoodsBrowseService extends CrudService<GoodsBrowseMapper, GoodsBrowse> {

	@Resource
	StoreGoodsMapper storeGoodsMapper;
	@Resource
	GoodsExtMapper goodsExtMapper;


	public GoodsBrowse get(String id) {
		return super.get(id);
	}
	
	public List<GoodsBrowse> findList(GoodsBrowse goodsBrowse) {
		return super.findList(goodsBrowse);
	}
	
	public Page<GoodsBrowse> findPage(Page<GoodsBrowse> page, GoodsBrowse goodsBrowse) {
		return super.findPage(page, goodsBrowse);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsBrowse goodsBrowse) {
		super.save(goodsBrowse);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsBrowse goodsBrowse) {
		super.delete(goodsBrowse);
	}

	@Transactional(readOnly = false)
	public void update(GoodsBrowse goodsBrowse) {
		mapper.update(goodsBrowse);
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
	public void saveOrUpdate(List<GoodsBrowse>  goodsBrowseList,StoreGoods storeGoods,GoodsBrowse goodsBrowse) {
		if(goodsBrowseList!=null && goodsBrowseList.size()>0){
			//存在则更新即可
			GoodsBrowse gb = goodsBrowseList.get(0);
			gb.setBrowseTime(new Date());
			mapper.update(gb);
		}else{
			goodsBrowse.setGoodsName(storeGoods.getGoodsName());
			goodsBrowse.setCategoryPid(storeGoods.getCategoryPid());
			goodsBrowse.setCategoryId(storeGoods.getCategoryId());
			goodsBrowse.setBrowseTime(new Date());
			goodsBrowse.setStoreGoodsId(storeGoods.getId());
			super.save(goodsBrowse);
		}
		//修改店铺商品中的数据
		if(storeGoods!=null){
			int goodsClick = 0;
			if(storeGoods.getGoodsClick()==null){
				goodsClick = 0;
			}else{
				goodsClick =  storeGoods.getGoodsClick();
			}
			goodsClick = goodsClick + 1;
			storeGoods.setGoodsClick(goodsClick);
			storeGoodsMapper.update(storeGoods);
		}
	}

}