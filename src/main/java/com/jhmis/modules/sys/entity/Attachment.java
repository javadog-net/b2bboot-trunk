/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 附件表Entity
 * @author tity
 * @version 2018-07-06
 */
public class Attachment extends DataEntity<Attachment> {
	
	private static final long serialVersionUID = 1L;
	private String attName;		// 文件名称
	private String attMd5;		// 文件MD5
	private String attUrl;		// 文件路径
	private String attThumb;	// 图片缩略图路径
	private String attExt;		// 文件扩展名
	private Long attSize;		// 文件尺寸
	private AttCat attCat;		// 文件分类 父类
	private String userId;		// 用户
	private String userType;		// 用户类型(1,后台用户，2供应商，3前台用户)
	private String attType;		// 附件类型(1图片，2文件，3媒体
	
	public Attachment() {
		super();
	}

	public Attachment(String id){
		super(id);
	}

	public Attachment(AttCat attCat){
		this.attCat = attCat;
	}

	@ExcelField(title="文件名称", align=2, sort=1)
	public String getAttName() {
		return attName;
	}

	public void setAttName(String attName) {
		this.attName = attName;
	}
	
	@ExcelField(title="文件MD5", align=2, sort=2)
	public String getAttMd5() {
		return attMd5;
	}

	public void setAttMd5(String attMd5) {
		this.attMd5 = attMd5;
	}
	
	@ExcelField(title="文件路径", align=2, sort=3)
	public String getAttUrl() {
		return attUrl;
	}

	public void setAttUrl(String attUrl) {
		this.attUrl = attUrl;
	}
	@ExcelField(title="缩略图", align=2, sort=4)
	public String getAttThumb() {
		return attThumb;
	}

	public void setAttThumb(String attThumb) {
		this.attThumb = attThumb;
	}

	@ExcelField(title="扩展名", align=2, sort=5)
	public String getAttExt() {
		return attExt;
	}

	public void setAttExt(String attExt) {
		this.attExt = attExt;
	}
	
	@ExcelField(title="文件尺寸", align=2, sort=6)
	public Long getAttSize() {
		return attSize;
	}

	public void setAttSize(Long attSize) {
		this.attSize = attSize;
	}
	
	public AttCat getAttCat() {
		return attCat;
	}

	public void setAttCat(AttCat attCat) {
		this.attCat = attCat;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getAttType() {
		return attType;
	}

	public void setAttType(String attType) {
		this.attType = attType;
	}
	
}