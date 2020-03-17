package com.jhmis.modules.shop.service.dealer;

import com.jhmis.core.taskqueue.Task;
import com.jhmis.core.taskqueue.TaskHandler;
import com.jhmis.core.taskqueue.TaskType;
import com.jhmis.modules.shop.entity.dealer.DealerMsgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DealerMsgTaskHandler implements TaskHandler {
    private static final Logger logger = LoggerFactory.getLogger(DealerMsgTaskHandler.class);
    @Autowired
    private
    DealerMsgService dealerMsgService;
    @Override
    public void doHandler(Task task) {
        logger.info("任务id:{}",task.getId());
        DealerMsgDTO dto = (DealerMsgDTO) task.getEntity();
        dealerMsgService.send(dto);
    }

    @Override
    public List<TaskType> getSupportTaskType() {
        return Arrays.asList(TaskType.DEALER_MESSAGE);
    }
}
