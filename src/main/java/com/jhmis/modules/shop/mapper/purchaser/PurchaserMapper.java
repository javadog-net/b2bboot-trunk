/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.wechat.entity.WxGroupMessage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 采购商管理MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
/**   
 * <p>Title: PurchaserMapper</p>  
 * <p>Description: </p>  
 * @author tc  
 * @date 2018年12月15日 下午3:20:41
 */ 
/**   
 * <p>Title: PurchaserMapper</p>  
 * <p>Description: </p>  
 * @author tc  
 * @date 2018年12月15日 下午3:20:47
 */ 
@MyBatisMapper
public interface PurchaserMapper extends BaseMapper<Purchaser> {

	public void updateAuditState(Purchaser entiry);
    /**
     * 删除子账号
     * @param purchaserId
     */
    public void deleteAccount(String purchaserId);
    /**
     * 逻辑删除子账号
     * @param purchaserId
     */
    public void logicDeleteAccount(String purchaserId);
    /**
     * 获取经销商有订单关系的采购商，这里因为传递的参数是Purchaser，直接把id当做供应商的id来处理
     */
    public List<Purchaser> getRelatedPurchaser(Purchaser entiry);
    /**
     * 
     * 登录时添入channel渠道。
     */
    public void updatechannelById(Purchaser purchsaer);
    
    /**
     * 注册时根据公司名称查询
     */
    public List<Purchaser> findByCompanyName(String companyName);
    /**
     * 注册时根据公司名称查询
     */
    public List<Purchaser> findByMobile(String mobile);
    
    /** 
      * @Title: updatedapartname 
      * @Description: TODO 修改部门  
      * @param purchsaer 
      * @return void
      * @author tc
      * @date 2018年12月15日下午3:20:49
      */
    public void updatedapartandcompany(Purchaser purchsaer);
    public void updatedapartname(Purchaser purchsaer);
	public void savechannelbyid(@Param("id")String id, @Param("openid")String openid);
    public Purchaser getpurchaserbyid(String id);
	public int deleteById(@Param("id")String id);
	public List<Purchaser> findListbymobile(String mobile);
	public Purchaser getone(String id);
	public List<Purchaser> findListAllOrArea(Purchaser purchaser);
	

}