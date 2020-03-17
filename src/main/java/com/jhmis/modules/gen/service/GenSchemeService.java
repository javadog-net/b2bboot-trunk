package com.jhmis.modules.gen.service;

import com.jhmis.modules.gen.entity.GenConfig;
import com.jhmis.modules.gen.entity.GenScheme;
import com.jhmis.modules.gen.entity.GenTable;
import com.jhmis.modules.gen.entity.GenTableColumn;
import com.jhmis.modules.gen.entity.GenTemplate;
import com.jhmis.modules.gen.mapper.GenSchemeMapper;
import com.jhmis.modules.gen.mapper.GenTableColumnMapper;
import com.jhmis.modules.gen.mapper.GenTableMapper;
import com.jhmis.modules.gen.util.a;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.BaseService;
import com.jhmis.modules.sys.entity.Menu;
import com.jhmis.modules.sys.service.SystemService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 菜单管理
 * @author tity
 *
 */
@Service
@Transactional(
    readOnly = true
)
public class GenSchemeService extends BaseService {
    @Autowired
    private GenSchemeMapper genSchemeMapper;
    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;
    @Autowired
    private SystemService systemService;

    public GenSchemeService() {
    }

    public GenScheme a(String id) {
        return (GenScheme)this.genSchemeMapper.get(id);
    }

    public Page<GenScheme> a1(Page<GenScheme> a2, GenScheme a3) {
        a.a5();
        a3.setPage(a2);
        a2.setList(this.genSchemeMapper.findList(a3));
        return a2;
    }

    @Transactional(
        readOnly = false
    )
    public String a2(GenScheme a3) throws Exception {
        String a4 = this.a3(a3);
        if (StringUtils.isBlank(a3.getId())) {
            a3.preInsert();
            this.genSchemeMapper.insert(a3);
        } else {
            a3.preUpdate();
            this.genSchemeMapper.update(a3);
        }

        return a4;
    }

    @Transactional(
        readOnly = false
    )
    public void c2(GenTable a1) {
        a1.setIsSync("1");
        a1.setOldName(a1.getName());
        a1.setOldComments(a1.getComments());
        a1.setOldGenIdType(a1.getGenIdType());
        Iterator<GenTableColumn> var3 = a1.getAllColumnList().iterator();

        while(var3.hasNext()) {
            GenTableColumn b1 = (GenTableColumn)var3.next();
            if ("1".equals(b1.getDelFlag())) {
                this.genTableColumnMapper.delete(b1);
            } else {
                b1.setOldComments(b1.getComments());
                b1.setOldIsPk(b1.getIsPk());
                b1.setOldJdbcType(b1.getJdbcType());
                b1.setOldName(b1.getName());
                this.genTableColumnMapper.update(b1);
            }
        }

        this.genTableMapper.update(a1);
    }

    @Transactional(
        readOnly = false
    )
    public void eb(GenTable c) {
        this.genTableMapper.delete(c);
        this.genTableColumnMapper.deleteByGenTable(c);
    }

    @Transactional(
        readOnly = false
    )
    public void t(String a) {
        if (StringUtils.isNotBlank(a)) {
            this.genTableMapper.buildTable(a);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void d(GenTable a1) {
        a1.preInsert();
        a1.setOldName(a1.getName());
        a1.setOldComments(a1.getComments());
        a1.setOldGenIdType(a1.getGenIdType());
        this.genTableMapper.insert(a1);
        Iterator<GenTableColumn> var3 = a1.getColumnList().iterator();

        while(var3.hasNext()) {
            GenTableColumn b = (GenTableColumn)var3.next();
            b.setGenTable(a1);
            b.setId((String)null);
            b.preInsert();
            b.setOldComments(b.getComments());
            b.setOldIsPk(b.getIsPk());
            b.setOldJdbcType(b.getJdbcType());
            b.setOldName(b.getName());
            this.genTableColumnMapper.insert(b);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void a4(GenScheme a5, Menu a6) {
        String a7 = StringUtils.lowerCase(a5.getModuleName()) + (StringUtils.isNotBlank(a5.getSubModuleName()) ? ":" + StringUtils.lowerCase(a5.getSubModuleName()) : "") + ":" + StringUtils.uncapitalize(a5.getGenTable().getClassName());
        String a8 = "/" + StringUtils.lowerCase(a5.getModuleName()) + (StringUtils.isNotBlank(a5.getSubModuleName()) ? "/" + StringUtils.lowerCase(a5.getSubModuleName()) : "") + "/" + StringUtils.uncapitalize(a5.getGenTable().getClassName());
        a6.setHref(a8);
        a6.setIsShow("1");
        a6.setType("1");
        a6.setPermission(a7 + ":list");
        this.systemService.saveMenu(a6);
        Menu a2 = new Menu();
        a2.setName("增加");
        a2.setIsShow("0");
        a6.setType("2");
        a2.setSort(Integer.valueOf(30));
        a2.setPermission(a7 + ":add");
        a2.setParent(a6);
        this.systemService.saveMenu(a2);
        Menu a3 = new Menu();
        a3.setName("删除");
        a3.setIsShow("0");
        a6.setType("2");
        a3.setSort(Integer.valueOf(60));
        a3.setPermission(a7 + ":del");
        a3.setParent(a6);
        this.systemService.saveMenu(a3);
        Menu a4 = new Menu();
        a4.setName("编辑");
        a4.setIsShow("0");
        a6.setType("2");
        a4.setSort(Integer.valueOf(90));
        a4.setPermission(a7 + ":edit");
        a4.setParent(a6);
        this.systemService.saveMenu(a4);
        Menu a9 = new Menu();
        a9.setName("查看");
        a9.setIsShow("0");
        a6.setType("2");
        a9.setSort(Integer.valueOf(120));
        a9.setPermission(a7 + ":view");
        a9.setParent(a6);
        this.systemService.saveMenu(a9);
        Menu b1 = new Menu();
        b1.setName("导入");
        b1.setIsShow("0");
        a6.setType("2");
        b1.setSort(Integer.valueOf(150));
        b1.setPermission(a7 + ":import");
        b1.setParent(a6);
        this.systemService.saveMenu(b1);
        Menu b2 = new Menu();
        b2.setName("导出");
        b2.setIsShow("0");
        a6.setType("2");
        b2.setSort(Integer.valueOf(180));
        b2.setPermission(a7 + ":export");
        b2.setParent(a6);
        this.systemService.saveMenu(b2);
    }

    @Transactional(
        readOnly = false
    )
    public void a5(GenScheme a1) {
        this.genSchemeMapper.delete(a1);
    }

    @Transactional(
        readOnly = false
    )
    public void c12(GenTable a1) {
        a1.setIsSync("1");
        a1.setOldName(a1.getName());
        a1.setOldComments(a1.getComments());
        a1.setOldGenIdType(a1.getGenIdType());
        Iterator<GenTableColumn> var3 = a1.getAllColumnList().iterator();

        while(var3.hasNext()) {
            GenTableColumn b1 = (GenTableColumn)var3.next();
            if ("1".equals(b1.getDelFlag())) {
                this.genTableColumnMapper.delete(b1);
            } else {
                b1.setOldComments(b1.getComments());
                b1.setOldIsPk(b1.getIsPk());
                b1.setOldJdbcType(b1.getJdbcType());
                b1.setOldName(b1.getName());
                this.genTableColumnMapper.update(b1);
            }
        }

        this.genTableMapper.update(a1);
    }

    @Transactional(
        readOnly = false
    )
    public void m(GenTable c) {
        this.genTableMapper.delete(c);
        this.genTableColumnMapper.deleteByGenTable(c);
    }

    @Transactional(
        readOnly = false
    )
    public void y(String a) {
        if (StringUtils.isNotBlank(a)) {
            this.genTableMapper.buildTable(a);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void v(GenTable a1) {
        a1.preInsert();
        a1.setOldName(a1.getName());
        a1.setOldComments(a1.getComments());
        a1.setOldGenIdType(a1.getGenIdType());
        this.genTableMapper.insert(a1);
        Iterator<GenTableColumn> var3 = a1.getColumnList().iterator();

        while(var3.hasNext()) {
            GenTableColumn b = (GenTableColumn)var3.next();
            b.setGenTable(a1);
            b.setId((String)null);
            b.preInsert();
            b.setOldComments(b.getComments());
            b.setOldIsPk(b.getIsPk());
            b.setOldJdbcType(b.getJdbcType());
            b.setOldName(b.getName());
            this.genTableColumnMapper.insert(b);
        }

    }

    private String a3(GenScheme a1) throws Exception {
        StringBuilder a2 = new StringBuilder();
        GenTable a3 = (GenTable)this.genTableMapper.get(a1.getGenTable().getId());
        a3.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(a3.getId()))));
        GenConfig a4 = a.a7();
        List<GenTemplate> a5 = a.a8(a4, a1.getCategory(), false, a1.getFormStyle());
        List<GenTemplate> a6 = a.a8(a4, a1.getCategory(), true, a1.getFormStyle());
        if (a6.size() > 0) {
            GenTable a7 = new GenTable();
            a7.setParentTable(a3.getName());
            a3.setChildList(this.genTableMapper.findList(a7));
        }

        String a8 = "";
        Iterator<GenTable> var9 = a3.getChildList().iterator();

        while(var9.hasNext()) {
            GenTable a9 = (GenTable)var9.next();
            a9.setParent(a3);
            a9.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(a9.getId()))));
            a1.setGenTable(a9);
            Iterator<GenTableColumn> var11 = a9.getColumnList().iterator();

            while(var11.hasNext()) {
                GenTableColumn c = (GenTableColumn)var11.next();
                if (c.getName().equals(a9.getParentTableFk())) {
                    a8 = c.getSimpleJavaField();
                }
            }

            Map<String, Object> a11 = a.a9(a1);
            a11.put("childUrlPrefix", a11.get("moduleName") + (StringUtils.isNotBlank(a1.getSubModuleName()) ? "/" + StringUtils.lowerCase(a1.getSubModuleName()) : ""));
            a11.put("functionName", a9.getComments());
            a11.put("functionNameSimple", a9.getComments());
            Iterator<GenTemplate> var12 = a6.iterator();

            while(var12.hasNext()) {
                GenTemplate tpl = (GenTemplate)var12.next();
                a2.append(a.a10(tpl, a11, true, a1.getProjectPath()));
            }
        }

        a1.setGenTable(a3);
        Map<String, Object> a12 = a.a9(a1);
        a12.put("childSimpleJavaField", a8);
        Iterator<GenTemplate> var17 = a5.iterator();

        while(var17.hasNext()) {
            GenTemplate tpl = (GenTemplate)var17.next();
            a2.append(a.a10(tpl, a12, true, a1.getProjectPath()));
        }

        return a2.toString();
    }

    public GenScheme b1(String a1, String a2) {
        return (GenScheme)this.genSchemeMapper.findUniqueByProperty(a1, a2);
    }
}
