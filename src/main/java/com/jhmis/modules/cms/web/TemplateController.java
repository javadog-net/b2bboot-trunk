package com.jhmis.modules.cms.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.FileTpl;
import com.jhmis.modules.cms.entity.Site;
import com.jhmis.modules.cms.service.FileTplService;
import com.jhmis.modules.cms.service.SiteService;


/**
 * 模板controller
 * @author lydia
 * @version 2019-09-06
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/template")
public class TemplateController extends BaseController {

    @Autowired
   	private FileTplService fileTplService;
	@Autowired
	ServletContext context;

    @RequiresPermissions("cms:template:edit")
   	@RequestMapping(value = "")
   	public String index() {
   		return "modules/cms/tplIndex";
   	}

    @RequiresPermissions("cms:template:edit")
   	@RequestMapping(value = "tree")
   	public String tree(Model model) {
        //Site site = siteService.get(Site.getCurrentSiteId());
   		//model.addAttribute("templateList", fileTplService.getListForEdit(site.getSolutionPath()));
   		return "modules/cms/tplTree";
   	}

    @RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String type,HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		String path = context.getRealPath("")+"/template/default/";
		List<FileTpl> list = fileTplService.getListForEdit(path);

		for (int i=0; i<list.size(); i++){
			FileTpl e = list.get(i);
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getName());
				String parentId = "0";
				if(StringUtils.isNotBlank(e.getParent())){
					parentId = e.getParent();
				}
				if(Site.TPL_BASE.equals(parentId)){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", parentId);
				}
				map.put("name", e.getFilename());
				map.put("text", e.getFilename());
				
				map.put("fullName", e.getName());
				map.put("isDirectory", e.isDirectory());
				
				mapList.add(map);
		}
		return mapList;
	}
    
    @RequiresPermissions("cms:template:edit")
   	@RequestMapping(value = "form")
   	public String form(String name, Model model) {
        model.addAttribute("template", fileTplService.getFileTpl(name));
   		return "modules/cms/tplForm";
   	}

    @RequiresPermissions("cms:template:edit")
   	@RequestMapping(value = "help")
   	public String help() {
   		return "modules/cms/tplHelp";
   	}
    
    @RequiresPermissions("cms:template:edit")
	@RequestMapping(value = "save")
	public String save(String name, String filename, String source, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/tplIndex";
		}
		source = StringEscapeUtils.unescapeHtml4(source);
		fileTplService.saveTpl(name,source);

		addMessage(redirectAttributes, "保存文件'" + filename + "'成功");
		return "redirect:" + adminPath + "/cms/template/form?name="+name;
	}
}
