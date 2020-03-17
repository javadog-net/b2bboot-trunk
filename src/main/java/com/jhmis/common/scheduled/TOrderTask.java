package com.jhmis.common.scheduled;

import com.alibaba.fastjson.JSON;
import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.*;
import com.jhmis.modules.customer.service.QygTOrderDetailService;
import com.jhmis.modules.customer.service.QygTOrderInfoService;
import com.jhmis.modules.customer.service.TOrderDetailService;
import com.jhmis.modules.customer.service.TOrderInfoService;
import com.jhmis.modules.euc.entity.EucLog;
import com.jhmis.modules.euc.entity.EucMsg;
import com.jhmis.modules.euc.entity.EucMsgOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.scheduled
 * @Author: hdx
 * @CreateTime: 2020-02-13 10:15
 * @Description: 365巨商会订单表更新
 */

@Component
@Configurable
@EnableScheduling
public class TOrderTask extends BaseController {

    @Autowired
    TOrderInfoService tOrderInfoService;

    @Autowired
    TOrderDetailService tOrderDetailService;

    @Autowired
    QygTOrderInfoService qygTOrderInfoService;

    @Autowired
    QygTOrderDetailService qygTOrderDetailService;

    //每个小时执行一次
    //@Scheduled(cron = "0 0 */1 * * ?")
    //一分钟执行一次
    //@Scheduled(cron = "0 */1 * * * ?")
    //每天执行一次
    @Scheduled(cron = "0 0 1 * * ? ")
    public void orderUpdate(){
        logger.error("365巨商会订单表更新开始" + new Date());
        try{
            //获取临时中转表中的Info-list
            List<TOrderInfo> listTOrderInfo = tOrderInfoService.findTaskList(new TOrderInfo());
            //循环进行插入
            for(TOrderInfo tOrderInfo:listTOrderInfo){
                QygTOrderInfo qygTOrderInfo = new QygTOrderInfo();
                BeanUtils.copyProperties(tOrderInfo, qygTOrderInfo);
                qygTOrderInfoService.save(qygTOrderInfo);
            }

            //获取临时中转表中的Info-detail
            List<TOrderDetail> listTOrderDetail = tOrderDetailService.findTaskList(new TOrderDetail());
            //循环进行插入
            for(TOrderDetail tOrderDetail:listTOrderDetail){
                QygTOrderDetail qygTOrderDetail = new QygTOrderDetail();
                BeanUtils.copyProperties(tOrderDetail, qygTOrderDetail);
                qygTOrderDetailService.save(qygTOrderDetail);
            }
        }catch (Exception e){
            logger.error("365巨商会订单表更新定时任务异常=" + e.getMessage());
        }
        logger.error("365巨商会订单表更新结束" + new Date());
    }
}
