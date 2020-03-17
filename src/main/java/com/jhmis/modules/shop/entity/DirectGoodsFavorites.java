/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 直采Entity
 * @author tc
 * @version 2019-03-26
 */
public class DirectGoodsFavorites extends DataEntity<DirectGoodsFavorites> {
	
	private static final long serialVersionUID = 1L;
	private String dealerAccountId;		// 供应商登陆账号ID
	private String storeGoodsId;		// 店铺商品ID
	private Date favDate;		// 收藏时间
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String goodsMainPic;		// 商品图片
	private String categoryPid;		// 商品一级分类ID
	private String categoryId;		// 商品二级分类ID
	private String logPrice;		// 商品收藏时的价格
	private String logMsg;		// 收藏时的备注
	
	public DirectGoodsFavorites() {
		super();
	}

	public DirectGoodsFavorites(String id){
		super(id);
	}

	@ExcelField(title="供应商登陆账号ID", align=2, sort=1)
	public String getDealerAccountId() {
		return dealerAccountId;
	}

	public void setDealerAccountId(String dealerAccountId) {
		this.dealerAccountId = dealerAccountId;
	}
	
	@ExcelField(title="店铺商品ID", align=2, sort=2)
	public String getStoreGoodsId() {
		return storeGoodsId;
	}

	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	
	@ExcelField(title="商品一级分类ID", align=2, sort=9)
	public String getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}
	
	@ExcelField(title="商品二级分类ID", align=2, sort=10)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="商品收藏时的价格", align=2, sort=11)
	public String getLogPrice() {
		return logPrice;
	}

	public void setLogPrice(String logPrice) {
		this.logPrice = logPrice;
	}
	
	@ExcelField(title="收藏时的备注", align=2, sort=12)
	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	
}