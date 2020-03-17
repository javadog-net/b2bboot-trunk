/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.service;

import java.util.List;

import com.jhmis.common.utils.CacheUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.sys.entity.SystemConfig;
import com.jhmis.modules.sys.mapper.SystemConfigMapper;

/**
 * 系统配置Service
 * @author tity
 * @version 2016-02-07
 */
@Service
@Transactional(readOnly = true)
public class SystemConfigService extends CrudService<SystemConfigMapper, SystemConfig> {
    public final static String SYS_CONFIG_CACHE_KEY = "system_config";
	public SystemConfig get(String id) {
        SystemConfig config = (SystemConfig) CacheUtils.get(SYS_CONFIG_CACHE_KEY);
        if(config == null){
            config = super.get(id);
        }
		return config;
	}
	
	public List<SystemConfig> findList(SystemConfig systemConfig) {
		return super.findList(systemConfig);
	}
	
	public Page<SystemConfig> findPage(Page<SystemConfig> page, SystemConfig systemConfig) {
		return super.findPage(page, systemConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(SystemConfig systemConfig) {
		super.save(systemConfig);
		CacheUtils.remove(SYS_CONFIG_CACHE_KEY);
	}
	
	@Transactional(readOnly = false)
	public void delete(SystemConfig systemConfig) {
		super.delete(systemConfig);
        CacheUtils.remove(SYS_CONFIG_CACHE_KEY);
	}
	
}