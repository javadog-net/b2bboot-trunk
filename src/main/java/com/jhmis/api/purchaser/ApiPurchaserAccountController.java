package com.jhmis.api.purchaser;

import com.google.common.collect.Lists;
import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.beanvalidator.BeanValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.MenuNode;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMenu;
import com.jhmis.modules.shop.entity.purchaser.PurchaserRole;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserRoleService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "ApiPurchaserAccountController", description = "采购商账号管理")
@RestController
@RequestMapping("/api/purchaser/account")
public class ApiPurchaserAccountController {
    @Autowired
    private PurchaserAccountService purchaserAccountService;
    @Autowired
    private PurchaserRoleService purchaserRoleService;

    /**
     * 账号列表
     * 暂时不做分页
     *
     * @return swagger 参数类型path(路径参数), query(在url中), body(json对象), header, form<formdata表单内容提交>
     * 对于在url中传递的参数使用query,表单内容的参数使用form,@RequestBody的json对象使用body
     * consumes="application/x-www-form-urlencoded"为普通表单
     * consumes="application/json;charset=utf-8"为json提交（默认就是)
     * 普通表单测试设置了consumes为普通表单的时候，如果有对象参数，必须手工选择x-www-form来提交
     * 对象参数独立占一行，没法隐藏
     */
    @ApiOperation(notes = "list", httpMethod = "POST", value = "账号列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "登录账号", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "realName", value = "真实姓名", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "departName", value = "部门名称", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:account:list")
    @RequestMapping("/list")
    public AjaxJson list(PurchaserAccount account, HttpServletRequest request, HttpServletResponse response) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if (account == null) {
            account = new PurchaserAccount();
        }
        account.setPurchaserId(currentAccount.getPurchaserId());
        account.setIsAdmin("0");
        //只查询子账号
        //分页写法
        Page<PurchaserAccount> page = purchaserAccountService.findPage(new Page<PurchaserAccount>(request, response), account);
        return AjaxJson.layuiTable(page);
        //List<PurchaserAccount> accountList = purchaserAccountService.findList(account);
        //return AjaxJson.layuiTable(accountList);

    }

    /**
     * 账号信息
     *
     * @return
     */
    @ApiOperation(notes = "info", httpMethod = "GET", value = "账号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "账号id", required = true, paramType = "query", dataType = "String")
    })
    @RequiresPermissions(value = {"purchaser:account:view", "purchaser:account:add", "purchaser:account:edit"}, logical = Logical.OR)
    @RequestMapping("/info")
    public AjaxJson info(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserAccount account = purchaserAccountService.get(id);
        if (account == null) {
            return AjaxJson.fail("无此账号信息");
        }
        if (!account.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        account.setRoleList(PurchaserUtils.getRoleListFromDb(account));
        return AjaxJson.ok(account);
    }

    /**
     * 获取采购商角色列表
     * @return
     */
    @ApiOperation(notes = "allrole", httpMethod = "GET", value = "获取采购商所有角色列表，供编辑账号使用")
    @RequiresRoles("purchaser")
    @RequestMapping("/allrole")
    public AjaxJson allRole() {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        PurchaserRole role = new PurchaserRole();
        role.setPurchaserId(currentAccount.getPurchaserId());
        List<PurchaserRole> roleList = purchaserRoleService.findAllList(role);
        //
        List<Map<String, String>> allRole = new ArrayList<>();
        for(PurchaserRole r:roleList){
            Map<String,String> m = new HashMap<>();
            m.put("id",r.getId());
            m.put("name",r.getName());
            allRole.add(m);
        }
        return AjaxJson.ok(allRole);
    }


    /**
     * 账号保存
     *
     * @return
     */
    @ApiOperation(notes = "save", httpMethod = "POST", value = "账号保存", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "账号id（修改必传，新增为空）", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "loginName", value = "登录账号（不允许修改）", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "realName", value = "真实姓名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码，不修改密码留空", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "departName", value = "部门名称", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "备注", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "isClosed", value = "是否停用(1,0)", required = true, defaultValue = "0", paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "roleIdList", value = "权限列表（权限id数组）", required = true, allowMultiple = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions(value={"purchaser:account:add","purchaser:account:edit"},logical= Logical.OR)
    @RequestMapping("/save")
    public AjaxJson save(PurchaserAccount account) throws Exception {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        boolean isnew = StringUtils.isBlank(account.getId()) ? true : false;
        if (isnew) {
            //检查必填
            if (StringUtils.isBlank(account.getLoginName())) {
                return AjaxJson.fail("登录账号不能为空");
            }
            if (StringUtils.isBlank(account.getRealName())) {
                return AjaxJson.fail("真实姓名不能为空");
            }
            if (StringUtils.isBlank(account.getNewPassword())) {
                return AjaxJson.fail("密码不能为空");
            }
            //检查账号重复
            PurchaserAccount checker = purchaserAccountService.findUniqueByProperty("login_name",account.getLoginName());
            if(checker!=null){
                return AjaxJson.fail("登录账号重复");
            }
            if (StringUtils.isBlank(account.getIsClosed())) {
                account.setIsClosed("0");
            }
            account.setIsAdmin("0");
            account.setPurchaserId(currentAccount.getPurchaserId());
            account.setCreateById(currentAccount.getId());
            account.setUpdateById(currentAccount.getId());
            //检查用户中心
            ExecuteResult<String> isregResult = UserApi.userIsRegistered(account.getLoginName());
            if(isregResult.isSuccess()){
                return AjaxJson.fail("此用户未在海尔用户中心注册，请先去注册");
            }
            ExecuteResult<HaierUserDTO> loginResult = UserApi.login(account.getLoginName(),account.getNewPassword());
            if(!loginResult.isSuccess()){
                return AjaxJson.fail(loginResult.getError(), UserApiErrorDesc.LoginError.getDesc(loginResult.getError()));
            }
        } else {
            PurchaserAccount entity = purchaserAccountService.get(account.getId());
            if (entity == null) {
                return AjaxJson.fail("无此账号");
            }
            if (!entity.getPurchaserId().equals(currentAccount.getPurchaserId())) {
                return AjaxJson.fail("非法访问");
            }
            if (StringUtils.isNotBlank(account.getLoginName())) {
               if(!entity.getLoginName().equalsIgnoreCase(account.getLoginName())){
                   //检查账号重复
                   PurchaserAccount checker = purchaserAccountService.findUniqueByProperty("login_name",account.getLoginName());
                   if(checker!=null){
                       return AjaxJson.fail("登录账号重复");
                   }
               }
            }
            account.setUpdateById(currentAccount.getId());
            MyBeanUtils.copyBeanNotNull2Bean(account, entity);
            account = entity;
        }
        //设置密码
        if(StringUtils.isNotBlank(account.getNewPassword())){
            //采购商的密码不做加密处理
            account.setPasswd(account.getNewPassword());
            //account.setPasswd(SystemService.entryptPassword(account.getNewPassword()));
        }
        // 角色数据有效性验证，过滤不在授权内的角色
        List<PurchaserRole> roleList = Lists.newArrayList();
        List<String> roleIdList = account.getRoleIdList();
        if(roleIdList==null || roleIdList.size()==0){
            return AjaxJson.fail("权限不能为空");
        }
        PurchaserRole role = new PurchaserRole();
        role.setPurchaserId(account.getPurchaserId());
        for (PurchaserRole r : purchaserRoleService.findList(role)) {
            if (roleIdList.contains(r.getId())) {
                roleList.add(r);
            }
        }
        account.setRoleList(roleList);
        //参数验证
        BeanValidators.validator(account);
        purchaserAccountService.save(account);
        return AjaxJson.ok();
    }

    /**
     * 账号删除
     *
     * @return
     */
    @ApiOperation(notes = "del", httpMethod = "POST", value = "账号删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "账号id", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:account:del")
    @RequestMapping("/del")
    public AjaxJson del(String id) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(id)){
            return AjaxJson.fail("参数为空");
        }
        PurchaserAccount account = purchaserAccountService.get(id);
        if (account == null) {
            return AjaxJson.fail("无此账号信息");
        }
        if (!account.getPurchaserId().equals(currentAccount.getPurchaserId())) {
            return AjaxJson.fail("非法访问");
        }
        purchaserAccountService.delete(account);
        return AjaxJson.ok();
    }

    /**
     * 账号删除
     *
     * @return
     */
    @ApiOperation(notes = "batchdel", httpMethod = "POST", value = "账号删除(批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "账号id逗号分隔", required = true, paramType = "form", dataType = "String")
    })
    @RequiresPermissions("purchaser:account:del")
    @RequestMapping("/batchdel")
    public AjaxJson batchdel(String ids) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if(StringUtils.isBlank(ids)){
            return AjaxJson.fail("参数为空");
        }
        String idArray[] =ids.split(",");
        for(String id : idArray) {
            PurchaserAccount account = purchaserAccountService.get(id);
            if (account == null) {
                return AjaxJson.fail("无此账号信息");
            }
            if (!account.getPurchaserId().equals(currentAccount.getPurchaserId())) {
                return AjaxJson.fail("非法访问");
            }
            purchaserAccountService.delete(account);
        }
        return AjaxJson.ok();
    }

    /**
     * 修改个人密码
     *
     * @return
     */
    @ApiOperation(notes = "modifypwd", httpMethod = "POST", value = "修改个人密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldpwd", value = "原密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "newpwd", value = "新密码", required = true, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/modifypwd")
    public AjaxJson modifypwd(String oldpwd, String newpwd) {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        if (StringUtils.isBlank(oldpwd) || StringUtils.isBlank(newpwd)) {
            return AjaxJson.fail("新老密码不能为空");
        }
        return purchaserAccountService.modifypwd(currentAccount, oldpwd, newpwd);
    }

    /**
     * 更新个人信息
     *
     * @return
     */
    @ApiOperation(notes = "updateinfo", httpMethod = "POST", value = "更新个人信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "真实姓名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "departName", value = "部门名称", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "remarks", value = "备注", required = false, paramType = "form", dataType = "String")
    })
    @RequiresRoles("purchaser")
    @RequestMapping("/updateinfo")
    public AjaxJson updateInfo(PurchaserAccount account) throws Exception {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        PurchaserAccount entity = purchaserAccountService.get(currentAccount.getId());
        if (entity == null) {
            return AjaxJson.fail("无此账号");
        }
        //参数验证
        BeanValidators.validator(account);
        account.setId(currentAccount.getId());
        account.setPurchaserId(currentAccount.getPurchaserId());
        MyBeanUtils.copyBeanNotNull2Bean(account, entity);
        purchaserAccountService.updateAccountInfo(entity);
        return AjaxJson.ok();
    }

    /**
     * 查看个人信息
     *
     * @return
     */
    @ApiOperation(notes = "viewinfo", httpMethod = "GET", value = "查看个人信息")
    @RequiresRoles("purchaser")
    @RequestMapping("/viewinfo")
    public AjaxJson viewInfo() {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        return AjaxJson.ok(currentAccount);
    }

    /**
     * 获取个人权限列表
     *
     * @return
     */
    @ApiOperation(notes = "getPermissions", httpMethod = "GET", value = "获取个人权限列表")
    @RequiresRoles("purchaser")
    @RequestMapping("/getPermissions")
    public AjaxJson getPermissions() {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        List<PurchaserMenu> menuList = PurchaserUtils.getMenuList();
        List<String> permissionList = new ArrayList<>();
        for (PurchaserMenu menu : menuList){
            if (org.apache.commons.lang3.StringUtils.isNotBlank(menu.getPermission())){
                for (String permission : org.apache.commons.lang3.StringUtils.split(menu.getPermission(),",")){
                    permissionList.add(permission);
                }
            }
        }
        return AjaxJson.ok(permissionList);
    }

    /**
     * 获取个人菜单列表
     *
     * @return
     */
    @ApiOperation(notes = "getMenus", httpMethod = "GET", value = "获取个人菜单列表")
    @RequiresRoles("purchaser")
    @RequestMapping("/getMenus")
    public AjaxJson getMenus() {
        PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
        if(currentAccount == null){
            return AjaxJson.fail("账号异常");
        }
        List<PurchaserMenu> list = PurchaserUtils.getMenuList();
        List<MenuNode> menuList = purchaserAccountService.processMenu(list);
        //考虑对菜单的处理
        return AjaxJson.ok(menuList);
    }
}
