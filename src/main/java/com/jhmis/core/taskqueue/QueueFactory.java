package com.jhmis.core.taskqueue;

import net.oschina.j2cache.J2Cache;

/**
 * 队列工厂
 */
public class QueueFactory {
    public static boolean isLocal = true;
    static {
        String cache_broadcast = J2Cache.getConfig().getProperty("cache.broadcast");
        if ("redis".equalsIgnoreCase(cache_broadcast)){
            isLocal = false;
        }
    }
    public static <E> IQueue<E> newQueue(){
        if(isLocal){
            return new LocalQueue<E>();
        } else {
            return new RedisQueue<E>();
        }
    }
}
