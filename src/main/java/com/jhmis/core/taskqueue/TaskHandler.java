package com.jhmis.core.taskqueue;

import java.util.List;

/**
 * 任务处理接口
 */
public interface TaskHandler {
    //处理任务
    void doHandler(Task task);
    //获取支持的任务类型
    List<TaskType> getSupportTaskType();
}
