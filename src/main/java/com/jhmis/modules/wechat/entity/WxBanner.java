/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.core.persistence.BaseEntity;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * bannerEntity
 * @author abc
 * @version 2019-01-25
 */
public class WxBanner extends BaseEntity<WxBanner> {
	
	private static final long serialVersionUID = 1L;
	private String classify;		// banner分类
	private String url;		// 地址
	private String type; //类型 1-会议，2-非会议
	private String link;		// 链接
	private String createPerson;		// 创建人
	private Date createTime;		// 创建时间

	
	public WxBanner() {
		super();
	}

	public WxBanner(String id){
		super(id);
	}

	@ExcelField(title="banner分类id", align=2, sort=1)
	public String getClassify() {
		return classify;
	}

	public void setClassify(String classifyId) {
		this.classify = classifyId;
	}
	
	@ExcelField(title="地址", align=2, sort=2)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	@ExcelField(title="类型（ 1-会议，2-非会议）", align=2, sort=3)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(title="链接", align=2, sort=4)
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@ExcelField(title="创建人", align=2, sort=5)
	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=6)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		if (!this.isNewRecord){
			if(this.getIdType().equals(IDTYPE_UUID)){
				setId(IdGen.uuid());
			}else if(this.getIdType().equals(IDTYPE_AUTO)){
				//使用自增长不需要设置主键
			}
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.createPerson = user.getName();
		}
		this.createTime = new Date();
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.createPerson = user.getName();
		}
		this.createTime = new Date();
	}
	
}