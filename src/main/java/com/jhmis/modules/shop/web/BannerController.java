/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.payment.kjt.PaymentKjtNotifyController;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.jhmis.modules.shop.entity.Banner;
import com.jhmis.modules.shop.service.BannerService;

/**
 * 横幅管理Controller
 * @author hdx
 * @version 2018-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/banner")
public class BannerController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(BannerController.class);
	@Autowired
	private BannerService bannerService;
	
	@ModelAttribute
	public Banner get(@RequestParam(required=false) String id) {
		logger.info("*_*_*_*_*_*_*_*_*_* /shop/banner/get *_*_*_*_*_*_*_*_*_*");
		Banner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bannerService.get(id);
			logger.info("*_*_*_*_*_*_*_*_*_* /shop/banner/get  id= "+ id +" *_*_*_*_*_*_*_*_*_*");
		}
		if (entity == null){
			entity = new Banner();
			logger.info("*_*_*_*_*_*_*_*_*_* /shop/banner/get  id 为空 *_*_*_*_*_*_*_*_*_*");
		}
		return entity;
	}
	
	/**
	 * 横幅管理列表页面
	 */
	@RequiresPermissions("shop:banner:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/bannerList";
	}
	
	/**
	 * 横幅管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:banner:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Banner banner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Banner> page = bannerService.findPage(new Page<Banner>(request, response), banner); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑横幅管理表单页面
	 */
	@RequiresPermissions(value={"shop:banner:view","shop:banner:add","shop:banner:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Banner banner, Model model) {
		model.addAttribute("banner", banner);
		if(StringUtils.isBlank(banner.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/bannerForm";
	}

	/**
	 * 保存横幅管理
	 */
	@RequiresPermissions(value={"shop:banner:add","shop:banner:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Banner banner, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, banner)){
			return form(banner, model);
		}
		//新增或编辑表单保存
		bannerService.save(banner);//保存
		addMessage(redirectAttributes, "保存横幅管理成功");
		return "redirect:"+Global.getAdminPath()+"/shop/banner/?repage";
	}
	
	/**
	 * 删除横幅管理
	 */
	@ResponseBody
	@RequiresPermissions("shop:banner:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Banner banner, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		bannerService.delete(banner);
		j.setMsg("删除横幅管理成功");
		return j;
	}
	
	/**
	 * 批量删除横幅管理
	 */
	@ResponseBody
	@RequiresPermissions("shop:banner:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bannerService.delete(bannerService.get(id));
		}
		j.setMsg("删除横幅管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:banner:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Banner banner, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "横幅管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Banner> page = bannerService.findPage(new Page<Banner>(request, response, -1), banner);
    		new ExportExcel("横幅管理", Banner.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出横幅管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:banner:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Banner> list = ei.getDataList(Banner.class);
			for (Banner banner : list){
				try{
					bannerService.save(banner);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条横幅管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条横幅管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入横幅管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/banner/?repage";
    }
	
	/**
	 * 下载导入横幅管理数据模板
	 */
	@RequiresPermissions("shop:banner:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "横幅管理数据导入模板.xlsx";
    		List<Banner> list = Lists.newArrayList(); 
    		new ExportExcel("横幅管理数据", Banner.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/banner/?repage";
    }

}