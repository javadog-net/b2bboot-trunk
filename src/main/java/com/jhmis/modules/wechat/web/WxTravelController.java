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

import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.entity.WxMessageRecord;
import com.jhmis.modules.wechat.service.WxMessageRecordService;
import com.jhmis.modules.wechat.service.WxPendingService;
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
import com.haier.webservices.client.shortmsg.SendMsgApi;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.wechat.entity.WxTravel;
import com.jhmis.modules.wechat.service.WxTravelService;

/**
 * 行程Controller
 * @author hdx
 * @version 2019-02-14
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxTravel")
public class WxTravelController extends BaseController {

	@Autowired
	private WxTravelService wxTravelService;

	@Autowired
	private WxMessageRecordService wxMessageRecordService;

	@ModelAttribute
	public WxTravel get(@RequestParam(required=false) String id) {
		WxTravel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxTravelService.get(id);
		}
		if (entity == null){
			entity = new WxTravel();
		}
		return entity;
	}
	
	/**
	 * 行程信息列表页面
	 */
	@RequiresPermissions("wechat:wxTravel:list")
	@RequestMapping(value = {"list", ""})
	public String list(WxTravel wxTravel, Model model) {
		model.addAttribute("wxTravel", wxTravel);
		return "modules/wechat/wxTravelList";
	}
	
	/**
	 * 行程信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxTravel:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxTravel wxTravel, HttpServletRequest request, HttpServletResponse response, Model model) {
		//查询非删除的行程
		wxTravel.setIsDel("0");
		Page<WxTravel> page = wxTravelService.findPage(new Page<WxTravel>(request, response), wxTravel); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑行程信息表单页面
	 */
	@RequiresPermissions(value={"wechat:wxTravel:view","wechat:wxTravel:add","wechat:wxTravel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxTravel wxTravel, Model model) {
		model.addAttribute("wxTravel", wxTravel);
		if(StringUtils.isBlank(wxTravel.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		model.addAttribute("view", true);
		return "modules/wechat/wxTravelForm";
	}


	/**
	 * 查看，增加，编辑行程信息表单页面
	 */
	@RequiresPermissions(value={"wechat:wxTravel:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(WxTravel wxTravel, Model model) {
		model.addAttribute("wxTravel", wxTravel);
		model.addAttribute("view", false);
		return "modules/wechat/wxTravelForm";
	}

	/**
	 * 保存行程信息
	 */
	@RequiresPermissions(value={"wechat:wxTravel:add","wechat:wxTravel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxTravel wxTravel, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxTravel)){
			return form(wxTravel, model);
		}
		//新增或编辑表单保存
		wxTravelService.save(wxTravel);//保存
		if("1".equals(wxTravel.getStartTicket())&&"1".equals(wxTravel.getReturnTicket())){
			//如果出票成功则发送短信
			String result = SendMsgApi.SendMessage(wxTravel.getMobile(),"尊敬的嘉宾：会务组已确认您的行程信息并出票，请打开App本次会议的【与我相关】栏目确认，具体出发信息以票务公司短信通知为准，来自【E企碰碰】");
			//记录短信履历
			saveMessageRecord(wxTravel.getMobile(),"行程id=" + wxTravel.getId() +"已出票",result);
		}
		addMessage(redirectAttributes, "保存行程信息成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wxTravel/?repage&meetingId="+wxTravel.getMeetingId();
	}
	
	/**
	 * 删除行程信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxTravel:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxTravel wxTravel, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxTravelService.delete(wxTravel);
		j.setMsg("删除行程信息成功");
		return j;
	}
	
	/**
	 * 批量删除行程信息
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxTravel:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxTravelService.delete(wxTravelService.get(id));
		}
		j.setMsg("删除行程信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxTravel:export")
    @RequestMapping(value = "export", method=RequestMethod.GET)
    public AjaxJson exportFile(WxTravel wxTravel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
        wxTravel.setIsDel("0");
		try {
            String fileName = "行程信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxTravel> page = wxTravelService.findPage(new Page<WxTravel>(request, response, -1), wxTravel);
			ExportExcel exportExcel = new ExportExcel("行程信息",getArray());
			exportExcel.setTravelDataList(page.getList(),getArray().length).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出行程信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxTravel:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxTravel> list = ei.getDataList(WxTravel.class);
			for (WxTravel wxTravel : list){
				try{
					wxTravelService.save(wxTravel);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条行程信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条行程信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入行程信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxTravel/?repage";
    }
	
	/**
	 * 下载导入行程信息数据模板
	 */
	@RequiresPermissions("wechat:wxTravel:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "行程信息数据导入模板.xlsx";
    		List<WxTravel> list = Lists.newArrayList(); 
    		new ExportExcel("行程信息数据", WxTravel.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/wxTravel/?repage";
    }

    public String[] getArray(){
		String arr[] = new String[]{
			"姓名", "手机号","交通工具","去程时间","出发站","到达站","航班号/车次号","接站","去程出票状态","返程交通工具","返程时间","返程出发站","返程到达站","返程航班号/车次号","返程出票状态"
		};
		return arr;
	}

	/**
	 * @description 短息履历
	 * @method
	 *
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