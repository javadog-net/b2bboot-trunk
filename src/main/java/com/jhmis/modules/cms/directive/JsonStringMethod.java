package com.jhmis.modules.cms.directive;

import com.alibaba.fastjson.JSON;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import freemarker.ext.beans.BeanModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * 
 * <p>
 * Title: JsEscapeDirective.java
 * </p>
 * Description: 将对像转化为json标准字符
 */
@FreemarkerComponent("jsonString")
public class JsonStringMethod implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List paramList) throws TemplateModelException {
        String result = "";
        if (null == paramList || paramList.size() == 0)
			return result;
        try {
        	Object param = paramList.get(0);
    		if (param instanceof BeanModel) {
    			Object wrappedObject = ((BeanModel) param).getWrappedObject();
    			result = JSON.toJSONString(wrappedObject);
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  result; 
	}

}
