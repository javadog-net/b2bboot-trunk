/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.Comment;

/**
 * 评论管理MAPPER接口
 * @author lydia
 * @version 2019-09-16
 */
@MyBatisMapper
public interface CommentMapper extends BaseMapper<Comment> {

    Integer selectCountById(String id);
}