package com.haier.order.dto;

public enum InoiceStatusEnum {

    ST_OPENER("开具中","1"),
    END_OPENER("已开具","2"),
    ST_CH("冲红中","3"),
    END_CH("已冲红","4");

    private String name;
    private String index;

    private InoiceStatusEnum(String name , String index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(String index) {
        for (InoiceStatusEnum c : InoiceStatusEnum.values()) {
            if (index.equals(c.getIndex())) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
