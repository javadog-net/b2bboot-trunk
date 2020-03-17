package com.jhmis.modules.gen.mapper;

import com.jhmis.modules.gen.entity.GenTable;
import com.jhmis.modules.gen.entity.GenTableColumn;
import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;

import java.util.List;

@MyBatisMapper
public interface GenTableColumnMapper
extends BaseMapper<GenTableColumn> {
    public void deleteByGenTable(GenTable var1);

    public void deleteByGenTableByLogic(GenTable var1);

    public List<GenTableColumn> findListAllStatus(GenTable var1);
}

