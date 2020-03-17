/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.common.config.Global;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.cms.utils.CmsUtils;

/**
 * 链接管理Entity
 * @author lydia
 * @version 2019-09-06
 */
public class Link extends DataEntity<Link> {

	private static final long serialVersionUID = 1L;
	private String linkGroup;		// 链接分类
	private String linkType;		// 链接类型（'text','logo'）
	private String name;		// 链接名称
	private String url;		// 链接地址
	private String logo;		// Logo
	private String descp;		// 描述
	private Integer sort;		// 排序

	public Link() {
		super();
		this.linkGroup = Global.YES;
	}

	public Link(String id){
		super(id);
	}

	@ExcelField(title="链接分类", align=2, sort=1)
	public String getLinkGroup() {
		return linkGroup;
	}

	public void setLinkGroup(String linkGroup) {
		this.linkGroup = linkGroup;
	}

	@ExcelField(title="链接类型（'text','logo'）", dictType="link_model_type", align=2, sort=2)
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	@ExcelField(title="链接名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title="链接地址", align=2, sort=4)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ExcelField(title="Logo", align=2, sort=5)
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@ExcelField(title="描述", align=2, sort=6)
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	@ExcelField(title="排序", align=2, sort=7)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getLogoSrc() {
		String imgLogo = CmsUtils.formatImageSrcToWeb(this.logo);
		if(StringUtils.isBlank(imgLogo)) return imgLogo;
		imgLogo = imgLogo.replace("/_thumbs", "");
		return imgLogo;
	}

}