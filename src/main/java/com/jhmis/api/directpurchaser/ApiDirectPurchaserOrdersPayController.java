package com.jhmis.api.directpurchaser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.haier.order.dto.RetDTO;
import com.haier.order.utils.OrderServiceUtils;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(value = "ApiDirectPurchaserOrdersPayController", description = "采购商-支付管理")
@RestController
@RequestMapping("/api/directpurchaser/orderspay")
public class ApiDirectPurchaserOrdersPayController extends BaseController{
    private static Logger log = LoggerFactory.getLogger(ApiDirectPurchaserOrdersPayController.class);


    /**
     * 去支付
     */
    @RequestMapping("/toPayOrder")
    public AjaxJson toPayOrder(HttpServletRequest request, HttpServletResponse response
            ,String uuid,String bankCode,String payProductCode,String bankCardNo,String bankAccountName) {
        //判断当前登录用户是否存在，是否有效
        PurchaserAccount account = PurchaserUtils.getPurchaserAccount();
        if(account == null || StringUtils.isEmpty(account.getId())|| !"0".equals(account.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        if(StringUtils.isEmpty(uuid)){
            return AjaxJson.fail("参数不能为空！");
        }
        try {
            //根据uuid获取订单详情 状态
            RetDTO dto = OrderServiceUtils.toPayOrder(uuid,account.getPurchaserId(),bankCode,payProductCode,bankCardNo,bankAccountName);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                //获取返回数据
                String data = dto.getRetData();
                System.out.println("data================"+data);
                if(StringUtils.isNotBlank(data)){
                    if(data.indexOf("code")>-1){
                        Map<String,String> map = JSONArray.parseObject(dto.getRetData(), new TypeReference<Map<String,String>>(){});
                        if(map != null && "S10000".equals(map.get("code"))){
                            return AjaxJson.ok(data);
                        }else {
                            //数据异常
                            log.error(data);
                            return AjaxJson.fail(data);
                        }
                    }else{
                        return AjaxJson.ok(JSON.toJSONString(data));
                    }

                }
            }else if(RetDTO.ERROR4CUSTOMER.equals(dto.getRetStatus())){
                return AjaxJson.fail(dto.getRetMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return AjaxJson.fail("支付失败！");
    }




    /**
     * 去支付
     */
    @RequestMapping(value = "notify")
    public String notify(HttpServletRequest request,HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            Map<?, ?> reqParams = request.getParameterMap();
            String params = JSON.toJSONString(reqParams);
            RetDTO dto = OrderServiceUtils.notify(params);
            if(dto != null && RetDTO.SUCCESS.equals(dto.getRetStatus())){
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }


}
