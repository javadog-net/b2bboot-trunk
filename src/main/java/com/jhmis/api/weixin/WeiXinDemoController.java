package com.jhmis.api.weixin;

import com.jhmis.common.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.weixin
 * @Author: hdx
 * @CreateTime: 2020-02-11 23:01
 * @Description: WeiXinDemoController
 */
@RestController
@RequestMapping("/weixin")
public class WeiXinDemoController {

    @Autowired
    private WeiXinService weiXinService;
    /**
     * 初始化微信JSSDK配置信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/initWXJSSDKConfigInfo")
    public AjaxJson initWXJSConfig (HttpServletRequest request, HttpServletResponse response) throws Exception{
        String shareUrl = request.getParameter("shareUrl");//分享的URL
        String json = "";
        try {
            Map map = weiXinService.initJSSDKConfigInfo(shareUrl);
            json = weiXinService.mapToJson(map);
        }catch (Exception e){
            AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(json);
    }

}
