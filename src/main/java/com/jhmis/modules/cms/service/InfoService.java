/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Category;
import com.jhmis.modules.cms.entity.Info;
import com.jhmis.modules.cms.entity.InfoProduct;
import com.jhmis.modules.cms.mapper.InfoMapper;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 信息管理Service
 *
 * @author lydia
 * @version 2019-09-30
 */
@Service
@Transactional(readOnly = true)
public class InfoService extends CrudService<InfoMapper, Info> {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private HtmlService htmlService;
    @Autowired
    private InfoProductService infoProductService;

    public Info get(String id) {
        return super.get(id);
    }

    public List<Info> findList(Info info) {
        return mapper.findList(info);
    }

    public Page<Info> findPage(Page<Info> page, Info info) {
        return super.findPage(page,info);
    }
    @Transactional(readOnly = false)
    public void save(Info info) {
        if(info != null && info.getHtmlIndexnum() ==0){
            info.setHtmlIndexnum(getMaxHtmlIndexNum());
        }
        super.save(info);
        if(info.getInfoProductList() != null){
            for(int i = 0;i<info.getInfoProductList().size();i++){
                InfoProduct infoProduct = info.getInfoProductList().get(i);
                infoProduct.setInfoId(info.getId());
                infoProductService.save(infoProduct);
            }
        }
        // 更新关联商品
        if (info.getOldInfoProductList() !=  null && info.getOldInfoProductList().size() > 0) {
            for(InfoProduct infoProduct:info.getOldInfoProductList()){
                infoProduct.setInfoId(info.getId());
                infoProductService.save(infoProduct);
            }

        }
    }

    @Transactional(readOnly = false)
    public void delete(Info info) {
        super.delete(info);
    }

    /**
     * 根据信息ID 查询信息的点击数量
     *
     * @param id
     * @return
     */
    public Info findHitsById(String id) {
        return mapper.findHitsById(id);
    }
    /**
    *@Description 修改浏览量
    *@Param
    *@Return
    *@Author t.c
    *@Date 2019-11-07
    */
    @Transactional(readOnly = false)
    public int updateHitsById(String id,Integer count){
        return mapper.updateHitsByIdTwo(id,count);
    }





    /**
     * 根据信息ID 更新点击数，freemarker 标签使用
     *
     * @param info
     * @return
     */
	@Transactional(readOnly = false)
    public int updateHitsById(Info info) {
        return mapper.updateHitsById(info);
    }
	@Transactional(readOnly = false)
    public void moveInfo(String infoArr[], Info info, Category oldCategory, Category distCategory, String templateWholePath, Map<String, Object> data) throws IOException, TemplateException {
        for (int i = 0; i < infoArr.length; i++) {
            info = mapper.findUniqueByProperty("id", infoArr[i]);
            if (info != null) {
                info.setCategoryId(distCategory.getId());
                save(info);
                htmlService.htmlInfo(data, distCategory, info, 1);
            }
        }
        //TODO 转移栏目时不静态化
//        // 所属栏目静态化
//        htmlService.htmlCategory(data, distCategory, templateWholePath, 1);
//        // 所属栏目的父栏目静态化
//
//        List<Category> categoryList = categoryService.findByIdPath(distCategory.getId());
//        for (Category c : categoryList) {
//            htmlService.htmlCategory(data, c, templateWholePath, 1);
//        }
//        //原栏目页静态化
//        htmlService.htmlCategory(data, oldCategory, templateWholePath, 1);
        //静态化首页
//        htmlService.htmlIndex(data);
    }

    /**
     * 查询包含文章内容的文章列表
     * 2@return
     */
    public  List<Info> findListWithBlob(Info info){
       return  mapper.findListWithBlob(info);
    }

    public int getMaxHtmlIndexNum(){
        int maxHtmlNum = mapper.getMaxHtmlIndexNum();
        return maxHtmlNum+1;
    }

    /**
     * 根据栏目ID 查询信息列表
     * @param categoryId
     * @return
     */
    public List<Info> findByCategoryId(String categoryId,String startDate,String endDate){
        Info info = new Info();
        if(StringUtils.isNotEmpty(startDate)){
            info.setStartDate(startDate);
        }
        if(StringUtils.isNotEmpty(endDate)){
            info.setEndDate(endDate);
        }
        Category category = new Category();
        category.setId(categoryId);
        info.setCategory(category);
        return mapper.findListWithBlob(info);

    }
    @Transactional(readOnly = false)
    public int updateImages(Info info){
        return mapper.updateImages(info);
    }
}