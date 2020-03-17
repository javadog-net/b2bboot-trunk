package com.jhmis.core.taskqueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskClient implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(TaskClient.class);
    private static final int THREADSIZE = 5;
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(THREADSIZE);
    private ApplicationContext applicationContext;
    private IQueue<Task> queue;
    //存储各种type事件对应的Handler处理类
    private Map<TaskType, List<TaskHandler>> handlers = new HashMap<>();
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("任务处理器开始启动");
        queue = applicationContext.getBean(IQueue.class);
        //获取任务处理器
        Map<String, TaskHandler> taskHandlerMap = applicationContext.getBeansOfType(TaskHandler.class);
        if (taskHandlerMap != null){
            for (Map.Entry<String, TaskHandler> entry : taskHandlerMap.entrySet()){
                //遍历每个TaskHandler，将这个TaskHandler放到集合对应的type中
                TaskHandler taskHandler = entry.getValue();
                //获取每个taskHandler所支持的事件类型
                List<TaskType> taskTypes = taskHandler.getSupportTaskType();

                for (TaskType type : taskTypes){
                    if (!handlers.containsKey(type)){
                        handlers.put(type, new ArrayList<TaskHandler>());
                    }
                    //将Handler放入指定type中
                    handlers.get(type).add(taskHandler);

                }
            }
        }
        //
        for (int i = 0; i <= THREADSIZE; i++) {
            fixedThreadPool.execute(new Consumer());
        }
        logger.info("任务处理器线程池创建完毕");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 消费者
     */
    private class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                Task task = null;
                try {
                    task = queue.take();
                    if(task == null) continue;
                    task.setStartTime(new Date());
                    //根据任务类型，执行不同的任务处理器
                    if (!handlers.containsKey(task.getTaskType())){
                        logger.error("不能识别的任务类型！");
                        continue;
                    }
                    //获取任务支持的的handler,进行处理
                    for (TaskHandler handler : handlers.get(task.getTaskType())){
                        handler.doHandler(task);
                    }
                    //
                    task.setFinishedTime(new Date());
                    //TODO 写入数据库执行状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    String taskId = "";
                    if (task != null) {
                        taskId = task.getId();
                    }
                    logger.error("执行任务错误,任务id:{},错误：{}", taskId, e.getMessage());
                }
            }
        }
    }
}
