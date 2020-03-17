package com.jhmis.api.purchaser;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.mapper.CartMapper;
import com.jhmis.modules.shop.mapper.StoreGoodsMapper;
import com.jhmis.modules.shop.mapper.StoreMapper;
import com.jhmis.modules.shop.service.CartService;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.service.StoreService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(value = "ApiCartController", description = "购物车管理")
@RestController
@RequestMapping("/api/purchaser/cart")
public class ApiCartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private  GoodsService goodsService;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private StoreGoodsMapper storeGoodsMapper;
    @ApiOperation(notes = "list", httpMethod = "GET", value = "购物车列表")
    @RequestMapping("list")
    public AjaxJson list(){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //查询该采购人下  购物车中的所有商品信息，以店铺，添加时间倒序排列
        Cart cart = new Cart();
        cart.setPurchaserAccountId(purchaserAccount.getId());
//       cart.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
        List<Store> storeList = storeService.findDistinctStore(cart);
        StoreGoods storeGoods = new StoreGoods();
        List<Store> cartGoodsList = new ArrayList<>();
        for(Store  store:storeList){
            storeGoods.setStoreId(store.getId());
            storeGoods.setPurchaserId(purchaserAccount.getPurchaserId());
            storeGoods.setPurchaserAccountId(purchaserAccount.getId());
//            storeGoods.setPurchaserId("486773abf4a74ca199fdd0998e105158");
//            storeGoods.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
            storeGoods.setIdList(null);
            List<StoreGoods> storeGoodsList = storeGoodsService.findCartStoreGoods(storeGoods);
            store.setStoreGoodsList(storeGoodsList);
            cartGoodsList.add(store);
        }
        return AjaxJson.ok(cartGoodsList);
    }

    /**
     * 加入购物车
     * @param cart
     * @param request
     * @return
     */
    @ApiOperation(notes = "saveToCart", httpMethod = "POST", value = "加入购物车")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "chooseNum", value = "商品数量", required = true, paramType = "query",dataType = "int")
    })
    @RequestMapping("saveToCart")
    public AjaxJson saveToCart(Cart cart, HttpServletRequest request){
        //判断参数是否合法
        if(cart == null ||  (cart.getChooseNum() != null && cart.getChooseNum() ==0)){
            return AjaxJson.fail("参数错误！");
        }
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        if(cart == null || StringUtils.isEmpty(cart.getStoreGoodsId())){
            return AjaxJson.fail("参数错误");
        }
//       cart.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
        cart.setPurchaserAccountId(purchaserAccount.getId());
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setId(cart.getStoreGoodsId());

        List<StoreGoods> storeGoodsList =  storeGoodsService.findList(storeGoods);
        if(storeGoodsList.size() ==0){
            return AjaxJson.fail("商品不存在");
        }
        //判断经销商商品是否是上架
        storeGoods = storeGoodsList.get(0);
        if(storeGoods != null){
            if(!Global.YES.equals(storeGoods.getIsShelf()) && !Global.YES.equals(storeGoods.getAuditState())){
                return AjaxJson.fail("商品未上架");
            }
        }

        cart.setMainPicUrl(storeGoods.getMainPicUrl());
        cart.setGoodsName(storeGoods.getGoodsName());
        cart.setStoreId(storeGoods.getStoreId());
        cart.setStoreName(storeGoods.getStoreName());
        cart.setGoodsCode(storeGoods.getCode());
        cart.setDelFlag(Global.NO);
        //判断同一个店铺下，同一个下单人，同一个商品是否存在，存在的话，修改数量
        Cart c = new Cart();
        c.setStoreId(cart.getStoreId());
        c.setGoodsCode(cart.getGoodsCode());
        c.setPurchaserAccountId(cart.getPurchaserAccountId());
        List<Cart> cartList = cartService.findList(c);
        if(cartList.size()> 0){
            Cart ca = cartList.get(0);
            ca.setChooseNum(ca.getChooseNum()+cart.getChooseNum());
            cartService.save(ca);
        }else{
            cart.setStoreName(storeGoods.getStoreName());
            cartService.save(cart);
        }
        return AjaxJson.ok("加入购物车成功");
    }

    /**
     * 从购物车批量删除
     * @param ids
     * @return
     */
    @ApiOperation(notes = "delete", httpMethod = "POST", value = "从购物批量车删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "购物车的id，多个id 用英文逗号分割", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping("delete")
    public  AjaxJson delete(String  ids){
        //判断参数是否合法
        if(StringUtils.isEmpty(ids)){
            return AjaxJson.fail("参数错误");
        }

        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        String idArray [] = ids.split(",");
        Cart cart = new Cart();
       cart.setPurchaserAccountId(purchaserAccount.getId());
//        cart.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
        for(String id:idArray){
            cart = cartService.get(id);
            cartService.delete(cart);
        }
        return AjaxJson.ok("删除成功");
    }

    /**
     * 修改购物车中商品数量
     * @param cart
     * @return
     */
    @ApiOperation(notes = "updateCart", httpMethod = "POST", value = "修改购物车数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "goodsCode", value = "商品编码", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "chooseNum", value = "商品数量", required = true, paramType = "query",dataType = "int")
    })
    @RequestMapping("updateCart")
    public AjaxJson updateCart(Cart cart){
        //判断参数是否合法
        if(cart == null || StringUtils.isEmpty(cart.getStoreId()) || StringUtils.isEmpty(cart.getGoodsCode()) || (cart.getChooseNum() != null && cart.getChooseNum() ==0)){
            return AjaxJson.fail("参数错误！");
        }
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        cart.setPurchaserAccountId(purchaserAccount.getId());
//        cart.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
        Cart c = new Cart();
        c.setStoreId(cart.getStoreId());
        c.setGoodsCode(cart.getGoodsCode());
        List<Cart> cartList = cartService.findList(c);
        if(cartList.size()> 0){
            Cart ca = cartList.get(0);
            ca.setChooseNum(cart.getChooseNum());
            cartService.save(ca);
            return AjaxJson.ok("修改成功！");
        }
        return AjaxJson.fail("修改失败！");
    }
    @ApiOperation(notes = "getCartGoodsCount", httpMethod = "GET", value = "获取购物车总件数")
    @RequestMapping("getCartGoodsCount")
    public AjaxJson  getCartGoodsCount(){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        Cart cart = new Cart();
        cart.setPurchaserAccountId(purchaserAccount.getId());
//        cart.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
        int count = cartService.getCartCount(cart);
        return AjaxJson.ok(count);
    }
    @ApiOperation(notes = "balance", httpMethod = "POST", value = "购物车节算")
    @ApiImplicitParams({@ApiImplicitParam(name = "cartIds", value = "购物车ID，多个ID之间逗号分割", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("balance")
    public AjaxJson  balance(String cartIds){
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())|| !"0".equals(purchaserAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        if(StringUtils.isEmpty(cartIds)){
            return AjaxJson.fail("参数错误");
        }
        List<String> cartIdList = new ArrayList<>();
        if(cartIds.indexOf(",") > 0){
            cartIdList = Arrays.asList(cartIds.split(","));
        }else{
            cartIdList.add(cartIds);
        }
        Cart  searchCart = new Cart();
        searchCart.setIdList(cartIdList);
        searchCart.setPurchaserAccountId(purchaserAccount.getId());
//        searchCart.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
        List<Store> storeList = storeMapper.findDistinctStore(searchCart);
        if(storeList.size() == 0){
            return AjaxJson.fail("结算失败");
        }
        List<Store> storeResultList = new ArrayList<>();
        for(Store store:storeList){
            StoreGoods storeGoods = new StoreGoods();
            storeGoods.setIdList(cartIdList);
            storeGoods.setStoreId(store.getId());
            storeGoods.setPurchaserId(purchaserAccount.getPurchaserId());
            storeGoods.setPurchaserAccountId(purchaserAccount.getId());
//            storeGoods.setPurchaserId("486773abf4a74ca199fdd0998e105158");
//            storeGoods.setPurchaserAccountId("581a099bb13941cc99741e66e2a543de");
            List<StoreGoods> storeGoodsList = storeGoodsMapper.findCartStoreGoods(storeGoods);
            store.setStoreGoodsList(storeGoodsList);
            storeResultList.add(store);
        }
        return AjaxJson.ok(storeResultList);
    }

}
