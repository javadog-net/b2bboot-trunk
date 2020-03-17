package com.jhmis.modules.cms.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsModel;
import com.jhmis.modules.cms.entity.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;

/**
 * 静态化需要的模板信息
 */
@Service
public class TemplateService {
    @Autowired
    private InfoService infoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private  CmsModelService cmsModelService;
    public List<String> getTemplateList(String templatePath,String typeName,boolean ismobile){
        //显示的时候从default 下来查找
        String templateStyle = "default";
        String path = "";
        if(!ismobile){
            path = templatePath+templateStyle+"/";
        }else{
            path = templatePath+templateStyle+"/mobile/";
        }
        File file = new File(path);
        List<String> fileNameList = Lists.newArrayList();
        if(file.exists()){
            /**
             * 根据名称过滤文件
             */
            File [] fileArr = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if(pathname.isDirectory()){
                        return false;
                    }
                    if(pathname.getName().contains(typeName)){
                        fileNameList.add(pathname.getName());
                        return true;
                    }
                    return false;
                }
            });
        }
        return  fileNameList;
    }

    /**
     * 分类获取模板信息
     * @param templatePath
     * @return
     */
    public Map<String,List<String>> getTemplate(String templatePath){
        Map<String,List<String>> mapList = Maps.newHashMap();
        List<String> indexList = getTemplateList(templatePath,"index_",false);
        List<String> list = getTemplateList(templatePath,"list_",false);
        List<String> contentList = getTemplateList(templatePath,"content_",false);
        List<String> mobileIndexList = getTemplateList(templatePath,"index_",true);
        List<String> mobileList = getTemplateList(templatePath,"list_",true);
        List<String> mobileContentList = getTemplateList(templatePath,"content_",true);
        List<String> mobileProductList = getTemplateList(templatePath,"product_",true);
        List<String> productList = getTemplateList(templatePath,"product_",false);
        mapList.put("mobileProductList",mobileProductList);
        mapList.put("productList",productList);
        mapList.put("indexList",indexList);
        mapList.put("list",list);
        mapList.put("contentList",contentList);
        mapList.put("mobileIndexList",mobileIndexList);
        mapList.put("mobileList",mobileList);
        mapList.put("mobileContentList",mobileContentList);
        return mapList;
    }

    /**
     * 根据栏目ID 获取栏目的内容模板信息
     * @param categoryId
     * @return
     */
    public String getContentTemplate(String categoryId,String infoId){
        String contentTemplateName = "";
        Category category = null;
        //首先根据infoId 来查询信息中是否设置了内容模板
        if(StringUtils.isNotEmpty(infoId)) {
            Info info = infoService.findUniqueByProperty("id", infoId);
            if (info != null && StringUtils.isNotEmpty(info.getContentTemplate())) {
                contentTemplateName = info.getContentTemplate();
            }
        }else if(StringUtils.isNotEmpty(categoryId)){
                //查询栏目中是否设置了内容模板
                 category = categoryService.findUniqueByProperty("id",categoryId);
                if(category != null && StringUtils.isNotEmpty(category.getContentTemplate())){
                    contentTemplateName = category.getContentTemplate();
                }
        }else{
            //查询模型中是否设置了内容模板
            if(category != null && StringUtils.isNotEmpty(category.getModelId())){
                CmsModel cmsModel = cmsModelService.findUniqueByProperty("id",category.getModelId());
                if(cmsModel != null && StringUtils.isNotEmpty(cmsModel.getContentTemplate())){
                    contentTemplateName = cmsModel.getContentTemplate();
                }
            }


        }
            return contentTemplateName;
    }

    /**
     * 查询栏目页模板信息
     * @param modelId
     * @return
     */
    public Map<String,String> getCategoryTemplateName(String modelId){
        Map<String,String> map = Maps.newHashMap();
        if(StringUtils.isNotEmpty(modelId)){
            CmsModel cmsModel = cmsModelService.findUniqueByProperty("id",modelId);
            if(cmsModel != null){
                if(StringUtils.isNotEmpty(cmsModel.getCategoryTemplate())) {
                    map.put("categoryTemplate", cmsModel.getCategoryTemplate());
                }
                if(StringUtils.isNotEmpty("")){
                    map.put("contentTemplate",cmsModel.getContentTemplate());
                }
            }
        }
        return map;
    }
}
