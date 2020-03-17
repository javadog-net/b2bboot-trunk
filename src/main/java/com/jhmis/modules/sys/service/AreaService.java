/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.service.TreeService;
import com.jhmis.modules.sys.entity.Area;
import com.jhmis.modules.sys.mapper.AreaMapper;
import com.jhmis.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author jhmis
 * @version 2017-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaMapper, Area> {

	@Autowired
	private AreaMapper mappaer;
	
	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
/**
 *  
  * @Title: findAllProvice 
  * @Description: TODO 查询所有的省份  
  * @return 
  * @return List<Area>
  * @author tc
  * @date 2019年9月9日下午4:55:02
 */
	public List<Area> findAllProvice() {
		// TODO Auto-generated method stub
		return mapper.findAllProvice();
	}
	
}
