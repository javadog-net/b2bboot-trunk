/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.purchaser;


import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 采购商角色Entity
 * @author tity
 * @version 2018-07-25
 */
public class PurchaserRole extends DataEntity<PurchaserRole> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserId;		// 采购商id
	private String name;		// 角色名称
	private String useable;		// 是否可用
	private String createById;		// 创建者
	private String updateById;		// 更新者

    private PurchaserAccount account;		// 供应商账号查询

    private List<PurchaserMenu> menuList = Lists.newArrayList(); // 拥有菜单列表
    private List<String> menuIdList = Lists.newArrayList();//拥有菜单列表
	public PurchaserRole() {
		super();
        this.useable= Global.YES;
	}
    public PurchaserRole(PurchaserAccount account) {
        this();
        this.account = account;
    }
	public PurchaserRole(String id){
		super(id);
        this.useable= Global.YES;
	}

	@ExcelField(title="采购商id", align=2, sort=1)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="角色名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="是否可用", dictType="yes_no", align=2, sort=3)
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}
	
	@ExcelField(title="创建者", align=2, sort=5)
	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	@ExcelField(title="更新者", align=2, sort=7)
	public String getUpdateById() {
		return updateById;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}

    public List<PurchaserMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<PurchaserMenu> menuList) {
        this.menuList = menuList;
    }

    public List<String> getMenuIdList() {
        //List<String> menuIdList = Lists.newArrayList();
        for (PurchaserMenu menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
	    this.menuIdList = menuIdList;
        menuList = Lists.newArrayList();
        for (String menuId : menuIdList) {
            PurchaserMenu menu = new PurchaserMenu();
            menu.setId(menuId);
            menuList.add(menu);
        }
    }

    public String getMenuIds() {
        return StringUtils.join(getMenuIdList(), ",");
    }

    public void setMenuIds(String menuIds) {
        menuList = Lists.newArrayList();
        if (menuIds != null){
            String[] ids = StringUtils.split(menuIds, ",");
            setMenuIdList(Lists.newArrayList(ids));
        }
    }
    /**
     * 获取权限字符串列表
     */
    public List<String> getPermissions() {
        List<String> permissions = Lists.newArrayList();
        for (PurchaserMenu menu : menuList) {
            if (menu.getPermission()!=null && !"".equals(menu.getPermission())){
                permissions.add(menu.getPermission());
            }
        }
        return permissions;
    }
}