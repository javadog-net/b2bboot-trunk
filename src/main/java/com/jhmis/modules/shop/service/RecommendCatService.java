/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.ArrayList;
import java.util.List;

import com.jhmis.modules.shop.entity.Advert;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.RecommendCatContent;
import com.jhmis.modules.shop.mapper.AdvertMapper;
import com.jhmis.modules.shop.mapper.GoodsCategoryMapper;
import com.jhmis.modules.shop.mapper.GoodsExtMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.RecommendCat;
import com.jhmis.modules.shop.mapper.RecommendCatMapper;

import javax.annotation.Resource;

/**
 * 分类推荐表Service
 * @author hdx
 * @version 2018-08-02
 */
@Service
@Transactional(readOnly = true)
public class RecommendCatService extends CrudService<RecommendCatMapper, RecommendCat> {

	@Resource
	private AdvertMapper advertMapper;

	@Resource
	private GoodsCategoryMapper goodsCategoryMapper;

	public RecommendCat get(String id) {
		return super.get(id);
	}
	
	public List<RecommendCat> findList(RecommendCat recommendCat) {
		return super.findList(recommendCat);
	}
	
	public Page<RecommendCat> findPage(Page<RecommendCat> page, RecommendCat recommendCat) {
		return super.findPage(page, recommendCat);
	}
	
	@Transactional(readOnly = false)
	public void save(RecommendCat recommendCat) {
		super.save(recommendCat);
	}
	
	@Transactional(readOnly = false)
	public void delete(RecommendCat recommendCat) {
		super.delete(recommendCat);
	}

	@Transactional(readOnly = false)
	public  List<RecommendCatContent> selectRcForApi(String categoryId,String cityid){
		List<RecommendCat> recommendCatList =  mapper.selectRcForApi(categoryId,cityid);
		if(recommendCatList.size()==0){
			return null;
		}
		//分类
		List<RecommendCatContent> recommendCatContentList = new ArrayList<RecommendCatContent>();
		for(RecommendCat rc:recommendCatList){
			RecommendCatContent rec = new RecommendCatContent();
			rec.setCategoryId(rc.getCategoryId());
			rec.setCategoryName(rc.getCategoryName());
			//放入分类图片
			GoodsCategory goodsCategory = goodsCategoryMapper.get(rc.getCategoryId());
			if(goodsCategory!=null){
				rec.setUrl(goodsCategory.getIconUrl());
			}
			String []arg = rc.getGoodsCode().split(",");
			List<RecommendCat> recommendCatChildList = new ArrayList<RecommendCat>();
			for(int i=0; i<arg.length; i++){
				RecommendCat recChild = new RecommendCat();
				recChild.setCategoryId(rc.getCategoryId());
				recChild.setCategoryName(rc.getCategoryName());
				recChild.setGoodsCode(rc.getGoodsCode().split(",")[i]);
				recChild.setGoodsId(rc.getGoodsId().split(",")[i]);
				recChild.setStoreId(rc.getStoreId().split(",")[i]);
				recChild.setStoreName(rc.getStoreName().split(",")[i]);
				recChild.setGoodsName(rc.getGoodsName().split(",")[i]);
				recChild.setSort(rc.getSort().split(",")[i]);
				//主图可能有多张
				recChild.setMainPicUrl(rc.getMainPicUrl().split(",")[i]);
				recChild.setMarketPrice(rc.getMarketPrice().split(",")[i]);
				recChild.setPrice(rc.getPrice().split(",")[i]);
				recommendCatChildList.add(recChild);
			}
			Advert advert = new Advert();
			advert.setRelevantId(rc.getCategoryId());
			//广告类型 0是分类推荐
			advert.setRelevantType("0");
			List<Advert> advertList =  advertMapper.findList(advert);
			rec.setRecommendCats(recommendCatChildList);
			//将广告放入其中
			rec.setAdvertList(advertList);
			recommendCatContentList.add(rec);
		}
		return recommendCatContentList;
	}
}