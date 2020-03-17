package com.jhmis.modules.cms.directive;

import com.haier.http.client.utils.DateUtil;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.index.IndexBean;
import com.jhmis.modules.index.IndexDocument;
import com.jhmis.modules.index.IndexUtil;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.*;
import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lydia
 * categoryId	栏目id
 * shopCategoryId	商品分类id
 * type			对象类型,info表示信息,goods表示商品
 * num			显示数量
 * beginnum  	从第几条开始，0代表从头开始
 * titleLen		标题显示长度
 * titleSuffix	标题超过显示长度时加的后缀
 * dateFormat	日期格式
 * channelPagemark	栏目页面标识
 * categoryPagemark	商品分类页面标识
 * key			搜索关键词
 * startdate	开始时间  yyyy-MM-dd
 * enddate		结束时间  yyyy-MM-dd
 * 返回值
 * IndexBean	lucene对象
 * index		索引
<@indexPage key='${key!""}' num="1" action="${contextPath}templetPro.do" page='${page!1}'; beanList,pager>
<#list beanList as bean>
${bean.title}
</#list>
<br/>
${pager.formPageStr}
</@indexPage>
 */
@FreemarkerComponent("indexList")
public class IndexListDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private InfoService infoService;
    protected Logger logger = LoggerFactory.getLogger(IndexListDirective.class);
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
       //信息栏目ID
        String categoryId = getParam(params,"categoryId");
        //商品分类Id
        String shopCategoryId = getParam(params,"shopCategoryId");
        //对象类型,info表示信息,goods表示商品
        String type = getParam(params,"type");
        //显示数量
        int num=getParamInt(params, "num", 10);
        int beginnum=getParamInt(params, "beginnum", 0);
        //标题长度
        int titleLen=getParamInt(params, "titleLen",0);
        String titleSuffix=getParam(params, "titleSuffix","");
        //搜索关键词
        String key=getParam(params, "key");
        //日期格式
        String dateFormat=getParam(params, "dateFormat");
        //栏目页面标识
        String categoryPagemark=getParam(params, "categoryPagemark");
        if(StringUtils.isEmpty(categoryId) && StringUtils.isNotEmpty(categoryPagemark)){
           Category category =  categoryService.findUniqueByProperty("category_mark",categoryPagemark);
           if(category != null && StringUtils.isNotEmpty(category.getId())){
               categoryId = category.getId();
           }
        }
        //商品分类页面标识
        String shopCategoryPagemark = getParam(params,"shopCategoryPagemark");
        if(StringUtils.isEmpty(shopCategoryId) && StringUtils.isNotEmpty(shopCategoryPagemark)) {
            GoodsCategory goodsCategory = goodsCategoryService.findUniqueByProperty("page_mark", goodsCategoryService);
            if (goodsCategory != null && StringUtils.isNotEmpty(goodsCategory.getId())) {
                shopCategoryId = goodsCategory.getId();
            }
        }
            //开始时间
            String startdate=getParam(params, "startdate");
            Date startDate=null;
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(startdate)){
                try {
                    startDate= DateUtil.parse(startdate, "yyyy-MM-dd");
                } catch (Exception e) {
                    logger.error("IndexListDirective-startDate-"+e.getMessage());
                }
            }
            //结束时间
            String enddate=getParam(params, "enddate");
            Date endDate=null;
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(enddate)){
                try {
                    endDate=DateUtil.parse(enddate, "yyyy-MM-dd");
                } catch (Exception e) {
                    logger.error("IndexListDirective-endDate-"+e.getMessage());
                }
            }
        
            if (body!=null) {
                //设置循环变量
                if (loopVars!=null && loopVars.length>0) {
                    try {
                        //查询信息
                        List<Document> docList= null;
                        try {
                            docList= IndexUtil.searchList(IndexUtil.INDEX_PATH,type,key,categoryId,shopCategoryId, startDate, endDate, 0, beginnum+num);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                       
                        if (docList!=null && docList.size()>0) {
                            Document document;
                            String id,dtype;
                            Info info  = null;
                            StoreGoods storeGoods = null;
                            for (int i = 0; i < docList.size(); i++) {
                                if (i>=beginnum) {
                                    document=docList.get(i);
                                    id=document.getField(IndexDocument.ID).stringValue();
                                    dtype=document.getField(IndexDocument.TYPE).stringValue();
                                    IndexBean indexBean=new IndexBean();
                                    //信息类索引
                                    if ("info".equals(dtype)) {
                                        info=infoService.findUniqueByProperty("id",id);
                                        if (info!=null) {
                                            info.setTitleSuffix(titleSuffix);
                                            if (titleLen>0) {
                                                info.setShortTitleLen(titleLen);
                                            }
                                            if (dateFormat.trim().length()>0) {
                                                info.setDateFormat(dateFormat);
                                            }
//                                            if (site!=null) {
//                                                info.setSitepath(env.getDataModel().get("contextPathNo").toString()
//                                                        +Htmlpath.site(site));
//                                                info.setMobileSitepath(Htmlpath.site(site));
//                                                info.setMobilepath(Htmlpath.mobile(site));
//                                                info.setContextPath(env.getDataModel().get("contextPathNo").toString());
//                                            }
                                            indexBean.setAddtime(info.getCreateDateStr());
                                            indexBean.setCategoryId(info.getCategoryId());
                                            indexBean.setDescription(info.getDescription());
                                            indexBean.setId(id);
                                            indexBean.setPageUrl(info.getPageUrl());
//                                            IndexBean.setTags(info.getTags());
                                            indexBean.setTitle(info.getShortTitle());
                                            indexBean.setType("info");
                                            indexBean.setObject(info);
                                        }
                                    }
                                    //商品类索引
                                    else if ("goods".equals(dtype)) {
                                        storeGoods = storeGoodsService.get(id);
                                        if (storeGoods!=null) {
//                                            storeGoods.setNameSuffix(titleSuffix);
//                                            if (titleLen>0) {
//                                                product.setShownameLen(titleLen);
//                                            }
                                            if (dateFormat.trim().length()>0) {
                                                storeGoods.setDateFormat(dateFormat);
                                            }
                                            //设置
                                            CmsConfig cmsConfig = CmsUtils.getCmsConfig();
                                            if (cmsConfig!=null) {
//                                                storeGoods.setSitepath(env.getDataModel().get("contextPathNo").toString()
//                                                        + Htmlpath.site(site));
//                                                product.setMobileSitepath(Htmlpath.site(site));
//                                                product.setMobilepath(Htmlpath.mobile(site));
                                                storeGoods.setContextPath(env.getDataModel().get("contextPathNo").toString());
                                            }
                                            indexBean.setAddtime(storeGoods.getCreateDateStr());
                                            indexBean.setShopCategoryId(storeGoods.getCategoryId());
                                            indexBean.setId(id);
                                            indexBean.setPageUrl(storeGoods.getPageUrl());
                                            indexBean.setTags(storeGoods.getKeyword());
                                            indexBean.setTitle(storeGoods.getGoodsName());
                                            indexBean.setType("goods");
                                            indexBean.setObject(storeGoods);
                                        }
                                    }
                                    loopVars[0]=new BeanModel(indexBean,new BeansWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));
                                    if(loopVars.length>1){
                                        loopVars[1]=new SimpleNumber(i);
                                    }
                                    body.render(env.getOut());
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }
    }
}
