package com.jhmis.modules.cms.directive;

import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Navigation;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.NavigationService;
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
 * @author lydia
 * @date 2019/9/23 9:42
 * 导航列表标签
 */
@FreemarkerComponent("navigationList")
public class NavigationListDirective  extends BaseDirective {
    @Autowired
    private NavigationService navigationService;
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String parid = getParam(params, "parid");// 空字符:所有;"par":一级栏目;"parid":此id下栏目;
        //是否只提取允许移动app访问的数据 1是
        String ismobile = getParam(params, "ismobile");
        String position = getParam(params, "position");
        Writer out = env.getOut();
        if (body != null) {
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                List<Navigation> navigationList = navigationService.findByPar(parid, position, ismobile);
                if (navigationList.size() > 0) {
                    //TODO 异常处理，待研究
//                    navigationList.forEach(navigation -> {
//                        loopVars[0] = new BeanModel(navigation, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
//                        if (loopVars.length > 1) {
//                            loopVars[1] = new SimpleNumber(navigationList.indexOf(navigation));
//                        }
//                        body.render(env.getOut());
//
//                    });
                    for (Navigation navigation : navigationList) {
                        loopVars[0] = new BeanModel(navigation, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                        if (loopVars.length > 1) {
                            loopVars[1] = new SimpleNumber(navigationList.indexOf(navigation));
                        }
                        body.render(env.getOut());

                    }
                }
            }
        }
    }
}
