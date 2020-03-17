package com.jhmis.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.Area;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.mapper.UserMapper;
import com.jhmis.modules.sys.service.AreaService;
import com.jhmis.modules.sys.service.GmInfoService;

@Controller
@RequestMapping(value="api/toApp")
public class ToAPPApi extends BaseController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private GmInfoService gmInfoService;
	@Autowired
    private UserMapper userMapper;
	/** 
	  * @Title: selectProvince 
	  * @Description: TODO  查询所有的省份
	  * @param response
	  * @param request
	  * @return 
	  * @return List<Map<String,String>>
	  * @author tc
	  * @date 2019年9月9日下午4:57:50
	  */
	@RequestMapping(value = "/btb/selectProvince")
	@ResponseBody
	public List<Map<String, String>> selectProvince(HttpServletResponse response, HttpServletRequest request) {
		logger.info("查询所有的省份***");
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<Area> provinceList = areaService.findAllProvice();
		List<Map<String, String>> pList = new ArrayList<Map<String, String>>();
		for (Area a : provinceList) {
			Map<String, String> pMap = new HashMap<String, String>();
			pMap.put("id", a.getId());
			pMap.put("name", a.getName());
			pList.add(pMap);
		}
		return pList;
	}
	
	
	
	/** 
	  * @Title: selectGmInfo 
	  * @Description: TODO  查询全国工贸信息
	  * @param response
	  * @param request
	  * @return 
	  * @return List<Map<String,String>>
	  * @author tc
	  * @date 2019年9月9日下午5:04:05
	  */
	@RequestMapping(value = "/btb/selectGmInfo")
	@ResponseBody
	public List<Map<String, String>> selectGmInfo(HttpServletResponse response, HttpServletRequest request) {
		logger.info("/btb/selectGmInfo->");
		response.setHeader("Access-Control-Allow-Origin", "*");
		List<Map<String, String>> gmMapList = new ArrayList<Map<String, String>>();
		List<GmInfo> gmInfoList = gmInfoService.findList(new GmInfo());
		for (GmInfo gmInfo : gmInfoList) {
			Map<String, String> pMap = new HashMap<String, String>();
			pMap.put("code", gmInfo.getBranchCode());
			pMap.put("name", gmInfo.getGmName());
			gmMapList.add(pMap);
		}
		return gmMapList;
	}
	
	
	/** 
	  * @Title: selectIndustryDirector 
	  * @Description: TODO  
	  * @param branchCode 工贸branchCode
	  * @param response
	  * @param request
	  * @return 
	  * @return Map<String,Object>
	  * @author tc
	  * @date 2019年9月11日下午2:39:24
	  */
	@RequestMapping(value = "/order/selectIndustryDirector")
	@ResponseBody
	public Map<String, Object> selectIndustryDirector(String branchCode, HttpServletResponse response,
			HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		logger.info("/order/selectIndustryDirector->branchCode:" + branchCode);
		branchCode = request.getParameter("gmcode");
		logger.info("/order/selectIndustryDirector->branchCode:" + branchCode);
		if(StringUtils.isBlank(branchCode)){
			logger.info("工贸 branchCode为：空.工贸有误");
			return null;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		try { 
		
			GmInfo gmIn=gmInfoService.findByBranchCode(branchCode);
			if(gmIn==null){
				logger.info("工贸 branchCode为："+branchCode+",此工贸查不到。工贸有误");
				return null;
			}
			List<User> userList = userMapper.findByBranchCode(branchCode);
			if (userList != null && userList.size() > 0) {
				User u = new User();
				if (userList.size() == 1) {
					u = userList.get(0);
				}else{
					for (User user : userList) {
						// 排除渠道经理
						if (!"工程总监(渠道经理),".equals(user.getRoleNames())) {
							u = user;
						}
						
					}
				}
				m.put("id", u.getId());
				m.put("account", u.getLoginName());
				m.put("username", u.getName());
				m.put("phone", u.getPhone());
				m.put("mobile", u.getMobile());
				m.put("email", u.getEmail());
				m.put("password", u.getPassword());
				m.put("branchCode", branchCode);
				m.put("gmname", u.getGmName());
			}
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
		return m;
	}

	
}
