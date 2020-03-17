/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.dealer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 供应商角色Entity
 * @author tity
 * @version 2018-07-25
 */
public class DealerRole extends DataEntity<DealerRole> {
	
	private static final long serialVersionUID = 1L;
	private String dealerId;		// 供应商id
	private String name;		// 角色名称
	private String useable;		// 是否可用
	private String createById;		// 创建者
	private String updateById;		// 更新者

	private DealerAccount account;		// 供应商账号查询
	private List<DealerMenu> menuList = Lists.newArrayList(); // 拥有菜单列表

    private List<String> menuIdList = Lists.newArrayList();
	public DealerRole() {
		super();
		this.useable= Global.YES;
	}

    public DealerRole(DealerAccount account) {
        this();
	    this.account = account;
    }

    public DealerRole(String id){
		super(id);
        this.useable= Global.YES;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
	@ExcelField(title="角色名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="是否可用", dictType="yes_no", align=2, sort=2)
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}
	
	public String getUpdateById() {
		return updateById;
	}

	public void setUpdateById(String updateById) {
		this.updateById = updateById;
	}

	@JsonIgnore
    public List<DealerMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<DealerMenu> menuList) {
		this.menuList = menuList;
	}

	public List<String> getMenuIdList() {
        //List<DealerMenu> menuList = Lists.newArrayList();
		for (DealerMenu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
		menuList = Lists.newArrayList();
		for (String menuId : menuIdList) {
			DealerMenu menu = new DealerMenu();
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
		for (DealerMenu menu : menuList) {
			if (menu.getPermission()!=null && !"".equals(menu.getPermission())){
				permissions.add(menu.getPermission());
			}
		}
		return permissions;
	}
}