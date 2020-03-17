/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Position;
import com.jhmis.modules.cms.mapper.PositionMapper;

/**
 * 位置管理Service
 * @author lydia
 * @version 2019-09-06
 */
@Service
@Transactional(readOnly = true)
public class PositionService extends CrudService<PositionMapper, Position> {

	public Position get(String id) {
		return super.get(id);
	}
	
	public List<Position> findList(Position position) {
		return super.findList(position);
	}
	
	public Page<Position> findPage(Page<Position> page, Position position) {
		return super.findPage(page, position);
	}
	
	@Transactional(readOnly = false)
	public void save(Position position) {
		super.save(position);
	}
	
	@Transactional(readOnly = false)
	public void delete(Position position) {
		super.delete(position);
	}
	
}