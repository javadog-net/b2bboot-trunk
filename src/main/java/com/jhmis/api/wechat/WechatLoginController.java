package com.jhmis.api.wechat;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.haier.user.api.UserApi;
import com.haier.user.api.dto.ExecuteResult;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.wechat.entity.WxMember;
import com.jhmis.modules.wechat.service.WxMemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 微信登录接口实现
 *
 * @author Lyz
 * @time 2018-11-21 13:36
 */
@RestController
@RequestMapping("/api/wechat/program")
public class WechatLoginController extends BaseController {

    @Value("${wx.program.appid}")
    private String appid;

    @Value("${wx.program.secret}")
    private String secret;

    @Value("${wx.program.code2Session-url}")
    private String code2SessionUrl;


    @Autowired
    private WxMemberService  wxMemberService;


    /**
     * 登录凭证校验。通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。
     * 文档地址：https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/code2Session.html
     *
     * @param code 小程序前台通过wx.login()获取的用户登录凭证（有效期五分钟）。
     */
    @RequestMapping("login")
    @ResponseBody
    public Map<String, String> demo(String code) {
        if (StrUtil.isNotBlank(code)) {
            String result = HttpRequest.get(code2SessionUrl + "?appid=" + appid + "&secret=" + secret +
                    "&js_code=" + code + "&grant_type=authorization_code").execute().body();
            Map<String, String> map = JSON.parseObject(result, new TypeReference<HashMap<String, String>>() {});
            //通过openid获取用户信息

            String errcode = map.get("errcode");
            if (StrUtil.isNotBlank(errcode)) {
                return null;
            }
            return map;
        }
        return null;
    }

    /**
     * 微信授权后将基本信息入库
     * @param
     * @param
     * @return
     */
    @ApiOperation(notes = "saveWxInfo", httpMethod = "POST", value = "微信授权后将基本信息入库", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "openid", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "avatarUrl", value = "图片", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "city", value = "城市", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "country", value = "国家", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "gender", value = "性别", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "language", value = "语言", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "nickName", value = "昵称", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省份", required = true, paramType = "form", dataType = "String"),
    })
    @PostMapping(value="/saveWxInfo")
    @ResponseBody
    public AjaxJson saveWxInfo(WxMember wxMember , HttpServletResponse response){
        //参数验证
        if(wxMember==null){
            return AjaxJson.fail("参数异常");
        }
        if(wxMember.getOpenid()==null){
            return AjaxJson.fail("openid参数异常");
        }
        wxMember.setNickname(filterEmoji(wxMember.getNickname()));
        WxMember wx = wxMemberService.findUniqueByProperty("openid",wxMember.getOpenid());
        if(wx==null){
            //保存
            wxMemberService.save(wxMember);
        }else{
            //更新
            wxMemberService.update(wxMember);
        }
        return AjaxJson.ok();
    }





}
