package com.haier.order.dto;

public class OrderProductAppraiseDTO {
	//商品评价DTO
	private OrderMainAppraiseDTO orderMainAppraiseDTO;
	//订单评价DTO
	private ProductAppraiseDTO productAppraiseDTO;
	
	public OrderMainAppraiseDTO getOrderMainAppraiseDTO() {
		return orderMainAppraiseDTO;
	}
	public void setOrderMainAppraiseDTO(OrderMainAppraiseDTO orderMainAppraiseDTO) {
		this.orderMainAppraiseDTO = orderMainAppraiseDTO;
	}
	public ProductAppraiseDTO getProductAppraiseDTO() {
		return productAppraiseDTO;
	}
	public void setProductAppraiseDTO(ProductAppraiseDTO productAppraiseDTO) {
		this.productAppraiseDTO = productAppraiseDTO;
	}
	@Override
	public String toString() {
		return "OrderProductAppraiseDTO [orderMainAppraiseDTO=" + orderMainAppraiseDTO + ", productAppraiseDTO="
				+ productAppraiseDTO + "]";
	}
}

