/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.dealer;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商账号管理MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface DealerAccountMapper extends BaseMapper<DealerAccount> {
    /**
     * 根据登录名称查询用户
     * @param loginName
     * @return
     */
    public DealerAccount getByLoginName(@Param("loginName") String loginName);

    /**
     * 更新用户密码
     * @param account
     * @return
     */
    public int updatePasswordById(DealerAccount account);

    /**
     * 更新登录信息，如：登录IP、登录时间
     * @param account
     * @return
     */
    public int updateLoginInfo(DealerAccount account);

    /**
     * 删除用户角色关联数据
     * @param account
     * @return
     */
    public int deleteAccountRole(DealerAccount account);

    /**
     * 插入用户角色关联数据
     * @param account
     * @return
     */
    public int insertAccountRole(DealerAccount account);

    /**
     * 更新用户信息
     * @param account
     * @return
     */
    public int updateAccountInfo(DealerAccount account);


    public List<DealerAccount> findRestList(DealerAccount account);
	
}