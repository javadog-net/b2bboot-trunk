package com.jhmis.modules.gen.web;

import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jhmis.modules.gen.entity.GenCustomObj;
import com.jhmis.modules.gen.entity.GenScheme;
import com.jhmis.modules.gen.entity.GenTable;
import com.jhmis.modules.gen.service.GenCustomObjService;
import com.jhmis.modules.gen.service.GenSchemeService;
import com.jhmis.modules.gen.service.GenTableService;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
/**
 * 自定义对象管理
 * @author tity
 *
 */
@Controller
@RequestMapping({"${adminPath}/gen/genCustomObj"})
public class GenCustomObjController extends BaseController {
    @Autowired
    private GenCustomObjService genCustomObjService;
    @Autowired
    public GenSchemeService genSchemeService;
    @Autowired
    public GenTableService genTableService;

    public GenCustomObjController() {
    }

    @ModelAttribute
    public GenCustomObj a(@RequestParam(required = false) String id) {
        GenCustomObj a1 = null;
        if (StringUtils.isNotBlank(id)) {
            a1 = this.genCustomObjService.get(id);
        }

        if (a1 == null) {
            a1 = new GenCustomObj();
        }

        return a1;
    }

    /**
     * 自定义java对象管理列表窗口页面
     * @return
     */
    @RequiresPermissions({"gen:genCustomObj:list"})
    @RequestMapping({"list", ""})
    public String a() {
        return "modules/gen/genCustomObjList";
    }

    /**
     * 自定义java对象管理列表数据
     * @param b
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequiresPermissions({"gen:genCustomObj:list"})
    @RequestMapping({"data"})
    public Map<String, Object> a(GenCustomObj b, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<GenCustomObj> a = this.genCustomObjService.findPage(new Page<GenCustomObj>(request, response), b);
        return this.getBootstrapData(a);
    }

    @RequiresPermissions(
        value = {"gen:genCustomObj:view", "gen:genCustomObj:add", "gen:genCustomObj:edit"},
        logical = Logical.OR
    )
    @RequestMapping({"form"})
    public String d(GenCustomObj a, Model model) {
        model.addAttribute("genCustomObj", a);
        return "modules/gen/genCustomObjForm";
    }

    @RequiresPermissions(
        value = {"gen:genCustomObj:add", "gen:genCustomObj:edit"},
        logical = Logical.OR
    )
    @RequestMapping({"save"})
    public String e(GenCustomObj c, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!this.beanValidator(model, c, new Class[0])) {
            return this.d(c, model);
        } else {
            this.genCustomObjService.save(c);
            this.addMessage(redirectAttributes, new String[]{"保存自定义对象成功"});
            return "redirect:" + Global.getAdminPath() + "/gen/genCustomObj/?repage";
        }
    }

    /**
     * 已定义对象生成
     * @param gen_table_id
     * @param genTableType
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequestMapping({"add"})
    public AjaxJson h(String gen_table_id, String genTableType, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        GenScheme a1 = this.genSchemeService.b1("gen_table_id", gen_table_id);
        if (a1 == null) {
            j.setSuccess(false);
            j.setMsg("添加JAVA自定义对象失败,请先生成代码!或者你可以到自定义对象管理中手动添加。");
            return j;
        } else {
            GenTable b1 = this.genTableService.a1(a1.getGenTable().getId());
            GenCustomObj c1 = new GenCustomObj();
            c1.setLabel(b1.getClassName());
            String a2 = b1.getClassName();
            if (StringUtils.isNotBlank(a2)) {
                a2 = a2.substring(0, 1).toLowerCase() + a2.substring(1);
            }

            String d = a1.getPackageName() + "." + a1.getModuleName() + ".entity" + (StringUtils.isBlank(a1.getSubModuleName()) ? "" : "." + StringUtils.lowerCase(a1.getSubModuleName())) + "." + b1.getClassName();
            c1.setValue(d);
            c1.setTableName(b1.getName());
            String a3 = "/" + a1.getModuleName() + (StringUtils.isNotBlank(a1.getSubModuleName()) ? "/" + StringUtils.lowerCase(a1.getSubModuleName()) : "") + "/" + a2;
            if (!"3".equals(genTableType) && !"4".equals(genTableType)) {
                c1.setDataUrl(a3 + "/data");
            } else {
                c1.setDataUrl(a3 + "/treeData");
            }

            this.genCustomObjService.save(c1);
            j.setMsg("添加自定义对象成功！请到自定义对象管理中查看。");
            return j;
        }
    }

    @ResponseBody
    @RequiresPermissions({"gen:genCustomObj:del"})
    @RequestMapping({"delete"})
    public AjaxJson h(GenCustomObj genCustomObj, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        this.genCustomObjService.delete(genCustomObj);
        j.setMsg("删除自定义对象成功");
        return j;
    }

    @ResponseBody
    @RequiresPermissions({"gen:genCustomObj:del"})
    @RequestMapping({"deleteAll"})
    public AjaxJson x(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String[] a1 = ids.split(",");
        String[] var8 = a1;
        int var7 = a1.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            String id = var8[var6];
            this.genCustomObjService.delete(this.genCustomObjService.get(id));
        }

        j.setMsg("删除自定义对象成功");
        return j;
    }

    @ResponseBody
    @RequiresPermissions({"gen:genCustomObj:export"})
    @RequestMapping(
        value = {"export"},
        method = {RequestMethod.POST}
    )
    public AjaxJson h(GenCustomObj a, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();

        try {
            String a1 = "自定义对象" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<GenCustomObj> b = this.genCustomObjService.findPage(new Page<GenCustomObj>(request, response, -1), a);
            (new ExportExcel("自定义对象", GenCustomObj.class)).setDataList(b.getList()).write(response, a1).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception var8) {
            j.setSuccess(false);
            j.setMsg("导出自定义对象记录失败！失败信息：" + var8.getMessage());
            return j;
        }
    }

    @RequiresPermissions({"gen:genCustomObj:import"})
    @RequestMapping(
        value = {"import"},
        method = {RequestMethod.POST}
    )
    public String e(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int a1 = 0;
            int a2 = 0;
            StringBuilder a3 = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<GenCustomObj> a4 = ei.getDataList(GenCustomObj.class, new int[0]);
            Iterator<GenCustomObj> var9 = a4.iterator();

            while(var9.hasNext()) {
                GenCustomObj a7 = (GenCustomObj)var9.next();

                try {
                    this.genCustomObjService.save(a7);
                    ++a1;
                } catch (ConstraintViolationException var11) {
                    ++a2;
                } catch (Exception var12) {
                    ++a2;
                }
            }

            if (a2 > 0) {
                a3.insert(0, "，失败 " + a2 + " 条自定义对象记录。");
            }

            this.addMessage(redirectAttributes, new String[]{"已成功导入 " + a1 + " 条自定义对象记录" + a3});
        } catch (Exception var13) {
            this.addMessage(redirectAttributes, new String[]{"导入自定义对象失败！失败信息：" + var13.getMessage()});
        }

        return "redirect:" + Global.getAdminPath() + "/gen/genCustomObj/?repage";
    }

    @RequiresPermissions({"gen:genCustomObj:import"})
    @RequestMapping({"import/template"})
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "自定义对象数据导入模板.xlsx";
            List<GenCustomObj> list = Lists.newArrayList();
            (new ExportExcel("自定义对象数据", GenCustomObj.class, 1, new int[0])).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception var5) {
            this.addMessage(redirectAttributes, new String[]{"导入模板下载失败！失败信息：" + var5.getMessage()});
            return "redirect:" + Global.getAdminPath() + "/gen/genCustomObj/?repage";
        }
    }
}
