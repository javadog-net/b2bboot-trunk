/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;

/**
 * 发票信息MAPPER接口
 * @author tity
 * @version 2018-08-14
 */
@MyBatisMapper
public interface PurchaserInvoiceMapper extends BaseMapper<PurchaserInvoice> {
    public void clearDefault(String purchaserId);

    public void setDefault(String id);
}