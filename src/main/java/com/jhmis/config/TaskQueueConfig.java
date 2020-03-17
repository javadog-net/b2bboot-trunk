package com.jhmis.config;

import com.jhmis.core.taskqueue.IQueue;
import com.jhmis.core.taskqueue.QueueFactory;
import com.jhmis.core.taskqueue.Task;
import com.jhmis.core.taskqueue.TaskClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 任务队列配置
 */
@Configuration
public class TaskQueueConfig {

    @Bean
    public IQueue<Task> queue() {
        IQueue<Task> bean = QueueFactory.newQueue();
        return bean;
    }

    @Bean
    public TaskClient taskClient(){
        return new TaskClient();
    }
}
