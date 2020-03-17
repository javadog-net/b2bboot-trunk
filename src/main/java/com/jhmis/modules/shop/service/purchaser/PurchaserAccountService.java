/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.haier.user.api.UserApi;
import com.haier.user.api.UserApiErrorDesc;
import com.haier.user.api.dto.ExecuteResult;
import com.haier.user.api.dto.HaierUserDTO;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.common.utils.MD5;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.captcha.CaptchaUtil;
import com.jhmis.core.persistence.MenuBuilder;
import com.jhmis.core.persistence.MenuNode;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.core.service.ServiceException;
import com.jhmis.modules.directpurchaser.mapper.DirectPurchaserAccountMapper;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMenu;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserMapper;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxMember;
import com.jhmis.modules.wechat.service.WxMemberService;

/**
 * 采购商账号Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class PurchaserAccountService extends CrudService<PurchaserAccountMapper, PurchaserAccount> {

    protected Logger logger = LoggerFactory.getLogger(PurchaserAccountService.class);
    @Autowired
    PurchaserService purchaserService;
    @Autowired
    PurchaserMapper purchasermapper;
    @Autowired
    private WxMemberService wxMemberService;
    @Autowired
    private PurchaserAccountMapper purchaseraccountmapper;
    @Autowired
    private DirectPurchaserAccountMapper directPurchaserAccountMapper;

    @Value("${Jsh.Logout}")
    protected String logoutUrl;

	public PurchaserAccount get(String id) {
		return super.get(id);
	}

    public PurchaserAccount getByName(String loginName){
        return this.mapper.getByLoginName(loginName);
    }
	public List<PurchaserAccount> findList(PurchaserAccount purchaserAccount) {
		return super.findList(purchaserAccount);
	}
	
	public Page<PurchaserAccount> findPage(Page<PurchaserAccount> page, PurchaserAccount purchaserAccount) {
		return super.findPage(page, purchaserAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserAccount purchaserAccount) {
		super.save(purchaserAccount);
        if (StringUtils.isNotBlank(purchaserAccount.getId()) && "0".equals(purchaserAccount.getIsAdmin())){
            // 更新用户与角色关联
            this.mapper.deleteAccountRole(purchaserAccount);
            if (purchaserAccount.getRoleList() != null && purchaserAccount.getRoleList().size() > 0){
                this.mapper.insertAccountRole(purchaserAccount);
            }else{
                throw new ServiceException(purchaserAccount.getLoginName() + "没有设置角色！");
            }
        }
        // 清除用户缓存
        PurchaserUtils.clearCache(purchaserAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserAccount purchaserAccount) {
		super.delete(purchaserAccount);
        this.mapper.deleteAccountRole(purchaserAccount);
        // 清除用户缓存
        PurchaserUtils.clearCache(purchaserAccount);
	}

    @Transactional(readOnly = false)
    public void updateAccountInfo(PurchaserAccount purchaserAccount) {
        this.mapper.updateAccountInfo(purchaserAccount);
        // 清除用户缓存
        PurchaserUtils.clearCache(purchaserAccount);
    }

    @Transactional(readOnly = false)
    public AjaxJson modifypwd(PurchaserAccount purchaserAccount, String oldpwd, String newpwd) {
        ExecuteResult<String> result = UserApi.updateUserPassword(purchaserAccount.getHaierUserId(),oldpwd,newpwd);
        if(!result.isSuccess()){
            return AjaxJson.fail(UserApiErrorDesc.LoginError.getDesc(result.getError()));
        }
        purchaserAccount.setPasswd(newpwd);
        this.mapper.updatePasswordById(purchaserAccount);
        // 清除用户缓存
        PurchaserUtils.clearCache(purchaserAccount);
        return AjaxJson.ok("修改密码成功");
    }

    /**
     * 处理输出给前台的菜单
     * @param list
     * @return
     */
    public List<MenuNode> processMenu(List<PurchaserMenu> list){
        if(list == null) return  null;
        List<MenuNode> menuList = Lists.newArrayList();
        for(PurchaserMenu menu : list){
            if("1".equals(menu.getId()) || !"1".equals(menu.getMenuType()) || !"1".equals(menu.getIsShow())){
                continue;
            }
            MenuNode menuNode = new MenuNode(menu.getId(),menu.getName(),menu.getParentId(),menu.getHref(),menu.getTarget(),menu.getIcon());
            menuList.add(menuNode);
        }
        //生成带子节点的树形菜单，如果前台只要id,parentid,结构，可以不用处理，交给前台处理
        menuList = MenuBuilder.bulid(menuList);
        return menuList;
    }

    /**
     * 校验用户带验证码
     * @param userName
     * @param password
     * @param captchaToken
     * @param captchaCode
     * @return
     */
    @Transactional(readOnly = false)
    public AjaxJson login(String userName, String password, boolean needValidCode, String captchaToken, String captchaCode){
        if(StringUtils.isBlank(userName)){
            return AjaxJson.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            return AjaxJson.fail("密码不能为空");
        }
        if(needValidCode) {
            if (captchaToken == null) {
                return AjaxJson.fail("验证码token不能为空");
            }
            if (captchaCode == null) {
                return AjaxJson.fail("验证码不能为空");
            }
            if (!CaptchaUtil.valid(captchaToken, captchaCode)) {
                return AjaxJson.fail(Constants.ERROR_CODE_VALIDCODE_ERROR, Constants.ERROR_DESC_VALIDCODE_ERROR);
            }
        }
        ExecuteResult<HaierUserDTO> result = UserApi.login(userName,password);
        if(!result.isSuccess()){
            return AjaxJson.fail(result.getError(), UserApiErrorDesc.LoginError.getDesc(result.getError()));
        }
        HaierUserDTO haierUser = result.getResult();
        //用户中心验证通过了
        PurchaserAccount account = this.getByName(userName);
        if(account == null){
            //插入新用户和采购商
            Purchaser purchaser = new Purchaser();
            account = new PurchaserAccount();
            //暂时表公司编码设置为用户的登录账号
            purchaser.setCompanyCode(userName);
            purchaser.setCompanyName(haierUser.getUserName());
            purchaser.setMobile(haierUser.getMobile());
            purchaser.setEmail(haierUser.getEmail());
            purchaser.setAuditState(Global.AUDIT_STATE_UNSUBMIT);//待完善状态
            purchaserService.save(purchaser);
            account.setPurchaserId(purchaser.getId());
            account.setLoginName(userName);
            account.setRealName(haierUser.getUserName());
            account.setPasswd(password);
            account.setMobile(haierUser.getMobile());
            account.setIsAdmin("1");
            account.setIsClosed("0");
            account.setEmail(haierUser.getEmail());
            account.setHaierUserId(String.valueOf(haierUser.getUserId()));
            account.setLastLoginDate(new Date());
            super.save(account);
        } else {
            if (Global.YES.equals(account.getIsClosed())) {
                return AjaxJson.fail(Constants.ERROR_CODE_STOP_ERROR, Constants.ERROR_DESC_STOP_ERROR);
            }
            account.setLastLoginDate(new Date());
            this.mapper.updateLoginInfo(account);
        }
        account.setRoleList(PurchaserUtils.getRoleListFromDb(account));
        //先清空账户缓存
        PurchaserUtils.clearCache(account);
        //再放入缓存
        PurchaserUtils.putCache(account);
        //
        List<PurchaserMenu> menuList = PurchaserUtils.getMenuList(account);
        List<String> permissionList = new ArrayList<>();
        for (PurchaserMenu menu : menuList){
            if (org.apache.commons.lang3.StringUtils.isNotBlank(menu.getPermission())){
                for (String permission : org.apache.commons.lang3.StringUtils.split(menu.getPermission(),",")){
                    permissionList.add(permission);
                }
            }
        }
        AjaxJson ret = new AjaxJson();
        ret.put("account",account);
        ret.put("menus",processMenu(menuList));
        ret.put("permissions",permissionList);
        ret.put("token", JWTUtils.sign(account.getId(), Global.USER_TYPE_PURCHASER));

        return ret;
    }

    
    /** 
      * @Title: loginByPhoneCode 
      * @Description: TODO  通过手机号跟验证码登录
      * @param userName
      * @param password
      * @param needValidCode
      * @param captchaToken
      * @param captchaCode
      * @return 
      * @return AjaxJson
      * @author tc
      * @date 2019年8月9日下午5:01:29
      */
    @Transactional(readOnly = false)
    public AjaxJson loginByPhoneCode(String userName,String phoneCode){
        if(StringUtils.isBlank(userName)){
            return AjaxJson.fail("用户名不能为空");
        }  
        if(StringUtils.isBlank(phoneCode)){
            return AjaxJson.fail("验证码不能为空");
        }
    	logger.info("UserApi.login(userName,code);前");
		ExecuteResult<HaierUserDTO> result = UserApi.verificodelogin(userName, phoneCode);
		logger.info("UserApi.login(userName,code);后");
		if (!result.isSuccess()) {
			logger.info("!result.isSuccess()");
			return AjaxJson.fail(result.getError(), UserApiErrorDesc.LoginError.getDesc(result.getError()));
		}
	
        HaierUserDTO haierUser = result.getResult();
        //用户中心验证通过了
        PurchaserAccount account = this.getByName(userName);
        if(account == null){
            //插入新用户和采购商
            Purchaser purchaser = new Purchaser();
            account = new PurchaserAccount();
            //暂时表公司编码设置为用户的登录账号
            purchaser.setCompanyCode(userName);
            purchaser.setCompanyName(haierUser.getUserName());
            purchaser.setMobile(haierUser.getMobile());
            purchaser.setEmail(haierUser.getEmail());
            purchaser.setAuditState(Global.AUDIT_STATE_UNSUBMIT);//待完善状态
            purchaserService.save(purchaser);
            account.setPurchaserId(purchaser.getId());
            account.setLoginName(userName);
            account.setRealName(haierUser.getUserName());
            account.setPasswd(userName);
            account.setMobile(haierUser.getMobile());
            account.setIsAdmin("1");
            account.setIsClosed("0");
            account.setEmail(haierUser.getEmail());
            account.setHaierUserId(String.valueOf(haierUser.getUserId()));
            account.setLastLoginDate(new Date());
            super.save(account);
        } else {
            if (Global.YES.equals(account.getIsClosed())) {
                return AjaxJson.fail(Constants.ERROR_CODE_STOP_ERROR, Constants.ERROR_DESC_STOP_ERROR);
            }
            account.setLastLoginDate(new Date());
            this.mapper.updateLoginInfo(account);
        }
        account.setRoleList(PurchaserUtils.getRoleListFromDb(account));
        //先清空账户缓存
        PurchaserUtils.clearCache(account);
        //再放入缓存
        PurchaserUtils.putCache(account);
        //
        List<PurchaserMenu> menuList = PurchaserUtils.getMenuList(account);
        List<String> permissionList = new ArrayList<>();
        for (PurchaserMenu menu : menuList){
            if (org.apache.commons.lang3.StringUtils.isNotBlank(menu.getPermission())){
                for (String permission : org.apache.commons.lang3.StringUtils.split(menu.getPermission(),",")){
                    permissionList.add(permission);
                }
            }
        }
        AjaxJson ret = new AjaxJson();
        ret.put("account",account);
        ret.put("logoutUrl",logoutUrl);
        ret.put("menus",processMenu(menuList));
        ret.put("permissions",permissionList);
        ret.put("token", JWTUtils.sign(account.getId(), Global.USER_TYPE_PURCHASER));

        return ret;
    }


    /**
     * 登录对接客户中心，默认快速登录
     * @param loginNum          88码
     * @return
     */
    @Transactional(readOnly = false)
    public AjaxJson  quickLogin( String loginNum){


        PurchaserAccount byNumber = this.getByNumber(loginNum);

        if(byNumber == null){
            return AjaxJson.fail("没有企业购登陆权限，如需开通，请前往注册。");
        }
        Purchaser purchaser = purchaserService.get(byNumber.getPurchaserId());

        byNumber.setRoleList(PurchaserUtils.getRoleListFromDb(byNumber));
        //先清空88码缓存
        PurchaserUtils.clearCacheNumber(byNumber);
        //再放入缓存
        PurchaserUtils.putCacheNumber(byNumber);
        // 授权
        List<PurchaserMenu> menuList = PurchaserUtils.getMenuList(byNumber);
        System.out.println(menuList);
        List<String> permissionList = new ArrayList<>();
        if (menuList!=null){
            for (PurchaserMenu menu : menuList){
                if (org.apache.commons.lang3.StringUtils.isNotBlank(menu.getPermission())){
                    for (String permission : org.apache.commons.lang3.StringUtils.split(menu.getPermission(),",")){
                        permissionList.add(permission);
                    }
                }
            }
        }
        AjaxJson ret = new AjaxJson();
        ret.put("byNumber",byNumber);
        ret.put("logoutUrl",logoutUrl);
        ret.put("purchaser",purchaser);
        ret.put("menus",processMenu(menuList));
        ret.put("permissions",permissionList);
        ret.put("token", JWTUtils.sign(byNumber.getId(), Global.USER_TYPE_PURCHASER));//用户类型

        return ret;
    }

    
    public AjaxJson logout(){
        PurchaserUtils.logout();
        return AjaxJson.ok();
    }


    /**
     * 校验用户登录
     * @param userName
     * @param password
     * @return
     */
    @Transactional(readOnly = false)
    public AjaxJson loginByWx(String userName, String password, String openid){
        logger.info("userName=" + userName + "  password=" + password +" openid=" +openid);
        if(StringUtils.isBlank(userName)){
            return AjaxJson.fail("用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            return AjaxJson.fail("密码不能为空");
        }
        logger.info("UserApi.login(userName,password);前");
        ExecuteResult<HaierUserDTO> result = UserApi.login(userName,password);
        logger.info("UserApi.login(userName,password);后");
        if(!result.isSuccess()){
            logger.info("!result.isSuccess()");
            return AjaxJson.fail(result.getError(), UserApiErrorDesc.LoginError.getDesc(result.getError()));
        }
        logger.info("result.getResult();");
        HaierUserDTO haierUser = result.getResult();
        logger.info("result.getResult();结束");
        //用户中心验证通过了
        logger.info("this.getByName(userName);" + userName);
        PurchaserAccount account=new PurchaserAccount();
        try{
        account = this.getByName(userName);
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        logger.info("后");
        if(account == null){
            logger.info("account == null");
            //插入新用户和采购商
            Purchaser purchaser = new Purchaser();
            account = new PurchaserAccount();
            //暂时把公司编码设置为用户的登录账号
            purchaser.setCompanyCode(userName);
            purchaser.setCompanyName(haierUser.getUserName());
            purchaser.setMobile(haierUser.getMobile());
            //purchaser.setChannel(openid);
            purchaser.setEmail(haierUser.getEmail());
            purchaser.setAuditState(Global.AUDIT_STATE_UNSUBMIT);//待完善状态
            try{
                logger.info("  purchaserService.save(purchaser);前");
                purchaserService.save(purchaser);
                logger.info("  purchaserService.save(purchaser);后");
                account.setPurchaserId(purchaser.getId());
                account.setLoginName(userName);
                account.setRealName(haierUser.getUserName());
                account.setPasswd(password);
                account.setMobile(haierUser.getMobile());
                account.setIsAdmin("1");
                account.setIsClosed("0");
                account.setEmail(haierUser.getEmail());
                account.setHaierUserId(String.valueOf(haierUser.getUserId()));
                account.setLastLoginDate(new Date());
                logger.info(" super.save(account);");
                super.save(account);
                logger.info("super.save(account);后");
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            logger.info("account != null");
            if (Global.YES.equals(account.getIsClosed())) {
                return AjaxJson.fail(Constants.ERROR_CODE_STOP_ERROR, Constants.ERROR_DESC_STOP_ERROR);
            }
            /*Purchaser purchaser1 = new Purchaser();
            String id=account.getPurchaserId();
            purchaser1= purchasermapper.getpurchaserbyid(id);
            if(StringUtils.isBlank(purchaser1.getChannel())){
            	purchaserService.savechannelbyid(id,openid);
            }*/
            account.setLastLoginDate(new Date());
            this.mapper.updateLoginInfo(account);
            
        }
        PurchaserAccount account2 = this.getByName(userName);
        WxMember wxMember2 = wxMemberService.findUniqueByProperty("user_id",account2.getId());
        if(wxMember2!=null){
        String openidnum=wxMember2.getOpenid();
              if(!openidnum.equals(openid)){
            	  logger.info("非本帐号登录 绑定");
            	  return AjaxJson.fail("请使用本微信绑定的帐号登录！");
              }
        }
        //进行wx相关信息补充
        if(haierUser!=null){
            logger.info("进行wx相关信息补充start");
            WxMember wxMember = wxMemberService.findUniqueByProperty("openid",openid);
            if(wxMember!=null){
            	/*if(StringUtils.isNotBlank(wxMember.getUserId())){
            		System.out.println("wxMember.getUserId()"+wxMember.getUserId());
            		System.out.println("account2.getId()"+account2.getId());
	            	if(!wxMember.getUserId().equals(account2.getId())){
	            		logger.info("非本帐号登录 绑定");
	            		return AjaxJson.fail("请使用本微信绑定的帐号登录！");
	            	}	
            	}else{
            		WxMember wxM = wxMemberService.findUniqueByProperty("openid",openid);
            	}*/
                //补充wx相关数据
                wxMember.setUserMobile(haierUser.getMobile());
                wxMember.setUserId(account2.getId());
                //更新数据
                wxMemberService.update(wxMember);

                
                
            }
        }
        account2.setRoleList(PurchaserUtils.getRoleListFromDb(account2));
        logger.info("进行wx相关信息补充end");
       
        //先清空账户缓存
        PurchaserUtils.clearCache(account2);
        //再放入缓存
        PurchaserUtils.putCache(account2);
        AjaxJson ret = new AjaxJson();
        ret.put("account",account2);
        ret.put("token", JWTUtils.sign(account2.getId(), Global.USER_TYPE_PURCHASER));
        return ret;
    }
    
    @Transactional(readOnly = false)
    public void updatedapartname(PurchaserAccount purchaseraccount){
   	purchaseraccountmapper.updatedapartname(purchaseraccount);
   	}
    @Transactional(readOnly = false)
	public PurchaserAccount getbyid(String id) {
		
		return purchaseraccountmapper.getbyid(id);
	} 

   
    public String getAvatarById(String id){
    	String avatar = purchaseraccountmapper.getAvatarById(id);
    	if(StringUtils.isBlank(avatar)){
        	avatar=wxMemberService.findAvatarurlByUserId(id);
        	if(StringUtils.isBlank(avatar)){
        		avatar="";
        	}
    	}
        return avatar;
    }
	
	public String getNicknameById(String id){
		String nickname = purchaseraccountmapper.getNicknameById(id);
		if(StringUtils.isBlank(nickname)){
        	nickname=wxMemberService.findNicknameByUserId(id);
        	if(StringUtils.isBlank(nickname)){
        		nickname="";
			}
        }
		return nickname;
		
	}
	 @Transactional(readOnly = false)
	public int deleteBypurchaserid(String id) {
		return purchaseraccountmapper.deletebypurchaserid(id);
	}
	 @Transactional(readOnly = false)
	public void deletebymobiles(String mobiles) {
		// TODO Auto-generated method stub
		 purchaseraccountmapper.deletebymobiles(mobiles);
	}
	 
	 public PurchaserAccount  getAdminByPurchaserId(String id) {
			PurchaserAccount pa=directPurchaserAccountMapper.getAdminByPurchaserId(id,"1");
			return pa;
		}
	    // TODO
	    public PurchaserAccount getByNumber(String loginNum){
	        return this.mapper.getByLoginNumber(loginNum);
	    }
	 /**
	     * 校验88码登录
	     * @param loginNum
	     * @param passwd
	     * @return
	     */
	   @Transactional(readOnly = false)
	    public AjaxJson  loginNumber( String loginNum,String passwd){
	       if (StringUtils.isBlank(loginNum)){
	           return AjaxJson.fail("88码不能为空");
	       }
	       if (StringUtils.isBlank(passwd)){
	           return AjaxJson.fail("密码不能为空");
	       }

	       PurchaserAccount byNumber = this.getByNumber(loginNum);

	       if(byNumber != null){
	           if(byNumber.getPasswd().equals(MD5.MD5Encode(passwd))){

	           }else{
	               //返回密码错误
	               return AjaxJson.fail("密码错误");
	           }
	       }else {
	           return AjaxJson.fail("用户名不存在");
	       }
	       Purchaser purchaser = purchaserService.get(byNumber.getPurchaserId());

	       byNumber.setRoleList(PurchaserUtils.getRoleListFromDb(byNumber));
	       //先清空88码缓存
	       PurchaserUtils.clearCacheNumber(byNumber);
	        //再放入缓存
	       PurchaserUtils.putCacheNumber(byNumber);
	       // 授权
	       List<PurchaserMenu> menuList = PurchaserUtils.getMenuList(byNumber);
	       System.out.println(menuList);
	       List<String> permissionList = new ArrayList<>();
	            if (menuList!=null){
	                for (PurchaserMenu menu : menuList){
	                    if (org.apache.commons.lang3.StringUtils.isNotBlank(menu.getPermission())){
	                        for (String permission : org.apache.commons.lang3.StringUtils.split(menu.getPermission(),",")){
	                            permissionList.add(permission);
	                        }
	                    }
	                }
	            }
	       AjaxJson ret = new AjaxJson();
	       ret.put("byNumber",byNumber);
	       ret.put("purchaser",purchaser);
	       ret.put("menus",processMenu(menuList));
	       ret.put("permissions",permissionList);
	       ret.put("token", JWTUtils.sign(byNumber.getId(), Global.USER_TYPE_PURCHASER));//用户类型

	       return ret;
	   }

}