package com.jhmis.api.customer;

import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.view.BrownDataFormView;
import com.jhmis.view.CheckDataFormView;
import com.jhmis.view.entity.BrownDataForm;
import com.jhmis.view.entity.CheckDataForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * @Description: 验收单报表
 */
@Api(value = "ApiCheckDataFormViewController", description = "验收单报表")
@RestController
@RequestMapping("/api/customer")
public class ApiCheckDataFormViewController extends BaseController{

    @Autowired
    CheckDataFormView checkDataFormView;
    /**
     * 验收单报表
     * @return
     */
    @ApiOperation(notes = "checkDataForm", httpMethod = "POST", value = "验收单报表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/checkDataForm", method = RequestMethod.POST)
    @SysLog(desc="验收单报表")
    public AjaxJson checkDataForm(CheckDataForm checkDataForm, HttpServletRequest request, HttpServletResponse response){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Map<String,Object> map= new HashMap<>();
        try {
            checkDataForm.setCust_code(currentAccount.getLoginName());
            map = checkDataFormView.getCheckDataFormView(checkDataForm);
        } catch (Exception e) {
            logger.error("验收单报表异常，原因：" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setCount(((Integer) map.get("count")).longValue());
        j.setData((List<CheckDataForm>) map.get("listCheckDataForm"));
        return j;
    }
}
