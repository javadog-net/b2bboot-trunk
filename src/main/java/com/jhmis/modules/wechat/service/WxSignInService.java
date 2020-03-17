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
import com.jhmis.modules.wechat.entity.WxSignIn;
import com.jhmis.modules.wechat.mapper.WxSignInMapper;


/**   
 * <p>Title: WxSignInService</p>  
 * <p>Description: </p>签到表 Service  
 * @author tc  
 * @date 2018年12月26日 下午3:59:32
 */ 
@Service
@Transactional(readOnly = true)
public class WxSignInService extends CrudService<WxSignInMapper, WxSignIn> {

	@Autowired
	private WxSignInMapper wxSignInMapper;

	public WxSignIn get(String id) {
		return super.get(id);
	}
	
	public List<WxSignIn> findList(WxSignIn wxSignIn) {
		return super.findList(wxSignIn);
	}
	
	public Page<WxSignIn> findPage(Page<WxSignIn> page, WxSignIn wxSignIn) {
		return super.findPage(page, wxSignIn);
	}
	
	@Transactional(readOnly = false)
	public void save(WxSignIn wxSignIn) {
		super.save(wxSignIn);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxSignIn wxSignIn) {
		super.delete(wxSignIn);
	}

	public WxSignIn getYesterdaySignIn(String userId) {
		return wxSignInMapper.getYesterdaySignIn(userId);
	}

	public WxSignIn countSignInfo(String userId) {
		return wxSignInMapper.countSignInfo(userId);
	}

	/** 
	  * @Title: findsigntoday 
	  * @Description: TODO  查询今天是否签到
	  * @param wxSignIn
	  * @return 
	  * @return WxSignIn
	  * @author tc
	  * @date 2018年12月26日下午3:57:41
	  */
	@Transactional(readOnly = false)
	public WxSignIn findsigntoday(String userid,String  year,String  month,String  day) {
		return wxSignInMapper.findsigntoday(userid, year, month, day);
	}

	/** 
	  * @Title: findsign 
	  * @Description: TODO查询每个月的签到天数  
	  * @param wxSignIn
	  * @return 
	  * @return List<WxSignIn>
	  * @author tc
	  * @date 2018年12月26日下午3:57:30
	  */
	@Transactional(readOnly = false)
	public List<WxSignIn> findsign(String userid, String year,String month) {
		return wxSignInMapper.findsign(userid, year, month);
	}

	/** 
	  * @Title: findcontinueday 
	  * @Description: TODO 查询连续天数  
	  * @param id
	  * @param idtab
	  * @return 
	  * @return String
	  * @author tc
	  * @date 2018年12月26日下午3:58:17
	  */
	@Transactional(readOnly = false)
	public String findcontinueday(String id, String idtab) {
		return wxSignInMapper.findcontinueday(id,idtab);
	}

	/** 
	  * @Title: updatecontinueday 
	  * @Description: TODO  修改连续签到天数
	  * @param id
	  * @param idtab
	  * @param continueday 
	  * @return void
	  * @author tc
	  * @date 2018年12月26日下午3:58:33
	  */
	@Transactional(readOnly = false)
	public void updatecontinueday(String id, String idtab, String continueday) {
		wxSignInMapper.updatecontinueday(id,idtab,continueday);
	}

	@Transactional(readOnly = false)
	public WxSignIn findusertab(String id,String idtab){
		
		return wxSignInMapper.findusertab(id,idtab);
	}
	@Transactional(readOnly = false)
	public int deletebyuserid(String id) {
		return wxSignInMapper.deletebyuserid(id);
	}
}