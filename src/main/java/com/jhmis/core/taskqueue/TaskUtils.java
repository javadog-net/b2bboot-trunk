package com.jhmis.core.taskqueue;

import com.jhmis.common.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskUtils {
    private static final Logger logger = LoggerFactory.getLogger(TaskUtils.class);
    //必须先初始化任务队列bean
    private static IQueue<Task> queue =  SpringContextHolder.getBean(IQueue.class);

    public static void put(Task task){
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("任务存入队列失败：{}",e.getMessage());
        }
    }
}
