package com.jhmis.modules.tools.utils;

import com.jhmis.modules.tools.entity.BatchsaleTemplate;
import com.jhmis.modules.tools.utils.ws.External;
import com.jhmis.modules.tools.utils.ws.client.StrXmlToPojoService;
import com.jhmis.modules.tools.utils.ws.impl.BCTStrXmlToPojoImpl;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 16:23 2018/7/22
 * @Modified By
 */
public class Main {
    public static void main(String[] args) {
        String s = External.getBatchSaleTemplate("B70U3307U");
        StrXmlToPojoService strXml = new BCTStrXmlToPojoImpl();
        BatchsaleTemplate b = (BatchsaleTemplate)strXml.returnWsPojo(s);
        System.out.print(b.getBatchsaleTemplateList().get(0).toString());
        System.out.print("********************************");



       /* String s2 = External.getMDMCustomersList("8700000235","青岛日日顺物流有限公司");
        System.out.print(s2);*/
    }
}
