/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.dealer;

import com.jhmis.common.config.Global;
import com.jhmis.common.mail.MailSendUtils;
import com.jhmis.common.sms.SMSUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.websocket.service.msg.MsgSocketHandler;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.core.taskqueue.Task;
import com.jhmis.core.taskqueue.TaskType;
import com.jhmis.core.taskqueue.TaskUtils;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.entity.dealer.DealerMsg;
import com.jhmis.modules.shop.entity.dealer.DealerMsgDTO;
import com.jhmis.modules.shop.entity.dealer.DealerMsgTpl;
import com.jhmis.modules.shop.mapper.dealer.DealerMsgMapper;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.sys.entity.SystemConfig;
import com.jhmis.modules.sys.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 供应商消息列表Service
 *
 * @author tity
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class DealerMsgService extends CrudService<DealerMsgMapper, DealerMsg> {
    private static final Logger logger = LoggerFactory.getLogger(DealerMsgService.class);
    @Autowired
    private DealerMsgTplService dealerMsgTplService;
    @Autowired
    private SystemConfigService systemConfigService;
    //websocket消息处理器
    private MsgSocketHandler msgSocketHandler = new MsgSocketHandler();

    public DealerMsg get(String id) {
        return super.get(id);
    }

    public List<DealerMsg> findList(DealerMsg dealerMsg) {
        return super.findList(dealerMsg);
    }

    public Page<DealerMsg> findPage(Page<DealerMsg> page, DealerMsg dealerMsg) {
        return super.findPage(page, dealerMsg);
    }

    public Page<DealerMsg> findSysMsgPage(Page<DealerMsg> page, DealerMsg dealerMsg) {
        dealerMsg.setPage(page);
        page.setList(mapper.findSysMsgList(dealerMsg));
        return page;
    }

    @Transactional(readOnly = false)
    public void save(DealerMsg dealerMsg) {
        super.save(dealerMsg);
    }

    @Transactional(readOnly = false)
    public void delete(DealerMsg dealerMsg) {
        this.mapper.deleteMsgRead(dealerMsg.getId());
        super.delete(dealerMsg);
    }

    /**
     * 读取消息
     *
     * @param msgId
     * @param accountId
     */
    @Transactional(readOnly = false)
    public void readMsg(String msgId, String accountId) {
        int count = this.mapper.isRead(msgId, accountId);
        if (count == 0) {
            this.mapper.readMsg(msgId, accountId);
        }
    }

    /**
     * 读取所有消息
     *
     * @param accountId
     */
    @Transactional(readOnly = false)
    public void readAllMsg(String accountId) {
        DealerMsg dealerMsg = new DealerMsg();
        dealerMsg.setAccountId(accountId);
        dealerMsg.setIsRead("0");
        List<DealerMsg> list = this.mapper.findList(dealerMsg);
        for (DealerMsg msg : list) {
            int count = this.mapper.isRead(msg.getId(), accountId);
            if (count == 0) {
                this.mapper.readMsg(msg.getId(), accountId);
            }
        }
    }


    /**
     * 未读消息数量
     *
     * @param type
     * @param accountId
     * @return
     */
    public int unReadCount(String type, String accountId) {
        return this.mapper.unReadCount(type, accountId);
    }

    /**
     * 发送消息，到队列
     *
     * @param code
     * @param dealerId
     * @param relId(主要传递订单id,招标id)
     * @param params
     */
    public void putMsg(String code, String dealerId, String relId, Map<String, String> params) {
        Task<DealerMsgDTO> task = new Task<>();
        task.setTaskType(TaskType.DEALER_MESSAGE);
        DealerMsgDTO dto = new DealerMsgDTO();
        dto.setCode(code);
        dto.setDealerId(dealerId);
        dto.setRelId(relId);
        dto.setParams(params);
        task.setEntity(dto);
        TaskUtils.put(task);
    }


    /**
     * 发送消息，根据消息实体
     *
     * @param dto
     */
    @Transactional(readOnly = false)
    public void send(DealerMsgDTO dto) {
        this.send(dto.getCode(), dto.getDealerId(), dto.getRelId(), dto.getParams());
    }

    /**
     * 发送模板消息
     * @param code
     * @param dealerId
     * @param relId(主要传递订单id,招标id)
     * @param params
     */
    @Transactional(readOnly = false)
    public void send(String code, String dealerId, String relId, Map<String, String> params) {
        Map<String, DealerMsgTpl> tplMap = dealerMsgTplService.getTplCache();
        DealerMsgTpl tpl = tplMap.get(code);
        if (tpl != null) {
            Dealer dealer = DealerUtils.getDealer(dealerId);
            if (dealer == null) return;
            try {
                if (Global.YES.equals(tpl.getMessageSwitch())) {
                    String tplContent = tpl.getMessageContent();
                    String content = dealerMsgTplService.processContent(tplContent, params);
                    DealerMsg dealerMsg = new DealerMsg();
                    dealerMsg.setCode(code);
                    dealerMsg.setType(tpl.getType());
                    dealerMsg.setDealerId(dealerId);
                    dealerMsg.setRelId(relId);
                    dealerMsg.setContent(content);
                    dealerMsg.setAddtime(new Date());
                    this.save(dealerMsg);
                    try {
                        msgSocketHandler.sendMessageToDealerById(dealerId,content);
                    } catch (Exception e) {
                        logger.error("发送站内信websocket错误：{}", e.getMessage());
                    }
                }
            } catch (Exception e) {
                logger.error("发送站内信错误：{}", e.getMessage());
            }
            //短信消息
            try {
                if (Global.YES.equals(tpl.getSmsSwitch())) {
                    String mobile = dealer.getMobile();
                    if (StringUtils.isNotBlank(mobile)) {
                        String tplContent = tpl.getSmsContent();
                        String content = dealerMsgTplService.processContent(tplContent, params);
                        SystemConfig config = systemConfigService.get("1");//获取短信和邮件配置
                        String ret = SMSUtils.send(config.getSmsName(), config.getSmsPassword(), mobile, content);
                        //TODO 写发送日志
                    }
                }
            } catch (Exception e) {
                logger.error("发送短信错误：{}", e.getMessage());
            }
            //邮件消息
            try {
                if (Global.YES.equals(tpl.getMailSwitch())) {
                    String email = dealer.getEmail();
                    if (StringUtils.isNotBlank(email)) {
                        String subjectTplContent = tpl.getMailSubject();
                        String subject = dealerMsgTplService.processContent(subjectTplContent, params);
                        String tplContent = tpl.getSmsContent();
                        String content = dealerMsgTplService.processContent(tplContent, params);
                        SystemConfig config = systemConfigService.get("1");//获取短信和邮件配置
                        boolean isSuccess = MailSendUtils.sendEmail(config.getSmtp(), config.getPort(), config.getMailName(), config.getMailPassword(), email, subject, content, "2");
                        //TODO 写发送日志
                    }
                }
            } catch (Exception e) {
                logger.error("发送邮件错误：{}", e.getMessage());
            }
        }
    }

}