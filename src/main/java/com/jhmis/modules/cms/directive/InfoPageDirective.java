package com.jhmis.modules.cms.directive;

import com.google.common.collect.Lists;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.cms.utils.FreemarkerPager;
import freemarker.core.Environment;
import freemarker.ext.beans.ArrayModel;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lydia
 * @date 2019/10/22 16:28
 */
@FreemarkerComponent("infoPage")
public class InfoPageDirective extends BaseDirective {
    @Autowired
    private InfoService infoService;
    @Autowired
    private CategoryService categoryService;

    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        //栏目id
        String categoryId = getParam(params, "categoryId");
        String categoryParId = getParam(params, "categoryParId");
        //显示数量
        int num = getParamInt(params, "num", 10);
        int beginnum = getParamInt(params, "beginnum", 0);
        //排序
        String order = getParam(params, "order", "1");
        //标题长度
        int titleLen = getParamInt(params, "titleLen", 0);
        String titleSuffix = getParam(params, "titleSuffix", "");
        //几天内为最新
        int newdays = getParamInt(params, "newdays", 0);
        //是否热点
        String isHot = getParam(params, "isHot", "");
        //是否置顶
        String isTop = getParam(params, "isTop", "");
        String isRecommend = getParam(params, "isRecommend", "");
        //是否按点击热度查询
        String hot = getParam(params, "hot");
        //日期格式
        String dateFormat = getParam(params, "dateFormat");
        //栏目页面标识
        String categoryPagemark = getParam(params, "categoryPagemark");
        String categoryParPagemark = getParam(params, "categoryParPagemark");
        //新闻分类
        String family = getParam(params, "family");//新闻行业
        String fieldParam=getParam(params, "fieldParam");
        String familyLevel2=getParam(params, "familyLevel2");//产品讲坛
        String familyLevel3=getParam(params, "familyLevel3");//产品经
        //是否只提取带图片的信息
        String img = getParam(params, "img");
        //是否只提取允许移动app访问的数据 1是
        String ismobile = getParam(params, "ismobile");
        //当前第几页
        int page = getParamInt(params, "page", 1);
        //最多显示页数
        int pagenum = getParamInt(params, "pagenum", 0);
        if (body != null) {
            Category category = null;
            Info info = new Info();
            //栏目信息
            if (StringUtils.isNotEmpty(categoryId)) {
                category = categoryService.findUniqueByProperty("id", categoryId);
            } else if (StringUtils.isNotEmpty(categoryPagemark)) {
                category = categoryService.findUniqueByProperty("category_mark", categoryPagemark);
            }
            if (category != null && StringUtils.isNotEmpty(category.getId())) {
                info.setCategoryId(category.getId());
            }
            //是否置顶
            if (StringUtils.isNotEmpty(isTop)) {
                info.setIsTop(isTop);
            }
            //是否热点
            if (StringUtils.isNotEmpty(isHot)) {
                info.setIsHot(isHot);
            }
            //是否推荐
            if (StringUtils.isNotEmpty(isRecommend)) {
                info.setIsRecommend(isRecommend);
            }
            //分类新闻行业
            if (StringUtils.isNotEmpty(family)) {
                info.setFamily(family);
            }
            //产品讲坛 分类
            if (StringUtils.isNotEmpty(familyLevel2)) {
                info.setFamilyLevel2(familyLevel2);
            }
            if (StringUtils.isNotEmpty(familyLevel3)) {
                info.setFamilyLevel3(familyLevel3);
            }
            //传递自定义字段参数
            if (StringUtils.isNotEmpty(fieldParam)) {
                String paramArr[]=fieldParam.split(",");
                if (paramArr!=null && paramArr.length>0) {
                    Map<String, String> fieldValues=new HashMap<String, String>();
                    for (String string2 : paramArr) {
                        if (StringUtils.isNotEmpty(string2) && env.getDataModel().get(string2)!=null) {
                            if(string2.equals("f_industry")) {
                               info.setIndustry(env.getDataModel().get(string2).toString());
                            }
                            if(string2.equals("f_product")) {
                                info.setCaseProducts(env.getDataModel().get(string2).toString());
                            }
                        }
                    }
                }
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
            if (newdays > 0) {
                info.setNewDays(newdays);
            }
            String orderSql = "";
            if ("1".equals(hot)) {
                info.setIsHot(hot);
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
            Page pageN = new Page();
            pageN.setPageNo(page);
            pageN.setPageSize(num);
            Page<Info> pageInfoList = infoService.findPage(pageN,info);
            List<Info> infoList = pageInfoList.getList();
            if (infoList != null && infoList.size() > 0) {
                for (int i = 0; i < infoList.size(); i++) {
                    infoList.get(i).setTitleSuffix(titleSuffix);
                    if (titleLen > 0) {
                        infoList.get(i).setShortTitleLen(titleLen);
                    }
                    if (dateFormat.trim().length() > 0) {
                        infoList.get(i).setDateFormat(dateFormat);

                    }

                }
            }
            FreemarkerPager pager=new FreemarkerPager();
            pager.setCurrPage(page);
            pager.setTotalCount(pageInfoList.getTotalPage());
            pager.setPageSize(num);
            pager.setUrl("index");
            //如果总页数大于最多显示页数，则设置总页数为最多显示页数
            if (pagenum > 0 && pagenum < pageInfoList.getPageSize()) {
                pager.setTotalPage(pagenum);
            }
            //如果有下一页，则输入下一页标识
            if (pager.getTotalPage() > page && !"1".equals(ismobile)) {
                env.getOut().write(CategoryService.HASNEXTPAGE);
            }
            loopVars[0] = new ArrayModel(infoList.toArray(), new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
            if (loopVars.length > 1) {
                loopVars[1] = new BeanModel(pager, new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
            }
            body.render(env.getOut());
        }
    }
}
