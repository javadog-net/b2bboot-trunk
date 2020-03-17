/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.sys.entity.Attachment;
import com.jhmis.modules.sys.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 附件表Service
 *
 * @author tity
 * @version 2018-07-06
 */
@Service
@Transactional(readOnly = true)
public class AttachmentService extends CrudService<AttachmentMapper, Attachment> {
    public Attachment get(String id) {
        return super.get(id);
    }

    public List<Attachment> findList(Attachment attachment) {
        return super.findList(attachment);
    }

    public Page<Attachment> findPage(Page<Attachment> page, Attachment attachment) {
        return super.findPage(page, attachment);
    }

    @Transactional(readOnly = false)
    public void save(Attachment attachment) {
        super.save(attachment);
    }

    @Transactional(readOnly = false)
    public void delete(Attachment attachment) {
        super.delete(attachment);
    }
}