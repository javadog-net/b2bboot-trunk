/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import com.google.common.collect.Lists;

import com.jhmis.core.persistence.TreeEntity;

/**
 * 商品管理Entity
 * @author tity
 * @version 2018-07-23
 */
public class GoodsCategory extends TreeEntity<GoodsCategory> {
	
	private static final long serialVersionUID = 1L;
	private String iconUrl;		// 图标路径
	private String classId;		// 品类
	private String isShow;//是否展示 1是   0否
	private String pageMark;//页面标识
	private String classTemplate;//分类模板
	private String goodsTemplate;//商品模版
	private String pageUrl;//页面路径
	private String repeatName;//只用于 检查重复

	public String getRepeatName() {
		return repeatName;
	}

	public void setRepeatName(String repeatName) {
		this.repeatName = repeatName;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	
	public String getPageMark() {
		return pageMark;
	}

	public void setPageMark(String pageMark) {
		this.pageMark = pageMark;
	}

	public String getClassTemplate() {
		return classTemplate;
	}

	public void setClassTemplate(String classTemplate) {
		this.classTemplate = classTemplate;
	}

	public String getGoodsTemplate() {
		return goodsTemplate;
	}

	public void setGoodsTemplate(String goodsTemplate) {
		this.goodsTemplate = goodsTemplate;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public List<GoodsClass> getGoodsClassList() {
		return goodsClassList;
	}

	public void setGoodsClassList(List<GoodsClass> goodsClassList) {
		this.goodsClassList = goodsClassList;
	}
	private List<Goods> goodsList = Lists.newArrayList();		// 子表列表

	private List<GoodsClass> goodsClassList;
	
	public GoodsCategory() {
		super();
	}

	public GoodsCategory(String id){
		super(id);
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public  GoodsCategory getParent() {
			return parent;
	}
	
	@Override
	public void setParent(GoodsCategory parent) {
		this.parent = parent;
		
	}
	
	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}