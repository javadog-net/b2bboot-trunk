package com.jhmis.modules.shop.entity;

import java.util.List;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 20:04 2018/8/7
 * @Modified By
 */
public class GoodsClassPropertiesContent {
    private String classId;		// 商品品类ID
    private String name;		// 属性名称
    private List<GoodsClassProperties> GoodsClassPropertiesList;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GoodsClassProperties> getGoodsClassPropertiesList() {
        return GoodsClassPropertiesList;
    }

    public void setGoodsClassPropertiesList(List<GoodsClassProperties> goodsClassPropertiesList) {
        GoodsClassPropertiesList = goodsClassPropertiesList;
    }
}
