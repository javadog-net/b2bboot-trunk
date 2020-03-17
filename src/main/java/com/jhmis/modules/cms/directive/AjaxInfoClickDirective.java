package com.jhmis.modules.cms.directive;


import com.jhmis.common.utils.IdGen;
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

/**
 * 信息点击
 */
@FreemarkerComponent("ajaxInfoClick")
public class AjaxInfoClickDirective extends BaseDirective {
	//TODO 具体的代码待调试的时候重新整理
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
						TemplateDirectiveBody body)throws TemplateException, IOException {

		//信息id
		String infoid=getParam(params, "infoId");
		//点击量显示span的属性
		String spanAttr=getParam(params, "spanAttr");
		//是否加载引用的js
		String loadjs=getParam(params, "loadjs");
		
		Writer out =env.getOut();
		if (body!=null) {
			//设置循环变量
			if (loopVars!=null && loopVars.length>0 && infoid.trim().length()>0) {
				String contextPath=env.getDataModel().get("contextPath").toString();
				StringBuilder sb=new StringBuilder();

				if ("true".equals(loadjs)) {
					//导入js
					sb.append("<script src='"+contextPath+"js/jquery-1.8.3.min.js'></script>");
				}
				//生成唯一标识
				String uuid= IdGen.uuid().replace("-", "");
				//生成显示点击量的span,默认显示loading
				sb.append("<span id='ajaxInfoClickSpan"+uuid+"' "+spanAttr+"><img src='"+contextPath+"js/images/ajax-loader.gif'/></span>");
				sb.append("<script>");
				//执行ajax操作
				sb.append("$.post('"+contextPath+"infoAjaxClick.do','id="+infoid+"&click="+getParam(params, "click")+"',ajaxInfoClickComplete"+uuid+",'text');");
				//回调函数
				sb.append("function ajaxInfoClickComplete"+uuid+"(data){");
				//显示点击量
				sb.append("$('#ajaxInfoClickSpan"+uuid+"').html(data);");
				sb.append("}");
				sb.append("</script>");
				loopVars[0]=new StringModel(sb.toString(),new BeansWrapper());
				body.render(env.getOut());  
			}
		}
	}
}
