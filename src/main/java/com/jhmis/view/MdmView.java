package com.jhmis.view;

import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.modules.customer.entity.CustomerProjectProduct;
import com.jhmis.modules.customer.service.CustomerProjectInfoService;
import com.jhmis.modules.customer.service.CustomerProjectProductDetailService;
import com.jhmis.modules.customer.service.CustomerProjectProductService;
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.service.MdmCustomersPartnerService;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class MdmView {
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;

    @Autowired
    MdmCustomersSourceService mdmCustomersSourceService;

    @Autowired
    MdmCustomersPartnerService mdmCustomersPartnerService;

    /**
     * @Author：hdx
     * @param customer_Number
     * @Description: MDM 经销商source数据源
     * @Date: Created in 9:30 2019/3/26
     * @Modified By
     */
    public void getMdmCustomersSource(String customer_Number) {
        //查询经销商source数据源视图
        String sql = "select b.SALES_GROUP as ComCode,  a.CUSTOMER_NUMBER as CusCode, a.ACCOUNT_GROUP as ACCOUNT_GROUP, A.market_area, e.Value_Meaning AS COMNAME,  CASE WHEN a.CUSTOMER_NAME IS NULL THEN a.CUSTOMER_NAME1 ELSE a.CUSTOMER_NAME END AS CusName, a.CUSTOMER_NAME1 as  CusAbbName, NULL as  OldCustCode, a.INDUSTRY_CLASS as  Kind, F.VALUE_MEANING as KindName, a.CITY_STREET_ROOM as  Address , a.STREET_ROOM as  Street , a.POSTAL_CODE  as  Post, a.NAME1 as  Linkman, a.PHONE_NUMBER as  Tel, a.FAX_NUMBER as  Fax, NULL as  E_Mail, NULL as  BankL,  NULL as  BankN, NULL as  BankA , NULL as  OrgID, a.TAX_CODE as  Tax, NULL as  EnrBankroll, NULL as  CreditGrade, NULL as  Channel, NULL as  AreaName, NULL as  SysTrade, a.CREATED_BY  as  Create_By, a.CREATED as  ImportDate, sysdate as SysDates, a.LAST_UPD_BY as  Alter_By, a.LAST_UPD as  Alter_Date, a.ACTIVE_FLAG as  ACTIVE_FLAG, a.LOCK_FLAG as  LOCK_FLAG, a.DELETE_FLAG as  DELETE_FLAG, a.OPERATE_STATUS as  OPERATE_STATUS, a.REMARK  as  MEMO from haiermdm.hrgc_hm_customers a  INNER join haiermdm.hrgc_hm_customer_sales_data b on b.CUSTOMER_ID =a.ROW_ID   INNER join haiermdm.hm_value_set e on e.value=b.SALES_GROUP and e.value_set_id='CompanyCode'   left join  haiermdm.hrgc_hm_cust_additional_data d on d.CUSTOMER_ID =a.ROW_ID left join  haiermdm.hm_value_set f on f.value=A.INDUSTRY_CLASS and f.value_set_id='IndustryClass'  where 1=1 and a.FOR_GVS=1  AND A.DELETE_FLAG=0   and a.PARTNER_FLAG_SP=1 and a.CUSTOMER_NUMBER = '"+ customer_Number +"'";
        //根据实体反射进行转换
        List<MdmCustomersSource> listMdmCustomersSource = jdbcTemplate1.query(sql, new BeanPropertyRowMapper(MdmCustomersSource.class));
        if(listMdmCustomersSource!=null && listMdmCustomersSource.size()>0){
            //证明有效逐条插入
            for(MdmCustomersSource mdmCustomersSource:listMdmCustomersSource){
                mdmCustomersSourceService.save(mdmCustomersSource);
            }
        }
    }

    /**
     * @Author：hdx
     * @param customer_Number
     * @Description: MDM 根据经销商编号查询经销商四方关系数据源
     * @Date: Created in 9:30 2019/3/26
     * @Modified By
     */
    public void getMdmCustomersPartner(String customer_Number) {
        //查询四方关系视图
        String sql = "select pp.CUSTOMER_NUMBER  ,pp.CUSTOMER_NAME1   ,pp.CUST_PARTNER_TYPE   ,pp.CUST_PARTNER_SUBJECT_ID    ,pp.SUB_CUSTOMER_NAME1         ,pp.SALES_GROUP                ,pp.PARTNER_ROW_ID AS PARTNER_ROW_ID  ,PP.LAST_UPD AS LAST_UPD  ,pp.CUSTOMER_FLAG  ,pp.cust_delete_flag  ,pp.sales_delete_flag  ,pp.partner_delete_flag ,pp.sub_customer_delete_flag from haiermdm.view_cust_partner_all pp where 1=1 and ( exists(  select *  from haiermdm.view_cust_partner_all p where p.customer_number = p.cust_partner_subject_id  and p.cust_partner_type = 'SP'  and p.cust_delete_flag= '0'  and p.sales_delete_flag= '0' and p.partner_delete_flag = '0' and p.sub_customer_delete_flag='0' and p.customer_number =pp.customer_number  )  )  and pp.cust_delete_flag= '0' and pp.sales_delete_flag= '0'  and pp.partner_delete_flag = '0'  and pp.sub_customer_delete_flag='0' AND pp.CUSTOMER_NUMBER = '"+ customer_Number +"'";
        //根据实体反射进行转换
        List<MdmCustomersPartner> listMdmCustomersPartner = jdbcTemplate1.query(sql, new BeanPropertyRowMapper(MdmCustomersPartner.class));
        if(listMdmCustomersPartner!=null && listMdmCustomersPartner.size()>0){
            //证明有效逐条插入
            for(MdmCustomersPartner mdmCustomersPartner:listMdmCustomersPartner){
                mdmCustomersPartnerService.save(mdmCustomersPartner);
            }
        }
    }



}
