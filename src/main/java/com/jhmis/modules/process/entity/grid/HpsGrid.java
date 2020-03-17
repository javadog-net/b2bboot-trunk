/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.grid;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 省市区匹配工贸Entity
 * @author mll
 * @version 2019-09-25
 */
public class HpsGrid extends DataEntity<HpsGrid> {
	
	private static final long serialVersionUID = 1L;
	private String centerCode;		// 中心编码
	private String centerName;		// 中心名称
	private String gridCenterCode;		// 网格小微编码
	private String gridCenterName;		// 网格小微名称
	private String gridCode;		// 网格编码
	private String gridName;		// 网格名称
	private String districtCode;		// 三级行政区编码
	private String cityCode;		// 二级行政区编码（直辖市为一级）
	private String cityName;		// 二级行政区名称（直辖市为一级）
	
	public HpsGrid() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public HpsGrid(String id){
		super(id);
	}

	@ExcelField(title="中心编码", align=2, sort=1)
	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
	
	@ExcelField(title="中心名称", align=2, sort=2)
	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	
	@ExcelField(title="网格小微编码", align=2, sort=3)
	public String getGridCenterCode() {
		return gridCenterCode;
	}

	public void setGridCenterCode(String gridCenterCode) {
		this.gridCenterCode = gridCenterCode;
	}
	
	@ExcelField(title="网格小微名称", align=2, sort=4)
	public String getGridCenterName() {
		return gridCenterName;
	}

	public void setGridCenterName(String gridCenterName) {
		this.gridCenterName = gridCenterName;
	}
	
	@ExcelField(title="网格编码", align=2, sort=5)
	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}
	
	@ExcelField(title="网格名称", align=2, sort=6)
	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	
	@ExcelField(title="三级行政区编码", align=2, sort=7)
	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	@ExcelField(title="二级行政区编码（直辖市为一级）", align=2, sort=8)
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@ExcelField(title="二级行政区名称（直辖市为一级）", align=2, sort=9)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}