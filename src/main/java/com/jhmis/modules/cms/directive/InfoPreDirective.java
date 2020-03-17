package com.jhmis.modules.cms.directive;

import com.jhmis.common.config.Global;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.InfoService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * 上一篇文章标签
 *
 * @author lydia
 * @date 2019/10/08 11:12
 */
@FreemarkerComponent("infoPreList")
public class InfoPreDirective extends BaseDirective {
    @Autowired
    private InfoService infoService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        //获取栏目信息
        String categoryId = getParam(params, "categoryId");
        //获取信息ID
        String infoId = getParam(params, "infoId");
        //是否手机
        String ismobile = getParam(params, "ismobile");
        //标题长度
        int titleLen = getParamInt(params, "titleLen", 0);
        //标题超过长度的后缀
        String titleSuffix = getParam(params, "titleSuffix", "");
        //是否按点击热度查询
        String hot = getParam(params, "hot");
        //日期格式
        String dateFormat = getParam(params, "dateFormat");
        //栏目页面标识
        String categoryPagemark = getParam(params, "categoryPagemark");
        int beginnum = getParamInt(params, "beginnum", 0);
        //排序
        String order = getParam(params, "order", "1");

        Writer out = env.getOut();
        if (body != null) {
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                Info currInfo = null;
                if (StringUtils.isNotEmpty(infoId)) {
                    currInfo = infoService.findUniqueByProperty("id", infoId);
                    //查询信息
                    Info info = new Info();
                    info.setDelFlag(Global.NO);
                    info.setCategoryId(currInfo.getCategoryId());
                    //TODO 手机
                    if ("1".equals(ismobile)) {

                    }
                    // 按热度排序
                    if ("1".equals(hot)) {
                        if (currInfo != null) {
                            info.setInfoStartHits(currInfo.getHits());
                            info.setOrderBySql(" a.hits ,a.create_date desc");

                        }
                    } else {
                        if ("1".equals(order)) {
                            //固顶有效并降序,发布时间降序(默认)
                            if (currInfo != null) {
                                info.setOrderBySql(" a.create_date,a.is_top ");
                                info.setInfostarttimeNoeq(currInfo.getCreateDate());
                            }
                        } else if ("2".equals(order)) {
                            //固顶有效并降序,发布时间升序
                            info.setOrderBySql(" a.create_date desc,a.is_top");
                            if (currInfo != null) {
                                info.setInfoendtimeNoeq(currInfo.getCreateDate());
                            }
                        } else if ("3".equals(order)) {
                            //发布时间倒序
                            info.setOrderBySql(" a.create_date");
                            if (currInfo != null) {
                                info.setInfostarttimeNoeq(currInfo.getCreateDate());
                            }
                        } else if ("4".equals(order)) {
                            //发布时间升序
                            info.setOrderBySql("  a.create_date desc ");
                            if (currInfo != null) {
                                info.setInfoendtimeNoeq(currInfo.getCreateDate());
                            }
                        }
                    }
                    List<Info> infoList = infoService.findList(info);
                    if (infoList.size() > 0) {
                        infoList.get(0).setTitleSuffix(titleSuffix);
                        if (titleLen > 0) {
                            infoList.get(0).setShortTitleLen(titleLen);
                        }
                        if (dateFormat.trim().length() > 0) {
                            infoList.get(0).setDateFormat(dateFormat);
                        }
                        //list 查询中没有查询中内容信息
                        Info infoN = infoService.findUniqueByProperty("id",infoList.get(0).getId());
                        loopVars[0] = new BeanModel(infoN, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                        body.render(env.getOut());
                    }

                }
            }
        }
    }
}
