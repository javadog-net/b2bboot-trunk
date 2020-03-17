package com.haier.order.dto;


public class OrderAddressDTO extends BaseModel{
	
	private static final long serialVersionUID = 3704287774931734205L;
	
	/**
	 * 标识为 当前订单使用地址
	 */
	public static final String IS_USER_ADDRESS="0";
	
	/**
	 * 标识为 当前订单不使用地址
	 */
	public static final String NOT_USER_ADDRESS="1";
	/**
	 * 订单主信息uuid
	*/
	
	private String orderMainUuid;
	/**
	 * 用户收货地址uuid
	 */
	private String customerAddressUuid;
	/**
	 * 用户收货地址uuid
	 */
	
	private String customerUuid;
	/**
	 * 收货人
	*/
	private String receiver;
	/**
	 * 邮编
	*/
	private String postCode;
	/**
	 * 地址
	*/
	
	private String address;
	/**
	 * 手机
	*/
	private String mobile;
	/**
	 * 电话
	*/
	
	private String tel;
	/**
	 * 省编码
	*/
	
	private String province;
	/**
	 * 市编码
	*/
	
	private String city;
	/**
	 * 地区编码
	*/
	
	private String region;
	/**
	 * 街道编码
	*/
	
	private String street;
	/**
	 * 地址别名
	*/
	
	private String alias;
	/**
	 * 地址变更备注
	*/
	
	private String changeRemark;
	/**
	 * 变更费用,由会员支付给商户
	*/
	private double changeMoney;
	/**
	 * 下单时产生的订单收货地址,变更计数为1,表示没有变更过。2表示变更过1次
	*/
	private int changeCount;
	/**
	 *  是否是当前订单使用的地址  0:标识当前使用的  1：表示不使用
	 */
	private String addressState;

	/**
	 *  单个缓存订单收货地址key前缀
	 */
	
	public static final String ORDER_ADDRESS_UUID = "order_address_uuid";	
	/**
	 * 变更计数为1，表示没有变更过
	 * 即普通订单收货地址（会员下单后，将会员自定义收货地址转为普通订单收货地址，普通订单收货地址只有一个）
	 */
	
	public static final int CHANGE_COUNT_NORMAL = 1;	
	
	
	private String provinceName;
	
	private String cityName;
	
	private String regionName;
	
	private String streetName;
	

	/**
	 * 会员的名称
	 */
	
	private String name;

	public String getCustomerAddressUuid() {
		return customerAddressUuid;
	}

	public void setCustomerAddressUuid(String customerAddressUuid) {
		this.customerAddressUuid = customerAddressUuid;
	}

	public String getOrderMainUuid() {
		return orderMainUuid;
	}

	public void setOrderMainUuid(String orderMainUuid) {
		this.orderMainUuid = orderMainUuid;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getChangeRemark() {
		return changeRemark;
	}

	public void setChangeRemark(String changeRemark) {
		this.changeRemark = changeRemark;
	}

	public double getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(double changeMoney) {
		this.changeMoney = changeMoney;
	}

	public int getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(int changeCount) {
		this.changeCount = changeCount;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public static String getOrderAddressUuid() {
		return ORDER_ADDRESS_UUID;
	}

	public static int getChangeCountNormal() {
		return CHANGE_COUNT_NORMAL;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

