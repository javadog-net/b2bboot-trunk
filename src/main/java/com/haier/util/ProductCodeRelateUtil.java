package com.haier.util;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author：hdx
 * @Description: 产品组对应关系表
 * @Date: Created in 11:26 2019/1/23
 * @Modified By
 */
public class ProductCodeRelateUtil{
    public static Map<String,String> getProductMap(){
        Map<String,String> map = new HashMap<>();
        map.put("中央空调","BA");
        map.put("商用空调","BB");
        map.put("家用空调","CA");
        map.put("新风系统","BA");
        map.put("电/燃气热水器","FA,FB"); 
        map.put("太阳能","FC");
        map.put("商用电脑","JB,JB,JC,JD,JE,JF,JG,JH");
        map.put("冰箱","AA");
        map.put("洗衣机","DA,DB");  
        map.put("厨房电器","GA,GB,GC,GD,GE,GF");
        map.put("医疗设备","NA");
        map.put("商用冷柜","AB");
        map.put("彩电","EA,EB");
        map.put("楼宇对讲;社区安防","JJ");
        map.put("军品定制","RA");
        map.put("小家电","LE");
        map.put("商用净水","LD,LF,LH,LS,LZ");
        return map;
    }
}
