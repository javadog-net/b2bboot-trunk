package com.jhmis.core.taskqueue;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 任务id
     */
    private String id;
    /**
     * 任务类型
     */
    private TaskType taskType;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 任务创建时间
     */
    private Date createTime = new Date();

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务完成时间
     */
    private Date finishedTime;

    /**
     * 任务类容实体
     */
    private T entity;

    public String getId() {
        if(id == null){
            id = UUID.randomUUID().toString().replaceAll("-", "");
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
