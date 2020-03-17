package com.jhmis.api.directpurchaser;

import com.haier.link.upper.dto.ProductInfo;
import com.haier.link.upper.model.Sign;
import com.haier.link.upper.service.LinkProductUpperReadService;
import com.haier.oms.client.model.qiyegou.QiYeGouQueryStockInfo;
import com.haier.util.OMS_Query;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.directpurchaser.service.DirectGoodsService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserService;
import com.jhmis.modules.purchasermainrel.entity.PurchaserMasterSlaveRel;
import com.jhmis.modules.purchasermainrel.service.PurchaserMasterSlaveRelService;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.*;
import com.jhmis.modules.shop.service.purchaser.PurchaserFavoritesService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "DirectPurchaserGoodsController", description = "采购商-直采商品")
@RestController
@RequestMapping("/api/directpurchaser/goods")
public class ApiDirectPurchaserGoodsController extends BaseController{
	 protected Logger logger = LoggerFactory.getLogger(ApiDirectPurchaserGoodsController.class);
    @Autowired
    private DirectPurchaserService directPurchaserService;
    @Autowired  
    private PurchaserMasterSlaveRelService purchaserMasterSlaveRelService;
    @Autowired  
    private DirectGoodsService  directGoodsService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private MdmCustomersPartnerService mdmCustomersPartnerService;
    @Autowired
    private BrandService brandService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private PurchaserFavoritesService purchaserFavoritesService;

    @Autowired
    private GoodsBrowseService goodsBrowseService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Lazy
    @Autowired
    private LinkProductUpperReadService linkProductUpperReadService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    private MdmCustomersSourceService mdmCustomersSourceService;
    @Autowired
    GoodsExtService goodsExtService;
    long key = Constants.PRODUCTS_CENTER_KEY;
    String secret = Constants.PRODUCTS_CENTER_SECRET;

    
    /**
     * 获取可采商品列表
     * @param
     * @return
     */
    /**
     * 可采商品列表页
     */
    @ApiOperation(notes = "getGoodsList",httpMethod = "POST",value = "可采商品列表页")
    @RequestMapping(value = "getGoodsList")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "商品名称", required = false, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "categoryPid", value = "一级分类", required = false, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "categoryId", value = "二级分类", required = false, paramType = "form", dataType = "String"),
        @ApiImplicitParam(name = "customersPartnerID", value = "送达方88码", required = false, paramType = "form", dataType = "String")
    })
    public  AjaxJson getGoodsList(Goods goods,HttpServletRequest request, HttpServletResponse response){
    	
    	try {
			
		
    	PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        String purchaserId=currentAccount.getPurchaserId();
        Purchaser purchaser=directPurchaserService.get(purchaserId);
    	if(currentAccount == null || StringUtils.isBlank(currentAccount.getId())){
          	AjaxJson.fail("直采商未登陆");
        }
    	String customersPartnerID = goods.getCustomersPartnerID();
    	logger.info("customersPartnerID============="+customersPartnerID);
    	if(StringUtils.isEmpty(customersPartnerID)){
    		//获取默认默认的送达方
    		//获取送达方数据
    		List<MdmCustomersPartner> shList=null;
    		if(purchaser!=null){
                if(StringUtils.isNotBlank(purchaser.getLoginNum())){
                    MdmCustomersPartner m = new MdmCustomersPartner();
                    m.setCustomerNumber(purchaser.getLoginNum());
                    List<MdmCustomersPartner> mdmCustomersPartnerList = mdmCustomersPartnerService.findList(m);
                    if(mdmCustomersPartnerList!=null && mdmCustomersPartnerList.size()>0){
                        //SH送达方
                        shList = new ArrayList<>();
                        //循环将四方关系
                        for(MdmCustomersPartner mcp:mdmCustomersPartnerList){
                            //SH为送达方 PY付款方 SP售达方 BP开票方)
                            if("SH".equals(mcp.getCustPartnerType())){
                                shList.add(mcp);
                            }
                            logger.info("shList============="+shList.size());
                        }
                        //当没有选择送达方时默认选择第一个送达方库存数量
                        if(shList!=null && shList.size()>0){
                            MdmCustomersPartner mp=shList.get(0);
                            if(mp!=null){
                                customersPartnerID=mp.getCustPartnerSubjectId();
                                goods.setCustomersPartnerID(customersPartnerID);
                                logger.info("shList======customersPartnerID======="+customersPartnerID);
                            }
                        }
                    }
                }
    		}
    	}
    	
        if("0".equals(purchaser.getIsAdmin())){
        	PurchaserMasterSlaveRel rel=purchaserMasterSlaveRelService.getMasterPurchaser(purchaserId);
        	purchaserId=rel.getPurchaserMasterId();
        }
        goods.setPurchaserId(purchaserId);
        logger.info("purchaserId======="+purchaserId);
        goods.setState("1");
        Page<Goods> page = directGoodsService.purchaserGoodsRelDate(new Page<Goods>(request, response), goods);
        final String customersPartnerIDf = customersPartnerID;
        page.getList().forEach(g -> g.setCustomersPartnerID(customersPartnerIDf));
        return AjaxJson.ok(page);
    	} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    	 return AjaxJson.fail("接口异常");
    }
    
    
    /**
     * 获取商品详情
     * @param
     * @return
     */
    /**
     * 可采商品详情
     */
    @ApiOperation(notes = "getGoodsInfo",httpMethod = "POST",value = "获取商品详情")
    @RequestMapping(value = "getGoodsInfo")
    @ApiImplicitParam(name = "code", value = "商品编号", required = false, paramType = "form", dataType = "String")
    public  AjaxJson getGoodsInfo(String code,HttpServletRequest request, HttpServletResponse response){
    	if(StringUtils.isNotBlank(code)){
    		Goods goods=directGoodsService.findByCode(code);
    		return AjaxJson.ok(goods);
    	}else{
    		return AjaxJson.fail("商品id为空");
    	}
    }
    
    
    /**
     * 获取父类商品分类
     * @param
     * @return
     */
    /**
     * 可采商品详情
     */
    @ApiOperation(notes = "getCategoryTree",httpMethod = "POST",value = "获取父类商品分类")
    @RequestMapping(value = "getCategoryTree")
    public  AjaxJson getCategoryTree(HttpServletRequest request, HttpServletResponse response){
    		GoodsCategory  gc=new GoodsCategory();
    		List<CategoryNode> cn=goodsCategoryService.findTree(gc);
    		return AjaxJson.ok(cn);
    }


    /**
     * 商品详情
     *
     * @return
     */
    @ApiOperation(notes = "detail", httpMethod = "GET", value = "商品详情(返回一个map包含产品中心productInfo,店铺商品sg,及商品基本信息g、goodsExt*******!!!!注意此接口需要调用产品中心，会有可能调用不成功)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeGoodsId", value = "店铺商品id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "customersPartnerID", value = "送达方88码", required = false, paramType = "query", dataType = "String"),
    })
    @RequestMapping("/detail")
    public AjaxJson detail(String storeGoodsId,String customersPartnerID) {
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

        if(!customersPartnerID.equals("undefined")){
            /**
             * 获取独享价
             */
            if(null != goods && currentAccount != null && org.apache.commons.lang3.StringUtils.isNotBlank(currentAccount.getPurchaserId())){
                goods.setPurchaserId(currentAccount.getPurchaserId());
                Goods g = directGoodsService.findPurchaserRelByGoods(goods);
                if(null != g && null != g.getExclusivePrice()){
                    goods.setExclusivePrice(g.getExclusivePrice());
                }
            }

            /**
             * 获取库存数量
             */
            if(StringUtils.isEmpty(customersPartnerID)){
                //获取默认默认的送达方
                //获取送达方数据
                List<MdmCustomersPartner> shList=null;
                Purchaser purchaser = directPurchaserService.get(currentAccount.getPurchaserId());
                if(purchaser != null){
                    if(StringUtils.isNotBlank(purchaser.getLoginNum())){
                        MdmCustomersPartner m = new MdmCustomersPartner();
                        m.setCustomerNumber(purchaser.getLoginNum());
                        List<MdmCustomersPartner> mdmCustomersPartnerList = mdmCustomersPartnerService.findList(m);
                        if(mdmCustomersPartnerList!=null && mdmCustomersPartnerList.size()>0){
                            //SH送达方
                            shList = new ArrayList<>();
                            //循环将四方关系
                            for(MdmCustomersPartner mcp:mdmCustomersPartnerList){
                                //SH为送达方 PY付款方 SP售达方 BP开票方)
                                if("SH".equals(mcp.getCustPartnerType())){
                                    shList.add(mcp);
                                }
                                logger.info("shList============="+shList.size());
                            }
                            //当没有选择送达方时默认选择第一个送达方库存数量
                            if(shList!=null && shList.size()>0){
                                MdmCustomersPartner mp=shList.get(0);
                                if(mp!=null){
                                    customersPartnerID = mp.getCustPartnerSubjectId();
                                    goods.setCustomersPartnerID(customersPartnerID);
                                    logger.info("shList======customersPartnerID======="+customersPartnerID);
                                }
                            }
                        }
                    }
                }
            }
            String salesFactory ="";
            String mainHouse="";
            MdmCustomersSource  mdms=new MdmCustomersSource ();
            mdms.setCusCode(goods.getCustomersPartnerID());
            List<MdmCustomersSource>  ms= mdmCustomersSourceService.findList(mdms);
            logger.info("ms============"+ms);
            if(ms!=null && ms.size()>0){
                MdmCustomersSource m=ms.get(0);
                logger.info("sssssssssss"+m);
                if(m!=null){
//        		销售工厂
                    salesFactory=m.getComCode();
                    //工贸
                    mainHouse=m.getOrgId();
                }
            }
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(salesFactory)){
                Map mapret= OMS_Query.query(salesFactory, mainHouse, goods.getCode());
                logger.info("mapret=============="+mapret);
                if(mapret!=null){
                    if("SUCCESS".equals(mapret.get("result"))){
                        List<QiYeGouQueryStockInfo> stockList=(List)mapret.get("detail");
                        if(stockList!=null && stockList.size()>0){
                            QiYeGouQueryStockInfo si=stockList.get(0);
                            if(si!=null){
                                goods.setStock(si.getNum());
                            }
                        }
                    }
                }
            }else {
                goods.setStock(0);
            }
        }
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
    

}
