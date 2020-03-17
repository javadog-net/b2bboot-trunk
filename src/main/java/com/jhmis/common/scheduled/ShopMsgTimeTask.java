package com.jhmis.common.scheduled;

import com.jhmis.api.process.ApiProcessAppController;
import com.jhmis.common.Enum.MsgFlagCode;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @CreateTime: 2019-09-27 10:14
 * @Description: 需求相关定时任务
 */
@Component
@Configurable
@EnableScheduling
public class ShopMsgTimeTask {

    Logger log = LoggerFactory.getLogger(ShopMsgTimeTask.class);

    @Autowired
    ShopMsgService shopMsgService;

    @Autowired
    ShopMsgDispatcherService shopMsgDispatcherService;

    /**
     *@Author: hdx
     *@CreateTime: 10:18 2019/9/27
     *@param:  * @param
     *@Description:  超过两小时未抢单(1分钟执行一次)

     */
    @Scheduled(cron = "0 */1 * * * ?")
    @Transactional
    public void overtimeNoGarb(){
        //判断是否大于两个小时
        long now = System.currentTimeMillis();
        //两小时基数
        long twoHours = 60*60*2*1000;
        //获取所有满足筛选的需求
        ShopMsg shopMsg = new ShopMsg();
        //首先满足总监审核通过
        shopMsg.setAppIspass(ProcessCode.DIRECTOR_ISCHECK_PASS.getLabel());
        //必须满足是需求状态是20 总监审核通过
        shopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_APPROVED.getLabel());
        List<ShopMsg> listShopMsg = shopMsgService.findListOver(shopMsg);
        //如果不存在
        if(null==listShopMsg||listShopMsg.size()==0){
            //log.error("定时任务" + ShopMsgCode.OVERTIME_NOTEXIST_ERROR.getDesc());
            return;
        }
        //如果存在将循环所有，进行时间的筛选，若大于两小时则进行派单管理划分
        for(ShopMsg hasShopMsg:listShopMsg){
            //如果不存在审核时间
            if(null ==hasShopMsg.getAppCheckDate()){
                log.error("定时任务" + ShopMsgCode.APPCHECK_EXCEPTION.getDesc());
            }
            //进行筛选
            long appcheckTime = hasShopMsg.getAppCheckDate().getTime();
            //从总监审核通过后如果大于两小时则处理
            if((appcheckTime + twoHours)<now){
                //插入派单列表
                ShopMsgDispatcher msgDispatcher = new ShopMsgDispatcher();
                //需求id
                msgDispatcher.setMsgId(hasShopMsg.getId());
                //是否已派单0未派单
                msgDispatcher.setDispaFlag(ProcessCode.NO_DISPATCHER.getLabel());
                //进入派单表的时间
                msgDispatcher.setCreateDate(new Date());
                //来源
                msgDispatcher.setSource(ProcessCode.DISORDER_SOURCE_TIMEOUT.getValue());
                //是否关闭 0未关闭
                msgDispatcher.setIsClosed(ProcessCode.MSG_IS_CLOSE.getLabel());
                shopMsgDispatcherService.save(msgDispatcher);
                //然后更新需求表,超过两小时未抢单
                hasShopMsg.setMsgFlag(MsgFlagCode.WAITING_LIST.getLabel());
                //更新进入派单表的标识字段
                hasShopMsg.setIsDispatch(ProcessCode.IS_DISPATCHER.getLabel());
                //更新
                shopMsgService.save(hasShopMsg);
            }

        }
    }
}
