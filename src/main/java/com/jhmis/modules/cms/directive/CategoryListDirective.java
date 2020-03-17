package com.jhmis.modules.cms.directive;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleNumber;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 栏目页列表标签
 *
 * @author lydia
 * @date 2019/10/15 17:42
 */
@FreemarkerComponent("categoryList")
public class CategoryListDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        String parid = getParam(params, "parid");// 空字符:所有;"par":一级栏目;"parid":此id下栏目;
        String ismobile = getParam(params, "ismobile"); //
        String categoryMarkPar = getParam(params, "categoryMarkPar");
        String isShow = getParam(params, "isShow");
        List<Category> categoryList = null;
        Category category = new Category();
        Category parent = new Category();
        if (StringUtils.isNotEmpty(isShow)) {
            category.setIsShow(isShow);
        }
        if (StringUtils.isNotEmpty(parid)) {
            //取一级栏目信息
            if ("par".equals(parid)) {
                parid = "0";
            }
        } else if (StringUtils.isNotEmpty(categoryMarkPar)) {
            category = categoryService.findUniqueByProperty("category_mark", categoryMarkPar);
            if (category != null && StringUtils.isNotEmpty(category.getId())) {
                parid = category.getId();
            }
        }
        if (StringUtils.isNotEmpty(parid)) {
            parent.setId(parid);
            category.setParent(parent);
        }
        //查询parid 下的所有栏目信息
        categoryList = categoryService.findByParentId(category);
        if (body != null) {
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                for (int i = 0; i < categoryList.size(); i++) {
                    loopVars[0] = new BeanModel(categoryList.get(i), new BeansWrapper());
                    if (loopVars.length > 1) {
                        loopVars[1] = new SimpleNumber(i);
                    }
                    body.render(env.getOut());
                }
            }
        }
    }
}
