package com.jhmis.modules.cms.directive;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Link;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.LinkService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@FreemarkerComponent("linkList")
public class LinkListDirective extends BaseDirective {
    @Autowired
    private LinkService linkService;
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        //显示条数
        int num = getParamInt(params, "num", 1000);
        //从第几个开始显示
        int beginnum = getParamInt(params, "beginnum", 0);
        //链接类型
        String linkType = getParam(params, "linkType");
        Writer out = env.getOut();
        if (body != null) {
            Link link = new Link();
            link.setLinkType(linkType);
            Page page = new Page<>();
            page.setPageNo(1);
            page.setPageSize(beginnum + num);
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                Page<Link> pageList = linkService.findPage(page, link);
                List<Link> linkList = pageList.getList();
                if (linkList != null && linkList.size() > 0) {
                    for (int i = 0; i < linkList.size(); i++) {
                        if (i >= beginnum) {
                            loopVars[0] = new BeanModel(linkList.get(i), new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                            if (loopVars.length > 1) {
                                loopVars[1] = new SimpleNumber(i);
                            }
                            body.render(env.getOut());
                        }
                    }
                }
            }
        }
    }
}
