package com.jhmis.modules.tools.utils.ws.impl;

import com.jhmis.modules.tools.entity.BatchsaleTemplate;
import com.jhmis.modules.tools.entity.BatchsaleTemplateItem;
import com.jhmis.modules.tools.utils.ws.client.StrXmlToPojoService;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 16:34 2018/7/22
 * @Modified By
 */
public class BCTStrXmlToPojoImpl implements StrXmlToPojoService {

    @Override
    public Object returnWsPojo(String xml) {
        Document doc = null;
        BatchsaleTemplate batchsaleTemplate = new BatchsaleTemplate();
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点smsReport
            Element Body_element = rootElt.element("Body");
            Element processResponse_element = Body_element.element("processResponse");
            Element result_element = processResponse_element.element("result");
            Element retCode_element = processResponse_element.element("retCode");
            Element retMsg_element = processResponse_element.element("retMsg");
            batchsaleTemplate.setRetCode(retCode_element.getText());
            batchsaleTemplate.setRetMsg(retMsg_element.getText());
            List<Element> result_list = result_element.elements();//所有子元素
            System.out.println("**************");
            List<BatchsaleTemplateItem> list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            for(int i=0; i<result_list.size(); i++){
                BatchsaleTemplateItem item = new BatchsaleTemplateItem();
                item.setRow_id(result_list.get(i).element("ROW_ID").getText());
                item.setMaterial_code(result_list.get(i).element("MATERIAL_CODE").getText());
                item.setSales_organization(result_list.get(i).element("SALES_ORGANIZATION").getText());
                item.setSales_channel(result_list.get(i).element("SALES_CHANNEL").getText());
                item.setSales_channel2(result_list.get(i).element("SALES_CHANNEL2").getText());
                item.setCustomer_code(result_list.get(i).element("CUSTOMER_CODE").getText());
                item.setActive_date_begin(sdf1.parse(result_list.get(i).element("ACTIVE_DATE_BEGIN").getText()));
                item.setActive_date_end(sdf1.parse(result_list.get(i).element("ACTIVE_DATE_END").getText()));
                item.setLast_upd(sdf.parse(result_list.get(i).element("LAST_UPD").getText()));
                item.setDelete_flag(result_list.get(i).element("DELETE_FLAG").getText());
                item.setDepartment(result_list.get(i).element("DEPARTMENT").getText());
                item.setCombine_type(result_list.get(i).element("COMBINE_TYPE").getText());
                item.setPut_methods(result_list.get(i).element("PUT_METHODS").getText());
                list.add(item);
            }
            batchsaleTemplate.setBatchsaleTemplateList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batchsaleTemplate;
    }
}
