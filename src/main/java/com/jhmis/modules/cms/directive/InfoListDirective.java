package com.jhmis.modules.cms.directive;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.InfoService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.SimpleNumber;
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
 * 信息列表标签
 * @author lydia
 * @date 2019/10/10 11:08
 */
@FreemarkerComponent("infoList")
public class InfoListDirective  extends BaseDirective {
    @Autowired
    private InfoService infoService;
    @Autowired
    private CategoryService categoryService;
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)throws TemplateException, IOException {
        //栏目id
        String categoryId=getParam(params, "categoryId");
        String categoryParId=getParam(params, "categoryParId");
        String familyLevel2=getParam(params, "familyLevel2");//产品讲坛
        String familyLevel3=getParam(params, "familyLevel3");//产品经
        String family=getParam(params, "family");//新闻行业
        //显示数量
        int num=getParamInt(params, "num", 10);
        int beginnum=getParamInt(params, "beginnum", 0);
        //排序
        String order=getParam(params, "order","1");
        //标题长度
        int titleLen=getParamInt(params, "titleLen",0);
        String titleSuffix=getParam(params, "titleSuffix","");
        //几天内为最新
        int newdays=getParamInt(params, "newdays",0);
        //是否按点击热度查询
        String hot=getParam(params, "hot");
        //是否置顶
        String isTop = getParam(params, "isTop", "");
        //日期格式
        String dateFormat=getParam(params, "dateFormat");
        //栏目页面标识
        String categoryPagemark=getParam(params, "categoryPagemark");
        String categoryParPagemark=getParam(params, "categoryParPagemark");
        //是否只提取带图片的信息
        String img=getParam(params, "img");
        //是否只提取允许移动app访问的数据 1是
        String ismobile=getParam(params, "ismobile");
        Writer out = env.getOut();
        if (body != null) {
            Category category = null;
            Info info = new Info();
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                //栏目信息
                if (StringUtils.isNotEmpty(categoryId)) {
                    category = categoryService.findUniqueByProperty("id", categoryId);
                } else if (StringUtils.isNotEmpty(categoryPagemark)) {
                    category = categoryService.findUniqueByProperty("category_mark", categoryPagemark);
                }
                if (category != null && StringUtils.isNotEmpty(category.getId())) {
                    info.setCategoryId(category.getId());
                }

                //父级栏目
                List<String> categoryIdList = Lists.newArrayList();
                List<Category> categoryList = null;
                if (StringUtils.isNotEmpty(categoryParId)) {
                    categoryList = categoryService.findByParentIdsLike(categoryParId);
                } else if (StringUtils.isNotEmpty(categoryParPagemark.trim())) {
                    Category ca = categoryService.findUniqueByProperty("category_mark", categoryParPagemark);
                    if (ca != null) {
                        categoryList = categoryService.findByParentIdsLike(ca.getId());
                    }
                }
                if (categoryIdList != null && categoryIdList.size() > 0) {
                    categoryList.forEach(category1 -> {
                        categoryIdList.add(category1.getId());
                    });
                    info.setCategoryIdList(categoryIdList);
                }
                if(newdays > 0){
                    info.setNewDays(newdays);
                }
                //是否置顶
                if (StringUtils.isNotEmpty(isTop)) {
                    info.setIsTop(isTop);
                }
                if (StringUtils.isNotEmpty(familyLevel2)) {
                    info.setFamilyLevel2(familyLevel2);
                }
                if (StringUtils.isNotEmpty(familyLevel3)) {
                    info.setFamilyLevel3(familyLevel3);
                }
                if (StringUtils.isNotEmpty(family)) {
                    info.setFamily(family);
                }
                String orderSql = "";
                if ("1".equals(hot)) { info.setIsHot(hot);

                  orderSql = " a.hits desc,a.create_date desc ";
                } else {
                    if ("1".equals(order)) {
                        //固顶有效并降序,发布时间降序(默认)
                        orderSql = " a.is_top desc,a.create_date desc";
                    } else if ("2".equals(order)) {
                        //固顶有效并降序,发布时间升序
                        orderSql = " a.is_top desc,a.create_date";
                    } else if ("3".equals(order)) {
                        //发布时间倒序
                        orderSql = " a.create_date desc";
                    } else if ("4".equals(order)) {
                        //发布时间升序
                        orderSql = " a.create_date";
                    }
                }
                if (StringUtils.isNotEmpty(orderSql)) {
                    info.setOrderBySql(orderSql);
                }
                info.setDelFlag(Global.NO);
                Page page = new Page();
                page.setPageNo(1);
                page.setPageSize(beginnum + num);
                Page<Info> pageInfoList = infoService.findPage(page, info);
                if (pageInfoList != null) {
                    List<Info> infoList = pageInfoList.getList();
                    if (infoList != null && infoList.size() > 0) {
                        for (int i = 0; i < infoList.size(); i++) {
                            if (i >= beginnum) {
                                infoList.get(i).setTitleSuffix(titleSuffix);
                                if (titleLen > 0) {
                                    infoList.get(i).setShortTitleLen(titleLen);
                                }
                                if (dateFormat.trim().length() > 0) {
                                    infoList.get(i).setDateFormat(dateFormat);
                                }
                                loopVars[0] = new BeanModel(infoList.get(i), new BeansWrapper());
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
}
