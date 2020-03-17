package com.jhmis.api.customer;

import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.CustomerTemporaryEngineer;
import com.jhmis.modules.customer.entity.DealerFb;
import com.jhmis.modules.customer.service.CustomerTemporaryEngineerService;
import com.jhmis.modules.customer.service.DealerFbService;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.sys.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(value = "ApiCustomerAuthController", description = "客单登录相关")
@RestController
@RequestMapping("/api/customer/auth")
public class ApiCustomerAuthController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(ApiCustomerAuthController.class);

    @Autowired
    private DealerAccountService dealerAccountService;

    @Autowired
    private DealerFbService dealerFbService;
    @Autowired
    private CustomerTemporaryEngineerService customerTemporaryEngineerService;

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation(notes = "login", httpMethod = "POST", value = "登录", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("/login")
    public AjaxJson login(String userName,String password){
        return dealerAccountService.login(userName,password,false,"","");
    }

    /**
     * 非标客户注册
     * @return
     */
    @ApiOperation(notes = "fbRegist", httpMethod = "POST", value = "非标客户注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/fbRegist")
    public AjaxJson fbRegist(DealerFb dealerFb){
        dealerFb.setAddTime(new Date());
        //待审核标识
        dealerFb.setIsCheck("0");
        dealerFbService.save(dealerFb);
        return AjaxJson.ok("信息提交成功，请等待管理员审核后，短信通知注册的手机号");
    }


    /**
     * 非标客户注册
     * @return
     */
    @ApiOperation(notes = "temporaryEngineerRegist", httpMethod = "POST", value = "非标客户注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/temporaryEngineerRegist")
    public AjaxJson temporaryEngineerRegist(CustomerTemporaryEngineer customerTemporaryEngineer){
        //校验是否已存在
        CustomerTemporaryEngineer checkEntity = customerTemporaryEngineerService.findUniqueByProperty("cus_mobile",customerTemporaryEngineer.getCusMobile());
        if(checkEntity!=null){
            return AjaxJson.fail("您的手机号已申请，请耐心等待管理员审核!");
        }
        customerTemporaryEngineer.setAddTime(new Date());
        //待审核标识
        customerTemporaryEngineer.setStatus("0");
        customerTemporaryEngineerService.save(customerTemporaryEngineer);
        return AjaxJson.ok("信息提交成功，请等待管理员审核后，短信通知注册的手机号!");
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation(notes = "loginout", httpMethod = "POST", value = "退出", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/logout")
    public AjaxJson logout(){
        return dealerAccountService.logout();
    }

    /**
    *@Author: hdx
    *@CreateTime: 9:53 2019/6/19
    *@param:  cuscode 经销商编码
     *        mobile 完善信息时手机号
    *@Description:进行经销商重置密码
    */
    @ApiOperation(notes = "reset", httpMethod = "POST", value = "重置找回密码", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/reset")
    public AjaxJson reset(String cuscode,String mobile){
        logger.info("******进入经销商重置密码接口--开始******参数cuscode：" + cuscode + " mobile=" + mobile);
        if(StringUtils.isEmpty(cuscode)||StringUtils.isEmpty(mobile)){
            logger.info("******进入经销商重置密码接口--参数异常******");
            return AjaxJson.fail("信息提交成功，请等待管理员审核后，短信通知注册的手机号!");
        }
        //进行经销商重置密码
        DealerAccount dealerAccount = new DealerAccount();
        //设置参数经销商登陆账号
        dealerAccount.setLoginName(cuscode);
        //设置经销商关联手机号
        dealerAccount.setMobile(mobile);
        List<DealerAccount> listDealerAccount = null;
        try{
            listDealerAccount = dealerAccountService.findRestList(dealerAccount);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        //如果不存在
        if(listDealerAccount==null||listDealerAccount.size()==0){
            return AjaxJson.fail("账号信息与手机号关联不匹配,请核实后再处理!");
        }
        //如果存在则重置密码
        DealerAccount d = listDealerAccount.get(0);
        //设置初始化密码
        AjaxJson aj = dealerAccountService.reset(d);
        //并且发送短信
        try {
			SendMsgApi.SendMessage(mobile,aj.getMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return aj;
    }

}
