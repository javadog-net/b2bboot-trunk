/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 采购商账号MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface PurchaserAccountMapper extends BaseMapper<PurchaserAccount> {
    /**
     * 根据登录名称查询用户
     * @param loginName
     * @return
     */
    public PurchaserAccount getByLoginName(@Param("loginName") String loginName);
    
    
    /**
	 * 根据88码名称查询用户
	 * @param number
	 * @return
	 */
    public PurchaserAccount getByLoginNumber(@Param("loginNum") String loginNum);
    
	/**
	 * 绑定手机号 邮箱号
	 * @param mobile
	 * @param email
	 * @return
	 */
    public PurchaserAccount saveBdMobile(String id,String mobile);
	/**
	 * 绑定 邮箱号
	 * @param mobile
	 * @param email
	 * @return
	 */
    public PurchaserAccount saveBdEmail(String id,String email);


    /**
     * 更新用户密码
     * @param account
     * @return
     */
    public int updatePasswordById(PurchaserAccount account);

    /**
     * 更新登录信息，如：登录IP、登录时间
     * @param account
     * @return
     */
    public int updateLoginInfo(PurchaserAccount account);

    /**
     * 删除用户角色关联数据
     * @param account
     * @return
     */
    public int deleteAccountRole(PurchaserAccount account);

    /**
     * 插入用户角色关联数据
     * @param account
     * @return
     */
    public int insertAccountRole(PurchaserAccount account);

    /**
     * 更新用户信息
     * @param account
     * @return
     */
    public int updateAccountInfo(PurchaserAccount account);

	/** 
	  * @Title: updatedapartname 
	  * @Description: TODO  
	  * @param purchaseraccount 
	  * @return void
	  * @author tc
	  * @date 2018年12月15日下午3:29:29
	  */
	public void updatedapartname(PurchaserAccount purchaseraccount);

	public PurchaserAccount getbyid(String id);
	
	public String getAvatarById(String id);
	
	public String getNicknameById(String id);

	public int deletebypurchaserid(String id);

	public void deletebymobiles(String mobiles);

}