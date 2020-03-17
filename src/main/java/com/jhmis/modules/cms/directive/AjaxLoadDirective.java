package com.jhmis.modules.cms.directive;

import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.UUID;

/**
 * @author lydia
 * @date 2019/11/6 17:34
 */
@FreemarkerComponent("ajaxLoad")
public class AjaxLoadDirective extends BaseDirective {
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)throws TemplateException, IOException {
//获取参数
        //把页面加载到指定id元素下
        String targetid=getParam(params, "targetid");
        //页面地址
        String url=getParam(params, "url");
        //参数，使用json数据
        String param=getParam(params, "param");
        //获取方法，get(默认)或post
        String method=getParam(params, "method","get");
        //是否加载引用的js
        String loadjs=getParam(params, "loadjs");

        Writer out =env.getOut();
        if (body!=null) {
            //设置循环变量
            if (loopVars!=null && loopVars.length>0 && targetid.trim().length()>0 && url.trim().length()>0) {
                String contextPath=env.getDataModel().get("contextPathNo").toString();
                StringBuilder sb=new StringBuilder();
                if ("true".equals(loadjs)) {
                    //导入js
                    sb.append("<script src='"+contextPath+"/js/jquery-1.8.3.min.js'></script>");
                }
                //生成唯一标识
                String uuid= UUID.randomUUID().toString().replace("-", "");
                //生成显示点击量的span,默认显示loading
                sb.append("<img src='"+contextPath+"/js/images/ajax-loader.gif'/>");
                sb.append("<script>");
                //执行ajax操作
                sb.append("$."+method+"('"+url+"',{"+param.replaceAll("'", "\"")+"},ajaxLoadComplete"+uuid+",'text');");
                //回调函数
                sb.append("function ajaxLoadComplete"+uuid+"(data){");
                //显示点击量
                sb.append("$('#"+targetid+"').html(data);");
                sb.append("}");
                sb.append("</script>");
                loopVars[0]=new StringModel(sb.toString(),new BeansWrapper());
                body.render(env.getOut());
            }
        }
    }
}
