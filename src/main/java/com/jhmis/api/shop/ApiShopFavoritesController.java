package com.jhmis.api.shop;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserGoodsFavorites;
import com.jhmis.modules.shop.entity.purchaser.PurchaserStoreFavorites;
import com.jhmis.modules.shop.service.*;
import com.jhmis.modules.shop.service.purchaser.*;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller
 * 主要是可能会与后台的控制器重名
 * 订单控制器
 */
@Api(value = "ApiShopFavoritesController", description = "收藏接口")
@RestController
@RequestMapping("/api/shop/favorites")
public class ApiShopFavoritesController {
    protected Logger logger = LoggerFactory.getLogger(ApiShopFavoritesController.class);
    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsExtService goodsExtService;

    @Autowired
    RecommendCatService recommendCatService;

    @Autowired
    RecommendSpecialtopicService recommendSpecialtopicService;

    @Autowired
    AdvertService advertService;

    @Autowired
    StoreGoodsService storeGoodsService;

    @Autowired
    GoodsClassService goodsClassService;

    @Autowired
    GoodsClassPropertiesService goodsClassPropertiesService;

    @Autowired
    GoodsCategoryService goodsCategoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    StoreService storeService;

    @Autowired
    PurchaserFavoritesService purchaserFavoritesService;

    @Autowired
    GoodsBrowseService goodsBrowseService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrderGoodsService orderGoodsService;

    @Autowired
    PurchaserAccountService purchaserAccountService;

    @Autowired
    PurchaserGoodsFavoritesService purchaserGoodsFavoritesService;

    @Autowired
    PurchaserStoreFavoritesService purchaserStoreFavoritesService;
    @Autowired
    PurchaserService purchaserService;


    /**
     * 查询此商品是否已收藏
     * @return
     */
    @ApiOperation(notes = "favoritesGoodsCheck", httpMethod = "GET", value = "查询此商品是否已收藏(返回值0未收藏，1已收藏)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/favoritesGoodsCheck")
    public AjaxJson favoritesGoodsCheck(String storeGoodsId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favorites  查询此商品是否已收藏----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favorites    参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favorites    账号异常 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        //验证是否已收藏
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favorites   调用check方法前 storeGoodsId= " + storeGoodsId +"currentAccount.getId() = "+ currentAccount.getId() +"*_*_*_*_*_*_*_*_*_*");
        flag = purchaserGoodsFavoritesService.check(storeGoodsId,currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favorites   调用check方法后*_*_*_*_*_*_*_*_*_*");
        if(!flag){
            return AjaxJson.ok("1");
        }
        return AjaxJson.ok("0");
    }

    /**
     * 查询此店铺是否已收藏
     * @return
     */
    @ApiOperation(notes = "favoritesStoreCheck", httpMethod = "GET", value = "查询此店铺是否已收藏(返回值0未收藏，1已收藏)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺ID", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/favoritesStoreCheck")
    public AjaxJson favoritesStoreCheck(String storeId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCheck    查询此店铺是否已收藏------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(storeId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCheck    参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCheck    账号异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCheck   调用check方法前 storeId= " + storeId +"currentAccount.getId() = "+ currentAccount.getId() +"*_*_*_*_*_*_*_*_*_*");
        flag = purchaserStoreFavoritesService.checkAgain(storeId,currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCheck   调用check方法后*_*_*_*_*_*_*_*_*_*");
        if(!flag){
            return AjaxJson.ok("1");
        }
        return AjaxJson.ok("0");
    }



    /**
     * 店铺收藏
     * @return
     */
    @ApiOperation(notes = "favoritesStore", httpMethod = "GET", value = "店铺收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query",dataType = "String"),
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesStore")
    public AjaxJson favoritesStore(String storeId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    店铺收藏------接口开始*_*_*_*_*_*_*_*_*_*");
        //参数check
        if(StringUtils.isEmpty(storeId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    storeId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    账号异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        Store store = storeService.findUniqueByProperty("id",storeId);
        if(store==null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    store此店铺不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺不存在");
        }
        boolean flag = purchaserStoreFavoritesService.checkAgain(storeId,currentAccount.getId());
        if(!flag){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    您已收藏此店铺,请勿重复收藏*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("您已收藏此店铺,请勿重复收藏");
        }
        PurchaserStoreFavorites purchaserStoreFavorites = new PurchaserStoreFavorites();
        purchaserStoreFavorites.setStoreId(store.getId());
        purchaserStoreFavorites.setFavDate(new Date());
        purchaserStoreFavorites.setPurchaserAccountId(currentAccount.getId());
        purchaserStoreFavorites.setStoreName(store.getStoreName());
        Purchaser purchaser = purchaserService.findUniqueByProperty("id",currentAccount.getPurchaserId());
        if(StringUtils.isNotEmpty( purchaser.getLogoUrl())){
            purchaserStoreFavorites.setStoreLogo(purchaser.getLogoUrl());
        }
        try{
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    save方法开始*_*_*_*_*_*_*_*_*_*");
            purchaserStoreFavoritesService.save(purchaserStoreFavorites);
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    save方法结束*_*_*_*_*_*_*_*_*_*");
        }catch (Exception e){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore   收藏店铺失败：" + e.getMessage() + "*_*_*_*_*_*_*_*_*_*");
            e.printStackTrace();
            return AjaxJson.fail("收藏店铺失败");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStore    收藏店铺成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok("收藏店铺成功");
    }


    /**
     * 商品收藏
     * @return
     */
    @ApiOperation(notes = "favoritesGoods", httpMethod = "GET", value = "商品收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesGoods")
    public AjaxJson favoritesGoods(String storeGoodsId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods    商品收藏------接口开始*_*_*_*_*_*_*_*_*_*");
        //参数check
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods    storeGoodsId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods    currentAccount账号异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        StoreGoods storeGoods = storeGoodsService.get(storeGoodsId);
        Store store = storeService.findUniqueByProperty("id",storeGoods.getStoreId());
        if(store==null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods    store此店铺不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺不存在");
        }
        storeGoods.setStoreName(store.getStoreName());
        if(storeGoods==null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods   storeGoods此店铺商品不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺商品不存在");
        }
        //验证是否已收藏
        boolean flag = purchaserGoodsFavoritesService.check(storeGoodsId,currentAccount.getId());
        if(!flag){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods   您已收藏此商品,请勿重复收藏*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("您已收藏此商品,请勿重复收藏");
        }
        PurchaserGoodsFavorites purchaserGoodsFavorites = new PurchaserGoodsFavorites();
        purchaserGoodsFavorites.setPurchaserAccountId(currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods   saveGoodsFavorites开始*_*_*_*_*_*_*_*_*_*");
        boolean saveFlag = purchaserGoodsFavoritesService.saveGoodsFavorites(purchaserGoodsFavorites,storeGoods);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods   saveGoodsFavorites结束*_*_*_*_*_*_*_*_*_*");
        if(!saveFlag){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods   收藏商品失败*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("收藏商品失败");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods  收藏商品成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok("收藏商品成功");
    }

    /**
     * 客户在详情页取消收藏商品
     * @return
     */
    @ApiOperation(notes = "favoritesGoodsCancel", httpMethod = "GET", value = "客户在详情页取消收藏商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesGoodsCancel")
    public AjaxJson favoritesGoodsCancel(String storeGoodsId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods    客户在详情页取消收藏商品------接口开始*_*_*_*_*_*_*_*_*_*");
        //参数非空check
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods    参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods   账号异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods   canecl方法调用前 storeGoodsId= " + storeGoodsId +"currentAccount.getId()"+ currentAccount.getId() +" *_*_*_*_*_*_*_*_*_*");
        purchaserGoodsFavoritesService.cancel(storeGoodsId,currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesGoods  取消收藏商品成功 *_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok("取消收藏商品成功");
    }


    /**
     * 客户在详情页取消收藏店铺
     * @return
     */
    @ApiOperation(notes = "favoritesStoreCancel", httpMethod = "GET", value = "客户在详情页取消收藏店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺ID", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesStoreCancel")
    public AjaxJson favoritesStoreCancel(String storeId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCancel    客户在详情页取消收藏店铺------接口开始*_*_*_*_*_*_*_*_*_*");
        //参数非空check
        if(StringUtils.isEmpty(storeId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCancel    storeId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCancel    currentAccount账号异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        purchaserStoreFavoritesService.cancel(storeId,currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/favoritesStoreCancel    取消收藏店铺成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok("取消收藏店铺成功");
    }
}
