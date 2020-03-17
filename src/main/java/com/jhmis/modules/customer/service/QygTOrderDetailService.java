/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import com.jhmis.modules.customer.entity.QygTOrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.QygTOrderDetail;
import com.jhmis.modules.customer.mapper.QygTOrderDetailMapper;

/**
 * 巨商会订单details非中转表Service
 * @author hdx
 * @version 2020-02-12
 */
@Service
@Transactional(readOnly = true)
public class QygTOrderDetailService extends CrudService<QygTOrderDetailMapper, QygTOrderDetail> {

	public QygTOrderDetail get(String id) {
		return super.get(id);
	}
	
	public List<QygTOrderDetail> findList(QygTOrderDetail qygTOrderDetail) {
		return super.findList(qygTOrderDetail);
	}
	
	public Page<QygTOrderDetail> findPage(Page<QygTOrderDetail> page, QygTOrderDetail qygTOrderDetail) {
		return super.findPage(page, qygTOrderDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(QygTOrderDetail qygTOrderDetail) {
		QygTOrderDetail qtod = mapper.get(qygTOrderDetail.getRowkey());
		if (qtod==null) {
			mapper.insert(qygTOrderDetail);
		} else {
			mapper.update(qygTOrderDetail);
		}

	}
	
	@Transactional(readOnly = false)
	public void delete(QygTOrderDetail qygTOrderDetail) {
		super.delete(qygTOrderDetail);
	}
	
}