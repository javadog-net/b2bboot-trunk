package com.jhmis.modules.euc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: b2bboot
 * @description: Euc 实体类的小类
 * @author: T.c
 * @create: 2020-03-06 16:45
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WinBidCompany {
    String companyCode ;//经销商编码
    String companyName ;// 经销商名称

}