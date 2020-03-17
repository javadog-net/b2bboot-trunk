package com.jhmis.modules.shop.entity;

import java.util.List;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 12:03 2018/8/18
 * @Modified By
 */
public class CategoryNode {
    private String id;
    private String parentId;
    private String name;
    private String icon_url;
    private String classId;
    private Integer sort;
    private List<CategoryNode> childNode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CategoryNode(String id, String parentId, String name, String icon_url, String classId, Integer sort) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.icon_url = icon_url;
        this.classId = classId;
        this.sort = sort;
    }

    public CategoryNode(String parentId, String name, String icon_url, String classId, Integer sort) {
        this.parentId = parentId;
        this.name = name;
        this.icon_url = icon_url;
        this.classId = classId;
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }


    public List<CategoryNode> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<CategoryNode> childNode) {
        this.childNode = childNode;
    }
}
