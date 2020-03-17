package com.jhmis.api.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.haier.user.api.hps.MyProject;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.entity.CustomerMsgFromHPS;
import com.jhmis.modules.customer.entity.HpsMessageReminder;
import com.jhmis.modules.customer.service.CustomerMsgService;
import com.jhmis.modules.customer.service.HpsMessageReminderService;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.view.WholeProcessHps;
import com.jhmis.view.entity.HpsWholeProcessOrderbill;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "ApiCustomerHpsController", description = "客单部分hps提供接口")
@RestController
@RequestMapping("/api/hps/customer")
public class ApiHpsCustomerController {
	@Autowired
    protected CustomerMsgService customerMsgService;
	@Autowired
	WholeProcessHps wholeProcessHps;
	@Autowired
	HpsMessageReminderService hpsMessageReminderService;
    /**
       * 商机报备列表
     * @return
     */
    @ApiOperation(notes = "getMyCustomerMsg", httpMethod = "GET", value = "商机报备列表")
    @RequestMapping("/getMyCustomerMsg")
    public AjaxJson getMyCustomerMsg(@RequestParam(value = "pageNo", required = true) Integer pageNo,
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
		}
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
        j.setCount(jsonObject.getJSONObject("data").getLongValue("totalCount"));
        com.alibaba.fastjson.JSONArray msg =  jsonObject.getJSONObject("data").getJSONArray("list");
        List<CustomerMsgFromHPS> msgList = JSONObject.parseArray(msg.toJSONString(), CustomerMsgFromHPS.class);
        
        if(msgList!=null && msgList.size()>0) {
        	for(CustomerMsgFromHPS customerMsgFromHPS : msgList) {
            	String  code = customerMsgFromHPS.getProjectCode();
            	if(StringUtils.isBlank(code)){
        			continue;
        		}
        		CustomerMsg customerMsg = new CustomerMsg();
        		customerMsg = customerMsgService.getByProjectId(code);
        		if(customerMsg != null) {
        			customerMsgFromHPS.setSmallBill(customerMsg.getSmallBill());
        		}
            }
        }
                            
        j.setData(msgList);
		return j;				
    }
    
    /**
     * 商机报备前段导出用
   * @return
   */
  @ApiOperation(notes = "getMyCustomerMsgExport", httpMethod = "GET", value = "商机报备列表")
  @RequestMapping("/getMyCustomerMsgExport")
  public AjaxJson getMyCustomerMsgExport(@RequestParam(value = "pageNo", required = true) Integer pageNo,
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
		}
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
      j.setCount(jsonObject.getJSONObject("data").getLongValue("totalCount"));
      j.setData(jsonObject.getJSONObject("data").getJSONArray("list"));
		return j;				
  }
    
    @ApiOperation(notes = "getBrownList", httpMethod = "GET", value = "工程版列表")
    @RequestMapping("/getBrownList")
    public AjaxJson getBrownList(@RequestParam(value = "pageNo", required = true) Integer pageNo,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "dealerCode", required = true) String dealerCode, 
			@RequestParam(value = "projectId", required = false) String projectId){    	
    	Map<String,Object> para  =  new HashMap<String,Object>();		
		para.put("page", pageNo);
		para.put("rows", pageSize);
		para.put("dealerCode", dealerCode);//项目编码	
		if(StringUtils.isNotBlank(projectId)){
			para.put("projectId", projectId);//项目名称
		}
		
		String jsonStr = MyProject.getBrownList(para);
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		if(jsonObject.getIntValue("errcode") != 0){
			return AjaxJson.fail(jsonObject.getString("errmsg"));
		}
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
        j.setCount(jsonObject.getJSONObject("data").getLongValue("totalCount"));                    
        j.setData(jsonObject.getJSONObject("data").getJSONArray("list"));
		return j;				
    }
    
    
    /**
     * 项目详情
     * @param projectId 项目id
   * @return
   */
  @ApiOperation(notes = "getProjectInfo", httpMethod = "GET", value = "项目详情")
  @RequestMapping("/getProjectInfo")
  public String getProjectInfo(String projectId) {
	  return MyProject.getProjectInfo(projectId);
  }
  
  
	 /**
	   * 工程版详情
	   * @param brownId 工程版id
	 * @return
	 */
	@ApiOperation(notes = "getBrownInfo", httpMethod = "GET", value = "工程版详情")
	@RequestMapping("/getBrownInfo")
	public String getBrownInfo(String brownId) {
		  return MyProject.getBrownInfo(brownId);
	}

	/**
	   * 全流程看板数据
	   * @param contractorCode 用户的8码
	 * @return
	 */
	@ApiOperation(notes = "getProjectCount", httpMethod = "GET", value = "全流程看板数据")
	@RequestMapping("/getProjectCount")
	public String getProjectCount(String contractorCode) {
		  return MyProject.getProjectCount(contractorCode);
	}
	
	/**
	   * 全流程项目信息列表
	 * @return
	 */
	@ApiOperation(notes = "getWholeProcessProject", httpMethod = "GET", value = "全流程项目信息列表")
	@RequestMapping("/getWholeProcessProject")
	public AjaxJson getWholeProcessProject(@RequestParam(value = "pageNo", required = true, defaultValue="1") Integer pageNo,
			@RequestParam(value = "pageSize", required = true, defaultValue="10") Integer pageSize,
			@RequestParam(value = "projectStatus", required = false) String projectStatus, 
			@RequestParam(value = "contractorCode", required = false) String contractorCode, 	
			@RequestParam(value = "projectCode", required = false) String projectCode,
			@RequestParam(value = "projectName", required = false) String projectName,
			@RequestParam(value = "funnelStage", required = false) String funnelStage,
			@RequestParam(value = "deptCode", required = false) String deptCode,
			@RequestParam(value = "industryHomeCategorye", required = false) String industryHomeCategorye,
			@RequestParam(value = "createDateStart", required = false) String createDateStart,
			@RequestParam(value = "createDateEnd", required = false) String createDateEnd,
			@RequestParam(value = "dataSource", required = false) String dataSource) {
		DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }     
        HpsWholeProcessOrderbill hpsWholeProcessOrderbill = new HpsWholeProcessOrderbill();
        hpsWholeProcessOrderbill.setPageNo(pageNo);
        hpsWholeProcessOrderbill.setPageSize(pageSize);
        if(StringUtils.isNotBlank(projectCode)){//项目编码
        	hpsWholeProcessOrderbill.setProject_code(projectCode);
        }
        if(StringUtils.isNotBlank(projectName)){//项目名称
        	hpsWholeProcessOrderbill.setProject_name(projectName);
        }
        if(StringUtils.isNotBlank(projectStatus)){//项目状态
        	hpsWholeProcessOrderbill.setProject_status(projectStatus);
        }
        if(StringUtils.isNotBlank(funnelStage)){//项目节点
        	hpsWholeProcessOrderbill.setFunnel_stage(funnelStage);
        }
        if(StringUtils.isNotBlank(deptCode)){//产品组
        	hpsWholeProcessOrderbill.setDept_code(deptCode);
        }
        if(StringUtils.isNotBlank(industryHomeCategorye)){//行业
        	hpsWholeProcessOrderbill.setIndustry_home_category(industryHomeCategorye);
        }
        if(StringUtils.isNotBlank(createDateStart)){//项目开始时间
        	hpsWholeProcessOrderbill.setCreate_date_start(createDateStart);
        }
        if(StringUtils.isNotBlank(createDateEnd)){//项目结束时间
        	hpsWholeProcessOrderbill.setCreate_date_end(createDateEnd);
        }
        if(StringUtils.isNotBlank(dataSource)){//项目来源        	
        	if(dataSource.equals("直转客")) {
        		hpsWholeProcessOrderbill.setProject_code("-Z");
        	}else {
        		hpsWholeProcessOrderbill.setData_source(dataSource);
        	}        	
        }
        
        Map<String,Object> map= new HashMap<>();
        
        try {
        	hpsWholeProcessOrderbill.setContractor_code(currentAccount.getLoginName());
        	map = wholeProcessHps.getWholeProcessFromHps(hpsWholeProcessOrderbill);
        } catch (Exception e) {
            return AjaxJson.fail(e.getMessage());
        }
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setCount(((Integer) map.get("count")).longValue());
        j.setData((List<HpsWholeProcessOrderbill>) map.get("listWholeProcess"));
        return j;
	}
	
	/**
	   * 消息提醒数据
	 * @return
	 */
	@ApiOperation(notes = "getMessage", httpMethod = "GET", value = "消息提醒列表")
	@RequestMapping("/getMessage")
	public AjaxJson getMessage() {
		DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if(currentAccount == null || StringUtils.isEmpty(currentAccount.getId())|| !"0".equals(currentAccount.getIsClosed())){
            return AjaxJson.fail("当前登录账号无效");
        }
        String code88 = currentAccount.getLoginName();

        AjaxJson ajaxJson = new AjaxJson();
        int count = hpsMessageReminderService.findMessageNumber(code88);
        if(count != 0) {
        	List<HpsMessageReminder> hpsMessageReminderList = hpsMessageReminderService.findMessageByCode88(code88);
        	ajaxJson.setData(hpsMessageReminderList);
        }
       
        ajaxJson.setCount( new Long((long)count) );
                
        return ajaxJson;
	}
	
	/**
	   * 根据id将消息设置为已读
	 * @return
	 */
	@ApiOperation(notes = "serIsread", httpMethod = "GET", value = "根据id将消息设置为已读")
	@RequestMapping("/serIsread")
	public AjaxJson serIsread(String id) {
		hpsMessageReminderService.setIsRead(id);
		return AjaxJson.ok();
	}
	
}
