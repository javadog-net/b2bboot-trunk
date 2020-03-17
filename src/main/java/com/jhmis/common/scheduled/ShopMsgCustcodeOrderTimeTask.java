package com.jhmis.common.scheduled;

import com.haier.mdm.khzj.Process;
import com.jhmis.common.Enum.MsgFlagCode;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.modules.customer.service.CustomerProjectInfoService;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
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
 * @Description: 订单相关中标更新定时任务
 */
@Component
@Configurable
@EnableScheduling
public class ShopMsgCustcodeOrderTimeTask {

    Logger log = LoggerFactory.getLogger(ShopMsgCustcodeOrderTimeTask.class);

    @Autowired
    CustomerProjectInfoService customerProjectInfoService;

    @Autowired
    ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;
    /**
     *@Author: hdx
     *@CreateTime: 10:18 2019/9/27
     *@param:  * @param
     *@Description:  每天定时更新将订单更新中标状态

     */
    @Scheduled(cron = "0 0 1 * * ? ")
    //@Scheduled(cron = "*/15 * * * * ?")
    @Transactional
    public void orderIsbind(){
        CustomerProjectInfo  customerProjectInfo = new  CustomerProjectInfo();
        //来源是客单提报的
        customerProjectInfo.setProjectsSource("GN_PRJ_08");
        List<CustomerProjectInfo> listCustomerProjectInfo = customerProjectInfoService.findListTask(customerProjectInfo);
        for(CustomerProjectInfo cp:listCustomerProjectInfo){
            //如果hps状态大于R5则视为中标
            if("5".equals(cp.getNodestate())||"6".equals(cp.getNodestate())||"7".equals(cp.getNodestate())||"8".equals(cp.getNodestate())||"9".equals(cp.getNodestate())){
                //需求Id是否为空
                if(StringUtils.isNotEmpty(cp.getMsgId())){
                    ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
                    //需求id
                    shopMsgCustcodeOrder.setMsgId(cp.getMsgId());
                    //只有跟进中的才可以
                    shopMsgCustcodeOrder.setCancelFlag(ProcessCode.NO.getLabel());
                    List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findListTask(shopMsgCustcodeOrder);
                    if(listShopMsgCustcodeOrder!=null && listShopMsgCustcodeOrder.size()>0){
                        ShopMsgCustcodeOrder smco = listShopMsgCustcodeOrder.get(0);
                        //设置已中标
                        smco.setIsBind(ProcessCode.YES.getLabel());
                        //设置中标时间
                        smco.setBindTime(cp.getSigndate());
                        //中标金额
                        smco.setTotalCount(cp.getBindamount());
                        shopMsgCustcodeOrderService.save(smco);
                    }
                }
            }

        }
    }
}
