package com.jhmis.modules.cms.service;

import com.jhmis.common.config.Global;
import com.jhmis.modules.cms.entity.CmsModel;
import com.jhmis.modules.cms.entity.CmsModelField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CmsOperateTableService {
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate primaryJdbcTemplate;

    public void createTable(CmsModel cmsModel, List<CmsModelField> cmsModelFieldList){
        StringBuffer sbuffer = new StringBuffer(
                "DROP TABLE IF EXISTS cms_"+cmsModel.getTableName()+"_field;" +
                        "CREATE TABLE cms_"+cmsModel.getTableName()+"field （" +
                        "id  VARCHAR(64) NOT NULL,"+
                        cmsModel.getId()+" VARCHAR(64)  NOT NULL COMMENT '模型ID,')");

        for(CmsModelField cmsModelField:cmsModelFieldList){
            sbuffer.append(cmsModelField.getField()+" VARCHAR("+((cmsModelField.getMaxLength()!=null && cmsModelField.getMaxLength()>0)?cmsModelField.getMaxLength():100)+") "+
                    (Global.YES.equals(cmsModelField.getIsRequire())?" NOT NULL":"")).append(",");
        }
        sbuffer.append("  PRIMARY KEY (`id`)");
        primaryJdbcTemplate.execute(sbuffer.toString());
    }
}
