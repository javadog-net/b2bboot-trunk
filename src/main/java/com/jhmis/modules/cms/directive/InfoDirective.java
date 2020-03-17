package com.jhmis.modules.cms.directive;

import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.InfoService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 内容详情标签
 * @author lydia
 * @date 2019/9/26 13:34
 */
@FreemarkerComponent("info")
public class InfoDirective extends BaseDirective {
    @Autowired
    private InfoService infoService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        //文章ID
        String id=getParam(params, "id");
        //html索引号
        String htmlindexnum=getParam(params, "htmlindexnum");
        //日期格式
        String dateFormat=getParam(params, "dateFormat");

        Writer out =env.getOut();
        if (body!=null) {
            //设置循环变量
            if (loopVars!=null && loopVars.length>0 ) {
                //查询信息
                Info info=null;
                if (StringUtils.isNotEmpty(id)) {
                    info=infoService.findUniqueByProperty("id",id);
                }else if (StringUtils.isNotEmpty(htmlindexnum)) {
                    try {
//                        info=infoService.findByIndexnum(Integer.parseInt(htmlindexnum));
                    } catch (Exception e) {
                    }
                }
                if (info!=null) {
//                    Site site=siteService.findById(info.getSite());
//                    if (site!=null) {
//                        //设置sitepath
//                        if (site!=null) {
//                            info.setSitepath(env.getDataModel().get("contextPathNo").toString()
//                                    +Htmlpath.site(site));
//                            info.setMobileSitepath(Htmlpath.site(site));
//                            info.setMobilepath(Htmlpath.mobile(site));
//                            info.setContextPath(env.getDataModel().get("contextPathNo").toString());
//                        }
//                    }
                    if (dateFormat.trim().length()>0) {
                        info.setDateFormat(dateFormat);
                    }
                    loopVars[0]=new BeanModel(info,new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                    body.render(env.getOut());
                }
            }
        }

    }
}
