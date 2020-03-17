/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Comment;
import com.jhmis.modules.cms.mapper.CommentMapper;

/**
 * 评论管理Service
 * @author lydia
 * @version 2019-09-16
 */
@Service
@Transactional(readOnly = true)
public class CommentService extends CrudService<CommentMapper, Comment> {

	public Comment get(String id) {
		return super.get(id);
	}
	
	public List<Comment> findList(Comment comment) {
		return super.findList(comment);
	}
	
	public Page<Comment> findPage(Page<Comment> page, Comment comment) {
		return super.findPage(page, comment);
	}
	
	@Transactional(readOnly = false)
	public void save(Comment comment) {
		super.save(comment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Comment comment) {
		super.delete(comment);
	}
	/**
	 * 递归获取评论信息
	 * @param comment
	 * @return
	 */
	public List<Comment> fact(Comment comment){
		List<Comment> list = mapper.findList(comment);
		if(list==null||list.size()==0){
			return null;
		}else {
			for (Comment comment2 : list) {
				comment.setParentId(comment2.getId());
				comment2.setComments(fact(comment));
			}
			return list;
		}
	}



/**
*@Description 查询评论数量
*@Param 
*@Return 
*@Author t.c
*@Date 2019-11-07
*/
    public Integer selectCountById(String id) {
	       return 	mapper.selectCountById(id);
    }
}