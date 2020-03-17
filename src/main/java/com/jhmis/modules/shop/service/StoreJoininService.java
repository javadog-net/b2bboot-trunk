/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.time.DateUtil;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.StoreJoinin;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.mapper.StoreJoininMapper;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 店铺申请Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class StoreJoininService extends CrudService<StoreJoininMapper, StoreJoinin> {
    @Autowired
    StoreService storeService;
    @Autowired
    DealerService dealerService;
	public StoreJoinin get(String id) {
		return super.get(id);
	}

	public StoreJoinin getByDealerId(String dealerId){
	    return this.mapper.getByDealerId(dealerId);
    }

	public List<StoreJoinin> findList(StoreJoinin storeJoinin) {
		return super.findList(storeJoinin);
	}
	
	public Page<StoreJoinin> findPage(Page<StoreJoinin> page, StoreJoinin storeJoinin) {
		return super.findPage(page, storeJoinin);
	}
	
	@Transactional(readOnly = false)
	public void save(StoreJoinin storeJoinin) {
		super.save(storeJoinin);
	}
	
	@Transactional(readOnly = false)
	public void delete(StoreJoinin storeJoinin) {
		super.delete(storeJoinin);
	}

    @Transactional(readOnly = false)
    public AjaxJson audit(StoreJoinin storeJoinin) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())){
            storeJoinin.setAuditor(user.getName());
        }
        storeJoinin.setAuditTime(new Date());
        this.mapper.updateAuditState(storeJoinin);
        if(Global.AUDIT_STATE_OK.equals(storeJoinin.getAuditState())){
            //检查现有店铺名是否重复
            Store store = storeService.findUniqueByProperty("store_name",storeJoinin.getStoreName());
            if(store!=null){
                return AjaxJson.fail("店名重复");
            }
            //同意插入店铺表
            store = new Store();
            store.setDealerId(storeJoinin.getDealerId());
            store.setStoreName(storeJoinin.getStoreName());
            store.setGrade(1);
            store.setState("1");
            store.setExpiryTime(DateUtil.addYears(new Date(),storeJoinin.getApplyYear()));
            store.setApplyYear(storeJoinin.getApplyYear());
            store.setIsSelfSupport("0");
            store.setCollectNum(0);
            store.setScoreProductQuality(5d);
            store.setScoreDemandResponse(5d);
            store.setScoreDeliveryCredit(5d);
            store.setScoreSupplySpeed(5d);
            store.setScoreCustomerServic(5d);
            storeService.save(store);
            //更新供应商店铺状态
            Dealer dealer = dealerService.get(storeJoinin.getDealerId());
            dealer.setIsStore("1");
            dealerService.save(dealer);
        }
        return AjaxJson.ok();
    }
}