package com.jhmis.modules.directpurchasergood.web;


import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserAccountService;
import com.jhmis.modules.directpurchaser.service.DirectPurchaserService;
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.IndustryService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/shop/directpurchaser/directpurchaser1")
public class DirectPurchaserGoodController extends BaseController {
    @Autowired
    private DirectPurchaserService directPurchaserService;
    @Autowired
    private DirectPurchaserAccountService directPurchaserAccountService;
    @Autowired
    private IndustryService industryService;
    @ModelAttribute
    public Purchaser get(@RequestParam(required=false) String id) {
        System.out.println("33333333333333333333333");
        Purchaser entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = directPurchaserService.get(id);
        }
        if (entity == null){
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
    @RequiresPermissions("shop:directpurchaser:directpurchaser:list")
    @RequestMapping(value = "subreldata")
    public Map<String, Object> subreldata(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Purchaser> page = directPurchaserService.getSubRelPurchaser(new Page<Purchaser>(request, response), purchaser);
        return getBootstrapData(page);
    }
    /**
     * 采购商列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:directpurchaser:directpurchaser:list")
    @RequestMapping(value = "noreldata")
    public Map<String, Object> noreldata(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
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
        if(purchaserId == null || "".equals(purchaserId)){
            map.put("rows","[]");
            map.put("total",0);
        }else{
            PurchaserAccount account = new PurchaserAccount();
            account.setPurchaserId(purchaserId);
            List<PurchaserAccount> list = directPurchaserAccountService.findList(account);
            map.put("rows",list);
            map.put("total", list.size());
        }
        return map;
    }
    /**
     * 查看，增加，编辑采购商表单页面
     */
    @RequiresPermissions(value={"shop:directpurchaser:directpurchaser:add","shop:directpurchaser:directpurchaser:edit"},logical= Logical.OR)
    @RequestMapping(value = "form")
    public String form(Purchaser purchaser, Model model) {
        //读取行业信息
        List<Industry> industryList = industryService.findList(new Industry());
        model.addAttribute("purchaser", purchaser);
        model.addAttribute("industryList", industryList);
        if(StringUtils.isBlank(purchaser.getId())){//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/shop/directpurchaser/directPurchaserForm";
    }
    /**
     * 采购商詳情表单页面
     */
    @RequiresPermissions(value={"shop:directpurchaser:directpurchaser:view"},logical=Logical.OR)
    @RequestMapping(value = "view")
    public String view(Purchaser purchaser, Model model) {
        //读取行业信息
        List<Industry> industryList = industryService.findList(new Industry());
        PurchaserAccount account=null;
        model.addAttribute("purchaser", purchaser);
        model.addAttribute("industryList", industryList);
        if(purchaser!=null){
            account=directPurchaserAccountService.getAdminByPurchaserId(purchaser.getId());
        }
        model.addAttribute("purchaserAccount", account);
        if(StringUtils.isBlank(purchaser.getId())){//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/shop/directpurchaser/directPurchaserView";
    }
    /**
     * 采购商詳情表单页面
     */
    @RequiresPermissions(value={"shop:directpurchaser:directpurchaser:auditView"},logical=Logical.OR)
    @RequestMapping(value = "auditView")
    public String auditView(Purchaser purchaser, Model model) {
        //读取行业信息
        List<Industry> industryList = industryService.findList(new Industry());
        PurchaserAccount account=null;
        model.addAttribute("purchaser", purchaser);
        model.addAttribute("industryList", industryList);
        if(purchaser!=null){
            account=directPurchaserAccountService.getAdminByPurchaserId(purchaser.getId());
        }
        model.addAttribute("purchaserAccount", account);
        if(StringUtils.isBlank(purchaser.getId())){//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/shop/directpurchaser/directPurchaserAuditView";
    }
    @RequiresPermissions(value={"shop:directpurchaser:directpurchaser:view","shop:directpurchaser:directpurchaser:add","shop:directpurchaser:directpurchaser:edit"},logical=Logical.OR)
    @RequestMapping(value = "accountForm")
    public String accountForm(String id, String purchaserId, Model model) {
        PurchaserAccount purchaserAccount;
        if(id == null || "".equals(id)){
            purchaserAccount =  new PurchaserAccount();
            purchaserAccount.setIsAdmin("1");
            purchaserAccount.setIsClosed("0");
        }else{
            purchaserAccount = directPurchaserAccountService.get(id);
            purchaserAccount.setRoleList(PurchaserUtils.getRoleListFromDb(purchaserAccount));
        }

        purchaserAccount.setPurchaserId(purchaserId);
        model.addAttribute("purchaserAccount", purchaserAccount);
        return "modules/shop/directpurchaser/directpurchaserAccountForm";
    }


    @RequiresPermissions(value={"shop:directpurchaser:directpurchaser:view","shop:directpurchaser:directpurchaser:add","shop:directpurchaser:directpurchaser:edit"},logical=Logical.OR)
    @RequestMapping(value = "auditPurchaser")
    public String auditPurchaser(String id, String companyType,String note,String auditState, Model model) {
        Purchaser purchaser=directPurchaserService.get(id);
        if(purchaser!=null){
            purchaser.setCompanyType(companyType);
            purchaser.setAuditDesc(note);
            purchaser.setAuditState(auditState);
        }
        directPurchaserService.updateinfo(purchaser);
        return "modules/shop/directpurchaser/directPurchaserList";
    }
}
