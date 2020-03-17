/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.purchaser;

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
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsg;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsgDTO;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsgTpl;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserMsgMapper;
import com.jhmis.modules.shop.utils.PurchaserUtils;
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
 * 采购商消息列表Service
 *
 * @author tity
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class PurchaserMsgService extends CrudService<PurchaserMsgMapper, PurchaserMsg> {
    private static final Logger logger = LoggerFactory.getLogger(PurchaserMsgService.class);
    @Autowired
    private PurchaserMsgTplService purchaserMsgTplService;
    @Autowired
    private SystemConfigService systemConfigService;
    //websocket消息处理器
    private MsgSocketHandler msgSocketHandler = new MsgSocketHandler();
    public PurchaserMsg get(String id) {
        return super.get(id);
    }

    public List<PurchaserMsg> findList(PurchaserMsg purchaserMsg) {
        return super.findList(purchaserMsg);
    }

    public Page<PurchaserMsg> findPage(Page<PurchaserMsg> page, PurchaserMsg purchaserMsg) {
        return super.findPage(page, purchaserMsg);
    }

    public Page<PurchaserMsg> findSysMsgPage(Page<PurchaserMsg> page, PurchaserMsg purchaserMsg) {
        purchaserMsg.setPage(page);
        page.setList(mapper.findSysMsgList(purchaserMsg));
        return page;
    }

    @Transactional(readOnly = false)
    public void save(PurchaserMsg purchaserMsg) {
        super.save(purchaserMsg);
    }

    @Transactional(readOnly = false)
    public void delete(PurchaserMsg purchaserMsg) {
        this.mapper.deleteMsgRead(purchaserMsg.getId());
        super.delete(purchaserMsg);
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
        PurchaserMsg purchaserMsg = new PurchaserMsg();
        purchaserMsg.setAccountId(accountId);
        purchaserMsg.setIsRead("0");
        List<PurchaserMsg> list = this.mapper.findList(purchaserMsg);
        for (PurchaserMsg msg : list) {
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
     * @param purchaserId
     * @param relId(主要传递订单id,招标id)
     * @param params
     */
    public void putMsg(String code, String purchaserId, String relId, Map<String, String> params) {
        Task<PurchaserMsgDTO> task = new Task<>();
        task.setTaskType(TaskType.PURCHASER_MESSAGE);
        PurchaserMsgDTO dto = new PurchaserMsgDTO();
        dto.setCode(code);
        dto.setPurchaserId(purchaserId);
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
    public void send(PurchaserMsgDTO dto) {
        this.send(dto.getCode(), dto.getPurchaserId(), dto.getRelId(), dto.getParams());
    }

    /**
     * 发送模板消息
     * @param code
     * @param purchaserId
     * @param relId(主要传递订单id,招标id)
     * @param params
     */
    @Transactional(readOnly = false)
    public void send(String code, String purchaserId, String relId, Map<String, String> params) {
        Map<String, PurchaserMsgTpl> tplMap = purchaserMsgTplService.getTplCache();
        PurchaserMsgTpl tpl = tplMap.get(code);
        if (tpl != null) {
            Purchaser purchaser = PurchaserUtils.getPurchaser(purchaserId);
            if (purchaser == null) return;
            try {
                if (Global.YES.equals(tpl.getMessageSwitch())) {
                    String tplContent = tpl.getMessageContent();
                    String content = purchaserMsgTplService.processContent(tplContent, params);
                    PurchaserMsg purchaserMsg = new PurchaserMsg();
                    purchaserMsg.setCode(code);
                    purchaserMsg.setType(tpl.getType());
                    purchaserMsg.setPurchaserId(purchaserId);
                    purchaserMsg.setRelId(relId);
                    purchaserMsg.setContent(content);
                    purchaserMsg.setAddtime(new Date());
                    this.save(purchaserMsg);
                    try {
                        msgSocketHandler.sendMessageToDealerById(purchaserId,content);
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
                    String mobile = purchaser.getMobile();
                    if (StringUtils.isNotBlank(mobile)) {
                        String tplContent = tpl.getSmsContent();
                        String content = purchaserMsgTplService.processContent(tplContent, params);
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
                    String email = purchaser.getEmail();
                    if (StringUtils.isNotBlank(email)) {
                        String subjectTplContent = tpl.getMailSubject();
                        String subject = purchaserMsgTplService.processContent(subjectTplContent, params);
                        String tplContent = tpl.getSmsContent();
                        String content = purchaserMsgTplService.processContent(tplContent, params);
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