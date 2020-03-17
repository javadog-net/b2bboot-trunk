/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 工贸城市Entity
 * @author tity
 * @version 2018-07-22
 */
public class GmCity extends DataEntity<GmCity> {
	
	private static final long serialVersionUID = 1L;
	private String cityId;		// city_id
	private String gmId;		// gm_id
	
	public GmCity() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public GmCity(String id){
		super(id);
	}

	@ExcelField(title="city_id", align=2, sort=1)
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@ExcelField(title="gm_id", align=2, sort=2)
	public String getGmId() {
		return gmId;
	}

	public void setGmId(String gmId) {
		this.gmId = gmId;
	}
	
}