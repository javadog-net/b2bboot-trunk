/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.entity;

import org.hibernate.validator.constraints.Length;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.DataEntity;

/**
 * 业务表字段Entity
 * @author jhmis
 * @version 2016-10-15
 */
public class DbTableColumn extends DataEntity<DbTableColumn> {
	
	private static final long serialVersionUID = 1L;
	private String name; 		// 列名
	private String comments;	// 描述
	private String jdbcType;	// JDBC类型


	public DbTableColumn() {
		super();
	}



	@Length(min=1, max=200)
	public String getName() {
		return StringUtils.lowerCase(name);
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getJdbcType() {
		return StringUtils.lowerCase(jdbcType);
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}




	/**
	 * 获取列名和说明
	 * @return
	 */
	public String getNameAndComments() {
		return getName() + (comments == null ? "" : "  :  " + comments);
	}
	
	/**
	 * 获取字符串长度
	 * @return
	 */
	public String getDataLength(){
		String[] ss = StringUtils.split(StringUtils.substringBetween(getJdbcType(), "(", ")"), ",");
		if (ss != null && ss.length == 1){// && "String".equals(getJavaType())){
			return ss[0];
		}
		return "0";
	}


}


