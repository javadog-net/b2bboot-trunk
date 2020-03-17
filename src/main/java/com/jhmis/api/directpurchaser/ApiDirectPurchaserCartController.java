package com.jhmis.api.directpurchaser;

import com.alibaba.fastjson.JSONObject;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cart.dto.CartCookieDTO;
import com.jhmis.modules.cart.dto.CartCookieProductDTO;
import com.jhmis.modules.cart.dto.CartManagerDTO;
import com.jhmis.modules.cart.service.CartCookieService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserAccountService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserService;
import com.jhmis.modules.purchasermainrel.entity.PurchaserMasterSlaveRel;
import com.jhmis.modules.purchasermainrel.service.PurchaserMasterSlaveRelService;
import com.jhmis.modules.shop.entity.DirectCart;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "DirectPurchaserCartController", description = "直采-购物车")
@RestController
@RequestMapping("/api/directpurchaser/cart")
public class ApiDirectPurchaserCartController extends BaseController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private CartCookieService cartCookieService;
    @Autowired
    private PurchaserAddressService purchaserAddressService;
    @Autowired
    private PurchaserInvoiceService purchaserInvoiceService;
    @Autowired
    private PurchaserService purchaserService;
    @Autowired
    private DirectPurchaserAccountService directPurchaserAccountService;
    @Autowired
    private PurchaserMasterSlaveRelService purchaserMasterSlaveRelService;
    @Autowired
    private DirectPurchaserService directPurchaserService;


    /**
     * 购物车cookies购物车对象
     */
    public static final String COOKIE_CART_NAME = "cartManager";

    /**
     * 购物车列表
     * @return
     */
    @ApiOperation(notes = "list", httpMethod = "GET", value = "购物车列表")
    @RequestMapping("list")
    public AjaxJson list(HttpServletRequest request){
        //判断当前登录用户是否存在，是否有效
        //获取当前登录人信息
//        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
//        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
//            return AjaxJson.fail("当前登录账号无效！");
//        }
        //需要按照漏斗产品组分类
        List<CartCookieProductDTO> listCookies = cartCookieService.getCartFromCookies(request.getCookies());

        if(listCookies == null || listCookies.size() == 0){
            return AjaxJson.fail("购物车为空");
        }
        List<String> ids = new ArrayList<String>();
        listCookies.forEach(m ->{
            if(StringUtils.isNotBlank(m.getProductUuid())){
                ids.add(m.getProductUuid());
            }
        });

        List<CartManagerDTO> listDirectCart = getCartData(listCookies);

        return AjaxJson.ok(listDirectCart);
    }

    public List<CartManagerDTO> getCartData(List<CartCookieProductDTO> listCookies){
        List<CartManagerDTO> listDirectCart = new ArrayList<CartManagerDTO>();
        try {
            //购物车按照产品组分类
            for(CartCookieProductDTO directCart : listCookies){
                String productGroupCode = directCart.getProductGroupCode();
                //声明直采购物车VO
                CartManagerDTO dcv = null;
                //根据产品组编码查询List
                List<CartCookieProductDTO> dcList = null;
                //放入供应商编码
//            directCart.setDealerAccountId(currentAccount.getId());
                if(CollectionUtils.isNotEmpty(listDirectCart)){
                    for(CartManagerDTO v : listDirectCart){
                        if(StringUtils.isNotBlank(productGroupCode)
                                && StringUtils.isNotBlank(v.getProductGroupCode())
                                && productGroupCode.equals(v.getProductGroupCode())){
                            dcv = v;
                            break;
                        }
                    }
                    if(dcv != null){
                        dcList = dcv.getListDirectCart();
                    }else {
                        dcv = new CartManagerDTO();
                        dcList = new ArrayList<CartCookieProductDTO>();
                    }
                }else {
                    //声明直采购物车VO
                    dcv = new CartManagerDTO();
                    //放入产品组编码
                    dcv.setProductGroupCode(productGroupCode);
                    //放入产品组名称
                    dcv.setProductGroupName(directCart.getProductGroupName());
                    //根据产品组编码查询List
                    dcList = new ArrayList<CartCookieProductDTO>();
                }
                dcList.add(directCart);
                //放入list
                dcv.setListDirectCart(dcList);
                //根据不同分组进行获取
                listDirectCart.add(dcv);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listDirectCart;
    }

    /**
     * 加入购物车
     * @param storeGoodsIds 店铺商品ID和数量，多个商品以逗号隔开   storeGoodsId_num  商品id和数量以下划线连接
     * @param request
     * @return
     */
    @ApiOperation(notes = "saveToCart", httpMethod = "POST", value = "加入购物车")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsIds", value = "店铺商品ID和数量，多个商品以逗号隔开", required = true, paramType = "query",dataType = "String")})
    @RequestMapping("saveToCart")
    public AjaxJson saveToCart(String storeGoodsIds, HttpServletRequest request, HttpServletResponse response){
        //判断参数是否合法
        if(StringUtils.isEmpty(storeGoodsIds)){
            return AjaxJson.fail("参数错误！");
        }
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效！");
        }
        List<String> idList = new ArrayList<String>();
        Map<String,Integer> productNumMap = new HashMap<String,Integer>();
        Map<String,Integer> productStockMap = new HashMap<String,Integer>();
        String[] storeGoodsIdList = storeGoodsIds.split(",");
        if(storeGoodsIdList != null && storeGoodsIdList.length > 0){
            for (int i = 0; i < storeGoodsIdList.length; i++){
                String product_num = storeGoodsIdList[i];
                if(StringUtils.isNotBlank(product_num)){
                    String[] productNum = product_num.split("_");
                    if(productNum != null && productNum.length > 0){
                        idList.add(productNum[0]);
                        productNumMap.put(productNum[0],Integer.parseInt(productNum[1]));
                        if(productNum.length > 1){
                            productStockMap.put(productNum[0],Integer.parseInt(productNum[2]));
                        }
                    }
                }

            }
        }
        try{
            String purchaserId = account.getPurchaserId();
            Purchaser purchaser = directPurchaserService.get(purchaserId);
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
            Map<String,Goods> goodsMap = new HashMap<String,Goods>();
            List<Goods> newList = new ArrayList<Goods>();
            if(CollectionUtils.isNotEmpty(goodsList)){
                Map<String, StoreGoods> map = new HashMap<String, StoreGoods>();
                StoreGoods storeGoods = new StoreGoods();
                storeGoods.setIdList(idList);
                List<StoreGoods> storeGoodsList = storeGoodsService.getStoreGoodsByCodes(storeGoods);
                if(CollectionUtils.isNotEmpty(storeGoodsList)){
                    for(StoreGoods s : storeGoodsList){
                        map.put(s.getCode(),s);
                    }
                }
                for(Goods g : goodsList){
                    goodsMap.put(g.getCode(),g);
                    int num = productNumMap.get(g.getCode());
                    if(num > 0){
                        g.setChooseNum(num);
                    }else {
                        return AjaxJson.fail("商品数量不对");
                    }
                    int stock = productStockMap.get(g.getCode());
                    if(stock > 0){
                        g.setStock(stock);
                    }
                    if(g.getExclusivePrice() == 0){
                        return AjaxJson.fail("专享价为0，无法购买");
                    }
                    //判断经销商商品是否是上架
                    if(Global.YES.equals(g.getState()) ){
                        return AjaxJson.fail("商品未上架");
                    }
                    if(map != null){
                        StoreGoods s = map.get(g.getCode());
                        if(s != null){
                            g.setStoreGoodsId(s.getId());
                        }
                    }
                    newList.add(g);
                }

            }
            Cookie[] cookies = new Cookie[1];


            //加入购物车（cookies）
            CartCookieDTO cookieCart = cartCookieService.addCartManagerDetail(request.getCookies(),newList);
            cookies = setCartToCookie(cookieCart,response);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail("加入购物车失败");
        }

        return AjaxJson.ok("加入购物车成功");
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

    public String stringToJsonString(String content){
        content = content.replace("=","\":\"");
        System.out.println(content);
        content = content.replace("&","\",\"");
        System.out.println(content);
//        content = "{\"" + content +"\"}";
        content = "{\"CartCookieDTO\":{" + "\"cookieCartMap\":" + content +"\"}" ;
       return content;
    }

    /**
     * 从购物车批量删除
     * @param storeGoodsIds
     * @return
     */
    @ApiOperation(notes = "delete", httpMethod = "POST", value = "从购物批量车删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsIds", value = "购物车的商品storeGoodsIds，多个storeGoodsIds 用英文逗号分割", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("delete")
    public  AjaxJson delete(String  storeGoodsIds,HttpServletRequest request, HttpServletResponse response){
        //判断参数是否合法
        if(StringUtils.isEmpty(storeGoodsIds)){
            return AjaxJson.fail("参数错误");
        }
        //判断当前登录用户是否存在，是否有效
//        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
//        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
//            return AjaxJson.fail("当前登录账号无效！");
//        }
        //从购物车删除商品
        Cookie[] cookies = new Cookie[1];
        //修改购物车中商品数量
        CartCookieDTO cookieCart = cartCookieService.removeProductFromCart(request.getCookies(),storeGoodsIds);
        cookies = setCartToCookie(cookieCart,response);

        return AjaxJson.ok("删除成功");
    }

    /**
     * 修改购物车中商品数量
     * @param cart
     * @return
     */
    @ApiOperation(notes = "updateCart", httpMethod = "POST", value = "修改购物车数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsId", value = "购物车的商品storeGoodsId", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "chooseNum", value = "商品数量", required = true, paramType = "query",dataType = "int")
    })
    @RequestMapping("updateCart")
    public AjaxJson updateCart(DirectCart cart,HttpServletRequest request, HttpServletResponse response){
        //判断参数是否合法
        if(cart == null || StringUtils.isBlank(cart.getStoreGoodsId()) || (cart.getChooseNum() != null && cart.getChooseNum() ==0)){
            return AjaxJson.fail("参数错误！");
        }
        //判断当前登录用户是否存在，是否有效
//        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
//        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
//            return AjaxJson.fail("当前登录账号无效！");
//        }
        try{

            Cookie[] cookies = new Cookie[1];
            //修改购物车中商品数量
            CartCookieDTO cookieCart = cartCookieService.changeCartProductNum(request.getCookies(),cart.getStoreGoodsId(),cart.getChooseNum());
            cookies = setCartToCookie(cookieCart,response);
            return AjaxJson.ok("修改成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxJson.fail("修改失败！");
    }


    @ApiOperation(notes = "cartCheckedProduct", httpMethod = "POST", value = "修改购物车商品选中状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsId", value = "购物车的商品storeGoodsId,多个以逗号隔开", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "checked", value = "判断是否选中  1选中    0未选中", required = true, paramType = "query",dataType = "int")
    })
    @RequestMapping("cartCheckedProduct")
    public AjaxJson cartCheckedProduct(String storeGoodsId,int checked,HttpServletRequest request, HttpServletResponse response){
        //判断参数是否合法
        if(StringUtils.isBlank(storeGoodsId) || (checked != 0 && checked != 1) ){
            return AjaxJson.fail("参数错误！");
        }
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效！");
        }
        Cookie[] cookies = new Cookie[1];
        //修改购物车中商品数量
        CartCookieDTO cookieCart = cartCookieService.cartCheckedProduct(request.getCookies(),storeGoodsId,checked);
        cookies = setCartToCookie(cookieCart,response);
        return AjaxJson.ok("修改成功！");
    }

    /**
     * 获取购物车总件数
     * @param
     * @return
     */
    @ApiOperation(notes = "getCartGoodsCount", httpMethod = "GET", value = "获取购物车总件数")
    @RequestMapping("getCartGoodsCount")
    public AjaxJson  getCartGoodsCount(HttpServletRequest request){
        //判断当前登录用户是否存在，是否有效
//        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
//        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
//            return AjaxJson.fail("当前登录账号无效！");
//        }
        int count = cartCookieService.getCartCount(request.getCookies());
        return AjaxJson.ok(count);
    }



    /**
     * 根据购物车id查询确认订单信息
     * @param
     * @return
     */
    @ApiOperation(notes = "balance", httpMethod = "POST", value = "购物车结算")
    @RequestMapping("balance")
    public AjaxJson  balance(HttpServletRequest request,HttpServletResponse response){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效！");
        }
        Map<String,Object> returnMap = new HashMap<String,Object>();
        //获取公司信息
        Purchaser purchaser = purchaserService.get(account.getPurchaserId());
        returnMap.put("purchaser",purchaser);

        PurchaserAddress purchaserAddress = new PurchaserAddress();
        purchaserAddress.setPurchaserId(account.getPurchaserId());
        //获取收货地址
        List<PurchaserAddress> purchaserAddressList = purchaserAddressService.findList(purchaserAddress);
        returnMap.put("addressList",purchaserAddressList);
        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
        purchaserInvoice.setPurchaserId(account.getPurchaserId());
        //获取发票信息
        List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
        returnMap.put("invoiceList",purchaserInvoiceList);

        //88码
        returnMap.put("cuscode",purchaser.getLoginNum());
        double creditBalance = directPurchaserAccountService.getCreditBalance(purchaser.getLoginNum());
        returnMap.put("creditBalance",creditBalance);

        //查找购物车信息
        List<CartCookieProductDTO> listCookies = cartCookieService.getCartToBalance(request.getCookies());

        List<CartManagerDTO> listCart = getCartData(listCookies);

        returnMap.put("cart",listCart);
        return AjaxJson.ok(returnMap);
    }


    /**
     *
     * 立即购买
     * @param storeGoodsIds     商品编号_数量
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(notes = "fastBuy", httpMethod = "POST", value = "立即购买结算")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsIds", value = "店铺商品ID和数量，多个商品以逗号隔开", required = true, paramType = "query",dataType = "String")})
    @RequestMapping("fastBuy")
    public AjaxJson  fastBuy(String storeGoodsIds,HttpServletRequest request,HttpServletResponse response){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isBlank(account.getId()) || !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效！");
        }
        if(StringUtils.isBlank(storeGoodsIds)){
            return AjaxJson.fail("参数错误！");
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
        Map<String,Object> returnMap = new HashMap<String,Object>();
        //获取公司信息
        Purchaser purchaser = purchaserService.get(account.getPurchaserId());
        returnMap.put("purchaser",purchaser);

        PurchaserAddress purchaserAddress = new PurchaserAddress();
        purchaserAddress.setPurchaserId(account.getPurchaserId());
        //获取收货地址
        List<PurchaserAddress> purchaserAddressList = purchaserAddressService.findList(purchaserAddress);
        returnMap.put("addressList",purchaserAddressList);
        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
        purchaserInvoice.setPurchaserId(account.getPurchaserId());
        //获取发票信息
        List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
        returnMap.put("invoiceList",purchaserInvoiceList);
        //88码
        returnMap.put("cuscode",purchaser.getLoginNum());
        double creditBalance = directPurchaserAccountService.getCreditBalance(purchaser.getLoginNum());
        returnMap.put("creditBalance",creditBalance);

        String purchaserId = purchaser.getId();
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
        //获取商品
        for (Goods g : goodsList){
            if(StringUtils.isNotBlank(g.getCode())){
                g.setChooseNum(productNumMap.get(g.getCode()));
            }
            if(g.getExclusivePrice() == null || g.getExclusivePrice() < 0){
                return AjaxJson.fail("商品信息错误！");
            }
        }
        try{
            //查找购物车信息
            List<CartCookieProductDTO> listCookies = getCookiesProduct(goodsList);

            List<CartManagerDTO> listCart = getCartData(listCookies);

            returnMap.put("cart",listCart);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxJson.ok(returnMap);
    }

    public List<CartCookieProductDTO> getCookiesProduct(List<Goods> goodsList){
        List<CartCookieProductDTO> list = new ArrayList<CartCookieProductDTO>();
        if(CollectionUtils.isNotEmpty(goodsList)){
            for (Goods goods : goodsList){
                CartCookieProductDTO cart = new CartCookieProductDTO();
                cart.setMainPicUrl(goods.getMainPicUrl());
                cart.setGoodsName(goods.getName());
                cart.setSkuNo(goods.getCode());
                cart.setProductUuid(goods.getCode());
                cart.setChooseNum(goods.getChooseNum());
                cart.setPrice(goods.getExclusivePrice());
                cart.setTotalPrice(goods.getExclusivePrice() * goods.getChooseNum());
                cart.setStoreGoodsId(goods.getCode());
                cart.setProductGroupCode(goods.getProductGroupCode());
                cart.setProductGroupName(goods.getProductGroupName());
                list.add(cart);
            }
        }

        return list;
    }



}
