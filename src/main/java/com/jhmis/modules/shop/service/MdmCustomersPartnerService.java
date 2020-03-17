/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.mapper.MdmCustomersPartnerMapper;

/**
 * MDM四方关系Service
 * @author hdx
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class MdmCustomersPartnerService extends CrudService<MdmCustomersPartnerMapper, MdmCustomersPartner> {

	public MdmCustomersPartner get(String id) {
		return super.get(id);
	}
	
	public List<MdmCustomersPartner> findList(MdmCustomersPartner mdmCustomersPartner) {
		return super.findList(mdmCustomersPartner);
	}
	
	public Page<MdmCustomersPartner> findPage(Page<MdmCustomersPartner> page, MdmCustomersPartner mdmCustomersPartner) {
		return super.findPage(page, mdmCustomersPartner);
	}
	
	@Transactional(readOnly = false)
	public void save(MdmCustomersPartner mdmCustomersPartner) {
		super.save(mdmCustomersPartner);
	}
	
	@Transactional(readOnly = false)
	public void delete(MdmCustomersPartner mdmCustomersPartner) {
		super.delete(mdmCustomersPartner);
	}
	
}