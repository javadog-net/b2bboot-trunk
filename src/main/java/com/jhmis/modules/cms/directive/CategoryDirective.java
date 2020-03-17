package com.jhmis.modules.cms.directive;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * @author lydia
 * @date 2019/10/9 13:12
 */
@FreemarkerComponent("category")
public class CategoryDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)throws TemplateException, IOException {

        String id = getParam(params,"id");
        String categoryMark = getParam(params,"categoryMark");
        Category category = null;
        if(StringUtils.isNotEmpty(id)){
            category = categoryService.findUniqueByProperty("id",id);
        }else if(StringUtils.isNotEmpty(categoryMark)){
            category = categoryService.findUniqueByProperty("category_mark",categoryMark);
        }else{

        }
        if (body!=null && category != null) {
            if (loopVars != null && loopVars.length > 0) {
                loopVars[0] = new BeanModel(category, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                if (loopVars.length > 1) {
                    loopVars[1] = new SimpleNumber(0);
                }
            }
            body.render(env.getOut());
        }

    }
}
