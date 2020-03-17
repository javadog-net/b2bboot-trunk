package com.jhmis.modules.shop.utils;

import com.jhmis.common.config.Global;
import com.jhmis.common.utils.CacheUtils;
import com.jhmis.common.utils.SpringContextHolder;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.dealer.DealerMenu;
import com.jhmis.modules.shop.entity.dealer.DealerRole;
import com.jhmis.modules.shop.mapper.dealer.DealerAccountMapper;
import com.jhmis.modules.shop.mapper.dealer.DealerMapper;
import com.jhmis.modules.shop.mapper.dealer.DealerMenuMapper;
import com.jhmis.modules.shop.mapper.dealer.DealerRoleMapper;
import com.jhmis.modules.sys.security.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.util.List;

public class DealerUtils {
    private static DealerMapper dealerMapper = SpringContextHolder.getBean(DealerMapper.class);
    private static DealerAccountMapper dealerAccountMapper = SpringContextHolder.getBean(DealerAccountMapper.class);
    private static DealerRoleMapper dealerRoleMapper = SpringContextHolder.getBean(DealerRoleMapper.class);
    private static DealerMenuMapper dealerMenuMapper = SpringContextHolder.getBean(DealerMenuMapper.class);

    public static final String DEALER_CACHE = "dealerCache";
    public static final String DEALER_CACHE_ID_ = "dealer_id_";
    public static final String DEALER_ACCOUNT_CACHE_ID_ = "dealer_account_id_";
    public static final String DEALER_ACCOUNT_CACHE_LOGIN_NAME_ = "dealer_account_ln_";
    public static final String DEALER_ACCOUNT_CACHE_MENU_LIST = "dealer_menuList_";
    public static final String DEALER_MSG_TPL_CACHE_KEY = "dealer_msg_tpl";

    /**
     * 根据ID获取供应商账号
     * @param id
     * @return 取不到返回null
     */
    public static DealerAccount get(String id){
        DealerAccount dealerAccount = (DealerAccount) CacheUtils.get(DEALER_CACHE, DEALER_ACCOUNT_CACHE_ID_ + id);
        if (dealerAccount ==  null){
            dealerAccount = dealerAccountMapper.get(id);
            if (dealerAccount == null){
                return null;
            }
            dealerAccount.setRoleList(getRoleListFromDb(dealerAccount));
            putCache(dealerAccount);
        }
        Dealer dealer = getDealer(dealerAccount.getDealerId());
        dealerAccount.setDealer(dealer);
        return dealerAccount;
    }

    /**
     * 根据登录名获取供应商账号
     * @param loginName
     * @return 取不到返回null
     */
    public static DealerAccount getByLoginName(String loginName){
        DealerAccount dealerAccount = (DealerAccount)CacheUtils.get(DEALER_CACHE, DEALER_ACCOUNT_CACHE_LOGIN_NAME_ + loginName);
        if (dealerAccount == null){
            dealerAccount = dealerAccountMapper.getByLoginName(loginName);
            if (dealerAccount == null){
                return null;
            }
            dealerAccount.setRoleList(getRoleListFromDb(dealerAccount));
            putCache(dealerAccount);
        }
        Dealer dealer = getDealer(dealerAccount.getDealerId());
        dealerAccount.setDealer(dealer);
        return dealerAccount;
    }

    /**
     * 根据供应商id获取供应商
     * @param dealerId
     * @return
     */
    public static Dealer getDealer(String dealerId){
        Dealer dealer = (Dealer)CacheUtils.get(DEALER_CACHE, DEALER_CACHE_ID_ + dealerId);
        if (dealer == null) {
            dealer = dealerMapper.get(dealerId);
            CacheUtils.put(DEALER_CACHE, DEALER_CACHE_ID_ + dealerId, dealer);
        }
        return dealer;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        DealerUtils.clearCache(getDealerAccount());
    }

    /**
     * 清除经销商缓存
     * @param dealerId
     */
    public static void clearDealerCache(String dealerId){
        CacheUtils.remove(DEALER_CACHE, DEALER_CACHE_ID_ + dealerId);
    }

    /**
     * 清除指定用户缓存
     * @param dealerAccount
     */
    public static void clearCache(DealerAccount dealerAccount){
        if(dealerAccount == null || StringUtils.isEmpty(dealerAccount.getId())){
            return;
        }
        CacheUtils.remove(DEALER_CACHE, DEALER_ACCOUNT_CACHE_ID_ + dealerAccount.getId());
        CacheUtils.remove(DEALER_CACHE, DEALER_ACCOUNT_CACHE_LOGIN_NAME_ + dealerAccount.getLoginName());
        CacheUtils.remove(DEALER_CACHE, DEALER_ACCOUNT_CACHE_MENU_LIST + dealerAccount.getId());
    }

    /**
     * 放入供应商缓存
     * @param dealerAccount
     */
    public static void putCache(DealerAccount dealerAccount){
        if(dealerAccount == null || StringUtils.isEmpty(dealerAccount.getId())){
            return;
        }
        CacheUtils.put(DEALER_CACHE, DEALER_ACCOUNT_CACHE_ID_ + dealerAccount.getId(), dealerAccount);
        CacheUtils.put(DEALER_CACHE, DEALER_ACCOUNT_CACHE_LOGIN_NAME_ + dealerAccount.getLoginName(), dealerAccount);
    }

    /**
     * 获取当前登录供应商用户
     * 注意，只有当前用户的操作带有auth头部，才能在控制器取到
     * 完全取消了session
     * @return 取不到返回 null
     */
    public static DealerAccount getDealerAccount(){
        Principal principal = getPrincipal();
        if (principal!=null){
            DealerAccount dealerAccount = get(principal.getId());
            if (dealerAccount != null && StringUtils.isNotBlank(dealerAccount.getId()) && StringUtils.isNotBlank(dealerAccount.getDealerId())){
                return dealerAccount;
            }
        }
        return null;
        //以下采用request中获取当前用户id来获取当前账号，当上面Principal不可用时采用

        /*HttpServletRequest req = Servlets.getRequest();
        if(req !=null){
            String userId = (String) req.getAttribute("current_user_id");
            String userType = (String) req.getAttribute("current_user_type");
            if(StringUtils.isBlank(userId) || StringUtils.isBlank(userType) || !Global.USER_TYPE_DEALER.equals(userType)){
                return null;
            }
            DealerAccount dealerAccount = get(userId);
            if (dealerAccount != null && StringUtils.isNotBlank(dealerAccount.getId()) && StringUtils.isNotBlank(dealerAccount.getDealerId())){
                return dealerAccount;
            }
        }
        return null;*/
    }

    /**
     * 获取当前用户角色列表
     * @return
     */
    public static List<DealerRole> getRoleListFromDb(DealerAccount dealerAccount){
        if(dealerAccount == null || StringUtils.isBlank(dealerAccount.getId())){
            return null;
        }
        List<DealerRole> roleList;
        DealerRole role = new DealerRole();
        role.setDealerId(dealerAccount.getDealerId());
        if (Global.YES.equals(dealerAccount.getIsAdmin())){
            roleList = dealerRoleMapper.findAllList(role);
        }else{
            roleList = dealerRoleMapper.findList(new DealerRole(dealerAccount));
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     * @return
     */
    public static List<DealerMenu> getMenuList(){
        return getMenuList(getDealerAccount());
    }

    public static List<DealerMenu> getMenuList(DealerAccount dealerAccount){
        if(dealerAccount == null || StringUtils.isBlank(dealerAccount.getId())){
            return null;
        }
        List<DealerMenu> menuList = (List<DealerMenu>) CacheUtils.get(DEALER_CACHE, DEALER_ACCOUNT_CACHE_MENU_LIST + dealerAccount.getId());
        if (menuList == null){
            menuList = getMenuListFromDb(dealerAccount);
            CacheUtils.put(DEALER_CACHE, DEALER_ACCOUNT_CACHE_MENU_LIST + dealerAccount.getId(),menuList);
        }
        return menuList;
    }

    public static List<DealerMenu> getMenuListFromDb(DealerAccount dealerAccount){
        List<DealerMenu> menuList;
        if (Global.YES.equals(dealerAccount.getIsAdmin())){
            menuList = dealerMenuMapper.findAllList(new DealerMenu());
        }else{
            DealerMenu m = new DealerMenu();
            m.setAccountId(dealerAccount.getId());
            menuList = dealerMenuMapper.findByAccountId(m);
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

    /**
     * 退出登录
     */
    public static void logout(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                DealerAccount dealerAccount = get(principal.getId());
                if (dealerAccount != null){
                    clearCache(dealerAccount);
                }
            }
            subject.logout();
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }

        //下面是不使用subject直接使用req的做法
        /*DealerAccount currentAccount = getDealerAccount();
        if(currentAccount != null){
            clearCache(currentAccount);
        }*/
    }
}
