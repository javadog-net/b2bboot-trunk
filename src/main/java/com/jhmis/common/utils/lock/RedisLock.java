package com.jhmis.common.utils.lock;

import net.oschina.j2cache.redis.RedisCacheProvider;
import net.oschina.j2cache.redis.RedisCacheProxy;

public class RedisLock {
    private static final RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
    /**
     * 默认过期时间
     */
    private static long expireMsecs = 60000;
    /**
     * 默认超时时间
     */
    private static long timeoutMsecs = 10000;

    /**
     * 锁定
     * @param key
     * @return
     */
    public static boolean lock(String key){
        return lock(key,timeoutMsecs);
    }

    /**
     * 锁定，带超时参数
     * @param key
     * @param timeoutMsecs
     * @return
     */
    public static boolean lock(String key, long timeoutMsecs){
        long timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs;
            String expiresStr = String.valueOf(expires);

            if (redisCacheProxy.setnx(key, expiresStr).longValue() == 1L) {
                return true;
            }

            String currentValueStr = redisCacheProxy.get(key);
            if ((currentValueStr != null) && (Long.parseLong(currentValueStr) < System.currentTimeMillis())) {
                String oldValueStr = redisCacheProxy.getSet(key, expiresStr);
                if ((oldValueStr != null) && (oldValueStr.equals(currentValueStr))) {
                    return true;
                }
            }

            timeout -= 100;
            try {
                Thread.sleep(100L);
                //TODO 是否抛出InterruptedException
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 是否锁定
     * @param key
     * @return
     */
    public static boolean isLock(String key){
        long expires = System.currentTimeMillis() + expireMsecs;
        String expiresStr = String.valueOf(expires);
        String currentValueStr = redisCacheProxy.get(key);
        if ((currentValueStr != null) && (Long.parseLong(currentValueStr) < System.currentTimeMillis())) {
            String oldValueStr = redisCacheProxy.getSet(key, expiresStr);
            if ((oldValueStr != null) && (oldValueStr.equals(currentValueStr))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     */
    public static void unLock(String key){
        if (isLock(key)) {
            redisCacheProxy.del(key);
        }
    }
}
