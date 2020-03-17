/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 横幅管理Entity
 * @author hdx
 * @version 2018-08-18
 */
public class Banner extends DataEntity<Banner> {
	
	private static final long serialVersionUID = 1L;
	private String slidePicUrl;		// 图片地址
	private String slideLinkUrl;		// 图片链接路径
	private String title;		// 标题
	private String info;		// 介绍内容
	private String isShow;		// 是否展示
	private String sort;		// 排序
	private String remark;		// 备注
	
	public Banner() {
		this.isShow = "1";
	}

	public Banner(String id){
		super(id);
	}

	@ExcelField(title="图片地址", align=2, sort=1)
	public String getSlidePicUrl() {
		return slidePicUrl;
	}

	public void setSlidePicUrl(String slidePicUrl) {
		this.slidePicUrl = slidePicUrl;
	}
	
	@ExcelField(title="图片链接路径", align=2, sort=2)
	public String getSlideLinkUrl() {
		return slideLinkUrl;
	}

	public void setSlideLinkUrl(String slideLinkUrl) {
		this.slideLinkUrl = slideLinkUrl;
	}
	
	@ExcelField(title="标题", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="介绍内容", align=2, sort=4)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	@ExcelField(title="是否展示", dictType="enable", align=2, sort=5)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@ExcelField(title="排序", align=2, sort=6)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="备注", align=2, sort=7)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}