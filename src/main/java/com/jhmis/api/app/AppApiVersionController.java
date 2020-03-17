package com.jhmis.api.app;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.wechat.entity.AppVersion;
import com.jhmis.modules.wechat.service.AppVersionService;

@RestController
@RequestMapping("/api/app/version")
public class AppApiVersionController{
	@Autowired
	AppVersionService appVersionService;
	
	@RequestMapping("/newVersion")
	public AjaxJson newVersion(String version,String type) {
		if(type.equals("android")){
			type="1";
		}else if(type.equals("ios")){
			type="2";
		}
		Date nowTime = new Date();
		AppVersion appVersion = new AppVersion();
		appVersion.setType(type);
		appVersion.setUpdate("1");
		appVersion = appVersionService.getNewVersion(appVersion,nowTime);		
		//不管是否最新返回最后一个版本
		if(appVersion!=null){
			appVersion.setFromVersion(version);
			//将版本号按照.分隔，存入数量
			String[] oldVArray = version.split("\\.");
			String[] newVArray = appVersion.getVersion().split("\\.");
			//分别比较oldVArray与newVArray的前3位
			for(int i=0 ; i<3; i++ ){
				if(Integer.parseInt(newVArray[i]) < Integer.parseInt(oldVArray[i])){
					return  AjaxJson.fail("当前版本为最新版本");
				}else if(Integer.parseInt(newVArray[i]) == Integer.parseInt(oldVArray[i])){
					continue;
				}else{
					if(newVArray.length==3){
						//大版本更新直接返回大版本
	        			return AjaxJson.ok(appVersion);
	        		}
	        		if(newVArray.length==4){
	        			//小版本升级。若无相应大版本，则先返回相应的大版本
	        			AppVersion appDownload = new AppVersion();
	        			appDownload.setVersion(newVArray[0]+"."+newVArray[1]+"."+newVArray[2]);
	        			appDownload.setType(type);
	        			List<AppVersion> appDownloads = appVersionService.findList(appDownload);
	        			if(appDownloads == null || appDownloads.size()==0){
	        				return AjaxJson.fail("无"+appDownload.getVersion()+"版本！");
	        			}else{
	        				appDownload = appDownloads.get(0);
	        				appDownload.setFromVersion(version);
	        				return AjaxJson.ok(appDownload); 
	        			}
	        		}	        		 
				}
	        }
			//前三位相等的情况下执行
			if(newVArray.length==3 && oldVArray.length==3){
				return  AjaxJson.fail("当前版本为最新版本");
			}else if(newVArray.length==4 && oldVArray.length==3){
				return AjaxJson.ok(appVersion);
			}else if(newVArray.length==4 && oldVArray.length==4){
				if(Integer.parseInt(newVArray[3]) > Integer.parseInt(oldVArray[3])){
					return AjaxJson.ok(appVersion);
				}else{
					return  AjaxJson.fail("当前版本为最新版本");
				}				
			}else{
				return  AjaxJson.fail("版本有误！"); 
			}
		}else{
			return  AjaxJson.fail("当前版本为最初版本");
		}
	}
	
}
