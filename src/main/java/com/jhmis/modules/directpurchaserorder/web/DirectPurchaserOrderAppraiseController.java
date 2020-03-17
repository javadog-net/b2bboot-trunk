package com.jhmis.modules.directpurchaserorder.web;


import com.alibaba.fastjson.JSONObject;
import com.haier.order.dto.*;
import com.haier.order.utils.OrderServiceUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.StoreGoodsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *后台直采交易订单评价管理
 */
@Controller
@RequestMapping("${adminPath}/shop/directpurchaserorder/appraise")
public class DirectPurchaserOrderAppraiseController extends BaseController {

    @Autowired
    private StoreGoodsService storeGoodsService;



    @RequiresPermissions("shop:directpurchaserorder:appraise:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        System.out.println("888888888888888888888888888888888");
        return "modules/shop/directpurchaserorder/ordersAppraiseList";
    }


    /**
     * 订单列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaserorder:appraise:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(ProductAppraiseDTO qm, HttpServletRequest request, HttpServletResponse response, Model model) {
        if(StringUtils.isNotBlank(qm.getOrderId())){
            qm.getMapCondition().put("orderId",1);
        }
        if(StringUtils.isNotBlank(qm.getCustomerName())){
            qm.getMapCondition().put("customerName",2);
        }
        if(StringUtils.isNotBlank(qm.getProductName())){
            qm.getMapCondition().put("productName",2);
        }

        Page page = new Page(request, response);

        try {
            RetDTO dto = OrderServiceUtils.getProductAppraiseByCondition(qm,page.getPageNo(),page.getPageSize());
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    Pager pager = JSONObject.parseObject(data,Pager.class);
                    if(pager != null && CollectionUtils.isNotEmpty(pager.getResults())){
                        List<JSONObject> productAppraiseList = pager.getResults();
                        List<ProductAppraiseDTO> paList = new ArrayList<ProductAppraiseDTO>();
                        if(CollectionUtils.isNotEmpty(productAppraiseList)){
                            List<String> orderUuids = new ArrayList<String>();
                            for(int i = 0;i < productAppraiseList.size();i ++){
                                JSONObject b = productAppraiseList.get(i);
                                ProductAppraiseDTO p = JSONObject.parseObject(b.toJSONString(),ProductAppraiseDTO.class);
                                paList.add(p);
                                if(StringUtils.isNotBlank(p.getOrderUuid())){
                                    orderUuids.add(p.getOrderUuid());
                                }
                            }
                            if(CollectionUtils.isNotEmpty(orderUuids)){
                                RetDTO orderDto = OrderServiceUtils.getOrderMapByUuids(orderUuids);
                                if(orderDto != null && RetDTO.SUCCESS.equals(orderDto.getRetStatus())) {
                                    String orderListStr = orderDto.getRetData();
                                    if (StringUtils.isNotBlank(orderListStr)) {
                                        Map<String, OrderMainDTO> orderMainDTOMap = JSONObject.parseObject(orderListStr, Map.class);
                                        if(orderMainDTOMap != null){
                                            paList.forEach(p ->{
                                                OrderMainDTO order = orderMainDTOMap.get(p.getOrderUuid());
                                                if(order != null){
                                                    p.setOrderId(order.getOrderId());
                                                }
                                            });
                                        }
                                    }
                                }
                            }

                            page.setPageNo(pager.getNowPage());
                            page.setPageSize(pager.getPageShow());
                            page.setCount(pager.getTotalNum());
                            page.setList(paList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Page<Orders> page = ordersService.findPageList(new Page<Orders>(request, response), orders);
        return getBootstrapData(page);
    }

    /**
     * 查看评价页面
     */
    @RequiresPermissions("shop:directpurchaserorder:appraise:view")
    @RequestMapping(value = "view")
    public String view(String id, Model model) {

        if(StringUtils.isNotEmpty(id)){
            try {
                //通过评价uuid获取商品评价详情
                RetDTO dto = OrderServiceUtils.getProductAppraiseByUuid(id);
                if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                    String data = dto.getRetData();
                    if(StringUtils.isNotBlank(data)){
                        ProductAppraiseDTO productAppraiseDTO = JSONObject.parseObject(data,ProductAppraiseDTO.class);
                        if(productAppraiseDTO != null){
                            model.addAttribute("productAppraiseDTO", productAppraiseDTO);
                            if(StringUtils.isNotBlank(productAppraiseDTO.getOrderUuid())){
                                //获取订单评价信息
                                RetDTO orderAppraiseDTO = OrderServiceUtils.getOrderMainAppraiseByOrderUuid(productAppraiseDTO.getOrderUuid());
                                if(orderAppraiseDTO != null && RetDTO.SUCCESS.equals(orderAppraiseDTO.getRetStatus())){
                                    OrderMainAppraiseDTO orderAppraise = JSONObject.parseObject(orderAppraiseDTO.getRetData(),OrderMainAppraiseDTO.class);
                                    if(orderAppraise != null){
                                        model.addAttribute("orderAppraise", orderAppraise);
                                    }
                                }
                            }
                            if(StringUtils.isNotBlank(productAppraiseDTO.getProductUuid())){
                                StoreGoods storeGoods = new StoreGoods();
                                storeGoods.setId(productAppraiseDTO.getProductUuid());
                                List<StoreGoods> storeGoodsList =  storeGoodsService.findList(storeGoods);
                                storeGoods = storeGoodsList.get(0);
                                model.addAttribute("product", storeGoods);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "modules/shop/directpurchaserorder/ordersAppraiseView";
    }


}
