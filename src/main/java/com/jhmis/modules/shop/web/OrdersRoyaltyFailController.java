package com.jhmis.modules.shop.web;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.mapper.JsonMapper;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.service.OrdersService;
import com.jhmis.payment.kjt.KjtResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/shop/ordersRoyalty")
public class OrdersRoyaltyFailController extends BaseController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 转账失败列表
     * @param orders
     * @return
     */
    @RequiresPermissions("shop:orders:royaltyFail")
    @RequestMapping(value = {"list", ""})
    public String list(Orders orders){
        return "modules/shop/ordersRoyaltyList";
    }
    /**
     * 转账失败列表列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:orders:royaltyFail")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Orders orders, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Orders> page = ordersService.findRoyaltyPageList(new Page<Orders>(request, response), orders);
        return getBootstrapData(page);
    }

    /**
     * 转账
     * @param orders
     * @return
     */
    @ResponseBody
    @RequiresPermissions("shop:orders:royaltyOrder")
    @RequestMapping(value = "royaltyOrder")
    public AjaxJson royaltyOrder(Orders orders, HttpServletRequest request, HttpServletResponse response){
        if(orders == null || StringUtils.isEmpty(orders.getId())){
            return AjaxJson.fail("参数错误");
        }
        orders = ordersService.get(orders.getId());
        String responseStr =  ordersService.tradeSettle(orders);
        KjtResponse kjtResponse = (KjtResponse) JsonMapper.fromJsonString(responseStr, KjtResponse.class);
        if("S10000" .equals(kjtResponse.getCode())){
            return AjaxJson.ok("转账成功");
        }else{
            logger.info(responseStr);
            return  AjaxJson.fail("转账失败！");
        }

    }
}
