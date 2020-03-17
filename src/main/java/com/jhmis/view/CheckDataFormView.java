package com.jhmis.view;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.view.entity.CheckDataForm;
import com.jhmis.view.entity.FLDataDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.view
 * @Author: hdx
 * @CreateTime: 2019-10-28 21:04
 * @Description: 验收单报表
 */
@Component
public class CheckDataFormView {
    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    //根据实体反射进行转换
    public Map<String,Object> getCheckDataFormView(CheckDataForm checkDataForm) throws Exception{
        Map<String,Object> map = new HashMap<>();
        //判断必填是否为空
        if(StringUtils.isEmpty(checkDataForm.getCust_code())){
            throw new Exception("经销商编码不能为空");
        }
        String basic_sql = "select * from view_QYG_CheckDataForm where cust_code = '" + checkDataForm.getCust_code() + "'";
        StringBuilder sb = new StringBuilder();
        sb.append(basic_sql);
        //判断Cbill_code工程单号 是否为空
        if(StringUtils.isNotEmpty(checkDataForm.getCbill_code())){
            sb.append(" and cbill_code like '%" + checkDataForm.getCbill_code() + "%'");
        }
        //判断project_name项目名称 是否为空
        if(StringUtils.isNotEmpty(checkDataForm.getProject_name())){
            sb.append(" and project_name like '%" + checkDataForm.getProject_name() + "%'");
        }
        //判断project_code项目编码 是否为空
        if(StringUtils.isNotEmpty(checkDataForm.getProject_code())){
            sb.append(" and project_code like '%" + checkDataForm.getProject_code() + "%'");
        }

        //判断verify_num验收次数 是否为空
        if(StringUtils.isNotEmpty(checkDataForm.getVerify_num())){
            sb.append(" and verify_num like '%" + checkDataForm.getVerify_num() + "%'");
        }

        //判断is_send是否传输GPMS 是否为空
        if(StringUtils.isNotEmpty(checkDataForm.getIs_send())){
            sb.append(" and is_send like '%" + checkDataForm.getIs_send() + "%'");
        }

        //判断product_group_name产品组 是否为空
        if(StringUtils.isNotEmpty(checkDataForm.getProduct_group_name())){
            sb.append(" and product_group_name like '%" + checkDataForm.getProduct_group_name() + "%'");
        }

        //判断验收状态是否为空
        if(StringUtils.isNotEmpty(checkDataForm.getVerify_status())){
            sb.append(" and verify_status = '" + checkDataForm.getVerify_status() + "'");
        }

        //查询总条数
        List<CheckDataForm> listCount = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(CheckDataForm.class));
        //分页参数
        int curPage = checkDataForm.getPageNo();
        int pageSize = checkDataForm.getPageSize();
        int startRow = (curPage - 1) * pageSize;
        sb.append(" limit " + startRow + ","+pageSize);
        List<CheckDataForm> listCheckDataForm = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(CheckDataForm.class));
        map.put("listCheckDataForm",listCheckDataForm);
        if(listCount==null || listCount.size()==0){
            map.put("count",0);
        }else{
            map.put("count",listCount.size());
        }
        return map;
    }
}
