package com.jhmis.api.mobiles;

import com.jhmis.common.beanvalidator.RegValidators;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.wechat.entity.Design;
import com.jhmis.modules.wechat.service.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
*@Description 移动端 免费设计
*@Author t.c
*@Date 2019-10-30
*/
@RestController
@RequestMapping("/api/mobiles/design")
public class MobilesApiDesignController {
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
			//循环遍历，若发现 申请的都已经有人处理，则继续可以申请，否则不可以申请
		List<Design> list=designService.findList(design);
		if(list!=null&&list.size()>0) {
			for (Design d : list) {
               if(StringUtils.isBlank(d.getReplyPerson())){
				   return AjaxJson.fail("您已提交过免费设计申请！");
			   }
			}
		}
		design.setSource("mobiles");
		designService.save(design);
		
		return AjaxJson.ok("免费设计申请提交成功！");
	}
	
	
}
