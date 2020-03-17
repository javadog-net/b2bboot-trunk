/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 内容图片集Entity
 * @author lydia
 * @version 2019-12-11
 */
public class InfoImg extends DataEntity<InfoImg> {
	
	private static final long serialVersionUID = 1L;
	private String infoId;		// info_id
	private String img;		// img
	private String title;		// title
	private String content;		// content
	private String ordernum;		// ordernum
	
	public InfoImg() {
		super();
	}

	public InfoImg(String id){
		super(id);
	}

	@ExcelField(title="info_id", align=2, sort=1)
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
	@ExcelField(title="img", align=2, sort=2)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@ExcelField(title="title", align=2, sort=3)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="content", align=2, sort=4)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="ordernum", align=2, sort=5)
	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	
}