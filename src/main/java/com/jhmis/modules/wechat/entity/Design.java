/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.jhmis.core.persistence.BaseEntity;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 免费设计Entity
 * @author abc
 * @version 2019-01-24
 */
public class Design extends BaseEntity<Design>  {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String phone;		// 电话
	private Date time;		// 生成时间
	private String source;		// 来源
	private String replyPerson;		// 接口人
	private Date replyTime;		// 处理时间
	private String result;		// 处理结果
	
	public Design() {
		super();
	}

	public Design(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="电话", align=2, sort=2)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生成时间", align=2, sort=3)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@ExcelField(title="来源", align=2, sort=4)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@ExcelField(title="接口人", align=2, sort=5)
	public String getReplyPerson() {
		return replyPerson;
	}

	public void setReplyPerson(String replyPerson) {
		this.replyPerson = replyPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="处理时间不能为空")
	@ExcelField(title="处理时间", align=2, sort=6)
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	@ExcelField(title="最终结果", align=2, sort=7)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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
			this.replyPerson = user.getName();
		}
		this.replyTime = new Date();
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.replyPerson = user.getName();
		}
		this.replyTime = new Date();
	}
	
}