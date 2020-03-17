package com.jhmis.modules.cms.utils;

import com.jhmis.common.utils.SpringContextHolder;
import com.jhmis.modules.cms.service.FreemarkerComponent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;

/**
 * @author yd
 * @date 2019/9/20 13:39
 */
public  class FreeMarkerUtils {

    public static void createHTML(Map<String, Object> data,String templatePath, String templateName, String htmlPath) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        File file = new File(templatePath);
        //指定模版路径
        configuration.setDirectoryForTemplateLoading(file);
        //设置共享变量
        Map<String, Object> map =  SpringContextHolder.getApplicationContext().getBeansWithAnnotation(FreemarkerComponent.class);
        for (String key : map.keySet()) {
            configuration.setSharedVariable(key, map.get(key));
        }
        Template template = configuration.getTemplate(templateName,"UTF-8");

        //静态页面路径
        File htmlFile = new File(htmlPath);
        if (!htmlFile.getParentFile().isDirectory() && !htmlFile.getParentFile().exists()) {
            htmlFile.getParentFile().mkdirs();
        }
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
        //处理模版
        try{
            template.process(data, writer);
        }catch (Exception e){
            e.printStackTrace();
        }
        writer.flush();
        writer.close();
    }
    /**
     * 处理页面后，装处理结果放入指定Out
     * @param context
     * @param data
     * @param templatePath
     * @throws TemplateModelException
     */
    public static void createWriter(ServletContext context, Map<String,Object> data, String templatePath, String templateFile,Writer writer) throws  IOException, TemplateException{
        createWriter(context, data, "UTF-8", templatePath, templateFile,"UTF-8",writer);
    }
    public static void createWriter(ServletContext context,Map<String,Object> data,String templetEncode,String templatePath,String templateFile,String htmlEncode,Writer writer) throws  IOException, TemplateException{

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        File file = new File(templatePath);
        //指定模版路径
        configuration.setDirectoryForTemplateLoading(file);
        //设置共享变量
        Map<String, Object> map =  SpringContextHolder.getApplicationContext().getBeansWithAnnotation(FreemarkerComponent.class);
        for (String key : map.keySet()) {
            configuration.setSharedVariable(key, map.get(key));
        }
        try {
            //指定模版路径
            Template template = configuration.getTemplate(templateFile,templetEncode);
            //处理模版
            template.process(data, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
