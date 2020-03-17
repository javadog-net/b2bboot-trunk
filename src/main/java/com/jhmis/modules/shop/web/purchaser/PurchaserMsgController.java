/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web.purchaser;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.common.websocket.service.msg.MsgSocketHandler;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.shop.entity.purchaser.PurchaserMsg;
import com.jhmis.modules.shop.service.purchaser.PurchaserMsgService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购商消息列表Controller
 * @author tity
 * @version 2018-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/purchaser/purchaserMsg")
public class PurchaserMsgController extends BaseController {

	@Autowired
	private PurchaserMsgService purchaserMsgService;
    //websocket消息处理器
    private MsgSocketHandler msgSocketHandler = new MsgSocketHandler();
	@ModelAttribute
	public PurchaserMsg get(@RequestParam(required=false) String id) {
		PurchaserMsg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = purchaserMsgService.get(id);
		}
		if (entity == null){
			entity = new PurchaserMsg();
		}
		return entity;
	}
	
	/**
	 * 消息列表页面
	 */
	@RequiresPermissions("shop:purchaser:purchaserMsg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/purchaser/purchaserMsgList";
	}
	
	/**
	 * 消息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserMsg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(PurchaserMsg purchaserMsg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PurchaserMsg> page = purchaserMsgService.findSysMsgPage(new Page<PurchaserMsg>(request, response), purchaserMsg);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑消息表单页面
	 */
	@RequiresPermissions(value={"shop:purchaser:purchaserMsg:view","shop:purchaser:purchaserMsg:add","shop:purchaser:purchaserMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PurchaserMsg purchaserMsg, Model model) {
		model.addAttribute("purchaserMsg", purchaserMsg);
		return "modules/shop/purchaser/purchaserMsgForm";
	}

	/**
	 * 保存消息
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:purchaser:purchaserMsg:add","shop:purchaser:purchaserMsg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(PurchaserMsg purchaserMsg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
        purchaserMsg.setCode("");
        purchaserMsg.setPurchaserId("");
        purchaserMsg.setType(Global.MESSAGE_TYPE_SYS);
        purchaserMsg.setRelId("");
        purchaserMsg.setAddtime(new Date());
		purchaserMsgService.save(purchaserMsg);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存消息成功");
        msgSocketHandler.sendMessageToAllPurchasers(purchaserMsg.getContent());
		return j;
	}
	
	/**
	 * 删除消息
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserMsg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(PurchaserMsg purchaserMsg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		purchaserMsgService.delete(purchaserMsg);
		j.setMsg("删除消息成功");
		return j;
	}
	
	/**
	 * 批量删除消息
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserMsg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			purchaserMsgService.delete(purchaserMsgService.get(id));
		}
		j.setMsg("删除消息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:purchaser:purchaserMsg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(PurchaserMsg purchaserMsg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "消息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PurchaserMsg> page = purchaserMsgService.findPage(new Page<PurchaserMsg>(request, response, -1), purchaserMsg);
    		new ExportExcel("消息", PurchaserMsg.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("shop:purchaser:purchaserMsg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PurchaserMsg> list = ei.getDataList(PurchaserMsg.class);
			for (PurchaserMsg purchaserMsg : list){
				try{
					purchaserMsgService.save(purchaserMsg);
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
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserMsg/?repage";
    }
	
	/**
	 * 下载导入消息数据模板
	 */
	@RequiresPermissions("shop:purchaser:purchaserMsg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "消息数据导入模板.xlsx";
    		List<PurchaserMsg> list = Lists.newArrayList(); 
    		new ExportExcel("消息数据", PurchaserMsg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/purchaser/purchaserMsg/?repage";
    }

}