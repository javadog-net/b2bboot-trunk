package com.jhmis.modules.gen.web;

import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.MyBeanUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.gen.entity.*;
import com.jhmis.modules.gen.mapper.GenDataBaseDictMapper;
import com.jhmis.modules.gen.mapper.GenTableColumnMapper;
import com.jhmis.modules.gen.mapper.GenTableMapper;
import com.jhmis.modules.gen.mapper.GenTemplateMapper;
import com.jhmis.modules.gen.service.GenCustomObjService;
import com.jhmis.modules.gen.service.GenSchemeService;
import com.jhmis.modules.gen.service.GenTableService;
import com.jhmis.modules.gen.service.GenTemplateService;
import com.jhmis.modules.gen.util.GenException;
import com.jhmis.modules.gen.util.a;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//代码生成主类
@Controller
@RequestMapping(value={"${adminPath}/gen/genTable"})
public class GenTableController
extends BaseController {
    @Autowired
    public GenTemplateService genTemplateService;
    @Autowired
    public GenTableService genTableService;
    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    public GenSchemeService genSchemeService;
    @Autowired
    public GenTemplateMapper genTemplateMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;
    @Autowired
    private GenDataBaseDictMapper genDataBaseDictMapper;
    @Autowired
    private GenCustomObjService genCustomObjService;

    /**
     * 从数据库表导入-保存提交
     * @param name
     * @param model
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:importDb"})
    @RequestMapping(value={"saveTableFromDB"})
    public AjaxJson a3(String name, Model model, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        StringBuffer a1 = new StringBuffer();
        String[] arrstring = name.split(",");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String a3 = arrstring[n2];
            if (StringUtils.isNotBlank(a3)) {
                if (!this.genTableService.a5(a3)) {
                	a1.append("<font color='red'>数据库导入表单" + a3 + " 失败，表已经添加!</font><br/>");
                } else {
                    GenTable a32 = new GenTable();
                    a32.setName(a3);
                    a32 = this.genTableService.a5(a32);
                    a32.setTableType("0");
                    this.genTableService.d(a32);
                    a1.append("<font color='green'>数据库导入表单'" + a32.getName() + "'成功!</font><br/>");
                }
            }
            ++n2;
        }
        j.setSuccess(true);
        j.setMsg(a1.toString());
        return j;
    }

    /**
     * 获取GenTable
     * @param a1
     * @return
     */
    public GenTable a0(GenTable a1) {
        if (StringUtils.isNotBlank(a1.getId())) {
            return this.genTableService.a1(a1.getId());
        }
        return a1;
    }

    public String a3(GenTable c1, HttpServletResponse response, Model model) throws IOException {
        c1 = this.a0(c1);
        model.addAttribute("genTable", c1);
        List<GenCustomObj> f = this.genCustomObjService.findList(new GenCustomObj());
        GenConfig f2 = a.a7();
        for (GenCustomObj genObj : f) {
            GenDict f1 = new GenDict();
            f1.setLabel(genObj.getLabel());
            f1.setValue(genObj.getValue());
            f1.setDescription(genObj.getRemarks());
            f2.getJavaTypeList().add(f1);
        }
        model.addAttribute("config", f2);
        model.addAttribute("tableList", this.genTableService.a3());
        return "modules/gen/genTableForm";
    }

    @RequiresPermissions(value={"gen:genTable:list"})
    @RequestMapping(value={"list", ""})
    public String a11(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, GenException {
        GenTemplate a5;
        if (request.getSession().getAttribute("template") == null) {
            a5 = new GenTemplate();
            a5.setName("0");
            GenTemplate t = this.genTemplateService.a2("0");
            if (t == null) {
                t = new GenTemplate();
                t.setId("0");
                t.setIsNewRecord(true);
                t.setName("0");
                this.genTemplateMapper.insert(t);
            }
            try {
                MyBeanUtils.copyBeanNotNull2Bean(a5, t);
                this.genTemplateService.a1(t);
            }
            catch (Exception exception) {
                // empty catch block
            }
            request.getSession().setAttribute("template", t);
        } else {
            a5 = (GenTemplate)(request.getSession().getAttribute("template"));
        }
        return "modules/gen/genTableList";
    }

    @RequiresPermissions(value={"gen:genTable:list"})
    public String a12(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, GenException {
        GenTemplate a5;
        if (request.getSession().getAttribute("template") == null) {
            a5 = new GenTemplate();
            a5.setName("0");
            GenTemplate t = this.genTemplateService.a2("0");
            if (t == null) {
                t = new GenTemplate();
                t.setId("0");
                t.setIsNewRecord(true);
                t.setName("0");
                this.genTemplateMapper.insert(t);
            }
            try {
                MyBeanUtils.copyBeanNotNull2Bean(a5, t);
                this.genTemplateService.a1(t);
            }
            catch (Exception exception) {
                // empty catch block
            }
            request.getSession().setAttribute("template", t);
        } else {
            a5 = (GenTemplate)(request.getSession().getAttribute("template"));
        }
        return "modules/gen/genTableList";
    }

    /**
     * 编辑表单页面
     * @param c1
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequiresPermissions(value={"gen:genTable:view", "gen:genTable:add", "gen:genTable:edit"}, logical=Logical.OR)
    @RequestMapping(value={"form"})
    public String a(GenTable c1, HttpServletResponse response, Model model) throws IOException {
        c1 = this.a0(c1);
        model.addAttribute("genTable", c1);
        List<GenCustomObj> f = this.genCustomObjService.findList(new GenCustomObj());
        GenConfig f2 = a.a7();
        for (GenCustomObj genObj : f) {
            GenDict f1 = new GenDict();
            f1.setLabel(genObj.getLabel());
            f1.setValue(genObj.getValue());
            f1.setDescription(genObj.getRemarks());
            f2.getJavaTypeList().add(f1);
        }
        model.addAttribute("config", f2);
        model.addAttribute("tableList", this.genTableService.a3());
        return "modules/gen/genTableForm";
    }

    /**
     * 保存业务表提交
     * @param a2
     * @param model
     * @param redirectAttributes
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:add", "gen:genTable:edit"}, logical=Logical.OR)
    @RequestMapping(value={"save"})
    public AjaxJson a4(GenTable a2, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
        GenTable a1 = this.a0(a2);
        a2.setOldComments(a1.getOldComments());
        a2.setOldName(a1.getOldName());
        a2.setOldGenIdType(a1.getOldGenIdType());
        AjaxJson j = new AjaxJson();
        if (!this.beanValidator(model, a2, new Class[0])) {
            j.setSuccess(false);
            j.setMsg("参数错误！");
            return j;
        }
        if (StringUtils.isBlank((CharSequence)a2.getId()) && !this.genTableService.a5(a2.getName())) {
            j.setSuccess(false);
            j.setMsg("添加失败！" + a2.getName() + " 记录已存在！");
            return j;
        }
        if (StringUtils.isBlank((CharSequence)a2.getId()) && !this.genTableService.a7(a2.getName())) {
            j.setSuccess(false);
            j.setMsg("添加失败！" + a2.getName() + "表已经在数据库中存在,请从数据库导入表单！");
            return j;
        }
        if (StringUtils.isNotBlank((CharSequence)a2.getId()) && !a2.getName().equals(a1.getName()) && !this.genTableService.a5(a2.getName())) {
            j.setSuccess(false);
            j.setMsg("重命名失败！" + a2.getName() + " 记录已存在！");
            return j;
        }
        if (StringUtils.isNotBlank(a2.getId()) && !a2.getName().equals(a1.getName()) && !this.genTableService.a7(a2.getName())) {
            j.setSuccess(false);
            j.setMsg("重命名失败！" + a2.getName() + "表已经在数据库中存在,请从数据库导入表单！");
            return j;
        }
        List<GenTableColumn> a4 = a2.getColumnList();
        for (GenTableColumn a5 : a4) {
            if ("gridselect".equals(a5.getShowType())) {
                if ("This".equals(a5.getJavaType())) continue;
                GenCustomObj genCuObj = (GenCustomObj)this.genCustomObjService.findUniqueByProperty("value", a5.getJavaType());
                if (genCuObj == null) {
                    j.setSuccess(false);
                    j.setMsg(String.valueOf(a5.getJavaField()) + "字段对应的显示表单类型你选择了【gridselect】或者【树选择控件】，<br/>但是对应的java类型你没有关联自定义java对象！<br/>");
                    return j;
                }
                a5.setDataUrl(genCuObj.getDataUrl());
                a5.setTableName(genCuObj.getTableName());
                continue;
            }
            if (!"treeselect".equals(a5.getShowType()) || "This".equals(a5.getJavaType()) || a5.getName().equals(a2.getParentTableFk())) continue;
            GenCustomObj a6 = (GenCustomObj)this.genCustomObjService.findUniqueByProperty("value", a5.getJavaType());
            if (a6 == null) {
                j.setSuccess(false);
                j.setMsg(String.valueOf(a5.getJavaField()) + "字段对应的显示表单类型你选择了【gridselect】或者【树选择控件】，<br/>但是对应的java类型你没有关联自定义java对象！<br/>");
                return j;
            }
            a5.setDataUrl(a6.getDataUrl());
            a5.setTableName(a6.getTableName());
        }
        if (StringUtils.isNotBlank(a2.getOldName()) && !this.genTableService.a7(a2.getOldName())) {
            this.genTableService.b2(a2);
        } else {
            this.genTableService.b1(a2);
        }
        j.setSuccess(true);
        j.setMsg("保存业务表'" + a2.getName() + "'成功");
        return j;
    }

    /**
     * 导入数据库表页面
     * @param genTable
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions(value={"gen:genTable:importDb"})
    @RequestMapping(value={"importTableFromDB"})
    public String a1(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
        return "modules/gen/importTableFromDB";
    }

    private boolean a3(GenTable a1, String columnName) {
        List<GenTableColumn> a2 = this.genDataBaseDictMapper.findTableColumnList(a1);
        for (GenTableColumn a3 : a2) {
            if (columnName == null || !columnName.equals(a3.getName())) continue;
            return true;
        }
        return false;
    }

    //导入数据库表列表数据
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:importDb"})
    @RequestMapping(value={"importTableData"})
    public Map<String, Object> a(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
        List<GenTable> a2 = this.genTableService.a4(genTable);
        HashMap<String, Object> x = new HashMap<String, Object>();
        x.put("rows", a2);
        x.put("total", a2.size());
        return x;
    }

    /**
     * 删除业务表
     * @param genTable
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"delete"})
    public AjaxJson a2(GenTable genTable, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        genTable = this.a0(genTable);
        this.genTableService.e(genTable);
        this.genSchemeService.a5(this.genSchemeService.b1("gen_table_id", genTable.getId()));
        j.setSuccess(true);
        j.setMsg("移除业务表记录成功");
        return j;
    }

    /**
     * 删除业务表和数据库表
     * @param x
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"deleteDb"})
    public AjaxJson a1(GenTable x, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        if (Global.isDemoMode().booleanValue()) {
            j.setSuccess(false);
            j.setMsg("演示模式，不允许操作！");
            return j;
        }
        x = this.a0(x);
        this.genTableService.e(x);
        this.genSchemeService.a5(this.genSchemeService.b1("gen_table_id", x.getId()));
        StringBuffer y = new StringBuffer();
        String c2 = Global.getConfig((String)"jdbc.type");
        if ("mysql".equals(c2)) {
            y.append("drop table if exists " + x.getName() + " ;");
        } else if ("oracle".equals(c2)) {
            try {
                y.append("DROP TABLE " + x.getName());
            }
            catch (Exception exception) {}
        } else if ("mssql".equals(c2) || "sqlserver".equals(c2)) {
            y.append("if exists (select * from sysobjects where id = object_id(N'[" + x.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" + x.getName() + "]");
        }
        this.genTableService.f(y.toString());
        j.setSuccess(true);
        j.setMsg("删除业务表记录和数据库表成功!");
        return j;
    }

    /**
     * 删除所有表单
     * @param ids
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:del"})
    @RequestMapping(value={"deleteAll"})
    public AjaxJson b3(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String[] arrstring = ids.split(",");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String id = arrstring[n2];
            this.genTableService.e(this.genTableService.a1(id));
            ++n2;
        }
        j.setSuccess(true);
        j.setMsg("移除业务表成功!");
        return j;
    }

    /**
     * 生成代码提交
     * @param a3
     * @param model
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value={"genCode"})
    public AjaxJson a1(GenScheme a3, Model model, RedirectAttributes redirectAttributes) throws Exception {
        AjaxJson j = new AjaxJson();
        String a1 = "";
        a1 = this.genSchemeService.a2(a3);
        j.setSuccess(true);
        j.setMsg(String.valueOf(a3.getGenTable().getName()) + "代码生成成功<br/>" + a1);
        return j;
    }

    /**
     * 生成代码表单
     * @param tableType
     * @param a1
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions(value={"gen:genTable:genCode"})
    @RequestMapping(value={"genCodeForm"})
    public String a2(String tableType, GenScheme a1, Model model, RedirectAttributes redirectAttributes) {
        GenScheme a3;
        if (StringUtils.isBlank(a1.getPackageName())) {
            a1.setPackageName("com.jhmis.modules");
        }
        if (StringUtils.isBlank(a1.getCategory())) {
            if ("1".equals(tableType)) {
                a1.setCategory("curd_many");
            } else if ("3".equals(tableType)) {
                a1.setCategory("treeTable");
            } else if ("4".equals(tableType)) {
                a1.setCategory("leftTreeRightTable");
            } else {
                a1.setCategory("curd");
            }
        }
        if ((a3 = this.genSchemeService.b1("gen_table_id", a1.getGenTable().getId())) != null) {
            a1 = a3;
        }
        if (StringUtils.isBlank(a1.getProjectPath())) {
            a1.setProjectPath(Global.getProjectPath());
        }
        model.addAttribute("genScheme", a1);
        model.addAttribute("config", a.a7());
        model.addAttribute("tableList", this.genTableService.a3());
        return "modules/gen/genCodeForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

    /**
     * 表单配置列表数据
     * @param b2
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:list"})
    @RequestMapping(value={"data"})
    public Map<String, Object> a(GenTable b2, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        b2 = this.a0(b2);
        User a1 = UserUtils.getUser();
        if (!a1.isAdmin()) {
            b2.setCreateBy(a1);
        }
		Page<GenTable> a6 = this.genTableService.a2(new Page<GenTable>(request, response), b2);
        return this.getBootstrapData(a6);
    }

    /**
     * 同步到数据库
     * @param a4
     * @param isForce
     * @param redirectAttributes
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value={"gen:genTable:synchDb"})
    @RequestMapping(value={"synchDb"})
    public AjaxJson a4(GenTable a4, String isForce, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        if (Global.isDemoMode().booleanValue()) {
            j.setSuccess(false);
            j.setMsg("演示模式，不允许操作！");
            return j;
        }
        String a1 = Global.getConfig((String)"jdbc.type");
        a4 = this.a0(a4);
        List<GenTableColumn> a2 = a4.getColumnList();
        List<GenTableColumn> a3 = a4.getAllColumnList();
        if ("mysql".equals(a1)) {
            if ("1".equals(isForce) && a4.getOldName() != null) {
                GenTable a6;
                StringBuffer a5 = new StringBuffer();
                if (!a4.getName().equalsIgnoreCase(a4.getOldName())) {
                    a5.append("ALTER  TABLE " + a4.getOldName() + " RENAME TO " + a4.getName() + ";");
                    this.genTableService.f("ALTER  TABLE " + a4.getOldName() + " RENAME TO " + a4.getName() + ";");
                    a6 = (GenTable)(this.genTableMapper.get(a4));
                    a6.setOldName(a4.getName());
                    this.genTableMapper.update(a6);
                }
                if (!a4.getComments().equals(a4.getOldComments())) {
                    a5.append("alter table " + a4.getName() + " comment '" + a4.getComments() + "';");
                    this.genTableService.f("alter table " + a4.getName() + " comment '" + a4.getComments() + "';");
                    a6 = (GenTable)(this.genTableMapper.get(a4));
                    a6.setOldComments(a4.getComments());
                    a6.setOldGenIdType(a4.getGenIdType());
                    this.genTableMapper.update(a6);
                }
                for (GenTableColumn a8 : a3) {
                    String a7 = a8.getOldName();
                    if (!a8.getDelFlag().equals("1") || !this.a3(a4, a7)) continue;
                    a5.append("alter table " + a4.getName() + " drop " + a7 + ";");
                    this.genTableService.f("alter table " + a4.getName() + " drop " + a7 + ";");
                    this.genTableColumnMapper.delete(a8);
                }
                for (GenTableColumn a9 : a3) {
                    String a10 = a9.getOldName();
                    String a11 = a9.getName();
                    if (a9.getDelFlag().equals("1") || a10 == null || a11.equals(a10) && a9.getJdbcType().equals(a9.getOldJdbcType()) && a9.getComments().equals(a9.getOldComments())) continue;
                    String a12 = StringUtils.isBlank((CharSequence)a9.getOldName()) ? a9.getName() : a9.getOldName();
                    a5.append("alter table " + a4.getName() + " change  " + a10 + " " + a11 + " " + a9.getJdbcType() + " comment '" + a9.getComments() + "';");
                    this.genTableService.f("alter table " + a4.getName() + " change  " + a12 + " " + a9.getName() + " " + a9.getJdbcType() + " comment '" + a9.getComments() + "';");
                    GenTableColumn a13 = (GenTableColumn)(this.genTableColumnMapper.get(a9.getId()));
                    a13.setOldComments(a9.getComments());
                    a13.setOldIsPk(a9.getIsPk());
                    a13.setOldJdbcType(a9.getJdbcType());
                    a13.setOldName(a9.getName());
                    this.genTableColumnMapper.update(a13);
                }
                for (GenTableColumn a12 : a3) {
                    String b1 = a12.getOldName();
                    String b2 = a12.getName();
                    if (a12.getDelFlag().equals("1") || b1 != null) continue;
                    a5.append("alter table " + a4.getName() + " add " + b2 + " " + a12.getJdbcType() + " comment '" + a12.getComments() + "';");
                    this.genTableService.f("alter table " + a4.getName() + " add " + a12.getName() + " " + a12.getJdbcType() + " comment '" + a12.getComments() + "';");
                    GenTableColumn b3 = (GenTableColumn)(this.genTableColumnMapper.get(a12.getId()));
                    b3.setOldComments(a12.getComments());
                    b3.setOldIsPk(a12.getIsPk());
                    b3.setOldJdbcType(a12.getJdbcType());
                    b3.setOldName(a12.getName());
                    this.genTableColumnMapper.update(b3);
                }
                if (a4.getGenIdType() != null && !a4.getGenIdType().equals(a4.getOldGenIdType())) {
                    for (GenTableColumn b1 : a3) {
                        if (b1.getDelFlag().equals("1") || b1.getName() == null || !b1.getIsPk().equals("1")) continue;
                        if (a4.getGenIdType().equals("2")) {
                            String b2 = b1.getJdbcType();
                            if (!b2.toLowerCase().contains("int") && !b2.toLowerCase().contains("integer")) {
                                b2 = "integer";
                                b1.setJdbcType(b2);
                                GenTableColumn b3 = (GenTableColumn)(this.genTableColumnMapper.get(b1.getId()));
                                b3.setJdbcType(b2);
                                b3.setOldJdbcType(b2);
                                this.genTableColumnMapper.update(b3);
                            }
                            this.genTableService.f("alter table " + a4.getName() + " change   " + b1.getName() + " " + b1.getName() + " " + (String)b2 + " auto_increment ;");
                            continue;
                        }
                        String b4 = b1.getJdbcType();
                        if (!b4.toLowerCase().contains("varchar")) {
                            b4 = "varchar(64)";
                            b1.setJdbcType(b4);
                            GenTableColumn b5 = (GenTableColumn)(this.genTableColumnMapper.get(b1.getId()));
                            b5.setJdbcType(b4);
                            b5.setOldJdbcType(b4);
                            this.genTableColumnMapper.update(b5);
                        }
                        this.genTableService.f("alter table " + a4.getName() + " change   " + b1.getName() + " " + b1.getName() + " " + (String)b4 + " ;");
                    }
                }
                String a11 = "";
                String b11 = "";
                for (GenTableColumn a62 : a2) {
                    if (a62.getIsPk().equals("1")) {
                        a11 = String.valueOf(a11) + a62.getName() + ",";
                    }
                    if (!"1".equals(a62.getOldIsPk())) continue;
                    b11 = String.valueOf(b11) + a62.getName() + ",";
                }
                if (!b11.equals(a11)) {
                    a5.append("alter table " + a4.getName() + " drop primary key;");
                    this.genTableService.f("alter table " + a4.getName() + " drop primary key;");
                    for (GenTableColumn a9 : a2) {
                        if (!"1".equals(a9.getOldIsPk())) continue;
                        GenTableColumn b2 = (GenTableColumn)(this.genTableColumnMapper.get(a9.getId()));
                        b2.setOldIsPk("0");
                        this.genTableColumnMapper.update(b2);
                    }
                    if (a11.length() > 0) {
                        a5.append("alter table " + a4.getName() + " add  CONSTRAINT PK_SJ_RESOURCE_CHARGES PRIMARY KEY(" + a11.substring(0, a11.length() - 1) + ");");
                        this.genTableService.f("alter table " + a4.getName() + " add  CONSTRAINT PK_SJ_RESOURCE_CHARGES PRIMARY KEY(" + a11.substring(0, a11.length() - 1) + ");");
                        for (GenTableColumn b3 : a2) {
                            if (!"1".equals(b3.getIsPk())) continue;
                            GenTableColumn x = (GenTableColumn)(this.genTableColumnMapper.get(b3.getId()));
                            x.setOldIsPk("1");
                            this.genTableColumnMapper.update(x);
                        }
                    }
                }
                j.setMsg("同步数据库表成功!");
            } else {
                StringBuffer b3 = new StringBuffer();
                if (StringUtils.isNotBlank(a4.getOldName())) {
                    b3.append("drop table if exists " + a4.getOldName() + " ;");
                } else {
                    b3.append("drop table if exists " + a4.getName() + " ;");
                }
                this.genTableService.f(b3.toString());
                GenTable g = (GenTable)(this.genTableMapper.get(a4));
                g.setOldName(a4.getName());
                this.genTableMapper.update(g);
                b3 = new StringBuffer();
                b3.append("create table " + a4.getName() + " (");
                String b4 = "";
                for (GenTableColumn b2 : a3) {
                    if (b2.getDelFlag().equals("1") || b2.getName() == null) continue;
                    if (b2.getIsPk().equals("1")) {
                        if (a4.getGenIdType().equals("2")) {
                            String c1 = b2.getJdbcType();
                            if (!c1.toLowerCase().contains("int") && !c1.toLowerCase().contains("integer")) {
                                c1 = "integer";
                                b2.setJdbcType(c1);
                            }
                            b3.append("  " + b2.getName() + " " + c1 + " auto_increment  comment '" + b2.getComments() + "',");
                        } else {
                            String c2 = b2.getJdbcType();
                            if (!c2.toLowerCase().contains("varchar")) {
                                c2 = "varchar(64)";
                                b2.setJdbcType(c2);
                            }
                            b3.append("  " + b2.getName() + " " + c2 + " comment '" + b2.getComments() + "',");
                        }
                        b4 = String.valueOf(b4) + b2.getName() + ",";
                        continue;
                    }
                    b3.append("  " + b2.getName() + " " + b2.getJdbcType() + " comment '" + b2.getComments() + "',");
                }
                b3.append("primary key (" + b4.substring(0, b4.length() - 1) + ") ");
                b3.append(") comment '" + a4.getComments() + "' DEFAULT CHARSET=utf8");
                this.genTableService.f(b3.toString());
                j.setMsg("重新建表成功!");
            }
        } else if ("oracle".equals(a1)) {
            if ("1".equals(isForce) && a4.getOldName() != null) {
                StringBuffer c7 = new StringBuffer();
                if (!a4.getName().equalsIgnoreCase(a4.getOldName())) {
                    c7.append("ALTER  TABLE " + a4.getOldName() + " RENAME TO " + a4.getName());
                    this.genTableService.f("ALTER  TABLE " + a4.getOldName() + " RENAME TO " + a4.getName());
                    GenTable b4 = (GenTable)(this.genTableMapper.get(a4));
                    b4.setOldName(a4.getName());
                    this.genTableMapper.update(b4);
                }
                if (!a4.getComments().equals(a4.getOldComments())) {
                    c7.append("alter table " + a4.getName() + " comment '" + a4.getComments() + "';");
                    this.genTableService.f("comment on table " + a4.getName() + " is '" + a4.getComments() + "'");
                    GenTable g6 = (GenTable)(this.genTableMapper.get(a4));
                    g6.setOldComments(a4.getComments());
                    g6.setOldGenIdType(a4.getGenIdType());
                    this.genTableMapper.update(g6);
                }
                for (GenTableColumn w1 : a3) {
                    String x1 = w1.getOldName();
                    if (!w1.getDelFlag().equals("1") || !this.a3(a4, x1)) continue;
                    c7.append("alter table " + a4.getName() + " drop column " + x1);
                    this.genTableService.f("alter table " + a4.getName() + " drop column " + x1);
                    this.genTableColumnMapper.delete(w1);
                }
                for (GenTableColumn f1 : a3) {
                    String v1 = f1.getOldName();
                    String r1 = f1.getName();
                    if (f1.getDelFlag().equals("1") || v1 == null || r1.equals(v1) && f1.getJdbcType().equals(f1.getOldJdbcType()) && f1.getComments().equals(f1.getOldComments())) continue;
                    String s1 = StringUtils.isBlank(f1.getOldName()) ? f1.getName() : f1.getOldName();
                    c7.append("alter table " + a4.getName() + " modify  " + v1 + " " + r1 + " " + f1.getJdbcType() + " comment '" + f1.getComments() + "';");
                    if (!r1.equals(v1)) {
                        this.genTableService.f("alter table " + a4.getName() + " rename  column " + s1 + " to " + f1.getName());
                    }
                    if (!f1.getJdbcType().equals(f1.getOldJdbcType())) {
                        this.genTableService.f("alter table " + a4.getName() + " modify   " + f1.getName() + " " + f1.getJdbcType());
                    }
                    if (!f1.getComments().equals(f1.getOldComments())) {
                        this.genTableService.f("comment on column " + a4.getName() + "." + f1.getName() + " is '" + f1.getComments() + "'");
                    }
                    GenTableColumn c1 = (GenTableColumn)(this.genTableColumnMapper.get(f1.getId()));
                    c1.setOldComments(f1.getComments());
                    c1.setOldIsPk(f1.getIsPk());
                    c1.setOldJdbcType(f1.getJdbcType());
                    c1.setOldName(f1.getName());
                    this.genTableColumnMapper.update(c1);
                }
                for (GenTableColumn d1 : a3) {
                    String c1 = d1.getOldName();
                    String d2 = d1.getName();
                    if (d1.getDelFlag().equals("1") || c1 != null) continue;
                    c7.append("alter table " + a4.getName() + " add " + d2 + " " + d1.getJdbcType() + " comment '" + d1.getComments() + "';");
                    this.genTableService.f("alter table " + a4.getName() + " add " + d1.getName() + " " + d1.getJdbcType());
                    this.genTableService.f("comment on column " + a4.getName() + "." + d1.getName() + " is '" + d1.getComments() + "'");
                    GenTableColumn x = (GenTableColumn)(this.genTableColumnMapper.get(d1.getId()));
                    x.setOldComments(d1.getComments());
                    x.setOldIsPk(d1.getIsPk());
                    x.setOldJdbcType(d1.getJdbcType());
                    x.setOldName(d1.getName());
                    this.genTableColumnMapper.update(x);
                }
                if (a4.getGenIdType() != null && !a4.getGenIdType().equals(a4.getOldGenIdType())) {
                    for (GenTableColumn a11 : a3) {
                        if (a11.getDelFlag().equals("1") || a11.getName() == null || !a11.getIsPk().equals("1")) continue;
                        if (a4.getGenIdType().equals("2")) {
                            String c2 = a11.getJdbcType();
                            if (c2.toLowerCase().contains("number")) continue;
                            c2 = "number";
                            a11.setJdbcType(c2);
                            GenTableColumn g11 = (GenTableColumn)(this.genTableColumnMapper.get(a11.getId()));
                            g11.setJdbcType(c2);
                            g11.setOldJdbcType(c2);
                            this.genTableColumnMapper.update(g11);
                            this.genTableService.f("alter table " + a4.getName() + " modify   " + a11.getName() + " " + c2);
                            continue;
                        }
                        String x1 = a11.getJdbcType();
                        if (x1.toLowerCase().contains("varchar2")) continue;
                        x1 = "varchar2(64)";
                        a11.setJdbcType(x1);
                        GenTableColumn b1 = (GenTableColumn)(this.genTableColumnMapper.get(a11.getId()));
                        b1.setJdbcType(x1);
                        b1.setOldJdbcType(x1);
                        this.genTableColumnMapper.update(b1);
                        this.genTableService.f("alter table " + a4.getName() + " modify   " + a11.getName() + " " + x1);
                    }
                }
                String v1 = "";
                String n1 = "";
                for (GenTableColumn m1 : a2) {
                    if (m1.getIsPk().equals("1")) {
                        n1 = String.valueOf(n1) + m1.getName() + ",";
                    }
                    if (!"1".equals(m1.getOldIsPk())) continue;
                    v1 = String.valueOf(v1) + m1.getName() + ",";
                }
                if (!v1.equals(n1)) {
                    c7.append("alter table " + a4.getName() + " drop primary key;");
                    this.genTableService.f("alter table " + a4.getName() + " drop constraint PK_" + a4.getName() + "_" + v1.substring(0, v1.length() - 1));
                    for (GenTableColumn t1 : a2) {
                        if (!"1".equals(t1.getOldIsPk())) continue;
                        GenTableColumn x12 = (GenTableColumn)(this.genTableColumnMapper.get(t1.getId()));
                        x12.setOldIsPk("0");
                        this.genTableColumnMapper.update(x12);
                    }
                    if (n1.length() > 0) {
                        c7.append("alter table " + a4.getName() + " add  CONSTRAINT PK_SJ_RESOURCE_CHARGES PRIMARY KEY(" + n1.substring(0, n1.length() - 1) + ");");
                        this.genTableService.f("alter table " + a4.getName() + " add constraint PK_" + a4.getName() + "_" + n1.substring(0, n1.length() - 1) + " primary key (" + n1.substring(0, n1.length() - 1) + ") ");
                        if (a4.getGenIdType().equals("2")) {
                            try {
                                this.genTableService.f("CREATE SEQUENCE " + a4.getName() + "_sequence" + " INCREMENT BY 1" + " START WITH 1" + " NOMAXVALUE" + " NOCYCLE" + " NOCACHE");
                                this.genTableService.f("create trigger " + a4.getName() + "_trig before" + " insert on " + a4.getName() + " for each row when (new." + n1 + " is null)" + " begin" + " select " + a4.getName() + "_sequence.nextval into:new." + n1 + " from dual;" + " end;");
                            }
                            catch (Exception t1) {
                                // empty catch block
                            }
                        }
                        for (GenTableColumn c1 : a2) {
                            if (!"1".equals(c1.getIsPk())) continue;
                            GenTableColumn nn = (GenTableColumn)(this.genTableColumnMapper.get(c1.getId()));
                            nn.setOldIsPk("1");
                            this.genTableColumnMapper.update(nn);
                        }
                    }
                }
                j.setMsg("同步数据库表成功!");
            } else {
                StringBuffer s = new StringBuffer();
                try {
                    if (StringUtils.isNotBlank(a4.getOldName())) {
                        s.append("DROP TABLE " + a4.getOldName());
                    } else {
                        s.append("DROP TABLE " + a4.getName());
                    }
                    this.genTableService.f(s.toString());
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                GenTable d2 = (GenTable)(this.genTableMapper.get(a4));
                d2.setOldName(a4.getName());
                this.genTableMapper.update(d2);
                s = new StringBuffer();
                s.append("create table " + a4.getName() + " (");
                String x4 = "";
                for (GenTableColumn g1 : a3) {
                    if (g1.getDelFlag().equals("1") || g1.getName() == null) continue;
                    String c1 = g1.getJdbcType();
                    if (c1.equalsIgnoreCase("integer")) {
                        c1 = "number(10,0)";
                    } else if (c1.equalsIgnoreCase("datetime")) {
                        c1 = "date";
                    } else if (c1.contains("nvarchar(")) {
                        c1 = c1.replace("nvarchar", "nvarchar2");
                    } else if (c1.contains("varchar(")) {
                        c1 = c1.replace("varchar", "varchar2");
                    } else if (c1.equalsIgnoreCase("double")) {
                        c1 = "float(24)";
                    } else if (c1.equalsIgnoreCase("longblob")) {
                        c1 = "blob";
                    } else if (c1.equalsIgnoreCase("longtext")) {
                        c1 = "clob";
                    }
                    if (g1.getIsPk().equals("1")) {
                        if (a4.getGenIdType().equals("2")) {
                            String bb = g1.getJdbcType();
                            if (!bb.toLowerCase().contains("int") && !bb.toLowerCase().contains("integer")) {
                                bb = "number";
                                g1.setJdbcType(bb);
                            }
                            s.append("  " + g1.getName() + " " + bb + ",");
                        } else {
                            s.append("  " + g1.getName() + " " + c1 + ",");
                        }
                        x4 = String.valueOf(x4) + g1.getName();
                        continue;
                    }
                    s.append("  " + g1.getName() + " " + c1 + ",");
                }
                s = new StringBuffer(String.valueOf(s.substring(0, s.length() - 1)) + ")");
                this.genTableService.f(s.toString());
                this.genTableService.f("comment on table " + a4.getName() + " is  '" + a4.getComments() + "'");
                for (GenTableColumn tt : a2) {
                    this.genTableService.f("comment on column " + a4.getName() + "." + tt.getName() + " is  '" + tt.getComments() + "'");
                }
                this.genTableService.f("alter table " + a4.getName() + " add constraint PK_" + a4.getName() + "_" + x4 + " primary key (" + x4 + ") ");
                if (a4.getGenIdType().equals("2")) {
                    try {
                        this.genTableService.f("CREATE SEQUENCE " + a4.getName() + "_sequence" + " INCREMENT BY 1" + " START WITH 1" + " NOMAXVALUE" + " NOCYCLE" + " NOCACHE");
                        this.genTableService.f("create trigger " + a4.getName() + "_trig before" + " insert on " + a4.getName() + " for each row when (new." + x4 + " is null)" + " begin" + " select " + a4.getName() + "_sequence.nextval into:new." + x4 + " from dual;" + " end;");
                    }
                    catch (Exception tt) {
                        // empty catch block
                    }
                }
                j.setMsg("重新建表成功!");
            }
        } else if ("mssql".equals(a1) || "sqlserver".equals(a1)) {
            if ("1".equals(isForce) && a4.getOldName() != null) {
                StringBuffer d11 = new StringBuffer();
                if (!a4.getName().equalsIgnoreCase(a4.getOldName())) {
                    d11.append("EXEC   sp_rename   '" + a4.getOldName() + "',   '" + a4.getName() + "' ");
                    this.genTableService.f("EXEC   sp_rename   '" + a4.getOldName() + "',   '" + a4.getName() + "' ");
                    GenTable a11 = (GenTable)(this.genTableMapper.get(a4));
                    a11.setOldName(a4.getName());
                    this.genTableMapper.update(a11);
                }
                if (!a4.getComments().equals(a4.getOldComments())) {
                    d11.append("alter table " + a4.getName() + " comment '" + a4.getComments() + "';");
                    this.genTableService.f("if not exists (SELECT  A.name,        C.value  FROM sys.tables A  inner JOIN sys.extended_properties C ON C.major_id = A.object_id  and minor_id=0  WHERE A.name = N'" + a4.getName() + "') " + " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'" + a4.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "'" + " EXEC sp_updateextendedproperty @name=N'MS_Description', @value=N'" + a4.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "'");
                    GenTable c2 = (GenTable)(this.genTableMapper.get(a4));
                    c2.setOldComments(a4.getComments());
                    c2.setOldGenIdType(a4.getGenIdType());
                    this.genTableMapper.update(c2);
                }
                for (GenTableColumn d1 : a3) {
                    String d2 = d1.getOldName();
                    if (!d1.getDelFlag().equals("1") || !this.a3(a4, d2)) continue;
                    d11.append("alter table " + a4.getName() + " drop COLUMN " + d2 + ";");
                    this.genTableService.f("alter table " + a4.getName() + " drop column " + d2 + ";");
                    this.genTableColumnMapper.delete(d1);
                }
                for (GenTableColumn a31 : a3) {
                    String a7 = a31.getOldName();
                    String d23 = a31.getName();
                    if (a31.getDelFlag().equals("1") || a7 == null || d23.equals(a7) && a31.getJdbcType().equals(a31.getOldJdbcType()) && a31.getComments().equals(a31.getOldComments())) continue;
                    String c5 = StringUtils.isBlank((CharSequence)a31.getOldName()) ? a31.getName() : a31.getOldName();
                    GenTableColumn c6 = (GenTableColumn)(this.genTableColumnMapper.get(a31.getId()));
                    if (!d23.equals(a7)) {
                        this.genTableService.f("EXEC sp_rename '" + a4.getName() + ".[" + c5 + "]', '" + a31.getName() + "', 'COLUMN';");
                        c6.setOldName(a31.getName());
                        this.genTableColumnMapper.update(c6);
                    }
                    if (!a31.getJdbcType().equals(a31.getOldJdbcType())) {
                        this.genTableService.f("alter table " + a4.getName() + " alter  column " + a31.getName() + " " + a31.getJdbcType() + ";");
                        c6.setOldName(a31.getJdbcType());
                        this.genTableColumnMapper.update(c6);
                    }
                    if (a31.getComments().equals(a31.getOldComments())) continue;
                    this.genTableService.f("if not exists (SELECT C.value AS column_description FROM sys.tables A INNER JOIN sys.columns B ON B.object_id = A.object_id INNER JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = N'" + a4.getName() + "' and B.name=N'" + a31.getName() + "')" + " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'" + a31.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "', @level2type=N'COLUMN',@level2name=N'" + a31.getName() + "'" + " EXEC sp_updateextendedproperty @name=N'MS_Description', @value=N'" + a31.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "', @level2type=N'COLUMN',@level2name=N'" + a31.getName() + "'");
                    c6.setOldName(a31.getComments());
                    this.genTableColumnMapper.update(c6);
                }
                for (GenTableColumn b1 : a3) {
                    String b2 = b1.getOldName();
                    String b3 = b1.getName();
                    if (b1.getDelFlag().equals("1") || b2 != null) continue;
                    d11.append("alter table " + a4.getName() + " add " + b3 + " " + b1.getJdbcType() + "';");
                    this.genTableService.f("alter table " + a4.getName() + " add " + b1.getName() + " " + b1.getJdbcType() + ";");
                    this.genTableService.f("if not exists (SELECT C.value AS column_description FROM sys.tables A INNER JOIN sys.columns B ON B.object_id = A.object_id INNER JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = N'" + a4.getName() + "' and B.name=N'" + b1.getName() + "')" + " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'" + b1.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "', @level2type=N'COLUMN',@level2name=N'" + b1.getName() + "'" + " EXEC sp_updateextendedproperty @name=N'MS_Description', @value=N'" + b1.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "', @level2type=N'COLUMN',@level2name=N'" + b1.getName() + "'");
                    GenTableColumn d1 = (GenTableColumn)(this.genTableColumnMapper.get(b1.getId()));
                    d1.setOldComments(b1.getComments());
                    d1.setOldIsPk(b1.getIsPk());
                    d1.setOldJdbcType(b1.getJdbcType());
                    d1.setOldName(b1.getName());
                    this.genTableColumnMapper.update(d1);
                }
                if (a4.getGenIdType() != null && !a4.getGenIdType().equals(a4.getOldGenIdType())) {
                    for (GenTableColumn x1 : a3) {
                        if (x1.getDelFlag().equals("1") || x1.getName() == null || !x1.getIsPk().equals("1")) continue;
                        if (a4.getGenIdType().equals("2")) {
                            String jdbcType = x1.getJdbcType();
                            if (!jdbcType.toLowerCase().contains("int") && !jdbcType.toLowerCase().contains("integer")) {
                                jdbcType = "int";
                                x1.setJdbcType(jdbcType);
                                GenTableColumn a5 = (GenTableColumn)(this.genTableColumnMapper.get(x1.getId()));
                                a5.setJdbcType(jdbcType);
                                a5.setOldJdbcType(jdbcType);
                                this.genTableColumnMapper.update(a5);
                            }
                            this.genTableService.f("Declare @Pk varChar(100) Select @Pk=Name from sysobjects where Parent_Obj=OBJECT_ID('" + a4.getName() + "') and xtype='PK' " + " if @Pk is not null" + " exec('Alter table " + a4.getName() + " Drop '+ @Pk) ");
                            this.genTableService.f("alter table " + a4.getName() + " alter   column " + x1.getName() + " " + (String)jdbcType + "  not null;");
                            this.genTableService.f("alter table " + a4.getName() + " add   constraint pkid primary key(" + x1.getName() + ") ;");
                            continue;
                        }
                        String jdbcType = x1.getJdbcType();
                        if (!jdbcType.toLowerCase().contains("varchar")) {
                            jdbcType = "varchar(64)";
                            x1.setJdbcType(jdbcType);
                            GenTableColumn c12 = (GenTableColumn)(this.genTableColumnMapper.get(x1.getId()));
                            c12.setJdbcType(jdbcType);
                            c12.setOldJdbcType(jdbcType);
                            this.genTableColumnMapper.update(c12);
                        }
                        this.genTableService.f("Declare @Pk varChar(100) Select @Pk=Name from sysobjects where Parent_Obj=OBJECT_ID('" + a4.getName() + "') and xtype='PK' " + " if @Pk is not null " + " exec('Alter table " + a4.getName() + " Drop '+ @Pk) ");
                        this.genTableService.f("alter table " + a4.getName() + " alter column   " + x1.getName() + " " + (String)jdbcType + " not null ;");
                        this.genTableService.f("alter table " + a4.getName() + " add   constraint pkid primary key(" + x1.getName() + ") ;");
                    }
                }
                String c2 = "";
                String c3 = "";
                for (GenTableColumn c4 : a2) {
                    if (c4.getIsPk().equals("1")) {
                        c2 = String.valueOf(c2) + c4.getName() + ",";
                    }
                    if (!"1".equals(c4.getOldIsPk())) continue;
                    c3 = String.valueOf(c3) + c4.getName() + ",";
                }
                if (!c3.equals(c2)) {
                    this.genTableService.f("Declare @Pk varChar(100) Select @Pk=Name from sysobjects where Parent_Obj=OBJECT_ID('" + a4.getName() + "') and xtype='PK' " + " if @Pk is not null " + " exec('Alter table " + a4.getName() + " Drop '+ @Pk) ");
                    for (GenTableColumn b1 : a2) {
                        if (!"1".equals(b1.getOldIsPk())) continue;
                        GenTableColumn a6 = (GenTableColumn)(this.genTableColumnMapper.get(b1.getId()));
                        a6.setOldIsPk("0");
                        this.genTableColumnMapper.update(a6);
                    }
                    if (c2.length() > 0) {
                        this.genTableService.f("alter table " + a4.getName() + " add   constraint pkid primary key(" + c2.substring(0, c2.length() - 1) + ") ;");
                        for (GenTableColumn column : a2) {
                            if (!"1".equals(column.getIsPk())) continue;
                            GenTableColumn copyColumn = (GenTableColumn)(this.genTableColumnMapper.get(column.getId()));
                            copyColumn.setOldIsPk("1");
                            this.genTableColumnMapper.update(copyColumn);
                        }
                    }
                }
                j.setMsg("同步数据库表成功!");
            } else {
                StringBuffer d1 = new StringBuffer();
                d1.append("if exists (select * from sysobjects where id = object_id(N'[" + a4.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" + a4.getName() + "]");
                this.genTableService.f(d1.toString());
                d1 = new StringBuffer();
                d1.append("create table " + a4.getName() + " (");
                for (GenTableColumn a7 : a2) {
                    if (a7.getIsPk().equals("1")) {
                        if (a4.getGenIdType().equals("2")) {
                            d1.append(String.valueOf(a7.getName()) + " int identity(1,1) primary key ,");
                            continue;
                        }
                        d1.append(String.valueOf(a7.getName()) + " varchar(64) primary key,");
                        continue;
                    }
                    d1.append("  " + a7.getName() + " " + a7.getJdbcType() + ",");
                }
                this.genTableService.f(String.valueOf(d1.toString().substring(0, d1.toString().length() - 1)) + ")");
                this.genTableService.f("if not exists (SELECT  A.name,        C.value  FROM sys.tables A  inner JOIN sys.extended_properties C ON C.major_id = A.object_id  and minor_id=0  WHERE A.name = N'" + a4.getName() + "') " + " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'" + a4.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "'" + " EXEC sp_updateextendedproperty @name=N'MS_Description', @value=N'" + a4.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "'");
                for (GenTableColumn column : a2) {
                    this.genTableService.f("if not exists (SELECT C.value AS column_description FROM sys.tables A INNER JOIN sys.columns B ON B.object_id = A.object_id INNER JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = N'" + a4.getName() + "' and B.name=N'" + column.getName() + "')" + " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'" + column.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "', @level2type=N'COLUMN',@level2name=N'" + column.getName() + "'" + " EXEC sp_updateextendedproperty @name=N'MS_Description', @value=N'" + column.getComments() + "' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'" + a4.getName() + "', @level2type=N'COLUMN',@level2name=N'" + column.getName() + "'");
                }
                j.setMsg("重新建表成功!");
            }
        }
        this.genTableService.c(a4);
        j.setSuccess(true);
        return j;
    }
}

