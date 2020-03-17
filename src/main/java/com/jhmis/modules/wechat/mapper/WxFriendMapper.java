/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxFriend;

/**
 * 聊天好友表MAPPER接口
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@MyBatisMapper
public interface WxFriendMapper extends BaseMapper<WxFriend> {
	/**
	 * 根据用户id获取相应的好友信息
	 * @param userId
	 * @return
	 */
	public List<WxFriend> findByUserId(String userId);

	public int deletebuuserid(String id);

	public int deletebufriendid(String id);

	public int deleteaddperson(String id);

	public List<WxFriend> findListMyFriendByFriendId(WxFriend wxFriend);

	public List<WxFriend> findMyFriendList(@Param("userId")String userId, @Param("tab")String tab);


}