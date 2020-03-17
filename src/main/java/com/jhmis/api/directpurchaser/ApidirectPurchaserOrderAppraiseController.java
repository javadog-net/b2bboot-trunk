package com.jhmis.api.directpurchaser;


import com.alibaba.fastjson.JSONObject;
import com.haier.order.dto.*;
import com.haier.order.utils.OrderServiceUtils;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *直采交易订单评价管理
 */
@Api(value = "ApidirectPurchaserOrderAppraiseController", description = "采购商-订单评论管理")
@RestController
@RequestMapping("/api/directpurchaserorder/appraise")
public class ApidirectPurchaserOrderAppraiseController extends BaseController {

    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private GoodsService goodsService;





    /**
     * 订单列表数据
     */
    @ResponseBody
    @RequestMapping(value = "getOrderAppraiseList")
    public AjaxJson getOrderAppraiseList(ProductAppraiseDTO qm, HttpServletRequest request, HttpServletResponse response) {
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
                        List<ProductAppraiseDTO> productAppraiseList = pager.getResults();
                        List<ProductAppraiseDTO> newproductAppraiseList = new ArrayList<ProductAppraiseDTO>();
                        if(CollectionUtils.isNotEmpty(productAppraiseList)){
                            List<String> orderUuids = new ArrayList<String>();
                            List<String> productSkus = new ArrayList<String>();
                            for(int i =0;i < productAppraiseList.size();i++){
                                ProductAppraiseDTO p = JSONObject.parseObject(JSONObject.toJSONString(productAppraiseList.get(i)),ProductAppraiseDTO.class);
                                if(StringUtils.isNotBlank(p.getOrderUuid())){
                                    orderUuids.add(p.getOrderUuid());
                                }
                                if(StringUtils.isBlank(p.getProductSkuNo())){
                                    productSkus.add(p.getProductSkuNo());
                                }
                                newproductAppraiseList.add(p);
                            }
                            Map<String,Goods> goodsMap = new HashMap<String, Goods>();
                            if(CollectionUtils.isNotEmpty(productSkus)){
                                Goods goods = new Goods();
                                goods.setCodeList(productSkus);
                                List<Goods> goodsList = goodsService.findByCodeList(goods);
                                if(CollectionUtils.isNotEmpty(goodsList)){
                                    for(Goods g : goodsList){
                                        goodsMap.put(g.getCode(),g);
                                    }
                                }
                            }
                            Map<String, OrderMainDTO> orderMainDTOMap = new HashMap<String, OrderMainDTO>();
                            if(CollectionUtils.isNotEmpty(orderUuids)){
                                RetDTO orderDto = OrderServiceUtils.getOrderMapByUuids(orderUuids);
                                if(orderDto != null && RetDTO.SUCCESS.equals(orderDto.getRetStatus())) {
                                    String orderListStr = orderDto.getRetData();
                                    if (StringUtils.isNotBlank(orderListStr)) {
                                        orderMainDTOMap = JSONObject.parseObject(orderListStr, Map.class);
                                    }
                                }
                            }

                            for(ProductAppraiseDTO p : newproductAppraiseList){
                                if(StringUtils.isNotBlank(p.getOrderUuid())){
                                    OrderMainDTO order = orderMainDTOMap.get(p.getOrderUuid());
                                    if(order != null){
                                        p.setOrderId(order.getOrderId());
                                    }
                                }
                                if(StringUtils.isNotBlank(p.getProductSkuNo())){
                                    Goods g = goodsMap.get(p.getProductSkuNo());
                                    if(g != null){
                                        p.setProductName(g.getName());
                                        p.setMainPicUrl(g.getMainPicUrl());
                                    }
                                }
                            }

                            page.setPageNo(pager.getNowPage());
                            page.setPageSize(pager.getPageShow());
                            page.setCount(pager.getTotalPage());
                            page.setList(newproductAppraiseList);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.layuiTable(page);
    }
    
    /**
     * 查看评价页面
     */
    @RequestMapping(value = "view")
    public AjaxJson view(String id) {

        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数错误！");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            //通过评价uuid获取商品评价详情
            RetDTO dto = OrderServiceUtils.getProductAppraiseByUuid(id);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                String data = dto.getRetData();
                if(StringUtils.isNotBlank(data)){
                    ProductAppraiseDTO productAppraiseDTO = JSONObject.parseObject(data,ProductAppraiseDTO.class);
                    if(productAppraiseDTO != null){
                        map.put("productAppraiseDTO", productAppraiseDTO);
                        if(StringUtils.isNotBlank(productAppraiseDTO.getOrderUuid())){
                            //获取订单评价信息
                            RetDTO orderAppraiseDTO = OrderServiceUtils.getOrderMainAppraiseByOrderUuid(productAppraiseDTO.getOrderUuid());
                            if(orderAppraiseDTO != null && RetDTO.SUCCESS.equals(orderAppraiseDTO.getRetStatus())){
                                OrderMainAppraiseDTO orderAppraise = JSONObject.parseObject(orderAppraiseDTO.getRetData(),OrderMainAppraiseDTO.class);
                                if(orderAppraise != null){
                                    map.put("orderAppraise", orderAppraise);
                                }
                            }
                        }
                        if(StringUtils.isNotBlank(productAppraiseDTO.getProductUuid())){
                            StoreGoods storeGoods = new StoreGoods();
                            storeGoods.setId(productAppraiseDTO.getProductUuid());
                            List<StoreGoods> storeGoodsList =  storeGoodsService.findList(storeGoods);
                            storeGoods = storeGoodsList.get(0);
                            map.put("product", storeGoods);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.ok(map);
    }

    /**
     * 编辑评价页
     */
    @ApiOperation(notes = "saveEditList",httpMethod = "POST",value = "编辑评价页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderProductAppraise", value = "商品和订单评价Entity", required = true, paramType = "query",dataType = "String"),
    })
    @RequestMapping("/saveEditList")
    public AjaxJson saveEditList(OrderProductAppraiseDTO orderProductAppraise) throws Exception{
    	//获取订单评论
    	OrderMainAppraiseDTO orderMainAppraiseDTO = orderProductAppraise.getOrderMainAppraiseDTO();
    	//获取商品评论
    	ProductAppraiseDTO productAppraiseDTO = orderProductAppraise.getProductAppraiseDTO();
    	//测试
//    	orderMainAppraiseDTO = new OrderMainAppraiseDTO();
//    	productAppraiseDTO = new ProductAppraiseDTO();
//    	
//    	orderMainAppraiseDTO.setAppraiseTime("2018-09-09");
//    	orderMainAppraiseDTO.setContent("凯哥你真的是,没谁了！");
//    	orderMainAppraiseDTO.setCustomerName("张文凯");
//    	orderMainAppraiseDTO.setOrderMainUuid("bcdf23c2e02f4001b48c6833b3d0a829");
//    	
//    	productAppraiseDTO.setAppContent("凯哥你真的是,没谁了！");
//    	productAppraiseDTO.setAppScore(5);
//    	productAppraiseDTO.setProductName("人肉大包子--张文凯");
    	
    	//保存评价
    	RetDTO retDTO = OrderServiceUtils.saveProductAppraise(productAppraiseDTO);
    	RetDTO retDTO2 = OrderServiceUtils.saveOrderMainAppraise(orderMainAppraiseDTO);
    	
    	if(retDTO != null && retDTO2 != null){
            if(retDTO.getRetStatus().equals(retDTO.SUCCESS) && retDTO2.getRetStatus().equals(retDTO.SUCCESS)){
                return AjaxJson.ok("成功");
            }
        }
    	
    	return AjaxJson.ok("失败");
    }
    
}
