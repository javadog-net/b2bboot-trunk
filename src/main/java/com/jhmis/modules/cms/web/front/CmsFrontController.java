package com.jhmis.modules.cms.web.front;

import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.entity.Visit;
import com.jhmis.modules.cms.service.CategoryService;
import com.jhmis.modules.cms.service.HtmlService;
import com.jhmis.modules.cms.service.InfoService;
import com.jhmis.modules.cms.service.VisitService;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.FreeMarkerUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lydia
 * @date 2019/10/8 15:53
 */
@Controller
public class CmsFrontController extends BaseController {
    @Autowired
    private InfoService infoService;
    @Autowired
    private HtmlService htmlService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private VisitService visitService;
    /**
     * 预览
     * @param map
     * @return
     */
    @RequestMapping("/categoryPreview")
    public String categoryPreview(String id, ModelMap map, HttpServletRequest request, HttpServletResponse response){
        String msg = "";
        try {
            if (StringUtils.isNotEmpty(id)){
                Category category=categoryService.findUniqueByProperty("id",id);
                if (category!=null) {
                    if (StringUtils.isNotEmpty(category.getOutUrl())) {
                        response.sendRedirect(category.getOutUrl());
                    }else {
                        //生成
                        Map<String,Object> data = getData(request);
                        data.put("currCategory",category);
                        htmlService.htmlCategory(data,category,1);
                        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
                        if(cmsConfig != null){
                            response.sendRedirect(getContextPathNoReplaceStr("")+ Htmlpath.htmlCmsCategoryPath(cmsConfig,category));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg="预览信息失败:"+e.getMessage();
            map.put("msg", msg);
            return "msg";
        }
        return null;
    }
    /**
     * 预览
     * @param map
     * @return
     */
    @RequestMapping("/infoPreview.do")
    public String infoPreview(String id, ModelMap map, HttpServletRequest request, HttpServletResponse response){
        String msg = "";
        try {
            if (StringUtils.isNotEmpty(id)){
                Info info=infoService.findUniqueByProperty("id",id);
                if (info!=null) {
                    if (StringUtils.isNotEmpty(info.getUrl())) {
                        response.sendRedirect(info.getUrl());
                    }else {
                        //查询该信息对应的栏目信息
                        Category category = null;
                        if(StringUtils.isNotEmpty(info.getCategoryId())){
                            category = categoryService.findUniqueByProperty("id",info.getCategoryId());
                        }
                        //生成
                        Map<String,Object> data = getData(request);
                        data.put("currInfo",info);
                        htmlService.htmlInfo(data,category,info,1);
                        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
                        if(cmsConfig != null){
                            response.sendRedirect(getContextPathNoReplaceStr("")+ Htmlpath.htmlInfoPath(cmsConfig,category,info));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg="预览信息失败:"+e.getMessage();
            map.put("msg", msg);
            return "msg";
        }
        return null;
    }
    /**
     * ajax点击
     * @return
     */
    @RequestMapping("/infoAjaxClick.do")
    public String info_ajaxClick(String id,String click,ModelMap map,HttpServletResponse response){
        int hits=0;
        if (StringUtils.isNotEmpty(id)) {
            Info info=infoService.findHitsById(id);
            if (info!=null) {
                if (!"0".equals(click)) {
                    info.setHits(info.getHits()+1);
                }
                //TODO  如果点击大于信息热点配置则设置为热点?????是否设置

                infoService.updateHitsById(info);
                hits=info.getHits();
            }
        }
        try {
            response.getWriter().print(hits);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ajax 增加浏览记录
     * @param categoryId
     * @param infoId
     * @param url
     * @param request
     * @param response
     * @return
     * @throws TemplateModelException
     * @throws IOException
     */
    @RequestMapping("/ajaxVisit")
    public String ajaxVisit(String categoryId,String infoId,String url,HttpServletRequest request,HttpServletResponse response) throws TemplateModelException, IOException{
        if (StringUtils.isNotEmpty(url)) {
            Visit visit=new Visit();
            visit.setCreateDate(new Date());
            visit.setCategoryId(categoryId);
            visit.setInfoId(infoId);
            visit.setIp(request.getRemoteAddr());
            String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + request.getContextPath() ;
            visit.setUrl(url.replace(basePath, "").replace("$$$", "&").replace("$$", "?"));
            visitService.save(visit);
        }
        return null;
    }
    @RequestMapping("/templetPro")
    public String templetPro(String templetFile,ModelMap map,HttpServletRequest request,HttpServletResponse response) {
        try {
            if (templetFile != null && templetFile.trim().length() > 0) {
                //查询cms配置
                CmsConfig cmsConfig = CmsUtils.getCmsConfig();
                if (cmsConfig != null && cmsConfig.getIndexPath() != null) {
                    String templateStyle = CmsUtils.getTemplateStyle();
                    //获取模板文件路径（yml文件中的配置路径+模板风格）
                    String templateFilePath = templatePath + templateStyle+"/";
                    File templateFile = new File(templateFilePath+templetFile);
                    if (!templateFile.exists()) {
                        logger.error("模板文件不存在！");
                        return null;
                    }
                    //生成静态页面
                    Map<String, Object> data = new HashMap<String, Object>();
                    data = setData(data,request);
                    response.setContentType("text/html");
                    response.setCharacterEncoding("UTF-8");
                    FreeMarkerUtils.createWriter(getServletContext(), data, templateFilePath, templetFile,response.getWriter());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/templet_pro")
    public String templet_pro(String templetPath,ModelMap map,HttpServletRequest request,HttpServletResponse response) throws TemplateException, IOException{
        return templetPro(templetPath, map, request,response);
    }
    /**
     * 获取数据处理模板并装处理结果以页面形式显示给用户
     * @return
     * @throws IOException
     * @throws TemplateModelException
     */
    @RequestMapping("/templetProFhtml")
    public String templetProFhtml(String templetFile,ModelMap map,HttpServletRequest request,HttpServletResponse response) throws TemplateModelException, IOException, SQLException {
        if (templetFile!=null && templetFile.trim().length()>0) {
            //查询cms配置
            CmsConfig cmsConfig = CmsUtils.getCmsConfig();
            if (cmsConfig!=null) {
                //生成静态页面
                Map<String,Object> data=new HashMap<String,Object>();
                data = setData(data,request);
                String templateFilePath = templatePath + cmsConfig.getTempletStyle()+"/";
                File templateFile = new File(templateFilePath+templetFile);
                if (!templateFile.exists()) {
                    logger.error("模板文件不存在！");
                    return null;
                }
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                try {
                    FreeMarkerUtils.createWriter(getServletContext(), data, templateFilePath, templetFile,response.getWriter());
                } catch (TemplateException e) {
                    logger.error("templetProFhtml:"+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 设置静态化参数
     * @param data
     */
    public Map<String,Object> setData(Map<String,Object> data,HttpServletRequest request) {
        CmsConfig cmsConfig = CmsUtils.getCmsConfig();
        data.put("cmsConfig", cmsConfig);
        data.put("contextPath", getContextPathNoReplaceStr(request.getContextPath()) + "/");
        data.put("contextPathNo",getContextPathNoReplaceStr(request.getContextPath()));//服务器地址
        data.put("request_remoteAddr", request.getRemoteAddr());
        Map<String, String> fieldValues = new HashMap<String, String>();
        //获取参数并放入data
        Enumeration<String> paramNames = request.getParameterNames();
        if (paramNames != null && paramNames.hasMoreElements()) {
            String name;
            while (paramNames.hasMoreElements()) {
                name = paramNames.nextElement();
                if (name != null &&
                        !name.equals("contextPath")) {
                    if (request.getParameterValues(name) != null && request.getParameterValues(name).length > 0) {
                        StringBuffer values = new StringBuffer();
                        for (String str : request.getParameterValues(name)) {
                            if (values.toString().length() > 0) {
                                values.append(",");
                            }
                            values.append(str);
                        }
                        data.put(name, values.toString());
                    } else {
                        data.put(name, request.getParameter(name));
                    }
                }
                //如果有currCategoryId参数则传递currCategory对象
                if (request.getParameter("currCategoryId")!=null && request.getParameter("currCategoryId").trim().length()>0) {
                    Category category = categoryService.findUniqueByProperty("id",request.getParameter("currCategoryId"));
                    data.put("currCategory",category);
                }
                //如果有currInfoid参数则传递currInfo对象
                if (request.getParameter("currInfoid")!=null && request.getParameter("currInfoid").trim().length()>0) {
                    Info info=infoService.findUniqueByProperty("id",request.getParameter("currInfoid"));
                    data.put("currInfo",info);
                }
            }
        }
        return data;
    }
}
