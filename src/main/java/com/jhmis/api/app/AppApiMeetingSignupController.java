package com.jhmis.api.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.jhmis.modules.sys.entity.GmCity;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.service.GmCityService;
import com.jhmis.modules.sys.service.GmInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.service.WxMeetingService;
import com.jhmis.modules.wechat.service.WxMeetingSignupService;


/**
 * <p>
 * Title: ApiMeetingSignupController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author tc
 * @date 2018年12月14日 下午2:37:01
 */
@RestController
@RequestMapping("/api/app/meetingSignup")
public class AppApiMeetingSignupController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(AppApiMeetingSignupController.class);

	@Resource
	private WxMeetingSignupService wxMeetingSignupService;
	@Resource
	private WxMeetingService wxMeetingService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private GmCityService gmCityService;
	@Autowired
	private GmInfoService gmInfoService;


	/**
	 * 保存会议报名表
	 */
	@RequestMapping(value = "save")
	public AjaxJson save(WxMeetingSignup wxMeetingSignup) throws Exception {
		wxMeetingSignupService.save(wxMeetingSignup);
		return AjaxJson.ok();
	}

	/**
	 * @Title: meetingStatus
	 * @Description: TODO
	 * @param pruchaser_accountid
	 * @param status
	 *            如果status是0，则要把状态是2的也查询出来。
	 *            如果status是1，则要把状态是4的也查询出来。
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2018年12月14日上午10:55:14
	 */
	@RequestMapping(value = "/meetingstatus")
	public AjaxJson meetingStatus(@RequestParam(value = "status", required = true) String status) {
		System.out.println("status is"+status);
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		String purchaseraccountid = purchaserAccount.getId();
		List<WxMeetingSignup> list = wxMeetingSignupService.findmeetingsignupstatus(status, purchaseraccountid);

		if (status.equals("0")) {
			System.out.println("status是1");
			List<WxMeetingSignup> list2 = wxMeetingSignupService.findmeetingsignupstatus("2", purchaseraccountid);
			list.addAll(list2);
			System.out.println(list.size()+"list.size()");
		}
		if (status.equals("1")) {
			System.out.println("status是1");
			List<WxMeetingSignup> list2 = wxMeetingSignupService.findmeetingsignupstatus("4", purchaseraccountid);
			list.addAll(list2);
			System.out.println(list.size()+"list.size()");
		}
		return AjaxJson.ok(list);
	}

	/**
	 * @Title: signupmeeting
	 * @Description: TODO 在线报名 修改Depart
	 * @param purchaseraccountid
	 * @param departname
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2018年12月15日下午3:44:50
	 */
	@RequestMapping(value = "/signupmeeting")
	public AjaxJson signupmeeting(@RequestParam(value = "meetingid", required = true) String meetingid,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "nickName", required = false,defaultValue="") String nickName,
			@RequestParam(value = "realName", required = false,defaultValue="") String realName,
			@RequestParam(value = "companyName", required = true) String companyName,
			@RequestParam(value = "departName", required = false,defaultValue="") String departName,
			@RequestParam(value = "address", required = false,defaultValue="") String address,
			@RequestParam(value = "industryName", required = true) String industryName,
			@RequestParam(value = "email", required = true) String email,
		    @RequestParam(value = "provinceId", required = false,defaultValue="") String provinceId,
		    @RequestParam(value = "cityId", required = false,defaultValue="") String cityId
								  ) {
		logger.info("provinceId=" + provinceId + "  cityId=" + cityId);
		System.out.println("报名信息" + "meetingid" + meetingid + "phone" + phone + "nickName" + nickName + "companyName"
				+ companyName + "departName" + departName + "address" + address + "industryName" + industryName
				+ "email" + email+"realName"+realName);
		try {
		
		if(departName==null||departName.equals("")||departName.equals("undefined")){
			departName="";
		}
		if(address==null||address.equals("")||address.equals("undefined")){
			address="";
		}
		if(nickName==null||nickName.equals("")||nickName.equals("undefined")){
			nickName="";
		}
		if(realName==null||realName.equals("")||realName.equals("undefined")){
			realName="";
		}
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		Purchaser p = purchaserService.get(purchaserAccount.getPurchaserId());
		String purchaseraccountid = purchaserAccount.getId();
    	if(StringUtils.isBlank(nickName)){
            nickName=realName;
        }
    	/*
        if(StringUtils.isBlank(phone)){
            return AjaxJson.fail("手机不能为空");
        }
        if(StringUtils.isNotBlank(phone)){
            if(!RegValidators.isMobile(phone)){
                return AjaxJson.fail("手机格式不正确");
            }
        }
        if(StringUtils.isNotBlank(email)){
            if(!RegValidators.isEmail(email)){
                return AjaxJson.fail("邮箱格式不正确");
            }
        }*/
		logger.info("/signupmeeting开始");
		WxMeetingSignup wxMeetingSignup = new WxMeetingSignup();
		List<WxMeetingSignup> listsign = null;
		try {
			listsign = wxMeetingSignupService.findMeetingByIdAndIdtwo(purchaseraccountid, meetingid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		logger.info("listsign" + listsign);
		System.out.println("listsign" + listsign);

		if (listsign != null && listsign.size() != 0 && !"2".equals(listsign.get(0).getSignStatus())) {
			logger.info("你已报名，请勿重复报名！");
			return AjaxJson.fail("你已报名，请勿重复报名！");
		}
		// 判断 是 2 的状态 拒绝状态
		// wxMeetingSignup = new WxMeetingSignup();
		if (listsign != null && listsign.size() != 0 && "2".equals(listsign.get(0).getSignStatus())) {
			wxMeetingSignup.setId(listsign.get(0).getId());
			logger.info("状态是2拒绝的");
		}
		//城市匹配工贸
		if(StringUtils.isNotBlank(provinceId) && StringUtils.isNotBlank(cityId)){
			logger.info("根据城市匹配工贸  provinceId=" + provinceId + "  cityId=" + cityId);
			//同时存在的时候进行匹配
			GmCity gmCity = new GmCity();
			gmCity.setCityId(cityId);
			List<GmCity> listGmCity = gmCityService.findList(gmCity);
			if(listGmCity!=null && listGmCity.size()>0){
				logger.info("*******listGmCity 不为空*******");
				//获取此工贸地址对应
				GmCity gc = listGmCity.get(0);
				//通过工贸获取具体工贸名称
				GmInfo gmInfo = new GmInfo();
				gmInfo.setBranchCode(gc.getGmId());
				List<GmInfo> listGmInfo = gmInfoService.findList(gmInfo);
				if(listGmInfo!=null && listGmInfo.size()>0){
					logger.info("*******listGmCity 不为空*******");
					//通过工贸id获取工贸信息
					GmInfo gi = listGmInfo.get(0);
					wxMeetingSignup.setProvinceId(provinceId);
					wxMeetingSignup.setCityId(cityId);
					wxMeetingSignup.setOrgid(gi.getGmCode());
					wxMeetingSignup.setOrgname(gi.getGmName());
				}else{
					wxMeetingSignup.setProvinceId(provinceId);
					wxMeetingSignup.setCityId(cityId);
				}
			}
		}
		wxMeetingSignup.setUserId(purchaseraccountid);
		wxMeetingSignup.setMeetingId(meetingid);
		wxMeetingSignup.setSignStatus("0");
		wxMeetingSignup.setSignTime(new Date());
		wxMeetingSignup.setSignWay("1");
		wxMeetingSignup.setAddress(address);
		wxMeetingSignup.setCompanyName(companyName);
		wxMeetingSignup.setDepartName(departName);
		wxMeetingSignup.setEmail(email);
		wxMeetingSignup.setIndustryName(industryName);
		wxMeetingSignup.setMobile(phone);
		wxMeetingSignup.setRealName(nickName);
		if (StringUtils.isBlank(purchaserAccount.getNickName())
				|| StringUtils.isBlank(purchaserAccount.getWxindustryname())
				|| StringUtils.isBlank(p.getCompanyName())) {
			purchaserAccount.setNickName(nickName);
			purchaserAccount.setDepartName(departName);
			purchaserAccount.setWxindustryname(industryName);
			p.setCompanyName(companyName);
			p.setDetailAddress(address);
			p.setDepart(departName);
			try {
				wxMeetingSignupService.save(wxMeetingSignup);
				wxMeetingService.addsignupnum(meetingid);
				purchaserService.updatedapartandcompany(p);
				purchaserAccountService.updateAccountInfo(purchaserAccount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				wxMeetingSignupService.save(wxMeetingSignup);
				wxMeetingService.addsignupnum(meetingid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxJson.ok("报名成功，等待管理员的审核！");
	}

	/**
	 * @Title: checkmeeting
	 * @Description: TODO 查询报名会议状态
	 * @param meetingid
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年2月15日上午10:12:56
	 */
	@RequestMapping(value = "/checkmeeting")
	public AjaxJson checkmeeting(@RequestParam(value = "meetingid", required = true) String meetingid) {
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
		if (purchaserAccount == null) {
			return AjaxJson.fail("401", "请重新登录！");
		}
		List<WxMeetingSignup> listsign = wxMeetingSignupService.findMeetingByIdAndIdtwo(purchaserAccount.getId(),
				meetingid);
		WxMeetingSignup wxMeetingSignup = new WxMeetingSignup();
		logger.info("listsign" + listsign);
		System.out.println("listsign" + listsign);
		if (listsign != null && listsign.size() != 0) {
			wxMeetingSignup = listsign.get(0);
			return AjaxJson.ok(wxMeetingSignup);
		}
		return AjaxJson.ok("00");
	}
}