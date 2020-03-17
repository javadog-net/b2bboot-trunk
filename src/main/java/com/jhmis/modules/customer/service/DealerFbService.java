/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.Date;
import java.util.List;

import com.jhmis.common.utils.IdGen;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
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
import com.jhmis.modules.customer.entity.DealerFb;
import com.jhmis.modules.customer.mapper.DealerFbMapper;

/**
 * 非标客户Service
 * @author hdx
 * @version 2019-05-25
 */
@Service
@Transactional(readOnly = true)
public class DealerFbService extends CrudService<DealerFbMapper, DealerFb> {
	@Autowired
	DealerMapper dealerMapper;
	@Autowired
	DealerAccountMapper dealerAccountMapper;

	public DealerFb get(String id) {
		return super.get(id);
	}
	
	public List<DealerFb> findList(DealerFb dealerFb) {
		return super.findList(dealerFb);
	}
	
	public Page<DealerFb> findPage(Page<DealerFb> page, DealerFb dealerFb) {
		return super.findPage(page, dealerFb);
	}
	
	@Transactional(readOnly = false)
	public void save(DealerFb dealerFb) {
		super.save(dealerFb);
	}
	
	@Transactional(readOnly = false)
	public void delete(DealerFb dealerFb) {
		super.delete(dealerFb);
	}

	@Transactional(readOnly = false)
	public DealerAccount perfectInfo(DealerFb dealerFb){
		Dealer dealer = new Dealer();

		//经销商编号
		dealer.setCompanyCode("FB"+dealerFb.getMobile());
		//经销商名称
		dealer.setCompanyName(dealerFb.getCompany());
		//联系人
		dealer.setContacts(dealerFb.getContact());
		//电话
		dealer.setTel(dealerFb.getMobile());
		//手机号
		dealer.setMobile(dealerFb.getMobile());
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
		dealerAccount.setLoginName("FB"+dealerFb.getMobile());
		//生成加密的密码
		String newpwd = SystemService.entryptPassword("Haier"+dealerFb.getMobile());
		dealerAccount.setPasswd(newpwd);
		dealerAccount.setRealName(dealerFb.getContact());
		dealerAccount.setMobile(dealerFb.getMobile());
		dealerAccount.setIsAdmin("1");
		dealerAccount.setIsClosed("0");
		dealerAccountMapper.insert(dealerAccount);
		return dealerAccount;
	}


	
}