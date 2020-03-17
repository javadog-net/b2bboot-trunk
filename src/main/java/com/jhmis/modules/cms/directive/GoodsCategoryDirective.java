package com.jhmis.modules.cms.directive;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

@FreemarkerComponent("goodsCategory")
public class GoodsCategoryDirective extends BaseDirective {
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String id = getParam(params, "id");
        String pageMark = getParam(params, "pageMark");
        GoodsCategory goodsCategory = new GoodsCategory();
        if (StringUtils.isNotEmpty(id)) {
            goodsCategory.setId(id);
        }
        if (StringUtils.isNotEmpty(pageMark)) {
            goodsCategory.setPageMark(pageMark);
        }
        goodsCategory = goodsCategoryService.get(goodsCategory);
        if (body != null) {
            loopVars[0] = new BeanModel(goodsCategory, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
            if (loopVars.length > 1) {
                loopVars[1] = new SimpleNumber(0);
            }
            body.render(env.getOut());
        }
    }
}
