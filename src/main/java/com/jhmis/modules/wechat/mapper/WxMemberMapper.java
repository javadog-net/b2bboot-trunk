/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxMember;

/**
 * 微信会员信息表MAPPER接口
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@MyBatisMapper
public interface WxMemberMapper extends BaseMapper<WxMember> {

	public WxMember findbinding(@Param("id")String id, @Param("openid")String openid);
	

	/**
	 * 根据企业购用户id，获取用户的微信头像
	 * @param userId
	 * @return 头像存储的url
	 */
	String findAvatarurlByUserId(String userId);
	/**
	 * 根据企业购用户id,获取用户昵称
	 * @param userId
	 * @return 用户昵称
	 */
	String findNicknameByUserId(String userId);

}