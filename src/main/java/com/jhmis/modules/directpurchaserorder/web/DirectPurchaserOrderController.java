package com.jhmis.modules.directpurchaserorder.web;


import com.alibaba.fastjson.JSONObject;
import com.haier.order.dto.*;
import com.haier.order.utils.OrderServiceUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.OrderGoodsService;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.jhmis.modules.sys.entity.DictValue;
import com.jhmis.modules.sys.utils.DictUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *后台直采交易订单管理
 */
@Controller
@RequestMapping("${adminPath}/shop/directpurchaserorder/orders")
public class DirectPurchaserOrderController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(DirectPurchaserOrderController.class);
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private PurchaserInvoiceService purchaserInvoiceService;

    @RequiresPermissions("shop:directpurchaserorder:orders:list")
    @RequestMapping(value = {"list", ""})
    public String list(HttpServletRequest request, HttpServletResponse response,Model model) {
        System.out.println("888888888888888888888888888888888");

        List<DictValue> dictValues = DictUtils.getDictList("order_state");
        model.addAttribute("dictValues",dictValues);
        request.setAttribute("dictValues",dictValues);
        return "modules/shop/directpurchaserorder/ordersList";
    }


    /**
     * 订单列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaserorder:orders:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(OrderMainDTO qm, HttpServletRequest request, HttpServletResponse response, Model model) {
        if(StringUtils.isNotBlank(qm.getOrderId())){
            qm.getMapCondition().put("orderId",1);
        }
        if(StringUtils.isNotBlank(qm.getCustomerName())){
            qm.getMapCondition().put("customerName",2);
        }
        if(qm.getOrderStateh() != null){
            qm.setOrderState(getOrderStateByHaier(qm.getOrderStateh()));
            if(qm.getOrderStateh() == 20){
                qm.getMapCondition().put("orderState",8);
            }else {
                qm.getMapCondition().put("orderState",1);
            }
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime())){
            qm.getMapCondition().put("createOpeTime",5);
        }
        if(StringUtils.isNotBlank(qm.getCreateOpeTime2())){
            qm.getMapCondition().put("createOpeTime2",6);
        }

        Page<OrderMainDTO> page = new Page<OrderMainDTO>(request, response);

        try {
            RetDTO dto = OrderServiceUtils.getListGetByCondition(qm,page.getPageNo(),page.getPageSize());
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    Pager pager = JSONObject.parseObject(data,Pager.class);
                    if(pager != null && CollectionUtils.isNotEmpty(pager.getResults())){
                        List<OrderMainDTO> orderList = JSONObject.parseArray(JSONObject.toJSONString(pager.getResults()),OrderMainDTO.class);
                        if(CollectionUtils.isNotEmpty(orderList)){
                            orderList.forEach(m ->{
                                if(StringUtils.isNotBlank(m.getOrderState())){
                                    m.setOrderStateh(setOrderState(m.getOrderState()));
                                }
                            });

                            page.setPageNo(pager.getNowPage());
                            page.setPageSize(pager.getPageShow());
                            page.setCount(pager.getTotalNum());
                            page.setList(orderList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBootstrapData(page);
    }



    //订单状态state：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
    public String getOrderStateByHaier(Integer state){
        //orderState 1等待买家付款  2等待卖家发货  3卖家备货中  4等待买家确认收货  5交易成功  6交易关闭
        String orderState = "";
        if(state == 10){
            orderState = OrderParamModel.ORDER_STATE_WAIT_PAY;
        }else if(state == 20){
            orderState = OrderParamModel.ORDER_STATE_WAIT_DELIVER+","+OrderParamModel.ORDER_STATE_PREPARE_DELIVER;
        }else if(state == 30){
            orderState = OrderParamModel.ORDER_STATE_WAIT_CONFIRM;
        }else if(state == 40){
            orderState = OrderParamModel.ORDER_STATE_SUCCESS;
        }else if(state == 0){
            orderState = OrderParamModel.ORDER_STATE_CLOSE;
        }
        return orderState;
    }

    //state 1等待买家付款  2等待卖家发货  3卖家备货中  4等待买家确认收货  5交易成功  6交易关闭
    public Integer setOrderState(String state){
        //订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
        Integer orderState = 10;
        //1等待买家付款
        if(OrderParamModel.ORDER_STATE_WAIT_PAY.equals(state)){
            //10(默认):未付款
            orderState = 10;
            //2等待卖家发货 3卖家备货中
        }else if(OrderParamModel.ORDER_STATE_WAIT_DELIVER.equals(state)
                || OrderParamModel.ORDER_STATE_PREPARE_DELIVER.equals(state)){
            //20:已付款;
            orderState = 20;
            //4等待买家确认收货
        }else if(OrderParamModel.ORDER_STATE_WAIT_CONFIRM.equals(state)){
            //30:已发货;
            orderState = 30;
            //5交易成功
        }else if(OrderParamModel.ORDER_STATE_SUCCESS.equals(state)){
            //40:已收货;
            orderState = 40;
            //6交易关闭
        }else if(OrderParamModel.ORDER_STATE_CLOSE.equals(state)){
            //0(已取消)
            orderState = 0;
        }

        return orderState;
    }
    
    
    
    


    /**
     * 查看，增加，编辑订单表单页面
     */
    @RequiresPermissions("shop:directpurchaserorder:orders:view")
    @RequestMapping(value = "view")
    public String view(String uuid, Model model) {

        if(StringUtils.isNotEmpty(uuid)){
            try {
                RetDTO dto = OrderServiceUtils.getOrderViewModelByUuid(uuid);
                if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                    String data = dto.getRetData();
                    if(StringUtils.isNotBlank(data)){
                        OrderMainDTO orderMainDTO = JSONObject.parseObject(data,OrderMainDTO.class);
                        if(StringUtils.isNotBlank(orderMainDTO.getCustomerAddInvoiceUuid())){
                            //获取发票信息
                            PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
                            purchaserInvoice.setPurchaserId(orderMainDTO.getCustomerUuid());
                            purchaserInvoice.setId(orderMainDTO.getCustomerAddInvoiceUuid());
                            List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
                            if(CollectionUtils.isNotEmpty(purchaserInvoiceList)){
                                purchaserInvoice = purchaserInvoiceList.get(0);
                                if(purchaserInvoice != null){
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
                        //获取物流信息
                        List<CloudWarehouseDTO> cloudWarehouseDTOList = orderMainDTO.getCloudWarehouseDTOList();
                        if(CollectionUtils.isNotEmpty(cloudWarehouseDTOList)){
                            CloudWarehouseDTO warehouseDTO = cloudWarehouseDTOList.get(0);
                            if(warehouseDTO != null){
                                orderMainDTO.setWarehouseCode(warehouseDTO.getExpno());
                            }

                        }
                        model.addAttribute("order",orderMainDTO);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "modules/shop/directpurchaserorder/ordersView";
    }

    /**
     * 保存订单
     */
    @RequiresPermissions(value={"shop:directpurchaserorder:orders:edit"})
    @RequestMapping(value = "save")
    public String save(OrderMainDTO order, Model model, RedirectAttributes redirectAttributes) throws Exception{
        if (!beanValidator(model, order)){
            return view(order.getUuid(), model);
        }
        //新增或编辑表单保存
//        ordersService.save(orders);//保存
        RetDTO dto = OrderServiceUtils.updateOrderMoney(order.getUuid(),order.getPayPrice()+"","");
        addMessage(redirectAttributes, "保存订单成功");
        return "redirect:"+ Global.getAdminPath()+"/shop/directpurchaserorder/orders/?repage";
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaserorder:orders:export")
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public AjaxJson exportFile(Orders orders, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "订单"+ DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Orders> page = null;//ordersService.findPage(new Page<Orders>(request, response, -1), orders);
            new ExportExcel("订单", Orders.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出订单记录失败！失败信息："+e.getMessage());
        }
        return j;
    }
    
    
    /**
     * 订单推送到LES
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaserorder:orders:list")
    @RequestMapping(value = "toLES")
    public AjaxJson toLES(String uuid, RedirectAttributes redirectAttributes) {
    	if(StringUtils.isEmpty(uuid)){
            return AjaxJson.fail("参数不能为空！");
        }
        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.toLES(uuid);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                Map data = JSONObject.parseObject(dto.getRetData(), Map.class) ;
               log.info("data================"+data);
                if(data!=null){
                	if("SUCCESS".equals(data.get("result"))){
                		 return AjaxJson.ok("推送成功！"); 
                	}else{
                		return AjaxJson.fail("推送失败！");
                	}

                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return AjaxJson.fail("推送失败！");
    }
    
    
    
    
    /**
     * 推送到GVS
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaserorder:orders:list")
    @RequestMapping(value = "toGVS")
    public AjaxJson toGVS(String uuid, RedirectAttributes redirectAttributes) {
    	if(StringUtils.isEmpty(uuid)){
            return AjaxJson.fail("参数不能为空！");
        }
        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.toGVS(uuid);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                Map data = JSONObject.parseObject(dto.getRetData(), Map.class) ;
               log.info("data================"+data);
                if(data!=null){
                	if("SUCCESS".equals(data.get("result"))){
                		 return AjaxJson.ok("推送成功！"); 
                	}else{
                		return AjaxJson.fail("推送失败！");
                	}

                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return AjaxJson.fail("推送失败！");
    }


    /**
     * 订单审核
     * @param uuid                       订单uuid
     * @param checkedStatus              审核状态   0未审核   1审核通过    2审核不通过
     * @return
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaserorder:orders:list")
    @RequestMapping(value = "checkedOrder")
    public AjaxJson checkedOrder(String uuid, String checkedStatus) {
        if(StringUtils.isEmpty(uuid) || StringUtils.isBlank(checkedStatus)){
            return AjaxJson.fail("参数不能为空！");
        }
        try {
            KpInvoiceDTO kpInvoiceDTO = new KpInvoiceDTO();
            RetDTO o = OrderServiceUtils.getOrderViewModelByUuid(uuid);
            if(o != null && RetDTO.SUCCESS.equals(o.getRetStatus())){
                String data = o.getRetData();
                if(StringUtils.isNotBlank(data)){
                    OrderMainDTO orderMainDTO = JSONObject.parseObject(data,OrderMainDTO.class);
                    if(StringUtils.isNotBlank(orderMainDTO.getCustomerAddInvoiceUuid())){
                        //获取发票信息
                        PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
                        purchaserInvoice.setPurchaserId(orderMainDTO.getCustomerUuid());
                        purchaserInvoice.setId(orderMainDTO.getCustomerAddInvoiceUuid());
                        List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
                        if(CollectionUtils.isNotEmpty(purchaserInvoiceList)){
                            purchaserInvoice = purchaserInvoiceList.get(0);
                            if(purchaserInvoice != null){
                                if("3".equals(purchaserInvoice.getKind())){
                                    kpInvoiceDTO.setCompanyName(purchaserInvoice.getCompanyName());
                                    kpInvoiceDTO.setTaxCode(purchaserInvoice.getTaxCode());
                                    kpInvoiceDTO.setRegAddr(purchaserInvoice.getRegAddr());
                                    kpInvoiceDTO.setRegBname(purchaserInvoice.getRegBname());
                                    kpInvoiceDTO.setOrderId(orderMainDTO.getOrderId());
                                }
                            }
                        }
                    }
                }
            }
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.checkedOrder(uuid,checkedStatus,kpInvoiceDTO);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                if("1".equals(checkedStatus)){
                    return AjaxJson.ok("审核订单通过成功");
                }else {
                    return AjaxJson.ok("审核订单不通过成功");
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return AjaxJson.fail("审核失败！");
    }


}
