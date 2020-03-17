package com.jhmis.modules.gen.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.modules.gen.entity.GenScheme;
import com.jhmis.modules.gen.entity.GenTable;
import com.jhmis.modules.gen.entity.GenTableColumn;
import com.jhmis.modules.gen.mapper.GenDataBaseDictMapper;
import com.jhmis.modules.gen.mapper.GenTableColumnMapper;
import com.jhmis.modules.gen.mapper.GenTableMapper;
import com.jhmis.modules.gen.template.FreemarkerHelper;
import com.jhmis.modules.gen.util.a;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.BaseService;
/**
 * 动态列表管理服务
 * @author tity
 *
 */
@Service
@Transactional(
    readOnly = true
)
public class CgAutoListService extends BaseService {
    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;
    @Autowired
    private GenDataBaseDictMapper genDataBaseDictMapper;

    public CgAutoListService() {
    }

    public GenTable get(String id) {
        GenTable genTable = (GenTable)this.genTableMapper.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(this.genTableColumnMapper.findList(genTableColumn));
        return genTable;
    }

    public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
        genTable.setPage(page);
        page.setList(this.genTableMapper.findList(genTable));
        return page;
    }

    public List<GenTable> findAll() {
        return this.genTableMapper.findAllList(new GenTable());
    }

    public List<GenTable> findTableListFormDb(GenTable genTable) {
        return this.genDataBaseDictMapper.findTableList(genTable);
    }

    public boolean checkTableName(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        } else {
            GenTable genTable = new GenTable();
            genTable.setName(tableName);
            List<GenTable> list = this.genTableMapper.findList(genTable);
            return list.size() == 0;
        }
    }

    public boolean checkTableNameFromDB(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        } else {
            GenTable genTable = new GenTable();
            genTable.setName(tableName);
            List<GenTable> list = this.genDataBaseDictMapper.findTableListByName(genTable);
            return list.size() == 0;
        }
    }

    public String generateCode(GenScheme genScheme) {
        new StringBuilder();
        GenTable genTable = (GenTable)this.genTableMapper.get(genScheme.getGenTable().getId());
        genTable.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        //GenConfig config = a.a7();
        genScheme.setGenTable(genTable);
        Map<String, Object> model = a.a9(genScheme);
        FreemarkerHelper viewEngine = new FreemarkerHelper();
        String html = viewEngine.parseTemplate("/com/jhmis/modules/gen/template/viewList.ftl", model);
        return html;
    }

    public String generateListCode(GenScheme genScheme) {
        new StringBuilder();
        GenTable genTable = (GenTable)this.genTableMapper.get(genScheme.getGenTable().getId());
        genTable.setColumnList(this.genTableColumnMapper.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        //GenConfig config = a.a7();
        genScheme.setGenTable(genTable);
        Map<String, Object> model = a.a9(genScheme);
        FreemarkerHelper viewEngine = new FreemarkerHelper();
        String html = viewEngine.parseTemplate("/com/jhmis/modules/gen/template/findList.ftl", model);
        return html;
    }
}
