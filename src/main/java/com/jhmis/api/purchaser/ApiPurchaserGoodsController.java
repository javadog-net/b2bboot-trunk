package com.jhmis.api.purchaser;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.purchasergoodsrel.entity.PurchaserGoodsRel;
import com.jhmis.modules.purchasergoodsrel.service.PurchaserGoodsRelService;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.*;
import com.jhmis.modules.shop.service.purchaser.PurchaserStoreFavoritesService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "ApiPurchaserGoodsController", description = "采购商-可采商品")
@RestController
@RequestMapping("/api/purchaser/goods")
public class ApiPurchaserGoodsController {
    protected Logger logger = LoggerFactory.getLogger(ApiPurchaserGoodsController.class);
    @Autowired
    GoodsCategoryService goodsCategoryService;
    @Autowired
    private PurchaserGoodsRelService purchaserGoodsRelService;
    @Autowired
    private CortryGoodsService cortryGoodsService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private GoodsExtService goodsExtService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private GoodsBrowseService goodsBrowseService;
    @Autowired
    PurchaserStoreFavoritesService purchaserStoreFavoritesService;
    long key = Constants.PRODUCTS_CENTER_KEY;
    String secret = Constants.PRODUCTS_CENTER_SECRET;

    /**
     * 可采购商品
     *
     * @param
     * @return
     */
    @ApiOperation(notes = "search", httpMethod = "POST", value = "可采购商品")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "产品编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "产品名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "brand", value = "产品品牌", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "purchaserId", value = "采购商ID", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "form", dataType = "int")
    })

    @RequestMapping("/search")
    public AjaxJson search(String code, String name, String brand, int pageNo, int pageSize,String purchaserId) {
        // 获取当前登录信息
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        // 登录信息不为空 登录信息id不为空
        if (account == null && StringUtils.isEmpty(account.getId())) {
            logger.info("22222");
            return AjaxJson.fail("采购商未登录");
        }
        // 可采商品进行分页  泛型 purchaserGoodsRel可采商品
        Page<PurchaserGoodsRel> page = new Page<PurchaserGoodsRel>();
        // 当前页和每页 显示的条数 设置到page
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        return cortryGoodsService.search(code, name, brand, page,purchaserId);
    }

    /**
     * 可采商商品详情
     *
     * @return
     */
    @ApiOperation(notes = "goodsDetail", httpMethod = "GET", value = "商品详情")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id", value = "可采商品ID", required = true, paramType = "query", dataType = "String")
            })
    @RequestMapping("goodsDetail")
    public AjaxJson goodsDetail(String id) {
        // 获取当前登录的信息
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        // 如果账号不等于空
        if (purchaserAccount == null && StringUtils.isEmpty(purchaserAccount.getPurchaserId())) {
            return AjaxJson.fail("账户异常,或账户不存在");
        }
        // 参数验证
        if (StringUtils.isEmpty(id)) {
            return AjaxJson.fail("参数不能为空");
        }
        // 可采商品查询
        PurchaserGoodsRel purchaserGoodsRel = purchaserGoodsRelService.get(id);
        if (purchaserGoodsRel == null) {
            return AjaxJson.fail("可采商品不存在");
        }
        PurchaserGoodsRel goodsRel = new PurchaserGoodsRel();
        goodsRel.setPurchaserId(id);
        List<PurchaserGoodsRel> list = purchaserGoodsRelService.findList(goodsRel);
        purchaserGoodsRel.setPurchaserGoodsRelList(list);
        return AjaxJson.ok(purchaserGoodsRel);
    }
      /**
     * 可采商商品详情
     *
     * @return
     */
//    @ApiOperation(notes = "GoodsDetail", httpMethod = "GET", value = "商品详情")
//    @ApiImplicitParams(
//            {@ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "query", dataType = "String")
//            })
//    @RequestMapping("GoodsDetail")
//    public AjaxJson GoodsDetail(String id) {
//        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
////        if (purchaserAccount == null && StringUtils.isEmpty(purchaserAccount.getPurchaserId())) {
////            return AjaxJson.fail("账户异常,或账户不存在");
////        }
//        // 参数验证
//        if (StringUtils.isEmpty(id)) {
//            return AjaxJson.fail("参数不能为空");
//        }
//        // 可采商品查询
//        GoodsBrowse goodsBrowse = goodsBrowseService.get(id);
////        if (goodsBrowse == null) {
////            return AjaxJson.fail("可采商品不存在");
////        }
//        GoodsBrowse browse = new GoodsBrowse();
//        browse.setStoreGoodsId(id);
//        List<GoodsBrowse> list = goodsBrowseService.findList(browse);
//        goodsBrowse.setGoodsBrowselList(list);
//        return AjaxJson.ok(goodsBrowse);
//    }

    /**
     * 子账号订单列表页
     */
    @ApiOperation(notes = "Slave", httpMethod = "GET", value = "子账号订单列表页")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "orderState", value = "订单状态", required = false, paramType = "query",dataType = "int"),
            @ApiImplicitParam(name = "orderSn", value = "订单号", required = false, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "createDateStart", value = "下单开始时间", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "delFlag", value = "删除标志（1 删除）", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createDateEnd", value = "下单结束时间", required = false, paramType = "query", dataType = "String")})
    @RequestMapping("/Slave")
    public AjaxJson Slave(OrderPay orderPay, Orders orders, HttpServletRequest request, HttpServletResponse response){
        // 判断当前用户是否登录有效
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getPurchaserId())){
            return AjaxJson.fail("当前账号未登录");
        }
        // 订单设置到采购商id中
//        orders.setPurchaserId(purchaserAccount.getPurchaserId());
        // 订单支付 设置到分页
        Page<OrderPay> orderPayPage = new Page<OrderPay>(request,response);
        //订单支付 分页查询
        Page<OrderPay> page = orderPayService.findPage(orderPayPage,orderPay,orders);
//        Page<OrderPay> page = orderPayService.findPage(new Page<OrderPay>(request,response),orderPay,orders);
        // 返回分页
        return AjaxJson.layuiTable(page);
    }

    /**
     * 订单详情
     * @return
     */
    @ApiOperation(notes = "Ordersdetail", httpMethod = "GET", value = "订单详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "订单ID", required = true, paramType = "query",dataType = "String")})
    @RequestMapping("/Ordersdetail")
    public AjaxJson Ordersdetail(String id){
        // 判断当前用户是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount!=null || StringUtils.isEmpty(purchaserAccount.getPurchaserId())){
            return AjaxJson.fail("当前账号未登录");
        }
        if (StringUtils.isEmpty(id)){
            return AjaxJson.fail("当前参数错误");
        }
        Orders orders = ordersService.get(id);
        if (orders==null){
            return AjaxJson.fail("当前订单不存在");
        }
        // 创建订单商品对象
        OrderGoods orderGoods = new OrderGoods();
        //id设置到订单商品里面
        orderGoods.setOrderId(id);
        // 查询订单商品集合
        List<OrderGoods> orderGoodsList = orderGoodsService.findList(orderGoods);
        // 把查处出集合 设置到订单中
        orders.setOrdersGoodsList(orderGoodsList);
        // 返回订单
        return AjaxJson.ok(orders);
    }
}
