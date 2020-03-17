/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 模型管理Entity
 * @author lydia
 * @version 2019-08-29
 */
public class CmsModel extends DataEntity<CmsModel> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 模型名称
	private String tableName;		// 表名
	private String disabled;		// 是否可用（0否1是）
	private String categoryTemplate;		// 栏目模板
	private String listTemplate;		// 列表页模板
	private String contentTemplate;		// 文章内容模板
	private String mcategoryTemplate;		// 手机栏目模板
	private String mlistTemplate;		// 手机列表页模板
	private String mcontentTemplate;		// 手机内容页模板
	private String isInstall;		// 是否安装（0否1是）
	private String sort;		// 排序
	
	public CmsModel() {
		super();
		disabled = Global.YES;
		isInstall = Global.YES;  //因为现在只有一个模型，是否可用和是否安装都默认设置成1
	}

	public CmsModel(String id){
		super(id);
	}

	@ExcelField(title="模型名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="表名", align=2, sort=2)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@ExcelField(title="是否可用（0否1是）", dictType="yes_no", align=2, sort=4)
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	@ExcelField(title="栏目模板", align=2, sort=5)
	public String getCategoryTemplate() {
		return categoryTemplate;
	}

	public void setCategoryTemplate(String categoryTemplate) {
		this.categoryTemplate = categoryTemplate;
	}
	
	@ExcelField(title="列表页模板", align=2, sort=6)
	public String getListTemplate() {
		return listTemplate;
	}

	public void setListTemplate(String listTemplate) {
		this.listTemplate = listTemplate;
	}
	
	@ExcelField(title="文章内容模板", align=2, sort=7)
	public String getContentTemplate() {
		return contentTemplate;
	}

	public void setContentTemplate(String contentTemplate) {
		this.contentTemplate = contentTemplate;
	}
	
	@ExcelField(title="手机栏目模板", align=2, sort=8)
	public String getMcategoryTemplate() {
		return mcategoryTemplate;
	}

	public void setMcategoryTemplate(String mcategoryTemplate) {
		this.mcategoryTemplate = mcategoryTemplate;
	}
	
	@ExcelField(title="手机列表页模板", align=2, sort=9)
	public String getMlistTemplate() {
		return mlistTemplate;
	}

	public void setMlistTemplate(String mlistTemplate) {
		this.mlistTemplate = mlistTemplate;
	}
	
	@ExcelField(title="手机内容页模板", align=2, sort=10)
	public String getMcontentTemplate() {
		return mcontentTemplate;
	}

	public void setMcontentTemplate(String mcontentTemplate) {
		this.mcontentTemplate = mcontentTemplate;
	}
	
	@ExcelField(title="是否安装（0否1是）", align=2, sort=11)
	public String getIsInstall() {
		return isInstall;
	}

	public void setIsInstall(String isInstall) {
		this.isInstall = isInstall;
	}
	
	@ExcelField(title="排序", align=2, sort=12)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}