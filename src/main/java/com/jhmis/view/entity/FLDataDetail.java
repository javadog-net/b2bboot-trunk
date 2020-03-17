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
 * @CreateTime: 2019-10-28 10:55
 * @Description: hps返利明细报表实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class FLDataDetail {
    private String syscode;  //序号
    private String fk_syscode; //汇总关联号
    private String systype;  //汇总关联号
    private String order_no;  //销售订单编号
    private String order_type;  //销售订单类型
    private String invoice_no; //发票号
    private String invoice_type; //发票类型
    private String invoice_row; //发票行项目
    private String contract_no; //合同编号
    private String prize_no; //奖励政策编码
    private String sales_group; //销售组织编码
    private String customer_code_sp; //售达方编码
    private String customer_code_py; //付款方编码
    private String customer_code_sh; //送达方编码
    private String sales_group_parent; //主户销售组织编码
    private String customer_code_parent; //主户售达方编码
    private String sales_group_pr; //总公司销售组织
    private String tally_no; //理货商编码
    private String is_parent; //是否总公司
    private String chain_no; //连锁编号
    private String brand_code; //品牌编码
    private String product_code; //产品组编码
    private String basic_mtl; //产品系列编码
    private String material_code;//物料编码
    private String material_descrition;  //物料名称
    private String amount; //数量
    private String exe_price; //执行价格
    private String netpr; //返利台阶单价
    private String money; //含税总价
    private String project_no; //工程单号
    private Date order_date; //订单日期
    private Date invoice_date; //发票日期
    private String is_after_calc; //是否补算
    private String is_write_off; //补算冲红标记
    private String flow_state; //流程状态
    private String prize_year; //政策年度
    private String out_factory; //发货工厂
    private String direct_out; //直发货
    private String restore_prize; //还原直扣返利
    private String customer_property; //客户属性
    private String total_money; //总金额
    private String base_money; //计算基数
    private String prize_money; //奖励金额
    private String prize_rate; //奖励率
    private String calc_comment; //计算备注
    private String prize_type; //奖励类型编码
    private Date date_modified; //更新时间
    private String last_modified_by; //最后修改人
    private String clearing_sheet_no; //结算单编号
    private String bill_state; //结算单状态
    private String memotype; //备注类型
    private String apply_no; //补算申请单号
    private Date prize_begin;//政策开始
    private Date prize_end; //政策结束
    private String taskid; //任务id
    private String rel_syscode; //补算关联id
    private String prize_comment; //返利备注
    private String zf_flag; //整车直发标记
    private Date created_date;
    private Date last_modified_date;
    private String load_batch;
    private String prize_name; //政策名称
    private String contract_name; //合同名称
    private String senddate;
    private String project_name; //项目名称
    private String project_code; //项目编码
    private int pageNo;
    private int pageSize;
}
