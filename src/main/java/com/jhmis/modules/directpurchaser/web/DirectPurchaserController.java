/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.directpurchaser.web;

import com.google.common.collect.Lists;
import com.haier.mdm.qygcx.CustomerInfoDateModel;
import com.haier.order.utils.OrderServiceUtils;
import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.haier.util.MDM_ADD;
import com.haier.util.MDM_QYG;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserAccountService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserService;
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;
import com.jhmis.modules.shop.service.IndustryService;
import com.jhmis.modules.shop.service.purchaser.PurchaserInvoiceService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购商管理Controller
 *
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/directpurchaser/directpurchaser")
public class DirectPurchaserController extends BaseController {

    @Autowired
    private DirectPurchaserService directPurchaserService;
    @Autowired
    private DirectPurchaserAccountService directPurchaserAccountService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private PurchaserInvoiceService purchaserInvoiceService;

    @ModelAttribute
    public Purchaser get(@RequestParam(required = false) String id) {
        System.out.println("33333333333333333333333");
        Purchaser entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = directPurchaserService.get(id);
        }
        if (entity == null) {
            entity = new Purchaser();
        }
        return entity;
    }

    /**
     * 采购商列表页面
     */
    @RequiresPermissions("shop:directpurchaser:directpurchaser:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        System.out.println("888888888888888888888888888888888");
        return "modules/shop/directpurchaser/directPurchaserList";
    }

    /**
     * 去采购商子列表页面
     */
    @RequiresPermissions("shop:directpurchaser:directpurchaser:list")
    @RequestMapping(value = "toSubRelList")
    public String toSubRelList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String pid = request.getParameter("id");
        model.addAttribute("pid", pid);
        model.addAttribute("purchaser", new Purchaser());
        return "modules/shop/directpurchaser/directPurchaserSubRelList";
    }

    /**
     * 去设置采购商子列表页面
     */
    @RequiresPermissions("shop:directpurchaser:directpurchaser:list")
    @RequestMapping(value = "toNoRelList")
    public String toNoRelList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String pid = request.getParameter("pid");
        model.addAttribute("pid", pid);
        return "modules/shop/directpurchaser/directPurchaserSubList";
    }

    /**
     * 采购商列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println("9999999999999999999999");
        Page<Purchaser> page = directPurchaserService.findPage(new Page<Purchaser>(request, response), purchaser);
        return getBootstrapData(page);
    }


    /**
     * 采购商列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:subreldata")
    @RequestMapping(value = "subreldata")
    public Map<String, Object> subreldata(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        System.out.println("==============id===========" + id);
        purchaser.setId(id);
        Page<Purchaser> page = directPurchaserService.getSubRelPurchaser(new Page<Purchaser>(request, response), purchaser);
        return getBootstrapData(page);
    }


    /**
     * 采购商列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:noreldata")
    @RequestMapping(value = "noreldata")
    public Map<String, Object> noreldata(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
        String id = request.getParameter("id");
        purchaser.setId(id);
        Page<Purchaser> page = directPurchaserService.getNoRelPurchaser(new Page<Purchaser>(request, response), purchaser);
        return getBootstrapData(page);
    }


    /**
     * 采购商账号列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:list")
    @RequestMapping(value = "accountData")
    public Map<String, Object> accountData(String purchaserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (purchaserId == null || "".equals(purchaserId)) {
            map.put("rows", "[]");
            map.put("total", 0);
        } else {
            PurchaserAccount account = new PurchaserAccount();
            account.setPurchaserId(purchaserId);
            List<PurchaserAccount> list = directPurchaserAccountService.findList(account);
            map.put("rows", list);
            map.put("total", list.size());
        }
        return map;
    }

    /**
     * 查看，增加，编辑采购商表单页面
     */
    @RequiresPermissions(value = {"shop:directpurchaser:directpurchaser:add", "shop:directpurchaser:directpurchaser:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Purchaser purchaser, Model model) {
        //读取行业信息
        List<Industry> industryList = industryService.findList(new Industry());
        model.addAttribute("purchaser", purchaser);
        model.addAttribute("industryList", industryList);
        if (StringUtils.isBlank(purchaser.getId())) {//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/shop/directpurchaser/directPurchaserForm";
    }


    /**
     * 采购商詳情表单页面
     */
    @RequiresPermissions(value = {"shop:directpurchaser:directpurchaser:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String view(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
        //读取行业信息
        List<Industry> industryList = industryService.findList(new Industry());
        PurchaserAccount account = null;
        if (StringUtils.isNotBlank(purchaser.getLoginNum())) {
            double creditBalance = 0.00;
            try {
                creditBalance = directPurchaserAccountService.getCreditBalance(purchaser.getLoginNum());
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            System.out.println("d:=" + nf.format(creditBalance));
            purchaser.setCreditLineStr(nf.format(creditBalance));
        }
        model.addAttribute("purchaser", purchaser);
        model.addAttribute("industryList", industryList);
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            account = directPurchaserAccountService.getAdminByPurchaserId(id);
        }
        List<Purchaser> subList = directPurchaserService.getSubPurchaserList(id);
        model.addAttribute("purchaserSubList", subList);
        model.addAttribute("purchaserAccount", account);
        if (StringUtils.isBlank(purchaser.getId())) {//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/shop/directpurchaser/directPurchaserView";
    }

    /**
     * 采购商詳情表单页面
     */
    @RequiresPermissions(value = {"shop:directpurchaser:directpurchaser:auditView"}, logical = Logical.OR)
    @RequestMapping(value = "auditView")
    public String auditView(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
        //读取行业信息
        List<Industry> industryList = industryService.findList(new Industry());
        PurchaserAccount account = null;
        if (StringUtils.isNotBlank(purchaser.getLoginNum())) {
            double creditBalance = 0.00;
            try {

                creditBalance = directPurchaserAccountService.getCreditBalance(purchaser.getLoginNum());
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            System.out.println("d:=" + nf.format(creditBalance));
            purchaser.setCreditLineStr(nf.format(creditBalance));
        }
        model.addAttribute("purchaser", purchaser);
        model.addAttribute("industryList", industryList);
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            account = directPurchaserAccountService.getAdminByPurchaserId(id);
        }
        List<Purchaser> subList = directPurchaserService.getSubPurchaserList(id);
        model.addAttribute("purchaserSubList", subList);
        model.addAttribute("purchaserAccount", account);
        if (StringUtils.isBlank(purchaser.getId())) {//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/shop/directpurchaser/directPurchaserAuditView";
    }


    @RequiresPermissions(value = {"shop:directpurchaser:directpurchaser:view", "shop:directpurchaser:directpurchaser:add", "shop:directpurchaser:directpurchaser:edit"}, logical = Logical.OR)
    @RequestMapping(value = "accountForm")
    public String accountForm(String id, String purchaserId, Model model) {
        PurchaserAccount purchaserAccount;
        if (id == null || "".equals(id)) {
            purchaserAccount = new PurchaserAccount();
            purchaserAccount.setIsAdmin("1");
            purchaserAccount.setIsClosed("0");
        } else {
            purchaserAccount = directPurchaserAccountService.get(id);
            purchaserAccount.setRoleList(PurchaserUtils.getRoleListFromDb(purchaserAccount));
        }

        purchaserAccount.setPurchaserId(purchaserId);
        model.addAttribute("purchaserAccount", purchaserAccount);
        return "modules/shop/directpurchaser/directpurchaserAccountForm";
    }


//    @RequiresPermissions(value={"shop:directpurchaser:directpurchaser:view","shop:directpurchaser:directpurchaser:add","shop:directpurchaser:directpurchaser:edit"},logical=Logical.OR)
//    @RequestMapping(value = "auditPurchaser")
//    public String auditPurchaser(String id, String companyType,String note,String auditState, Model model) {
//        Purchaser purchaser=directPurchaserService.get(id);
//        if(purchaser!=null){
//        	purchaser.setCompanyType(companyType);
//        	purchaser.setAuditDesc(note);
//        	purchaser.setAuditState(auditState);
//        }
//        directPurchaserService.updateinfo(purchaser);
//        return "modules/shop/directpurchaser/directPurchaserList";
//    }

    /**
     * 保存采购商
     */
    @RequiresPermissions(value = {"shop:directpurchaser:directpurchaser:add", "shop:directpurchaser:directpurchaser:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(Purchaser purchaser, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, purchaser)) {
            return form(purchaser, model);
        }
        //新增或编辑表单保存
        directPurchaserService.save(purchaser);//保存
        addMessage(redirectAttributes, "保存采购商成功");
        return "redirect:" + Global.getAdminPath() + "/shop/directpurchaser/directpurchaser/?repage";
    }

    /**
     * 保存采购商账号
     */
    @ResponseBody
    @RequiresPermissions(value = {"shop:directpurchaser:directpurchaser:add", "shop:directpurchaser:directpurchaser:edit"}, logical = Logical.OR)
    @RequestMapping(value = "saveAccount")
    public AjaxJson saveAccount(PurchaserAccount purchaserAccount, Model model, RedirectAttributes redirectAttributes) throws Exception {
        AjaxJson j = new AjaxJson();
        boolean isnew = purchaserAccount.getIsNewRecord();

        if (isnew) {
            //检查必填
            if (StringUtils.isBlank(purchaserAccount.getLoginName())) {
                return AjaxJson.fail("登录账号不能为空");
            }
            if (StringUtils.isBlank(purchaserAccount.getNewPassword())) {
                return AjaxJson.fail("密码不能为空");
            }
            //检查账号重复
            PurchaserAccount checker = directPurchaserAccountService.findUniqueByProperty("login_name", purchaserAccount.getLoginName());
            if (checker != null) {
                return AjaxJson.fail("登录账号重复");
            }
            if (StringUtils.isBlank(purchaserAccount.getIsClosed())) {
                purchaserAccount.setIsClosed("0");
            }
            purchaserAccount.setIsAdmin("1");
            //检查用户中心
            ExecuteResult<String> isregResult = UserApi.userIsRegistered(purchaserAccount.getLoginName());
            if (isregResult.isSuccess()) {
                return AjaxJson.fail("此用户未在海尔用户中心注册，请先去注册");
            }
            ExecuteResult<HaierUserDTO> loginResult = UserApi.login(purchaserAccount.getLoginName(), purchaserAccount.getNewPassword());
            if (!loginResult.isSuccess()) {
                return AjaxJson.fail(loginResult.getError(), UserApiErrorDesc.LoginError.getDesc(loginResult.getError()));
            }
        } else {
            PurchaserAccount entity = directPurchaserAccountService.get(purchaserAccount.getId());
            if (entity == null) {
                return AjaxJson.fail("无此账号");
            }
            if (StringUtils.isNotBlank(purchaserAccount.getLoginName())) {
                if (!entity.getLoginName().equalsIgnoreCase(purchaserAccount.getLoginName())) {
                    //检查账号重复
                    PurchaserAccount checker = directPurchaserAccountService.findUniqueByProperty("login_name", purchaserAccount.getLoginName());
                    if (checker != null) {
                        return AjaxJson.fail("登录账号重复");
                    }
                }
            }
        }

        //设置密码
        if (StringUtils.isNotBlank(purchaserAccount.getNewPassword())) {
            //采购商的密码不做加密处理
            purchaserAccount.setPasswd(purchaserAccount.getNewPassword());
            //purchaserAccount.setPasswd(SystemService.entryptPassword(purchaserAccount.getNewPassword()));
        }
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())) {
            purchaserAccount.setUpdateById(user.getId());
            purchaserAccount.setCreateById(user.getId());
        }
        directPurchaserAccountService.save(purchaserAccount);//新建或者编辑保存
        j.setSuccess(true);
        j.setMsg("保存采购商账号成功");
        return j;
    }

    /**
     * 删除采购商账号
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:edit")
    @RequestMapping(value = "deleteAccount")
    public AjaxJson deleteAccount(PurchaserAccount purchaserAccount, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        directPurchaserAccountService.delete(purchaserAccount);
        j.setMsg("删除采购商账号成功");
        return j;
    }

    /**
     * 删除采购商
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(Purchaser purchaser, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        directPurchaserService.delete(purchaser);
        j.setMsg("删除采购商成功");
        return j;
    }

    /**
     * 批量删除采购商
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            directPurchaserService.delete(directPurchaserService.get(id));
        }
        j.setMsg("删除采购商成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public AjaxJson exportFile(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "采购商" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<Purchaser> page = directPurchaserService.findPage(new Page<Purchaser>(request, response, -1), purchaser);
            new ExportExcel("采购商", Purchaser.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出采购商记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("shop:directpurchaser:directpurchaser:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Purchaser> list = ei.getDataList(Purchaser.class);
            for (Purchaser purchaser : list) {
                try {
                    directPurchaserService.save(purchaser);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条采购商记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条采购商记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入采购商失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/shop/directpurchaser/directpurchaser/?repage";
    }

    /**
     * 下载导入采购商数据模板
     */
    @RequiresPermissions("shop:directpurchaser:directpurchaser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "采购商数据导入模板.xlsx";
            List<Purchaser> list = Lists.newArrayList();
            new ExportExcel("采购商数据", Purchaser.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/shop/directpurchaser/directpurchaser/?repage";
    }

    /**
     * 审核
     *
     * @return
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:audit")
    @RequestMapping(value = "auditPurchaser")
    public AjaxJson auditPurchaser(String id, String companyType, String auditDesc, String state) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isBlank(id)) {
            j.setSuccess(false);
            j.setMsg("采购商不存在");
            return j;
        }
        Purchaser purchaser = directPurchaserService.get(id);
        if (Global.AUDIT_STATE_NO.equals(state) && StringUtils.isBlank(auditDesc)) {
            j.setSuccess(false);
            j.setMsg("审核失败，请填写不同意的原因");
            return j;
        }
        System.out.println("dddddddddddddddddddddddddd");
        purchaser.setAuditState(state);
        purchaser.setAuditDesc(auditDesc);
        purchaser.setCompanyType(companyType);
        try {
            //如果审核通过，需要调用MDM接口保存客户信息
            if (Global.AUDIT_STATE_OK.equals(state)) {
                if (StringUtils.isNotBlank(purchaser.getAreaInfo())) {
                    String[] str = purchaser.getAreaInfo().split("/");
                    if (str.length > 1) {
                        purchaser.setCityName(str[1]);
                    }
                }
                PurchaserInvoice purchaserInvoice = new PurchaserInvoice();
                purchaserInvoice.setPurchaserId(purchaser.getId());
                List<PurchaserInvoice> purchaserInvoiceList = purchaserInvoiceService.findList(purchaserInvoice);
                if (purchaserInvoiceList.size() == 0) {
                    return AjaxJson.fail("采购商发票地址不能为空!");
                }
                purchaserInvoice = purchaserInvoiceList.get(0);
                purchaser.setVatregno(purchaserInvoice.getTaxCode());
                purchaser.setRegPhone(purchaserInvoice.getRegPhone());
                purchaser.setRegAddr(purchaserInvoice.getRegAddr());

                String companyName = purchaser.getCompanyName();
                if (StringUtils.isNotBlank(companyName)) {
                    Map map = MDM_QYG.Query("S00763", "HAIERMDM", "HRHAIERBTB_CUST_INFO", "CUSTOMER_NAME1", companyName, "IN");
                    String code = (String) map.get("return_code");
                    //此次注册是新增还是修改   1新增   2扩充
                    String type = "1";
                    //客户88码
                    String customcode = "";
                    //判断MDM是否在3200建户
                    boolean isHave = false;
                    System.out.println("校验接口返回：" + map);
                    if ("SUCCESS".equals(code)) {
                        Map map1 = MDM_QYG.readStringXmlOutInfo(map.get("result").toString());
                        List list = (List) map1.get("date");
                        if (list != null && list.size() > 0) {
                            CustomerInfoDateModel cid = (CustomerInfoDateModel) list.get(0);
                            Map map88 = MDM_QYG.Query("S00763", "HAIERMDM", "HRHAIERBTB_HM_CUSTOMERS", "CUSTOMER_NUMBER", cid.getCUSTOMER_NUMBER(), "=");
                            String code88 = (String) map88.get("return_code");
                            System.out.println("通过88码接口查询返回：" + map88);
                            customcode = cid.getCUSTOMER_NUMBER();
                            //校验客户是否在3200建户接口，成功返回数据说明是3200
                            if ("SUCCESS".equals(code88)) {
                                isHave = true;
                            }
                            if (isHave) {
                                purchaser.setLoginNum(customcode);
                                PurchaserAccount account = directPurchaserAccountService.getAdminByPurchaserId(purchaser.getId());
                                if (account != null) {
                                    account.setLoginNum(customcode);
                                    directPurchaserAccountService.updateAccountInfo(account);
                                }
                            } else {
                                type = "2";
                                purchaser.setLoginNum(customcode);
                                Map mapnew = MDM_ADD.organizationDate(purchaser, type);
                                if (mapnew != null) {
                                    if ("S".equals(mapnew.get("retcode"))) {
                                        String loginNum = mapnew.get("customcode").toString();
                                        purchaser.setLoginNum(loginNum);
                                        PurchaserAccount account = directPurchaserAccountService.getAdminByPurchaserId(purchaser.getId());
                                        if (account != null) {
                                            account.setLoginNum(loginNum);
                                            directPurchaserAccountService.updateAccountInfo(account);
                                        }
                                    } else {
                                        j.setSuccess(false);
                                        j.setMsg(mapnew.get("msg").toString());
                                        return j;
                                    }
                                }
                            }
                        }
                    } else {
                        Map mapnew = MDM_ADD.organizationDate(purchaser, type);
                        System.out.println("注册接口返回：" + mapnew);
                        if (mapnew != null) {
                            if ("S".equals(mapnew.get("retcode"))) {
                                String loginNum = mapnew.get("customcode").toString();
                                purchaser.setLoginNum(loginNum);
                                PurchaserAccount account = directPurchaserAccountService.getAdminByPurchaserId(purchaser.getId());
                                if (account != null) {
                                    account.setLoginNum(loginNum);
                                    directPurchaserAccountService.updateAccountInfo(account);
                                }
                            } else {
                                j.setSuccess(false);
                                j.setMsg(mapnew.get("msg").toString());
                                return j;
                            }
                        }
                    }

                }
            }

            directPurchaserService.audit(purchaser);
            j.setSuccess(true);
            j.setMsg("审核成功！");
            String content = "您的注册信息已审核通过，登录帐号为" + purchaser.getLoginNum() + "，初始密码与帐号一致，如有疑问请联系客服。【企业购】";

            //调用接口生成定时任务调用短信服务
            OrderServiceUtils.sendMsg(purchaser.getMobile(), content, DateUtils.getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("审核失败！");
            return j;
        }

        return j;
    }
}