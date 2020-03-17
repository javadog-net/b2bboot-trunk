/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxFriendMessage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 聊天信息表MAPPER接口
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@MyBatisMapper
public interface WxFriendMessageMapper extends BaseMapper<WxFriendMessage> {
    List<WxFriendMessage> findFriendMsgInfoList(String userid);
    int messageNumber(@Param("userId") String userId,@Param("friendId")String friendId);
	int deletebyfromuserid(String id);
	int deletebytouserid(String id);
	void updateIsRead(@Param("friendId")String friendId,@Param("userId") String userId);
	WxFriendMessage getlastmessage(@Param("userId")String userId, @Param("friendId")String friendId);
	List<WxFriendMessage> findToUserIdIsMe(String id);
}