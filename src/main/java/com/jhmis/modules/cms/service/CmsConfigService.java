/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.io.File;
import java.util.List;

import com.jhmis.common.utils.CacheUtils;
import com.jhmis.common.utils.FileUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.mapper.CmsConfigMapper;

/**
 * cms配置管理表Service
 * @author lydia
 * @version 2019-08-29
 */
@Service
@Transactional(readOnly = true)
public class CmsConfigService extends CrudService<CmsConfigMapper, CmsConfig> {
	public CmsConfig get(String id) {
		return super.get(id);
	}
	
	public List<CmsConfig> findList(CmsConfig cmsConfig) {
		return super.findList(cmsConfig);
	}
	
	public Page<CmsConfig> findPage(Page<CmsConfig> page, CmsConfig cmsConfig) {
		return super.findPage(page, cmsConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsConfig cmsConfig,String templateResourceDir,String staticDir) {
		//判断“首页生成路径是否改变”
		if(StringUtils.isEmpty(cmsConfig.getOldIndexPath()) ||(cmsConfig.getInfoPath().equals(cmsConfig.getOldIndexPath()))){
			//拷贝静态文件，如果目标文件中存在该文件，则覆盖
			FileUtils.copyDirectoryCover(templateResourceDir,staticDir,true);
		}
		//清空缓存
		CmsUtils.removeCache(CmsUtils.CMS_CONFIG_CACHE);
		super.save(cmsConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsConfig cmsConfig) {
		super.delete(cmsConfig);
	}
}