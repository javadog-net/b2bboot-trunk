package com.jhmis.api.old;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.haier.webservices.client.hps.project.HpsApi;
import com.jhmis.common.Enum.*;
import com.jhmis.common.Exception.EucException;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.wsClient.test.main;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.customer.entity.*;
import com.jhmis.modules.customer.mapper.CustomerProjectInfoMapper;
import com.jhmis.modules.customer.mapper.CustomerProjectProductDetailMapper;
import com.jhmis.modules.customer.mapper.CustomerProjectProductMapper;
import com.jhmis.modules.customer.service.*;
import com.jhmis.modules.euc.entity.EucMsg;
import com.jhmis.modules.euc.service.EucMsgService;
import com.jhmis.modules.old.entity.*;
import com.jhmis.modules.old.service.*;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.entity.shopmsgproduct.ShopMsgProduct;
import com.jhmis.modules.process.entity.shopmsgstatus.ShopMsgStatus;
import com.jhmis.modules.process.entity.shopmsgzykc.ShopMsgZykc;
import com.jhmis.modules.process.mapper.dispatcher.ShopMsgDispatcherMapper;
import com.jhmis.modules.process.mapper.shopmsg.ShopMsgMapper;
import com.jhmis.modules.process.mapper.shopmsgorder.ShopMsgCustcodeOrderMapper;
import com.jhmis.modules.process.mapper.shopmsgstatus.ShopMsgStatusMapper;
import com.jhmis.modules.process.mapper.shopmsgzykc.ShopMsgZykcMapper;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
import com.jhmis.modules.process.service.shopmsgproduct.ShopMsgProductService;
import com.jhmis.modules.process.service.shopmsgstatus.ShopMsgStatusService;
import com.jhmis.modules.process.service.shopmsgzykc.ShopMsgZykcService;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.AreaCodeService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.service.GmInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Api(value = "ApiOldController", description = "数据整理")
@RestController
@RequestMapping("/api/old")
public class ApiOldController extends BaseController {

    @Autowired
    OldShopMsgService oldShopMsgService;

    @Autowired
    ShopMsgService shopMsgService;

    @Autowired
    ShopMsgMapper shopMsgMapper;

    @Autowired
    AreaCodeService areaCodeService;

    @Autowired
    ShopMsgProductService shopMsgProductService;

    @Autowired
    OldShopMsgCustcodeOrderService oldShopMsgCustcodeOrderService;

    @Autowired
    ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;

    @Autowired
    ShopMsgCustcodeOrderMapper shopMsgCustcodeOrderMapper;

    @Autowired
    OldShopMsgDispatcherService oldShopMsgDispatcherService;

    @Autowired
    ShopMsgDispatcherService shopMsgDispatcherService;

    @Autowired
    ShopMsgDispatcherMapper shopMsgDispatcherMapper;

    @Autowired
    OldShopMsgZykcService oldShopMsgZykcService;

    @Autowired
    ShopMsgZykcService shopMsgZykcService;

    @Autowired
    ShopMsgZykcMapper shopMsgZykcMapper;

    @Autowired
    ShopMsgStatusService shopMsgStatusService;

    @Autowired
    OldShopMsgStatusService oldShopMsgStatusService;

    @Autowired
    ShopMsgStatusMapper shopMsgStatusMapper;

    @Autowired
    OldShopProjectInfoService oldShopProjectInfoService;

    @Autowired
    CustomerProjectInfoService customerProjectInfoService;

    @Autowired
    CustomerProjectProductService customerProjectProductService;

    @Autowired
    CustomerProjectInfoMapper customerProjectInfoMapper;

    @Autowired
    CustomerProjectProductMapper customerProjectProductMapper;

    @Autowired
    OldShopProjectProductService oldShopProjectProductService;

    @Autowired
    OldShopProjectProductDetailService oldShopProjectProductDetailService;
    @Autowired
    CustomerProjectProductDetailService customerProjectProductDetailService;

    @Autowired
    CustomerProjectProductDetailMapper customerProjectProductDetailMapper;
    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: old_shop_msg整合
     */
    @ApiOperation(notes = "old_shop_msg", httpMethod = "GET", value = "old_shop_msg", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/old_shop_msg", method = RequestMethod.GET)
    public AjaxJson old_shop_msg() {
        try {
            List<OldShopMsg> listOldShopMsg = null;
            try {
                listOldShopMsg = oldShopMsgService.findList(new OldShopMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (OldShopMsg oldShopMsg : listOldShopMsg) {
                ShopMsg shopMsg = new ShopMsg();
                //id
                shopMsg.setId(oldShopMsg.getId());
                //Companyname
                shopMsg.setCompanyName(oldShopMsg.getCompanyname());
                //省份
                AreaCode areaCode_p = areaCodeService.findUniqueByProperty("city_name", oldShopMsg.getProvincename());
                if (areaCode_p != null) {
                    shopMsg.setProvinceId(areaCode_p.getId());
                    shopMsg.setProvinceName(areaCode_p.getCityName());
                }
                //城市
                AreaCode areaCodeCity = new AreaCode();
                areaCodeCity.setCityName(oldShopMsg.getCityname());
                if (areaCode_p != null) {
                    areaCodeCity.setParentId(areaCode_p.getId());
                }
                List<AreaCode> listAreaCode_c = areaCodeService.findList(areaCodeCity);
                if (listAreaCode_c != null && listAreaCode_c.size()>0) {
                    shopMsg.setCityId(listAreaCode_c.get(0).getId());
                    shopMsg.setCityName(listAreaCode_c.get(0).getCityName());
                }
                //地区
                AreaCode a = new AreaCode();
                if (listAreaCode_c != null && listAreaCode_c.size()>0) {
                    a.setParentId(listAreaCode_c.get(0).getId());
                    List<AreaCode> listAreaCode_d = areaCodeService.findList(a);
                    if (listAreaCode_d != null && listAreaCode_d.size() > 0) {
                        shopMsg.setDistricId(listAreaCode_d.get(0).getId());
                        shopMsg.setDistricName(listAreaCode_d.get(0).getCityName());
                    }
                }
                //AddressDetail详细地址
                if (oldShopMsg.getAdress() == null) {
                    shopMsg.setAddressDetail(shopMsg.getProvinceName() + shopMsg.getCityName() + shopMsg.getDistricName());
                } else {
                    shopMsg.setAddressDetail(oldShopMsg.getAdress());
                }
                //address
                shopMsg.setAddress(shopMsg.getProvinceName() + shopMsg.getCityName() + shopMsg.getDistricName());
                //orgid 工贸编码
                shopMsg.setOrgId(oldShopMsg.getOrgid());
                //orgName 工贸名称
                shopMsg.setOrgName(oldShopMsg.getOrgname());
                //channel来源渠道
                String channel = MsgChannelCode.getIndex(oldShopMsg.getChannel());
                if (StringUtils.isBlank(channel)) {
                    shopMsg.setChannel(MsgChannelCode.HAIER_B2B.getIndex());
                } else {
                    shopMsg.setChannel(channel);
                }
                //connect_name 联系人
                shopMsg.setConnectName(oldShopMsg.getKeypersion());
                //mobile 手机号
                shopMsg.setMobile(oldShopMsg.getKeymobile());
                //reserveMobile 400 预留手机号
                shopMsg.setReserveMobile(oldShopMsg.getReservemobile());
                //email 邮箱
                shopMsg.setEmail(oldShopMsg.getEmail());
                //qq
                shopMsg.setQq(oldShopMsg.getQq());
                //depart 部门
                String depart = DepartCode.getIndex(oldShopMsg.getDepart());
                if (StringUtils.isBlank(depart)) {
                    shopMsg.setDepart(DepartCode.DEPART_PURCHASE.getLabel());
                } else {
                    shopMsg.setDepart(depart);
                }
                //monney 金钱
                shopMsg.setMoney(oldShopMsg.getMoney());
                //createDate 创建时间
                shopMsg.setCreateDate(oldShopMsg.getCreateDate());
                //companyOrgCode 组织机构代码
                shopMsg.setCompanyOrgCode(oldShopMsg.getFirstcompanyorgcode());
                //companyOrgName 组织机构名称
                shopMsg.setCompanyOrgName(oldShopMsg.getFirstcompanyorgname());
                //category 分类
                String category = ProcessCode.getIndex(oldShopMsg.getCategory());
                if (StringUtils.isBlank(category)) {
                    shopMsg.setCategory(ProcessCode.MSG_CATEGORY_ENQUIRY.getLabel());
                } else {
                    shopMsg.setCategory(category);
                }
                //projectcode hps状态
                shopMsg.setProjectCode(oldShopMsg.getProjectcode());
                //infcode infcode 之前老漏斗返回
                shopMsg.setInfcode(oldShopMsg.getInfcode());
                //isread 是否已读
                shopMsg.setIsRead(oldShopMsg.getIsread());
                //isdispatch 是否进入派单
                shopMsg.setIsDispatch(oldShopMsg.getIsdispatch());
                //msgFlag
                if ("0".equals(oldShopMsg.getMsgflag())) {
                    //待审核
                    shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_TO_BE_AUDITED.getLabel());
                } else if ("1".equals(oldShopMsg.getMsgflag())) {
                    //需求已提交的标注
                    shopMsg.setMsgFlag(MsgFlagCode.CHOOSES_UNDERTAKE_PROJECT.getLabel());
                } else if ("2".equals(oldShopMsg.getMsgflag())) {
                    //App  审核通过，进入抢单池
                    shopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_APPROVED.getLabel());
                } else if ("4".equals(oldShopMsg.getMsgflag())) {
                    //App  抢单成功
                    shopMsg.setMsgFlag(MsgFlagCode.GRAB_SINGLE_SUCCESS.getLabel());
                } else if ("5".equals(oldShopMsg.getMsgflag())) {
                    //进入派单
                    shopMsg.setMsgFlag(MsgFlagCode.WAITING_LIST.getLabel());
                } else if ("6".equals(oldShopMsg.getMsgflag())) {
                    //已派单
                    if (StringUtils.isNotBlank(oldShopMsg.getPtispass())) {
                        shopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_SENT_ORDER.getLabel());
                    } else {
                        shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_DIRECT_DELIVERY.getLabel());
                    }

                } else if ("9".equals(oldShopMsg.getMsgflag())) {
                    //工程单提交到漏斗
                    shopMsg.setMsgFlag(MsgFlagCode.CHOOSES_UNDERTAKE_PROJECT.getLabel());
                } else if ("10".equals(oldShopMsg.getMsgflag())) {
                    //工程单提交到漏斗
                    shopMsg.setMsgFlag(MsgFlagCode.PLATFORM_AUDIT_PASS.getLabel());
                } else if ("99".equals(oldShopMsg.getMsgflag())) {
                    //工程单提交到漏斗
                    shopMsg.setMsgFlag(MsgFlagCode.DIRECTOR_FAILED_TO_APPROVE.getLabel());
                }
                //pt_ispass 平台审核是否通过
                shopMsg.setPtIspass(oldShopMsg.getPtispass());
                //pt_checker 平台审核人
                shopMsg.setPtChecker(oldShopMsg.getPtchecker());
                //pt_check_date 平台审核时间
                shopMsg.setPtCheckDate(oldShopMsg.getPtchecktime());
                //pt_descp 平台审核备注
                shopMsg.setPtDescp(oldShopMsg.getPtdescp());
                //app_ispass 平台审核是否通过
                shopMsg.setAppIspass(oldShopMsg.getAppispass());
                //app_checker APP 审核人
                shopMsg.setAppChecker(oldShopMsg.getAppchecker());
                //app_check_date app审核时间
                shopMsg.setAppCheckDate(oldShopMsg.getApprotime());
                //appdescp app审核原因
                shopMsg.setAppDescp(oldShopMsg.getAppdescp());
                //appCancleReason 废弃原因
                shopMsg.setAppCancleReason(oldShopMsg.getAppdescp());
                //garbtime 抢单时间
                shopMsg.setGrabDate(oldShopMsg.getGrabtime());
                //custCode 经销商编码
                shopMsg.setCustCode(oldShopMsg.getCustcode());
                //is_back 是否反馈，1是，0否
                shopMsg.setIsBack(oldShopMsg.getIsback());
                //oper_exchange 合作交流内容
                shopMsg.setOperExchange(oldShopMsg.getCooperationandexchange());
                //manager_no海尔接口人编码
                shopMsg.setManagerNo(oldShopMsg.getManagerno());
                //manager_name海尔接口人名称
                shopMsg.setManagerName(oldShopMsg.getManagername());
                //remarks 总监备注
                shopMsg.setRemarks(oldShopMsg.getRemarks());
                //remarks_date 总监备注填写时间
                shopMsg.setRemarksDate(oldShopMsg.getRemarkstime());
                //remarks_person 填写备注的总监编号
                shopMsg.setRemarksPerson(oldShopMsg.getRemarksperson());
                //sendmsg  是否已成功发送短信，（1-已成功发送）
                shopMsg.setSendmsg(oldShopMsg.getSendmsg());
                //unread_msg 客户未读信息条数
                shopMsg.setUnreadMsg(oldShopMsg.getUnreadmsg());
                //memo 采购需求描述
                shopMsg.setMemo(oldShopMsg.getMemo());
                //pro_group  产品组冗余字段
                shopMsg.setProGroup(oldShopMsg.getProduct());
                if(StringUtils.isNotEmpty(oldShopMsg.getProduct())){
                    String code[] = oldShopMsg.getProduct().split(";");
                    String endIndex = "";
                    for (int i = 0; i < code.length; i++) {
                        String index = ProductCode.getIndex(code[i]);
                        String value = ProductCode.getValue(index);
                        endIndex = endIndex + index + ";";
                        ShopMsgProduct shopMsgProduct = new ShopMsgProduct();
                        shopMsgProduct.setMsgId(oldShopMsg.getId());
                        shopMsgProduct.setProductGroupName(code[i]);
                        shopMsgProduct.setProductGroupCode(value);
                        shopMsgProduct.setPurchaseBudget("0");
                        shopMsgProduct.setEstimatedQuantity("0");
                        shopMsgProduct.setBeWisdom("0");
                        try {
                            shopMsgProductService.save(shopMsgProduct);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    shopMsg.setProGroupCode(endIndex.substring(0, endIndex.length() - 1));
                }
                shopMsg.setProjectCode(oldShopMsg.getProjectcode());
                shopMsg.setMemo(oldShopMsg.getMemo());
                shopMsg.setMsgNo(oldShopMsg.getId());
                shopMsg.setCreateDate(oldShopMsg.getCreatetime());
                //nodetag
                shopMsg.setNodetag(oldShopMsg.getNodetag());
                try {
                    shopMsgMapper.insert(shopMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxJson.ok("");
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: old_shop_msg_custorder
     */
    @ApiOperation(notes = "old_shop_msg_custorder", httpMethod = "GET", value = "old_shop_msg_custorder", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/old_shop_msg_custorder", method = RequestMethod.GET)
    public AjaxJson old_shop_msg_custorder() {
        List<OldShopMsgCustcodeOrder> listOldShopMsgCustcodeOrder = oldShopMsgCustcodeOrderService.findList(new OldShopMsgCustcodeOrder());
        try{
            for (OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder :listOldShopMsgCustcodeOrder){
                ShopMsgCustcodeOrder shopMsgCustcodeOrder = new ShopMsgCustcodeOrder();
                //id
                shopMsgCustcodeOrder.setId(oldShopMsgCustcodeOrder.getId());
                //msg_id
                shopMsgCustcodeOrder.setMsgId(oldShopMsgCustcodeOrder.getMsgid());
                //org_id工贸id
                shopMsgCustcodeOrder.setOrgId(oldShopMsgCustcodeOrder.getOrgid());
                //org_name
                shopMsgCustcodeOrder.setOrgName(oldShopMsgCustcodeOrder.getOrgname());
                //company_name
                shopMsgCustcodeOrder.setCompanyName(oldShopMsgCustcodeOrder.getCompanyname());
                //from_source
                if(StringUtils.isNotBlank(oldShopMsgCustcodeOrder.getFromsource())){
                    if("抢单".equals(oldShopMsgCustcodeOrder.getFromsource())){
                        shopMsgCustcodeOrder.setFromSource("0");
                    }else if("派单".equals(oldShopMsgCustcodeOrder.getFromsource())){
                        OldShopMsg oldShopMsg = oldShopMsgService.get(oldShopMsgCustcodeOrder.getMsgid());
                        if(oldShopMsg!=null){
                            if(StringUtils.isNotBlank(oldShopMsg.getPtispass())){
                                shopMsgCustcodeOrder.setFromSource("2");

                            }else{
                                shopMsgCustcodeOrder.setFromSource("1");
                            }
                        }
                    }
                }
                //cancelfalg
                shopMsgCustcodeOrder.setCancelFlag(oldShopMsgCustcodeOrder.getCancelfalg());
                //canceler
                shopMsgCustcodeOrder.setCanceler(oldShopMsgCustcodeOrder.getCanceler());
                //canceltime
                shopMsgCustcodeOrder.setCancelDate(oldShopMsgCustcodeOrder.getCanceltime());
                //cancel_reason
                shopMsgCustcodeOrder.setCancelReason(oldShopMsgCustcodeOrder.getCancelreason());
                //cust_name
                shopMsgCustcodeOrder.setCustName(oldShopMsgCustcodeOrder.getCustname());
                //cust_code
                shopMsgCustcodeOrder.setCustCode(oldShopMsgCustcodeOrder.getCustcode());
                //address
                shopMsgCustcodeOrder.setAddress(oldShopMsgCustcodeOrder.getAddress());
                //under_take
                if(StringUtils.isNotBlank(oldShopMsgCustcodeOrder.getUndertake())){
                    if("工程单".equals(oldShopMsgCustcodeOrder.getUndertake())){
                        shopMsgCustcodeOrder.setUnderTake("0");
                    }else if("零售".equals(oldShopMsgCustcodeOrder.getUndertake())){
                        shopMsgCustcodeOrder.setUnderTake("1");
                    }
                }
                //memo
                shopMsgCustcodeOrder.setMemo(oldShopMsgCustcodeOrder.getMemo());
                //total_count
                shopMsgCustcodeOrder.setTotalCount(oldShopMsgCustcodeOrder.getTotalcount());
                //create_date
                shopMsgCustcodeOrder.setCreateDate(oldShopMsgCustcodeOrder.getCreateDate());
                //is_end
                //shopMsgCustcodeOrder.setIsEnd();
                //is_bind
                shopMsgCustcodeOrder.setIsBind(oldShopMsgCustcodeOrder.getIsbind());
                //timeout_flag
                shopMsgCustcodeOrder.setTimeoutFlag(oldShopMsgCustcodeOrder.getTimeoutflag());
                //timeout_reason
                shopMsgCustcodeOrder.setTimeoutReason(oldShopMsgCustcodeOrder.getTimeoutreason());
                //bind_time
                shopMsgCustcodeOrder.setBindTime(oldShopMsgCustcodeOrder.getBindtime());
                //is_install
                shopMsgCustcodeOrder.setIsInstall(oldShopMsgCustcodeOrder.getIsinstall());
                //install_persion
                shopMsgCustcodeOrder.setInstallPersion(oldShopMsgCustcodeOrder.getInstallpersion());
                //install_date
                shopMsgCustcodeOrder.setInstallDate(oldShopMsgCustcodeOrder.getInstalltime());
                //is_deliver
                shopMsgCustcodeOrder.setIsDeliver(oldShopMsgCustcodeOrder.getIsdeliver());
                //deliver_num
                shopMsgCustcodeOrder.setDeliverNum(oldShopMsgCustcodeOrder.getDelivernum());
                //deliver_time
                shopMsgCustcodeOrder.setDeliverTime(oldShopMsgCustcodeOrder.getDelivertime());
                //is_sign
                shopMsgCustcodeOrder.setIsSign(oldShopMsgCustcodeOrder.getIssign());
                //sign_url
                shopMsgCustcodeOrder.setSignUrl(oldShopMsgCustcodeOrder.getSignurl());
                //sign_date
                shopMsgCustcodeOrder.setSignDate(oldShopMsgCustcodeOrder.getSigntime());
                //is_sunburn
                shopMsgCustcodeOrder.setIsSunburn(oldShopMsgCustcodeOrder.getIssunburn());
                //is_backpass
                shopMsgCustcodeOrder.setIsBackpass(oldShopMsgCustcodeOrder.getIsbackpass());
                //pro_group
                shopMsgCustcodeOrder.setProGroup(oldShopMsgCustcodeOrder.getProduct());
                if(StringUtils.isNotEmpty(oldShopMsgCustcodeOrder.getProduct())){
                    String code[] = oldShopMsgCustcodeOrder.getProduct().split(";");
                    String endIndex = "";
                    for(int i=0; i<code.length; i++){
                        String index = ProductCode.getIndex(code[i]);
                        endIndex = endIndex + index + ";";

                    }
                    shopMsgCustcodeOrder.setProGroupCode(endIndex.substring(0,endIndex.length()-1));
                }
                //create_time
                shopMsgCustcodeOrder.setCreateDate(oldShopMsgCustcodeOrder.getAddtime());
                shopMsgCustcodeOrderMapper.insert(shopMsgCustcodeOrder);
                //shopMsgCustcodeOrderService.save(shopMsgCustcodeOrder);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return AjaxJson.ok();
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: old_shop_msg_dispatcher
     */
    @ApiOperation(notes = "old_shop_msg_dispatcher", httpMethod = "GET", value = "old_shop_msg_dispatcher", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/old_shop_msg_dispatcher", method = RequestMethod.GET)
    public AjaxJson old_shop_msg_dispatcher() {
        List<OldShopMsgDispatcher> listOldShopMsgDispatcher = oldShopMsgDispatcherService.findList(new OldShopMsgDispatcher());
        try{
            for(OldShopMsgDispatcher oldShopMsgDispatcher:listOldShopMsgDispatcher){
                ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
                shopMsgDispatcher.setId(oldShopMsgDispatcher.getId());
                //msg_id
                shopMsgDispatcher.setMsgId(oldShopMsgDispatcher.getMsgid());
                //dispa_flag
                shopMsgDispatcher.setDispaFlag(oldShopMsgDispatcher.getDispaflag());
                //dispa_date
                shopMsgDispatcher.setDispaDate(oldShopMsgDispatcher.getDispatime());
                //dispa_user
                shopMsgDispatcher.setDispaUser(oldShopMsgDispatcher.getDispauser());
                //custcode
                shopMsgDispatcher.setCustcode(oldShopMsgDispatcher.getCustcode());
                //dispa_desc
                shopMsgDispatcher.setDispaDesc(oldShopMsgDispatcher.getDispadesc());
                //create_date
                shopMsgDispatcher.setCreateDate(oldShopMsgDispatcher.getCreateDate());
                //source
                shopMsgDispatcher.setSource(oldShopMsgDispatcher.getSource());
                //is_closed
                shopMsgDispatcher.setIsClosed(oldShopMsgDispatcher.getIsclosed());
                //closer
                shopMsgDispatcher.setCloser(oldShopMsgDispatcher.getCloser());
                //close_date
                shopMsgDispatcher.setCloseDate(oldShopMsgDispatcher.getClosetime());
                //close_reason
                shopMsgDispatcher.setCloseReason(oldShopMsgDispatcher.getClosereason());
                //dispa_type
                OldShopMsg oldShopMsg = oldShopMsgService.get(oldShopMsgDispatcher.getMsgid());
                if(oldShopMsg!=null){
                    if(StringUtils.isNotBlank(oldShopMsg.getPtispass())){
                        shopMsgDispatcher.setDispaType("1");
                    }else{
                        shopMsgDispatcher.setDispaType("0");
                    }
                }
                shopMsgDispatcherMapper.insert(shopMsgDispatcher);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxJson.ok();
    }
    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: shop_msg_zykc
     */
    @ApiOperation(notes = "shop_msg_zykc", httpMethod = "GET", value = "shop_msg_zykc", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/shop_msg_zykc", method = RequestMethod.GET)
    public AjaxJson shop_msg_zykc() {

        List<OldShopMsgZykc> listOldShopMsgZykc = oldShopMsgZykcService.findList(new OldShopMsgZykc());
        try{
            for(OldShopMsgZykc oldShopMsgZykc:listOldShopMsgZykc){
                ShopMsgZykc shopMsgZykc = new ShopMsgZykc();
                shopMsgZykc.setId(oldShopMsgZykc.getId());
                //msg_id
                shopMsgZykc.setMsgId(oldShopMsgZykc.getMsgid());
                //create_date
                shopMsgZykc.setCreateDate(oldShopMsgZykc.getCreateDate());
                //company_name
                shopMsgZykc.setCompanyName(oldShopMsgZykc.getCompanyname());
                //cancel_type
                shopMsgZykc.setCancelType(oldShopMsgZykc.getCancletype());
                //cancel_reson
                shopMsgZykc.setCancelReson(oldShopMsgZykc.getCancelreson());
                //cancel_desc
                shopMsgZykc.setCancelDesc(oldShopMsgZykc.getCanceldesc());
                //cancle_date
                shopMsgZykc.setCancleDate(oldShopMsgZykc.getCancletime());
                //custcode
                shopMsgZykc.setCustcode(oldShopMsgZykc.getCustcode());
                //trade_count
                shopMsgZykc.setTradeCount(oldShopMsgZykc.getTradecount());
                //is_check
                shopMsgZykc.setIsCheck(oldShopMsgZykc.getIscheck());
                //checker
                shopMsgZykc.setChecker(oldShopMsgZykc.getChecker());
                //check_date
                shopMsgZykc.setCheckDate(oldShopMsgZykc.getChecktime());
                //image_url
                shopMsgZykc.setImageUrl(oldShopMsgZykc.getPimageurl());
                //is_closed
                shopMsgZykc.setIsClosed(oldShopMsgZykc.getIsclosed());
                //order_id
                List<OldShopMsgCustcodeOrder> listOldShopMsgCustcodeOrder = oldShopMsgCustcodeOrderService.findList(new OldShopMsgCustcodeOrder().setMsgid(oldShopMsgZykc.getMsgid()));
                if(listOldShopMsgCustcodeOrder!=null && listOldShopMsgCustcodeOrder.size()>0){
                    shopMsgZykc.setOrderId(listOldShopMsgCustcodeOrder.get(0).getId());
                }
                shopMsgZykcMapper.insert(shopMsgZykc);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return AjaxJson.ok();
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: shop_msg_status
     */
    @ApiOperation(notes = "shop_msg_status", httpMethod = "GET", value = "shop_msg_status", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/shop_msg_status", method = RequestMethod.GET)
    public AjaxJson shop_msg_status() {
        List<OldShopMsgStatus> oldListShopMsgStatus = oldShopMsgStatusService.findList(new OldShopMsgStatus());
        for(OldShopMsgStatus oldShopMsgStatus:oldListShopMsgStatus){
            ShopMsgStatus shopMsgStatus = new ShopMsgStatus();
            shopMsgStatus.setId(oldShopMsgStatus.getId());
            //msg_id
            shopMsgStatus.setMsgId(oldShopMsgStatus.getQutoesid());
            //create_date
            shopMsgStatus.setCreateDate(oldShopMsgStatus.getCreatetime());
            //status_name
            shopMsgStatus.setStatusName(oldShopMsgStatus.getStatusname());
            //operator
            shopMsgStatus.setOperator(oldShopMsgStatus.getOperator());
            //content
            shopMsgStatus.setContent(oldShopMsgStatus.getContent());
            shopMsgStatusMapper.insert(shopMsgStatus);
        }
        return AjaxJson.ok();
    }

    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: shop_msg_status
     */
    @ApiOperation(notes = "old_shop_project_info", httpMethod = "GET", value = "old_shop_project_info", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/old_shop_project_info", method = RequestMethod.GET)
    public AjaxJson old_shop_project_info() {
        List<OldShopProjectInfo> listOldShopProjectInfo = oldShopProjectInfoService.findList(new OldShopProjectInfo());
        try{
            for(OldShopProjectInfo oldShopProjectInfo:listOldShopProjectInfo){
                CustomerProjectInfo customerProjectInfo = new CustomerProjectInfo();
                //msg_id
                OldShopMsg os = new OldShopMsg();
                os.setInfcode(oldShopProjectInfo.getInfcode());
                List<OldShopMsg> listOldShopMsg = oldShopMsgService.findList(os);
                if(listOldShopMsg!=null && listOldShopMsg.size()>0){
                    customerProjectInfo.setMsgId(listOldShopMsg.get(0).getId());
                }
                customerProjectInfo.setProjectsSource("GN_PRJ_08");
                //projectcode
                customerProjectInfo.setProjectcode(oldShopProjectInfo.getProjectcode());
                //projectname
                customerProjectInfo.setProjectname(oldShopProjectInfo.getProjectname());
                //companyname
                customerProjectInfo.setCompanyname(oldShopProjectInfo.getCompanyname());
                //companysize
                customerProjectInfo.setCompanysize(oldShopProjectInfo.getCompanysize());
                //contact
                customerProjectInfo.setContact(oldShopProjectInfo.getContact());
                //contmobile
                customerProjectInfo.setContmobile(oldShopProjectInfo.getContmobile());
                //product
                customerProjectInfo.setProduct(oldShopProjectInfo.getProduct());
                //count
                customerProjectInfo.setCount(oldShopProjectInfo.getCount());
                //bindtime
                customerProjectInfo.setBindtime(oldShopProjectInfo.getBindtime());
                //bindamount
                customerProjectInfo.setBindamount(oldShopProjectInfo.getBindamount());
                //indus
                customerProjectInfo.setIndus(oldShopProjectInfo.getIndus());
                //address
                customerProjectInfo.setAddress(oldShopProjectInfo.getAddress());
                //orgid
                customerProjectInfo.setOrgid(oldShopProjectInfo.getOrgid());
                //orgname
                customerProjectInfo.setOrgname(oldShopProjectInfo.getOrgname());
                //orgIdcharge
                customerProjectInfo.setOrgidcharge(oldShopProjectInfo.getOrgidcharge());
                //distributor
                customerProjectInfo.setDistributor(oldShopProjectInfo.getDistributor());
                //orderstate
                customerProjectInfo.setOrderstate(oldShopProjectInfo.getOrderstate());
                //projectstate
                customerProjectInfo.setProjectstate(oldShopProjectInfo.getProjectstate());
                //losstime
                customerProjectInfo.setLosstime(oldShopProjectInfo.getLosstime());
                //nodename
                customerProjectInfo.setNodename(oldShopProjectInfo.getNodename());
                //nodestate
                customerProjectInfo.setNodestate(oldShopProjectInfo.getNodestate());
                //causeloss
                customerProjectInfo.setCauseloss(oldShopProjectInfo.getCauseloss());
                //channel
                customerProjectInfo.setChannel(oldShopProjectInfo.getChannel());
                //descp
                customerProjectInfo.setDescp(oldShopProjectInfo.getDescp());
                //infcode
                customerProjectInfo.setInfcode(oldShopProjectInfo.getInfcode());
                //cancelflag
                customerProjectInfo.setCancelflag(oldShopProjectInfo.getCancelflag());
                //cancelreason
                customerProjectInfo.setCancelreason(oldShopProjectInfo.getCancelreason());
                //cancelperson
                customerProjectInfo.setCancelperson(oldShopProjectInfo.getCancelperson());
                //canceltime
                customerProjectInfo.setCanceltime(oldShopProjectInfo.getCanceltime());
                //bz1
                customerProjectInfo.setBz1(oldShopProjectInfo.getBz1());
                //bz2
                customerProjectInfo.setBz2(oldShopProjectInfo.getBz2());
                //creater
                customerProjectInfo.setCreater(oldShopProjectInfo.getCreater());
                //createDate
                customerProjectInfo.setCreateDate(oldShopProjectInfo.getCreateDate());
                //lastUpDater
                customerProjectInfo.setLastupdater(oldShopProjectInfo.getLastupdater());
                //lastUpDate
                customerProjectInfo.setLastupdate(oldShopProjectInfo.getLastupdate());
                //allmoney
                customerProjectInfo.setAllmoney(oldShopProjectInfo.getAllmoney());
                //custContact
                customerProjectInfo.setCustcontact(oldShopProjectInfo.getCustcontact());
                //isValid
                customerProjectInfo.setIsvalid(oldShopProjectInfo.getIsvalid());
                //partyPrjCompany
                customerProjectInfo.setPartyprjcompany(oldShopProjectInfo.getPartyprjcompany());
                //tenderDate
                customerProjectInfo.setTenderdate(oldShopProjectInfo.getTenderdate());
                //yjSignDate
                customerProjectInfo.setYjsigndate(oldShopProjectInfo.getYjsigndate());
                //bidProgress
                customerProjectInfo.setBidprogress(oldShopProjectInfo.getBidprogress());
                //signDate
                customerProjectInfo.setSigndate(oldShopProjectInfo.getSigndate());
                //constructionState
                customerProjectInfo.setConstructionstate(oldShopProjectInfo.getConstructionstate());
                //projectType
                customerProjectInfo.setProjecttype(oldShopProjectInfo.getProjecttype());
                //resourceFlag
                customerProjectInfo.setResourceflag(oldShopProjectInfo.getResourceflag());
                //isNewCust
                customerProjectInfo.setIsnewcust(oldShopProjectInfo.getIsnewcust());
                //isNewUser
                customerProjectInfo.setIsnewuser(oldShopProjectInfo.getIsnewuser());
                //projectSourse
                customerProjectInfo.setProjectsourse(oldShopProjectInfo.getProjectsourse());
                //isBidLoss
                customerProjectInfo.setIsbidloss(oldShopProjectInfo.getIsbidloss());
                //statusFX
                customerProjectInfo.setStatusfx(oldShopProjectInfo.getStatusfx());
                //bidLossMoney
                customerProjectInfo.setBidlossmoney(oldShopProjectInfo.getBidlossmoney());
                //isFedbatch
                customerProjectInfo.setIsfedbatch(oldShopProjectInfo.getIsfedbatch());
                //userGroup
                customerProjectInfo.setUsergroup(oldShopProjectInfo.getUsergroup());
                //userType
                customerProjectInfo.setUsertype(oldShopProjectInfo.getUsertype());
                //expr1
                customerProjectInfo.setExpr1(oldShopProjectInfo.getExpr1());
                //expr2
                customerProjectInfo.setExpr2(oldShopProjectInfo.getExpr2());
                //iSZLSP
                customerProjectInfo.setIszlsp(oldShopProjectInfo.getIszlsp());
                //expr5
                customerProjectInfo.setExpr5(oldShopProjectInfo.getExpr5());
                //expr6
                customerProjectInfo.setExpr6(oldShopProjectInfo.getExpr6());
                //oldProjectCode
                customerProjectInfo.setOldprojectcode(oldShopProjectInfo.getOldprojectcode());
                //r4savetime
                customerProjectInfo.setR4savetime(oldShopProjectInfo.getR4savetime());
                //signProgress
                customerProjectInfo.setSignprogress(oldShopProjectInfo.getSignprogress());
                customerProjectInfoMapper.insert(customerProjectInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxJson.ok();
    }



    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: shop_msg_status
     */
    @ApiOperation(notes = "old_shop_project_product", httpMethod = "GET", value = "old_shop_project_product", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/old_shop_project_product", method = RequestMethod.GET)
    public AjaxJson old_shop_project_product() {
        List<OldShopProjectProduct> listOldShopProjectProduct = oldShopProjectProductService.findList(new OldShopProjectProduct());
        try {
            for(OldShopProjectProduct oldShopProjectProduct:listOldShopProjectProduct){
                CustomerProjectProduct customerProjectProduct = new CustomerProjectProduct();
                //projectcode
                customerProjectProduct.setProjectcode(oldShopProjectProduct.getProjectcode());
                //name
                customerProjectProduct.setName(oldShopProjectProduct.getName());
                //count
                customerProjectProduct.setCount(oldShopProjectProduct.getCount());
                //money
                customerProjectProduct.setMoney(oldShopProjectProduct.getMoney());
                //address
                customerProjectProduct.setAddress(oldShopProjectProduct.getAddress());
                //code
                customerProjectProduct.setCode(oldShopProjectProduct.getCode());
                customerProjectProductMapper.insert(customerProjectProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.ok();
    }



    /**
     *@Author: hdx
     *@CreateTime: 2019年11月7日 09:47:59
     *@Description: shop_msg_status
     */
    @ApiOperation(notes = "old_shop_project_product_detail", httpMethod = "GET", value = "old_shop_project_product_detail", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/old_shop_project_product_detail", method = RequestMethod.GET)
    public AjaxJson old_shop_project_product_detail() {
        List<OldShopProjectProductDetail> listOldShopProjectProductDetail = oldShopProjectProductDetailService.findList(new OldShopProjectProductDetail());

        try {
            for(OldShopProjectProductDetail oldShopProjectProductDetail:listOldShopProjectProductDetail){
                CustomerProjectProductDetail customerProjectProductDetail = new CustomerProjectProductDetail();
                //id
                customerProjectProductDetail.setId(oldShopProjectProductDetail.getId());
                //projectCode
                customerProjectProductDetail.setProjectcode(oldShopProjectProductDetail.getProjectcode());
                //currState
                customerProjectProductDetail.setCurrstate(oldShopProjectProductDetail.getCurrstate());
                //amount
                customerProjectProductDetail.setAmount(oldShopProjectProductDetail.getAmount());
                //cjPrice
                customerProjectProductDetail.setCjprice(oldShopProjectProductDetail.getCjprice());
                //cjPriceSum
                customerProjectProductDetail.setCjpricesum(oldShopProjectProductDetail.getCjpricesum());
                //price
                customerProjectProductDetail.setPrice(oldShopProjectProductDetail.getPrice());
                //priceSum
                customerProjectProductDetail.setPricesum(oldShopProjectProductDetail.getPricesum());
                //bargainPrice
                customerProjectProductDetail.setBargainprice(oldShopProjectProductDetail.getBargainprice());
                //bargainPriceSum
                customerProjectProductDetail.setBargainpricesum(oldShopProjectProductDetail.getBargainpricesum());
                //stepFLRebat
                customerProjectProductDetail.setStepflrebat(oldShopProjectProductDetail.getStepflrebat());
                //productID
                customerProjectProductDetail.setProductid(oldShopProjectProductDetail.getProductid());
                //productName
                customerProjectProductDetail.setProductname(oldShopProjectProductDetail.getProductname());
                //classCode
                customerProjectProductDetail.setClasscode(oldShopProjectProductDetail.getClasscode());
                //className
                customerProjectProductDetail.setClassname(oldShopProjectProductDetail.getClassname());
                //modelCode
                customerProjectProductDetail.setModelcode(oldShopProjectProductDetail.getModelcode());
                //modelName
                customerProjectProductDetail.setModelname(oldShopProjectProductDetail.getModelname());
                //sendBccState
                customerProjectProductDetail.setSendbccstate(oldShopProjectProductDetail.getSendbccstate());
                //bCCFXPrice
                customerProjectProductDetail.setBccfxprice(oldShopProjectProductDetail.getBccfxprice());
                //bCCBLPrice
                customerProjectProductDetail.setBccblprice(oldShopProjectProductDetail.getBccblprice());
                //bCCBBPrice
                customerProjectProductDetail.setBccbbprice(oldShopProjectProductDetail.getBccbbprice());
                //pLPrice
                customerProjectProductDetail.setPlprice(oldShopProjectProductDetail.getPlprice());
                //fXPrice
                customerProjectProductDetail.setFxprice(oldShopProjectProductDetail.getFxprice());
                //bLPrice
                customerProjectProductDetail.setBlprice(oldShopProjectProductDetail.getBlprice());
                //bBPrice
                customerProjectProductDetail.setBbprice(oldShopProjectProductDetail.getBbprice());
                //hTType
                customerProjectProductDetail.setHttype(oldShopProjectProductDetail.getHttype());
                //sysDateTime
                customerProjectProductDetail.setSysdatetime(oldShopProjectProductDetail.getSysdatetime());
                //isValid
                customerProjectProductDetail.setIsvalid(oldShopProjectProductDetail.getIsvalid());
                //ISPROCHANGE
                customerProjectProductDetail.setIsprochange(oldShopProjectProductDetail.getIsprochange());
                //modlebllv
                customerProjectProductDetail.setModlebllv(oldShopProjectProductDetail.getModlebllv());
                //xQQty
                customerProjectProductDetail.setXqqty(oldShopProjectProductDetail.getXqqty());
                //companyname
                customerProjectProductDetail.setCompanyname(oldShopProjectProductDetail.getCompanyname());
                //manager
                customerProjectProductDetail.setManager(oldShopProjectProductDetail.getManager());
                //mobilephone
                customerProjectProductDetail.setMobilephone(oldShopProjectProductDetail.getMobilephone());
                //relation
                customerProjectProductDetail.setRelation(oldShopProjectProductDetail.getRelation());
                //tzsPg
                customerProjectProductDetail.setTzspg(oldShopProjectProductDetail.getTzspg());
                //expr1
                customerProjectProductDetail.setExpr1(oldShopProjectProductDetail.getExpr1());
                //expr2
                customerProjectProductDetail.setExpr2(oldShopProjectProductDetail.getExpr2());
                //expr3
                customerProjectProductDetail.setExpr3(oldShopProjectProductDetail.getExpr3());
                //expr4
                customerProjectProductDetail.setExpr4(oldShopProjectProductDetail.getExpr4());
                //addressDetail
                customerProjectProductDetail.setAddressdetail(oldShopProjectProductDetail.getAddressdetail());
                customerProjectProductDetailMapper.insert(customerProjectProductDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxJson.ok();
    }


}
