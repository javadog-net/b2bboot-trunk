package com.jhmis.core.taskqueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 本地队列
 * @param <E>
 */
public class LocalQueue<E> implements IQueue<E> {
    private LinkedBlockingQueue<E> queue = new LinkedBlockingQueue<>();
    @Override
    public void put(E e) throws InterruptedException {
        queue.put(e);
    }

    @Override
    public E take() throws InterruptedException {
        return queue.take();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
