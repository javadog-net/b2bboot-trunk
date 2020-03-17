package com.jhmis.api.wechat;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.wechat.service.WxPendingService;

@RestController
@RequestMapping("/api/wechat/pending")
public class ApiWxPendingController {
	
	@Autowired
	private WxPendingService wxPendingService;

	
	@RequestMapping("/pendingList")
	public AjaxJson pendingList(@RequestParam(value="userId")String userId){

		wxPendingService.remind(userId);	
			
		return AjaxJson.ok(wxPendingService.findPendingList(userId));
	}
	
	@RequestMapping("/pendingStatus")
	public AjaxJson pendingStatus(String id){
		//根据传入的id,设置相应的消息状态为已读
		wxPendingService.updataStatus(id);		
		return AjaxJson.ok();
	}
	
	@RequestMapping("/pendingNumber")
	public AjaxJson pendingNumber(String userId){
		//根据userId查询该用户未处理事项的数量
		return AjaxJson.ok(wxPendingService.pendingNumber(userId));
	}
	
}
