package com.jhmis.api.app;

import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.*;
import com.jhmis.modules.wechat.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @description 会议行程相关接口
 * @date: 10:22:37
 * @author:hdx
 */
@RestController
@RequestMapping(value = "/api/app/travel")
public class AppApiTravelController extends BaseController {

    @Autowired
    WxTravelService wxTravelService;

    @Autowired
    WxMessageRecordService wxMessageRecordService;

    @Autowired
    WxMeetingService wxMeetingService;

    @Autowired
    WxMeetingSignupService wxMeetingSignupService;
    protected Logger logger = LoggerFactory.getLogger(AppApiTravelController.class);


    /**
     * @param wxTravel
     * @return
     * @description 会议行程用户添加
     * @method add
     * @date: 10:23:47
     * @author:hdx
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public AjaxJson add(WxTravel wxTravel, HttpServletRequest request, HttpServletResponse response) {
        //获取当前用户信息
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            logger.info("*********会议行程用户添加 /add 接口  401 重新登录**********");
            return AjaxJson.fail("401", "请重新登录！");
        }
        //数据录入验证
        if (wxTravel == null) {
            logger.info("*********会议行程用户添加 /add 接口  wxTravel==null**********");
            return AjaxJson.fail("数据异常,请重新录入");
        }
        //获取当前会议
        WxMeeting m = wxMeetingService.get(wxTravel.getMeetingId());
        if(null==m){
            logger.info("*********会议行程用户添加 /add 接口 无此会议**********");
            return AjaxJson.fail("不存在此会议");
        }
       
        //联系人
        if (StringUtils.isEmpty(wxTravel.getConcat())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getConcat()==null**********");
            return AjaxJson.fail("联系人不能为空");
        }
        //手机号
        if (StringUtils.isEmpty(wxTravel.getMobile())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getMobile()==null**********");
            return AjaxJson.fail("手机号不能为空");
        }
        //证件类型
        if (StringUtils.isEmpty(wxTravel.getCertificateType())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getCertificateType()==null**********");
            return AjaxJson.fail("证件类型不能为空");
        }
        //证件号码
        if (StringUtils.isEmpty(wxTravel.getCertificateNo())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getCertificateNo()==null**********");
            return AjaxJson.fail("证件号码不能为空");
        }
        //交通工具
        if (StringUtils.isEmpty(wxTravel.getStartVehicle())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getStartVehicle()==null**********");
            return AjaxJson.fail("交通工具不能为空");
        }
        //出发日期
        if (wxTravel.getStartTime() == null) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getStartTime()==null**********");
            return AjaxJson.fail("出发日期不能为空");
        }
        //出发站
        if (StringUtils.isEmpty(wxTravel.getStartStand())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getStartStand()==null**********");
            return AjaxJson.fail("出发站不能为空");
        }
        //到达站
        if (StringUtils.isEmpty(wxTravel.getStartStandEnd())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getStartStandEnd()==null**********");
            return AjaxJson.fail("到达站不能为空");
        }
        //车次号
        if (StringUtils.isEmpty(wxTravel.getStartVehicleNo())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getStartVehicleNo()==null**********");
            return AjaxJson.fail("车次号/航班号不能为空");
        }
        //是否接站
        if (StringUtils.isEmpty(wxTravel.getStartReceive())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getStartReceive()==null**********");
            return AjaxJson.fail("是否接站不能为空");
        }
        //交通工具
        if (StringUtils.isEmpty(wxTravel.getReturnVehicle())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getReturnVehicle()==null**********");
            return AjaxJson.fail("返程交通工具不能为空");
        }
        //出发日期
        if (wxTravel.getReturnTime() == null) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getReturnTime()==null**********");
            return AjaxJson.fail("返程出发日期不能为空");
        }
        //出发站
        if (StringUtils.isEmpty(wxTravel.getReturnStand())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getReturnStand()==null**********");
            return AjaxJson.fail("返程出发站不能为空");
        }
        //到达站
        if (StringUtils.isEmpty(wxTravel.getReturnStandEnd())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getReturnStandEnd()==null**********");
            return AjaxJson.fail("返程到达站不能为空");
        }
        //车次号
        if (StringUtils.isEmpty(wxTravel.getReturnVehicleNo())) {
            logger.info("*********会议行程用户添加 /add 接口 wxTravel.getReturnVehicleNo()==null**********");
            return AjaxJson.fail("返程车次号/航班号不能为空");
        }
        //数据查重,如有填写不可修改，只可跟后台联系
        WxTravel checkWxTravel = new WxTravel();
        checkWxTravel.setMeetingId(wxTravel.getMeetingId());
        checkWxTravel.setMeetingSignupId(wxTravel.getMeetingSignupId());
        List<WxTravel> wxTravelList = wxTravelService.findList(checkWxTravel);
        if(wxTravelList!=null && wxTravelList.size()>0){
            //证明无此行程
            logger.info("*********会议行程用户添加 /add 接口 有此行程！**********");
            return AjaxJson.fail("您已填写行程！不可重复提交");
        }
        //验证是否报名
        WxMeetingSignup wxMeetingSignup  = wxMeetingSignupService.get(wxTravel.getMeetingSignupId());
        if(wxMeetingSignup==null){
            //证明无此报名信息
            logger.info("*********会议行程用户添加 /add 接口 无此报名信息！**********");
            return AjaxJson.fail("无此报名信息");
        }
        //人员信息
        wxTravel.setPurchaserAccountId(purchaserAccount.getId());
        //添加时间
        wxTravel.setAddTime(new Date());
        //默认状态出票中
        wxTravel.setStartTicket(Constants.TRAVEL_TICKET_IN);
        wxTravel.setReturnTicket(Constants.TRAVEL_TICKET_IN);
        wxTravel.setIsDel("0");
        wxTravelService.save(wxTravel);
        //如果出票成功则发送短信
        String result="";
		try {
			result = SendMsgApi.SendMessage(m.getOriginator(),m.getName() + ","+wxMeetingSignup.getCompanyName()+"的"+ wxTravel.getConcat() +"提报了行程，请登录系统处理，来自【E企碰碰】");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //记录短信履历
        saveMessageRecord(m.getOriginator(),m.getName() + "会议,"+wxMeetingSignup.getCompanyName()+"的"+ wxTravel.getConcat() +"提报了行程，请登录系统处理，来自【E企碰碰】" ,result);
        return AjaxJson.ok("已成功填写行程");
    }

    /**
     * @description 根据行程id获取行程相关信息
     * @method   find
     * @return  AjaxJson
     * @date:  15:38:28
     * @author:hdx
     */
    @RequestMapping("/get")
    public AjaxJson find(String id){
        //获取当前用户信息
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if (purchaserAccount == null) {
            logger.info("*********会议行程用户添加 /get 接口  401 重新登录**********");
            return AjaxJson.fail("401", "请重新登录！");
        }
        //参数验证
       if(StringUtils.isEmpty(id)){
           //必填参数异常
           logger.info("*********会议行程用户添加 /get 接口 必填参数异常！**********");
           return AjaxJson.fail("必填参数不能为空");
       }
        WxTravel wxTravel = new WxTravel();
        wxTravel.setId(id);
        wxTravel.setIsDel("0");
        List<WxTravel> wxTravelList = wxTravelService.findList(wxTravel);
        if(wxTravelList==null||wxTravelList.size()==0){
            return AjaxJson.fail("没有此行程");
        }
        return AjaxJson.ok(wxTravelList.get(0));
    }

    /**
     * @description 短息履历
     * @method
     * @return
     * @date:  15:28:06
     * @author:hdx
     */
    public void saveMessageRecord(String mobile,String content,String result){
        User user = UserUtils.getUser();
        WxMessageRecord wxMessageRecord = new WxMessageRecord();
        wxMessageRecord.setAddPerson(user.getId());
        wxMessageRecord.setAddTime(new Date());
        wxMessageRecord.setContent(content);
        wxMessageRecord.setResult(result);
        wxMessageRecord.setMobile(mobile);
        wxMessageRecordService.save(wxMessageRecord);
    }

}
