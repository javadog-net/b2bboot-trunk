/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.ArrayList;
import java.util.List;

import com.jhmis.modules.shop.entity.GoodsClassPropertiesContent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.GoodsClassProperties;
import com.jhmis.modules.shop.mapper.GoodsClassPropertiesMapper;

/**
 * 商品属性Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GoodsClassPropertiesService extends CrudService<GoodsClassPropertiesMapper, GoodsClassProperties> {

	public GoodsClassProperties get(String id) {
		return super.get(id);
	}
	
	public List<GoodsClassProperties> findList(GoodsClassProperties goodsClassProperties) {
		return super.findList(goodsClassProperties);
	}
	
	public Page<GoodsClassProperties> findPage(Page<GoodsClassProperties> page, GoodsClassProperties goodsClassProperties) {
		return super.findPage(page, goodsClassProperties);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsClassProperties goodsClassProperties) {
		super.save(goodsClassProperties);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsClassProperties goodsClassProperties) {
		super.delete(goodsClassProperties);
	}

	@Transactional(readOnly = false)
	public List<GoodsClassProperties> findConcatProperty(String classId){
		return mapper.findConcatProperty(classId);
	}

	@Transactional(readOnly = false)
	public List<GoodsClassPropertiesContent> findClassProperties(String classId){
		//拿到分组后
		List<GoodsClassProperties> goodsClassPropertiesList =  mapper.findClassProperties(classId);
		List<GoodsClassPropertiesContent> gcpcList = new ArrayList<>();
		for(GoodsClassProperties goodsClassProperties:goodsClassPropertiesList){
			//根据用逗号隔开的数据进行分组
			GoodsClassPropertiesContent gcpc = new GoodsClassPropertiesContent();
			List<GoodsClassProperties> goodsClassPropertiesListChild = new ArrayList<>();
			gcpc.setClassId(goodsClassProperties.getClassId());
			gcpc.setName(goodsClassProperties.getName());
			String arg[] = goodsClassProperties.getValue().split(",");
			for(int i=0; i<arg.length; i++){
				GoodsClassProperties gcpChild = new GoodsClassProperties();
				gcpChild.setClassId(gcpc.getClassId());
				gcpChild.setName(gcpc.getName());
				gcpChild.setValue(arg[i]);
				gcpChild.setId(goodsClassProperties.getId());
				goodsClassPropertiesListChild.add(gcpChild);
			}
			gcpc.setGoodsClassPropertiesList(goodsClassPropertiesListChild);
			gcpcList.add(gcpc);
		}
		return gcpcList;
	}
}