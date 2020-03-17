/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.directpurchaser.service;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Article;
import com.jhmis.modules.directpurchaser.mapper.DirectPurchaserAccountMapper;
import com.jhmis.modules.directpurchaser.mapper.DirectPurchaserMapper;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
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
public class DirectPurchaserService extends CrudService<PurchaserMapper, Purchaser> {
	@Autowired
	DirectPurchaserAccountMapper directPurchaserAccountMapper;
	@Autowired
	DirectPurchaserMapper directPurchasermapper;

	public Purchaser get(String id) {
		return super.get(id);
	}

	public List<Purchaser> findList(Purchaser purchaser) {
		return super.findList(purchaser);
	}

	public Page<Purchaser> findPage(Page<Purchaser> page, Purchaser purchaser) {
		purchaser.setDelFlag(Article.DEL_FLAG_NORMAL);
		purchaser.setPurchaseType("1");
		Page<Purchaser> list=super.findPage(page, purchaser);
		if(list!=null){
			List<Purchaser> plist=list.getList();
			if(plist!=null && plist.size()>0){
				for(Purchaser pur : plist){
					if(pur!=null){
						PurchaserAccount account=directPurchaserAccountMapper.getAdminByPurchaserId(pur.getId(),"1");
						if(account!=null){
							pur.setLoginNum(account.getLoginNum());
							pur.setLoginName(account.getLoginName());
						}
					}
				}
			}
			list.setList(plist);
		}
		return list;
	}
	
	/**
	 * 获取关联的子采购商
	 * @param page
	 * @param purchaser
	 * @return
	 */
	public Page<Purchaser> getSubRelPurchaser(Page<Purchaser> page,  Purchaser purchaser) {
		purchaser.setPage(page);
		String id=purchaser.getId();
		List<Purchaser> plist = directPurchasermapper.getSubRelPurchaser(purchaser);
		if(plist!=null && plist.size()>0){
			page.setCount(plist.size());
			for(Purchaser pur : plist){
				if(pur!=null){
					PurchaserAccount account=directPurchaserAccountMapper.getAdminByPurchaserId(pur.getId(),"1");
					if(account!=null){
						pur.setLoginNum(account.getLoginNum());
						pur.setLoginName(account.getLoginName());
					}
				}
			}
		}
		page.setList(plist);
		return page;
	}
	
	/**
	 * 获取关联的子采购商
	 * @param id
	 * @return
	 */
	public List<Purchaser>  getSubPurchaserList(String id){
		Purchaser purchaser = new Purchaser();
		purchaser.setId(id);
		List<Purchaser> plist=directPurchasermapper.getSubRelPurchaser(purchaser);
		return plist;
	}
	
	/**
	 * 获取未关联的采购商
	 * @param page
	 * @param purchaser
	 * @return
	 */
	public Page<Purchaser> getNoRelPurchaser(Page<Purchaser> page,  Purchaser purchaser) {
		purchaser.setPage(page);
		String id=purchaser.getId();
		List<Purchaser> plist=directPurchasermapper.getNoRelPurchaser(id);
			if(plist!=null && plist.size()>0){
				page.setCount(plist.size());
				for(Purchaser pur : plist){
					if(pur!=null){
						PurchaserAccount account=directPurchaserAccountMapper.getAdminByPurchaserId(pur.getId(),"1");
						if(account!=null){
							pur.setLoginNum(account.getLoginNum());
							pur.setLoginName(account.getLoginName());
						}
					}
				}
			}
			page.setList(plist);
		return page;
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
		List<PurchaserAccount> accountList = directPurchaserAccountMapper.findList(entity);
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
		directPurchasermapper.updateAuditState(purchaser);
		// 清除缓存
		PurchaserUtils.clearPurchaserCache(purchaser.getId());
	}

	@Transactional(readOnly = false)
	public void updatechannelById(Purchaser purchaser) {
		directPurchasermapper.updatechannelById(purchaser);
	}

	@Transactional(readOnly = false)
	public void updatePurchaserRel(String id,String isAdmin) {
		directPurchasermapper.updatePurchaserRel(id,isAdmin);
	}
	
	@Transactional(readOnly = false)
	public void updatedapartname(Purchaser purchaser) {
		directPurchasermapper.updatedapartname(purchaser);
	}

	@Transactional(readOnly = false)
	public void updatedapartandcompany(Purchaser purchaser) {
		directPurchasermapper.updatedapartandcompany(purchaser);
	}

	@Transactional(readOnly = false)
	public void updateinfo(Purchaser purchaser) {
		directPurchasermapper.update(purchaser);
	}

	@Transactional(readOnly = false)
	public void savechannelbyid(String id, String openid) {
		directPurchasermapper.savechannelbyid(id, openid);
	}

	@Transactional(readOnly = false)
	public Purchaser getpurchaserbyid(String id) {
		return directPurchasermapper.getpurchaserbyid(id);
	}

	@Transactional(readOnly = false)
	public int deleteById(String id) {
		return directPurchasermapper.deleteById(id);
		
		
	}

	public List<Purchaser> findListbymobile(String mobiles) {
		// TODO Auto-generated method stub
		return directPurchasermapper.findListbymobile(mobiles);
	}

	public Purchaser getone(String id) {
		// TODO Auto-generated method stub
		return directPurchasermapper.getone(id);
	}
}