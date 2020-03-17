/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 模块字段管理Entity
 * @author lydia
 * @version 2019-08-30
 */
public class CmsModelField extends DataEntity<CmsModelField> {
	
	private static final long serialVersionUID = 1L;
	private String modelId;		// 模型ID
	private String field;		// 字段名称
	private String name;		// 显示名称
	private String tips;		// 提示
	private String css;		// 表单样式名
	private Integer minLength;		// 字符长度最大值
	private Integer maxLength;		// 字符长度最小值
	private String pattern;		// 验证规则
	private String errorTips;		// 错误提示
	private String formType;		// 表单类型
	private String defaultValue;		// 默认值
	private String dataSource;		// 是否来源（1 字典 2 可选项）
	private String valueOptions;		// 可选项
	private String dictionaryId;		// 字典值
	private String setting;		// 字段校验规则（json格式）
	private String formAttribute;		// 表单附加属性（可以添加js事件）
	private String unsetRoleids;		// 禁止设置字段的角色
	private String groupType;		// 字段所在组（分组用于在不同的tab页显示）
	private String isRequire;		// 是否必填（0否1是）
	private String isUnique;		// 值唯一
	private String isSearch;		// 是否作为搜索条件
	private String isFullText;		// 是否作为全站搜索的信息
	private String isPosition;		// 是否在推荐位中作为标签使用
	private String disabled;		// 是否可用（0否1是）
	
	public CmsModelField() {
		super();
	}

	public CmsModelField(String id){
		super(id);
	}

	@ExcelField(title="模型ID", align=2, sort=1)
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@ExcelField(title="字段名称", align=2, sort=2)
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	@ExcelField(title="显示名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="提示", align=2, sort=4)
	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
	
	@ExcelField(title="表单样式名", align=2, sort=5)
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
	
	@ExcelField(title="字符长度最大值", align=2, sort=6)
	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	
	@ExcelField(title="字符长度最小值", align=2, sort=7)
	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	
	@ExcelField(title="验证规则", align=2, sort=8)
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@ExcelField(title="错误提示", align=2, sort=9)
	public String getErrorTips() {
		return errorTips;
	}

	public void setErrorTips(String errorTips) {
		this.errorTips = errorTips;
	}
	
	@ExcelField(title="表单类型", align=2, sort=10)
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	@ExcelField(title="默认值", align=2, sort=11)
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@ExcelField(title="是否来源（1 字典 2 可选项）", dictType="yes_no", align=2, sort=12)
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	@ExcelField(title="可选项", align=2, sort=13)
	public String getValueOptions() {
		return valueOptions;
	}

	public void setValueOptions(String valueOptions) {
		this.valueOptions = valueOptions;
	}
	
	@ExcelField(title="字典值", align=2, sort=14)
	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	
	@ExcelField(title="字段校验规则（json格式）", align=2, sort=15)
	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}
	
	@ExcelField(title="表单附加属性（可以添加js事件）", align=2, sort=16)
	public String getFormAttribute() {
		return formAttribute;
	}

	public void setFormAttribute(String formAttribute) {
		this.formAttribute = formAttribute;
	}
	
	@ExcelField(title="禁止设置字段的角色", align=2, sort=17)
	public String getUnsetRoleids() {
		return unsetRoleids;
	}

	public void setUnsetRoleids(String unsetRoleids) {
		this.unsetRoleids = unsetRoleids;
	}
	
	@ExcelField(title="字段所在组（分组用于在不同的tab页显示）", align=2, sort=18)
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	@ExcelField(title="是否必填（0否1是）", dictType="yes_no", align=2, sort=19)
	public String getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}
	
	@ExcelField(title="值唯一", align=2, sort=20)
	public String getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(String isUnique) {
		this.isUnique = isUnique;
	}
	
	@ExcelField(title="是否作为搜索条件", align=2, sort=21)
	public String getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}
	
	@ExcelField(title="是否作为全站搜索的信息", align=2, sort=22)
	public String getIsFullText() {
		return isFullText;
	}

	public void setIsFullText(String isFullText) {
		this.isFullText = isFullText;
	}
	
	@ExcelField(title="是否在推荐位中作为标签使用", dictType="yes_no", align=2, sort=23)
	public String getIsPosition() {
		return isPosition;
	}

	public void setIsPosition(String isPosition) {
		this.isPosition = isPosition;
	}
	
	@ExcelField(title="是否可用（0否1是）", dictType="yes_no", align=2, sort=24)
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
}