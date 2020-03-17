package com.jhmis.common.wsClient.utils;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jhmis.common.wsClient.entity.BatchsaleTemplate;
import com.jhmis.common.wsClient.entity.BatchsaleTemplateItem;
import com.jhmis.common.wsClient.entity.MDMCustomer;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WsUtils {
    public static List<MDMCustomer> returnMDMCustomer(Document doc) {
        Element rootElt = doc.getRootElement(); // 获取根节点smsReport
        Element getMDM_CustomersListResult_element = rootElt.element("getMDM_CustomersListResult");
        String a = getMDM_CustomersListResult_element.getText();
        //json字符串组数组直接转化对象list
        ArrayList<MDMCustomer> MDMCustomer = JSON.parseObject(a, new TypeReference<ArrayList<MDMCustomer>>() {
        });
        return MDMCustomer;
    }

    public static BatchsaleTemplate returnBatchsaleTemplate(Document doc) {
        BatchsaleTemplate batchsaleTemplate = new BatchsaleTemplate();
        try {
            Element rootElt = doc.getRootElement(); // 获取根节点smsReport
            Element result_element = rootElt.element("result");
            Element retCode_element = rootElt.element("retCode");
            Element retMsg_element = rootElt.element("retMsg");
            batchsaleTemplate.setRetCode(retCode_element.getText());
            batchsaleTemplate.setRetMsg(retMsg_element.getText());
            List<Element> result_list = result_element.elements();//所有子元素
            System.out.println("**************");
            List<BatchsaleTemplateItem> list = new ArrayList<>();
            for (int i = 0; i < result_list.size(); i++) {
                Date begin =  WsUtils.returnDate("yyyy-MM-dd",result_list.get(i).element("ACTIVE_DATE_BEGIN").getText());
                Date end =  WsUtils.returnDate("yyyy-MM-dd",result_list.get(i).element("ACTIVE_DATE_END").getText());
                Date upd =  WsUtils.returnDate("yyyy-MM-dd HH:mm:ss",result_list.get(i).element("LAST_UPD").getText());
                BatchsaleTemplateItem item = new BatchsaleTemplateItem();
                item.setRow_id(result_list.get(i).element("ROW_ID").getText());
                item.setMaterial_code(result_list.get(i).element("MATERIAL_CODE").getText());
                item.setSales_organization(result_list.get(i).element("SALES_ORGANIZATION").getText());
                item.setSales_channel(result_list.get(i).element("SALES_CHANNEL").getText());
                item.setSales_channel2(result_list.get(i).element("SALES_CHANNEL2").getText());
                item.setCustomer_code(result_list.get(i).element("CUSTOMER_CODE").getText());
                item.setActive_date_begin(begin);
                item.setActive_date_end(end);
                item.setLast_upd(upd);
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

    public static Date returnDate(String SimpleDateFormatStr,String DateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(SimpleDateFormatStr);
        Date d = new Date();
        try {
            d = sdf.parse(DateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
