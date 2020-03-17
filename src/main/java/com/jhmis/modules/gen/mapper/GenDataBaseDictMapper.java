package com.jhmis.modules.gen.mapper;

import com.jhmis.modules.gen.entity.GenTable;
import com.jhmis.modules.gen.entity.GenTableColumn;
import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;

import java.util.List;

@MyBatisMapper
public interface GenDataBaseDictMapper
extends BaseMapper<GenTableColumn> {
    public List<GenTable> findTableList(GenTable var1);

    public List<GenTable> findTableListByName(GenTable var1);

    public List<GenTableColumn> findTableColumnList(GenTable var1);

    public List<String> findTablePK(GenTable var1);
}

