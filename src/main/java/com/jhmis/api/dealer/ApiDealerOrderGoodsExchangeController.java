package com.jhmis.api.dealer;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.OrderGoodsExchange;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.OrderGoodsExchangeService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(value = "ApiDealerOrderGoodsExchangeController", description = "供应商-换货管理")
@RestController
@RequestMapping("/api/dealer/orderGoodsChange")
public class ApiDealerOrderGoodsExchangeController extends BaseController {
    @Autowired
    private OrderGoodsExchangeService orderGoodsExchangeService;


    /**
     * 审核换货申请
     * @return
     */
    @ApiOperation(notes = "auditGoodsExchange",httpMethod = "POST",value = "审核换货申请",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "申请单ID", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "auditState", value = "是否通过 1 通过  2 拒绝", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "auditDesc", value = "审核意见", required = true, paramType = "form",dataType = "String")
    })
    @RequestMapping("/auditGoodsExchange")
    public AjaxJson auditGoodsExchange(OrderGoodsExchange orderGoodsExchange) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
        if (StringUtils.isBlank(orderGoodsExchange.getId())) {
            return AjaxJson.fail("申请单ID 不能为空");
        }
        OrderGoodsExchange exchange = orderGoodsExchangeService.get(orderGoodsExchange);
        if (exchange == null) {
            return AjaxJson.fail("申请单不存在");
        }

        //检查必填
        if (StringUtils.isBlank(orderGoodsExchange.getAuditState())) {
            return AjaxJson.fail("审核状态");
        }
        exchange.setAuditState(orderGoodsExchange.getAuditState());
        exchange.setAuditDesc(orderGoodsExchange.getAuditDesc());
        exchange.setAuditTime(new Date());
        exchange.setAuditor(currentAccount.getId());
        orderGoodsExchangeService.auditExchangeApply(exchange);
        return AjaxJson.ok("审核成功！");
    }

}
