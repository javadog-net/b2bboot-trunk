/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.util.List;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.cms.entity.CmsModelField;
import com.jhmis.modules.cms.mapper.CmsModelFieldMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.CmsModel;
import com.jhmis.modules.cms.mapper.CmsModelMapper;

/**
 * 模型管理Service
 * @author lydia
 * @version 2019-08-29
 */
@Service
@Transactional(readOnly = true)
public class CmsModelService extends CrudService<CmsModelMapper, CmsModel> {
	@Autowired
	private CmsModelMapper cmsModelMapper;
	@Autowired
	private CmsModelFieldMapper cmsModelFieldMapper;

	public CmsModel get(String id) {
		return super.get(id);
	}
	
	public List<CmsModel> findList(CmsModel cmsModel) {
		return super.findList(cmsModel);
	}
	
	public Page<CmsModel> findPage(Page<CmsModel> page, CmsModel cmsModel) {
		return super.findPage(page, cmsModel);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsModel cmsModel) {
		super.save(cmsModel);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsModel cmsModel) {
		super.delete(cmsModel);
	}

	/**
	 * 安装模型
	 * @param cmsModel
	 */
	@Transactional(readOnly = false)
	public void isInstall(CmsModel cmsModel){
		//安装
		//查找model_id 对应的cms_model_field 中是否有内容，如果有则根据表名生成cms_tablename_field 的表
		if(StringUtils.isNotEmpty(cmsModel.getId())){
			List<CmsModelField> cmsModelFieldList = cmsModelFieldMapper.findByModelId(cmsModel.getId());
			if(cmsModelFieldList.size() > 0){
				//TODO 生成数据库表
			}else{
				cmsModelMapper.update(cmsModel);
			}
		}
	}

	/**
	 * 卸载模型
	 * @param cmsModel
	 */
	@Transactional(readOnly = false)
	public void unInstall(CmsModel cmsModel){
		//TODO 从栏目表根据modelId 和delFlag !=1 查询
		//如果存在自定义表，则删除自定义表
		List<CmsModelField> cmsModelFieldList = cmsModelFieldMapper.findByModelId(cmsModel.getId());
		if(cmsModelFieldList.size() > 0 ){
			//删除对应的自定义表
		}
		cmsModel.setIsInstall(Global.NO);
		cmsModelMapper.update(cmsModel);
	}

	/**
	 * 查找可用的模型
	 * @return
	 */
	public List<CmsModel> findCmsModelList(){
		return cmsModelMapper.findCmsModelList();
	}
}