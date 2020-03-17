/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.dealer;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.service.dealer.DealerAccountService;
import com.jhmis.modules.shop.service.dealer.DealerService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.service.SystemService;
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
 * 供应商管理Controller
 * @author tity
 * @version 2018-07-29
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/dealer/dealer")
public class DealerController extends BaseController {

	@Autowired
	private DealerService dealerService;
    @Autowired
    private DealerAccountService dealerAccountService;
	@ModelAttribute
	public Dealer get(@RequestParam(required=false) String id) {
		Dealer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dealerService.get(id);
		}
		if (entity == null){
			entity = new Dealer();
		}
		return entity;
	}
	
	/**
	 * 供应商列表页面
	 */
	@RequiresPermissions("shop:dealer:dealer:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/dealer/dealerList";
	}
	
	/**
	 * 供应商列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealer:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Dealer dealer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Dealer> page = dealerService.findPage(new Page<Dealer>(request, response), dealer); 
		return getBootstrapData(page);
	}

    /**
     * 供应商账号列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:dealer:dealer:list")
    @RequestMapping(value = "accountData")
    public Map<String, Object> accountData(String dealerId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(dealerId == null || "".equals(dealerId)){
            map.put("rows","[]");
            map.put("total",0);
        }else{
            DealerAccount account = new DealerAccount();
            account.setDealerId(dealerId);
            List<DealerAccount> list = dealerAccountService.findList(account);
            map.put("rows",list);
            map.put("total", list.size());
        }
        return map;
    }

	/**
	 * 查看，增加，编辑供应商表单页面
	 */
	@RequiresPermissions(value={"shop:dealer:dealer:view","shop:dealer:dealer:add","shop:dealer:dealer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Dealer dealer, Model model) {
		model.addAttribute("dealer", dealer);
		if(StringUtils.isBlank(dealer.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/dealer/dealerForm";
	}

    @RequiresPermissions(value={"shop:dealer:dealer:view","shop:dealer:dealer:add","shop:dealer:dealer:edit"},logical=Logical.OR)
    @RequestMapping(value = "accountForm")
    public String accountForm(String id, String dealerId, Model model) {
        DealerAccount dealerAccount;
        if(id == null || "".equals(id)){
            dealerAccount =  new DealerAccount();
            dealerAccount.setIsAdmin("1");
            dealerAccount.setIsClosed("0");
        }else{
            dealerAccount = dealerAccountService.get(id);
            dealerAccount.setRoleList(DealerUtils.getRoleListFromDb(dealerAccount));
        }

        dealerAccount.setDealerId(dealerId);
        model.addAttribute("dealerAccount", dealerAccount);
        return "modules/shop/dealer/dealerAccountForm";
    }

	/**
	 * 保存供应商
	 */
	@RequiresPermissions(value={"shop:dealer:dealer:add","shop:dealer:dealer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Dealer dealer, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dealer)){
			return form(dealer, model);
		}
		if(StringUtils.isBlank(dealer.getScore())){
			dealer.setScore("0");
		}
		String gmname = dealer.getGmName();
		if(StringUtils.isNotBlank(gmname)){			
			if(gmname.substring(gmname.length()-2).equals("工贸") || gmname.substring(gmname.length()-2).equals("中心")){
				dealer.setGmName(gmname.substring(0, gmname.length()-2));
			}
		}
		String underProduct = dealer.getUnderProduct();
		if(StringUtils.isNotBlank(underProduct)){
			if(underProduct.substring(underProduct.length()-1).equals(",")){
				dealer.setUnderProduct(underProduct.substring(0, underProduct.length()-1));
			}
		}
		
		
		//新增或编辑表单保存
		dealerService.save(dealer);//保存
		addMessage(redirectAttributes, "保存供应商成功");
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealer/?repage";
	}

    /**
     * 保存供应商账号
     */
    @ResponseBody
    @RequiresPermissions(value={"shop:dealer:dealer:add","shop:dealer:dealer:edit"},logical=Logical.OR)
    @RequestMapping(value = "saveAccount")
    public AjaxJson saveAccount(DealerAccount dealerAccount, Model model, RedirectAttributes redirectAttributes) throws Exception{
        AjaxJson j = new AjaxJson();
        boolean isnew = dealerAccount.getIsNewRecord();

        if (isnew) {
            //检查必填
            if (StringUtils.isBlank(dealerAccount.getLoginName())) {
                return AjaxJson.fail("登录账号不能为空");
            }
            if (StringUtils.isBlank(dealerAccount.getNewPassword())) {
                return AjaxJson.fail("密码不能为空");
            }
            //检查账号重复
            DealerAccount checker = dealerAccountService.findUniqueByProperty("login_name",dealerAccount.getLoginName());
            if(checker!=null){
                return AjaxJson.fail("登录账号重复");
            }
            if (StringUtils.isBlank(dealerAccount.getIsClosed())) {
                dealerAccount.setIsClosed("0");
            }
            dealerAccount.setIsAdmin("1");
        } else {
            DealerAccount entity = dealerAccountService.get(dealerAccount.getId());
            if (entity == null) {
                return AjaxJson.fail("无此账号");
            }
            if (StringUtils.isNotBlank(dealerAccount.getLoginName())) {
                if(!entity.getLoginName().equalsIgnoreCase(dealerAccount.getLoginName())){
                    //检查账号重复
                    DealerAccount checker = dealerAccountService.findUniqueByProperty("login_name",dealerAccount.getLoginName());
                    if(checker!=null){
                        return AjaxJson.fail("登录账号重复");
                    }
                }
            }
        }

        //设置密码
        if(StringUtils.isNotBlank(dealerAccount.getNewPassword())){
            dealerAccount.setPasswd(SystemService.entryptPassword(dealerAccount.getNewPassword()));
        }
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())){
            dealerAccount.setUpdateById(user.getId()) ;
            dealerAccount.setCreateById(user.getId()) ;
        }
        dealerAccountService.save(dealerAccount);//新建或者编辑保存
        j.setSuccess(true);
        j.setMsg("保存供应商账号成功");
        return j;
    }
	/**
	 * 删除供应商
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealer:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Dealer dealer, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		dealerService.delete(dealer);
		j.setMsg("删除供应商成功");
		return j;
	}

    /**
     * 删除供应商账号
     */
    @ResponseBody
    @RequiresPermissions("shop:dealer:dealer:edit")
    @RequestMapping(value = "deleteAccount")
    public AjaxJson deleteAccount(DealerAccount dealerAccount, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        dealerAccountService.delete(dealerAccount);
        j.setMsg("删除供应商账号成功");
        return j;
    }

	/**
	 * 批量删除供应商
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealer:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dealerService.delete(dealerService.get(id));
		}
		j.setMsg("删除供应商成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealer:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Dealer dealer, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "供应商"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Dealer> page = dealerService.findPage(new Page<Dealer>(request, response, -1), dealer);
    		new ExportExcel("供应商", Dealer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出供应商记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:dealer:dealer:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Dealer> list = ei.getDataList(Dealer.class);
			for (Dealer dealer : list){
				try{
					dealerService.save(dealer);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条供应商记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条供应商记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入供应商失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealer/?repage";
    }
	
	/**
	 * 下载导入供应商数据模板
	 */
	@RequiresPermissions("shop:dealer:dealer:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "供应商数据导入模板.xlsx";
    		List<Dealer> list = Lists.newArrayList(); 
    		new ExportExcel("供应商数据", Dealer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealer/?repage";
    }

    /**
     * 审核
     * @return
     */
    @ResponseBody
    @RequiresPermissions("shop:dealer:dealer:audit")
    @RequestMapping(value = "audit", method=RequestMethod.POST)
    public AjaxJson audit(Dealer dealer) {
        if(StringUtils.isBlank(dealer.getId())){
            return AjaxJson.fail("供应商不存在");
        }
        if(!Global.AUDIT_STATE_WAIT.equals(dealer.getAuditState())){
            return AjaxJson.fail("待审核状态才能审核");
        }
        if(Global.AUDIT_STATE_NO.equals(dealer.getAuditState()) && StringUtils.isBlank(dealer.getAuditDesc())){
            return AjaxJson.fail("请填写不同意的原因");
        }
        dealerService.audit(dealer);
        return AjaxJson.ok();
    }
}