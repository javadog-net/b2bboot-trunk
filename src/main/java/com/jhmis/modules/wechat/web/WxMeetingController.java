/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.wechat.entity.CmsActivity;
import com.jhmis.modules.wechat.entity.CmsPrize;
import com.jhmis.modules.wechat.entity.CmsSignupDraw;
import com.jhmis.modules.wechat.entity.WxGroup;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.entity.WxMeetingDirect;
import com.jhmis.modules.wechat.entity.WxMeetingFile;
import com.jhmis.modules.wechat.entity.WxMeetingSchedule;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.entity.WxTravel;
import com.jhmis.modules.wechat.service.CmsActivityService;
import com.jhmis.modules.wechat.service.CmsPrizeService;
import com.jhmis.modules.wechat.service.CmsSignupDrawService;
import com.jhmis.modules.wechat.service.CmsWinPrizeService;
import com.jhmis.modules.wechat.service.WxGroupService;
import com.jhmis.modules.wechat.service.WxGroupUserService;
import com.jhmis.modules.wechat.service.WxMeetingDirectService;
import com.jhmis.modules.wechat.service.WxMeetingFileService;
import com.jhmis.modules.wechat.service.WxMeetingScheduleService;
import com.jhmis.modules.wechat.service.WxMeetingService;
import com.jhmis.modules.wechat.service.WxMeetingSignupService;
import com.jhmis.modules.wechat.service.WxTravelService;

/**
 * 会议表Controller
 *
 * @author lvyangzhuo
 * @version 2018-11-23
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxMeeting")
public class WxMeetingController extends BaseController {

	@Autowired
	private WxMeetingService wxMeetingService;
	@Autowired
	private WxMeetingDirectService wxMeetingDirectService;
	@Autowired
	private WxMeetingSignupService wxMeetingSignupService;
	@Autowired
	private WxMeetingScheduleService wxMeetingScheduleService;
	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private WxGroupService wxGroupService;
	@Autowired
	private WxMeetingFileService wxMeetingFileService;
	@Autowired
	private WxTravelService wxTravelService;
	@Autowired
	private WxGroupUserService wxGroupUserService;
	@Autowired
	private CmsActivityService cmsActivityService;
	@Autowired
	private CmsPrizeService cmsPrizeService;
	@Autowired
	private CmsSignupDrawService cmsSignupDrawService;
	@Autowired
	private CmsWinPrizeService cmsWinPrizeService;

	@ModelAttribute
	public WxMeeting get(@RequestParam(required = false) String id) {
		WxMeeting entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxMeetingService.get(id);
		}
		if (entity == null) {
			entity = new WxMeeting();
		}
		return entity;
	}

	/**
	 * 会议表列表页面
	 */
	@RequiresPermissions("wechat:wxMeeting:list")
	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/wechat/meeting/wxMeetingList";
	}

	/**
	 * 会议表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeeting:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMeeting wxMeeting, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<WxMeeting> p = new Page<WxMeeting>(request, response);
		 p.setOrderBy("start_time desc");
		Page<WxMeeting> page = wxMeetingService.findPage(p, wxMeeting);
		return getBootstrapData(page);

	}

	/**
	 * @param wxMeeting 此方法用于 绑定banner链接 
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("wechat:wxMeeting:list")
	@RequestMapping(value = "/bannerlist")
	@ResponseBody
	public AjaxJson bannerlist(WxMeeting wxMeeting) throws Exception {
		List<WxMeeting> wxMeetingList = wxMeetingService.findList(wxMeeting);
		return AjaxJson.ok(wxMeetingList);
	}

	
	
	/**
	 * 查看，增加，编辑会议表表单页面
	 */
	@RequiresPermissions(value = { "wechat:wxMeeting:view", "wechat:wxMeeting:add",
			"wechat:wxMeeting:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMeeting wxMeeting, Model model) {
		model.addAttribute("wxMeeting", wxMeeting);
		return "modules/wechat/meeting/wxMeetingForm";
	}

	/**
	 * 生成二维码页面
	 */
	@RequiresPermissions(value = { "wechat:wxMeeting:view", "wechat:wxMeeting:add",
			"wechat:wxMeeting:edit" }, logical = Logical.OR)
	@RequestMapping(value = "qdcode")
	public String qdcode(WxMeeting wxMeeting, Model model) {
		model.addAttribute("wxMeeting", wxMeeting);
		return "modules/wechat/meeting/wxMeetingQdCode";
	}
	
	@ResponseBody
	@RequiresPermissions(value = { "wechat:wxMeeting:add", "wechat:wxMeeting:edit" }, logical = Logical.OR)
	@RequestMapping(value = "/epLoginAccount", method = RequestMethod.POST)
	public AjaxJson epLoginAccount(String account) throws Exception {

		AjaxJson j = new AjaxJson();
		// 查询此创建会议用户是否是企业购会员
		// PurchaserAccount purchaserAccount =
		// purchaserAccountService.getByName(account);
		PurchaserAccount purchaserAccount = purchaserAccountService.findUniqueByProperty("login_name", account);
		if (purchaserAccount == null) {
			j.setSuccess(false);
			j.setMsg("用户非企业购用户！");
			j.setResult("");
		} else {
			j.setSuccess(true);
			j.setMsg("账户可以使用");
			j.setResult(purchaserAccount.getLoginName());
		}
		return j;
	}

	/**
	 * 保存会议表
	 */
	@ResponseBody
	@RequiresPermissions(value = { "wechat:wxMeeting:add", "wechat:wxMeeting:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(WxMeeting wxMeeting, Model model, RedirectAttributes redirectAttributes) throws Exception {
		wxMeeting.setConferenceLength(wxMeetingService.differentDays(wxMeeting.getStartTime(), wxMeeting.getEndTime()));
		AjaxJson j = new AjaxJson();
		/*
		 * if (!beanValidator(model, wxMeeting)) { j.setSuccess(false);
		 * j.setMsg("非法参数！"); return j; }
		 */
		// 查询此创建会议用户是否是企业购会员
		PurchaserAccount purchaserAccount = purchaserAccountService.findUniqueByProperty("login_name",
				wxMeeting.getOriginator());
		/*
		 * if(purchaserAccount==null){ j.setSuccess(false);
		 * j.setMsg("用户非企业购用户,请先登录E企碰碰小程序！"); }
		 */
		// 添加默认状态
		// 判断是否是修改会议d=Double.parseDouble(s);
		// String[] split = wxMeeting.getMap().split(",");
		// GPS g = testmap.bd09_To_Gcj02(Double.parseDouble(split[0]),
		// Double.parseDouble(split[1]));
		// 新增或编辑表单保存
		// String a = g.getLat() + "," + g.getLon();
		// wxMeeting.setMap(a);
		wxMeeting.setAddTime(new Date());
		wxMeeting.setIsDel("0");
		wxMeetingService.save(wxMeeting, purchaserAccount);// 保存

		j.setSuccess(true);
		j.setMsg("保存会议表成功");
		return j;
	}

	/**
	 * 删除会议表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeeting:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMeeting wxMeeting, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxMeetingService.delete(wxMeeting);
		j.setMsg("删除会议表成功");
		return j;
	}

	/**
	 * 批量删除会议表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeeting:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			wxMeetingService.delete(wxMeetingService.get(id));
			System.out.println("meeting删除成功");
			WxMeetingDirect wxMeetingDirect = new WxMeetingDirect();
			wxMeetingDirect.setMeetingId(id);
			wxMeetingDirectService.deleteAll(wxMeetingDirectService.findList(wxMeetingDirect));
			System.out.println("direct删除成功");
			WxMeetingSignup wxMeetingSignup = new WxMeetingSignup();
			wxMeetingSignup.setMeetingId(id);
			wxMeetingSignupService.deleteAll(wxMeetingSignupService.findList(wxMeetingSignup));
			System.out.println("signup删除成功");
			WxMeetingSchedule wxMeetingSchedule = new WxMeetingSchedule();
			wxMeetingSchedule.setMeetingId(id);
			wxMeetingScheduleService.deleteAll(wxMeetingScheduleService.findList(wxMeetingSchedule));
			System.out.println("schedule删除成功");
			WxGroup wxGroup = new WxGroup();
			wxGroup.setSource(id);
			List<WxGroup> list = wxGroupService.findList(wxGroup);
			wxGroupService.deleteAll(list);
			System.out.println("wxGroup删除成功");
			WxGroupUser wxGroupUser = new WxGroupUser();
			if (null != list && list.size() > 0) {
				wxGroupUser.setGroupId(list.get(0).getId());
				wxGroupUserService.deleteAll(wxGroupUserService.findList(wxGroupUser));
				System.out.println("wxgroupuser删除成功");
			} 
			
			WxMeetingFile wxMeetingFile=new WxMeetingFile();
			wxMeetingFile.setMeetingId(id);
			wxMeetingFileService.deleteAll(wxMeetingFileService.findList(wxMeetingFile));
			System.out.println("wxmeetingfile删除成功");

			WxTravel wxTravel=new WxTravel();
			wxTravel.setMeetingId(id);
			wxTravelService.deleteAll(wxTravelService.findList(wxTravel));
			System.out.println("Wxtravel 删除成功");
			CmsActivity cmsActivity=new CmsActivity();
			cmsActivity.setMeetingId(id);
			cmsActivityService.deleteAll(cmsActivityService.findList(cmsActivity));
			System.out.println("cmsactivity删除成功");
			CmsPrize cmsprize=new CmsPrize();
			cmsprize.setMeetingId(id);
			cmsPrizeService.deleteAll(cmsPrizeService.findList(cmsprize));
			System.out.println("cmsprize删除成功");
			CmsSignupDraw cmsDrawSignup=new CmsSignupDraw();
			cmsDrawSignup.setMeetingId(id);
			cmsSignupDrawService.deleteAll(cmsSignupDrawService.findList(cmsDrawSignup));
			System.out.println("cmssignupdraw删除成功");
			
			
			
			
		}
		j.setMsg("删除会议表成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeeting:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(WxMeeting wxMeeting, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "会议表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<WxMeeting> page = wxMeetingService.findPage(new Page<WxMeeting>(request, response, -1), wxMeeting);
			new ExportExcel("会议表", WxMeeting.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议表记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "detail")
	public WxMeeting detail(String id) {
		return wxMeetingService.get(id);
	}

	/**
	 * 导入Excel数据
	 */
	@RequiresPermissions("wechat:wxMeeting:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMeeting> list = ei.getDataList(WxMeeting.class);
			for (WxMeeting wxMeeting : list) {
				try {
					wxMeetingService.save(wxMeeting);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条会议表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条会议表记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会议表失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/wxMeeting/?repage";
	}

	/**
	 * 下载导入会议表数据模板
	 */
	@RequiresPermissions("wechat:wxMeeting:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "会议表数据导入模板.xlsx";
			List<WxMeeting> list = Lists.newArrayList();
			new ExportExcel("会议表数据", WxMeeting.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/wxMeeting/?repage";
	}

	/**
	 * 跳转会议日程列表页
	 *
	 * @param wxMeeting
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wechat:wxMeeting:schedule")
	@RequestMapping(value = "schedule")
	public String goScheduleList(WxMeeting wxMeeting, Model model) {
		model.addAttribute("wxMeeting", wxMeeting);
		return "modules/wechat/meeting/wxMeetingScheduleList";
	}

	/**
	 * 会议直播表列表页面
	 *
	 * @param wxMeeting
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wechat:wxMeetingDirect:direct")
	@RequestMapping(value = "direct")
	public String goDirectList(WxMeeting wxMeeting, Model model) {
		model.addAttribute("wxMeeting", wxMeeting);
		return "modules/wechat/meeting/wxMeetingDirectList";
	}

	/**
	 * 会议报名表列表页面
	 * 
	 * @param wxMeeting
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wechat:wxMeetingSignup:edit")
	@RequestMapping(value = "signup")
	public String signup(WxMeetingSignup wxMeetingSignup, Model model) {
		model.addAttribute("wxMeetingSignup", wxMeetingSignup);
		return "modules/wechat/meeting/wxMeetingSignupList";
	}

	/**
	 * 跳转百度地图点选页面
	 * 
	 * @return
	 */
	@RequestMapping("goBmap")
	public String goBmap() {
		return "modules/wechat/meeting/wxMeetingMap";
	}

}