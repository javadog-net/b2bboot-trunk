package com.jhmis.modules.cms.directive;

import com.haier.http.client.utils.DateUtil;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseDirective;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.cms.utils.FreemarkerPager;
import com.jhmis.modules.index.IndexBean;
import com.jhmis.modules.index.IndexDocument;
import com.jhmis.modules.index.IndexUtil;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import com.jhmis.modules.shop.service.GoodsService;
import com.jhmis.modules.shop.service.StoreGoodsService;
import freemarker.core.Environment;
import freemarker.ext.beans.ArrayModel;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.lucene.document.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @author lydia
 * @date 2019/12/2 15:37
 */
@FreemarkerComponent("indexPage")
public class IndexPageDirective extends BaseDirective {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private StoreGoodsService storeGoodsService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private InfoService infoService;
    protected Logger logger = LoggerFactory.getLogger(IndexListDirective.class);
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        //信息栏目ID
        String categoryId = getParam(params, "categoryId");
        //商品分类Id
        String shopCategoryId = getParam(params, "shopCategoryId");
        //对象类型,info表示信息,goods表示商品
        String type = getParam(params, "type");
        //显示数量
        int num = getParamInt(params, "num", 10);
        int beginnum = getParamInt(params, "beginnum", 0);
        //标题长度
        int titleLen = getParamInt(params, "titleLen", 0);
        String titleSuffix = getParam(params, "titleSuffix", "");
        //搜索关键词
        String key = getParam(params, "key");
        //日期格式
        String dateFormat = getParam(params, "dateFormat");
        //栏目页面标识
        String categoryPagemark = getParam(params, "categoryPagemark");
        if (StringUtils.isEmpty(categoryId) && StringUtils.isNotEmpty(categoryPagemark)) {
            Category category = categoryService.findUniqueByProperty("category_mark", categoryPagemark);
            if (category != null && StringUtils.isNotEmpty(category.getId())) {
                categoryId = category.getId();
            }
        }
        //商品分类页面标识
        String shopCategoryPagemark = getParam(params, "shopCategoryPagemark");
        if (StringUtils.isEmpty(shopCategoryId) && StringUtils.isNotEmpty(shopCategoryPagemark)) {
            GoodsCategory goodsCategory = goodsCategoryService.findUniqueByProperty("page_mark", goodsCategoryService);
            if (goodsCategory != null && StringUtils.isNotEmpty(goodsCategory.getId())) {
                shopCategoryId = goodsCategory.getId();
            }
        }
        //开始时间
        String startdate = getParam(params, "startdate");
        Date startDate = null;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(startdate)) {
            try {
                startDate = DateUtil.parse(startdate, "yyyy-MM-dd");
            } catch (Exception e) {
                logger.error("IndexListDirective-startDate-" + e.getMessage());
            }
        }
        //结束时间
        String enddate = getParam(params, "enddate");
        Date endDate = null;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(enddate)) {
            try {
                endDate = DateUtil.parse(enddate, "yyyy-MM-dd");
            } catch (Exception e) {
                logger.error("IndexListDirective-endDate-" + e.getMessage());
            }
        }

        //当前第几页
        int page = getParamInt(params, "page", 1);
        //页面地址
        String action = getParam(params, "action");

        Writer out = env.getOut();
        if (body != null) {
            //设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                try {
                    //查询信息
                    FreemarkerPager pager = null;
                    try {
                        if(type.equals("info")){
                            pager = IndexUtil.searchPage(IndexUtil.INDEX_PATH+"/cmsinfo", type, key,categoryId,shopCategoryId, startDate, endDate, page, num);
                        }else if(type.equals("goods")){
                            pager = IndexUtil.searchPage(IndexUtil.INDEX_PATH+"/product", type, key,categoryId,shopCategoryId, startDate, endDate, page, num);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (pager != null) {
                        List<Document> docList = (List<Document>) pager.getList();
                        List<IndexBean> beanList = new ArrayList<IndexBean>();
                        if (docList != null && docList.size() > 0) {
                            Document document;
                            String id, dtype;
                            Info info = null;
                            StoreGoods storeGoods = null;
                            for (int i = 0; i < docList.size(); i++) {
                                document = docList.get(i);
                                id = document.getField(IndexDocument.ID).stringValue();
                                dtype = document.getField(IndexDocument.TYPE).stringValue();
                                IndexBean indexBean = new IndexBean();
                                //信息类索引
                                if ("info".equals(type)) {
                                    info = infoService.findUniqueByProperty("id",id);
                                    if (info != null) {
                                        info.setTitleSuffix(titleSuffix);
                                        if (titleLen > 0) {
                                            info.setShortTitleLen(titleLen);
                                        }
                                        if (dateFormat.trim().length() > 0) {
                                            info.setDateFormat(dateFormat);
                                        }
                                        info.setContextPath(env.getDataModel().get("contextPathNo").toString());
                                        indexBean.setAddtime(info.getCreateDateStr());
                                        indexBean.setCategoryId(info.getCategoryId());
                                        indexBean.setDescription(info.getDescription());
                                        indexBean.setId(id);
                                        indexBean.setPageUrl(info.getPageUrl());
                                        if(info.getCategory() != null){
                                            indexBean.setCategoryName(info.getCategory().getName());
                                        }else{
                                            indexBean.setCategoryName("");
                                        }

//                                        indexBean.setTags(info.getTags());
                                        indexBean.setTitle(info.getShortTitle());
                                        indexBean.setType("info");
                                        indexBean.setObject(info);
                                        beanList.add(indexBean);
                                    }
                                }
                                //商品类索引
                                else if ("goods".equals(type)) {
                                    {
                                        storeGoods = storeGoodsService.get(id);
                                        Goods goods = null;
                                        if(storeGoods != null && StringUtils.isNotEmpty(storeGoods.getCode())){
                                            goods = goodsService.findByCode(storeGoods.getCode());
                                        }
                                        if (storeGoods!=null) {
//                                            storeGoods.setNameSuffix(titleSuffix);
//                                            if (titleLen>0) {
//                                                product.setShownameLen(titleLen);
//                                            }
                                            if (dateFormat.trim().length()>0) {
                                                storeGoods.setDateFormat(dateFormat);
                                            }
//                                            //设置sitepath
//                                            if (site!=null) {
//                                                product.setSitepath(env.getDataModel().get("contextPathNo").toString()
//                                                        +Htmlpath.site(site));
//                                                product.setMobileSitepath(Htmlpath.site(site));
//                                                product.setMobilepath(Htmlpath.mobile(site));
//                                                product.setContextPath(env.getDataModel().get("contextPathNo").toString());
//                                            }
                                            indexBean.setAddtime(storeGoods.getCreateDateStr());
                                            indexBean.setId(id);
                                            indexBean.setTags(storeGoods.getKeyword());
                                            indexBean.setType("goods");
                                            if(goods != null){
                                                indexBean.setDescription(goods.getDes());
                                                if(StringUtils.isNotEmpty(goods.getMainPicUrl())) {
                                                    if(goods.getMainPicUrl().indexOf("|")>0){
                                                        String arr[] = goods.getMainPicUrl().split("\\|");
                                                        indexBean.setMainImage(arr[0]);
                                                    }else{
                                                        indexBean.setMainImage(goods.getMainPicUrl());
                                                    }
                                                }
                                            }

                                            indexBean.setObject(storeGoods);
                                            beanList.add(indexBean);
                                        }
                                    }
                                }
                            }
                        }
                        if (action.trim().length() > 0) {
                            pager.setAction(action);
                        }
                        HashMap<String, String> paramMap = new HashMap<String, String>();
                        paramMap.put("templetPath", env.getDataModel().get("templetPath") != null ? env.getDataModel().get("templetPath").toString() : "");
                        paramMap.put("key", env.getDataModel().get("key") != null ? env.getDataModel().get("key").toString() : "");
                        paramMap.put("categoryId", env.getDataModel().get("categoryId") != null ? env.getDataModel().get("categoryId").toString() : "");
                        paramMap.put("categoryPagemark", env.getDataModel().get("categoryPagemark") != null ? env.getDataModel().get("categoryPagemark").toString() : "");
                        paramMap.put("startdate", env.getDataModel().get("startdate") != null ? env.getDataModel().get("startdate").toString() : "");
                        paramMap.put("enddate", env.getDataModel().get("enddate") != null ? env.getDataModel().get("enddate").toString() : "");
                        pager.setParams(paramMap);
                        loopVars[0] = new ArrayModel(beanList.toArray(), new BeansWrapper());
                        if (loopVars.length > 1) {
                            loopVars[1] = new BeanModel(pager, new BeansWrapper());
                        }
                        body.render(env.getOut());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
