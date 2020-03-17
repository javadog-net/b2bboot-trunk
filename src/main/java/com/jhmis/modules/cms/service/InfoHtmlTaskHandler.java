package com.jhmis.modules.cms.service;

import com.jhmis.core.taskqueue.Task;
import com.jhmis.core.taskqueue.TaskHandler;
import com.jhmis.core.taskqueue.TaskType;
import com.jhmis.modules.cms.entity.Info;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author lydia
 * @date 2019/12/8 17:50
 * 信息页静态化对列
 */
@Component
public class InfoHtmlTaskHandler implements TaskHandler {
    private static final Logger logger = LoggerFactory.getLogger(InfoHtmlTaskHandler.class);

    @Autowired
    private HtmlService htmlService;
    @Override
    public void doHandler(Task task) {
        logger.info("任务id:{}",task.getId());
        Info dto = (Info) task.getEntity();
        try {
            htmlService.htmlInfoHandler(dto);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
    @Override
    public List<TaskType> getSupportTaskType() {
        return   Arrays.asList(TaskType.INFO_HTML);
    }

}
