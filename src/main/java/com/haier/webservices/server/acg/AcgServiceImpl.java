package com.haier.webservices.server.acg;

import com.jhmis.common.Exception.AcgException;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerForAcg;
import com.jhmis.modules.shop.service.dealer.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.haier.webservices.server.acg.impl
 * @Author: hdx
 * @CreateTime: 2019-08-14 16:11
 * @Description: AcgService Acg推送接口实现
 */
public class AcgServiceImpl implements AcgService {
    protected Logger logger = LoggerFactory.getLogger(AcgServiceImpl.class);

    @Autowired
    DealerService dealerService;
    @Override
    public AjaxJson pushContractor(DealerForAcg dealerForAcg) {
        logger.info("AcgService Acg推送接口实现开始");
        logger.info("AcgService Acg参数" +dealerForAcg.toString());
        try {
            logger.info("AcgService 调用fromAcg开始");
            dealerService.fromAcg(dealerForAcg);
        }catch (AcgException e){
            logger.info("AcgService 接口异常，异常原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        //处理
        logger.info("AcgService 接口成功，91码=" + dealerForAcg.getCompanyCode());
        logger.error("AcgService 接口成功，91码=" + dealerForAcg.getCompanyCode());
        return AjaxJson.ok(dealerForAcg.getCompanyCode()+" 操作成功");
    }
}
