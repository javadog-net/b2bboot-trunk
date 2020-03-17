package com.haier.webservices.client.mdm.createcust2mdm;

/**
 * 创建采购商model
 * @author wangbn
 *
 */
public class CustomerConstantModel {
	
	public String IN_CUSTOMERNUMBER="";
	//是否有售达方功能
	public String IN_PARTNERFLAGSP="1";
	//是否有送达方功能
	private String IN_PARTNERFLAGSH="1";
	//是否有开票方功能
	private String IN_PARTNERFLAGBP="1";
	//是否有付款方功能
	private String IN_PARTNERFLAGPY="1";
	//客户标题
	private String IN_CUSTOMERTITLE="公司";
	//客户账户组
	private String IN_ACCOUNTGROUP="0280";
	//客户名称
	private String IN_CUSTOMERNAME="";
	//搜索关键字
	private String IN_SEARCHTERMS="";
	//国家
	private String IN_CUSTOMERCOUNTRY="CN";
	//语言
	private String IN_LANGUAGE="ZH";
	//地区
	private String IN_REGION="";
	//城市
	private String IN_CITY="";
	//街道
	private String IN_STREET="";
	//联系人
	private String IN_CONTACTPERSON="";
	//联系电话
	private String IN_PHONENUMBER1="";
	//增值税登记号
	private String IN_VATREGNO="";
	//客户性质小类
	private String IN_INDUSTRYCLASS="HY007";
	//开票类型
	private String IN_TAXDOCTYPE="";
	//是否金融户
	private String IN_CUSTFLAGBANKING="0";
	//法人代表
	private String IN_LEGAL_PERSON="";
	//工贸代码
	private String IN_TRAINSTATION="";
	//电话
	private String IN_TELEPHONE="";
	//法人属性
	private String IN_LEGAL_TYPE="";
	
	
	
	//银行信息
	
	//银行国家
	private String IN_BANKCOUNTRY="";
	//银行代码
	private String IN_BANKKEY="";
	//银行账户
	private String IN_ACCOUNTHOLDER="";
	//银行名称
	private String IN_BANKNAME="";
	
	
	//公司信息
	
	//公司代码
	private String IN_COMPANYCODE="";
	//统驭科目
	private String IN_RECONACCOUNT="1122002000";
	//排序码
	private String IN_SORTKEY="001";
	//付款条件
	private String IN_TERMSOFPAYMENT="0000";
	
	
	
	//销售信息
	
	//销售组织
	private String IN_SALES_GROUP="3200";
	//分销渠道
	private String IN_DISTRIBUTION_CHANNEL="00";
	//产品组
	private String IN_DIVISION="00";
	//客户统计组
	private String IN_CUSTOMER_SUMMARY_GROUP="1";
	//货币代码
	private String IN_CURRENCY="CNY";
	//客户定价过程
	private String IN_CUSTOMER_PRICE_PROCEDURE="1";
	//客户组的帐户分配
	private String IN_ACCT_ASSGMT_GROUP="20";
	//客户的税分类
	private String IN_TAX_CLASSIFICATION="1";
	//装运条件
	private String IN_SHIP_CONDITION="01";
	//与 POD 处理相关
	private String IN_POD_RELATION="";
	
	//扩展信息
	
	//管理客户
	private String IN_MANAGECUSTOMER="B9000025";
	//行政区域(销售)
	private String IN_SALES_REGION="";
	
	//快捷通信息
	
	//户主名称
	private String IN_FINANACOUNTNAME="";
	//开户机构
	private String IN_FINANORGN="";
	//开户机构简称
	private String IN_FINANORGNSHORT="KJT";
	//户头编码
	private String IN_FINANACOUNTCODE="";
	//币种
	private String IN_FINANCURRENCY="";
	//类型
	private String IN_FINANTYPE="";
	//状态
	private String IN_FINANSTATE="0";	
	
	
	//详细地址
	
	//省/直辖市/自治区
	private String in_ADDRESS_PROVINCE="";
	//市/自治州
	private String in_ADDRESS_CITY="";
	//区/县/旗
	private String in_ADDRESS_AREA="";
	//街道/乡/镇
	private String in_ADDRESS_TOWN="";
	//社区/行政村/屯
	private String in_ADDRESS_VILLAGE="";
	//路/街道/巷
	private String in_ADDRESS_ROAD="";
	//门牌号
	private String in_ADDRESS_HOUSE_NUMBER="";
	//经度
	private String in_ADDRESS_LONGITUDE="";
	//纬度
	private String in_ADDRESS_LATITUDE="";
	//具体地址
	private String in_SPECIFIC_ADDRESS="";
	
	
	//公用字段
	
	//操作状态
	private String OPERATETYPE="";
	//是否更新基本信息
	private String UPDATE_BASE="";
	//是否更新银行信息
	private String UPDATE_BANK="";
	//是否更新扩展信息
	private String UPDATE_EXTEND="";
	//是否更新销售信息
	private String UPDATE_SALE="";
	//是否更新公司信息
	private String UPDATE_COMP="";
	//是否更新快捷通信息
	private String UPDATE_FINANCE="";
	//是否有附件
	private String IF_AFFIX="";
	//附件FTP服务器IP地址
	private String IN_AFFIX_IP="";
	//附件FTP服务器用户名
	private String IN_AFFIX_USERNAME="";
	//附件FTP服务器密码
	private String IN_AFFIX_PASSWORD="";
	//附件所在的文件夹的URL
	private String IN_AFFIX_URL="";
	//附件名称
	private String IN_AFFIX_FILENAME="";
	//创建系统的ALM号
	private String IN_SYSNAME="";
	//创建人
	private String IN_CREATED_BY="";
	public String getIN_PARTNERFLAGSP() {
		return IN_PARTNERFLAGSP;
	}
	public void setIN_PARTNERFLAGSP(String iN_PARTNERFLAGSP) {
		IN_PARTNERFLAGSP = iN_PARTNERFLAGSP;
	}
	public String getIN_PARTNERFLAGSH() {
		return IN_PARTNERFLAGSH;
	}
	public void setIN_PARTNERFLAGSH(String iN_PARTNERFLAGSH) {
		IN_PARTNERFLAGSH = iN_PARTNERFLAGSH;
	}
	public String getIN_PARTNERFLAGBP() {
		return IN_PARTNERFLAGBP;
	}
	public void setIN_PARTNERFLAGBP(String iN_PARTNERFLAGBP) {
		IN_PARTNERFLAGBP = iN_PARTNERFLAGBP;
	}
	public String getIN_PARTNERFLAGPY() {
		return IN_PARTNERFLAGPY;
	}
	public void setIN_PARTNERFLAGPY(String iN_PARTNERFLAGPY) {
		IN_PARTNERFLAGPY = iN_PARTNERFLAGPY;
	}
	public String getIN_CUSTOMERTITLE() {
		return IN_CUSTOMERTITLE;
	}
	public void setIN_CUSTOMERTITLE(String iN_CUSTOMERTITLE) {
		IN_CUSTOMERTITLE = iN_CUSTOMERTITLE;
	}
	public String getIN_ACCOUNTGROUP() {
		return IN_ACCOUNTGROUP;
	}
	public void setIN_ACCOUNTGROUP(String iN_ACCOUNTGROUP) {
		IN_ACCOUNTGROUP = iN_ACCOUNTGROUP;
	}
	public String getIN_CUSTOMERNAME() {
		return IN_CUSTOMERNAME;
	}
	public void setIN_CUSTOMERNAME(String iN_CUSTOMERNAME) {
		IN_CUSTOMERNAME = iN_CUSTOMERNAME;
	}
	public String getIN_SEARCHTERMS() {
		return IN_SEARCHTERMS;
	}
	public void setIN_SEARCHTERMS(String iN_SEARCHTERMS) {
		IN_SEARCHTERMS = iN_SEARCHTERMS;
	}
	public String getIN_CUSTOMERCOUNTRY() {
		return IN_CUSTOMERCOUNTRY;
	}
	public void setIN_CUSTOMERCOUNTRY(String iN_CUSTOMERCOUNTRY) {
		IN_CUSTOMERCOUNTRY = iN_CUSTOMERCOUNTRY;
	}
	public String getIN_LANGUAGE() {
		return IN_LANGUAGE;
	}
	public void setIN_LANGUAGE(String iN_LANGUAGE) {
		IN_LANGUAGE = iN_LANGUAGE;
	}
	public String getIN_REGION() {
		return IN_REGION;
	}
	public void setIN_REGION(String iN_REGION) {
		IN_REGION = iN_REGION;
	}
	public String getIN_CITY() {
		return IN_CITY;
	}
	public void setIN_CITY(String iN_CITY) {
		IN_CITY = iN_CITY;
	}
	public String getIN_STREET() {
		return IN_STREET;
	}
	public void setIN_STREET(String iN_STREET) {
		IN_STREET = iN_STREET;
	}
	public String getIN_CONTACTPERSON() {
		return IN_CONTACTPERSON;
	}
	public void setIN_CONTACTPERSON(String iN_CONTACTPERSON) {
		IN_CONTACTPERSON = iN_CONTACTPERSON;
	}
	public String getIN_PHONENUMBER1() {
		return IN_PHONENUMBER1;
	}
	public void setIN_PHONENUMBER1(String iN_PHONENUMBER1) {
		IN_PHONENUMBER1 = iN_PHONENUMBER1;
	}
	public String getIN_VATREGNO() {
		return IN_VATREGNO;
	}
	public void setIN_VATREGNO(String iN_VATREGNO) {
		IN_VATREGNO = iN_VATREGNO;
	}
	public String getIN_INDUSTRYCLASS() {
		return IN_INDUSTRYCLASS;
	}
	public void setIN_INDUSTRYCLASS(String iN_INDUSTRYCLASS) {
		IN_INDUSTRYCLASS = iN_INDUSTRYCLASS;
	}
	public String getIN_TAXDOCTYPE() {
		return IN_TAXDOCTYPE;
	}
	public void setIN_TAXDOCTYPE(String iN_TAXDOCTYPE) {
		IN_TAXDOCTYPE = iN_TAXDOCTYPE;
	}
	public String getIN_CUSTFLAGBANKING() {
		return IN_CUSTFLAGBANKING;
	}
	public void setIN_CUSTFLAGBANKING(String iN_CUSTFLAGBANKING) {
		IN_CUSTFLAGBANKING = iN_CUSTFLAGBANKING;
	}
	public String getIN_LEGAL_PERSON() {
		return IN_LEGAL_PERSON;
	}
	public void setIN_LEGAL_PERSON(String iN_LEGAL_PERSON) {
		IN_LEGAL_PERSON = iN_LEGAL_PERSON;
	}
	public String getIN_TRAINSTATION() {
		return IN_TRAINSTATION;
	}
	public void setIN_TRAINSTATION(String iN_TRAINSTATION) {
		IN_TRAINSTATION = iN_TRAINSTATION;
	}
	public String getIN_TELEPHONE() {
		return IN_TELEPHONE;
	}
	public void setIN_TELEPHONE(String iN_TELEPHONE) {
		IN_TELEPHONE = iN_TELEPHONE;
	}
	public String getIN_LEGAL_TYPE() {
		return IN_LEGAL_TYPE;
	}
	public void setIN_LEGAL_TYPE(String iN_LEGAL_TYPE) {
		IN_LEGAL_TYPE = iN_LEGAL_TYPE;
	}
	public String getIN_BANKCOUNTRY() {
		return IN_BANKCOUNTRY;
	}
	public void setIN_BANKCOUNTRY(String iN_BANKCOUNTRY) {
		IN_BANKCOUNTRY = iN_BANKCOUNTRY;
	}
	public String getIN_BANKKEY() {
		return IN_BANKKEY;
	}
	public void setIN_BANKKEY(String iN_BANKKEY) {
		IN_BANKKEY = iN_BANKKEY;
	}
	public String getIN_ACCOUNTHOLDER() {
		return IN_ACCOUNTHOLDER;
	}
	public void setIN_ACCOUNTHOLDER(String iN_ACCOUNTHOLDER) {
		IN_ACCOUNTHOLDER = iN_ACCOUNTHOLDER;
	}
	public String getIN_BANKNAME() {
		return IN_BANKNAME;
	}
	public void setIN_BANKNAME(String iN_BANKNAME) {
		IN_BANKNAME = iN_BANKNAME;
	}
	public String getIN_COMPANYCODE() {
		return IN_COMPANYCODE;
	}
	public void setIN_COMPANYCODE(String iN_COMPANYCODE) {
		IN_COMPANYCODE = iN_COMPANYCODE;
	}
	public String getIN_RECONACCOUNT() {
		return IN_RECONACCOUNT;
	}
	public void setIN_RECONACCOUNT(String iN_RECONACCOUNT) {
		IN_RECONACCOUNT = iN_RECONACCOUNT;
	}
	public String getIN_SORTKEY() {
		return IN_SORTKEY;
	}
	public void setIN_SORTKEY(String iN_SORTKEY) {
		IN_SORTKEY = iN_SORTKEY;
	}
	public String getIN_TERMSOFPAYMENT() {
		return IN_TERMSOFPAYMENT;
	}
	public void setIN_TERMSOFPAYMENT(String iN_TERMSOFPAYMENT) {
		IN_TERMSOFPAYMENT = iN_TERMSOFPAYMENT;
	}
	public String getIN_SALES_GROUP() {
		return IN_SALES_GROUP;
	}
	public void setIN_SALES_GROUP(String iN_SALES_GROUP) {
		IN_SALES_GROUP = iN_SALES_GROUP;
	}
	public String getIN_DISTRIBUTION_CHANNEL() {
		return IN_DISTRIBUTION_CHANNEL;
	}
	public void setIN_DISTRIBUTION_CHANNEL(String iN_DISTRIBUTION_CHANNEL) {
		IN_DISTRIBUTION_CHANNEL = iN_DISTRIBUTION_CHANNEL;
	}
	public String getIN_DIVISION() {
		return IN_DIVISION;
	}
	public void setIN_DIVISION(String iN_DIVISION) {
		IN_DIVISION = iN_DIVISION;
	}
	public String getIN_CUSTOMER_SUMMARY_GROUP() {
		return IN_CUSTOMER_SUMMARY_GROUP;
	}
	public void setIN_CUSTOMER_SUMMARY_GROUP(String iN_CUSTOMER_SUMMARY_GROUP) {
		IN_CUSTOMER_SUMMARY_GROUP = iN_CUSTOMER_SUMMARY_GROUP;
	}
	public String getIN_CURRENCY() {
		return IN_CURRENCY;
	}
	public void setIN_CURRENCY(String iN_CURRENCY) {
		IN_CURRENCY = iN_CURRENCY;
	}
	public String getIN_CUSTOMER_PRICE_PROCEDURE() {
		return IN_CUSTOMER_PRICE_PROCEDURE;
	}
	public void setIN_CUSTOMER_PRICE_PROCEDURE(String iN_CUSTOMER_PRICE_PROCEDURE) {
		IN_CUSTOMER_PRICE_PROCEDURE = iN_CUSTOMER_PRICE_PROCEDURE;
	}
	public String getIN_ACCT_ASSGMT_GROUP() {
		return IN_ACCT_ASSGMT_GROUP;
	}
	public void setIN_ACCT_ASSGMT_GROUP(String iN_ACCT_ASSGMT_GROUP) {
		IN_ACCT_ASSGMT_GROUP = iN_ACCT_ASSGMT_GROUP;
	}
	public String getIN_TAX_CLASSIFICATION() {
		return IN_TAX_CLASSIFICATION;
	}
	public void setIN_TAX_CLASSIFICATION(String iN_TAX_CLASSIFICATION) {
		IN_TAX_CLASSIFICATION = iN_TAX_CLASSIFICATION;
	}
	public String getIN_SHIP_CONDITION() {
		return IN_SHIP_CONDITION;
	}
	public void setIN_SHIP_CONDITION(String iN_SHIP_CONDITION) {
		IN_SHIP_CONDITION = iN_SHIP_CONDITION;
	}
	public String getIN_POD_RELATION() {
		return IN_POD_RELATION;
	}
	public void setIN_POD_RELATION(String iN_POD_RELATION) {
		IN_POD_RELATION = iN_POD_RELATION;
	}
	public String getIN_MANAGECUSTOMER() {
		return IN_MANAGECUSTOMER;
	}
	public void setIN_MANAGECUSTOMER(String iN_MANAGECUSTOMER) {
		IN_MANAGECUSTOMER = iN_MANAGECUSTOMER;
	}
	public String getIN_SALES_REGION() {
		return IN_SALES_REGION;
	}
	public void setIN_SALES_REGION(String iN_SALES_REGION) {
		IN_SALES_REGION = iN_SALES_REGION;
	}
	public String getIN_FINANACOUNTNAME() {
		return IN_FINANACOUNTNAME;
	}
	public void setIN_FINANACOUNTNAME(String iN_FINANACOUNTNAME) {
		IN_FINANACOUNTNAME = iN_FINANACOUNTNAME;
	}
	public String getIN_FINANORGN() {
		return IN_FINANORGN;
	}
	public void setIN_FINANORGN(String iN_FINANORGN) {
		IN_FINANORGN = iN_FINANORGN;
	}
	public String getIN_FINANORGNSHORT() {
		return IN_FINANORGNSHORT;
	}
	public void setIN_FINANORGNSHORT(String iN_FINANORGNSHORT) {
		IN_FINANORGNSHORT = iN_FINANORGNSHORT;
	}
	public String getIN_FINANACOUNTCODE() {
		return IN_FINANACOUNTCODE;
	}
	public void setIN_FINANACOUNTCODE(String iN_FINANACOUNTCODE) {
		IN_FINANACOUNTCODE = iN_FINANACOUNTCODE;
	}
	public String getIN_FINANCURRENCY() {
		return IN_FINANCURRENCY;
	}
	public void setIN_FINANCURRENCY(String iN_FINANCURRENCY) {
		IN_FINANCURRENCY = iN_FINANCURRENCY;
	}
	public String getIN_FINANTYPE() {
		return IN_FINANTYPE;
	}
	public void setIN_FINANTYPE(String iN_FINANTYPE) {
		IN_FINANTYPE = iN_FINANTYPE;
	}
	public String getIN_FINANSTATE() {
		return IN_FINANSTATE;
	}
	public void setIN_FINANSTATE(String iN_FINANSTATE) {
		IN_FINANSTATE = iN_FINANSTATE;
	}
	public String getIn_ADDRESS_PROVINCE() {
		return in_ADDRESS_PROVINCE;
	}
	public void setIn_ADDRESS_PROVINCE(String in_ADDRESS_PROVINCE) {
		this.in_ADDRESS_PROVINCE = in_ADDRESS_PROVINCE;
	}
	public String getIn_ADDRESS_CITY() {
		return in_ADDRESS_CITY;
	}
	public void setIn_ADDRESS_CITY(String in_ADDRESS_CITY) {
		this.in_ADDRESS_CITY = in_ADDRESS_CITY;
	}
	public String getIn_ADDRESS_AREA() {
		return in_ADDRESS_AREA;
	}
	public void setIn_ADDRESS_AREA(String in_ADDRESS_AREA) {
		this.in_ADDRESS_AREA = in_ADDRESS_AREA;
	}
	public String getIn_ADDRESS_TOWN() {
		return in_ADDRESS_TOWN;
	}
	public void setIn_ADDRESS_TOWN(String in_ADDRESS_TOWN) {
		this.in_ADDRESS_TOWN = in_ADDRESS_TOWN;
	}
	public String getIn_ADDRESS_VILLAGE() {
		return in_ADDRESS_VILLAGE;
	}
	public void setIn_ADDRESS_VILLAGE(String in_ADDRESS_VILLAGE) {
		this.in_ADDRESS_VILLAGE = in_ADDRESS_VILLAGE;
	}
	public String getIn_ADDRESS_ROAD() {
		return in_ADDRESS_ROAD;
	}
	public void setIn_ADDRESS_ROAD(String in_ADDRESS_ROAD) {
		this.in_ADDRESS_ROAD = in_ADDRESS_ROAD;
	}
	public String getIn_ADDRESS_HOUSE_NUMBER() {
		return in_ADDRESS_HOUSE_NUMBER;
	}
	public void setIn_ADDRESS_HOUSE_NUMBER(String in_ADDRESS_HOUSE_NUMBER) {
		this.in_ADDRESS_HOUSE_NUMBER = in_ADDRESS_HOUSE_NUMBER;
	}
	public String getIn_ADDRESS_LONGITUDE() {
		return in_ADDRESS_LONGITUDE;
	}
	public void setIn_ADDRESS_LONGITUDE(String in_ADDRESS_LONGITUDE) {
		this.in_ADDRESS_LONGITUDE = in_ADDRESS_LONGITUDE;
	}
	public String getIn_ADDRESS_LATITUDE() {
		return in_ADDRESS_LATITUDE;
	}
	public void setIn_ADDRESS_LATITUDE(String in_ADDRESS_LATITUDE) {
		this.in_ADDRESS_LATITUDE = in_ADDRESS_LATITUDE;
	}
	public String getIn_SPECIFIC_ADDRESS() {
		return in_SPECIFIC_ADDRESS;
	}
	public void setIn_SPECIFIC_ADDRESS(String in_SPECIFIC_ADDRESS) {
		this.in_SPECIFIC_ADDRESS = in_SPECIFIC_ADDRESS;
	}
	public String getOPERATETYPE() {
		return OPERATETYPE;
	}
	public void setOPERATETYPE(String oPERATETYPE) {
		OPERATETYPE = oPERATETYPE;
	}
	public String getUPDATE_BASE() {
		return UPDATE_BASE;
	}
	public void setUPDATE_BASE(String uPDATE_BASE) {
		UPDATE_BASE = uPDATE_BASE;
	}
	public String getUPDATE_BANK() {
		return UPDATE_BANK;
	}
	public void setUPDATE_BANK(String uPDATE_BANK) {
		UPDATE_BANK = uPDATE_BANK;
	}
	public String getUPDATE_EXTEND() {
		return UPDATE_EXTEND;
	}
	public void setUPDATE_EXTEND(String uPDATE_EXTEND) {
		UPDATE_EXTEND = uPDATE_EXTEND;
	}
	public String getUPDATE_SALE() {
		return UPDATE_SALE;
	}
	public void setUPDATE_SALE(String uPDATE_SALE) {
		UPDATE_SALE = uPDATE_SALE;
	}
	public String getUPDATE_COMP() {
		return UPDATE_COMP;
	}
	public void setUPDATE_COMP(String uPDATE_COMP) {
		UPDATE_COMP = uPDATE_COMP;
	}
	public String getUPDATE_FINANCE() {
		return UPDATE_FINANCE;
	}
	public void setUPDATE_FINANCE(String uPDATE_FINANCE) {
		UPDATE_FINANCE = uPDATE_FINANCE;
	}
	public String getIF_AFFIX() {
		return IF_AFFIX;
	}
	public void setIF_AFFIX(String iF_AFFIX) {
		IF_AFFIX = iF_AFFIX;
	}
	public String getIN_AFFIX_IP() {
		return IN_AFFIX_IP;
	}
	public void setIN_AFFIX_IP(String iN_AFFIX_IP) {
		IN_AFFIX_IP = iN_AFFIX_IP;
	}
	public String getIN_AFFIX_USERNAME() {
		return IN_AFFIX_USERNAME;
	}
	public void setIN_AFFIX_USERNAME(String iN_AFFIX_USERNAME) {
		IN_AFFIX_USERNAME = iN_AFFIX_USERNAME;
	}
	public String getIN_AFFIX_PASSWORD() {
		return IN_AFFIX_PASSWORD;
	}
	public void setIN_AFFIX_PASSWORD(String iN_AFFIX_PASSWORD) {
		IN_AFFIX_PASSWORD = iN_AFFIX_PASSWORD;
	}
	public String getIN_AFFIX_URL() {
		return IN_AFFIX_URL;
	}
	public void setIN_AFFIX_URL(String iN_AFFIX_URL) {
		IN_AFFIX_URL = iN_AFFIX_URL;
	}
	public String getIN_AFFIX_FILENAME() {
		return IN_AFFIX_FILENAME;
	}
	public void setIN_AFFIX_FILENAME(String iN_AFFIX_FILENAME) {
		IN_AFFIX_FILENAME = iN_AFFIX_FILENAME;
	}
	public String getIN_SYSNAME() {
		return IN_SYSNAME;
	}
	public void setIN_SYSNAME(String iN_SYSNAME) {
		IN_SYSNAME = iN_SYSNAME;
	}
	public String getIN_CREATED_BY() {
		return IN_CREATED_BY;
	}
	public void setIN_CREATED_BY(String iN_CREATED_BY) {
		IN_CREATED_BY = iN_CREATED_BY;
	}
	public String getIN_CUSTOMERNUMBER() {
		return IN_CUSTOMERNUMBER;
	}
	public void setIN_CUSTOMERNUMBER(String iN_CUSTOMERNUMBER) {
		IN_CUSTOMERNUMBER = iN_CUSTOMERNUMBER;
	}
	

}
