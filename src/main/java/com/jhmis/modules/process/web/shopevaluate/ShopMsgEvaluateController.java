/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.web.shopevaluate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.haier.user.api.UserApi;
import com.haier.user.api.dto.ExecuteResult;
import com.jhmis.common.Enum.ProcessCode;
import com.jhmis.common.Exception.ShopMsgException;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.mapper.shopevaluate.ShopMsgEvaluateMapper;
import com.jhmis.modules.process.service.dispatcher.ShopMsgDispatcherService;
import com.jhmis.modules.process.service.shopmsg.ShopMsgService;
import com.jhmis.modules.process.service.shopmsgorder.ShopMsgCustcodeOrderService;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
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
import com.jhmis.modules.process.entity.shopevaluate.ShopMsgEvaluate;
import com.jhmis.modules.process.service.shopevaluate.ShopMsgEvaluateService;

/**
 * 评价相关Controller
 * @author hdx
 * @version 2019-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/process/shopevaluate/shopMsgEvaluate")
public class ShopMsgEvaluateController extends BaseController {

	@Autowired
	private ShopMsgEvaluateService shopMsgEvaluateService;

	@Autowired
	private ShopMsgService shopMsgService;

	@Autowired
	ShopMsgDispatcherService shopMsgDispatcherService;

	@Autowired
	ShopMsgCustcodeOrderService shopMsgCustcodeOrderService;

    @Autowired
    ShopMsgEvaluateMapper shopMsgEvaluateMapper;

	
	@ModelAttribute
	public ShopMsgEvaluate get(@RequestParam(required=false) String id) {
		ShopMsgEvaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopMsgEvaluateService.get(id);
		}
		if (entity == null){
			entity = new ShopMsgEvaluate();
		}
		return entity;
	}
	
	/**
	 * 需要评价相关列表页面
	 */
	@RequiresPermissions("process:shopevaluate:shopMsgEvaluate:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/process/shopevaluate/shopMsgEvaluateList";
	}
	
	/**
	 * 需要评价相关列表数据
	 */
	@ResponseBody
	@RequiresPermissions("process:shopevaluate:shopMsgEvaluate:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ShopMsgEvaluate shopMsgEvaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		//Page<ShopMsgEvaluate> page = shopMsgEvaluateService.findPage(new Page<ShopMsgEvaluate>(request, response), shopMsgEvaluate);
		Page<ShopMsgEvaluate> ShopMsgEvaluatePage = new Page<ShopMsgEvaluate>(request, response);
		ShopMsgEvaluatePage.setOrderBy(" sm.create_date desc");
		Page<ShopMsgEvaluate> page = shopMsgEvaluateService.findListGroup(ShopMsgEvaluatePage, shopMsgEvaluate);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑需要评价相关表单页面
	 */
	@RequiresPermissions(value={"process:shopevaluate:shopMsgEvaluate:view","process:shopevaluate:shopMsgEvaluate:add","process:shopevaluate:shopMsgEvaluate:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ShopMsgEvaluate shopMsgEvaluate, Model model) {
		model.addAttribute("shopMsgEvaluate", shopMsgEvaluate);
		if(StringUtils.isBlank(shopMsgEvaluate.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/process/shopevaluate/shopMsgEvaluateForm";
	}

	/**
	 * 保存需要评价相关
	 */
	@RequiresPermissions(value={"process:shopevaluate:shopMsgEvaluate:add","process:shopevaluate:shopMsgEvaluate:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ShopMsgEvaluate shopMsgEvaluate, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, shopMsgEvaluate)){
			return form(shopMsgEvaluate, model);
		}
		//新增或编辑表单保存
		shopMsgEvaluateService.save(shopMsgEvaluate);//保存
		addMessage(redirectAttributes, "保存需要评价相关成功");
		return "redirect:"+Global.getAdminPath()+"/process/shopevaluate/shopMsgEvaluate/?repage";
	}
	
	/**
	 * 删除需要评价相关
	 */
	@ResponseBody
	@RequiresPermissions("process:shopevaluate:shopMsgEvaluate:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ShopMsgEvaluate shopMsgEvaluate, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shopMsgEvaluateService.delete(shopMsgEvaluate);
		j.setMsg("删除需要评价相关成功");
		return j;
	}
	
	/**
	 * 批量删除需要评价相关
	 */
	@ResponseBody
	@RequiresPermissions("process:shopevaluate:shopMsgEvaluate:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shopMsgEvaluateService.delete(shopMsgEvaluateService.get(id));
		}
		j.setMsg("删除需要评价相关成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("process:shopevaluate:shopMsgEvaluate:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ShopMsgEvaluate shopMsgEvaluate, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "需要评价相关"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ShopMsgEvaluate> page = shopMsgEvaluateService.findPage(new Page<ShopMsgEvaluate>(request, response, -1), shopMsgEvaluate);
    		new ExportExcel("需要评价相关", ShopMsgEvaluate.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出需要评价相关记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("process:shopevaluate:shopMsgEvaluate:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ShopMsgEvaluate> list = ei.getDataList(ShopMsgEvaluate.class);
			for (ShopMsgEvaluate shopMsgEvaluate : list){
				try{
					shopMsgEvaluateService.save(shopMsgEvaluate);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条需要评价相关记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条需要评价相关记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入需要评价相关失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopevaluate/shopMsgEvaluate/?repage";
    }
	
	/**
	 * 下载导入需要评价相关数据模板
	 */
	@RequiresPermissions("process:shopevaluate:shopMsgEvaluate:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "需要评价相关数据导入模板.xlsx";
    		List<ShopMsgEvaluate> list = Lists.newArrayList(); 
    		new ExportExcel("需要评价相关数据", ShopMsgEvaluate.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/process/shopevaluate/shopMsgEvaluate/?repage";
    }


	/**
	 * 查看详情页面
	 */
	@RequiresPermissions(value={"process:shopmsg:shopMsg:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(ShopMsgEvaluate shopMsgEvaluate, Model model) {
		//查看此详情
		ShopMsg shopMsg = shopMsgService.get(shopMsgEvaluate.getId());
		//查看详情标记为已读
		if(ProcessCode.MSG_NO_READ.getLabel().equals(shopMsg.getIsRead())){
			//如果没有进入详情读过，更新状态
			try{
				shopMsg.setIsRead(ProcessCode.MSG_IS_READ.getLabel());
				shopMsgService.updateMsg(shopMsg);
			} catch (ShopMsgException e) {
				logger.error("更新需求发生异常,错误:" + e.getMessage());
			}
		}
		//如果是派单相关可查派单相关数据
		if(ProcessCode.IS_DISPATCHER.getLabel().equals(shopMsg.getIsDispatch())){
			//证明进入过派单,查询一下派单相关信息
			ShopMsgDispatcher shopMsgDispatcher = new ShopMsgDispatcher();
			shopMsgDispatcher.setMsgId(shopMsg.getId());
			//设置msgid
			List<ShopMsgDispatcher> listShopMsgDispatcher = shopMsgDispatcherService.findList(shopMsgDispatcher);
			model.addAttribute("listShopMsgDispatcher", listShopMsgDispatcher);
		}
		//如果经销商订单列表存在则查询相关数据
		if(!"".equals(shopMsg.getCustCode()) && null !=shopMsg.getCustCode()){
			ShopMsgCustcodeOrder smco = new ShopMsgCustcodeOrder();
			smco.setMsgId(shopMsg.getId());
			List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder = shopMsgCustcodeOrderService.findList(smco);
			model.addAttribute("listShopMsgCustcodeOrder", listShopMsgCustcodeOrder);
		}
		//查询相关评价信息
        ShopMsgEvaluate sme = new ShopMsgEvaluate();
        sme.setMsgid(shopMsgEvaluate.getId());
        List<ShopMsgEvaluate> listShopMsgEvaluate = shopMsgEvaluateService.findList(sme);
        if(listShopMsgEvaluate!=null && listShopMsgEvaluate.size()>0){
            model.addAttribute("listShopMsgEvaluate", listShopMsgEvaluate);
        }
		model.addAttribute("shopMsg", shopMsg);
		return "modules/process/shopevaluate/shopMsgEvaluateView";
	}

    /**
     * 提交客服反馈
     */
    @ResponseBody
    @RequiresPermissions("process:shopevaluate:shopMsgEvaluate:feedback")
    @RequestMapping(value = "feedback",method = RequestMethod.POST)
    public AjaxJson feedback(String id,String content) {
        AjaxJson j = new AjaxJson();
        ShopMsgEvaluate sme = shopMsgEvaluateService.get(id);
        User currentUser = UserUtils.getUser();
        //反馈人
        sme.setFeedbackperson(currentUser.getLoginName());
        //反馈时间
        sme.setFeedbacktime(new Date());
        //反馈内容
        sme.setFeedbackdesc(content);
        shopMsgEvaluateMapper.update(sme);
        j.setMsg("反馈成功");
        return j;
    }
}