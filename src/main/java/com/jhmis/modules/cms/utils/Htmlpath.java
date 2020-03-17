package com.jhmis.modules.cms.utils;

import com.haier.http.client.utils.DateUtil;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.shop.entity.GoodsCategory;
import com.jhmis.modules.shop.entity.StoreGoods;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lydia
 * @date 2019/9/24 17:17
 */
public class Htmlpath {
    /**
     * cms 静态文件首页生成目录
     * @return
     */
    public  static String htmlIndexPath(CmsConfig cmsConfig){
        if(cmsConfig != null && StringUtils.isNotEmpty(cmsConfig.getIndexPath())){
            String cmsPath = cmsConfig.getIndexPath();
            if(!cmsPath.startsWith("/")){
                cmsPath = "/"+cmsPath;
            }
            if(!cmsPath.endsWith("/")){
                cmsPath = cmsPath+"/";
            }
            return cmsPath;
        }
        //如果未设置，则使用站点名称
        return "/"+cmsConfig.getName()+"/";
    }

    /**
     * 获取文章栏目的生成目录
     * @param category
     * @return
     */
    public static String htmlCmsCategoryPath(CmsConfig cmsConfig, Category category){
        if (category!=null) {
            //获取{category}变量
            String categoryVal="";
            if (StringUtils.isNotEmpty(category.getCategoryMark())) {
                categoryVal=category.getCategoryMark();
            }else if (category.getSort()>0) {
                categoryVal=""+category.getSort();
            }else {
                categoryVal=category.getId();
            }
            if (StringUtils.isNotEmpty(cmsConfig.getCmsCategoryPath())) {
                String cmsCategoryPath=cmsConfig.getCmsCategoryPath().trim();
                //处理前后/
                if (!cmsCategoryPath.startsWith("/")) {
                    cmsCategoryPath="/"+cmsCategoryPath;
                }
                if (!cmsCategoryPath.endsWith("/")) {
                    cmsCategoryPath=cmsCategoryPath+"/";
                }
                cmsCategoryPath=cmsCategoryPath.replace("{category}", categoryVal);
                return cmsCategoryPath;
            }else {
                //如果未设置使用默认值
                return cmsConfig.getCmsCategoryPath()+"/"+categoryVal+"/";
            }
        }
        return "/null/";
    }
   
    /** 
      * @Title: htmlGoodsCategoryPath 
      * @Description: TODO  获取商品栏目的生成目录
      * @param cmsConfig
      * @param goodsCategory
      * @return 
      * @return String
      * @author tc
      * @date 2019年10月24日上午10:52:26
      */
    public static String htmlGoodsCategoryPath(CmsConfig cmsConfig, GoodsCategory goodsCategory){
        if (goodsCategory!=null) {
            //获取{shopcategory}变量
            String categoryVal="";
            if (StringUtils.isNotEmpty(goodsCategory.getPageMark())) {//获取栏目页面标识
                categoryVal=goodsCategory.getPageMark();
            }else if (goodsCategory.getSort()>0) {
                categoryVal=""+goodsCategory.getSort();
            }else {
                categoryVal=goodsCategory.getId();
            }
            if (StringUtils.isNotEmpty(cmsConfig.getCategoryPath())) {
                String shopCategoryPath=cmsConfig.getCategoryPath().trim();
                //处理前后/
                if (!shopCategoryPath.startsWith("/")) {
                    shopCategoryPath="/"+shopCategoryPath;
                }
                if (!shopCategoryPath.endsWith("/")) {
                    shopCategoryPath=shopCategoryPath+"/";
                }
                shopCategoryPath=shopCategoryPath.replace("{shopcategory}", categoryVal);
                return shopCategoryPath;
            }else {
                //如果未设置使用默认值
                return cmsConfig.getCategoryPath()+"/"+categoryVal;
            }
        }
        return "/null/";
    }
    
    
    
    /**
     * 内容页生成路径
     * @param cmsConfig
     * @param info
     * @return
     */
    public static String  htmlInfoPath(CmsConfig cmsConfig,Category category, Info info){
            if (cmsConfig!=null && category!=null && info!=null) {
                //获取{category}变量
                String categoryVal="";
                if (StringUtils.isNotEmpty(category.getCategoryMark())) {
                    categoryVal=category.getCategoryMark();
                }else if (category.getSort()!= null && category.getSort()>0) {
                    categoryVal=""+category.getSort();
                }else {
                    categoryVal=category.getId();
                }
                //获取{info}变量
                String infoVal="";
                //TODO 内容页生成静态化文件名的顺序？指定——indexnum——info.id
                if (info.getHtmlIndexnum() != 0 && info.getHtmlIndexnum()>0) {
                    infoVal=""+info.getHtmlIndexnum();
                }else {
                    infoVal=info.getId();
                }
                String yyyyVal="null";
                String MMVal="null";
                String ddVal="null";
                String HHVal="null";
                if (info.getCreateDate()!=null) {
                    yyyyVal= DateUtil.format(info.getCreateDate(), "yyyy");
                    MMVal=DateUtil.format(info.getCreateDate(), "MM");
                    ddVal=DateUtil.format(info.getCreateDate(), "dd");
                    HHVal=DateUtil.format(info.getCreateDate(), "HH");
                }
                if (StringUtils.isNotEmpty(cmsConfig.getInfoPath())) {
                    String infopath=cmsConfig.getInfoPath().trim();
                    //处理前后/
                    if (!infopath.startsWith("/")) {
                        infopath="/"+infopath;
                    }
                    infopath=infopath.replace("{category}", categoryVal);
                    infopath=infopath.replace("{info}", infoVal);
                    infopath=infopath.replace("{yyyy}", yyyyVal);
                    infopath=infopath.replace("{MM}", MMVal);
                    infopath=infopath.replace("{dd}", ddVal);
                    infopath=infopath.replace("{HH}", HHVal);
                    return infopath;
                }else {
                    //如果未设置使用默认值
                    return "/"+categoryVal+"/info/"+yyyyVal+"/"+infoVal+".html";
                }
            }
            return "/null/null/null/null.html";
    }
    
    
    
    /** 
      * @Title: goodsPath 
      * @Description: TODO  商品静态化
      * @param cmsConfig
      * @param category
      * @param goods
      * @return 
      * @return String
      * @author tc
      * @date 2019年10月21日下午4:58:10
      */
    public static String  goodsPath(CmsConfig cmsConfig,GoodsCategory category, StoreGoods goods){
    //如果htmlpath有 则直接返回
        if(null!=goods && null!=goods.getHtmlpath()){
            return goods.getHtmlpath();
        }

    	//如果原来oldHtmlPath不是空的，则取原来的
    	if(null!=goods && null!=goods.getOldHtmlPath()){
    		return goods.getOldHtmlPath();
    	}
            if (cmsConfig!=null && category!=null && goods!=null) {
                //获取{category}变量
                String categoryVal="";
                if (StringUtils.isNotEmpty(category.getPageMark())) {
                    categoryVal=category.getPageMark();
                }else {
                    categoryVal=category.getId();
                }
                //获取{goods}变量
                String goodsVal="";
                //TODO 内容页生成静态化文件名的顺序？指定——indexnum——goods.id
                if (goods.getHtmlIndexNum() > 0) {
                	goodsVal=""+goods.getHtmlIndexNum();
                }else {
                	goodsVal=goods.getId();
                }
                String yyyyVal="null";
                String MMVal="null";
                String ddVal="null";
                String HHVal="null";
                if (goods.getCreateDate()!=null) {
                    yyyyVal= DateUtil.format(goods.getCreateDate(), "yyyy");
                    MMVal=DateUtil.format(goods.getCreateDate(), "MM");
                    ddVal=DateUtil.format(goods.getCreateDate(), "dd");
                    HHVal=DateUtil.format(goods.getCreateDate(), "HH");
                }
                if (StringUtils.isNotEmpty(cmsConfig.getProductPath())) {
                    String goodsPath=cmsConfig.getProductPath().trim();
                    //处理前后/
                    if (!goodsPath.startsWith("/")) {
                    	goodsPath="/"+goodsPath;
                    }
                    goodsPath=goodsPath.replace("{shopcategory}", categoryVal);
                    goodsPath=goodsPath.replace("{product}", goodsVal);
                    goodsPath=goodsPath.replace("{yyyy}", yyyyVal);
                    goodsPath=goodsPath.replace("{MM}", MMVal);
                    goodsPath=goodsPath.replace("{dd}", ddVal);
                    goodsPath=goodsPath.replace("{HH}", HHVal);
                    return goodsPath;
                }else {
                    //如果未设置使用默认值
                    return "/"+categoryVal+"/goods/"+yyyyVal+"/"+goodsVal+".html";
                }
            }
            return "/null/null/null/null.html";
    }
    

    
}
