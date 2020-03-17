
/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.api.app.ApiAppGeTuiController;
import com.jhmis.api.app.ApiAppGeTuiToFriendController;
import com.jhmis.api.app.ApiAppPushtoSingleController;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxAppCid;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.entity.WxMessageRecord;
import com.jhmis.modules.wechat.entity.WxPending;
import com.jhmis.modules.wechat.mapper.WxMeetingMapper;
import com.jhmis.modules.wechat.mapper.WxPendingMapper;
import com.jhmis.modules.wechat.service.WxAppCidService;
import com.jhmis.modules.wechat.service.WxMeetingService;
import com.jhmis.modules.wechat.service.WxMeetingSignupService;
import com.jhmis.modules.wechat.service.WxMessageRecordService;

import net.sf.json.JSONObject;

/**
 * 会议报名表Controller
 * 
 * @author lvyangzhuo
 * @version 2018-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxMeetingSignup")
public class WxMeetingSignupController extends BaseController {

	@Autowired
	private WxMessageRecordService wxMessageRecordService;
	@Autowired
	private WxMeetingSignupService wxMeetingSignupService;
	@Autowired
	private WxMeetingMapper wxMeetingMapper;
	@Autowired
	private WxAppCidService wxAppCidService;
	@Autowired
	WxPendingMapper wxPendingMapper;
	@Autowired
	SimpMessageSendingOperations simpMessageSendingOperations;

	@ModelAttribute
	public WxMeetingSignup get(@RequestParam(required = false) String id) {
		WxMeetingSignup entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = wxMeetingSignupService.get(id);
		}
		if (entity == null) {
			entity = new WxMeetingSignup();
		}
		return entity;
	}

	/**
	 * 会议报名表列表页面
	 */
	@RequiresPermissions("wechat:wxMeetingSignup:list")
	@RequestMapping(value = { "list", "" })
	public String list(WxMeetingSignup wxMeetingSignup, Model model) {
		model.addAttribute("wxMeetingSignup", wxMeetingSignup);
		return "modules/wechat/meeting/wxMeetingSignupList";
	}

	/**
	 * 会议报名表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxMeetingSignup wxMeetingSignup, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<WxMeetingSignup> pa = new Page<WxMeetingSignup>(request, response);
		pa.setOrderBy("sign_status asc");
		Page<WxMeetingSignup> page = wxMeetingSignupService.findPage(pa, wxMeetingSignup);
		return getBootstrapData(page);
	}
	/*
	 * public Map<String, Object> data(WxMeetingSignup wxMeetingSignup,
	 * HttpServletRequest request, HttpServletResponse response, Model model) {
	 * Page<WxMeetingSignup> page = wxMeetingSignupService.findPage(new
	 * Page<WxMeetingSignup>(request, response), wxMeetingSignup);
	 * System.out.println("ddddd"); System.out.println(page); return
	 * getBootstrapData(page); }
	 */

	/**
	 * 批量发送会前通知短信
	 */
	@ResponseBody
	@RequestMapping(value = "notifyMsg")
	public AjaxJson notifyMsg(String id) {
		// 判断meetingId是否为空
		if (StringUtils.isEmpty(id)) {
			return AjaxJson.fail("发送消息异常,会议id不能为空");
		}
		// 计数器
		int i = 0;
		// 记录发送的手机号
		StringBuilder mobileStrBulider = new StringBuilder();
		List<WxMeetingSignup> listWxMeetingSignup = wxMeetingSignupService.notifyMsg(id);
		String meetingname = wxMeetingMapper.get(id).getName();
		System.out.println("=====" + listWxMeetingSignup + "====");

		// 证明List存在数据
		if (listWxMeetingSignup != null && listWxMeetingSignup.size() > 0 && StringUtils.isNotEmpty(meetingname)) {
			for (WxMeetingSignup wxMeetingSignup : listWxMeetingSignup) {
				// 循环进行短信推送
				String result="";
				try {
					result = SendMsgApi.SendMessage(wxMeetingSignup.getMobile(),
							"尊敬的嘉宾,【" + meetingname + "】会务组已帮您安排晚宴、会议的座次等会场信息，请先用App扫码签到后，找到本次会议的【与我相关】栏目查看,来自【E企碰碰】");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!"".equals(result)) {
					i++;
					mobileStrBulider.append(wxMeetingSignup.getMobile());
				}
			}
		}
		saveMessageRecord(mobileStrBulider.toString(),
				"尊敬的嘉宾,会务组已帮您安排桌号、座号等会场信息，请签到后打开App本次会议的【与我相关】中查看,来自【E企碰碰】meetingid=" + id, "成功发送个数=" + i);
		// 记录履历
		return AjaxJson.ok("会前通知短信已群发");
	}

	/**
	 * 查看，增加，编辑会议报名表表单页面
	 */
	@RequiresPermissions(value = { "wechat:wxMeetingSignup:view", "wechat:wxMeetingSignup:add",
			"wechat:wxMeetingSignup:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxMeetingSignup wxMeetingSignup, Model model) {
		model.addAttribute("wxMeetingSignup", wxMeetingSignup);
		return "modules/wechat/meeting/wxMeetingSignupForm";
	}

	/**
	 * 保存会议报名表
	 */
	@RequiresPermissions(value = { "wechat:wxMeetingSignup:add", "wechat:wxMeetingSignup:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxMeetingSignup wxMeetingSignup, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		wxMeetingSignupService.save(wxMeetingSignup);// 新建或者编辑保存
		return "redirect:" + Global.getAdminPath() + "/wechat/wxMeetingSignup/?repage&&meetingId="
				+ wxMeetingSignup.getMeetingId();
	}

	/**
	 * 删除会议报名表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxMeetingSignup wxMeetingSignup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxMeetingSignupService.delete(wxMeetingSignup);
		j.setMsg("删除会议报名表成功");
		return j;
	}

	/**
	 * 批量删除会议报名表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			wxMeetingSignupService.delete(wxMeetingSignupService.get(id));
		}
		j.setMsg("删除会议报名表成功");
		return j;
	}

	/**
	 * 会议签到
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:edit")
	@RequestMapping(value = "signup")
	public AjaxJson signup(WxMeetingSignup wxMeetingSignup, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String result=wxMeetingSignupService.signup(wxMeetingSignup);
		if(result!=null&&result.equals("1")){
			j.setMsg("会议签到成功");
		}
		if(result!=null&&result.equals("0")){
			j.setMsg("会议签到失败。失败原因：请先确认是否已经参加或未进行审核！");
		}
		
	
		return j;
	}

	/**
	 * 批量会议签到
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:edit")
	@RequestMapping(value = "signupAll")
	public AjaxJson signupAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		
		int success=0;
		int fail=0;
		for (String id : idArray) {
			String result=wxMeetingSignupService.signup(wxMeetingSignupService.get(id));
			if(result!=null&&result.equals("1")){
				success=success+1;
			}
			if(result!=null&&result.equals("0")){
				fail=fail+1;
			}
			
		}
		j.setMsg("签到成功"+success+"条，签到失败"+fail+"条.失败原因：请先确认是否已经参加或未进行审核！");
		return j;
	}

	/**
	 * 批量审核通过会议报名表 已参加的会议不能再进行审核
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:edit")
	@RequestMapping(value = "audit")
	public AjaxJson audit(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			WxMeetingSignup wxMeetingSignup = wxMeetingSignupService.get(id);
			String meetingname = wxMeetingMapper.get(wxMeetingSignup.getMeetingId()).getName();
			wxMeetingSignup.setId(id);
			if (wxMeetingSignup.getSignStatus().equals("3")) {
				wxMeetingSignup.setSignStatus("3");
				j.setMsg("已参加不可经行操作！");
				j.setSuccess(false);
				return j;
			} else if (wxMeetingSignup.getSignStatus().equals("1") || wxMeetingSignup.getSignStatus().equals("4")) {
				j.setMsg("已经审核不可经行操作！");
				j.setSuccess(false);
				return j;
			} else {
				// 放入审核人id
				User user = UserUtils.getUser();
				if (user != null) {
					wxMeetingSignup.setExaminePerson(user.getName());
					wxMeetingSignup.setExamineTime(new Date());
				} else {
					j.setMsg("系统错误，操作会议报名记录失败，请重新登录");
					j.setSuccess(false);
					return j;
				}

				wxMeetingSignup.setSignStatus(WxMeetingSignup.AUDIT_YES);

				// 存入待处理表
				WxPending pending = new WxPending();
				pending.setUserId(wxMeetingSignup.getUserId());
				pending.setType("0");
				pending.setTypeId(wxMeetingSignup.getMeetingId());
				pending.setTime(wxMeetingSignup.getExamineTime());
				pending.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				pending.setStatus("0");
				pending.setSignStatus(WxMeetingSignup.AUDIT_YES);
				pending.setMeetingName(wxMeetingMapper.get(wxMeetingSignup.getMeetingId()).getName());
				wxPendingMapper.insert(pending);
				JSONObject pendingJsonObject = JSONObject.fromObject(pending);// 将java对象转换为json对象
				String pendingJson = pendingJsonObject.toString();
				simpMessageSendingOperations.convertAndSendToUser(wxMeetingSignup.getUserId(), "/pending", pendingJson);
			}

			wxMeetingSignupService.save(wxMeetingSignup);
			// 然后进群
			wxMeetingSignupService.insertGroup(wxMeetingSignup);
			try {
				// 发送短信通知
				String content = "恭喜！您已成功参与【" + meetingname + "】！请打开APP查看更多会务信息，以便享受更多服务！来自【E企碰碰】";
				String title = "您有一条会议动态通知请尽快查看！";
				String result = SendMsgApi.SendMessage(wxMeetingSignup.getMobile(), content);
				saveMessageRecord(wxMeetingSignup.getMobile(), content, result);
				WxAppCid wxAppCid = new WxAppCid();
				wxAppCid.setUserId(wxMeetingSignup.getUserId());
				List<WxAppCid> listcid = wxAppCidService.findList(wxAppCid);
				for (WxAppCid cid : listcid) {
					if (StringUtils.isNotBlank(cid.getCId())) {
						// 发送任务栏推送消息 wxMeetingSignup.getMeetingId(),
						ApiAppGeTuiToFriendController.pushToMsgByCid
						(cid.getCId(), content,
								title,cid.getPhoneType(),"/pages/meeting/meeting_about?id="+wxMeetingSignup.getMeetingId());
						
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		j.setMsg("操作会议报名记录成功");
		return j;
	}

	/**
	 * @Title: auditandschedule
	 * @Description: TODO 批量 审核 行程 会议
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年2月18日下午4:07:06
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:edit")
	@RequestMapping(value = "auditandschedule")
	public AjaxJson auditandschedule(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			WxMeetingSignup wxMeetingSignup = wxMeetingSignupService.get(id);
			String meetingname = wxMeetingMapper.get(wxMeetingSignup.getMeetingId()).getName();
			wxMeetingSignup.setId(id);
			if (wxMeetingSignup.getSignStatus().equals("3")) {
				j.setMsg("已参加不可经行操作！");
				j.setSuccess(false);
				return j;
			} else if (wxMeetingSignup.getSignStatus().equals("1") || wxMeetingSignup.getSignStatus().equals("4")) {
				j.setMsg("已经审核不可经行操作！");
				j.setSuccess(false);
				return j;
			} else {
				// 放入审核人id
				User user = UserUtils.getUser();
				if (user != null) {
					wxMeetingSignup.setExaminePerson(user.getName());
					wxMeetingSignup.setExamineTime(new Date());
				} else {
					j.setMsg("系统错误，操作会议报名记录失败，请重新登录");
					j.setSuccess(false);
					return j;
				}

				wxMeetingSignup.setSignStatus(WxMeetingSignup.SIGNSTATUS_PASSANDSCHEDULE);

				// 存入待处理表
				WxPending pending = new WxPending();
				pending.setUserId(wxMeetingSignup.getUserId());
				pending.setType("0");
				pending.setTypeId(wxMeetingSignup.getMeetingId());
				pending.setTime(wxMeetingSignup.getExamineTime());
				pending.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				pending.setStatus("0");
				pending.setSignStatus(WxMeetingSignup.SIGNSTATUS_PASSANDSCHEDULE);
				pending.setMeetingName(wxMeetingMapper.get(wxMeetingSignup.getMeetingId()).getName());
				wxPendingMapper.insert(pending);
				JSONObject pendingJsonObject = JSONObject.fromObject(pending);// 将java对象转换为json对象
				String pendingJson = pendingJsonObject.toString();
				simpMessageSendingOperations.convertAndSendToUser(wxMeetingSignup.getUserId(), "/pending", pendingJson);
			}

			wxMeetingSignupService.save(wxMeetingSignup);
			// 然后进群
			wxMeetingSignupService.insertGroup(wxMeetingSignup);
			try {

				// 发送短信通知
				String content = "恭喜！您已成功参与【" + meetingname + "】！如需会务订票，请打开APP找到本次会议的【与我相关】栏目填写行程信息，来自【E企碰碰】";
				String title = "您有一条会议动态通知请尽快查看！";
				String result = SendMsgApi.SendMessage(wxMeetingSignup.getMobile(), content);
				saveMessageRecord(wxMeetingSignup.getMobile(), content, result);
				WxAppCid wxAppCid = new WxAppCid();
				wxAppCid.setUserId(wxMeetingSignup.getUserId());
				List<WxAppCid> listcid = wxAppCidService.findList(wxAppCid);
				for (WxAppCid cid : listcid) {
					if (StringUtils.isNotBlank(cid.getCId())) {
						// 发送任务栏推送消息
					
						ApiAppGeTuiToFriendController.pushToMsgByCid
						(cid.getCId(), content,
								title,cid.getPhoneType(),"/pages/meeting/meeting_about?id="+wxMeetingSignup.getMeetingId());
						
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		j.setMsg("操作会议报名记录成功");
		return j;
	}

	/**
	 * 批量审核拒绝会议报名表 已参加的会议不能再进行审核
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:edit")
	@RequestMapping(value = "reverseAudit")
	public AjaxJson reverseAudit(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			WxMeetingSignup wxMeetingSignup = wxMeetingSignupService.get(id);
			wxMeetingSignup.setId(id);
			if (wxMeetingSignup.getSignStatus().equals("3")) {
				j.setMsg("已参加不可经行操作！");
				j.setSuccess(false);
				return j;
			} else if (wxMeetingSignup.getSignStatus().equals("1") || wxMeetingSignup.getSignStatus().equals("4")) {
				j.setMsg("已经审核不可经行操作！");
				j.setSuccess(false);
				return j;
			} else {
				// 放入审核人id
				User user = UserUtils.getUser();
				if (user != null) {
					wxMeetingSignup.setExaminePerson(user.getName());
					wxMeetingSignup.setExamineTime(new Date());
				} else {
					j.setMsg("系统错误，操作会议报名记录失败，请重新登录");
					j.setSuccess(false);
					return j;
				}

				wxMeetingSignup.setSignStatus(WxMeetingSignup.AUDIT_NO);

				// 存入待处理表
				WxPending pending = new WxPending();
				pending.setUserId(wxMeetingSignup.getUserId());
				pending.setType("0");
				pending.setTypeId(wxMeetingSignup.getMeetingId());
				pending.setTime(wxMeetingSignup.getExamineTime());
				pending.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				pending.setStatus("0");
				pending.setSignStatus(WxMeetingSignup.AUDIT_NO);
				pending.setMeetingName(wxMeetingMapper.get(wxMeetingSignup.getMeetingId()).getName());
				wxPendingMapper.insert(pending);
				JSONObject pendingJsonObject = JSONObject.fromObject(pending);// 将java对象转换为json对象
				String pendingJson = pendingJsonObject.toString();
				simpMessageSendingOperations.convertAndSendToUser(wxMeetingSignup.getUserId(), "/pending", pendingJson);
			}
			wxMeetingSignupService.save(wxMeetingSignup);
			// 发送短信通知
			try {

				String content = "您的报名参会申请未通过审核，如有疑问请与会务组沟通，谢谢！来自【E企碰碰】";
				String result = SendMsgApi.SendMessage(wxMeetingSignup.getMobile(), content);
				saveMessageRecord(wxMeetingSignup.getMobile(), content, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		j.setMsg("操作会议报名记录成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxMeetingSignup:export")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public AjaxJson exportFile(WxMeetingSignup wxMeetingSignup, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {

		AjaxJson j = new AjaxJson();
		try {
			String fileName = "会议报名表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<WxMeetingSignup> list = null;
			list = wxMeetingSignupService.findList(wxMeetingSignup);
			new ExportExcel("会议报名表", WxMeetingSignup.class, 1).setDataList(list).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议报名表记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("wechat:wxMeetingSignup:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		String meetingid = "";
		String mobile = "";
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxMeetingSignup> list = ei.getDataList(WxMeetingSignup.class);
			System.out.println("=======" + list.size() + "==");
			for (WxMeetingSignup wxMeetingSignup : list) {
				System.out.println("--" + wxMeetingSignup + "---");
				mobile = wxMeetingSignup.getMobile().trim();
				meetingid = wxMeetingSignup.getMeetingId().trim();
				try {
					if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(meetingid)) {
						continue;
					}
					List<WxMeetingSignup> listsign = wxMeetingSignupService.getmobileandmeetingid(mobile, meetingid);
					if (listsign != null && listsign.size() > 1) {
						continue;
					}
					wxMeetingSignup.setMobile(mobile);
					wxMeetingSignupService.importFileUpdate(wxMeetingSignup);
					successNum++;
				} catch (ConstraintViolationException ex) {
					System.out.println(ex.getStackTrace());
					failureNum++;
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条会议报名表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条会议报名表记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会议报名表失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/wxMeetingSignup/?repage&&meetingId=" + meetingid;
	}

	/**
	 * 下载导入会议报名表数据模板
	 */
	@RequiresPermissions("wechat:wxMeetingSignup:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, WxMeetingSignup wxMeetingSignup,
			RedirectAttributes redirectAttributes) {
		try {// Lists.newArrayList();
			String fileName = "会议报名表数据导入模板.xlsx";
			System.out.println(wxMeetingSignup);
			System.out.println(wxMeetingSignup.getMeetingId() + "meetingid");

			List<WxMeetingSignup> list = wxMeetingSignupService.findList(wxMeetingSignup);
			new ExportExcel("会议报名表数据", WxMeetingSignup.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/wxMeetingSignup/?repage";
	}

	/**
	 * @description 短息履历
	 * @method
	 * @return
	 * @date: 15:28:06
	 * @author:hdx
	 */
	public void saveMessageRecord(String mobile, String content, String result) {
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