/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.TGridArea;
import com.jhmis.modules.customer.mapper.TGridAreaMapper;

/**
 * 网格码表Service
 * @author hdx
 * @version 2020-02-25
 */
@Service
@Transactional(readOnly = true)
public class TGridAreaService extends CrudService<TGridAreaMapper, TGridArea> {
	
	@Autowired
	TGridAreaMapper TGridAreaMapper;
	
	public TGridArea get(String id) {
		return super.get(id);
	}
	
	public List<TGridArea> findList(TGridArea tGridArea) {
		return super.findList(tGridArea);
	}
	
	public Page<TGridArea> findPage(Page<TGridArea> page, TGridArea tGridArea) {
		return super.findPage(page, tGridArea);
	}
	
	@Transactional(readOnly = false)
	public void save(TGridArea tGridArea) {
		super.save(tGridArea);
	}
	
	@Transactional(readOnly = false)
	public void delete(TGridArea tGridArea) {
		super.delete(tGridArea);
	}

	/**
	 * 根据工贸编码，获取工贸名称
	 * @param gmCode
	 * @return
	 */
	public String findNameByCode(String gmCode) {
		return TGridAreaMapper.findNameByCode(gmCode);
	}
	
	/**
	 * 获取省份接口
	 * @return
	 */
	public List<String> getProvince(){
		return TGridAreaMapper.getProvince();
	}
	
	/**
	 * 根据省名称获取城市
	 * @param province 省份
	 * @return
	 */
	public List<String> getCityByProvince(String province){
		return TGridAreaMapper.getCityByProvince(province);
	}
	
	/**
	 * 根据城市名称获取区县
	 * @param province 省份
	 * @param city 城市
	 * @return
	 */
	public List<String> getAreaByCity(String province,String city){
		return TGridAreaMapper.getAreaByCity(province,city);
	}
	
	
}