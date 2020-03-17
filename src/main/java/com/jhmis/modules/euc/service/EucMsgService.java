/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.haier.user.api.hps.MyProject;
import com.haier.webservices.client.hps.project.HpsApi;
import com.jhmis.common.Enum.EucMsgCode;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Exception.EucException;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerMsg;
import com.jhmis.modules.customer.entity.ViewQygProjectdetailviewdate;
import com.jhmis.modules.customer.service.CustomerMsgService;
import com.jhmis.modules.customer.service.ViewQygProjectdetailviewdateService;
import com.jhmis.modules.euc.entity.*;
import com.jhmis.modules.euc.mapper.EucMsgMapper;
import com.jhmis.modules.euc.mapper.EucMsgOrderMapper;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.service.GmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * euc系统相关需求Service
 * @author hdx
 * @version 2019-11-08
 */
@Service
@Transactional(readOnly = true)
public class EucMsgService extends CrudService<EucMsgMapper, EucMsg> {

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
    EucMsgMapper eucMsgMapper;

    @Autowired
    EucLogService eucLogService;

    @Autowired
    EucMsgOrderService eucMsgOrderService;

    @Autowired
    EucMsgOrderMapper eucMsgOrderMapper;


    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

	public EucMsg get(String id) {
		return super.get(id);
	}

    public EucMsg getById(String id) {
        return eucMsgMapper.getById(id);
    }
	
	public List<EucMsg> findList(EucMsg eucMsg) {
		return super.findList(eucMsg);
	}
    //获取所有的没有共享的单子
    public List<EucMsg> findAllNoShare() {
        return mapper.findAllNoShare();
    }

    public Page<EucMsg> findPuncturePage(Page<EucMsg> page, EucMsg eucMsg) {
        eucMsg.setPage(page);
        page.setList(mapper.findPuncture(eucMsg));
        return page;
    }



    public List<EucMsg> findListOver(EucMsg eucMsg) {
        return this.findListOver(eucMsg);
    }

	
	public Page<EucMsg> findPage(Page<EucMsg> page, EucMsg eucMsg) {
		return super.findPage(page, eucMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(EucMsg eucMsg) {
		super.save(eucMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(EucMsg eucMsg) {
		super.delete(eucMsg);
	}

	@Transactional(readOnly = false)
	public void fromEucMsg(EucMsg eucMsg) throws EucException {
		//商机编码是否为空
		if(StringUtils.isBlank(eucMsg.getBusinessCode())){
			throw new EucException(EucMsgCode.PARAM_BUSINESS_CODE_ERROR);
		}
		//商机名称是否为空
		if(StringUtils.isBlank(eucMsg.getBusinessName())){
			throw new EucException(EucMsgCode.PARAM_BUSINESS_NAME_ERROR);
		}
		//商机来源是否为空
		if(StringUtils.isBlank(eucMsg.getBusinessSource())){
			throw new EucException(EucMsgCode.PARAM_BUSINESS_SOURCE_ERROR);
		}
		//商机类型不能为空
		if(StringUtils.isBlank(eucMsg.getBusinessType())){
			throw new EucException(EucMsgCode.PARAM_BUSINESSTYPE_ERROR);
		}
		//甲方公司名称不能为空
		if(StringUtils.isBlank(eucMsg.getCompanyNameA())){
			throw new EucException(EucMsgCode.PARAM_COMPANYNAMEA_ERROR);
		}
		//中心不能为空
		if(StringUtils.isBlank(eucMsg.getCenter())){
			throw new EucException(EucMsgCode.PARAM_CENTER_ERROR);
		}
        //省份不能为空
        if(StringUtils.isBlank(eucMsg.getProvince())){
            throw new EucException(EucMsgCode.PARAM_PROVINCE_ERROR);
        }
        //省份Id不能为空
        if(StringUtils.isBlank(eucMsg.getProvinceId())){
            throw new EucException(EucMsgCode.PARAM_PROVINCE_ERROR);
        }
        //城市不能为空
        if(StringUtils.isBlank(eucMsg.getCity())){
            throw new EucException(EucMsgCode.PARAM_CITY_ERROR);
        }
        //城市Id不能为空
        if(StringUtils.isBlank(eucMsg.getCityId())){
            throw new EucException(EucMsgCode.PARAM_CITYID_ERROR);
        }
        //区县不能为空
        if(StringUtils.isBlank(eucMsg.getDistrict())){
            throw new EucException(EucMsgCode.PARAM_DISTRICT_ERROR);
        }
        //区县id不能为空
        if(StringUtils.isBlank(eucMsg.getDistrictId())){
            throw new EucException(EucMsgCode.PARAM_DISTRICTID_ERROR);
        }
        //区县不能为空
        if(StringUtils.isBlank(eucMsg.getBusinessOpportunityAddress())){
            throw new EucException(EucMsgCode.PARAM_BUSINESSOPPORTUNITYADDRESS_ERROR);
        }
        //是否是top用户
        if(StringUtils.isBlank(eucMsg.getIfTopCustomer())){
            eucMsg.setIfTopCustomer("否");
        }
        //网格编码不能为空
        if(StringUtils.isBlank(eucMsg.getGridCode())){
            throw new EucException(EucMsgCode.PARAM_GRIDCODE_ERROR);
        }

        //是否已经存在此商机
        EucMsg eucMsgHas = this.findUniqueByProperty("business_code",eucMsg.getBusinessCode());
        //判断是否存在
        if(eucMsgHas!=null){
            //首先判断是否有效
            if(ProcessCode.NO.getLabel().equals(eucMsgHas.getIsValid())){
                //如果无效
                throw new EucException(EucMsgCode.ERROR_NOTVALIAD);
            }
            //判断是否是指派(经销商编码和名称)
            if(StringUtils.isNotBlank(eucMsg.getContractorCode()) && StringUtils.isNotBlank(eucMsg.getContractorName())){
                //证明是指派经销商
                EucMsgOrder eucMsgOrder = new EucMsgOrder();
                //设置经销商编码
                eucMsgOrder.setContractorCode(eucMsg.getContractorCode());
                //商机名称
                eucMsgOrder.setBusinessCode(eucMsg.getBusinessCode());
                //是否放弃为0
                eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
                List<EucMsgOrder> listEucMsgOrder = eucMsgOrderService.findList(eucMsgOrder);
                //判断是否已经存在此订单
                if(null!=listEucMsgOrder && listEucMsgOrder.size()>0){
                    throw new EucException(EucMsgCode.ERROR_HASPUSH);
                }else{
                    //更新经销商信息
                    eucMsgHas.setContractorCode(eucMsg.getContractorCode());
                    eucMsgHas.setContractorName(eucMsg.getContractorName());
                    //如果此订单不存在则生成指派订单信息
                    createOrder(eucMsgHas,EucMsgCode.ORDER_TYPE_DISPATCHER.getLabel());
                    //成功推送返回id
                }
            }else{
                //判断是否已经有经销商共享
                if(null != eucMsgHas.getShareCount() && 0!=eucMsgHas.getShareCount()){
                    //已经有经销商共享单,不可更新
                    throw new EucException(EucMsgCode.ERROR_NOCHANGE);
                }
                //如果没有则更新需求
                EucMsg endEucMsg = updateValue(eucMsg, eucMsgHas);
                //进行时间
                mapper.update(endEucMsg);
            }
        }else{
            //EUC商机不存在
            //创建EUC基础数据
            EucMsg saveMsg = createEuc(eucMsg);
            //如果不存在再判断是否指派
            if(StringUtils.isNotBlank(eucMsg.getContractorCode()) && StringUtils.isNotBlank(eucMsg.getContractorName())){
                //指派的情况则创建经销商指派订单
                createOrder(saveMsg,EucMsgCode.ORDER_TYPE_DISPATCHER.getLabel());
            }
        }
	}

    public EucMsg updateValue(EucMsg eucMsg,EucMsg hasEucMsg){
	    //设置id
        eucMsg.setId(hasEucMsg.getId());
        //设置编码
        eucMsg.setBusinessCode(hasEucMsg.getBusinessCode());
        //更新时间
        eucMsg.setUpdateDate(new Date());
        //备注
        eucMsg.setRemarks("EUC数据更新" + new Date());
        //msgId
        eucMsg.setMsgId(hasEucMsg.getMsgId());
        //进入时间
        eucMsg.setEntryDate(hasEucMsg.getEntryDate());
        //是否有效
        eucMsg.setIsValid(hasEucMsg.getIsValid());
        //范围状态
        eucMsg.setScreenStstus(hasEucMsg.getScreenStstus());
        //共享数量
        eucMsg.setShareCount(hasEucMsg.getShareCount());
        //是否传入hps
        eucMsg.setIsSendHps(hasEucMsg.getIsSendHps());
        //最近处理时间
        eucMsg.setLastProcessingTime(hasEucMsg.getLastProcessingTime());
        return eucMsg;
    }


    /**
     *@Author: hdx
     *@CreateTime: 15:12 2020/3/6
     *@param:  code 是否回传EUC成功
     *@param:  status 回传状态
     *@param:  eucMsg EUC共享基础数据
     *@param:  eucMsgOrder 经销商订单数据
     *@param:  EucReturnBody 回传euc字段
     *@Description: EUC日志存储
     */
	public void eucSaveLog(String code, String status, EucReturnBody eucReturnBody, EucMsg eucMsg, EucMsgOrder eucMsgOrder){
        EucLog eucLog = new EucLog();
        //回传内容
        String content = "";
        //成功失败判别
        String flag = "失败";
        //返回状态
        if("0".equals(code)){
            flag = "成功";
        }
        switch (status){
            //待经销商抢单
            case "200":
                content = eucReturnBody.getRemark() + flag + eucReturnBody.toString();
                break;
            //待经销商选择承接方式
            case "300":
                content = "待经销商选择承接方式状态回传" + flag + eucReturnBody.toString();
                break;
            case "500":
                content = eucReturnBody.getRemark() + flag + eucReturnBody.toString();
                break;
            //待经销商完善客单报备信息
            case "600":
                String undertake = "工程";
                //履约方式
                if(StringUtils.isNotBlank(eucReturnBody.getPerformanceWay())){
                    if(EucMsgCode.UNDERTAKE_RETAIL.getLabel().equals(eucReturnBody.getPerformanceWay())){
                        undertake = "零售";
                    }
                }
                content = "经销商选择"+ undertake +"，待经销商完善客单报备信息状态回传" + flag + eucReturnBody.toString();
            break;
            //零售闭环
            case " 700":
                content = "EUC零售单审核，零售闭环" + flag + eucReturnBody.toString();
                break;
            //提报hps
            case "1000":
                content = "企业购提报hps成功后，回传EUC" + flag + eucReturnBody.toString();
                break;

        }
        //需求Id
        eucLog.setMsgId(eucMsg.getMsgId());
        //商机编码
        eucLog.setBusinessCode(eucMsg.getBusinessCode());
        //商机名称
        eucLog.setBusinessName(eucMsg.getBusinessName());
        //创建时间
        eucLog.setAddTime(new Date());
        //状态
        eucLog.setStatus(status);
        //内容
        eucLog.setContent(content);
        //保存
        eucLogService.save(eucLog);
    }

	/**
	*@Author: hdx
	*@CreateTime: 15:12 2020/3/6
	*@param:  eucMsg
	*@Description: EUC生成订单
	*/
	private EucMsgOrder createOrder(EucMsg eucMsg,String orderType) throws EucException{
        EucMsgOrder eucMsgOrder = new EucMsgOrder();
        //需求id
        eucMsgOrder.setEucId(eucMsg.getId());
        //未放弃
        eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
        eucMsgOrder.setOrderType(orderType);
        //经销商编码
        eucMsgOrder.setContractorCode(eucMsg.getContractorCode());
        //经销商名称
        eucMsgOrder.setContractorName(eucMsg.getContractorName());
        //创建时间
        eucMsgOrder.setCreateTime(new Date());
        //更新时间
        eucMsgOrder.setUpdateTime(new Date());
        //商机编码
        eucMsgOrder.setBusinessCode(eucMsg.getBusinessCode());
        //中心编码
        eucMsgOrder.setOrgId(eucMsg.getCenter());
        //是否中标
        eucMsgOrder.setIsBind(ProcessCode.NO.getLabel());
        //保存订单信息表
        eucMsgOrderService.save(eucMsgOrder);
        //EUC共享单数据字段+1
        if(null == eucMsg.getShareCount()){
            eucMsg.setShareCount(1);
        }else{
            int num = eucMsg.getShareCount();
            eucMsg.setShareCount(num+1);
        }
        //进行更新基础数据
        eucMsgService.save(eucMsg);
        //EUC状态内容回传
        EucReturnBody eucReturnBody = new EucReturnBody();
        //商机编码
        eucReturnBody.setBusinessCode(eucMsg.getBusinessCode());
        //商机状态
        eucReturnBody.setStatus(EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel());
        //操作人 - 经销商
        eucReturnBody.setPerson(eucMsgOrder.getContractorName());
        //操作人操作人编码 - 经销商编码
        eucReturnBody.setPersonCode(eucMsgOrder.getContractorCode());
        //备注
        eucReturnBody.setRemark(EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getValue());
        //回传EUC
        String code = updateToEuc(eucReturnBody);
        //EUC状态回传日志
        eucSaveLog(code,EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel(),eucReturnBody,eucMsg,eucMsgOrder);
        return eucMsgOrder;
    }



    /**
     *@Author: hdx
     *@CreateTime: 15:12 2020/3/6
     *@param:  eucMsg
     *@Description: EUC基础数据
     */
    private EucMsg createEuc(EucMsg eucMsg) throws EucException{
        //生成msgId
        eucMsg.setMsgId(getOrderIdByTime());
        //传入时间
        eucMsg.setEntryDate(new Date());
        //筛选条件 - 默认网格
        eucMsg.setScreenStstus(EucMsgCode.SCREEN_STSTUS_GIRD.getLabel());
        //是否可见 - 可见
        eucMsg.setIsValid(ProcessCode.YES.getLabel());
        //共享数量 - 0
        eucMsg.setShareCount(0);
        //是否已传入hps
        eucMsg.setIsSendHps(ProcessCode.NO.getLabel());
        //最近处理时间时间
        eucMsg.setLastProcessingTime(new Date());
        //进行保存EUC基础数据表
        eucMsgService.save(eucMsg);
        return eucMsg;
    }



    @Transactional(readOnly = false)
    public Map<String,Object> report(CustomerMsg customerMsg, Dealer d, DealerAccount currentAccount) throws EucException {
        if(StringUtils.isEmpty(customerMsg.getProjectName())||StringUtils.isEmpty(customerMsg.getFirstCompanyName())||StringUtils.isEmpty(customerMsg.getAddressCity())||StringUtils.isEmpty(customerMsg.getAddressProvince())||StringUtils.isEmpty(customerMsg.getAddressCounty())||StringUtils.isEmpty(customerMsg.getProjectCreaterCode())){
            logger.info("*_*_*_*_*_*_*_*_*_* 参数错误 *_*_*_*_*_*_*_*_*_*");
            throw new EucException("参数错误！");
        }
        if(StringUtils.isNotBlank(customerMsg.getProjectId())){
            logger.info("*_*_*_*_*_*_*_*_*_* 该项目不能重复提交*_*_*_*_*_*_*_*_*_*");
            throw new EucException("该项目不能重复提交！");
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
        customerMsg.setMsgId(customerMsg.getMsgId());
        customerMsg.setIsDel("0");
        customerMsg.setUserId(currentAccount.getId());
        customerMsg.setProjectManagerCode(customerMsg.getProjectCreaterCode());
        Map<String,Object> map =  HpsApi.SaveEucProjectFromQYG(customerMsg);
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
            try{
                customerMsgService.save(customerMsg);
            }catch (Exception e){
                e.printStackTrace();
            }
            throw new EucException(errorMsg);
        }else if("success".equals(flag)){
            //success代表漏斗返回成功
            logger.info("*_*_*_*_*_*_*_*_*_* success代表漏斗返回成功 *_*_*_*_*_*_*_*_*_*");
            String projectCode = (String) map.get("projectCode");
            String msgId = (String) map.get("msgId");
            customerMsg.setProjectId(projectCode);
            customerMsg.setStatus("1");
            customerMsgService.save(customerMsg);
            //更新EUC列表需求信息
            EucMsg eucMsg = eucMsgService.findUniqueByProperty("msg_id",customerMsg.getMsgId());
            //证明传入hps
            eucMsg.setIsSendHps("1");
            //回填projectCode
            //eucMsg.setProjectCode(projectCode);
            //海尔接口人编码
            eucMsg.setProjectManagerCode(customerMsg.getProjectCreaterCode());
            //海尔接口人名称
            eucMsg.setProjectManagerName(customerMsg.getProjectManagerName());
            //客单需求id
            eucMsg.setCustomerMsgId(customerMsg.getId());
            //更新时间
            eucMsg.setUpdateDate(new Date());
            eucMsgMapper.update(eucMsg);
            //更新订单表数据
            EucMsgOrder eucMsgOrder = new EucMsgOrder();
            //设置经销商编码
            eucMsgOrder.setContractorCode(customerMsg.getContractorCode());
            //是否放弃为0
            eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
            //EUC信息
            eucMsgOrder.setEucId(eucMsg.getId());
            List<EucMsgOrder> listEucMsgOrder = eucMsgOrderService.findList(eucMsgOrder);
            if(null!=listEucMsgOrder && listEucMsgOrder.size()>0){
                eucMsgOrder = listEucMsgOrder.get(0);
                eucMsgOrder.setProjectCode(projectCode);
                eucMsgOrderMapper.update(eucMsgOrder);
            }
            //EUC状态内容回传
            EucReturnBody eucReturnBody = new EucReturnBody();
            //商机编码
            eucReturnBody.setBusinessCode(eucMsg.getBusinessCode());
            //商机状态
            eucReturnBody.setStatus(EucMsgCode.FUNNEL_ALREADY_EXISTS.getLabel());
            //操作人 - 经销商
            eucReturnBody.setPerson(eucMsgOrder.getContractorName());
            //操作人操作人编码 - 经销商编码
            eucReturnBody.setPersonCode(eucMsgOrder.getContractorCode());
            //备注
            eucReturnBody.setRemark(EucMsgCode.FUNNEL_ALREADY_EXISTS.getLabel());
            //回传EUC
            String code = updateToEuc(eucReturnBody);
            //EUC状态回传日志
            eucSaveLog(code,EucMsgCode.FUNNEL_ALREADY_EXISTS.getLabel(),eucReturnBody,eucMsg,eucMsgOrder);
            //查询view_QYG_ProjectDetailViewDate 视图
            String view_qyg_projectdetailviewdate_select_sql = "select * from view_QYG_ProjectDetailViewDate where projectcode='"+ projectCode +"'";
            //根据实体反射进行转换
            List<ViewQygProjectdetailviewdate> listViewQygProjectdetailviewdate = jdbcTemplate1.query(view_qyg_projectdetailviewdate_select_sql, new BeanPropertyRowMapper(ViewQygProjectdetailviewdate.class));
            if(listViewQygProjectdetailviewdate!=null && listViewQygProjectdetailviewdate.size()>0){
                //证明有效逐条插入
                for(ViewQygProjectdetailviewdate viewQygProjectdetailviewdate:listViewQygProjectdetailviewdate){
                    ViewQygProjectdetailviewdate projectdetailviewdate = viewQygProjectdetailviewdateService.getByProjectcode(viewQygProjectdetailviewdate.getProjectcode());
                    if(StringUtils.isBlank(viewQygProjectdetailviewdate.getProjectcode()) || StringUtils.isBlank(viewQygProjectdetailviewdate.getMsgid()) ){
                        continue;
                    }
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
            throw new EucException("调用异常");
        }
        return map;
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
		return "EUC"+newDate+result;
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

    @Transactional(readOnly = false)
    public Page<EucMsg> garbList(String orgId, HttpServletRequest request, HttpServletResponse response) throws EucException{
	    //判断中心是否为空
        if(StringUtils.isEmpty(orgId)){
            throw new EucException(EucMsgCode.PARAM_CENTER_ERROR);
        }
        //根据经销商编码和工贸查询待抢单信息
        EucMsg eucMsg = new EucMsg();
        //中心编码
        eucMsg.setCenter(orgId);
        //可抢单数据
        eucMsg.setIsAll(ProcessCode.YES.getLabel());
        //查询待抢单的信息
        Page<EucMsg> pageEucMsg = new Page<EucMsg>(request, response);

        Page<EucMsg>  pageResult = eucMsgService.findPage(pageEucMsg,eucMsg);
        return pageResult;
    }

    @Transactional(readOnly = false)
    public Page<EucMsg> garbList_v1(String orgId, String gridCode, HttpServletRequest request, HttpServletResponse response) throws EucException{
        logger.info("/garbList_v1 提供大客户app的euc抢单列表_v1" );
        //判断中心是否为空
        if(StringUtils.isEmpty(orgId)){
            throw new EucException(EucMsgCode.PARAM_CENTER_ERROR);
        }
        //判断网格编码是否为空
        if(StringUtils.isEmpty(gridCode)){
            throw new EucException(EucMsgCode.PARAM_GRID_CODE_ERROR);
        }
        //根据经销商编码和工贸查询待抢单信息
        EucMsg eucMsg = new EucMsg();
        //中心编码
        eucMsg.setCenter(orgId);
        //网格编码
        eucMsg.setGridCode(gridCode);
        //euc商机必须有效
        eucMsg.setIsValid(ProcessCode.YES.getLabel());
        //查询待抢单的信息
        Page<EucMsg> pageEucMsg = new Page<EucMsg>(request, response);
        Page<EucMsg>  pageResult = eucMsgService.findPuncturePage(pageEucMsg,eucMsg);
        return pageResult;
    }


    /**
    *@Description 抢单
    *@Param
    *@Return
    *@Author t.c
    *@Date 2020-03-06
    */
    @Transactional(readOnly = false)
    public void dealerRobtrade(String eucMsgId,String dealerCode,String dealerName) throws EucException{
        logger.info("/dealerRobtrade 提供大客户app的euc抢单接口1" );
        //判断eucMsgId是否为空
        if(StringUtils.isEmpty(eucMsgId)){
            throw new EucException(EucMsgCode.PARAM_EUCMSGID_EXIST);
        }
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerName)){
            throw new EucException(EucMsgCode.PARAM_DEALERNAME_EXIST);
        }
        logger.info("/dealerRobtrade 提供大客户app的euc抢单接口2" );
        //根据查询此需求是否已有经销商抢到
        EucMsg hasEucMsg =null;
        try{
            hasEucMsg = eucMsgService.getById(eucMsgId);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("/dealerRobtrade 提供大客户app的euc抢单接口3" );
        //需求不存在
        if(hasEucMsg==null){
            throw new EucException(EucMsgCode.EUC_MSG_NO_EXIST);
        }
        logger.info("/dealerRobtrade 提供大客户app的euc抢单接口4" );
        //判断是否存在经销商承接
        if(StringUtils.isNotEmpty(hasEucMsg.getContractorCode())){
            throw new EucException(EucMsgCode.DEALERCODE_EXIST);
        }
        //设置经销商编码
        hasEucMsg.setContractorCode(dealerCode);
        //设置经销商名称
        hasEucMsg.setContractorName(dealerName);
        //设置更新时间
        hasEucMsg.setUpdateDate(new Date());
        logger.info("/dealerRobtrade 提供大客户app的euc抢单接口5" );
        mapper.update(hasEucMsg);
        //增加euc订单
        EucMsgOrder eucMsgOrder = new EucMsgOrder();
        //设置msgId
        eucMsgOrder.setEucId(eucMsgId);
        //经销商编码
        eucMsgOrder.setContractorCode(dealerCode);
        //经销商名称
        eucMsgOrder.setContractorName(dealerName);
        //创建时间
        eucMsgOrder.setCreateTime(new Date());
        //是否放弃
        eucMsgOrder.setIsAbandon("0");
        //订单类型
        eucMsgOrder.setOrderType(EucMsgCode.ORDER_TYPE_GARB.getLabel());
        //是否中标
        eucMsgOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
        //更新时间
        eucMsgOrder.setUpdateTime(new Date());
        //工贸id
        eucMsgOrder.setOrgId(hasEucMsg.getCenter());
        logger.info("/dealerRobtrade 提供大客户app的euc抢单接口6" );
        eucMsgOrderService.save(eucMsgOrder);

        //成功录入后回传EUC
        //String httpStr = returnEuc(hasEucMsg.getBusinessCode(),EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel(),hasEucMsg.getContractorName(),hasEucMsg.getContractorCode(),"","");
        EucReturnBody eucReturnBody=new EucReturnBody();
        eucReturnBody.setBusinessCode(hasEucMsg.getBusinessCode());
        eucReturnBody.setStatus(EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel());
        WinBidCompany winBidCompany=new WinBidCompany();
        winBidCompany.setCompanyName(hasEucMsg.getContractorName());
        winBidCompany.setCompanyCode(hasEucMsg.getContractorCode());
        eucReturnBody.setWinBidCompany(winBidCompany);
        String result=updateToEuc(eucReturnBody);
        EucLog eucLog = new EucLog();
        //时间
        eucLog.setAddTime(new Date());
        //商机编码
        eucLog.setBusinessCode(hasEucMsg.getBusinessCode());
        //商机名称
        eucLog.setBusinessName(hasEucMsg.getBusinessName());
        //需求id
        eucLog.setMsgId(hasEucMsg.getMsgId());
        //状态
        eucLog.setStatus(EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel());
        if("0".equals(result)){
            //回传成功
            logger.info("*_*_*_*_*_*_*_*_*_* EUC回传成功 *_*_*_*_*_*_*_*_*_*");
            eucLog.setContent("经销商抢单，待经销商选择承接方式状态回传成功" );
        }else{
            //回传失败
            eucLog.setContent("经销商抢单，待经销商选择承接方式状态回传失败");
            logger.info("*_*_*_*_*_*_*_*_*_* EUC回传失败 *_*_*_*_*_*_*_*_*_*");
        }
        logger.info("/dealerRobtrade 提供大客户app的euc抢单接口8" );
        eucLogService.save(eucLog);

    }

    @Transactional(readOnly = false)
    public EucMsgOrder dealerRobtrade_v1(String eucMsgId,String dealerCode,String dealerName) throws EucException{
        logger.info("/dealerRobtrade_v1 提供大客户app的euc抢单接口_v1" );
        //判断eucMsgId是否为空
        if(StringUtils.isEmpty(eucMsgId)){
            throw new EucException(EucMsgCode.PARAM_EUCMSGID_EXIST);
        }
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        //判断dealerName是否为空
        if(StringUtils.isEmpty(dealerName)){
            throw new EucException(EucMsgCode.PARAM_DEALERNAME_EXIST);
        }
        //查询是否存在此需求
        EucMsg hasEucMsg = mapper.getOwnById(eucMsgId);
        //需求不存在
        if(hasEucMsg==null){
            throw new EucException(EucMsgCode.EUC_MSG_NO_EXIST);
        }
        //商机需求无效
        if(ProcessCode.NO.getLabel().equals(hasEucMsg.getIsValid())){
            throw new EucException(EucMsgCode.EUC_MSG_NOINPOOL);
        }
        //判断是否此经销商已经抢到
        //证明是指派经销商
        EucMsgOrder eucMsgOrder = new EucMsgOrder();
        //设置经销商编码
        eucMsgOrder.setContractorCode(dealerCode);
        //商机名称
        eucMsgOrder.setContractorName(dealerName);
        //是否放弃为0
        eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
        //EUC信息
        eucMsgOrder.setEucId(hasEucMsg.getId());
        List<EucMsgOrder> listEucMsgOrder = eucMsgOrderService.findList(eucMsgOrder);
        if(null!=listEucMsgOrder && listEucMsgOrder.size()>0){
            throw new EucException(EucMsgCode.ERROR_ALREADY_HAVE);
        }
        //填充经销编码和经销商名称
        hasEucMsg.setContractorCode(dealerCode);
        hasEucMsg.setContractorName(dealerName);
        //生成抢单订单
        return createOrder(hasEucMsg,EucMsgCode.ORDER_TYPE_GARB.getLabel());

    }


    @Transactional(readOnly = false)
    public void dealerUnderTake(String undertake,String dealerCode, String dealerName,String orderId) throws EucException{
        //判断orderId是否为空
        if(StringUtils.isEmpty(orderId)){
            throw new EucException(EucMsgCode.PARAM_ORDERID_EXIST);
        }
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        //判断dealerName是否为空
        if(StringUtils.isEmpty(dealerName)){
            throw new EucException(EucMsgCode.PARAM_DEALERNAME_EXIST);
        }
        //判断undertake是否为空
        if(StringUtils.isEmpty(undertake)){
            throw new EucException(EucMsgCode.PARAM_UNDERTAKE_EXIST);
        }
        //根据订单id查询
        EucMsgOrder eucMsgOrder = eucMsgOrderService.get(orderId);
        if(eucMsgOrder==null){
            throw new EucException(EucMsgCode.NOT_ORDER_EXIST);
        }
        //判断是否存在这个经销商承接
        if(!dealerCode.equals(eucMsgOrder.getContractorCode())){
            throw new EucException(EucMsgCode.ERROR_CHECK_EUC_FROM);
        }
        //是否已承接
        if(StringUtils.isNotEmpty(eucMsgOrder.getUndertake())){
            throw new EucException(EucMsgCode.AL_UNDERTAKE_EXIST);
        }
        eucMsgOrder.setUndertake(undertake);
        //如果是工程单
        if(undertake.equals(EucMsgCode.UNDERTAKE_PROJECT.getLabel())){
            //成功录入后回传EUC
            String httpStr = returnEuc(eucMsgOrder.getBusinessCode(),EucMsgCode.WAITING_FOR_INFORMATION.getLabel(),eucMsgOrder.getContractorName(),eucMsgOrder.getContractorCode(),"","");
            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
            Boolean returnFlag = (Boolean)jo.get("data");
            EucLog eucLog = new EucLog();
            //时间
            eucLog.setAddTime(new Date());
            //商机编码
            eucLog.setBusinessCode(eucMsgOrder.getBusinessCode());
            //商机名称
            eucLog.setBusinessName(eucMsgOrder.getBusinessName());
            //需求id
            eucLog.setMsgId(eucMsgOrder.getMsgId());
            //状态
            eucLog.setStatus(EucMsgCode.WAITING_FOR_INFORMATION.getLabel());
            if(returnFlag!=null){
                if(returnFlag){
                    //回传成功
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传成功 *_*_*_*_*_*_*_*_*_*");
                    eucLog.setContent("经销商选择工程单，待经销商完善客单报备信息状态回传成功" + returnFlag.toString());
                }else{
                    //回传失败
                    eucLog.setContent("经销商选择工程单，待经销商完善客单报备信息状态回传失败" + returnFlag.toString());
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传失败 *_*_*_*_*_*_*_*_*_*");
                }
            }else{
                eucLog.setContent(returnFlag.toString());
            }

            eucLogService.save(eucLog);


        }else if(undertake.equals(EucMsgCode.UNDERTAKE_RETAIL.getLabel())){
        //如果是零售
            eucMsgOrder.setIsCheck(ProcessCode.YES.getLabel());
            //成功录入后回传EUC
            String httpStr = returnEuc(eucMsgOrder.getBusinessCode(),EucMsgCode.AUTHENTICATION_MATERIALS.getLabel(),eucMsgOrder.getContractorName(),eucMsgOrder.getContractorCode(),"","");
            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
            Boolean returnFlag = (Boolean)jo.get("data");
            EucLog eucLog = new EucLog();
            //时间
            eucLog.setAddTime(new Date());
            //商机编码
            eucLog.setBusinessCode(eucMsgOrder.getBusinessCode());
            //商机名称
            eucLog.setBusinessName(eucMsgOrder.getBusinessName());
            //需求id
            eucLog.setMsgId(eucMsgOrder.getMsgId());
            //状态
            eucLog.setStatus(EucMsgCode.AUTHENTICATION_MATERIALS.getLabel());
            if(returnFlag!=null){
                if(returnFlag){
                    //回传成功
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传成功 *_*_*_*_*_*_*_*_*_*");
                    eucLog.setContent("经销商选择零售单，待经销商上传鉴证性材料状态回传成功" + returnFlag.toString());
                }else{
                    //回传失败
                    eucLog.setContent("经销商选择零售单，待经销商上传鉴证性材料状态回传失败" + returnFlag.toString());
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传失败 *_*_*_*_*_*_*_*_*_*");
                }
            }else{
                eucLog.setContent(returnFlag.toString());
            }

            eucLogService.save(eucLog);
        }else{
            throw new EucException(EucMsgCode.ERROR_UNDERTAKE);
        }
        //设置更新时间
        eucMsgOrder.setUpdateTime(new Date());
        eucMsgOrderService.save(eucMsgOrder);
    }


    @Transactional(readOnly = false)
    public void dealerUnderTake_v1(String undertake,String dealerCode, String dealerName,String orderId) throws EucException{
        //判断orderId是否为空
        if(StringUtils.isEmpty(orderId)){
            throw new EucException(EucMsgCode.PARAM_ORDERID_EXIST);
        }
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        //判断dealerName是否为空
        if(StringUtils.isEmpty(dealerName)){
            throw new EucException(EucMsgCode.PARAM_DEALERNAME_EXIST);
        }
        //判断undertake是否为空
        if(StringUtils.isEmpty(undertake)){
            throw new EucException(EucMsgCode.PARAM_UNDERTAKE_EXIST);
        }
        //根据订单id查询
        EucMsgOrder eucMsgOrder = eucMsgOrderService.get(orderId);
        if(eucMsgOrder==null){
            throw new EucException(EucMsgCode.NOT_ORDER_EXIST);
        }
        //判断是否存在这个经销商承接
        if(!dealerCode.equals(eucMsgOrder.getContractorCode())){
            throw new EucException(EucMsgCode.ERROR_CHECK_EUC_FROM);
        }
        //是否已承接
        if(StringUtils.isNotEmpty(eucMsgOrder.getUndertake())){
            throw new EucException(EucMsgCode.AL_UNDERTAKE_EXIST);
        }
        //设置承接方式
        eucMsgOrder.setUndertake(undertake);
        //更新时间
        eucMsgOrder.setUpdateTime(new Date());

        //根据eucId查询商机信息
        EucMsg eucMsg = mapper.getOwnById(eucMsgOrder.getEucId());
        if(null == eucMsg){
            throw new EucException(EucMsgCode.EUC_MSG_NO_EXIST);
        }
        //选择承接方式
        orderUndertake(eucMsg,eucMsgOrder);
    }

    /**
    *@Author: hdx
    *@CreateTime: 10:20 2020/3/10
    *@param:
    *@Description: 经销商选择承接方式
    */
    private void orderUndertake(EucMsg eucMsg, EucMsgOrder eucMsgOrder){
        //承接方式的区分
        EucReturnBody eucReturnBody = new EucReturnBody();
        //回传状态工程单
        eucReturnBody.setPerformanceWay(EucMsgCode.UNDERTAKE_PROJECT.getValue());
        if(EucMsgCode.UNDERTAKE_RETAIL.getLabel().equals(eucMsgOrder.getUndertake())){
            //回传状态零售单
            eucReturnBody.setPerformanceWay(EucMsgCode.UNDERTAKE_RETAIL.getValue());
        }
        eucMsgOrderService.save(eucMsgOrder);
        //EUC状态内容回传
        //商机编码
        eucReturnBody.setBusinessCode(eucMsg.getBusinessCode());
        //商机状态
        eucReturnBody.setStatus(EucMsgCode.WAITING_FOR_INFORMATION.getLabel());
        //操作人 - 经销商
        eucReturnBody.setPerson(eucMsgOrder.getContractorName());
        //操作人操作人编码 - 经销商编码
        eucReturnBody.setPersonCode(eucMsgOrder.getContractorCode());
        //备注
        eucReturnBody.setRemark(EucMsgCode.WAITING_FOR_INFORMATION.getValue());
        //回传EUC
        String code = updateToEuc(eucReturnBody);
        //EUC状态回传日志
        eucSaveLog(code,EucMsgCode.WAITING_FOR_INFORMATION.getLabel(),eucReturnBody,eucMsg,eucMsgOrder);
    }




    @Transactional(readOnly = false)
    public void eucMsgAbandon(String dealerCode,String orderId,String abandonType,String abandonRemark,String abandonWrite) throws EucException{
        //判断经销商是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        //判断orderId是否为空
        if(StringUtils.isEmpty(orderId)){
            throw new EucException(EucMsgCode.PARAM_ORDERID_EXIST);
        }
        //判断abandonType是否为空
        if(StringUtils.isEmpty(abandonType)){
            throw new EucException(EucMsgCode.PARAM_ABANDONTYPE_EXIST);
        }
        //根据查询此需求是否已有经销商抢到
        EucMsgOrder hasEucMsgOrder = eucMsgOrderService.get(orderId);
        //需求不存在
        if(hasEucMsgOrder==null){
            throw new EucException(EucMsgCode.EUC_MSG_NO_EXIST);
        }
        //判断是否存是这个经销商承接
        if(!dealerCode.equals(hasEucMsgOrder.getContractorCode())){
            throw new EucException(EucMsgCode.ERROR_CHECK_EUC_FROM);
        }
        //Euc需求
        EucMsg eucMsg = this.get(hasEucMsgOrder.getEucId());
        if(eucMsg==null){
            throw new EucException(EucMsgCode.EUC_MSG_NO_EXIST);
        }
        //判断是否处于可放弃阶段
        //此需求已进入工程系统，无法放弃
        if(StringUtils.isNotEmpty(hasEucMsgOrder.getProjectCode())){
            throw new EucException(EucMsgCode.ERROR_ABANDON_PROJECT);
        }
        //此需求已上传见证性材料，无法放弃
        if(StringUtils.isNotEmpty(hasEucMsgOrder.getImageUrl())){
            throw new EucException(EucMsgCode.ERROR_ABANDON_RETAIL);
        }
        if(EucMsgCode.ABANDONTYPE_NO_UNDEDRTAKE.getLabel().equals(abandonType)){
            //未选择承接方式前废弃
            //经销商编码
            eucMsg.setContractorCode("");
            //经销商名称
            eucMsg.setContractorName("");
            //更新时间
            eucMsg.setUpdateDate(new Date());
            mapper.update(eucMsg);
            hasEucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());

            //成功录入后回传EUC
            String httpStr = returnEuc(eucMsg.getBusinessCode(),EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel(),eucMsg.getContractorName(),eucMsg.getContractorCode(),"","");
            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
            Boolean returnFlag = (Boolean)jo.get("data");
            EucLog eucLog = new EucLog();
            //时间
            eucLog.setAddTime(new Date());
            //商机编码
            eucLog.setBusinessCode(eucMsg.getBusinessCode());
            //商机名称
            eucLog.setBusinessName(eucMsg.getBusinessName());
            //需求id
            eucLog.setMsgId(eucMsg.getMsgId());
            //状态
            eucLog.setStatus(EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel());
            if(returnFlag!=null){
                if(returnFlag){
                    //回传成功
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传成功 *_*_*_*_*_*_*_*_*_*");
                    eucLog.setContent("为选择承接方式前放弃，待经销商抢单状态回传成功" + returnFlag.toString());
                }else{
                    //回传失败
                    eucLog.setContent("为选择承接方式前放弃，待经销商抢单状态回传失败" + returnFlag.toString());
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传失败 *_*_*_*_*_*_*_*_*_*");
                }
            }else{
                eucLog.setContent( returnFlag.toString());
            }

            eucLogService.save(eucLog);


        }else if(EucMsgCode.ABANDONTYPE_PROJECT.getLabel().equals(abandonType)){
            //工程单放弃
            //经销商编码
            eucMsg.setContractorCode("");
            //经销商名称
            eucMsg.setContractorName("");
            //更新时间
            eucMsg.setUpdateDate(new Date());
            mapper.update(eucMsg);
            hasEucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());

            //成功录入后回传EUC
            String httpStr = returnEuc(eucMsg.getBusinessCode(),EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel(),eucMsg.getContractorName(),eucMsg.getContractorCode(),"","");
            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
            Boolean returnFlag = (Boolean)jo.get("data");
            EucLog eucLog = new EucLog();
            //时间
            eucLog.setAddTime(new Date());
            //商机编码
            eucLog.setBusinessCode(eucMsg.getBusinessCode());
            //商机名称
            eucLog.setBusinessName(eucMsg.getBusinessName());
            //需求id
            eucLog.setMsgId(eucMsg.getMsgId());
            //状态
            eucLog.setStatus(EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel());
            if(returnFlag!=null){
                if(returnFlag){
                    //回传成功
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传成功 *_*_*_*_*_*_*_*_*_*");
                    eucLog.setContent("选择工程单后放弃，待经销商抢单状态回传成功" + returnFlag.toString());
                }else{
                    //回传失败
                    eucLog.setContent("选择工程单后放弃，待经销商抢单状态回传失败" + returnFlag.toString());
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传失败 *_*_*_*_*_*_*_*_*_*");
                }
            }else{
                eucLog.setContent(returnFlag.toString());
            }

            eucLogService.save(eucLog);
        }else if(EucMsgCode.ABANDONTYPE_RETAIL.getLabel().equals(abandonType)){
            //零售单放弃
            //经销商编码
            eucMsg.setContractorCode("");
            //经销商名称
            eucMsg.setContractorName("");
            //更新时间
            eucMsg.setUpdateDate(new Date());
            mapper.update(eucMsg);
            hasEucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());

            //成功录入后回传EUC
            String httpStr = returnEuc(eucMsg.getBusinessCode(),EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel(),eucMsg.getContractorName(),eucMsg.getContractorCode(),"","");
            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
            Boolean returnFlag = (Boolean)jo.get("data");
            EucLog eucLog = new EucLog();
            //时间
            eucLog.setAddTime(new Date());
            //商机编码
            eucLog.setBusinessCode(eucMsg.getBusinessCode());
            //商机名称
            eucLog.setBusinessName(eucMsg.getBusinessName());
            //需求id
            eucLog.setMsgId(eucMsg.getMsgId());
            //状态
            eucLog.setStatus(EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel());
            if(returnFlag!=null){
                if(returnFlag){
                    //回传成功
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传成功 *_*_*_*_*_*_*_*_*_*");
                    eucLog.setContent("选择零售单后放弃，待经销商抢单状态回传成功" + returnFlag.toString());
                }else{
                    //回传失败
                    eucLog.setContent("选择零售单后放弃，待经销商抢单状态回传失败" + returnFlag.toString());
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传失败 *_*_*_*_*_*_*_*_*_*");
                }
            }else{
                eucLog.setContent(returnFlag.toString());
            }

            eucLogService.save(eucLog);
        }else if(EucMsgCode.ABANDONTYPE_RETAIL_RETURN.getLabel().equals(abandonType)){
            //零售单回退
            //更新时间
            //清空
            hasEucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
            hasEucMsgOrder.setUndertake("");
            //成功录入后回传EUC
            String httpStr = returnEuc(eucMsg.getBusinessCode(),EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel(),eucMsg.getContractorName(),eucMsg.getContractorCode(),"","");
            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(httpStr);
            Boolean returnFlag = (Boolean)jo.get("data");
            EucLog eucLog = new EucLog();
            //时间
            eucLog.setAddTime(new Date());
            //商机编码
            eucLog.setBusinessCode(eucMsg.getBusinessCode());
            //商机名称
            eucLog.setBusinessName(eucMsg.getBusinessName());
            //需求id
            eucLog.setMsgId(eucMsg.getMsgId());
            //状态
            eucLog.setStatus(EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel());
            if(returnFlag!=null){
                if(returnFlag){
                    //回传成功
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传成功 *_*_*_*_*_*_*_*_*_*");
                    eucLog.setContent("选择零售单后返回，待经销商选择承接方式状态回传成功" + returnFlag.toString());
                }else{
                    //回传失败
                    eucLog.setContent("选择零售单后返回，待经销商选择承接方式状态回传失败" + returnFlag.toString());
                    logger.info("*_*_*_*_*_*_*_*_*_* EUC回传失败 *_*_*_*_*_*_*_*_*_*");
                }
            }else{
                eucLog.setContent(returnFlag.toString());
            }

            eucLogService.save(eucLog);
        }
        //放弃原因
        hasEucMsgOrder.setAbandonReason(EucMsgCode.ABANDONREASON_ACTIVE.getValue());
        //放弃类型
        hasEucMsgOrder.setAbandonType(abandonType);
        //放弃时间
        hasEucMsgOrder.setAbandonTime(new Date());
        //放弃原因备注
        hasEucMsgOrder.setAbandonRemark(abandonRemark);
        //经销商放弃自主录入
        hasEucMsgOrder.setAbandonWrite(abandonWrite);
        eucMsgOrderMapper.update(hasEucMsgOrder);
    }

    /**
    *@Author: hdx
    *@CreateTime: 13:41 2020/3/10
    *@param:  * @param null
    *@Description:
        经销商放弃
     */
    @Transactional(readOnly = false)
    public void eucMsgAbandon_v1(String dealerCode,String orderId,String abandonType,String abandonRemark,String abandonWrite) throws EucException{
        //判断经销商是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        //判断orderId是否为空
        if(StringUtils.isEmpty(orderId)){
            throw new EucException(EucMsgCode.PARAM_ORDERID_EXIST);
        }
        //判断abandonType是否为空
        if(StringUtils.isEmpty(abandonType)){
            throw new EucException(EucMsgCode.PARAM_ABANDONTYPE_EXIST);
        }
        //根据查询此需求是否已有经销商抢到
        EucMsgOrder hasEucMsgOrder = eucMsgOrderService.get(orderId);
        //需求不存在
        if(hasEucMsgOrder==null){
            throw new EucException(EucMsgCode.EUC_MSG_NO_EXIST);
        }
        //判断是否存是这个经销商承接
        if(!dealerCode.equals(hasEucMsgOrder.getContractorCode())){
            throw new EucException(EucMsgCode.ERROR_CHECK_EUC_FROM);
        }
        //Euc需求
        EucMsg eucMsg = mapper.getOwnById(hasEucMsgOrder.getEucId());
        if(eucMsg==null){
            throw new EucException(EucMsgCode.EUC_MSG_NO_EXIST);
        }
        //判断是否处于可放弃阶段
        //此需求已进入工程系统，无法放弃
        if(StringUtils.isNotEmpty(hasEucMsgOrder.getProjectCode())){
            throw new EucException(EucMsgCode.ERROR_ABANDON_PROJECT);
        }
        //此需求已上传见证性材料，无法放弃
        if(StringUtils.isNotEmpty(hasEucMsgOrder.getImageUrl())){
            throw new EucException(EucMsgCode.ERROR_ABANDON_RETAIL);
        }
        //放弃原因
        hasEucMsgOrder.setAbandonReason(EucMsgCode.ABANDONREASON_ACTIVE.getValue());
        //放弃类型
        hasEucMsgOrder.setAbandonType(abandonType);
        //放弃时间
        hasEucMsgOrder.setAbandonTime(new Date());
        //放弃原因备注
        hasEucMsgOrder.setAbandonRemark(abandonRemark);
        //经销商放弃自主录入
        hasEucMsgOrder.setAbandonWrite(abandonWrite);
        //未选择承接方式前废弃
        if(EucMsgCode.ABANDONTYPE_NO_UNDEDRTAKE.getLabel().equals(abandonType)){
            //设置放弃类型
            hasEucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_NO_UNDEDRTAKE.getLabel());
            //是否放弃
            hasEucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
            //放弃订单
            orderAbandon(eucMsg,hasEucMsgOrder,"经销商未选择承接方式前废弃");
            //更新EUC订单信息
        }else if(EucMsgCode.ABANDONTYPE_PROJECT.getLabel().equals(abandonType)){
            //设置放弃类型
            hasEucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_PROJECT.getLabel());
                    //是否放弃
            hasEucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
            //工程单放弃
            orderAbandon(eucMsg,hasEucMsgOrder,"经销商工程单放弃");
        }else if(EucMsgCode.ABANDONTYPE_RETAIL.getLabel().equals(abandonType)){
            //零售单放弃
            //设置放弃类型
            hasEucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_NO_UNDEDRTAKE.getLabel());


            //是否放弃
            hasEucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
            //工程单放弃
            orderAbandon(eucMsg,hasEucMsgOrder,"经销商零售单放弃");
        }else if(EucMsgCode.ABANDONTYPE_RETAIL_RETURN.getLabel().equals(abandonType)){
            //零售回退
            hasEucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
            hasEucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_RETAIL_RETURN.getLabel());
            //承接方式为空
            hasEucMsgOrder.setUndertake("");
            EucReturnBody eucReturnBody = new EucReturnBody();
            //EUC状态内容回传
            //商机编码
            eucReturnBody.setBusinessCode(eucMsg.getBusinessCode());
            //商机状态
            eucReturnBody.setStatus(EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel());
            //操作人 - 经销商
            eucReturnBody.setPerson(hasEucMsgOrder.getContractorName());
            //操作人操作人编码 - 经销商编码
            eucReturnBody.setPersonCode(hasEucMsgOrder.getContractorCode());
            //备注
            eucReturnBody.setRemark(EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getValue());
            //回传EUC
            String code = updateToEuc(eucReturnBody);
            //EUC状态回传日志
            eucSaveLog(code,EucMsgCode.TO_BE_SELECTEDBY_THE_DEALER.getLabel(),eucReturnBody,eucMsg,hasEucMsgOrder);
            //更新订单状态
            eucMsgOrderMapper.update(hasEucMsgOrder);
        }
    }


    /**
    *@Author: hdx
    *@CreateTime: 15:12 2020/3/10
    *@param:  * @param null
    *@Description:
        经销商放弃订单
    */
    private void orderAbandon(EucMsg eucMsg, EucMsgOrder eucMsgOrder, String description){
        //更新EUC订单信息
        updateEucMsgShareCount(eucMsg);
        //是否放弃
        eucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
        //承接方式的区分
        EucReturnBody eucReturnBody = new EucReturnBody();
        //EUC状态内容回传
        //商机编码
        eucReturnBody.setBusinessCode(eucMsg.getBusinessCode());
        //商机状态
        eucReturnBody.setStatus(EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel());
        //操作人 - 经销商
        eucReturnBody.setPerson(eucMsgOrder.getContractorName());
        //操作人操作人编码 - 经销商编码
        eucReturnBody.setPersonCode(eucMsgOrder.getContractorCode());
        //备注
        eucReturnBody.setRemark(description + ","+ EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getValue());
        //回传EUC
        String code = updateToEuc(eucReturnBody);
        //EUC状态回传日志
        eucSaveLog(code,EucMsgCode.WAITING_FOR_DEALER_TO_GRAB.getLabel(),eucReturnBody,eucMsg,eucMsgOrder);
        //更新订单状态
        eucMsgOrderMapper.update(eucMsgOrder);
    }



    /**
    *@Author: hdx
    *@CreateTime: 14:48 2020/3/10
    *@param:
    *@Description:
        更新EucMsg共享数量
    */
    private void updateEucMsgShareCount(EucMsg eucMsg){
        //更新时间
        eucMsg.setUpdateDate(new Date());
        //EUC需求共享单-1
        int count = 0;
        //共享数量不为空
        if(null!=eucMsg.getShareCount()){
            //共享数量
            count = eucMsg.getShareCount();
            //如果等于0则共享数量标记为0
            if(count == 0){
                eucMsg.setShareCount(0);
            }else{
                //如果不等于0则减一
                eucMsg.setShareCount(count-1);
            }
        }
        //更新EUC需求
        mapper.update(eucMsg);
    }


    @Transactional(readOnly = false)
    public Page<EucMsgOrder> dealerOrderList(String dealerCode,String flag,String type, HttpServletRequest request, HttpServletResponse response) throws EucException{
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        EucMsgOrder eucMsgOrder = new EucMsgOrder();
        //标识
        if(EucMsgCode.UNDERTAKE_PROJECT.getLabel().equals(flag)){
            //承接方式
            eucMsgOrder.setUndertake(flag);
            if(EucMsgCode.ORDER_PROCESS_CLOSE.getLabel().equals(type)){
                //工程关闭
                eucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_PROJECT.getLabel());
                //放弃
                eucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_ON_GOINT.getLabel().equals(type)){
                //跟进中
                eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_BIND.getLabel().equals(type)){
                //中标
                eucMsgOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
            }
        }else if(EucMsgCode.UNDERTAKE_RETAIL.getLabel().equals(flag)){
            //承接方式
            eucMsgOrder.setUndertake(flag);
            if(EucMsgCode.ORDER_PROCESS_CLOSE.getLabel().equals(type)){
                //零售关闭
                eucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_RETAIL.getLabel());
                //放弃
                eucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
                //未中标
                eucMsgOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_ON_GOINT.getLabel().equals(type)){
                //跟进中
                eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
                //未中标
                eucMsgOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_BIND.getLabel().equals(type)){
                //中标
                eucMsgOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
            }
        }else if(EucMsgCode.UNDERTAKE_CANCEL.getLabel().equals(flag)){
            //放弃
            eucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
            //未中标
            eucMsgOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
            //承接方式为空
            eucMsgOrder.setNoUndertake("1");
        }
        eucMsgOrder.setContractorCode(dealerCode);
        Page<EucMsgOrder> pageEucMsgOrder = new Page<EucMsgOrder>(request,response);
        pageEucMsgOrder.setOrderBy(" undertake,update_date desc");
        return eucMsgOrderService.findPage(pageEucMsgOrder,eucMsgOrder);
    }

    @Transactional(readOnly = false)
    public Page<EucMsgOrder> dealerOrderList_v1(String dealerCode,String flag,String type, HttpServletRequest request, HttpServletResponse response) throws EucException{
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        EucMsgOrder eucMsgOrder = new EucMsgOrder();
        //标识
        if(EucMsgCode.UNDERTAKE_PROJECT.getLabel().equals(flag)){
            //承接方式
            eucMsgOrder.setUndertake(flag);
            if(EucMsgCode.ORDER_PROCESS_CLOSE.getLabel().equals(type)){
                //工程关闭
                eucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_PROJECT.getLabel());
                //放弃
                eucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_ON_GOINT.getLabel().equals(type)){
                //跟进中
                eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_BIND.getLabel().equals(type)){
                //中标
                eucMsgOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
            }
        }else if(EucMsgCode.UNDERTAKE_RETAIL.getLabel().equals(flag)){
            //承接方式
            eucMsgOrder.setUndertake(flag);
            if(EucMsgCode.ORDER_PROCESS_CLOSE.getLabel().equals(type)){
                //零售关闭
                eucMsgOrder.setAbandonType(EucMsgCode.ABANDONTYPE_RETAIL.getLabel());
                //放弃
                eucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
                //未中标
                eucMsgOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_ON_GOINT.getLabel().equals(type)){
                //跟进中
                eucMsgOrder.setIsAbandon(ProcessCode.NO.getLabel());
                //未中标
                eucMsgOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
            }else if(EucMsgCode.ORDER_PROCESS_BIND.getLabel().equals(type)){
                //中标
                eucMsgOrder.setIsBind(ProcessCode.IS_BIND.getLabel());
            }
        }else if(EucMsgCode.UNDERTAKE_CANCEL.getLabel().equals(flag)){
            //放弃
            eucMsgOrder.setIsAbandon(ProcessCode.YES.getLabel());
            //未中标
            eucMsgOrder.setIsBind(ProcessCode.IS_NOT_BIND.getLabel());
            //承接方式为空
            eucMsgOrder.setNoUndertake("1");
        }
        eucMsgOrder.setContractorCode(dealerCode);
        Page<EucMsgOrder> pageEucMsgOrder = new Page<EucMsgOrder>(request,response);
        pageEucMsgOrder.setOrderBy(" undertake,update_date desc");
        return eucMsgOrderService.findPage(pageEucMsgOrder,eucMsgOrder);
    }


    @Transactional(readOnly = false)
    public void dealerRetailUpload(String orderId,String urls,String dealerCode,String tradeCount) throws EucException{
        //判断dealerCode是否为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        //判断eucMsgId是否为空
        if(StringUtils.isEmpty(orderId)){
            throw new EucException(EucMsgCode.PARAM_ORDERID_EXIST);
        }
        //判断urls是否为空
        if(StringUtils.isEmpty(urls)){
            throw new EucException(EucMsgCode.PARAM_ABANDONTYPE_EXIST);
        }
        //根据查询此需求是否已有经销商抢到
        EucMsgOrder hasEucMsgOrder = eucMsgOrderService.get(orderId);
        //需求不存在
        if(hasEucMsgOrder==null){
            throw new EucException(EucMsgCode.NOT_ORDER_EXIST);
        }
        //判断是否存是这个经销商承接
        if(!dealerCode.equals(hasEucMsgOrder.getContractorCode())){
            throw new EucException(EucMsgCode.ERROR_CHECK_EUC_FROM);
        }
        hasEucMsgOrder.setImageUrl(urls);
        hasEucMsgOrder.setTradeCount(tradeCount);
        hasEucMsgOrder.setUpdateTime(new Date());
        eucMsgOrderMapper.update(hasEucMsgOrder);
        //获取EUC需求单
        EucMsg eucMsg = mapper.getOwnById(hasEucMsgOrder.getEucId());
        //成功录入后回传EUC
        String httpStr = returnEuc(hasEucMsgOrder.getBusinessCode(),EucMsgCode.REVIEWED_BY_CUSTOMER_SERVICE.getLabel(),hasEucMsgOrder.getContractorName(),hasEucMsgOrder.getContractorCode(),"","");
        //承接方式的区分
        EucReturnBody eucReturnBody = new EucReturnBody();
        //EUC状态内容回传
        //商机编码
        eucReturnBody.setBusinessCode(eucMsg.getBusinessCode());
        //商机状态
        eucReturnBody.setStatus(EucMsgCode.REVIEWED_BY_CUSTOMER_SERVICE.getLabel());
        //操作人 - 经销商
        eucReturnBody.setPerson(hasEucMsgOrder.getContractorName());
        //操作人操作人编码 - 经销商编码
        eucReturnBody.setPersonCode(hasEucMsgOrder.getContractorCode());
        //备注
        eucReturnBody.setRemark(EucMsgCode.REVIEWED_BY_CUSTOMER_SERVICE.getLabel());
        //回传EUC
        String code = updateToEuc(eucReturnBody);
        //EUC状态回传日志
        eucSaveLog(code,EucMsgCode.REVIEWED_BY_CUSTOMER_SERVICE.getLabel(),eucReturnBody,eucMsg,hasEucMsgOrder);
    }


    @Transactional(readOnly = false)
    public EucMsgOrder dealerOrderDetails(String orderId) throws EucException{
        //判断orderId是否为空
        if(StringUtils.isEmpty(orderId)){
            throw new EucException(EucMsgCode.PARAM_ORDERID_EXIST);
        }
        EucMsgOrder eucMsgOrder = eucMsgOrderMapper.get(orderId);
        return eucMsgOrder;
    }

    /**
     *@Author: hdx
     *@CreateTime: 16:49 2019/10/8
     *@param:
     * @param dealerCode
     *@Description: 经销商-根据销商编码返回每个状态的订单数量
     */
    @Transactional(readOnly = false)
    public List<Integer>  dealerOrderAllStatus(String dealerCode) throws EucException {
        //dealerCode为空
        if(StringUtils.isEmpty(dealerCode)){
            throw new EucException(EucMsgCode.PARAM_DEALERCODE_EXIST);
        }
        List<Integer> listStatus = null;
        try{
            listStatus = eucMsgOrderMapper.dealerOrderAllStatus(dealerCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return listStatus;
    }

    /**
     * 调用HPS 接口查询是否有申诉权限
     * @param projectCode 项目编码
     * @param msgId 商机编号
     * @param contractorCode
     * @param contractorName
     * @return
     */
    public String getAppealAuth(String projectCode,String msgId,String contractorCode,String contractorName){
       Map<String,Object> param = Maps.newHashMap();
        String eucMsgId = "";
        if(StringUtils.isNotEmpty(msgId)){
            if(msgId.indexOf("EUC")>-1){
                eucMsgId = msgId.substring(3);
            }else{
                eucMsgId = msgId;
            }
        }
        param.put("projectCode",projectCode);
        param.put("msgId",eucMsgId);
        param.put("contractorCode",contractorCode);
        param.put("contractorName",contractorName);
        Map<String,String> header = Maps.newHashMap();
        header.put("ApiAuthorization", MyProject.hpsHeader);
        logger.info("projectCode="+projectCode+"  msgId="+msgId+" contractorCode="+contractorCode+" contractorName="+contractorName);
//        String  httpStr = HttpRequest.post(MyProject.APPEAL_CANBE_APPLY_URL).addHeaders(header).body(param).execute().body();
        String  httpStr = HttpRequest.get(MyProject.APPEAL_CANBE_APPLY_URL).addHeaders(header).form(param).execute().body();
        logger.info("httpStr----------"+httpStr);
        return httpStr;

    }

    /**
     * 获取
     * @return
     */
    public EucMsg getOwnById(String id){
        return mapper.getOwnById(id);
    }

    public static void main(String[] args){
        System.out.println(2<<3);
        System.out.println(2^4);
        File file = new File("D:\\text.txt");
        try{
            FileReader fr =new FileReader(file);

            //将文件读取流包装成缓冲读取流

            BufferedReader br =new BufferedReader(fr);



            String str;

            while ((str = br.readLine())!= null)  //逐行读取数据

            {

                System.out.println(str);

            }

        }catch(Exception e){
            e.getMessage();
        }

    }
}
