/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 文章关联产品Entity
 * @author lydia
 * @version 2019-10-14
 */
public class InfoProduct extends DataEntity<InfoProduct> {
	
	private static final long serialVersionUID = 1L;
	private String infoId;		// 内容ID
	private String productId;		// 产品ID
	private String productName; //产品名称
	private String sort;		// sort
	private String htmlpath;//商品的路径
	private String mainPicUrl;//产品的主图 一个

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public String getHtmlpath() {
		return htmlpath;
	}

	public void setHtmlpath(String htmlpath) {
		this.htmlpath = htmlpath;
	}

	public InfoProduct() {
		super();
	}

	public InfoProduct(String id){
		super(id);
	}

	@ExcelField(title="内容ID", align=2, sort=1)
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
	@ExcelField(title="产品ID", align=2, sort=2)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@ExcelField(title="sort", align=2, sort=3)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}