/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.modules.cms.entity.Article;
import com.jhmis.modules.shop.entity.Cart;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.mapper.StoreMapper;

/**
 * 店铺管理Service
 *
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class StoreService extends CrudService<StoreMapper, Store> {

    @Autowired
    private StoreGoodsMapper storeGoodsMapper;

    @Autowired
    private StoreMapper storeMapper;

    public Store get(String id) {
        return super.get(id);
    }

    public List<Store> findList(Store store) {
        return super.findList(store);
    }

    public Page<Store> findPage(Page<Store> page, Store store) {
        store.setDelFlag(Article.DEL_FLAG_NORMAL);
        return super.findPage(page, store);
    }

    @Transactional(readOnly = false)
    public void save(Store store) {
        super.save(store);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Store store) {
        int i = storeMapper.deleteByLogic(store);
        if (i <= 0) {
            throw new RuntimeException();
        }
        storeGoodsMapper.deleteByStoreIdOrCode(store.getId(),null);
    }

    @Transactional(readOnly = false)
    public Store selectByDealerId(String dealerId) {
        return mapper.selectByDealerId(dealerId);
    }

    @Transactional(readOnly = false)
    public void updateStore(Store store) {
        mapper.update(store);
    }

    public List<Store> findDistinctStore(Cart cart) {
        return mapper.findDistinctStore(cart);
    }
    
    public List<Store> selectByIds(Cart cart) {
        return mapper.selectByIds(cart);
    }


}