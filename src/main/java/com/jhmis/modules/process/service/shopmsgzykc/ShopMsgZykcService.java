/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shopmsgzykc;

import java.util.Date;
import java.util.List;

import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.shopmsgzykc.ShopMsgZykc;
import com.jhmis.modules.process.mapper.shopmsgzykc.ShopMsgZykcMapper;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 零售相关Service
 * @author hdx
 * @version 2019-10-11
 */
@Service
@Transactional(readOnly = true)
public class ShopMsgZykcService extends CrudService<ShopMsgZykcMapper, ShopMsgZykc> {

	public ShopMsgZykc get(String id) {
		return super.get(id);
	}
	
	public List<ShopMsgZykc> findList(ShopMsgZykc shopMsgZykc) {
		return super.findList(shopMsgZykc);
	}
	
	public Page<ShopMsgZykc> findPage(Page<ShopMsgZykc> page, ShopMsgZykc shopMsgZykc) {
		return super.findPage(page, shopMsgZykc);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopMsgZykc shopMsgZykc) {
		super.save(shopMsgZykc);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopMsgZykc shopMsgZykc) {
		super.delete(shopMsgZykc);
	}


	@Transactional(readOnly = false)
	public void check(String zykcId,String ischeck) throws ShopMsgException {
		//zykcId 为空
		if(StringUtils.isBlank(zykcId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_ZYKCID_ERROR);
		}
		//zykcId type
		if(StringUtils.isBlank(ischeck)){
			throw new ShopMsgException(ShopMsgCode.PARAM_ISCHECK_ERROR);
		}
		ShopMsgZykc shopMsgZykc = this.get(zykcId);
		if(null==shopMsgZykc){
			throw new ShopMsgException(ShopMsgCode.ORDER_ZYKC_ERROR);
		}
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		//审核时间
		shopMsgZykc.setCheckDate(new Date());
		//审核类型
		shopMsgZykc.setIsCheck(ischeck);
		//审核人
		shopMsgZykc.setChecker(currentUser.getLoginName());
		//更新
		mapper.update(shopMsgZykc);
	}
}