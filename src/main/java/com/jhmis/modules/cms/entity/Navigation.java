/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jhmis.common.config.Global;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.TreeEntity;

import java.util.List;

/**
 * 导航管理Entity
 * @author lydia
 * @version 2019-09-05
 */
public class Navigation extends TreeEntity<Navigation> {
	
	private static final long serialVersionUID = 1L;
	private String parentIds;		// 所有上级ID，英文逗号分割
	private String linkType;		// （0空 1内容模块 2外部链接 3 产品模块 ）
	private String nType;		// 导航类型（pc、mobile）
	private String position;		// 导航位置（顶部导航、底部导航）
	private String thumb;		// 导航图标
	private String name;		// 导航名称
	private String linkKind;		// 链接到栏目还是具体内容
	private String linkId;		// 栏目ID或者内容ID
	private String linkName;		// 栏目页名称或内容页标题
	private String linkMark;		// 内容标识、页面标识
	private String linkUrl;		// 链接地址
	private String isShow;		// 导航栏显示（0否1是）
	private String isLink;		// 是否可点（0否1是）
	private String isTargetBlank;		// 是否在新窗口中打开（0否1是）
	private String isExtend;		// 是否动态扩展栏目（0否1是）
	private Integer sort;		// 排序
	private String pageUrl;   //链接地址

	private String sortType;  //排序方式 非数据库字段，只做判断 up  down
	
	public Navigation() {
		super();
		this.isShow = Global.YES;
		this.isLink = Global.YES;
		this.isTargetBlank = Global.YES;
		this.isExtend = Global.NO;
	}

	public Navigation(String id){
		super(id);
	}

	@JsonBackReference
	@ExcelField(title="直接上级导航ID", fieldType=Navigation.class, value="parent.name", align=2, sort=1)
	public Navigation getParent() {
		return parent;
	}

	public void setParent(Navigation parent) {
		this.parent = parent;
	}

	@ExcelField(title="所有上级ID，英文逗号分割", align=2, sort=2)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@ExcelField(title="（0空 1内容模块 2外部链接 3 产品模块 ）", align=2, sort=3)
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	@ExcelField(title="导航类型（pc、mobile）", align=2, sort=4)
	public String getNType() {
		return nType;
	}

	public void setNType(String nType) {
		this.nType = nType;
	}
	
	@ExcelField(title="导航位置（顶部导航、底部导航）", align=2, sort=5)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="导航图标", align=2, sort=6)
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	@ExcelField(title="导航名称", align=2, sort=7)
	public String getName() {
		if("0".equals(id))
			return "顶级导航";
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="链接到栏目还是具体内容", align=2, sort=8)
	public String getLinkKind() {
		return linkKind;
	}

	public void setLinkKind(String linkKind) {
		this.linkKind = linkKind;
	}
	
	@ExcelField(title="栏目ID或者内容ID", align=2, sort=9)
	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	
	@ExcelField(title="栏目页名称或内容页标题", align=2, sort=10)
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	
	@ExcelField(title="内容标识、页面标识", align=2, sort=11)
	public String getLinkMark() {
		return linkMark;
	}

	public void setLinkMark(String linkMark) {
		this.linkMark = linkMark;
	}
	
	@ExcelField(title="外部链接地址", align=2, sort=12)
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	@ExcelField(title="导航栏显示（0否1是）", dictType="yes_no", align=2, sort=13)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@ExcelField(title="是否可点（0否1是）", dictType="yes_no", align=2, sort=14)
	public String getIsLink() {
		return isLink;
	}

	public void setIsLink(String isLink) {
		this.isLink = isLink;
	}
	
	@ExcelField(title="是否在新窗口中打开（0否1是）", dictType="yes_no", align=2, sort=15)
	public String getIsTargetBlank() {
		return isTargetBlank;
	}

	public void setIsTargetBlank(String isTargetBlank) {
		this.isTargetBlank = isTargetBlank;
	}
	
	@ExcelField(title="是否动态扩展栏目（0否1是）", dictType="yes_no", align=2, sort=16)
	public String getIsExtend() {
		return isExtend;
	}

	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
	}
	
	@ExcelField(title="排序", align=2, sort=17)
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public static void sortList(List<Navigation> list, List<Navigation> sourcelist, String parentId){
		for (int i=0; i<sourcelist.size(); i++){
			Navigation e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<sourcelist.size(); j++){
					Navigation child = sourcelist.get(j);
					if (child.getParent()!=null && child.getParent().getId()!=null
							&& child.getParent().getId().equals(e.getId())){
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}