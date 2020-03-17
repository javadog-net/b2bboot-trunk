package com.jhmis.modules.cms.service;

import com.jhmis.common.utils.FileUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.entity.CmsModel;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.mapper.CmsModelMapper;
import com.jhmis.modules.cms.utils.CmsEnum;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.FreeMarkerUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.GoodsCategoryService;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lydia
 * @date 2019/9/25 16:56
 */
@Service
public class HtmlService {
    @Autowired
    private CmsModelMapper cmsModelMapper;
    @Autowired
    private CommentService commentService;
    @Value("${cms.static.path}")
    private String staticPath;
    @Value("${cms.templates.path}")
    protected String templatePath;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;
    protected Logger logger = LoggerFactory.getLogger(HtmlService.class);

    /**
     * 生成首页html
     *
     * @throws IOException
     * @throws TemplateException
     */
    public void htmlIndex(Map<String, Object> data) throws IOException, TemplateException {
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        String templateName = CmsEnum.INDEX_TEMPLATE.getCode();
        if (cmsConfig != null) {
            String htmlPath = staticPath + Htmlpath.htmlIndexPath(cmsConfig);
            String templateWholePath = CmsUtils.getTemplateWholePath(templatePath);
            FreeMarkerUtils.createHTML(data, templateWholePath, templateName, htmlPath + "index.html");
        }
    }

    /**
     * 生成栏目页
     *
     * @param category
     * @param page
     */
    public void htmlCategory(Map<String, Object> data, Category category, int page) throws IOException, TemplateException {
        //模板位置
        String templetName = "";
        if (category != null && StringUtils.isNotEmpty(category.getCategoryTemplate()) && category.getCategoryTemplate().trim().length() > 0) {
            templetName = category.getCategoryTemplate().trim();
        }
        if (StringUtils.isNotEmpty(templetName) && StringUtils.isNotEmpty(category.getCategoryMark())) {
            String templateWholePath = CmsUtils.getTemplateWholePath(templatePath);
            //生成静态页面
            data.put("currCategory", category);
            CmsConfig cmsConfig = CmsUtils.getCmsConfig();
            FreeMarkerUtils.createHTML(data, templateWholePath, templetName, staticPath + Htmlpath.htmlCmsCategoryPath(cmsConfig, category) + "index.html");
        }
    }

    /**
     * 把静态化栏目加入对列
     *
     * @param categoryList
     * @param data
     */
    public void htmlCategoryTask(List<Category> categoryList, Map<String, Object> data) throws IOException, TemplateException {
        //模板位置
        String templetName = "";
        for (Category ca : categoryList) {
            if (StringUtils.isNotEmpty(ca.getCategoryMark())) {
                data.put("currCategory", ca);
                ca.setData(data);
                if (ca.getCategoryTemplate() != null && ca.getCategoryTemplate().trim().length() > 0) {
                    templetName = ca.getCategoryTemplate().trim();
                }
                if (StringUtils.isNotEmpty(ca.getCategoryTemplate())) {
                    String templateWholePath = CmsUtils.getTemplateWholePath(templatePath);
                    CmsConfig cmsConfig = CmsUtils.getCmsConfig();
                    FreeMarkerUtils.createHTML(ca.getData(), templateWholePath, templetName, staticPath + Htmlpath.htmlCmsCategoryPath(cmsConfig, ca) + "index.html");

                }
//                //指令存入队列
//                Task<Category> task = new Task<>();
//                task.setTaskType(TaskType.CATEGORY_HTML);
//                task.setEntity(ca);
//                TaskUtils.put(task);
            }
        }
    }

    /**
     * 对列中使用的方法
     * 生成栏目页
     *
     * @param category
     */
    public void htmlCategoryHandler(Category category) throws IOException, TemplateException {
        //模板位置
        String templetName = "";
        if (category.getCategoryTemplate() != null && category.getCategoryTemplate().trim().length() > 0) {
            templetName = category.getCategoryTemplate().trim();
        }
        String templateWholePath = CmsUtils.getTemplateWholePath(templatePath);
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        FreeMarkerUtils.createHTML(category.getData(), templateWholePath, templetName, staticPath + Htmlpath.htmlCmsCategoryPath(cmsConfig, category) + "index.html");

    }

    /**
     * 信息页静态化
     *
     * @param data
     * @param info
     * @param page
     */
    public void htmlInfo(Map<String, Object> data, Category category, Info info, int page) throws IOException, TemplateException {
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        //查询模型信息
        CmsModel cmsModel = null;
        if (category != null && StringUtils.isNotEmpty(category.getModelId())) {
            cmsModel = cmsModelMapper.findUniqueByProperty("id", category.getModelId());
        }
        //判断内容模板
        //TODO  是否设置默认的内容模板
        String templetName = "content_info.html";
        //如果内容页设置了模板，则取内容中设置的模板
        if (StringUtils.isNotEmpty(info.getContentTemplate())) {
            templetName = info.getContentTemplate();
            //内容页没设置，则取栏目页设置的内容模板
        } else if (category != null && StringUtils.isNotEmpty(category.getContentTemplate())) {
            templetName = category.getContentTemplate();
            //如果栏目页没设置模板信息，则取模型中设置的内容模板
        } else if (cmsModel != null && StringUtils.isNotEmpty(cmsModel.getContentTemplate())) {
            templetName = cmsModel.getContentTemplate();
        }
        String templateWholePath = CmsUtils.getTemplateWholePath(templatePath);
        data.put("currInfo", info);
        data.put("currCategory", category);
        //回更htmlPath
        info.setHtmlpath(Htmlpath.htmlInfoPath(cmsConfig, category, info));
        infoService.updateHitsById(info);
        FreeMarkerUtils.createHTML(data, templateWholePath, templetName, staticPath + Htmlpath.htmlInfoPath(cmsConfig, category, info));

    }

    /**
     * 把静态化栏目加入对列
     *
     * @param infoList
     * @param data
     */
    public void htmlInfoTask(List<Info> infoList, Map<String, Object> data) throws IOException, TemplateException {
        for (Info info : infoList) {
            data.put("currInfo", info);
            data.put("currCategory", info.getCategory());
            info.setData(data);
            htmlInfoHandler(info);
//            //指令存入队列
//            Task<Info> task = new Task<>();
//            task.setTaskType(TaskType.INFO_HTML);
//            task.setEntity(info1);
//            TaskUtils.put(task);
        }
    }

    /**
     * 对列中使用的方法
     * 信息页静态化
     *
     * @param info
     */
    public void htmlInfoHandler(Info info) throws IOException, TemplateException {
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        //查询模型信息
        CmsModel cmsModel = null;
        Category category = info.getCategory();
        if (category != null && StringUtils.isNotEmpty(category.getModelId())) {
            cmsModel = cmsModelMapper.findUniqueByProperty("id", category.getModelId());
        }
        //判断内容模板
        //TODO  是否设置默认的内容模板
        String templetName = "content_info.html";
        //如果内容页设置了模板，则取内容中设置的模板
        if (StringUtils.isNotEmpty(info.getContentTemplate())) {
            templetName = info.getContentTemplate();
            //内容页没设置，则取栏目页设置的内容模板
        } else if (category != null && StringUtils.isNotEmpty(category.getContentTemplate())) {
            templetName = category.getContentTemplate();
            //如果栏目页没设置模板信息，则取模型中设置的内容模板
        } else if (cmsModel != null && StringUtils.isNotEmpty(cmsModel.getContentTemplate())) {
            templetName = cmsModel.getContentTemplate();
        }
        String templateWholePath = CmsUtils.getTemplateWholePath(templatePath);
        Map<String, Object> data = info.getData();
        data.put("currInfo", info);
        data.put("currCategory", category);
        info.setHtmlpath(Htmlpath.htmlInfoPath(cmsConfig, category, info));
        infoService.updateHitsById(info);
        FreeMarkerUtils.createHTML(data, templateWholePath, templetName, staticPath + Htmlpath.htmlInfoPath(cmsConfig, category, info));
    }


    /**
     * @param request
     * @param category
     * @param goods
     * @param storeGoods
     * @param templatePath
     * @param page
     * @return void
     * @throws IOException
     * @throws TemplateException
     * @Title: htmlGoods
     * @Description: TODO  商品详情静态化
     * @author tc
     * @date 2019年10月23日下午2:45:43
     */
    public void htmlGoods(HttpServletRequest request, GoodsCategory category, Goods goods,
                          StoreGoods storeGoods, String templatePath, int page, String htmlPath) throws IOException, TemplateException {
        //判断内容模板
        //TODO  是否设置默认的内容模板
        String templetName = "product_goods.html";
        /*
        //获取模版的顺序 。单独商品指定(由后台指定)-->栏目模版-->设置栏目时指定的商品模版
        if (category != null && StringUtils.isNotEmpty(category.getClassTemplate())) {
            templetName = category.getClassTemplate();
        }
        if (category != null && StringUtils.isNotEmpty(category.getClassTemplate())) {
            templetName = category.getClassTemplate();
        }
        if (goods != null && StringUtils.isNotEmpty(goods.getGoodsTemplate())) {
            templetName = goods.getGoodsTemplate();
        }
        if (StringUtils.isBlank(templetName)) {
            templetName = "product_goods.html";
        }*/
        if (StringUtils.isNotBlank(goods.getTitle())) {
            storeGoods.setTitle(goods.getTitle());
        } else {
            storeGoods.setTitle(goods.getName());
        }
        if (StringUtils.isNotBlank(goods.getDes())) {
            storeGoods.setDes(goods.getDes());
        } else {
            storeGoods.setTitle(goods.getName());
        }
        if (StringUtils.isNotBlank(goods.getKeyword())) {
            storeGoods.setKeyword(goods.getKeyword());
        } else {
            storeGoods.setTitle(goods.getName());
        }

        //生成静态页
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("currCategory", category);
        data.put("storeGoods", storeGoods);
        data.put("pagenum", 0);
        data.put("contextPath", BaseController.getContextPathNoReplaceStr(request.getContextPath()) + "/");
        data.put("contextPathNo", BaseController.getContextPathNoReplaceStr(request.getContextPath()));
        FreeMarkerUtils.createHTML(data, templatePath, templetName, staticPath + htmlPath);
    }


    /**
     * @param data
     * @param goodsCategory
     * @param templatePath
     * @param page
     * @return void
     * @throws IOException
     * @throws TemplateException
     * @Title: htmlGoodsCategory
     * @Description: TODO 商品栏目静态化
     * @author tc
     * @date 2019年10月24日上午11:00:18
     */
    public void htmlGoodsCategory(Map<String, Object> data, GoodsCategory goodsCategory, String templatePath, int page) throws IOException, TemplateException {
        //模板位置
        String templetName = "product_category.html";
        if (goodsCategory.getClassTemplate() != null && goodsCategory.getClassTemplate().trim().length() > 0) {
            templetName = goodsCategory.getClassTemplate().trim();
        }
        //生成静态页面
        data.put("currGoodsCategory", goodsCategory);
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        String htmlpath=Htmlpath.htmlGoodsCategoryPath(cmsConfig, goodsCategory);
        goodsCategory.setPageUrl(htmlpath);
        goodsCategoryService.updatecategoryurl(goodsCategory);
        FreeMarkerUtils.createHTML(data, templatePath, templetName, staticPath + htmlpath + "index.html");
    }

    public void htmlGoodsCategoryPar(Map<String, Object> data, String templatePath, int page) throws IOException, TemplateException {

        String templetName = "par_product_category.html";
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        FreeMarkerUtils.createHTML(data, templatePath, templetName, staticPath + "par_product/par_product_category.html");
    }

    /**
     * 全站静态化
     *
     * @param data
     */
    public void htmlAll(HttpServletRequest request, Map<String, Object> data) {
        //静态化首页
        try {
            htmlIndex(data);
            logger.info("首页静态化完成！");
            //栏目页静态化
            List<Category> categoryList = categoryService.findByModelId(null);
            htmlCategoryTask(categoryList, data);
            logger.info("栏目页静态化完成！");
            //信息页静态化
            Info info = new Info();
            List<Info> infoList = infoService.findListWithBlob(info);
            htmlInfoTask(infoList, data);
            logger.info("信息页静态化完成！");
            //商品分类静态化
//            List<GoodsCategory> goodsCategoryList =
            // 商品页静态化
            goodsCategoryService.goodsHtml(request, templatePath);
            logger.info("商品页静态化完成！");
        } catch (IOException e) {
            logger.error("全站静态化失败："+e.getMessage());
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.error("全站静态化失败："+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 全站静态化
     *
     * @param data
     */
    public void htmlAllDo(ServletContext context, Map<String, Object> data) {
        //静态化首页
        try {
            htmlIndex(data);
            logger.info("首页静态化完成！");
            //栏目页静态化
            List<Category> categoryList = categoryService.findByModelId(null);
            htmlCategoryTask(categoryList, data);
            logger.info("栏目页静态化完成！");
            //信息页静态化
            Info info = new Info();
            List<Info> infoList = infoService.findListWithBlob(info);
            htmlInfoTask(infoList, data);
            logger.info("信息页静态化完成！");
            //商品分类静态化
//            List<GoodsCategory> goodsCategoryList =
//            // 商品页静态化
//            goodsCategoryService.goodsHtml(request, templatePath);
//            logger.info("商品页静态化完成！");
        } catch (IOException e) {
            logger.error("全站静态化失败："+e.getMessage());
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.error("全站静态化失败："+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 栏目页静态化每一页
     * @throws TemplateException
     * @throws IOException
     * @throws TemplateException
     * @throws IOException
     */
    public void htmlPage(Category category, ServletContext context, String templetPath, int page) throws IOException, TemplateException{
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        String templeWholetPath = "";
        if (cmsConfig != null && category!=null && StringUtils.isNotEmpty(category.getCategoryTemplate().trim()) && StringUtils.isNotEmpty(category.getId())) {
            if (category.getMaxpage()==0 || (category.getMaxpage()>0 && category.getMaxpage()>=page)) {
                templeWholetPath = templetPath + CmsUtils.getTemplateStyle();
                //生成静态页面
                Map<String,Object> data=new HashMap<String,Object>();
                category = categoryService.findUniqueByProperty("id",category.getId());
                data.put("currCategory", category);
                data.put("page", page);
                if (category.getMaxpage()>0) {
                    data.put("pagenum", category.getMaxpage());
                }
                data.put("contextPath", BaseController.getContextPathNoReplaceStr(context.getContextPath())+"/");
                data.put("contextPathNo",  BaseController.getContextPathNoReplaceStr(context.getContextPath()));
                data.put("webpath", context.getRealPath("/"));
                String htmlRootPath=staticPath+Htmlpath.htmlCmsCategoryPath(cmsConfig, category);
                //判断栏目文件夹是否存在
                File categoyHtmlFolder=new File(htmlRootPath);
                if (!categoyHtmlFolder.exists()) {
                    categoyHtmlFolder.mkdirs();
                }
                FreeMarkerUtils.createHTML(data, templeWholetPath, category.getCategoryTemplate(),htmlRootPath+"index"+(page>1?"_"+(page-1):"")+".html");
                String content = FileUtils.readFile(htmlRootPath+"index"+(page>1?"_"+(page-1):"")+".html");
                //如果内容里有<!--hasNextPage-->字符串则需要生成下一页
                if (content.indexOf(CategoryService.HASNEXTPAGE)>-1) {
                    htmlPage(category, context, templetPath, page+1);
                }
            }
        }
    }
}
