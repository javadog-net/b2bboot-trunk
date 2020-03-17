package com.jhmis.api.customer;

import com.haier.user.api.ProductsApi;
import com.haier.webservices.client.hps.project.HpsApi;
import com.haier.webservices.client.hps.project.LockUserVO;
import com.haier.webservices.client.hps.project.ProjectDetailInfoQYGVO;
import com.haier.webservices.client.hps.project.ProjectDetailInfoVO;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MD5;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.customer.entity.*;
import com.jhmis.modules.customer.service.*;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.service.GmInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 10:56 2019/4/17
 * @Modified By
 */
@Api(value = "ApiJshController", description = "巨商会相关订单信息")
@RestController
@RequestMapping("/api/customer")
public class ApiJshController {
    protected Logger logger = LoggerFactory.getLogger(ApiJshController.class);

    @Autowired
    protected TOrderInfoService tOrderInfoService;

    @Autowired
    protected QygTOrderInfoService qygTOrderInfoService;

    @Autowired
    protected ViewBusinessOpportunityService viewBusinessOpportunityService;

    /**
     * 车辆轨迹监控平台-B2B全流程轨迹查询-id
     */
    @Value("${smart.id}")
    protected static String id = "19";
    /**
     * 车辆轨迹监控平台-B2B全流程轨迹查询-key
     */
    @Value("${smart.key}")
    protected static String key = "9C7A38D851434A4FE05396108A0A8D0F";

    /**
     * 大数据共享云平台免登陆认证接口-B2B全流程订单状态查询userId
     */
    @Value("${rrs.userId}")
    protected static String userId = "b2bHaier";
    /**
     * 大数据共享云平台免登陆认证接口-B2B全流程订单状态查询username
     */
    @Value("${rrs.username}")
    protected static String username = "b2bHaier";


    @Value("${rrs.key}")
    protected static String rrskey = "6A1D02DA475D4A7BE05396108A0BBC3T";


    /**
     * 大数据共享云平台免登陆认证接口地址
     */
    protected String rrsLoginUrl = "https://icloud.rrswl.com/user/rrsLogin?systemid=SYSTEMID&time=TIME&token=TOKEN&userId=USERID&username=USERNAME&bstkd=BSTKD&type=TYPE";

    /**
     * 获取履约中订单列表
     * @param
     * @return
     */
    @ApiOperation(notes = "orderList", httpMethod = "POST", value = "获取履约中订单列表")
    @RequestMapping(value = "orderList",method = RequestMethod.POST)
    @SysLog(desc="获取履约中订单列表")
    public AjaxJson  orderList(TOrderInfo tOrderInfo,HttpServletRequest request, HttpServletResponse response){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiJshController  /orderList  获取履约中订单列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //设置经销商账号
        tOrderInfo.setSaletoCode(currentAccount.getLoginName());
        //tOrderInfo.setSaletoCode("8700000983");
        Page<TOrderInfo> pageTOrderInfo = new Page<>(request,response);
        //获取履约中订单列表
        Page<TOrderInfo> resTOrderInfo = tOrderInfoService.findPage(pageTOrderInfo,tOrderInfo);
        return AjaxJson.layuiTable(resTOrderInfo);
    }


    /**
     * 获取物流轨迹参数
     * @param ordedrNo 订单号
     * @return
     */
    @ApiOperation(notes = "smartMap", httpMethod = "POST", value = "获取物流轨迹参数")
    @RequestMapping(value = "smartMap",method = RequestMethod.POST)
    @SysLog(desc="获取物流轨迹参数")
    public AjaxJson  smartMap(String ordedrNo){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiJshController  /smartMap  获取物流轨迹参数----------接口开始*_*_*_*_*_*_*_*_*_*");
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //实体封装
        QygTOrderInfo qygTOrderInfo = new QygTOrderInfo();
        //订单号
        qygTOrderInfo.setOrderNo(ordedrNo);
        //88码
        qygTOrderInfo.setSaletoCode(currentAccount.getLoginName());
        //tOrderInfo.setSaletoCode("8700000983");
        //获取履约中订单列表
        List<QygTOrderInfo> listQygTOrderInfo = null;
        listQygTOrderInfo = qygTOrderInfoService.findList(qygTOrderInfo);
        if(listQygTOrderInfo==null || listQygTOrderInfo.size()==0){
            logger.info("*_*_*_*_*_*_*_*_*_* 没有查到此单 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前订单不存在,请核对后再试");
        }
        QygTOrderInfo toi = listQygTOrderInfo.get(0);
        logger.info(toi.toString());
        //提货单号85码
        String sapDn = toi.getSapDn();
        if(StringUtils.isEmpty(sapDn)){
            logger.info("*_*_*_*_*_*_*_*_*_* 没有此85单号 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("没有查到此85单号");
        }
        String endUrl = "";
        String secondUrl = "https://gps.rrswl.com/GPSTrackWeb/ordermap.jsp?orderno=ORDERNO&orbittype=1&ordertype=2&pjtype=1&systemid=SYSTEMID&time=TIME&token=TOKEN";
        String firstUrl = "https://gps.rrswl.com/GPSTrackWeb/map.jsp?ordertype=2&orderno=ORDERNO&systemid=SYSTEMID&time=TIME&token=TOKEN";
        if("RRS,TC,TM".indexOf(toi.getStockType())!=-1){
            //证明是二次物流
            endUrl = secondUrl;
        }else if("ZK,ZJ".indexOf(toi.getOrderType())!=-1 || "CT,ZY".indexOf(toi.getOrderChannel())!=-1){
            //证明是一次物流
            endUrl = firstUrl;
        }
        //单号85码
        endUrl = endUrl.replace("ORDERNO",sapDn);
        //SYSTEMID
        endUrl = endUrl.replace("SYSTEMID",id);
        //TIME
        String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        endUrl = endUrl.replace("TIME",now);
        //TOKEN
        String token = createToken(now,id,key);
        endUrl = endUrl.replace("TOKEN",token);
        return AjaxJson.ok(endUrl);
    }


    /**
     * 获取大数据共享云平台免登陆
     * @param ordedrNo 订单单号
     * @return
     */
    @ApiOperation(notes = "rrsLogin", httpMethod = "POST", value = "大数据共享云平台免登陆认证接口-B2B全流程订单状态查询")
    @RequestMapping(value = "rrsLogin",method = RequestMethod.POST)
    @SysLog(desc="大数据共享云平台免登陆认证接口-B2B全流程订单状态查询")
    public AjaxJson rrsLogin(String ordedrNo){
        if(StringUtils.isEmpty(ordedrNo)){
            logger.info("*_*_*_*_*_*_*_*_*_* 没有此订单号 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("不存在此订单号");
        }
        //实体封装
        QygTOrderInfo qygTOrderInfo = new QygTOrderInfo();
        //订单号
        qygTOrderInfo.setOrderNo(ordedrNo);
        //获取履约中订单列表
        List<QygTOrderInfo> listQygTOrderInfo = null;
        listQygTOrderInfo = qygTOrderInfoService.findList(qygTOrderInfo);
        if(listQygTOrderInfo==null || listQygTOrderInfo.size()==0){
            logger.info("*_*_*_*_*_*_*_*_*_* 没有查到此单 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前订单不存在,请核对后再试");
        }
        QygTOrderInfo toi = listQygTOrderInfo.get(0);
        logger.info(toi.toString());
        //提货单号85码
        String sapDn5 = toi.getSapDn5();
        if(StringUtils.isEmpty(sapDn5)){
            logger.info("*_*_*_*_*_*_*_*_*_* 没有此85单号 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("没有查到此85单号");
        }
        String url = this.rrsLoginUrl;
        //判断一次物流还是二次物流
        String sapDn1 = toi.getSapDn1();
        //如果同时存在则调用工厂发货的接口-- 一次物流
        if(StringUtils.isNotBlank(sapDn1) && StringUtils.isNotBlank(sapDn5)){
            url = url.replaceAll("TYPE","1");
        }else if(StringUtils.isBlank(sapDn1) && StringUtils.isNotBlank(sapDn5)){
            url = url.replaceAll("TYPE","2");
        }else{
            url = url.replaceAll("TYPE","2");
        }
        //systemid
        url= url.replaceAll("SYSTEMID","001");
        //单号85码
        url = url.replace("BSTKD",sapDn5);
        //TIME
        String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        url = url.replace("TIME",now);
        //TOKEN
        String token = createToken(now,rrskey,"001");
        url = url.replace("TOKEN",token);
        //username
        url = url.replace("USERNAME",username);
        //userId
        url = url.replace("USERID",userId);
        return AjaxJson.ok(url);
    }



    /**
     * 根据查询履约相关的所有数据
     * @param
     * @return
     */
    @ApiOperation(notes = "inperOrderList", httpMethod = "POST", value = "根据查询履约相关的所有数据")
    @RequestMapping(value = "inperOrderList",method = RequestMethod.POST)
    @SysLog(desc="根据查询履约相关的所有数据")
    public AjaxJson  inperOrderList(ViewBusinessOpportunity viewBusinessOpportunity,HttpServletRequest request, HttpServletResponse response){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiJshController  /orderList  根据查询履约相关的所有数据----------接口开始*_*_*_*_*_*_*_*_*_*");
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //加入订单状态筛选状态
        //a.已下单：订单创建时间字段 给365确认一下  create_time
        //b.已评审：sap_judged 是1的 为已评审 否为公共字段 （一次物流已发货，）
        //c.一次物流:
        //d.二次物流
        //e.已返单
        //状态不为空则
        if(StringUtils.isNotBlank(viewBusinessOpportunity.getOrderStatus())){
            String orderStatus = viewBusinessOpportunity.getOrderStatus();
            switch (orderStatus){
                case "1":
                    //已下单的话，只有有订单号了就是创建了
                  break;
                case "2":
                    viewBusinessOpportunity.setSapJudgeDate(ProcessCode.YES.getLabel());
                    break;
                case "3":
                    viewBusinessOpportunity.setSapSended1(ProcessCode.YES.getLabel());
                    break;
                case "4":
                    viewBusinessOpportunity.setSapSended5(ProcessCode.YES.getLabel());
                    break;
                case "5":
                    viewBusinessOpportunity.setSapReorderd(ProcessCode.YES.getLabel());
                    break;
            }
        }
        Page<ViewBusinessOpportunity> page = new Page<>(request,response);
        //设置排序规则
        //page.setOrderBy("project_code desc");
        //设置经销商编码
        logger.info(viewBusinessOpportunity.toString());
        viewBusinessOpportunity.setCustCode(currentAccount.getLoginName());
        logger.info("CustCode=" + currentAccount.getLoginName());
        Page<ViewBusinessOpportunity> pageViewBusinessOpportunity = null;
        try{
            pageViewBusinessOpportunity = viewBusinessOpportunityService.findPage(page,viewBusinessOpportunity);
            logger.info("pageViewBusinessOpportunity=" + pageViewBusinessOpportunity.toString());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return AjaxJson.layuiTable(pageViewBusinessOpportunity);
    }



    /**
    *@Author: hdx
    *@CreateTime: 14:28 2020/1/20
    *@param:  * @param null
    *@Description: 生成token方法
        平台Key+平台id+请求时间，通过md5加密后的字符串，加上请求时间的第4位到第8位通过md5加密生成token(32位小写)。
        例：
        平台Key：72E900B4DDCB06FBE05397108A0A00DB
        平台Id：1
        请求时间：20180808162030
        md5(72E900B4DDCB06FBE05397108A0A00DB120180808162030)=a4e05dec559c0d95ef7454cfed7fc6ed
        md5(a4e05dec559c0d95ef7454cfed7fc6ed0808)=06abaa9bb40363e5c695dd3a3f780fd4
        最终生成的token:06abaa9bb40363e5c695dd3a3f780fd4
     */
    public static String createToken(String dateNow,String key,String id){
        String now = dateNow;
        String keyAddIdAddNow = key+id+now;
        String md5keyAddIdAddNow = MD5.MD5Encode(keyAddIdAddNow);
        String endStr = md5keyAddIdAddNow + now.substring(4,8);
        String tooken = MD5.MD5Encode(endStr);
        return tooken;
    }
}
