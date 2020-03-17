package com.jhmis.api.store;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.OrderGoodsService;
import com.jhmis.modules.shop.service.OrdersService;
import com.jhmis.modules.shop.service.purchaser.PurchaserMsgService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "ApiStoreOrdersController", description = "店铺订单管理")
@RestController
@RequestMapping("/api/store/orders")
public class ApiStoreOrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private PurchaserMsgService purchaserMsgService;

    /**
     * 供应商订单列表
     * @param orders
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(notes = "list", httpMethod = "GET", value = "供应商订单列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "当前第几页", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createDateStart", value = "下单开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createDateEnd", value = "下单结束时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isInvoiceFinished", value = "待开票0 已开票 1", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "orderState", value = "订单状态", required = false, paramType = "query", dataType = "int")
    })
    @RequestMapping("list")
    public AjaxJson list(Orders orders, HttpServletRequest request, HttpServletResponse response){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        orders.setDealerId(currentAccount.getDealerId());
        // 待开票==已申请未开票
        if(Global.NO.equals(orders.getIsInvoiceFinished())){
            orders.setIsApplyInvoice(Global.YES);
        }
        //订单中要包含订单产品信息
        Page<Orders> page = ordersService.findPage(new Page<Orders>(request,response),orders);
        return AjaxJson.layuiTable(page);
    }

    /**
     * 订单详情
     * @return
     */
    @ApiOperation(notes = "detail", httpMethod = "GET", value = "供应商-订单详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "订单ID", required = false, paramType = "query",dataType = "String")})
    @RequestMapping("/detail")
    public AjaxJson detail(String id){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isEmpty(id)){
            return AjaxJson.fail("参数错误");
        }
        Orders orders = ordersService.get(id);
        if(orders == null){
            return AjaxJson.fail("订单不存在");
        }
        if (!orders.getDealerId().equals(currentAccount.getDealerId())) {
            return AjaxJson.fail("非法访问");
        }
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderId(id);
        List<OrderGoods> orderGoodsList = orderGoodsService.findList(orderGoods);
        orders.setOrdersGoodsList(orderGoodsList);
        return AjaxJson.ok(orders);
    }
    @ApiOperation(notes = "deliverGoods", httpMethod = "POST", value = "供应商-发货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "shippingInfo", value = "订单ID", required = true, paramType = "form",dataType = "String")})
    @RequestMapping("/deliverGoods")
    public AjaxJson deliverGoods(String id,String shippingInfo){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isEmpty(id)){
            return  AjaxJson.fail("订单ID 不能为空");
        }
        if(StringUtils.isEmpty(shippingInfo)){
            return AjaxJson.fail("发货信息不能为空");
        }
        Orders orders = ordersService.get(id);
        if(orders == null){
            return AjaxJson.fail("订单信息不存在");
        }
        if (!orders.getDealerId().equals(currentAccount.getDealerId())) {
            return AjaxJson.fail("非法访问");
        }
        if(Global.ORDER_STATE_PAY_FINISHED !=orders.getOrderState()){
            return AjaxJson.fail("该订单所在状态不支持发货");
        }
        orders.setOrderState(Global.ORDER_STATE_SHIPPED);
        orders.setShippingInfo(shippingInfo);
        ordersService.save(orders);
        return AjaxJson.ok("发货成功！");
    }

    /**
     * 开发票
     * @return
     */
    @ApiOperation(notes = "drawABill", httpMethod = "POST", value = "店铺-开发票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "form",dataType = "String")
         })
    @RequestMapping("/drawABill")
    public AjaxJson drawABill(String id){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isEmpty(id)){
            return AjaxJson.fail("参数错误");
        }
        Orders orders = ordersService.findUniqueByProperty("id",id);
        if(orders == null){
            return AjaxJson.fail("订单不存在");
        }
        if(!Global.YES.equals(orders.getIsApplyInvoice())){
            return AjaxJson.fail("该订单未申请开票");
        }
        if(Global.YES.equals(orders.getIsInvoiceFinished())){
            return AjaxJson.fail("该订单已开发票");
        }
        orders.setIsInvoiceFinished(Global.YES);
        ordersService.save(orders);
        return AjaxJson.ok("开票成功");
    }

    /**
     * 店铺看板相关订单统计
     * @return
     */
    @ApiOperation(notes = "boardStatis", httpMethod = "POST", value = "店铺-看板-订单统计")
    @RequestMapping("/boardStatis")
    public AjaxJson boardStatis(){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Orders orders = new Orders();
        orders.setDealerId(currentAccount.getDealerId());
//        orders.setDealerId("1");
        orders.setOrderState(Global.ORDER_STATE_NEW);
        int underPay = ordersService.findByOrderState(orders);
        orders.setOrderState(Global.ORDER_STATE_PAY_FINISHED);
        int underSendOut = ordersService.findByOrderState(orders);
        orders.setOrderState(Global.ORDER_STATE_SHIPPED);
        int underRecevice = ordersService.findByOrderState(orders);
        Map<String,Integer> map = new HashMap<>();
        map.put("underPay",underPay);
        map.put("underSendOut",underSendOut);
        map.put("underRecevice",underRecevice);
        return AjaxJson.ok(map);
    }
}
