/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 广告管理Entity
 * @author hdx
 * @version 2018-08-04
 */
public class Advert extends DataEntity<Advert> {
	
	private static final long serialVersionUID = 1L;
	private String relevantType;		// 关联类型 (0是分类推荐 ,1是特别推荐)
	private String relevantId;		// 关联id
	private String advertImgs;		// 图片(若是多张则逗号隔开)
	private String advertType;		// 广告类型(0是单图，1是多图)
	private String advertUrl;		// 广告链接(多个链接用逗号隔开)
	private String advertTitle;		// 广告标题
	private String advertInfo;		// 广告信息
	private String sort;		// sort
	private String remark;		// remark
	
	public Advert() {
		super();
	}

	public Advert(String id){
		super(id);
	}

	@ExcelField(title="关联类型 (0是分类推荐 ,1是特别推荐)", dictType="", align=2, sort=1)
	public String getRelevantType() {
		return relevantType;
	}

	public void setRelevantType(String relevantType) {
		this.relevantType = relevantType;
	}
	
	@ExcelField(title="关联id", align=2, sort=2)
	public String getRelevantId() {
		return relevantId;
	}

	public void setRelevantId(String relevantId) {
		this.relevantId = relevantId;
	}
	
	@ExcelField(title="图片(若是多张则逗号隔开)", align=2, sort=3)
	public String getAdvertImgs() {
		return advertImgs;
	}

	public void setAdvertImgs(String advertImgs) {
		this.advertImgs = advertImgs;
	}
	
	@ExcelField(title="广告类型(0是单图，1是多图)", dictType="", align=2, sort=4)
	public String getAdvertType() {
		return advertType;
	}

	public void setAdvertType(String advertType) {
		this.advertType = advertType;
	}
	
	@ExcelField(title="广告链接(多个链接用逗号隔开)", align=2, sort=5)
	public String getAdvertUrl() {
		return advertUrl;
	}

	public void setAdvertUrl(String advertUrl) {
		this.advertUrl = advertUrl;
	}
	
	@ExcelField(title="广告标题", align=2, sort=6)
	public String getAdvertTitle() {
		return advertTitle;
	}

	public void setAdvertTitle(String advertTitle) {
		this.advertTitle = advertTitle;
	}
	
	@ExcelField(title="广告信息", align=2, sort=7)
	public String getAdvertInfo() {
		return advertInfo;
	}

	public void setAdvertInfo(String advertInfo) {
		this.advertInfo = advertInfo;
	}
	
	@ExcelField(title="sort", align=2, sort=8)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="remark", align=2, sort=9)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}