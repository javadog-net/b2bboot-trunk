/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 猜你想要推荐人数表Entity
 * @author tc
 * @version 2019-10-29
 */
public class GuessRecommend extends DataEntity<GuessRecommend> {
	
	private static final long serialVersionUID = 1L;
	private String guessRecommendNum;		// 猜你想要 推荐的人数
	
	public GuessRecommend() {
		super();
	}

	public GuessRecommend(String id){
		super(id);
	}

	@ExcelField(title="猜你想要 推荐的人数", align=2, sort=1)
	public String getGuessRecommendNum() {
		return guessRecommendNum;
	}

	public void setGuessRecommendNum(String guessRecommendNum) {
		this.guessRecommendNum = guessRecommendNum;
	}
	
}