package com.jhmis.core.taskqueue;

/**
 * 任务类型枚举
 */
public enum TaskType {
    //供应商消息
    DEALER_MESSAGE(0),
    //采购商消息
    PURCHASER_MESSAGE(1),
    //栏目页静态化
    CATEGORY_HTML(2),
    //信息页静态化
    INFO_HTML(3);
    //更多任务类型，待扩展
    private int value;
    public int getValue(){
        return value;
    }

    TaskType(int value){
        this.value = value;
    }
}
