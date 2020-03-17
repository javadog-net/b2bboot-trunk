/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.purchaser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.IndustryService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
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
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaser")
public class PurchaserController extends BaseController {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private PurchaserService purchaserService;
    @Autowired
    private PurchaserAccountService purchaserAccountService;
    @Autowired
    private IndustryService industryService;
	@ModelAttribute
	public Purchaser get(@RequestParam(required=false) String id) {
		Purchaser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserService.get(id);
		}
		if (entity == null){
			entity = new Purchaser();
		}
		return entity;
	}
	
	/**
	 * 采购商列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaser:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/purchaser/purchaserList";
	}
	
	/**
	 * 采购商列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaser:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Purchaser> page = purchaserService.findPage(new Page<Purchaser>(request, response), purchaser); 
		return getBootstrapData(page);
	}

    /**
     * 采购商账号列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:purchaser:purchaser:list")
    @RequestMapping(value = "accountData")
    public Map<String, Object> accountData(String purchaserId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(purchaserId == null || "".equals(purchaserId)){
            map.put("rows","[]");
            map.put("total",0);
        }else{
            PurchaserAccount account = new PurchaserAccount();
            account.setPurchaserId(purchaserId);
            List<PurchaserAccount> list = purchaserAccountService.findList(account);
            map.put("rows",list);
            map.put("total", list.size());
        }
        return map;
    }
    
	/**
	 * 查看，增加，编辑采购商表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaser:view","shop:purchaser:purchaser:add","shop:purchaser:purchaser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Purchaser purchaser, Model model) {
	    //读取行业信息
        List<Industry> industryList = industryService.findList(new Industry());
		model.addAttribute("purchaser", purchaser);
        model.addAttribute("industryList", industryList);
		if(StringUtils.isBlank(purchaser.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/purchaser/purchaserForm";
	}

    @RequiresPermissions(value={"shop:purchaser:purchaser:view","shop:purchaser:purchaser:add","shop:purchaser:purchaser:edit"},logical=Logical.OR)
    @RequestMapping(value = "accountForm")
    public String accountForm(String id, String purchaserId, Model model) {
        PurchaserAccount purchaserAccount;
        if(id == null || "".equals(id)){
            purchaserAccount =  new PurchaserAccount();
            purchaserAccount.setIsAdmin("1");
            purchaserAccount.setIsClosed("0");
        }else{
            purchaserAccount = purchaserAccountService.get(id);
            purchaserAccount.setRoleList(PurchaserUtils.getRoleListFromDb(purchaserAccount));
        }

        purchaserAccount.setPurchaserId(purchaserId);
        model.addAttribute("purchaserAccount", purchaserAccount);
        return "modules/shop/purchaser/purchaserAccountForm";
    }
    
	/**
	 * 保存采购商
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaser:add","shop:purchaser:purchaser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Purchaser purchaser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, purchaser)){
			return form(purchaser, model);
		}
		//新增或编辑表单保存
		purchaserService.save(purchaser);//保存
		addMessage(redirectAttributes, "保存采购商成功");
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaser/?repage";
	}


	/**
	 * 采购商登录
	 * @param number
	 * @param password
	 * @param code
	 * @return
	 */

	@RequestMapping(value = "login")
	@RequiresPermissions(value={"shop:purchaser:purchaser:login","shop:purchaser:purchaser:edit"},logical=Logical.OR)

	public AjaxJson login(HttpServletResponse response, HttpServletRequest request,
						  @RequestParam(name = "number",defaultValue = "888888")String  number,
						  @RequestParam(name = "password",defaultValue = "123456")String password,
						  @RequestParam(name = "code",defaultValue = "7566")String code){
		System.out.println("number: "+number);
		System.out.println("password: "+password);
		System.out.println("code: "+code);
		if (StringUtils.isBlank(number)) {
			return AjaxJson.fail("88码不能为空");
		}
		if (StringUtils.isBlank(password)) {
			return AjaxJson.fail("密码不能为空");
		}

		if (StringUtils.isBlank(code)){
			return AjaxJson.fail("推荐码不能为空");
		}

		return purchaserAccountService.loginNumber(number, password);

	}

    /**
     * 保存采购商账号
     */
    @ResponseBody
    @RequiresPermissions(value={"shop:purchaser:purchaser:add","shop:purchaser:purchaser:edit"},logical=Logical.OR)
    @RequestMapping(value = "saveAccount")
    public AjaxJson saveAccount(PurchaserAccount purchaserAccount, Model model, RedirectAttributes redirectAttributes) throws Exception{
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
            PurchaserAccount checker = purchaserAccountService.findUniqueByProperty("login_name",purchaserAccount.getLoginName());
            if(checker!=null){
                return AjaxJson.fail("登录账号重复");
            }
            if (StringUtils.isBlank(purchaserAccount.getIsClosed())) {
                purchaserAccount.setIsClosed("0");
            }
            purchaserAccount.setIsAdmin("1");
            //检查用户中心
            ExecuteResult<String> isregResult = UserApi.userIsRegistered(purchaserAccount.getLoginName());
            if(isregResult.isSuccess()){
                return AjaxJson.fail("此用户未在海尔用户中心注册，请先去注册");
            }
            ExecuteResult<HaierUserDTO> loginResult = UserApi.login(purchaserAccount.getLoginName(),purchaserAccount.getNewPassword());
            if(!loginResult.isSuccess()){
                return AjaxJson.fail(loginResult.getError(), UserApiErrorDesc.LoginError.getDesc(loginResult.getError()));
            }
        } else {
            PurchaserAccount entity = purchaserAccountService.get(purchaserAccount.getId());
            if (entity == null) {
                return AjaxJson.fail("无此账号");
            }
            if (StringUtils.isNotBlank(purchaserAccount.getLoginName())) {
                if(!entity.getLoginName().equalsIgnoreCase(purchaserAccount.getLoginName())){
                    //检查账号重复
                    PurchaserAccount checker = purchaserAccountService.findUniqueByProperty("login_name",purchaserAccount.getLoginName());
                    if(checker!=null){
                        return AjaxJson.fail("登录账号重复");
                    }
                }
            }
        }

        //设置密码
        if(StringUtils.isNotBlank(purchaserAccount.getNewPassword())){
            //采购商的密码不做加密处理
            purchaserAccount.setPasswd(purchaserAccount.getNewPassword());
            //purchaserAccount.setPasswd(SystemService.entryptPassword(purchaserAccount.getNewPassword()));
        }
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())){
            purchaserAccount.setUpdateById(user.getId()) ;
            purchaserAccount.setCreateById(user.getId()) ;
        }
        purchaserAccountService.save(purchaserAccount);//新建或者编辑保存
        j.setSuccess(true);
        j.setMsg("保存采购商账号成功");
        return j;
    }

    /**
     * 删除采购商账号
     */
    @ResponseBody
    @RequiresPermissions("shop:purchaser:purchaser:edit")
    @RequestMapping(value = "deleteAccount")
    public AjaxJson deleteAccount(PurchaserAccount purchaserAccount, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        purchaserAccountService.delete(purchaserAccount);
        j.setMsg("删除采购商账号成功");
        return j;
    }
    
	/**
	 * 删除采购商
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaser:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Purchaser purchaser, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserService.delete(purchaser);
		j.setMsg("删除采购商成功");
		return j;
	}
	
	/**
	 * 批量删除采购商
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaser:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserService.delete(purchaserService.get(id));
		}
		j.setMsg("删除采购商成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Purchaser purchaser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购商"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Purchaser> page = purchaserService.findPage(new Page<Purchaser>(request, response, -1), purchaser);
    		new ExportExcel("采购商", Purchaser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出采购商记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:purchaser:purchaser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Purchaser> list = ei.getDataList(Purchaser.class);
			for (Purchaser purchaser : list){
				try{
					purchaserService.save(purchaser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条采购商记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条采购商记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入采购商失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaser/?repage";
    }
	
	/**
	 * 下载导入采购商数据模板
	 */
	@RequiresPermissions("shop:purchaser:purchaser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "采购商数据导入模板.xlsx";
    		List<Purchaser> list = Lists.newArrayList(); 
    		new ExportExcel("采购商数据", Purchaser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaser/?repage";
    }

    /**
     * 审核
     * @return
     */
    @ResponseBody
    @RequiresPermissions("shop:purchaser:purchaser:audit")
    @RequestMapping(value = "audit", method=RequestMethod.POST)
    public AjaxJson audit(Purchaser purchaser) {
        if(StringUtils.isBlank(purchaser.getId())){
            return AjaxJson.fail("采购商不存在");
        }
        if(Global.AUDIT_STATE_WAIT.equals(purchaser.getAuditState())){
            return AjaxJson.fail("待审核状态才能审核");
        }
        if(Global.AUDIT_STATE_NO.equals(purchaser.getAuditState()) && StringUtils.isBlank(purchaser.getAuditDesc())){
            return AjaxJson.fail("请填写不同意的原因");
        }
        purchaserService.audit(purchaser);
        return AjaxJson.ok();
    }
    
    
   /* *//**
     * 采购商注册
     * 直采类型采购商注册
     *//*
    @ApiOperation(notes = "purchaserRegister", httpMethod = "POST", value = "采购商注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    		@ApiImplicitParam(name = "apptab", value = "apptab", required = true, paramType = "form", dataType = "String"),
    		@ApiImplicitParam(name = "mobile", value = "手机", required = true, paramType = "form", dataType = "String"),
    		@ApiImplicitParam(name = "code", value = "短信验证码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyNature", value = "公司性质", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "proviceId", value = "省份id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "proviceName", value = "省份名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityId", value = "城市id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cityName", value = "城市名称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyNum", value = "公司人数", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "companyTel", value = "公司电话", required = false, paramType = "form", dataType = "String"),
//            @ApiImplicitParam(name = "bankName", value = "开户银行", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "bankAccount", value = "开户账号", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "bankAddress", value = "开户地址", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "licenceUrl", value = "三证合一url", required = false, paramType = "form", dataType = "String"),
    		//@ApiImplicitParam(name = "logoUrl", value = "公司LOGO", required = false, paramType = "form", dataType = "String"),
    		@ApiImplicitParam(name = "invitationCode", value = "邀请码", required = false, paramType = "form", dataType = "String"),
//    		@ApiImplicitParam(name = "purchase", value = "是否直采", required = false, paramType = "form", dataType = "String")
            
    })
    @PostMapping("/purchaserRegister") 
    public AjaxJson purchaserRegister(String apptab, String mobile, String code, String userName, String email,String password,
    					  String companyName,String contacts,String companyNature,String proviceId,String proviceName,String cityId,
    					  String cityName,String companyNum,String companyTel,String bankAccount,String bankAddress,String licenceUrl,
    					  String logoUrl,String invitationCode){
    	
    	if(StringUtils.isBlank(mobile)){
            return AjaxJson.fail("手机号不能为空");
        }
    	if(StringUtils.isNotBlank(mobile)){
            if(!RegValidators.isMobile(mobile)){
                return AjaxJson.fail("手机格式不正确");
            }
        }
    	if(StringUtils.isBlank(code)){
    		return AjaxJson.fail("短信验证码不能为空");
    	}
    	if(StringUtils.isBlank(userName)){
    		return AjaxJson.fail("用户名不能为空");
    	}
    	if(StringUtils.isNotBlank(email)){
            if(!RegValidators.isEmail(email)){
                return AjaxJson.fail("邮箱格式不正确");
            }
        }
    	if(StringUtils.isBlank(password)){
    		return AjaxJson.fail("密码不能为空");
    	}
    	if(StringUtils.isBlank(bankAccount)){
    		return AjaxJson.fail("开户账号不能为空");
    	}
    	if(StringUtils.isBlank(bankAddress)){
    		return AjaxJson.fail("开户地址不能为空");
    	}
    	if(StringUtils.isBlank(licenceUrl)){
    		return AjaxJson.fail("三证合一url不能为空");
    	}
    	//校验用户是否已经注册了
    	ExecuteResult<String> usernameResult = UserApi.userIsRegistered(userName);
        if(!usernameResult.isSuccess()){
            return AjaxJson.fail("该账号已经注册了，请直接登录");
        }
        //根据公司名称查询是否已经注册
        boolean name = purchaserService.findByCompanyName(companyName);
        if(name){
        	return AjaxJson.fail("该公司名称已被注册");
        }
        ExecuteResult<HaierUserDTO> result = UserApi.userRegister(userName, mobile, email, password, code);
        if(!result.isSuccess()){
            return AjaxJson.fail(result.getError(), UserApiErrorDesc.RegisterError.getDesc(result.getError()));
        }
        HaierUserDTO haierUser = result.getResult();
        //插入采购商
        Purchaser purchaser = new Purchaser();
        PurchaserAccount account = new PurchaserAccount();
        purchaser.setMobile(mobile);
        purchaser.setEmail(email);
        purchaser.setCompanyName(companyName);
        purchaser.setContacts(contacts);
        purchaser.setCompanyNature(companyNature);
        purchaser.setProvinceId(proviceId);
        String areaInfo=proviceName+"/"+cityName;
        purchaser.setAreaInfo(areaInfo);
        purchaser.setCityId(cityId);
        purchaser.setCompanyNum(companyNum);
        if (StringUtils.isNotEmpty(companyTel)) {
        	purchaser.setCompanyTel(companyTel);
		}
        purchaser.setBankAddress(bankAddress);
        if(StringUtils.isNotEmpty(licenceUrl)){
        	purchaser.setLicenceUrl(licenceUrl);
        }
        
        purchaser.setInvitationCode(invitationCode);
        purchaserService.save(purchaser);
        
        account.setLoginName(userName);
        account.setPasswd(password);
        
        account.setPurchaserId(purchaser.getId());
        account.setRealName(userName);
        account.setIsAdmin("1");
        account.setCreateDate(new Date());
        account.setIsClosed("0");
        account.setHaierUserId(String.valueOf(haierUser.getUserId()));
        purchaserAccountService.save(account);
    	return AjaxJson.ok("注册成功");
    	
    }*/
   /* 
    *//**
     * 判断公司名称在库中有没有，没有去海尔调，返回后存自己的库
     *//*
    @ApiOperation(notes = "isCompanyName", httpMethod = "POST", value = "采购商注册", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
    		@ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String")
    
    })
    @PostMapping("/isCompanyName")
    public String isCompanyName(String companyName){
    	//ExecuteResult<String> purchaserRegister = UserApi.purchaserRegister(companyName);
    	//查本地库
    	String isExist = purchaserService.findByCompanyName(companyName);
    	if (isExist.equals(true)) {
			
    		return "true";
		}else{//查海尔库
			
			//海尔库有公司名称注册信息，返回保存本库
			
			//没有公司名称注册信息，继续注册
			
		}
    	return "false";
	}*/
    
}