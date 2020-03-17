package com.jhmis.modules.iim.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * LayJson--->Data对象
 * 
 * @author Swhite
 * 
 */
public class LayGroupJsonData {
	
	@SuppressWarnings("rawtypes")
	private HashMap owner = new HashMap(); // 我的信息

	
	private List<Friend> list = new ArrayList<Friend>(); // 群组中的成员列表


	@SuppressWarnings("rawtypes")
	public void setOwner(HashMap owner) {
		this.owner = owner;
	}


	@SuppressWarnings("rawtypes")
	public HashMap getOwner() {
		return owner;
	}


	public void setList(List<Friend> list) {
		this.list = list;
	}


	public List<Friend> getList() {
		return list;
	}


}
