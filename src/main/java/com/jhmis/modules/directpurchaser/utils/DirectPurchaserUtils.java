package com.jhmis.modules.directpurchaser.utils;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.CacheUtils;
import com.jhmis.common.utils.SpringContextHolder;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMenu;
import com.jhmis.modules.shop.entity.purchaser.PurchaserRole;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserMapper;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserMenuMapper;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserRoleMapper;
import com.jhmis.modules.sys.security.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.util.List;

public class DirectPurchaserUtils {
    private static PurchaserMapper purchaserMapper = SpringContextHolder.getBean(PurchaserMapper.class);
    private static PurchaserAccountMapper purchaserAccountMapper = SpringContextHolder.getBean(PurchaserAccountMapper.class);
    private static PurchaserRoleMapper purchaserRoleMapper = SpringContextHolder.getBean(PurchaserRoleMapper.class);
    private static PurchaserMenuMapper purchaserMenuMapper = SpringContextHolder.getBean(PurchaserMenuMapper.class);

    public static final String PURCHASER_CACHE = "purchaserCache";
    public static final String PURCHASER_CACHE_ID_ = "purchaser_id_";
    public static final String PURCHASER_ACCOUNT_CACHE_ID_ = "purchaser_account_id_";
    public static final String PURCHASER_ACCOUNT_CACHE_LOGIN_NAME_ = "purchaser_account_ln_";
    public static final String PURCHASER_ACCOUNT_CACHE_MENU_LIST = "purchaser_menuList_";
    public static final String PURCHASER_MSG_TPL_CACHE_KEY = "dealer_msg_tpl";

    /**
     * 根据ID获取采购商账号
     * @param id
     * @return 取不到返回null
     */
    public static PurchaserAccount get(String id){
        PurchaserAccount purchaserAccount = (PurchaserAccount) CacheUtils.get(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_ID_ + id);
        if (purchaserAccount ==  null){
            purchaserAccount = purchaserAccountMapper.get(id);
            if (purchaserAccount == null){
                return null;
            }
            purchaserAccount.setRoleList(purchaserRoleMapper.findList(new PurchaserRole(purchaserAccount)));
            putCache(purchaserAccount);
        }
        Purchaser purchaser = getPurchaser(purchaserAccount.getPurchaserId());
        purchaserAccount.setPurchaser(purchaser);
        return purchaserAccount;
    }

    /**
     * 根据登录名获取采购商账号
     * @param loginName
     * @return 取不到返回null
     */
    public static PurchaserAccount getByLoginName(String loginName){
        PurchaserAccount purchaserAccount = (PurchaserAccount)CacheUtils.get(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_LOGIN_NAME_ + loginName);
        if (purchaserAccount == null){
            purchaserAccount = purchaserAccountMapper.getByLoginName(loginName);
            if (purchaserAccount == null){
                return null;
            }
            purchaserAccount.setRoleList(purchaserRoleMapper.findList(new PurchaserRole(purchaserAccount)));
            putCache(purchaserAccount);
        }
        Purchaser purchaser = getPurchaser(purchaserAccount.getPurchaserId());
        purchaserAccount.setPurchaser(purchaser);
        return purchaserAccount;
    }

    /**
     * 根据采购商id获取采购商
     * @param purchaserId
     * @return
     */
    public static Purchaser getPurchaser(String purchaserId){
        Purchaser purchaser = (Purchaser)CacheUtils.get(PURCHASER_CACHE, PURCHASER_CACHE_ID_ + purchaserId);
        if (purchaser == null) {
            purchaser = purchaserMapper.get(purchaserId);
            CacheUtils.put(PURCHASER_CACHE, PURCHASER_CACHE_ID_ + purchaserId, purchaser);
        }
        return purchaser;
    }
    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        DirectPurchaserUtils.clearCache(getPurchaserAccount());
    }

    /**
     * 清除采购商缓存
     * @param purchaserId
     */
    public static void clearPurchaserCache(String purchaserId){
        CacheUtils.remove(PURCHASER_CACHE, PURCHASER_CACHE_ID_ + purchaserId);
    }
    /**
     * 清除指定用户缓存
     * @param purchaserAccount
     */
    public static void clearCache(PurchaserAccount purchaserAccount){
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())){
            return;
        }
        CacheUtils.remove(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_MENU_LIST + purchaserAccount.getId());
        CacheUtils.remove(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_ID_ + purchaserAccount.getId());
        CacheUtils.remove(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_LOGIN_NAME_ + purchaserAccount.getLoginName());
    }

    /**
     * 放入采购商缓存
     * @param purchaserAccount
     */
    public static void putCache(PurchaserAccount purchaserAccount){
        if(purchaserAccount == null || StringUtils.isEmpty(purchaserAccount.getId())){
            return;
        }
        CacheUtils.put(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_ID_ + purchaserAccount.getId(), purchaserAccount);
        CacheUtils.put(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_LOGIN_NAME_ + purchaserAccount.getLoginName(), purchaserAccount);
    }

    /**
     * 获取当前登录采购商用户
     * @return 取不到返回 null
     */
    public static PurchaserAccount getPurchaserAccount(){
        Principal principal = getPrincipal();
        if (principal!=null){
            PurchaserAccount purchaserAccount = get(principal.getId());
            if (purchaserAccount != null && StringUtils.isNotBlank(purchaserAccount.getId()) && StringUtils.isNotBlank(purchaserAccount.getPurchaserId())){
                System.out.println(purchaserAccount);
            	return purchaserAccount;
            }
        }
        return null;
        //以下采用request中获取当前用户id来获取当前账号，当上面Principal不可用时采用

       /* HttpServletRequest req = Servlets.getRequest();
        if(req !=null){
            String userId = (String) req.getAttribute("current_user_id");
            String userType = (String) req.getAttribute("current_user_type");
            if(StringUtils.isBlank(userId) || StringUtils.isBlank(userType) || !Global.USER_TYPE_PURCHASER.equals(userType)){
                return null;
            }
            PurchaserAccount purchaserAccount = get(userId);
            if (purchaserAccount != null && StringUtils.isNotBlank(purchaserAccount.getId()) && StringUtils.isNotBlank(purchaserAccount.getPurchaserId())){
                return purchaserAccount;
            }
        }
        return null;*/
    }

    /**
     * 获取当前用户角色列表
     * @return
     */
    public static List<PurchaserRole> getRoleListFromDb(PurchaserAccount purchaserAccount){
        if(purchaserAccount == null || StringUtils.isBlank(purchaserAccount.getId())){
            return null;
        }
        List<PurchaserRole> roleList;
        PurchaserRole role = new PurchaserRole();
        role.setPurchaserId(purchaserAccount.getPurchaserId());
        if (Global.YES.equals(purchaserAccount.getIsAdmin())){
            roleList = purchaserRoleMapper.findAllList(role);
        }else{
            roleList = purchaserRoleMapper.findList(new PurchaserRole(purchaserAccount));
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     * @return
     */
    public static List<PurchaserMenu> getMenuList(){
        return getMenuList(getPurchaserAccount());
    }

    public static List<PurchaserMenu> getMenuList(PurchaserAccount purchaserAccount){
        if(purchaserAccount == null || StringUtils.isBlank(purchaserAccount.getId())){
            return null;
        }
        List<PurchaserMenu> menuList = (List<PurchaserMenu>) CacheUtils.get(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_MENU_LIST + purchaserAccount.getId());
        if (menuList == null){
            menuList = getMenuListFromDb(purchaserAccount);
            CacheUtils.put(PURCHASER_CACHE, PURCHASER_ACCOUNT_CACHE_MENU_LIST + purchaserAccount.getId(),menuList);
        }
        return menuList;
    }

    public static List<PurchaserMenu> getMenuListFromDb(PurchaserAccount purchaserAccount){
        List<PurchaserMenu> menuList;
        if (Global.YES.equals(purchaserAccount.getIsAdmin())){
            menuList = purchaserMenuMapper.findAllList(new PurchaserMenu());
        }else{
            PurchaserMenu m = new PurchaserMenu();
            m.setAccountId(purchaserAccount.getId());
            menuList = purchaserMenuMapper.findByAccountId(m);
        }
        return menuList;
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                return principal;
            }
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static void logout(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                PurchaserAccount purchaserAccount = get(principal.getId());
                if (purchaserAccount != null){
                    clearCache(purchaserAccount);
                }
            }
            subject.logout();
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }

        //下面是不使用subject直接使用req的做法
        /*PurchaserAccount currentAccount = getPurchaserAccount();
        if(currentAccount != null){
            clearCache(currentAccount);
        }*/
    }
}
