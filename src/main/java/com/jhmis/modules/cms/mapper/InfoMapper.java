/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.Info;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 信息管理MAPPER接口
 * @author lydia
 * @version 2019-09-30
 */
@MyBatisMapper
public interface InfoMapper extends BaseMapper<Info> {
    /**
     * 根据信息ID 查询信息的点击数量
     * @param id
     * @return
     */
    Info findHitsById(String id);

    /**
     * 根据信息ID 更新点击数，freemarker 标签使用
     * @param info
     * @return
     */
    int updateHitsById(Info info);
    /**
    *@Description 修改浏览量
    *@Param  id 文章id count修改数
    *@Return
    *@Author t.c
    *@Date 2019-11-07
    */
    int updateHitsByIdTwo(@Param(value="id") String id, @Param(value="hits") Integer hits);

    /**
     * 查询包含文章内容的文章列表
     * @param info
     * @return
     */
    List<Info> findListWithBlob(Info info);

    /**
     * 查询最大索引号
     * @return
     */
    int getMaxHtmlIndexNum();
    /**
     * 迁移更新图片信息
     * @param info
     * @return
     */
    int updateImages(Info info);
}