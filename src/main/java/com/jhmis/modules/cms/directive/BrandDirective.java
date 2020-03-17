package com.jhmis.modules.cms.directive;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Link;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.LinkService;
import com.jhmis.modules.shop.entity.Brand;
import com.jhmis.modules.shop.service.BrandService;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@FreemarkerComponent("brand")
public class BrandDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String categoryId = getParam(params,"categoryId");
        String categoryMark = getParam(params,"categoryMark");
        //显示条数
        int num = getParamInt(params, "num", 10);
        //从第几个开始显示
        int beginnum = getParamInt(params, "beginnum", 0);
        Writer out = env.getOut();
        if (body != null) {
            Page<Category> page = new Page<Category>();
            page.setPageNo(1);
            page.setPageSize(beginnum + num);
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                Category category=new Category();
                Category category1=new Category();
                if(StringUtils.isNotBlank(categoryId)) {
                    category1.setId(categoryId);
                }
                if(StringUtils.isNotBlank(categoryMark)) {
                    category1.setCategoryMark(categoryMark);
                    List<Category> listca=categoryService.findList(category1);
                    if(null!=listca&&listca.size()>0) {
                        category1.setId(listca.get(0).getId());
                    }
                }
                category.setParent(category1);
                Page<Category> pageList = categoryService.findPage(page, category);
                List<Category> categoriesList = pageList.getList();
                if (categoriesList != null && categoriesList.size() > 0) {
                    for (int i = 0; i < categoriesList.size(); i++) {
                        if (i >= beginnum) {
                            loopVars[0] = new BeanModel(categoriesList.get(i), new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                            if (loopVars.length > 1) {
                                loopVars[1] = new SimpleNumber(i);
                            }
                            body.render(out);
                        }
                    }
                }
            }
        }
    }
}
