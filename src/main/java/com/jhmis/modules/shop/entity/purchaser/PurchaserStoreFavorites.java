/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.purchaser;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 店铺收藏Entity
 * @author hdx
 * @version 2018-08-16
 */
public class PurchaserStoreFavorites extends DataEntity<PurchaserStoreFavorites> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserAccountId;		// 采购商登录账号ID
	private Date favDate;		// 收藏时间
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
    private String storeLogo;		// 店铺logo
	private String logMsg;		// 收藏备注
	
	public PurchaserStoreFavorites() {
		super();
	}

	public PurchaserStoreFavorites(String id){
		super(id);
	}

	@ExcelField(title="采购商登录账号ID", align=2, sort=1)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="收藏时间不能为空")
	@ExcelField(title="收藏时间", align=2, sort=2)
	public Date getFavDate() {
		return favDate;
	}

	public void setFavDate(Date favDate) {
		this.favDate = favDate;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=3)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=4)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    @ExcelField(title="收藏备注", align=2, sort=5)
	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	
}