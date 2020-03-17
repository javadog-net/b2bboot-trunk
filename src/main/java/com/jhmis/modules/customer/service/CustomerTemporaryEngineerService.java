/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.Date;
import java.util.List;

import com.jhmis.common.utils.IdGen;
import com.jhmis.modules.customer.entity.DealerFb;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.mapper.dealer.DealerAccountMapper;
import com.jhmis.modules.shop.mapper.dealer.DealerMapper;
import com.jhmis.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerTemporaryEngineer;
import com.jhmis.modules.customer.mapper.CustomerTemporaryEngineerMapper;

/**
 * 临时工程商Service
 * @author hdx
 * @version 2019-05-28
 */
@Service
@Transactional(readOnly = true)
public class CustomerTemporaryEngineerService extends CrudService<CustomerTemporaryEngineerMapper, CustomerTemporaryEngineer> {
	@Autowired
	DealerMapper dealerMapper;
	@Autowired
	DealerAccountMapper dealerAccountMapper;
	public CustomerTemporaryEngineer get(String id) {
		return super.get(id);
	}
	
	public List<CustomerTemporaryEngineer> findList(CustomerTemporaryEngineer customerTemporaryEngineer) {
		return super.findList(customerTemporaryEngineer);
	}
	
	public Page<CustomerTemporaryEngineer> findPage(Page<CustomerTemporaryEngineer> page, CustomerTemporaryEngineer customerTemporaryEngineer) {
		return super.findPage(page, customerTemporaryEngineer);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerTemporaryEngineer customerTemporaryEngineer) {
		super.save(customerTemporaryEngineer);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerTemporaryEngineer customerTemporaryEngineer) {
		super.delete(customerTemporaryEngineer);
	}

	@Transactional(readOnly = false)
	public DealerAccount perfectInfo(CustomerTemporaryEngineer customerTemporaryEngineer){
		Dealer dealer = new Dealer();

		//经销商编号
		dealer.setCompanyCode("FB"+customerTemporaryEngineer.getCusMobile());
		//经销商名称
		dealer.setCompanyName(customerTemporaryEngineer.getCusName());
		//联系人
		dealer.setContacts(customerTemporaryEngineer.getCusHeadPerson());
		//电话
		dealer.setTel(customerTemporaryEngineer.getCusMobile());
		//手机号
		dealer.setMobile(customerTemporaryEngineer.getCusMobile());
		//渠道来源
		dealer.setChannelName("非标客户");
		//创建时间
		dealer.setCreateDate(new Date());
		//uuid
		dealer.setId(IdGen.uuid());
		//存入dealer表
		dealerMapper.insert(dealer);
		DealerAccount dealerAccount = new DealerAccount();
		dealerAccount.setCreateDate(new Date());
		//uuid
		dealerAccount.setId(IdGen.uuid());
		dealerAccount.setDealerId(dealer.getId());
		dealerAccount.setLoginName("FB"+customerTemporaryEngineer.getCusMobile());
		//生成加密的密码
		String mobile = customerTemporaryEngineer.getCusMobile();
		String endPass = "";
		if(mobile==null || mobile.length()<4){
			endPass = "123456";
		}else{
			endPass = mobile.substring(5,11);
		}
		String newpwd = SystemService.entryptPassword(endPass);
		dealerAccount.setPasswd(newpwd);
		dealerAccount.setRealName(customerTemporaryEngineer.getCusHeadPerson());
		dealerAccount.setMobile(customerTemporaryEngineer.getCusMobile());
		dealerAccount.setIsAdmin("1");
		dealerAccount.setIsClosed("0");
		dealerAccountMapper.insert(dealerAccount);
		return dealerAccount;
	}

}