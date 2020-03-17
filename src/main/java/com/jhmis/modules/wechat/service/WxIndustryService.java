package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhmis.modules.wechat.entity.WxIndustry;
import com.jhmis.modules.wechat.mapper.WxIndustryMapper;

@Service
public class WxIndustryService {
	@Autowired
	private WxIndustryMapper mapper;

	public WxIndustry find(String id) {
		return mapper.find(id);
	}

	public List<WxIndustry> findList(WxIndustry wxIndustry) {
		return mapper.findLists(wxIndustry);
	}

}
