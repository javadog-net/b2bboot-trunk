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
import java.util.List;
import java.util.Map;
@FreemarkerComponent("goodsCategoryList")
public class GoodsCategoryListDirective extends BaseDirective {
    @Autowired
    GoodsCategoryService goodsCategoryService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String parid = getParam(params, "parid");// 空字符:所有;"par":一级商品分类;"parid":此id下商品分类;
        if (loopVars != null && loopVars.length > 0) {
            GoodsCategory goodsCategory = new GoodsCategory();
            if (StringUtils.isNotEmpty(parid)) {
                GoodsCategory parent = new GoodsCategory();
                if ("par".equals(parid)) {
                    parent.setId("0");
                } else {
                    parent.setId(parid);
                }
                goodsCategory.setParent(parent);
            }
            //查询商品分类二级
            List<GoodsCategory> goodsCategoryList = goodsCategoryService.findList(goodsCategory);
            for (int i = 0; i < goodsCategoryList.size(); i++) {
                loopVars[0] = new BeanModel(goodsCategoryList.get(i), new BeansWrapper());
                if (loopVars.length > 1){
                    loopVars[1] = new SimpleNumber(i);
                }
                body.render(env.getOut());
            }
            if(null==goodsCategoryList||goodsCategoryList.size()<=0){
                GoodsCategory gc=goodsCategoryService.get(parid);
                GoodsCategory goodsc=new GoodsCategory();
                GoodsCategory p= new GoodsCategory();
                p.setId(gc.getParentId());
                goodsc.setParent(p);
                goodsCategoryList = goodsCategoryService.findList(goodsc);
                for (int i = 0; i < goodsCategoryList.size(); i++) {
                    loopVars[0] = new BeanModel(goodsCategoryList.get(i), new BeansWrapper());
                    if (loopVars.length > 1){
                        loopVars[1] = new SimpleNumber(i);
                    }
                    body.render(env.getOut());
                }

            }
        }
    }
}
