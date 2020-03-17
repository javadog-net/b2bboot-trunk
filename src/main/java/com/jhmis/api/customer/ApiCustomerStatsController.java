package com.jhmis.api.customer;

import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.euc.entity.EucMsgOrder;
import com.jhmis.modules.euc.service.EucMsgOrderService;
import com.jhmis.modules.euc.service.EucMsgService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.customer
 * @Author: hdx
 * @CreateTime: 2020-02-04 09:40
 * @Description: 提供企业购自用数据统计
 */

@Api(value = "ApiCustomerStatsController", description = "提供企业购自用数据统计")
@RestController
@RequestMapping("/api/customer/stats")
public class ApiCustomerStatsController extends BaseController {

    @Autowired
    private ShopMsgService shopMsgService;

    @Autowired
    EucMsgOrderService eucMsgOrderService;

    @ApiOperation(notes = "eucOrderStats", httpMethod = "GET", value = "获取euc数据统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "校验身份", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "content", value = "内容", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "eucOrderStats",method = RequestMethod.GET)
    @SysLog(desc = "获取euc数据统计")
    public AjaxJson eucOrderStats(){
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Map<String,Object> map = new HashMap<>();
        int dispatcherCount = 0;
        int garbCount = 0;
        //获取抢单数和派单数
        EucMsgOrder emo = new EucMsgOrder();
        //88码
        emo.setContractorCode(currentAccount.getLoginName());
        //派单数
        emo.setOrderType(EucMsgCode.ORDER_TYPE_DISPATCHER.getLabel());
        //派单
        List<EucMsgOrder> dispatcherOrder = eucMsgOrderService.findList(emo);
        //抢单数
        emo.setOrderType(EucMsgCode.ORDER_TYPE_GARB.getLabel());
        //抢单
        List<EucMsgOrder> garbOrder = eucMsgOrderService.findList(emo);
        //非空赋值
        if (null!=dispatcherOrder) {
            dispatcherCount = dispatcherOrder.size();
        }
        if (null!=garbOrder) {
            garbCount = garbOrder.size();
        }
        map.put("dispatcherCount",dispatcherCount);
        map.put("garbCount",garbCount);
        return AjaxJson.ok(map);
    }
}
