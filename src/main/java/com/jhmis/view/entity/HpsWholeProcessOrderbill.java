package com.jhmis.view.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class HpsWholeProcessOrderbill {
	private String id;  //ID 
	private String project_id;//项目ID（漏斗R1阶段表）  
	private String dept_code;//项目所属产业(项目负责人的产业)  
	private String domain_model;//产业模式编码（客单和直单）  
	private String domain_model_name;//产业模式名称  
	private String project_code;//项目编码  
	private String project_name;//项目名称  
	private Date created_date;//创建时间  
	private String project_manager_code;//项目负责人编码  
	private String project_manager_name;//项目负责人名称  
	private String wbs_code;//WBS号  
	private String data_source;//数据来源编码（工程平台自创建和企业购传入）  
	private String data_source_name;//数据来源名称  
	private String first_company_id;//甲方公司ID  
	private String first_company_name;//甲方公司名称  
	private String first_company_org_code;//统一社会信用代码  
	private String contractor_code;//承接商编码  
	private String contractor_name;//承接商名称  
	private String address_province;//项目地址（省）  
	private String address_city;//项目地址（市）  
	private String address_county;//项目地址（区/县）  
	private String address_detail;//详细地址  
	private String center;//项目所属中心编码  
	private String center_name;//项目所属中心名称  
	private String grid_code;//网格编码  
	private String grid_name;//网格名称  
	private String grid_user_code;//网格经营体长工号  
	private String grid_user_name;//网格经营体长姓名  
	private String funnel_stage;//漏斗阶段（R1~R6）  
	private String project_status;//项目状态  
	private String project_status_name;//项目状态描述  
	private String industry_home_category;//项目所属行业门类  
	private String industry_home_category_name;//项目所属行业门类描述  
	private String industry_category;//行业大类  
	private String industry_category_name;//行业大类描述  
	private String chance_point;//机会点  
	private String chance_point_name;//机会点描述  
	private String user_group;//用户群  
	private String user_group_name;//用户群描述  
	private String lock_user;//锁定用户编码  
	private String lock_user_name;//锁定用户名称  
	private Date estimate_proposal_time;//预计做方案时间  
	private Date actual_proposal_time;//实际做方案时间  
	private Date estimate_tender_time;//预计投标时间  
	private Date actual_tender_time;//实际投标时间  
	private Date estimate_bid_time;//预计中标时间  
	private Date actual_bid_time;//实际中标时间  
	private String be_win_bid;//中标结果  
	private Date estimated_signing_time;//预计签约日期  
	private Date actual_signing_time;//实际签约日期  
	private Date estimated_delivery_time;//预计首单日期  
	private Integer project_all_amount;//项目总数量  
	private Double project_all_money;//项目总金额（万）  
	private Double tax_equipment_money;//含税设备金额(万)  
	private Double tax_installation_money;//含税安装金额(万)  
	private Boolean deleted;//是否删除:1删除;0未删除  
	private Date last_updte_date;//记录最后修改时间  
	private int pageNo; //页数
    private int pageSize; //每页显示数量
    private String create_date_start; //项目开始时间
    private String create_date_end; //项目结束
}
