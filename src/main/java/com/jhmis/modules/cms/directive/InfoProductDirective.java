package com.jhmis.modules.cms.directive;

import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.InfoProduct;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.InfoProductService;
import freemarker.core.Environment;
import freemarker.ext.beans.ArrayModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * 文章关联商品标签
 * @author lydia
 * @date 2019/10/14 15:20
 */
@FreemarkerComponent("infoProduct")
public class InfoProductDirective extends BaseDirective {
    @Autowired
    private InfoProductService infoProductService;
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)throws TemplateException, IOException {
        //信息id
        String infoid = getParam(params, "infoid");
        Writer out =env.getOut();
        if (body!=null) {
            //设置循环变量
            if (loopVars!=null && loopVars.length>0 && infoid.trim().length()>0) {
                //查询商品集
                InfoProduct infoProduct=new InfoProduct();
                infoProduct.setInfoId(infoid);
                List<InfoProduct> infoProductList=infoProductService.findList(infoProduct);
                if (infoProductList!=null && infoProductList.size()>0) {
                    loopVars[0]=new ArrayModel(infoProductList.toArray(),new BeansWrapper());
                    body.render(env.getOut());
                }
            }
        }
    }
}
