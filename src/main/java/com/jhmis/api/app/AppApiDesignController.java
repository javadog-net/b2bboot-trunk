package com.jhmis.api.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.beanvalidator.RegValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.wechat.entity.Design;
import com.jhmis.modules.wechat.service.DesignService;

@RestController
@RequestMapping("/api/app/design")
public class AppApiDesignController {
	@Autowired
	DesignService designService;
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public AjaxJson add(String name,String phone){
		if(StringUtils.isBlank(name)){
			return AjaxJson.fail("姓名不能为空");
		}
		if(StringUtils.isBlank(phone)){
			return AjaxJson.fail("手机不能为空");
		}
		if(!RegValidators.isMobile(phone)){
			return AjaxJson.fail("手机格式不正确");
		}
		Design design = new Design();		
		design.setPhone(phone);	
		design.setName(name);
		if(!designService.findList(design).isEmpty()){
			return AjaxJson.fail("您已提交过免费设计申请！");
		}
		
		design.setSource("app");
		designService.save(design);
		
		return AjaxJson.ok("免费设计申请提交成功！");
	}
	
	
}
