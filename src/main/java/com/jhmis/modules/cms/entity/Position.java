/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 位置管理Entity
 * @author lydia
 * @version 2019-09-06
 */
public class Position extends DataEntity<Position> {
	
	private static final long serialVersionUID = 1L;
	private String modelId;		// 模型ID
	private String categoryId;		// 栏目ID
	private String name;		// 推荐位名称
	private Integer maxNum;		// 最大保存条数
	private String thumb;		// 图片
	private Integer sort;		// 排序
	private String descp;		// 描述信息

	private String modelName; // 模型名称  只做显示
	private String categoryName; //蓝名名称，只做显示
	public Position() {
		super();
	}

	public Position(String id){
		super(id);
	}

	@ExcelField(title="模型ID", align=2, sort=1)
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@ExcelField(title="栏目ID", align=2, sort=2)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="推荐位名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="最大保存条数", align=2, sort=4)
	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	@ExcelField(title="图片", align=2, sort=5)
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	@ExcelField(title="排序", align=2, sort=6)
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="描述信息", align=2, sort=7)
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}