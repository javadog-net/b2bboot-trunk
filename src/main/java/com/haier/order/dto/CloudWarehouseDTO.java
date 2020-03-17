package com.haier.order.dto;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CloudWarehouseDTO extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5996007371569901537L;
	/**
	 * 消息code，消息唯一码，不能重复。
	 */
	
	private String outcode;
	/**
	 * 通知类型，接口方法名rrs_statusback
	 */
	
	private String butype;
	/**
	 * 来源，根据系统区分，固定值 “JSH”
	 */
	
	private String source;
	/**
	 * 签名
	 * base64(MD5(content+keyValue))
	 * ketValue：Haier,123
	 * content：如下
	 */
	
	private String sign;
	/**
	 * 仓库编码：按日日顺C码
	 */
	
	private String storecode;
	/**
	 * 业务单号：外围系统单号
	 */
	
	private String orderno;
	/**
	 * 快递单号：自动分配的快递单号或客户生成的快递单号---61开头无用
	 */
	
	private String expno;
	/**
	 * 操作人
	 */
	
	private String operator;
	/**
	 * 操作人联系方式：针对TMS状态回传有效联系人电话
	 */
	
	private String opercontact;
	/**
	 * 操作时间：格式 yyyy-mm-dd hh:mm:ss
	 */
	
	private String operdate;
	/**
	 * 状态
	 * 信息内容：
	 * WMS_ACCEPT -接单
	 * WMS_FAILED -拒单
	 * TMS_ACCEPT 揽收
	 * TMS_REJECT -揽收失败
	 * TMS_DELIVERING -派送
	 * TMS_STATION_IN -分站进
	 * TMS_STATION_OUT -分站出
	 * TMS_CHANGE -用户改约
	 * TMS_ERROR -异常
	 * TMS_CANCEL_SUCCESS –拦截成功
	 * TMS_SIGN -签收成功
	 * TMS_FAILED -拒签
	 * TMS_DELIVERY-网点交付
	 * TMS_FAILED_IN -转运入库
	 * TMS_FAILED_OUT -转运出库
	 * TMS_RETURN-拒收入库
	 * TMS_RESULT_S-取件成功
	 * TMS_RESULT_F-取件失败
	 * COD_SUCCESS 扣款成功
	 * TMS_DB_CREATE 调拨单生成
	 * TMS_DB_OUT调拨单出库
	 * TMS_DB_IN 调拨单入库
	 */
	
	private String status;
	/**
	 * 状态说明
	 */
	
	private String content;
	/**
	 * 备注
	 */
	
	private String remark;
	/**
	 * 属性备注
	 */
	
	private String attributes;



	public void setOutcode(String outcode){
		this.outcode=outcode;
	}
	public String getOutcode(){
		return outcode;
	}
	public void setButype(String butype){
		this.butype=butype;
	}
	public String getButype(){
		return butype;
	}
	public void setSource(String source){
		this.source=source;
	}
	public String getSource(){
		return source;
	}
	public void setSign(String sign){
		this.sign=sign;
	}
	public String getSign(){
		return sign;
	}
	public void setStorecode(String storecode){
		this.storecode=storecode;
	}
	public String getStorecode(){
		return storecode;
	}
	public void setOrderno(String orderno){
		this.orderno=orderno;
	}
	public String getOrderno(){
		return orderno;
	}
	public void setExpno(String expno){
		this.expno=expno;
	}
	public String getExpno(){
		return expno;
	}
	public void setOperator(String operator){
		this.operator=operator;
	}
	public String getOperator(){
		return operator;
	}
	public void setOpercontact(String opercontact){
		this.opercontact=opercontact;
	}
	public String getOpercontact(){
		return opercontact;
	}
	public void setOperdate(String operdate){
		this.operdate=operdate;
	}
	public String getOperdate(){
		return operdate;
	}
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setAttributes(String attributes){
		this.attributes=attributes;
	}
	public String getAttributes(){
		return attributes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();

		result = prime * result + outcode!=null?outcode.hashCode() : 0;

		result = prime * result + butype!=null?butype.hashCode() : 0;

		result = prime * result + source!=null?source.hashCode() : 0;

		result = prime * result + sign!=null?sign.hashCode() : 0;

		result = prime * result + storecode!=null?storecode.hashCode() : 0;

		result = prime * result + orderno!=null?orderno.hashCode() : 0;

		result = prime * result + expno!=null?expno.hashCode() : 0;

		result = prime * result + operator!=null?operator.hashCode() : 0;

		result = prime * result + opercontact!=null?opercontact.hashCode() : 0;

		result = prime * result + operdate!=null?operdate.hashCode() : 0;

		result = prime * result + content!=null?content.hashCode() : 0;

		result = prime * result + remark!=null?remark.hashCode() : 0;

		result = prime * result + attributes!=null?attributes.hashCode() : 0;
		return result;
	}
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		CloudWarehouseDTO that = (CloudWarehouseDTO) o;
		if (!outcode.equals(that.outcode))
			return false;
		if (!butype.equals(that.butype))
			return false;
		if (!source.equals(that.source))
			return false;
		if (!sign.equals(that.sign))
			return false;
		if (!storecode.equals(that.storecode))
			return false;
		if (!orderno.equals(that.orderno))
			return false;
		if (!expno.equals(that.expno))
			return false;
		if (!operator.equals(that.operator))
			return false;
		if (!opercontact.equals(that.opercontact))
			return false;
		if (!operdate.equals(that.operdate))
			return false;
		if (!content.equals(that.content))
			return false;
		if (!remark.equals(that.remark))
			return false;
		if (!attributes.equals(that.attributes))
			return false;
		return true;
	}

	public String toString() {
		return super.toString()+"，CloudWarehouseDTO {"+
				"outcode='"+outcode+"'"+
				", butype='"+butype+"'"+
				", source='"+source+"'"+
				", sign='"+sign+"'"+
				", storecode='"+storecode+"'"+
				", orderno='"+orderno+"'"+
				", expno='"+expno+"'"+
				", operator='"+operator+"'"+
				", opercontact='"+opercontact+"'"+
				", operdate='"+operdate+"'"+
				", content='"+content+"'"+
				", remark='"+remark+"'"+
				", attributes='"+attributes+"'"+

				'}';
	}

}

