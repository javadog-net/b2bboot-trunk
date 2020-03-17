/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

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
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.wechat.entity.CmsWinPrize;
import com.jhmis.modules.wechat.service.CmsWinPrizeService;

/**
 * 中奖表Controller
 * @author tc
 * @version 2019-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/cmsWinPrize")
public class CmsWinPrizeController extends BaseController {

	@Autowired
	private CmsWinPrizeService cmsWinPrizeService;
	
	@ModelAttribute
	public CmsWinPrize get(@RequestParam(required=false) String id) {
		CmsWinPrize entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsWinPrizeService.get(id);
		}
		if (entity == null){
			entity = new CmsWinPrize();
		}
		return entity;
	}
	
	/**
	 * 中奖操作列表页面
	 */
	@RequiresPermissions("wechat:cmsWinPrize:list")
	@RequestMapping(value = {"list", ""})
	public String list(CmsWinPrize cmsWinPrize, Model model) {
		System.out.println("中奖活动id"+cmsWinPrize.getActvId());
		model.addAttribute("cmsWinPrize", cmsWinPrize);
		return "modules/wechat/cmsWinPrizeList";
	}
	
	/**
	 * 中奖操作列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsWinPrize:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsWinPrize cmsWinPrize, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("cmswinprize=="+cmsWinPrize+"==");
		Page<CmsWinPrize> page = cmsWinPrizeService.findPage(new Page<CmsWinPrize>(request, response), cmsWinPrize); 
		try {
			page.setList(cmsWinPrizeService.findDrawList(cmsWinPrize.getActvId(),cmsWinPrize.getUserPhone(),
					             cmsWinPrize.getCompanyName(),cmsWinPrize.getUserName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑中奖操作表单页面
	 */
	@RequiresPermissions(value={"wechat:cmsWinPrize:view","wechat:cmsWinPrize:add","wechat:cmsWinPrize:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsWinPrize cmsWinPrize, Model model) {
		 cmsWinPrize=cmsWinPrizeService.getByid(cmsWinPrize.getActvId(),cmsWinPrize.getUserId());
		model.addAttribute("cmsWinPrize", cmsWinPrize);
		if(StringUtils.isBlank(cmsWinPrize.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/cmsWinPrizeForm";
	}

	/**
	 * 保存中奖操作
	 */
	@RequiresPermissions(value={"wechat:cmsWinPrize:add","wechat:cmsWinPrize:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsWinPrize cmsWinPrize, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsWinPrize)){
			return form(cmsWinPrize, model);
		}
		//新增或编辑表单保存
		cmsWinPrizeService.save(cmsWinPrize);//保存
		addMessage(redirectAttributes, "保存中奖操作成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/cmsWinPrize/?repage&actvId="+cmsWinPrize.getActvId();
	}
	
	/**
	 * 删除中奖操作
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsWinPrize:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsWinPrize cmsWinPrize, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsWinPrizeService.delete(cmsWinPrize);
		j.setMsg("删除中奖操作成功");
		return j;
	}
	
	/**
	 * 批量删除中奖操作
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsWinPrize:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsWinPrizeService.delete(cmsWinPrizeService.get(id));
		}
		j.setMsg("删除中奖操作成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsWinPrize:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CmsWinPrize cmsWinPrize, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "中奖操作"+DateUtils.getDate("y0yyyMMddHHmmss")+".xlsx";
            Page<CmsWinPrize> page = cmsWinPrizeService.findPage(new Page<CmsWinPrize>(request, response, -1), cmsWinPrize);
    		new ExportExcel("中奖操作", CmsWinPrize.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出中奖操作记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:cmsWinPrize:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsWinPrize> list = ei.getDataList(CmsWinPrize.class);
			for (CmsWinPrize cmsWinPrize : list){
				try{
					cmsWinPrizeService.save(cmsWinPrize);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条中奖操作记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条中奖操作记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入中奖操作失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/cmsWinPrize/?repage";
    }
	
	/**
	 * 下载导入中奖操作数据模板
	 */
	@RequiresPermissions("wechat:cmsWinPrize:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "中奖操作数据导入模板.xlsx";
    		List<CmsWinPrize> list = Lists.newArrayList(); 
    		new ExportExcel("中奖操作数据", CmsWinPrize.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/cmsWinPrize/?repage";
    }

}