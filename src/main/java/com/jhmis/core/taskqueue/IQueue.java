package com.jhmis.core.taskqueue;

/**
 * 简化队列接口
 * 只保留put和take方法
 * @param <E>
 */
public interface IQueue<E> {
    /**
     * 放入队列
     * @param e
     * @throws InterruptedException
     */
    void put(E e) throws InterruptedException;

    /**
     * 取出队列
     * @return
     * @throws InterruptedException
     */
    E take() throws InterruptedException;

    /**
     * 队列长度
     * @return
     */
    int size();
}
