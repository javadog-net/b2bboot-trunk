/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import java.io.File;
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
import com.jhmis.common.wsClient.test.main;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.SkMv;
import com.jhmis.modules.wechat.entity.SkMvComment;
import com.jhmis.modules.wechat.entity.SkMvDetails;
import com.jhmis.modules.wechat.service.SkMvCommentService;
import com.jhmis.modules.wechat.service.SkMvDetailsService;
import com.jhmis.modules.wechat.service.SkMvService;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

/**
 * 商空视频Controller
 * 
 * @author Tc
 * @version 2019-05-24
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/skMv")
public class SkMvController extends BaseController {

	@Autowired
	private SkMvService skMvService;
@Autowired
private SkMvDetailsService skMvDetailsService;
	
	@ModelAttribute
	public SkMv get(@RequestParam(required = false) String id) {
		SkMv entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = skMvService.get(id);
		}
		if (entity == null) {
			entity = new SkMv();
		}
		return entity;
	}

	/**
	 * 商空视频列表页面
	 */
	@RequiresPermissions("wechat:skMv:list")
	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/wechat/skMvList";
	}

	/**
	 * 商空视频列表数据
	 * select *from sk_mv order BY sk_mv_remark desc,sk_mv_addtime desc
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMv:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SkMv skMv, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<> page = skMvService.findPage(new Page<SkMv>(request, response), skMv);
		Page<SkMv> pa = new Page<SkMv>(request, response);
		pa.setOrderBy("sk_mv_remark desc,sk_mv_addtime desc");
		Page<SkMv> page = skMvService.findPage(pa, skMv);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑商空视频表单页面
	 */
	@RequiresPermissions(value = { "wechat:skMv:view", "wechat:skMv:add", "wechat:skMv:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(SkMv skMv, Model model) {
		model.addAttribute("skMv", skMv);
		if (StringUtils.isBlank(skMv.getId())) {// 如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/skMvForm";
	}

	/**
	 * 保存商空视频
	 */
	@RequiresPermissions(value = { "wechat:skMv:add", "wechat:skMv:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(SkMv skMv, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, skMv)) {
			return form(skMv, model);
		}
		System.out.println("skmv.url" + skMv.getSkMvUrl());
		//获取视频的时长
		String time = getTime(skMv.getSkMvUrl());
		skMv.setSkMvSize(time);
		User currentUser = UserUtils.getUser();
		skMv.setSkMvAdduser(currentUser.getLoginName());
		skMv.setSkMvAddtime(new Date());
		skMv.setSkMvRemark("0");//未置顶状态
		// 新增或编辑表单保存
		skMvService.save(skMv);// 保存视频
	
		//保存视频详情
		SkMvDetails skMvDetails=skMvDetailsService.getSkMvById(skMv.getId());
		System.out.println(skMvDetails+"skMvDetails");
		System.out.println(skMv.getId()+"skMv.getId()");
		if(skMvDetails==null||StringUtils.isBlank(skMvDetails.getId())){
			//注意
			skMvDetails=new SkMvDetails();
			System.out.println(skMv.getId()+"skMv.getId()");
			System.out.println(skMv+"skMvskMvskMvskMv");
		skMvDetails.setSkMvId(skMv.getId());
		skMvDetails.setSkMvName(skMv.getSkMvName());
		skMvDetails.setSkMvVisits("0");
		skMvDetails.setSkMvLike("0");
		skMvDetails.setSkMvCommentCount("0");
		}else{
			skMvDetails.setSkMvName(skMv.getSkMvName());
		}
		skMvDetailsService.save(skMvDetails);
		
		addMessage(redirectAttributes, "保存商空视频成功");
		
		return "redirect:" + Global.getAdminPath() + "/wechat/skMv/?repage";
	}

	
	/** 
	  * @Title: istop 
	  * @Description: TODO 对视频是否进行置顶 取消置顶  
	  * @param skMv
	  * @param top
	  * @param redirectAttributes
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年5月30日下午1:54:11
	  */
	@ResponseBody
	@RequiresPermissions("wechat:skMv:del")
	@RequestMapping(value = "istop")
	public AjaxJson istop(SkMv skMv, String top,RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		List<SkMv> list=skMvService.findById(skMv.getId());
		if(list==null||list.size()<=0){
			j.setSuccess(false);
			j.setMsg("数据有误！");
			return j;
		}
		skMv=list.get(0);
		if(skMv.getSkMvRemark()!=null&&skMv.getSkMvRemark().equals(top)){
			j.setSuccess(false);
			j.setMsg("请勿重复操作");
			return j;
		}
		//查询判断 是否已经有置顶了。只能有一个置顶的视频
		if(top!=null&&top.equals("1")){
			List<SkMv> listAll=skMvService.findIsTop();
			for(SkMv mv:listAll){
				if(mv.getSkMvRemark()!=null&&mv.getSkMvRemark().equals("1")){
					j.setSuccess(false);
					j.setMsg("已有视频置顶，请先取消已置顶的视频!");
					return j;
				}
				
			}
		}
			skMvService.isTop(skMv.getId(),top);
			if(top!=null&&top.equals("1")){
		        j.setMsg("置顶视频成功");
			}else{
				j.setMsg("该视频成功取消置顶");
			}
		return j;   
	}

	
	/**
	 * 删除商空视频
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMv:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SkMv skMv, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		skMvService.delete(skMv);
		skMvDetailsService.delete(skMvDetailsService.getSkMvById(skMv.getId()));
		j.setMsg("删除商空视频成功");
		return j;   
	}

	/**
	 * 批量删除商空视频
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMv:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			skMvService.delete(skMvService.get(id));//
			skMvDetailsService.delete(skMvDetailsService.getSkMvById(id));
		}
		j.setMsg("删除商空视频成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:skMv:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(SkMv skMv, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "商空视频" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<SkMv> page = skMvService.findPage(new Page<SkMv>(request, response, -1), skMv);
			new ExportExcel("商空视频", SkMv.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出商空视频记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("wechat:skMv:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SkMv> list = ei.getDataList(SkMv.class);
			for (SkMv skMv : list) {
				try {
					skMvService.save(skMv);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条商空视频记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条商空视频记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入商空视频失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/skMv/?repage";
	}

	/**
	 * 下载导入商空视频数据模板
	 */
	@RequiresPermissions("wechat:skMv:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "商空视频数据导入模板.xlsx";
			List<SkMv> list = Lists.newArrayList();
			new ExportExcel("商空视频数据", SkMv.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/skMv/?repage";
	}

	/** 
	  * @Title: getTime 
	  * @Description: TODO 获取视频的时长 
	  * @param Url
	  * @return 
	  * @return String
	  * @author tc
	  * @date 2019年5月24日下午4:12:02
	  */
	public static String getTime(String Url) {
		String time = "";
		try {
			
			// 本地 用 d:/attachment 生产用 /b2bboot/attachment
			String url = "/b2bboot/attachment" + Url;
			System.out.println(url + "url");
			File source = new File(url);
			Encoder encoder = new Encoder();
			MultimediaInfo m = encoder.getInfo(source);
			long millisecond = m.getDuration();// 毫秒数
			System.out.println(millisecond);

			int hour = (int) millisecond / (60 * 60 * 1000);// 小时
			int minute = (int) (millisecond % (60 * 60 * 1000)) / 60000;// 分钟
			int second = (int) ((millisecond % (60 * 60 * 1000)) % 60000) / 1000;// 秒
			System.out.println("总时间：" + hour + ":" + minute + ":" + second);
			time = hour + ":" + minute + ":" + second;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return time;
	}

	public static void main(String[] args) {
		String s=getTime("/userfiles/sys/1/files/201905/d5f1ace2-05d0-4928-8a70-d360ff4e52fd.mp4");
		System.out.println(s);
	}
}