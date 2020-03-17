package com.jhmis.api.directpurchaser;


import com.alibaba.fastjson.JSONObject;
import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.Sign;
import com.haier.link.upper.model.SimpleProduct;
import com.haier.link.upper.service.LinkProductUpperReadService;
import com.haier.order.dto.*;
import com.haier.order.utils.DateFormatHelper;
import com.haier.order.utils.OrderServiceUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cart.dto.CartCookieDTO;
import com.jhmis.modules.cart.dto.CartCookieProductDTO;
import com.jhmis.modules.cart.dto.CartManagerDTO;
import com.jhmis.modules.cart.dto.CartManagerDetailDTO;
import com.jhmis.modules.cart.service.CartCookieService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserAccountService;
import com.jhmis.modules.purchasermainrel.entity.PurchaserMasterSlaveRel;
import com.jhmis.modules.purchasermainrel.service.PurchaserMasterSlaveRelService;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(value = "ApiDirectPurchaserOrdersController", description = "采购商-订单管理")
@RestController
@RequestMapping("/api/directpurchaser/orders")
public class ApiDirectPurchaserOrdersController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(ApiDirectPurchaserOrdersController.class);

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private PurchaserAddressService purchaserAddressService;
    @Autowired
    private PurchaserInvoiceService purchaserInvoiceService;
    @Autowired
    private MdmCustomersSourceService mdmCustomersSourceService;
    @Autowired
    private CartCookieService cartCookieService;
    @Autowired
    private PurchaserMasterSlaveRelService purchaserMasterSlaveRelService;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private PurchaserAccountService purchaserAccountService;
    @Autowired
    private DirectPurchaserAccountService directPurchaserAccountService;
    @Lazy
    @Autowired
    private LinkProductUpperReadService linkProductUpperReadService;
    long key = Constants.PRODUCTS_CENTER_KEY;
    String secret = Constants.PRODUCTS_CENTER_SECRET;


    /**
     * 购物车cookies购物车对象
     */
    public static final String COOKIE_CART_NAME = "cartManager";






    @ApiOperation(notes = "sendProCode",httpMethod = "POST",value = "获取产品组数据")
    @RequestMapping(value = "/sendProCode")
    public AjaxJson sendProCode(String code, RedirectAttributes redirectAttributes) throws Exception {
        //非空验证
        Sign s = new Sign(key, secret);
        try{
            ProductInfo productInfo = linkProductUpperReadService.getProductInfoByCode(code, s).getResult();
            if(productInfo==null){
                return AjaxJson.fail("接口异常,产品中心数据查询为空" );
            }
        }catch (Exception e){
            return AjaxJson.fail("接口异常,异常原因:" + e.getMessage());
        }
        addMessage(redirectAttributes, "调取产品中心成功");
        return AjaxJson.ok("查询成功",linkProductUpperReadService.getProductInfoByCode(code, s).getResult());
    }


    /**
     * 保存云仓信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/saveWarehouseStatus")
    public String saveWarehouseStatus(HttpServletRequest request,HttpServletResponse response) {
        try {
            InputStream inputStream=request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inputStream.close();
            String params = new String(outSteam.toByteArray(), "UTF-8");
            System.out.println("params========="+params);
            RetDTO dto = OrderServiceUtils.saveWarehouseStatus(params);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                return dto.getRetData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }





    /**
     * 立即购买
     * @param
     * @return
     */
    @ApiOperation(notes = "save",httpMethod = "POST",value = "立即购买订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsIds", value = "店铺商品ID和数量，多个商品以逗号隔开", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "purchaserAddressId", value = "地址ID", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "purchaserInvoiceId", value = "发票ID", required = false, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "isApplyInvoice", value = "是否需要开发票", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/save")
    public AjaxJson save(CartManagerDTO cart){
        long time1=System.currentTimeMillis();
        log.info("===========立即购买=保存订单开始============="+time1);
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isEmpty(account.getId())|| !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        String storeGoodsIds = cart.getStoreGoodsIds();
        String purchaserAddressId = cart.getPurchaserAddressId();
        String isApplyInvoice = cart.getIsApplyInvoice();
        String purchaserInvoiceId = cart.getPurchaserInvoiceId();
        String orderInvoiceType = cart.getOrderInvoiceType();

        //获取公司信息
        Purchaser purchaser = purchaserService.get(account.getPurchaserId());
        if(purchaser != null){
            cart.setBillToPartyCode(purchaser.getLoginNum());
            cart.setBillToPartyName(purchaser.getCompanyName());
        }

        //判断参数是否正确
        if(StringUtils.isEmpty(storeGoodsIds)){
            return AjaxJson.fail("参数错误!");
        }
        if(StringUtils.isBlank(purchaserAddressId)){
            return AjaxJson.fail("收货地址不能为空！");
        }
        if("1".equals(isApplyInvoice) && StringUtils.isBlank(purchaserInvoiceId)){
            return AjaxJson.fail("需要开发票请选择发票！");
        }



        List<String> idList = new ArrayList<String>();
        Map<String,Integer> productNumMap = new HashMap<String,Integer>();
        String[] storeGoodsIdList = storeGoodsIds.split(",");
        if(storeGoodsIdList != null && storeGoodsIdList.length > 0){
            for (int i = 0; i < storeGoodsIdList.length; i++){
                String product_num = storeGoodsIdList[i];
                if(StringUtils.isNotBlank(product_num)){
                    String[] productNum = product_num.split("_");
                    if(productNum != null && productNum.length > 0){
                        idList.add(productNum[0]);
                        productNumMap.put(productNum[0],Integer.parseInt(productNum[1]));
                    }
                }

            }
        }
        String purchaserId = account.getPurchaserId();
        if("0".equals(purchaser.getIsAdmin())){
            PurchaserMasterSlaveRel rel = purchaserMasterSlaveRelService.getMasterPurchaser(purchaserId);
            purchaserId = rel.getPurchaserMasterId();
        }
        Goods goods = new Goods();
        goods.setCodeList(idList);
        goods.setPurchaserId(purchaserId);
        List<Goods> goodsList = goodsService.findByCodeList(goods);

        if(goodsList.size() ==0){
            return AjaxJson.fail("商品不存在");
        }
        Map<String, StoreGoods> storeGoodsMap = new HashMap<String, StoreGoods>();
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setIdList(idList);
        List<StoreGoods> storeGoodsList = storeGoodsService.getStoreGoodsByCodes(storeGoods);
        if(CollectionUtils.isNotEmpty(storeGoodsList)){
            for(StoreGoods s : storeGoodsList){
                storeGoodsMap.put(s.getCode(),s);
            }
        }
        //获取商品
        goodsList.forEach(g -> {
            if(StringUtils.isNotBlank(g.getCode())){
                g.setChooseNum(productNumMap.get(g.getCode()));
                if(storeGoodsMap != null){
                    StoreGoods s = storeGoodsMap.get(g.getCode());
                    if(s != null){
                        g.setStoreGoodsId(s.getId());
                    }
                }
            }
        });

        //查找购物车信息
        List<CartCookieProductDTO> listCookies = getCookiesProduct(goodsList);


        //获取购物车信息
        Map<String,List<CartCookieProductDTO>> cartMap = new HashMap<String,List<CartCookieProductDTO>>();
        cartMap.put(COOKIE_CART_NAME,listCookies);
        if(CollectionUtils.isEmpty(listCookies)){
            return AjaxJson.fail("商品数据错误");
        }
        Map<String,CartCookieProductDTO> productMap = new HashMap<String,CartCookieProductDTO>();
        List<String> storeIdList = new ArrayList<String>();
        listCookies.forEach(m -> {
            productMap.put(m.getStoreGoodsId(),m);
            storeIdList.add(m.getStoreId());
        });


        PurchaserAddress purchaserAddress = new PurchaserAddress();
        purchaserAddress.setId(purchaserAddressId);
        purchaserAddress.setPurchaserId(account.getPurchaserId());
        if(StringUtils.isNotEmpty(purchaserAddressId)){
            List<PurchaserAddress> purchaserAddressList = purchaserAddressService.findList(purchaserAddress);
            if(purchaserAddressList.size() == 0){
                return AjaxJson.fail("采购商地址不存在!");
            }
            purchaserAddress = purchaserAddressList.get(0);
        }
        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
        purchaserInvoice.setPurchaserId(account.getPurchaserId());
        if(StringUtils.isNotEmpty(isApplyInvoice) && Global.YES.equals(isApplyInvoice) && StringUtils.isNotEmpty(purchaserInvoiceId)){
            List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
            if(purchaserInvoiceList.size() == 0){
                return AjaxJson.fail("采购商发票地址不能为空!");
            }
            purchaserInvoice = purchaserInvoiceList.get(0);
        }
        //根据送达方的88码获取销售工厂
        String code = cart.getShipToPartyCode();
        MdmCustomersSource  mdms=new MdmCustomersSource ();
        mdms.setCusCode(code);
        List<MdmCustomersSource>  ms= mdmCustomersSourceService.findList(mdms);
        if(CollectionUtils.isNotEmpty(ms)){
            MdmCustomersSource s=ms.get(0);
            if(s!=null){
                //销售工厂
                cart.setSalesFactory(s.getComCode());
                //工贸
                cart.setMainHouse(s.getOrgId());
            }
        }

        //获取登录人所属公司
        Purchaser p = purchaserService.get(account.getPurchaserId());
        long time2 = System.currentTimeMillis();
        log.info("============组装数据开始============="+time2);
        // 声明参数
        //组织下单信息
        PlaceOrderDTO placeOrderDTO = setPlaceOrderDTO(cartMap,purchaserAddress,purchaserInvoice,null,p.getId(),cart);
        placeOrderDTO.setCustomerUuid(p.getId());
        placeOrderDTO.setCustomerName(p.getCompanyName());
        placeOrderDTO.setCustomerCode(p.getLoginNum());
        long time3 = System.currentTimeMillis();
        log.info("============组装数据结束============="+time3);
        log.info("============组装数据所用时间============="+ (time3 - time2));
        boolean isCanPay = false;
        if("2".equals(cart.getPayType())){
            if(!"1".equals(account.getIsAdmin())){
                return AjaxJson.fail("该账号不是主账号,无法进行授信支付");
            }
            PurchaserAccount a = purchaserAccountService.getAdminByPurchaserId(account.getPurchaserId());
            if(a == null){
                return AjaxJson.fail("该账的主账号信息错误");
            }
            //授信额度
            double creditBalance = directPurchaserAccountService.getCreditBalance(a.getLoginNum());
            if(creditBalance < placeOrderDTO.getTotalMoney()){
                return AjaxJson.fail("授信额度不足，无法下单");
            }
            isCanPay = true;
        }
        List<String> ordersList = new ArrayList<String>();

        AjaxJson ret = new AjaxJson();
        try{
            long time4 = System.currentTimeMillis();
            log.info("============保存订单开始============="+time4);
            // 调用方法
            RetDTO dto = OrderServiceUtils.batchSaveSmallShopOrder4others(placeOrderDTO);
            long time5 = System.currentTimeMillis();
            log.info("============保存订单结束============="+time5);
            log.info("============保存订单所用时间=============" + (time5 - time4));
            log.info("============保存订单返回数据=============" + dto);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                Map<String,Object> map = JSONObject.parseObject(dto.getRetData(),Map.class);
                if(map != null && "SUCCESS".equals(map.get("result"))){
                    //授信支付成功
                    if(isCanPay){
                        List<String> orderIds = (List<String>) map.get("orderIds");
                        if(CollectionUtils.isNotEmpty(orderIds)){
                            RetDTO retDTO = OrderServiceUtils.batchPayOrder(orderIds,purchaser.getId(),"1");
                            log.info("============授信支付成功返回数据=============" + retDTO);
                            if(retDTO != null && RetDTO.SUCCESS.equals(retDTO.getRetStatus())){
                                ret.put("orderPaySuccess",true);
                            }else {
                                ret.put("orderPaySuccess",false);
                            }
                        }
                    }
                    ret.put("orderGroupUuid",map.get("orderGroupUuid").toString());
                    return ret;
                }else if(map != null && "FAILURE".equals(map.get("result"))){
                    return AjaxJson.fail(map.get("msg").toString());
                }
            }else{
                return AjaxJson.fail("提交订单失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail("提交订单失败");
        }
        return AjaxJson.fail("提交订单失败");
    }





    /**
     * 从购物车保存订单
     * @return
     */
    @ApiOperation(notes = "saveFromCart",httpMethod = "POST",value = "从购物车保存订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "purchaserAddressId", value = "地址ID", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "purchaserInvoiceId", value = "发票ID", required = false, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "isApplyInvoice", value = "是否需要开发票", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/saveFromCart")
    public AjaxJson saveFromCart(HttpServletRequest request,HttpServletResponse response,CartManagerDTO cart) throws Exception{
        long time1=System.currentTimeMillis();
        log.info("============保存订单开始============="+time1);
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效！");
        }
        String purchaserAddressId = cart.getPurchaserAddressId();
        String isApplyInvoice = cart.getIsApplyInvoice();
        String purchaserInvoiceId = cart.getPurchaserInvoiceId();
        //获取公司信息
        Purchaser purchaser = purchaserService.get(account.getPurchaserId());
        if(purchaser != null){
            cart.setBillToPartyCode(purchaser.getLoginNum());
            cart.setBillToPartyName(purchaser.getCompanyName());
        }

        if(StringUtils.isBlank(purchaserAddressId)){
            return AjaxJson.fail("收货地址不能为空！");
        }
        if("1".equals(isApplyInvoice) && StringUtils.isBlank(purchaserInvoiceId)){
            return AjaxJson.fail("需要开发票请选择发票！");
        }
        //获取购物车信息
        Map<String,List<CartCookieProductDTO>> cartMap = cartCookieService.getCartByStoreGoodsIds(request.getCookies());

        List<CartCookieProductDTO> listCookies = cartCookieService.getCartToBalance(request.getCookies());
        if(CollectionUtils.isEmpty(listCookies)){
            return AjaxJson.fail("商品数据错误");
        }
        Map<String,CartCookieProductDTO> productMap = new HashMap<String,CartCookieProductDTO>();
        List<String> storeGoodsIdList = new ArrayList<String>();
        List<String> storeIdList = new ArrayList<String>();
        listCookies.forEach(m -> {
            storeGoodsIdList.add(m.getStoreGoodsId());
            productMap.put(m.getStoreGoodsId(),m);
            storeIdList.add(m.getStoreId());
        });

        PurchaserAddress purchaserAddress = new PurchaserAddress();
        purchaserAddress.setId(purchaserAddressId);
        purchaserAddress.setPurchaserId(account.getPurchaserId());
        if(StringUtils.isNotEmpty(purchaserAddressId)){
            List<PurchaserAddress> purchaserAddressList = purchaserAddressService.findList(purchaserAddress);
            if(purchaserAddressList.size() == 0){
                return AjaxJson.fail("采购商地址不存在!");
            }
            purchaserAddress = purchaserAddressList.get(0);
        }
        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
        purchaserInvoice.setPurchaserId(account.getPurchaserId());
        if(StringUtils.isNotEmpty(isApplyInvoice) && Global.YES.equals(isApplyInvoice) && StringUtils.isNotEmpty(purchaserInvoiceId)){
            purchaserInvoice.setId(purchaserInvoiceId);
            List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
            if(purchaserInvoiceList.size() == 0){
                return AjaxJson.fail("采购商发票地址不能为空!");
            }
            purchaserInvoice = purchaserInvoiceList.get(0);
        }
        //根据送达方的88码获取销售工厂
        String code= cart.getShipToPartyCode();
        MdmCustomersSource  mdms=new MdmCustomersSource ();
        mdms.setCusCode(code);
        List<MdmCustomersSource>  ms= mdmCustomersSourceService.findList(mdms);
        if(CollectionUtils.isNotEmpty(ms)){
            MdmCustomersSource s=ms.get(0);
            if(s!=null){
                //销售工厂
                cart.setSalesFactory(s.getComCode());
                //工贸
                cart.setMainHouse(s.getOrgId());
            }
        }

        //获取登录人所属公司
        Purchaser p = purchaserService.get(account.getPurchaserId());
        long time2 = System.currentTimeMillis();
        log.info("============组装数据开始============="+time2);
        // 声明参数
        //组织下单信息
        PlaceOrderDTO placeOrderDTO = setPlaceOrderDTO(cartMap,purchaserAddress,purchaserInvoice,null,p.getId(),cart);
        placeOrderDTO.setCustomerUuid(p.getId());
        placeOrderDTO.setCustomerName(p.getCompanyName());
        long time3 = System.currentTimeMillis();
        log.info("============组装数据结束============="+time3);
        log.info("============组装数据所用时间============="+ (time3 - time2));
        List<String> ordersList = new ArrayList<String>();
        boolean isCanPay = false;
        AjaxJson ret = new AjaxJson();
        try{
            if("2".equals(cart.getPayType())){
                if(!"1".equals(account.getIsAdmin())){
                    return AjaxJson.fail("该账号不是主账号,无法进行授信支付");
                }
                PurchaserAccount a = purchaserAccountService.getAdminByPurchaserId(account.getPurchaserId());
                if(a == null){
                    return AjaxJson.fail("该账号的主账号信息错误");
                }
                //授信额度
                double creditBalance = directPurchaserAccountService.getCreditBalance(a.getLoginNum());
                if(creditBalance < placeOrderDTO.getTotalMoney()){
                    return AjaxJson.fail("授信额度不足，无法下单");
                }
                isCanPay = true;

            }
            long time4 = System.currentTimeMillis();
            log.info("============保存订单开始============="+time4);
            // 调用方法
            RetDTO dto = OrderServiceUtils.batchSaveSmallShopOrder4others(placeOrderDTO);
            long time5 = System.currentTimeMillis();
            log.info("============保存订单结束============="+time5);
            log.info("============保存订单所用时间=============" + (time5 - time4));
            log.info("============保存订单返回数据=============" + dto);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                Map<String,Object> map = JSONObject.parseObject(dto.getRetData(),Map.class);
                if(map != null && "SUCCESS".equals(map.get("result"))){
                    List<String> orderIds = (List<String>) map.get("orderIds");
                    ordersList.addAll(orderIds);
                    //授信支付成功
                    if(isCanPay){
                        if(CollectionUtils.isNotEmpty(orderIds)){
                            RetDTO retDTO = OrderServiceUtils.batchPayOrder(orderIds,purchaser.getId(),"1");
                            log.info("============授信支付成功返回数据=============" + retDTO);
                            if(retDTO != null && RetDTO.SUCCESS.equals(retDTO.getRetStatus())){
                                ret.put("orderPaySuccess",true);
                            }else {
                                ret.put("orderPaySuccess",false);
                            }
                        }
                    }
                    ret.put("orderGroupUuid",map.get("orderGroupUuid").toString());
                }else if(map != null && "FAILURE".equals(map.get("result"))){
                    return AjaxJson.fail(map.get("msg").toString());
                }
            }else{
                return AjaxJson.fail("提交订单失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail("提交订单失败");
        }
        if(CollectionUtils.isEmpty(ordersList)){
            return AjaxJson.fail("提交订单失败");
        }else{
            //删除购物车已经下单的商品
            //从购物车删除商品
            Cookie[] cookies = new Cookie[1];
            //修改购物车中商品数量
            CartCookieDTO cookieCart = cartCookieService.removeProductFromCart(request.getCookies(),storeGoodsIdList.toString());
            cookies = setCartToCookie(cookieCart,response);

            return ret;
        }
    }

    public Cookie[] setCartToCookie(CartCookieDTO cartCookieModel,
                                    HttpServletResponse response) {

        // 如果是未登录会员，需要更新cookies数据
        Cookie newCookie=null;

        String cartcookie = JSONObject.toJSONString(cartCookieModel);
        try {
            newCookie = new Cookie(COOKIE_CART_NAME,
                    URLEncoder.encode(cartcookie, "UTF-8"));
            newCookie.setPath("/");
            newCookie.setMaxAge(5 * 365 * 24 * 60 * 60);
            response.addCookie(newCookie);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("com.aebiz.b2b2cpv.pcweb.frontweb.cart.cartmgr.controller.CartManagerController#addCartToCookie_P2"+ex.getMessage());
        }
        Cookie[] cookies=new Cookie[]{newCookie};
        return cookies;
    }

    /**
     * 组织下单数据
     * @param cartMap        商品数据
     * @param purchaserAddress      收货地址
     * @param purchaserInvoice      发票信息
     * @return
     */
    public PlaceOrderDTO setPlaceOrderDTO(Map<String,List<CartCookieProductDTO>> cartMap
            ,PurchaserAddress purchaserAddress
            ,PurchaserInvoice purchaserInvoice
            ,Map<String,Store> storeMap
            ,String customerUuid,CartManagerDTO cart){
        PlaceOrderDTO dto = new PlaceOrderDTO();
        dto.setCustomerAddressUuid(purchaserAddress.getId());
        //组织收货地址
        dto.setOrderAddressDTO(setOrderAddress(purchaserAddress,customerUuid));
        //组织购物车
        dto.setCartManagerModels(setCartManagerDTOs(cartMap,purchaserInvoice,storeMap,cart));
        double totalMoney = 0;
        List<CartManagerDTO> cartList = dto.getCartManagerModels();
        if(CollectionUtils.isNotEmpty(cartList)){
            for(CartManagerDTO c : cartList){
                totalMoney += c.getPayMoney();
            }
            dto.setTotalMoney(totalMoney);
        }

        return dto;
    }

    /**
     * 组织购物车信息
     * @param cartMap         商品数据
     * @param purchaserInvoice       发票信息
     * @return
     */
    public List<CartManagerDTO> setCartManagerDTOs(Map<String,List<CartCookieProductDTO>> cartMap
            , PurchaserInvoice purchaserInvoice,Map<String,Store> storeMap,CartManagerDTO cartManagerDTO){
        List<CartManagerDTO> cartManagerDTOS = new ArrayList<CartManagerDTO>();
        if(cartMap != null){
            for (String storeId : cartMap.keySet()) {
                CartManagerDTO cart = new CartManagerDTO();
                cart = cartManagerDTO;
                double totalMoney = 0;
                if(storeMap != null){
                    Store s = storeMap.get(storeId);
                    if(s != null){
                        cart.setStoreUuid(storeId);
                        cart.setStoreName(s.getStoreName());
                    }
                }
                //组织发票信息
                cart = setCartManagerDTO(cart,purchaserInvoice);
                List<CartCookieProductDTO> productModels = cartMap.get(storeId);
                List<CartManagerDetailDTO> cartManagerDetailList = new ArrayList<CartManagerDetailDTO>();
                if (productModels != null && productModels.size() > 0) {
                    for(CartCookieProductDTO p : productModels){
                        CartManagerDetailDTO cartManagerDetail = new CartManagerDetailDTO();
                        //获取产品组号
                        Sign sign = new Sign(key, secret);
                        ProductInfo productInfo = linkProductUpperReadService.getProductInfoByCode(p.getSkuNo(), sign).getResult();
                        if(productInfo!=null){
                            SimpleProduct simple=productInfo.getSimpleProduct();
                            if(simple!=null){
                                cartManagerDetail.setProductGroupCode(simple.getProductGroupCode());
                            }
                        }
                        cartManagerDetail.setSkuNo(p.getSkuNo());
                        cartManagerDetail.setBuyNum(p.getChooseNum());
                        cartManagerDetail.setBasePrice(p.getPrice());
                        cartManagerDetail.setProductName(p.getGoodsName());
                        cartManagerDetail.setProductUuid(p.getProductUuid());
                        cartManagerDetail.setNowPrice(p.getPrice() * p.getChooseNum());
                        cartManagerDetailList.add(cartManagerDetail);
                        totalMoney += cartManagerDetail.getNowPrice();
                    }
                    cart.setCartManagerDetailList(cartManagerDetailList);
                }
                cart.setTotalMoney(totalMoney);
                cart.setPayMoney(totalMoney);
                cartManagerDTOS.add(cart);
            }
        }

        return cartManagerDTOS;
    }

    /**
     * 组织发票信息
     * @param cart
     * @param purchaserInvoice
     * @return
     */
    public CartManagerDTO setCartManagerDTO(CartManagerDTO cart,PurchaserInvoice purchaserInvoice){
        cart.setCustomerAddInvoiceUuid(purchaserInvoice.getId());
        // 采购商
//        cart.setCustomerUuid(purchaserInvoice.getPurchaserId());
//        // 发票类型
        cart.setOrderInvoiceCate(purchaserInvoice.getKind());
//        // 单位名称
        cart.setOrderInvoiceTitle(purchaserInvoice.getCompanyName());
        // 纳税人识别号
        cart.setOrderInvoiceTaxCode(purchaserInvoice.getTaxCode());
//        // 注册地址
//        cart.put("regAddr",purchaserInvoice.getRegAddr());
//        // 注册电话
//        cart.put("regPhone",purchaserInvoice.getRegPhone());
//        // 开户银行
//        cart.put("regBname",purchaserInvoice.getRegBname());
//        // 银行账户
//        cart.put("regBaccount",purchaserInvoice.getRegBaccount());
//        // 开户银行
//        cart.put("recName",purchaserInvoice.getRecName());
//        // 收票人手机号
//        cart.put("recMobphone",purchaserInvoice.getRecMobphone());
//        // 省
//        cart.put("recProvinceId",purchaserInvoice.getRecProvinceId());
//        // 市
//        cart.put("recCityId",purchaserInvoice.getRecCityId());
//        // 区
//        cart.put("recDistrictId",purchaserInvoice.getRecDistrictId());
//        // 收票人所在地区
//        cart.put("recAreaInfo",purchaserInvoice.getRecAreaInfo());
//        // 送票详细地址
//        cart.put("recDetailAddr",purchaserInvoice.getRecDetailAddr());
//        // 是否默认
//        cart.put("isDefault",purchaserInvoice.getIsDefault());

        return cart;
    }

    /**
     * 组织收货地址信息
     * @param purchaserAddress       收货地址
     * @return
     */
    public OrderAddressDTO setOrderAddress(PurchaserAddress purchaserAddress,String customerUuid){
        OrderAddressDTO address = new OrderAddressDTO();
        if(purchaserAddress != null){
            address.setCustomerAddressUuid(purchaserAddress.getId());
            address.setReceiver(purchaserAddress.getTrueName());
            address.setMobile(purchaserAddress.getMobilePhone());
            address.setPostCode(purchaserAddress.getZipCode());
            address.setAddress(purchaserAddress.getDetailAddress());
            address.setTel(purchaserAddress.getTelPhone());
            address.setAlias(purchaserAddress.getAreaInfo());
            address.setCustomerUuid(customerUuid);
            address.setProvince(purchaserAddress.getProvinceId());
            address.setCity(purchaserAddress.getCityId());
            address.setRegion(purchaserAddress.getDistrictId());
        }
        return address;
    }

    /**
     * 订单列表页
     */
    @ApiOperation(notes = "getOrdersList",httpMethod = "POST",value = "订单列表页")
    @ResponseBody
    @RequestMapping(value = "getOrdersList")
    public AjaxJson getOrdersList(OrderMainDTO qm, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isNotBlank(qm.getOrderId())){
            qm.getMapCondition().put("orderId",1);
        }
        if(StringUtils.isNotBlank(qm.getCustomerName())){
            qm.getMapCondition().put("customerName",2);
        }
        if(qm.getOrderStateh() != null){
            qm.setOrderState(getOrderStateByHaier(qm.getOrderStateh()));
            if(qm.getOrderStateh() == 20){
                qm.getMapCondition().put("orderState",8);
            }else {
                qm.getMapCondition().put("orderState",1);
            }
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime())){
            qm.setCreateOpeTime(qm.getCreateOpeTime() + " 00:00:00");
            qm.getMapCondition().put("createOpeTime",5);
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime2())){
            qm.setCreateOpeTime2(qm.getCreateOpeTime2() + " 23:59:59");
            qm.getMapCondition().put("createOpeTime2",6);
        }
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }
        qm.setCustomerUuid(account.getPurchaserId()); //  "ba617dd16d6e418d981ec6e5347caf65"
        qm.getMapCondition().put("customerUuid", 1);
        qm.setSortName("createOpeTime");

        Page<OrderMainDTO> page = new Page<OrderMainDTO>(request, response);

        try {
            //获取订单实体类
            RetDTO dto = OrderServiceUtils.getListGetByCondition(qm,page.getPageNo(),page.getPageSize());
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //返回数据
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    //转类型
                    Pager pager = JSONObject.parseObject(data,Pager.class);
                    if(pager != null && CollectionUtils.isNotEmpty(pager.getResults())){
                        //获取全部订单
                        List<OrderMainDTO> orderList = JSONObject.parseArray(JSONObject.toJSONString(pager.getResults()),OrderMainDTO.class);
                        if(CollectionUtils.isNotEmpty(orderList)){
                            List<String> productCodeList = new ArrayList<String>();

                            orderList.forEach(m ->{
                                //修改自定义订单状态
                                if(StringUtils.isNotBlank(m.getOrderState())){
                                    m.setOrderStateh(setOrderState(m.getOrderState()));
                                }
                                List<OrderDetailDTO> detailDTOS = m.getOrderDetailList();
                                if(CollectionUtils.isNotEmpty(detailDTOS)){
                                    detailDTOS.forEach(d -> {
                                        productCodeList.add(d.getSkuNo());
                                    });
                                }
                            });
                            Goods goods = new Goods();
                            goods.setCodeList(productCodeList);
                            goods.setPurchaserId(account.getPurchaserId());
                            List<Goods> goodsList = goodsService.findByCodeList(goods);
                            Map<String,Goods> goodsMap = new HashMap<String, Goods>();
                            if(CollectionUtils.isNotEmpty(goodsList)){
                                goodsList.forEach(g -> {
                                    goodsMap.put(g.getCode(),g);
                                });
                            }
                            List<OrderMainDTO> newOrderList = new ArrayList<OrderMainDTO>();
                            for(OrderMainDTO o : orderList){
                                //修改自定义订单状态
                                List<OrderDetailDTO> detailDTOS = o.getOrderDetailList();
                                if(CollectionUtils.isNotEmpty(detailDTOS)){
                                    for(OrderDetailDTO d : detailDTOS){
                                        if(goodsMap != null && goodsMap.get(d.getSkuNo()) != null){
                                            Goods g = goodsMap.get(d.getSkuNo());
                                            d.setProductMainImageKey(g.getMainPicUrl());
                                            d.setProductMainImageUrl(g.getMainPicUrl());
                                        }
                                    }
                                }
                                newOrderList.add(o);
                            }

                            //获取到页数全部放到订单实体类中
                            page.setPageNo(pager.getNowPage());
                            page.setPageSize(pager.getPageShow());
                            page.setCount(pager.getTotalNum());
                            page.setList(newOrderList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.layuiTable(page);
    }


    //订单状态state：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
    public String getOrderStateByHaier(Integer state){
        //orderState 1等待买家付款  2等待卖家发货  3卖家备货中  4等待买家确认收货  5交易成功  6交易关闭
        String orderState = "";
        if(state == 10){
            orderState = OrderParamModel.ORDER_STATE_WAIT_PAY;
        }else if(state == 20){
            orderState = OrderParamModel.ORDER_STATE_WAIT_DELIVER+","+OrderParamModel.ORDER_STATE_PREPARE_DELIVER;
        }else if(state == 30){
            orderState = OrderParamModel.ORDER_STATE_WAIT_CONFIRM;
        }else if(state == 40){
            orderState = OrderParamModel.ORDER_STATE_SUCCESS;
        }else if(state == 0){
            orderState = OrderParamModel.ORDER_STATE_CLOSE;
        }
        return orderState;
    }

    public Orders setOrders(OrderMainDTO order){
        Orders o = new Orders();
        o.setId(order.getUuid());
        // 订单编号
        o.setOrderSn(order.getOrderId());
        // 店铺ID
        o.setStoreId(order.getStoreUuid());
        // 店铺名称
        o.setStoreName(order.getStoreName());
        // 供应商ID
        o.setDealerId(order.getCustomerUuid());
        // 采购商ID
        o.setPurchaserId(order.getCustomerUuid());
        // 采购商账号ID
        o.setPurchaserAccountId(order.getCustomerUuid());
        // 采购商手机
        o.setPurchaserPhone(order.getMobile());
        // 订单总价格
        o.setOrderAmount(order.getTotalPrice());
        // 商品总价格
        o.setGoodsAmount(order.getTotalAmountPrice());
        // 支付方式
        o.setPaymentCode(order.getPayType());
        // 评价状态 0未评价，1已评价，2已过期未评价
        o.setEvaluationState(order.getAppraiseState());
        // 订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
        o.setOrderState(setOrderState(order.getOrderState()));
        // 退款状态:0是无退款,1是部分退款,2是全部退款
        o.setRefundState(order.getRefundState());
        // 退款金额
        o.setRefundAmount(order.getRefundMoney());
        // 订单来源   0 平台 1 小程序
//        o.setOrderFrom(order.getorder);
        //发货时间
        o.setSendOutTime(DateFormatHelper.toDate(order.getSendTime()));
        //商品名称
        o.setProductName(order.getProductName());
        //商品名称
        o.setProductNo(order.getProductNo());

        return o;
    }

    //state 1等待买家付款  2等待卖家发货  3卖家备货中  4等待买家确认收货  5交易成功  6交易关闭
    public Integer setOrderState(String state){
        //订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
        Integer orderState = 10;
        //1等待买家付款
        if(OrderParamModel.ORDER_STATE_WAIT_PAY.equals(state)){
            //10(默认):未付款
            orderState = 10;
            //2等待卖家发货 3卖家备货中
        }else if(OrderParamModel.ORDER_STATE_WAIT_DELIVER.equals(state)
                || OrderParamModel.ORDER_STATE_PREPARE_DELIVER.equals(state)){
            //20:已付款;
            orderState = 20;
            //4等待买家确认收货
        }else if(OrderParamModel.ORDER_STATE_WAIT_CONFIRM.equals(state)){
            //30:已发货;
            orderState = 30;
            //5交易成功
        }else if(OrderParamModel.ORDER_STATE_SUCCESS.equals(state)){
            //40:已收货;
            orderState = 40;
            //6交易关闭
        }else if(OrderParamModel.ORDER_STATE_CLOSE.equals(state)){
            //0(已取消)
            orderState = 0;
        }

        return orderState;
    }

    /**
     * 订单详情
     */
    @RequestMapping(value = "getOrderMessage")
    public AjaxJson getOrderMessage(String uuid) {
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isEmpty(account.getId())|| !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        OrderMainDTO orderMainDTO  = null;
        if(StringUtils.isEmpty(uuid)){
            return AjaxJson.fail("参数不能为空！");
        }
        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.getOrderViewModelByUuid(uuid);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    orderMainDTO = JSONObject.parseObject(data,OrderMainDTO.class);
                    List<String> productCodeList = new ArrayList<String>();
                    List<OrderDetailDTO> detailDTOS = orderMainDTO.getOrderDetailList();
                    if(CollectionUtils.isNotEmpty(detailDTOS)){
                        for(OrderDetailDTO d : detailDTOS){
                            productCodeList.add(d.getSkuNo());
                        }
                    }
                    Goods goods = new Goods();
                    goods.setCodeList(productCodeList);
                    goods.setPurchaserId(account.getPurchaserId());
                    List<Goods> goodsList = goodsService.findByCodeList(goods);
                    Map<String,Goods> goodsMap = new HashMap<String, Goods>();
                    if(CollectionUtils.isNotEmpty(goodsList)){
                        goodsList.forEach(g -> {
                            goodsMap.put(g.getCode(),g);
                        });
                    }
                    List<OrderDetailDTO> newList = new ArrayList<OrderDetailDTO>();
                    //修改自定义订单状态
                    if(CollectionUtils.isNotEmpty(detailDTOS)){
                        for(OrderDetailDTO d : detailDTOS){
                            if(goodsMap != null && goodsMap.get(d.getSkuNo()) != null){
                                Goods g = goodsMap.get(d.getSkuNo());
                                d.setProductMainImageKey(g.getMainPicUrl());
                                d.setProductMainImageUrl(g.getMainPicUrl());
                            }
                            newList.add(d);
                        }
                        orderMainDTO.setOrderDetailList(newList);
                    }
                    if(StringUtils.isNotBlank(orderMainDTO.getCustomerAddInvoiceUuid())){
                        //获取发票信息
                        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
                        purchaserInvoice.setPurchaserId(account.getPurchaserId());
                        purchaserInvoice.setId(orderMainDTO.getCustomerAddInvoiceUuid());
                        List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
                        if(CollectionUtils.isNotEmpty(purchaserInvoiceList)){
                            purchaserInvoice = purchaserInvoiceList.get(0);
                            if(purchaserInvoice != null){
                                orderMainDTO.setPurchaserInvoice(purchaserInvoice);
                                if("2".equals(purchaserInvoice.getKind())){
                                    orderMainDTO.setInvoiceTypeName("增值税发票");
                                }else if("3".equals(purchaserInvoice.getKind())){
                                    orderMainDTO.setInvoiceTypeName("电子发票");
                                }else if("1".equals(purchaserInvoice.getKind())){
                                    orderMainDTO.setInvoiceTypeName("普通发票");
                                }
                            }
                        }
                    }
                    if(orderMainDTO.getOrderAddressDTO() != null
                            && StringUtils.isNotBlank(orderMainDTO.getOrderAddressDTO().getCustomerAddressUuid())){
                        String purchaserAddressId = orderMainDTO.getOrderAddressDTO().getCustomerAddressUuid();
                        //组装订单收货地址
                        PurchaserAddress purchaserAddress = new PurchaserAddress();
                        purchaserAddress.setId(purchaserAddressId);
                        purchaserAddress.setPurchaserId(account.getPurchaserId());
                        if(StringUtils.isNotEmpty(purchaserAddressId)){
                            List<PurchaserAddress> purchaserAddressList = purchaserAddressService.findList(purchaserAddress);
                            if(CollectionUtils.isNotEmpty(purchaserAddressList)){
                                purchaserAddress = purchaserAddressList.get(0);
                                orderMainDTO.setPurchaserAddress(purchaserAddress);

                            }
                        }
                        //收货人
                        orderMainDTO.setReceive(orderMainDTO.getOrderAddressDTO().getReceiver());
                        orderMainDTO.setMobile(orderMainDTO.getOrderAddressDTO().getMobile());
                        orderMainDTO.setReceiveAddress(orderMainDTO.getOrderAddressDTO().getAddress());
                    }

                    //获取物流信息
                    List<CloudWarehouseDTO> cloudWarehouseDTOList = orderMainDTO.getCloudWarehouseDTOList();
                    if(CollectionUtils.isNotEmpty(cloudWarehouseDTOList)){
                        CloudWarehouseDTO warehouseDTO = cloudWarehouseDTOList.get(0);
                        if(warehouseDTO != null){
                            orderMainDTO.setWarehouseCode(warehouseDTO.getExpno());
                        }

                    }



                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.ok(orderMainDTO);
    }

    /**
     * 获取子账号订单列表页
     * @param qm
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(notes = "getSubOrdersList",httpMethod = "POST",value = "订单列表页")
    @RequestMapping(value = "getSubOrdersList")
    public AjaxJson getSubOrdersList(OrderMainDTO qm, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isNotBlank(qm.getOrderId())){
            qm.getMapCondition().put("orderId",1);
        }
        if(StringUtils.isNotBlank(qm.getCustomerName())){
            qm.getMapCondition().put("customerName",2);
        }
        if(qm.getOrderStateh() != null){
            qm.setOrderState(getOrderStateByHaier(qm.getOrderStateh()));
            if(qm.getOrderStateh() == 20){
                qm.getMapCondition().put("orderState",8);
            }else {
                qm.getMapCondition().put("orderState",1);
            }
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime())){
            qm.getMapCondition().put("createOpeTime",5);
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime2())){
            qm.getMapCondition().put("createOpeTime2",6);
        }
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }
        if("1".equals(account.getIsAdmin())){
            List<PurchaserMasterSlaveRel> ids = purchaserMasterSlaveRelService.getSubByAdmin(account.getPurchaserId());
            if(CollectionUtils.isNotEmpty(ids)){
                List<String> subPurchaserIds = ids.stream().map(PurchaserMasterSlaveRel :: getPurchaserSlaveId).collect(Collectors.toList());
                //获取子账号的登录账户
                qm.setCustomerUuid(StringUtils.join(subPurchaserIds,","));
                qm.getMapCondition().put("customerUuid", 8);
            }else {
                return AjaxJson.fail("该账号没有子账号！");
            }
        }else {
            return AjaxJson.fail("该账号不是主账号");
        }

        Page<OrderMainDTO> page = new Page<OrderMainDTO>(request, response);

        try {
            //获取订单实体类
            RetDTO dto = OrderServiceUtils.getListGetByCondition(qm,page.getPageNo(),page.getPageSize());
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //返回数据
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    //转类型
                    Pager pager = JSONObject.parseObject(data,Pager.class);
                    if(pager != null && CollectionUtils.isNotEmpty(pager.getResults())){
                        //获取全部订单
                        List<OrderMainDTO> orderList = JSONObject.parseArray(JSONObject.toJSONString(pager.getResults()),OrderMainDTO.class);
                        if(CollectionUtils.isNotEmpty(orderList)){
                            List<String> productCodeList = new ArrayList<String>();

                            orderList.forEach(m ->{
                                //修改自定义订单状态
                                if(StringUtils.isNotBlank(m.getOrderState())){
                                    m.setOrderStateh(setOrderState(m.getOrderState()));
                                }
                                List<OrderDetailDTO> detailDTOS = m.getOrderDetailList();
                                if(CollectionUtils.isNotEmpty(detailDTOS)){
                                    detailDTOS.forEach(d -> {
                                        productCodeList.add(d.getSkuNo());
                                    });
                                }
                            });
                            Goods goods = new Goods();
                            goods.setCodeList(productCodeList);
                            goods.setPurchaserId(account.getPurchaserId());
                            List<Goods> goodsList = goodsService.findByCodeList(goods);
                            Map<String,Goods> goodsMap = new HashMap<String, Goods>();
                            if(CollectionUtils.isNotEmpty(goodsList)){
                                goodsList.forEach(g -> {
                                    goodsMap.put(g.getCode(),g);
                                });
                            }
                            List<OrderMainDTO> newOrderList = new ArrayList<OrderMainDTO>();
                            for(OrderMainDTO o : orderList){
                                //修改自定义订单状态
                                List<OrderDetailDTO> detailDTOS = o.getOrderDetailList();
                                if(CollectionUtils.isNotEmpty(detailDTOS)){
                                    for(OrderDetailDTO d : detailDTOS){
                                        if(goodsMap != null && goodsMap.get(d.getSkuNo()) != null){
                                            Goods g = goodsMap.get(d.getSkuNo());
                                            d.setProductMainImageKey(g.getMainPicUrl());
                                            d.setProductMainImageUrl(g.getMainPicUrl());
                                        }
                                    }
                                }
                                newOrderList.add(o);
                            }

                            //获取到页数全部放到订单实体类中
                            page.setPageNo(pager.getNowPage());
                            page.setPageSize(pager.getPageShow());
                            page.setCount(pager.getTotalPage());
                            page.setList(newOrderList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.layuiTable(page);
    }



    /**
     * 通过订单租uuid获取订单数据
     * @param orderGroupUuid
     * @return
     */
    @RequestMapping(value = "getByOrderGroupUuid")
    public AjaxJson getByOrderGroupUuid(String orderGroupUuid) {
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isEmpty(account.getId())|| !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        List<OrderMainDTO> orderMainDTOList  = null;
        if(StringUtils.isBlank(orderGroupUuid)){
            return AjaxJson.fail("参数为空！");
        }
        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.getByOrderGroupUuid(orderGroupUuid);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    orderMainDTOList = JSONObject.parseArray(data,OrderMainDTO.class);
                    return AjaxJson.ok(orderMainDTOList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.fail("错误！");
    }

    /**
     * 组织购物车商品数据
     * @param goodsList
     * @return
     */
    public List<CartCookieProductDTO> getCookiesProduct(List<Goods> goodsList){
        List<CartCookieProductDTO> list = new ArrayList<CartCookieProductDTO>();
        if(CollectionUtils.isNotEmpty(goodsList)){
            goodsList.forEach(goods -> {
                CartCookieProductDTO cart = new CartCookieProductDTO();
                cart.setMainPicUrl(goods.getMainPicUrl());
                cart.setGoodsName(goods.getName());
                cart.setSkuNo(goods.getCode());
                cart.setProductUuid(goods.getStoreGoodsId());
                cart.setChooseNum(goods.getChooseNum());
                cart.setPrice(goods.getExclusivePrice());
                cart.setTotalPrice(goods.getExclusivePrice() * goods.getChooseNum());
                cart.setStoreGoodsId(goods.getCode());
                cart.setProductGroupCode(goods.getProductGroupCode());
                cart.setProductGroupName(goods.getProductGroupName());
                list.add(cart);
            });
        }

        return list;
    }

    /**
     * 取消订单
     * @param orderUuid               订单uuid
     * @param cancelReason             原因
     * @return
     */
    @ApiOperation(notes = "closeOrder",httpMethod = "POST",value = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderUuid", value = "订单uuid", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "cancelReason", value = "原因", required = false, paramType = "query",dataType = "String"),
    })
    @RequestMapping(value = "closeOrder")
    public AjaxJson closeOrder(String orderUuid, String cancelReason) {
        if(StringUtils.isEmpty(orderUuid)){
            return AjaxJson.fail("参数错误！");
        }
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }

        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.closeOrder(orderUuid,cancelReason,account.getId(),"1");
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                return AjaxJson.ok("订单取消成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.fail("取消失败！");
    }

    /**
     * 会员确认收货
     * @param orderUuid
     * @return
     */
    @ApiOperation(notes = "arrivalOrder",httpMethod = "POST",value = "会员确认收货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderUuid", value = "订单uuid", required = true, paramType = "query",dataType = "String"),
    })
    @RequestMapping(value = "arrivalOrder")
    public AjaxJson arrivalOrder(String orderUuid) {
        if(StringUtils.isEmpty(orderUuid)){
            return AjaxJson.fail("参数错误！");
        }
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }

        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.arrivalOrder(orderUuid,account.getId(),"1");
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                return AjaxJson.ok("订单确认收货成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return AjaxJson.fail("订单确认收货失败！");
    }


    /**
     * 导出excel文件
     */
    @ApiOperation(notes = "orderExport",httpMethod = "GET",value = "订单导出")
    @ResponseBody
    @RequestMapping(value = "orderExport")
    public AjaxJson orderExport(OrderMainDTO qm, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if(StringUtils.isNotBlank(qm.getOrderId())){
            qm.getMapCondition().put("orderId",1);
        }
        if(StringUtils.isNotBlank(qm.getCustomerName())){
            qm.getMapCondition().put("customerName",2);
        }
        if(qm.getOrderStateh() != null){
            qm.setOrderState(getOrderStateByHaier(qm.getOrderStateh()));
            if(qm.getOrderStateh() == 20){
                qm.getMapCondition().put("orderState",8);
            }else {
                qm.getMapCondition().put("orderState",1);
            }
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime())){
            qm.setCreateOpeTime(qm.getCreateOpeTime() + " 00:00:00");
            qm.getMapCondition().put("createOpeTime",5);
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime2())){
            qm.setCreateOpeTime2(qm.getCreateOpeTime2() + " 23:59:59");
            qm.getMapCondition().put("createOpeTime2",6);
        }
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isEmpty(account.getId())|| !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        qm.setCustomerUuid(account.getPurchaserId()); //  "ba617dd16d6e418d981ec6e5347caf65"
        qm.getMapCondition().put("customerUuid", 1);
        qm.setSortName("createOpeTime");
        List<OrderMainDTO> newOrderList = new ArrayList<OrderMainDTO>();

        Page<OrderMainDTO> page = new Page<OrderMainDTO>(request, response);
        try {
            //获取订单实体类
            RetDTO dto = OrderServiceUtils.getListGetByCondition(qm,page.getPageNo(),page.getPageSize());
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //返回数据
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    //转类型
                    Pager pager = JSONObject.parseObject(data,Pager.class);
                    if(pager != null && CollectionUtils.isNotEmpty(pager.getResults())){
                        //获取全部订单
                        List<OrderMainDTO> orderList = JSONObject.parseArray(JSONObject.toJSONString(pager.getResults()),OrderMainDTO.class);
                        if(CollectionUtils.isNotEmpty(orderList)){
                            List<String> productCodeList = new ArrayList<String>();

                            orderList.forEach(m ->{
                                //修改自定义订单状态
                                if(StringUtils.isNotBlank(m.getOrderState())){
                                    m.setOrderStateh(setOrderState(m.getOrderState()));
                                }
                                List<OrderDetailDTO> detailDTOS = m.getOrderDetailList();
                                if(CollectionUtils.isNotEmpty(detailDTOS)){
                                    String orderDetailId = "";
                                    for(OrderDetailDTO d : detailDTOS){
                                        if(StringUtils.isNotBlank(orderDetailId)){
                                            orderDetailId += "," + d.getOrderDetailId();
                                        }else {
                                            orderDetailId = d.getOrderDetailId();
                                        }
                                        productCodeList.add(d.getProductUuid());
                                    }
                                    m.setOrderDetailId(orderDetailId);
                                }
                            });
                            Goods goods = new Goods();
                            goods.setCodeList(productCodeList);
                            goods.setPurchaserId(account.getPurchaserId());
                            List<Goods> goodsList = goodsService.findByCodeList(goods);
                            Map<String,Goods> goodsMap = new HashMap<String, Goods>();
                            if(CollectionUtils.isNotEmpty(goodsList)){
                                goodsList.forEach(g -> {
                                    goodsMap.put(g.getCode(),g);
                                });
                            }
                            for(OrderMainDTO o : orderList){
                                //修改自定义订单状态
                                List<OrderDetailDTO> detailDTOS = o.getOrderDetailList();
                                if(CollectionUtils.isNotEmpty(detailDTOS)){
                                    for(OrderDetailDTO d : detailDTOS){
                                        if(goodsMap != null && goodsMap.get(d.getSkuNo()) != null){
                                            Goods g = goodsMap.get(d.getSkuNo());
                                            d.setProductMainImageKey(g.getMainPicUrl());
                                            d.setProductMainImageUrl(g.getMainPicUrl());
                                        }
                                    }
                                }
                                newOrderList.add(o);
                            }

                            //获取到页数全部放到订单实体类中
                            page.setPageNo(pager.getNowPage());
                            page.setPageSize(pager.getPageShow());
                            page.setCount(pager.getTotalNum());
                            page.setList(newOrderList);
                        }
                    }
                }
            }

            String fileName = "订单"+ DateFormatHelper.getNowTimeStr("yyyyMMddHHmmss")+".xlsx";
            new ExportExcel("订单", OrderMainDTO.class,1).setDataList(newOrderList).write(response,fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("导出订单记录失败！失败信息："+e.getMessage());
        }
        return j;
    }


    /**
     * 获取某状态下的订单数量
     * @return
     */
    @ApiOperation(notes = "getOrderCount",httpMethod = "POST",value = "获取某状态下的订单数量")
    @RequestMapping(value = "getOrderCount")
    public AjaxJson getOrderCount() {
        //获取当前登录人
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId())){
            return AjaxJson.fail("直采商未登陆");
        }
        AjaxJson returnMap = new AjaxJson();
        try {
            OrderMainDTO qm = new OrderMainDTO();
            qm.setCustomerUuid(account.getPurchaserId());
            qm.getMapCondition().put("customerUuid",1);

            //海尔订单状态state：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
            //orderState 1等待买家付款  2等待卖家发货  3卖家备货中  4等待买家确认收货  5交易成功  6交易关闭
            //代付款订单数量
            int waitPayNum = 0;
            qm.setOrderState(OrderParamModel.ORDER_STATE_WAIT_PAY);
            qm.getMapCondition().put("orderState",1);
            RetDTO waitPay = OrderServiceUtils.getCustomerCount(qm);
            if(waitPay != null && RetDTO.SUCCESS.equals(waitPay.getRetStatus())){
                waitPayNum = Integer.parseInt(waitPay.getRetData());
            }
            //代发货订单数量
            int waitDeliverNum = 0;
            qm.setOrderState(OrderParamModel.ORDER_STATE_WAIT_DELIVER+","+OrderParamModel.ORDER_STATE_PREPARE_DELIVER);
            qm.getMapCondition().put("orderState",8);
            RetDTO waitDeliver = OrderServiceUtils.getCustomerCount(qm);
            if(waitDeliver != null && RetDTO.SUCCESS.equals(waitDeliver.getRetStatus())){
                waitDeliverNum = Integer.parseInt(waitDeliver.getRetData());
            }
            //代签收订单数量
            int waitConfirmNum = 0;
            qm.setOrderState(OrderParamModel.ORDER_STATE_WAIT_CONFIRM);
            qm.getMapCondition().put("orderState",1);
            RetDTO waitConfirm = OrderServiceUtils.getCustomerCount(qm);
            if(waitConfirm != null && RetDTO.SUCCESS.equals(waitConfirm.getRetStatus())){
                waitConfirmNum = Integer.parseInt(waitConfirm.getRetData());
            }
            //已签收订单数量
            int successNum = 0;
            qm.setOrderState(OrderParamModel.ORDER_STATE_WAIT_CONFIRM);
            qm.getMapCondition().put("orderState",1);
            RetDTO success = OrderServiceUtils.getCustomerCount(qm);
            if(success != null && RetDTO.SUCCESS.equals(success.getRetStatus())){
                successNum = Integer.parseInt(success.getRetData());
            }
            //已签收订单数量
            int closeNum = 0;
            qm.setOrderState(OrderParamModel.ORDER_STATE_WAIT_CONFIRM);
            qm.getMapCondition().put("orderState",1);
            RetDTO close = OrderServiceUtils.getCustomerCount(qm);
            if(close != null && RetDTO.SUCCESS.equals(close.getRetStatus())){
                closeNum = Integer.parseInt(success.getRetData());
            }
            //已签收订单数量
            int waitAppraiseNum = 0;
            qm.setOrderState(OrderParamModel.ORDER_STATE_WAIT_CONFIRM);
            qm.getMapCondition().put("orderState",1);
            qm.setAppraiseState("0");
            qm.getMapCondition().put("appraiseState",1);
            RetDTO waitAppraise = OrderServiceUtils.getCustomerCount(qm);
            if(waitAppraise != null && RetDTO.SUCCESS.equals(waitAppraise.getRetStatus())){
                waitAppraiseNum = Integer.parseInt(waitAppraise.getRetData());
            }



            returnMap.put("waitPay",waitPayNum);
            returnMap.put("waitDeliver",waitDeliverNum);
            returnMap.put("waitConfirm",waitConfirmNum);
            returnMap.put("success",successNum);
            returnMap.put("closeNum",closeNum);
            returnMap.put("waitAppraiseNum",waitAppraiseNum);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxJson.fail("获取订单数量出错");
        }
        return returnMap;
    }


}
