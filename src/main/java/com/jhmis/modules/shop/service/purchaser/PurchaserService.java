/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Article;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserMapper;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 采购商管理Service
 * 
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class PurchaserService extends CrudService<PurchaserMapper, Purchaser> {
	@Autowired
	PurchaserAccountMapper accountMapper;
	@Autowired
	PurchaserMapper purchasermapper;

	public Purchaser get(String id) {
		return super.get(id);
	}

	public List<Purchaser> findList(Purchaser purchaser) {
		return super.findList(purchaser);
	}

	public Page<Purchaser> findPage(Page<Purchaser> page, Purchaser purchaser) {
		purchaser.setDelFlag(Article.DEL_FLAG_NORMAL);
		purchaser.setPurchaseType("0");
		return super.findPage(page, purchaser);
	}

	/**
	 * 获取与经销商订单关联的采购商
	 * 
	 * @param page
	 * @param purchaser
	 * @return
	 */
	public Page<Purchaser> getRelatedPurchaser(Page<Purchaser> page, Purchaser purchaser) {
		purchaser.setPage(page);
		page.setList(this.mapper.getRelatedPurchaser(purchaser));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(Purchaser purchaser) {
		super.save(purchaser);
		// 清除缓存
		PurchaserUtils.clearPurchaserCache(purchaser.getId());
	}

	@Transactional(readOnly = false)
	public void delete(Purchaser purchaser) {
		// 同时删除对应的所有子账号缓存
		PurchaserAccount entity = new PurchaserAccount();
		entity.setPurchaserId(purchaser.getId());
		List<PurchaserAccount> accountList = accountMapper.findList(entity);
		for (PurchaserAccount account : accountList) {
			PurchaserUtils.clearCache(account);
		}
		this.mapper.logicDeleteAccount(purchaser.getId());
		mapper.deleteByLogic(purchaser);
		// 清除缓存
		PurchaserUtils.clearPurchaserCache(purchaser.getId());
	}

	@Transactional(readOnly = false)
	public void audit(Purchaser purchaser) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())) {
			purchaser.setAuditor(user.getName());
		}
		purchaser.setAuditTime(new Date());
		this.mapper.updateAuditState(purchaser);
		// 清除缓存
		PurchaserUtils.clearPurchaserCache(purchaser.getId());
	}

	@Transactional(readOnly = false)
	public void updatechannelById(Purchaser purchaser) {
		purchasermapper.updatechannelById(purchaser);
	}

	@Transactional(readOnly = false)
	public void updatedapartname(Purchaser purchaser) {
		purchasermapper.updatedapartname(purchaser);
	}

	@Transactional(readOnly = false)
	public void updatedapartandcompany(Purchaser purchaser) {
		purchasermapper.updatedapartandcompany(purchaser);
	}

	@Transactional(readOnly = false)
	public void updateinfo(Purchaser purchaser) {
		purchasermapper.update(purchaser);
	}

	@Transactional(readOnly = false)
	public void savechannelbyid(String id, String openid) {
		purchasermapper.savechannelbyid(id, openid);
	}

	@Transactional(readOnly = false)
	public Purchaser getpurchaserbyid(String id) {
		return purchasermapper.getpurchaserbyid(id);
	}

	@Transactional(readOnly = false)
	public int deleteById(String id) {
		return purchasermapper.deleteById(id);
		
		
	}

	public List<Purchaser> findListbymobile(String mobiles) {
		// TODO Auto-generated method stub
		return purchasermapper.findListbymobile(mobiles);
	}

	public Purchaser getone(String id) {
		// TODO Auto-generated method stub
		return purchasermapper.getone(id);
	}
	
	/**
	 * 注册，根据用户名查询是否存在，调海尔接口
	 * @param companyName
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean findByCompanyName(String companyName) {
		
	  List<Purchaser> name=purchasermapper.findByCompanyName(companyName);
	  if(name !=null  && name.size() > 0){
		  return true;
	  }else{
			return false;
		}
	}
	/**
	 *判断用户是否注册
	 * @param mobile
	 * @return
     * 已经注册返回1005
	 */
	@Transactional(readOnly = false)
	public List<Purchaser> findByMobile(String mobile) {
		List<Purchaser> findByMobile = purchasermapper.findByMobile(mobile);
		if(findByMobile != null && findByMobile.size() > 0){
			return findByMobile;
		}else{
			return null;
		}
	}

	public List<Purchaser> findListAllOrArea(Purchaser purchaser) {
		// TODO Auto-generated method stub
		
		return purchasermapper.findListAllOrArea(purchaser);
	}
}