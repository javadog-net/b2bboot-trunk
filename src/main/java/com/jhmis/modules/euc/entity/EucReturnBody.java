package com.jhmis.modules.euc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.modules.euc.entity
 * @Author: hdx
 * @CreateTime: 2020-03-06 10:15
 * @Description: 回传EUC状态实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EucReturnBody {
    private String businessCode;
    private String status;
    private String person;
    private String personCode;
    private String remark;
    private String projectCode;
    private String performanceWay;
    private WinBidCompany winBidCompany;

}
