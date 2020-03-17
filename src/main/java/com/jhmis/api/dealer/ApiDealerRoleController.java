package com.jhmis.api.dealer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.beanvalidator.BeanValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.dealer.DealerMenu;
import com.jhmis.modules.shop.entity.dealer.DealerRole;
import com.jhmis.modules.shop.service.dealer.DealerMenuService;
import com.jhmis.modules.shop.service.dealer.DealerRoleService;
import com.jhmis.modules.shop.utils.DealerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(value = "ApiDealerRoleController", description = "供应商角色管理")
@RestController
@RequestMapping("/api/dealer/role")
public class ApiDealerRoleController {
    @Autowired
    private DealerRoleService dealerRoleService;
    @Autowired
    private DealerMenuService dealerMenuService;

    /**
     * 角色列表
     *
     * @return
     */
    @ApiOperation(notes = "list", httpMethod = "POST", value = "角色列表", consumes = "application/x-www-form-urlencoded")
    @RequiresPermissions("dealer:role:list")
    @RequestMapping("/list")
    public AjaxJson list(HttpServletRequest request, HttpServletResponse response) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
        DealerRole role = new DealerRole();
        role.setDealerId(currentAccount.getDealerId());
        //不分页写法
        List<DealerRole> roleList = dealerRoleService.findList(role);
        return AjaxJson.layuiTable(roleList);
        //分页写法
        //Page<DealerRole> page = dealerRoleService.findPage(new Page<DealerRole>(request, response), role);
        //return AjaxJson.layuiTable(page);
    }

    /**
     * 角色信息
     *
     * @return
     */
    @ApiOperation(notes = "info", httpMethod = "GET", value = "角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "账号id", required = true, paramType = "query", dataType = "String")
    })
    @RequiresPermissions(value = {"dealer:role:view", "dealer:role:add", "dealer:role:edit"}, logical = Logical.OR)
    @RequestMapping("/info")
    public AjaxJson info(String id) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        DealerRole role = dealerRoleService.get(id);
        if (role == null) {
            return AjaxJson.fail("无此角色信息");
        }
        if (!role.getDealerId().equals(currentAccount.getDealerId())) {
            return AjaxJson.fail("非法访问");
        }
        return AjaxJson.ok(role);
    }

    /**
     * 角色拥有的功能菜单
     *
     * @return //功能权限
     * var ref = $('#menuTree').jstree(true);
     * var ids = ref.get_selected();
     * //取半选节点ID
     * $("#menuTree li").has("i[class*='jstree-undetermined']").each(function(){
     * ids+=","+$(this).attr("id");
     * });
     * $("#menuIds").val(ids);
     */
    @ApiOperation(notes = "menutree", httpMethod = "GET", value = "角色拥有的功能菜单(供编辑角色菜单的jstree使用)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id（新增角色传空字符串）", required = false, paramType = "query", dataType = "String")
    })
    @RequiresPermissions(value = {"dealer:role:view", "dealer:role:add", "dealer:role:edit"}, logical = Logical.OR)
    @RequestMapping("/menutree")
    public List<Map<String, Object>> menuTree(String roleId) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            AjaxJson.fail("账号异常");
            return null;
        }
        String menuIds = "";
        if (StringUtils.isNotBlank(roleId)) {
            DealerRole role = dealerRoleService.get(roleId);
            if (role != null) {
                menuIds = "," + role.getMenuIds() + ",";
            }
        }
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<DealerMenu> list = dealerMenuService.findAllList(new DealerMenu());
        for (int i = 0; i < list.size(); i++) {
            DealerMenu e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", e.getId());
            if ("0".equals(e.getParentId())) {
                map.put("parent", "#");
                Map<String, Object> state = Maps.newHashMap();
                state.put("opened", true);
                map.put("state", state);

            } else {
                if (i == 0) {
                    map.put("parent", "#");
                } else {
                    map.put("parent", e.getParentId());
                }
            }

            if (menuIds.contains("," + e.getId() + ",") && dealerMenuService.getChildren(e.getId()).size() == 0) {
                Map<String, Object> state = Maps.newHashMap();
                state.put("selected", true);
                map.put("state", state);
            }

            if (StringUtils.isNotBlank(e.getIcon())) {
                map.put("icon", e.getIcon());
            }
            if ("2".equals(e.getMenuType())) {
                map.put("type", "btn");
            } else if ("1".equals(e.getMenuType())) {
                map.put("type", "menu");
            }
            map.put("text", e.getName());
            map.put("name", e.getName());

            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 角色保存
     *
     * @return
     */
    @ApiOperation(notes = "save", httpMethod = "POST", value = "角色保存", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "账号id（修改必传，新增为空）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "角色名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "useable", value = "是否可用(1,0)", required = true, defaultValue = "1", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "menuIdList", value = "菜单列表（菜单id数组）", required = false, allowMultiple = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions(value = {"dealer:role:add", "dealer:role:edit"}, logical = Logical.OR)
    @RequestMapping("/save")
    public AjaxJson save(DealerRole role) throws Exception {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
        //
        boolean isnew = StringUtils.isBlank(role.getId()) ? true : false;
        role.setDealerId(currentAccount.getDealerId());
        if (isnew) {
            //检查必填
            if (StringUtils.isBlank(role.getName())) {
                return AjaxJson.fail("角色名称不能为空");
            }
            if (StringUtils.isBlank(role.getUseable())) {
                return AjaxJson.fail("是否可用不能为空");
            }
            //检查角色名称重复
            DealerRole checker = dealerRoleService.getByName(role);
            if (checker != null) {
                return AjaxJson.fail("角色名称重复");
            }
            if (StringUtils.isBlank(role.getUseable())) {
                role.setUseable("1");
            }
            role.setDealerId(currentAccount.getDealerId());
            role.setCreateById(currentAccount.getId());
            role.setUpdateById(currentAccount.getId());
        } else {
            DealerRole entity = dealerRoleService.get(role.getId());
            if (entity == null) {
                return AjaxJson.fail("无此角色");
            }
            if (!entity.getDealerId().equals(currentAccount.getDealerId())) {
                return AjaxJson.fail("非法访问");
            }
            if (StringUtils.isNotBlank(role.getName())) {
                if (!entity.getName().equalsIgnoreCase(role.getName())) {
                    //检查账号重复
                    DealerRole checker = dealerRoleService.getByName(role);
                    if (checker != null) {
                        return AjaxJson.fail("角色名称重复");
                    }
                }
            }
            role.setUpdateById(currentAccount.getId());
            MyBeanUtils.copyBeanNotNull2Bean(role, entity);
            role = entity;
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<DealerMenu> menuList = Lists.newArrayList();
        List<String> menuIdList = role.getMenuIdList();
        if (menuIdList == null || menuIdList.size() == 0) {
            return AjaxJson.fail("功能菜单不能为空");
        } else {
            DealerMenu menu = new DealerMenu();
            for (DealerMenu m : dealerMenuService.findAllList(menu)) {
                if (menuIdList.contains(m.getId())) {
                    menuList.add(m);
                }
            }
            role.setMenuList(menuList);
        }
        //参数验证
        BeanValidators.validator(role);
        //
        dealerRoleService.save(role);
        return AjaxJson.ok();
    }

    /**
     * 角色删除
     *
     * @return
     */
    @ApiOperation(notes = "del", httpMethod = "POST", value = "角色删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("dealer:role:del")
    @RequestMapping("/del")
    public AjaxJson del(String id) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        DealerRole role = dealerRoleService.get(id);
        if (role == null) {
            return AjaxJson.fail("无此角色信息");
        }
        if (!role.getDealerId().equals(currentAccount.getDealerId())) {
            return AjaxJson.fail("非法访问");
        }
        dealerRoleService.delete(role);
        return AjaxJson.ok();
    }

    /**
     * 角色删除
     *
     * @return
     */
    @ApiOperation(notes = "batchdel", httpMethod = "POST", value = "角色删除(批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "角色id(逗号分隔)", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("dealer:role:del")
    @RequestMapping("/batchdel")
    public AjaxJson batchdel(String ids) {
        DealerAccount currentAccount = DealerUtils.getDealerAccount();
        if (currentAccount == null) {
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(ids)){
            return AjaxJson.fail("参数为空");
        }
        String idArray[] =ids.split(",");
        for(String id : idArray) {
            DealerRole role = dealerRoleService.get(id);
            if (role == null) {
                return AjaxJson.fail("无此角色信息");
            }
            if (!role.getDealerId().equals(currentAccount.getDealerId())) {
                return AjaxJson.fail("非法访问");
            }
            dealerRoleService.delete(role);
        }
        return AjaxJson.ok();
    }
}
