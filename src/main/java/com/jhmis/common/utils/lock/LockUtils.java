package com.jhmis.common.utils.lock;

import net.oschina.j2cache.J2Cache;

/**
 * 锁工具单元
 */
public class LockUtils {
    public static boolean isLocal = true;
    static {
        String cache_broadcast = J2Cache.getConfig().getProperty("cache.broadcast");
        if ("redis".equalsIgnoreCase(cache_broadcast)){
            isLocal = false;
        }
        //是否本地锁，还是redis锁
    }

    /**
     * 锁定
     * @param key
     * @return
     */
    public static boolean lock(String key){
        if(isLocal){
            return LocalLock.lock(key);
        } else {
            return RedisLock.lock(key);
        }
    }

    /**
     * 锁定带超时时间
     * @param key
     * @param timeoutMsecs 超时毫秒数
     * @return
     */
    public static boolean lock(String key, long timeoutMsecs){
        if(isLocal){
            return LocalLock.lock(key,timeoutMsecs);
        } else {
            return RedisLock.lock(key,timeoutMsecs);
        }
    }

    /**
     * 解锁
     * @param key
     */
    public static void unLock(String key){
        if(isLocal){
            LocalLock.unLock(key);
        } else {
            RedisLock.unLock(key);
        }
    }

    /**
     * 是否有锁
     * @param key
     * @return
     */
    public static boolean isLock(String key){
        if(isLocal){
            return LocalLock.isLock(key);
        } else {
            return RedisLock.isLock(key);
        }
    }
}
