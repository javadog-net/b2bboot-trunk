/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.AttCat;
import com.jhmis.modules.sys.service.AttCatService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 附件管理Controller
 * @author tity
 * @version 2018-07-06
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/attCat")
public class AttCatController extends BaseController {

	@Autowired
	private AttCatService attCatService;
	
	@ModelAttribute
	public AttCat get(@RequestParam(required=false) String id) {
		AttCat entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attCatService.get(id);
		}
		if (entity == null){
			entity = new AttCat();
		}
		return entity;
	}
	
	/**
	 * 附件列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(AttCat attCat,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/sys/attachment/attCatList";
	}

	/**
	 * 查看，增加，编辑附件表单页面
	 */
	@RequestMapping(value = "form")
	public String form(AttCat attCat, Model model) {
		if (attCat.getParent()!=null && StringUtils.isNotBlank(attCat.getParent().getId())){
			attCat.setParent(attCatService.get(attCat.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(attCat.getId())){
				AttCat attCatChild = new AttCat();
				attCatChild.setParent(new AttCat(attCat.getParent().getId()));
				List<AttCat> list = attCatService.findList(attCat); 
				if (list.size() > 0){
					attCat.setSort(list.get(list.size()-1).getSort());
					if (attCat.getSort() != null){
						attCat.setSort(attCat.getSort() + 30);
					}
				}
			}
		}
		if (attCat.getSort() == null){
			attCat.setSort(30);
		}
		model.addAttribute("attCat", attCat);
		return "modules/sys/attachment/attCatForm";
	}

	/**
	 * 保存附件
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(AttCat attCat, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, attCat)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		//新增或编辑表单保存
		attCatService.save(attCat);//保存
		j.setSuccess(true);
		j.put("attCat", attCat);
		j.setMsg("保存附件成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<AttCat> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return attCatService.getChildren(parentId);
	}
	
	/**
	 * 删除附件
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(AttCat attCat, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		attCatService.delete(attCat);
		j.setSuccess(true);
		j.setMsg("删除附件成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<AttCat> list = attCatService.findList(new AttCat());
		for (int i=0; i<list.size(); i++){
			AttCat e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}