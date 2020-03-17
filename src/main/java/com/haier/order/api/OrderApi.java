package com.haier.order.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.haier.order.dto.RetDTO;

import retrofit2.http.POST;



public interface OrderApi {
	
	
	/**
	 * 测试是否通
	 * @return
	 */
	@POST("/order/normalordermgr/ordermain/4others/testConnet")
	public RetDTO testConnet();
	
	/**
	 * 根据订单id获取订单信息
	 * @param orderId
	 * @return List<OrderMainDTO>
	 */
	@POST("/order/normalordermgr/ordermain/4others/getByOrderId")
	public RetDTO getByOrderId(@RequestParam("orderId")String orderId);

    /**
     * 根据订单uuid获取
     * @param listOrderUuids
     * @return Map<String, OrderMainDTO>
     */
	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getOrderMapByUuids")
	public RetDTO getOrderMapByUuids(@RequestParam("listOrderUuids[]") List<String> listOrderUuids);

	
	/**
	 * @param orderMainUuid
	 * @return OrderMainDTO
	 */
	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getViewModelByUuid")
	public RetDTO getViewModelByUuid(@RequestParam("orderMainUuid")String orderMainUuid);

	/**
	 * @param orderNos
	 * @return List<OrderMainDTO>
	 */
	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getByOrderIds")
	public RetDTO getByOrderIds(@RequestParam("orderNos[]")List<String> orderNos);

	
	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getUnsettleOrder")
	public RetDTO getUnsettleOrder(@RequestParam("timeStr")String timeStr);

	/**
	 * @param payOrderId
	 * @return List<OrderMainDTO>
	 */
	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getByOrderGroupUuid")
	public RetDTO getByOrderGroupUuid(@RequestParam("payOrderId") String payOrderId);

	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getOrderIdsByOrderGroupUuid")
	public RetDTO getOrderIdsByOrderGroupUuid(@RequestParam("payOrderUuid")String payOrderUuid);

//	/**
//	 * @param qm
//	 * @param nowPage
//	 * @param pageShow
//	 * @return List<OrderMainDTO>
//	 */
//	@RequestMapping(value = "/getListGetByCondition")
//	public RetDTO getListGetByCondition(@RequestBody OrderMainQueryDTO qm, @RequestParam(value="nowPage",required=true) int nowPage,
//			@RequestParam(value="pageShow",required=true) int pageShow);

	/**
	 * 
	 * @param storeUuid
	 * @return int
	 */
	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getUnFinishAccountCountByStoreUuid")
	public RetDTO getUnFinishAccountCountByStoreUuid(@RequestParam("storeUuid")String storeUuid);
	/**
	 * 
	 * @param storeUuid
	 * @return int
	 */
	@RequestMapping(value = "/order/normalordermgr/ordermain/4others/getUnFinishOrderByStoreUuid")
	public RetDTO getUnFinishOrderByStoreUuid(@RequestParam("storeUuid")String storeUuid);

	
	
	

}

