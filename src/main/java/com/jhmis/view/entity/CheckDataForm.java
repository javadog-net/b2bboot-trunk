package com.jhmis.view.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.view.entity
 * @Author: hdx
 * @CreateTime: 2019-10-28 20:30
 * @Description: 验收单报表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class CheckDataForm {
    private String id;//验收单id
    private String cbill_id;//工程单ID
    private String cbill_code;//工程单号
    private String project_id;//项目ID
    private String project_code;//项目单号
    private String project_name;//项目名称
    private String project_linkman;//项目联系人
    private String project_address;//项目地址
    private String project_phone;//项目联系方式
    private String cust_code;//经销商编码
    private String cust_name;//经销商名称
    private String cust_linkman;//经销商联系人
    private String cust_linkman_phone;//经销商联系人电话
    private String product_group_code;//产品组
    private String product_group_name;//产品组描述
    private String center;//中心
    private String center_name;//中心名称
    private String project_date;//项目审批通过时间
    private String domain;//产业(商空的判断)
    private String installer;//安装商名称
    private String installer_linkman;//安装商联系人
    private String installer_linkman_phone;//安装商电话
    private String invoice_code;//发票代码
    private String invoice_number;//发票号码
    private String sale_money;//销售额(元)
    private String tax_id;//税务登记号
    private String tax_money;//税额(元)
    private String invoice_date;//开票日期
    private String invoice_file;//发票扫描件
    private String market_grade;//市场级别
    private String bill_quantity;//工程版审批数量
    private String gvs_quantity;//实际提货数量
    private String verify_quantity;//申请验收数量
    private String quantity;//实际验收数量
    private String verify_rate;//验收数量比例
    private String quality;//安装质量
    private String apply_id;//申请人id
    private String applyer;//申请人名称
    private String apply_date;//申请时间
    private String address_province;//省
    private String address_city;//市
    private String address_county;//区
    private String address_detail;//项目验收地址
    private String verify_status;//状态
    private String verify_num;//是否初审，次数小于3
    private String begin_date;//GVS编号确认时间就是计时开始时间
    private String end_date;//最后时间180天
    private String first_fail_reason;//初审不合格原因
    private String first_fail_file;//初审见证性资料
    private String appeal_start_date;//申诉开始时间
    private String appeal_over_date;//申诉结束时间
    private String again_apply_reason;//复检申请原因
    private String again_fail_reason;//复检不合格原因
    private String again_fail_file;//复审见证性资料
    private String is_freeze;//是否封号
    private String freeze_date;//封号时间
    private String freeze_reault;//封号结果
    private String is_send;//是否传输GPMS
    private String send_date;//传输时间
    private String send_reault;//传输结果
    private String is_send1;//是否传输(小微erp)
    private String send_date1;//传输时间(小微erp)
    private String send_reault1;//传输结果(小微erp)
    private String memo;//备注
    private String first_gvs_quantity;//初审时收货数量
    private String first_verify_quantity;//申请验收数量
    private String first_quantity;//初审数量
    private String first_rate;//初审验收比例
    private String temp_text1;//默认为空
    private String temp_text2;//默认为空
    private String created_by_id;//创建人ID经销商维护人ID
    private String last_modified_by_id;//最后修改人ID
    private String deleted;//是否删除
    private String created_by;//创建人经销商维护人
    private String created_date;//创建时间
    private String last_modified_by;//最后修改人名称
    private String last_modified_date;//最后修改时间
    private String cre_pro_id;//创建来源信息
    private String mod_pro_id;//修改来源信息
    private String batch_date;//批处理时间
    private String provinceCityCountyDetail;//省市区县地址
    private String invoiceFileName;//文件名
    private String firstFailFileName;//文件名
    private String againFailFileName;//文件名
    private int pageNo; //页数
    private int pageSize; //每页显示数量
    //hps后补充字段
    private Date yanshoujiezhiDate;//验收截止时间
    private String yanshoudaojishi;//验收倒计时
    private String isFanlixieyi;//返利协议是否生效

}
