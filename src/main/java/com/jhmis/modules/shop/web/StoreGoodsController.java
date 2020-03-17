/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.shop.entity.StoreGoods;
import com.jhmis.modules.shop.service.StoreGoodsService;
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
import java.util.List;
import java.util.Map;

/**
 * 店铺商品管理Controller
 *
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/storeGoods")
public class StoreGoodsController extends BaseController {

    @Autowired
    private StoreGoodsService storeGoodsService;

    @ModelAttribute
    public StoreGoods get(@RequestParam(required = false) String id) {
        StoreGoods entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = storeGoodsService.get(id);
        }
        if (entity == null) {
            entity = new StoreGoods();
        }
        return entity;
    }

    /**
     * 商品列表页面
     */
    @RequiresPermissions("shop:storeGoods:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/shop/storeGoodsList";
    }

    /**
     * 商品列表弹出子页
     */
    @RequiresPermissions("shop:storeGoods:listchild")
    @RequestMapping(value = {"listchild"})
    public String listchild() {
        return "modules/shop/storeGoodsListChild";
    }

    /**
     * 商品列表数据
     */
    @ResponseBody
    @RequiresPermissions("shop:storeGoods:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreGoods> page = storeGoodsService.findPage(new Page<StoreGoods>(request, response), storeGoods);
        return getBootstrapData(page);
    }


    /**
     * 查看，增加，编辑商品表单页面
     */
    @RequiresPermissions(value = {"shop:storeGoods:view", "shop:storeGoods:add", "shop:storeGoods:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(StoreGoods storeGoods, Model model) {
        model.addAttribute("storeGoods", storeGoods);
        if (StringUtils.isBlank(storeGoods.getId())) {//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/shop/storeGoodsForm";
    }

    /**
     * 保存商品
     */
    @RequiresPermissions(value = {"shop:storeGoods:add", "shop:storeGoods:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(StoreGoods storeGoods, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, storeGoods)) {
            return form(storeGoods, model);
        }
        //新增或编辑表单保存
        storeGoodsService.save(storeGoods);//保存
        addMessage(redirectAttributes, "保存商品成功");
        return "redirect:" + Global.getAdminPath() + "/shop/storeGoods/?repage";
    }

    /**
     * 删除商品
     */
    @ResponseBody
    @RequiresPermissions("shop:storeGoods:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(StoreGoods storeGoods, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        storeGoodsService.delete(storeGoods);
        j.setMsg("删除商品成功");
        return j;
    }

    /**
     * 批量删除商品
     * 20181112 改为逻辑删除 lyz
     */
    @ResponseBody
    @RequiresPermissions("shop:storeGoods:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (storeGoodsService.batchDelete(ids,request,CmsUtils.getTemplateWholePath(templatePath))) {
            j.setMsg("删除商品成功");
        } else {
            j.setMsg("删除商品失败");
        }
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("shop:storeGoods:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public AjaxJson exportFile(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "商品" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<StoreGoods> page = storeGoodsService.findPage(new Page<StoreGoods>(request, response, -1), storeGoods);
            new ExportExcel("商品", StoreGoods.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出商品记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("shop:storeGoods:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<StoreGoods> list = ei.getDataList(StoreGoods.class);
            for (StoreGoods storeGoods : list) {
                try {
                    storeGoodsService.save(storeGoods);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条商品记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条商品记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入商品失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/shop/storeGoods/?repage";
    }

    /**
     * 下载导入商品数据模板
     */
    @RequiresPermissions("shop:storeGoods:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "商品数据导入模板.xlsx";
            List<StoreGoods> list = Lists.newArrayList();
            new ExportExcel("商品数据", StoreGoods.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/shop/storeGoods/?repage";
    }

    /**
     * 选择关联商品
     * @return
     */
    @RequestMapping("/selectInfoGoods")
    public String selectProduct(){
        return "modules/shop/selectInfoGoods";
    }

    /**
     * 根据栏目ID 获取店铺商品信息
     */
    @ResponseBody
    @RequiresPermissions("shop:storeGoods:list")
    @RequestMapping(value = "listData")
    public Map<String, Object> listData(StoreGoods storeGoods, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<StoreGoods> page = storeGoodsService.findPage(new Page<StoreGoods>(request, response), storeGoods);
        return getBootstrapData(page);
    }


}