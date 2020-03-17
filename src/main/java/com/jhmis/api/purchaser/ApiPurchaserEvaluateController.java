package com.jhmis.api.purchaser;

import com.jhmis.api.shop.ApiShopBannerController;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserStoreFavorites;
import com.jhmis.modules.shop.service.*;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "ApiPurchaserEvaluateController", description = "采购商-评价管理")
@RestController
@RequestMapping("/api/purchaser/evaluate")
public class ApiPurchaserEvaluateController {

    protected Logger logger = LoggerFactory.getLogger(ApiPurchaserEvaluateController.class);
    @Autowired
    StoreService storeService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrderGoodsService orderGoodsService;

    @Autowired
    GoodsEvaluateService goodsEvaluateService;

    @Autowired
    PurchaserAccountService purchaserAccountService;

    @Autowired
    StoreEvaluateService storeEvaluateService;

    /**
     * 在订单点击评价总按钮,展示待评价内容
     * @return
     */
    @ApiOperation(notes = "toEvaluatedListShow", httpMethod = "GET", value = " 在订单点击评价总按钮,展示待评价内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/toEvaluatedListShow")
    public AjaxJson toEvaluatedListShow(String orderId){
        Map<String,Object> map = new HashMap<>();
        //参数check
        if(StringUtils.isEmpty(orderId)){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        //查询订单
        Orders order = ordersService.get(orderId);
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setOrderId(orderId);
        //查询店铺相关信息
        Store store = storeService.findUniqueByProperty("id",order.getStoreId());
        //查询订单商品
        List<OrderGoods> orderGoodsList = orderGoodsService.findList(orderGoods);
        map.put("store",store);
        map.put("orderGoodsList",orderGoodsList);
        return AjaxJson.ok(map);
    }


    /**
     * 商品评价(单一对产品评价)
     * @return
     */
    @ApiOperation(notes = "evaluate", httpMethod = "POST", value = " 商品评价",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderGoodsId", value = "订单商品id", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "content", value = "评价内容", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "isanonymous", value = "是否匿名", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "productQuality", value = "产品质量评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "demandResponse", value = "需求响应评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "deliveryCredit", value = "物流配送评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "supplySpeed", value = "供货速度评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "customerService", value = "售后服务评分", required = true, paramType = "form",dataType = "String")})
   /* @RequiresRoles("purchaser")*/
    @RequestMapping("/evaluate")
    public AjaxJson evaluate(GoodsEvaluate goodsEvaluate){
        //参数check
        if(StringUtils.isEmpty(goodsEvaluate.getOrderGoodsId())|| StringUtils.isEmpty(goodsEvaluate.getContent())|| StringUtils.isEmpty(goodsEvaluate.getIsanonymous())){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        goodsEvaluate.setPurchaserAccountId(currentAccount.getId());
        //查询唯一订单商品
        OrderGoods orderGoods = orderGoodsService.get(goodsEvaluate.getOrderGoodsId());
        if(orderGoods==null){
            return AjaxJson.fail("此订单商品不存在");
        }
        Orders orders = ordersService.findUniqueByProperty("id",orderGoods.getOrderId());
        if(orders==null){
            return AjaxJson.fail("此订单不存在");
        }
        goodsEvaluate.setOrderSn(orders.getOrderSn());
        boolean flag = goodsEvaluateService.saveGoodsEvaluate(goodsEvaluate,orders,orderGoods);
        if(!flag){
            return AjaxJson.fail("商品评价失败");
        }
        return AjaxJson.ok("商品评价成功");
    }

    /**
     * 商品二次评价(单一对产品追评)
     * @return
     */
    @ApiOperation(notes = "evaluateAgain", httpMethod = "POST", value = " 商品二次评价",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderGoodsId", value = "订单商品id", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "contentAgain", value = "追加评价内容", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "imageAgain", value = "追加评价图片", required = false, paramType = "form",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/evaluateAgain")
    public AjaxJson evaluateAgain(GoodsEvaluate goodsEvaluate){
        //参数check
        if(StringUtils.isEmpty(goodsEvaluate.getOrderGoodsId())|| StringUtils.isEmpty(goodsEvaluate.getContentAgain())){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        goodsEvaluate.setPurchaserAccountId(currentAccount.getId());
        //查询唯一订单商品
        OrderGoods orderGoods = orderGoodsService.get(goodsEvaluate.getOrderGoodsId());
        Orders orders = ordersService.findUniqueByProperty("id",orderGoods.getOrderId());
        if(orders==null){
            return AjaxJson.fail("此订单不存在");
        }
        boolean flag = goodsEvaluateService.goodsEvaluateAgain(goodsEvaluate,orders,orderGoods);
        return AjaxJson.ok("商品再次评价成功");
    }

    /**
     * 订单统一评价(实体嵌套list)
     * @return
     */
    @ApiOperation(notes = "orderEvaluate", httpMethod = "POST", value = "订单统一评价(实体嵌套list,参照orderEvaluate.jsp)",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "isanonymous", value = "是否匿名", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "scoreProductQuality", value = "产品质量评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "scoreDemandResponse", value = "物流配送评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "scoreDemandResponse", value = "物流配送评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "scoreSupplySpeed", value = "供货速度评分", required = true, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "scoreCustomerService", value = "售后服务评分", required = true, paramType = "form",dataType = "String")})

  /*  @RequiresRoles("purchaser")*/
    @RequestMapping("/orderEvaluate")
    public AjaxJson orderEvaluate(OrderEvaluate orderEvaluate){
        logger.info("*************进入orderEvaluate***************");
        //参数check
        if(StringUtils.isEmpty(orderEvaluate.getOrderId())|| StringUtils.isEmpty(orderEvaluate.getIsanonymous())){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        logger.info("*****************findUniqueByProperty前***************");
        Orders orders = ordersService.findUniqueByProperty("id",orderEvaluate.getOrderId());
        logger.info("*****************findUniqueByProperty后***************");
        if(orders==null){
            return AjaxJson.fail("此订单不存在");
        }
        orderEvaluate.setPurchaserAccountId(currentAccount.getId());
        orderEvaluate.setPurchaserId(currentAccount.getPurchaserId());
        logger.info("*****************saveStoreEvaluate前***************");
        try{
            boolean flag = storeEvaluateService.saveStoreEvaluate(orderEvaluate,orders,currentAccount);
            if(!flag){
                logger.info("店铺评价失败");
                return AjaxJson.fail("店铺评价失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return AjaxJson.ok("店铺评价成功");
    }

    /**
     * 已评价商品详情接口
     * @return
     */
    @ApiOperation(notes = "hasEvaluatedList", httpMethod = "POST", value = "展示已经评价的商品列表（用于我的评价）",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsName", value = "商品名称", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "orderSn", value = "订单编码", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "storeName", value = "店铺名称", required = false, paramType = "form",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/hasEvaluatedList")
    public AjaxJson hasEvaluatedList(GoodsEvaluate goodsEvaluate, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        goodsEvaluate.setPurchaserAccountId(currentAccount.getId());
        Page<GoodsEvaluate> newPage= new Page<GoodsEvaluate>(request, response);
        newPage.setOrderBy("addtime desc");
        //查询列表
        Page<GoodsEvaluate> goodsEvaluatePage = goodsEvaluateService.findPage(new Page<GoodsEvaluate>(request, response), goodsEvaluate);
        return AjaxJson.ok(goodsEvaluatePage);
    }


    /**
     * 点击商品再次评价带出之前评价内容
     * @return
     */
    @ApiOperation(notes = "evaluateAgainShowLast", httpMethod = "GET", value = " 点击商品再次评价带出之前评价内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderGoodsId", value = "订单商品id", required = true, paramType = "query",dataType = "String"),
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/evaluateAgainShowLast")
    public AjaxJson evaluateAgainShowLast(String orderGoodsId){
        //参数check
        if(StringUtils.isEmpty(orderGoodsId)){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        //查询唯一订单商品
        OrderGoods orderGoods = orderGoodsService.get(orderGoodsId);
        if(orderGoods==null){
            return AjaxJson.fail("此订单产品不存在");
        }
        GoodsEvaluate goodsEvaluate = new GoodsEvaluate();
        goodsEvaluate.setOrderId(orderGoods.getOrderId());
        goodsEvaluate.setGoodsCode(orderGoods.getGoodsCode());
        goodsEvaluate.setPurchaserAccountId(currentAccount.getId());
        List<GoodsEvaluate> goodsEvaluateList= goodsEvaluateService.findList(goodsEvaluate);
        if(goodsEvaluateList!=null && goodsEvaluateList.size()>0){
            goodsEvaluate = goodsEvaluateList.get(0);
        }else{
            return AjaxJson.fail("该商品尚未评价");
        }
        return AjaxJson.ok(goodsEvaluate);
    }


    /**
     * 订单评价详情
     * @return
     */
    @ApiOperation(notes = "orderEvaluatedDetail", httpMethod = "GET", value = "订单评价详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/orderEvaluatedDetail")
    public AjaxJson orderEvaluatedDetail(String orderId){
        //参数验证
        if(StringUtils.isEmpty(orderId)){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        StoreEvaluate storeEvaluate = new StoreEvaluate();
        storeEvaluate.setPurchaserAccountId(currentAccount.getId());
        storeEvaluate.setOrderId(orderId);
        List<StoreEvaluate> storeEvaluateList =  storeEvaluateService.findList(storeEvaluate);
        if(storeEvaluateList!=null && storeEvaluateList.size()>0){
            //存在值
            storeEvaluate = storeEvaluateList.get(0);
        }else{
            return AjaxJson.fail("不存在此店铺评价");
        }
        GoodsEvaluate goodsEvaluate = new GoodsEvaluate();
        goodsEvaluate.setPurchaserAccountId(currentAccount.getId());
        goodsEvaluate.setOrderId(orderId);

        List<GoodsEvaluate> goodsEvaluates = goodsEvaluateService.findList(goodsEvaluate);
        storeEvaluate.setGoodsEvaluates(goodsEvaluates);
        return AjaxJson.ok(storeEvaluate);
    }


    /**
     * 单一产品评价详情
     * @return
     */
    @ApiOperation(notes = "goodsEvaluatedDetail", httpMethod = "GET", value = "单一产品评价详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderGoodsId", value = "订单商品id", required = true, paramType = "query",dataType = "String"),
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/goodsEvaluatedDetail")
    public AjaxJson goodsEvaluatedDetail(String orderGoodsId){
        //参数验证
        if(StringUtils.isEmpty(orderGoodsId)){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        //查询唯一订单商品
        OrderGoods orderGoods = orderGoodsService.get(orderGoodsId);
        if(orderGoods==null){
            return AjaxJson.fail("此商品订单不存在");
        }
        GoodsEvaluate goodsEvaluate = new GoodsEvaluate();
        goodsEvaluate.setOrderId(orderGoods.getOrderId());
        goodsEvaluate.setPurchaserAccountId(currentAccount.getId());
        goodsEvaluate.setGoodsCode(orderGoods.getGoodsCode());
        List<GoodsEvaluate> goodsEvaluateList = goodsEvaluateService.findList(goodsEvaluate);
        if(goodsEvaluateList!=null && goodsEvaluateList.size()>0){
            goodsEvaluate = goodsEvaluateList.get(0);
        }else{
            return AjaxJson.fail("此商品评价不存在");
        }
        return AjaxJson.ok(goodsEvaluate);
    }

}
