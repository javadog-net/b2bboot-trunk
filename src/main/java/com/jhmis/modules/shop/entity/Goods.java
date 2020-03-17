/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import com.jhmis.modules.shop.entity.GoodsCategory;

import com.jhmis.core.persistence.DataEntity;

import java.util.List;

import javax.persistence.Transient;

import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品表Entity
 * @author tity
 * @version 2018-07-23
 */
public class Goods extends DataEntity<Goods> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 产品编码
	private String mainPicUrl;		// 产品主图
	private String name;		// 产品名称
	private String brand;		// 产品品牌
	private GoodsCategory cat;		// 产品分类 父类
	private String productGroupCode;
	private String productGroupName;
	private String title;//商品title
	private String keyword;//商品keyword
	private String des;//商品的des
	private String goodsTemplate;//商品模版
	private String pageUrl; // 产品页地址

	public String getGoodsTemplate() {
		return goodsTemplate;
	}

	public void setGoodsTemplate(String goodsTemplate) {
		this.goodsTemplate = goodsTemplate;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	

	@Transient
	private int stock=0;//可购库存
	@Transient
	private Double exclusivePrice = 0.00;//独享价
	
	@Transient
	private String purchaserId;//直采商id
	
	@Transient
	private int chooseNum;//购买数量
	@Transient
	private List<String> codeList;//产品编码集合
	@Transient
	private String updateTime;//关联商品时间

	@Transient
	private String storeGoodsId;//店铺产品id
	@Transient
	private String customersPartnerID;//送达方id

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public String getCustomersPartnerID() {
		return customersPartnerID;
	}

	public void setCustomersPartnerID(String customersPartnerID) {
		this.customersPartnerID = customersPartnerID;
	}

	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}


	public String getStoreGoodsId() {
		return storeGoodsId;
	}

	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}


	public int getChooseNum() {
		return chooseNum;
	}

	public void setChooseNum(int chooseNum) {
		this.chooseNum = chooseNum;
	}

	public List<String> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}


	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	//ext表中获取
	private String categoryPid;		// 产品一级目录
	private String categoryId;		// 产品二级目录
	private String state;		// 是否可用  0  可用  1 禁用
	private Double marketPrice;		// 市场价

	private String brandName; //品牌名称

	private String storeId; //品牌名称

	private GoodsExt goodsExt; //商品扩展表

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public GoodsExt getGoodsExt() {
		return goodsExt;
	}

	public void setGoodsExt(GoodsExt goodsExt) {
		this.goodsExt = goodsExt;
	}

	private int pageNo;//当前页

	private int pageSize;//每页长度

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Goods() {
		super();
	}

	public Goods(String id){
		super(id);
	}

	public Goods(GoodsCategory cat){
		this.cat = cat;
	}

	@ExcelField(title="产品编码", align=2, sort=0)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="产品主图", align=2, sort=1)
	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	
	@ExcelField(title="产品名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="产品品牌", align=2, sort=3)
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public GoodsCategory getCat() {
		return cat;
	}

	public void setCat(GoodsCategory cat) {
		this.cat = cat;
	}

	@ExcelField(title="产品一级目录", align=2, sort=1)
	public String getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}

	@ExcelField(title="产品二级目录", align=2, sort=2)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@ExcelField(title="是否可用  0  可用  1 禁用", align=2, sort=3)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public Double getExclusivePrice() {
		return exclusivePrice;
	}

	public void setExclusivePrice(Double exclusivePrice) {
		this.exclusivePrice = exclusivePrice;
	}


	@ExcelField(title="市场价", align=2, sort=4)
	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}