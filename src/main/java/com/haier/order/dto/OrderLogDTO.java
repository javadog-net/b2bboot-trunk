package com.haier.order.dto;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class OrderLogDTO extends BaseModel{
	/**
	 * es缓存index
	 */
	public static final String ES_INDEX = "order_log";
	/**
	 * es缓存type
	 */
	public static final String ES_TYPE = "order_log";
	
	/**
	 * 订单主信息uuid
	*/
	@Column(length=32)
	private String orderMainUuid;
	/**
	 * 操作人类型
	*/
	@Column(length=1)
	private String operType;
	/**
	 * 操作类型
	*/
	@Column(length=2)
	private String opeType;
	/**
	 * 操作前数据
	*/
	@Column(length=200)
	private String beforeContent;
	/**
	 * 操作后数据
	*/
	@Column(length=200)
	private String afterContent;
	/**
	 * 备注
	*/
	@Column(length=2000)
	private String note;

	 /**
	  * 操作类型名称
	  * */
    @Transient
    private String opeTypeName;

    /**
     * 操作人类型
     */
    @Transient
    private String operTypeName;

    public String getOperTypeName() {
        return OrderLogParamModel.getOperType(this.getOperType());
    }
    
    public String getOpeTypeName() {
        return OrderLogParamModel.getOpeType(this.getOpeType());
    }
    
    
    

	public void setOpeTypeName(String opeTypeName) {
		this.opeTypeName = opeTypeName;
	}

	public void setOperTypeName(String operTypeName) {
		this.operTypeName = operTypeName;
	}

	public void setOrderMainUuid(String orderMainUuid){
		this.orderMainUuid=orderMainUuid;
	}
	public String getOrderMainUuid(){
		return orderMainUuid;
	}
	public void setOperType(String operType){
		this.operType=operType;
	}
	public String getOperType(){
		return operType;
	}
	public void setOpeType(String opeType){
		this.opeType=opeType;
	}
	public String getOpeType(){
		return opeType;
	}
	public void setBeforeContent(String beforeContent){
		this.beforeContent=beforeContent;
	}
	public String getBeforeContent(){
		return beforeContent;
	}
	public void setAfterContent(String afterContent){
		this.afterContent=afterContent;
	}
	public String getAfterContent(){
		return afterContent;
	}
	public void setNote(String note){
		this.note=note;
	}
	public String getNote(){
		return note;
	}


	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();

		result = prime * result + orderMainUuid!=null?orderMainUuid.hashCode() : 0;

		result = prime * result + operType!=null?operType.hashCode() : 0;

		result = prime * result + opeType!=null?opeType.hashCode() : 0;

		result = prime * result + beforeContent!=null?beforeContent.hashCode() : 0;

		result = prime * result + afterContent!=null?afterContent.hashCode() : 0;

		result = prime * result + note!=null?note.hashCode() : 0;
		return result;
	}
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		OrderLogDTO that = (OrderLogDTO) o;
		if (!orderMainUuid.equals(that.orderMainUuid))
			return false;
		if (!operType.equals(that.operType))
			return false;
		if (!opeType.equals(that.opeType))
			return false;
		if (!beforeContent.equals(that.beforeContent))
			return false;
		if (!afterContent.equals(that.afterContent))
			return false;
		if (!note.equals(that.note))
			return false;
		return true;
	}

	public String toString() {
		return super.toString()+"，BaseOrderLogModel {"+
				"orderMainUuid='"+orderMainUuid+"'"+
				", operType='"+operType+"'"+
				", opeType='"+opeType+"'"+
				", beforeContent='"+beforeContent+"'"+
				", afterContent='"+afterContent+"'"+
				", note='"+note+"'"+

				'}';
	}
}

