package net.oschina.j2cache.redis;

/**
 * 
 *  队列枚举
 * @author TC
 * 
 */
public enum QueueEnum {

    /**
     * 
     */
    UNPAID_GETUIINFO_QUEUE("unpaid_getuiinfo_queue"),

    /**
     * accessToken获取失败队列
     */
    ACCESS_GETUIINFO_QUEUE("access_getuiinfo_queue");


    private String name;

    public String type(){
        return this.name;
    }

    QueueEnum(String name) {
        this.name = name;
    }
}
