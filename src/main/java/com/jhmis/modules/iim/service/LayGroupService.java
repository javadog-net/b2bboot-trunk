/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.iim.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.iim.entity.LayGroup;
import com.jhmis.modules.iim.entity.LayGroupUser;
import com.jhmis.modules.iim.mapper.LayGroupMapper;
import com.jhmis.modules.iim.mapper.LayGroupUserMapper;
import com.jhmis.modules.sys.entity.User;

/**
 * 群组Service
 * @author lgf
 * @version 2016-08-07
 */
@Service
@Transactional(readOnly = true)
public class LayGroupService extends CrudService<LayGroupMapper, LayGroup> {

	@Autowired
	private LayGroupUserMapper layGroupUserMapper;
	
	public LayGroup get(String id) {
		LayGroup layGroup = super.get(id);
		return layGroup;
	}

	public List<LayGroupUser> getUsersByGroup (LayGroup layGroup){
		return  layGroupUserMapper.findList(new LayGroupUser(layGroup));
	}
	
	public List<LayGroup> findList(LayGroup layGroup) {
		List<LayGroup> layGroupList= new ArrayList<LayGroup>();
		List<LayGroup> list = super.findList(layGroup);
		for(LayGroup u:list){
			layGroupList.add(this.get(u.getId()));
		}
		return layGroupList;
	}
	
	public List<LayGroup> findGroupList(User user) {
		List<LayGroup> layGroupList= new ArrayList<LayGroup>();
		LayGroupUser layGroupUser = new LayGroupUser();
		layGroupUser.setUser(user);
		List<LayGroupUser> list = layGroupUserMapper.findList(layGroupUser);
		for(LayGroupUser u:list){
			layGroupList.add(this.get(u.getGroup().getId()));
		}
		return layGroupList;
	}
	
	public Page<LayGroup> findPage(Page<LayGroup> page, LayGroup layGroup) {
		return super.findPage(page, layGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(LayGroup layGroup) {
		super.save(layGroup);
		for (LayGroupUser layGroupUser : layGroup.getLayGroupUserList()){
			if (layGroupUser.getId() == null){
				continue;
			}
			if (LayGroupUser.DEL_FLAG_NORMAL.equals(layGroupUser.getDelFlag())){
				if (StringUtils.isBlank(layGroupUser.getId())){
					layGroupUser.setGroup(layGroup);
					layGroupUser.preInsert();
					layGroupUserMapper.insert(layGroupUser);
				}else{
					layGroupUser.preUpdate();
					layGroupUserMapper.update(layGroupUser);
				}
			}else{
				layGroupUserMapper.delete(layGroupUser);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(LayGroup layGroup) {
		super.delete(layGroup);
		layGroupUserMapper.delete(new LayGroupUser(layGroup));
	}
	
}