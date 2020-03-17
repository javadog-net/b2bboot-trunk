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
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * 内容栏目路径
 * @author lydia
 * @date 2019/10/07 17:13
 */
@FreemarkerComponent("categoryPath")
public class CategoryPathDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)throws TemplateException, IOException {
        //栏目ID
        String categoryId = getParam(params,"id");
        //栏目标识
        String categoryMark = getParam(params,"categoryMark");
        Writer out =env.getOut();
        if (body!=null) {
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                Category category = null;
                //首先通过栏目Id查询栏目信息
                if (StringUtils.isNotEmpty(categoryId.trim())) {
                    category = categoryService.findUniqueByProperty("id", categoryId);
                    //通过栏目标识查询栏目信息
                } else if (StringUtils.isNotEmpty(categoryMark.trim())) {
                    category = categoryService.findUniqueByProperty("category_mark", categoryMark);
                }
                if (category != null) {
                    //查询该栏目的全路径（所有父级栏目+当前栏目）
                    List<Category> categoryList = categoryService.findByIdPath(category.getId());
                    for (int i = 0; i < categoryList.size(); i++) {
                        loopVars[0] = new BeanModel(categoryList.get(i), new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
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
