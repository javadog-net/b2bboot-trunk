/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.jhmis.common.utils.IdGen;
import com.jhmis.modules.shop.entity.DirectCart;
import com.jhmis.modules.shop.entity.DirectMsgProduct;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.mapper.DirectCartMapper;
import com.jhmis.modules.shop.mapper.DirectMsgProductMapper;
import com.jhmis.modules.shop.mapper.dealer.DealerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.DirectMsg;
import com.jhmis.modules.shop.mapper.DirectMsgMapper;

/**
 * 直采需求Service
 * @author hdx
 * @version 2019-04-03
 */
@Service
@Transactional(readOnly = true)
public class DirectMsgService extends CrudService<DirectMsgMapper, DirectMsg> {

	@Autowired
	DealerMapper dealerMapper;
	@Autowired
	DirectCartMapper directCartMapper;
	@Autowired
	DirectMsgProductMapper directMsgProductMapper;

	public DirectMsg get(String id) {
		return super.get(id);
	}
	
	public List<DirectMsg> findList(DirectMsg directMsg) {
		return super.findList(directMsg);
	}
	
	public Page<DirectMsg> findPage(Page<DirectMsg> page, DirectMsg directMsg) {
		return super.findPage(page, directMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(DirectMsg directMsg) {
		super.save(directMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(DirectMsg directMsg) {
		super.delete(directMsg);
	}

	@Transactional(readOnly = false)
	public Map<String,Object> saveOrder(String projectName, String shPartner, String pyPartner, String bpPartner, String cartIds, DealerAccount currentAccount, String addressProvince, String addressCity, String addressCounty) {
		Map<String,Object> map = new HashMap<>();
		DirectMsg directMsg = new DirectMsg();
		//项目名称
		directMsg.setProjectname(projectName);
		//送达方
		directMsg.setShPartner(shPartner);
		//付款方
		directMsg.setPyPartner(pyPartner);
		//开票方
		directMsg.setBpPartner(bpPartner);
		//需求单编号
		directMsg.setMsgOrder(getOrderIdByTime());
		//甲方公司名
		Dealer d = dealerMapper.get(currentAccount.getId());
		//联系公司
		directMsg.setFirstCompanyName(d.getCompanyName());
		//联系人
		directMsg.setFirstContactName(d.getContacts());
		//联系电话
		directMsg.setPhone(d.getMobile());
		//状态待审核
		directMsg.setStatus(DirectMsg.DIRECT_MSG_STATUS_WAIT);
		//省市区
		directMsg.setAddressProvince(addressProvince);
		directMsg.setAddressCity(addressCity);
		directMsg.setAddressCounty(addressCounty);
		//添加时间
		directMsg.setAddTime(new Date());
		directMsg.setId(IdGen.uuid());
		mapper.insert(directMsg);
		//添加入产品详情
		String []cartId = cartIds.split(",");
		List<DirectMsgProduct> directMsgProductList = new ArrayList<>();
		for(int i=0; i<cartId.length; i++){
			DirectMsgProduct dmp = new DirectMsgProduct();
			DirectCart directCart = directCartMapper.get(cartId[i]);
			dmp.setDirectMsgId(directMsg.getId());
			dmp.setDirectMsgOrder(directMsg.getMsgOrder());
			dmp.setAddTime(new Date());
			dmp.setGoodsCode(directCart.getGoodsCode());
			dmp.setNum(directCart.getChooseNum()==null?"0":directCart.getChooseNum().toString());
			dmp.setPrice(directCart.getPrice());
			dmp.setStoreGoodsId(directCart.getStoreGoodsId());
			dmp.setProductGroupCode(directCart.getProductGroupCode());
			dmp.setProductGroupName(directCart.getProductGroupName());
			dmp.setId(IdGen.uuid());
			directMsgProductMapper.insert(dmp);
			directMsgProductList.add(dmp);
			//将购物车信息删除
			directCartMapper.delete(cartId[i]);
		}
		directMsg.setDirectMsgProductList(directMsgProductList);
		map.put("directMsg",directMsg);
		return  map;
	}


	public static String getOrderIdByTime() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String newDate=sdf.format(new Date());
		String result="";
		Random random=new Random();
		for(int i=0;i<3;i++){
			result+=random.nextInt(10);
		}
		return newDate+result;
	}

	
}