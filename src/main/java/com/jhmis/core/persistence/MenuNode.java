package com.jhmis.core.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class MenuNode {
    private String id;

    private String parentId;

    private String name;

    private String href;		// 链接

    private String target;		// 目标

    private String icon;		// 图标

    private List<MenuNode> children;

    public MenuNode(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public MenuNode(String id, String name, MenuNode parent) {
        this.id = id;
        this.name = name;
        this.parentId = parent.getId();
    }

    public MenuNode(String id, String name, String parentId, String href, String target, String icon) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.href = href;
        this.target = target;
        this.icon = icon;
    }

    public MenuNode(String id, String name, MenuNode parent, String href, String target, String icon) {
        this.id = id;
        this.name = name;
        this.parentId = parent.getId();;
        this.href = href;
        this.target = target;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }

    //特别指定菜单的根节点id
    @JsonIgnore
    public static String getRootId(){
        return "1";
    }
}
