package com.jhmis.api.customer;

import com.haier.http.client.hps.HpsUploadApi;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.service.GmInfoService;
import com.jhmis.view.MdmView;
import com.haier.webservices.client.hps.exception.HpsExceptionApi;
import com.haier.webservices.client.hps.verify.*;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.customer.entity.*;
import com.jhmis.modules.customer.service.*;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.Exception;
import java.util.*;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 10:56 2019/4/17
 * @Modified By
 */
@Api(value = "ApiVerifyController", description = "客单验收")
@RestController
@RequestMapping("/api/verify")
public class ApiVerifyController {
    protected Logger logger = LoggerFactory.getLogger(ApiVerifyController.class);

    @Autowired
    protected CustomerMsgService customerMsgService;

    @Autowired
    protected ProductGroupService productGroupService;

    @Autowired
    protected CustomerProjectInfoService customerProjectInfoService;

    @Autowired
    protected CustomerProjectProductService customerProjectProductService;

    @Autowired
    protected CustomerProjectProductDetailService customerProjectProductDetailService;

    @Autowired
    protected AreaCodeService areaCodeService;

    @Autowired
    protected  IndustryCodeService industryCodeService;

    @Autowired
    protected DealerService dealerService;

    @Autowired
    protected ViewQygBrownItemService viewQygBrownItemService;

    @Autowired
    protected ViewQygBrownService viewQygBrownService;

    @Autowired
    protected ViewQygBrownInfoService viewQygBrownInfoService;

    @Autowired
    protected ViewQygBrownExceptionInfoService viewQygBrownExceptionInfoService;
    
    @Autowired
    protected GmInfoService gmInfoService;
    /**
     * 工程版信息获取接口
     * @return
     */
    @ApiOperation(notes = "queryPageForVerify", httpMethod = "POST", value = "工程版信息获取接口", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/queryPageForVerify")
    public AjaxJson queryPageForVerify(String brownCode,HttpServletRequest request, HttpServletResponse response){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /report  工程版信息获取接口----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        //参数验证
        if(StringUtils.isBlank(pageNo)||StringUtils.isBlank(pageSize)){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /report  工程版信息获取接口----------分页参数异常*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("分页参数异常");
        }
        String dealerCode = currentAccount.getLoginName();
        //调用接口
        ResInfoBrown res = HpsVerifyApi.queryPageForVerifyFromHPS(Integer.valueOf(pageNo),Integer.valueOf(pageSize),dealerCode,brownCode);
        if(res==null){
            return AjaxJson.fail("未查到工程板信息");
        }
        PageableVo pageableVo = new PageableVo();
        //返回值类型
        if(HpsVerifyApi.RESINFOBROWN_CODE_Y.equals(res.getCode())){
            logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /report  工程版信息获取接口----------调用接口成功 返回Y *_*_*_*_*_*_*_*_*_*");
            pageableVo = res.getBrownPage();
        }else{
            logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /report  工程版信息获取接口----------调用接口成功 返回非Y*_*_*_*_*_*_*_*_*_*");
            String msg = res.getMsg();
            return AjaxJson.fail(msg);
        }
        Page<Object> pageableVoPage = new Page<Object>();
        List<Object> obj = (List<Object>)pageableVo.getList();
        pageableVoPage.setList(obj);
        pageableVoPage.setCount(pageableVo.getTotalCount());
        return AjaxJson.layuiTable(pageableVoPage);
    }

    /**
     * 根据工程版ID获取基础信息
     * @return
     */
    @ApiOperation(notes = "getVerifyBillFromBrown", httpMethod = "POST", value = "根据工程版ID获取基础信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/getVerifyBillFromBrown")
    public AjaxJson queryPageForVerify(String id){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getVerifyBillFromBrown  根据工程版ID获取基础信息----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //参数验证
        if(StringUtils.isEmpty(id)){
            logger.info("*_*_*_*_*_*_*_*_*_* id为空 *_*_*_*_*_*_*_*_*_*");
        }
        //调用接口
        ResInfoVerifyInfo resInfoVerifyInfo = HpsVerifyApi.getVerifyBillFromBrownFromHPS(id);
        //判断是否有效
        if(resInfoVerifyInfo==null){
            return AjaxJson.fail("数据异常,请联系管理员");
        }
   
        return AjaxJson.ok(resInfoVerifyInfo);
    }

    /**
     * 验收及申诉接口
     * @return
     */
    @ApiOperation(notes = "saveOrUpdate", httpMethod = "POST", value = "验收及申诉接口", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/saveOrUpdate")
    public AjaxJson saveOrUpdate(VerifyBillSaveVo verifyBillSaveVo){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /saveOrUpdate  验收及申诉接口----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //调用接口
        //参数类型转化
        //根据工程版查询其参数信息
        ViewQygBrown viewQygBrown = new ViewQygBrown();
        viewQygBrown.setBrownId(verifyBillSaveVo.getCbillId());
        List<ViewQygBrown> listViewQygBrown = viewQygBrownService.findList(viewQygBrown);
        ViewQygBrown v = null;
        if(listViewQygBrown==null || listViewQygBrown.size()==0){
            return AjaxJson.fail("此工程版不存在");
        }
        v = listViewQygBrown.get(0);
        GmInfo gmInfo = gmInfoService.findUniqueByProperty("branch_code",v.getCenter());
        v.setCenterName(gmInfo.getGmName());
  
        ViewQygBrownInfo viewQygBrownInfo = new ViewQygBrownInfo();
        viewQygBrownInfo.setProjectCode(v.getProjectCode());
        List<ViewQygBrownInfo> listViewQygBrownInfo = viewQygBrownInfoService.findList(viewQygBrownInfo);
        if(listViewQygBrownInfo==null || listViewQygBrownInfo.size()<=0){
            return AjaxJson.fail("不存在该验收单");
        }
        viewQygBrownInfo = listViewQygBrownInfo.get(0);
        ViewQygBrownItem viewQygBrownItem = new ViewQygBrownItem();
        viewQygBrownItem.setBrownId(verifyBillSaveVo.getCbillId());
        List<ViewQygBrownItem> listViewQygBrownItem = viewQygBrownItemService.findList(viewQygBrownItem);
        ResInfo resInfo = HpsVerifyApi.saveOrUpdateFromHPS(verifyBillSaveVo,v,viewQygBrownInfo,listViewQygBrownItem);
        //判断是否有效
        if(resInfo==null){
            return AjaxJson.fail("数据异常,请联系管理员");
        }
        return AjaxJson.ok(resInfo);
    }

    /**
     * 上传文件
     * @return
     */
    @ApiOperation(notes = "uploadFile", httpMethod = "POST", value = "上传文件", consumes = "multipart/*")
    @ApiImplicitParams({
    })
    @PostMapping("uploadFile")
    public AjaxJson uploadFile(HttpServletRequest request, @ApiParam(name = "任意", value = "文件上传")MultipartFile file){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /uploadFile  上传文件----------接口开始*_*_*_*_*_*_*_*_*_*");
        //调用接口
        Map<String,Object> map = new HashMap<>();
        try {
            File uploadFile = MultipartFileToFile(file);
            map = HpsUploadApi.hpsUploadApi(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判断是否有效
        return AjaxJson.ok(map);
    }

    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用当前时间作为文件名，防止生成的临时文件重复
        try {
            File file = File.createTempFile(System.currentTimeMillis() + "", prefix);

            multiFile.transferTo(file);

            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据hps工程版视图查询
     * @return
     */
    @ApiOperation(notes = "getviewQYGBrown", httpMethod = "GET", value = "根据hps工程版视图查询", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/getviewQYGBrown")
    public AjaxJson getviewQYGBrown(String brownId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getviewQYGBrownItem  根据hps工程版视图查询----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //调用接口
        ViewQygBrown viewQygBrown = new ViewQygBrown();
        viewQygBrown.setId(brownId);
        List<ViewQygBrown> listViewQygBrown = viewQygBrownService.findList(viewQygBrown);
        //判断是否有效
        if(listViewQygBrown==null || listViewQygBrown.size()==0){
            return AjaxJson.fail("数据为空");
        }
        return AjaxJson.ok(listViewQygBrown);
    }


    /**
     * 根据hps工程版详情视图查询
     * @return
     */
    @ApiOperation(notes = "getviewQYGBrownItem", httpMethod = "GET", value = "根据hps工程版详情视图查询", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/getviewQYGBrownItem")
    public AjaxJson getviewQYGBrownItem(String brownId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getviewQYGBrownItem  根据hps工程版详情视图查询----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //调用接口
        ViewQygBrownItem viewQygBrownItem = new ViewQygBrownItem();
        viewQygBrownItem.setBrownId(brownId);
        List<ViewQygBrownItem> listViewQygBrownItem = viewQygBrownItemService.findList(viewQygBrownItem);
        //判断是否有效
        if(listViewQygBrownItem==null || listViewQygBrownItem.size()==0){
            return AjaxJson.fail("数据为空");
        }
        return AjaxJson.ok(listViewQygBrownItem);
    }


    /**
     * 异常报备
     * @return
     */
    @ApiOperation(notes = "createBrownException", httpMethod = "POST", value = "异常报备", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/createBrownException")
    public AjaxJson createBrownException(BrownException brownException){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /saveOrUpdate  异常报备----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        ViewQygBrownItem viewQygBrownItem = new ViewQygBrownItem();
        viewQygBrownItem.setBrownId(brownException.getBrownId());
        List<ViewQygBrownItem> listViewQygBrownItem = viewQygBrownItemService.findList(viewQygBrownItem);
        //调用接口
        String  resInfo = HpsExceptionApi.createBrownExceptionFromHps(brownException,listViewQygBrownItem);
        //判断是否有效
        if("".equals(resInfo)){
            return AjaxJson.fail("数据异常,请联系管理员");
        }
        return AjaxJson.ok(resInfo);
    }

    /**
     * 获取经销商所有工程版
     * @return
     */
    @ApiOperation(notes = "getViewQygBrown", httpMethod = "POST", value = "获取经销商所有工程版", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/getViewQygBrown")
    public AjaxJson getViewQygBrown(ViewQygBrown viewQygBrown,HttpServletRequest request, HttpServletResponse response){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getViewQygBrown  获取经销商所有工程版----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        String dealerCode = currentAccount.getLoginName();
        //String dealerCode = "8800167101";
        viewQygBrown.setDealerCode(dealerCode);

        if(!"".equals(viewQygBrown.getEffectTimeStr())&& null!= viewQygBrown.getEffectTimeStr()){
            //证明时间筛选存在
            String[] timeStr = viewQygBrown.getEffectTimeStr().split(" - ");
            viewQygBrown.setEffectTimeStart(timeStr[0]);
            viewQygBrown.setEffectTimeEnd(timeStr[1]);
        }
        if(!"".equals(viewQygBrown.getExpireTimeStr())&& null!= viewQygBrown.getExpireTimeStr()){
            //证明时间筛选存在
            String[] timeStr = viewQygBrown.getExpireTimeStr().split(" - ");
            viewQygBrown.setExpireTimeStart(timeStr[0]);
            viewQygBrown.setExpireTimeEnd(timeStr[1]);
        }

        Page<ViewQygBrown> page = new Page<ViewQygBrown>(request, response);
        Page<ViewQygBrown> pageViewQygBrown = viewQygBrownService.findPage(page, viewQygBrown);
        return AjaxJson.layuiTable(pageViewQygBrown);
    }

    /**
     * 获取经销商所有工程版详情
     * @return
     */
    @ApiOperation(notes = "getBrownDetails", httpMethod = "POST", value = "获取经销商所有工程版的详情", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/getBrownDetails")
    public AjaxJson getBrownDetails(ViewQygBrownItem viewQygBrownItem,HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getBrownDetails  获取经销商所有工程版的详情----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        List<ViewQygBrownItem> listViewQygBrownItem = viewQygBrownItemService.findList(viewQygBrownItem);
        if(listViewQygBrownItem==null || listViewQygBrownItem.size()==0){
            //证明不存在此工程版详情报出异常
            return AjaxJson.fail("此工程版详细信息不存在");
        }
        //查询功臣版信息和工程版信息详情
        ViewQygBrown viewQygBrown = viewQygBrownService.get(viewQygBrownItem.getBrownId());
        //查询中心名称
        if(viewQygBrown!=null){
            GmInfo gmInfo = gmInfoService.findUniqueByProperty("branch_code",viewQygBrown.getCenter());
            viewQygBrown.setCenterName(gmInfo.getGmName());
        }

        map.put("viewQygBrownItem",listViewQygBrownItem);
        map.put("viewQygBrown",viewQygBrown);
        return AjaxJson.ok(map);
    }

    /**
     * 获取经销商所有可验收工程版列表
     * @return
     */
    @ApiOperation(notes = "getBrownInfo", httpMethod = "POST", value = "获取经销商所有可验收工程版列表", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/getBrownInfo")
    public AjaxJson getBrownInfo(ViewQygBrownInfo viewQygBrownInfo,HttpServletRequest request, HttpServletResponse response){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getBrownInfo  获取经销商所有可验收工程版列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        String dealerCode = currentAccount.getLoginName();
        //String dealerCode = "8800167101";
        viewQygBrownInfo.setDealerCode(dealerCode);
        Page<ViewQygBrownInfo> page = new Page<ViewQygBrownInfo>(request, response);
        Page<ViewQygBrownInfo> pageViewQygBrown = viewQygBrownInfoService.findPage(page, viewQygBrownInfo);
        return AjaxJson.layuiTable(pageViewQygBrown);
    }
    
    /**
     * 获取经销商所有可异常报备工程版列表
     * @return
     */
    @ApiOperation(notes = "getBrownExceptionInfo", httpMethod = "POST", value = "获取经销商所有可验收工程版列表", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/getBrownExceptionInfo")
    public AjaxJson getBrownExceptionInfo(ViewQygBrownExceptionInfo viewQygBrownExceptionInfo,HttpServletRequest request, HttpServletResponse response){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getBrownExceptionInfo  获取经销商所有可验收工程版列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        String dealerCode = currentAccount.getLoginName();
        viewQygBrownExceptionInfo.setDealerCode(dealerCode);
        Page<ViewQygBrownExceptionInfo> page = new Page<ViewQygBrownExceptionInfo>(request, response);
        Page<ViewQygBrownExceptionInfo> pageViewQygBrown = viewQygBrownExceptionInfoService.findPage(page, viewQygBrownExceptionInfo);
        return AjaxJson.layuiTable(pageViewQygBrown);
    }

    /**
     * 根据id获取经销商工程版信息
     * @return
     */
    @ApiOperation(notes = "getBrownInfoById", httpMethod = "GET", value = "根据id获取经销商工程版信息", consumes = "application/x-www-form-urlencoded")
    @RequestMapping("/getBrownInfoById")
    public AjaxJson getBrownInfoById(String id,HttpServletRequest request, HttpServletResponse response){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiVerifyController  /getBrownInfo  根据id获取经销商工程版信息----------接口开始*_*_*_*_*_*_*_*_*_*");
        if(StringUtils.isEmpty(id)){
            return AjaxJson.fail("参数不能为空");
        }
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        ViewQygBrownInfo viewQygBrownInfo = viewQygBrownInfoService.get(id);
        if(viewQygBrownInfo==null){
            logger.info("*_*_*_*_*_*_*_*_*_* viewQygBrownInfo为空 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("此工程板信息不存在");
        }
        return AjaxJson.ok(viewQygBrownInfo);
    }
}
