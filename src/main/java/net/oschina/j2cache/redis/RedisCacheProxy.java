package net.oschina.j2cache.redis;

import net.oschina.j2cache.redis.client.RedisClient;
import net.oschina.j2cache.redis.support.RedisClientFactoryAdapter;
import redis.clients.jedis.BinaryJedisPubSub;

import java.io.Closeable;
import java.util.List;
import java.util.Set;

/**
 * Redis cache 代理，用来获取 redis client
 *
 * @author zhangyw
 */
public class RedisCacheProxy implements Closeable {

    private RedisClientFactoryAdapter redisClientFactoryAdapter;

    public RedisCacheProxy(RedisClientFactoryAdapter redisClientFactoryAdapter) {
        this.redisClientFactoryAdapter = redisClientFactoryAdapter;
        if (this.redisClientFactoryAdapter == null) {
            throw new RuntimeException("jedis handler adapter must configuration");
        }
    }

    public RedisClient getResource() {
        return this.redisClientFactoryAdapter.getRedisClientFactory().getResource();
    }

    @SuppressWarnings("unchecked")
	public void returnResource(RedisClient redisClient) {
        this.redisClientFactoryAdapter.getRedisClientFactory().returnResource(redisClient);
    }

    public byte[] hget(byte[] key, byte[] fieldKey) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.hget(key, fieldKey);
        } finally {
            returnResource(redisClient);
        }
    }

    public void hset(byte[] key, byte[] fieldKey, byte[] val) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            redisClient.hset(key, fieldKey, val);
        } finally {
            returnResource(redisClient);
        }
    }
    
    public void hset(byte[] key, byte[] fieldKey, byte[] val, int expireInSec) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            redisClient.hset(key, fieldKey, val);
            redisClient.expire(key, expireInSec);
        } finally {
            returnResource(redisClient);
        }
    }

    public void hdel(byte[] key, byte[]... fieldKey) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            redisClient.hdel(key, fieldKey);
        } finally {
            returnResource(redisClient);
        }
    }

    public Set<String> hkeys(String key) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.hkeys(key);
        } finally {
            returnResource(redisClient);
        }
    }

    public Set<byte[]> hkeys(byte[] key) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.hkeys(key);
        } finally {
            returnResource(redisClient);
        }
    }

    public void del(String key) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            redisClient.del(key);
        } finally {
            returnResource(redisClient);
        }
    }

    public void del(byte[] key) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            redisClient.del(key);
        } finally {
            returnResource(redisClient);
        }
    }

    public void subscribe(BinaryJedisPubSub binaryJedisPubSub, byte[]... channels) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            redisClient.subscribe(binaryJedisPubSub, channels);
        } finally {
            returnResource(redisClient);
        }
    }

    public void publish(byte[] channel, byte[] message) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            redisClient.publish(channel, message);
        } finally {
            returnResource(redisClient);
        }
    }

    public void close() {
        redisClientFactoryAdapter.close();
    }
    
    public Long setnx(String key, String value){
    	RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.setnx(key, value);
        } finally {
            returnResource(redisClient);
        }
    }
    
    public String get(String key){
    	RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.get(key);
        } finally {
            returnResource(redisClient);
        }
    }
    
    public String getSet(String key, String value){
    	RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.getSet(key, value);
        } finally {
            returnResource(redisClient);
        }
    }

    /**
     * 入队
     * @param key
     * @param args
     * @return
     */
    public Long lpush(byte[] key, byte[]... args) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.lpush(key, args);
        } finally {
            returnResource(redisClient);
        }
    }

    /**
     * 入队
     * @param key
     * @param strings
     * @return
     */
    public Long lpush(String key, String... strings) {
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.lpush(key, strings);
        } finally {
            returnResource(redisClient);
        }
    }

    /**
     * 出队
     * @param timeout
     * @param keys
     * @return
     */
    public List<byte[]> brpop(int timeout, byte[]... keys){
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.brpop(timeout, keys);
        } finally {
            returnResource(redisClient);
        }
    }

    /**
     * 出队
     * @param timeout
     * @param keys
     * @return
     */
    public List<String> brpop(int timeout, String... keys){
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.brpop(timeout, keys);
        } finally {
            returnResource(redisClient);
        }
    }

    /**
     * 队列长度
     * @param key
     * @return
     */
    public Long llen(byte[] key){
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.llen(key);
        } finally {
            returnResource(redisClient);
        }
    }

    /**
     * 队列长度
     * @param key
     * @return
     */
    public Long llen(String key){
        RedisClient redisClient = null;
        try {
            redisClient = getResource();
            return redisClient.llen(key);
        } finally {
            returnResource(redisClient);
        }
    }
}
