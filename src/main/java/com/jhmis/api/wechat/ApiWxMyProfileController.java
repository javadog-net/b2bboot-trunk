package com.jhmis.api.wechat;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.service.WxFeedbackService;
import com.jhmis.modules.wechat.service.WxGroupService;
import com.jhmis.modules.wechat.service.WxGroupUserService;

@RestController
@RequestMapping("/api/wechat/myprofile")
public class ApiWxMyProfileController {
	@Autowired
	PurchaserService purchaserservice;
	@Autowired
	PurchaserAccountService purchaseraccountservice;
	@Autowired
	WxGroupUserService wxgroupuserservice;
	@Autowired
	WxGroupService wxgroupservice;
	@Autowired
	WxFeedbackService wxFeedbaceService;
	/** 
	  * @Title: findmyprofile 
	  * @Description: TODO  我的资料查看
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2018年12月17日上午10:03:03
	  */
	@RequestMapping("/findmyprofile")
	public AjaxJson findmyprofile() {
		PurchaserAccount purchaseraccount = PurchaserUtils.getPurchaserAccount();
		if (purchaseraccount == null) {
			return AjaxJson.fail("获取信息有误，请重新登录！");
		}
		 purchaseraccount=purchaseraccountservice.get(purchaseraccount.getId());
		return AjaxJson.ok(purchaseraccount);
	}

	/** 
	  * @Title: updatemyprofile 
	  * @Description: TODO  我的资料修改 
	  * @param id   accountid
	  * @param address 详细地址
	  * @param departName 部门名称
	  * @param industryname 行业id
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2018年12月17日上午10:03:17
	  */
	@RequestMapping(value = "/updatemyprofile", method = RequestMethod.POST)
	public AjaxJson updatemyprofile(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "departName", required = true) String departName,
			@RequestParam(value = "industryname", required = true) String industryname,
			@RequestParam(value="companyname",required=true)String companyname,
			@RequestParam(value="realname",required=true)String realname
			) {
		if(StringUtils.isBlank(id)){
			System.out.println("id为空");
			return AjaxJson.ok("信息有误，请重新登录");
		}
		if (StringUtils.isBlank(departName)) {
			System.out.println("departname 为空");
			return AjaxJson.fail("请选择部门信息");
		}
		if (StringUtils.isBlank(address)) {
			System.out.println("address 为空");
			return AjaxJson.fail("请输入详细地址");
		}
		/*if (StringUtils.isBlank(companyname)) {
			System.out.println("companyname 为空");
			return AjaxJson.fail("请输入公司名称");
		}*/
		/*if (StringUtils.isBlank(realname)) {
			System.out.println("realname为空");
			return AjaxJson.fail("请输入个人名称");
		}*/
		
		if(industryname.equals("undefined")||industryname.equals("")){
			System.out.println("行业 为空 或 是 undefined");
			return AjaxJson.fail("请选择行业信息");
		}
		System.out.println("进入修改接口");
		PurchaserAccount account = purchaseraccountservice.getbyid(id);
		System.out.println(id+"id");
		System.out.println(address+"address"+departName+"departname"+industryname+"industryname");
		//System.out.println(account.getId()+"id");
		if (account == null) {
			System.out.println("account null");
			return AjaxJson.fail("信息有误，请重新登录！");
		}
		Purchaser purchaser = purchaserservice.get(account.getPurchaserId());
		if (StringUtils.isNotBlank(departName)) {
			account.setDepartName(departName);
			purchaser.setDepart(departName);
		}
		if (StringUtils.isNotBlank(address)) {
			purchaser.setDetailAddress(address);
		}
		if (StringUtils.isNotBlank(companyname)) {
			purchaser.setCompanyName(companyname);
		}
		if (StringUtils.isNotBlank(realname)) {
			account.setRealName(realname);
		}
		
		
		//通过accountid 查询出groupid集合
		/*List<WxGroupUser> list= wxgroupuserservice.findrepeatgroup(id);
		if(list.size()==0){//说明该用户一个群组（集合）都没有，修改信息并加入该群租。
			purchaseraccountservice.updateAccountInfo(account);
			purchaserservice.updateinfo(purchaser);
			WxGroup wxgroup=new WxGroup();
			wxgroup=wxgroupservice.findbysource(industryname);
		    String groupid=wxgroup.getId();
		    WxGroupUser wxGroupUser=new WxGroupUser();
			wxGroupUser.setGroupId(groupid);
			wxGroupUser.setUserId(id);
			wxGroupUser.setJoinTime(new Date());
			wxGroupUser.setIsAdmin("1");
			wxgroupuserservice.save(wxGroupUser);
			return AjaxJson.ok("修改信息成功,且您自动加入该行业群聊！");
		}*/
		//如果不是0，说明该用户有群里（集合），判断该集合中是有包含了该行业。若包含则不进行操作，若不包含则删除原先的，再进行添加
	    //String groupid=wxgroupservice.findbysource(industryname).getId();
	    /*System.out.println(groupid+"gid");
	    for(WxGroupUser wxgroupuser:list){
	    	System.out.println(wxgroupuser.getGroupId()+"limiandeid");
	    	if(wxgroupuser.getGroupId()!=null){
		    	if(wxgroupuser.getGroupId().equals(groupid)){//行业相同
		    		purchaseraccountservice.updateAccountInfo(account);
					purchaserservice.updateinfo(purchaser);
		    		return AjaxJson.ok("修改信息成功！");
		    	}
	    	}
	    }*/
	    PurchaserAccount purchaseraccount1=new PurchaserAccount();
		purchaseraccount1= purchaseraccountservice.get(id);
		String industryid=purchaseraccount1.getWxindustryname();//获取原行业id
		if(StringUtils.isNotBlank(industryid)){
		    if(industryid.equals(industryname)){
		    	purchaseraccountservice.updateAccountInfo(account);
				purchaserservice.updateinfo(purchaser);
				System.out.println("行业相同的，至修改了其他信息！");
	    		return AjaxJson.ok("修改信息成功！");
		    }else{
		    	WxGroupUser wgu=new WxGroupUser();
		    	wgu.setGroupId(wxgroupservice.findbysource(industryid).getId());
		    	wgu.setUserId(id);
		    	if (null!=wxgroupuserservice.get(wgu)){
		    		System.out.println("之前已加入行业群聊--删除前");
					wxgroupuserservice.deletebyidandgroupid(id,
						  wxgroupservice.findbysource(industryid).getId());//删除之前的
					System.out.println("之前已加入行业群聊--删除后");
		    	}
					WxGroupUser wxGroupUser1=new WxGroupUser();
					wxGroupUser1.setGroupId(wxgroupservice.findbysource(industryname).getId());
					wxGroupUser1.setUserId(id);
					wxGroupUser1.setJoinTime(new Date());
					wxGroupUser1.setIsAdmin("1");
					System.out.println("加入行业群聊--前");					
					wxgroupuserservice.save(wxGroupUser1);//，添加新的
					System.out.println("之前已加入行业群聊--后");
					if (StringUtils.isNotBlank(industryname)) {
						account.setWxindustryname(industryname);
					}
					purchaseraccountservice.updateAccountInfo(account);
					purchaserservice.updateinfo(purchaser);
					System.out.println("修改了行业，并且删除了之前的行业！");
					return AjaxJson.ok("修改信息成功,且您已自动加入该行业群聊！");
		    }
	    }
		else{
			System.out.println("industryid 空");
			if (StringUtils.isNotBlank(industryname)) {
				account.setWxindustryname(industryname);
			}
			purchaseraccountservice.updateAccountInfo(account);
			purchaserservice.updateinfo(purchaser);
			WxGroupUser wxGroupUser=new WxGroupUser();
			wxGroupUser.setGroupId(wxgroupservice.findbysource(industryname).getId());
			wxGroupUser.setUserId(id);
			wxGroupUser.setJoinTime(new Date());
			wxGroupUser.setIsAdmin("1");
			wxgroupuserservice.save(wxGroupUser);
			System.out.println("行业相同的，至修改了其他信息！");
    		return AjaxJson.ok("修改信息成功，且您已自动加入该行业群聊！");
		}
		
      // return AjaxJson.fail("修改信息失败，请重新登录修改！");
	}
	
	/** 
	  * @Title: feedback 
	  * @Description: TODO  
	  * @param userId
	  * @param content
	  * @return 
	  * @return AjaxJson
	  * @author mll
	  * @date 2018年12月18日上午11:14:35
	  */
	@RequestMapping("/feedback")
	public AjaxJson feedback(@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "content", required = true) String content){
		
		wxFeedbaceService.add(userId,content);
		return AjaxJson.ok("意见反馈提交成功！");
	}
	/** 
	  * @Title: checkupmyprofile 
	  * @Description: TODO  
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2018年12月18日下午1:35:53
	  */
	@RequestMapping(value="/checkupmyprofile" ,method=RequestMethod.POST)
	public AjaxJson checkupmyprofile(@RequestParam(value="accountid",required=true)String id){
		PurchaserAccount purchaseraccount = purchaseraccountservice.get(id);
		if(purchaseraccount==null){
			return AjaxJson.fail("个人信息有误，请重新登录！");
		}
	if(	StringUtils.isBlank(purchaseraccount.getDepartName())){
                        		
		return AjaxJson.fail("请完善部门信息！");
	}
	if(	StringUtils.isBlank(purchaseraccount.getDetailAddress())){
		
		return AjaxJson.fail("请完善地址信息！");
	}
	if(	StringUtils.isBlank(purchaseraccount.getWxindustryname())){
		
		return AjaxJson.fail("请完善行业信息！");
	}
			return AjaxJson.ok("检查ok!");
		
		
	}
	
}