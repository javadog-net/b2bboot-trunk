package com.haier.user.api.hps;

import java.util.HashMap;
import java.util.Map;

import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.haier.user.api.HttpWebUtil;
import com.jhmis.common.utils.StringUtils;

public class MyProject {

	public final static String PROJECT_LIST_URL = Constants.HPS_URL + "/out_api/project/list";
public final static String PROJECT_DETAILS_URL = Constants.HPS_URL + "/out_api/project/info";
	public final static String PROJECT_INFO_URL = Constants.HPS_URL + "/out_api/project/info";
	public final static String PROJECT_COUNT_URL = Constants.HPS_URL + "/out_api/project/count";
	public final static String BROWN_LIST_URL = Constants.HPS_URL + "/out_api/project/brownList";
	public final static String BROWN_INFO_URL = Constants.HPS_URL + "/out_api/project/brownInfo";
	//是否有申诉权限的接口
	public final static String APPEAL_CANBE_APPLY_URL = Constants.HPS_URL + "/out_api/appeal/canBeApply";

	//申诉申请的接口
	public final static String APPEAL_APPLY_FOR_APPEAL= Constants.HPS_URL + "/out_api/appeal/applyForAppeal";



	public final static String hpsHeader = "sJkvKSC2ymYwyvJu";
	
	//项目列表
	public static String getProjectList(Map<String,Object> para){	
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("ApiAuthorization", hpsHeader);
		return HttpWebUtil.QixinhttpRequestHeader(PROJECT_LIST_URL, para, header);
	}
	
	//项目详情
	public static String getProjectInfo(String projectId){	
		Map<String,Object> params  =  new HashMap<String,Object>();
		params.put("projectId", projectId);
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("ApiAuthorization", hpsHeader);
		return HttpWebUtil.QixinhttpRequestHeader(PROJECT_INFO_URL, params, header);
	}
	
	//全流程看板数据
	public static String getProjectCount(String contractorCode) {
		
		Map<String,Object> params  =  new HashMap<String,Object>();
		params.put("contractorCode", contractorCode);
		
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("ApiAuthorization", hpsHeader);
		
		return HttpWebUtil.QixinhttpRequestHeader(PROJECT_COUNT_URL, params, header);
	}
	
	//工程版列表
	public static String getBrownList(Map<String,Object> para){	
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("ApiAuthorization", hpsHeader);
		return HttpWebUtil.QixinhttpRequestHeader(BROWN_LIST_URL, para, header);
	}
	
	//工程版详情
	public static String getBrownInfo(String brownId){	
		Map<String,Object> params  =  new HashMap<String,Object>();
		params.put("brownId", brownId);
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("ApiAuthorization", hpsHeader);
		return HttpWebUtil.QixinhttpRequestHeader(BROWN_INFO_URL, params, header);
	}
	public static String getProjectDetails(Map<String,Object> para){
		Map<String,String> header  =  new HashMap<String,String>();
		header.put("ApiAuthorization", "sJkvKSC2ymYwyvJu");
		return HttpWebUtil.QixinhttpRequestHeader(PROJECT_DETAILS_URL, para, header);
	}
}
