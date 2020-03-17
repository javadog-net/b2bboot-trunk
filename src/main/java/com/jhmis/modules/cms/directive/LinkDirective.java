package com.jhmis.modules.cms.directive;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Link;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.LinkService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * @author lydia
 * @date 2019/11/8 16:50
 */
@FreemarkerComponent("link")
public class LinkDirective extends BaseDirective {
    @Autowired
    private LinkService linkService;
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String id = getParam(params,"id");
        if(StringUtils.isNotEmpty(id)){
            Link link = linkService.findUniqueByProperty("id",id);
            if (body != null) {
                if (loopVars != null && loopVars.length > 0) {
                    loopVars[0] = new BeanModel(link, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                    body.render(env.getOut());
                }
            }
        }
    }
}
