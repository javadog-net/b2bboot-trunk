/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasermainrel.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 采购商主副关系Entity
 * @author wbn
 * @version 2019-07-24
 */
public class PurchaserMasterSlaveRel extends DataEntity<PurchaserMasterSlaveRel> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserMasterId;		// 采购商主号
	private String purchaserSlaveId;		// 采购商子号
	private String opeDate;		// 操作时间
	
	public PurchaserMasterSlaveRel() {
		super();
	}

	public PurchaserMasterSlaveRel(String id){
		super(id);
	}

	@ExcelField(title="采购商主号", align=2, sort=1)
	public String getPurchaserMasterId() {
		return purchaserMasterId;
	}

	public void setPurchaserMasterId(String purchaserMasterId) {
		this.purchaserMasterId = purchaserMasterId;
	}
	
	@ExcelField(title="采购商子号", align=2, sort=2)
	public String getPurchaserSlaveId() {
		return purchaserSlaveId;
	}

	public void setPurchaserSlaveId(String purchaserSlaveId) {
		this.purchaserSlaveId = purchaserSlaveId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="操作时间", align=2, sort=3)
	public String getOpeDate() {
		return opeDate;
	}

	public void setOpeDate(String opeDate) {
		this.opeDate = opeDate;
	}
	
}