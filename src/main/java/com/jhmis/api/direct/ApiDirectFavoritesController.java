package com.jhmis.api.direct;

import java.util.HashMap;
import java.util.Map;

import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.utils.DealerUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.DirectGoodsFavorites;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.AdvertService;
import com.jhmis.modules.shop.service.BrandService;
import com.jhmis.modules.shop.service.DirectGoodsFavoritesService;
import com.jhmis.modules.shop.service.GoodsBrowseService;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.GoodsClassPropertiesService;
import com.jhmis.modules.shop.service.GoodsClassService;
import com.jhmis.modules.shop.service.GoodsExtService;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.OrderGoodsService;
import com.jhmis.modules.shop.service.OrdersService;
import com.jhmis.modules.shop.service.RecommendCatService;
import com.jhmis.modules.shop.service.RecommendSpecialtopicService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.service.StoreService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserFavoritesService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.service.purchaser.PurchaserStoreFavoritesService;
import com.jhmis.modules.shop.utils.PurchaserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller
 * 主要是可能会与后台的控制器重名
 * 直采收藏控制器
 */
@Api(value = "ApiDirectFavoritesController", description = "直采收藏接口")
@RestController
@RequestMapping("/api/direct/favorites")
public class ApiDirectFavoritesController {
    protected Logger logger = LoggerFactory.getLogger(ApiDirectFavoritesController.class);
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
    GoodsBrowseService goodsBrowseService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrderGoodsService orderGoodsService;

    @Autowired
    PurchaserAccountService purchaserAccountService;

    @Autowired
    DirectGoodsFavoritesService directGoodsFavoritesService;

    @Autowired
    PurchaserStoreFavoritesService purchaserStoreFavoritesService;
    @Autowired
    PurchaserService purchaserService;


    /**
     * 查询此商品是否已收藏
     * @return
     */
    @ApiOperation(notes = "directfavoritesGoodsCheck", httpMethod = "GET", value = "查询此商品是否已收藏(返回值0未收藏，1已收藏)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String")
    })
    @RequestMapping("/directfavoritesGoodsCheck")
    public AjaxJson favoritesGoodsCheck(String storeGoodsId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/direct/favorites  查询此商品是否已收藏----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/direct/favorites    参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/direct/favorites    账号异常 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        //验证是否已收藏
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/direct/favorites   调用check方法前 storeGoodsId= " + storeGoodsId +"currentAccount.getId() = "+ currentAccount.getId() +"*_*_*_*_*_*_*_*_*_*");
        flag = directGoodsFavoritesService.check(storeGoodsId,currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/direct/favorites   调用check方法后*_*_*_*_*_*_*_*_*_*");
        if(!flag){
            return AjaxJson.ok("1");
        }
        return AjaxJson.ok("0");
    }

    
    /**
     * 商品收藏
     * @return
     */
    @ApiOperation(notes = "directfavoritesGoods", httpMethod = "GET", value = "商品收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("dealer")
    @RequestMapping("/directfavoritesGoods")
    public AjaxJson favoritesGoods(String storeGoodsId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/Direct/DirectfavoritesGoods    商品收藏------接口开始*_*_*_*_*_*_*_*_*_*");
        //参数check
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/Direct/DirectfavoritesGoods    storeGoodsId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数不能为空");
        }
        //验证身份
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/Direct/DirectfavoritesGoods    currentAccount账号异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        StoreGoods storeGoods = storeGoodsService.get(storeGoodsId);
        if(storeGoods==null){
        	logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/Direct/DirectfavoritesGoods    store此店铺不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("商品不存在！");
        }
        Store store = storeService.findUniqueByProperty("id",storeGoods.getStoreId());
        if(store==null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/Direct/DirectfavoritesGoods    store此店铺不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺不存在");
        }
        storeGoods.setStoreName(store.getStoreName());
        //验证是否已收藏
        boolean flag = directGoodsFavoritesService.check(storeGoodsId,currentAccount.getId());
        if(!flag){
            logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods   您已收藏此商品,请勿重复收藏*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("您已收藏此商品,请勿重复收藏");
        }
        DirectGoodsFavorites directGoodsFavorites = new DirectGoodsFavorites();
        directGoodsFavorites.setDealerAccountId(currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/Direct/DirectfavoritesGoods   saveGoodsFavorites开始*_*_*_*_*_*_*_*_*_*");
        boolean saveFlag = directGoodsFavoritesService.saveGoodsFavorites(directGoodsFavorites,storeGoods);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectFavoritesController   /api/Direct/DirectfavoritesGoods   saveGoodsFavorites结束*_*_*_*_*_*_*_*_*_*");
        if(!saveFlag){
            logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods   收藏商品失败*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("收藏商品失败");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods  收藏商品成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok("收藏商品成功");
    }

    /**
     * 客户在详情页取消收藏商品
     * @return
     */
    @ApiOperation(notes = "directfavoritesGoodsCancel", httpMethod = "GET", value = "客户在详情页取消收藏商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品ID", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("dealer")
    @RequestMapping("/directfavoritesGoodsCancel")
    public AjaxJson favoritesGoodsCancel(String storeGoodsId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods    客户在详情页取消收藏商品------接口开始*_*_*_*_*_*_*_*_*_*");
        //参数非空check
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods    参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods   账号异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("账号异常");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods   canecl方法调用前 storeGoodsId= " + storeGoodsId +"currentAccount.getId()"+ currentAccount.getId() +" *_*_*_*_*_*_*_*_*_*");
        directGoodsFavoritesService.cancel(storeGoodsId,currentAccount.getId());
        logger.info("*_*_*_*_*_*_*_*_*_* ApidirectFavoritesController   /api/direct/favoritesGoods  取消收藏商品成功 *_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok("取消收藏商品成功");
    }



}
