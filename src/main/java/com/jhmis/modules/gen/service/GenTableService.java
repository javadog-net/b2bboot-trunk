package com.jhmis.modules.gen.service;

import com.jhmis.modules.gen.entity.GenTable;
import com.jhmis.modules.gen.entity.GenTableColumn;
import com.jhmis.modules.gen.mapper.GenDataBaseDictMapper;
import com.jhmis.modules.gen.mapper.GenTableColumnMapper;
import com.jhmis.modules.gen.mapper.GenTableMapper;
import com.jhmis.modules.gen.util.a;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.BaseService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 表单管理服务
 * @author tity
 *
 */
@Service
@Transactional(
    readOnly = true
)
public class GenTableService extends BaseService {
    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;
    @Autowired
    private GenDataBaseDictMapper genDataBaseDictMapper;

    public GenTableService() {
    }

    public List<GenTable> a13() {
        return this.genTableMapper.findAllList(new GenTable());
    }

    @Transactional(
        readOnly = false
    )
    public void n(GenTable c) {
        this.genTableMapper.delete(c);
        this.genTableColumnMapper.deleteByGenTable(c);
    }

    @Transactional(
        readOnly = false
    )
    public void j(String a) {
        if (StringUtils.isNotBlank(a)) {
            this.genTableMapper.buildTable(a);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void b1(GenTable a1) {
        a1.setIsSync("0");
        GenTable a2 = null;
        if (StringUtils.isBlank(a1.getId())) {
            a1.preInsert();
            this.genTableMapper.insert(a1);
        } else {
            a2 = this.a1(a1.getId());
            a1.preUpdate();
            this.genTableMapper.update(a1);
        }

        HashSet<String> a3 = new HashSet<String>();
        Iterator<GenTableColumn> var5 = a1.getColumnList().iterator();

        while(var5.hasNext()) {
            GenTableColumn a4 = (GenTableColumn)var5.next();
            a4.setGenTable(a1);
            if (StringUtils.isBlank(a4.getId())) {
                a4.preInsert();
                this.genTableColumnMapper.insert(a4);
            } else {
                a3.add(a4.getId());
                a4.preUpdate();
                this.genTableColumnMapper.update(a4);
            }
        }

        if (a2 != null) {
            List<GenTableColumn> a5 = a2.getAllColumnList();
            Iterator<GenTableColumn> var6 = a5.iterator();

            while(var6.hasNext()) {
                GenTableColumn c1 = (GenTableColumn)var6.next();
                if (!a3.contains(c1.getId())) {
                    this.genTableColumnMapper.delete(c1);
                }
            }
        }

    }

    public boolean a8(String a7) {
        if (StringUtils.isBlank(a7)) {
            return true;
        } else {
            GenTable a6 = new GenTable();
            a6.setName(a7);
            List<GenTable> list = this.genTableMapper.findList(a6);
            return list.size() == 0;
        }
    }

    public GenTable a1(String id) {
        GenTable a1 = (GenTable)this.genTableMapper.get(id);
        if (a1.getGenIdType() == null) {
            a1.setGenIdType("1");
        }

        GenTableColumn a2 = new GenTableColumn();
        a2.setGenTable(new GenTable(a1.getId()));
        a1.setColumnList(this.genTableColumnMapper.findList(a2));
        a1.setAllColumnList(this.genTableColumnMapper.findListAllStatus(a1));
        return a1;
    }

    public Page<GenTable> a2(Page<GenTable> a5, GenTable a4) {
        a4.setPage(a5);
        a5.setList(this.genTableMapper.findList(a4));
        return a5;
    }

    @Transactional(
        readOnly = false
    )
    public void a2(GenTable a1) {
        a1.setIsSync("0");
        GenTable a2 = null;
        if (StringUtils.isBlank(a1.getId())) {
            a1.preInsert();
            this.genTableMapper.insert(a1);
        } else {
            a2 = this.a1(a1.getId());
            a1.preUpdate();
            this.genTableMapper.update(a1);
        }

        HashSet<String> a3 = new HashSet<String>();
        Iterator<GenTableColumn> var5 = a1.getColumnList().iterator();

        while(var5.hasNext()) {
            GenTableColumn a4 = (GenTableColumn)var5.next();
            a4.setGenTable(a1);
            if (StringUtils.isBlank(a4.getId())) {
                a4.preInsert();
                this.genTableColumnMapper.insert(a4);
            } else {
                a3.add(a4.getId());
                a4.preUpdate();
                this.genTableColumnMapper.update(a4);
            }
        }

        if (a2 != null) {
            List<GenTableColumn> a5 = a2.getAllColumnList();
            Iterator<GenTableColumn> var6 = a5.iterator();

            while(var6.hasNext()) {
                GenTableColumn c1 = (GenTableColumn)var6.next();
                if (!a3.contains(c1.getId())) {
                    this.genTableColumnMapper.delete(c1);
                }
            }
        }

    }

    public List<GenTable> a4(GenTable a6) {
        return this.genDataBaseDictMapper.findTableList(a6);
    }

    public boolean a5(String a7) {
        if (StringUtils.isBlank(a7)) {
            return true;
        } else {
            GenTable a6 = new GenTable();
            a6.setName(a7);
            List<GenTable> list = this.genTableMapper.findList(a6);
            return list.size() == 0;
        }
    }

    @Transactional(
        readOnly = false
    )
    public void d5(GenTable a1) {
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

    public GenTable a5(GenTable a1) {
        if (StringUtils.isNotBlank(a1.getName())) {
            List<GenTable> a2 = this.genDataBaseDictMapper.findTableListByName(a1);
            if (a2.size() > 0) {
                if (StringUtils.isBlank(a1.getId())) {
                    a1 = (GenTable)a2.get(0);
                    if (StringUtils.isBlank(a1.getComments())) {
                        a1.setComments(a1.getName());
                    }

                    a1.setClassName(StringUtils.toCapitalizeCamelCase(a1.getName()));
                }

                List<GenTableColumn> a3 = this.genDataBaseDictMapper.findTableColumnList(a1);
                Iterator<GenTableColumn> var5 = a3.iterator();

                GenTableColumn e;
                boolean b;
                GenTableColumn column;
                Iterator<GenTableColumn> var8;
                while(var5.hasNext()) {
                    e = (GenTableColumn)var5.next();
                    b = false;
                    var8 = a1.getColumnList().iterator();

                    while(var8.hasNext()) {
                        column = (GenTableColumn)var8.next();
                        if (column.getName() != null && column.getName().equals(e.getName())) {
                            b = true;
                        }
                    }

                    if (!b) {
                        a1.getColumnList().add(e);
                    }
                }

                var5 = a1.getColumnList().iterator();

                while(var5.hasNext()) {
                    e = (GenTableColumn)var5.next();
                    b = false;
                    var8 = a3.iterator();

                    while(var8.hasNext()) {
                        column = (GenTableColumn)var8.next();
                        if (column.getName().equals(e.getName())) {
                            b = true;
                        }
                    }

                    if (!b) {
                        e.setDelFlag("1");
                    }
                }

                List<String> a5 = this.genDataBaseDictMapper.findTablePK(a1);
                a1.setPkList(a5);
                if (a5.size() > 0) {
                    Iterator<GenTableColumn> var11 = a1.getColumnList().iterator();

                    label61:
                    while(true) {
                        while(true) {
                            GenTableColumn a6;
                            do {
                                if (!var11.hasNext()) {
                                    break label61;
                                }

                                a6 = (GenTableColumn)var11.next();
                            } while(!((String)a5.get(0)).equalsIgnoreCase(a6.getName()));

                            if (!a6.getJdbcType().toLowerCase().contains("integer") && !a6.getJdbcType().toLowerCase().contains("int")) {
                                a1.setGenIdType("1");
                            } else {
                                a1.setGenIdType("2");
                            }
                        }
                    }
                }

                a.a4(a1);
            }
        }

        return a1;
    }

    public List<GenTable> a3() {
        return this.genTableMapper.findAllList(new GenTable());
    }

    @Transactional(
        readOnly = false
    )
    public void e(GenTable c) {
        this.genTableMapper.delete(c);
        this.genTableColumnMapper.deleteByGenTable(c);
    }

    @Transactional(
        readOnly = false
    )
    public void f(String a) {
        if (StringUtils.isNotBlank(a)) {
            this.genTableMapper.buildTable(a);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void b2(GenTable a2) {
        boolean a1 = true;
        GenTable a3 = null;
        GenTableColumn b1;
        if (StringUtils.isBlank(a2.getId())) {
            a1 = false;
        } else {
            a3 = this.a1(a2.getId());
            if (a3.getColumnList().size() == a2.getColumnList().size() && a3.getName().equals(a2.getName()) && a3.getComments().equals(a2.getComments()) && a3.getGenIdType().equals(a2.getGenIdType())) {
                Iterator<GenTableColumn> var5 = a2.getColumnList().iterator();

                label71:
                while(true) {
                    while(true) {
                        if (!var5.hasNext()) {
                            break label71;
                        }

                        GenTableColumn a4 = (GenTableColumn)var5.next();
                        if (StringUtils.isBlank(a4.getId())) {
                            a1 = false;
                        } else {
                            b1 = (GenTableColumn)this.genTableColumnMapper.get(a4.getId());
                            if (!b1.getName().equals(a4.getName()) || !b1.getJdbcType().equals(a4.getJdbcType()) || !b1.getIsPk().equals(a4.getIsPk()) || !b1.getComments().equals(a4.getComments())) {
                                a1 = false;
                            }
                        }
                    }
                }
            } else {
                a1 = false;
            }
        }

        if (!a1) {
            a2.setIsSync("0");
        }

        if (StringUtils.isBlank(a2.getId())) {
            a2.preInsert();
            this.genTableMapper.insert(a2);
        } else {
            a2.preUpdate();
            a2.setOldName(a3.getOldName());
            a2.setOldComments(a3.getOldComments());
            a2.setOldGenIdType(a3.getOldGenIdType());
            this.genTableMapper.update(a2);
        }

        HashSet<String> a6 = new HashSet<String>();
        Iterator<GenTableColumn> var11 = a2.getColumnList().iterator();

        while(var11.hasNext()) {
            GenTableColumn a7 = (GenTableColumn)var11.next();
            if (StringUtils.isBlank(a7.getId())) {
                a7.setGenTable(a2);
                a7.preInsert();
                this.genTableColumnMapper.insert(a7);
            } else {
                a6.add(a7.getId());
                GenTableColumn a8 = (GenTableColumn)this.genTableColumnMapper.get(a7.getId());
                a7.preUpdate();
                a7.setOldComments(a8.getOldComments());
                a7.setOldIsPk(a8.getOldIsPk());
                a7.setOldJdbcType(a8.getOldJdbcType());
                a7.setOldName(a8.getOldName());
                this.genTableColumnMapper.update(a7);
            }
        }

        if (a3 != null) {
            List<GenTableColumn> a9 = a3.getColumnList();
            Iterator<GenTableColumn> var12 = a9.iterator();

            while(var12.hasNext()) {
                b1 = (GenTableColumn)var12.next();
                if (!a6.contains(b1.getId())) {
                    if (b1.getOldName() != null) {
                        this.genTableColumnMapper.deleteByLogic(b1);
                    } else {
                        this.genTableColumnMapper.delete(b1);
                    }
                }
            }
        }

    }

    public List<GenTable> a4() {
        return this.genTableMapper.findAllList(new GenTable());
    }

    @Transactional(
        readOnly = false
    )
    public void e1(GenTable c) {
        this.genTableMapper.delete(c);
        this.genTableColumnMapper.deleteByGenTable(c);
    }

    @Transactional(
        readOnly = false
    )
    public void g(String a) {
        if (StringUtils.isNotBlank(a)) {
            this.genTableMapper.buildTable(a);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void c(GenTable a1) {
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

    public boolean a7(String a8) {
        if (StringUtils.isBlank(a8)) {
            return true;
        } else {
            GenTable a1 = new GenTable();
            a1.setName(a8);
            List<GenTable> a2 = this.genDataBaseDictMapper.findTableListByName(a1);
            return a2.size() == 0;
        }
    }

    @Transactional(
        readOnly = false
    )
    public void x(String d) {
        String[] a = d.toString().split(";");
        String[] var6 = a;
        int var5 = a.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String c = var6[var4];
            if (StringUtils.isNotBlank(c)) {
                this.genTableMapper.buildTable(c);
            }
        }

    }

    @Transactional(
        readOnly = false
    )
    public void d2(GenTable a1) {
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
    public void xx(GenTable a1) {
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
    public void nv(GenTable c) {
        this.genTableMapper.delete(c);
        this.genTableColumnMapper.deleteByGenTable(c);
    }

    @Transactional(
        readOnly = false
    )
    public void t2(String a) {
        if (StringUtils.isNotBlank(a)) {
            this.genTableMapper.buildTable(a);
        }

    }

    @Transactional(
        readOnly = false
    )
    public void x(GenTable a1) {
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
}
