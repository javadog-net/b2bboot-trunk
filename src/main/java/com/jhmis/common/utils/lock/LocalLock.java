package com.jhmis.common.utils.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LocalLock {
    private static final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    /**
     * 锁定
     * @param key
     * @return
     */
    public static boolean lock(String key){
        ReentrantLock lock = getLock(key);
        if (null == lock) {
            lock = lockMap.get(key);
        }
        try {
            lock.lockInterruptibly();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 锁定，带超时参数
     * @param key
     * @param timeoutMsecs
     * @return
     */
    public static boolean lock(String key, long timeoutMsecs){
        ReentrantLock lock = getLock(key);
        if (null == lock) {
            lock = lockMap.get(key);
        }
        try {
            return lock.tryLock(timeoutMsecs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否锁定
     * @param key
     * @return
     */
    public static boolean isLock(String key) {
        return lockMap.containsKey(key) && lockMap.get(key).isLocked();
    }

    /**
     * 解锁
     * @param key
     */
    public static void unLock(String key){
        ReentrantLock lock = getLock(key);
        if (null != lock) {
            lock.unlock();
            if (!lock.hasQueuedThreads()) {
                lockMap.remove(key);
            }
        }
    }

    private static ReentrantLock getLock(String key) {
        return lockMap.putIfAbsent(key, new ReentrantLock());
    }
}
