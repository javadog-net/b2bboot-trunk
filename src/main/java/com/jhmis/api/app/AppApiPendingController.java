package com.jhmis.api.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.wechat.service.WxPendingService;

@RestController
@RequestMapping("/api/app/pending")
public class AppApiPendingController extends BaseController{
	
	@Autowired
	private WxPendingService wxPendingService;

	
	/** 
	  * @Title: pendingList 
	  * @Description: TODO 查询该用户所有的未处理事项  
	  * @param userId
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年1月9日上午9:52:49
	  */
	@RequestMapping("/pendingList")
	public AjaxJson pendingList(){
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
        String userId = purchaserAccount.getId();

		wxPendingService.remind(userId);	
			
		return AjaxJson.ok(wxPendingService.findPendingList(userId));
	}
	
	/** 
	  * @Title: pendingStatus 
	  * @Description: TODO  查看未处理事项后 修改该事项的状态为已读
	  * @param id
	  * @return 
	  * @return AjaxJson
	  * @author tc
	  * @date 2019年1月9日上午9:53:16
	  */
	@RequestMapping("/pendingStatus")
	public AjaxJson pendingStatus(@RequestParam(value="id")String id){
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
		//根据传入的id,设置相应的消息状态为已读
		if(StringUtils.isBlank(id)){
			return AjaxJson.fail("参数异常！");
		}
		wxPendingService.updataStatus(id);		
		return AjaxJson.ok();
	}
	
	/**
	 * 根据userId查询该用户未处理事项的数量
	 * @return
	 */
	@RequestMapping("/pendingNumber")
	public AjaxJson pendingNumber(){
		
		PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(purchaserAccount == null){
        	return AjaxJson.fail("401","请重新登录！");
        }
        String userId = purchaserAccount.getId();
		return AjaxJson.ok(wxPendingService.pendingNumber(userId));
	}
	
}
