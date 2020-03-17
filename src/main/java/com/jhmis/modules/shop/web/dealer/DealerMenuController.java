/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.dealer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.dealer.DealerMenu;
import com.jhmis.modules.shop.service.dealer.DealerMenuService;
import org.apache.shiro.authz.annotation.Logical;
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
 * 供应商菜单管理Controller
 * @author tity
 * @version 2018-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/dealer/dealerMenu")
public class DealerMenuController extends BaseController {

	@Autowired
	private DealerMenuService dealerMenuService;
	
	@ModelAttribute
	public DealerMenu get(@RequestParam(required=false) String id) {
		DealerMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dealerMenuService.get(id);
		}
		if (entity == null){
			entity = new DealerMenu();
		}
		return entity;
	}
	
	/**
	 * 菜单列表页面
	 */
	@RequiresPermissions("shop:dealer:dealerMenu:list")
	@RequestMapping(value = {"list", ""})
	public String list(DealerMenu dealerMenu,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/shop/dealer/dealerMenuList";
	}

	/**
	 * 查看，增加，编辑菜单表单页面
	 */
	@RequiresPermissions(value={"shop:dealer:dealerMenu:view","shop:dealer:dealerMenu:add","shop:dealer:dealerMenu:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DealerMenu dealerMenu, Model model) {
		if (dealerMenu.getParent()==null||dealerMenu.getParent().getId()==null){
			dealerMenu.setParent(new DealerMenu(dealerMenu.getRootId()));
		}
		//if (dealerMenu.getParent()!=null && StringUtils.isNotBlank(dealerMenu.getParent().getId())){
			dealerMenu.setParent(dealerMenuService.get(dealerMenu.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(dealerMenu.getId())){
				DealerMenu dealerMenuChild = new DealerMenu();
				dealerMenuChild.setParent(new DealerMenu(dealerMenu.getParent().getId()));
				List<DealerMenu> list = dealerMenuService.findList(dealerMenu); 
				if (list.size() > 0){
					dealerMenu.setSort(list.get(list.size()-1).getSort());
					if (dealerMenu.getSort() != null){
						dealerMenu.setSort(dealerMenu.getSort() + 30);
					}
				}
			}
		//}
		if (dealerMenu.getSort() == null){
			dealerMenu.setSort(30);
		}
		model.addAttribute("dealerMenu", dealerMenu);
		return "modules/shop/dealer/dealerMenuForm";
	}

	/**
	 * 保存菜单
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:dealer:dealerMenu:add","shop:dealer:dealerMenu:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(DealerMenu dealerMenu, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, dealerMenu)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

        // 获取排序号，最末节点排序号+30
        if (StringUtils.isBlank(dealerMenu.getId())){
            List<DealerMenu> list = Lists.newArrayList();
            List<DealerMenu> sourcelist = dealerMenuService.findAllList(new DealerMenu());
            DealerMenu.sortList(list, sourcelist, dealerMenu.getParentId(), false);
            if (list.size() > 0){
                dealerMenu.setSort(list.get(list.size()-1).getSort() + 30);
            }
        }
		//新增或编辑表单保存
		dealerMenuService.save(dealerMenu);//保存
		j.setSuccess(true);
		j.put("dealerMenu", dealerMenu);
		j.setMsg("保存菜单成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<DealerMenu> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "1";
		}
		return dealerMenuService.getChildren(parentId);
	}
	
	/**
	 * 删除菜单
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMenu:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DealerMenu dealerMenu, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		dealerMenuService.delete(dealerMenu);
		j.setSuccess(true);
		j.setMsg("删除菜单成功");
		return j;
	}

	/**
	 * 修改菜单排序
	 */
	@RequiresPermissions(value={"shop:dealer:dealerMenu:add","shop:dealer:dealerMenu:edit"},logical=Logical.OR)
	@ResponseBody
	@RequestMapping(value = "sort")
	public AjaxJson sort(String id1, int sort1, String id2, int sort2) {
		AjaxJson j = new AjaxJson();
		if(Global.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		DealerMenu dealerMenu = new DealerMenu();
		dealerMenu.setId(id1);
		dealerMenu.setSort(sort1);
		dealerMenuService.updateMenuSort(dealerMenu);
		dealerMenu.setId(id2);
		dealerMenu.setSort(sort2);
		dealerMenuService.updateMenuSort(dealerMenu);
		j.setSuccess(true);
		j.setMsg("排序成功！");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<DealerMenu> list = dealerMenuService.findList(new DealerMenu());
		for (int i=0; i<list.size(); i++){
			DealerMenu e = list.get(i);
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