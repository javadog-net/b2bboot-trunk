package com.jhmis.modules.cms.web;

import com.google.common.collect.Lists;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.entity.Htmlquartz;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.service.HtmlquartzService;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.cms.utils.CmsEnum;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/cms")
public class HtmlController extends BaseController {
    @Autowired
    private HtmlService htmlService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private HtmlquartzService htmlquartzService;
    private List<Integer> hours;
    private List<Integer> mins;

    /**
     * 首页静态化确认页
     *
     * @param map
     * @param response
     * @return
     */
    @RequestMapping("/htmlIndexConfirm")
    public String htmlIndexConfirm(ModelMap map, HttpServletResponse response) {
        return "modules/cms/htmlIndexConfirm";
    }

    /**
     * 首页静态化
     *
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/htmlIndex")
    public AjaxJson htmlIndex(Model model, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        //获取cms配置中的风格
        String templateStyle = CmsUtils.getTemplateStyle();
        //获取模板文件路径（yml文件中的配置路径+模板风格）
        String templateFilePath = templatePath + templateStyle;
        File templateFile = new File(templateFilePath);
        if (!templateFile.exists()) {
            j.setSuccess(false);
            j.setMsg("模板所在文件夹不存在");
            return j;
        }
        //生成静态文件的路径
        String htmlPath = staticPath + Htmlpath.htmlIndexPath(CmsUtils.getCmsConfig());

        File htmlPathDir = new File(htmlPath);
        if (!htmlPathDir.exists()) {
            htmlPathDir.mkdirs();
        }
        //生成静态页面
        Map<String, Object> data = getData(request);
        try {
            htmlService.htmlIndex(data);
            j.setSuccess(true);
        } catch (IOException e) {
            logger.error("生成静态页面异常", e.getMessage());
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.error("生成静态页面异常", e.getMessage());
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 进入栏目静态化页面
     *
     * @param map
     * @param response
     * @return
     */
    @RequestMapping("/categoryHtml")
    public String categoryHtml(ModelMap map, HttpServletResponse response) {
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        if (cmsConfig != null) {
            List<Category> categoryList = Lists.newArrayList();
            List<Category> sourcelist = categoryService.findByModelId(null);
            Category.sortList(categoryList, sourcelist, "0");
            map.put("categoryList", categoryList);
        }
        return "modules/cms/categoryHtml";
    }

    /**
     * 栏目页静态化
     *
     * @param categoryIds
     * @param request
     * @return
     */
//    @RequestMapping("/categoryHtmlDo")
//    @ResponseBody
//    public AjaxJson categoryHtmlDo(String categoryIds, String createType, HttpServletRequest request) {
//        if (StringUtils.isEmpty(categoryIds) && StringUtils.isEmpty(createType)) {
//            return AjaxJson.fail("参数错误!");
//        }
//        try {
//            Category htmlCategory = new Category();
//            Map<String, Object> data = getData(request);
//            List<Category> categoryList = new ArrayList<>();
//            //静态化选中栏目
//            if (CmsEnum.HTML_SELECTED.getCode().equals(createType)) {
//                String[] idArr = categoryIds.split(",");
//                Category category = null;
//                for (int i = 0; i < idArr.length; i++) {
//                    category = categoryService.findUniqueByProperty("id", idArr[i]);
//
//                }
//                //静态化所有栏目
//            } else if (CmsEnum.HTML_ALL.getCode().equals(createType)) {
//                categoryList = categoryService.findByModelId(null);
//            }
//            //把栏目页放入对列
//            if (categoryList.size() > 0) {
//                htmlCategory.setData(data);
//                htmlCategory.setHtmlCategoryList(categoryList);
//                htmlService.htmlCategoryTask(htmlCategory);
//            }
//            //静态化首页
//            htmlService.htmlIndex(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//        return AjaxJson.ok("栏目页静态化成功！");
//
//    }

    /**
     * 栏目页静态化
     *
     * @param categoryIds
     * @param request
     * @return
     */
    @RequestMapping("/categoryHtmlDo")
    @ResponseBody
    public AjaxJson categoryHtmlDo(String[] categoryIds, String createType, HttpServletRequest request) {
        if (categoryIds == null && StringUtils.isEmpty(createType)) {
            return AjaxJson.fail("参数错误!");
        }
        try {
            Category htmlCategory = new Category();
            Map<String, Object> data = getData(request);
            List<Category> totalList = new ArrayList<>();
            //静态化选中栏目
            if (CmsEnum.HTML_SELECTED.getCode().equals(createType) && categoryIds != null) {
                Category category = new Category();
                category.setCategoryIds(categoryIds);
                List<Category> categoryList = categoryService.findList(category);
                if (categoryList.size() > 0) {
                    htmlService.htmlCategoryTask(categoryList, getData(request));
                }
                //静态化所有栏目
            } else if (CmsEnum.HTML_ALL.getCode().equals(createType)) {
                List<Category> categoryList = categoryService.findByModelId(null);
                htmlService.htmlCategoryTask(categoryList, getData(request));
            }
            //静态化首页
            htmlService.htmlIndex(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return AjaxJson.ok("栏目页静态化成功！");

    }

    /**
     * 进入信息静态化页面
     *
     * @param map
     * @param response
     * @return
     */
    @RequestMapping("/infoHtml")
    public String infoHtml(ModelMap map, HttpServletResponse response) {
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        if (cmsConfig != null) {
            List<Category> categoryList = Lists.newArrayList();
            List<Category> sourcelist = categoryService.findByModelId(null);
            Category.sortList(categoryList, sourcelist, "0");
            map.put("categoryList", categoryList);
        }
        return "modules/cms/infoHtml";
    }


    /**
     * 信息页静态化
     * @param ids
     * @param request
     * @return
     */
//    @RequestMapping("/infoHtmlDo")
//    public AjaxJson infoHtmlDo(String ids, HttpServletRequest request) {
//        if (StringUtils.isEmpty(ids)) {
//            return AjaxJson.fail("参数错误!");
//        }
//        String[] idArr = ids.split(";");
//        Info info = null;
//        Category category = null;
//        Map<String, Object> data = getData(request);
//        try {
//            for (int i = 0; i < idArr.length; i++) {
//                info = infoService.findUniqueByProperty("id", idArr[i]);
//                if(info != null && StringUtils.isNotEmpty(info.getCategoryId())){
//                    category = categoryService.findUniqueByProperty("id",info.getCategoryId());
//                    if(category != null){
//                        htmlService.htmlInfo(data, category, info, 1);
//                    }
//                    htmlService.htmlCategory(data,category,1);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            e.printStackTrace();
//        }
//        return AjaxJson.ok("栏目页静态化成功！");
//
//    }

    /**
     * 信息页静态化
     *
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "/infoHtmlDo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson infoHtmlDo(String ids, String createType, String startDate, String endDate, HttpServletRequest request) {
        if (StringUtils.isEmpty(ids) && StringUtils.isEmpty(createType)) {
            return AjaxJson.fail("参数错误!");
        }
        List<Category> categoryList = new ArrayList<>();
        Map<String, Object> data = getData(request);
        Category category = null;
        try {
            //生成所有栏目对应的信息
            if (CmsEnum.HTML_SELECTED.getCode().equals(createType)) {
                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                    category = categoryService.findUniqueByProperty("id", idArr[i]);
                    if (category != null) {
                        categoryList.add(category);
                    }
                }
                //生成全部栏目的信息
            } else if (CmsEnum.HTML_ALL.getCode().equals(createType)) {
                //查询所有栏目
                categoryList = categoryService.findByModelId(null);
            }
            for (int i = 0; i < categoryList.size(); i++) {
                //根据栏目列表查询信息列表,信息列表放到队列中执行
                List<Info> infoList = infoService.findByCategoryId(categoryList.get(i).getId(), startDate, endDate);
                htmlService.htmlInfoTask(infoList, data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return AjaxJson.ok("栏目页静态化成功！");

    }

    /**
     * 全站静态化页面
     *
     * @param map
     * @return
     */

    @RequestMapping("/htmlAll")
    public String htmlAll(ModelMap map, HttpServletResponse response) {
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        if (cmsConfig != null) {
            Htmlquartz htmlquartz = htmlquartzService.findUniqueByProperty("type", CmsEnum.HTML_QUARTZ.getCode());
            map.put("htmlquartz", htmlquartz);
            map.put("hours", getHours());
            map.put("mins", getMins());
        }
        return "modules/cms/htmlAll";
    }

    /**
     * 全站静态化调度
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/htmlAllSet")
    public AjaxJson htmlAllSet(String type, String intervalType, String exetimeHour, String exetimeMin, String intervalMin, String intervalHour, ModelMap map, HttpServletResponse response) {
        try {
            //处理静态化调度
            //查询全站静态化的调度
            Htmlquartz htmlquartz = htmlquartzService.findUniqueByProperty("id", "1");
            if (StringUtils.isEmpty(type) || "0".equals(type)) {
                //设置为无，则删除原来的配置
                if (htmlquartz != null) {
                    htmlquartzService.delete(htmlquartz);
                }
            } else {
                if (htmlquartz == null) {
                    htmlquartz = new Htmlquartz();
                }
                htmlquartz.setType(type);
                htmlquartz.setIntervalType(intervalType);
                htmlquartz.setExetimeHour(Integer.parseInt(exetimeHour));
                htmlquartz.setExetimeMin(Integer.parseInt(exetimeMin));
                htmlquartz.setIntervalHour(Integer.parseInt(intervalHour));
                htmlquartz.setIntervalMin(Integer.parseInt(intervalMin));
                htmlquartz.setObjtype(CmsEnum.HTML_QUARTZ.getCode());
                htmlquartzService.save(htmlquartz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("全站静态化调度设置失败:" + e.getMessage());
            return AjaxJson.fail(e.getMessage());
        }
        return AjaxJson.ok(" 全站静态化调度设置成功");

    }

    /**
     * 全部静态化
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/htmlAllDo")
    public AjaxJson htmlAllDo(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        try {
            long startTime = System.currentTimeMillis();

            long endTime = System.currentTimeMillis();
            htmlService.htmlAll(request, getData(request));
            logger.info("全站静态化用时：" + (endTime - startTime) / 1000 + "秒");
            //远程发布
            return AjaxJson.ok("全站静态化成功！");
        } catch (Exception e) {
            logger.error("全站静态化处理失败，原因:" + e.toString());
            return AjaxJson.fail("全站静态化失败!");
        }

    }

    /**
     * 日期型数据转换，将页面上的表示日期限的字符串，转换为Date型
     **/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));

    }

    public List<Integer> getHours() {
        hours = new ArrayList<Integer>();
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    public List<Integer> getMins() {
        mins = new ArrayList<Integer>();
        for (int i = 0; i < 60; i++) {
            mins.add(i);
        }
        return mins;
    }

    public void setMins(List<Integer> mins) {
        this.mins = mins;
    }
}
