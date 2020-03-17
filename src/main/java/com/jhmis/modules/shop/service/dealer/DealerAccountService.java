/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.dealer;

import com.google.common.collect.Lists;
import com.haier.webservices.client.mdmTob2b.source.MdmSourceApi;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.JWTUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.captcha.CaptchaUtil;
import com.jhmis.core.persistence.MenuBuilder;
import com.jhmis.core.persistence.MenuNode;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.core.service.ServiceException;
import com.jhmis.modules.customer.entity.TGridArea;
import com.jhmis.modules.customer.service.TGridAreaService;
import com.jhmis.modules.process.service.grid.HpsGridService;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.entity.MdmSourceReturn;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.dealer.DealerMenu;
import com.jhmis.modules.shop.mapper.dealer.DealerAccountMapper;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 供应商账号管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class DealerAccountService extends CrudService<DealerAccountMapper, DealerAccount> {

	@Autowired
    private MdmCustomersSourceService mdmCustomersSourceService;
	
	@Autowired
    HpsGridService hpsGridService;
	
	@Autowired
    DealerService dealerService;
	
	@Autowired
	TGridAreaService tGridAreaService;
    
	
	public DealerAccount get(String id) {
		return super.get(id);
	}

	public DealerAccount getByName(String loginName){
	    return this.mapper.getByLoginName(loginName);
    }

	public List<DealerAccount> findList(DealerAccount dealerAccount) {
		return super.findList(dealerAccount);
	}

    public List<DealerAccount> findRestList(DealerAccount dealerAccount) {
        return this.mapper.findRestList(dealerAccount);
    }
	
	public Page<DealerAccount> findPage(Page<DealerAccount> page, DealerAccount dealerAccount) {
		return super.findPage(page, dealerAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(DealerAccount dealerAccount) {
	    //主账号的添加放到供应商模块
		super.save(dealerAccount);
        if (StringUtils.isNotBlank(dealerAccount.getId()) && "0".equals(dealerAccount.getIsAdmin())){
            // 更新用户与角色关联
            this.mapper.deleteAccountRole(dealerAccount);
            if (dealerAccount.getRoleList() != null && dealerAccount.getRoleList().size() > 0){
                this.mapper.insertAccountRole(dealerAccount);
            }else{
                throw new ServiceException(dealerAccount.getLoginName() + "没有设置角色！");
            }
        }
        // 清除用户缓存
        DealerUtils.clearCache(dealerAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(DealerAccount dealerAccount) {
		super.delete(dealerAccount);
		this.mapper.deleteAccountRole(dealerAccount);
        // 清除用户缓存
		DealerUtils.clearCache(dealerAccount);
	}

    @Transactional(readOnly = false)
    public void updateAccountInfo(DealerAccount dealerAccount) {
        this.mapper.updateAccountInfo(dealerAccount);
        // 清除用户缓存
        DealerUtils.clearCache(dealerAccount);
    }

    @Transactional(readOnly = false)
    public AjaxJson modifypwd(DealerAccount dealerAccount,String oldpwd,String newpwd) {
	    //校验老密码
        String pwd = dealerAccount.getPasswd();
        if(!SystemService.validatePassword(oldpwd,pwd)){
            AjaxJson.fail(Constants.ERROR_CODE_OLDCIPHERKEY_ERROR,Constants.ERROR_DESC_OLDCIPHERKEY_ERROR);
        }
        newpwd = SystemService.entryptPassword(newpwd);
        dealerAccount.setPasswd(newpwd);
        this.mapper.updatePasswordById(dealerAccount);
        // 清除用户缓存
        DealerUtils.clearCache(dealerAccount);
        return AjaxJson.ok("修改密码成功");
    }

    @Transactional(readOnly = false)
    public AjaxJson reset(DealerAccount dealerAccount) {
        //重置密码（123456）
        String newcipherkey = "1c9c288977a4b284efd4c36029ae46120f4c11951260792cb8e5be1f";
        dealerAccount.setPasswd(newcipherkey);
        this.mapper.updatePasswordById(dealerAccount);
        // 清除用户缓存
        DealerUtils.clearCache(dealerAccount);
        //发送手机信息
        return AjaxJson.ok("您的密码已经初始化,密码为:123456");
    }

    /**
     * 处理输出给前台的菜单
     * @param list
     * @return
     */
	public List<MenuNode> processMenu(List<DealerMenu> list){
	    if(list == null) return  null;
        List<MenuNode> menuList = Lists.newArrayList();
        for(DealerMenu menu : list){
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
        //如果已经登录？
        if(needValidCode) {
            if (captchaToken == null) {
                return AjaxJson.fail("验证码token不能为空");
            }
            if (captchaCode == null) {
                return AjaxJson.fail("验证码不能为空");
            }
            if (!CaptchaUtil.valid(captchaToken,captchaCode)) {
                return AjaxJson.fail(Constants.ERROR_CODE_VALIDCODE_ERROR, Constants.ERROR_DESC_VALIDCODE_ERROR);
            }
        }
        DealerAccount account = this.getByName(userName);
        if(account == null){
            return AjaxJson.fail(Constants.ERROR_CODE_NO_USER,Constants.ERROR_DESC_NO_USER);
        }
        if(!SystemService.validatePassword(password,account.getPasswd())){
            return AjaxJson.fail(Constants.ERROR_CODE_CIPHERKEY_ERROR,Constants.ERROR_DESC_CIPHERKEY_ERROR);
        }
        if(Global.YES.equals(account.getIsClosed())){
            return AjaxJson.fail(Constants.ERROR_CODE_STOP_ERROR,Constants.ERROR_DESC_STOP_ERROR);
        }
        account.setLastLoginDate(new Date());
        this.mapper.updateLoginInfo(account);
        account.setRoleList(DealerUtils.getRoleListFromDb(account));
        
        //获取dealer
        Dealer dealer = DealerUtils.getDealer(account.getDealerId());
        if(userName.length() >1){
            if("8".equals(userName.substring(0,1))){               
            	MdmSourceReturn mdmSourceReturn = MdmSourceApi.addSourceFromMdm(userName);
            	if("S".equals(mdmSourceReturn.getOutRetcode())){           		
            		MdmCustomersSource mdmCustomersSource = mdmSourceReturn.getMdmCustomersSource();
            		String gmId = mdmCustomersSource.getOrgId();
            		String gmName = tGridAreaService.findNameByCode(gmId);

                    dealer.setCustomerCategory(mdmCustomersSource.getCustomerCategory());
                    dealer.setIndustryClass(mdmCustomersSource.getIndustryClass());
        			dealer.setCompanyName(mdmCustomersSource.getCusAbbName());
        			dealer.setGmId(gmId);
        			dealer.setGmName(gmName);
        			
        			if(StringUtils.isNotBlank(mdmCustomersSource.getMdmArea())) {
        				dealer.setMdmProvince(mdmCustomersSource.getMdmProvince());
            			dealer.setMdmCity(mdmCustomersSource.getMdmCity());
            			dealer.setMdmArea(mdmCustomersSource.getMdmArea());
            			
            			TGridArea tGridArea = new TGridArea();
            			tGridArea.setDistrictcode(mdmCustomersSource.getMdmArea());
            			List<TGridArea> tGridAreaList = tGridAreaService.findList(tGridArea);
            			if(tGridAreaList != null && tGridAreaList.size()>0) {
            				tGridArea = tGridAreaList.get(0);
            				dealer.setWgcode(tGridArea.getWgcode());
            				dealer.setWgname(tGridArea.getWgname());
            			}
        			}      			
        			dealerService.save(dealer);
            	
            		//校验88码状态，如果DELETE_FLAG 是1 提示账号异常
            		if(StringUtils.isNotEmpty(mdmCustomersSource.getDeleteFlag()) && "1".equals(mdmCustomersSource.getDeleteFlag())){
                        return  AjaxJson.fail("账号异常，请联系工贸业务接口人！");
                    }
            	}           	
            }
        }
        account.setDealer(dealer);
        //先清空账户缓存
        DealerUtils.clearCache(account);
        //再放入缓存
        DealerUtils.putCache(account);
        //
        List<DealerMenu> menuList = DealerUtils.getMenuList(account);
        List<String> permissionList = new ArrayList<>();
        for (DealerMenu menu : menuList){
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
        ret.put("token", JWTUtils.sign(account.getId(), Global.USER_TYPE_DEALER));
        return ret;
    }

    
    /**
     * 校验用户
     * @param userName
     * @param phoneNumber
     * @return
     */
    @Transactional(readOnly = false)
    public AjaxJson loginTransfer(String userName, String phoneNumber){
        if(StringUtils.isBlank(userName)){
            return AjaxJson.fail("用户名不能为空");
        }
        DealerAccount account = this.getByName(userName);
        //如果没有用户名
        if(account == null){
        	// 再去校验是否在MDM主数据中
        	MdmCustomersSource mdmCustomersSource = new MdmCustomersSource();
            mdmCustomersSource.setCusCode(userName);
            List<MdmCustomersSource> listMdmCustomersSource = mdmCustomersSourceService.findList(mdmCustomersSource);
            if(listMdmCustomersSource==null && listMdmCustomersSource.size()==0){
            	MdmSourceReturn mdmSourceReturn = MdmSourceApi.addSourceFromMdm(userName);
            	if("S".equals(mdmSourceReturn.getOutRetcode())){
            		mdmCustomersSource = mdmSourceReturn.getMdmCustomersSource();
            		mdmCustomersSourceService.save(mdmCustomersSource);
            		mdmCustomersSource.setComName(mdmCustomersSource.getCusAbbName());
            		//加入大渠道
                    mdmCustomersSource.setCustomerCategory(mdmCustomersSource.getCustomerCategory());
                    //加入小渠道
                    mdmCustomersSource.setIndustryClass(mdmCustomersSource.getIndustryClass());                 
            	}else if("E".equals(mdmSourceReturn.getOutRetcode())){
            		if("取数条件内无数据".equals(mdmSourceReturn.getOutRetmsg())){
                		mdmSourceReturn.setOutRetmsg("请确认8码申请流程结束，检查8码是否有误！");
                	}
                    return AjaxJson.fail(mdmSourceReturn.getOutRetmsg());
            	}else{
                    return AjaxJson.fail("请联系管理员");		
            	}   
            }else if(listMdmCustomersSource.size()>0){           
                mdmCustomersSource = listMdmCustomersSource.get(0);
                mdmCustomersSource.setComName(mdmCustomersSource.getCusAbbName());
            }
            Boolean flag = mdmCustomersSourceService.perfectInfo(mdmCustomersSource,"Haierb2b",phoneNumber);
            if(flag){
            	account = this.getByName(userName);
            	if(account==null){
            		return AjaxJson.fail("完善信息失败");
            	}
            }else{
                return AjaxJson.fail("完善信息失败");
            }
        }
        
    	//获取dealer
        Dealer dealer = DealerUtils.getDealer(account.getDealerId());   
    	if(userName.length() >1){
            if("8".equals(userName.substring(0,1))){               
            	MdmSourceReturn mdmSourceReturn = MdmSourceApi.addSourceFromMdm(userName);
            	if("S".equals(mdmSourceReturn.getOutRetcode())){           		
            		MdmCustomersSource mdmCustomersSource = mdmSourceReturn.getMdmCustomersSource();
            		String gmId = mdmCustomersSource.getOrgId();
            		String gmName = tGridAreaService.findNameByCode(gmId);

                    dealer.setCustomerCategory(mdmCustomersSource.getCustomerCategory());
                    dealer.setIndustryClass(mdmCustomersSource.getIndustryClass());
        			dealer.setCompanyName(mdmCustomersSource.getCusAbbName());
        			dealer.setGmId(gmId);
        			dealer.setGmName(gmName);
        			
        			if(StringUtils.isNotBlank(mdmCustomersSource.getMdmArea())) {
        				dealer.setMdmProvince(mdmCustomersSource.getMdmProvince());
            			dealer.setMdmCity(mdmCustomersSource.getMdmCity());
            			dealer.setMdmArea(mdmCustomersSource.getMdmArea());
            			
            			TGridArea tGridArea = new TGridArea();
            			tGridArea.setDistrictcode(mdmCustomersSource.getMdmArea());
            			List<TGridArea> tGridAreaList = tGridAreaService.findList(tGridArea);
            			if(tGridAreaList != null && tGridAreaList.size()>0) {
            				tGridArea = tGridAreaList.get(0);
            				dealer.setWgcode(tGridArea.getWgcode());
            				dealer.setWgname(tGridArea.getWgname());
            			}
        			}      			
        			dealerService.save(dealer);
            	
            		//校验88码状态，如果DELETE_FLAG 是1 提示账号异常
            		if(StringUtils.isNotEmpty(mdmCustomersSource.getDeleteFlag()) && "1".equals(mdmCustomersSource.getDeleteFlag())){
                        return  AjaxJson.fail("账号异常，请联系工贸业务接口人！");
                    }
            	}           	
            }
        }
    	account.setDealer(dealer);
       
        if(Global.YES.equals(account.getIsClosed())){
            return AjaxJson.fail(Constants.ERROR_CODE_STOP_ERROR,Constants.ERROR_DESC_STOP_ERROR);
        }
        account.setLastLoginDate(new Date());
        if(StringUtils.isNotBlank(phoneNumber)){
        	if(StringUtils.isBlank(account.getMobile()) || !phoneNumber.equals(account.getMobile())){
        		account.setMobile(phoneNumber);     
        	}
        }
        this.mapper.updateLoginInfo(account);
        account.setRoleList(DealerUtils.getRoleListFromDb(account));
        
        
        //先清空账户缓存
        DealerUtils.clearCache(account);
        //再放入缓存
        DealerUtils.putCache(account);
        //
        List<DealerMenu> menuList = DealerUtils.getMenuList(account);
        List<String> permissionList = new ArrayList<>();
        for (DealerMenu menu : menuList){
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
        ret.put("token", JWTUtils.sign(account.getId(), Global.USER_TYPE_DEALER));
        return ret;
    }
    
    
    public AjaxJson logout(){
        DealerUtils.logout();
        return AjaxJson.ok();
    }

    public static void main(String[] args) {
        String s = SystemService.entryptPassword("234567");
        System.out.println(s);
    }
}