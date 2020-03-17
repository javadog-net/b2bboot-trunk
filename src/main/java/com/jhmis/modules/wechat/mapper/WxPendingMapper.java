package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxPending;

@MyBatisMapper
public interface WxPendingMapper {
	/**
	 * 根据id获取相应待处理事项
	 * @param id
	 * @return 待处理对象
	 */
	WxPending getById(String id);
	/**
	 * 获取用户的所有未查看的待处理事项
	 * @return
	 */
	List<WxPending> findAllPending(String userId);
	/**
	 * 根据WxPending,查询待处理表中的数据
	 * @param wxPending
	 * @return
	 */
	List<WxPending>  getByWxPending(WxPending wxPending);
	
	/**
	 * 插入一条待处理事项
	 * @param wxPending
	 */
	void insert(WxPending wxPending);
	/**
	 * 根据id更新是否查看状态
	 * @param id
	 */
	void updateStatusById(String id);
	
	/**
	 * 根据id删除相应的待处理事项
	 * @param id
	 */
	void deleteById(String id);
	/**
	 * 根据userId查询待处理事项个数量
	 * @param userId
	 * @return
	 */
	int getPendingNumber(String userId);
	int deletebyuserid(String id);
}
