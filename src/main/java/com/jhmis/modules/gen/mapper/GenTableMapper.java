package com.jhmis.modules.gen.mapper;

import com.jhmis.modules.gen.entity.GenTable;
import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;

import org.apache.ibatis.annotations.Param;

@MyBatisMapper
public interface GenTableMapper
extends BaseMapper<GenTable> {
    public int buildTable(@Param(value = "sql") String var1);
}

