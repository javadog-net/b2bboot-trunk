/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.GuessRecommend;
import com.jhmis.modules.shop.mapper.GuessRecommendMapper;

/**
 * 猜你想要推荐人数表Service
 * @author tc
 * @version 2019-10-29
 */
@Service
@Transactional(readOnly = true)
public class GuessRecommendService extends CrudService<GuessRecommendMapper, GuessRecommend> {

	public GuessRecommend get(String id) {
		//id="418a747ff49c4fiu9fd9603a656506ds";
		return super.get(id);
	}
	
	public List<GuessRecommend> findList(GuessRecommend guessRecommend) {
		return super.findList(guessRecommend);
	}
	
	public Page<GuessRecommend> findPage(Page<GuessRecommend> page, GuessRecommend guessRecommend) {
		return super.findPage(page, guessRecommend);
	}
	
	@Transactional(readOnly = false)
	public void save(GuessRecommend guessRecommend) {
		super.save(guessRecommend);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuessRecommend guessRecommend) {

		super.delete(guessRecommend);
	}

	@Transactional(readOnly = false)
	public void updateRecommendNum() {
		GuessRecommend guess=mapper.get("418a747ff49c4fiu9fd9603a656506ds");
		if (guess!=null){
			String t=guess.getGuessRecommendNum();
			Integer num=Integer.parseInt(t);
			guess.setGuessRecommendNum(String.valueOf(num));
			mapper.update(guess);
		}
	}
}