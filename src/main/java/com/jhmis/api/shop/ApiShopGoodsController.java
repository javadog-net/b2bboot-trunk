package com.jhmis.api.shop;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.Sign;
import com.haier.oms.client.model.qiyegou.QiYeGouQueryStockInfo;
import com.haier.util.OMS_Query;
import com.jhmis.modules.directpurchaser.service.DirectGoodsService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserService;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.service.*;
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
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserStoreFavorites;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserFavoritesService;
import com.jhmis.modules.shop.service.purchaser.PurchaserGoodsFavoritesService;
import com.jhmis.modules.shop.service.purchaser.PurchaserStoreFavoritesService;
import com.jhmis.modules.shop.utils.PurchaserUtils;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller
 * 主要是可能会与后台的控制器重名
 * 商品控制器
 */
@Api(value = "ApiShopGoodsController", description = "商品管理")
@RestController
@RequestMapping("/api/shop/goods")
public class ApiShopGoodsController {
    protected Logger logger = LoggerFactory.getLogger(ApiShopGoodsController.class);

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
    private LinkProductUpperReadService linkProductUpperReadService;

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
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goods  商品分类列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        GoodsCategory goodsCategory = new GoodsCategory();
        if (null == g.getId()) {
            //如果为空则查询父类分类
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goods  id为空则查询父类分类*_*_*_*_*_*_*_*_*_*");
            g.setId("0");
        }
        goodsCategory.setParent(g);
        List<GoodsCategory> list = goodsCategoryService.findList(goodsCategory);
        if (list == null) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goods 未查到数据*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("未查到数据");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goods 查询成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(list);
    }

    /**
     * 商品相关推荐，精选，猜你想要
     *
     * @return
     */
    @ApiOperation(notes = "goodsWants", httpMethod = "POST", value = "商品相关推荐，精选，猜你想要(用于侧边栏等边角处)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categoryPid", value = "分类父级id", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isRecommend", value = "是否推荐（1是0否）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isPromotion", value = "是否促销（1是0否）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isTop", value = "是否置顶（1是0否）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isNew", value = "是否上新（1是0否）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isHotSale", value = "是否热卖（1是0否）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isToIndexYanxxuan", value = "是否企业购严选（1是0否）", required = false, paramType = "form", dataType = "String"),

    })
    @RequestMapping("/goodsWants")
    public AjaxJson goodsWants(StoreGoods storeGoods) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodsWants  商品相关推荐，精选，猜你想要----------接口开始*_*_*_*_*_*_*_*_*_*");
        //必须是上架的产品
        storeGoods.setIsShelf(Global.YES);
        List<StoreGoods> storeGoodsList = storeGoodsService.findList(storeGoods);
        //改为返回layer格式
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodsWants  商品相关推荐，精选，猜你想要----------接口调用成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.layuiTable(storeGoodsList);
    }

    /**
     * 店铺商品分类列表
     *
     * @return
     */
    @ApiOperation(notes = "storeGoodsCategory", httpMethod = "GET", value = "店铺商品分类列表(用于shop_index.html)")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "String")})
    @RequestMapping("/storeGoodsCategory")
    public AjaxJson storeGoodsCategory(String storeId) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/storeGoodsCategory  店铺商品分类列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        Map<String,Object> map = new HashMap<>();
        //参数校验
        if (StringUtils.isEmpty(storeId)) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/storeGoodsCategory  storeId必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("必填参数不能为空");
        }
        Store store = storeService.findUniqueByProperty("id", storeId);
        if (store == null) {
            //此店铺不存在
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/storeGoodsCategory  store此店铺不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺不存在");
        }
        //给予前台展示
        List<StoreGoodsCategory> sgcList = new ArrayList<>();
        //查找店铺下所包含的所有分类
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/storeGoodsCategory 查找店铺下所包含的所有分类*_*_*_*_*_*_*_*_*_*");
        List<GoodsCategory> goodsCategoryList = goodsCategoryService.findStoreAllCat(storeId);
        if (goodsCategoryList != null && goodsCategoryList.size() > 0) {
            //分类查找
            for (GoodsCategory gc : goodsCategoryList) {
                //用于装载数据返回前台
                StoreGoodsCategory sgc = new StoreGoodsCategory();
                StoreGoods sg = new StoreGoods();
                sg.setCategoryPid(gc.getId());
                //必须是上架的产品
                sg.setIsShelf(Global.YES);
                sg.setStoreId(store.getId());
                List<StoreGoods> storeGoodsList = storeGoodsService.findList(sg);
                sgc.setCategoryId(gc.getId());
                sgc.setCategoryName(gc.getName());
                sgc.setStoreGoodsList(storeGoodsList);
                sgcList.add(sgc);
            }
        } else {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/storeGoodsCategory 暂无数据*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("暂无数据");
        }
        map.put("store",store);
        map.put("sgcList",sgcList);
        //改为返回layer格式
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/storeGoodsCategory  接口调用成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(map);
    }

    /**
     * 根据分类查询商品show
     *
     * @return
     */
    @ApiOperation(notes = "goodsByGoodsCategory", httpMethod = "POST", value = "根据商品分类进行筛选商品(用于首页)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({@ApiImplicitParam(name = "categoryPid", value = "分类", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityid", value = "区域id(根据页面定位获取地区id,目的为了匹配供应商工贸所在地区进行筛选)", required = false, paramType = "form", dataType = "String")})
    @RequestMapping("/goodsByGoodsCategory")
    public AjaxJson goodsByGoodsCategory(StoreGoods storeGoods) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodsByGoodsCategory  根据分类查询商品----------接口开始*_*_*_*_*_*_*_*_*_*");
        if (StringUtils.isEmpty(storeGoods.getCategoryPid())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodsByGoodsCategory  必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("必填参数不能为空");
        }
        //必须是上架产品
        storeGoods.setIsShelf(Global.YES);
        List<StoreGoods> storeGoodsList = storeGoodsService.selectByCategoryPid(storeGoods);
        if (storeGoodsList == null) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodsByGoodsCategory  未查到数据*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("未查到数据");
        }
        //此不需要layer格式
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodsByGoodsCategory  调用成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(storeGoodsList);
    }

    /**
     * 商品搜索
     *
     * @return
     */
    @ApiOperation(notes = "searchShowTag", httpMethod = "POST", value = "商品搜索列表(页顶搜索框处录入文字后搜索,根据关键字进行标签搜索展示)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索关键字", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/searchShowTag")
    public AjaxJson searchShowTag(StoreGoods storeGoods) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchShowTag  商品搜索----------接口开始*_*_*_*_*_*_*_*_*_*");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(storeGoods.getKey())) {
            //设置搜索值
            storeGoods.setGoodsName(storeGoods.getKey());
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchShowTag  商品搜索搜索值:" + storeGoods.getKey()+ "*_*_*_*_*_*_*_*_*_*");
            //首先匹配属性分类
            GoodsClass goodsClass = goodsClassService.findUniqueByProperty("name", storeGoods.getKey());
            if (goodsClass != null) {
                logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchShowTag 首先匹配属性分类*_*_*_*_*_*_*_*_*_*");
                List<GoodsClassPropertiesContent> goodsClassPropertiesContentList = goodsClassPropertiesService.findClassProperties(goodsClass.getId());
                if (goodsClassPropertiesContentList != null) {
                    map.put("goodsClassPropertiesContentList", goodsClassPropertiesContentList);
                }
            } else {
                logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchShowTag 匹配小分类属性名称*_*_*_*_*_*_*_*_*_*");
                //匹配小分类属性名称
                GoodsClassProperties gcp = new GoodsClassProperties();
                gcp.setName(storeGoods.getKey());
                //防止属性值有重复的，采用list获取
                List<GoodsClassProperties> goodsClassPropertiesList = goodsClassPropertiesService.findList(gcp);
                if (goodsClassPropertiesList.size() != 0) {
                    List<GoodsClassPropertiesContent> goodsClassPropertiesContentList = goodsClassPropertiesService.findClassProperties(goodsClassPropertiesList.get(0).getClassId());
                    if (goodsClassPropertiesContentList != null) {
                        map.put("goodsClassPropertiesContentList", goodsClassPropertiesContentList);
                    }
                }else{
                    //匹配属性值
                    //匹配小分类属性值
                    GoodsClassProperties gcpForValue = new GoodsClassProperties();
                    gcpForValue.setValue(storeGoods.getKey());
                    //防止属性值有重复的，采用list获取
                    List<GoodsClassProperties> gcpList = goodsClassPropertiesService.findList(gcpForValue);
                    if (gcpList.size() != 0) {
                        List<GoodsClassPropertiesContent> goodsClassPropertiesContentList = goodsClassPropertiesService.findClassProperties(gcpList.get(0).getClassId());
                        if (goodsClassPropertiesContentList != null) {
                            map.put("goodsClassPropertiesContentList", goodsClassPropertiesContentList);
                        }
                    }
                }
            }
        }
        //品牌相关
        List<Brand> listBrand = brandService.selectBrandForSearch(storeGoods.getKey());
        //分类相关
        GoodsCategory goodsCategory = new GoodsCategory();
        GoodsCategory par = new GoodsCategory();
        goodsCategory.setName(storeGoods.getKey());
        List<GoodsCategory> goodsCategoryList = goodsCategoryService.findList(goodsCategory);
        List<GoodsCategory> containerList = new ArrayList<>();
        List<GoodsCategory> tempList =  new ArrayList<>();;
        if(goodsCategoryList!=null){
            int len = goodsCategoryList.size();
            for(int i=0; i<len; i++){
                if("0".equals(goodsCategoryList.get(i).getParentId())){
                    containerList.add(goodsCategoryList.get(i));
                }
            }
        }
        if(containerList.size()>0){
            for(GoodsCategory gc:containerList){
                GoodsCategory gparent = new GoodsCategory();
                gparent.setParent(gc);
                tempList.addAll(goodsCategoryService.findList(gparent));
            }
        }
        map.put("listBrand", listBrand);
        map.put("goodsCategoryList", tempList);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchShowTag 接口调用成功*_*_*_*_*_*_*_*_*_*");
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
            @ApiImplicitParam(name = "comprehensiveFlag", value = "综合排序('0'是排序，'1'是不排序) 会按照是否推荐进行排序", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsClickFlag", value = "是否根据浏览数量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsSaleFlag", value = "是否根据销售量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsCollectFlag", value = "是否根据收藏量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsCommentFlag", value = "是否根据评论数量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityid", value = "区域id(根据页面定位获取地区id,目的为了匹配供应商工贸所在地区进行筛选)", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping("/searchGoodsByCategory")
    public AjaxJson searchGoodsByCategory(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  根据分类进入展品列表页----------接口开始*_*_*_*_*_*_*_*_*_*");
        Page<StoreGoods> page = new Page<StoreGoods>(request, response);
        Map<String, Object> map = new HashMap<>();
        String orderBystr = "";
        ///根据属性排序
        if ("1".equals(storeGoods.getComprehensiveFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  综合排序*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.is_recommend desc,";
        }
        if ("1".equals(storeGoods.getGoodsClickFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  点击次数排序*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_click desc,";
        }
        if ("1".equals(storeGoods.getGoodsSaleFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  销量次数排序*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_salenum desc,";
        }
        if ("1".equals(storeGoods.getGoodsCollectFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  收藏次数排序*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_collect desc,";
        }
        if ("1".equals(storeGoods.getGoodsCommentFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  评论次数排序*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_comment_num desc,";
        }
        if (!"".equals(orderBystr)) {
            orderBystr = orderBystr.substring(0, orderBystr.length() - 1);
            page.setOrderBy(orderBystr);
        }
        //查询需要是上架的产品
        storeGoods.setIsShelf(Global.YES);
        Page<StoreGoods> pageStoreGoods = storeGoodsService.findPage(page, storeGoods);
        //改为返回layer格式
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchGoodsByCategory  调用接口成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.layuiTable(pageStoreGoods);
    }


    /**
     * 根据分类进入店铺商品列表页
     *
     * @return
     */
    @ApiOperation(notes = "searchStoreGoodsByCategory", httpMethod = "POST", value = "根据分类进入展品列表页进行筛选(用于单一店铺商品)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categoryPid", value = "父级分类", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "子集分类", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "comprehensiveFlag", value = "综合排序('0'是排序，'1'是不排序) 会按照是否推荐进行排序", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsClickFlag", value = "是否根据浏览数量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsSaleFlag", value = "是否根据销售量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsCollectFlag", value = "是否根据收藏量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "goodsCommentFlag", value = "是否根据评论数量排序('0'是排序，'1'是不排序)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityid", value = "区域id(根据页面定位获取地区id,目的为了匹配供应商工贸所在地区进行筛选)", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping("/searchStoreGoodsByCategory")
    public AjaxJson searchStoreGoodsByCategory(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  调用接口成功*_*_*_*_*_*_*_*_*_*");
        //参数校验
        if (StringUtils.isEmpty(storeGoods.getStoreId())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  StoreId必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("必填参数不能为空");
        }
        Store store = storeService.findUniqueByProperty("id", storeGoods.getStoreId());
        if (store == null) {
            //此店铺不存在
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  /此店铺不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺不存在");
        }
        Page<StoreGoods> page = new Page<StoreGoods>(request, response);
        Map<String, Object> map = new HashMap<>();
        String orderBystr = "";
        ///根据属性排序
        if ("1".equals(storeGoods.getComprehensiveFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  排序comprehensive*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.is_recommend desc,";
        }
        if ("1".equals(storeGoods.getGoodsClickFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  GoodsClick*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_click desc,";
        }
        if ("1".equals(storeGoods.getGoodsSaleFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  GoodsSale*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_salenum desc,";
        }
        if ("1".equals(storeGoods.getGoodsCollectFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  GoodsCollect*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_collect desc,";
        }
        if ("1".equals(storeGoods.getGoodsCommentFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  GoodsComment*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.goods_comment_num desc,";
        }
        if ("1".equals(storeGoods.getMarketPriceFlag())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchStoreGoodsByCategory  MarketPrice*_*_*_*_*_*_*_*_*_*");
            orderBystr += "a.market_price desc,";
        }
        if (!"".equals(orderBystr)) {
            orderBystr = orderBystr.substring(0, orderBystr.length() - 1);
            page.setOrderBy(orderBystr);
        }
        //查询需要是上架的产品
        storeGoods.setIsShelf(Global.YES);
        Page<StoreGoods> pageStoreGoods = storeGoodsService.findPage(page, storeGoods);
        //改为返回layer格式
        return AjaxJson.layuiTable(pageStoreGoods);
    }

    /**
     * 商品匹配属性搜索
     *
     * @return
     */
    @ApiOperation(notes = "searchByTag", httpMethod = "POST", value = "商品匹配属性搜索(根据筛选出的tag标签进行筛选)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索关键字", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "brand", value = "品牌", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "tagText", value = "标签属性(采用  匹数::1匹,能效等级::3级 这种格式)", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categoryPid", value = "父级分类", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "子集分类", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "cityid", value = "区域id(根据页面定位获取地区id,目的为了匹配供应商工贸所在地区进行筛选)", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping("/searchByTag")
    public AjaxJson searchByTag(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchByTag  调用接口开始*_*_*_*_*_*_*_*_*_*");
        Page<StoreGoods> page = new Page<StoreGoods>(request, response);
        //关键字若不为空
        if (StringUtils.isNotEmpty(storeGoods.getKey())) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchByTag  关键字若不为空*_*_*_*_*_*_*_*_*_*");
            storeGoods.setGoodsName(storeGoods.getKey());
        }
        //属性标签如果不为空
        if (StringUtils.isNotEmpty(storeGoods.getTagText())) {
            String[] args = storeGoods.getTagText().split(",");
            List<GoodsClassProperties> goodsClassPropertiesList = new ArrayList<>();
            //进行属性匹配
            for (int i = 0; i < args.length; i++) {
                String []gcp = args[i].split("::");
                GoodsClassProperties goodsClassProperties = new GoodsClassProperties();
                goodsClassProperties.setName(gcp[0]);
                goodsClassProperties.setValue(gcp[1]);
                goodsClassPropertiesList.add(goodsClassProperties);
            }
            //放入属性匹配值
            storeGoods.setGoodsClassPropertiesList(goodsClassPropertiesList);
        }
        String orderBystr = "";
        ///根据属性排序
        if ("1".equals(storeGoods.getComprehensiveFlag())) {
            orderBystr += "sg.is_recommend desc,";
        }
        if ("1".equals(storeGoods.getGoodsClickFlag())) {
            orderBystr += "sg.goods_click desc,";
        }
        if ("1".equals(storeGoods.getGoodsSaleFlag())) {
            orderBystr += "sg.goods_salenum desc,";
        }
        if ("1".equals(storeGoods.getGoodsCollectFlag())) {
            orderBystr += "sg.goods_collect desc,";
        }
        if ("1".equals(storeGoods.getGoodsCommentFlag())) {
            orderBystr += "sg.goods_comment_num desc,";
        }
        if ("1".equals(storeGoods.getMarketPriceFlag())) {
            orderBystr += "a.market_price desc,";
        }
        if (!"".equals(orderBystr)) {
            orderBystr = orderBystr.substring(0, orderBystr.length() - 1);
            page.setOrderBy(orderBystr);
        }
        //查询需要是上架的产品
        storeGoods.setIsShelf(Global.YES);
        Page<StoreGoods> pageStoreGoods = storeGoodsService.findPageSearchTag(page, storeGoods);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/searchByTag  接口调用成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(pageStoreGoods);
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
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  调用接口开始*_*_*_*_*_*_*_*_*_*");
        //身份验证
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        //参数验证
        if (StringUtils.isEmpty(storeGoodsId)) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  storeGoodsId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //店铺产品查询
        StoreGoods storeGoods = storeGoodsService.get(storeGoodsId);
        if (storeGoods == null) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  此店铺商品不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺商品不存在");
        }
        //店铺
        Store store = storeService.findUniqueByProperty("id",storeGoods.getStoreId());
        Map<String, Object> map = new HashMap<>();
        //查询产品中心数据
        Sign s = new Sign(key, secret);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  linkProductUpperReadService.getProductInfoByCode前*_*_*_*_*_*_*_*_*_*");
        ProductInfo productInfo = linkProductUpperReadService.getProductInfoByCode(storeGoods.getCode(), s).getResult();
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  linkProductUpperReadService.getProductInfoByCode后*_*_*_*_*_*_*_*_*_*");
        Goods goods = goodsService.findByCode(storeGoods.getCode());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  goodsService.findByCode*_*_*_*_*_*_*_*_*_*");
        GoodsExt goodsExt = goodsExtService.findUniqueByProperty("code", storeGoods.getCode());
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  goodsExt*_*_*_*_*_*_*_*_*_*");
        //查看阶梯价格
        if (currentAccount!=null) {
            StoreGoods sgs = storeGoodsService.selectPriceGroup(storeGoods.getStoreId(), currentAccount.getPurchaserId(), storeGoods.getCode());
            if (sgs != null && sgs.getPriceGroup() != null) {
                logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  存在阶梯价格："+ sgs.getPriceGroup() +"*_*_*_*_*_*_*_*_*_*");
                storeGoods.setPrice(sgs.getPriceGroup());
                //计入价格组标识,到时此标记为一时则为优惠价给予前台展示
                storeGoods.setPriceGroupTag("1");
            }
        }
        if (currentAccount != null) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/detail  进行足迹记录*_*_*_*_*_*_*_*_*_*");
            storeGoods.setPurchaserId(currentAccount.getId());
            //进行足记记录
            GoodsBrowse goodsBrowse = new GoodsBrowse();
            //参数check
            goodsBrowse.setPurchaserAccountId(currentAccount.getId());
            goodsBrowse.setGoodsCode(storeGoods.getCode());
            goodsBrowse.setStoreId(storeGoods.getStoreId());
            //若浏览过则更新即可
            List<GoodsBrowse> goodsBrowseList = goodsBrowseService.findList(goodsBrowse);
            //进行保存或者更新操作
            goodsBrowseService.saveOrUpdate(goodsBrowseList, storeGoods, goodsBrowse);
        }
        //查询品牌名称
        Brand b = brandService.findUniqueByProperty("id", goods.getBrand());
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
     * 商城首页中商品分类商品列表
     *
     * @return
     */
    @ApiOperation(notes = "categoryGoods", httpMethod = "GET", value = "商城首页中商品分类商品列表(若不填写参数则返回全部一级分类下产品),此列表中包含分类下广告")
    @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "分类id", required = false, paramType = "query", dataType = "String")})
    @RequestMapping("/categoryGoods")
    public AjaxJson categoryGoods(String categoryId,String cityid) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryGoods  商城首页中商品分类商品列表调用接口开始*_*_*_*_*_*_*_*_*_*");
        List<RecommendCatContent> recommendCatContentList = null;
        //根据产品大类进行产品查找
        try {
            //判断是否为空,若为空则查找全部
            if (StringUtils.isEmpty(categoryId)) {
                categoryId = "";
            }
            recommendCatContentList = recommendCatService.selectRcForApi(categoryId,cityid);
            if (recommendCatContentList == null) {
                logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryGoods  没有查到数据*_*_*_*_*_*_*_*_*_*");
                return AjaxJson.fail("没有查到数据");
            }
        } catch (Exception e) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryGoods  数据异常,原因： "+ e.getMessage() +"*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("数据异常,原因：" + e.getMessage());
        }
        //不需要layer格式
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryGoods  接口调用成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(recommendCatContentList);
    }

    /**
     * 商城首页中热卖专题等商品列表
     *
     * @return
     */
    @ApiOperation(notes = "dictionaryGoods", httpMethod = "POST", value = "商城首页中热卖专题等商品列表,此列表中包含分类下广告", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryId", value = "字典分类id", required = false, paramType = "form", dataType = "String")})
    @RequestMapping("/dictionaryGoods")
    public AjaxJson dictionaryGoods(String dictionaryId,String cityid) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryGoods 商城首页中热卖专题等商品列表调用接口开始*_*_*_*_*_*_*_*_*_*");
        List<RecommendRstContent> recommendRstContentList = null;
        //根据字典大类进行产品查找
        try {
            //判断是否为空,若为空则查找全部
            if (StringUtils.isEmpty(dictionaryId)) {
                dictionaryId = "";
            }
            recommendRstContentList = recommendSpecialtopicService.selectRcForApi(dictionaryId,cityid);
            if (recommendRstContentList == null) {
                return AjaxJson.fail("没有查到数据");
            }
        } catch (Exception e) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryGoods  数据异常,原因： "+ e.getMessage() +"*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("数据异常,原因：" + e.getMessage());
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/categoryGoods  接口调用成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(recommendRstContentList);
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


    /**
     * 获取评价列表
     *
     * @return
     */
    @ApiOperation(notes = "goodEvaluate", httpMethod = "POST", value = "获取商品详情中的评价接口",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品Id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "form", dataType = "int")})
    @RequestMapping("/goodEvaluate")
    public AjaxJson goodEvaluate(String storeGoodsId,HttpServletRequest request, HttpServletResponse response) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 获取评价列表调用接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate storeGoodsId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //店铺产品查询
        StoreGoods storeGoods = storeGoodsService.get(storeGoodsId);
        if (storeGoods == null) {
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 此店铺商品不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺商品不存在");
        }
        GoodsEvaluate goodsEvalute = new GoodsEvaluate();
        goodsEvalute.setGoodsCode(storeGoods.getCode());
        goodsEvalute.setStoreId(storeGoods.getStoreId());
        Page<GoodsEvaluate> pageGoodsEvaluate = goodsEvaluateService.findPage(new Page<GoodsEvaluate>(request, response), goodsEvalute);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 接口调用成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.layuiTable(pageGoodsEvaluate);
    }


    /**
     * 获取店铺信息
     *
     * @return
     */
    @ApiOperation(notes = "getStoreInfo", httpMethod = "GET", value = "获取店铺内容(用于店铺内容信息展示)")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "String")})
    @RequestMapping("/getStoreInfo")
    public AjaxJson getStoreInfo(String storeId) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 获取店铺信息调用接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(storeId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate storeId参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        Store store = storeService.findUniqueByProperty("id",storeId);
        if(store==null){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 此店铺不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺不存在");
        }
        if(currentAccount != null){
            PurchaserStoreFavorites purchaserStoreFavorites = new PurchaserStoreFavorites();
            purchaserStoreFavorites.setPurchaserAccountId(currentAccount.getId());
            purchaserStoreFavorites.setStoreId(storeId);
            List<PurchaserStoreFavorites> purchaserStoreFavoritesList = purchaserStoreFavoritesService.findList(purchaserStoreFavorites);
            if(purchaserStoreFavoritesList!=null && purchaserStoreFavoritesList.size()>0){
                //存在收藏的店铺
                store.setIsCollect("1");
                logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 存在收藏的店铺 store.setIsCollect(\"1\")*_*_*_*_*_*_*_*_*_*");
            }else{
                store.setIsCollect("0");
                logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate  store.setIsCollect(\"0\");*_*_*_*_*_*_*_*_*_*");
            }
        }

        return AjaxJson.ok(store);
    }

    /**
     * 根据storeGoodsId查询商品
     *
     * @return
     */
    @ApiOperation(notes = "getStoreGoodsById", httpMethod = "GET", value = "根据storeGoodsId查询商品")
    @ApiImplicitParams({@ApiImplicitParam(name = "storeGoodsId", value = "店铺商品id", required = true, paramType = "query", dataType = "String")})
    @RequestMapping("/getStoreGoodsById")
    public AjaxJson getStoreGoodsById(String storeGoodsId) {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate getStoreGoodsById接口调用开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(storeGoodsId)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 参数异常,必填参数不能为空*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        //验证身份
        StoreGoods storeGoods = new StoreGoods();
        storeGoods.setId(storeGoodsId);
        List<StoreGoods> storeGoodsList = storeGoodsService.findList(storeGoods);
        if(storeGoodsList!=null && storeGoodsList.size()>0){
            storeGoods = storeGoodsList.get(0);
        }else{
            logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 此店铺商品不存在*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此店铺商品不存在");
        }
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopGoodsController  /api/shop/goodEvaluate 调用接口成功*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.ok(storeGoods);
    }



}
