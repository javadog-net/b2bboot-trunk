/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * cms配置管理表Entity
 * @author lydia
 * @version 2019-08-29
 */
public class CmsConfig extends DataEntity<CmsConfig> {
	
	private static final long serialVersionUID = 1L;
	private String name="b2b";		// 系统名称
	private String templetStyle;		// 模板风格
	private String indexPath;		// 首页生成规则
	private String cmsCategoryPath;		// 栏目页生成规则
	private String infoPath;		// 内容页生成规则
	private String htmlPath;		// html生成路径
	private String mobilePath;		// 移动页生成文件夹名
	private String categoryPath;		// 商品分类页生成规则
	private String productPath;		// 商品页生产规则
	private String allowComment;		// 是否允许评论（0否1是）
	private String isAnonymous;		// 是否匿名评论（0否1是）
	private String logo ;  // logo

	private String oldIndexPath;    //不在数据库中使用，仅做判断
	
	public CmsConfig() {
		super();
		this.allowComment = Global.NO;
		this.isAnonymous = Global.NO;
	}

	public CmsConfig(String id){
		super(id);
	}

	@ExcelField(title="系统名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="模板风格", align=2, sort=2)
	public String getTempletStyle() {
		return templetStyle;
	}

	public void setTempletStyle(String templetStyle) {
		this.templetStyle = templetStyle;
	}
	
	@ExcelField(title="首页生成规则", align=2, sort=3)
	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}
	
	@ExcelField(title="栏目页生成规则", align=2, sort=4)
	public String getCmsCategoryPath() {
		return cmsCategoryPath;
	}

	public void setCmsCategoryPath(String cmsCategoryPath) {
		this.cmsCategoryPath = cmsCategoryPath;
	}
	
	@ExcelField(title="内容页生成规则", align=2, sort=5)
	public String getInfoPath() {
		return infoPath;
	}

	public void setInfoPath(String infoPath) {
		this.infoPath = infoPath;
	}
	
	@ExcelField(title="html生成路径", align=2, sort=6)
	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}
	
	@ExcelField(title="移动页生成文件夹名", align=2, sort=7)
	public String getMobilePath() {
		return mobilePath;
	}

	public void setMobilePath(String mobilePath) {
		this.mobilePath = mobilePath;
	}
	
	@ExcelField(title="商品分类页生成规则", align=2, sort=8)
	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	
	@ExcelField(title="商品页生产规则", align=2, sort=9)
	public String getProductPath() {
		return productPath;
	}

	public void setProductPath(String productPath) {
		this.productPath = productPath;
	}
	
	@ExcelField(title="是否允许评论（0否1是）", dictType="yes_no", align=2, sort=10)
	public String getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(String allowComment) {
		this.allowComment = allowComment;
	}
	
	@ExcelField(title="是否匿名评论（0否1是）", dictType="yes_no", align=2, sort=11)
	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getOldIndexPath() {
		return oldIndexPath;
	}

	public void setOldIndexPath(String oldIndexPath) {
		this.oldIndexPath = oldIndexPath;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}