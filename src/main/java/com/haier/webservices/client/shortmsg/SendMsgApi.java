package com.haier.webservices.client.shortmsg;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.shop.entity.dealer.DealerMsgTpl;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsgTpl;
import com.jhmis.modules.shop.service.dealer.DealerMsgTplService;
import com.jhmis.modules.shop.service.purchaser.PurchaserMsgTplService;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxMessageRecord;
import com.jhmis.modules.wechat.service.WxMessageRecordService;



  

/**   
 * <p>Title: SendMsgApi</p>  
 * <p>Description:短信接口 </p>  
 * @author tc  
 * @date 2019年8月29日 上午11:39:18
 */ 
//@Component
@Component
public class SendMsgApi {
	@Autowired
	private WxMessageRecordService wxMessageRecordService;
	@Autowired
	private DealerMsgTplService dealerMsgTplService;
	@Autowired
	private PurchaserMsgTplService purchaserMsgTplService;
	
	static Logger logger = LoggerFactory.getLogger(SendMsgApi.class);

    public static void main(String[] args) {
    	String aaa_c="521";
    	String content="您有一个{$521}退款单需要处理，退款编号：{$521}。";
    	List<String> list=new ArrayList<>();
    	list.add("123");
    	list.add("321");
    	for(String l:list){
    	String a="{$".concat(aaa_c).concat("}");
    	 String con= StringUtils.replaceOnce(content,a,l);
    	System.out.println(con);
    	content=con;
    	}
        System.out.println(content);    	
    }

    /**
	 * 发送短信功能
	 * @param mobilePhone
	 * @param content
	 * @return
	 */
	public  static String  SendMessage(String mobilePhone,String content) throws Exception{
		String result = "";
		boolean b=isPhone(mobilePhone);
		if(!b){
			result="手机号格式不准确";
			return result;
		}
		SendMesServiceSingleStub stub = null;
//		String targetEndpoint = "http://10.135.1.198:7001/EAI/RoutingProxyService/EAI_SOAP_ServiceRoot?INT_CODE=EAI_INT_1704";//测试
		String targetEndpoint = "http://10.135.7.152:7001/EAI/RoutingProxyService/EAI_SOAP_ServiceRoot?INT_CODE=EAI_INT_1704";
		try {
			stub = new SendMesServiceSingleStub(targetEndpoint);
			Options options = stub._getServiceClient().getOptions();
			options.setTimeOutInMilliSeconds(10000);
			options.setProperty(HTTPConstants.SO_TIMEOUT, 10000);
			options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, 10000);
			stub._getServiceClient().setOptions(options);
			SendMesServiceSingleStub.InputType inputType = new SendMesServiceSingleStub.InputType();
			SendMesServiceSingleStub.SendMesServiceSingle sendMess = new SendMesServiceSingleStub.SendMesServiceSingle();
			inputType.setDepartment("B2B");
			inputType.setMsgCode("B2B");
			inputType.setPhoneNum(mobilePhone);
			inputType.setMsgContnet(content);
			sendMess.setIn(inputType);
			SendMesServiceSingleStub.SendMesServiceSingleResponse response = stub.sendMesServiceSingle(sendMess);
			SendMesServiceSingleStub.OutputType outputType = response.getOut();
			result = outputType.getResult();
			logger.info("短信结果===>result"+result);
			String msgCode = outputType.getMsgCode();
			logger.info("短信msgcode===>"+msgCode);
		} catch (AxisFault e) {
			throw new Exception(e.getMessage());
		} catch (RemoteException e) {
			// TODO Auteo-generated catch block
			throw new Exception(e.getMessage());
		}
		return result;
	}
	
	/** 
	  * @Title: tplSendMessage 
	  * @Description: TODO 根据模版发送短信 
	  * @param phone 手机号
	  * @param tpl_code 模版的编号 
	  * @param listTpl 发送短信的参数值。对应模版的取值个数
	  * @param type  0 是 dealer , 1 是 purchaser
	  * @return 发送结果
	  * @throws Exception 
	  * @return String
	  * @author tc
	  * @date 2019年9月2日下午6:24:08
	  */
	public  String tplSendMessage(String phone,String tpl_code,List<String> listTpl,String type)
			throws Exception{
		String result="";
		try {
		
		if(type!=null&&type.equals("0")){//dealer 发送模版
			DealerMsgTpl dealerMsgTpl=new DealerMsgTpl();
			dealerMsgTpl.setCode(tpl_code);
			List<DealerMsgTpl> listDealer=dealerMsgTplService.findList(dealerMsgTpl);
			if(listDealer!=null&&listDealer.size()>0){
				dealerMsgTpl=listDealer.get(0);
			}
			//${}
			String content=dealerMsgTpl.getSmsContent();
			if(StringUtils.isNotBlank(content)){
				for(String tpl:listTpl){
				content=StringUtils.replaceOnce(content,"${".concat(tpl_code.trim()).concat("}"),tpl);
				}
			}
			
		 result=SendMessage(phone,content);
			
		}
		if(type!=null&&type.equals("1")){//purchaser 发送模版
			PurchaserMsgTpl purchaserMsgTpl=new PurchaserMsgTpl();
			purchaserMsgTpl.setCode(tpl_code);
			List<PurchaserMsgTpl> listPurchaser=purchaserMsgTplService.findList(purchaserMsgTpl);
			if(listPurchaser!=null&&listPurchaser.size()>0){
				purchaserMsgTpl=listPurchaser.get(0);
			}
			//${}
			String content=purchaserMsgTpl.getSmsContent();
			if(StringUtils.isNotBlank(content)){
				for(String tpl:listTpl){
				content=StringUtils.replaceOnce(content,"${".concat(tpl_code.trim())+"}",tpl);
				}
			}
			
			 result=SendMessage(phone,content);
		}
		
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		} 
		return result;
	} 
	
	
	
	/** 
	  * @Title: saveMessageRecord 
	  * @Description: TODO  保存短信履历
	  * @param mobile
	  * @param content
	  * @param result 
	  * @return void
	  * @author tc
	  * @date 2019年8月29日上午11:39:43
	  */
	public void  saveMessageRecord(String mobile, String content, String result) {
		WxMessageRecord wxMessageRecord = new WxMessageRecord();
		User user = UserUtils.getUser();
		if(user!=null){
			wxMessageRecord.setAddPerson(user.getLoginName());
		}
		wxMessageRecord.setAddTime(new Date());
		wxMessageRecord.setContent(content);
		wxMessageRecord.setResult(result);
		wxMessageRecord.setMobile(mobile);
		wxMessageRecordService.save(wxMessageRecord);
	}
	
	
	public static boolean isPhone(String phone) {
	    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	    if (phone.length() != 11) {
	       //("手机号应为11位数");
	        return false;
	    } else {
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(phone);
	        boolean isMatch = m.matches();
	        if (!isMatch) {
	         //   ("请填入正确的手机号");
	        }
	        return isMatch;
	    }
	}
	
	
	
	
}
