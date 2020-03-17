/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.AppVersion;
import com.jhmis.modules.wechat.mapper.AppVersionMapper;

/**
 * app版本Service
 * @author abc
 * @version 2019-05-10
 */
@Service
@Transactional(readOnly = true)
public class AppVersionService extends CrudService<AppVersionMapper, AppVersion> {

	@Autowired
	private AppVersionMapper appVersionMapper;
	
	public AppVersion get(String id) {
		return super.get(id);
	}
	
	public List<AppVersion> findList(AppVersion appVersion) {
		return super.findList(appVersion);
	}
	
	public Page<AppVersion> findPage(Page<AppVersion> page, AppVersion appVersion) {
		return super.findPage(page, appVersion);
	}
	
	@Transactional(readOnly = false)
	public void save(AppVersion appVersion) {
		super.save(appVersion);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppVersion appVersion) {
		super.delete(appVersion);
	}
	
	public AppVersion getNewVersion(AppVersion appVersion, Date nowTime){	
		return appVersionMapper.getNewVersion(appVersion, nowTime);
	}
	
	/**
	 * 检测用户版本是否需要更新
	 * @param newV
	 * @param oldV
	 * @return
	 */
	public boolean comparativeEdition(String newV,String oldV) {
		System.out.println("newV=" + newV + ", oldV =" + oldV);
		//by hdx 2019年4月5日 11:51:11 开始
		//将上传版本与数据库最新版本比较
		String newVReplaceStr = newV.replace(".","");
		String oldVReplaceStr = oldV.replace(".","");
		if(Integer.parseInt(newVReplaceStr)<Integer.parseInt(oldVReplaceStr)){
		    logger.info("前端上传版本号大于目前数据库版本号");
		    return false;
        }
        //by hdx 2019年4月5日 11:51:11 结束
		String[] newVArray = newV.split("\\.");
        String[] oldVArray = oldV.split("\\.");
        //判断是否长度为3位数
        if(newVArray.length!=oldVArray.length){
            return false;
        }
        for(int i=0 ; i<newVArray.length ; i++ ){
        	if(Integer.parseInt(newVArray[i]) > Integer.parseInt(oldVArray[i])){
        		return true;
        	}
        }
		return false;
	}
	
}