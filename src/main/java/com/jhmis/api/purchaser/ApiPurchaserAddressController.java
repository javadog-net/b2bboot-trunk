package com.jhmis.api.purchaser;

import com.jhmis.common.beanvalidator.BeanValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.service.purchaser.PurchaserAddressService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "ApiPurchaserAddressController", description = "采购商地址管理")
@RestController
@RequestMapping("/api/purchaser/address")
public class ApiPurchaserAddressController {
    @Autowired
    PurchaserAddressService purchaserAddressService;
    @ApiOperation(notes = "list", httpMethod = "POST", value = "地址列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, paramType = "form", dataType = "int")
    })
    @RequiresPermissions("purchaser:address:list")
    @RequestMapping("/list")
    public AjaxJson list(PurchaserAddress address, HttpServletRequest request, HttpServletResponse response) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if (address == null) {
            address = new PurchaserAddress();
        }
        address.setPurchaserId(currentAccount.getPurchaserId());
        //分页写法
        Page<PurchaserAddress> page = purchaserAddressService.findPage(new Page<PurchaserAddress>(request, response), address);
        return AjaxJson.layuiTable(page);
    }

    /**
     * 地址信息
     *
     * @return
     */
    @ApiOperation(notes = "info", httpMethod = "GET", value = "地址信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", required = true, paramType = "query", dataType = "String")
    })
    @RequiresPermissions(value = {"purchaser:address:view", "purchaser:address:add", "purchaser:address:edit"}, logical = Logical.OR)
    @RequestMapping("/info")
    public AjaxJson info(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserAddress address = purchaserAddressService.get(id);
        if (address == null) {
            return AjaxJson.fail("无此地址信息");
        }
        if (!address.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        return AjaxJson.ok(address);
    }

    /**
     * 地址保存
     *
     * @return
     */
    @ApiOperation(notes = "save", httpMethod = "POST", value = "地址保存", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id（修改必传，新增为空）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "trueName", value = "真实姓名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "provinceId", value = "省code", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "市code", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "districtId", value = "区code", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "areaInfo", value = "所在地区", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "detailAddress", value = "详细地址", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "telPhone", value = "电话", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobilePhone", value = "手机", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "zipcode", value = "邮编", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "备注", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isDefault", value = "默认(1,是，0，否)", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions(value={"purchaser:address:add","purchaser:address:edit"},logical= Logical.OR)
    @RequestMapping("/save")
    public AjaxJson save(PurchaserAddress address) throws Exception {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        boolean isnew = StringUtils.isBlank(address.getId()) ? true : false;
        if (isnew) {
            //检查必填
            if (StringUtils.isBlank(address.getTrueName())) {
                return AjaxJson.fail("姓名不能为空");
            }
            if (StringUtils.isBlank(address.getAreaInfo())) {
                return AjaxJson.fail("所在地区不能为空");
            }
            if (StringUtils.isBlank(address.getDetailAddress())) {
                return AjaxJson.fail("详细地址不能为空");
            }
            if (StringUtils.isBlank(address.getTelPhone()) && StringUtils.isBlank(address.getMobilePhone())) {
                return AjaxJson.fail("手机或者电话必填一个");
            }
            if (StringUtils.isBlank(address.getIsDefault())) {
                address.setIsDefault("1");
            }
            address.setPurchaserId(currentAccount.getPurchaserId());
        } else {
            PurchaserAddress entity = purchaserAddressService.get(address.getId());
            if (entity == null) {
                return AjaxJson.fail("无此地址");
            }
            if (!entity.getPurchaserId().equals(currentAccount.getPurchaserId())) {
                return AjaxJson.fail("非法访问");
            }
            MyBeanUtils.copyBeanNotNull2Bean(address, entity);
            address = entity;
        }

        //参数验证
        BeanValidators.validator(address);
        purchaserAddressService.save(address);
        return AjaxJson.ok();
    }

    /**
     * 地址删除
     *
     * @return
     */
    @ApiOperation(notes = "del", httpMethod = "POST", value = "地址删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:address:del")
    @RequestMapping("/del")
    public AjaxJson del(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserAddress address = purchaserAddressService.get(id);
        if (address == null) {
            return AjaxJson.fail("无此地址信息");
        }
        if (!address.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        purchaserAddressService.delete(address);
        return AjaxJson.ok();
    }

    /**
     * 地址删除
     *
     * @return
     */
    @ApiOperation(notes = "batchdel", httpMethod = "POST", value = "地址删除(批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "地址id逗号分隔", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:address:del")
    @RequestMapping("/batchdel")
    public AjaxJson batchdel(String ids) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(ids)){
            return AjaxJson.fail("参数为空");
        }
        String idArray[] =ids.split(",");
        for(String id : idArray) {
            PurchaserAddress address = purchaserAddressService.get(id);
            if (address == null) {
                return AjaxJson.fail("无此地址信息");
            }
            if (!address.getPurchaserId().equals(currentAccount.getPurchaserId())) {
                return AjaxJson.fail("非法访问");
            }
            purchaserAddressService.delete(address);
        }
        return AjaxJson.ok();
    }

    /**
     * 设为默认地址
     *
     * @return
     */
    @ApiOperation(notes = "setDefault", httpMethod = "POST", value = "设为默认地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:address:edit")
    @RequestMapping("/setDefault")
    public AjaxJson setDefault(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserAddress address = purchaserAddressService.get(id);
        if (address == null) {
            return AjaxJson.fail("无此地址信息");
        }
        if (!address.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        if("1".equals(address.getIsDefault())){
            return AjaxJson.fail("已经是默认地址，不需要再次设置");
        }
        address.setIsDefault("1");
        purchaserAddressService.setDefault(address);
        return AjaxJson.ok();
    }

    /**
     * 获取默认地址
     *
     * @return
     */
    @ApiOperation(notes = "getDefault", httpMethod = "GET", value = "获取默认地址")
    @RequiresRoles("purchaser")
    @RequestMapping("/getDefault")
    public AjaxJson getDefault() {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        PurchaserAddress address = purchaserAddressService.getDefault(currentAccount.getPurchaserId());
        return AjaxJson.ok(address);
    }
}
