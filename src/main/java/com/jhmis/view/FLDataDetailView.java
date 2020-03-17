package com.jhmis.view;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.view.entity.FLDataDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.view
 * @Author: hdx
 * @CreateTime: 2019-10-28 10:49
 * @Description: HPS系统返利明细报表
 */
@Component
public class FLDataDetailView {
    @Autowired
    @Qualifier("secondJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    //根据实体反射进行转换
    public Map<String,Object> getFLDataDetail(FLDataDetail fLDataDetail) throws Exception{
        Map<String,Object> map = new HashMap<>();
        //判断必填是否为空
        if(StringUtils.isEmpty(fLDataDetail.getCustomer_code_sp())){
            throw new Exception("经销商编码不能为空");
        }
        String basic_sql = "select * from view_QYG_FLDataDetail where customer_code_sp = '" + fLDataDetail.getCustomer_code_sp() + "'";
        StringBuilder sb = new StringBuilder();
        sb.append(basic_sql);
        //判断project_no工程单号 是否为空
        if(StringUtils.isNotEmpty(fLDataDetail.getProject_no())){
          sb.append(" and project_no like '%" + fLDataDetail.getProject_no() + "%'");
        }
        //判断project_code项目编码 是否为空
        if(StringUtils.isNotEmpty(fLDataDetail.getProject_code())){
            sb.append(" and project_code like '%" + fLDataDetail.getProject_code() + "%'");
        }
        //判断project_name项目编名称 是否为空
        if(StringUtils.isNotEmpty(fLDataDetail.getProject_name())){
            sb.append(" and project_name like '%" + fLDataDetail.getProject_name() + "%'");
        }

        //判断contract_no合同编号 是否为空
        if(StringUtils.isNotEmpty(fLDataDetail.getContract_no())){
            sb.append(" and contract_no like '%" + fLDataDetail.getContract_no() + "%'");
        }

        //判断material_descrition物料名称
        if(StringUtils.isNotEmpty(fLDataDetail.getMaterial_descrition())){
            sb.append(" and material_descrition like '%" + fLDataDetail.getMaterial_descrition() + "%'");
        }

        //判断bill_state结算单状态 是否为空
        if(StringUtils.isNotEmpty(fLDataDetail.getBill_state())){
            sb.append(" and bill_state = '" + fLDataDetail.getBill_state() + "'");
        }
        //查询总条数
        List<FLDataDetail> listCount = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(FLDataDetail.class));
        //分页参数
        int curPage = fLDataDetail.getPageNo();
        int pageSize = fLDataDetail.getPageSize();
        int startRow = (curPage - 1) * pageSize;
        sb.append(" limit " + startRow + ","+pageSize);
        List<FLDataDetail> listFLDataDetail = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper(FLDataDetail.class));
        map.put("listFLDataDetail",listFLDataDetail);
        if(listCount==null || listCount.size()==0){
            map.put("count",0);
        }else{
            map.put("count",listCount.size());
        }
        return map;
    }
}

