/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.purchaser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhmis.core.persistence.TreeEntity;

import java.util.List;

/**
 * 采购商菜单Entity
 * @author tity
 * @version 2018-07-25
 */
public class PurchaserMenu extends TreeEntity<PurchaserMenu> {
	
	private static final long serialVersionUID = 1L;
	private String href;		// 链接
	private String target;		// 目标
	private String icon;		// 图标
	private String isShow;		// 显示
	private String permission;		// 权限标识
	private String menuType;		// 菜单类型

    private String accountId; //采购商账号id
	
	public PurchaserMenu() {
		super();
        this.sort = 30;
        this.isShow = "1";
        this.menuType="1";
	}

    @JsonIgnore
    public static void sortList(List<PurchaserMenu> list, List<PurchaserMenu> sourcelist, String parentId, boolean cascade){
        for (int i=0; i<sourcelist.size(); i++){
            PurchaserMenu e = sourcelist.get(i);
            if (e.getParent()!=null && e.getParent().getId()!=null
                    && e.getParent().getId().equals(parentId)){
                list.add(e);
                if (cascade){
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j=0; j<sourcelist.size(); j++){
                        PurchaserMenu child = sourcelist.get(j);
                        if (child.getParent()!=null && child.getParent().getId()!=null
                                && child.getParent().getId().equals(e.getId())){
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @JsonIgnore
    public static String getRootId(){
        return "1";
    }
	public PurchaserMenu(String id){
		super(id);
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
	
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	public  PurchaserMenu getParent() {
			return parent;
	}
	
	@Override
	public void setParent(PurchaserMenu parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

    @JsonIgnore
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}