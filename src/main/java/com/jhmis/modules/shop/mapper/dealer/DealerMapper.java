/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.dealer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.dealer.Dealer;

/**
 * 供应商管理MAPPER接口
 * @author tity
 * @version 2018-07-29
 */
@MyBatisMapper
public interface DealerMapper extends BaseMapper<Dealer> {
	public void updateAuditState(Dealer entiry);

    /**
     * 删除子账号
     * @param dealerId
     */
	public void deleteAccount(String dealerId);

	/**
	 * 逻辑删除子账号
	 * @param dealerId
	 */
	int logicDeleteAccount(String dealerId);
		public List<Dealer> findListAllOrArea(Dealer dealer);
	/**
	 * 分页查询
	 */
	public List<Dealer> find(Dealer Dealer,String order,int currPage,int pageSize);
	
	/**
	 * app派单分页查询
	 */
	public List<Dealer> findApp(Dealer Dealer,String product,int currPage,int pageSize);
	
	/**
	 * 派单时分页查询经销商
	 */
	public List<Dealer> find(Dealer Dealer,String product,String istotal,String city,String order,int currPage,int pageSize);
	
	/**
	 *发布、修改需求的时候，判断需求是否有可承接的经销商时使用
	 * @param cityName 
	 * @param proGroup 
	 * @param state 
	 * @return
	 */
	public List<Dealer> findUndertake(@Param("state")String state, @Param("proGroup")String proGroup, @Param("cityName")String cityName);
	
	
	/**
	 * 统计
	 * @param info
	 * @return
	 */
	public int count(Dealer Dealer);
	
	/**
	 * app统计
	 * @param info
	 * @return
	 */
	public List<Dealer> countApp(Dealer Dealer);
	
	/**
	 * 根据
	 * @param Dealer
	 * @param product
	 * @param istotal
	 * @param area
	 * @return
	 */
	public int countSelectFran(Dealer Dealer,String product,String istotal,String area);

	/**
	 * 根据id查询
	 * @param id
	 * @param cache
	 * @return
	 */
	public Dealer findById(int id);

	public List<Dealer> findByDepartAndMid(String zjid, String mid);
	
	/**
	 * 通过工贸id获取城市id
	 */
	public List<String> selectCityId(String orgid);
	
	/**
	 * 根据工贸ID 和 是否入围标注 查询入围的经销商的手机号
	 * @param orgid
	 * @return
	 */
	List<Dealer> selectFranchiser(String orgid,String status);
	
	
	/**导出
	 * @param Dealer
	 * @return
	 */
	public List<Dealer> exportListByExample(Dealer Dealer, String order);
	
	 /**
     * 根据orgid返回工贸下对应的经销商列表
     * @param orgid
     */
    List<Dealer> selectFranchiserByOrgid(String orgid);
    
    /**
     * 根据custcode查询经销商
     * @param custcode
     */
    List<Dealer> selectFranchiserByCode(String custcode);
    
    /**
     * 根据orgid返回工贸下对应的商空经销商列表
     */
    List<Dealer> selectFranchiserSk(String orgid);    
    
    /**
     * 查询符合条件的入围经销商和非入围经销商
     */

	public List<Dealer> findListdealer(Map<String, Object> map);

    /**
     * 获取所有的项目编码
     * @return
     */
	public List<String> findCompanyCode();
}