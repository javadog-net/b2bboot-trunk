package com.jhmis.modules.shop.entity;

import java.util.List;

/**
 * @Author：hdx
 * @Description: 分类推荐容器表
 * @Date: Created in 20:50 2018/8/6
 * @Modified By
 */
public class RecommendCatContent {
    private String categoryId;
    private String categoryName;
    private String url; //分类图
    private List<RecommendCat> RecommendCats;
    private List<Advert> advertList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Advert> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advert> advertList) {
        this.advertList = advertList;
    }

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

    public List<RecommendCat> getRecommendCats() {
        return RecommendCats;
    }

    public void setRecommendCats(List<RecommendCat> recommendCats) {
        RecommendCats = recommendCats;
    }
}
