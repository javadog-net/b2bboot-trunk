package com.haier.mdm.khzj;

import java.io.Serializable;

public class ParamObject implements Serializable {
    private String inCUSTOMERNUMBER;    //客户编号
    private String IN_PARTNERFLAGSP;    //是否有售达方功能  默认1
    private String IN_PARTNERFLAGSH;    //是否有送达方功能  默认1
    private String IN_PARTNERFLAGBP;    //是否有开票方功能  默认1
    private String IN_PARTNERFLAGPY;    //是否有付款方功能  默认1

    private String IN_CUSTOMERTITLE;    //客户标题          公司、女士、先生、先生和夫人 默认公司
    private String IN_ACCOUNTGROUP;     //客户账户组         默认0280
    private String IN_CUSTOMERNAME;     //客户名称          售达方为客户证件上的公司名称，同科目组内不能与MDM已有客户重复
    private String IN_SEARCHTERMS;      //搜索关键字        最好限制在6个字符内，SAP有限制
    private String IN_CUSTOMERCOUNTRY;  //国家              默认CN
    private String IN_LANGUAGE;         //语言              默认ZH
    private String IN_REGION;           //地区
    private String IN_CITY;             //城市
    private String IN_STREET;           //街道
    private String IN_CONTACTPERSON;    //联系人
    private String IN_PHONENUMBER1;     //联系电话          长度为13位以内，SAP有长度限制
    private String IN_VATREGNO;         //增值税登记号
    private String IN_INDUSTRYCLASS;    //客户性质小类      默认HY007(小渠道)
    private String IN_TAXDOCTYPE;       //开票类型          01增值税发票，02普通发票
    private String inLEGALPERSON;       //法人代表
    private String IN_LEGAL_TYPE;       //法人属性
    private String IN_TRAINSTATION;     //工贸代码          默认空
    private String IN_TELEPHONE;        //电话号码          长度为13位以内，SAP有长度限制

    private String IN_BANKCOUNTRY;      //银行国家          默认CN
    private String IN_BANKKEY;          //银行代码
    private String IN_ACCOUNTHOLDER;    //银行账号
    private String IN_BANKNAME;         //银行名称
    private String IN_COMPANYCODE;      //公司代码
    private String IN_RECONACCOUNT;     //统驭科目
    private String IN_SORTKEY;          //排序码
    private String IN_CUSTOMER_SUMMARY_GROUP;   //客户统计组
    private String IN_CURRENCY;         //货币代码
    private String IN_CUSTOMER_PRICE_PROCEDURE;  //客户定价过程
    private String IN_ACCT_ASSGMT_GROUP;//客户组的账户分配
    private String IN_TAX_CLASSIFICATION;//客户的税分类
    private String IN_SHIP_CONDITION;   //装运条件
    private String IN_POD_RELATION;     //与POD处理相关
    private String IN_SALES_REGION;     //行政区域（销售）
    private String IN_FINANACOUNTNAME;  //户主名称
    private String IN_FINANORGN;        //开户机构
    private String IN_FINANORGNSHORT;   //开户机构简称
    private String IN_FINANACOUNTCODE;  //户头编码
    private String IN_FINANCURRENCY;    //币种
    private String IN_FINANTYPE;        //类型
    private String IN_FINANSTATE;       //状态
    private String IN_SYSNAME;          //创建系统的ALM号
    private String IN_CREATED_BY;       //创建人
    private String OPERATETYPE;         //操作状态                  CREATE新增，UPDATE修改，EXTEND扩展
    private String UPDATE_BASE;         //是否更新基本信息          修改时，如果修改基本信息，该字段值为X,否则为空
    private String UPDATE_BANK;         //是否更新银行信息          修改时，如果修改基本信息，该字段值为X,否则为空
    private String UPDATE_EXTEND;       //是否更新扩展信息          修改时，如果修改基本信息，该字段值为X,否则为空
    private String UPDATE_SALE;         //是否更新销售信息          修改时，如果修改基本信息，该字段值为X,否则为空
    private String UPDATE_COMP;         //是否更新公司信息          修改时，如果修改基本信息，该字段值为X,否则为空
    private String UPDATE_FINANCE;      //是否更新快捷通信息         修改时，如果修改基本信息，该字段值为X,否则为空
    private String IF_AFFIX;            //是否有附件
    private String IN_AFFIX_IP;         //附件FTP服务器IP地址
    private String IN_AFFIX_USERNAME;   //附件FTP服务器用户名
    private String IN_AFFIX_PASSWORD;   //附件FTP密码
    private String IN_AFFIX_URL;        //附件所在的文件夹的URL
    private String IN_AFFIX_FILENAME;   //附件名称
    private String in_ADDRESS_PROVINCE; //省/直辖市/自治区
    private String in_ADDRESS_CITY;     //市/自治州
    private String in_ADDRESS_AREA;     //区/县/旗
    private String in_ADDRESS_TOWN;     //街道/乡/镇
    private String in_ADDRESS_VILLAGE;  //社区/行政村/屯
    private String in_ADDRESS_ROAD;     //路/街道/巷
    private String in_ADDRESS_HOUSE_NUMBER;//门牌号
    private String in_ADDRESS_LONGITUDE;//经度
    private String in_ADDRESS_LATITUDE; //纬度
    private String in_SPECIFIC_ADDRESS; //具体地址
    private String IN_INNER_COMPANYCODE;//


    public String getInCUSTOMERNUMBER() {
        return inCUSTOMERNUMBER;
    }

    public void setInCUSTOMERNUMBER(String inCUSTOMERNUMBER) {
        this.inCUSTOMERNUMBER = inCUSTOMERNUMBER;
    }

    public String getIN_PARTNERFLAGSP() {
        return IN_PARTNERFLAGSP;
    }

    public void setIN_PARTNERFLAGSP(String IN_PARTNERFLAGSP) {
        this.IN_PARTNERFLAGSP = IN_PARTNERFLAGSP;
    }

    public String getIN_PARTNERFLAGSH() {
        return IN_PARTNERFLAGSH;
    }

    public void setIN_PARTNERFLAGSH(String IN_PARTNERFLAGSH) {
        this.IN_PARTNERFLAGSH = IN_PARTNERFLAGSH;
    }

    public String getIN_PARTNERFLAGBP() {
        return IN_PARTNERFLAGBP;
    }

    public void setIN_PARTNERFLAGBP(String IN_PARTNERFLAGBP) {
        this.IN_PARTNERFLAGBP = IN_PARTNERFLAGBP;
    }

    public String getIN_PARTNERFLAGPY() {
        return IN_PARTNERFLAGPY;
    }

    public void setIN_PARTNERFLAGPY(String IN_PARTNERFLAGPY) {
        this.IN_PARTNERFLAGPY = IN_PARTNERFLAGPY;
    }

    public String getIN_CUSTOMERTITLE() {
        return IN_CUSTOMERTITLE;
    }

    public void setIN_CUSTOMERTITLE(String IN_CUSTOMERTITLE) {
        this.IN_CUSTOMERTITLE = IN_CUSTOMERTITLE;
    }

    public String getIN_ACCOUNTGROUP() {
        return IN_ACCOUNTGROUP;
    }

    public void setIN_ACCOUNTGROUP(String IN_ACCOUNTGROUP) {
        this.IN_ACCOUNTGROUP = IN_ACCOUNTGROUP;
    }

    public String getIN_CUSTOMERNAME() {
        return IN_CUSTOMERNAME;
    }

    public void setIN_CUSTOMERNAME(String IN_CUSTOMERNAME) {
        this.IN_CUSTOMERNAME = IN_CUSTOMERNAME;
    }

    public String getIN_SEARCHTERMS() {
        return IN_SEARCHTERMS;
    }

    public void setIN_SEARCHTERMS(String IN_SEARCHTERMS) {
        this.IN_SEARCHTERMS = IN_SEARCHTERMS;
    }

    public String getIN_CUSTOMERCOUNTRY() {
        return IN_CUSTOMERCOUNTRY;
    }

    public void setIN_CUSTOMERCOUNTRY(String IN_CUSTOMERCOUNTRY) {
        this.IN_CUSTOMERCOUNTRY = IN_CUSTOMERCOUNTRY;
    }

    public String getIN_LANGUAGE() {
        return IN_LANGUAGE;
    }

    public void setIN_LANGUAGE(String IN_LANGUAGE) {
        this.IN_LANGUAGE = IN_LANGUAGE;
    }

    public String getIN_REGION() {
        return IN_REGION;
    }

    public void setIN_REGION(String IN_REGION) {
        this.IN_REGION = IN_REGION;
    }

    public String getIN_CITY() {
        return IN_CITY;
    }

    public void setIN_CITY(String IN_CITY) {
        this.IN_CITY = IN_CITY;
    }

    public String getIN_STREET() {
        return IN_STREET;
    }

    public void setIN_STREET(String IN_STREET) {
        this.IN_STREET = IN_STREET;
    }

    public String getIN_CONTACTPERSON() {
        return IN_CONTACTPERSON;
    }

    public void setIN_CONTACTPERSON(String IN_CONTACTPERSON) {
        this.IN_CONTACTPERSON = IN_CONTACTPERSON;
    }

    public String getIN_PHONENUMBER1() {
        return IN_PHONENUMBER1;
    }

    public void setIN_PHONENUMBER1(String IN_PHONENUMBER1) {
        this.IN_PHONENUMBER1 = IN_PHONENUMBER1;
    }

    public String getIN_VATREGNO() {
        return IN_VATREGNO;
    }

    public void setIN_VATREGNO(String IN_VATREGNO) {
        this.IN_VATREGNO = IN_VATREGNO;
    }

    public String getIN_INDUSTRYCLASS() {
        return IN_INDUSTRYCLASS;
    }

    public void setIN_INDUSTRYCLASS(String IN_INDUSTRYCLASS) {
        this.IN_INDUSTRYCLASS = IN_INDUSTRYCLASS;
    }

    public String getIN_TAXDOCTYPE() {
        return IN_TAXDOCTYPE;
    }

    public void setIN_TAXDOCTYPE(String IN_TAXDOCTYPE) {
        this.IN_TAXDOCTYPE = IN_TAXDOCTYPE;
    }

    public String getInLEGALPERSON() {
        return inLEGALPERSON;
    }

    public void setInLEGALPERSON(String inLEGALPERSON) {
        this.inLEGALPERSON = inLEGALPERSON;
    }

    public String getIN_LEGAL_TYPE() {
        return IN_LEGAL_TYPE;
    }

    public void setIN_LEGAL_TYPE(String IN_LEGAL_TYPE) {
        this.IN_LEGAL_TYPE = IN_LEGAL_TYPE;
    }

    public String getIN_TRAINSTATION() {
        return IN_TRAINSTATION;
    }

    public void setIN_TRAINSTATION(String IN_TRAINSTATION) {
        this.IN_TRAINSTATION = IN_TRAINSTATION;
    }

    public String getIN_TELEPHONE() {
        return IN_TELEPHONE;
    }

    public void setIN_TELEPHONE(String IN_TELEPHONE) {
        this.IN_TELEPHONE = IN_TELEPHONE;
    }

    public String getIN_BANKCOUNTRY() {
        return IN_BANKCOUNTRY;
    }

    public void setIN_BANKCOUNTRY(String IN_BANKCOUNTRY) {
        this.IN_BANKCOUNTRY = IN_BANKCOUNTRY;
    }

    public String getIN_BANKKEY() {
        return IN_BANKKEY;
    }

    public void setIN_BANKKEY(String IN_BANKKEY) {
        this.IN_BANKKEY = IN_BANKKEY;
    }

    public String getIN_ACCOUNTHOLDER() {
        return IN_ACCOUNTHOLDER;
    }

    public void setIN_ACCOUNTHOLDER(String IN_ACCOUNTHOLDER) {
        this.IN_ACCOUNTHOLDER = IN_ACCOUNTHOLDER;
    }

    public String getIN_COMPANYCODE() {
        return IN_COMPANYCODE;
    }

    public void setIN_COMPANYCODE(String IN_COMPANYCODE) {
        this.IN_COMPANYCODE = IN_COMPANYCODE;
    }

    public String getIN_RECONACCOUNT() {
        return IN_RECONACCOUNT;
    }

    public void setIN_RECONACCOUNT(String IN_RECONACCOUNT) {
        this.IN_RECONACCOUNT = IN_RECONACCOUNT;
    }

    public String getIN_SORTKEY() {
        return IN_SORTKEY;
    }

    public void setIN_SORTKEY(String IN_SORTKEY) {
        this.IN_SORTKEY = IN_SORTKEY;
    }

    public String getIN_CUSTOMER_SUMMARY_GROUP() {
        return IN_CUSTOMER_SUMMARY_GROUP;
    }

    public void setIN_CUSTOMER_SUMMARY_GROUP(String IN_CUSTOMER_SUMMARY_GROUP) {
        this.IN_CUSTOMER_SUMMARY_GROUP = IN_CUSTOMER_SUMMARY_GROUP;
    }

    public String getIN_CURRENCY() {
        return IN_CURRENCY;
    }

    public void setIN_CURRENCY(String IN_CURRENCY) {
        this.IN_CURRENCY = IN_CURRENCY;
    }

    public String getIN_CUSTOMER_PRICE_PROCEDURE() {
        return IN_CUSTOMER_PRICE_PROCEDURE;
    }

    public void setIN_CUSTOMER_PRICE_PROCEDURE(String IN_CUSTOMER_PRICE_PROCEDURE) {
        this.IN_CUSTOMER_PRICE_PROCEDURE = IN_CUSTOMER_PRICE_PROCEDURE;
    }

    public String getIN_ACCT_ASSGMT_GROUP() {
        return IN_ACCT_ASSGMT_GROUP;
    }

    public void setIN_ACCT_ASSGMT_GROUP(String IN_ACCT_ASSGMT_GROUP) {
        this.IN_ACCT_ASSGMT_GROUP = IN_ACCT_ASSGMT_GROUP;
    }

    public String getIN_TAX_CLASSIFICATION() {
        return IN_TAX_CLASSIFICATION;
    }

    public void setIN_TAX_CLASSIFICATION(String IN_TAX_CLASSIFICATION) {
        this.IN_TAX_CLASSIFICATION = IN_TAX_CLASSIFICATION;
    }

    public String getIN_SHIP_CONDITION() {
        return IN_SHIP_CONDITION;
    }

    public void setIN_SHIP_CONDITION(String IN_SHIP_CONDITION) {
        this.IN_SHIP_CONDITION = IN_SHIP_CONDITION;
    }

    public String getIN_POD_RELATION() {
        return IN_POD_RELATION;
    }

    public void setIN_POD_RELATION(String IN_POD_RELATION) {
        this.IN_POD_RELATION = IN_POD_RELATION;
    }

    public String getIN_SALES_REGION() {
        return IN_SALES_REGION;
    }

    public void setIN_SALES_REGION(String IN_SALES_REGION) {
        this.IN_SALES_REGION = IN_SALES_REGION;
    }

    public String getIN_FINANACOUNTNAME() {
        return IN_FINANACOUNTNAME;
    }

    public void setIN_FINANACOUNTNAME(String IN_FINANACOUNTNAME) {
        this.IN_FINANACOUNTNAME = IN_FINANACOUNTNAME;
    }

    public String getIN_FINANORGN() {
        return IN_FINANORGN;
    }

    public void setIN_FINANORGN(String IN_FINANORGN) {
        this.IN_FINANORGN = IN_FINANORGN;
    }

    public String getIN_FINANORGNSHORT() {
        return IN_FINANORGNSHORT;
    }

    public void setIN_FINANORGNSHORT(String IN_FINANORGNSHORT) {
        this.IN_FINANORGNSHORT = IN_FINANORGNSHORT;
    }

    public String getIN_FINANACOUNTCODE() {
        return IN_FINANACOUNTCODE;
    }

    public void setIN_FINANACOUNTCODE(String IN_FINANACOUNTCODE) {
        this.IN_FINANACOUNTCODE = IN_FINANACOUNTCODE;
    }

    public String getIN_FINANCURRENCY() {
        return IN_FINANCURRENCY;
    }

    public void setIN_FINANCURRENCY(String IN_FINANCURRENCY) {
        this.IN_FINANCURRENCY = IN_FINANCURRENCY;
    }

    public String getIN_FINANTYPE() {
        return IN_FINANTYPE;
    }

    public void setIN_FINANTYPE(String IN_FINANTYPE) {
        this.IN_FINANTYPE = IN_FINANTYPE;
    }

    public String getIN_FINANSTATE() {
        return IN_FINANSTATE;
    }

    public void setIN_FINANSTATE(String IN_FINANSTATE) {
        this.IN_FINANSTATE = IN_FINANSTATE;
    }

    public String getIN_SYSNAME() {
        return IN_SYSNAME;
    }

    public void setIN_SYSNAME(String IN_SYSNAME) {
        this.IN_SYSNAME = IN_SYSNAME;
    }

    public String getIN_CREATED_BY() {
        return IN_CREATED_BY;
    }

    public void setIN_CREATED_BY(String IN_CREATED_BY) {
        this.IN_CREATED_BY = IN_CREATED_BY;
    }

    public String getOPERATETYPE() {
        return OPERATETYPE;
    }

    public void setOPERATETYPE(String OPERATETYPE) {
        this.OPERATETYPE = OPERATETYPE;
    }

    public String getUPDATE_BASE() {
        return UPDATE_BASE;
    }

    public void setUPDATE_BASE(String UPDATE_BASE) {
        this.UPDATE_BASE = UPDATE_BASE;
    }

    public String getUPDATE_BANK() {
        return UPDATE_BANK;
    }

    public void setUPDATE_BANK(String UPDATE_BANK) {
        this.UPDATE_BANK = UPDATE_BANK;
    }

    public String getUPDATE_EXTEND() {
        return UPDATE_EXTEND;
    }

    public void setUPDATE_EXTEND(String UPDATE_EXTEND) {
        this.UPDATE_EXTEND = UPDATE_EXTEND;
    }

    public String getUPDATE_SALE() {
        return UPDATE_SALE;
    }

    public void setUPDATE_SALE(String UPDATE_SALE) {
        this.UPDATE_SALE = UPDATE_SALE;
    }

    public String getUPDATE_COMP() {
        return UPDATE_COMP;
    }

    public void setUPDATE_COMP(String UPDATE_COMP) {
        this.UPDATE_COMP = UPDATE_COMP;
    }

    public String getUPDATE_FINANCE() {
        return UPDATE_FINANCE;
    }

    public void setUPDATE_FINANCE(String UPDATE_FINANCE) {
        this.UPDATE_FINANCE = UPDATE_FINANCE;
    }

    public String getIF_AFFIX() {
        return IF_AFFIX;
    }

    public void setIF_AFFIX(String IF_AFFIX) {
        this.IF_AFFIX = IF_AFFIX;
    }

    public String getIN_AFFIX_IP() {
        return IN_AFFIX_IP;
    }

    public void setIN_AFFIX_IP(String IN_AFFIX_IP) {
        this.IN_AFFIX_IP = IN_AFFIX_IP;
    }

    public String getIN_AFFIX_USERNAME() {
        return IN_AFFIX_USERNAME;
    }

    public void setIN_AFFIX_USERNAME(String IN_AFFIX_USERNAME) {
        this.IN_AFFIX_USERNAME = IN_AFFIX_USERNAME;
    }

    public String getIN_AFFIX_PASSWORD() {
        return IN_AFFIX_PASSWORD;
    }

    public void setIN_AFFIX_PASSWORD(String IN_AFFIX_PASSWORD) {
        this.IN_AFFIX_PASSWORD = IN_AFFIX_PASSWORD;
    }

    public String getIN_AFFIX_URL() {
        return IN_AFFIX_URL;
    }

    public void setIN_AFFIX_URL(String IN_AFFIX_URL) {
        this.IN_AFFIX_URL = IN_AFFIX_URL;
    }

    public String getIN_AFFIX_FILENAME() {
        return IN_AFFIX_FILENAME;
    }

    public void setIN_AFFIX_FILENAME(String IN_AFFIX_FILENAME) {
        this.IN_AFFIX_FILENAME = IN_AFFIX_FILENAME;
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

    public String getIN_INNER_COMPANYCODE() {
        return IN_INNER_COMPANYCODE;
    }

    public void setIN_INNER_COMPANYCODE(String IN_INNER_COMPANYCODE) {
        this.IN_INNER_COMPANYCODE = IN_INNER_COMPANYCODE;
    }

	public String getIN_BANKNAME() {
		return IN_BANKNAME;
	}

	public void setIN_BANKNAME(String iN_BANKNAME) {
		IN_BANKNAME = iN_BANKNAME;
	}
    
    
}