package com.jhmis.api.purchaser;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.GoodsBrowse;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.GoodsBrowseService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "ApiPurchaserBrowseController", description = "采购商-足迹管理")
@RestController
@RequestMapping("/api/purchaser/browse")
public class ApiPurchaserBrowseController {

    @Autowired
    GoodsBrowseService goodsBrowseService;

    /**
     * 商品足迹查看（用户浏览记录查看）
     * @return
     */
    @ApiOperation(notes = "browseList", httpMethod = "POST", value = " 用户浏览记录查看",consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsName", value = "商品名称", required = false, paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码(不填默认1)", required = false, paramType = "form",dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示数量(不填默认10)", required = false, paramType = "form",dataType = "int")})

    /*@RequiresRoles("purchaser")*/
    @RequestMapping("/browseList")
    public AjaxJson browseList(GoodsBrowse goodsBrowse, HttpServletRequest request, HttpServletResponse response){
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        goodsBrowse.setPurchaserAccountId(currentAccount.getId());
        Page<GoodsBrowse> page = goodsBrowseService.findPage(new Page<GoodsBrowse>(request, response), goodsBrowse);
        return AjaxJson.layuiTable(page);
    }



    /**
     * 商品足迹删除（删除浏览记录）
     * @return
     */
    @ApiOperation(notes = "browseDel", httpMethod = "GET", value = " 商品足迹删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "browseId", value = "商品足迹id", required = true, paramType = "query",dataType = "String")
    })
   /* @RequiresRoles("purchaser")*/
    @RequestMapping("/browseDel")
    public AjaxJson browseDel(String browseId){
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        //参数check
        if(StringUtils.isEmpty(browseId)){
            return AjaxJson.fail("参数异常,必填参数不能为空");
        }
        goodsBrowseService.deleteById(browseId);
        return AjaxJson.ok("删除浏览记录记录成功");
    }

}
