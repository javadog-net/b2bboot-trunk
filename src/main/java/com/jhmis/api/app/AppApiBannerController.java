package com.jhmis.api.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.modules.wechat.entity.WxBanner;
import com.jhmis.modules.wechat.service.WxBannerService;

@RestController
@RequestMapping(value = "/api/app/banner")
public class AppApiBannerController {
	
	@Autowired
	WxBannerService wxBannerService;
	
	@RequestMapping("/find")
	public AjaxJson find(String classify, @RequestParam(value = "link", required = false) String link){
		WxBanner wxBanner = new WxBanner();
		wxBanner.setClassify(classify);
		wxBanner.setLink(link);
		List<WxBanner> wxBanners = wxBannerService.findList(wxBanner);
		return AjaxJson.ok(wxBanners);
	}
}
