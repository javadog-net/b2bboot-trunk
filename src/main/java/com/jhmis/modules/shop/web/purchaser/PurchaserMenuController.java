/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.purchaser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMenu;
import com.jhmis.modules.shop.service.purchaser.PurchaserMenuService;
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
 * 采购商菜单Controller
 * @author tity
 * @version 2018-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaserMenu")
public class PurchaserMenuController extends BaseController {

	@Autowired
	private PurchaserMenuService purchaserMenuService;
	
	@ModelAttribute
	public PurchaserMenu get(@RequestParam(required=false) String id) {
		PurchaserMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserMenuService.get(id);
		}
		if (entity == null){
			entity = new PurchaserMenu();
		}
		return entity;
	}
	
	/**
	 * 菜单列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaserMenu:list")
	@RequestMapping(value = {"list", ""})
	public String list(PurchaserMenu purchaserMenu,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/shop/purchaser/purchaserMenuList";
	}

	/**
	 * 查看，增加，编辑菜单表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserMenu:view","shop:purchaser:purchaserMenu:add","shop:purchaser:purchaserMenu:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserMenu purchaserMenu, Model model) {
        if (purchaserMenu.getParent()==null||purchaserMenu.getParent().getId()==null){
            purchaserMenu.setParent(new PurchaserMenu(purchaserMenu.getRootId()));
        }
        //if (purchaserMenu.getParent()!=null && StringUtils.isNotBlank(purchaserMenu.getParent().getId())){
        purchaserMenu.setParent(purchaserMenuService.get(purchaserMenu.getParent().getId()));
        // 获取排序号，最末节点排序号+30
        if (StringUtils.isBlank(purchaserMenu.getId())){
            PurchaserMenu purchaserMenuChild = new PurchaserMenu();
            purchaserMenuChild.setParent(new PurchaserMenu(purchaserMenu.getParent().getId()));
            List<PurchaserMenu> list = purchaserMenuService.findList(purchaserMenu);
            if (list.size() > 0){
                purchaserMenu.setSort(list.get(list.size()-1).getSort());
                if (purchaserMenu.getSort() != null){
                    purchaserMenu.setSort(purchaserMenu.getSort() + 30);
                }
            }
        }
        //}
        if (purchaserMenu.getSort() == null){
            purchaserMenu.setSort(30);
        }
        model.addAttribute("purchaserMenu", purchaserMenu);
        return "modules/shop/purchaser/purchaserMenuForm";
	}

	/**
	 * 保存菜单
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:purchaser:purchaserMenu:add","shop:purchaser:purchaserMenu:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PurchaserMenu purchaserMenu, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, purchaserMenu)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
        // 获取排序号，最末节点排序号+30
        if (StringUtils.isBlank(purchaserMenu.getId())){
            List<PurchaserMenu> list = Lists.newArrayList();
            List<PurchaserMenu> sourcelist = purchaserMenuService.findAllList(new PurchaserMenu());
            PurchaserMenu.sortList(list, sourcelist, purchaserMenu.getParentId(), false);
            if (list.size() > 0){
                purchaserMenu.setSort(list.get(list.size()-1).getSort() + 30);
            }
        }
		//新增或编辑表单保存
		purchaserMenuService.save(purchaserMenu);//保存
		j.setSuccess(true);
		j.put("purchaserMenu", purchaserMenu);
		j.setMsg("保存菜单成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<PurchaserMenu> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "1";
		}
		return purchaserMenuService.getChildren(parentId);
	}
	
	/**
	 * 删除菜单
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserMenu:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserMenu purchaserMenu, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserMenuService.delete(purchaserMenu);
		j.setSuccess(true);
		j.setMsg("删除菜单成功");
		return j;
	}

    /**
     * 修改菜单排序
     */
    @RequiresPermissions(value={"shop:purchaser:purchaserMenu:add","shop:purchaser:purchaserMenu:edit"},logical=Logical.OR)
    @ResponseBody
    @RequestMapping(value = "sort")
    public AjaxJson sort(String id1, int sort1, String id2, int sort2) {
        AjaxJson j = new AjaxJson();
        if(Global.isDemoMode()){
            j.setSuccess(false);
            j.setMsg("演示模式，不允许操作！");
            return j;
        }
        PurchaserMenu purchaserMenu = new PurchaserMenu();
        purchaserMenu.setId(id1);
        purchaserMenu.setSort(sort1);
        purchaserMenuService.updateMenuSort(purchaserMenu);
        purchaserMenu.setId(id2);
        purchaserMenu.setSort(sort2);
        purchaserMenuService.updateMenuSort(purchaserMenu);
        j.setSuccess(true);
        j.setMsg("排序成功！");
        return j;
    }
    
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<PurchaserMenu> list = purchaserMenuService.findList(new PurchaserMenu());
		for (int i=0; i<list.size(); i++){
			PurchaserMenu e = list.get(i);
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