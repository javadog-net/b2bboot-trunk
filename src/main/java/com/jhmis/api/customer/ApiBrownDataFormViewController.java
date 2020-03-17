package com.jhmis.api.customer;

import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.view.BrownDataFormView;
import com.jhmis.view.FLDataDetailView;
import com.jhmis.view.entity.BrownDataForm;
import com.jhmis.view.entity.CheckDataForm;
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
 * @Description: 工程版报表
 */
@Api(value = "ApiFLDataDetailViewController", description = "工程版报表")
@RestController
@RequestMapping("/api/customer")
public class ApiBrownDataFormViewController extends BaseController {


    @Autowired
    BrownDataFormView brownDataFormView;
    /**
     * 工程版报表
     * @return
     */
    @ApiOperation(notes = "brownDataForm", httpMethod = "POST", value = "工程版报表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/brownDataForm", method = RequestMethod.POST)
    public AjaxJson brownDataForm(BrownDataForm brownDataForm){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Map<String,Object> map= new HashMap<>();
        try {
            brownDataForm.setDealerCode(currentAccount.getLoginName());
            map = brownDataFormView.getBrownDataForm(brownDataForm);
        } catch (Exception e) {
            logger.error("工程版报表异常，原因：" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setCount(((Integer) map.get("count")).longValue());
        j.setData((List<BrownDataForm>) map.get("listBrownDataForm"));
        return j;
    }
}
