package com.jhmis.api.customer;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.user.api.ProductsApi;
import com.haier.user.api.hps.MyProject;
import com.haier.webservices.client.hps.project.HpsApi;
import com.haier.webservices.client.hps.project.LockUserVO;
import com.haier.webservices.client.hps.project.ProjectDetailInfoQYGVO;
import com.haier.webservices.client.hps.project.ProjectDetailInfoVO;
import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 10:56 2019/4/17
 * @Modified By
 */
@Api(value = "ApiCustomerController", description = "客单需求相关信息")
@RestController
@RequestMapping("/api/customer")
public class ApiCustomerController {
    protected Logger logger = LoggerFactory.getLogger(ApiCustomerController.class);

    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;
    
    @Autowired
    protected CustomerMsgService customerMsgService;

    @Autowired
    protected ProductGroupService productGroupService;

    @Autowired
    protected CustomerProjectInfoService customerProjectInfoService;
@Autowired
    CustomerCommentService customerCommentService;

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
    protected CustomerMsgProductService customerMsgProductService;

    @Autowired
    GmInfoService gmInfoService;
    
    @Autowired
    OpportunityPointUserGroupsService opportunityPointUserGroupsService;
    
    @Autowired
    IndustryProductsService industryProductsService;
    
    @Autowired
    ProductSkService productSkService;
    
    @Autowired
    OpportunityPointSkService opportunityPointSkService;
    
    @Autowired
    CustomerQuotesService  customerQuotesService;
    
    @Autowired
    protected ViewQygProjectdetailviewdateService viewQygProjectdetailviewdateService;
   
    
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
        Dealer d = dealerService.get(currentAccount.getDealerId());
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        if(StringUtils.isEmpty(customerMsg.getProjectName())||StringUtils.isEmpty(customerMsg.getFirstCompanyName())||StringUtils.isEmpty(customerMsg.getAddressCity())||StringUtils.isEmpty(customerMsg.getAddressProvince())||StringUtils.isEmpty(customerMsg.getAddressCounty())||StringUtils.isEmpty(customerMsg.getProjectCreaterCode())){
            logger.info("*_*_*_*_*_*_*_*_*_* 参数错误 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数错误！");
        }
        if(StringUtils.isNotBlank(customerMsg.getMsgId())){
            logger.info("*_*_*_*_*_*_*_*_*_* 该项目不能重复提交*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("该项目不能重复提交！");
        }
        if(StringUtils.isNotBlank(customerMsg.getProjectId())){
        	logger.info("*_*_*_*_*_*_*_*_*_* 该项目不能重复提交*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("该项目不能重复提交！");
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
        //不管是否存在都入库，并完善信息
        customerMsg.setAddTime(new Date());
        customerMsg.setContractorCode(currentAccount.getLoginName());
        customerMsg.setContractorName(d.getCompanyName());
        customerMsg.setDomainModel(CustomerMsg.CUSTOMER_ORDER);
        customerMsg.setMsgId(getOrderIdByTime());
        customerMsg.setIsDel("0");
        customerMsg.setUserId(currentAccount.getId());
        customerMsg.setProjectManagerCode(customerMsg.getProjectCreaterCode());
        Map<String,Object> map =  HpsApi.SaveProjectFromQYG(customerMsg);

        
 	    //处理省市区名称
        AreaCode areaCode = new AreaCode();
        //省市区名称
        areaCode = areaCodeService.get(customerMsg.getAddressProvince());
        customerMsg.setAddressProvinceName(areaCode.getCityName());
        areaCode = areaCodeService.get(customerMsg.getAddressCity());
        customerMsg.setAddressCityName(areaCode.getCityName());
        areaCode = areaCodeService.get(customerMsg.getAddressCounty());
        customerMsg.setAddressCountyName(areaCode.getCityName());
        //处理工贸
        if(!"".equals(customerMsg.getCenter())){
            GmInfo gmInfo = gmInfoService.findUniqueByProperty("branch_code",customerMsg.getCenter());
            customerMsg.setCenterName(gmInfo.getGmName());
        }
        //处理是否成功
        String flag = (String) map.get("flag");
        //处理省市区名称
       if("error".equals(flag)){
           //error代表漏斗返回失败
           customerMsg.setStatus("0");
           //填写失败原因
           String errorMsg = (String) map.get("errorMsg");
           customerMsg.setErrorMsg(errorMsg);
           logger.info("*_*_*_*_*_*_*_*_*_* 调用失败，失败原因"+ errorMsg  +" (msgId= " + customerMsg.getMsgId() + ")*_*_*_*_*_*_*_*_*_*");
           customerMsgService.save(customerMsg);
           
           return AjaxJson.fail(errorMsg);
       }else if("success".equals(flag)){   	   
           //success代表漏斗返回成功
           String projectCode = (String) map.get("projectCode");
           String msgId = (String) map.get("msgId");
           customerMsg.setProjectId(projectCode);
           customerMsg.setStatus("1");
           customerMsgService.save(customerMsg);
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
     * 失败项目再次报备需求
     * @return
     */
    @ApiOperation(notes = "failReportTwo", httpMethod = "POST", value = "失败项目再次报备需求", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping("/failReportTwo")
    public AjaxJson failReportTwo(CustomerMsg customerMsg){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /failReportTwo  失败项目再次报备需求----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        Dealer d = dealerService.get(currentAccount.getDealerId());
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        if(StringUtils.isEmpty(customerMsg.getProjectName())||StringUtils.isEmpty(customerMsg.getFirstCompanyName())||StringUtils.isEmpty(customerMsg.getAddressCity())||StringUtils.isEmpty(customerMsg.getAddressProvince())||StringUtils.isEmpty(customerMsg.getAddressCounty())||StringUtils.isEmpty(customerMsg.getProjectCreaterCode())){
            logger.info("*_*_*_*_*_*_*_*_*_* 参数错误 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数错误！");
        }
        if(StringUtils.isBlank(customerMsg.getId())){
            logger.info("*_*_*_*_*_*_*_*_*_* 参数id缺失*_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("参数id缺失！");
        }else {
        	CustomerMsg msg = customerMsgService.get(customerMsg.getId());
        	if(msg==null) {
        		logger.info("*_*_*_*_*_*_*_*_*_* 该项目不能重新提交*_*_*_*_*_*_*_*_*_*");
                return AjaxJson.fail("该项目不能重新提交！");
        	}else {
        		if(StringUtils.isBlank(msg.getMsgId())){
        			return AjaxJson.fail("请联系企业购管理员！");
        		}
        		customerMsg.setMsgId(msg.getMsgId());
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
        //不管是否存在都入库，并完善信息
        customerMsg.setAddTime(new Date());
        customerMsg.setContractorCode(currentAccount.getLoginName());
        customerMsg.setContractorName(d.getCompanyName());
        customerMsg.setDomainModel(CustomerMsg.CUSTOMER_ORDER);
        customerMsg.setIsDel("0");
        customerMsg.setUserId(currentAccount.getId());
        customerMsg.setProjectManagerCode(customerMsg.getProjectCreaterCode());
        Map<String,Object> map =  HpsApi.SaveProjectFromQYG(customerMsg);      
 	    //处理省市区名称
        AreaCode areaCode = new AreaCode();
        //省市区名称
        areaCode = areaCodeService.get(customerMsg.getAddressProvince());
        customerMsg.setAddressProvinceName(areaCode.getCityName());
        areaCode = areaCodeService.get(customerMsg.getAddressCity());
        customerMsg.setAddressCityName(areaCode.getCityName());
        areaCode = areaCodeService.get(customerMsg.getAddressCounty());
        customerMsg.setAddressCountyName(areaCode.getCityName());
        //处理工贸
        if(!"".equals(customerMsg.getCenter())){
            GmInfo gmInfo = gmInfoService.findUniqueByProperty("branch_code",customerMsg.getCenter());
            customerMsg.setCenterName(gmInfo.getGmName());
        }
        //处理是否成功
        String flag = (String) map.get("flag");
        //处理省市区名称
       if("error".equals(flag)){
           //error代表漏斗返回失败
           customerMsg.setStatus("0");
           //填写失败原因
           String errorMsg = (String) map.get("errorMsg");
           customerMsg.setErrorMsg(errorMsg);
           logger.info("*_*_*_*_*_*_*_*_*_* 调用失败，失败原因"+ errorMsg  +" (msgId= " + customerMsg.getMsgId() + ")*_*_*_*_*_*_*_*_*_*");
           customerMsgService.saveR(customerMsg);
           
           return AjaxJson.fail(errorMsg);
       }else if("success".equals(flag)){   	   
           //success代表漏斗返回成功
           String projectCode = (String) map.get("projectCode");
           String msgId = (String) map.get("msgId");
           customerMsg.setProjectId(projectCode);
           customerMsg.setStatus("1");
           customerMsgService.saveR(customerMsg);
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
 
    @ApiOperation(notes = "findData", httpMethod = "GET", value = "获取需求信息")
    @RequestMapping("/findData")
    public AjaxJson findData(String id, String projectCode){

    	CustomerMsg customerMsg = new CustomerMsg();
    	
    	if(StringUtils.isNotBlank(projectCode)){
    		customerMsg = customerMsgService.getByProjectId(projectCode);
    		if(customerMsg == null) {
    			return AjaxJson.fail("该项目不允许编辑！");
    		}
        }else if(StringUtils.isNotBlank(id)) {
        	customerMsg = customerMsgService.get(id);
        	
        }else {
        	return AjaxJson.fail("参数有误");
        }
    	String msgId = customerMsg.getMsgId();
    	CustomerQuotes customerQuotes = new CustomerQuotes();
    	customerQuotes.setMsgId(msgId);
    	List<CustomerQuotes> quotes = customerQuotesService.findList(customerQuotes);
    	customerMsg.setQuotes(quotes);
    	
    	CustomerMsgProduct customerMsgProduct = new CustomerMsgProduct();
    	customerMsgProduct.setCustomerMsgId(msgId);
    	List<CustomerMsgProduct> customerMsgProducts = customerMsgProductService.findList(customerMsgProduct);
    	customerMsg.setListCustomerMsgProduct(customerMsgProducts);
    	
    	
    	return AjaxJson.ok(customerMsg);
    }
    

    /**
     * 获取产品组列表
     * @param
     * @return
     */
    @ApiOperation(notes = "getProductGroup", httpMethod = "GET", value = "获取产品组列表")
    @RequestMapping("getProductGroup")
    public AjaxJson  getProductGroup(){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /getProductGroup  获取产品组列表----------接口开始*_*_*_*_*_*_*_*_*_*");
        //获取所有产品组列表
        List<ProductGroup> productGroupList = productGroupService.findList(new ProductGroup());
        if(productGroupList==null || productGroupList.size()==0){
            logger.info("*_*_*_*_*_*_*_*_*_* 数据不存在 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("数据不存在");
        }
        return AjaxJson.ok(productGroupList);
    }

    /**
     * 获取行业大类门类
     * @param
     * @return
     */
    @ApiOperation(notes = "getIndustry", httpMethod = "GET", value = "获取行业大类门类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "level", value = "等级(1是行业门类 2是行业大类)", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping("getIndustry")
    public AjaxJson  getIndustry(IndustryCode industryCode){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /getIndustry  获取行业大类门类----------接口开始*_*_*_*_*_*_*_*_*_*");
        Page page = new Page();
        page.setOrderBy("order_number");
        industryCode.setPage(page);
        //获取所有产品组列表
        List<IndustryCode> listIndustryCode = industryCodeService.findList(industryCode);
        if(listIndustryCode==null || listIndustryCode.size()==0){
            logger.info("*_*_*_*_*_*_*_*_*_* 数据不存在 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("数据不存在");
        }
        return AjaxJson.ok(listIndustryCode);
    }

    /**
     * 获取行业门类
     * @param
     * @return
     */
    @ApiOperation(notes = "getIndustryCategory", httpMethod = "GET", value = "获取行业门类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentCode", value = "父级id", required = true, paramType = "form", dataType = "String"),
    })
    @RequestMapping("getIndustryCategory")
    public AjaxJson  getIndustryCategory(IndustryCode industryCode){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /getIndustry  获取行业大类门类----------接口开始*_*_*_*_*_*_*_*_*_*");
        //获取所有产品组列表
        List<IndustryCode> listIndustryCode = industryCodeService.findList(industryCode);
        if(listIndustryCode==null || listIndustryCode.size()==0){
            logger.info("*_*_*_*_*_*_*_*_*_* 数据不存在 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("数据不存在");
        }
        return AjaxJson.ok(listIndustryCode);
    }


    /**
     * 获取需求单
     * @param
     * @return
     */
    @ApiOperation(notes = "getCustomerMsg", httpMethod = "GET", value = "获取用户所有需求单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态（1是 成功进入漏斗(有效)  0是 没有进入漏斗（无效））", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "projectName", value = "项目名称", required = false, paramType = "form", dataType = "String")
    })
    @RequestMapping("/getCustomerMsg")
    public AjaxJson  getCustomerMsg(CustomerMsg customerMsg, HttpServletRequest request, HttpServletResponse response){
    	logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /getCustomerMsg  获取用户所有需求单----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }   
//        if(!"".equals(customerMsg.getAllTime())&& null!= customerMsg.getAllTime()){
//            //证明时间筛选存在
//            String[] timeStr = customerMsg.getAllTime().split(" - ");
//            customerMsg.setStartTime(timeStr[0]);
//            customerMsg.setEndTime(timeStr[1]);
//        }
        
        if(StringUtils.isNotBlank(customerMsg.getStartTime())){
        	if(StringUtils.isBlank(customerMsg.getEndTime())){
        		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		Date date = new Date();
                customerMsg.setEndTime(ft.format(date));
            }           
        }
        if(StringUtils.isNotBlank(customerMsg.getEndTime())){
        	if(StringUtils.isBlank(customerMsg.getStartTime())){
        		customerMsg.setStartTime("2019-05-16 14:06:26");
        	}
        }   
        customerMsg.setUserId(currentAccount.getId());
        Page<CustomerMsg> pageCustomerMsg = customerMsgService.findPage(new Page<CustomerMsg>(request, response), customerMsg);	
        return AjaxJson.layuiTable(pageCustomerMsg);
    }

    /**
     * 校验项目名称是否重复(已于2019年6月16日 王海欧通知废弃掉)
     * @param  projectName
     * @return
     */
    @ApiOperation(notes = "checkProjectName", httpMethod = "GET", value = "校验项目名称是否重复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectName", value = "项目名称", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("checkProjectName")
    public AjaxJson  checkProjectName(@Param("projectName") String projectName){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /checkProjectName  校验项目名称是否重复----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        if(StringUtils.isBlank(projectName)){
            return AjaxJson.fail("参数不能为空");
        }
        logger.info("*_*_*_*_*_*_*_*_*_*  DealerAccount currentAccount = DealerUtils.getDealerAccount(); *_*_*_*_*_*_*_*_*_*");
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        logger.info("*_*_*_*_*_*_*_*_*_*  CustomerMsg customerMsg = new CustomerMsg(); *_*_*_*_*_*_*_*_*_*");
        //校验是否存在此项目名称内容
        CustomerMsg customerMsg = new CustomerMsg();
        customerMsg.setProjectName(projectName);
        customerMsg.setStatus("1");
        List<CustomerMsg> listCustomerMsg = customerMsgService.findList(customerMsg);
        if(listCustomerMsg!=null && listCustomerMsg.size()>0){
            logger.info("*_*_*_*_*_*_*_*_*_* 项目名称重复 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("项目名称重复");
        }
        return AjaxJson.ok("校验成功");
    }


    /**
     * 根据企业购订单信息查询漏斗相关订单信息
     * @param
     * @return
     */
    @ApiOperation(notes = "getHpsOrder", httpMethod = "GET", value = "根据企业购订单信息查询漏斗相关订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerId", value = "需求id", required = true, paramType = "form", dataType = "String")
    })
    @RequestMapping("getHpsOrder")
    public AjaxJson  getHpsOrder(@Param("customerId") String customerId){
        logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /getHpsOrder  根据企业购订单信息查询漏斗相关订单信息----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        //通过id查询具体信息
        CustomerMsg customerMsg = customerMsgService.get(customerId);
        if(customerMsg==null){
            return AjaxJson.fail("当前需求不存在");
        }
        //查询所属行业
        String industry_home_category_code = customerMsg.getIndustryHomeCategory();
        String industry_category_code = customerMsg.getIndustryCategory();
        IndustryCode industryCode_home = industryCodeService.findUniqueByProperty("code",industry_home_category_code);
        IndustryCode industry_category = industryCodeService.findUniqueByProperty("code",industry_category_code);
        if(industryCode_home!=null){
            customerMsg.setIndustryHomeCategoryName(industryCode_home.getName());
        }
        if(industry_category!=null){
            customerMsg.setIndustryCategoryName(industry_category.getName());
        }
        //有效则查询漏斗项目
        CustomerProjectInfo customerProjectInfo = customerProjectInfoService.findUniqueByProperty("msg_id",customerMsg.getMsgId());
        //查询所有产品组
        CustomerMsgProduct customerMsgProduct = new CustomerMsgProduct();
        customerMsgProduct.setCustomerMsgId(customerMsg.getMsgId());
        List<CustomerMsgProduct> listCustomerMsgProduct = customerMsgProductService.findList(customerMsgProduct);
        for(CustomerMsgProduct cmp : listCustomerMsgProduct){
            ProductGroup productGroup = productGroupService.findUniqueByProperty("product_group_code",cmp.getProductGroup());
            if(null!=productGroup){
                cmp.setProductGroupName(productGroup.getProductGroupName());
            }
        }
        customerMsg.setListCustomerMsgProduct(listCustomerMsgProduct);
        //如果存在.查询是否有效
        if("0".equals(customerMsg.getStatus())){
            //无效
            return AjaxJson.ok(customerMsg);
        }
        if(customerProjectInfo!=null){
            //漏斗产品信息
            CustomerProjectProduct customerProjectProduct = new CustomerProjectProduct();
            customerProjectProduct.setProjectcode(customerProjectInfo.getProjectcode());
            //漏斗产品详情
            CustomerProjectProductDetail customerProjectProductDetail = new CustomerProjectProductDetail();
            customerProjectProductDetail.setProjectcode(customerMsg.getMsgId());
            List<CustomerProjectProductDetail> listCustomerProjectProductDetail =  customerProjectProductDetailService.findList(customerProjectProductDetail);
            customerMsg.setCustomerProjectInfo(customerProjectInfo);
            customerMsg.setListCustomerProjectProductDetail(listCustomerProjectProductDetail);
        }

        return AjaxJson.ok(customerMsg);
    }

    /**
     * 生成订单编号 如20190925102763xxx
     * @return
     */
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
    
    /**
     * 获取机会点
     * @return
     */
    @ApiOperation(notes = "getOpportunityPoint", httpMethod = "GET", value = "获取机会点")
    @RequestMapping("/getOpportunityPoint")
    public List<OpportunityPointUserGroups> getOpportunityPoint(){
    	OpportunityPointUserGroups opportunityPoint = new OpportunityPointUserGroups();
    	opportunityPoint.setType("1");
    	List<OpportunityPointUserGroups> opportunityPoints = opportunityPointUserGroupsService.findList(opportunityPoint);   	
    	return opportunityPoints;
    }
    
    
    
    
    /**
     * 获取商空机会点
     * @return
     */
    @ApiOperation(notes = "getSKOpportunityPoint", httpMethod = "GET", value = "获取机会点")
    @RequestMapping("/getSKOpportunityPoint")
    public List<OpportunityPointSk> getSKOpportunityPoint(@RequestParam(value = "parentID", required = false)String parentID){
    	OpportunityPointSk opportunityPoint = new OpportunityPointSk();
    	if(StringUtils.isNotBlank(parentID)){
    		opportunityPoint.setParentid(parentID);
    	}    	
    	List<OpportunityPointSk> opportunityPoints = opportunityPointSkService.findList(opportunityPoint);   	
    	return opportunityPoints;
    }
    
    
    /**
     * 根据机会点获取用户群组
     * @return
     */
    @ApiOperation(notes = "getUserGroups", httpMethod = "GET", value = "根据机会点获取用户群组")
    @RequestMapping("/getUserGroups")
    public List<OpportunityPointUserGroups> getUserGroups(String code){
    	OpportunityPointUserGroups opportunityPoint = new OpportunityPointUserGroups();
    	opportunityPoint.setType("2");
    	opportunityPoint.setFatherCode(code);
    	List<OpportunityPointUserGroups> opportunityPoints = opportunityPointUserGroupsService.findList(opportunityPoint);   	
    	return opportunityPoints;
    }

    
    /**
     * 获取工程需求申请列表 
     * @throws ParseException 
     */
    @ApiOperation(notes = "customerDemandList", httpMethod = "GET", value = "获取工程需求申请列表")
    @RequestMapping("/customerDemandList")
    public  AjaxJson customerDemandList(CustomerSearch customerSearch, HttpServletRequest request, HttpServletResponse response) throws ParseException{
    	
    	logger.info("*_*_*_*_*_*_*_*_*_* ApiCustomerController  /getCustomerMsg  获取用户所有需求单----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }
        
        if(StringUtils.isBlank(customerSearch.getCusCode())){
        	return AjaxJson.fail("8码不能为空");
        }
    	
        ProjectDetailInfoQYGVO  projectDetailInfoQYGVO = HpsApi.projectDetailInfoList(customerSearch);
        if(projectDetailInfoQYGVO == null){
        	return AjaxJson.fail("无数据");
        }
        if(projectDetailInfoQYGVO.getProjectDetailInfoVOList() == null || projectDetailInfoQYGVO.getProjectDetailInfoVOList().size()==0){
    		return AjaxJson.fail("无数据");
    	}
        List<ProjectDetailInfoVO> projectDetailInfoList = projectDetailInfoQYGVO.getProjectDetailInfoVOList();
    	List<CustomerDemand> customerDemands = new ArrayList<CustomerDemand>();
    	
    	for(ProjectDetailInfoVO projectDetailInfoVO : projectDetailInfoList){
    		CustomerDemand customerDemand = new CustomerDemand();
    		customerDemand.setMsgId(projectDetailInfoVO.getMsgId());
    		customerDemand.setLoginLevel(projectDetailInfoVO.getLoginLevel()+"");
    		customerDemand.setNodeName(projectDetailInfoVO.getNodename());
    		customerDemand.setOperateStatus(projectDetailInfoVO.getOperateStatus());
    		customerDemand.setOperater(projectDetailInfoVO.getOperater());
    		customerDemand.setProjectCode(projectDetailInfoVO.getProjectCode());
    		customerDemand.setProjectCreaterName(projectDetailInfoVO.getProjectCreaterName());
    		customerDemand.setProjectName(projectDetailInfoVO.getProjectName());
    		customerDemand.setProjectState(projectDetailInfoVO.getProjectState());
    		
    	    javax.xml.datatype.XMLGregorianCalendar cal = projectDetailInfoVO.getCreatedDate();
    		GregorianCalendar ca = cal.toGregorianCalendar();
    		String format = "MM/dd/yyyy HH:mm:ss";
    		SimpleDateFormat formatter = new SimpleDateFormat(format);
    		customerDemand.setCreateTime(formatter.format(ca.getTime()));
    		
    		CustomerMsg customerMsg = new CustomerMsg();
    		customerMsg = customerMsgService.getByMsgid(projectDetailInfoVO.getMsgId());
    		if(customerMsg == null){
    	
    		}else{
    			customerDemand.setId(customerMsg.getId());
    			customerDemand.setSmallBill(customerMsg.getSmallBill());
    		}   		
    		customerDemands.add(customerDemand);
    	}

    	
    	Page page = new Page();
    	page.setList(customerDemands);
    	page.setCount(projectDetailInfoQYGVO.getListCount());
    	page.setPageNo(Integer.parseInt(customerSearch.getPageNo()));
    	page.setPageSize(Integer.parseInt(customerSearch.getPageSize()));
    	
    	return AjaxJson.layuiTable(page);
    }
    
    
    /**
     * 根据关键词（产品编码、产品型号、产品组、产品品牌）获取产品详情
     * @return
     */
    @ApiOperation(notes = "getProductInfo", httpMethod = "GET", value = "根据关键词（产品编码、产品型号、产品组、产品品牌）获取产品详情")
    @RequestMapping("/getProductInfo")
    public String getProductInfo(String keyWords, @RequestParam(value = "pageNo", defaultValue="1", required = false)String pageNo ,@RequestParam(value = "pageSize", defaultValue="20", required = false)String pageSize){    	
    	logger.info("*_*_*_*_*_*_*_*_*_* 获取产品详情开始 *_*_*_*_*_*_*_*_*_*");
    	String productInfo = ProductsApi.getProductInfo(keyWords, pageNo, pageSize);  	
    	logger.info("*_*_*_*_*_*_*_*_*_* 获取产品详情结束 *_*_*_*_*_*_*_*_*_*");
    	return productInfo;
    }
    
    @ApiOperation(notes = "getProductInfoTest", httpMethod = "GET", value = "根据关键词（产品编码、产品型号、产品组、产品品牌）获取产品详情")
    @RequestMapping("/getProductInfoTest")
    public String getProductInfoTest(String keyWords, @RequestParam(value = "pageNo", defaultValue="1", required = false)String pageNo ,@RequestParam(value = "pageSize", defaultValue="20", required = false)String pageSize){    	
    	logger.info("*_*_*_*_*_*_*_*_*_* 获取产品详情开始 *_*_*_*_*_*_*_*_*_*");
    	String productInfo = ProductsApi.getProductInfoTest(keyWords, pageNo, pageSize);  	
    	logger.info("*_*_*_*_*_*_*_*_*_* 获取产品详情结束 *_*_*_*_*_*_*_*_*_*");
    	return productInfo;
    }
    
    /**
     * 根据产品名称获取产业等信息
     * @param name
     * @return
     */
    @ApiOperation(notes = "getIndustryByProducts", httpMethod = "GET", value = "根据产品名称获取所属产业")
    @RequestMapping("/getIndustryByProducts")
    public IndustryProducts getIndustryByProducts(String name){
    	IndustryProducts industryProduct = new IndustryProducts();
    	industryProduct.setName(name);
    	List<IndustryProducts> industryProducts = industryProductsService.findList(industryProduct);
 
    	if(industryProducts != null && industryProducts.size()>0){
    		industryProduct = industryProducts.get(0);
    	}
    	return industryProduct;
    }
   
    
    /**
     * 锁定接口人（模糊匹配）
     * @param key
     * @return
     */
    @ApiOperation(notes = "getLockUserList", httpMethod = "GET", value = "根据机会点获取用户群组")
    @RequestMapping("/getLockUserList")
    public List<LockUserVO> getLockUserList(String key){    	
    	List<LockUserVO> info = HpsApi.lockUserList(key);  	
    	return info;
    }

    /**
     * 获取商空系列产品数据信息
     * @param
     * @return
     */
    @ApiOperation(notes = "getProductSKList", httpMethod = "GET", value = "获取商空产品数据信息")
    @RequestMapping("/getProductSKList")
    public List<ProductSk> getProductSKList(@RequestParam(value = "parentID", required = false)String parentID,
    		@RequestParam(value = "productType", required = false)String productType,
    		@RequestParam(value = "bigandsmall", required = false)String bigandsmall
    		){    	
    	ProductSk productSk = new ProductSk();
    	if(StringUtils.isNotBlank(parentID)){
    		productSk.setParentid(parentID);
    	}
    	if(StringUtils.isNotBlank(productType)){
    		productSk.setProducttype(productType);
    	}
    	if(StringUtils.isNotBlank(bigandsmall)){
    		productSk.setBigandsmall(bigandsmall);
    	}
    	List<ProductSk> listProductSk = productSkService.findList(productSk);   	
    	return listProductSk;
    }

    /**
     *@Description 客单评价
     *@Param 以项目为维度，项目编码为主键
     *@Return
     *@Author t.c
     *@Date 2020-01-21
     */
    @ApiOperation(notes = "addcustomercomment", httpMethod = "post", value = "客单评价")
    @RequestMapping("/addcustomercomment")
    public AjaxJson addCustomerComment(
            @RequestParam(value = "commentType")String commentType,
            @RequestParam(value = "commentUser")String commentUser,
            @RequestParam(value = "commentContent")String commentContent,
            @RequestParam(value = "commentMobile")String commentMobile,
            @RequestParam(value = "commentDegreeScore")String commentDegreeScore,
            @RequestParam(value = "commentBeautifulScore")String commentBeautifulScore,
            @RequestParam(value = "commentHumanityScore")String commentHumanityScore,
            @RequestParam(value = "projectId ")String projectId,
            @RequestParam(value="commentName")String commentUserName
    ) {
        CustomerComment customerComment=new CustomerComment();
        customerComment.setId(projectId);
        customerComment.setCommentType(commentType);
        customerComment.setCommentMobile(commentMobile);
        customerComment.setCommentUser(commentUser);
        customerComment.setCommentBeautifulScore(commentBeautifulScore);
        customerComment.setCommentDegreeScore(commentDegreeScore);
        customerComment.setCommentHumanityScore(commentHumanityScore);
        customerComment.setCommentDate(new Date());
        customerComment.setCommentContent(commentContent);
        customerCommentService.save(customerComment);
        try {
            String httpStr = addCommentEuc(projectId,commentUser,commentUserName,commentContent);
            JSONObject jo = JSON.parseObject(httpStr);
            String fFlag = (String)jo.get("code");
            if(("0").equals(fFlag)){
                return AjaxJson.ok("评价成功！");
            }else{
                return AjaxJson.fail("传入EUC评价失败");
            }

        } catch (Exception e) {
            return AjaxJson.fail("传入EUC评价失败");
        }


    }
    public static String addCommentEuc (String projectCode, String appraiserCode, String appraiserName, String content)
    {
            cn.hutool.json.JSONObject param = JSONUtil.createObj();
            //操作人
            param.put("appraiserName", appraiserName);
            //操作人编码
            param.put("appraiserCode", appraiserCode);
            //操作人备注
            param.put("content", content);
            //项目编码
            param.put("projectCode", projectCode);
            Map<String,String> map = new HashMap<String,String>();
            map.put("Content-Type", "application/json;charset=utf-8");

            String  httpStr = HttpRequest.post(Constants.EUC_URL+"/bussiness/saveEvaluate").addHeaders(map).body(param).execute().body();

            return httpStr;
    }



    /**
     * 我的项目
     * @return
     */
    @ApiOperation(notes = "getMyProjectList", httpMethod = "GET", value = "根据机会点获取用户群组")
    @RequestMapping("/getMyProjectList")
    public AjaxJson getProjectList(@RequestParam(value = "pageNo", required = true) Integer pageNo,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "projectStatus", required = false) String projectStatus, 
			@RequestParam(value = "contractorCode", required = false) String contractorCode, 	
			@RequestParam(value = "projectCode", required = false) String projectCode,
			@RequestParam(value = "projectName", required = false) String projectName,
			@RequestParam(value = "funnelStage", required = false) String funnelStage,
			@RequestParam(value = "contractApprovalStatus", required = false) String contractApprovalStatus,
			@RequestParam(value = "industryHomeCategory", required = false) String industryHomeCategory,
			@RequestParam(value = "createDateStart", required = false) String createDateStart,
			@RequestParam(value = "createDateEnd", required = false) String createDateEnd,
			@RequestParam(value = "projectSourceStr", required = false) String projectSourceStr){    	
    	Map<String,Object> para  =  new HashMap<String,Object>();		
		para.put("page", pageNo);
		para.put("rows", pageSize);
		if(StringUtils.isNotBlank(projectStatus)){
			para.put("projectStatusStr", projectStatus);
		}
		if(StringUtils.isNotBlank(contractorCode)){
			para.put("contractorCode", contractorCode);
		}
		if(StringUtils.isNotBlank(projectCode)){
			para.put("projectCode", projectCode);
		}
		if(StringUtils.isNotBlank(projectName)){
			para.put("projectName", projectName);
		}
		if(StringUtils.isNotBlank(funnelStage)){
			para.put("funnelStage", funnelStage);
		}
		if(StringUtils.isNotBlank(contractApprovalStatus)){
			para.put("contractApprovalStatus", contractApprovalStatus);
		}
		if(StringUtils.isNotBlank(industryHomeCategory)){
			para.put("industryHomeCategory", industryHomeCategory);
		}
		if(StringUtils.isNotBlank(createDateStart)){
			para.put("createDateStart", createDateStart);
		}
		if(StringUtils.isNotBlank(createDateEnd)){
			para.put("createDateEnd", createDateEnd);
		}
		if(StringUtils.isNotBlank(projectSourceStr)){
			para.put("projectSourceStr", projectSourceStr);
		}
		String jsonStr = MyProject.getProjectList(para);
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		if(jsonObject.getIntValue("errcode") != 0){
			return AjaxJson.fail(jsonObject.getString("errmsg"));
		}else{
			AjaxJson j = new AjaxJson();
			j.setSuccess(true);
	        j.setCount(jsonObject.getJSONObject("data").getLongValue("totalCount"));
	        j.setData(jsonObject.getJSONObject("data").getJSONArray("list"));
			return j;
		}
		
    } 
    

    /**
       * 失败项目删除
     * @param
     * @return
     */
    @ApiOperation(notes = "delFailProject", httpMethod = "GET", value = "删除失败项目")
    @RequestMapping("/delFailProject")
    public AjaxJson  delFailProject(String id, HttpServletRequest request, HttpServletResponse response){
    	logger.info("*_*_*_*_*_*_*_*_* * ApiCustomerController  /delFailProject  删除失败项目----------接口开始*_*_*_*_*_*_*_*_*_*");
        //判断当前登录用户是否存在，是否有效
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            logger.info("*_*_*_*_*_*_*_*_*_* 当前登录账号无效 *_*_*_*_*_*_*_*_*_*");
            return AjaxJson.fail("当前登录账号无效");
        }   
        
        CustomerMsg customerMsg = customerMsgService.get(id);
        if(customerMsg == null) {
        	return AjaxJson.fail("该项目不存在");
        }
        if(!customerMsg.getStatus().equals("0") || customerMsg.getStatus().equals("请联系业务人员！")) {
        	return AjaxJson.fail("该项目不能删除");
        }
        String msgId = customerMsg.getMsgId();
        if(StringUtils.isNotBlank(msgId)) {
        	customerMsgProductService.deleteByMsgid(msgId);          
            customerQuotesService.deleteByMsgid(msgId);
            customerMsgService.delete(customerMsg);
            return AjaxJson.ok();
        }else {
        	return AjaxJson.fail("msgId有误！");
        }

        
    }
	/**
     * 我的项目详情
     * @return
     */
    @ApiOperation(notes = "getMyProjectDetails", httpMethod = "GET", value = "我的我的项目详情项目")
    @RequestMapping("/getMyProjectDetails")
    public AjaxJson getMyProjectDetails(@RequestParam(value = "projectId") String projectId) {
        Map<String, Object> para = new HashMap<String, Object>();
        if (StringUtils.isBlank(projectId)) {
            return AjaxJson.fail("项目id不能为空");
        }
        if (StringUtils.isNotBlank(projectId)) {
            para.put("projectId", projectId);
        }
        String jsonStr = MyProject.getProjectDetails(para);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        if (jsonObject.getIntValue("errcode") != 0) {
            return AjaxJson.fail(jsonObject.getString("errmsg"));
        } else {
            AjaxJson j = new AjaxJson();
            j.setResult(jsonObject.getJSONObject("data"));
            return j;
        }
    }
    
    
    
}
