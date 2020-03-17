package com.jhmis.api.customer;

import com.haier.webservices.client.hps.verify.VerifyBillSaveVo;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.view.FLDataDetailView;
import com.jhmis.view.entity.BrownDataForm;
import com.jhmis.view.entity.FLDataDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.customer
 * @Author: hdx
 * @CreateTime: 2019-10-28 15:23
 * @Description: 返利明细报表
 */
@Api(value = "ApiFLDataDetailViewController", description = "返利明细报表视图")
@RestController
@RequestMapping("/api/customer")
public class ApiFLDataDetailViewController extends BaseController {

    @Autowired
    FLDataDetailView fLDataDetailView;
    /**
     * 返利明细报表列表
     * @return
     */
    @ApiOperation(notes = "FLData", httpMethod = "POST", value = "验收及申诉接口", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/FLData", method = RequestMethod.POST)
    @SysLog(desc="返利明细报表列表")
    public AjaxJson FLData(FLDataDetail fLDataDetail){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Map<String,Object> map= new HashMap<>();
        try {
            fLDataDetail.setCustomer_code_sp(currentAccount.getLoginName());
            map = fLDataDetailView.getFLDataDetail(fLDataDetail);
        } catch (Exception e) {
            logger.error("返利明细报表列表异常，原因：" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setCount(((Integer) map.get("count")).longValue());
        j.setData((List<FLDataDetail>) map.get("listFLDataDetail"));
        return j;
    }
}
