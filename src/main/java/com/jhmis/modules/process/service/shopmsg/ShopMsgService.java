/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shopmsg;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.date.DateTime;
import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.haier.webservices.client.hps.project.HpsApi;
import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.Enum.*;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.common.utils.*;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.grid.HpsGrid;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsg.ShopMsgVo;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.entity.shopmsgproduct.ShopMsgProduct;
import com.jhmis.modules.process.entity.shopmsgstatus.ShopMsgStatus;
import com.jhmis.modules.process.entity.shopmsgzykc.ShopMsgZykc;
import com.jhmis.modules.process.mapper.dispatcher.ShopMsgDispatcherMapper;
import com.jhmis.modules.process.mapper.shopmsgorder.ShopMsgCustcodeOrderMapper;
import com.jhmis.modules.process.mapper.shopmsgproduct.ShopMsgProductMapper;
import com.jhmis.modules.process.mapper.shopmsgzykc.ShopMsgZykcMapper;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.grid.HpsGridService;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
import com.jhmis.modules.process.service.shopmsgproduct.ShopMsgProductService;
import com.jhmis.modules.process.service.shopmsgstatus.ShopMsgStatusService;
import com.jhmis.modules.process.service.shopmsgzykc.ShopMsgZykcService;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.mapper.dealer.DealerMapper;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.sys.entity.Role;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.mapper.RoleMapper;
import com.jhmis.modules.sys.mapper.UserMapper;
import com.jhmis.modules.sys.utils.UserUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.mapper.shopmsg.ShopMsgMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 发布需求相关Service
 * @author hdx
 * @version 2019-09-03
 */
@Service
@Transactional(readOnly = true)
public class ShopMsgService extends CrudService<ShopMsgMapper, ShopMsg> {

	@Autowired
	DealerMapper dealerMapper;

	@Autowired
	ShopMsgDispatcherMapper shopMsgDispatcherMapper;

	@Autowired
	HpsGridService hpsGridService;

	@Autowired
	ShopMsgProductService shopMsgProductService;

	@Autowired
	ShopMsgStatusService shopMsgStatusService;

	@Autowired
	SendMsgApi sendMsgApi;

	@Autowired
	ShopMsgProductMapper shopMsgProductMapper;

	@Autowired
	MsgUtils msgUtils;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	DealerService dealerService;
	@Autowired
	DealerAccountService dealerAccountService;

	@Autowired
	ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;

	@Autowired
	ShopMsgCustcodeOrderMapper shopMsgCustcodeOrderMapper;

	@Autowired
	ShopMsgZykcService shopMsgZykcService;

	@Autowired
	ShopMsgZykcMapper shopMsgZykcMapper;

	@Autowired
	RoleMapper roleMapper;

    @Autowired
    ShopMsgDispatcherService shopMsgDispatcherService;

    //hps密钥匹配
    private final static String KEY = "HaierHps20200204";


	Logger log = LoggerFactory.getLogger(ShopMsgService.class);

	public ShopMsg get(String id) {
		return super.get(id);
	}
	
	public List<ShopMsg> findList(ShopMsg shopMsg) {
		return super.findList(shopMsg);
	}

	public List<ShopMsg> findListOver(ShopMsg shopMsg) {
		return mapper.findListOver(shopMsg);
	}

	public Page<ShopMsg> findPage(Page<ShopMsg> page, ShopMsg shopMsg) {
		return super.findPage(page, shopMsg);
	}

	public Page<ShopMsg> findReturnPage(Page<ShopMsg> page, ShopMsg shopMsg) {
		dataRuleFilter(shopMsg);
		shopMsg.setPage(page);
		page.setList(mapper.findReturnList(shopMsg));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(ShopMsg shopMsg) {
		if(StringUtils.isBlank(shopMsg.getId())){
			//设置节点状态
			shopMsg.setNodetag(ProcessCode.MOBILE_PROCESS_RESPONSE.getLabel());
		}
		super.save(shopMsg);
	}

	@Transactional(readOnly = false)
	public void delete(ShopMsg shopMsg) {
		super.delete(shopMsg);
	}


	@Transactional(readOnly = false)
	public ShopMsg getShopMsgDetail(String msgId) throws ShopMsgException{
		//需求id是否为空
		if(StringUtils.isEmpty(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//根据id查询需求详情
		ShopMsg shopMsg = this.get(msgId);
		//是否存在此需求
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
        //如果是派单相关可查派单相关数据
        if(ProcessCode.IS_DISPATCHER.getLabel().equals(shopMsg.getIsDispatch())){
            //证明进入过派单,查询一下派单相关信息
            ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
            shopMsgDispatcher.setMsgId(shopMsg.getId());
            //设置msgid
            List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
            shopMsg.setListShopMsgDispatcher(listShopMsgDispatcher);
        }
        //如果经销商订单列表存在则查询相关数据
        if(!"".equals(shopMsg.getCustCode()) && null !=shopMsg.getCustCode()){
            ShopMsgCustcodeOrder smco = new ShopMsgCustcodeOrder();
            smco.setMsgId(shopMsg.getId());
            List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(smco);
            shopMsg.setListShopMsgCustcodeOrder(listShopMsgCustcodeOrder);
            Dealer d = new Dealer();
            d.setCompanyCode(listShopMsgCustcodeOrder.get(0).getCustCode());
            List<Dealer> listDealer = dealerService.findList(d);
            //赋值经销商数据
            if(listDealer!=null && listDealer.size()>0){
                shopMsg.setDealer(listDealer.get(0));
            }
        }
		return shopMsg;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param orgId  工贸编码
	 *@param:  * @param directorNumber  总监账号
	 *@Description: directorGetMsgDetail 查询总监待审核需求

	 */
	@Transactional(readOnly = false)
	public List<ShopMsg> directorGetMsgDetail(String orgId, String directorNumber) throws ShopMsgException{
		//工贸编码是否为空
		/*if(StringUtils.isEmpty(orgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_ORGID_ERROR);
		}*/
		//总监账号是否为空
		if(StringUtils.isEmpty(directorNumber)){
			throw new ShopMsgException(ShopMsgCode.PARAM_SERVICE_CHECK_ERROR);
		}
		/*//判断是否是总监
		User user = UserUtils.getByLoginName(directorNumber);
		if(null==user){
			throw new ShopMsgException(ShopMsgCode.SYSUSER_NOTEXIST_ERROR);
		}
		//判断是否是总监权限
		List<Role> roleList = user.getRoleList();
		if(null==roleList||0==roleList.size()){
			throw new ShopMsgException(ShopMsgCode.ROLES_NOTEXIST_ERROR);
		}
		//判断是否是是总监权限
		Boolean hasDirectorRoles = false;
		for(Role role:roleList){
			if(Constants.DIRECTOR_ROLE_ID.equals(role.getId())){
				hasDirectorRoles = true;
			}
		}
		//证明没有总监权限
		if(!hasDirectorRoles){
			throw new ShopMsgException(ShopMsgCode.OPEARTROLES_NOTEXIST_ERROR);
		}*/
		ShopMsg shopMsg = new ShopMsg();
		shopMsg.setOrgId(orgId);
		shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_AUDIT_PASS.getLabel());
		//添加排序
		Page<ShopMsg> shopMsgPage = new Page<ShopMsg>();
		shopMsgPage.setOrderBy(" a.create_date desc");
		shopMsg.setPage(shopMsgPage);
		return mapper.findList(shopMsg);
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param shopMsg
	 *@Description: addShopMsg 客服进行审核

	 */
	@Transactional(readOnly = false)
	public void ptCheck(ShopMsg shopMsg) throws ShopMsgException{
		//判断是否为空
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//审核状态是否为空
		if(StringUtils.isEmpty(shopMsg.getIsCheck())){
			throw new ShopMsgException(ShopMsgCode.PARAM_SERVICE_CHECK_ERROR);
		}
		//判断是否已经确认公司名称及组织机构代码证
		if(StringUtils.isEmpty(shopMsg.getCompanyOrgName())|| StringUtils.isEmpty(shopMsg.getCompanyOrgCode())){
			throw new ShopMsgException(ShopMsgCode.ORG_CHECK_EXCEPTION);
		}
		//判断是否在待处理状态
		if(!MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel().equals(shopMsg.getMsgFlag())){
			throw new ShopMsgException(ShopMsgCode.MSGFLAG_NOINPROCESS_ERROR);
		}
		//isCheck 0为 关闭  1 通过
		String statusNode = MsgFlagCode.PLATFORM_AUDIT_PASS.getLabel();
		String statusName = MsgFlagCode.PLATFORM_AUDIT_PASS.getValue();
		if(ProcessCode.SERVICE_ISCHECK_NO_PASS.getLabel().equals(shopMsg.getIsCheck())){
			//不通过
			shopMsg.setPtIspass(ProcessCode.SERVICE_ISCHECK_NO_PASS.getLabel());
			shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_AUDIT_NOT_PASS.getLabel());
			statusNode = MsgFlagCode.PLATFORM_AUDIT_NOT_PASS.getLabel();
			statusName = MsgFlagCode.PLATFORM_AUDIT_NOT_PASS.getValue();
		}else{
			//通过
			shopMsg.setPtIspass(ProcessCode.SERVICE_ISCHECK_PASS.getLabel());
			shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_AUDIT_PASS.getLabel());
			//设置手机端全流程节点状态
			shopMsg.setNodetag(ProcessCode.MOBILE_PROCESS_DOCUMENTARY.getLabel());
		}
		//平台审核时间
		shopMsg.setPtCheckDate(new Date());
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		shopMsg.setPtChecker(currentUser.getLoginName());
		mapper.update(shopMsg);
		//需求状态履历表
		String content = currentUser.getLoginName() + "平台,已于"+ new Date().toLocaleString() + "审核一条采购需求，需求id=" + shopMsg.getId() + ",审核结果：" + statusName;
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),currentUser.getLoginName(),OperatorTypeCode.CUSTOMER_SERVICE.getValue(),statusNode,statusName,content);
		shopMsgStatusService.save(shopMsgStatus);
		//根据工贸查询总监相关信息
		List<User> listUser = UserUtils.getAboutUser(shopMsg.getOrgId());
		//找到总监(只可有一个总监)
		if(null==listUser || listUser.size()==0){
			log.error(ShopMsgCode.DIRECT_NOTEXIST_ERROR.getValue());
			return;
		}
		for (User user:listUser){
			User u = UserUtils.getByLoginName(user.getLoginName());
			//判断是否是总监权限
			Boolean flag = this.checkUserRoles(u.getLoginName(),Constants.DIRECTOR_ROLE_ID);
			if(flag){
				//通知总监需要处理(短信)
				List<String> listTpl = new ArrayList<>();
				//时间参数
				listTpl.add(new Date().toLocaleString());
				//公司名
				listTpl.add(shopMsg.getCompanyName());
				//联系人
				listTpl.add(shopMsg.getConnectName());
				try {
					//发送短信
					sendMsgApi.tplSendMessage(u.getMobile(),"platform_pass_to_GM",listTpl,"0");
				} catch (Exception e) {
					log.error("平台审核后通知总监审核短信异常,原因:" + e.getMessage() );
				}
			}
			//通知总监需要处理(app推送)
			Map<String, String> params = new HashMap<String, String>();
			StringBuilder sb = new StringBuilder();
			StringBuilder sendAppMsg = new StringBuilder();
			sb.append(u.getLoginName());
			params.put("pushUser", sb.toString());
			//如果是青岛工贸下的还需要加上01411729
			if ("12A02".equals(shopMsg.getOrgId())) {
				sb.append(";01411729");
				params.put("pushUser", sb.toString());
			}
			params.put("content", "项目审核通知：您于" + new Date().toLocaleString() + "收到最新信息，请及时审核");
			params.put("jumpType", "qd1");
			params.put("id", shopMsg.getId());
			sendAppMsg.append(params);
			// 是否推送成功
			try{
				MsgUtils.sendApp(params);
			}catch (Exception e){
				logger.error("发送app推送消息(平台审核后通知总监及时审核)异常" + e.getMessage()+",数据=" + sendAppMsg);
			}
		}
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:09 2019/9/23
	 *@param:  * @param msgId
	 * @param operator 操作人
	 * @param statusNode  状态节点
	 * @param statusName  状态节点
	 * @param operatorType 操作人类型
	 * @param content  内容
	 *@Description: 获取整合后ShopMsgStatus对象
	 */
	public static ShopMsgStatus returnShopMsgStatus(String msgId, String operator,String operatorType, String statusNode,String statusName,  String content){
		ShopMsgStatus shopMsgStatus = new ShopMsgStatus();
		shopMsgStatus.setMsgId(msgId);
		shopMsgStatus.setOperator(operator);
		shopMsgStatus.setStatusNode(statusNode);
		shopMsgStatus.setStatusName(statusName);
		shopMsgStatus.setOperatorType(operatorType);
		shopMsgStatus.setContent(content);
		shopMsgStatus.setCreateDate(new Date());
		return  shopMsgStatus;
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param shopMsg dealerId
	 *@Description: addShopMsg 进行派单（包含平台客服直派和派单处理）

	 */
	@Transactional(readOnly = false)
	public void dispatcherOrder(ShopMsg shopMsg,String dealerId) throws ShopMsgException{
		//判断是否为空
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//判断是否为空
		if(StringUtils.isEmpty(dealerId)){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
        //判断是否处于此节点状态可处理
        if(!MsgFlagCode.WAITING_LIST.getLabel().equals(shopMsg.getMsgFlag())&& !MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel().equals(shopMsg.getMsgFlag())&& !MsgFlagCode.CHOOSE_UNDERTAKE_BEFORE_CLOSING.getLabel().equals(shopMsg.getMsgFlag())){
            throw new ShopMsgException(ShopMsgCode.NODE_EXCEPTION);
        }
		//查看deler是否存在
		Dealer d = dealerMapper.get(dealerId);
		//判断是否为空
		if(null==d){
			throw new ShopMsgException(ShopMsgCode.DEALER_NOTEXIST_ERROR);
		}
        //获取当前操作用户
        User currentUser = UserUtils.getUser();
		//判断是否是平台客服直派还是在派单里处理
        if(shopMsg.getMsgFlag().equals(MsgFlagCode.WAITING_LIST.getLabel())){
            //此处是派单管理里进行派单
            shopMsg.setMsgFlag(MsgFlagCode.PAYMENT_MANAGEMENT_PAYMENT.getLabel());
        }else{
            //平台通过
            shopMsg.setPtIspass(ProcessCode.SERVICE_ISCHECK_PASS.getLabel());
            //平台审核人
            shopMsg.setPtChecker(currentUser.getLoginName());
            //平台审核时间
            shopMsg.setPtCheckDate(new Date());
            //平台直派状态
            shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_DIRECT_DELIVERY.getLabel());
        }
        //指派的经销商88
		shopMsg.setCustCode(d.getCompanyCode());
		//如果存在已派单标识
		shopMsg.setIsDispatch(ProcessCode.IS_DISPATCHER.getLabel());
		//需求信息更新
		try{
			mapper.update(shopMsg);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.UPDATE_MSG_EXCEPTION + e.getMessage());
		}

        if(shopMsg.getMsgFlag().equals(MsgFlagCode.WAITING_LIST.getLabel())){
            //此处是派单管理里进行派单
            ShopMsgDispatcher sm = new ShopMsgDispatcher();
            sm.setMsgId(shopMsg.getId());
            sm.setIsClosed(ProcessCode.DISPATCHER_MSG_IS_CLOSE.getLabel());
            List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherMapper.findList(sm);
            if(null==listShopMsgDispatcher||listShopMsgDispatcher.size()==0){
                throw new ShopMsgException(ShopMsgCode.DISPATCHER_EXCEPTION);
            }
            ShopMsgDispatcher smdUpdate = listShopMsgDispatcher.get(0);
            //派给哪个经销商
            smdUpdate.setCustcode(d.getCompanyCode());
            //派单时间
            smdUpdate.setDispaDate(new Date());
            //来源
            smdUpdate.setSource(ProcessCode.DISORDER_SOURCE_TIMEOUT.getValue());
            //操作派单人员
            smdUpdate.setDispaUser(currentUser.getLoginName());
            //派单标识已派单
            smdUpdate.setDispaFlag(ProcessCode.IS_DISPATCHER.getLabel());
            //平台派单
            smdUpdate.setDispaType(ProcessCode.DISPA_TYPE_PT.getLabel());
            if(StringUtils.isNotBlank(shopMsg.getDispatcherId())){
				smdUpdate.setId(shopMsg.getDispatcherId());
			}
            //更新派单表
            try {
                shopMsgDispatcherService.save(smdUpdate);
            }catch (Exception e){
                throw new ShopMsgException(ShopMsgCode.UPDATE_DISPATCHER_EXCEPTION + e.getMessage());
            }
        }else{
            //此处是客服直接派单
            //派单表更新
            ShopMsgDispatcher s = new ShopMsgDispatcher();
            //派给哪个经销商
            s.setCustcode(d.getCompanyCode());
            //需求id
            s.setMsgId(shopMsg.getId());
            //派单时间
            s.setDispaDate(new Date());
            //来源
            s.setSource(ProcessCode.DISORDER_SOURCE_DISPAT.getValue());
            //操作派单人员
            s.setDispaUser(currentUser.getLoginName());
            //派单标识已派单
            s.setDispaFlag(ProcessCode.IS_DISPATCHER.getLabel());
            //平台派单
            s.setDispaType(ProcessCode.DISPA_TYPE_PT.getLabel());
            //派单id
            s.setId(IdGen.uuid());
            //派单创建时间
            s.setCreateDate(new Date());
			//是否关闭为未关闭
			s.setIsClosed(ProcessCode.NO.getLabel());
            try{
                shopMsgDispatcherMapper.insert(s);
            }catch (Exception e){
                throw new ShopMsgException(ShopMsgCode.INSERT_DISPATCHER_EXCEPTION + e.getMessage());
            }
        }

		//需求订单表生成
		ShopMsgCustcodeOrder shco = new ShopMsgCustcodeOrder();
		//设置id
		shco.setMsgId(shopMsg.getId());
		//设置经销商编码
		shco.setCustCode(d.getCompanyCode());
		//设置工贸编码
		shco.setOrgId(shopMsg.getOrgId());
		//设置工贸名称
		shco.setOrgName(shopMsg.getOrgName());
		//设置需求的用户公司名称
		shco.setCompanyName(shopMsg.getCompanyName());
		//设置来源
		shco.setFromSource(ProcessCode.ORDER_SOURCE_PLATFORM_ASSIGNMENT.getLabel());
		//订单状态
        shco.setCancelFlag(ProcessCode.ORDER_CANCEL_FLAG_WAIT.getLabel());
        //经销商名称
        shco.setCustName(d.getCompanyName());
        //地址
        shco.setAddress(shopMsg.getAddress());
        //创建时间
        shco.setCreateDate(new Date());
        //产品组
		shco.setProGroup(shopMsg.getProGroup());
		//产品组编码
		shco.setProGroupCode(shopMsg.getProGroupCode());
		//是否中标标识未中标
		shco.setIsBind(ProcessCode.NO.getLabel());
        //订单编号
        if(StringUtils.isNotBlank(shopMsg.getMsgNo())){
            shco.setOrderNo(shopMsg.getMsgNo());
        }else{
            shco.setOrderNo(shopMsg.getId());
        }
        //添加
		try{
		shopMsgCustcodeOrderService.save(shco);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.ORDER_INSERT_EXCEPTION + e.getMessage());
		}
		//进行履历添加
		//需求状态履历表
        String content = "";
        if(shopMsg.getMsgFlag().equals(MsgFlagCode.WAITING_LIST.getLabel())){
             content = currentUser.getLoginName() + "平台(客服),已于"+ new Date().toLocaleString() + "在派单管理中派单一条抢单超时需求，需求id=" + shopMsg.getId() + ",派给" + d.getCompanyName();
        }else {
             content = currentUser.getLoginName() + "平台(客服),已于"+ new Date().toLocaleString() + "派单一条需求，需求id=" + shopMsg.getId() + ",派给" + d.getCompanyName();
        }
        //记录履历表
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),currentUser.getLoginName(),OperatorTypeCode.CUSTOMER_SERVICE.getValue(),MsgFlagCode.PLATFORM_DIRECT_DELIVERY.getLabel(),"已分配工程商",content);
		shopMsgStatusService.save(shopMsgStatus);
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param shopMsg
	 *@Description: addShopMsg 关闭需求

	 */
	@Transactional(readOnly = false)
	public void closeMsg(ShopMsg shopMsg) throws ShopMsgException{
		//判断是否为空
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//获取需求状态
		String msgFlag = shopMsg.getMsgFlag();
		//如果等于待审核0和经销商选择承接方式前关闭31的状态可以关闭其余不符合要求
		if(MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel().equals(msgFlag)){
			shopMsg.setMsgFlag(MsgFlagCode.CUSTOMER_CLOSING.getLabel());
		}else{
			//不可关闭需求，需求在流程中
			throw new ShopMsgException(ShopMsgCode.CLOSE_SHOPMSG_ERROR);
		}
		this.updateMsg(shopMsg);
		//需求状态履历表(上传见证性材料)
		String statusNode = MsgFlagCode.CUSTOMER_CLOSING.getLabel();
		String statusName = MsgFlagCode.CUSTOMER_CLOSING.getValue();
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		String content = currentUser + "-平台,已于"+ new Date().toLocaleString() + "关闭需求,id=" + shopMsg.getId();
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),currentUser.getLoginName(),OperatorTypeCode.CUSTOMER_SERVICE.getValue(),statusNode,statusName,content);
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param id  需求id
	 *@param:  * @param remarks  总监备注
	 *@param:  * @param remarksperson 总监备注人
	 *@Description: directorRemarks 对应App留言板-行业总监未处理原因备注

	 */
	@Transactional(readOnly = false)
	public void directorRemarks(String msgId, String remarks, String remarksperson) throws ShopMsgException{
		//需求id是否为空
		if(StringUtils.isEmpty(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//备注是否为空
		if(StringUtils.isEmpty(remarks)){
			throw new ShopMsgException(ShopMsgCode.PARAM_REMARK_ERROR);
		}
		//备注人是否为空
		if(StringUtils.isEmpty(remarksperson)){
			throw new ShopMsgException(ShopMsgCode.PARAM_REMARKSPERSON_ERROR);
		}
		//查看是否存在此需求
		ShopMsg sm = mapper.get(msgId);
		if(null==sm){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//设置总监备注
		sm.setRemarks(remarks);
		//设置总监备注人
		sm.setRemarksPerson(remarksperson);
		//设置总监备注时间
		sm.setRemarksDate(new Date());
		//进行更新需求表
		try{
			mapper.update(sm);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.UPDATE_MSG_EXCEPTION);
		}
	}


	//校验是用户是否有此权限角色
	public boolean checkUserRoles(String no,String roleId) throws ShopMsgException{
		//判断是否是总监
		User user = UserUtils.getByLoginName(no);
		if(null==user){
			throw new ShopMsgException(ShopMsgCode.SYSUSER_NOTEXIST_ERROR);
		}
		//判断是否是总监权限
		List<Role> roleList = user.getRoleList();
		if(null==roleList||0==roleList.size()){
			throw new ShopMsgException(ShopMsgCode.ROLES_NOTEXIST_ERROR);
		}
		//判断是否是是总监权限
		Boolean hasDirectorRoles = false;
		for(Role role:roleList){
			if(roleId.equals(role.getId())){
				hasDirectorRoles = true;
			}
		}
		//证明没有总监权限
		if(!hasDirectorRoles){
			throw new ShopMsgException(ShopMsgCode.OPEARTROLES_NOTEXIST_ERROR);
		}
		return hasDirectorRoles;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param shopMsg
	 *@Description: directorCheck 总监审核

	 */
	@Transactional(readOnly = false)
	public void directorCheck(ShopMsg shopMsg) throws ShopMsgException{
		//需求id是否为空
		if(StringUtils.isEmpty(shopMsg.getId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//产品组是否为空
		if(StringUtils.isEmpty(shopMsg.getProGroup())){
			throw new ShopMsgException(ShopMsgCode.PARAM_PROGROUP_ERROR);
		}
		//联系人是否为空
		if(StringUtils.isEmpty(shopMsg.getConnectName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_CONNECTNAME_ERROR);
		}
		//公司名是否为空
		if(StringUtils.isEmpty(shopMsg.getCompanyName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_COMPANYNAME_ERROR);
		}
		//联系电话是否为空
		if(StringUtils.isEmpty(shopMsg.getMobile())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MOBILE_ERROR);
		}
		//省份id是否为空
		if(StringUtils.isEmpty(shopMsg.getProvinceId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_PROVICEID_ERROR);
		}
		//城市id是否为空
		if(StringUtils.isEmpty(shopMsg.getCityId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_CITYID_ERROR);
		}
		//地区id是否为空
		if(StringUtils.isEmpty(shopMsg.getDistricId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICID_ERROR);
		}
		//省份是否为空
		if(StringUtils.isEmpty(shopMsg.getProvinceName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_PROVICENAME_ERROR);
		}
		//城市是否为空
		if(StringUtils.isEmpty(shopMsg.getCityName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_CITYNAME_ERROR);
		}
		//地区是否为空
		if(StringUtils.isEmpty(shopMsg.getDistricName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICNAME_ERROR);
		}
		//总监账号是否为空
		if(StringUtils.isEmpty(shopMsg.getDirectorNo())){
			throw new ShopMsgException(ShopMsgCode.PARAM_DIRECTORNO_ERROR);
		}
		//总监审核结果是否为空
		if(StringUtils.isEmpty(shopMsg.getAppIspass())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MANAGERNAME_ERROR);
		}
		//根据id查询已存在库里
		ShopMsg hasShopMsg = mapper.get(shopMsg.getId());
		if(null==hasShopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		/*//判断是否是总监角色
		boolean isUserRoles = checkUserRoles(shopMsg.getDirectorNo(),Constants.DIRECTOR_ROLE_ID);
		if(!isUserRoles){
			//证明用户没有此权限
			throw new ShopMsgException(ShopMsgCode.USERDIRECTROLES_NOTEXIST_ERROR);
		}*/
		//判断此刻需求的状态
		if(!MsgFlagCode.PLATFORM_AUDIT_PASS.getLabel().equals(hasShopMsg.getMsgFlag())){
			//如果当前状态不等于平台审核通过
			throw new ShopMsgException(ShopMsgCode.NODE_EXCEPTION);
		}
		//进行数据更新
		String isPass = ProcessCode.DIRECTOR_ISCHECK_PASS.getLabel();
		//判断需求是否总监通过
		if(ProcessCode.DIRECTOR_ISCHECK_NO_PASS.getLabel().equals(shopMsg.getAppIspass())){
			isPass = ProcessCode.DIRECTOR_ISCHECK_NO_PASS.getLabel();
			//设置总监废弃原因
			hasShopMsg.setAppCancleReason(shopMsg.getAppCancleReason());
			//设置总监废弃详细描述
			hasShopMsg.setAppDescp(shopMsg.getAppDescp());
			//设置需求状态,不通过
			hasShopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_FAILED_TO_APPROVE.getLabel());
		}else{
            //负责人编号是否为空
            if(StringUtils.isEmpty(shopMsg.getManagerNo())){
                throw new ShopMsgException(ShopMsgCode.PARAM_MANAGERNO_ERROR);
            }
            //负责人名称是否为空
            if(StringUtils.isEmpty(shopMsg.getManagerName())){
                throw new ShopMsgException(ShopMsgCode.PARAM_MANAGERNAME_ERROR);
            }
			//设置全流程节点状态
			hasShopMsg.setNodetag(ProcessCode.MOBILE_PROCESS_DOCUMENTARY_SE.getLabel());
			//设置需求状态,通过
			hasShopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_APPROVED.getLabel());
		}
		//设置审核结果
		hasShopMsg.setAppIspass(isPass);
		//设置审核人
		hasShopMsg.setAppChecker(shopMsg.getDirectorNo());
		//设置审核时间
		hasShopMsg.setAppCheckDate(new Date());
		//设置联系人
		hasShopMsg.setConnectName(shopMsg.getConnectName());
		//设置公司名
		hasShopMsg.setCompanyName(shopMsg.getCompanyName());
		//设置联系电话
		hasShopMsg.setMobile(shopMsg.getMobile());
		//设置省份id
		hasShopMsg.setProvinceId(shopMsg.getProvinceId());
		//设置城市id
		hasShopMsg.setCityId(shopMsg.getCityId());
		//设置地区id
		hasShopMsg.setDistricId(shopMsg.getDistricId());
		//设置省份
		hasShopMsg.setProvinceName(shopMsg.getProvinceName());
		//设置城市
		hasShopMsg.setCityName(shopMsg.getCityName());
		//设置地区
		hasShopMsg.setDistricName(shopMsg.getDistricName());
		//设置负责人编号
		hasShopMsg.setManagerNo(shopMsg.getManagerNo());
		//设置负责人姓名
		hasShopMsg.setManagerName(shopMsg.getManagerName());
		//设置产品组
		hasShopMsg.setProGroup(shopMsg.getProGroup());
		List<String> proGroupList = Arrays.asList(shopMsg.getProGroup().split(";"));
		String pro_code = "";
		//进行产品组的更新
		for(String pindex:proGroupList){
			pro_code = pro_code.concat(ProductCode.getIndex(pindex) + ";");
		}
		//拼接产品组编码时是否有多余分号
		if(pro_code.contains(";")){
			pro_code = pro_code.substring(0,pro_code.length()-1);
		}
		//设置产品组对应码表
		hasShopMsg.setProGroupCode(pro_code);
		//产品子表录入先删后插
		shopMsgProductMapper.execDeleteSql("delete from shop_msg_product where msg_id = '" + shopMsg.getId()+ "'");
		String proGroupCode = hasShopMsg.getProGroupCode();
		if(StringUtils.isNotEmpty(proGroupCode)){
			String pdcode[] = proGroupCode.split(";");
			for(int i=0; i<pdcode.length; i++){
				ShopMsgProduct s = new ShopMsgProduct();
				//是否智慧
				s.setBeWisdom("0");
				//如果数量不为空
				if(StringUtils.isNotEmpty(shopMsg.getQuantity())){
					s.setEstimatedQuantity(shopMsg.getQuantity());
				}else{
					s.setEstimatedQuantity("0");
				}
				//如果预计的采购预算不为空
				if(StringUtils.isNotEmpty(shopMsg.getBudget())){
					s.setPurchaseBudget(shopMsg.getBudget());
				}else{
					s.setPurchaseBudget("0");
				}
				//需求id0·
				s.setMsgId(shopMsg.getId());
				//设置产品编码（hps对应编码）
				s.setProductGroupCode(ProductCode.getValue(pdcode[i]));
				//设置产品名称（用户语言）
				s.setProductGroupName(ProductCode.getLabel(pdcode[i]));
				shopMsgProductService.save(s);
			}
		}
		//根据地区id进行工贸选取
		HpsGrid hpsGrid = hpsGridService.findUniqueByProperty("district_code",shopMsg.getDistricId());
		if(null!=hpsGrid){
			//设置工贸id
			hasShopMsg.setOrgId(hpsGrid.getCenterCode());
			//设置工贸名称
			hasShopMsg.setOrgName(hpsGrid.getCenterName());
		}
		//更新需求表
		mapper.update(hasShopMsg);
		//给可以抢单的经销商通知消息
		Dealer d = new Dealer();
		d.setGmId(hasShopMsg.getOrgId());
		d.setUndertakeArea(hasShopMsg.getCityName());
		d.setUnderProduct(hasShopMsg.getProGroup());
		List<Dealer> listDealer = null;
		try{
		    listDealer = dealerService.findList(d);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.APPCHECK_EXCEPTION);
		}
		if(listDealer==null|| listDealer.size()==0){
			//如果不存在
			log.error("/directorApproval-总监审核接口" + ShopMsgCode.DELEARGARB_NOTEXIST_ERROR.getDesc());
		}
		// 声明推送app接口参数
		Map<String, String> params = new HashMap<String, String>();
		//声明需要发送的经销商记录值
		StringBuilder sb = new StringBuilder();
		StringBuilder sendAppMsg = new StringBuilder();
		//时间转换
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//判断是否可以发送短信
		if(ProcessCode.SERVICE_ISCHECK_PASS.getLabel().equals(shopMsg.getAppIspass())){
		//循环可抢单的经销商进行消息推送
		for(Dealer dealer:listDealer){
			List<String> listTpl = new ArrayList<>();
			//时间参数
			listTpl.add(new Date().toLocaleString());
			try {
				//发送短信
				sendMsgApi.tplSendMessage(dealer.getMobile(),"dealer_grab_sheet",listTpl,"0");
			} catch (Exception e) {
				log.error("总监审核异常,原因:" + e.getMessage() );
			}
			// 获取经销商pushUser
			sb.append(dealer.getCompanyCode() + ";");
		}
		//发送app推送消息(通知经销商抢单)
		try {
			params.put("pushUser", sb.toString().substring(0, sb.length() - 1));
			params.put("content", "抢单提醒： 您于" + sdf.format(new Date()) + "收到抢单信息，请及时抢单");
			params.put("jumpType", "qd2");
			params.put("mid", hasShopMsg.getId());
			sendAppMsg.append(params);
			// 是否推送成功
			MsgUtils.sendApp(params);
		} catch (Exception e) {
			logger.error("发送app推送消息(通知经销商抢单)异常" + e.getMessage()+",数据=" + sendAppMsg);
		}
		}
        //判断需求是否总监通过
        String content = "";
		String statusNode = "";
        String statusName = "";

        if(ProcessCode.DIRECTOR_ISCHECK_NO_PASS.getLabel().equals(shopMsg.getAppIspass())){
            //拒绝
            content = shopMsg.getDirectorNo() + ",已于"+ new Date().toLocaleString() + "审核一条采购需求，需求id=" + shopMsg.getId() + ",审核结果:" + ProcessCode.DIRECTOR_ISCHECK_NO_PASS.getValue();
            statusNode = MsgFlagCode.DIRECTOR_FAILED_TO_APPROVE.getLabel();
            statusName = MsgFlagCode.DIRECTOR_FAILED_TO_APPROVE.getValue();
        }else{
            //成功
            content = shopMsg.getDirectorNo() + ",已于"+ new Date().toLocaleString() + "审核一条采购需求，需求id=" + shopMsg.getId() + ",审核结果:" + ProcessCode.DIRECTOR_ISCHECK_PASS.getValue();
            statusNode = MsgFlagCode.DIRECTOR_APPROVED.getLabel();
            statusName = "分配海尔责任人";
        }
        //需求状态履历表
        ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),shopMsg.getDirectorNo(),OperatorTypeCode.CHIEF_INSPECTOR.getValue(),statusNode,statusName,content);
        shopMsgStatusService.save(shopMsgStatus);
	}

	public static void main(String[] args) {
	Map<String, String> params = new HashMap<String, String>();
		StringBuilder sendAppMsg = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		params.put("pushUser", "00000006");
		params.put("content", "抢单提醒： 您于" + sdf.format(new Date()) + "收到抢单信息，请及时抢单");
		params.put("jumpType", "qd2");
		params.put("mid", "12302");
		sendAppMsg.append(params);
		// 是否推送成功
		try {
			boolean flag = MsgUtils.sendApp(params);
		} catch (Exception e) {
		}
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param null
	 *@Description: addShopMsg 开启需求

	 */
	@Transactional(readOnly = false)
	public void restart(ShopMsg shopMsg) throws ShopMsgException{
		//判断是否为空
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//获取需求状态
		String msgFlag = shopMsg.getMsgFlag();
		//如果等于60客服关闭可以开启其余不符合要求
		if(MsgFlagCode.CUSTOMER_CLOSING.getLabel().equals(msgFlag)){
			shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel());
		}else{
			//不可开启需求，需求在流程中
			throw new ShopMsgException(ShopMsgCode.CLOSE_SHOPMSG_ERROR);
		}
		//需求状态履历表(上传见证性材料)
		this.updateMsg(shopMsg);
		//需求状态履历表(上传见证性材料)
		String statusNode = MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel();
		String statusName = MsgFlagCode.PLATFORM_TO_BE_AUDITED.getValue();
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		String content = currentUser + "-平台,已于"+ new Date().toLocaleString() + "开启需求,id=" + shopMsg.getId();
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),currentUser.getLoginName(),OperatorTypeCode.CUSTOMER_SERVICE.getValue(),statusNode,statusName,content);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param null
	 *@Description: sendShortMsg 发送短信

	 */
	@Transactional(readOnly = false)
	public void sendShortMsg(ShopMsg shopMsg,String content) throws ShopMsgException{
		//shopMsg是否为空
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//内容是否为空
		if(StringUtils.isEmpty(content)){
			throw new ShopMsgException(ShopMsgCode.PARAM_CONTENT_ERROR);
		}
		//发送短信
		try {
			SendMsgApi.SendMessage(shopMsg.getMobile(),content);
		} catch (Exception e) {
			throw new ShopMsgException(ShopMsgCode.SENDMSG_ERROR);
		}
		//更新
		shopMsg.setIsRead(ProcessCode.IS_SENDMSG.getLabel());
		mapper.update(shopMsg);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param null
	 *@Description: sendShortMsg 发送短信

	 */
	@Transactional(readOnly = false)
	public void hpsSendMsg(String key, String mobile,String content) throws ShopMsgException{
		//密钥不能为空
		if(StringUtils.isEmpty(key)){
			throw new ShopMsgException("密钥不能为空");
		}
		//密钥不能为空
		if(!KEY.equals(key)){
			throw new ShopMsgException("密钥不正确");
		}
		//手机号不能为空
		if(StringUtils.isEmpty(mobile)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MOBILE_ERROR);
		}
		//手机号正则
		if(!RegexUtils.checkMobile(mobile)){
			throw new ShopMsgException(ShopMsgCode.REG_MOBILE_ERROR);
		}

		//内容是否为空
		if(StringUtils.isEmpty(content)){
			throw new ShopMsgException(ShopMsgCode.PARAM_CONTENT_ERROR);
		}
		//发送短信
		try {
			SendMsgApi.SendMessage(mobile,content);
		} catch (Exception e) {
			throw new ShopMsgException(e.getMessage());
		}
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param null
	 *@Description: sendShortMsg 发送短信

	 */
	@Transactional(readOnly = false)
	public String hpsSendMsgByCode(String key, String cusCode,String content) throws ShopMsgException{
		//密钥不能为空
		if(StringUtils.isEmpty(key)){
			throw new ShopMsgException("密钥不能为空");
		}
		//密钥不能为空
		if(!KEY.equals(key)){
			throw new ShopMsgException("密钥不正确");
		}
		//经销商不能为空
		if(StringUtils.isEmpty(cusCode)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
		}
		//经销商编码
		try{

		}catch (Exception e){

		}
		Dealer dealer = dealerService.findUniqueByProperty("company_code",cusCode);
		//如果不存在
		if(null==dealer){
			throw new ShopMsgException("此经销商不存在");
		}
		/*DealerAccount dealerAccount = dealerAccountService.getByName(cusCode);
		if(dealerAccount==null){
			throw new ShopMsgException("此经销商不存在");
		}*/
		//手机号
		String mobile = dealer.getMobile();
		//手机号不能为空
		if(StringUtils.isEmpty(mobile)){
			throw new ShopMsgException("此经销商手机号不存");
		}
		//手机号正则
		if(!RegexUtils.checkMobile(mobile)){
			throw new ShopMsgException("此经销商手机号格式不正确");
		}
		//内容是否为空
		if(StringUtils.isEmpty(content)){
			throw new ShopMsgException(ShopMsgCode.PARAM_CONTENT_ERROR);
		}
		//发送短信
		try {
			SendMsgApi.SendMessage(mobile,content);
		} catch (Exception e) {
			throw new ShopMsgException(e.getMessage());
		}
		return mobile;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param shopMsg
	 *@Description: updateMsg 更新需求

	 */
	@Transactional(readOnly = false)
	public void updateMsg(ShopMsg shopMsg) throws ShopMsgException{
		//shopMsg是否为空
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		try{
			mapper.update(shopMsg);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.UPDATE_MSG_EXCEPTION);
		}

	}


	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param null
	 *@Description: addShopMsg 保存需求

	 */
	@Transactional(readOnly = false)
	public void saveByManager(ShopMsg shopMsg) throws Exception{
		boolean newFlag = false;
		//根据地区id进行工贸选取
		HpsGrid hpsGrid = hpsGridService.findUniqueByProperty("district_code",shopMsg.getDistricId());
		if(null!=hpsGrid){
			//设置工贸id
			shopMsg.setOrgId(hpsGrid.getCenterCode());
			//设置工贸名称
			shopMsg.setOrgName(hpsGrid.getCenterName());
		}
		if(StringUtils.isEmpty(shopMsg.getId())){
			//标识状态0 待审核
			shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel());
			//是否已读 0 未读
			shopMsg.setIsRead(ProcessCode.MSG_NO_READ.getLabel());
			//创建时间
			shopMsg.setCreateDate(new Date());
			//派单标识，未派单
			shopMsg.setIsDispatch(ProcessCode.NO_DISPATCHER.getLabel());
			//证明是第一次新增需求
			newFlag = true;
			//进行单号的生成
			shopMsg.setMsgNo(msgUtils.getOrderIdByTime());
			//发送短信
			try {
				List<String> listTpl = new ArrayList<>();
				//根据工贸查询总监相关信息
				List<User> listUser = UserUtils.getAboutUser(shopMsg.getOrgId());
				//找到总监(只可有一个总监)
				if(null==listUser || listUser.size()==0){
					log.error(ShopMsgCode.DIRECT_NOTEXIST_ERROR.getValue());
				}
				User u = listUser.get(0);
				//判断是否是总监权限
				List<Role> roleList = u.getRoleList();
				for(Role role:roleList){
					if(Constants.DIRECTOR_ROLE_ID.equals(role.getId())){
						//证明此是总监并发送给其短信
						//总监名
						listTpl.add(u.getName());
						//总监手机号
						listTpl.add(u.getMobile());
						//发送短信模板
						sendMsgApi.tplSendMessage(shopMsg.getMobile(),"user_publishing_requirement",listTpl,"0");
					}
				}
			}catch (Exception e){
				logger.error(e.getMessage());
			}
		}
		//省市区全拼
		shopMsg.setAddress(shopMsg.getProvinceName()+" " +shopMsg.getCityName() + " " + shopMsg.getDistricName() );
		//分类(询价)
        shopMsg.setCategory(ProcessCode.MSG_CATEGORY_ENQUIRY.getLabel());
        //节点标识
		shopMsg.setNodetag(ProcessCode.MOBILE_PROCESS_RESPONSE.getLabel());
		super.save(shopMsg);
		//判断如果是否初次录入，则计入履历
		if(newFlag){
			//需求状态履历表
			String content = shopMsg.getCompanyName() + ",已于"+ new Date().toLocaleString() + "发布一条采购需求，需求id=" + shopMsg.getId();
			ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),shopMsg.getConnectName(),OperatorTypeCode.CUSTOMER_SERVICE.getValue(),MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel(),"需求提报",content);
			shopMsgStatusService.save(shopMsgStatus);
		}
		//产品子表录入先删后插
		shopMsgProductMapper.deleteByMsgId(shopMsg.getId());
		String proGroupCode = shopMsg.getProGroupCode();
		if(StringUtils.isNotEmpty(proGroupCode)){
			String pdcode[] = proGroupCode.split(",");
			for(int i=0; i<pdcode.length; i++){
				ShopMsgProduct s = new ShopMsgProduct();
				//是否智慧
				s.setBeWisdom("0");
				//数量
				s.setEstimatedQuantity("0");
				//预计的采购预算
				s.setPurchaseBudget("0");
				//需求id
				s.setMsgId(shopMsg.getId());
				//设置产品编码（hps对应编码）
				s.setProductGroupCode(ProductCode.getValue(pdcode[i]));
				//设置产品名称（用户语言）
				s.setProductGroupName(ProductCode.getLabel(pdcode[i]));
				shopMsgProductService.save(s);
			}
		}
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 14:47 2019/9/2
	 *@param:  * @param null
	 *@Description: addShopMsg 对外发布需求接口

	 */
	@Transactional(readOnly = false)
	public void addShopMsg(ShopMsg shopMsg) throws ShopMsgException {
		//校验手机号是否为空
		if(StringUtils.isEmpty(shopMsg.getMobile())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MOBILE_ERROR);
		}
		//校验联系人是否为空
		if(StringUtils.isEmpty(shopMsg.getConnectName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_CONNECTNAME_ERROR);
		}
        //校验公司名是否为空
        if(StringUtils.isEmpty(shopMsg.getCompanyName())){
            throw new ShopMsgException(ShopMsgCode.PARAM_COMPANYNAME_ERROR);
        }
		//校验手机号正则规则
		boolean mobileFlag = RegexUtils.checkMobile(shopMsg.getMobile());
		if(!mobileFlag){
			throw new ShopMsgException(ShopMsgCode.REG_MOBILE_ERROR);
		}
		//校验省份编码是否为空
		if(StringUtils.isEmpty(shopMsg.getProvinceId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_PROVICEID_ERROR);
		}
		//校验城市编码是否为空
		if(StringUtils.isEmpty(shopMsg.getCityId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_CITYID_ERROR);
		}
		//校验地区编码是否为空
		if(StringUtils.isEmpty(shopMsg.getDistricId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICID_ERROR);
		}
		//校验省份名称是否为空
		if(StringUtils.isEmpty(shopMsg.getProvinceName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_PROVICENAME_ERROR);
		}
		//校验城市是否为空
		if(StringUtils.isEmpty(shopMsg.getCityName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_CITYNAME_ERROR);
		}
		//校验地区名称是否为空
		if(StringUtils.isEmpty(shopMsg.getDistricName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICNAME_ERROR);
		}
		//校验产品是否为空
		if(StringUtils.isEmpty(shopMsg.getProGroup())){
			throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICNAME_ERROR);
		}
		//校验邮件规则
		boolean emailFlag = RegexUtils.checkEmail(shopMsg.getEmail());
		if(!emailFlag){
			throw new ShopMsgException(ShopMsgCode.REG_EMAIL_ERROR);
		}
		//添加时间
		shopMsg.setCreateDate(new Date());
		//标识状态0 待审核
		shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel());
		//是否已读 0 未读
		shopMsg.setIsRead(ProcessCode.MSG_NO_READ.getLabel());
		//省市区全拼
		shopMsg.setAddress(shopMsg.getProvinceName()+" " +shopMsg.getCityName() + " " + shopMsg.getDistricName() );
		//根据地区id进行工贸选取
		HpsGrid hpsGrid = hpsGridService.findUniqueByProperty("district_code",shopMsg.getDistricId());
		if(null!=hpsGrid){
			//设置工贸id
			shopMsg.setOrgId(hpsGrid.getCenterCode());
			//设置工贸名称
			shopMsg.setOrgName(hpsGrid.getCenterName());
		}
		//进行单号的生成
		shopMsg.setMsgNo(msgUtils.getOrderIdByTime());
		this.save(shopMsg);
        //需求状态履历表
        String content = shopMsg.getCompanyName() + ",已于"+ new Date().toLocaleString() + "发布一条采购需求，需求id=" + shopMsg.getId();
        ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),shopMsg.getConnectName(),OperatorTypeCode.CUSTOMER_SERVICE.getValue(),MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel(),"需求提报",content);
        shopMsgStatusService.save(shopMsgStatus);
		//产品子表录入先删后插
		mapper.execDeleteSql("delete from shop_msg_product where msg_id = '" + shopMsg.getId()+ "'");
		String proGroupCode = shopMsg.getProGroupCode();
		if(StringUtils.isNotEmpty(proGroupCode)){
			String pdcode[] = proGroupCode.split(";");
			for(int i=0; i<pdcode.length; i++){
				ShopMsgProduct s = new ShopMsgProduct();
				//是否智慧
				s.setBeWisdom("0");
				//数量
				s.setEstimatedQuantity("0");
				//预计的采购预算
				s.setPurchaseBudget("0");
				//需求id
				s.setMsgId(shopMsg.getId());
				//设置产品编码（hps对应编码）
				if(StringUtils.isEmpty(ProductCode.getValue(pdcode[i]))){
					throw new ShopMsgException(ShopMsgCode.REG_PRODUCT_ERROR);
				}
				s.setProductGroupCode(ProductCode.getValue(pdcode[i]));
				//设置产品名称（用户语言）
				if(StringUtils.isEmpty(ProductCode.getLabel(pdcode[i]))){
					throw new ShopMsgException(ShopMsgCode.REG_PRODUCT_ERROR);
				}
				s.setProductGroupName(ProductCode.getLabel(pdcode[i]));
				shopMsgProductService.save(s);
			}
		}
		//如果添加需求后发送短信

        //获取行业总监信息
        User u = null;
        try{
            u = this.selectIndustryDirector(shopMsg.getOrgId());
        }catch (Exception e){
            logger.error("发布需求查询总监信息异常，原因:"+ e.getMessage());
        }
        if(null==u){
        	u = new User();
        	u.setName("海尔负责人");
			u.setMobile("0532-88935920");
        }
        //给用户发短信
        List<String> listTpl = new ArrayList<>();
        listTpl.add(u.getName());
        listTpl.add(u.getMobile());
        try {
            sendMsgApi.tplSendMessage(shopMsg.getMobile(),"user_publishing_requirement",listTpl,"0");
        } catch (Exception e) {
            logger.error("addShopMsg 对外发布需求接口异常,原因:" + e.getMessage());
        }
    }

	/**
	 *@Author: hdx
	 *@CreateTime: 17:54 2019/9/2
	 *@param:  * @param shopMsg
	 *@Description: 发布需求自注册

	 */
	@Transactional(readOnly = false)
	public void transMember(ShopMsg shopMsg) throws ShopMsgException {
		ExecuteResult<String> result = null;
		//校验是否已经注册
		try {
			result = UserApi.userIsRegistered(shopMsg.getMobile());
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.SENDMSG_ERROR);
		}
		if(!result.isSuccess()){
			throw new ShopMsgException(ShopMsgCode.SENDMSG_ERROR);
		}
		//校验验证码是否为空
		if(StringUtils.isEmpty(shopMsg.getCode())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MOBILE_ERROR);
		}
		//注册海尔用户中心
		ExecuteResult<HaierUserDTO> res = UserApi.userRegister(shopMsg.getMobile(), shopMsg.getMobile(), "", shopMsg.getMobile(), shopMsg.getCode());
		if(!res.isSuccess()){
			throw new ShopMsgException(UserApiErrorDesc.RegisterError.getDesc(result.getError()));
		}
		//发送短信通知给用户
		try {
			//发送短信
			sendMsgApi.tplSendMessage(shopMsg.getMobile(),"user_transformation",new ArrayList<>(),"0");
		} catch (Exception e) {
			log.error("发布需求未注册用户转化,原因:" + e.getMessage() );
			throw new ShopMsgException(ShopMsgCode.SENDMSG_ERROR);
		}
	}

	/**
	 * @Title: selectIndustryDirector
	 * @Description: TODO 获取工贸下的工程总监
	 * @param branchCode
	 * @return
	 * @throws ShopMsgException
	 * @return User
	 * @author tc
	 * @date 2019年9月25日下午6:01:15
	 */
	public User selectIndustryDirector(String branchCode) throws ShopMsgException {
		logger.info("/order/selectIndustryDirector->branchCode:" + branchCode);
		User user=null;
		if (StringUtils.isEmpty(branchCode)) {
			logger.info("工贸 branchCode为空.");
			throw new ShopMsgException(ShopMsgCode.PARAM_ORGID_ERROR);
		}
		//通过branchCode 查询该工贸下角色
		List<User> userList = userMapper.findByBranchCode(branchCode);
		if (userList != null && userList.size() > 0) {
			for(User u:userList){

                if (user == null){
                    user = userMapper.getByLoginName(new User(null, u.getLoginName()));
                    if (user == null){
                        return null;
                    }
                    user.setRoleList(roleMapper.findList(new Role(user)));
                }
				List<String> roleList= u.getRoleIdList();
				if(roleList==null||roleList.size()<0){//无用户权限
					throw new ShopMsgException(ShopMsgCode.ROLES_NOTEXIST_ERROR);
				}
				for(String roleId:roleList){
					if(Constants.DIRECTOR_ROLE_ID.equals(roleId)){//比对，取出总监权限的用户
						user=u;
					}
				}
			}
		}
		if(user==null){
			String msg="该工贸下无总监用户";
			throw new ShopMsgException(msg);
		}
		return user;
	}

	/**
	 * @Title: selectDealerByOrgid
	 * @Description: TODO  根据工贸code查询经销商
	 * @param orgid
	 * @return
	 * @throws ShopMsgException
	 * @return List<Dealer>
	 * @author tc
	 * @date 2019年9月25日下午6:04:53
	 */
	public List<Dealer> selectDealerByOrgid(String orgid)throws ShopMsgException {
		List<Dealer> dealerList=null;
		if(StringUtils.isEmpty(orgid)){
			throw new ShopMsgException(ShopMsgCode.PARAM_ORGID_ERROR);
		}try {
			dealerList=dealerMapper.selectFranchiserByOrgid(orgid);
		} catch (Exception e) {
			throw new ShopMsgException(e.getMessage());
		}
		return dealerList;
	}


	/**
	 * @Title: sureHaierPerson
	 * @Description: TODO  确认海尔接口人
	 * @param shopMsg
	 * @return
	 * @throws ShopMsgException
	 * @return List<Dealer>
	 * @author tc
	 * @date 2019年9月25日下午6:04:53
	 */
	@Transactional(readOnly = false)
	public void sureHaierPerson(ShopMsg shopMsg)throws ShopMsgException {
		//id为空
		if(StringUtils.isEmpty(shopMsg.getId())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//海尔接口人编码
		if(StringUtils.isEmpty(shopMsg.getManagerNo())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MANAGERNO_ERROR);
		}
		//海尔接口人名称
		if(StringUtils.isEmpty(shopMsg.getManagerName())){
			throw new ShopMsgException(ShopMsgCode.PARAM_MANAGERNAME_ERROR);
		}
		//更新需求表中相关字段
		ShopMsg s = mapper.get(shopMsg.getId());
		if(null == s){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//设置负责人编号
		s.setManagerNo(shopMsg.getManagerNo());
		//设置负责人名称
		s.setManagerName(shopMsg.getManagerName());
		try {
			mapper.update(s);
		} catch (Exception e) {
			throw new ShopMsgException(ShopMsgCode.UPDATE_MSG_EXCEPTION);
		}
	}
    /**
     *@Author: hdx
     *@CreateTime: 13:42 2019/10/8
     *@param:  * @param dealerCode 经销商编码
     *@Description: 经销商查看可抢单信息
     */
    @Transactional(readOnly = false)
    public List<ShopMsg> dealerPreempInfo(String dealerCode)throws ShopMsgException {
        //dealerCode为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
        }
        //查询是否存在此经销商
        Dealer dealer = new Dealer();
        //经销商编码
        dealer.setCompanyCode(dealerCode);
        List<Dealer> listDealer = dealerService.findList(dealer);
        //判断是否存在
        if(listDealer==null || listDealer.size()==0){
            //抛出异常
            throw new ShopMsgException(ShopMsgCode.DEALER_NOTEXIST_ERROR);
        }
        //获取此经销商
        Dealer d = listDealer.get(0);
        //查看是否有可抢单信息
        ShopMsg shopMsg = new ShopMsg();
        //设置需求状态为总监通过状态限制
        shopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_APPROVED.getLabel());
        //设置工贸限制
        shopMsg.setOrgId(d.getGmId());
        //设置承接区域
        shopMsg.setCityName(d.getUndertakeArea());
        //查看未加入产品组筛选条件的待抢单信息
        List<ShopMsg> listShopMsg = mapper.dealerPreempInfo(shopMsg);
        //然后筛选是否符合满足产品组条件
        if(null==listShopMsg || listShopMsg.size()==0){
            //不存在直接返回
            return listShopMsg;
        }
        //获取经销商承接品类
        String underProduct = d.getUnderProduct();
        if(underProduct==null){
			throw new ShopMsgException(ShopMsgCode.DEALER_NOTUNDERTAKE_ERROR);
		}
        //循环将不满足要求的数据剔除
        List<ShopMsg> endListShopMsg = new ArrayList<>();
        listShopMsg.forEach(sm->{
            //循环产品组
            List<String> listStr = Arrays.asList(sm.getProGroup().split(";"));
            boolean endFlag = true;
            for(String str:listStr){
                //是否包含
                if(!underProduct.contains(str)){
                    endFlag = false;
                }
            }
            //如果包含则加入新数组列表
            if(endFlag){
                endListShopMsg.add(sm);
            }
        });
        return endListShopMsg;
        //更新需求表中相关字段
    }

	/**
	 *@Author: hdx
	 *@CreateTime: 16:03 2019/10/8
	 *@param:  * @param dealerCode 经销商编码
	 *@Description: 经销商查看是否还可继续抢单(当处于跟进中的单子大于10时,则不可再抢单)
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> dealerIsGrab(String dealerCode)throws ShopMsgException {
		//声明返回对象
		Map<String, Object> map = new HashMap<String, Object>();
		//默认返回可以抢单
		map.put("flag",true);
		//dealerCode为空
		if(StringUtils.isEmpty(dealerCode)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
		}
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
		//经销商编码
		shopMsgCustcodeOrder.setCustCode(dealerCode);
		//订单来源
		shopMsgCustcodeOrder.setFromSource(ProcessCode.ORDER_SOURCE_GRAB.getLabel());
		//订单状态
		shopMsgCustcodeOrder.setCancelFlag(ProcessCode.CANCEL_FLAG_WAIT.getLabel());
		//查询订单数
		List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(shopMsgCustcodeOrder);
		//如果不存在
		if(null==listShopMsgCustcodeOrder || listShopMsgCustcodeOrder.size()==0){
			//订单数量
			map.put("orderNum",0);
			return map;
		}

		ShopMsgCustcodeOrder so = new ShopMsgCustcodeOrder();
		//经销商编码
		so.setCustCode(dealerCode);
		//订单来源
		so.setFromSource(ProcessCode.ORDER_SOURCE_GRAB.getLabel());
		//订单状态
		so.setIsBind(ProcessCode.IS_BIND.getLabel());
		//查询订单数
		List<ShopMsgCustcodeOrder> listSo = shopMsgCustcodeOrderService.findList(shopMsgCustcodeOrder);
		int countSo = 0;
		if(null==listSo || listSo.size()==0){
			//订单数量
			countSo = 0;
		}else{
			countSo = listSo.size();
		}
		//如果数量大于10
		int orderSize = listShopMsgCustcodeOrder.size();
		orderSize = orderSize - countSo;
		if(orderSize>10){
			map.put("flag",false);
			map.put("orderNum",orderSize);
			return map;
		}
		map.put("orderNum",orderSize);
		return map;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 16:49 2019/10/8
	 *@param:  * @param msgId
	 * @param directorNo
	 * @param currPage
	 * @param pageSize
	 *@Description: 总监派单拉取经销商列表
	 */
	@Transactional(readOnly = false)
	public Page<?> directorPullListDealer(String msgId, String directorNo, String orgid, int pageNo, int pageSize,HttpServletRequest request, HttpServletResponse response) throws ShopMsgException {

		//msgId为空
		if(StringUtils.isEmpty(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//directorNo为空
		if(StringUtils.isEmpty(directorNo)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DIRECTORNO_ERROR);
		}
		//orgid为空
		/*if(StringUtils.isEmpty(orgid)){
			throw new ShopMsgException(ShopMsgCode.PARAM_ORGID_ERROR);
		}*/
		/*//判断是否是总监角色
		boolean isUserRoles = checkUserRoles(directorNo,Constants.DIRECTOR_ROLE_ID);
		if(!isUserRoles){
			//证明用户没有此权限
			throw new ShopMsgException(ShopMsgCode.USERDIRECTROLES_NOTEXIST_ERROR);
		}*/
		//根据需求id查询需求
		ShopMsg shopMsg = this.get(msgId);
		//需求是否存在
        if(null==shopMsg){
            //证明用户没有此权限
            throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
        }
		//查询候选经销商
		Dealer d = new Dealer();
		d.setGmId(orgid);
		d.setUndertakeArea(shopMsg.getCityName());
		d.setUnderProduct(shopMsg.getProGroup());
		//是否可抢单标识
		d.setAllowDispatch(ProcessCode.YES.getLabel());
		//查询分页
		Page<Dealer> pageDealer = new Page<Dealer>(request, response);
		pageDealer.setPageNo(pageNo);
		pageDealer.setPageSize(pageSize);
		Page<Dealer> page = dealerService.findPage(pageDealer, d);
		return page;
	}
	/**
	 *@Author: hdx
	 *@CreateTime: 16:29 2019/10/14
	 *@param:  * @param directorNo
	 * @param msgid
	 * @param dealerCode
	 * @param dispadesc
	 * @param managerNo
	 * @param managerName
	 *@Description:总监派单
	 */
	@Transactional(readOnly = false)
	public void directorDispatch(String directorNo, String msgid, String dealerCode, String dispadesc, String managerNo, String managerName)  throws ShopMsgException {
		//directorNo为空
		if(StringUtils.isEmpty(directorNo)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DIRECTORNO_ERROR);
		}
		//msgid为空
		if(StringUtils.isEmpty(msgid)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//dealerCode为空
		if(StringUtils.isEmpty(dealerCode)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
		}
		//managerNo为空
		if(StringUtils.isEmpty(managerNo)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MANAGERNO_ERROR);
		}
		//managerName为空
		if(StringUtils.isEmpty(managerName)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MANAGERNAME_ERROR);
		}
		//判断是否是总监角色
		boolean isUserRoles = checkUserRoles(directorNo,Constants.DIRECTOR_ROLE_ID);
		if(!isUserRoles){
			//证明用户没有此权限
			throw new ShopMsgException(ShopMsgCode.USERDIRECTROLES_NOTEXIST_ERROR);
		}
		//查询此需求是否存在
		ShopMsg shopMsg = this.get(msgid);
		if(null==shopMsg){
			//证明用户没有此权限
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//进行派单信息填充
		ShopMsgDispatcher s = new ShopMsgDispatcher();
		//派给哪个经销商
		s.setCustcode(dealerCode);
		//需求id
		s.setMsgId(msgid);
		//派单时间
		s.setDispaDate(new Date());
		//来源
		s.setSource(ProcessCode.DISORDER_SOURCE_DISPAT.getValue());
		//操作派单人员
		s.setDispaUser(directorNo);
		//派单标识已派单
		s.setDispaFlag(ProcessCode.IS_DISPATCHER.getLabel());
		//派单id
		s.setId(IdGen.uuid());
		//派单创建时间
		s.setCreateDate(new Date());
		//增加派单类型
		s.setDispaType(ProcessCode.DISPA_TYPE_ZJ.getLabel());
		//增加派单原因
		s.setDispaDesc(dispadesc);
		//派单表增加数据
		shopMsgDispatcherMapper.insert(s);
		Dealer dealer = null;
		//查询
		try{
			dealer = dealerService.findUniqueByProperty("company_code",dealerCode);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.DEALER_EXIS_EXCEPTION + e.getMessage());
		}
		//是否存在
		if(dealer==null){
			throw new ShopMsgException(ShopMsgCode.DEALER_NOTEXIST_ERROR);
		}
		//然后完善具体的经销商订单信息
		//需求订单表生成
		ShopMsgCustcodeOrder shco = new ShopMsgCustcodeOrder();
		//设置id
		shco.setMsgId(msgid);
		//设置经销商编码
		shco.setCustCode(dealerCode);
		//设置工贸编码
		shco.setOrgId(shopMsg.getOrgId());
		//设置工贸名称
		shco.setOrgName(shopMsg.getOrgName());
		//设置需求的用户公司名称
		shco.setCompanyName(shopMsg.getCompanyName());
		//设置来源
		shco.setFromSource(ProcessCode.ORDER_SOURCE_DIRECTOR_DISPATCH.getLabel());
		//订单状态
		shco.setCancelFlag(ProcessCode.ORDER_CANCEL_FLAG_WAIT.getLabel());
		//经销商名称
		shco.setCustName(dealer.getCompanyName());
		//地址
		shco.setAddress(shopMsg.getAddress());
		//创建时间
		shco.setCreateDate(new Date());
		//产品组
		shco.setProGroup(shopMsg.getProGroup());
		//产品组编码
		shco.setProGroupCode(shopMsg.getProGroupCode());

        //订单编号
        if(StringUtils.isNotBlank(shopMsg.getMsgNo())){
            shco.setOrderNo(shopMsg.getMsgNo());
        }else{
            shco.setOrderNo(shopMsg.getId());
        }
		//未承接
		//shco.setUnderTake(ProcessCode.NO_UNDERTAKE.getLabel());
		//添加
		try{
			shopMsgCustcodeOrderService.save(shco);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.ORDER_INSERT_EXCEPTION + e.getMessage());
		}
		//更新需求表
		//录入责任人名称
		shopMsg.setManagerName(managerName);
		//录入责任人编号
		shopMsg.setManagerNo(managerNo);
		//需求状态
		shopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_SENT_ORDER.getLabel());
		//是否已进入派单
		shopMsg.setIsDispatch(ProcessCode.IS_DISPATCHER.getLabel());
		//经销商编码
		shopMsg.setCustCode(dealerCode);
		//更新需求表
		this.updateMsg(shopMsg);
		//给经销商发短信
		List<String> listTpl = new ArrayList<>();
		//公司名称
		listTpl.add(shopMsg.getCompanyName());
		//联系人
		listTpl.add(shopMsg.getConnectName());
		//发送短信模板(通知经销商总监已派单)
		try {
			sendMsgApi.tplSendMessage(dealer.getMobile(),"dealer_dispatch",listTpl,"0");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			/*params.put("pushUser", sb.toString().substring(0, sb.length() - 1));
			params.put("content", "抢单提醒： 您于" + sdf.format(new Date()) + "收到抢单信息，请及时抢单");
			params.put("jumpType", "qd2");
			params.put("mid", msgInfo.getId().toString());
			MsgUtils.sendApp();*/
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		//给总监发
		List<String> listTplDirector = new ArrayList<>();
		//公司名称
		listTpl.add(shopMsg.getCompanyName());
		//联系人
		listTpl.add(shopMsg.getConnectName());
		//客户
		listTpl.add(dealerCode + dealer.getCompanyName());
		//发送短信模板(通知经销商总监已派单)
		try {
			sendMsgApi.tplSendMessage(dealer.getMobile(),"dealer_dispatch_GM",listTpl,"0");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			/*params.put("pushUser", sb.toString().substring(0, sb.length() - 1));
			params.put("content", "抢单提醒： 您于" + sdf.format(new Date()) + "收到抢单信息，请及时抢单");
			params.put("jumpType", "qd2");
			params.put("mid", msgInfo.getId().toString());
			MsgUtils.sendApp();*/
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		//需求状态履历表(总监派单)
		String statusNode = MsgFlagCode.DIRECTOR_SENT_ORDER.getLabel();
		String statusName = MsgFlagCode.DIRECTOR_SENT_ORDER.getValue();
		String content = directorNo + "总监,已于"+ new Date().toLocaleString() + "派单一条采购需求，需求id=" + shopMsg.getId() + ",派给" +dealer.getCompanyCode()+ "--" + dealer.getCompanyName() +"经销商";
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),directorNo,OperatorTypeCode.CHIEF_INSPECTOR.getValue(),statusNode,"已分配工程商",content);
		shopMsgStatusService.save(shopMsgStatus);
	}




	/**
	 *@Author: hdx
	 *@CreateTime: 16:49 2019/10/8
	 *@param:
	 * @param msgId
	 * @param pageNo
	 * @param pageSize
	 *@Description: 管理员派单拉取经销商列表
	 */
	@Transactional(readOnly = false)
	public Page<?> adminPullListDealer(String msgId, int pageNo, int pageSize,HttpServletRequest request, HttpServletResponse response) throws ShopMsgException {
		//msgId为空
		if(StringUtils.isEmpty(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//根据需求id查询需求
		ShopMsg shopMsg = this.get(msgId);
		//查询候选经销商
		Dealer d = new Dealer();
		//工贸id
		d.setGmId(shopMsg.getOrgId());
		//承接区域
		d.setUndertakeArea(shopMsg.getCityName());
		//成绩品类
		d.setUnderProduct(shopMsg.getProGroup());
		//查询分页
		Page<Dealer> pageDealer = new Page<Dealer>(request, response);
		pageDealer.setPageNo(pageNo);
		pageDealer.setPageSize(pageSize);
		Page<Dealer> page = dealerService.findPage(pageDealer, d);
		return page;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 16:49 2019/10/8
	 *@param:
	 * @param dealerCode
	 *@Description: 经销商-根据销商编码返回每个状态的订单数量
	 */
	@Transactional(readOnly = false)
	public List<Integer>  dealerOrderAllStatus(String dealerCode) throws ShopMsgException {
		//dealerCode为空
		if(StringUtils.isEmpty(dealerCode)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
		}
		//查询是否存在此经销商
		try{
			Dealer dealer = checkDealer(dealerCode);
		}catch (ShopMsgException e){
			throw new ShopMsgException(e.getMessage());
		}
		List<Integer> listStatus = null;
		try{
			listStatus = shopMsgCustcodeOrderMapper.dealerOrderAllStatus(dealerCode);
		}catch (Exception e){
			e.printStackTrace();
		}
		return listStatus;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:51 2019/10/10
	 *@param:  * @param dealerCode
	 *@Description:校验经销商是否存在

	 */
	public Dealer checkDealer(String dealerCode) throws ShopMsgException{
		//查询
		Dealer dealer = null;
		try{
			dealer = dealerService.findUniqueByProperty("company_code",dealerCode);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.DEALER_EXIS_EXCEPTION + e.getMessage());
		}
		//是否存在
		if(dealer==null){
			throw new ShopMsgException(ShopMsgCode.DEALER_NOTEXIST_ERROR);
		}
		return dealer;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 14:59 2019/10/10
	 *@param:  * @param dealerCode
	 *@Description: 经销商- 根据经销商编码获得未处理(未选择承接方式)订单数量

	 */
	public int dealerOrderUnderTake(String dealerCode) throws ShopMsgException{
		int count = 0;
		//查询是否存在此经销商
		try{
			Dealer dealer = checkDealer(dealerCode);
		}catch (ShopMsgException e){
			throw new ShopMsgException(e.getMessage());
		}
		try{
			count = shopMsgCustcodeOrderMapper.dealerOrderUnderTake(dealerCode);
		}catch (Exception e){
			throw new ShopMsgException(e.getMessage());
		}
		return count;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:06 2019/10/10
	 *@param:  * @param dealerCode
	 *@param gcFlag
	 *@param lsFlag
	 *@param cancelFlag
	 *@Description: 经销商-查看我的项目

	 */
	@Transactional(readOnly = false)
	public Page<ShopMsgCustcodeOrder> dealerGetMineOrder(String dealerCode, String gcFlag, String lsFlag, String cancelFlag,int pageNo,int pageSize,HttpServletRequest request, HttpServletResponse response) throws ShopMsgException{

		//查询是否存在此经销商
		try{
			Dealer dealer = checkDealer(dealerCode);
		}catch (ShopMsgException e){
			throw new ShopMsgException(e.getMessage());
		}
		//声明查询实体
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
		//设置对应经销商编码
		shopMsgCustcodeOrder.setCustCode(dealerCode);
		//整合实体类
		shopMsgCustcodeOrder = getCustcodeOrder(shopMsgCustcodeOrder,gcFlag,lsFlag,cancelFlag);
		//查询订单相关信息
		//查询分页
		Page<ShopMsgCustcodeOrder> pageShopMsgCustcodeOrder = new Page<ShopMsgCustcodeOrder>(request,response);
		pageShopMsgCustcodeOrder.setPageNo(pageNo);
		pageShopMsgCustcodeOrder.setPageSize(pageSize);
        //加入排序规则
        pageShopMsgCustcodeOrder.setOrderBy(" underTake, cancelFlag, createDate DESC");
		Page<ShopMsgCustcodeOrder> page = shopMsgCustcodeOrderService.findPage(pageShopMsgCustcodeOrder, shopMsgCustcodeOrder);
		return page;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:06 2019/10/10
	 *@param:  * @param dealerCode
	 *@param gcFlag
	 *@param lsFlag
	 *@param cancelFlag
	 *@Description: 总监/管理员-查看我的项目

	 */
	@Transactional(readOnly = false)
	public Page<ShopMsgCustcodeOrder> directorOrAdminGetOrder(String custCode, String directorNo,String orgId, String gcFlag, String lsFlag, String cancelFlag,int pageNo,int pageSize) throws ShopMsgException{
		//声明查询实体
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
		//设置工贸编码
		shopMsgCustcodeOrder.setOrgId(orgId);
		//true证明是总监查看，反之为管理员查看
		if(StringUtils.isNotBlank(directorNo)){
			//判断是否是总监角色
			/*boolean isUserRoles = checkUserRoles(directorNo,Constants.DIRECTOR_ROLE_ID);
			if(!isUserRoles){
				//证明用户没有此权限
				throw new ShopMsgException(ShopMsgCode.USERDIRECTROLES_NOTEXIST_ERROR);
			}*/
		}
		//整合实体类
		shopMsgCustcodeOrder = getCustcodeOrder(shopMsgCustcodeOrder,gcFlag,lsFlag,cancelFlag);
        shopMsgCustcodeOrder.setCustCode(custCode);
		//查询分页
		Page<ShopMsgCustcodeOrder> pageShopMsgCustcodeOrder = new Page<ShopMsgCustcodeOrder>();
		pageShopMsgCustcodeOrder.setPageNo(pageNo);
		pageShopMsgCustcodeOrder.setPageSize(pageSize);
		//加入排序规则
        pageShopMsgCustcodeOrder.setOrderBy(" underTake, cancelFlag, createDate DESC");
		Page<ShopMsgCustcodeOrder> page = null;
        try{
        	page = shopMsgCustcodeOrderService.findPage(pageShopMsgCustcodeOrder, shopMsgCustcodeOrder);
		}catch (Exception e){
        	e.printStackTrace();
		}

		return page;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:35 2019/10/10
	 *@param:  * @param shopMsgCustcodeOrder
	 * @param gcFlag
	 * @param lsFlag
	 * @param cancelFlag
	 *@Description: 整合shopMsgCustcodeOrder实体，用于查询我的订单

	 */
	public ShopMsgCustcodeOrder getCustcodeOrder(ShopMsgCustcodeOrder shopMsgCustcodeOrder,String gcFlag, String lsFlag, String cancelFlag){
		//如果工程标记不为空
		if(StringUtils.isNotEmpty(gcFlag)){
			//工程单关闭
			if(ProcessCode.ORDER_PROCESS_CLOSE.getLabel().equals(gcFlag)){
				//设置承接方式为工程单0
				shopMsgCustcodeOrder.setUnderTake(ProcessCode.GC_UNDERTAKE.getLabel());
				//工程关闭CancelFlag-3
				shopMsgCustcodeOrder.setCancelFlag(ProcessCode.ANCEL_FLAG_WORKS_CLOSURE.getLabel());
			}else if(ProcessCode.ORDER_PROCESS_ON_GOINT.getLabel().equals(gcFlag)){
				//进行中CancelFlag-0
                //设置承接方式为工程单0
                shopMsgCustcodeOrder.setUnderTake(ProcessCode.GC_UNDERTAKE.getLabel());
				shopMsgCustcodeOrder.setCancelFlag(ProcessCode.ORDER_CANCEL_FLAG_WAIT.getLabel());
				shopMsgCustcodeOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
			}else{
                //设置承接方式为工程单0
                shopMsgCustcodeOrder.setUnderTake(ProcessCode.GC_UNDERTAKE.getLabel());
				//中标
				shopMsgCustcodeOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
			}
		}
		//如果零售标记不为空
		if(StringUtils.isNotEmpty(lsFlag)){
			//设置承接方式为零售单1
			shopMsgCustcodeOrder.setUnderTake(ProcessCode.LS_UNDERTAKE.getLabel());
			//零售单关闭
			if(ProcessCode.ORDER_PROCESS_CLOSE.getLabel().equals(lsFlag)){
				//零售关闭CancelFlag-2
				shopMsgCustcodeOrder.setCancelFlag(ProcessCode.ANCEL_FLAG_RETAIL_CLOSURE.getLabel());
			}else if(ProcessCode.ORDER_PROCESS_ON_GOINT.getLabel().equals(lsFlag)){
				//进行中CancelFlag-0
				shopMsgCustcodeOrder.setCancelFlag(ProcessCode.CANCEL_FLAG_WAIT.getLabel());
				shopMsgCustcodeOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
			}else{
				//中标
				shopMsgCustcodeOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
			}
		}
		//如果废弃
		if(StringUtils.isNotEmpty(cancelFlag)){
			//承接前废弃
			shopMsgCustcodeOrder.setCancelFlag(ProcessCode.CANCEL_FLAG_FOLLOW.getLabel());
		}
		return shopMsgCustcodeOrder;
	}

	/**
	 *@Author: hdx
	 *@CreateTime: 17:06 2019/10/10
	 *@param:  * @param dealerCode
	 *@param msgId
	 *@param cancelReason
	 *@param memo
	 *@param dealerCode
	 *@Description: 经销商-在选择承接方式之前弃单

	 */
	@Transactional(readOnly = false)
	public void dealerCancelOrderBefore(String msgId,String cancelReason,String memo,String dealerCode) throws ShopMsgException{
		//需求id为空
		if(StringUtils.isBlank(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//dealerCode为空
		if(StringUtils.isBlank(dealerCode)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
		}
		//查询是否存在此经销商
		Dealer dealer = null;
		try{
			dealer = checkDealer(dealerCode);
		}catch (ShopMsgException e){
			throw new ShopMsgException(e.getMessage());
		}
		//弃单原因为空
		if(StringUtils.isBlank(cancelReason)){
			throw new ShopMsgException(ShopMsgCode.PARAM_CANCELREASON_ERROR);
		}
		//查询是否存在此需求
		ShopMsg sm = this.get(msgId);
		if(null==sm){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//校验需求状态，必须处于未选择承接方式状态才可进行
		if(!sm.getMsgFlag().equals(MsgFlagCode.PLATFORM_DIRECT_DELIVERY.getLabel()) && !sm.getMsgFlag().equals(MsgFlagCode.DIRECTOR_SENT_ORDER.getLabel()) && !sm.getMsgFlag().equals(MsgFlagCode.GRAB_SINGLE_SUCCESS.getLabel())){
			throw new ShopMsgException(ShopMsgCode.MSGFLAG_NOINPROCESS_ERROR);
		}
		//更新下节点状态
		sm.setMsgFlag(MsgFlagCode.CHOOSE_UNDERTAKE_BEFORE_CLOSING.getLabel());
		//更新是否已经进入派单表状态
		sm.setIsDispatch(ProcessCode.YES.getLabel());
		//更新节点状态
		this.updateMsg(sm);
		//查询对应订单
		//声明查询实体
		ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
		//需求Id
		shopMsgCustcodeOrder.setMsgId(msgId);
		//订单状态
		shopMsgCustcodeOrder.setCancelFlag(ProcessCode.ORDER_CANCEL_FLAG_WAIT.getLabel());
		List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(shopMsgCustcodeOrder);
		if(null==listShopMsgCustcodeOrder||listShopMsgCustcodeOrder.size()==0){
			throw new ShopMsgException(ShopMsgCode.ORDER_NODE_NOTEXIST_ERROR);
		}
		ShopMsgCustcodeOrder oneShopMsgCustcodeOrder = listShopMsgCustcodeOrder.get(0);
		//承接前废弃
		oneShopMsgCustcodeOrder.setCancelFlag(ProcessCode.CANCEL_FLAG_FOLLOW.getLabel());
		//废弃原因
		oneShopMsgCustcodeOrder.setCancelReason(cancelReason);
		//废弃人
		oneShopMsgCustcodeOrder.setCanceler(dealerCode);
		//废弃时间
		oneShopMsgCustcodeOrder.setCancelDate(new Date());
		//备注
		oneShopMsgCustcodeOrder.setMemo(memo);
		//
		//更新订单表
		try{
			shopMsgCustcodeOrderMapper.update(oneShopMsgCustcodeOrder);
		}catch (Exception e){
			throw new ShopMsgException("经销商-在选择承接方式之前弃单中，更新订单表失败，原因：" + e.getMessage());
		}
		//需求状态履历表(为承接方式前废弃)
		String statusNode = MsgFlagCode.CHOOSE_UNDERTAKE_BEFORE_CLOSING.getLabel();
		String statusName = MsgFlagCode.CHOOSE_UNDERTAKE_BEFORE_CLOSING.getValue();
		String content = dealer.getCompanyName() + "-经销商,已于"+ new Date().toLocaleString() + ",在未选择承接方式前关闭,需求id=" + sm.getId() + ",公司名=" + sm.getCompanyName() + ",结果：关闭成功" ;
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(sm.getId(),dealer.getCompanyCode(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
		shopMsgStatusService.save(shopMsgStatus);

		//重新录入派单表
		ShopMsgDispatcher smd = new ShopMsgDispatcher();
		//需求id
		smd.setMsgId(msgId);
		//来源
		smd.setSource(ProcessCode.DISORDER_SOURCE_CANCEL.getValue());
		//派单id
		smd.setId(IdGen.uuid());
		//派单创建时间
		smd.setCreateDate(new Date());
		//派单标识未派单
		smd.setDispaFlag(ProcessCode.NO.getLabel());
		//是否关闭表示
		smd.setIsClosed(ProcessCode.NO.getLabel());
		//派单表增加数据
		shopMsgDispatcherMapper.insert(smd);
		//需求状态履历表(重新归为平台待派单)
		statusNode = MsgFlagCode.PAYMENT_MANAGEMENT_PAYMENT.getLabel();
		statusName = MsgFlagCode.PAYMENT_MANAGEMENT_PAYMENT.getValue();
		content = dealer.getCompanyName()+ "因在选择承接方式前废弃此订单," + sm.getCompanyName() + "需求已于"+ new Date().toLocaleString() + ",重新置入派单管理" ;
		shopMsgStatus = ShopMsgService.returnShopMsgStatus(sm.getId(),dealer.getCompanyCode(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
		shopMsgStatusService.save(shopMsgStatus);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:06 2019/10/10
	 *@param:  * @param dealerCode
	 *@param msgId
	 *@param dealerCode
	 *@Description: 经销商-抢单

	 */
	@Transactional(readOnly = false)
	public void dealerRobtrade(String msgId,String dealerCode) throws ShopMsgException{
		//需求id为空
		if(StringUtils.isBlank(msgId)){
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//dealerCode为空
		if(StringUtils.isBlank(dealerCode)){
			throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
		}
		//查询是否存在此需求
		ShopMsg sm = this.get(msgId);
		if(null==sm){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//是否此单还存在
		if(!MsgFlagCode.DIRECTOR_APPROVED.getLabel().equals(sm.getMsgFlag())){
			throw new ShopMsgException(ShopMsgCode.CHANGE_MSGFLAG_ERROR);
		}
		//查询是否存在此经销商
		Dealer dealer = null;
		try{
			dealer = checkDealer(dealerCode);
		}catch (ShopMsgException e){
			throw new ShopMsgException(e.getMessage());
		}
		//更新需求表数据
		//抢单时间
		sm.setGrabDate(new Date());
		//需求状态改为30 抢单成功
		sm.setMsgFlag(MsgFlagCode.GRAB_SINGLE_SUCCESS.getLabel());
		//经销商编码
		sm.setCustCode(dealerCode);
		//抢单时间
        sm.setGrabDate(new Date());
		//更新
		this.updateMsg(sm);
		//进行订单表新增
		//需求订单表生成
		ShopMsgCustcodeOrder shco = new ShopMsgCustcodeOrder();
		//设置id
		shco.setMsgId(sm.getId());
		//设置经销商编码
		shco.setCustCode(dealerCode);
		//设置工贸编码
		shco.setOrgId(sm.getOrgId());
		//设置工贸名称
		shco.setOrgName(sm.getOrgName());
		//设置需求的用户公司名称
		shco.setCompanyName(sm.getCompanyName());
		//设置来源
		shco.setFromSource(ProcessCode.ORDER_SOURCE_GRAB.getLabel());
		//订单状态
		shco.setCancelFlag(ProcessCode.ORDER_CANCEL_FLAG_WAIT.getLabel());
		//经销商名称
		shco.setCustName(dealer.getCompanyName());
		//地址
		shco.setAddress(sm.getAddress());
		//创建时间
		shco.setCreateDate(new Date());
		//产品组
		shco.setProGroup(sm.getProGroup());
		//产品组编码
		shco.setProGroupCode(sm.getProGroupCode());
		//未承接
		//shco.setUnderTake(ProcessCode.NO_UNDERTAKE.getLabel());
		//是否中标默认0未中标
		shco.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
		//订单编号
        if(StringUtils.isNotBlank(sm.getMsgNo())){
            shco.setOrderNo(sm.getMsgNo());
        }else{
            shco.setOrderNo(sm.getId());
        }
		//添加
		try{
			shopMsgCustcodeOrderService.save(shco);
		}catch (Exception e){
			throw new ShopMsgException(ShopMsgCode.ORDER_INSERT_EXCEPTION +"," + e.getMessage());
		}
		//成功后给总监发送短息及推送信息
		List<String> listTplDirector = new ArrayList<>();
		//根据工贸查询总监相关信息
		List<User> listUser = null;
		try{
			listUser = UserUtils.getAboutUser(sm.getOrgId());
		}catch (Exception e){
			throw new ShopMsgException("根据工贸查询总监相关信息异常，原因:" +"," + e.getMessage());
		}
		//找到总监(只可有一个总监)
		if(null==listUser || listUser.size()==0){
			log.error(ShopMsgCode.DIRECT_NOTEXIST_ERROR.getValue());
			return;
		}
		User endUser = null;
		//循环用户
		for(User u:listUser){
			User user = UserUtils.getByLoginName(u.getLoginName());
			List<Role> roleList = user.getRoleList();
			for(Role role:roleList){
				if(Constants.DIRECTOR_ROLE_ID.equals(role.getId())){
					endUser = user;
				}
			}
		}
		User u = null;
		if(endUser==null){
			 u = listUser.get(0);
		}else{
			 u = endUser;
		}
		//判断是否是总监权限
		List<Role> roleList = u.getRoleList();
		for(Role role:roleList){
			if(Constants.DIRECTOR_ROLE_ID.equals(role.getId())){
				//证明此是总监并发送给其短信
				//项目名称(也就是公司名称)
				listTplDirector.add(sm.getCompanyName());
				//经销商名称
				listTplDirector.add(dealer.getCompanyName());
				//发送短信模板
				try {
					sendMsgApi.tplSendMessage(u.getPhone(),"dealer_grab_sheet_success",listTplDirector,"0");
				} catch (Exception e) {
					logger.error("抢单成功给总监发送短信异常，原因：" + e.getMessage());
				}
				//大客户app推送接口
				// 声明推送app接口参数
				Map<String, String> params = new HashMap<String, String>();
				StringBuilder sb = new StringBuilder();
				StringBuilder sendAppMsg = new StringBuilder();
				//推送账号
				params.put("pushUser", u.getLoginName());
				//推送内容
				params.put("content",
						"抢单成功通知：" + sm.getCompanyName() + "项目已被" + dealer.getCompanyName() + "成功抢单，请及时跟踪");
				//qd3给总监推送抢单通知
				params.put("jumpType", "qd3");
				//经销商订单id
				params.put("id", shco.getId());
				//需求id
				params.put("mid", sm.getId());
				//经销商编码
				params.put("customernumber", dealer.getCompanyCode());
				params.put("undertake", shco.getUnderTake());
				params.put("canelflag", shco.getCancelFlag());
				params.put("depart", shco.getOrgId());
				params.put("orderid", shco.getId());
				sendAppMsg.append(params);
				// 是否推送成功
				try{
				MsgUtils.sendApp(params);
				} catch (Exception e) {
					logger.error("发送app推送消息(通知总监经销商已抢单)异常" + e.getMessage());
				}
			}
		}
		//需求状态履历表(总监派单)
		String statusNode = MsgFlagCode.GRAB_SINGLE_SUCCESS.getLabel();
		String statusName = "已分配工程商";
		String content = dealer.getCompanyName() + "-经销商,已于"+ new Date().toLocaleString() + "抢到一条采购需求，需求id=" + sm.getId() + ",公司名=" + sm.getCompanyName() + ",结果：抢单成功" ;
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(sm.getId(),dealer.getCompanyCode(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
		shopMsgStatusService.save(shopMsgStatus);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:06 2019/10/10
	 *@param:  * @param dealerCode
	 *@param msgId 需求id
	 *@param orderId 订单id
	 *@param dealerCode 经销商编码
	 *@param undertake 承接方式
	 *@param managerNo 海尔接口人编码
	 *@param managerName 海尔接口人名称
	 *@Description: 经销商-选择承接方式
	 */
	@Transactional(readOnly = false)
	public void dealerUnderTake(String msgId,String orderId,String dealerCode,String undertake,String managerNo,String managerName) throws ShopMsgException {
		//需求id为空
		if (StringUtils.isBlank(msgId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//orderId订单id为空
		if (StringUtils.isBlank(orderId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR);
		}
		//dealerCode经销商编码为空
		if (StringUtils.isBlank(dealerCode)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_DEALERNUMBER_ERROR);
		}
		//undertake承接方式为空
		if (StringUtils.isBlank(undertake)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_UNDERTAKE_ISNULL_ERROR);
		}
		//查询是否存在此需求
		ShopMsg sm = this.get(msgId);
		if (null == sm) {
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//校验此需求状态是否满足承接方式
		if(!(MsgFlagCode.GRAB_SINGLE_SUCCESS.getLabel().equals(sm.getMsgFlag())||MsgFlagCode.PLATFORM_DIRECT_DELIVERY.getLabel().equals(sm.getMsgFlag())||MsgFlagCode.DIRECTOR_SENT_ORDER.getLabel().equals(sm.getMsgFlag())||MsgFlagCode.PAYMENT_MANAGEMENT_PAYMENT.getLabel().equals(sm.getMsgFlag()))){
			throw new ShopMsgException(ShopMsgCode.MSGFLAG_NOINPROCESS_ERROR);
		}

		//设置海尔接口人编码
		if(StringUtils.isNotBlank(managerNo)){
			sm.setManagerNo(managerNo);
		}
		if(StringUtils.isNotBlank(managerName)){
			//设置海尔接口人
			sm.setManagerName(managerName);
		}
		//orderId此经销商订单信息是否存在
		ShopMsgCustcodeOrder smco = shopMsgCustcodeOrderService.get(orderId);
		if (null == smco) {
			throw new ShopMsgException(ShopMsgCode.ORDER_NOTEXIST_ERROR);
		}
		//查看此需求下产品子表信息
		List<ShopMsgProduct> listShopMsgProduct = shopMsgProductService.findList(new ShopMsgProduct().setMsgId(msgId));
		if(null==listShopMsgProduct || listShopMsgProduct.size()==0){
			throw new ShopMsgException(ShopMsgCode.SHOPPRODUCT_EXIS_EXCEPTION);
		}
		//查询是否存在此经销商
		Dealer dealer = null;
		try{
			dealer = checkDealer(dealerCode);
		}catch (ShopMsgException e){
			throw new ShopMsgException(e.getMessage());
		}
		//履历记录内容
		String content = "";
		//需求状态履历表
		String statusNode = "";
		String statusName = "";
		//判断是工程单还是零售
		if(undertake.equals(ProcessCode.GC_UNDERTAKE.getLabel())){
			//工程单
			Map<String,Object> map = new HashMap<>();
			try{
				map = HpsApi.processSaveProject(smco, sm,listShopMsgProduct);
			}catch (Exception e){
				throw new ShopMsgException(e.getMessage());
			}
			String flag = (String) map.get("flag");
			// 成功进入漏斗
			if (!"error".equals(flag)) {
				// 根据漏斗返回的接口编码，更新需求信息
				//设置需求状态为40,经销商选择承接方式-工程单
				sm.setMsgFlag(MsgFlagCode.CHOOSES_UNDERTAKE_PROJECT.getLabel());
				//经销商编码
				sm.setCustCode(dealerCode);
				//hps返回项目编码
				sm.setProjectCode((String) map.get("projectCode"));
				//海尔接口人编码
				sm.setManagerNo(managerNo);
				//海尔接口人名称
				sm.setManagerName(managerName);
				//更新nodetag
                sm.setNodetag(ProcessCode.MOBILE_PROCESS_ARRIVAL.getLabel());
				//更新需求表
				this.updateMsg(sm);
				//工程单内容
				content = dealer.getCompanyName() + "-经销商,已于"+ new Date().toLocaleString() + "选择承接方式-工程单，需求id=" + sm.getId() + ",公司名=" + sm.getCompanyName() + "结果：传输至hps系统，返回项目编码=" + (String) map.get("projectCode") ;
				statusNode = MsgFlagCode.CHOOSES_UNDERTAKE_PROJECT.getLabel();
				statusName = "方案提报";
			}else {
				throw new ShopMsgException("选择工程单异常,原因: " + map.get("errorMsg"));
			}
		}else if(undertake.equals(ProcessCode.LS_UNDERTAKE.getLabel())){
			//零售
			ShopMsgZykc smz = new ShopMsgZykc();
			//设置id
			smz.setMsgId(sm.getId());
			//创建时间
			smz.setCreateDate(new Date());
			//提需求公司名
			smz.setCompanyName(sm.getCompanyName());
			//经销商编码
			smz.setCustcode(dealerCode);
			//设置关闭状态(未关闭)
			smz.setIsClosed(ProcessCode.NO.getLabel());
			//orderId 订单id
			smz.setOrderId(orderId);
			//新增
			shopMsgZykcService.save(smz);
			//零售内容
			content = dealer.getCompanyName() + "-经销商,已于"+ new Date().toLocaleString() + "选择承接方式-零售单，需求id=" + sm.getId() + ",公司名=" + sm.getCompanyName() + "结果：进入零售管理" ;
			statusNode = MsgFlagCode.CHOOSE_UNDERTAKE_RETAIL.getLabel();
			statusName = "方案提报";
			//并更新需求表状态信息
			sm.setMsgFlag(MsgFlagCode.CHOOSE_UNDERTAKE_RETAIL.getLabel());
			this.updateMsg(sm);
		}else{
			//抛出承接方式数据异常
			throw new ShopMsgException(ShopMsgCode.REG_UNDERTAKE_ERROR);
		}
		//设置承接方式
		smco.setUnderTake(undertake);
		//设置可以出库
        smco.setIsDeliver(ProcessCode.NO.getLabel());
        //安装
        smco.setIsInstall(ProcessCode.NO.getLabel());
        //签收
        smco.setIsSign(ProcessCode.NO.getLabel());
		//更新订单需求表
		shopMsgCustcodeOrderMapper.update(smco);
		//需求状态履历表(总监派单)
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(sm.getId(),dealer.getCompanyCode(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
		try{
			shopMsgStatusService.save(shopMsgStatus);
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:06 2019/10/10
	 *@param msgId 需求id
	 *@param cancelFlag 废弃类型
	 *@param cancelreson 废弃原因
	 *@param canceldesc 废弃描述
	 *@param orderId 经销商订单id
	 *@Description: 经销商-作废零售订单

	 */
	@Transactional(readOnly = false)
	public void dealerCancelOrder(String msgId,String cancelFlag,String cancelreson,String canceldesc,String orderId) throws ShopMsgException{
		//需求id为空
		if (StringUtils.isBlank(msgId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//orderId订单id为空
		if (StringUtils.isBlank(orderId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR);
		}
		//查询是否存在此需求
		ShopMsg sm = this.get(msgId);
		if (null == sm) {
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//orderId此经销商订单信息是否存在
		ShopMsgCustcodeOrder smco = shopMsgCustcodeOrderService.get(orderId);
		if (null == smco) {
			throw new ShopMsgException(ShopMsgCode.ORDER_NOTEXIST_ERROR);
		}
		//cancelFlag废弃类型
		if (StringUtils.isBlank(cancelFlag)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_CANCELFLAG_ERROR);
		}
		//cancelFlag废弃原因
		if (StringUtils.isBlank(cancelreson)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_CANCELRESON_ERROR);
		}
		//cancelFlag废弃描述
		if (StringUtils.isBlank(canceldesc)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_CANCELDESC_ERROR);
		}
		//查询此零售单
		ShopMsgZykc smz = new ShopMsgZykc();
		//需求id
		smz.setMsgId(msgId);
		//订单id
		smz.setOrderId(orderId);
		//查询自由库存
		List<ShopMsgZykc> listShopMsgZykc = shopMsgZykcService.findList(smz);
		if(null==listShopMsgZykc || listShopMsgZykc.size()==0){
			throw new ShopMsgException(ShopMsgCode.ORDER_ZYKC_ERROR);
		}
		//如果存在
		ShopMsgZykc szykc = listShopMsgZykc.get(0);
		//设置废弃描述
		szykc.setCancelDesc(canceldesc);
		//设置废弃理由
		szykc.setCancelReson(cancelreson);
		//设置废弃类型
		szykc.setCancelType(cancelFlag);
		//设置废弃时间
		szykc.setCancleDate(new Date());
		//设置已关闭
		szykc.setIsClosed(ProcessCode.NO.getLabel());
		//更新
		shopMsgZykcMapper.update(szykc);
		//更新订单表
		//设置废弃时间
		smco.setCancelDate(new Date());
		//设置废弃人
		smco.setCanceler(szykc.getCustcode());
		//设置废弃原因
		smco.setCancelReason(cancelreson);
		//设置订单的状态
		smco.setCancelFlag(ProcessCode.ANCEL_FLAG_RETAIL_CLOSURE.getLabel());
		//更新订单表
		shopMsgCustcodeOrderMapper.update(smco);
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 9:38 2019/10/14
	 *@param:
	 *@param msgId 需求id
	 *@param urls  图片url
	 *@param dealerCode  经销商编码
	 *@Description:

	 */
	@Transactional(readOnly = false)
	public void dealerZykcUpload(String msgId,String urls,String dealerCode,String orderId,String tradeCount) throws ShopMsgException{
		//需求id为空
		if (StringUtils.isBlank(msgId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//url为空
		if (StringUtils.isBlank(urls)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_ZYKCURL_ERROR);
		}
		//orderId为空
		if (StringUtils.isBlank(orderId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR);
		}
		//tradeCount为空
		if (StringUtils.isBlank(tradeCount)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_TRADECOUNT_ERROR);
		}
		//查询是否存在此经销商
		Dealer dealer = null;
		try{
			dealer = checkDealer(dealerCode);
		}catch (ShopMsgException e){
			throw new ShopMsgException(e.getMessage());
		}
		//查询零售单信息
		ShopMsgZykc zykc = new ShopMsgZykc();
		//需求id
		zykc.setMsgId(msgId);
		//经销商名称
		zykc.setCustcode(dealerCode);
		//经销商订单id
		zykc.setOrderId(orderId);
		List<ShopMsgZykc> listShopMsgZykc = shopMsgZykcMapper.findList(zykc);
		//零售单是否存在
		if(null==listShopMsgZykc || listShopMsgZykc.size()==0){
			throw new ShopMsgException(ShopMsgCode.ORDER_ZYKC_ERROR);
		}
		//获取零售单
		ShopMsgZykc shopMsgZykc = listShopMsgZykc.get(0);
		//设置见证性材料
		shopMsgZykc.setImageUrl(urls);
		//设置交易金额
		shopMsgZykc.setTradeCount(tradeCount);
		//更新零售单信息
		shopMsgZykcMapper.update(shopMsgZykc);
		//更新订单状态
        ShopMsgCustcodeOrder shopMsgCustcodeOrder= shopMsgCustcodeOrderMapper.get(orderId);
        if(shopMsgCustcodeOrder!=null){
            //是否中标
            shopMsgCustcodeOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
            //中标时间
            shopMsgCustcodeOrder.setBindTime(new DateTime());
            //中标金额
            shopMsgCustcodeOrder.setTotalCount(shopMsgZykc.getTradeCount());
            shopMsgCustcodeOrderMapper.update(shopMsgCustcodeOrder);
        }
		//需求状态履历表(上传见证性材料)
		String statusNode = MsgFlagCode.UPLOAD_WITNESS_MATERIAL.getLabel();
		String statusName = MsgFlagCode.UPLOAD_WITNESS_MATERIAL.getValue();
		String content = dealer.getCompanyName() + "-经销商,已于"+ new Date().toLocaleString() + "上传见证性材料，交易金额为:" + tradeCount;
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(msgId,dealer.getCompanyCode(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
	}
	/**
	 *@Author: hdx
	 *@CreateTime: 17:32 2019/10/14
	 *@param:  * @param msgId
	 *@Description: 获取需求履历
	 */
	@Transactional(readOnly = false)
	public List<ShopMsgStatus> getMsgRecord(String msgId) throws ShopMsgException{
		//需求id为空
		if (StringUtils.isBlank(msgId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		ShopMsgStatus sms = new ShopMsgStatus();
		//设置需求id
		sms.setMsgId(msgId);
		Page p = new Page<>();
		//设置排序规则
		p.setOrderBy("create_date asc");
		//放入分页对象
		sms.setPage(p);
		List<ShopMsgStatus> listShopMsgStatus = shopMsgStatusService.findList(sms);
		return listShopMsgStatus;
	}



	/**
	 *@Author: hdx
	 *@CreateTime: 17:32 2019/10/14
	 *@param:  * @param msgId
	 *@Description: 获取需求履历
	 */
	@Transactional(readOnly = false)
	public List<ShopMsg> purchaserGetMsgList(String mobile,String category,String nodetag) throws ShopMsgException{
		//mobile为空
		if (StringUtils.isBlank(mobile)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_MOBILE_ERROR);
		}
		ShopMsg shopMsg = new ShopMsg();
		//设置手机号
		shopMsg.setMobile(mobile);
		//设置分类
        shopMsg.setCategory(category);
        //设置nodetag
        shopMsg.setNodetag(nodetag);
        //排序
		Page<ShopMsg> pageShopMsg = new Page<ShopMsg>();
		pageShopMsg.setOrderBy(" a.create_date desc");
		shopMsg.setPage(pageShopMsg);
		List<ShopMsg> listShopMsg = this.findList(shopMsg);
		return listShopMsg;
	}

    /**
     *@Author: hdx
     *@CreateTime: 17:32 2019/10/14
     *@param:  * @param msgId
     *@Description: 根据经销商88码获取信息
     */
    @Transactional(readOnly = false)
    public Dealer getDealerDetails(String dealerCode) throws ShopMsgException{
        //dealerCode为空
        if (StringUtils.isBlank(dealerCode)) {
            throw new ShopMsgException(ShopMsgCode.PARAM_DEALERCODE_ERROR);
        }
        //查询
        Dealer dealer = null;
        try{
            dealer = dealerService.findUniqueByProperty("company_code",dealerCode);
        }catch (Exception e){
            throw new ShopMsgException(ShopMsgCode.DEALER_EXIS_EXCEPTION + e.getMessage());
        }
        //是否存在
        if(dealer==null){
            throw new ShopMsgException(ShopMsgCode.DEALER_NOTEXIST_ERROR);
        }
        return dealer;
    }

	/**
	 *@Author: hdx
	 *@CreateTime: 17:32 2019/10/14
	 *@param:  * @param msgId
	 *@Description: 根据经销商88码获取信息
	 */
	@Transactional(readOnly = false)
	public ShopMsgZykc dealerZykcGetInfo(String msgId,String orderId) throws ShopMsgException{
		//需求id为空
		if (StringUtils.isBlank(msgId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_MSGID_ERROR);
		}
		//订单id为空
		if (StringUtils.isBlank(orderId)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR);
		}
		//查看需求是否存在
		ShopMsg shopMsg = this.get(msgId);
		if(null==shopMsg){
			throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
		}
		//查询零售单数据
		ShopMsgZykc smz = new ShopMsgZykc();
		//需求id
		smz.setMsgId(msgId);
		//订单id
		smz.setOrderId(orderId);
		List<ShopMsgZykc> listShopMsgZykc = shopMsgZykcService.findList(smz);
		//是否为空
		if(null==listShopMsgZykc||listShopMsgZykc.size()==0){
			throw new ShopMsgException(ShopMsgCode.ORDER_ZYKC_ERROR);
		}
		ShopMsgZykc smzk = listShopMsgZykc.get(0);
		smzk.setShopMsg(shopMsg);
		return smzk;
	}


	/**
	 *@Author: hdx
	 *@CreateTime: 17:32 2019/10/14
	 *@param:  * @param msgId
	 *@Description: 派单关闭
	 */
	@Transactional(readOnly = false)
	public void dispatclose(ShopMsgDispatcher shopMsgDispatcher) throws ShopMsgException{
		//派单关闭id为空
		if (StringUtils.isBlank(shopMsgDispatcher.getId())) {
			throw new ShopMsgException(ShopMsgCode.PARAM_DISPATID_ERROR);
		}
		//派单理由不能为空
		if (StringUtils.isBlank(shopMsgDispatcher.getCloseReason())) {
			throw new ShopMsgException(ShopMsgCode.PARAM_CLOESEREASON_ERROR);
		}
		//查询派单信息
		shopMsgDispatcher = shopMsgDispatcherService.get(shopMsgDispatcher.getId());
		if(null==shopMsgDispatcher){
			throw new ShopMsgException(ShopMsgCode.DISPAT_NOTEXIST_ERROR);
		}
		//获取当前操作用户
		User currentUser = UserUtils.getUser();
		//关闭
		shopMsgDispatcher.setIsClosed(ProcessCode.YES.getLabel());
		//关闭人
		shopMsgDispatcher.setCloser(currentUser.getLoginName());
		//关闭时间
		shopMsgDispatcher.setCloseDate(new Date());
		//更新派单
		shopMsgDispatcherMapper.update(shopMsgDispatcher);
		//获取ShopMsg
		ShopMsg shopmsg = this.get(shopMsgDispatcher.getMsgId());
		shopmsg.setMsgFlag(MsgFlagCode.CUSTOMER_DELIVERY_CLOSED.getLabel());
		this.updateMsg(shopmsg);
        //履历
        String statusNode = MsgFlagCode.CUSTOMER_DELIVERY_CLOSED.getLabel();
        String statusName = MsgFlagCode.CUSTOMER_DELIVERY_CLOSED.getValue();
        //获取当前操作用户
        String content = currentUser + "-平台,已于"+ new Date().toLocaleString() + "再派单管理中关闭，需求公司名=" + shopmsg.getCompanyName() + ",需求id=" + shopmsg.getId();
        ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopmsg.getId(),currentUser.getLoginName(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
	}


    /**
     *@Author: hdx
     *@CreateTime: 17:32 2019/10/14
     *@param:  * @param msgId
     *@param:  * @param password
     *@param:  * @param shopMsg实体
     *@Description: 400/690 客服 推送需求接口
     */
    @Transactional(readOnly = false)
    public ShopMsgVo pushMsgToB2b(String username, String password, ShopMsgVo shopMsgVo) throws ShopMsgException, InvocationTargetException, IllegalAccessException {
    	//ShopMsg copy实体
		ShopMsg sm = new ShopMsg();
		if(MsgChannelCode.FOUR_CUSTOMER_SERVICE.getIndex().equals(shopMsgVo.getChannel())){
			//400接口验证用户名验证
			if (!Constants.B2B_400_USERNAME.equals(username)) {
				throw new ShopMsgException(ShopMsgCode.FOUR_ACCOUNT_ERROR);
			}
			//400接口验证密码验证
			if (!Constants.B2B_400_CIPHERKEY.equals(password)) {
				throw new ShopMsgException(ShopMsgCode.FOUR_PASSWORD_ERROR);
			}
		}else if(MsgChannelCode.GRAND_RECEPTION.getIndex().equals(shopMsgVo.getChannel())){
			//690接口验证用户名验证
			if (!Constants.B2B_RECEPTION_USERNAME.equals(username)) {
				throw new ShopMsgException(ShopMsgCode.SIX_ACCOUNT_ERROR);
			}
			//690接口验证密码验证
			if (!Constants.B2B_RECEPTION_CIPHERKEY.equals(password)) {
				throw new ShopMsgException(ShopMsgCode.SIX_PASSWORD_ERROR);
			}
		}else {
			throw new ShopMsgException(ShopMsgCode.WEBSERVICE_CHANNEL_EXCEPTION);
		}
        //校验手机号是否为空
        if(StringUtils.isEmpty(shopMsgVo.getMobile())){
            throw new ShopMsgException(ShopMsgCode.PARAM_MOBILE_ERROR);
        }
        //校验公司名是否为空
        if(StringUtils.isEmpty(shopMsgVo.getCompanyName())){
            throw new ShopMsgException(ShopMsgCode.PARAM_COMPANYNAME_ERROR);
        }
        //校验手机号正则规则
        boolean mobileFlag = RegexUtils.checkMobile(shopMsgVo.getMobile());
        if(!mobileFlag){
            throw new ShopMsgException(ShopMsgCode.REG_MOBILE_ERROR);
        }
        //校验省份编码是否为空
        if(StringUtils.isEmpty(shopMsgVo.getProvinceId())){
            throw new ShopMsgException(ShopMsgCode.PARAM_PROVICEID_ERROR);
        }
        //校验城市编码是否为空
        if(StringUtils.isEmpty(shopMsgVo.getCityId())){
            throw new ShopMsgException(ShopMsgCode.PARAM_CITYID_ERROR);
        }
        //校验地区编码是否为空
        if(StringUtils.isEmpty(shopMsgVo.getDistricId())){
            throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICID_ERROR);
        }
        //校验省份名称是否为空
        if(StringUtils.isEmpty(shopMsgVo.getProvinceName())){
            throw new ShopMsgException(ShopMsgCode.PARAM_PROVICENAME_ERROR);
        }
        //校验城市是否为空
        if(StringUtils.isEmpty(shopMsgVo.getCityName())){
            throw new ShopMsgException(ShopMsgCode.PARAM_CITYNAME_ERROR);
        }
        //校验地区名称是否为空
        if(StringUtils.isEmpty(shopMsgVo.getDistricName())){
            throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICNAME_ERROR);
        }
        //校验产品是否为空
        if(StringUtils.isEmpty(shopMsgVo.getProGroupCode())){
            throw new ShopMsgException(ShopMsgCode.PARAM_DISTRICNAME_ERROR);
        }
		BeanUtils.copyProperties(sm,shopMsgVo);
        //标识状态0 待审核
		sm.setMsgFlag(MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel());
        //是否已读 0 未读
		sm.setIsRead(ProcessCode.MSG_NO_READ.getLabel());
        //省市区全拼
		sm.setAddress(shopMsgVo.getProvinceName()+" " +shopMsgVo.getCityName() + " " + shopMsgVo.getDistricName() );
        //根据地区id进行工贸选取
        HpsGrid hpsGrid = hpsGridService.findUniqueByProperty("district_code",shopMsgVo.getDistricId());
        if(null!=hpsGrid){
            //设置工贸id
			sm.setOrgId(hpsGrid.getCenterCode());
            //设置工贸名称
			sm.setOrgName(hpsGrid.getCenterName());
        }
        //进行单号的生成
		sm.setMsgNo(msgUtils.getOrderIdByTime());
		//添加时间
		sm.setCreateDate(new Date());
		//派单标识添加未进入派单
		sm.setIsDispatch(ProcessCode.NO.getLabel());
        this.save(sm);
        //产品子表录入先删后插
        mapper.execDeleteSql("delete from shop_msg_product where msg_id = '" + sm.getId()+ "'");
        String proGroupCode = sm.getProGroupCode();
        if(StringUtils.isNotEmpty(proGroupCode)){
            String pdcode[] = proGroupCode.split(";");
            for(int i=0; i<pdcode.length; i++){
                ShopMsgProduct s = new ShopMsgProduct();
                //是否智慧
                s.setBeWisdom("0");
                //数量
                s.setEstimatedQuantity("0");
                //预计的采购预算
                s.setPurchaseBudget("0");
                //需求id
                s.setMsgId(sm.getId());
                //设置产品编码（hps对应编码）
                if(StringUtils.isEmpty(ProductCode.getValue(pdcode[i]))){
                    throw new ShopMsgException(ShopMsgCode.REG_PRODUCT_ERROR);
                }
                s.setProductGroupCode(ProductCode.getValue(pdcode[i]));
                //设置产品名称（用户语言）
                if(StringUtils.isEmpty(ProductCode.getLabel(pdcode[i]))){
                    throw new ShopMsgException(ShopMsgCode.REG_PRODUCT_ERROR);
                }
                s.setProductGroupName(ProductCode.getLabel(pdcode[i]));
                shopMsgProductService.save(s);
            }
        }
		BeanUtils.copyProperties(shopMsgVo,sm);
        return shopMsgVo;
    }



    /**
     *@Author: hdx
     *@CreateTime: 17:32 2019/10/14
     *@param:  * @param msgId
     *@Description: 经销商-app物流出库接口
     */
    @Transactional(readOnly = false)
    public void dealerDeliver(String orderid,String deliverNum) throws ShopMsgException{
        //订单id为空
        if (StringUtils.isBlank(orderid)) {
            throw new ShopMsgException(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR);
        }
        //订单id为空
        if (StringUtils.isBlank(deliverNum)) {
            throw new ShopMsgException(ShopMsgCode.PARAM_DELIVERNUM_ERROR);
        }
        ShopMsgCustcodeOrder shopMsgCustcodeOrder = shopMsgCustcodeOrderService.get(orderid);
        //是否存在此订单
        if(null==shopMsgCustcodeOrder){
            throw new ShopMsgException(ShopMsgCode.ORDER_NOTEXIST_ERROR);
        }
        //是否存在此需求单
        ShopMsg shopMsg = this.get(shopMsgCustcodeOrder.getMsgId());
        if(shopMsg==null){
            throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
        }
        //设置物流单号
        shopMsgCustcodeOrder.setDeliverNum(deliverNum);
        //设置物流出库时间
        shopMsgCustcodeOrder.setDeliverTime(new Date());
        //设置为物流已出库
        shopMsgCustcodeOrder.setIsDeliver(ProcessCode.YES.getLabel());
        //更新订单信息
        shopMsgCustcodeOrderMapper.update(shopMsgCustcodeOrder);
        //更新需求单
        shopMsg.setNodetag(ProcessCode.MOBILE_PROCESS_ARRIVAL.getLabel());
        //更新需求
        mapper.update(shopMsg);
		//履历
		String statusNode = MsgFlagCode.MOBILE_PROCESS_ARRIVAL.getLabel();
		String statusName = "已出库";
		//获取当前操作用户
		String content = shopMsgCustcodeOrder.getCustName() + "-经销商,已于"+ new Date().toLocaleString() + "物流出库,物流单号= "+deliverNum+",需求公司名=" + shopMsgCustcodeOrder.getCompanyName() + ",需求id=" + shopMsgCustcodeOrder.getId();
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),shopMsgCustcodeOrder.getCustName(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
		shopMsgStatusService.save(shopMsgStatus);
    }


    /**
     *@Author: hdx
     *@CreateTime: 17:32 2019/10/14
     *@param:  * @param msgId
     *@Description: 经销商-app安装接口
     */
    @Transactional(readOnly = false)
    public void dealerInstall(String orderid,String installman) throws ShopMsgException{
        //订单id为空
        if (StringUtils.isBlank(orderid)) {
            throw new ShopMsgException(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR);
        }
        //订单id为空
        if (StringUtils.isBlank(installman)) {
            throw new ShopMsgException(ShopMsgCode.PARAM_INSTALLMAN_ERROR);
        }
        ShopMsgCustcodeOrder shopMsgCustcodeOrder = shopMsgCustcodeOrderService.get(orderid);
        //是否存在此订单
        if(null==shopMsgCustcodeOrder){
            throw new ShopMsgException(ShopMsgCode.ORDER_NOTEXIST_ERROR);
        }
        //是否存在此需求单
        ShopMsg shopMsg = this.get(shopMsgCustcodeOrder.getMsgId());
        if(shopMsg==null){
            throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
        }
        //设置安装人
        shopMsgCustcodeOrder.setInstallPersion(installman);
        //设置安装时间
        shopMsgCustcodeOrder.setInstallDate(new Date());
        //设置安装状态已安装
        shopMsgCustcodeOrder.setIsInstall(ProcessCode.YES.getLabel());
        //更新订单信息
        shopMsgCustcodeOrderMapper.update(shopMsgCustcodeOrder);
        //更新需求单
        shopMsg.setNodetag(ProcessCode.MOBILE_PROCESS_INSTALL.getLabel());
        //更新需求
        mapper.update(shopMsg);
		//履历
		String statusNode = MsgFlagCode.MOBILE_PROCESS_INSTALL.getLabel();
		String statusName = "已安装";
		//获取当前操作用户
		String content = shopMsgCustcodeOrder.getCustName() + "-经销商,已于"+ new Date().toLocaleString() + "安装，安装人= "+ installman +"需求公司名" + shopMsgCustcodeOrder.getCompanyName() + ",需求id=" + shopMsgCustcodeOrder.getId();
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),shopMsgCustcodeOrder.getCustName(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
		shopMsgStatusService.save(shopMsgStatus);
    }


    /**
     *@Author: hdx
     *@CreateTime: 17:32 2019/10/14
     *@param:  * @param msgId
     *@Description: 经销商-app物流签收接口(上传订单及签收凭证)
     */
    @Transactional(readOnly = false)
    public void dealerSign(String orderid,String signUrl) throws ShopMsgException{
        //订单id为空
        if (StringUtils.isBlank(orderid)) {
            throw new ShopMsgException(ShopMsgCode.PARAM_ORDERID_ISNULL_ERROR);
        }
        ShopMsgCustcodeOrder shopMsgCustcodeOrder = shopMsgCustcodeOrderService.get(orderid);
        //是否存在此订单
        if(null==shopMsgCustcodeOrder){
            throw new ShopMsgException(ShopMsgCode.ORDER_NOTEXIST_ERROR);
        }
        //是否存在此需求单
        ShopMsg shopMsg = this.get(shopMsgCustcodeOrder.getMsgId());
        if(shopMsg==null){
            throw new ShopMsgException(ShopMsgCode.MSG_NOTEXIST_ERROR);
        }
        //设置签收证明
        shopMsgCustcodeOrder.setSignUrl(signUrl);
        //设置签收时间
        shopMsgCustcodeOrder.setSignDate(new Date());
        //设置签收状态已签收
        shopMsgCustcodeOrder.setIsSign(ProcessCode.YES.getLabel());
        //更新订单信息
        shopMsgCustcodeOrderMapper.update(shopMsgCustcodeOrder);
        //更新需求单
        shopMsg.setNodetag(ProcessCode.MOBILE_PROCESS_DRYING.getLabel());
        //更新需求
        mapper.update(shopMsg);
		//履历
		String statusNode = MsgFlagCode.MOBILE_PROCESS_ARRIVAL.getLabel();
		String statusName = "已签收";
		//获取当前操作用户
		String content = shopMsgCustcodeOrder.getCustName() + "-经销商,已于"+ new Date().toLocaleString() + "物流签收，需求公司名" + shopMsgCustcodeOrder.getCompanyName() + ",需求id=" + shopMsgCustcodeOrder.getId();
		ShopMsgStatus shopMsgStatus = ShopMsgService.returnShopMsgStatus(shopMsg.getId(),shopMsgCustcodeOrder.getCustName(),OperatorTypeCode.DISTRIBUTOR.getValue(),statusNode,statusName,content);
		shopMsgStatusService.save(shopMsgStatus);
    }


	/**
	 *@Author: hdx
	 *@CreateTime: 17:32 2019/10/14
	 *@param:  * @param msgId
	 *@Description: 根据采购商手机号获取需求数量集合
	 */
	@Transactional(readOnly = false)
	public List<Integer> getShopMsgListCount(String mobile) throws ShopMsgException{
		List<Integer> listString = new ArrayList<>();
		int listcount0 = 0;
		int listcount1 = 0;
		int listcount3 = 0;
		int listcount4 = 0;
		int listcount5 = 0;
		//手机号
		if (StringUtils.isBlank(mobile)) {
			throw new ShopMsgException(ShopMsgCode.PARAM_MOBILE_ERROR);
		}
		//相应0
			List<ShopMsg> list0 = this.findList(new ShopMsg().setNodetag(ProcessCode.MOBILE_PROCESS_RESPONSE.getLabel()).setMobile(mobile));
			if(list0!=null && list0.size()>0){
				listcount0 = list0.size();
			}
		//跟单1 2
			List<ShopMsg> list1 = this.findList(new ShopMsg().setNodetag(ProcessCode.MOBILE_PROCESS_DOCUMENTARY.getLabel()).setMobile(mobile));
			if(list1!=null && list1.size()>0){
				listcount1 = list1.size();
			}
		//到货3
			List<ShopMsg> list3 = this.findList(new ShopMsg().setNodetag(ProcessCode.MOBILE_PROCESS_ARRIVAL.getLabel()).setMobile(mobile));
			if(list3!=null && list3.size()>0){
				listcount3 = list3.size();
			}
		//安装4
			List<ShopMsg> list4 = this.findList(new ShopMsg().setNodetag(ProcessCode.MOBILE_PROCESS_INSTALL.getLabel()).setMobile(mobile));
			if(list4!=null && list4.size()>0){
				listcount4 = list4.size();
			}
		//晒单5
			List<ShopMsg> list5 = this.findList(new ShopMsg().setNodetag(ProcessCode.MOBILE_PROCESS_DRYING.getLabel()).setMobile(mobile));
			if(list5!=null && list5.size()>0){
				listcount5 = list5.size();
			}
			listString.add(listcount0);
			listString.add(listcount1);
			listString.add(listcount3);
			listString.add(listcount4);
			listString.add(listcount5);
			return listString;
	}
}