/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shopevaluate;

import java.util.Date;
import java.util.List;

import com.jhmis.common.Enum.ShopMsgCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.utils.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.shopevaluate.ShopMsgEvaluate;
import com.jhmis.modules.process.mapper.shopevaluate.ShopMsgEvaluateMapper;

/**
 * 评价相关Service
 * @author hdx
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class ShopMsgEvaluateService extends CrudService<ShopMsgEvaluateMapper, ShopMsgEvaluate> {

	public ShopMsgEvaluate get(String id) {
		return super.get(id);
	}
	
	public List<ShopMsgEvaluate> findList(ShopMsgEvaluate shopMsgEvaluate) {
		return super.findList(shopMsgEvaluate);
	}
	
	public Page<ShopMsgEvaluate> findPage(Page<ShopMsgEvaluate> page, ShopMsgEvaluate shopMsgEvaluate) {
		return super.findPage(page, shopMsgEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopMsgEvaluate shopMsgEvaluate) {
		super.save(shopMsgEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopMsgEvaluate shopMsgEvaluate) {
		super.delete(shopMsgEvaluate);
	}

	@Transactional(readOnly = false)
	public void evaluateAdd(ShopMsgEvaluate shopMsgEvaluate) throws ShopMsgException {
		//需求id不能为空
		if(StringUtils.isBlank(shopMsgEvaluate.getMsgid())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ISNULL_ERROR);
		}
		//判断需求是否存在
		ShopMsgEvaluate sme = new ShopMsgEvaluate();
		sme.setMsgid(shopMsgEvaluate.getMsgid());
		sme.setNodestate(shopMsgEvaluate.getNodestate());
		List<ShopMsgEvaluate> listShopMsgEvaluate = this.findList(sme);
		if(listShopMsgEvaluate!=null && listShopMsgEvaluate.size()>0){
			throw new ShopMsgException(ShopMsgCode.HAS_EVALUATE_EXCEPTION);
		}
		//需求节点不能为空
		if(StringUtils.isBlank(shopMsgEvaluate.getNodestate())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ISNULL_ERROR);
		}
		//技术评分不能为空
		if(StringUtils.isBlank(shopMsgEvaluate.getSkillGrade())){
			throw new ShopMsgException(ShopMsgCode.PARAM_SKILLGRADE_ERROR);
		}

		//态度不能为空
		if(StringUtils.isBlank(shopMsgEvaluate.getAttitudeGrade())){
			throw new ShopMsgException(ShopMsgCode.PARAM_ATTITUDEGRADE_ERROR);
		}

		//提交人不能为空
		if(StringUtils.isBlank(shopMsgEvaluate.getSubuserId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_SUBUSER_ERROR);
		}
		//时间
		shopMsgEvaluate.setAddtime(new Date());
		String nodename = "";
		switch(shopMsgEvaluate.getNodestate()){
			case "0":
				nodename = "需求响应";
				break;
			case "1":
				nodename = "总监响应";
				break;
			case "2":
				nodename = "供应商承接";
				break;
			case "3":
				nodename = "到货";
				break;
			case "4":
				nodename = "安装";
				break;
			default:
				break;
		}
		//节点状态
		shopMsgEvaluate.setNodename(nodename);
		//加入时间
		shopMsgEvaluate.setAddtime(new Date());
		this.save(shopMsgEvaluate);
	}


	@Transactional(readOnly = false)
	public List<ShopMsgEvaluate> evaluateList(String msgId) throws ShopMsgException {
		//需求id非空校验
		if(StringUtils.isBlank(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//查询列表
		return this.findList(new ShopMsgEvaluate().setMsgid(msgId));
	}

	@Transactional(readOnly = false)
	public List<ShopMsgEvaluate> getEvaluate(String msgId) throws ShopMsgException {
		//需求id非空校验
		if(StringUtils.isBlank(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//查询列表
		return this.findList(new ShopMsgEvaluate().setMsgid(msgId).setNodestate("3"));
	}

	public Page<ShopMsgEvaluate> findListGroup(Page<ShopMsgEvaluate> page, ShopMsgEvaluate shopMsgEvaluate) {
		dataRuleFilter(shopMsgEvaluate);
		shopMsgEvaluate.setPage(page);
		page.setList(mapper.findListGroup(shopMsgEvaluate));
		return page;
	}


}