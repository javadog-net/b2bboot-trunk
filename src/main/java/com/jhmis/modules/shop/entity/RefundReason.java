/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 退货原因管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class RefundReason extends DataEntity<RefundReason> {
	
	private static final long serialVersionUID = 1L;
	private String refundType;		// 退货方式
	private String reasonInfo;		// 原因内容
	private Integer sort;		// 排序
	private Date updateTime;		// 更新时间
	
	public RefundReason() {
		super();
	}

	public RefundReason(String id){
		super(id);
	}

	@ExcelField(title="退货方式", dictType="refund_type", align=2, sort=1)
	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	
	@ExcelField(title="原因内容", align=2, sort=2)
	public String getReasonInfo() {
		return reasonInfo;
	}

	public void setReasonInfo(String reasonInfo) {
		this.reasonInfo = reasonInfo;
	}
	
	@ExcelField(title="排序", align=2, sort=3)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	@ExcelField(title="更新时间", align=2, sort=4)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}