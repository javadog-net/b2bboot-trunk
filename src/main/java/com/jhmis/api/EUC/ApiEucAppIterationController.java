package com.jhmis.api.EUC;

import com.haier.webservices.client.mdmTob2b.source.MdmSourceApi;
import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.EucException;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.annotation.SysLog;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.lock.LockUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.UploadService;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.TGridArea;
import com.jhmis.modules.customer.service.CustomerMsgService;
import com.jhmis.modules.customer.service.TGridAreaService;
import com.jhmis.modules.customer.service.ViewQygProjectdetailviewdateService;
import com.jhmis.modules.euc.entity.EucMsg;
import com.jhmis.modules.euc.entity.EucMsgOrder;
import com.jhmis.modules.euc.service.EucMsgOrderService;
import com.jhmis.modules.euc.service.EucMsgService;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.entity.MdmSourceReturn;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.entity.Role;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.service.GmInfoService;
import com.jhmis.modules.sys.utils.UserUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.EUC
 * @Author: hdx
 * @CreateTime: 2019-11-07 09:45
 * @Description: EUC与大客户app相关接口提供 -- 迭代更新
 * 以下接口中service层均已_v1标识
 */
@Api(value = "ApiEucAppIterationController", description = "企业购给大客户app提供EUC相关接口 -- 迭代更新")
@RestController
@RequestMapping("/api/eucapp/iteration")
public class ApiEucAppIterationController extends BaseController {

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
    UploadService uploadService;

    @Autowired
    EucMsgOrderService eucMsgOrderService;
    
    @Autowired
    TGridAreaService tGridAreaService;

    /**
     *@Author: hdx
     *@CreateTime: 2019年12月23日 09:50:43
     *@Description: 提供大客户app的euc抢单列表
     */
    @ApiOperation(notes = "garbList", httpMethod = "POST", value = "提供大客户app的euc抢单列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "中心编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "gridCode", value = "网格编码", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/garbList", method = RequestMethod.POST)
    @SysLog(desc="提供大客户app的euc抢单列表")
    public AjaxJson garbList(String orgId,String gridCode, HttpServletRequest request, HttpServletResponse response) {
        Page<EucMsg> pageEucMsg = null;
        try {
            pageEucMsg = eucMsgService.garbList_v1(orgId,gridCode,request,response);
        } catch (Exception e) {
            logger.error("/garbList 提供大客户app的euc抢单列表异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(pageEucMsg);
    }


    /**
     *@Author: hdx
     *@CreateTime: 2019年12月23日 09:50:43
     *@Description: 提供大客户app的euc抢单接口
     */
    @ApiOperation(notes = "dealerRobtrade", httpMethod = "POST", value = "提供大客户app的euc抢单接口", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eucMsgId", value = "euc需求id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "dealerName", value = "经销商名称", required = true, paramType = "form", dataType = "String"),

    })
    @RequestMapping(value = "/dealerRobtrade", method = RequestMethod.POST)
    @SysLog(desc="提供大客户app的euc抢单接口")
    public AjaxJson dealerRobtrade(String eucMsgId,String dealerCode,String dealerName) {
        //开启锁
        boolean lockMsgId = LockUtils.lock(eucMsgId);
        EucMsgOrder eucMsgOrder = null;
        List<EucMsg> listEucMsg = null;
        try {
            if(lockMsgId) {
                eucMsgOrder = eucMsgService.dealerRobtrade_v1(eucMsgId, dealerCode, dealerName);
            }
        } catch (Exception e) {
            logger.error("/dealerRobtrade 提供大客户app的euc抢单接口异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }finally {
            //释放锁
            LockUtils.unLock(eucMsgId);
        }
        return AjaxJson.ok(eucMsgOrder);
    }


    /**
     *@Author: hdx
     *@CreateTime: 2019年12月23日 09:50:43
     *@Description: 提供大客户app的euc需求选择承接方式接口
     */
    @ApiOperation(notes = "dealerUnderTake", httpMethod = "POST", value = "提供大客户app的euc需求选择承接方式接口", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "undertake", value = "承接方式(0工程单，1零售)", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "dealerName", value = "经销商名称", required = true, paramType = "form", dataType = "String"),

    })
    @RequestMapping(value = "/dealerUnderTake", method = RequestMethod.POST)
    @SysLog(desc="提供大客户app的euc需求选择承接方式接口")
    public AjaxJson dealerUnderTake(String undertake,String dealerCode, String dealerName,String orderId) {
        List<EucMsg> listEucMsg = null;
        try {
            eucMsgService.dealerUnderTake_v1(undertake, dealerCode,dealerName, orderId);
        } catch (Exception e) {
            logger.error("/dealerUnderTake 提供大客户app的euc需求选择承接方式接口异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(EucMsgCode.UNDERTAKE_SUCCESS.getValue());
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 经销商放弃订单
     */
    @ApiOperation(notes = "eucMsgAbandon", httpMethod = "POST", value = "经销商放弃订单", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "abandonType", value = "放弃类型", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "abandonRemark", value = "经销商放弃备注原因", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "abandonWrite", value = "经销商放弃自主录入", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/eucMsgAbandon", method = RequestMethod.POST)
    @SysLog(desc="经销商放弃订单")
    public AjaxJson eucMsgAbandon(String dealerCode,String orderId,String abandonType,String abandonRemark,String abandonWrite) {
        try {
            eucMsgService.eucMsgAbandon_v1(dealerCode, orderId, abandonType,abandonRemark, abandonWrite);
        } catch (Exception e) {
            logger.error("/eucMsgAbandon 经销商放弃订单异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(EucMsgCode.UNDERTAKE_SUCCESS.getValue());
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
    public AjaxJson euc_list(EucMsgOrder eucMsgOrder, HttpServletRequest request, HttpServletResponse response) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || com.jhmis.common.utils.StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        eucMsgOrder.setContractorCode(currentAccount.getLoginName());
        //是否是top用户
        if(StringUtils.isNotBlank(eucMsgOrder.getIfTopCustomer())){
            if("1".equals(eucMsgOrder.getIfTopCustomer())){
                eucMsgOrder.setIfTopCustomer("是");
            }else if("0".equals(eucMsgOrder.getIfTopCustomer())){
                eucMsgOrder.setIfTopCustomer("否");
            }
        }
        Page<EucMsgOrder> p = new Page<EucMsgOrder>(request, response);
        p.setOrderBy(" updateTime desc");
        Page<EucMsgOrder> page = eucMsgOrderService.findPage(p, eucMsgOrder);
        return AjaxJson.layuiTable(page);
    }


    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 查看经销商订单
     */
    @ApiOperation(notes = "dealerOrderList", httpMethod = "POST", value = "查看经销商订单", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "flag", value = "订单标识", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "orgId", value = "中心编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "订单类型", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/dealerOrderList", method = RequestMethod.POST)
    @SysLog(desc="查看经销商订单")
    public AjaxJson dealerOrderList(String dealerCode,String flag,String type,String orgId
            ,HttpServletRequest request, HttpServletResponse response) {
        Page<?> pageEucMsg = null;
        try {
            pageEucMsg = eucMsgService.dealerOrderList_v1(dealerCode, flag, type, request, response);
        } catch (Exception e) {
            logger.error("/dealerOrderList 查看经销商订单异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(pageEucMsg);
    }


    /**
     *@Author: hdx
     *@CreateTime: 17:56 2019/10/8
     *@param file 上传文件
     *@param dealerCode 经销商编码
     *@Description:  经销商上传euc零售见证性材料图片(只图片上传接口，返回对应图片url)
     */
    @ApiOperation(notes = "dealerRetailUploadImg", httpMethod = "POST", value = "经销商上传euc零售见证性材料图片(只图片上传接口，返回对应图片url)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传文件", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/dealerRetailUploadImg", method = RequestMethod.POST)
    @SysLog(desc="经销商上传euc零售见证性材料图片(只图片上传接口，返回对应图片url)")
    public AjaxJson dealerRetailUploadImg(HttpServletRequest request, @ApiParam(name = "任意", value = "文件上传") MultipartFile file, String dealerCode) throws ShopMsgException {
        AjaxJson ret = null;
        AttInfo attInfo = null;
        try {
            ret = uploadService.uploadFiles(request, null, Global.USER_TYPE_PROCESS, dealerCode);
            if(ret.isSuccess()){
                //存在
                attInfo = (AttInfo)((List)ret.getBody().get("result")).get(0);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return AjaxJson.fail("/dealerRetailUploadImg- 经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)异常,原因:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxJson.fail("/dealerRetailUploadImg- 经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)异常,原因:" + e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxJson.fail("/dealerRetailUploadImg- 经销商-零售单上传见证性材料(只图片上传接口，返回对应图片url)异常,原因:" + e.getMessage());
        }
        return AjaxJson.ok(attInfo);
    }


    /**
     *@Author: hdx
     *@CreateTime: 17:56 2019/10/8
     *@param orderId 需求id
     *@param urls 文件图片(多个以逗号隔开)
     *@param dealerCode 经销商编码
     *@param tradeCount 交易金额
     *@Description:  经销商euc-零售单上传见证性材料(图片url+交易金额)
     */
    @ApiOperation(notes = "dealerRetailUpload", httpMethod = "POST", value = "零售单euc上传见证性材料(图片url+交易金额)", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "urls", value = "文件图片(多个以逗号隔开)", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "tradeCount", value = "交易金额", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/dealerRetailUpload", method = RequestMethod.POST)
    @SysLog(desc="经销商上传euc零售见证性材料(图片url+交易金额)")
    public AjaxJson dealerRetailUpload(String orderId,String urls,String dealerCode,String tradeCount) throws EucException {
        try{
            eucMsgService.dealerRetailUpload(orderId,urls, dealerCode, tradeCount);
        }catch (EucException e){
            logger.error("/dealerRetailUpload-经销商上传euc零售见证性材料(图片url+交易金额)异常,原因:" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(ShopMsgCode.getName("MSG_SUCCESS_005"));

    }



    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: 经销商euc-查看经销商订单详情
     */
    @ApiOperation(notes = "dealerOrderDetails", httpMethod = "POST", value = "查看经销商订单详情", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/dealerOrderDetails", method = RequestMethod.POST)
    @SysLog(desc="查看经销商订单详情")
    public AjaxJson dealerOrderDetails(String orderId) {
        EucMsgOrder eucMsgOrder = null;
        try {
            eucMsgOrder = eucMsgService.dealerOrderDetails(orderId);
        } catch (Exception e) {
            logger.error("/dealerOrderDetails 查看经销商订单详情异常，原因=" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(eucMsgOrder);
    }


    /**
     *@Author: hdx
     *@CreateTime: 17:56 2019/10/8
     *@param:
     *@param dealerCode 经销商编码
     *@Description:  经销商euc-根据销商编码返回每个状态的订单数量
     */
    @ApiOperation(notes = "dealerOrderAllStatus", httpMethod = "POST", value = "经销商euc-根据销商编码返回每个状态的订单数量", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping(value = "/dealerOrderAllStatus", method = RequestMethod.POST)
    @SysLog(desc="经销商euc-根据销商编码返回每个状态的订单数量")
    public AjaxJson dealerOrderAllStatus(String dealerCode) {
        List<Integer> listAllStatus = null;
        try {
            listAllStatus = eucMsgService.dealerOrderAllStatus(dealerCode);
        } catch (EucException e) {
            logger.error("/dealerOrderAllStatus-经销商euc-根据销商编码返回每个状态的订单数量异常,原因:" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        //返回每个状态的订单数量成功
        return AjaxJson.ok(listAllStatus);
    }

    /**
     *@Author: hdx
     *@CreateTime: 16:06 2019/9/25
     *@param orgId 工贸id
     *@Description: 获取总监信息
     */
    @ApiOperation(notes = "getDirectorInfo", httpMethod = "POST", value = "获取总监信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "工贸id", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/getDirectorInfo", method = RequestMethod.POST)
    @SysLog(desc="获取总监信息")
    public AjaxJson getDirectorInfo(String orgId) {
        if(StringUtils.isBlank(orgId)){
            return AjaxJson.fail("中心编码不能为空");
        }
        //声明Director用户
        User director = new User();
        //根据工贸查询总监相关信息
        List<User> listUser = UserUtils.getAboutUser(orgId);
        //找到总监(只可有一个总监)
        if(null==listUser || listUser.size()==0){
            logger.error(ShopMsgCode.DIRECT_NOTEXIST_ERROR.getValue());
        }
        //判断是否是总监权限
        for(User u:listUser){
            User full_user = UserUtils.getByLoginName(u.getLoginName());
            if(full_user!=null){
                List<Role> roleList = full_user.getRoleList();
                for(Role role:roleList){
                    if(Constants.DIRECTOR_ROLE_ID.equals(role.getId())){
                        //证明此是总监
                        director = u;
                    }
                }
            }
        }
        return AjaxJson.ok(director);
    }
    
    
    
    /**
     *@Author: hdx
     *@CreateTime: 16:06 2019/9/25
     *@param dealerCode 经销商编码
     *@Description: 获取mdm数据
     */
    @ApiOperation(notes = "getMdmData", httpMethod = "POST", value = "获取mdm数据", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dealerCode", value = "经销商编码", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping(value = "/getMdmData", method = RequestMethod.POST)
    @SysLog(desc="获取mdm数据")
    public AjaxJson getMdmData(String dealerCode) {
    	MdmSourceReturn mdmSourceReturn = MdmSourceApi.addSourceFromMdm(dealerCode);
    	if("S".equals(mdmSourceReturn.getOutRetcode())){
    		MdmCustomersSource mdmCustomersSource = mdmSourceReturn.getMdmCustomersSource();			
    			if(StringUtils.isNotBlank(mdmCustomersSource.getMdmArea())) {
        			TGridArea tGridArea = new TGridArea();
        			tGridArea.setDistrictcode(mdmCustomersSource.getMdmArea());
        			List<TGridArea> tGridAreaList = tGridAreaService.findList(tGridArea);
        			if(tGridAreaList != null && tGridAreaList.size()>0) {
        				tGridArea = tGridAreaList.get(0);
        				mdmCustomersSource.setWgcode(tGridArea.getWgcode());
        				mdmCustomersSource.setWgname(tGridArea.getWgname());
        			}
    			}       			
    		return AjaxJson.ok(mdmCustomersSource);
    	}else{
    		return AjaxJson.fail(mdmSourceReturn.getOutRetmsg());
    	}
    }
    
    
    
}
