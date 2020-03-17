package com.jhmis.api.EUC;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.haier.webservices.client.hps.project.HpsApi;
import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.Exception.EucException;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.entity.ViewQygProjectdetailviewdate;
import com.jhmis.modules.customer.service.CustomerMsgService;
import com.jhmis.modules.customer.service.ViewQygProjectdetailviewdateService;
import com.jhmis.modules.euc.entity.*;
import com.jhmis.modules.euc.service.EucMsgOrderService;
import com.jhmis.modules.euc.service.EucMsgService;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.service.GmInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.EUC
 * @Author: hdx
 * @CreateTime: 2019-11-07 09:45
 * @Description: EUC相关接口提供
 */
@Api(value = "ApiProcessAppController", description = "企业购给大客户app提供接口")
@RestController
@RequestMapping("/api/euc")
public class ApiEucController extends BaseController {

    @Autowired
    DealerService dealerService;

    @Autowired
    EucMsgService eucMsgService;

    @Autowired
    ViewQygProjectdetailviewdateService viewQygProjectdetailviewdateService;

    @Autowired
    AreaCodeService areaCodeService;

    @Autowired
    GmInfoService gmInfoService;

    @Autowired
    CustomerMsgService customerMsgService;

    @Autowired
    private EucMsgOrderService eucMsgOrderService;

    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;


    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 提供ECU-获取经销商列表
     */
    @ApiOperation(notes = "dealers", httpMethod = "GET", value = "提供ECU-获取经销商列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/dealers", method = RequestMethod.POST)
    public AjaxJson dealers(Dealer dealer, HttpServletRequest request, HttpServletResponse response) {
        List<Dealer> listDealer = null;
        Page<Dealer> page = null;
        try {
            Dealer d = new Dealer();
            //漯河归为郑州(张楠楠电话沟通)
            if("12E02".equals(dealer.getGmId())){
                dealer.setGmId("12E01");
            }
            page = dealerService.findPage(new Page<Dealer>(request, response), dealer);
        } catch (Exception e) {
            logger.error("/dealers 提供ECU-获取经销商列表异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(page);
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@param eucMsg 实体
     *@Description: 提供ECU-向企业购提报需求
     */
    @ApiOperation(notes = "euc_msg", httpMethod = "POST", value = "提供ECU-向企业购提报需求", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/euc_msg", method = RequestMethod.POST)
    public AjaxJson euc_msg(EucMsg eucMsg) {
        try {
            eucMsgService.fromEucMsg(eucMsg);
        } catch (Exception e) {
            logger.error("/euc_msg 向企业购提报需求异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(eucMsg.getMsgId());
    }



    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@param businessCode 商机编码
     *@Description: 提供供应商中台-根据商机编码获取详细信息
     */
    @ApiOperation(notes = "euc_details", httpMethod = "GET", value = "提供供应商中台-根据商机编码获取详细信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/euc_details/{businessCode}", method = RequestMethod.GET)
    public AjaxJson euc_details(@PathVariable(required = true) String businessCode) {
        logger.info("提供供应商中台-根据商机编码获取详细信息");
        String httpStr = "";
        JSONObject param = JSONUtil.createObj();
        param.put("businessCode", businessCode);
        Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Type", "application/json;charset=utf-8");
        try{
            httpStr = HttpRequest.post(Constants.EUC_URL+"/bussiness/findDetailByBussinessCode").addHeaders(map).body(param).execute().body();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return AjaxJson.ok(httpStr);
    }


    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 提供供应商中台-查询商机列表
     */
    @ApiOperation(notes = "euc_list", httpMethod = "POST", value = "提供供应商中台-查询商机列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/euc_list", method = RequestMethod.POST)
    public AjaxJson euc_list(EucMsg eucMsg, HttpServletRequest request, HttpServletResponse response) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        eucMsg.setContractorCode(currentAccount.getLoginName());
        //是否是top用户
        if(StringUtils.isNotBlank(eucMsg.getIfTopCustomer())){
            if("1".equals(eucMsg.getIfTopCustomer())){
                eucMsg.setIfTopCustomer("是");
            }else if("0".equals(eucMsg.getIfTopCustomer())){
                eucMsg.setIfTopCustomer("否");
            }
        }
        Page<EucMsg> page = eucMsgService.findPage(new Page<EucMsg>(request, response), eucMsg);
        return AjaxJson.layuiTable(page);
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 提供供应商中台-根据id查询基础EUC信息
     */
    @ApiOperation(notes = "euc_list_detail", httpMethod = "GET", value = "提供供应商中台-根据id查询基础EUC信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/euc_list_detail/{id}", method = RequestMethod.GET)
    public AjaxJson euc_list_detail(@PathVariable String id) {
        EucMsg eucMsg = eucMsgService.getOwnById(id);
        return AjaxJson.ok(eucMsg);
    }


    /**
     * 经销商报备需求
     * @return
     */
    @ApiOperation(notes = "report", httpMethod = "POST", value = "经销商报备需求", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/report")
    public AjaxJson report(CustomerMsg customerMsg){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /report  经销商报备需求----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Dealer d = dealerService.get(currentAccount.getDealerId());
        Map<String,Object> map = null;
        try {
            map = eucMsgService.report(customerMsg,d,currentAccount);
        } catch (EucException e) {
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(map);
    }


    /**
     * 二次编辑提交接口
     * @return
     */
    @ApiOperation(notes = "updateReport", httpMethod = "POST", value = "经销商报备需求", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/updateReport")
    public AjaxJson updateReport(CustomerMsg customerMsg){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /updateReport  小单需求编辑----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        Dealer d = dealerService.get(currentAccount.getDealerId());
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        if(StringUtils.isBlank(customerMsg.getMsgId())){
            logger.info("*_*_*_*_*_*_*_*_*_* 该项目不能修改*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("该项目不能修改！");
        }
        String projectId = customerMsg.getProjectId();
        if(StringUtils.isBlank(projectId)){
            logger.info("*_*_*_*_*_*_*_*_*_* 该项目不能修改*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("该项目不能修改！");
        }else{
            CustomerMsg cus = new CustomerMsg();
            cus.setProjectId(projectId);
            cus.setMsgId(customerMsg.getMsgId());
            List<CustomerMsg> listCus = customerMsgService.findList(cus);
            if(listCus != null && listCus.size()>0){
                CustomerMsg cusNew = listCus.get(0);
                customerMsg.setId(cusNew.getId());
                if(StringUtils.isNotBlank(cusNew.getSmallBill())){
                    if(!"2".equals(cusNew.getSmallBill()) &&  !"审批拒绝".equals(cusNew.getOperateStatus()) ){
                        return AjaxJson.fail("该项目不能修改！");
                    }
                }

            }else{
                return AjaxJson.fail("该项目不能修改！");
            }
        }
        if(StringUtils.isNotBlank(customerMsg.getChancePoint())){
            String chancePoint = customerMsg.getChancePoint();
            if(chancePoint.startsWith(",")){
                customerMsg.setChancePoint(chancePoint.substring(1));
            }
            if(chancePoint.endsWith(",")){
                customerMsg.setChancePoint(chancePoint.substring(0, chancePoint.length()-1));
            }
        }
        if(StringUtils.isNotBlank(customerMsg.getIndustryHomeCategory())){
            String industryHomeCategory = customerMsg.getIndustryHomeCategory();
            if(industryHomeCategory.startsWith(",")){
                customerMsg.setIndustryHomeCategory(industryHomeCategory.substring(1));
            }
            if(industryHomeCategory.endsWith(",")){
                customerMsg.setIndustryHomeCategory(industryHomeCategory.substring(0, industryHomeCategory.length()-1));
            }
        }
        if(StringUtils.isNotBlank(customerMsg.getIndustryCategory())){
            String industryCategory = customerMsg.getIndustryCategory();
            if(industryCategory.startsWith(",")){
                customerMsg.setIndustryCategory(industryCategory.substring(1));
            }
            if(industryCategory.endsWith(",")){
                customerMsg.setIndustryCategory(industryCategory.substring(0, industryCategory.length()-1));
            }
        }
        Map<String,Object> map =  HpsApi.updateProjectFromQYG(customerMsg);
        //处理是否成功
        String flag = (String) map.get("flag");
        //处理省市区名称
        if("error".equals(flag)){
            //填写失败原因
            String errorMsg = (String) map.get("errorMsg");
            customerMsg.setErrorMsg(errorMsg);
            if("1".equals(customerMsg.getSmallBill()) &&  !"审批拒绝".equals(customerMsg.getOperateStatus()) ){
                customerMsg.setSmallBill("2");
            }
            customerMsgService.saveR(customerMsg);
            logger.info("*_*_*_*_*_*_*_*_*_* updateProjectFromQYG调用失败，失败原因"+ errorMsg +" *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail(errorMsg);
        }else if("success".equals(flag)){
            //success代表漏斗返回成功
            projectId = (String) map.get("projectCode");
            String msgId = (String) map.get("msgId");
            customerMsg.setProjectId(projectId);
            customerMsgService.saveR(customerMsg);
            //查询view_QYG_ProjectDetailViewDate 视图
            String view_qyg_projectdetailviewdate_select_sql = "select * from view_QYG_ProjectDetailViewDate where projectcode='"+ projectId +"'";
            //根据实体反射进行转换
            List<ViewQygProjectdetailviewdate> listViewQygProjectdetailviewdate = jdbcTemplate1.query(view_qyg_projectdetailviewdate_select_sql, new BeanPropertyRowMapper(ViewQygProjectdetailviewdate.class));
            if(listViewQygProjectdetailviewdate!=null && listViewQygProjectdetailviewdate.size()>0){
                //证明有效逐条插入
                for(ViewQygProjectdetailviewdate viewQygProjectdetailviewdate:listViewQygProjectdetailviewdate){
                    if(StringUtils.isBlank(viewQygProjectdetailviewdate.getProjectcode()) || StringUtils.isBlank(viewQygProjectdetailviewdate.getMsgid()) ){
                        continue;
                    }
                    ViewQygProjectdetailviewdate projectdetailviewdate = viewQygProjectdetailviewdateService.getByProjectcode(viewQygProjectdetailviewdate.getProjectcode());
                    if(projectdetailviewdate==null){
                        viewQygProjectdetailviewdateService.insert(viewQygProjectdetailviewdate);
                    }else{
                        viewQygProjectdetailviewdateService.update(viewQygProjectdetailviewdate);
                    }
                }
            }
        }
        else{
            //失败直接调用异常
            customerMsg.setStatus("0");
            customerMsg.setErrorMsg("调用异常");
            logger.info("*_*_*_*_*_*_*_*_*_* 调用异常 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("调用异常");
        }

        return AjaxJson.ok(map);
    }



    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 提供供应商中台-首页查询商机列表
     */
    @ApiOperation(notes = "euc_list_index", httpMethod = "GET", value = "提供供应商中台-首页查询商机列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/euc_list_index", method = RequestMethod.GET)
    public AjaxJson euc_list_index(String gmId, HttpServletRequest request, HttpServletResponse response) {
        logger.info("*_*_*_*_*_*_*_*_*_* euc_list_index 提供供应商中台-首页查询商机列表 *_*_*_*_*_*_*_*_*_*");
      /*  DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Dealer d = dealerService.get(currentAccount.getDealerId());*/
        logger.info("*_*_*_*_*_*_*_*_*_* Dealer 有值 *_*_*_*_*_*_*_*_*_*");
        EucMsg eucMsg = new EucMsg();
        //中心录入
        eucMsg.setCenter(gmId);
        logger.info("*_*_*_*_*_*_*_*_*_* Center值=");
        //查看所有
        eucMsg.setIsAll("1");
        Page<EucMsg> listPage = eucMsgService.findPage(new Page<EucMsg>(request, response), eucMsg);
        return AjaxJson.ok(listPage);
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 提供供应商中台-查询所有符合中心下商机列表
     */
    @ApiOperation(notes = "euc_list_all", httpMethod = "POST", value = "提供供应商中台-查询所有符合中心下商机列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/euc_list_all", method = RequestMethod.POST)
    public AjaxJson euc_list_all(EucMsg eucMsg, HttpServletRequest request, HttpServletResponse response) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Dealer d = dealerService.get(currentAccount.getDealerId());
        //是否是top用户
        if(StringUtils.isNotBlank(eucMsg.getIfTopCustomer())){
            if("1".equals(eucMsg.getIfTopCustomer())){
                eucMsg.setIfTopCustomer("是");
            }else if("0".equals(eucMsg.getIfTopCustomer())){
                eucMsg.setIfTopCustomer("否");
            }
        }
        //中心录入
        eucMsg.setCenter(d.getGmId());
        //查看所有
        eucMsg.setIsAll("1");
        Page<EucMsg> page = eucMsgService.findPage(new Page<EucMsg>(request, response), eucMsg);
        return AjaxJson.layuiTable(page);
    }


    /**
     * 保存或编辑申诉信息
     * @param eucMsgOrder
     * @return
     */
    @ApiOperation(notes = "update_appeal", httpMethod = "POST", value = "提供供应商中台-保存申诉信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商机订单ID（保存和修改都必须传）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "appealContent", value = "申诉内容", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "appealFiles", value = "申诉文件URL地址，多个文件| 分割", required = true, paramType = "form", dataType = "String")
    })
    @PostMapping(value = "/update_appeal")
    public AjaxJson update_appeal(EucMsgOrder eucMsgOrder){
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        if(eucMsgOrder == null || StringUtils.isEmpty(eucMsgOrder.getId()) ){
            return AjaxJson.fail("参数错误");
        }
        if(StringUtils.isEmpty(eucMsgOrder.getAppealContent()) && StringUtils.isEmpty(eucMsgOrder.getAppealFiles())){
            return AjaxJson.fail("申诉内容和申诉文件至少填一项");
        }
        //申诉状态修改为“保存申诉信息”前台根据此状态来判断是否显示“提交”按钮
        eucMsgOrder.setAppealStatus(EucMsgCode.AUTH_APPEAL_SAVE.getValue());
        //保存申诉信息
        eucMsgOrderService.updateAppealInfo(eucMsgOrder);
        return AjaxJson.ok("保存申诉信息成功！");
    }

    /**
     * 供应商中台-是否有申诉权限
     * @param projectCode
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(notes = "get_appeal_auth", httpMethod = "GET", value = "提供供应商中台-是否有申诉权限", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @GetMapping(value = "/get_appeal_auth")
    public AjaxJson get_appeal_auth(String projectCode,HttpServletRequest request,HttpServletResponse response){
//        DealerAccount currentAccount = DealerUtils.getDealerAccount();
//        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
//            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
//            return AjaxJson.fail("当前登录账号无效");
//        }
        if(StringUtils.isEmpty(projectCode)){
            return AjaxJson.fail("参数错误！");
        }
        //根据项目编码查询 订单信息
        EucMsgOrder eucMsgOrder = new EucMsgOrder();
        eucMsgOrder.setProjectCode(projectCode);
        List<EucMsgOrder> eucMsgOrderList = eucMsgOrderService.findList(eucMsgOrder);
        if(eucMsgOrderList.size() == 0 ){
            return AjaxJson.fail("项目信息不存在");
        }
        if(eucMsgOrderList.size() >1){
            return AjaxJson.fail("该项目编码对应两条信息");
        }

         eucMsgOrder = eucMsgOrderList.get(0);

        //  调用HPS 是否具有申请权限的接口
        String httpStr = eucMsgService.getAppealAuth(projectCode,eucMsgOrder.getMsgId(),eucMsgOrder.getContractorCode(),eucMsgOrder.getContractorName());
        logger.info("HPS canBeApply response--------------"+httpStr);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(httpStr);
        String errCode = (String)jsonObject.get("errcode");
        String errMsg = (String)jsonObject.get("errmsg");
        if("0".equals(errCode)){
            return  AjaxJson.ok();
        }else{
            return AjaxJson.fail(errMsg);
        }

    }

    /**
     * 提交申诉信息至HPS
     * @param id
     * @return
     */
    @ApiOperation(notes = "submit_appeal", httpMethod = "POST", value = "提供供应商中台-提交申诉信息至HPS", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商机订单ID（必须传）", required = false, paramType = "form", dataType = "String")
    })
    @PostMapping(value = "/submit_appeal")
    public AjaxJson submit_appeal(String id){
        if(StringUtils.isEmpty(id)){
            return AjaxJson.fail("参数错误");
        }
        EucMsgOrder eucMsgOrder = eucMsgOrderService.get(id);
        if(eucMsgOrder == null){
            return  AjaxJson.fail("申诉信息不存在");
        }
        //调用HPS 是否有申诉权限接口
        String httpStr = eucMsgService.getAppealAuth(eucMsgOrder.getProjectCode(),eucMsgOrder.getMsgId(),eucMsgOrder.getContractorCode(),eucMsgOrder.getContractorName());
        logger.info("HPS canBeApply response--------------"+httpStr);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(httpStr);
        Integer errCode = (Integer)jsonObject.get("errcode");
        String errMsg = (String)jsonObject.get("errmsg");
        if(errCode == 0){
            //调用 HPS 的保存申诉信息接口，更新申诉提交时间等信息
            String resultMsg = eucMsgOrderService.submitToHps(eucMsgOrder);
            if(Global.HPS_RESPONSE_OK.equals(resultMsg)){
                return  AjaxJson.ok("申诉信息提交成功！");
            }else{
                return AjaxJson.fail(resultMsg);
            }

        }else{
            return AjaxJson.fail(errMsg);
        }

    }

    /**
     *
     * @param authInfoRequest
     * @return
     */
    @ApiOperation(notes = "updateAuthStatus", httpMethod = "POST", value = "提供给HPS-更新授权状态", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @PostMapping(value = "/updateAuthStatus")
    public AjaxJson updateAuthStatus(@RequestBody AuthInfoRequest authInfoRequest) {
        String businessCode = authInfoRequest.getBusinessCode();
        List<AuthInfo> authList = authInfoRequest.getAuthList();
        logger.info("businessCode is ----------------"+businessCode);
        logger.info("authList.size is ----------------"+authList.size());

        if(StringUtils.isEmpty(businessCode)){
            return AjaxJson.fail("商机编码不能为空");
        }
        //根据商机编码查询商机信息
        EucMsg eucMsg = eucMsgService.findUniqueByProperty("business_code",businessCode);
        if(eucMsg == null){
            return AjaxJson.fail("商机信息不存在");
        }
        eucMsgOrderService.hpsAuth(eucMsg,authList);
        return AjaxJson.ok("获取授权状态成功！");
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 提供供应商中台-查询所有符合中心下商机列表 - 版本修改为网格中心全国
     */
    @ApiOperation(notes = "euc_list_all_v1", httpMethod = "POST", value = "提供供应商中台-查询所有符合中心下商机列表 - 版本修改为网格中心全国", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/euc_list_all_v1", method = RequestMethod.POST)
    public AjaxJson euc_list_all_v1(EucMsg eucMsg, HttpServletRequest request, HttpServletResponse response) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        Dealer d = dealerService.get(currentAccount.getDealerId());
        //是否是top用户
        if(StringUtils.isNotBlank(eucMsg.getIfTopCustomer())){
            if("1".equals(eucMsg.getIfTopCustomer())){
                eucMsg.setIfTopCustomer("是");
            }else if("0".equals(eucMsg.getIfTopCustomer())){
                eucMsg.setIfTopCustomer("否");
            }
        }
        //中心录入
        eucMsg.setCenter(d.getGmId());
        //网格录入
        eucMsg.setGridCode(d.getWgcode());
        Page<EucMsg> page = null;
        try {
            page = eucMsgService.findPuncturePage(new Page<EucMsg>(request, response), eucMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.layuiTable(page);
    }

}
