package com.haier.webservices.server.hps;

import com.jhmis.common.Exception.HpsException;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.shop.service.dealer.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.haier.webservices.server.hps.impl
 * @Author: hdx
 * @CreateTime: 2019-08-14 21:16
 * @Description: hps Webservice接口实现
 */
public class HpsServiceImpl implements HpsService {
    protected Logger logger = LoggerFactory.getLogger(HpsServiceImpl.class);
    @Autowired
    DealerService dealerService;

    @Override
    public AjaxJson transCode(String oldCode, String newCode) {
        logger.info("HpsService transCode接口实现开始");
        logger.info("HpsService transCode参数,oldCode=" + oldCode + "newCode=" + newCode);
        try {
            logger.info("HpsService 调用transCode开始");
            dealerService.transCode(oldCode,newCode);
        } catch (HpsException e) {
            logger.info("HpsService 接口异常，异常原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
            logger.info("HpsService 接口成功，" + oldCode + " 已转化为 "+newCode);
            logger.error("HpsService 接口成功，" + oldCode + " 已转化为 "+newCode);
          return AjaxJson.ok("操作成功: "+ oldCode + " 已转化为 "+newCode);
    }
}
