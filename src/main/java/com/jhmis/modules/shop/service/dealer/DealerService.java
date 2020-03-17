/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.dealer;

import com.jhmis.common.Enum.AcgErrorCode;
import com.jhmis.common.Enum.HpsErrorCode;
import com.jhmis.common.Exception.AcgException;
import com.jhmis.common.Exception.HpsException;
import com.jhmis.common.utils.IdGen;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.dealer.DealerForAcg;
import com.jhmis.modules.shop.mapper.dealer.DealerAccountMapper;
import com.jhmis.modules.shop.mapper.dealer.DealerMapper;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.service.SystemService;
import com.jhmis.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 供应商管理Service
 * @author tity
 * @version 2018-07-29
 */
@Service
@Transactional(readOnly = true)
public class DealerService extends CrudService<DealerMapper, Dealer> {
    @Autowired
    DealerAccountMapper accountMapper;
    @Autowired
    DealerMapper dealermapper;
	public Dealer get(String id) {
		return super.get(id);
	}
	
	public List<Dealer> findList(Dealer dealer) {
		return super.findList(dealer);
	}
	
	public Page<Dealer> findPage(Page<Dealer> page, Dealer dealer) {
		return super.findPage(page, dealer);
	}
	
	@Transactional(readOnly = false)
	public void save(Dealer dealer) {
		super.save(dealer);
        //清除缓存
        DealerUtils.clearDealerCache(dealer.getId());
	}
	
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void delete(Dealer dealer) {
        //同时删除对应的所有子账号缓存
        DealerAccount entity = new DealerAccount();
        entity.setDealerId(dealer.getId());
        List<DealerAccount> accountList = accountMapper.findList(entity);
        for(DealerAccount account:accountList){
            DealerUtils.clearCache(account);
        }
	    int i = mapper.logicDeleteAccount(dealer.getId());
        if (i <= 0) {
            throw new RuntimeException();
        }
        mapper.deleteByLogic(dealer.getId());
		//清除缓存
        DealerUtils.clearDealerCache(dealer.getId());

	}

    @Transactional(readOnly = false)
    public void audit(Dealer dealer) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())){
            dealer.setAuditor(user.getName());
        }
        dealer.setAuditTime(new Date());
        this.mapper.updateAuditState(dealer);
        //清除缓存
        DealerUtils.clearDealerCache(dealer.getId());
    }

    @Transactional(readOnly = false)
    public void transCode(String oldCode,String newCode) throws HpsException{
	    //验证参数
        if (StringUtils.isBlank(oldCode)){
            throw new HpsException(HpsErrorCode.PARAM_OLDCODE_ERROR);
        }
        if (StringUtils.isBlank(newCode)){
            throw new HpsException(HpsErrorCode.PARAM_NEWCODE_ERROR);
        }
        //查询是否存在
        Dealer oldDealer = null;
        try{
            oldDealer = mapper.findUniqueByProperty("company_code",oldCode);
        }catch (Exception e){
            throw new HpsException(HpsErrorCode.DATA_ERROR);
        }
        if(oldDealer==null){
            throw new HpsException(HpsErrorCode.OLDCODE_NO_EXIST_ERROR);
        }
        DealerAccount oldDealerAccount = null;
        try{
            oldDealerAccount = accountMapper.findUniqueByProperty("login_name",oldCode);
        }catch (Exception e){
            throw new HpsException(HpsErrorCode.OLDCODE_NO_EXIST_ERROR);
        }
        //查询账户
        if(oldDealerAccount==null){
            throw new HpsException(HpsErrorCode.OLDCODE_NO_ACOUNT_EXIST_ERROR);
        }
        //如果转换后的账号存在则提示错误
        Dealer newDealer = null;
        try{
            newDealer = mapper.findUniqueByProperty("company_code",newCode);
        }catch (Exception e){
            throw new HpsException(HpsErrorCode.DATA_ERROR);
        }
        if(newDealer!=null){
            throw new HpsException(HpsErrorCode.NEWCODE_TRANS_ERROR);
        }
        //如果转换后的account账号存在则提示错误
        DealerAccount newDealerAccount = null;
        try{
            newDealerAccount = accountMapper.findUniqueByProperty("login_name",newCode);
        }catch (Exception e){
            throw new HpsException(HpsErrorCode.DATA_ERROR);
        }
        //查询账户
        if(newDealerAccount!=null){
            throw new HpsException(HpsErrorCode.NEWCODE_TRANS_ACCOUNT_ERROR);
        }
        //更新账号
        oldDealer.setCompanyCode(newCode);
        oldDealerAccount.setLoginName(newCode);
        try{
            mapper.update(oldDealer);
            accountMapper.update(oldDealerAccount);
        }catch (Exception e){
            throw new HpsException(HpsErrorCode.UPDATE_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public void fromAcg(DealerForAcg dealerForAcg) throws AcgException {

	    //渠道默认ACG
        dealerForAcg.setChannelName("ACG");
        //uuid
        dealerForAcg.setId(IdGen.uuid());
        //创建时间
        dealerForAcg.setCreateDate(new Date());
        //校验抛出异常
        if(StringUtils.isEmpty(dealerForAcg.getCompanyCode())){
            throw new AcgException(AcgErrorCode.PARAM_COMPANYCODE_ERROR);
        }
        Dealer daread = null;
        try{
            daread = mapper.findUniqueByProperty("company_code",dealerForAcg.getCompanyCode());
        }catch (Exception e){
            throw new AcgException(AcgErrorCode.UNKNOWN_ERROR);
        }
        if(daread!=null){
            throw new AcgException(AcgErrorCode.HAVE_ERROR);
        }
        if(StringUtils.isEmpty(dealerForAcg.getCompanyName())){
            throw new AcgException(AcgErrorCode.PARAM_COMPANYNAME_ERROR);
        }
        if(StringUtils.isEmpty(dealerForAcg.getMobile())){
            throw new AcgException(AcgErrorCode.PARAM_MOBILE_ERROR);
        }
        if(StringUtils.isEmpty(dealerForAcg.getContacts())){
            throw new AcgException(AcgErrorCode.PARAM_CONTACTS_ERROR);
        }
//        if(StringUtils.isEmpty(dealer.getEmail())){
//            throw new AcgException(AcgErrorCode.PARAM_EMAIL_ERROR);
//        }
        try {
            Dealer dealer = new Dealer();
            dealer.setId(dealerForAcg.getId());
            dealer.setCompanyCode(dealerForAcg.getCompanyCode());
            dealer.setCompanyName(dealerForAcg.getCompanyName());
            dealer.setChannelName(dealerForAcg.getChannelName());
            dealer.setContacts(dealerForAcg.getContacts());
            dealer.setMobile(dealerForAcg.getMobile());
            dealer.setCreateDate(new Date());
            //存入dealer表
            mapper.insert(dealer);
        }catch (Exception e){
            throw new AcgException(AcgErrorCode.SAVE_ERROR);
        }
        DealerAccount dealerAccount = new DealerAccount();
        //uuid
        dealerAccount.setId(IdGen.uuid());
        //dealer id
        dealerAccount.setDealerId(dealerForAcg.getId());
        //经销商编码
        dealerAccount.setLoginName(dealerForAcg.getCompanyCode());
        //生成加密的密码
        String newpwd = SystemService.entryptPassword("123456");
        //密码
        dealerAccount.setPasswd(newpwd);
        //联系人
        dealerAccount.setRealName(dealerForAcg.getContacts());
        //手机号
        dealerAccount.setMobile(dealerForAcg.getMobile());
        //为管理员
        dealerAccount.setIsAdmin("1");
        //邮箱
        dealerAccount.setEmail(dealerForAcg.getEmail());
        //是否关闭
        dealerAccount.setIsClosed("0");
        //创建时间
        dealerAccount.setCreateDate(new Date());
        try {
            accountMapper.insert(dealerAccount);
        }catch (Exception e){
            throw new AcgException(AcgErrorCode.SAVE_ACCOUNT_ERROR);
        }

    }

	public List<Dealer> findListAllOrArea(Dealer dealer) {
		// TODO Auto-generated method stub
		
		return dealermapper.findListAllOrArea(dealer);
	}

	public List<Dealer> findUndertake(String state, String proGroup, String cityName) {
		// TODO Auto-generated method stub
		return dealermapper.findUndertake(state,proGroup,cityName);
	}

	public List<Dealer> findListdealer(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dealermapper.findListdealer(map);
	}
	
	
	public List<String> findCompanyCode(){
		return dealermapper.findCompanyCode();
	}
	
}