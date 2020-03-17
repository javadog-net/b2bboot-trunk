package com.jhmis.api.purchaser;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserGoodsFavorites;
import com.jhmis.modules.shop.entity.purchaser.PurchaserStoreFavorites;
import com.jhmis.modules.shop.service.purchaser.PurchaserGoodsFavoritesService;
import com.jhmis.modules.shop.service.purchaser.PurchaserStoreFavoritesService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "ApiPurchaserFavoritesController", description = "采购商-收藏管理")
@RestController
@RequestMapping("/api/purchaser/favorites")
public class ApiPurchaserFavoritesController {

    @Autowired
    PurchaserGoodsFavoritesService purchaserGoodsFavoritesService;
    @Autowired
    PurchaserStoreFavoritesService purchaserStoreFavoritesService;

    /**
     * 查询客户商品收藏的内容
     * @return
     */
    @ApiOperation(notes = "favoritesGoodsList", httpMethod = "POST", value = "查询客户商品收藏的内容",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeName", value = "店铺名称", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "categoryPid", value = "商品一级分类", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "商品二级分类", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码(不填默认1)", required = false, paramType = "form",dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示数量(不填默认10)", required = false, paramType = "form",dataType = "int")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesGoodsList")
    public AjaxJson favoritesGoodsList(PurchaserGoodsFavorites purchaserGoodsFavorites, HttpServletRequest request, HttpServletResponse response){
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        purchaserGoodsFavorites.setPurchaserAccountId(currentAccount.getId());
        Page<PurchaserGoodsFavorites> purchaserGoodsFavoritesPage = purchaserGoodsFavoritesService.findPage(new Page<PurchaserGoodsFavorites>(request, response), purchaserGoodsFavorites);
        return AjaxJson.layuiTable(purchaserGoodsFavoritesPage);
    }

    /**
     * 查询客户店铺收藏的内容
     * @return
     */
    @ApiOperation(notes = "favoritesStoreList", httpMethod = "POST", value = "查询客户店铺收藏的内容",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeName", value = "店铺名称", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码(不填默认1)", required = false, paramType = "form",dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示数量(不填默认10)", required = false, paramType = "form",dataType = "int")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesStoreList")
    public AjaxJson favoritesStoreList(PurchaserStoreFavorites purchaserStoreFavorites, HttpServletRequest request, HttpServletResponse response){
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        purchaserStoreFavorites.setPurchaserAccountId(currentAccount.getId());
        Page<PurchaserStoreFavorites> purchaserStoreFavoritesPage = purchaserStoreFavoritesService.findPage(new Page<PurchaserStoreFavorites>(request, response), purchaserStoreFavorites);
        return AjaxJson.layuiTable(purchaserStoreFavoritesPage);
    }

    /**
     * 客户在收藏列表删除商品
     * @return
     */
    @ApiOperation(notes = "favoritesGoodsDel", httpMethod = "GET", value = "客户在收藏列表删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "favoritesGoodsId", value = "收藏id(可批量删除中间用逗号隔开)", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesGoodsDel")
    public AjaxJson favoritesGoodsDel(String favoritesGoodsId){
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isEmpty(favoritesGoodsId)){
            return AjaxJson.fail("必填参数不能为空");
        }
        purchaserGoodsFavoritesService.deleteById(favoritesGoodsId);
        return AjaxJson.ok("取消收藏内容成功");
    }

    /**
     * 客户在收藏列表删除商品
     * @return
     */
    @ApiOperation(notes = "favoritesStoreDel", httpMethod = "GET", value = "客户在收藏列表删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "favoritesStoreId", value = "收藏id(可批量删除中间用逗号隔开)", required = true, paramType = "query",dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/favoritesStoreDel")
    public AjaxJson favoritesStoreDel(String favoritesStoreId){
        //验证身份
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isEmpty(favoritesStoreId)){
            return AjaxJson.fail("必填参数不能为空");
        }
        purchaserStoreFavoritesService.deleteById(favoritesStoreId);
        return AjaxJson.ok("取消收藏内容成功");
    }

}
