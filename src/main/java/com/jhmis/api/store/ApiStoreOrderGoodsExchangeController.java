package com.jhmis.api.store;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Api(value = "ApiDealerOrderGoodsExchangeController", description = "店铺-换货管理")
@RestController
@RequestMapping("/api/store/orderGoodsChange")
public class ApiStoreOrderGoodsExchangeController extends BaseController {
    @Autowired
    private OrderGoodsExchangeService orderGoodsExchangeService;
    /**
     * 换货列表
     * @param orderGoodsExchange
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(notes = "exchageList", httpMethod = "GET", value = "换货列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "auditState", value = "换货状态", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "orderSn", value = "订单号", required = false, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "auditTimeStart", value = "审核开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "auditTimeEnd", value = "审核结束时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createDateStart", value = "申请开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createDateEnd", value = "申请结束时间", required = false, paramType = "query", dataType = "String")})
    @RequestMapping("/exchageList")
    public AjaxJson exchageList(OrderGoodsExchange orderGoodsExchange, HttpServletRequest request, HttpServletResponse response){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
        orderGoodsExchange.setDealerId("1");
        orderGoodsExchange.setDealerId(currentAccount.getDealerId());
        Page<OrderGoodsExchange> page = orderGoodsExchangeService.findPage(new Page<OrderGoodsExchange>(request,response),orderGoodsExchange);
        return AjaxJson.layuiTable(page);
    }

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
//        exchange.setAuditor("1");
        orderGoodsExchangeService.auditExchangeApply(exchange);
        return AjaxJson.ok("审核成功！");
    }

    /**
     * 查看换货详情
     * @param orderGoodsExchange
     * @return
     */
    @ApiOperation(notes = "detail",httpMethod = "GET",value = "店铺查看换货详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "申请单ID", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/detail")
    public AjaxJson detail(OrderGoodsExchange orderGoodsExchange){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
//        orderGoodsExchange.setDealerId("1");
        if(orderGoodsExchange == null || StringUtils.isEmpty(orderGoodsExchange.getId())){
            return AjaxJson.fail("参数错误");
        }
        orderGoodsExchange.setDealerId(currentAccount.getDealerId());
        orderGoodsExchange = orderGoodsExchangeService.get(orderGoodsExchange);
        if(orderGoodsExchange != null && orderGoodsExchange.getDealerId() == null){
            return AjaxJson.fail("您无法查看该申请单的详细信息");
        }
        return AjaxJson.ok(orderGoodsExchange);

    }
}
