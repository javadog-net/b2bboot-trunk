package com.jhmis.api.purchaser;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.entity.OrderPay;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.mapper.OrderPayMapper;
import com.jhmis.modules.shop.service.OrderGoodsService;
import com.jhmis.modules.shop.service.OrderPayService;
import com.jhmis.modules.shop.service.OrdersService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.jhmis.modules.shop.service.purchaser.PurchaserMsgService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Api(value = "ApiPurchaserOrdersController", description = "采购商-订单管理")
@RestController
@RequestMapping("/api/purchaser/orders")
public class ApiPurchaserOrdersController  {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private PurchaserAddressService purchaserAddressService;
    @Autowired
    private PurchaserInvoiceService purchaserInvoiceService;
    @Autowired
    private PurchaserMsgService purchaserMsgService;
    @Autowired
    private OrderPayMapper orderPayMapper;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(ApiPurchaserOrdersController.class);

    /**
     * 订单列表
     * @return
     */
    @ApiOperation(notes = "list", httpMethod = "GET", value = "订单列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "orderState", value = "订单状态", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "orderSn", value = "订单号", required = false, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "createDateStart", value = "下单开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "delFlag", value = "删除标志（1 删除）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createDateEnd", value = "下单结束时间", required = false, paramType = "query", dataType = "String")})
    @RequestMapping("/list")
    public AjaxJson list(OrderPay orderPay,Orders orders, HttpServletRequest request, HttpServletResponse response){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getPurchaserId())){
            return AjaxJson.fail("当前账号未登录");
        }
        orders.setPurchaserId(purchaserAccount.getPurchaserId());
//        orders.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        Page<OrderPay> page = orderPayService.findPage(new Page<OrderPay>(request,response),orderPay,orders);
        return AjaxJson.layuiTable(page);
    }

    /**
     * 订单详情
     * @return
     */
    @ApiOperation(notes = "detail", httpMethod = "GET", value = "订单详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "query",dataType = "String")})
    @RequestMapping("/detail")
    public AjaxJson detail(String id){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getPurchaserId())){
            return AjaxJson.fail("当前账号未登录");
        }
        if(StringUtils.isEmpty(id)){
            return AjaxJson.fail("参数错误");
        }
        Orders orders = ordersService.get(id);
        if(orders == null){
            return AjaxJson.fail("订单不存在");
        }
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderId(id);
        List<OrderGoods> orderGoodsList = orderGoodsService.findList(orderGoods);
        orders.setOrdersGoodsList(orderGoodsList);
        return AjaxJson.ok(orders);
    }

    /**
     * 立即购买
     * @param
     * @return
     */
    @ApiOperation(notes = "save",httpMethod = "POST",value = "立即购买订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "num", value = "购买数量", required = true, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "purchaserAddressId", value = "地址ID", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "purchaserInvoiceId", value = "发票ID", required = false, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "isApplyInvoice", value = "是否需要开发票", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/save")
    public AjaxJson save(String storeGoodsId,Integer num,String purchaserAddressId,String purchaserInvoiceId,String isApplyInvoice){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //判断参数是否正确
        if(StringUtils.isEmpty(storeGoodsId) || num == null){
            return AjaxJson.fail("参数错误!");
        }
        PurchaserAddress purchaserAddress = new PurchaserAddress();
        purchaserAddress.setId(purchaserAddressId);
        purchaserAddress.setPurchaserId(purchaserAccount.getPurchaserId());
//        purchaserAddress.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        if(StringUtils.isNotEmpty(purchaserAddressId)){
            List<PurchaserAddress> purchaserAddressList = purchaserAddressService.findList(purchaserAddress);
            if(purchaserAddressList.size() == 0){
                return AjaxJson.fail("采购商地址不存在!");
            }
            purchaserAddress = purchaserAddressList.get(0);
        }
        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
        purchaserInvoice.setPurchaserId(purchaserAccount.getPurchaserId());
//       purchaserInvoice.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        if(StringUtils.isNotEmpty(isApplyInvoice) && Global.YES.equals(isApplyInvoice) && StringUtils.isNotEmpty(purchaserInvoiceId)){
           List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
            if(purchaserInvoiceList.size() == 0){
                return AjaxJson.fail("采购商发票地址不能为空!");
            }
            purchaserInvoice = purchaserInvoiceList.get(0);
        }
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setId(storeGoodsId);
        storeGoods.setIsShelf(Global.YES);
        storeGoods.setPurchaserAccountId(purchaserAccount.getId());
        storeGoods.setPurchaserId(purchaserAccount.getPurchaserId());
//        storeGoods.setPurchaserId("486773abf4a74ca199fdd0998e105158");
//        storeGoods.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
        List<StoreGoods> storeGoodsList = storeGoodsService.findStoreGoodsAndProPrice(storeGoods);
        if(storeGoodsList.size() == 0 ){
            return AjaxJson.fail("该产品已下架");
        }
        if(storeGoodsList.size()>1){
            return AjaxJson.fail("店铺商品ID不正确");
        }
        //判断是否有库存
        for(StoreGoods storeG:storeGoodsList){
            if(storeG.getStock() < num){
                return AjaxJson.fail("库存不足！");
            }
        }
        List<String> ordersList = ordersService.save(purchaserAccount.getId(),num,storeGoodsList,purchaserAddress,purchaserInvoice,isApplyInvoice);
//      List<String> ordersList =    ordersService.save("581a099bb13941cc99741e66e2a543de",num,storeGoodsList,purchaserAddress,purchaserInvoice,isApplyInvoice);
        if(ordersList == null || ordersList.size() == 0 ){
            return AjaxJson.fail("提交订单失败");
        }else{
            return AjaxJson.ok(ordersList);
        }

    }
    /**
     * 从购物车保存订单
     * @param cartIds
     * @return
     */
    @ApiOperation(notes = "saveFromCart",httpMethod = "POST",value = "从购物车保存订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "cartIds", value = "购物车ID，多个ID之间英文逗号分割", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "purchaserAddressId", value = "地址ID", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "purchaserInvoiceId", value = "发票ID", required = false, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "isApplyInvoice", value = "是否需要开发票", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/saveFromCart")
    public AjaxJson saveFromCart(String cartIds,String purchaserAddressId,String purchaserInvoiceId,String isApplyInvoice){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //判断参数是否正确
        if(StringUtils.isEmpty(cartIds)){
            return   AjaxJson.fail("参数错误");
        }
        List<String> cartIdList = new ArrayList<>();
        if(cartIds.indexOf(",")>0){
            cartIdList = Arrays.asList(cartIds.split(","));
        }else{
            cartIdList.add(cartIds);
        }
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setIdList(cartIdList);
        List<StoreGoods> storeGoodsList = storeGoodsService.findCartStoreGoods(storeGoods);
        for(StoreGoods goods:storeGoodsList){
            if(goods.getStock() < goods.getChooseNum() ){
               return  AjaxJson.fail("商品："+goods.getGoodsName()+"库存不足");
            }
        }

        PurchaserAddress purchaserAddress = new PurchaserAddress();
        purchaserAddress.setId(purchaserAddressId);
        purchaserAddress.setPurchaserId(purchaserAccount.getPurchaserId());
//        purchaserAddress.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        if(StringUtils.isNotEmpty(purchaserAddressId)){
            List<PurchaserAddress> purchaserAddressList = purchaserAddressService.findList(purchaserAddress);
            if(purchaserAddressList.size() == 0){
                return AjaxJson.fail("采购商地址不存在!");
            }
            purchaserAddress = purchaserAddressList.get(0);
        }
        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
        purchaserInvoice.setPurchaserId(purchaserAccount.getPurchaserId());
//        purchaserInvoice.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        if(StringUtils.isNotEmpty(isApplyInvoice) && Global.YES.equals(isApplyInvoice) && StringUtils.isNotEmpty(purchaserInvoiceId)){
            List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
            if(purchaserInvoiceList.size() == 0){
                return AjaxJson.fail("采购商发票地址不能为空!");
            }
            purchaserInvoice = purchaserInvoiceList.get(0);
        }
       List<String> ordersList = ordersService.saveFromCart(cartIdList,purchaserAccount.getId(),purchaserAddress,purchaserInvoice,isApplyInvoice);
//      List<String> ordersList =  ordersService.saveFromCart(cartIdList,"581a099bb13941cc99741e66e2a543de",purchaserAddress,purchaserInvoice,isApplyInvoice);
       if(ordersList == null || ordersList.size() == 0){
           return AjaxJson.fail("提交订单失败");
       }else{
           return AjaxJson.ok(ordersList);
       }
    }
    /**
     * 取消订单
     * @param id
     * @return
     */
    @ApiOperation(notes = "cancelOrder", httpMethod = "POST", value = "取消订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "form",dataType = "String")})
    @RequestMapping(value = "cancelOrder" ,method = RequestMethod.POST)
    public  AjaxJson cancelOrder(String id){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //判断参数是否为空
        if(StringUtils.isEmpty(id)){
            return AjaxJson.fail("订单ID不能为空");
        }
        // 判断订单是否存在
        Orders orders = ordersService.get(id);
        if(orders == null){
            return AjaxJson.fail("订单信息不存在");
        }
        if(Global.ORDER_STATE_NEW != orders.getOrderState()){
            return AjaxJson.fail("该状态下的订单不能取消");
        }
        orders.setOrderState(Global.ORDER_STATE_CANCEL);
        orders.setCancelTime(new Date());
        orders.setCanceler(purchaserAccount.getId());
        ordersService.cancleOrder(orders);
        return AjaxJson.ok("取消订单成功！");
    }
    /**
     * 删除订单
     * @param ids
     * @return
     */
    @ApiOperation(notes = "deleteOrder", httpMethod = "POST", value = "删除订单")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "订单ID(多个订单之间英文逗号分割)", required = true, paramType = "form",dataType = "String")})
    @RequestMapping(value = "deleteOrder" ,method = RequestMethod.POST)
    public  AjaxJson deleteOrder(String ids){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //判断参数是否为空
        if(StringUtils.isEmpty(ids)){
            return AjaxJson.fail("订单ID不能为空");
        }
        List<String> idList = new ArrayList<>();
        if(ids.indexOf(",") > 0){
            idList = Arrays.asList(ids.split(","));
        }else{
            idList.add(ids);
        }
        ordersService.deleteByOrderId(idList);
        return AjaxJson.ok("删除订单成功！");
    }

    /**
     * 采购商  待付款、付收货  待发货数量
     * @return
     */
    @ApiOperation(notes = "boardStatis", httpMethod = "POST", value = "采购商-订单统计")
    @RequestMapping("/boardStatis")
    public AjaxJson boardStatis(){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        Orders orders = new Orders();
        orders.setPurchaserId(purchaserAccount.getPurchaserId());
//        orders.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        OrderPay pay = new OrderPay();
//        pay.setPurchaserId("486773abf4a74ca199fdd0998e105158");
        pay.setPurchaserId(purchaserAccount.getPurchaserId());
        pay.setOrderState(Global.ORDER_STATE_NEW);
        int underPay = orderPayMapper.getOrderPayCount(pay);
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
    @ApiOperation(notes = "confirmReceive", httpMethod = "POST", value = "确认收货")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "form",dataType = "String")})
    @RequestMapping(value = "confirmReceive" ,method = RequestMethod.POST)
    public  AjaxJson confirmReceive(String id,HttpServletRequest request,HttpServletResponse response){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //判断参数是否为空
        if(StringUtils.isEmpty(id)){
            return AjaxJson.fail("订单ID不能为空");
        }
        // 判断订单是否存在
        Orders orders = ordersService.get(id);
        if(orders == null){
            return AjaxJson.fail("订单信息不存在");
        }
        if(Global.ORDER_STATE_SHIPPED != orders.getOrderState()){
            return AjaxJson.fail("当前状态下不能确认收货");
        }
        orders.setOrderState(Global.ORDER_STATE_RECEIVED);
        orders.setDeliveryTime(new Date());
        orders.setConfirmReceiver(purchaserAccount.getId());
//        orders.setConfirmReceiver("581a099bb13941cc99741e66e2a543de");
        ordersService.confirmReceive(orders);
        String responseStr = ordersService.tradeSettle(orders);
        logger.info(responseStr);
        return AjaxJson.ok("确认收货成功！");
    }
}
