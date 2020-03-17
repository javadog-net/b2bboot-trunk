/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import com.jhmis.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.QygTOrderInfo;
import com.jhmis.modules.customer.mapper.QygTOrderInfoMapper;

/**
 * 巨商会订单info非中转表Service
 * @author hdx
 * @version 2020-02-12
 */
@Service
@Transactional(readOnly = true)
public class QygTOrderInfoService extends CrudService<QygTOrderInfoMapper, QygTOrderInfo> {

	public QygTOrderInfo get(String id) {
		return super.get(id);
	}
	
	public List<QygTOrderInfo> findList(QygTOrderInfo qygTOrderInfo) {
		return super.findList(qygTOrderInfo);
	}
	
	public Page<QygTOrderInfo> findPage(Page<QygTOrderInfo> page, QygTOrderInfo qygTOrderInfo) {
		return super.findPage(page, qygTOrderInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(QygTOrderInfo qygTOrderInfo) {
		QygTOrderInfo qtoi = mapper.get(qygTOrderInfo.getRowkey());
		if (qtoi==null) {
			mapper.insert(qygTOrderInfo);
		} else {
			mapper.update(qygTOrderInfo);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(QygTOrderInfo qygTOrderInfo) {
		super.delete(qygTOrderInfo);
	}
	
}