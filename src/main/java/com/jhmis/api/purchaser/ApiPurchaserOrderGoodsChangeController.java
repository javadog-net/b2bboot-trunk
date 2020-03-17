package com.jhmis.api.purchaser;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.entity.OrderGoodsExchange;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.OrderGoodsExchangeService;
import com.jhmis.modules.shop.service.OrderGoodsService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Api(value = "ApiPurchaserOrderGoodsChangeController", description = "采购商-换货管理")
@RestController
@RequestMapping("/api/purchaser/orderGoodsChange")
public class ApiPurchaserOrderGoodsChangeController extends BaseController {
    @Autowired
    private OrderGoodsExchangeService orderGoodsExchangeService;
    @Autowired
    private OrderGoodsService orderGoodsService;

    /**
     * 申请换货
     * @param
     * @return
     */

    @ApiOperation(notes = "exchageApply",httpMethod = "POST",value = "申请换货",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderGoodsId", value = "订单商品ID", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "exchangeNum", value = "换货数量", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "goodName", value = "换货名称", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "exchangeReason", value = "换货原因", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "换货备注", required = true, paramType = "form",dataType = "String"),
    })
    @RequestMapping("/exchageApply")
    public AjaxJson exchageApply(OrderGoodsExchange orderGoodsExchange){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //判断退货申请中是否存在退换信息
        OrderGoods orderGoods = orderGoodsService.get(orderGoodsExchange.getOrderGoodsId());
        if(orderGoods != null && StringUtils.isNotEmpty(orderGoods.getIsExchange()) && (Global.GOODS_EXCHANGE_APPLY.equals(orderGoods.getIsExchange()) || Global.GOODS_EXCHANGE_APPLY_PASS.equals(orderGoods.getIsExchange()))){
            return  AjaxJson.fail("该商品已存在退货申请");
        }
        orderGoodsExchange.setApplyer(purchaserAccount.getId());
//        orderGoodsExchange.setApplyer("486773abf4a74ca199fdd0998e105158");
        orderGoodsExchange.setApplyTime(new Date());
        orderGoodsExchange.setAuditState("0");
        orderGoodsExchangeService.exchangeApply(orderGoodsExchange);
        return AjaxJson.ok("申请换货成功！");
    }

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
            @ApiImplicitParam(name = "createDateStart", value = "申请开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createDateEnd", value = "申请结束时间", required = false, paramType = "query", dataType = "String")})
    @RequestMapping("/exchageList")
    public AjaxJson exchageList(OrderGoodsExchange orderGoodsExchange, HttpServletRequest request, HttpServletResponse response){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
//        orderGoodsExchange.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        orderGoodsExchange.setPurchaserId(purchaserAccount.getPurchaserId());
        Page<OrderGoodsExchange> page = orderGoodsExchangeService.findPage(new Page<OrderGoodsExchange>(request,response),orderGoodsExchange);
        return AjaxJson.layuiTable(page);
    }

    /**
     * 查看换货详情
     * @param orderGoodsExchange
     * @return
     */
    @ApiOperation(notes = "detail",httpMethod = "GET",value = "采购商查看换货详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "申请单ID", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/detail")
    public AjaxJson detail(OrderGoodsExchange orderGoodsExchange){
       //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        orderGoodsExchange.setPurchaserId(purchaserAccount.getPurchaserId());
//        orderGoodsExchange.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        if(orderGoodsExchange == null || StringUtils.isEmpty(orderGoodsExchange.getId())){
            return AjaxJson.fail("参数错误");
        }
        orderGoodsExchange = orderGoodsExchangeService.get(orderGoodsExchange);
        if(orderGoodsExchange == null){
            return AjaxJson.fail("获取换货详情失败");
        }
        return AjaxJson.ok(orderGoodsExchange);

    }
}
