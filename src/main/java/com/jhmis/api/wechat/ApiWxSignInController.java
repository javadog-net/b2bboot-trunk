package com.jhmis.api.wechat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.wechat.entity.WxSignIn;
import com.jhmis.modules.wechat.service.WxSignInService;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**   
 * <p>Title: ApiWxSignInController</p>  
 * <p>Description: </p>签到 功能  查看签到天数，连续天数 
 * @author tc  
 * @date 2018年12月26日 下午3:45:49
 */ 
@RestController
@RequestMapping("/api/wechat/signIn")
@Api(value = "WxSignInController", description = "小程序签到")
public class ApiWxSignInController extends BaseController {

    @Autowired
    private WxSignInService wxSignInService;
    @Autowired
    private PurchaserAccountService purchaserAddressService;


    /** 
      * @Title: addsignin 
      * @Description: TODO 签到功能 并判断是否连续，未连续则连续天数为0，否则+1  
      * @param wxSignIn
      * @return 
      * @return AjaxJson
      * @author tc
      * @date 2018年12月26日下午3:44:49
      */
    @RequestMapping(value = "/addsignin")
    @ApiOperation(notes = "addsignin", httpMethod = "POST", value = "签到接口")
    public AjaxJson add(@RequestParam(value="id", required=true)String id) {
        //获取当前用户信息
       // PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
    	
        PurchaserAccount currentAccount=purchaserAddressService.get(id);
        if (currentAccount == null || StrUtil.isBlank(currentAccount.getId())) {
            return AjaxJson.fail(Constants.ERROR_CODE_NO_USER, Constants.ERROR_DESC_NO_USER);
        }
        WxSignIn wxSignIn=new WxSignIn();
        wxSignIn.setUserId(currentAccount.getId());
        Calendar now = Calendar.getInstance();  
        System.out.println("年: " + now.get(Calendar.YEAR));  
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1));  
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
        String year=String.valueOf(now.get(Calendar.YEAR));
        String month=String.valueOf(now.get(Calendar.MONTH) + 1);
        String day=String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        System.out.println("已赋值");
        wxSignIn.setSignInYear(year);
        System.out.println(year+"y");
        wxSignIn.setSignInMonth(month);
        wxSignIn.setSignInDay(day);
        System.out.println("set值");
        WxSignIn wxsi= wxSignInService.findsigntoday(currentAccount.getId(), year, month, day);
        System.out.println(wxsi+"wxsi");
        Calendar ca = Calendar.getInstance();
	    ca.setTime(new Date()); // 获取当前时间
   		ca.add(Calendar.DATE, -1);// 日期减1 
   		Date resultDate = ca.getTime(); // 
   	    SimpleDateFormat mm = new SimpleDateFormat("M"); 
   	    System.out.println(mm.format(resultDate)); 
   	    String month1=mm.format(resultDate);
   	    SimpleDateFormat yy = new SimpleDateFormat("yyyy");
   	    String year1=yy.format(resultDate);
   	    SimpleDateFormat dd = new SimpleDateFormat("d");
	    String day1=dd.format(resultDate);
	    System.out.println(year1+month1+day1+"前一天");
	    WxSignIn wxSignIn1=new WxSignIn();
	    wxSignIn1.setUserId(currentAccount.getId());
	    wxSignIn1.setSignInYear(year1);
	    wxSignIn1.setSignInMonth(month1);
	    wxSignIn1.setSignInDay(day1);
	    WxSignIn wx= wxSignInService.findsigntoday(currentAccount.getId(), year1, month1, day1);
	    System.out.println(wx+"wx");
       if(wxsi==null){
    	   
    	   wxSignIn.setCreateTime(new Date());
    	   wxSignInService.save(wxSignIn);
    	  WxSignIn ws= wxSignInService.findusertab(currentAccount.getId(),currentAccount.getId());
      if(ws==null){
    	  System.out.println("ws=null1");
   	    	WxSignIn w=new WxSignIn();
   	    	w.setUserId(currentAccount.getId());
   	    	w.setUserIdTab(currentAccount.getId());
   	    	w.setContinueDay("1");
   	    	System.out.println("ws=null2");
   	    	System.out.println(w+"w");
   	    	wxSignInService.save(w);
   	    	return AjaxJson.ok("签到成功！");
   	    }else{
	     if(wx==null){
	       String continueday="1";
	       System.out.println("连续签到中断 昨天未签到");
    	   wxSignInService.updatecontinueday(currentAccount.getId(),currentAccount.getId(),continueday);
    	   return AjaxJson.ok("签到成功！");
	     }else{
	    	 String continueday=ws.getContinueDay();
	    	 System.out.println(continueday+"continueday查看连续签到天数");
	    	 Integer i=Integer.parseInt(continueday);
	    	 i=i+1; 
	    	 continueday=String.valueOf(i);
	    	 System.out.println(continueday+"转化成string");
	    	 System.out.println(i+"昨天已经签到，联系签到");
	    	 try{
	    	wxSignInService.updatecontinueday(currentAccount.getId(),currentAccount.getId(),continueday);
	    	 }catch (Exception e) {
				// TODO: handle exception
	    		 e.printStackTrace();
	    		 System.out.println(e.toString());
			}
	    	System.out.println("t");
	    	  return AjaxJson.ok("签到成功！");
	     }
   	    }
       }else{
    	   System.out.println("已经签到过了");
    	   return AjaxJson.fail("今天已签到，请明天再来！");
       }
    }


    /** 
      * @Title: signincontinue 
      * @Description: TODO 统计签到页面连续签到天数 以及每个月的签到天数
      * @param year前台传需要查询的年
      * @param month前台传入需要查询的月
      * @return 
      * @return AjaxJson
      * @author tc
      * @date 2018年12月26日下午3:46:29
      */
    @RequestMapping(value = "/signincontinue",method=RequestMethod.POST)
    public AjaxJson signin(@RequestParam(value="year",required=true)String year,
    		                  @RequestParam(value="month",required=true)String month,
    		                  @RequestParam(value="id", required=true)String id) {
        //获取当前用户信息
       // PurchaserAccount currentAccount = PurchaserUtils.getPurchaserAccount();
    	PurchaserAccount currentAccount=purchaserAddressService.get(id);
        if (currentAccount == null || StrUtil.isBlank(currentAccount.getId())) {
            return AjaxJson.fail(Constants.ERROR_CODE_NO_USER, Constants.ERROR_DESC_NO_USER);
        }
        WxSignIn wxSignIn=new WxSignIn();
        wxSignIn.setUserId(currentAccount.getId());
        wxSignIn.setSignInMonth(month);
        wxSignIn.setSignInYear(year);
        List<WxSignIn> list= wxSignInService.findsign(currentAccount.getId(), year, month);
        String continueday=wxSignInService.findcontinueday(currentAccount.getId(),currentAccount.getId());
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("list", list);
        map.put("continueday", continueday);
        return AjaxJson.ok(map);
    }
    
    
    
}
