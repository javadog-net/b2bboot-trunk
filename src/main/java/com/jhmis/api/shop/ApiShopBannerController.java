package com.jhmis.api.shop;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.shop.entity.Banner;
import com.jhmis.modules.shop.service.BannerService;
import com.jhmis.modules.shop.web.BannerController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 所有的api控制器类名命名为API+目录名+模块名+Controller
 * 主要是可能会与后台的控制器重名
 * 订单控制器
 */
@Api(value = "ApiShopFavoritesController", description = "平台横幅banner图")
@RestController
@RequestMapping("/api/shop/banner")
public class ApiShopBannerController {
    protected Logger logger = LoggerFactory.getLogger(ApiShopBannerController.class);
    @Autowired
    BannerService bannerService;

    /**
     * 商城首页中banner
     *
     * @return
     */
    @ApiOperation(notes = "bannerList", httpMethod = "GET", value = "商城首页中banner图")
    @RequestMapping("/bannerList")
    public AjaxJson bannerList() {
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/banner/bannerList    接口开始*_*_*_*_*_*_*_*_*_*");
        Banner banner = new Banner();
        List<Banner> bannerList = bannerService.findList(banner);
        logger.info("*_*_*_*_*_*_*_*_*_* ApiShopFavoritesController   /api/shop/banner/bannerList    接口结束*_*_*_*_*_*_*_*_*_*");
        return AjaxJson.layuiTable(bannerList);
    }
}
