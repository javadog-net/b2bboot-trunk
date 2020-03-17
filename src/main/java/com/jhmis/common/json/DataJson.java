package com.jhmis.common.json;

import java.util.List;


public class DataJson {
	
	private int total;
	@SuppressWarnings("rawtypes")
	private List rows;
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the list
	 */
	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}
	/**
	 * @param list the list to set
	 */
	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows;
	}
	

}
