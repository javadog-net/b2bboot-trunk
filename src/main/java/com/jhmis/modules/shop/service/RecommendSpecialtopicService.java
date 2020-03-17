/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.ArrayList;
import java.util.List;

import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.mapper.AdvertMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.mapper.RecommendSpecialtopicMapper;

import javax.annotation.Resource;

/**
 * 特别推荐表Service
 * @author hdx
 * @version 2018-08-02
 */
@Service
@Transactional(readOnly = true)
public class RecommendSpecialtopicService extends CrudService<RecommendSpecialtopicMapper, RecommendSpecialtopic> {

	@Resource
	private AdvertMapper advertMapper;

	public RecommendSpecialtopic get(String id) {
		return super.get(id);
	}

	public List<RecommendSpecialtopic> findList(RecommendSpecialtopic recommendSpecialtopic) {
		return super.findList(recommendSpecialtopic);
	}
	
	public Page<RecommendSpecialtopic> findPage(Page<RecommendSpecialtopic> page, RecommendSpecialtopic recommendSpecialtopic) {
		return super.findPage(page, recommendSpecialtopic);
	}
	
	@Transactional(readOnly = false)
	public void save(RecommendSpecialtopic recommendSpecialtopic) {
		super.save(recommendSpecialtopic);
	}
	
	@Transactional(readOnly = false)
	public void delete(RecommendSpecialtopic recommendSpecialtopic) {
		super.delete(recommendSpecialtopic);
	}

	@Transactional(readOnly = false)
	public  List<RecommendRstContent> selectRcForApi(String dictionaryId,String cityid){
		List<RecommendSpecialtopic> RecommendSpecialtopicList =  mapper.selectRcForApi(dictionaryId,cityid);
		if(RecommendSpecialtopicList.size()==0){
			return null;
		}
		//分类整合list
		List<RecommendRstContent> recommendRstContentList = new ArrayList<RecommendRstContent>();
		for(RecommendSpecialtopic rs:RecommendSpecialtopicList){
			RecommendRstContent rec = new RecommendRstContent();
			rec.setDictionaryId(rs.getDictionaryId());
			rec.setDictionaryName(rs.getDictionaryName());
			String []arg = rs.getGoodsCode().split(",");
			List<RecommendSpecialtopic> recommendSpecialtopicChildList = new ArrayList<RecommendSpecialtopic>();
			for(int i=0; i<arg.length; i++){
				RecommendSpecialtopic recChild = new RecommendSpecialtopic();
				recChild.setDictionaryId(rs.getDictionaryId());
				recChild.setDictionaryName(rs.getDictionaryName());
				recChild.setGoodsCode(rs.getGoodsCode().split(",")[i]);
				recChild.setGoodsId(rs.getGoodsId().split(",")[i]);
				recChild.setStoreId(rs.getStoreId().split(",")[i]);
				recChild.setStoreName(rs.getStoreName().split(",")[i]);
				recChild.setGoodsName(rs.getGoodsName().split(",")[i]);
				recChild.setSort(rs.getSort().split(",")[i]);
				//主图可能有多张
				recChild.setMainPicUrl(rs.getMainPicUrl().split(",")[i]);
				recChild.setMarketPrice(rs.getMarketPrice().split(",")[i]);
				recChild.setPrice(rs.getPrice().split(",")[i]);
				recommendSpecialtopicChildList.add(recChild);
			}
			Advert advert = new Advert();
			advert.setRelevantId(rs.getDictionaryId());
			//广告类型 0是分类推荐
			advert.setRelevantType("1");
			List<Advert> advertList =  advertMapper.findList(advert);
			//将广告放入其中
			rec.setAdvertList(advertList);
			rec.setRecommendSpecialtopic(recommendSpecialtopicChildList);
			recommendRstContentList.add(rec);
		}
		return recommendRstContentList;
	}
}