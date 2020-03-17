package com.jhmis.api.direct;

import com.haier.webservices.client.gvs.GvsService;
import com.haier.webservices.client.gvs.OMSIN;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.DirectMsg;
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.DirectMsgService;
import com.jhmis.modules.shop.service.MdmCustomersPartnerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(value = "ApiDirectOrderController", description = "获取GVS余额接口")
@RestController
@RequestMapping("/api/direct/order")
public class ApiDirectOrderController {
    protected Logger logger = LoggerFactory.getLogger(ApiDirectOrderController.class);

    @Autowired
    DirectMsgService directMsgService;
    /**
     * 生成订单
     * @return
     */
    @ApiOperation(notes = "subOrder", httpMethod = "POST", value = "生成订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectName", value = "项目名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "shPartner", value = "送达方(shPartner)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "pyPartner", value = "付款方(pyPartner)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bpPartner", value = "开票方(bpPartner)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "cartIds", value = "购物车ids(多个购物车id由逗号隔开)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addressProvince", value = "省份id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addressCity", value = "城市id", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "addressCounty", value = "区/县id", required = true, paramType = "query", dataType = "String")}
           )
    @RequestMapping("/subOrder")
    public AjaxJson subOrder(String projectName,String shPartner,String pyPartner,String bpPartner,String cartIds,String addressProvince,String addressCity,String addressCounty) {
        if(StringUtils.isEmpty(projectName) ||  StringUtils.isEmpty(shPartner) ||  StringUtils.isEmpty(pyPartner) ||  StringUtils.isEmpty(bpPartner) ||  StringUtils.isEmpty(cartIds)||  StringUtils.isEmpty(addressProvince)||  StringUtils.isEmpty(addressCity)||  StringUtils.isEmpty(addressCounty)){
            return AjaxJson.fail("参数错误！");
        }
    	 //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //进行保存
        Map<String,Object> map = directMsgService.saveOrder(projectName,shPartner,pyPartner,bpPartner,cartIds,currentAccount,addressProvince,addressCity,addressCounty);
        return AjaxJson.ok(map);
    }
}
