/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.haier.user.api.hps.MyProject;
import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.EucException;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.config.Global;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.euc.entity.*;
import com.jhmis.modules.euc.mapper.EucMsgMapper;
import com.jhmis.modules.process.entity.shopmsgzykc.ShopMsgZykc;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.euc.mapper.EucMsgOrderMapper;

/**
 * euc订单Service
 * @author hdx
 * @version 2019-12-25
 */
@Service
@Transactional(readOnly = true)
public class EucMsgOrderService extends CrudService<EucMsgOrderMapper, EucMsgOrder> {

	@Autowired
	EucLogService eucLogService;
	@Autowired
	private EucMsgMapper eucMsgMapper;

	public EucMsgOrder get(String id) {
		return super.get(id);
	}
	
	public List<EucMsgOrder> findList(EucMsgOrder eucMsgOrder) {
		return super.findList(eucMsgOrder);
	}
	public List<EucMsgOrder> findListOver(EucMsgOrder eucMsgOrder) {
		return mapper.findListOver(eucMsgOrder);
	}

	public Page<EucMsgOrder> findPage(Page<EucMsgOrder> page, EucMsgOrder eucMsgOrder) {
		return super.findPage(page, eucMsgOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(EucMsgOrder eucMsgOrder) {
		super.save(eucMsgOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(EucMsgOrder eucMsgOrder) {
		super.delete(eucMsgOrder);
	}

	@Transactional(readOnly = false)
	public void check(String orderId,String ischeck) throws EucException {
		//orderId 为空
		if(StringUtils.isBlank(orderId)){
			throw new EucException(EucMsgCode.PARAM_ORDERID_EXIST);
		}
		//ischeck type
		if(StringUtils.isBlank(ischeck)){
			throw new EucException(ShopMsgCode.PARAM_ISCHECK_ERROR);
		}
		EucMsgOrder eucMsgOrder = this.get(orderId);
		if(null==eucMsgOrder){
			throw new EucException(ShopMsgCode.ORDER_ZYKC_ERROR);
		}
		//获取EUC需求
		EucMsg eucMsg = eucMsgMapper.getOwnById(eucMsgOrder.getEucId());
		if(null==eucMsg){
			throw new EucException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//是否审核通过
		//ischeck type
		if("0".equals(ischeck)){
			//中标
			eucMsgOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
			EucMsgOrder eucMsgOrderOther = new EucMsgOrder();
			//EUC的id
			eucMsgOrderOther.setEucId(eucMsg.getId());
			//EUC订单未放弃
			eucMsgOrderOther.setIsAbandon(ProcessCode.NO.getLabel());
			//中标后将所有其余单子改为已结单
			List<EucMsgOrder> listeucMsgOrderOther = mapper.findList(eucMsgOrderOther);
			if(null!=listeucMsgOrderOther && listeucMsgOrderOther.size()>0){
				//数据不为空
				for(EucMsgOrder emoother:listeucMsgOrderOther){
					if(eucMsgOrder.getContractorCode().equals(emoother.getContractorCode())){
						continue;
					}
					//设置放弃
					emoother.setIsAbandon(ProcessCode.YES.getLabel());
					//放弃类型
					if("".equals(emoother.getUndertake())){
						emoother.setAbandonType(EucMsgCode.ABANDONTYPE_NO_UNDEDRTAKE.getLabel());
					}else if(EucMsgCode.UNDERTAKE_PROJECT.getLabel().equals(emoother.getUndertake())){
						emoother.setAbandonType(EucMsgCode.ABANDONTYPE_PROJECT.getLabel());
					}else if(EucMsgCode.UNDERTAKE_RETAIL.getLabel().equals(emoother.getUndertake())){
						emoother.setAbandonType(EucMsgCode.ABANDONTYPE_RETAIL.getLabel());
					}
					//放弃原因
					emoother.setAbandonReason("已结单");
					//放弃时间
					emoother.setAbandonTime(new Date());
					//经销商放弃备注原因
					emoother.setAbandonRemark(emoother.getBusinessName() + emoother.getBusinessCode() +"已在"+ new Date().toLocaleString() + emoother.getContractorCode() + "已中标");
					mapper.update(emoother);
				}
			}
			//无效
			eucMsg.setIsValid(ProcessCode.NO.getLabel());
			//更新时间
			eucMsg.setUpdateDate(new Date());
			//更新需求
			eucMsgMapper.update(eucMsg);
		}
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		//审核时间
		eucMsgOrder.setCheckDate(new Date());
		//审核类型
		eucMsgOrder.setIsCheck(ischeck);
		//审核人
		eucMsgOrder.setChecker(currentUser.getLoginName());
		//更新
		mapper.update(eucMsgOrder);
		//EUC状态内容回传
		EucReturnBody eucReturnBody = new EucReturnBody();
		//商机编码
		eucReturnBody.setBusinessCode(eucMsg.getBusinessCode());
		//商机状态
		eucReturnBody.setStatus(EucMsgCode.RETAIL_CLOSED_LOOP.getLabel());
		//操作人 - 经销商
		eucReturnBody.setPerson(eucMsgOrder.getContractorName());
		//操作人操作人编码 - 经销商编码
		eucReturnBody.setPersonCode(eucMsgOrder.getContractorCode());
		//备注
		eucReturnBody.setRemark(EucMsgCode.RETAIL_CLOSED_LOOP.getLabel());
		//回传EUC
		String code = updateToEuc(eucReturnBody);
		//EUC状态回传日志
		new EucMsgService().eucSaveLog(code,EucMsgCode.RETAIL_CLOSED_LOOP.getLabel(),eucReturnBody,eucMsg,eucMsgOrder);
	}

	/**
	 *@Description 给euc推送修改状态
	 *@Param
	 *@Return
	 *@Author t.c
	 *@Date 2020-03-06
	 */
	public static String updateToEuc(EucReturnBody eucReturnBody){
		JSONObject param = JSONUtil.createObj();
		//商机编码
		param.put("businessCode", eucReturnBody.getBusinessCode());
		//状态
		param.put("status", eucReturnBody.getStatus());
		//操作人
		param.put("person", eucReturnBody.getPerson());
		//操作人编码
		param.put("personCode", eucReturnBody.getPersonCode());
		//操作人备注
		param.put("remark", eucReturnBody.getRemark());
		//项目编码
		param.put("projectCode", eucReturnBody.getProjectCode());
		param.put("winBidCompany",eucReturnBody.getWinBidCompany());
		param.put("performanceW",eucReturnBody.getPerformanceWay());
		Map<String,String> map = new HashMap<String,String>();
		map.put("Content-Type", "application/json;charset=utf-8");
		String  httpStr = HttpRequest.post(Constants.EUC_URL+"/bussiness/updateStatusHaier").addHeaders(map).body(param).execute().body();
		com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
		String fFlag = "1";
		if(null!=jo){
			fFlag = jo.get("code")+"";
		}
		return fFlag;
	}


	public static String returnEuc(String businessCode, String status, String person, String personCode, String remark,String projectCode){
		JSONObject param = JSONUtil.createObj();
		//商机编码
		param.put("businessCode", businessCode);
		//状态
		param.put("status", status);
		//操作人
		param.put("person", person);
		//操作人编码
		param.put("personCode", personCode);
		//操作人备注
		param.put("remark", remark);
		//项目编码
		param.put("projectCode", projectCode);
		Map<String,String> map = new HashMap<String,String>();
		map.put("Content-Type", "application/json;charset=utf-8");
		String  httpStr = HttpRequest.post(Constants.EUC_URL+"/bussiness/updateStatusHaier").addHeaders(map).body(param).execute().body();
		return httpStr;
	}

	/**
	 * 提供给EUC的接口  获取供应商的授权状态
	 */
	@Transactional(readOnly = false)
	public void hpsAuth(EucMsg eucMsg,List<AuthInfo> authList){
		for(AuthInfo authInfo:authList) {
			logger.info("hpsAuth 提供给HPS 的授权状态查询接口   projectcode="+authInfo.getProjectCode());
			//更新商机订单表中的授权状态和授权时间
			if (StringUtils.isNotEmpty(authInfo.getProjectCode())) {
				//根据项目编码查询订单信息
				EucMsgOrder order = findUniqueByProperty("project_code", authInfo.getProjectCode());
				logger.info("projectcode is--"+order.getProjectCode()+" order---getEucId -"+order.getEucId());
				if (order != null) {
					//授权状态
					order.setAuthorityStatus(authInfo.getAuthStatus());
					//授权时间
					if (StringUtils.isNotEmpty(authInfo.getAuthTime())) {
						order.setAuthorityTime(DateUtils.parseDate(authInfo.getAuthTime()));
					} else {
						order.setAuthorityTime(new Date());
					}
					if (StringUtils.isNotEmpty(authInfo.getRefuseReason())) {
						order.setAppealRemark(authInfo.getRefuseReason());
					}
					//如果是通过申诉后授权的，更新申诉状态
					if (order.getAppealTime() != null) {
						order.setAppealStatus(authInfo.getAuthStatus());
					}
					logger.info("update appealInfo-------order.getId---"+order.getId());
					mapper.updateAppealInfo(order);
				}
			}
		}
		//更新 商机信息中 是否可以在抢单池中可见的标志
			if(eucMsg != null){
				eucMsg.setIsValid(Global.NO);
				logger.info("update eucMsg isValid-------eucMsg.getId---"+eucMsg.getId());
				eucMsgMapper.updateIsValid(eucMsg);
			}

	}

	/**
	 * 保存或编辑申诉信息
	 * @param eucMsgOrder
	 */
	@Transactional(readOnly = false)
	public void updateAppealInfo(EucMsgOrder eucMsgOrder){
		mapper.updateAppealInfo(eucMsgOrder);
	}

	/**
	 * 提交申诉信息至HPS
	 * @param eucMsgOrder
	 */
	@Transactional(readOnly = false)
	public String submitToHps(EucMsgOrder eucMsgOrder) {
		String resultMsg = "OK";
		//调用 HPS 的保存申诉接口
		String httpStr = applyForAppeal(eucMsgOrder.getProjectCode(),eucMsgOrder.getMsgId(),eucMsgOrder.getProjectCode(),eucMsgOrder.getContractorName(),eucMsgOrder.getAppealFiles());
		logger.info("applyforappeal response---------------"+httpStr);
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(httpStr);
		Integer errCode = (Integer)jsonObject.get("errcode");
		String errMsg = (String)jsonObject.get("errmsg");
		if(errCode == 0){
			// 把申诉 状态修改改申诉待审核
			eucMsgOrder.setAppealStatus(EucMsgCode.AUTH_APPEAL_AUDITING.getValue());
			//授权状态设置为待授权
			eucMsgOrder.setAuthorityStatus(EucMsgCode.AUTH_DEFAULT.getValue());
			//申诉提交时间
			eucMsgOrder.setAppealTime(new Date());
			//提交申诉信息至HPS 后，更新申诉状态，申诉时间 和授权状态
			mapper.updateAppealInfo(eucMsgOrder);
		}else{
			resultMsg = errMsg;
		}

		return resultMsg;
	}
	/**
	 * 申诉申请
	 * @param projectCode
	 * @param msgId
	 * @param contractorCode
	 * @param contractorName
	 * @param appealFileUrl
	 * @return
	 */
	public String applyForAppeal(String projectCode,String msgId,String contractorCode,String contractorName,String appealFileUrl ){
		Map<String,Object> param = Maps.newHashMap();
		param.put("projectCode",projectCode);
		param.put("msgId",msgId);
		param.put("contractorCode",contractorCode);
		param.put("contractorName",contractorName);
		param.put("appealFileUrl",Global.getBaseUrl()+appealFileUrl);
		Map<String,String> header = Maps.newHashMap();
		header.put("ApiAuthorization", MyProject.hpsHeader);
//		header.put("Content-Type", "application/json;charset=utf-8");
//		String  httpStr = HttpRequest.post(MyProject.APPEAL_APPLY_FOR_APPEAL).addHeaders(header).body(param).execute().body();
		String  httpStr = HttpRequest.get(MyProject.APPEAL_APPLY_FOR_APPEAL).addHeaders(header).form(param).execute().body();
		logger.info("applyforappeal--------------"+httpStr);
		return httpStr;

	}

}