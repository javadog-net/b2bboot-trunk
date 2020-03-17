package com.jhmis.core.taskqueue;

import net.oschina.j2cache.redis.RedisCacheProvider;
import net.oschina.j2cache.redis.RedisCacheProxy;
import net.oschina.j2cache.util.SerializationUtils;

import java.io.*;
import java.util.List;

/**
 * redis队列
 *
 * @param <E>
 */
public class RedisQueue<E> implements IQueue<E> {
    private static final RedisCacheProxy redisCacheProxy = new RedisCacheProvider().getResource();
    private static final String TASK_QUEUE_KEY = "task_queue_key";

    /**
     * 对象转byte[]
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] object2Bytes(Object obj) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        byte[] bytes = bo.toByteArray();
        bo.close();
        oo.close();
        return bytes;
    }

    /**
     * byte[]转对象
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytes2Object(byte[] bytes) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }

    /**
     * 放入队列
     * 没有使用对象转字节流，使用了j2Cache提供的序列化方法。
     * @param e
     * @throws InterruptedException
     */
    @Override
    public void put(E e) throws InterruptedException {
        try {
            redisCacheProxy.lpush(TASK_QUEUE_KEY.getBytes(), SerializationUtils.serialize(e));
        } catch (IOException e1) {
            throw new InterruptedException();
        }
    }

    @Override
    public E take() throws InterruptedException {
        List<byte[]> objs =  redisCacheProxy.brpop(0,TASK_QUEUE_KEY.getBytes());
        if(objs == null || objs.size() !=2){
            return null;
        }
        try {
            return (E)SerializationUtils.deserialize(objs.get(1));
        } catch (IOException e1) {
            throw new InterruptedException();
        }
    }

    @Override
    public int size() {
        return redisCacheProxy.llen(TASK_QUEUE_KEY).intValue();
    }
}
