package com.jhmis.modules.shop.service.purchaser;

import com.jhmis.core.taskqueue.Task;
import com.jhmis.core.taskqueue.TaskHandler;
import com.jhmis.core.taskqueue.TaskType;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class PurchaserMsgTaskHandler implements TaskHandler {
    private static final Logger logger = LoggerFactory.getLogger(PurchaserMsgTaskHandler.class);
    @Autowired
    private
    PurchaserMsgService purchaserMsgService;
    @Override
    public void doHandler(Task task) {
        logger.info("任务id:{}",task.getId());
        PurchaserMsgDTO dto = (PurchaserMsgDTO) task.getEntity();
        purchaserMsgService.send(dto);
    }

    @Override
    public List<TaskType> getSupportTaskType() {
        return Arrays.asList(TaskType.PURCHASER_MESSAGE);
    }
}
