package com.haier.order.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.order.TransferTemplate;
import com.haier.order.dto.RetDTO;
//import org.junit.Assert;
//import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;
public class OrderWebServiceT {
	private static Logger log = LoggerFactory.getLogger(OrderWebServiceT.class);
//	String remoteUrl="http://222.175.112.174:8625/";
	String remoteUrl="http://localhost:9018/";
	private TransferTemplate transferTemplate=new TransferTemplate();
	
//	@Test
	public void testConnet()throws Exception{
		Map bargainingCreateDTO=new HashMap();
//		bargainingCreateDTO.put("orderUuid","1");
//		bargainingCreateDTO.put("saleOrderId","saleOrderId");
		Map skuPrice=new HashMap();
//		skuPrice.put("skuNo1",8.0);
//		skuPrice.put("skuNo2",4.0);
//		bargainingCreateDTO.put("skuPrice",skuPrice);
//		bargainingCreateDTO.put("oper","oper");
//		Class[] argClazzs={Object.class};
//		Object[] args={bargainingCreateDTO};
		
		   // 1、生成调用retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(remoteUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2、生成调用实例
        OrderWebService service = retrofit.create(OrderWebService.class);
//        Method method=service.getClass().getMethod("testConnet");
        // 3、组织参数
        // 4、执行调用
        Call<Object> call =  service.testConnet();
        Object requestDTOS = call.execute().body();
//		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"testConnet",null,null);
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
//		return "aa";
	}

	//@Test
	public String createBargainingT(int i) throws Exception {
		// 声明参数
		// 值为：{"oper":"oper","orderUuid":"1","saleOrderId":"saleOrderId","skuPrice":{"skuNo1":8.0,"skuNo2":4.0}}
		Map bargainingCreateDTO=new HashMap();
		bargainingCreateDTO.put("orderUuid","1");
		bargainingCreateDTO.put("saleOrderId","saleOrderId");
		Map skuPrice=new HashMap();
		skuPrice.put("skuNo1",8.0);
		skuPrice.put("skuNo2",4.0);
		bargainingCreateDTO.put("skuPrice",skuPrice);
		bargainingCreateDTO.put("oper","oper");
		Class[] argClazzs={Object.class};
		Object[] args={bargainingCreateDTO};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：createBargaining
		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"createBargaining",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":""}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));
		return retDTO.getRetData();
	}



	//@Test
	public void submitBargainingT(String bargainingUuid) throws Exception {
		// 声明参数
		// 值为："ope"
		String oper="ope";
		// 值为："af2aedce0ca44dd396c897d8fe2fb2dd"
//		String bargainingUuid="af2aedce0ca44dd396c897d8fe2fb2dd";
		Class[] argClazzs={String.class,String.class};
		Object[] args={oper,bargainingUuid};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：submitBargaining
		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"submitBargaining",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":""}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));
	}

	//@Test
	public void auditUnPassBargainingT(String bargainingUuid) throws Exception {
		// 声明参数
		// 值为：{"bargainingUuid":"af2aedce0ca44dd396c897d8fe2fb2dd","note":"note","oper":"oper"}
		Map bargainingEditDTO=new HashMap();
		bargainingEditDTO.put("bargainingUuid",bargainingUuid);
		bargainingEditDTO.put("note","note");
		bargainingEditDTO.put("oper","oper");
		Class[] argClazzs={Object.class};
		Object[] args={bargainingEditDTO};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：auditUnPassBargaining
		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"auditUnPassBargaining",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":""}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));

	}
	//@Test
	public String getWithDetailAndLogByUuidT(String uuid) throws Exception {
		// 声明参数
		// 值为："647447d0c91c4c4a9d10f77181614968"
//		String uuid="647447d0c91c4c4a9d10f77181614968";
		Class[] argClazzs={String.class};
		Object[] args={uuid};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：getWithDetailAndLogByUuid
		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"getWithDetailAndLogByUuid",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":"{\"auditLogList\":[],\"bargainingDTO\":{\"afterPrice\":12.0,\"auditState\":\"1\",\"beforePrice\":0.0,\"createOpeTime\":\"2018-11-21 15:03:03\",\"createOper\":\"oper\",\"delFlag\":1,\"mapCondition\":{},\"opeTime\":\"2018-11-21 15:03:03\",\"oper\":\"oper\",\"orderUuid\":\"1\",\"saleOrderId\":\"saleOrderId\",\"sortName\":\"opeTime\",\"sortType\":\"desc\",\"uuid\":\"647447d0c91c4c4a9d10f77181614968\",\"version\":0},\"detailList\":[{\"afterSkuPrice\":4.0,\"bargainingUuid\":\"647447d0c91c4c4a9d10f77181614968\",\"beforeSkuPrice\":4.0,\"belongUuid\":\"\",\"createOpeTime\":\"2018-11-21 15:03:03\",\"createOper\":\"oper\",\"delFlag\":1,\"mapCondition\":{},\"opeTime\":\"2018-11-21 15:03:03\",\"oper\":\"oper\",\"skuNo\":\"skuNo2\",\"sortName\":\"opeTime\",\"sortType\":\"desc\",\"uuid\":\"d4b1cd33eb6f4026af39863d4c99242d\",\"version\":0},{\"afterSkuPrice\":8.0,\"bargainingUuid\":\"647447d0c91c4c4a9d10f77181614968\",\"beforeSkuPrice\":8.0,\"belongUuid\":\"\",\"createOpeTime\":\"2018-11-21 15:03:03\",\"createOper\":\"oper\",\"delFlag\":1,\"mapCondition\":{},\"opeTime\":\"2018-11-21 15:03:03\",\"oper\":\"oper\",\"skuNo\":\"skuNo1\",\"sortName\":\"opeTime\",\"sortType\":\"desc\",\"uuid\":\"e9a0176729804757bdc41603dbf8f888\",\"version\":0}]}"}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));
		return retDTO.getRetData();
	}

	//@Test
	public void updateBargainingT(String bargainingUuid,Map<String,Double> map) throws Exception {
		// 声明参数
		// 值为：{"bargainingUuid":"647447d0c91c4c4a9d10f77181614968","detailPrice":{"d4b1cd33eb6f4026af39863d4c99242d":9.0,"e9a0176729804757bdc41603dbf8f888":3.0},"oper":"oper"}
		Map bargainingUpdateDTO=new HashMap();
		bargainingUpdateDTO.put("bargainingUuid",bargainingUuid);
		Map detailPrice=new HashMap();
		bargainingUpdateDTO.put("detailPrice",map);
		bargainingUpdateDTO.put("oper","oper");
		Class[] argClazzs={Object.class};
		Object[] args={bargainingUpdateDTO};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：updateBargaining
		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"updateBargaining",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":""}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));

	}

	//@Test
	public void auditPassBargainingT(String bargainingUuid) throws Exception {
		// 声明参数
		// 值为：{"bargainingUuid":"af2aedce0ca44dd396c897d8fe2fb2dd","note":"审核通过","oper":"oper"}
		Map bargainingEditDTO=new HashMap();
		bargainingEditDTO.put("bargainingUuid",bargainingUuid);
		bargainingEditDTO.put("note","审核通过");
		bargainingEditDTO.put("oper","oper");
		Class[] argClazzs={Object.class};
		Object[] args={bargainingEditDTO};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：auditPassBargaining
		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"auditPassBargaining",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":""}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));

	}

	//@Test
	public void cancelBargainingT(String bargainingUuid) throws Exception {
		// 声明参数
		// 值为：{"bargainingUuid":"1472c1bae730400eb097cfb50f30ec0f","note":"取消","oper":"oper"}
		Map bargainingEditDTO=new HashMap();
		bargainingEditDTO.put("bargainingUuid",bargainingUuid);
		bargainingEditDTO.put("note","取消");
		bargainingEditDTO.put("oper","oper");
		Class[] argClazzs={Object.class};
		Object[] args={bargainingEditDTO};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：cancelBargaining
		Object requestDTOS =transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"cancelBargaining",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":""}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));

	}


//	@Test
	public void batchSaveSmallShopOrder4othersT() throws Exception {
		// 声明参数
		Map bargainingEditDTO=new HashMap();
		Class[] argClazzs={Object.class};
		Object[] args={bargainingEditDTO};
		// 调用方法
		// 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
		// 调用原bbc 类中方法为：cancelBargaining
		Object requestDTOS = transferTemplate.executeGsonTemplate(remoteUrl,OrderWebService.class,"batchSaveSmallShopOrder4others",args,argClazzs);
		// TODO 验证方法返回
		// 返回值为：{"retStatus":"1","retMessageKey":"","retMessage":"","retData":""}
		String jsonStr=JSON.toJSONString(requestDTOS);
		log.info(jsonStr);
		RetDTO retDTO= JSONObject.parseObject(jsonStr,RetDTO.class);
//		Assert.assertTrue(retDTO.getRetStatus().equals(retDTO.SUCCESS));

	}


}
