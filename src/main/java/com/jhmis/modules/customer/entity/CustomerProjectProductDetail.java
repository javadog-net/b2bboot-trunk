/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 客单漏斗项目产品详情Entity
 * @author hdx
 * @version 2019-04-16
 */
public class CustomerProjectProductDetail extends DataEntity<CustomerProjectProductDetail> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String currstate;		// 当前节点
	private String amount;		// 数量
	private String cjprice;		// 供价
	private String cjpricesum;		// 供价合计（供价*数量=总价）
	private String price;		// 折扣后价格（单个产品实际价格）
	private String pricesum;		// 折扣后合计总价（实际价格*数量=总价）
	private String bargainprice;		// 合同价
	private String bargainpricesum;		// 合同价总计
	private String stepflrebat;		// 690客单使用的台阶型号返利点
	private String productid;		// 产品组编码
	private String productname;		// 产品组名称
	private String classcode;		// classcode
	private String classname;		// classname
	private String modelcode;		// 物料编码
	private String modelname;		// 物料名称
	private String sendbccstate;		// 传输到BCC的状态
	private String bccfxprice;		// BCC分享价
	private String bccblprice;		// BCC保利价
	private String bccbbprice;		// BCC保本价
	private String plprice;		// PL售价
	private String fxprice;		// 计算后分享价
	private String blprice;		// 计算后保利价
	private String bbprice;		// 计算后保本价
	private String httype;		// 合同类型
	private Date sysdatetime;		// 创建日期
	private String isvalid;		// 是有效
	private String isprochange;		// 是否是本次的新增产品（项目变更）
	private String modlebllv;		// 不良率
	private String xqqty;		// 需求数量
	private String companyname;		// 公司名称
	private String manager;		// 负责人
	private String mobilephone;		// 电话
	private String relation;		// 与用户关系
	private String tzspg;		// 投标商实力评估
	private String expr1;		// expr1
	private String expr2;		// expr2
	private String expr3;		// 电话
	private String expr4;		// expr4
	private String addressdetail;		// 安装地址
	private String modelblprice;		// 型号保利价
	
	public CustomerProjectProductDetail() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public CustomerProjectProductDetail(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=1)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="当前节点", align=2, sort=2)
	public String getCurrstate() {
		return currstate;
	}

	public void setCurrstate(String currstate) {
		this.currstate = currstate;
	}
	
	@ExcelField(title="数量", align=2, sort=3)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@ExcelField(title="供价", align=2, sort=4)
	public String getCjprice() {
		return cjprice;
	}

	public void setCjprice(String cjprice) {
		this.cjprice = cjprice;
	}
	
	@ExcelField(title="供价合计（供价*数量=总价）", align=2, sort=5)
	public String getCjpricesum() {
		return cjpricesum;
	}

	public void setCjpricesum(String cjpricesum) {
		this.cjpricesum = cjpricesum;
	}
	
	@ExcelField(title="折扣后价格（单个产品实际价格）", align=2, sort=6)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@ExcelField(title="折扣后合计总价（实际价格*数量=总价）", align=2, sort=7)
	public String getPricesum() {
		return pricesum;
	}

	public void setPricesum(String pricesum) {
		this.pricesum = pricesum;
	}
	
	@ExcelField(title="合同价", align=2, sort=8)
	public String getBargainprice() {
		return bargainprice;
	}

	public void setBargainprice(String bargainprice) {
		this.bargainprice = bargainprice;
	}
	
	@ExcelField(title="合同价总计", align=2, sort=9)
	public String getBargainpricesum() {
		return bargainpricesum;
	}

	public void setBargainpricesum(String bargainpricesum) {
		this.bargainpricesum = bargainpricesum;
	}
	
	@ExcelField(title="690客单使用的台阶型号返利点", align=2, sort=10)
	public String getStepflrebat() {
		return stepflrebat;
	}

	public void setStepflrebat(String stepflrebat) {
		this.stepflrebat = stepflrebat;
	}
	
	@ExcelField(title="产品组编码", align=2, sort=11)
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	@ExcelField(title="产品组名称", align=2, sort=12)
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	@ExcelField(title="classcode", align=2, sort=13)
	public String getClasscode() {
		return classcode;
	}

	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}
	
	@ExcelField(title="classname", align=2, sort=14)
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	@ExcelField(title="物料编码", align=2, sort=15)
	public String getModelcode() {
		return modelcode;
	}

	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	
	@ExcelField(title="物料名称", align=2, sort=16)
	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	
	@ExcelField(title="传输到BCC的状态", align=2, sort=17)
	public String getSendbccstate() {
		return sendbccstate;
	}

	public void setSendbccstate(String sendbccstate) {
		this.sendbccstate = sendbccstate;
	}
	
	@ExcelField(title="BCC分享价", align=2, sort=18)
	public String getBccfxprice() {
		return bccfxprice;
	}

	public void setBccfxprice(String bccfxprice) {
		this.bccfxprice = bccfxprice;
	}
	
	@ExcelField(title="BCC保利价", align=2, sort=19)
	public String getBccblprice() {
		return bccblprice;
	}

	public void setBccblprice(String bccblprice) {
		this.bccblprice = bccblprice;
	}
	
	@ExcelField(title="BCC保本价", align=2, sort=20)
	public String getBccbbprice() {
		return bccbbprice;
	}

	public void setBccbbprice(String bccbbprice) {
		this.bccbbprice = bccbbprice;
	}
	
	@ExcelField(title="PL售价", align=2, sort=21)
	public String getPlprice() {
		return plprice;
	}

	public void setPlprice(String plprice) {
		this.plprice = plprice;
	}
	
	@ExcelField(title="计算后分享价", align=2, sort=22)
	public String getFxprice() {
		return fxprice;
	}

	public void setFxprice(String fxprice) {
		this.fxprice = fxprice;
	}
	
	@ExcelField(title="计算后保利价", align=2, sort=23)
	public String getBlprice() {
		return blprice;
	}

	public void setBlprice(String blprice) {
		this.blprice = blprice;
	}
	
	@ExcelField(title="计算后保本价", align=2, sort=24)
	public String getBbprice() {
		return bbprice;
	}

	public void setBbprice(String bbprice) {
		this.bbprice = bbprice;
	}
	
	@ExcelField(title="合同类型", align=2, sort=25)
	public String getHttype() {
		return httype;
	}

	public void setHttype(String httype) {
		this.httype = httype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建日期", align=2, sort=26)
	public Date getSysdatetime() {
		return sysdatetime;
	}

	public void setSysdatetime(Date sysdatetime) {
		this.sysdatetime = sysdatetime;
	}
	
	@ExcelField(title="是有效", align=2, sort=27)
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	
	@ExcelField(title="是否是本次的新增产品（项目变更）", align=2, sort=28)
	public String getIsprochange() {
		return isprochange;
	}

	public void setIsprochange(String isprochange) {
		this.isprochange = isprochange;
	}
	
	@ExcelField(title="不良率", align=2, sort=29)
	public String getModlebllv() {
		return modlebllv;
	}

	public void setModlebllv(String modlebllv) {
		this.modlebllv = modlebllv;
	}
	
	@ExcelField(title="需求数量", align=2, sort=30)
	public String getXqqty() {
		return xqqty;
	}

	public void setXqqty(String xqqty) {
		this.xqqty = xqqty;
	}
	
	@ExcelField(title="公司名称", align=2, sort=31)
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	@ExcelField(title="负责人", align=2, sort=32)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	@ExcelField(title="电话", align=2, sort=33)
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	@ExcelField(title="与用户关系", align=2, sort=34)
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	@ExcelField(title="投标商实力评估", align=2, sort=35)
	public String getTzspg() {
		return tzspg;
	}

	public void setTzspg(String tzspg) {
		this.tzspg = tzspg;
	}
	
	@ExcelField(title="expr1", align=2, sort=36)
	public String getExpr1() {
		return expr1;
	}

	public void setExpr1(String expr1) {
		this.expr1 = expr1;
	}
	
	@ExcelField(title="expr2", align=2, sort=37)
	public String getExpr2() {
		return expr2;
	}

	public void setExpr2(String expr2) {
		this.expr2 = expr2;
	}
	
	@ExcelField(title="电话", align=2, sort=38)
	public String getExpr3() {
		return expr3;
	}

	public void setExpr3(String expr3) {
		this.expr3 = expr3;
	}
	
	@ExcelField(title="expr4", align=2, sort=39)
	public String getExpr4() {
		return expr4;
	}

	public void setExpr4(String expr4) {
		this.expr4 = expr4;
	}
	
	@ExcelField(title="安装地址", align=2, sort=40)
	public String getAddressdetail() {
		return addressdetail;
	}

	public void setAddressdetail(String addressdetail) {
		this.addressdetail = addressdetail;
	}
	
	@ExcelField(title="型号保利价", align=2, sort=41)
	public String getModelblprice() {
		return modelblprice;
	}

	public void setModelblprice(String modelblprice) {
		this.modelblprice = modelblprice;
	}
	
}