/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxGroupUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 群组成员MAPPER接口
 * 
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@MyBatisMapper
public interface WxGroupUserMapper extends BaseMapper<WxGroupUser> {
	List<WxGroupUser> getGroupList(String userId);

	List<WxGroupUser> getIndustryCircle(String userId);

	List<WxGroupUser> getUserGroup(String userId);

	public List<WxGroupUser> findrepeatgroup(String userid);

	public void deletebyidandgroupid(@Param("userid") String userid, @Param("groupid") String groupid);

	/**
	 * 根据groupId获取群内全部成员的用户id
	 * 
	 * @param groupId
	 * @return
	 */
	public List<String> getGroupUsers(String groupId);

	int deletebyuserid(String id);

	void updateIsRead(@Param("groupId") String groupid, @Param("state") String state, @Param("userId") String userid);

	void updateIsReadByUidAndGid(@Param("groupId") String groupId, @Param("id") String id);

	List<WxGroupUser> getIsReadGroup(String id);

	List<WxGroupUser> findGeTuiUser(@Param("groupId")String groupId,@Param("userId") String userId);

	WxGroupUser findIsOrAdmin(@Param("groupId")String groupId,@Param("userId") String userId);

	void deleteGroupUser(@Param("groupId")String groupId,@Param("userId") String userId);
}