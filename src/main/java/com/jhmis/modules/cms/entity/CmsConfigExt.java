/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 内容管理-水印功能Entity
 * @author lydia
 * @version 2019-09-02
 */
public class CmsConfigExt extends DataEntity<CmsConfigExt> {
	
	private static final long serialVersionUID = 1L;
	private String types;		// 水印类型（img图片水印 txt文本水印）
	private String imgMinWidth;		// 图片最小宽度
	private String imgMinHeight;		// 图片最小高度
	private String imgPath;		// 图片路径
	private String imgLocation;		// 图片水印位置
	private String imgLeft;		// 水平偏移
	private String imgTop;		// 垂直偏移
	private String imgAlpha;		// 透明度
	private String txtMinWidth;		// 添加文本水印的最小图片宽度
	private String txtMinHeight;		// 添加文本水印的最小图片高度
	private String txt;		// 文本水印内容
	private String txtLocation;		// 文本水印位置
	private String txtLeft;		// 文本水印水平偏移
	private String txtTop;		// 文本水印垂直偏移
	private String txtAlpha;		// 文本水印透明度
	private String txtFontSize;		// 字体大小
	private String txtFontColor;		// 字体颜色
	private String txtFontName;		// 字体名称
	
	public CmsConfigExt() {
		super();
	}

	public CmsConfigExt(String id){
		super(id);
	}

	@ExcelField(title="水印类型（img图片水印 txt文本水印）", align=2, sort=1)
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	
	@ExcelField(title="图片最小宽度", align=2, sort=2)
	public String getImgMinWidth() {
		return imgMinWidth;
	}

	public void setImgMinWidth(String imgMinWidth) {
		this.imgMinWidth = imgMinWidth;
	}
	
	@ExcelField(title="图片最小高度", align=2, sort=3)
	public String getImgMinHeight() {
		return imgMinHeight;
	}

	public void setImgMinHeight(String imgMinHeight) {
		this.imgMinHeight = imgMinHeight;
	}
	
	@ExcelField(title="图片路径", align=2, sort=4)
	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	@ExcelField(title="图片水印位置", align=2, sort=5)
	public String getImgLocation() {
		return imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
	
	@ExcelField(title="水平偏移", align=2, sort=6)
	public String getImgLeft() {
		return imgLeft;
	}

	public void setImgLeft(String imgLeft) {
		this.imgLeft = imgLeft;
	}
	
	@ExcelField(title="垂直偏移", align=2, sort=7)
	public String getImgTop() {
		return imgTop;
	}

	public void setImgTop(String imgTop) {
		this.imgTop = imgTop;
	}
	
	@ExcelField(title="透明度", align=2, sort=8)
	public String getImgAlpha() {
		return imgAlpha;
	}

	public void setImgAlpha(String imgAlpha) {
		this.imgAlpha = imgAlpha;
	}
	
	@ExcelField(title="添加文本水印的最小图片宽度", align=2, sort=9)
	public String getTxtMinWidth() {
		return txtMinWidth;
	}

	public void setTxtMinWidth(String txtMinWidth) {
		this.txtMinWidth = txtMinWidth;
	}
	
	@ExcelField(title="添加文本水印的最小图片高度", align=2, sort=10)
	public String getTxtMinHeight() {
		return txtMinHeight;
	}

	public void setTxtMinHeight(String txtMinHeight) {
		this.txtMinHeight = txtMinHeight;
	}
	
	@ExcelField(title="文本水印内容", align=2, sort=11)
	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	
	@ExcelField(title="文本水印位置", align=2, sort=12)
	public String getTxtLocation() {
		return txtLocation;
	}

	public void setTxtLocation(String txtLocation) {
		this.txtLocation = txtLocation;
	}
	
	@ExcelField(title="文本水印水平偏移", align=2, sort=13)
	public String getTxtLeft() {
		return txtLeft;
	}

	public void setTxtLeft(String txtLeft) {
		this.txtLeft = txtLeft;
	}
	
	@ExcelField(title="文本水印垂直偏移", align=2, sort=14)
	public String getTxtTop() {
		return txtTop;
	}

	public void setTxtTop(String txtTop) {
		this.txtTop = txtTop;
	}
	
	@ExcelField(title="文本水印透明度", align=2, sort=15)
	public String getTxtAlpha() {
		return txtAlpha;
	}

	public void setTxtAlpha(String txtAlpha) {
		this.txtAlpha = txtAlpha;
	}
	
	@ExcelField(title="字体大小", align=2, sort=16)
	public String getTxtFontSize() {
		return txtFontSize;
	}

	public void setTxtFontSize(String txtFontSize) {
		this.txtFontSize = txtFontSize;
	}
	
	@ExcelField(title="字体颜色", align=2, sort=17)
	public String getTxtFontColor() {
		return txtFontColor;
	}

	public void setTxtFontColor(String txtFontColor) {
		this.txtFontColor = txtFontColor;
	}
	
	@ExcelField(title="字体名称", align=2, sort=18)
	public String getTxtFontName() {
		return txtFontName;
	}

	public void setTxtFontName(String txtFontName) {
		this.txtFontName = txtFontName;
	}
	
}