package com.haier.order.service;

import com.haier.order.dto.KpInvoiceDTO;
import com.haier.order.dto.OrderMainAppraiseDTO;
import com.haier.order.dto.OrderMainDTO;
import com.haier.order.dto.ProductAppraiseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface OrderWebService {


	/**
	 * 测试是否通
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/testConnet")
	public Call<Object> testConnet();
	/**
	 * 接口调用说明
	 * 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
	 * 调用原bbc 类中方法为：createBargaining
	 * @param BargainingCreateDTO 调用参数--BargainingCreateDTO
	 * @return 统一返回 Object ，具体接口还需修改
	 */
	@POST("/saleOrder/orderBusiness/bargaining/4web/createBargaining")
	public Call<Object> createBargaining(@Body Object BargainingCreateDTO);

	/**
	 * 接口调用说明
	 * 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
	 * 调用原bbc 类中方法为：submitBargaining
	 * @param oper 调用参数--oper
	 * @param bargainingUuid 调用参数--bargainingUuid
	 * @return 统一返回 Object ，具体接口还需修改
	 */
	@POST("/saleOrder/orderBusiness/bargaining/4web/submitBargaining")
	public Call<Object> submitBargaining(@Query("oper") String oper,@Query("bargainingUuid") String bargainingUuid);

	/**
	 * 接口调用说明
	 * 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
	 * 调用原bbc 类中方法为：auditUnPassBargaining
	 * @param BargainingEditDTO 调用参数--BargainingEditDTO
	 * @return 统一返回 Object ，具体接口还需修改
	 */
	@POST("/saleOrder/orderBusiness/bargaining/4web/auditUnPassBargaining")
	public Call<Object> auditUnPassBargaining(@Body Object BargainingEditDTO);

	/**
	 * 接口调用说明
	 * 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
	 * 调用原bbc 类中方法为：auditPassBargaining
	 * @param BargainingEditDTO 调用参数--BargainingEditDTO
	 * @return 统一返回 Object ，具体接口还需修改
	 */
	@POST("/saleOrder/orderBusiness/bargaining/4web/auditPassBargaining")
	public Call<Object> auditPassBargaining(@Body Object BargainingEditDTO);

	/**
	 * 接口调用说明
	 * 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
	 * 调用原bbc 类中方法为：cancelBargaining
	 * @param BargainingEditDTO 调用参数--BargainingEditDTO
	 * @return 统一返回 Object ，具体接口还需修改
	 */
	@POST("/saleOrder/orderBusiness/bargaining/4web/cancelBargaining")
	public Call<Object> cancelBargaining(@Body Object BargainingEditDTO);

	/**
	 * 接口调用说明
	 * 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
	 * 调用原bbc 类中方法为：getWithDetailAndLogByUuid
	 * @param uuid 调用参数--uuid
	 * @return 统一返回 Object ，具体接口还需修改
	 */
	@POST("/saleOrder/orderBusiness/bargaining/4web/getWithDetailAndLogByUuid")
	public Call<Object> getWithDetailAndLogByUuid(@Query("uuid") String uuid);

	/**
	 * 接口调用说明
	 * 调用原bbc 类路径：com.aebiz.plugins.b2b.salesorder.webapis.saleorder.orderbusiness.plugins.orderbusiness_p2.controller.Bargaining2WebController
	 * 调用原bbc 类中方法为：updateBargaining
	 * @param BargainingUpdateDTO 调用参数--BargainingUpdateDTO
	 * @return 统一返回 Object ，具体接口还需修改
	 */
	@POST("/saleOrder/orderBusiness/bargaining/4web/updateBargaining")
	public Call<Object> updateBargaining(@Body Object BargainingUpdateDTO);

	/**
	 * 调用原bbc 类路径：com.aebiz.plugins.order.normalordermgr.orderbusiness.plugins.orderbusiness_p17.controller.batchSaveSmallShopOrder4others
	 * 调用原bbc 类中方法为：batchSaveSmallShopOrder4others
	 * @param placeOrderDTO     下单传参辅助model
	 * @return                  统一返回 Object ，具体接口还需修改
	 */
	@POST("/order/normalordermgr/orderbusiness/4others/batchSaveSmallShopOrder4others")
	public Call<Object> batchSaveSmallShopOrder4others(@Body Object placeOrderDTO);

	/*************订单接口   开始****************/
	/**
	 * 获取订单列表（查询）
	 * @param qm
	 * @param nowPage
	 * @param pageShow
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/getListGetByCondition")
	public Call<Object> getListGetByCondition(@Body OrderMainDTO qm, @Query("nowPage") int nowPage, @Query("pageShow") int pageShow);

	/**
	 * 获取某状态下的订单数量
	 * @param qm
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/getCustomerCount")
	public Call<Object> getCustomerCount(@Body OrderMainDTO qm);

	/**
	 * 通过订单uuids获取订单信息
	 * @param listOrderUuids
	 * @return Map<String, OrderMainDTO>
	 */
	@POST("/order/normalordermgr/ordermain/4others/getOrderMapByUuids")
	public Call<Object> getOrderMapByUuids(@Query("listOrderUuids") List<String> listOrderUuids);

	/**
	 * 获取订单详情
	 * @param orderMainUuid
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/getViewModelByUuid")
	public Call<Object> getOrderViewModelByUuid(@Query("orderMainUuid") String orderMainUuid);



	/**
	 * 去支付
	 * @param orderMainUuid
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/toPayOrder")
	public Call<Object> toPayOrder(@Query("orderMainUuid") String orderMainUuid, @Query("customerUuid") String customerUuid
			, @Query("bankCode")String bankCode, @Query("payProductCode")String payProductCode
			, @Query("bankCardNo")String bankCardNo, @Query("bankAccountName")String bankAccountName);



	/**
	 * 将订单推送到LES
	 * @param orderMainUuid
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/toLES")
	public Call<Object> toLES(@Query("orderMainUuid") String orderMainUuid);
	/**
	 * 将订单推送到GVS
	 * @param orderMainUuid
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/toGVS")
	public Call<Object> toGVS(@Query("orderMainUuid") String orderMainUuid);
	/**
	 * 订单审核
	 * @param orderMainUuid              订单uuid
	 * @param checkedStatus              审核状态   0未审核   1审核通过    2审核不通过
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/checkedOrder")
	public Call<Object> checkedOrder(@Query("orderMainUuid")String orderMainUuid, @Query("checkedStatus")String checkedStatus,@Body KpInvoiceDTO kpInvoiceDTO);


	/**
	 * 订单支付完成
	 * @param orderNos
	 * @param oper
	 * @param operTypeCustomer
	 * @return
	 */
	@POST("/order/normalordermgr/orderbusiness/4others/batchPayOrder4others")
	public Call<Object> batchPayOrder(@Query("orderNos[]")List<String> orderNos, @Query("oper")String oper,
									  @Query("operTypeCustomer")String operTypeCustomer);

	/**
	 * 异步回调修改订单状态
	 * @param params
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/notify")
	public Call<Object> notify(@Query("params") String params);



	/**
	 * 异步回调修改订单状态
	 * @param params
	 * @return
	 */
	@POST("/order/cloudwarehouse/4others/saveWarehouseStatus")
	public Call<Object> saveWarehouseStatus(@Query("params") String params);



	/**
	 * 修改订单金额
	 * @param orderUuid
	 * @param orderMoney
	 * @param oper
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/updateOrderMoney")
	public Call<Object> updateOrderMoney(@Query("orderUuid") String orderUuid, @Query("orderMoney")String orderMoney, @Query("oper")String oper);

	/**
	 * 通过订单租uuid获取订单数据
	 * @param payOrderId
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/getByOrderGroupUuid")
	public Call<Object> getByOrderGroupUuid(@Query("payOrderId") String payOrderId);

	/**
	 * 取消订单
	 * @param orderUuid               订单uuid
	 * @param cancelReason             原因
	 * @param customerUuid             会员uuid
	 * @param operTypeCustomer         操作人类型   1会员   2商户   3平台
	 * @return
	 */
	@POST("/order/normalordermgr/orderbusiness/4others/closeOrder4others")
	public Call<Object> closeOrder(@Query("orderUuid") String orderUuid,
								   @Query("cancelReason") String cancelReason,
								   @Query("customerUuid") String customerUuid,
								   @Query("operTypeCustomer") String operTypeCustomer);


	/**
	 * 会员确认收货
	 * @param orderMainUuid
	 * @param customerUuid
	 * @param operTypeCustomer
	 * @return
	 */
	@POST("/order/normalordermgr/orderbusiness/4others/arrivalOrder4others")
	public Call<Object> arrivalOrder4others(@Query("orderMainUuid") String orderMainUuid,
											@Query("customerUuid") String customerUuid,
											@Query("operTypeCustomer") String operTypeCustomer);





	/*************订单接口   结束****************/

	/*************订单评价接口   开始****************/
	/**
	 * 获取订单评价列表（分页）
	 * @param qm
	 * @param nowPage
	 * @param pageShow
	 * @return
	 */
	@POST("/order/normalordermgr/ordermainappraise/4others/getByCondition")
	public Call<Object> getOrderMainAppraiseByCondition(@Body Object qm, @Query("nowPage") int nowPage,@Query("pageShow") int pageShow);
	/**
	 * 获取订单评价详情
	 * @param uuid
	 * @return
	 */
	@POST("/order/normalordermgr/ordermainappraise/4others/getByUuid")
	public Call<Object> getOrderMainAppraiseByUuid(@Query("uuid") String uuid);

	/**
	 * 通过订单uuid获取订单评价信息
	 * @param orderUuid
	 * @return
	 */
	@POST("/order/normalordermgr/ordermainappraise/4others/getOrderMainAppraiseByOrderUuid_P2")
	public Call<Object> getOrderMainAppraiseByOrderUuid(@Query("orderUuid") String orderUuid);

	/**
	 *保存订单评价
	 * @param omam
	 * @return
	 */
	@POST("/order/normalordermgr/ordermainappraise/4others/saveOrderMainAppraise_P2")
	public Call<Object> saveOrderMainAppraise(@Body OrderMainAppraiseDTO omam);
	/***********订单评价接口   结束****************/

	/***********商品评价接口   开始****************/
	/**
	 * 获取商品评价列表（分页）
	 * @param qm
	 * @param nowPage
	 * @param pageShow
	 * @return
	 */
	@POST("/product/productappraisemgr/productappraise/4others/getByCondition")
	public Call<Object> getProductAppraiseByCondition(@Body ProductAppraiseDTO qm, @Query("nowPage") int nowPage, @Query("pageShow") int pageShow);
	/**
	 * 获取商品评价详情
	 * @param uuid
	 * @return
	 */
	@POST("/product/productappraisemgr/productappraise/4others/getByUuid")
	public Call<Object> getProductAppraiseByUuid(@Query("uuid") String uuid);
	/**
	 * 保存评价
	 * @param product 商品评价实体
	 * @return String 实体uuid
	 */
	@POST("/product/productappraisemgr/productappraise/4others/myCreate4others")
	public Call<Object> saveProductAppraise(@Body ProductAppraiseDTO product);


	/***********商品评价接口   结束****************/


	/*******************发票相关接口   开始************************/
	/**
	 * 开票
	 * @param kpInvoiceDTO
	 * @return
	 */
	@POST("/finance/invoice/4web/openerInvoice")
	public Call<Object> openerInvoice(@Body KpInvoiceDTO kpInvoiceDTO);

	/**
	 * 发票查询
	 * @param orderId
	 * @return
	 */
	@POST("/finance/invoice/4web/queryInvoice")
	public Call<Object> queryInvoice(@Query("orderId")String orderId);

	/**
	 * 发票冲红
	 * @param orderId  订单编号
	 * @param reason   冲红原因
	 * @return
	 */
	@POST("/finance/invoice/4web/chInvoice")
	public Call<Object> chInvoice(@Query("orderId")String orderId,@Query("reason")String reason);


	/**
	 * 异步回调修改订单状态
	 * @return
	 */
	@POST("/finance/invoice/4web/invoice/notify")
	public Call<Object> invoiceNotify(@Query("params") String params);

	/*******************发票相关接口   结束************************/


	/**
	 * 发送短信接口
	 * @param mobile
	 * @param content
	 * @param time
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/sendMsgJob")
	public Call<Object> sendMsg(@Query("mobile") String mobile,@Query("content") String content,@Query("time") String time);


}
