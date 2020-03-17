package com.jhmis.modules.cms.directive;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.sys.entity.DictType;
import com.jhmis.modules.sys.entity.DictValue;
import com.jhmis.modules.sys.service.DictTypeService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author lydia
 * @date 2019/11/3 15:18
 */
@FreemarkerComponent("dictionary")
public class DictionaryDirective extends BaseDirective {
    @Autowired
    private DictTypeService dictTypeService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {

        //获取参数
        String type = getParam(params, "type");
        String typeId = getParam(params, "typeId");
        if (body != null) {
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                //查询字典类型
                DictType dictType = null;
                if (StringUtils.isNotEmpty(type)) {
                    dictType = dictTypeService.findUniqueByProperty("type", type);
                } else if (StringUtils.isNotEmpty(typeId)) {
                    dictType = dictTypeService.findUniqueByProperty("id", typeId);
                }
                if (dictType != null && StringUtils.isNotEmpty(dictType.getId())) {
                    DictValue dictValue = new DictValue();
                    dictValue.setDictType(dictType);

                    List<DictValue> dictValueList = dictTypeService.findValueList(dictValue);
                    if (dictValueList != null && dictValueList.size() > 0) {
                        for (int i = 0; i < dictValueList.size(); i++) {
                            loopVars[0] = new BeanModel(dictValueList.get(i), new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
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

}
