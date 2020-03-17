package com.jhmis.modules.shop.entity;

import java.util.List;

public class StoreGoodsCategory {
    private String categoryId; //分类id

    private String categoryName;  //分类名称

    private List<StoreGoods> storeGoodsList; //店铺商品列表

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<StoreGoods> getStoreGoodsList() {
        return storeGoodsList;
    }

    public void setStoreGoodsList(List<StoreGoods> storeGoodsList) {
        this.storeGoodsList = storeGoodsList;
    }
}
