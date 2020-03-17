package com.jhmis.api.store;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.Store;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.StoreService;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "ApiStoreSetupController", description = "店铺设置")
@RestController
@RequestMapping("/api/store/setup")
public class ApiStoreSetupController {
    @Autowired
    private DealerAccountService dealerAccountService;
    @Autowired
    private StoreService storeService;
    /**
     * 设置banner
     * @return
     */
    @ApiOperation(notes = "setBanner", httpMethod = "POST", value = "店铺幻灯片图片", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slidePicUrl", value = "店铺幻灯片图片(多张用| 隔开)", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "slideLinkUrl", value = "店铺幻灯片链接(匹配图片也是用|隔开)", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/setBanner")
    public AjaxJson setBanner(String slidePicUrl,String slideLinkUrl){
        if(StringUtils.isEmpty(slidePicUrl)||StringUtils.isEmpty(slideLinkUrl) ){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Store store = storeService.selectByDealerId(currentAccount.getDealerId());
        if(store==null){
            return AjaxJson.fail("此店铺不存在");
        }
        store.setSlidePicUrl(slidePicUrl);
        store.setSlideLinkUrl(slideLinkUrl);
        storeService.updateStore(store);
        return AjaxJson.ok("设置成功");
    }


    /**
     *  店铺banner信息回显
     * @return
     */
    @ApiOperation(notes = "storeBannerInfo", httpMethod = "GET", value = "店铺banner信息回显")
    @RequestMapping("/storeBannerInfo")
    public AjaxJson storeBannerInfo(){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        Store store = storeService.selectByDealerId(currentAccount.getDealerId());
        if(store==null){
            return AjaxJson.fail("此店铺不存在");
        }
        return AjaxJson.ok(store);
    }



}
