/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Sensitive;
import com.jhmis.modules.cms.mapper.SensitiveMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 敏感词管理Service
 * @author lydia
 * @version 2019-11-02
 */
@Service
@Transactional(readOnly = true)
public class SensitiveService extends CrudService<SensitiveMapper, Sensitive> {

	public Sensitive get(String id) {
		return super.get(id);
	}
	
	public List<Sensitive> findList(Sensitive sensitive) {
		return super.findList(sensitive);
	}
	
	public Page<Sensitive> findPage(Page<Sensitive> page, Sensitive sensitive) {
		return super.findPage(page, sensitive);
	}
	
	@Transactional(readOnly = false)
	public void save(Sensitive sensitive) {
		super.save(sensitive);
	}
	
	@Transactional(readOnly = false)
	public void delete(Sensitive sensitive) {
		super.delete(sensitive);
	}

	/**
	 * 替换处理
	 * @param content
	 * @return
	 */
	public String replace(String content){
		List<Sensitive> list=find(null, "", true);
		if (list!=null && list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				if (StringUtils.isNotEmpty(content)) {
					content=content.replace(list.get(i).getSensitiveword(), list.get(i).getReplaceto());
				}
			}
		}
		return content;
	}
	/**
	 * 查询
	 */
	public List<Sensitive> find(Sensitive Sensitive,String order,boolean cache){
		//TODO  cache的问题
			return mapper.findList(Sensitive);
		}
	
}