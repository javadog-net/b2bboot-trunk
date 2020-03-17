package com.jhmis.view.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.view.entity
 * @Author: hdx
 * @CreateTime: 2019-10-28 16:49
 * @Description: 工程版报表
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class BrownDataForm {
    private String projectCode; //项目编码
    private String projectName; //项目编码
    private String brownId; //工程版Id
    private String brownCode; //工程版单号
    private String projectFirstParty; //工程单位-R1甲方名称
    private String totalAmount; //工程版下单总金额
    private String productGroupCode; //产品组编码
    private String productGroupName; //产品组名称
    private String dealerCode; //经销商编码
    private String dealerName; //经销商名称
    private String centerName; //中心名称
    private String type; //类型
    private String domainCode; //产业编码
    private String domainName; //产业名称
    private String openSystem; //传送开货系统类型
    private String brownCount; //工程板对应的产品组下单的总数
    private Date createdDate; //创建时间
    private Date createdDate2; //创建时间
    private Date expireTime; //到期日期
    private String caddress; //验收地址
    private String productCode; //产品编码
    private String productName; //产品名称
    private String orderQuantity; //下单数量
    private String price; //报价
    private String supplyPrice; //供价
    private String approvePrice; //审批价
    private String billPrice; //开票价
    private String izhikou; //直扣
    private String ihoufan; //后返
    private String itaifan; //台返
    private String reason; //延期作废原因
    private String status; //审核状态(审核中,审核结束)
    private String gpmsSuccess; //传送GPMS标识
    private String rrsBeSuccess; //传送RRS标识
    private Date rrsSuccessTime; //传送RRS时间
    private String gvsBeSuccess; //传送GVS标识
    private Date gvsTime; //传送GVS时间
    private String cproContact; //R2联系人
    private String cproTel; //R2联系方式
    private int pageNo; //页数
    private int pageSize; //每页显示数量
    private String createdDateStart; //创建时间Start
    private String createdDateEnd; //创建时间End
    private String expireTimeStart; //到期时间Start
    private String expireTimeEnd; //到期时间End
    //hps后补充字段
    private Date yanshoujiezhiDate;//验收截止时间
    private String yanshoudaojishi;//验收倒计时
    private String isFanlixieyi;//返利协议是否生效
    private String verify_status;//验收状态
    private String LFIMG2;//已提货数量
}
