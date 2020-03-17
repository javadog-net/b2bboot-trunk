/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.CmsPrize;
import com.jhmis.modules.wechat.mapper.CmsPrizeMapper;

/**
 * 奖项表操作Service
 * @author tc
 * @version 2019-02-27
 */
@Service
@Transactional(readOnly = true)
public class CmsPrizeService extends CrudService<CmsPrizeMapper, CmsPrize> {

	@Autowired
	private CmsPrizeMapper cmsPrizeMapper;
	
	public CmsPrize get(String id) {
		return super.get(id);
	}
	
	public List<CmsPrize> findList(CmsPrize cmsPrize) {
		return super.findList(cmsPrize);
	}
	
	public Page<CmsPrize> findPage(Page<CmsPrize> page, CmsPrize cmsPrize) {
		return super.findPage(page, cmsPrize);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsPrize cmsPrize) {
		super.save(cmsPrize);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsPrize cmsPrize) {
		super.delete(cmsPrize);
	}

	@Transactional(readOnly = false)
	public void updatePrizeStatus(String prizeId, String tab) {
		cmsPrizeMapper.updatePrizeStatus(prizeId,tab);
	}
	
}