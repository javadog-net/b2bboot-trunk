/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxSignIn;

/**   
 * <p>Title: WxSignInMapper</p>  
 * <p>Description: </p>  签到mapper接口
 * @author tc  
 * @date 2018年12月26日 下午3:54:00
 */ 
@MyBatisMapper
public interface WxSignInMapper extends BaseMapper<WxSignIn> {

    WxSignIn getYesterdaySignIn(String userId);

    WxSignIn countSignInfo(String userId);

public WxSignIn findsigntoday(@Param("userId")String userid,@Param("signInYear")String year,
		    @Param("signInMonth")String month,@Param("signInDay")String day );

public List<WxSignIn> findsign(@Param("userId")String userid,@Param("signInYear")String year,
	    @Param("signInMonth")String month);

public String findcontinueday(@Param("userid")String id, @Param("useridtab")String idtab);

public int updatecontinueday(@Param("userid")String id, @Param("useridtab")String idtab, 
		      @Param("continueday")String continueday);

public WxSignIn findusertab(@Param("userid")String id, @Param("useridtab")String idtab);

int deletebyuserid(String id);
	
}