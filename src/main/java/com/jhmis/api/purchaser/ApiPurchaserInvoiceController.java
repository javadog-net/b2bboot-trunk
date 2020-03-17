package com.jhmis.api.purchaser;

import com.alibaba.fastjson.JSON;
import com.haier.order.dto.RetDTO;
import com.haier.order.utils.OrderServiceUtils;
import com.jhmis.common.beanvalidator.BeanValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

@Api(value = "ApiPurchaserInvoiceController", description = "采购商发票管理")
@RestController
@RequestMapping("/api/purchaser/invoice")
public class ApiPurchaserInvoiceController {
    @Autowired
    PurchaserInvoiceService purchaserInvoiceService;
    @ApiOperation(notes = "list", httpMethod = "POST", value = "发票列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, paramType = "form", dataType = "int")
    })
    @RequiresPermissions("purchaser:invoice:list")
    @RequestMapping("/list")
    public AjaxJson list(PurchaserInvoice invoice, HttpServletRequest request, HttpServletResponse response) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if (invoice == null) {
            invoice = new PurchaserInvoice();
        }
        invoice.setPurchaserId(currentAccount.getPurchaserId());
        //分页写法
        Page<PurchaserInvoice> page = purchaserInvoiceService.findPage(new Page<PurchaserInvoice>(request, response), invoice);
        return AjaxJson.layuiTable(page);
    }

    /**
     * 发票信息
     *
     * @return
     */
    @ApiOperation(notes = "info", httpMethod = "GET", value = "发票信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "发票id", required = true, paramType = "query", dataType = "String")
    })
    @RequiresPermissions(value = {"purchaser:invoice:view", "purchaser:invoice:add", "purchaser:invoice:edit"}, logical = Logical.OR)
    @RequestMapping("/info")
    public AjaxJson info(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        PurchaserInvoice invoice = purchaserInvoiceService.get(id);
        if (invoice == null) {
            return AjaxJson.fail("无此发票信息");
        }
        if (!invoice.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        return AjaxJson.ok(invoice);
    }

    /**
     * 发票删除
     *
     * @return
     */
    @ApiOperation(notes = "del", httpMethod = "POST", value = "发票删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "发票id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:invoice:del")
    @RequestMapping("/del")
    public AjaxJson del(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserInvoice invoice = purchaserInvoiceService.get(id);
        if (invoice == null) {
            return AjaxJson.fail("无此发票信息");
        }
        if (!invoice.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        purchaserInvoiceService.delete(invoice);
        return AjaxJson.ok();
    }

    /**
     * 发票删除
     *
     * @return
     */
    @ApiOperation(notes = "batchdel", httpMethod = "POST", value = "发票删除(批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "发票id逗号分隔", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:invoice:del")
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
            PurchaserInvoice invoice = purchaserInvoiceService.get(id);
            if (invoice == null) {
                return AjaxJson.fail("无此发票信息");
            }
            if (!invoice.getPurchaserId().equals(currentAccount.getPurchaserId())) {
                return AjaxJson.fail("非法访问");
            }
            purchaserInvoiceService.delete(invoice);
        }
        return AjaxJson.ok();
    }

    /**
     * 发票保存
     *
     * @return
     */
    @ApiOperation(notes = "save", httpMethod = "POST", value = "发票保存", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "发票id（修改必传，新增为空）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "kind", value = "发票类型(1普通发票，2增值税发票)", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyName", value = "纳税人识别号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "taxCode", value = "纳税人识别号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "regAddr", value = "公司地址", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "regPhone", value = "公司电话", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "regBname", value = "开户银行", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "regBaccount", value = "银行账号", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "recName", value = "收票人姓名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "recMobphone", value = "收票人手机", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "recProvinceId", value = "省code", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "recCityId", value = "市code", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "recDistrictId", value = "区code", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "recAreaInfo", value = "收票人所在地区", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "recDetailAddr", value = "收票人详细地址", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isDefault", value = "默认(1,是，0，否)", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions(value={"purchaser:invoice:add","purchaser:invoice:edit"},logical= Logical.OR)
    @RequestMapping("/save")
    public AjaxJson save(PurchaserInvoice invoice) throws Exception {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        boolean isnew = StringUtils.isBlank(invoice.getId()) ? true : false;
        if (isnew) {
            //检查必填
            if (StringUtils.isBlank(invoice.getCompanyName())) {
                return AjaxJson.fail("公司名称不能为空");
            }
            if (StringUtils.isBlank(invoice.getTaxCode())) {
                return AjaxJson.fail("纳税人识别号不能为空");
            }
            if (StringUtils.isBlank(invoice.getRecName())) {
                return AjaxJson.fail("收票人姓名不能为空");
            }
            if (StringUtils.isBlank(invoice.getRecMobphone())) {
                return AjaxJson.fail("收票人手机不能为空");
            }
            if (StringUtils.isBlank(invoice.getRecAreaInfo())) {
                return AjaxJson.fail("收票人所在地区不能为空");
            }
            if (StringUtils.isBlank(invoice.getRecDetailAddr())) {
                return AjaxJson.fail("收票人详细地址不能为空");
            }
            //增值税发票
            if("2".equals(invoice.getKind())){
                if (StringUtils.isBlank(invoice.getRegAddr())) {
                    return AjaxJson.fail("公司地址不能为空");
                }
                if (StringUtils.isBlank(invoice.getRegPhone())) {
                    return AjaxJson.fail("公司电话不能为空");
                }
                if (StringUtils.isBlank(invoice.getRegBname())) {
                    return AjaxJson.fail("开户银行不能为空");
                }
                if (StringUtils.isBlank(invoice.getRegBaccount())) {
                    return AjaxJson.fail("银行账号不能为空");
                }
            }
            if (StringUtils.isBlank(invoice.getIsDefault())) {
                invoice.setIsDefault("1");
            }
            invoice.setPurchaserId(currentAccount.getPurchaserId());
        } else {
            PurchaserInvoice entity = purchaserInvoiceService.get(invoice.getId());
            if (entity == null) {
                return AjaxJson.fail("无此发票");
            }
            if (!entity.getPurchaserId().equals(currentAccount.getPurchaserId())) {
                return AjaxJson.fail("非法访问");
            }
            MyBeanUtils.copyBeanNotNull2Bean(invoice, entity);
            invoice = entity;
        }

        //参数验证
        BeanValidators.validator(invoice);
        purchaserInvoiceService.save(invoice);
        return AjaxJson.ok();
    }

    /**
     * 设为默认发票
     *
     * @return
     */
    @ApiOperation(notes = "setDefault", httpMethod = "POST", value = "设为默认发票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "发票id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:invoice:edit")
    @RequestMapping("/setDefault")
    public AjaxJson setDefault(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserInvoice invoice = purchaserInvoiceService.get(id);
        if (invoice == null) {
            return AjaxJson.fail("无此发票信息");
        }
        if (!invoice.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        if("1".equals(invoice.getIsDefault())){
            return AjaxJson.fail("已经是默认发票，不需要再次设置");
        }
        invoice.setIsDefault("1");
        purchaserInvoiceService.setDefault(invoice);
        return AjaxJson.ok();
    }

    /**
     * 获取默认发票
     *
     * @return
     */
    @ApiOperation(notes = "getDefault", httpMethod = "GET", value = "获取默认发票")
    @RequiresRoles("purchaser")
    @RequestMapping("/getDefault")
    public AjaxJson getDefault() {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        PurchaserInvoice invoice = purchaserInvoiceService.getDefault(currentAccount.getPurchaserId());
        return AjaxJson.ok(invoice);
    }

    /**
     * 去支付
     */
    @RequestMapping(value = "notify")
    public void notify(HttpServletRequest request,HttpServletResponse response) {
        PrintWriter out = null;
        String params = "";
        try {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            } finally {
                br.close();
            }
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();
            params = sb.toString();
            if(org.apache.commons.lang3.StringUtils.isNotBlank(params)){
                RetDTO dto = OrderServiceUtils.invoiceNotify(params);
                if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                    try {
                        out = response.getWriter();
                        out.print("success");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        out.flush();
                        out.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
