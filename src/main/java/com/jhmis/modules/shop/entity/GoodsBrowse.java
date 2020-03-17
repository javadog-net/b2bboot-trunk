/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品浏览记录Entity
 * @author tity
 * @version 2018-07-22
 */
public class GoodsBrowse extends DataEntity<GoodsBrowse> {
	
	private static final long serialVersionUID = 1L;
	private String storeId;		// 店铺ID
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String purchaserAccountId;		// 供应商登录账号
	private Date browseTime;		// 浏览时间
	private String categoryPid;		// 商品一级分类
	private String categoryId;		// 商品二级分类
	private Date beginBrowseTime;		// 开始 浏览时间
	private Date endBrowseTime;		// 结束 浏览时间

	private String brandId;   //品牌id
	private String brandName;   //品牌名称
	private Double marketPrice;   //市场价
	private Double price;   //市场价
	private String mainPicUrl;   //项目主图
	private String storeGoodsId;  //店铺商品id

	public String getStoreGoodsId() {
		return storeGoodsId;
	}

	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public GoodsBrowse() {
		super();
	}

	public GoodsBrowse(String id){
		super(id);
	}

	@ExcelField(title="店铺ID", align=2, sort=1)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="商品编码", align=2, sort=2)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="商品名称", align=2, sort=3)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@ExcelField(title="供应商登录账号", align=2, sort=4)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="浏览时间不能为空")
	@ExcelField(title="浏览时间", align=2, sort=5)
	public Date getBrowseTime() {
		return browseTime;
	}

	public void setBrowseTime(Date browseTime) {
		this.browseTime = browseTime;
	}
	
	@ExcelField(title="商品一级分类", align=2, sort=6)
	public String getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}
	
	@ExcelField(title="商品二级分类", align=2, sort=7)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public Date getBeginBrowseTime() {
		return beginBrowseTime;
	}

	public void setBeginBrowseTime(Date beginBrowseTime) {
		this.beginBrowseTime = beginBrowseTime;
	}
	
	public Date getEndBrowseTime() {
		return endBrowseTime;
	}

	public void setEndBrowseTime(Date endBrowseTime) {
		this.endBrowseTime = endBrowseTime;
	}
		
}