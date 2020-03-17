/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 店铺商品管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class StoreGoods extends DataEntity<StoreGoods> {
	
	private static final long serialVersionUID = 1L;

	private String storeId;		// 店铺ID
	private String categoryPid;		// 产品大类
	private String categoryId;		// 产品子类
	private String code;		// 产品编码
	private Double marketPrice;		// 市场价
	private Double price;		// 卖价
	private Integer stock;		// 库存
	private Integer stockWarning;		// 库存预警
	private String isRecommend;		// 是否推荐
	private String isPromotion;		// 是否促销
	private String isHotSale;//是否热卖 0是 1否
	private String isTop;//是否置顶
	private String isNew;//是否新品
	private String htmlpath;//静态化路径
	private int htmlIndexNum;//html索引号
	private String oldHtmlPath;//原来html路径
	private String dateFormat;
	private int newDays;//静态化使用 最近几天发布的商品
	private String orderBySql;//静态化使用 按照什么分组sql
	private String isToIndexYanxuan;//是否发布至严选
 	private String isToIndexBrand;//发布至品牌专区
	private String isToIndexZhengcai;//发布至政采

	public String getIsToIndexYiqing() {
		return isToIndexYiqing;
	}

	public void setIsToIndexYiqing(String isToIndexYiqing) {
		this.isToIndexYiqing = isToIndexYiqing;
	}

	private String isToIndexYiqing;//发布至疫情专区
	public String getMainOnePic() {
		return mainOnePic;
	}

	public void setMainOnePic(String mainOnePic) {
		this.mainOnePic = mainOnePic;
	}

	private String mainOnePic;//获取主图的第一个路径

	private String pageUrl;

	public String getIsToIndexYanxuan() {
		return isToIndexYanxuan;
	}

	public void setIsToIndexYanxuan(String isToIndexYanxuan) {
		this.isToIndexYanxuan = isToIndexYanxuan;
	}

	public String getIsToIndexBrand() {
		return isToIndexBrand;
	}

	public void setIsToIndexBrand(String isToIndexBrand) {
		this.isToIndexBrand = isToIndexBrand;
	}

	public String getIsToIndexZhengcai() {
		return isToIndexZhengcai;
	}

	public void setIsToIndexZhengcai(String isToIndexZhengcai) {
		this.isToIndexZhengcai = isToIndexZhengcai;
	}

	public String getOrderBySql() {
		return orderBySql;
	}

	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}

	public int getNewDays() {
		return newDays;
	}

	public void setNewDays(int newDays) {
		this.newDays = newDays;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getHtmlpath() {
		return htmlpath;
	}

	public void setHtmlpath(String htmlpath) {
		this.htmlpath = htmlpath;
	}

	public int getHtmlIndexNum() {
		return htmlIndexNum;
	}

	public void setHtmlIndexNum(int htmlIndexNum) {
		this.htmlIndexNum = htmlIndexNum;
	}

	

	public String getOldHtmlPath() {
		return oldHtmlPath;
	}

	public void setOldHtmlPath(String oldHtmlPath) {
		this.oldHtmlPath = oldHtmlPath;
	}

	public String getIsHotSale() {
		return isHotSale;
	}

	public void setIsHotSale(String isHotSale) {
		this.isHotSale = isHotSale;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	private String isShelf;		// 是否上架
	private Date shelfTime;		// 申请上架时间
	private Date auditTime;		// 审核时间
	private String auditor;		// 审核人
	private String auditState;		// 审核状态
	private String auditDesc;		// 审核意见
	private Integer goodsClick;		// 浏览数量
	private Integer goodsSalenum;		// 销售数量
	private Integer goodsCollect;		// 收藏数量
	private Integer goodsCommentNum;		// 评论数
	private String createById;		// 创建者
	private String updateById;		// 更新者
	private Date beginShelfTime;		// 开始 申请上架时间
	private Date endShelfTime;		// 结束 申请上架时间
	private Date beginAuditTime;		// 开始 审核时间
	private Date endAuditTime;		// 结束 审核时间
	private Integer beginGoodsSalenum;		// 开始 销售数量

	private Integer endGoodsSalenum;		// 结束 销售数量
	private Integer beginGoodsCollect;		// 开始 收藏数量
	private Integer endGoodsCollect;		// 结束 收藏数量
	private Integer beginGoodsCommentNum;		// 开始 评论数
	private Integer endGoodsCommentNum;		// 结束 评论数
	private Integer chooseNum;		// 商品数量
	private  String cartId;  //购物车ID
	private String purchaserAccountId; //采购商账号ID

	public String getStrs() {
		return strs;
	}

	public void setStrs(String strs) {
		this.strs = strs;
	}

	private String strs;//用于where in 查询使用
	private String categoryPname; //产品大类名称

	private String goodsName;     //产品名称

	private String storeName;     //店铺名称

	private String key; //搜索关键字

	private Double priceGroup; //特定价格组

	private String priceGroupTag; //特定价格组Tag标识

	private String purchaserId; //采购商id

	private String tagId;//属性标签id

	private String endTag; //融合完属性标签

	private List<GoodsClassProperties> goodsClassPropertiesList; //属性标签对应值

	private double promotionPrice;//促销价，只显示，不保存在数据库
	private String mainPicUrl;		//只显示  产品主图

	private String cityid; //城市id

	private String brand;  //品牌

	private Brand brands;//品牌相关信息
	private String goodsSaleFlag; // 根据销售排序
	private String goodsCollectFlag; //根据收藏排序
	private String goodsCommentFlag;  //根据评论排序
	private String goodsClickFlag; //根据浏览数量排序
	private String marketPriceFlag;		// 根据市场价排序

	//TDK
	private String title;//商品title
	private String keyword;//商品keyword
	private String des;//商品的des

	private String contextPath ;//搜索页面使用

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

	public String getSalePoint() {
		return salePoint;
	}

	private String salePoint;//商品卖点

	public void setSalePoint(String salePoint) {
		this.salePoint = salePoint;
	}
	public String getMarketPriceFlag() {
		return marketPriceFlag;
	}

	public void setMarketPriceFlag(String marketPriceFlag) {
		this.marketPriceFlag = marketPriceFlag;
	}

	private String comprehensiveFlag; //综合排序

	private String isSelfSupport;    //是否自营

	private String tagText;//属性标签值

	private List<String> idList;//购物车ID集合
	//只做查询
	private String dealerId; // 经销商ID
	private String dealerName;//经销商名称
	private String kjtAccount; //快捷通账号

	//添加时间的字符串表示方法
	private String createDateStr;


	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getKjtAccount() {
		return kjtAccount;
	}

	public void setKjtAccount(String kjtAccount) {
		this.kjtAccount = kjtAccount;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getTagText() {
		return tagText;
	}

	public void setTagText(String tagText) {
		this.tagText = tagText;
	}

	public String getIsSelfSupport() {
		return isSelfSupport;
	}

	public void setIsSelfSupport(String isSelfSupport) {
		this.isSelfSupport = isSelfSupport;
	}

	public String getComprehensiveFlag() {
		return comprehensiveFlag;
	}

	public void setComprehensiveFlag(String comprehensiveFlag) {
		this.comprehensiveFlag = comprehensiveFlag;
	}

	public Brand getBrands() {
		return brands;
	}

	public void setBrands(Brand brands) {
		this.brands = brands;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getGoodsClickFlag() {
		return goodsClickFlag;
	}

	public void setGoodsClickFlag(String goodsClickFlag) {
		this.goodsClickFlag = goodsClickFlag;
	}

	public String getGoodsSaleFlag() {
		return goodsSaleFlag;
	}

	public void setGoodsSaleFlag(String goodsSaleFlag) {
		this.goodsSaleFlag = goodsSaleFlag;
	}

	public String getGoodsCollectFlag() {
		return goodsCollectFlag;
	}

	public void setGoodsCollectFlag(String goodsCollectFlag) {
		this.goodsCollectFlag = goodsCollectFlag;
	}

	public String getGoodsCommentFlag() {
		return goodsCommentFlag;
	}

	public void setGoodsCommentFlag(String goodsCommentFlag) {
		this.goodsCommentFlag = goodsCommentFlag;
	}

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public List<GoodsClassProperties> getGoodsClassPropertiesList() {
		return goodsClassPropertiesList;
	}

	public void setGoodsClassPropertiesList(List<GoodsClassProperties> goodsClassPropertiesList) {
		this.goodsClassPropertiesList = goodsClassPropertiesList;
	}

	public String getEndTag() {
		return endTag;
	}

	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getPriceGroupTag() {
		return priceGroupTag;
	}

	public void setPriceGroupTag(String priceGroupTag) {
		this.priceGroupTag = priceGroupTag;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	public Double getPriceGroup() {
		return priceGroup;
	}

	public void setPriceGroup(Double priceGroup) {
		this.priceGroup = priceGroup;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public String getCategoryPname() {
		return categoryPname;
	}

	public void setCategoryPname(String categoryPname) {
		this.categoryPname = categoryPname;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	private List<StoreOrderGoods>  storeOrderGoodsList; //订单中的店铺商品信息

	private List<StorePriceGroup> storePriceGroupList; //阶梯价格相关信息

	public List<StorePriceGroup> getStorePriceGroupList() {
		return storePriceGroupList;
	}

	public void setStorePriceGroupList(List<StorePriceGroup> storePriceGroupList) {
		this.storePriceGroupList = storePriceGroupList;
	}

	public StoreGoods() {
		super();
	}

	public StoreGoods(String id){
		super(id);
	}

	@ExcelField(title="店铺ID", align=2, sort=1)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="产品大类", align=2, sort=2)
	public String getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}
	
	@ExcelField(title="产品子类", align=2, sort=3)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="产品编码", align=2, sort=4)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="市场价不能为空")
	@ExcelField(title="市场价", align=2, sort=5)
	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	@NotNull(message="卖价不能为空")
	@ExcelField(title="卖价", align=2, sort=6)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull(message="库存不能为空")
	@ExcelField(title="库存", align=2, sort=7)
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	@ExcelField(title="库存预警", align=2, sort=8)
	public Integer getStockWarning() {
		return stockWarning;
	}

	public void setStockWarning(Integer stockWarning) {
		this.stockWarning = stockWarning;
	}
	
	@ExcelField(title="是否推荐", dictType="yes_no", align=2, sort=9)
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	
	@ExcelField(title="是否促销", dictType="yes_no", align=2, sort=10)
	public String getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(String isPromotion) {
		this.isPromotion = isPromotion;
	}
	
	@ExcelField(title="是否上架", dictType="yes_no", align=2, sort=11)
	public String getIsShelf() {
		return isShelf;
	}

	public void setIsShelf(String isShelf) {
		this.isShelf = isShelf;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="申请上架时间", align=2, sort=12)
	public Date getShelfTime() {
		return shelfTime;
	}

	public void setShelfTime(Date shelfTime) {
		this.shelfTime = shelfTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=13)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@ExcelField(title="审核人", align=2, sort=14)
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@ExcelField(title="审核状态", dictType="audit_state", align=2, sort=15)
	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	@ExcelField(title="审核意见", align=2, sort=16)
	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	
	@NotNull(message="浏览数量不能为空")
	@ExcelField(title="浏览数量", align=2, sort=17)
	public Integer getGoodsClick() {
		return goodsClick;
	}

	public void setGoodsClick(Integer goodsClick) {
		this.goodsClick = goodsClick;
	}
	
	@NotNull(message="销售数量不能为空")
	@ExcelField(title="销售数量", align=2, sort=18)
	public Integer getGoodsSalenum() {
		return goodsSalenum;
	}

	public void setGoodsSalenum(Integer goodsSalenum) {
		this.goodsSalenum = goodsSalenum;
	}
	
	@NotNull(message="收藏数量不能为空")
	@ExcelField(title="收藏数量", align=2, sort=19)
	public Integer getGoodsCollect() {
		return goodsCollect;
	}

	public void setGoodsCollect(Integer goodsCollect) {
		this.goodsCollect = goodsCollect;
	}
	
	@ExcelField(title="评论数", align=2, sort=20)
	public Integer getGoodsCommentNum() {
		return goodsCommentNum;
	}

	public void setGoodsCommentNum(Integer goodsCommentNum) {
		this.goodsCommentNum = goodsCommentNum;
	}
	
	@ExcelField(title="创建者", align=2, sort=22)
	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	@ExcelField(title="更新者", align=2, sort=25)
	public String getUpdateById() {
		return updateById;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}
	
	public Date getBeginShelfTime() {
		return beginShelfTime;
	}

	public void setBeginShelfTime(Date beginShelfTime) {
		this.beginShelfTime = beginShelfTime;
	}
	
	public Date getEndShelfTime() {
		return endShelfTime;
	}

	public void setEndShelfTime(Date endShelfTime) {
		this.endShelfTime = endShelfTime;
	}
		
	public Date getBeginAuditTime() {
		return beginAuditTime;
	}

	public void setBeginAuditTime(Date beginAuditTime) {
		this.beginAuditTime = beginAuditTime;
	}
	
	public Date getEndAuditTime() {
		return endAuditTime;
	}

	public void setEndAuditTime(Date endAuditTime) {
		this.endAuditTime = endAuditTime;
	}
		
	public Integer getBeginGoodsSalenum() {
		return beginGoodsSalenum;
	}

	public void setBeginGoodsSalenum(Integer beginGoodsSalenum) {
		this.beginGoodsSalenum = beginGoodsSalenum;
	}
	
	public Integer getEndGoodsSalenum() {
		return endGoodsSalenum;
	}

	public void setEndGoodsSalenum(Integer endGoodsSalenum) {
		this.endGoodsSalenum = endGoodsSalenum;
	}
		
	public Integer getBeginGoodsCollect() {
		return beginGoodsCollect;
	}

	public void setBeginGoodsCollect(Integer beginGoodsCollect) {
		this.beginGoodsCollect = beginGoodsCollect;
	}
	
	public Integer getEndGoodsCollect() {
		return endGoodsCollect;
	}

	public void setEndGoodsCollect(Integer endGoodsCollect) {
		this.endGoodsCollect = endGoodsCollect;
	}
		
	public Integer getBeginGoodsCommentNum() {
		return beginGoodsCommentNum;
	}

	public void setBeginGoodsCommentNum(Integer beginGoodsCommentNum) {
		this.beginGoodsCommentNum = beginGoodsCommentNum;
	}
	
	public Integer getEndGoodsCommentNum() {
		return endGoodsCommentNum;
	}

	public void setEndGoodsCommentNum(Integer endGoodsCommentNum) {
		this.endGoodsCommentNum = endGoodsCommentNum;
	}
	public List<StoreOrderGoods> getStoreOrderGoodsList() {
		return storeOrderGoodsList;
	}

	public void setStoreOrderGoodsList(List<StoreOrderGoods> storeOrderGoodsList) {
		this.storeOrderGoodsList = storeOrderGoodsList;
	}

	public double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Integer getChooseNum() {
		return chooseNum;
	}

	public void setChooseNum(Integer chooseNum) {
		this.chooseNum = chooseNum;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}

	public String getPageUrl() {
		 if (StringUtils.isNotEmpty(htmlpath)) {
			return pageUrl=htmlpath;
		}
		return "";
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getCreateDateStr() {
		if (dateFormat==null || dateFormat.trim().length()==0) {
			dateFormat="yyyy-MM-dd HH:mm:ss";
		}
		if (createDate!=null) {
			createDateStr=new SimpleDateFormat(dateFormat).format(createDate);
		}
		return createDateStr;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
}