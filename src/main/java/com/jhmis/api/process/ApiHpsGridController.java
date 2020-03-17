/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.process;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.customer.entity.TGridArea;
import com.jhmis.modules.customer.service.TGridAreaService;
import com.jhmis.modules.process.entity.grid.HpsGrid;
import com.jhmis.modules.process.service.grid.HpsGridService;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import com.jhmis.modules.shop.service.dealer.DealerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 省市区匹配工贸Controller
 * @author mll
 * @version 2019-09-25
 */
@RestController
@RequestMapping(value = "/api/process")
public class ApiHpsGridController extends BaseController {

	@Autowired
	private HpsGridService hpsGridService;
	
	@Autowired
	private TGridAreaService tGridAreaService;
	
	@Autowired
	private DealerService dealerService;
	
	/**
	 * 判断用户是否存在网格数据
	 * @return
	 */
	@ApiOperation(notes = "getWgData", httpMethod = "GET", value = "判断用户是否存在网格数据", consumes = "application/x-www-form-urlencoded")
	@RequestMapping(value = "/getWgData")
	public AjaxJson getWgData(String dealerId){
		if (StringUtils.isBlank(dealerId)) {
            return AjaxJson.fail("dealerId不能为空");
        }		
		Dealer dealer = dealerService.get(dealerId);
		if(dealer == null) {
			return AjaxJson.fail("dealerId为错误id");
		}
		TGridArea tGridArea = new TGridArea();
		if (StringUtils.isNotBlank(dealer.getWgcode())) {
            tGridArea.setWgcode(dealer.getWgcode());
            List<TGridArea> tGridAreaList = tGridAreaService.findList(tGridArea);
            if(tGridAreaList != null) {
            	tGridArea = tGridAreaList.get(0);
            	return AjaxJson.ok(tGridArea);
            }else{
            	return AjaxJson.fail("101", "该区域无网格数据，请重新维护！");
            }
        }else{
        	return  AjaxJson.fail("102", "数据不完善，请维护数据！");
        }
		
	}
	
	/**
	 * 更新用户的网格数据
	 * @return
	 */
	@ApiOperation(notes = "updateWgData", httpMethod = "GET", value = "判断用户是否存在网格数据", consumes = "application/x-www-form-urlencoded")
	@RequestMapping(value = "/updateWgData")
	public AjaxJson updateWgData(String dealerId, String district){
		if (StringUtils.isBlank(dealerId)) {
            return AjaxJson.fail("dealerId不能为空");
        }
		if (StringUtils.isBlank(district)) {
            return AjaxJson.fail("地区districtc不能为空");
        }		
		Dealer dealer = dealerService.get(dealerId);
		if(dealer == null) {
			return AjaxJson.fail("dealerId为错误id");
		}
		TGridArea tGridArea = new TGridArea();
		tGridArea.setDistrict(district);

        List<TGridArea> tGridAreaList = tGridAreaService.findList(tGridArea);
        if(tGridAreaList != null && tGridAreaList.size()>0) {
        	tGridArea = tGridAreaList.get(0);
        	dealer.setMdmProvince(tGridArea.getProvincecode());
        	dealer.setMdmCity(tGridArea.getCitycode());
        	dealer.setMdmArea(tGridArea.getDistrictcode());
        	dealer.setWgcode(tGridArea.getWgcode());
        	dealer.setWgname(tGridArea.getWgname());
        	dealerService.save(dealer);
        	
        	AjaxJson ajaxJson = new AjaxJson();
    		ajaxJson.setResult(tGridArea);
    		return ajaxJson;
        }else {
        	return AjaxJson.fail("103","完善网格数据失败！");
        }
	
	}
	
	
	/**
	 * 获取省份接口
	 * @return
	 */
	@ApiOperation(notes = "getProvince", httpMethod = "GET", value = "获取省份接口", consumes = "application/x-www-form-urlencoded")
	@RequestMapping(value = "/getProvince")
	public List<String> getProvince(){	
		return tGridAreaService.getProvince();
	}
	
	/**
	 * 根据省名称获取城市
	 * @param province 省份
	 * @return
	 */
	@ApiOperation(notes = "getCityByProvince", httpMethod = "GET", value = "获取省份接口", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParam(name = "province", value = "省份", required = true, paramType = "form", dataType = "String")
	@RequestMapping(value = "/getCityByProvince")
	public List<String> getCityByProvince(String province){	
		return tGridAreaService.getCityByProvince(province);
	}
	
	/**
	 * 根据省和城市名称获取区县
	 * @param province 省份
	 * @param city 城市
	 * @return
	 */
	@ApiOperation(notes = "getAreaByCity", httpMethod = "GET", value = "获取省份接口", consumes = "application/x-www-form-urlencoded")
	@RequestMapping(value = "/getAreaByCity")
	public List<String> getAreaByCity(@RequestParam(value = "province", required = true)String province, @RequestParam(value = "city", required = true)String city){		
		return tGridAreaService.getAreaByCity(province,city);
	}
	
	
	/**
	 * 根据所属中心(工贸)获取相应的城市
	 * @param centerName 中心名称
	 * @return
	 */
	@ApiOperation(notes = "getCityByCenter", httpMethod = "GET", value = "根据所属中心(工贸)获取相应的城市", consumes = "application/x-www-form-urlencoded")
	@ApiImplicitParam(name = "centerName", value = "中心名称", required = true, paramType = "form", dataType = "String")
	@RequestMapping(value = "/getCityByCenter")
	public List<String> getCityByCenter(String centerName){		
		List<String> citys = hpsGridService.findCityByCenter(centerName);		
		return citys;
	}
	
	

}