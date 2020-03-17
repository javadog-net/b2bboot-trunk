package com.jhmis.common.Enum;

import java.util.*;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.common.Enum
 * @Author: hdx
 * @CreateTime: 2019-09-23 11:32
 * @Description: 产品组对应匹配枚举类
 */
public enum ProductCode {

    CENTRAL_AIR_CONDITIONING("0","中央空调","BA"),
    COMMERCIAL_AIR_CONDITIONING("1","商用空调","BB"),
    HOUSEHOLD_AIR_CONDITIONER("2","家用空调","CA"),
    ELECTRIC_GAS_WATER_HEATER("3","电/燃气热水器","FA,FB"),
    SOLAR_ENERGY("4","太阳能","FC"),
    COMMERCIAL_COMPUTER("5","商用电脑","BB"),
    REFRIGERATOR("6","冰箱","AA"),
    WASHING_MACHINE("7","洗衣机","DA,DB"),
    KITCHEN_APPLIANCES("8","厨房电器","GA,GB,GC,GD,GE,GF"),
    MEDICAL_EQUIPMENT("9","医疗设备","NA"),
    COMMERCIAL_REFRIGERATOR("10","商用冷柜","AB"),
    COLOR_TV("11","彩电","EA,EB"),
    SMART_HOME_SECURITY("12","智能家居/安防","JJ"),
    COMMUNITY_SECURITY("13","社区安防","JJ"),
    CUSTOMIZATION_MILITARY_PRODUCTS("14","军品定制","RA"),
    SMALL_HOME_APPLIANCES("15","小家电","LE"),
    COMMERCIAL_WATER_PURIFICATION("16","商用净水","LD,LF,LH,LS,LZ")
    ;
    private String  index;
    private  String label;
    private  String value;


    public static List<ProductCodeEntity> getProduct() {
        List<ProductCodeEntity> listProductCodeEntity = Collections.synchronizedList(new ArrayList<>());
        for (ProductCode p : ProductCode.values()) {
            ProductCodeEntity productCodeEntity = new ProductCode.ProductCodeEntity(p.getIndex(),p.getLabel(),p.getValue());
            listProductCodeEntity.add(productCodeEntity);
        }
        return listProductCodeEntity;
    }


    // 获取label普通方法
    public static String getLabel(String index) {
        for (ProductCode productCode : ProductCode.values()) {
            if (productCode.getIndex().equals(index)) {
                return productCode.label;
            }
        }
        return null;
    }
    // 获取value普通方法
    public static String getValue(String index) {
        for (ProductCode productCode : ProductCode.values()) {
            if (productCode.getIndex().equals(index)) {
                return productCode.value;
            }
        }
        return null;
    }
    // 获取index普通方法
    public static String getIndex(String label) {
        for (ProductCode productCode : ProductCode.values()) {
            if (productCode.getLabel().equals(label)) {
                return productCode.index;
            }
        }
        return null;
    }



    ProductCode(String index, String label, String value) {
        this.index = index;
        this.label = label;
        this.value = value;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public static class ProductCodeEntity{
        //产品编号
        private String code;
        //产品名称
        private String name;
        //对应转换过后的编码
        private String transCode;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTransCode() {
            return transCode;
        }

        public void setTransCode(String transCode) {
            this.transCode = transCode;
        }

        public ProductCodeEntity(String code, String name, String transCode) {
            this.code = code;
            this.name = name;
            this.transCode = transCode;
        }
    }


}

