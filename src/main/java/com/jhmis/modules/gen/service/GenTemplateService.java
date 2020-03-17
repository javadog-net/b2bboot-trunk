package com.jhmis.modules.gen.service;

import com.jhmis.modules.gen.entity.GenTemplate;
import com.jhmis.modules.gen.mapper.GenTemplateMapper;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.BaseService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模板管理服务
 * @author tity
 *
 */
@Service
@Transactional(
    readOnly = true
)
public class GenTemplateService extends BaseService {
    @Autowired
    private GenTemplateMapper genTemplateMapper;

    public GenTemplateService() {
    }

    public GenTemplate a2(String a) {
        return (GenTemplate)this.genTemplateMapper.get(a);
    }

    public Page<GenTemplate> a(Page<GenTemplate> a, GenTemplate a1) {
        a1.setPage(a);
        a.setList(this.genTemplateMapper.findList(a1));
        return a;
    }

    @Transactional(
        readOnly = false
    )
    public void a1(GenTemplate a1) {
        if (a1.getContent() != null) {
            a1.setContent(StringEscapeUtils.unescapeHtml4(a1.getContent()));
        }

        if (StringUtils.isBlank(a1.getId())) {
            a1.preInsert();
            this.genTemplateMapper.insert(a1);
        } else {
            a1.preUpdate();
            this.genTemplateMapper.update(a1);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void a(GenTemplate a1) {
        this.genTemplateMapper.delete(a1);
    }
}
