package com.jhmis.api.direct;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.Sign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haier.link.upper.service.LinkProductUpperReadService;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.Brand;
import com.jhmis.modules.shop.entity.CategoryNode;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.GoodsExt;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.AdvertService;
import com.jhmis.modules.shop.service.BrandService;
import com.jhmis.modules.shop.service.GoodsBrowseService;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.GoodsClassPropertiesService;
import com.jhmis.modules.shop.service.GoodsClassService;
import com.jhmis.modules.shop.service.GoodsEvaluateService;
import com.jhmis.modules.shop.service.GoodsExtService;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.OrderGoodsService;
import com.jhmis.modules.shop.service.OrdersService;
import com.jhmis.modules.shop.service.RecommendCatService;
import com.jhmis.modules.shop.service.RecommendSpecialtopicService;
import com.jhmis.modules.shop.service.StoreEvaluateService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import com.jhmis.modules.shop.service.StoreService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserFavoritesService;
import com.jhmis.modules.shop.service.purchaser.PurchaserGoodsFavoritesService;
import com.jhmis.modules.shop.service.purchaser.PurchaserStoreFavoritesService;
import com.jhmis.modules.shop.utils.DealerUtils;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller
 * 主要是可能会与后台的控制器重名
 * 直采商品控制器
 */
@Api(value = "ApiDirectGoodsController", description = "直采商品相关接口")
@RestController
@RequestMapping("/api/direct/goods")
public class ApiDirectGoodsController {
    protected Logger logger = LoggerFactory.getLogger(ApiDirectGoodsController.class);

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

    @Lazy
    @Autowired
    LinkProductUpperReadService linkProductUpperReadService;

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
    GoodsEvaluateService goodsEvaluateService;

    @Autowired
    PurchaserAccountService purchaserAccountService;

    @Autowired
    StoreEvaluateService storeEvaluateService;

    @Autowired
    PurchaserGoodsFavoritesService purchaserGoodsFavoritesService;

    @Autowired
    PurchaserStoreFavoritesService purchaserStoreFavoritesService;
    long key = Constants.PRODUCTS_CENTER_KEY;
    String secret = Constants.PRODUCTS_CENTER_SECRET;

    /**
     * 商品分类列表
     *
     * @return
     */
    @ApiOperation(notes = "categoryList", httpMethod = "GET", value = "商品分类列表(id为空则是查找一级分类,如果不为空则查询其子类)")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "分类", required = false, paramType = "query", dataType = "String")})
    @RequestMapping("/categoryList")
    public AjaxJson categoryList(GoodsCategory g) {
    	  DealerAccount currentAccount = DealerUtils.getDealerAccount();
          if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
              return AjaxJson.fail("当前登录账号无效");
          }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/goods  商品分类列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        GoodsCategory goodsCategory = new GoodsCategory();
        if (null == g.getId()) {
            //如果为空则查询父类分类
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/goods  id为空则查询父类分类*_*_*_*_*_*_*_*_*_*");
            g.setId("0");
        }
        goodsCategory.setParent(g);
        List<GoodsCategory> list = goodsCategoryService.findList(goodsCategory);
        if (list == null) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/goods 未查到数据*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("未查到数据");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/goods 查询成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(list);
    }


    /**
     * 商品详情
     *
     * @return
     */
    @ApiOperation(notes = "detail", httpMethod = "GET", value = "商品详情(返回一个map包含产品中心productInfo,店铺商品sg,及商品基本信息g、goodsExt*******!!!!注意此接口需要调用产品中心，会有可能调用不成功)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品id", required = true, paramType = "query", dataType = "String"),
    })
    @RequestMapping("/detail")
    public AjaxJson detail(String storeGoodsId) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/detail  调用接口开始*_*_*_*_*_*_*_*_*_*");
        //身份验证
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //参数验证
        if (StringUtils.isEmpty(storeGoodsId)) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/detail  storeGoodsId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //店铺产品查询
        StoreGoods storeGoods = storeGoodsService.get(storeGoodsId);
        if (storeGoods == null) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/detail  此店铺商品不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺商品不存在");
        }
        //店铺
        Store store = storeService.findUniqueByProperty("id",storeGoods.getStoreId());
        Map<String, Object> map = new HashMap<>();
        //查询产品中心数据
        Sign s = new Sign(key,secret);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/detail  linkProductUpperReadService.getProductInfoByCode前*_*_*_*_*_*_*_*_*_*");
        ProductInfo productInfo = linkProductUpperReadService.getProductInfoByCode(storeGoods.getCode(), s).getResult();
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/detail  linkProductUpperReadService.getProductInfoByCode后*_*_*_*_*_*_*_*_*_*");
        Goods goods = goodsService.findByCode(storeGoods.getCode());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/detail  goodsService.findByCode*_*_*_*_*_*_*_*_*_*");
        GoodsExt goodsExt = goodsExtService.findUniqueByProperty("code", storeGoods.getCode());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGoodsController  /api/shop/detail  goodsExt*_*_*_*_*_*_*_*_*_*");
        //查询品牌名称
        Brand b = brandService.findUniqueByProperty("id",goods.getBrand());
        //店铺
        map.put("store",store);
        //品牌信息
        map.put("brand", b);
        //商品基本信息
        map.put("goods", goods);
        //商品扩展信息
        map.put("goodsExt", goodsExt);
        //店铺商品信息
        map.put("storeGoods", storeGoods);
        //产品中心信息
        map.put("productInfo", productInfo);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  接口调用结束*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(map);
    }


    /**
     * 根据分类进入展品列表页
     *
     * @return
     */
    @ApiOperation(notes = "searchGoodsByCategory", httpMethod = "POST", value = "根据分类进入展品列表页进行筛选(用于平台商品)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryPid", value = "父级分类", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "子集分类", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "form", dataType = "int"),
    })
    @RequestMapping("/searchGoodsByCategory")
    public AjaxJson searchGoodsByCategory(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response) {
    	  DealerAccount currentAccount = DealerUtils.getDealerAccount();
          if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
              return AjaxJson.fail("当前登录账号无效");
          }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  根据分类进入展品列表页----------接口开始*_*_*_*_*_*_*_*_*_*");
        Page<StoreGoods> page = new Page<StoreGoods>(request, response);
        Map<String, Object> map = new HashMap<>();
        //查询需要是上架的产品
        storeGoods.setIsShelf(Global.YES);
        //此次是直采的商品
        storeGoods.setRemarks("1");
        Page<StoreGoods> pageStoreGoods = storeGoodsService.findPage(page, storeGoods);
        //改为返回layer格式
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  调用接口成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.layuiTable(pageStoreGoods);
    }

    /**
     * 商城首页中分类树
     *
     * @return
     */
    @ApiOperation(notes = "categoryTree", httpMethod = "GET", value = "商城首页中分类树(用于首页nav及列表页分类)")
    @RequestMapping("/categoryTree")
    public AjaxJson categoryTree() {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryTree 商城首页中分类树调用接口开始*_*_*_*_*_*_*_*_*_*");
        GoodsCategory goodsCategory = new GoodsCategory();
        List<CategoryNode> categoryTree = goodsCategoryService.findTree(goodsCategory);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryTree 商城首页中分类树调用接口结束*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(categoryTree);
    }

    @RequestMapping("/categoryTreePar")
    public AjaxJson categoryTreePar(GoodsCategory goodsCategory) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryTree 商城首页中分类树调用接口开始*_*_*_*_*_*_*_*_*_*");
        List<CategoryNode> categoryTree = goodsCategoryService.findTree(goodsCategory);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryTree 商城首页中分类树调用接口结束*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(categoryTree);
    }
    
    
}
