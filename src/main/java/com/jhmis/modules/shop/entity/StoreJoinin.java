/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 店铺申请Entity
 * @author tity
 * @version 2018-07-22
 */
public class StoreJoinin extends DataEntity<StoreJoinin> {
	
	private static final long serialVersionUID = 1L;
	private String dealerId;		// 供应商ID
	private String storeName;		// 店铺名称
	private Integer applyYear;		// 申请时长
	private String auditState;		// 审核状态
	private Date auditTime;		// 审核时间
	private String auditor;		// 审核人
	private String auditDesc;		// 审核意见

    private String createById;		// 创建者
    private String updateById;		// 更新者

    //以下4个字段用于供应商信息显示，用于查看店铺申请
    private String companyCode;		// 供应商编码
    private String companyName;		// 供应商名称
    private String contacts;		// 公司联系人
    private String tel;		// 公司联系人电话
    //
	public StoreJoinin() {
		super();
	}

	public StoreJoinin(String id){
		super(id);
	}


	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

    @NotNull(message="店铺名称不能为空")
    @Length(min=1, max=64, message="店铺名称长度必须介于 1 和 64 之间")
	@ExcelField(title="店铺名称", align=2, sort=5)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@NotNull(message="申请时长不能为空")
    @Range(min=1,max=20, message="申请时长必须介于 1年 和 20年之间")
	@ExcelField(title="申请时长", align=2, sort=6)
	public Integer getApplyYear() {
		return applyYear;
	}

	public void setApplyYear(Integer applyYear) {
		this.applyYear = applyYear;
	}
	
	@ExcelField(title="审核状态", dictType="audit_state", align=2, sort=7)
	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=8)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@ExcelField(title="审核人", align=2, sort=9)
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@ExcelField(title="审核意见", align=2, sort=10)
	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

    @ExcelField(title="供应商编码", align=2, sort=1)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @ExcelField(title="供应商名称", align=2, sort=2)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @ExcelField(title="联系人", align=2, sort=3)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @ExcelField(title="电话", align=2, sort=4)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreateById() {
        return createById;
    }

    public void setCreateById(String createById) {
        this.createById = createById;
    }

    public String getUpdateById() {
        return updateById;
    }

    public void setUpdateById(String updateById) {
        this.updateById = updateById;
    }
}