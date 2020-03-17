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
 * ajax 访问
 */
@FreemarkerComponent("ajaxVisit")
public class AjaxVisitDirective extends BaseDirective {
	
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)throws TemplateException, IOException {

		//栏目id
		String categoryId=getParam(params, "categoryId");
		//信息id
		String infoId=getParam(params, "infoId");
		//是否加载引用的js
		String loadjs=getParam(params, "loadjs");
		Writer out =env.getOut();
		if (body!=null) {
			//设置循环变量
			if (loopVars!=null && loopVars.length>0 ) {
				String contextPath=env.getDataModel().get("contextPath").toString();
				StringBuilder sb=new StringBuilder();

				//TODO 具体的代码待调试的时候重新整理
				if ("true".equals(loadjs)) {
					//导入js
					sb.append("<script src='"+contextPath+"js/jquery-1.8.3.min.js'></script>");
				}
				//生成唯一标识
				String uuid=UUID.randomUUID().toString().replace("-", "");
				sb.append("<script>");
				//执行ajax操作
				sb.append("$.post('"+contextPath+"ajaxVisit.do'" +
						",'categoryId="+categoryId+"&infoId="+infoId+"&url='+location.href.replace(/\\?/g,'$$').replace(/\\&/g,'$$$'));");
				sb.append("</script>");
				loopVars[0]=new StringModel(sb.toString(),new BeansWrapper());
				body.render(env.getOut());  
			}
		}
	}
}
