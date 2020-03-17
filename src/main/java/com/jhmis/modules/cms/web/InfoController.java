/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.entity.InfoProduct;
import com.jhmis.modules.cms.service.*;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 信息管理Controller
 *
 * @author lydia
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/info")
public class InfoController extends BaseController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private HtmlService htmlService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private SensitiveService sensitiveService;
    @Autowired
    private InfoProductService infoProductService;

    @ModelAttribute
    public Info get(@RequestParam(required = false) String id) {
        Info entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = infoService.get(id);
        }
        if (entity == null) {
            entity = new Info();
        }
        return entity;
    }

    /**
     * 信息管理列表页面
     */
    @RequiresPermissions("cms:info:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/cms/infoList";
    }

    /**
     * 信息管理列表数据
     */
    @ResponseBody
    @RequiresPermissions("cms:info:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(Info info, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Info> page = infoService.findPage(new Page<Info>(request, response), info);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑信息管理表单页面
     */
    @RequiresPermissions(value = {"cms:info:view", "cms:info:add", "cms:info:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(Info info, Model model) {
        //查询内容模板
        List<String> contentTemplateList = templateService.getTemplateList(templatePath, "content_", false);
        if (StringUtils.isBlank(info.getId()) && info.getCategory() != null) {
            model.addAttribute("isAdd", true);
            String contentTemplateName = templateService.getContentTemplate(info.getCategory().getId(), info.getId());
            info.setContentTemplate(contentTemplateName);
        }
        if (StringUtils.isNotEmpty(info.getId())) {
            List<InfoProduct> infoProductList = new ArrayList<>();
            // 查询关联商品
            if (info != null && StringUtils.isNotEmpty(info.getId())) {
                InfoProduct infoProduct = new InfoProduct();
                infoProduct.setInfoId(info.getId());
                infoProductList = infoProductService.findList(infoProduct);
            }

            model.addAttribute("infoProductList", infoProductList);
        }

        model.addAttribute("contentList", contentTemplateList);
        model.addAttribute("info", info);
        return "modules/cms/infoForm";
    }

    /**
     * 保存信息管理
     */
    @RequiresPermissions(value = {"cms:info:add", "cms:info:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(Info info,String delOldProduct, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {
        if (!beanValidator(model, info)) {
            return form(info, model);
        }
        //新增或编辑表单保存
        if (StringUtils.isNotEmpty(info.getContent())) {
            info.setContent(StringEscapeUtils.unescapeHtml(info.getContent()));
        }
        // 敏感词处理
        info.setTitle(sensitiveService.replace(info.getTitle()));
        info.setShortTitle(sensitiveService.replace(info.getShortTitle()));
        info.setContent(sensitiveService.replace(info.getContent()));
        info.setDescription(sensitiveService.replace(info.getDescription()));
        //如果没有上传图片，则取图片集的第一张图片作为信息图片
       //如果没有上传图片，则取图片集的第一张图片作为信息图片
        String image = "";
        if(StringUtils.isEmpty(info.getImage())){
            if(StringUtils.isNotEmpty(info.getImages())){
                image =  info.getImages().split("\\|")[0];
                info.setImage(image);
            }else if(StringUtils.isNotEmpty(info.getContent())){
                // 如果没有选择信息图片，则检查信息内容中是否有图片
                try {
                    Parser parser = new Parser(info.getContent());
                    NodeFilter filter = new TagNameFilter("img");
                    NodeList nodes = parser.extractAllNodesThatMatch(filter);
                    Node eachNode = null;
                    ImageTag imageTag = null;
                    if (nodes != null && nodes.size() > 0) {
                        // 遍历所有的img节点
                        for (int i = 0; i < nodes.size(); i++) {
                            if (info.getImage() == null || info.getImage().trim().length() == 0) {
                                eachNode = (Node) nodes.elementAt(i);
                                if (eachNode instanceof ImageTag) {
                                    imageTag = (ImageTag) eachNode;
                                    info.setImage(imageTag.getAttribute("src"));
                                    System.out.println("信息图片"+info.getImage());
                                    System.out.println("请求路径"+request.getContextPath());
                                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(info.getImage())
                                            && info.getImage().startsWith(request.getContextPath())) {
                                        info.setImage(info.getImage().replaceFirst(request.getContextPath(), ""));
                                        System.out.println("最后路径："+info.getImage());
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } catch (ParserException e) {
                    e.printStackTrace();
                }
            }
        }
        //文章关联商品
        Enumeration<String> paramNames = request.getParameterNames();
        String paramName = "";
        String productid = "";
        String productcontent = "";
        List<InfoProduct> infoProductList = new ArrayList<>();
        List<InfoProduct> oldInfoProductList = new ArrayList<>();
        while (paramNames.hasMoreElements()) {
            paramName = paramNames.nextElement();
            if (paramName.startsWith("productname")) {
                productid = paramName.replace("productname", "");
                // 生成对象
                InfoProduct infoProduct = new InfoProduct();
                infoProduct.setProductId(productid);
                infoProductList.add(infoProduct);

            }
            if (paramName.startsWith("oldinfoproductsid")) {
                // 需要更新的商品处理
                productid = paramName.replace("oldinfoproductsid", "");
                InfoProduct infoProduct = new InfoProduct();
                infoProduct.setId(productid);
                infoProduct.setProductId(request.getParameter("oldproductid" + productid));
                oldInfoProductList.add(infoProduct);
            }
        }
        // 删除关联商品
        if(StringUtils.isNotEmpty(delOldProduct)){
            if (StringUtils.isNotEmpty(delOldProduct)) {
                String dels[] = delOldProduct.split(";");
                if (dels != null && dels.length > 0) {
                    for (int i = 0; i < dels.length; i++) {
                        if (dels[i].trim().length() > 0) {
                            InfoProduct infoProduct = infoProductService.findUniqueByProperty("id",dels[i]);
                            if(infoProduct != null){
                                infoProductService.delete(infoProduct);

                            }
                        }
                    }
                }
            }
        }
        info.setInfoProductList(infoProductList);
        info.setOldInfoProductList(oldInfoProductList);
        infoService.save(info);//保存
        addMessage(redirectAttributes, "保存信息管理成功");
        redirectAttributes.addFlashAttribute("info", info);
        return "redirect:" + Global.getAdminPath() + "/cms/info/confirmHtml";
    }

    @RequiresPermissions(value = {"cms:info:view", "cms:info:add", "cms:info:edit"}, logical = Logical.OR)
    @RequestMapping(value = "confirmHtml")
    public String confirmHtml(Info info, Model model) {
        model.addAttribute("info", info);
        if (StringUtils.isBlank(info.getId())) {//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/cms/infoMakeHtml";
    }

    /**
     * 删除信息管理
     */
    @ResponseBody
    @RequiresPermissions("cms:info:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(Info info, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        infoService.delete(info);
        j.setMsg("删除信息管理成功");
        return j;
    }

    /**
     * 批量删除信息管理
     */
    @ResponseBody
    @RequiresPermissions("cms:info:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            infoService.delete(infoService.get(id));
        }
        j.setMsg("删除信息管理成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("cms:info:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public AjaxJson exportFile(Info info, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "信息管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<Info> page = infoService.findPage(new Page<Info>(request, response, -1), info);
            new ExportExcel("信息管理", Info.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出信息管理记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("cms:info:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Info> list = ei.getDataList(Info.class);
            for (Info info : list) {
                try {
                    infoService.save(info);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条信息管理记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条信息管理记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入信息管理失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/info/?repage";
    }

    /**
     * 下载导入信息管理数据模板
     */
    @RequiresPermissions("cms:info:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "信息管理数据导入模板.xlsx";
            List<Info> list = Lists.newArrayList();
            new ExportExcel("信息管理数据", Info.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/cms/info/?repage";
    }

    /**
     * 信息页静态化
     *
     * @param ids             文章内容ID
     * @param request
     * @return
     */

    @RequiresPermissions("cms:info:edit")
    @RequestMapping(value = "infoMakeHtml", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson infoMakeHtml(String ids,String htmlCategory, String htmlCategoryPar, String htmlIndex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(ids)) {
            addMessage(redirectAttributes, "参数不能为空！");
            AjaxJson.fail("参数错误！");
        }
        String idArr[] = ids.split(",");
        Info info = null;
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        Map<String, Object> data = getData(request);
        Category category = null;
        try {
            for (int i = 0; i < idArr.length; i++) {
                info = infoService.findUniqueByProperty("id", idArr[i]);
                if (info != null) {
                    //查询所属栏目信息
                    if (i == 0 && StringUtils.isNotEmpty(info.getCategoryId())) {
                        category = categoryService.findUniqueByProperty("id", info.getCategoryId());
                    }
                    //静态化信息页
                    htmlService.htmlInfo(data, category, info, 1);
                }
            }
            if(category != null){
                // 所属栏目静态化
                if (Global.YES.equals(htmlCategory)) {
                    htmlService.htmlCategory(data, category, 1);
                    //TODO 如果该栏目时产品讲坛，静态化该子级栏目  后续改成定时任务
                    if(StringUtils.isNotEmpty(category.getName())&& category.getName().equals("产品讲坛")){
                        List<Category> categoryList = categoryService.findSonById(category.getId());
                        if(categoryList != null && categoryList.size() >0){
                            htmlService.htmlCategoryTask(categoryList, data);
                        }
                    }

                }
                // 如果不是一级栏目 所属栏目的父栏目静态化
                if (Global.YES.equals(htmlCategoryPar) && category.getParent() != null) {
                    List<Category> categoryList = categoryService.findByIdPath(category.getId());
                    htmlService.htmlCategoryTask(categoryList, data);
                }
            }
            //首页静态化
            if (Global.YES.equals(htmlIndex)) {
                String htmlPath = Htmlpath.htmlIndexPath(CmsUtils.getCmsConfig());
                File htmlPathDir = new File(htmlPath);
                if (!htmlPathDir.exists()) {
                    htmlPathDir.mkdirs();
                }
                htmlService.htmlIndex(data);
            }
            addMessage(redirectAttributes, "信息静态化成功！");
            return AjaxJson.ok("静态化成功");
        } catch (IOException e) {
            logger.error("信息静态化失败-" + e.getMessage());
            return AjaxJson.fail("静态化失败！");
        } catch (TemplateException e) {
            logger.error("信息静态化失败-" + e.getMessage());
            e.printStackTrace();
            return AjaxJson.fail("静态化失败！");
        }
    }

    @RequiresPermissions("cms:info:edit")
    @RequestMapping(value = "infoMakeHtmlMove", method = RequestMethod.POST)
    public String infoMakeHtmlMove(String ids, HttpServletRequest request, String oldCategoryId, String htmlOldCategory,
                                   String htmlCategory, String htmlCategoryPar, String htmlIndex, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(ids)) {
            addMessage(redirectAttributes, "参数不能为空！");
            return "redirect:" + adminPath + "/cms/";
        }
        String idArr[] = ids.split(",");
        Info info = null;
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        Map<String, Object> data = getData(request);
        Category category = null;
        List<Category> totalCategoryList = new ArrayList<>();
        try {
            for (int i = 0; i < idArr.length; i++) {
                info = infoService.findUniqueByProperty("id", idArr[i]);
                if (info != null) {
                    //查询所属栏目信息
                    if (i == 0 && StringUtils.isNotEmpty(info.getCategoryId())) {
                        category = categoryService.findUniqueByProperty("id", info.getCategoryId());
                    }
                    //静态化信息页
                    htmlService.htmlInfo(data, category, info, 1);
                }
            }
            // 所属栏目静态化
            if (Global.YES.equals(htmlCategory)) {
                htmlService.htmlCategory(data, category, 1);
            }
            //原栏目页静态化
            if (StringUtils.isNotEmpty(htmlOldCategory) && Global.YES.equals(htmlOldCategory) && StringUtils.isNotEmpty(oldCategoryId)) {
                Category oldCategory = categoryService.findUniqueByProperty("id", oldCategoryId);
                htmlService.htmlCategory(data, oldCategory, 1);
            }
            // 所属栏目的父栏目静态化
            if (Global.YES.equals(htmlCategoryPar)) {
                List<Category> categoryList = categoryService.findByIdPath(category.getId());
                if(categoryList != null && categoryList.size() > 0){
                    totalCategoryList.addAll(categoryList);
                }
            }

            //首页静态化
            if (Global.YES.equals(htmlIndex)) {
                String htmlPath = Htmlpath.htmlIndexPath(CmsUtils.getCmsConfig());
                File htmlPathDir = new File(htmlPath);
                if (!htmlPathDir.exists()) {
                    htmlPathDir.mkdirs();
                }
                htmlService.htmlIndex(data);
            }
            j.setSuccess(true);
            addMessage(redirectAttributes, "信息静态化成功！");
        } catch (IOException e) {
            j.setSuccess(false);
            e.printStackTrace();
            addMessage(redirectAttributes, e.getMessage());
        } catch (TemplateException e) {
            j.setSuccess(false);
            e.printStackTrace();
            addMessage(redirectAttributes, e.getMessage());
        }
        return "redirect:" + adminPath + "/cms/";
    }

    /**
     * 移动（文章）信息至新栏目
     *
     * @param infoIds
     * @return
     */
    @RequestMapping(value = "infoMoveCategory", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson infoMoveCategory(String infoIds, String distCategoryId, HttpServletRequest request, Model model) {
        if (StringUtils.isEmpty(infoIds) || StringUtils.isEmpty(distCategoryId)) {
            AjaxJson.fail("参数错误！");
        }
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        j.setMsg("转移栏目失败！");
        //原栏目信息
        String[] infoArr = infoIds.split(";");
        Category oldCategory = categoryService.findUniqueByProperty("id", infoArr[0]);
        Category distCategory = categoryService.findUniqueByProperty("id", distCategoryId);
        Info info = null;
        Map<String, Object> data = getData(request);
        String templateWholePath = CmsUtils.getTemplateWholePath(templatePath);
        try {
            infoService.moveInfo(infoArr, info, oldCategory, distCategory, templateWholePath, data);
            j.setSuccess(true);
            j.setMsg("转移栏目成功！");
        } catch (IOException | TemplateException | NullPointerException e) {
            logger.error(e.getMessage());
        } finally {
            return j;
        }
    }


}