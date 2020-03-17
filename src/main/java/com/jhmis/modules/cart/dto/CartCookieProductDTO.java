package com.jhmis.modules.cart.dto;

import com.jhmis.modules.shop.entity.GoodsClassProperties;

import java.io.Serializable;
import java.util.List;

/**
 * cookies 中存储的商品数据
 * @author wzh
 * @data 2017/4/5
 */
public class CartCookieProductDTO implements Serializable {

    private static final long serialVersionUID = -8029066174084273697L;
    /**
     *  商品编号
     *  */
    private String productUuid;
    /**
     *  商品SKUNo
     *  */
    private String skuNo;
    /**
     *  库存
     *  */
    private int stock;
    /**
     *  判断是否选中  1选中    0未选中
     *  */
    private int checked = 1;
    /**
     *  商品在前台购物车展示的位置(以免刷新的时候，数据乱串)
     *  */
    private int position = 0;
    /**
     *  商品类型
     *  */
    private String type;
    /**
     *  在数据库中是否存在
     *  */
    private boolean esistedInDb = false;
    /**
     * 商品名称
     */
    private String goodsName;

    private String storeId;		// 店铺ID
    private String storeName;		// 店铺名称
    private String mainPicUrl;		// 产品主图
    private Integer chooseNum;		// 商品数量
    private String storeGoodsId;		// 店铺商品ID
    private String productGroupCode;		// 产品组编码
    private String productGroupName;		// 产品组名称
    private double price; //产品价格
    /**
     * 属性标签对应值
     */
    private List<GoodsClassProperties> goodsClassPropertiesList;

    private double totalPrice;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(String productUuid) {
        this.productUuid = productUuid;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEsistedInDb() {
        return esistedInDb;
    }

    public void setEsistedInDb(boolean esistedInDb) {
        this.esistedInDb = esistedInDb;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    public Integer getChooseNum() {
        return chooseNum;
    }

    public void setChooseNum(Integer chooseNum) {
        this.chooseNum = chooseNum;
    }

    public String getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(String storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
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

    public List<GoodsClassProperties> getGoodsClassPropertiesList() {
        return goodsClassPropertiesList;
    }

    public void setGoodsClassPropertiesList(List<GoodsClassProperties> goodsClassPropertiesList) {
        this.goodsClassPropertiesList = goodsClassPropertiesList;
    }
}

