/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.dealer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jhmis.common.websocket.service.msg.MsgSocketHandler;
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
import com.jhmis.modules.shop.entity.dealer.DealerMsg;
import com.jhmis.modules.shop.service.dealer.DealerMsgService;

/**
 * 供应商消息列表Controller
 * @author tity
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/dealer/dealerMsg")
public class DealerMsgController extends BaseController {

	@Autowired
	private DealerMsgService dealerMsgService;
    //websocket消息处理器
    private MsgSocketHandler msgSocketHandler = new MsgSocketHandler();
	@ModelAttribute
	public DealerMsg get(@RequestParam(required=false) String id) {
		DealerMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dealerMsgService.get(id);
		}
		if (entity == null){
			entity = new DealerMsg();
		}
		return entity;
	}
	
	/**
	 * 消息列表页面
	 */
	@RequiresPermissions("shop:dealer:dealerMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/dealer/dealerMsgList";
	}
	
	/**
	 * 消息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DealerMsg dealerMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DealerMsg> page = dealerMsgService.findSysMsgPage(new Page<DealerMsg>(request, response), dealerMsg);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑消息表单页面
	 */
	@RequiresPermissions(value={"shop:dealer:dealerMsg:view","shop:dealer:dealerMsg:add","shop:dealer:dealerMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DealerMsg dealerMsg, Model model) {
		model.addAttribute("dealerMsg", dealerMsg);
		return "modules/shop/dealer/dealerMsgForm";
	}

	/**
	 * 保存消息
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:dealer:dealerMsg:add","shop:dealer:dealerMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(DealerMsg dealerMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
        dealerMsg.setCode("");
        dealerMsg.setDealerId("");
        dealerMsg.setType(Global.MESSAGE_TYPE_SYS);
        dealerMsg.setRelId("");
        dealerMsg.setAddtime(new Date());
		dealerMsgService.save(dealerMsg);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存消息成功");
        msgSocketHandler.sendMessageToAlldealers(dealerMsg.getContent());
		return j;
	}
	
	/**
	 * 删除消息
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(DealerMsg dealerMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		dealerMsgService.delete(dealerMsg);
		j.setMsg("删除消息成功");
		return j;
	}
	
	/**
	 * 批量删除消息
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dealerMsgService.delete(dealerMsgService.get(id));
		}
		j.setMsg("删除消息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:dealer:dealerMsg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(DealerMsg dealerMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "消息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DealerMsg> page = dealerMsgService.findPage(new Page<DealerMsg>(request, response, -1), dealerMsg);
    		new ExportExcel("消息", DealerMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出消息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:dealer:dealerMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DealerMsg> list = ei.getDataList(DealerMsg.class);
			for (DealerMsg dealerMsg : list){
				try{
					dealerMsgService.save(dealerMsg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条消息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条消息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入消息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealerMsg/?repage";
    }
	
	/**
	 * 下载导入消息数据模板
	 */
	@RequiresPermissions("shop:dealer:dealerMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "消息数据导入模板.xlsx";
    		List<DealerMsg> list = Lists.newArrayList(); 
    		new ExportExcel("消息数据", DealerMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/dealer/dealerMsg/?repage";
    }

}