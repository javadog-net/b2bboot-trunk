package com.jhmis.api.direct;

import com.haier.webservices.client.gvs.GvsService;
import com.haier.webservices.client.gvs.OMSIN;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
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

@Api(value = "ApiDirectGvsController", description = "获取GVS余额接口")
@RestController
@RequestMapping("/api/direct/gvs")
public class ApiDirectGvsController {
    protected Logger logger = LoggerFactory.getLogger(ApiDirectGvsController.class);

    @Autowired
    private MdmCustomersPartnerService mdmCustomersPartnerService;

    /**
     * 获取GVS余额接口
     * @return
     */
    @ApiOperation(notes = "getGvsBalance", httpMethod = "GET", value = "获取GVS余额接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "KUNNR", value = "付款方编码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "KKBER", value = "3200直采写死", required = true, paramType = "query", dataType = "String")}
           )
    @RequestMapping("/getGvsBalance")
    public AjaxJson getGvsBalance(String KUNNR,String KKBER) {
    	 //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        //客户余额=KLIMK-（SKFOR+SSOBL+OEIKW+OLIKW+OFAKW）
        logger.info("*_*_*_*_*_*_*_*_*_* ApiDirectGvsController  /getGvsBalance  获取GVS余额接口----------接口开始*_*_*_*_*_*_*_*_*_*");
        List<OMSIN> listResult = GvsService.query(KUNNR,KKBER);
        //异常情况
        if(listResult==null || listResult.size()==0 ){
            return AjaxJson.fail("未查到余额");
        }
        BigDecimal b = new BigDecimal(0);
        for(OMSIN omsin : listResult){
            BigDecimal KLIMK = omsin.getKLIMK();
            BigDecimal SKFOR = omsin.getSKFOR();
            BigDecimal SSOBL = omsin.getSSOBL();
            BigDecimal OEIKW = omsin.getOEIKW();
            BigDecimal OLIKW = omsin.getOLIKW();
            BigDecimal OFAKW = omsin.getOFAKW();
            BigDecimal subSum = SKFOR.add(SSOBL).add(OEIKW).add(OLIKW).add(OFAKW);
            BigDecimal end = KLIMK.subtract(subSum);
            b = b.add(end);
        }
        return AjaxJson.ok(b.toString());
    }
}
