/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import com.jhmis.common.utils.CacheUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsgTpl;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserMsgTplMapper;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购商消息模板Service
 * @author tity
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class PurchaserMsgTplService extends CrudService<PurchaserMsgTplMapper, PurchaserMsgTpl> {

	public PurchaserMsgTpl get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserMsgTpl> findList(PurchaserMsgTpl purchaserMsgTpl) {
		return super.findList(purchaserMsgTpl);
	}
	
	public Page<PurchaserMsgTpl> findPage(Page<PurchaserMsgTpl> page, PurchaserMsgTpl purchaserMsgTpl) {
		return super.findPage(page, purchaserMsgTpl);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserMsgTpl purchaserMsgTpl) {
		super.save(purchaserMsgTpl);
        delTplCache();
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserMsgTpl purchaserMsgTpl) {
		super.delete(purchaserMsgTpl);
        delTplCache();
	}

    private void delTplCache(){
        CacheUtils.remove(PurchaserUtils.PURCHASER_CACHE, PurchaserUtils.PURCHASER_MSG_TPL_CACHE_KEY);
    }
    public Map<String,PurchaserMsgTpl> getTplCache(){
        Map<String,PurchaserMsgTpl> tplMap = (Map<String,PurchaserMsgTpl>) CacheUtils.get(PurchaserUtils.PURCHASER_CACHE, PurchaserUtils.PURCHASER_MSG_TPL_CACHE_KEY);
        if (tplMap ==  null){
            tplMap = new HashMap<>();
            List<PurchaserMsgTpl> list = this.mapper.findAllList(new PurchaserMsgTpl());
            for(PurchaserMsgTpl tpl:list){
                tplMap.put(tpl.getCode(),tpl);
            }
        }
        return tplMap;
    }

    /**
     * 处理模板消息内容替换
     * @param tplContent
     * @param params
     * @return
     */
    public String processContent(String tplContent,Map<String,String> params){
        String content = tplContent;
        for (Map.Entry<String,String> entry : params.entrySet()) {
            content = content.replace("{{".concat(entry.getKey()).concat(".DATA}}"), entry.getValue());
        }
        return content;
    }
}