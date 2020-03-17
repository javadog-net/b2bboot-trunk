/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.purchaser;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品店铺收藏管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class PurchaserFavorites extends DataEntity<PurchaserFavorites> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserAccountId;		// 采购商登录账号ID
	private String favType;		// 类型:goods为商品,store为店铺,默认为商品
	private Date favDate;		// 收藏时间
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String goodsMainPic;		// 商品图片
	private String categoryId;		// 商品二级分类ID
	private Double logPrice;		// 商品收藏时价格
	private String logMsg;		// 收藏备注
	private String brandId;		//品牌id
	private String brandName;		//品牌名称
	private double marketPrice;  //市场价
	private double price;  //卖价

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public PurchaserFavorites() {
		super();
	}

	public PurchaserFavorites(String id){
		super(id);
	}

	@ExcelField(title="采购商登录账号ID", align=2, sort=1)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@ExcelField(title="类型:goods为商品,store为店铺,默认为商品", align=2, sort=2)
	public String getFavType() {
		return favType;
	}

	public void setFavType(String favType) {
		this.favType = favType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="收藏时间不能为空")
	@ExcelField(title="收藏时间", align=2, sort=3)
	public Date getFavDate() {
		return favDate;
	}

	public void setFavDate(Date favDate) {
		this.favDate = favDate;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=4)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=5)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@ExcelField(title="商品编码", align=2, sort=6)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="商品名称", align=2, sort=7)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@ExcelField(title="商品图片", align=2, sort=8)
	public String getGoodsMainPic() {
		return goodsMainPic;
	}

	public void setGoodsMainPic(String goodsMainPic) {
		this.goodsMainPic = goodsMainPic;
	}
	
	@ExcelField(title="商品二级分类ID", align=2, sort=9)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="商品收藏时价格", align=2, sort=10)
	public Double getLogPrice() {
		return logPrice;
	}

	public void setLogPrice(Double logPrice) {
		this.logPrice = logPrice;
	}
	
	@ExcelField(title="收藏备注", align=2, sort=11)
	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	
}