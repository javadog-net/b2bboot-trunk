package com.jhmis.api.directpurchaser;


import com.alibaba.fastjson.JSONObject;
import com.haier.order.dto.KpInvoiceDTO;
import com.haier.order.dto.OrderMainDTO;
import com.haier.order.dto.RetDTO;
import com.haier.order.utils.OrderServiceUtils;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "ApiDirectPurchaserInvoiceController", description = "采购商-发票管理")
@RestController
@RequestMapping("/api/directpurchaser/invoice")
public class ApiDirectPurchaserInvoiceController extends BaseController {

    @Autowired
    private PurchaserInvoiceService purchaserInvoiceService;


    /**
     * 开具发票
     * @param orderMainUuid
     * @return
     * @throws Exception
     */
    @ApiOperation(notes = "openerInvoice",httpMethod = "POST",value = "开具发票")
    @RequestMapping(value = "/openerInvoice")
    public AjaxJson openerInvoice(String orderMainUuid) throws Exception {
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isEmpty(account.getId())|| !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        if(StringUtils.isEmpty(orderMainUuid)){
            return AjaxJson.fail("参数不能为空！");
        }
        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.getOrderViewModelByUuid(orderMainUuid);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    OrderMainDTO orderMainDTO = JSONObject.parseObject(data,OrderMainDTO.class);
                    KpInvoiceDTO kpInvoiceDTO = new KpInvoiceDTO();

                    if(StringUtils.isNotBlank(orderMainDTO.getCustomerAddInvoiceUuid())){
                        //获取发票信息
                        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
                        purchaserInvoice.setPurchaserId(account.getPurchaserId());
                        purchaserInvoice.setId(orderMainDTO.getCustomerAddInvoiceUuid());
                        List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
                        if(CollectionUtils.isNotEmpty(purchaserInvoiceList)){
                            purchaserInvoice = purchaserInvoiceList.get(0);
                            if(purchaserInvoice != null){
                                BeanUtils.copyProperties(purchaserInvoice, kpInvoiceDTO);
                                orderMainDTO.setPurchaserInvoice(purchaserInvoice);
                                if("2".equals(purchaserInvoice.getKind())){
                                    orderMainDTO.setInvoiceTypeName("增值税发票");
                                }else if("3".equals(purchaserInvoice.getKind())){
                                    orderMainDTO.setInvoiceTypeName("电子发票");
                                }else if("1".equals(purchaserInvoice.getKind())){
                                    orderMainDTO.setInvoiceTypeName("普通发票");
                                }
                            }
                        }
                    }
                    if(kpInvoiceDTO != null){
                        kpInvoiceDTO.setOrderId(orderMainDTO.getOrderId());
                        RetDTO retDTO = OrderServiceUtils.openerInvoice(kpInvoiceDTO);
                        if(retDTO != null && RetDTO.SUCCESS.equals(retDTO.getRetStatus())){
                            return AjaxJson.ok(retDTO.getRetData());
                        }else {
                            return AjaxJson.fail(retDTO.getRetMessage());
                        }

                    }else {
                        return AjaxJson.fail("发票信息有误");
                    }



                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxJson.fail("开票失败");
        }

        return AjaxJson.fail("开票失败");
    }

    /**
     * 发票冲红
     * @param orderId       订单编号
     * @param note          冲红原因
     * @return
     * @throws Exception
     */
    @ApiOperation(notes = "chInvoice",httpMethod = "POST",value = "发票冲红")
    @RequestMapping(value = "/chInvoice")
    public AjaxJson chInvoice(String orderId,String note) throws Exception {
        if(StringUtils.isBlank(orderId)){
            return AjaxJson.fail("参数错误");
        }

        try{
            RetDTO dto = OrderServiceUtils.chInvoice(orderId, note);
            if(dto != null){
                if(RetDTO.SUCCESS.equals(dto.getRetStatus())){
                    return AjaxJson.ok("冲红成功");
                }else if(RetDTO.ERROR4CUSTOMER.equals(dto.getRetStatus())){
                    return AjaxJson.fail(dto.getRetMessage());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail("冲红失败");
        }

        return AjaxJson.ok("冲红成功");
    }

    /**
     * 查询开票信息结果
     * @param orderId
     * @return
     * @throws Exception
     */
    @ApiOperation(notes = "queryInvoice",httpMethod = "POST",value = "查询开票信息结果")
    @RequestMapping(value = "/queryInvoice")
    public AjaxJson queryInvoice(String orderId) throws Exception {
        if(StringUtils.isBlank(orderId)){
            return AjaxJson.fail("参数错误");
        }

        try{
            RetDTO dto = OrderServiceUtils.queryInvoice(orderId);
            if(dto != null){
                if(RetDTO.SUCCESS.equals(dto.getRetStatus())){
                    String url = dto.getRetData();
                    return AjaxJson.ok(url);
                }else {
                    return AjaxJson.fail(dto.getRetMessage());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail("查询失败");
        }

        return AjaxJson.ok("查询成功");
    }














}
