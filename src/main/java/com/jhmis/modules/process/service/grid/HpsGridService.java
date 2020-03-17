/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.grid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.grid.HpsGrid;
import com.jhmis.modules.process.mapper.grid.HpsGridMapper;

/**
 * 省市区匹配工贸Service
 * @author mll
 * @version 2019-09-25
 */
@Service
@Transactional(readOnly = true)
public class HpsGridService extends CrudService<HpsGridMapper, HpsGrid> {

	@Autowired 
	private HpsGridMapper hpsGridMapper;
	
	public HpsGrid get(String id) {
		return super.get(id);
	}
	
	public String findByCenterCode(String centerCode){
		return hpsGridMapper.findByCenterCode(centerCode);
	}
	
	
	public List<HpsGrid> findList(HpsGrid hpsGrid) {
		return super.findList(hpsGrid);
	}
	
	public Page<HpsGrid> findPage(Page<HpsGrid> page, HpsGrid hpsGrid) {
		return super.findPage(page, hpsGrid);
	}
	
	@Transactional(readOnly = false)
	public void save(HpsGrid hpsGrid) {
		super.save(hpsGrid);
	}
	
	@Transactional(readOnly = false)
	public void delete(HpsGrid hpsGrid) {
		super.delete(hpsGrid);
	}
	
	/**
	 * 根据所属中心(工贸)名称获取下属所有城市 
	 * @param centerName 中心(工贸)名称
	 * @return 下属城市列表
	 */
	public List<String> findCityByCenter(String centerName){
		return hpsGridMapper.findCityByCenter(centerName);
	}
	
}