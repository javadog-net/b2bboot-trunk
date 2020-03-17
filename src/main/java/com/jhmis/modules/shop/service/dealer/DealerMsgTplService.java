/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.dealer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhmis.common.utils.CacheUtils;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.utils.DealerUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.dealer.DealerMsgTpl;
import com.jhmis.modules.shop.mapper.dealer.DealerMsgTplMapper;

/**
 * 供应商消息模板Service
 * @author tity
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class DealerMsgTplService extends CrudService<DealerMsgTplMapper, DealerMsgTpl> {

	public DealerMsgTpl get(String id) {
		return super.get(id);
	}
	
	public List<DealerMsgTpl> findList(DealerMsgTpl dealerMsgTpl) {
		return super.findList(dealerMsgTpl);
	}
	
	public Page<DealerMsgTpl> findPage(Page<DealerMsgTpl> page, DealerMsgTpl dealerMsgTpl) {
		return super.findPage(page, dealerMsgTpl);
	}
	
	@Transactional(readOnly = false)
	public void save(DealerMsgTpl dealerMsgTpl) {
		super.save(dealerMsgTpl);
		//清除缓存
        delTplCache();
	}
	
	@Transactional(readOnly = false)
	public void delete(DealerMsgTpl dealerMsgTpl) {
		super.delete(dealerMsgTpl);
		//清除缓存
        delTplCache();
	}

	private void delTplCache(){
        CacheUtils.remove(DealerUtils.DEALER_CACHE, DealerUtils.DEALER_MSG_TPL_CACHE_KEY);
    }

	public Map<String,DealerMsgTpl> getTplCache(){
        Map<String,DealerMsgTpl> tplMap = (Map<String,DealerMsgTpl>) CacheUtils.get(DealerUtils.DEALER_CACHE, DealerUtils.DEALER_MSG_TPL_CACHE_KEY);
        if (tplMap ==  null){
            tplMap = new HashMap<>();
            List<DealerMsgTpl> list = this.mapper.findAllList(new DealerMsgTpl());
            for(DealerMsgTpl tpl:list){
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