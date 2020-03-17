/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.Date;
import java.util.List;

import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.mapper.dealer.DealerAccountMapper;
import com.jhmis.modules.shop.mapper.dealer.DealerMapper;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.TGridArea;
import com.jhmis.modules.customer.service.TGridAreaService;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.mapper.MdmCustomersSourceMapper;

/**
 * 直采专区mdm主数据源Service
 * @author hdx
 * @version 2019-03-26
 */
@Service
@Transactional(readOnly = true)
public class MdmCustomersSourceService extends CrudService<MdmCustomersSourceMapper, MdmCustomersSource> {

	@Autowired
	DealerMapper dealerMapper;
	@Autowired
	DealerAccountMapper dealerAccountMapper;
	@Autowired
	TGridAreaService tGridAreaService;
	
    @Autowired
    private MdmCustomersSourceMapper mdmCustomersSourceMapper;

	public MdmCustomersSource get(String id) {
		return super.get(id);
	}
	
	public MdmCustomersSource getByCusCode(String cusCode) {
		return mdmCustomersSourceMapper.getByCusCode(cusCode);
	}
	
	public List<MdmCustomersSource> findList(MdmCustomersSource mdmCustomersSource) {
		return super.findList(mdmCustomersSource);
	}
	
	public Page<MdmCustomersSource> findPage(Page<MdmCustomersSource> page, MdmCustomersSource mdmCustomersSource) {
		return super.findPage(page, mdmCustomersSource);
	}
	@Transactional(readOnly = false)
	//完善88码用户信息
	public boolean perfectInfo(MdmCustomersSource mdmCustomersSource,String password,String mobile){
		Dealer dealer = new Dealer();
		//经销商编号
		dealer.setCompanyCode(mdmCustomersSource.getCusCode());
		//经销商名称
		dealer.setCompanyName(mdmCustomersSource.getComName());
		//邮箱
		dealer.setZipCode(mdmCustomersSource.getPost());
		//联系人
		dealer.setContacts(mdmCustomersSource.getLinkman());
		//电话
		dealer.setTel(mdmCustomersSource.getTel());
		//手机号
		dealer.setMobile(mobile);
		//邮箱
		dealer.setEmail(mdmCustomersSource.getEMail());
		//地址
		dealer.setAreaInfo(mdmCustomersSource.getAreaName());
		//详细地址
		dealer.setDetailAddress(mdmCustomersSource.getAddress());
		//渠道来源
		dealer.setChannelName("直采专区");
		//税号
		dealer.setTaxCode(mdmCustomersSource.getTax());
		//创建时间
		dealer.setCreateDate(new Date());
		//uuid
		dealer.setId(IdGen.uuid());
		//加入大渠道
		dealer.setCustomerCategory(mdmCustomersSource.getCustomerCategory());
		//加入小渠道
		dealer.setIndustryClass(mdmCustomersSource.getIndustryClass());
		
		if(StringUtils.isNotBlank(mdmCustomersSource.getMdmArea())){
			dealer.setMdmProvince(mdmCustomersSource.getMdmProvince());
			dealer.setMdmCity(mdmCustomersSource.getMdmCity());
			dealer.setMdmArea(mdmCustomersSource.getMdmArea());
			
			TGridArea tGridArea = new TGridArea();
			tGridArea.setDistrictcode(mdmCustomersSource.getMdmArea());
			List<TGridArea> tGridAreaList = tGridAreaService.findList(tGridArea);
			if(tGridAreaList != null && tGridAreaList.size()>0) {
				tGridArea = tGridAreaList.get(0);
				dealer.setWgcode(tGridArea.getWgcode());
				dealer.setWgname(tGridArea.getWgname());
			}
        }
		
		
		//存入dealer表
		try {
			dealerMapper.insert(dealer);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		DealerAccount dealerAccount = new DealerAccount();
		//uuid
		dealerAccount.setId(IdGen.uuid());
		dealerAccount.setDealerId(dealer.getId());
		dealerAccount.setLoginName(mdmCustomersSource.getCusCode());
		//生成加密的密码
		String newpwd = SystemService.entryptPassword(password);
		dealerAccount.setPasswd(newpwd);
		dealerAccount.setRealName(mdmCustomersSource.getLinkman());
		dealerAccount.setMobile(mobile);
		dealerAccount.setIsAdmin("1");
		dealerAccount.setEmail(mdmCustomersSource.getEMail());
		dealerAccount.setIsClosed("0");
		try {
			dealerAccountMapper.insert(dealerAccount);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional(readOnly = false)
	public void save(MdmCustomersSource mdmCustomersSource) {
		super.save(mdmCustomersSource);
	}
	
	@Transactional(readOnly = false)
	public void delete(MdmCustomersSource mdmCustomersSource) {
		super.delete(mdmCustomersSource);
	}
	
}