/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxAppCid;

/**
 * 设备cidMAPPER接口
 * @author tc
 * @version 2019-05-07
 */
@MyBatisMapper
public interface WxAppCidMapper extends BaseMapper<WxAppCid> {

	List<WxAppCid> findListCidByUserid(String friendId);

	void deleteByUserId(String wxAppCid);

	void deleteByUserPhone(String mobile);

	List<WxAppCid> findByCid(String cId);

	List<WxAppCid> getByCidAndUserId(WxAppCid cid);
	
}