package com.jhmis.modules.shop.entity;

public class MdmSourceReturn {

	MdmCustomersSource mdmCustomersSource;//mdm主数据	
	String outRetmsg; //返回描述
	String outRetcode; //返回状态
	
	
	
	public MdmSourceReturn() {
		super();
	}



	public MdmSourceReturn(MdmCustomersSource mdmCustomersSource,String outRetmsg,
			String outRetcode) {
		super();
		this.mdmCustomersSource = mdmCustomersSource;
		this.outRetmsg = outRetmsg;
		this.outRetcode = outRetcode;
	}



	public MdmCustomersSource getMdmCustomersSource() {
		return mdmCustomersSource;
	}



	public void setMdmCustomersSource(MdmCustomersSource mdmCustomersSource) {
		this.mdmCustomersSource = mdmCustomersSource;
	}



	public String getOutRetmsg() {
		return outRetmsg;
	}



	public void setOutRetmsg(String outRetmsg) {
		this.outRetmsg = outRetmsg;
	}



	public String getOutRetcode() {
		return outRetcode;
	}



	public void setOutRetcode(String outRetcode) {
		this.outRetcode = outRetcode;
	}
	
	
}
