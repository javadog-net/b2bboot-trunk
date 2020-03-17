package com.jhmis.api.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.service.MdmCustomersPartnerService;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;
import com.jhmis.modules.shop.entity.Mdm88;
import com.jhmis.modules.shop.service.Mdm88Service;

@RestController
@RequestMapping("/api/wechat/mdm88")
public class Mdm88Controller {
	
	@Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    MdmCustomersSourceService mdmCustomersSourceService;

    @Autowired
    MdmCustomersPartnerService mdmCustomersPartnerService;
    
    @Autowired
    Mdm88Service mdm88Service;
    
    
    
    @RequestMapping("/get88FromMdm")
    public void get88FromMdm() throws Exception{
  
    	List<Mdm88> mdm88s = mdm88Service.findList(new Mdm88());
    	
    	if(mdm88s != null && mdm88s.size()>0){
    		
    		for(Mdm88 mdm88 : mdm88s){
    			
    			MdmCustomersPartner mdmCustomersPartner = new MdmCustomersPartner();
    			mdmCustomersPartner.setCustomerNumber(mdm88.getCode88());
    			List<MdmCustomersPartner> partners = mdmCustomersPartnerService.findList(mdmCustomersPartner);
    			if(partners == null || partners.size()<1 ){
    				getMdmCustomersPartner(mdm88.getCode88());
    			}
    			
    			MdmCustomersSource mdmCustomersSource = new MdmCustomersSource();
    			mdmCustomersSource.setCusCode(mdm88.getCode88());
    			List<MdmCustomersSource> sources = mdmCustomersSourceService.findList(mdmCustomersSource);
    			if(sources == null || sources.size()<1){
    				getMdmCustomersSource(mdm88.getCode88());
    			}
    				
    		}
    	}
    	   	    	
    }
	
    public List<Map<String, Object>> getMdmCustomersSource(String mdm88)  throws Exception {
        String sql = "select b.SALES_GROUP as ComCode,  a.CUSTOMER_NUMBER as CusCode, a.ACCOUNT_GROUP as ACCOUNT_GROUP, A.market_area, e.Value_Meaning AS COMNAME,  CASE WHEN a.CUSTOMER_NAME IS NULL THEN a.CUSTOMER_NAME1 ELSE a.CUSTOMER_NAME END AS CusName, a.CUSTOMER_NAME1 as  CusAbbName, NULL as  OldCustCode, a.INDUSTRY_CLASS as  Kind, F.VALUE_MEANING as KindName, a.CITY_STREET_ROOM as  Address , a.STREET_ROOM as  Street , a.POSTAL_CODE  as  Post, a.NAME1 as  Linkman, a.PHONE_NUMBER as  Tel, a.FAX_NUMBER as  Fax, NULL as  E_Mail, NULL as  BankL,  NULL as  BankN, NULL as  BankA , NULL as  OrgID, a.TAX_CODE as  Tax, NULL as  EnrBankroll, NULL as  CreditGrade, NULL as  Channel, NULL as  AreaName, NULL as  SysTrade, a.CREATED_BY  as  Create_By, a.CREATED as  ImportDate, sysdate as SysDates, a.LAST_UPD_BY as  Alter_By, a.LAST_UPD as  Alter_Date, a.ACTIVE_FLAG as  ACTIVE_FLAG, a.LOCK_FLAG as  LOCK_FLAG, a.DELETE_FLAG as  DELETE_FLAG, a.OPERATE_STATUS as  OPERATE_STATUS, a.REMARK  as  MEMO from haiermdm.hrgc_hm_customers a  INNER join haiermdm.hrgc_hm_customer_sales_data b on b.CUSTOMER_ID =a.ROW_ID   INNER join haiermdm.hm_value_set e on e.value=b.SALES_GROUP and e.value_set_id='CompanyCode'   left join  haiermdm.hrgc_hm_cust_additional_data d on d.CUSTOMER_ID =a.ROW_ID left join  haiermdm.hm_value_set f on f.value=A.INDUSTRY_CLASS and f.value_set_id='IndustryClass'  where 1=1 and a.FOR_GVS=1  AND A.DELETE_FLAG=0   and a.PARTNER_FLAG_SP=1 and a.CUSTOMER_NUMBER = '" + mdm88 + "'";
        List<Map<String, Object>> resObj = (List<Map<String, Object>>) jdbcTemplate1.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(sql);
            }
        }, new PreparedStatementCallback<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.execute();
                ResultSet rs = null;
                try {
                    rs = ps.getResultSet();

                    while(rs.next()){
                        MdmCustomersSource m = new MdmCustomersSource();
                        m.setComCode(rs.getString(1));
                        m.setCusCode(rs.getString(2));
                        m.setAccountGroup(rs.getString(3));
                        m.setMarketArea(rs.getString(4));
                        m.setComName(rs.getString(5));
                        m.setCusName(rs.getString(6));
                        m.setCusAbbName(rs.getString(7));
                        m.setOldCustCode(rs.getString(8));
                        m.setKind(rs.getString(9));
                        m.setKindName(rs.getString(10));
                        m.setAddress(rs.getString(11));
                        m.setStreet(rs.getString(12));
                        m.setPost(rs.getString(13));
                        m.setLinkman(rs.getString(14));
                        m.setTel(rs.getString(15));
                        m.setFax(rs.getString(16));
                        m.setEMail(rs.getString(17));
                        m.setBankL(rs.getString(18));
                        m.setBankN(rs.getString(19));
                        m.setBankA(rs.getString(20));
                        m.setOrgId(rs.getString(21));
                        m.setTax(rs.getString(22));
                        m.setEnrBankroll(rs.getString(23));
                        m.setCreditGrade(rs.getString(24));
                        m.setChannel(rs.getString(25));
                        m.setAreaName(rs.getString(26));
                        m.setSysTrade(rs.getString(27));
                        m.setImportDate(rs.getDate(29));
                        m.setSysDates(rs.getDate(30));
                        m.setAlterBy(rs.getString(31));
                        m.setAlterDate(rs.getDate(32));
                        m.setActiveFlag(rs.getString(33));
                        m.setLockFlag(rs.getString(34));
                        m.setDelFlag(rs.getString(35));
                        m.setOperateStatus(rs.getString(36));
                        m.setMemo(rs.getString(37));
                        mdmCustomersSourceService.save(m);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    rs.close();
                }
                return null;
            }
        } );
        return resObj;
    }


    public List<Map<String, Object>> getMdmCustomersPartner(String mdm88) throws Exception {
        String sql = "select pp.CUSTOMER_NUMBER  ,pp.CUSTOMER_NAME1   ,pp.CUST_PARTNER_TYPE   ,pp.CUST_PARTNER_SUBJECT_ID    ,pp.SUB_CUSTOMER_NAME1         ,pp.SALES_GROUP                ,pp.PARTNER_ROW_ID AS PARTNER_ROW_ID  ,PP.LAST_UPD AS LAST_UPD  ,pp.CUSTOMER_FLAG  ,pp.cust_delete_flag  ,pp.sales_delete_flag  ,pp.partner_delete_flag ,pp.sub_customer_delete_flag from haiermdm.view_cust_partner_all pp where 1=1 and ( exists(  select *  from haiermdm.view_cust_partner_all p where p.customer_number = p.cust_partner_subject_id  and p.cust_partner_type = 'SP'  and p.cust_delete_flag= '0'  and p.sales_delete_flag= '0' and p.partner_delete_flag = '0' and p.sub_customer_delete_flag='0' and p.customer_number =pp.customer_number  )  )  and pp.cust_delete_flag= '0' and pp.sales_delete_flag= '0'  and pp.partner_delete_flag = '0'  and pp.sub_customer_delete_flag='0' AND pp.CUSTOMER_NUMBER = '" + mdm88 + "'";

        List<Map<String, Object>> resObj = (List<Map<String, Object>>) jdbcTemplate1.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(sql);
            }
        }, new PreparedStatementCallback<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.execute();
                ResultSet rs = null;
                try {
                    rs = ps.getResultSet();

                    while(rs.next()){
                        MdmCustomersPartner mdmCustomersPartner = new MdmCustomersPartner();
                        mdmCustomersPartner.setCustomerNumber(rs.getString(1));
                        mdmCustomersPartner.setCustomerName1(rs.getString(2));
                        mdmCustomersPartner.setCustPartnerType(rs.getString(3));
                        mdmCustomersPartner.setCustPartnerSubjectId(rs.getString(4));
                        mdmCustomersPartner.setSubCustomerName1(rs.getString(5));
                        mdmCustomersPartner.setSalesGroup(rs.getString(6));
                        mdmCustomersPartner.setPartnerRowId(rs.getString(7));
                        mdmCustomersPartner.setLastUpd(rs.getDate(8));
                        mdmCustomersPartner.setCustomerFlag(rs.getString(9));
                        mdmCustomersPartner.setCustDeleteFlag(rs.getString(10));
                        mdmCustomersPartner.setSalesDeleteFlag(rs.getString(11));
                        mdmCustomersPartner.setPartnerDeleteFlag(rs.getString(12));
                        mdmCustomersPartner.setSubCustomerDeleteFlag(rs.getString(13));
                        mdmCustomersPartnerService.save(mdmCustomersPartner);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    rs.close();
                }
                return null;
            }
        } );
        return resObj;
    }
	
	
}
